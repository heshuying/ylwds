package ltd.newbee.mall.controller.mall;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import ltd.newbee.mall.common.Constants;
import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.controller.vo.NewBeeMallUserVO;
import ltd.newbee.mall.entity.MallUser;
import ltd.newbee.mall.entity.TbUser;
import ltd.newbee.mall.entity.TbUserAddr;
import ltd.newbee.mall.service.NewBeeMallOrderService;
import ltd.newbee.mall.service.NewBeeMallUserService;
import ltd.newbee.mall.service.TbUserAddrService;
import ltd.newbee.mall.service.TbUserService;
import ltd.newbee.mall.util.BeanUtil;
import ltd.newbee.mall.util.MD5Util;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class PersonalController {

    @Resource
    private NewBeeMallUserService newBeeMallUserService;
    @Autowired
    private NewBeeMallOrderService orderService;
    @Autowired
    private TbUserAddrService userAddrService;
    @Autowired
    private TbUserService userService;

    @GetMapping("/personal")
    public String personalPage(HttpServletRequest request,
                               HttpSession httpSession) {
        request.setAttribute("path", "personal");
        return "mall/personal";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.removeAttribute(Constants.MALL_USER_SESSION_KEY);
        return "mall/login";
    }

    @GetMapping({"/login", "login.html"})
    public String loginPage() {
        return "mall/login";
    }

    @GetMapping({"/register", "register.html"})
    public String registerPage() {
        return "mall/register";
    }

    @GetMapping("/personal/addresses")
    public String addressesPage() {
        return "mall/addresses";
    }

    @PostMapping("/login")
    @ResponseBody
    public Result login(@RequestParam("loginName") String loginName,
                        @RequestParam("verifyCode") String verifyCode,
                        @RequestParam("password") String password,
                        HttpSession httpSession) {
        if (StringUtils.isEmpty(loginName)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_NAME_NULL.getResult());
        }
        if (StringUtils.isEmpty(password)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_PASSWORD_NULL.getResult());
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
                .eq("login_name",loginName)
                .eq("password_md5", MD5Util.MD5Encode(password, "UTF-8"))
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
            return ResultGenerator.genSuccessResult();
        }
        //登录失败
        return ResultGenerator.genFailResult(loginResult);
    }

    @PostMapping("/register")
    @ResponseBody
    public Result register(@RequestParam("loginName") String loginName,
                           @RequestParam("verifyCode") String verifyCode,
                           @RequestParam("password") String password,
                           HttpSession httpSession) {
        if (StringUtils.isEmpty(loginName)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_NAME_NULL.getResult());
        }
        if (StringUtils.isEmpty(password)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_PASSWORD_NULL.getResult());
        }
        if (StringUtils.isEmpty(verifyCode)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_VERIFY_CODE_NULL.getResult());
        }
        String kaptchaCode = httpSession.getAttribute(Constants.MALL_VERIFY_CODE_KEY) + "";
        if (StringUtils.isEmpty(kaptchaCode) || !verifyCode.equals(kaptchaCode)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_VERIFY_CODE_ERROR.getResult());
        }
        //todo 清verifyCode
        String registerResult = newBeeMallUserService.register(loginName, password);
        //注册成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(registerResult)) {
            return ResultGenerator.genSuccessResult();
        }
        //注册失败
        return ResultGenerator.genFailResult(registerResult);
    }

    @PostMapping("/personal/updateInfo")
    @ResponseBody
    public Result updateInfo(@RequestBody MallUser mallUser, HttpSession httpSession) {
        NewBeeMallUserVO mallUserTemp = newBeeMallUserService.updateUserInfo(mallUser, httpSession);
        if (mallUserTemp == null) {
            Result result = ResultGenerator.genFailResult("修改失败");
            return result;
        } else {
            //返回成功
            Result result = ResultGenerator.genSuccessResult();
            return result;
        }
    }

    /**
     * 我的订单列表
     * @param params
     * @param request
     * @param httpSession
     * @return
     */
    @GetMapping("/my/orders")
    public String orderListPage(@RequestParam Map<String, Object> params, HttpServletRequest request, HttpSession httpSession) {
        NewBeeMallUserVO user = (NewBeeMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        params.put("customerId", user.getUserId());
        if (StringUtils.isEmpty(params.get("page"))) {
            params.put("page", 1);
        }
        params.put("limit", Constants.ORDER_SEARCH_PAGE_LIMIT);
        //封装我的订单数据
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        request.setAttribute("orderPageResult", orderService.getMyOrdersForSupplier(pageUtil));
        request.setAttribute("path", "orders");
        return "mall/my-orders";
    }

    /**
     * 我的收获地址
     * @param request
     * @param httpSession
     * @return
     */
    @GetMapping("/my/delivery")
    public String delivery(HttpServletRequest request, HttpSession httpSession) {
        NewBeeMallUserVO user = (NewBeeMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);

        List<TbUserAddr> userAddrList=userAddrService.list(
                new QueryWrapper<TbUserAddr>()
                .eq("user_id",user.getUserId())
                .eq("status",0)
        );
        request.setAttribute("userAddrList", userAddrList);
        request.setAttribute("path", "");
        return "";
    }

    @PostMapping("/my/delivery/add")
    public String addDelivery(@RequestBody TbUserAddr userAddr, HttpServletRequest request, HttpSession httpSession) {
        NewBeeMallUserVO user = (NewBeeMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);

        userAddr.setUserId(user.getUserId());
        userAddr.setCreateTime(new Date());
        userAddr.setStatus(0);
        userAddr.setIsDefault(false);
        userAddrService.save(userAddr);
        return "";
    }

    @PostMapping("/my/delivery/del")
    public String delDelivery(@RequestBody Long deliveryId, HttpServletRequest request, HttpSession httpSession) {
        NewBeeMallUserVO user = (NewBeeMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        TbUserAddr userAddr=userAddrService.getById(deliveryId);
        userAddr.setStatus(1);
        userAddr.setUserId(user.getUserId());
        userAddr.setCreateTime(new Date());
        userAddrService.updateById(userAddr);
        return "";
    }

    @PostMapping("/my/delivery/update")
    public String updateDelivery(@RequestBody TbUserAddr userAddr, HttpServletRequest request, HttpSession httpSession) {
        NewBeeMallUserVO user = (NewBeeMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);

        userAddrService.updateById(userAddr);
        return "";
    }

    @PostMapping("/my/delivery/default")
    public String updateDelivery(@RequestBody Long deliveryId, HttpServletRequest request, HttpSession httpSession) {
        NewBeeMallUserVO user = (NewBeeMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
        TbUserAddr exists=new TbUserAddr();
        exists.setUserId(user.getUserId());
        exists.setIsDefault(false);
        userAddrService.update(exists,new QueryWrapper<TbUserAddr>()
        .eq("user_id", user.getUserId()));

        TbUserAddr defaultAddr=new TbUserAddr();
        defaultAddr.setAddrId(deliveryId);
        defaultAddr.setIsDefault(true);
        userAddrService.updateById(defaultAddr);
        return "";
    }
}
