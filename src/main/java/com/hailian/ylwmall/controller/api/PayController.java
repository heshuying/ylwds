package com.hailian.ylwmall.controller.api;

import com.hailian.ylwmall.common.Constants;
import com.hailian.ylwmall.controller.vo.NewBeeMallUserVO;
import com.hailian.ylwmall.dto.pay.EnsureTradeReq;
import com.hailian.ylwmall.service.PayService;
import com.hailian.ylwmall.util.KJTPayUtil;
import com.hailian.ylwmall.util.Result;
import com.hailian.ylwmall.util.ResultGenerator;
import com.kjtpay.gateway.common.domain.base.RequestBase;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

@Api(value = "支付相关接口", tags = {"支付相关接口"})
@Controller
@RequestMapping("/api/pay")
public class PayController {
    @Autowired
    PayService payService;

    /**
     * 担保支付(网银)
     */
    @ApiOperation(value = "担保支付(网银)")
    @GetMapping("/ensureTradeBank")
    public String ensureTradeBank(@ModelAttribute EnsureTradeReq reqBean, HttpServletRequest request) {
        // 设置登录人
        NewBeeMallUserVO user = (NewBeeMallUserVO) request.getSession().getAttribute(Constants.MALL_USER_SESSION_KEY);
        if(user == null){
            return "error/error";
        }
        if(StringUtils.isBlank(reqBean.getOrderId())){
            return "error/error";
        }
        reqBean.setPayType("1");
        RequestBase requestBase = payService.ensureTrade(reqBean, request);
        Map<String,String> req = KJTPayUtil.objToMap(requestBase);
        request.setAttribute("map", req);
        return "mall/send";
    }

    /**
     * 担保支付(协议)
     */
    @ApiOperation(value = "担保支付(协议)")
    @ResponseBody
    @GetMapping("/ensureTradeAgreement")
    public Result ensureTradeAgreement(@ModelAttribute EnsureTradeReq reqBean, HttpServletRequest request) {
        // 设置登录人
        NewBeeMallUserVO user = (NewBeeMallUserVO) request.getSession().getAttribute(Constants.MALL_USER_SESSION_KEY);
        if(user == null){
            return ResultGenerator.genFailResult("用户未登录");
        }
        if(StringUtils.isBlank(reqBean.getOrderId())){
            return ResultGenerator.genFailResult("请求参数错误");
        }
        reqBean.setPayType("2");
        Result result = payService.ensureTradeAgreement(reqBean, request);
        return result;
    }

    /**
     * 交易达成
     */
    @ApiOperation(value = "交易达成")
    @ResponseBody
    @GetMapping("/tradeSettle/{orderId}")
    public Result tradeSettle(@PathVariable String orderId, HttpServletRequest request) {
        NewBeeMallUserVO user = (NewBeeMallUserVO) request.getSession().getAttribute(Constants.MALL_USER_SESSION_KEY);
        if(user == null){
            return ResultGenerator.genFailResult("用户未登录");
        }

        Result result = payService.tradeSettle(orderId, user.getUserId());
        return result;
    }

    /**
     * 协议支付/ 直接支付-支付确认
     */
    @ApiOperation(value = "协议支付/ 直接支付-支付确认")
    @ResponseBody
    @GetMapping("/agreementPayConfirm/{payToken}/{phoneCheckCode}")
    public Result agreementPayConfirm(@PathVariable String payToken, @PathVariable String phoneCheckCode, HttpServletRequest request) {
        Long userId = (Long)request.getSession().getAttribute("loginUserId");
        if(Objects.isNull(userId)){
            return ResultGenerator.genFailResult("用户未登录");
        }
        Result result = payService.agreementPayConfirm(payToken, phoneCheckCode);
        return result;
    }
}
