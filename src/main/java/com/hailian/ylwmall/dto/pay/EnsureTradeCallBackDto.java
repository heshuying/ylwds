package com.hailian.ylwmall.dto.pay;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * 担保支付回调bean
 * @author 19033323
 */
@Data
public class EnsureTradeCallBackDto {
    @Expose
    @SerializedName("notify_id")
    private String notify_id;
    @Expose
    @SerializedName("notify_type")
    private String notify_type;
    @Expose
    @SerializedName("notify_time")
    private String notify_time;
    @Expose
    @SerializedName("_input_charset")
    private String _input_charset;
    @Expose
    @SerializedName("sign")
    private String sign;
    @Expose
    @SerializedName("sign_type")
    private String sign_type;
    @Expose
    @SerializedName("version")
    private String version;
    @Expose
    @SerializedName("outer_trade_no")
    private String outer_trade_no;
    @Expose
    @SerializedName("inner_trade_no")
    private String inner_trade_no;
    @Expose
    @SerializedName("trade_status")
    private String trade_status;
    @Expose
    @SerializedName("trade_amount")
    private String trade_amount;
    @Expose
    @SerializedName("gmt_create")
    private String gmt_create;
    @Expose
    @SerializedName("gmt_payment")
    private String gmt_payment;
    @Expose
    @SerializedName("gmt_close")
    private String gmt_close;
    @Expose
    @SerializedName("failReason")
    private String failReason;
    @Expose
    @SerializedName("failCode")
    private String failCode;
    @Expose
    @SerializedName("signToken")
    private String signToken;
    @Expose
    @SerializedName("bankCode")
    private String bankCode;
    @Expose
    @SerializedName("bankName")
    private String bankName;

}
