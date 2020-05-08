package com.hailian.ylwmall.dto.pay;

import lombok.Data;

/**
 * 担保支付回调bean
 * @author 19033323
 */
@Data
public class EnsureTradeCallBackDto {
    private String notify_id;
    private String notify_type;
    private String notify_time;
    private String _input_charset;
    private String sign;
    private String sign_type;
    private String version;
    private String outer_trade_no;
    private String inner_trade_no;
    private String trade_status;
    private String trade_amount;
    private String gmt_create;
    private String gmt_payment;
    private String gmt_close;
    private String failReason;
    private String failCode;
    private String signToken;
    private String bankCode;
    private String bankName;

}
