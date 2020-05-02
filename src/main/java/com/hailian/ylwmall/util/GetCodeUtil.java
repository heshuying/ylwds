package com.hailian.ylwmall.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class GetCodeUtil {

    /**
     * 根据用户id获取订单id 规则：用户id加1000000,拼接yyMMddHHmmss再拼接0-9一位随机数
     * 示例：用户id为852628,当前时间为2015年12月5号11点48分25秒，生成的随机数为3，
     * 则用户的订单号为18526281512051148253
     */
    public static synchronized String getOrderId(Long userId) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmmss");
        String dateString = formatter.format(currentTime);
        String orderId = (userId + 1000000) + dateString + new Random().nextInt(10);
        return orderId;
    }

}
