package com.hailian.ylwmall.service;

import com.kjtpay.gateway.common.domain.base.RequestBase;
import com.kjtpay.gateway.common.domain.base.ResponseParameter;

import javax.servlet.http.HttpServletRequest;

public interface PayService {
//    RequestBase ensureTradeOld(String outTradeNo);

    RequestBase ensureTrade(String orderId, String payType, HttpServletRequest request);

    ResponseParameter tradeSettle(String origOutTradeNo);

    ResponseParameter agreementPayConfirm(String payToken, String phoneCheckCode);
}
