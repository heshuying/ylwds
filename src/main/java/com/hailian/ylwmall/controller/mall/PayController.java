package com.hailian.ylwmall.controller.mall;

import com.hailian.ylwmall.dto.pay.EnsureTradeReq;
import com.hailian.ylwmall.service.PayService;
import com.hailian.ylwmall.util.KJTPayUtil;
import com.kjtpay.gateway.common.domain.base.RequestBase;
import com.kjtpay.gateway.common.domain.base.ResponseParameter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/pay")
public class PayController {
    @Autowired
    PayService payService;

    /**
     * 担保支付(网银)
     */
    @GetMapping("/ensureTradeBank")
    public String ensureTradeBank(@ModelAttribute EnsureTradeReq reqBean, HttpServletRequest request) {
        // 设置登录人
        Long userId = (Long)request.getSession().getAttribute("loginUserId");
        if(Objects.isNull(userId)){
            return "error/error";
        }
        if(StringUtils.isBlank(reqBean.getOrderId()) || StringUtils.isBlank(reqBean.getPayType())){
            return "error/error";
        }
        reqBean.setPayType("1");
        RequestBase requestBase = payService.ensureTrade(reqBean, request);
        Map<String,String> req = KJTPayUtil.objToMap(requestBase);
        request.setAttribute("map", req);
        return "mall/send";
    }

    /**
     * 担保支付(网银)
     */
    @ResponseBody
    @GetMapping("/ensureTradeAgreement")
    public String ensureTradeAgreement(@ModelAttribute EnsureTradeReq reqBean, HttpServletRequest request) {
        // 设置登录人
        Long userId = (Long)request.getSession().getAttribute("loginUserId");
        if(Objects.isNull(userId)){
            return "error/error";
        }
        if(StringUtils.isBlank(reqBean.getOrderId())){
            return "error/error";
        }
        reqBean.setPayType("2");
        String result = payService.ensureTradeAgreement(reqBean, request);
        return result;
    }

    /**
     * 交易达成
     * @param outTradeNo
     * @param request
     * @return
     */
    @ResponseBody
    @GetMapping("/tradeSettle/{outTradeNo}")
    public ResponseParameter tradeSettle(@PathVariable String outTradeNo, HttpServletRequest request) {
        ResponseParameter result = payService.tradeSettle(outTradeNo);
        return result;
    }

    /**
     * 协议支付/ 直接支付-支付确认
     */
    @ResponseBody
    @GetMapping("/agreementPayConfirm/{payToken}/{phoneCheckCode}")
    public ResponseParameter agreementPayConfirm(@PathVariable String payToken, @PathVariable String phoneCheckCode, HttpServletRequest request) {
        ResponseParameter result = payService.agreementPayConfirm(payToken, phoneCheckCode);
        return result;
    }
}
