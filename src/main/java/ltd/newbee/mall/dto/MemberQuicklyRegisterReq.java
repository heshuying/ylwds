package ltd.newbee.mall.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * Created by 01440590 on 2019/2/20.
 */
@Data
public class MemberQuicklyRegisterReq {
    @SerializedName("service")
    @Expose
    private String service;//接口名称

    @SerializedName("version")
    @Expose
    private String version;//接口版本号

    @SerializedName("partner_id")
    @Expose
    private String partnerId;//商户号

    @SerializedName("_input_charset")
    @Expose
    private String inputCharset;//商户号

    @SerializedName("sign")
    @Expose
    private String sign;//签名

    @SerializedName("sign_type")
    @Expose
    private String signType;//签名方式

    @SerializedName("return_url")
    @Expose
    private String returnUrl;//快捷通处理完请求后，当前页面自动跳转到商户网站里指定页面的http 路径

    @SerializedName("memo")
    @Expose
    private String memo;//备注

    @SerializedName("outer_order_no")
    @Expose
    private String outerOrderNo;//商户流水号

    @SerializedName("name")
    @Expose
    private String name;//姓名

    @SerializedName("id_card")
    @Expose
    private String idCard;//身份证

    @SerializedName("mobile")
    @Expose
    private String mobile;//手机号

    @SerializedName("need_send_msn")
    @Expose
    private String needSendMsn;//1 发送短信（默认） 2 不发送短信

    @SerializedName("merchant_name")
    @Expose
    private String merchantName;//若用户选择发送短信，可以传入他们的简称,不传就用系统的

    @SerializedName("need_open_fund")
    @Expose
    private String needOpenFund;//是否开通天天聚:1 开通 2 不开通（默认）
}
