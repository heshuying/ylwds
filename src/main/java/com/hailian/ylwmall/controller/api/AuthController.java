package com.hailian.ylwmall.controller.api;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hailian.ylwmall.common.Constants;
import com.hailian.ylwmall.common.ServiceResultEnum;
import com.hailian.ylwmall.controller.vo.NewBeeMallUserVO;
import com.hailian.ylwmall.dto.LoginDto;
import com.hailian.ylwmall.dto.ProfileDto;
import com.hailian.ylwmall.dto.RegisterFirstDto;
import com.hailian.ylwmall.entity.TbUser;
import com.hailian.ylwmall.service.TbUserService;
import com.hailian.ylwmall.util.BeanUtil;
import com.hailian.ylwmall.util.MD5Util;
import com.hailian.ylwmall.util.Result;
import com.hailian.ylwmall.util.ResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by 19012964 on 2020/4/15.
 */
@Api(value = "用户管理相关接口", tags = {"用户管理"})
@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private TbUserService userService;
    @Autowired
    private HttpSession httpSession;

    @ApiOperation(value = "登陆")
    @PostMapping("/login")
    @ResponseBody
    public Result login(@RequestBody LoginDto loginDto) {
        if (loginDto==null||
                StringUtils.isEmpty(loginDto.getLoginName())
                ||StringUtils.isEmpty(loginDto.getPassword())) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_NAME_NULL.getResult());
        }
//        if (StringUtils.isEmpty(verifyCode)) {
//            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_VERIFY_CODE_NULL.getResult());
//        }
        String kaptchaCode = httpSession.getAttribute(Constants.MALL_VERIFY_CODE_KEY) + "";
//        if (StringUtils.isEmpty(kaptchaCode) || !verifyCode.equals(kaptchaCode)) {
//            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_VERIFY_CODE_ERROR.getResult());
//        }
        //todo 清verifyCode
        String loginResult=ServiceResultEnum.SUCCESS.getResult();
        TbUser user = userService.getOne(new QueryWrapper<TbUser>()
                .eq("login_name",loginDto.getLoginName())
                .eq("password_md5", MD5Util.MD5Encode(loginDto.getPassword(), "UTF-8"))
                .in("user_type",new String[]{"01","02"}));
        if (user == null||user.getUserStatus()==4) {
            loginResult= "用户名或密码错误";

        }else if(user.getUserStatus()==2){
            loginResult= "用户已被锁定";
        }else if(user.getUserStatus()==0){
            loginResult= "用户资料还未审核";
        }else if(user.getUserStatus()==3){
            loginResult= "checkFail,"+ user.getUserId();
        }

        //登录成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(loginResult)) {
            NewBeeMallUserVO newBeeMallUserVO = new NewBeeMallUserVO();
            BeanUtil.copyProperties(user, newBeeMallUserVO);
            httpSession.setAttribute(Constants.MALL_USER_SESSION_KEY, newBeeMallUserVO);
            return ResultGenerator.genSuccessResult(newBeeMallUserVO);
        }
        //登录失败
        return ResultGenerator.genFailResult(loginResult);
    }

    @ApiOperation(value = "注册")
    @PostMapping("/register/first")
    @ResponseBody
    public Result registFirst(@RequestBody RegisterFirstDto registerFirstDto){

        return userService.register(registerFirstDto);
    }

    @ApiOperation(value = "获取用户注册信息")
    @GetMapping("/profile/info")
    @ResponseBody
    public Result profileInfo(@RequestParam Long userId){
        return userService.getProfile(userId);
    }

    @ApiOperation(value = "用户注册信息更新")
    @PostMapping("/profile/update")
    @ResponseBody
    public Result updateProfile(@RequestBody ProfileDto dto){
        return userService.updateUserProfile(dto);
    }

    @ApiOperation(value = "获取session信息")
    @GetMapping("/user/info")
    @ResponseBody
    public Result loginUser(){
        NewBeeMallUserVO user = (NewBeeMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        return ResultGenerator.genSuccessResult(user);
    }
}
