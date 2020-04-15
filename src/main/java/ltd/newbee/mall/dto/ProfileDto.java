package ltd.newbee.mall.dto;

import lombok.Data;

/**
 * Created by 19012964 on 2020/4/15.
 */
@Data
public class ProfileDto {
    /**
     * 用户主键id
     */
    private Long userId;

    /**
     * 公司名
     */
    private String companyName;

    /**
     * 纳税人识别号
     */
    private String taxNo;

    /**
     * 注册手机号
     */
    private String regCellphone;

    /**
     * 营业执照号
     */
    private String compLegalNo;

    /**
     * 营业执照附件
     */
    private String compLegalUrl;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String area;

    /**
     * 详细地址
     */
    private String detail;

    /**
     * 注册地址
     */
    private String compAddr;

    /**
     * 联系人
     */
    private String contactor;

    /**
     * 联系人电话
     */
    private String contactorPhone;

    /**
     * 联系人邮箱
     */
    private String contactorEmail;

    /**
     * 财务联系人
     */
    private String financer;

    /**
     * 财务联系人电话
     */
    private String financerPhone;

    /**
     * 开票类型01-增值；02-普通
     */
    private String taxType;

    /**
     * 银行开户全称
     */
    private String bankHolder;

    /**
     * 银行账户
     */
    private String bankAccNo;

    /**
     * 开户行名称
     */
    private String bankName;

    /**
     * 开户行代码
     */
    private String bankCode;

    /**
     * 供应商承诺书
     */
    private String promiseUri;

    /**
     *  收货地址-省
     */
    private String deliveryProv;

    /**
     * 收货地址-市
     */
    private String deliveryCity;

    /**
     * 收货地址-区
     */
    private String deliveryArea;

    /**
     * 收货地址-详细地址
     */
    private String deliveryDetail;

    /**
     * 收货地址-收货人手机号
     */
    private String deliveryPhone;
}
