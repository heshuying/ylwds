package ltd.newbee.mall.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * Created by 01440590 on 2019/1/24.
 */
@Data
public class TransferToCardBizContent {
    @SerializedName("out_trade_no")
    @Expose
    private String outTradeNo;//平台(商户)订单号，字母数字下划线，确保每笔订单唯一

    @SerializedName("payer_identity_type")
    @Expose
    private String payerIdentityType;//出款快捷通会员标识类型，默认1 1-快捷通会员ID 2-快捷通会员登录号

    @SerializedName("payer_identity")
    @Expose
    private String payerIdentity;//出款账号

    @SerializedName("amount")
    @Expose
    private String amount;//出款金额，15位以内最大保留2位精度数字

    @SerializedName("currency")
    @Expose
    private String currency;//币种，默认人民币CNY

    @SerializedName("bank_card_no")
    @Expose
    private String bankCardNo;//银行卡号，字母数字

    @SerializedName("bank_account_name")
    @Expose
    private String bankAccountName;//银行卡账户名，不能包含数字

    @SerializedName("bank_code")
    @Expose
    private String bankCode;//银行编码，字母

    @SerializedName("bank_name")
    @Expose
    private String bankName;//银行名称

    @SerializedName("bank_branch_name")
    @Expose
    private String bankBranchName;//银行分支行名称

    @SerializedName("bank_line_no")
    @Expose
    private String bankLineNo;//分支行行号。若为大额或者对公出款，该字段务必填写。否则可能会出现部分银行出款失败或者出款被退票等现象

    @SerializedName("biz_product_code")
    @Expose
    private String bizProductCode;//业务产品码 10220-付款到卡（次日） 10221-付款到卡（普通）

    @SerializedName("pay_product_code")
    @Expose
    private String payProductCode;//支付产品码 14-付款到银行卡-对私 15-付款到银行卡-对公

    @SerializedName("notify_url")
    @Expose
    private String notifyUrl;//通知
}
