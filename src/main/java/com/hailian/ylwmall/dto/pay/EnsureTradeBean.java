package com.hailian.ylwmall.dto.pay;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kjtpay.gateway.common.domain.instanttrade.TradeInfo;
import lombok.Data;

import java.util.Map;

@Data
public class EnsureTradeBean {
    @SerializedName("payer_identity_type")
    @Expose
    private String payerIdentityType;//买家快捷通会员标识类型，默认1，1-快捷通会员ID，2-快捷通会员登入号，可空

    @SerializedName("payer_identity")
    @Expose
    private String payerIdentity;//如没有快捷通会员ID和登入账号，则填写固定值：anonymous，非空

    @SerializedName("payer_platform_type")
    @Expose
    private String payerPlatformType;//买家平台类型，固定值1

    @SerializedName("payer_ip")
    @Expose
    private String payerIp;//买家公网ip地址

    @SerializedName("inexpectant_pay_product_code")
    @Expose
    private String inexpectantPayProductCode;//不期望使用的支付方式，可空

    @SerializedName("biz_product_code")
    @Expose
    private String bizProductCode;//业务产品码 20601

    @SerializedName("cashier_type")
    @Expose
    private String cashierType;//收银台类型，WEB

    @SerializedName("timeout_express")
    @Expose
    private String timeoutExpress;//订单允许的最晚付款时间 默认：2h，不接受小数

    @SerializedName("merchant_custom")
    @Expose
    private String merchantCustom;//商户自定义域

    @SerializedName("return_url")
    @Expose
    private String returnUrl;//返回页面路径:

    @SerializedName("pay_method")
    @Expose
    private Map<String,String> payMethod;//支付方式

    @SerializedName("trade_info")
    @Expose
    private TradeInfo tradeInfo;//交易信息

    @SerializedName("terminal_info")
    @Expose
    private Map<String,String> terminalInfo;//存放终端类型(terminal_type)、ip(ip)等信息字段，非空
}
