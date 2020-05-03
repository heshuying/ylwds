package com.hailian.ylwmall.service;

import com.hailian.ylwmall.dto.pay.EnsureTradeReq;
import com.hailian.ylwmall.util.Result;
import com.kjtpay.gateway.common.domain.base.RequestBase;
import com.kjtpay.gateway.common.domain.base.ResponseParameter;

import javax.servlet.http.HttpServletRequest;

public interface PayService {
//    RequestBase ensureTradeOld(String outTradeNo);

    RequestBase ensureTrade(EnsureTradeReq reqBean, HttpServletRequest request);

    Result ensureTradeAgreement(EnsureTradeReq reqBean, HttpServletRequest request);

    ResponseParameter tradeSettle(String origOutTradeNo);

    ResponseParameter agreementPayConfirm(String payToken, String phoneCheckCode);
}
