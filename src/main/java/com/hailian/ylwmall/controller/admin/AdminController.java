package com.hailian.ylwmall.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hailian.ylwmall.common.Constants;
import com.hailian.ylwmall.common.ServiceResultEnum;
import com.hailian.ylwmall.entity.AdminUser;
import com.hailian.ylwmall.entity.TbUser;
import com.hailian.ylwmall.service.AdminUserService;

import com.hailian.ylwmall.service.TbUserService;
import com.hailian.ylwmall.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Resource
    private AdminUserService adminUserService;

    @Autowired
    private TbUserService userService;

    @GetMapping({"/login"})
    public String login() {
        return "admin/login";
    }

    @GetMapping({"/test"})
    public String test() {
        return "admin/test";
    }


    @GetMapping({"", "/", "/index", "/index.html"})
    public String index(HttpServletRequest request) {
        request.setAttribute("path", "index");
        request.setAttribute("categoryCount", 0);
        request.setAttribute("blogCount", 0);
        request.setAttribute("linkCount", 0);
        request.setAttribute("tagCount", 0);
        request.setAttribute("commentCount", 0);
        request.setAttribute("path", "index");
        return "admin/index";
    }

    @PostMapping(value = "/login")
    public String login(@RequestParam("userName") String userName,
                        @RequestParam("password") String password,
                        @RequestParam("verifyCode") String verifyCode,
                        HttpSession session) {
        if (StringUtils.isEmpty(verifyCode)) {
            session.setAttribute("errorMsg", "验证码不能为空");
            return "admin/login";
        }
        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            session.setAttribute("errorMsg", "用户名或密码不能为空");
            return "admin/login";
        }
        String kaptchaCode = session.getAttribute(Constants.Manage_Verify_Code) + "";
        if (StringUtils.isEmpty(kaptchaCode) || !verifyCode.equals(kaptchaCode)) {
            session.setAttribute("errorMsg", "验证码错误");
            return "admin/login";
        }
        TbUser adminUser = userService.getOne(new QueryWrapper<TbUser>()
        .eq("login_name",userName)
        .eq("password_md5", MD5Util.MD5Encode(password, "UTF-8"))
        .in("user_type",new String[]{"03","04"}));

        if (adminUser == null) {
            session.setAttribute("errorMsg", "用户名或密码错误");
            return "admin/login";
        }
        if(adminUser.getUserStatus()==4) {
            session.setAttribute("errorMsg", "用户名或密码错误");
            return "admin/login";
        }
        if(adminUser.getUserStatus()==2) {
            session.setAttribute("errorMsg", "用户已被锁定");
            return "admin/login";
        }
        if(adminUser.getUserStatus()==0) {
            session.setAttribute("errorMsg", "用户资料还未审核");
            return "admin/login";
        }
        session.setAttribute("loginUser", adminUser.getNickName());
        session.setAttribute("loginUserId", adminUser.getUserId());
        session.setAttribute("loginType", adminUser.getUserType());
        session.setAttribute("checked", adminUser.getUserStatus()==0?false:true);

        if("04".equals(adminUser.getUserType())
                && adminUser.getUserStatus()==3){
            return "redirect:http://106.53.192.56/outer/#/registerbus/busCompany?userId=" + adminUser.getUserId();
        }
        //session过期时间设置为7200秒 即两小时
        //session.setMaxInactiveInterval(60 * 60 * 2);
        return "redirect:/admin/index";

    }

    @GetMapping("/profile")
    public String profile(HttpServletRequest request) {
        Integer loginUserId = (int) request.getSession().getAttribute("loginUserId");
        AdminUser adminUser = adminUserService.getUserDetailById(loginUserId);
        if (adminUser == null) {
            return "admin/login";
        }
        request.setAttribute("path", "profile");
        request.setAttribute("loginUserName", adminUser.getLoginUserName());
        request.setAttribute("nickName", adminUser.getNickName());
        return "admin/profile";
    }

    @PostMapping("/profile/password")
    @ResponseBody
    public String passwordUpdate(HttpServletRequest request, @RequestParam("originalPassword") String originalPassword,
                                 @RequestParam("newPassword") String newPassword) {
        if (StringUtils.isEmpty(originalPassword) || StringUtils.isEmpty(newPassword)) {
            return "参数不能为空";
        }
        Integer loginUserId = (int) request.getSession().getAttribute("loginUserId");
        if (adminUserService.updatePassword(loginUserId, originalPassword, newPassword)) {
            //修改成功后清空session中的数据，前端控制跳转至登录页
            request.getSession().removeAttribute("loginUserId");
            request.getSession().removeAttribute("loginUser");
            request.getSession().removeAttribute("errorMsg");
            return ServiceResultEnum.SUCCESS.getResult();
        } else {
            return "修改失败";
        }
    }

    @PostMapping("/profile/name")
    @ResponseBody
    public String nameUpdate(HttpServletRequest request, @RequestParam("loginUserName") String loginUserName,
                             @RequestParam("nickName") String nickName) {
        if (StringUtils.isEmpty(loginUserName) || StringUtils.isEmpty(nickName)) {
            return "参数不能为空";
        }
        Integer loginUserId = (int) request.getSession().getAttribute("loginUserId");
        if (adminUserService.updateName(loginUserId, loginUserName, nickName)) {
            return ServiceResultEnum.SUCCESS.getResult();
        } else {
            return "修改失败";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().removeAttribute("loginUserId");
        request.getSession().removeAttribute("loginUser");
        request.getSession().removeAttribute("errorMsg");
        return "admin/login";
    }
}
