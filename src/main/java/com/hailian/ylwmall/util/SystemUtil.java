package com.hailian.ylwmall.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 13
 * @qq交流群 796794009
 * @email 2449207463@qq.com
 * @link https://github.com/newbee-ltd
 */
public class SystemUtil {

    private SystemUtil() {
    }

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    /**
     * 登录或注册成功后,生成保持用户登录状态会话token值
     *
     * @param src:为用户最新一次登录时的now()+user.id+random(4)
     * @return
     */
    public static String genToken(String src) {
        if (null == src || "".equals(src)) {
            return null;
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(src.getBytes());
            String result = new BigInteger(1, md.digest()).toString(16);
            if (result.length() == 31) {
                result = result + "-";
            }
            System.out.println(result);
            return result;
        } catch (Exception e) {
            return null;
        }
    }
    public static Long buildOrderNo(Long supplerId){
        String no=sdf.format(new Date())+(1 + (int) (Math.random() * 10000))+supplerId;
        return Long.parseLong(no);
    }
    public static String buildSettleNo(Long supplerId){
        String no=sdf.format(new Date())+(1 + (int) (Math.random() * 10000))+supplerId;
        return no;
    }

    public static String buildSettleName(String name, Date start, Date end){
        StringBuilder settleName=new StringBuilder(name);
        int year=DateTimeUtil.getYearOfDate(start);

        int mounth = DateTimeUtil.getMonthOfDate(start);
        int endMounth=DateTimeUtil.getMonthOfDate(end);
        if(mounth==endMounth){
            settleName.append(year).append("年").append(mounth).append("月").append("对账单");
        }else{
            settleName.append(year).append("年").append(mounth).
                    append("、").append(endMounth).append("月").append("对账单");
        }
        return settleName.toString();
    }

}
