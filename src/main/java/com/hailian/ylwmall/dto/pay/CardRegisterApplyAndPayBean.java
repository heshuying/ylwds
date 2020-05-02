package com.hailian.ylwmall.dto.pay;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * 协议申请加支付
 * @author 19033323
 */
@Data
public class CardRegisterApplyAndPayBean {
    @Expose
    @SerializedName("pay_product_code")
    private String payProductCode;

    @Expose
    @SerializedName("amount")
    private String amount;

    @Expose
    @SerializedName("token_id")
    private String tokenId;

    @Expose
    @SerializedName("signing_pay")
    private String signingPay;

    @Expose
    @SerializedName("bank_account_name")
    private String bankAccountName;

    @SerializedName("certificates_type")
    @Expose
    private String certificatesType;

    @SerializedName("certificates_number")
    @Expose
    private String certificatesNumber;

    @SerializedName("bank_card_no")
    @Expose
    private String bankCardNo;

    @SerializedName("cvv2")
    @Expose
    private String cvv2;

    @SerializedName("valid_date")
    @Expose
    private String validDate;

    @SerializedName("phone_num")
    @Expose
    private String phoneNum;

    @Expose
    @SerializedName("extension")
    private String extension;

    @Expose
    @SerializedName("token_valid_minutes")
    private String tokenValidMinutes;
}
