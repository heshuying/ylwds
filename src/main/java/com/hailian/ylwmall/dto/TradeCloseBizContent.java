package com.hailian.ylwmall.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * Created by 01440590 on 2019/1/25.
 */
@Data
public class TradeCloseBizContent {
    @SerializedName("out_trade_no")
    @Expose
    private String outTradeNo;//平台(商户)订单号，字母数字下划线，确保每笔订单唯一

    @SerializedName("trade_no")
    @Expose
    private String tradeNo;//快捷通交易订单号
}
