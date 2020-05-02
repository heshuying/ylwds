package com.hailian.ylwmall.dto.pay;

import lombok.Data;

/**
 * 担保支付请求参数Bean
 */
@Data
public class EnsureTradeReq {
    // 订单id
    String orderId;
    // 支付方式（1：银联支付 2：协议支付 3：扫码支付）
    String payType;

}
