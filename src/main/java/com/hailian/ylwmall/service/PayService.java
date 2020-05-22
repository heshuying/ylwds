package com.hailian.ylwmall.service;

import com.hailian.ylwmall.dto.pay.EnsureTradeCallBackDto;
import com.hailian.ylwmall.dto.pay.EnsureTradeReq;
import com.hailian.ylwmall.dto.pay.RefundCallBackDto;
import com.hailian.ylwmall.util.Result;
import com.kjtpay.gateway.common.domain.base.RequestBase;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface PayService {
//    RequestBase ensureTradeOld(String outTradeNo);

    RequestBase ensureTrade(EnsureTradeReq reqBean, HttpServletRequest request);

    Result ensureTradePurse(EnsureTradeReq reqBean, HttpServletRequest request);

    Result ensureTradeAgreement(EnsureTradeReq reqBean, HttpServletRequest request);

    Result tradeSettle(String origOutTradeNo, Long userId);

    Result agreementPayConfirm(Long userId, String orderId, String phoneCheckCode);

    Result tradeRefund(String orderId, Long userId);

    Result tradeQuery(String orderId, String payType);

    @Transactional
    Boolean ensureTradeAsyncNotify(EnsureTradeCallBackDto reqBean);

    @Transactional
    void tradeRefundAsyncNotify(RefundCallBackDto reqBean);

}
