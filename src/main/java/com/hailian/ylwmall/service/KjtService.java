package com.hailian.ylwmall.service;

/**
 * Created by 01440590 on 2019/1/22.
 */
public interface KjtService {
    /**
     * 及时付款生成付款码
     * @param outTradeNo 商户订单号
     * @param subject 商品名称
     * @param price 单价
     * @param quantity 数量
     * @param totalAmount 金额
     * @param payMethod 付款方式 04-->支付宝  03-->微信
     */
    String instantTrade(String outTradeNo, String subject, String price, String quantity, String totalAmount,
                        String payerIp, String payMethod);//及时到账接口

    /**
     * 商户验签
     * @param verifyData 需要眼前字符串
     * @return
     */
    boolean verify(String verifyData);

    /**
     * 提现到银行卡
     * @param outTradeNo 商户订单号
     * @param amount 提现金额
     * @param bankCardNo 银行卡号
     * @param bankAccountName 姓名
     * @param bankCode 银行简称
     * @param bankName 银行名称
     * @param bankBranchName 支行
     * @param productCode 支付产品码 14-对私  15-对公
     * @return
     */
    String transferToCard(String outTradeNo, String amount, String bankCardNo, String bankAccountName, String bankCode,
                          String bankName, String bankBranchName, String productCode);


    /**
     * 只有交易状态为等待买家付款时才能关闭交易。
     * @param outTradeNo
     * @return
     */
    String tradeClose(String outTradeNo, String tradeNo);

    /**
     * 退款接口
     * @param outTradeNo 退款流水号
     * @param origOutTradeNo 原始平台订单号
     * @param refundAmount 退款金额（单位：元 精确到小数点后两位）
     * @return
     */
    String tradeRefund(String outTradeNo, String origOutTradeNo, String refundAmount);

    /**
     * 个人账户快速注册接口
     * @param outOrderNo 商户流水号
     * @param name 姓名
     * @param idCard 身份证
     * @param mobile 手机号
     * @param merchantName 简称
     * @return
     */
    String memberQuicklyRegister(String outOrderNo, String name, String idCard, String mobile, String merchantName);

    /**
     * 转账到户
     * @param outTradeNo 平台(商户)订单号
     * @param payeeIdentity 入款账号
     * @param transferAmount 转账金额 两位小数 单位元
     * @return
     */
    String transferToAccount(String outTradeNo, String payeeIdentity, String transferAmount);
}
