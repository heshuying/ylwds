package com.hailian.ylwmall.dto.pay;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 担保支付请求参数Bean
 */
@Data
@ApiModel(value = "担保支付请求参数")
public class EnsureTradeReq {
    @ApiModelProperty(value="订单id",name="订单id")
    String orderId;
    @ApiModelProperty(value="支付方式（1：银联支付 2：协议支付 3：扫码支付）",name="支付方式")
    String payType;

    @ApiModelProperty(value="证件类型：01身份证 02军官证 03护照 04户口簿",name="证件类型")
    private String certificatesType;
    @ApiModelProperty(value="证件号码",name="证件号码")
    private String certificatesNumber;
    @ApiModelProperty(value="账户名",name="账户名")
    private String bankAccountName;
    @ApiModelProperty(value="银行卡号",name="银行卡号")
    private String bankCardNo;
    @ApiModelProperty(value="安全码，信用卡必传",name="安全码")
    private String cvv2;
    @ApiModelProperty(value="信用卡有效期 YYYY/MM",name="信用卡有效期")
    private String validDate;
    @ApiModelProperty(value="手机号",name="手机号")
    private String phoneNum;

}
