package com.hailian.ylwmall.dto.pay;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * 退款回调bean
 * @author 19033323
 */
@Data
public class RefundCallBackDto {
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
    @SerializedName("orig_outer_trade_no")
    private String orig_outer_trade_no;
    @Expose
    @SerializedName("outer_trade_no")
    private String outer_trade_no;
    @Expose
    @SerializedName("inner_trade_no")
    private String inner_trade_no;
    @Expose
    @SerializedName("refund_status")
    private String refund_status;
    @Expose
    @SerializedName("refund_amount")
    private String refund_amount;
    @Expose
    @SerializedName("gmt_refund")
    private String gmt_refund;
    @Expose
    @SerializedName("failReason")
    private String failReason;
    @Expose
    @SerializedName("failCode")
    private String failCode;

}
