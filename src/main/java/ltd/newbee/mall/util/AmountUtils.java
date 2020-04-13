package ltd.newbee.mall.util;

import java.math.BigDecimal;

public class AmountUtils {
    public static String toYuan(Object source){
        if(source == null){
            return "0.00";
        }
        BigDecimal bigDecimal = new BigDecimal(source.toString());
        bigDecimal = bigDecimal.divide(new BigDecimal(100)).setScale(2);
        return bigDecimal.toString();
    }
    public static int toFen(Object source){
        if(source == null){
            return 0;
        }
       try {
           BigDecimal bigDecimal = new BigDecimal(source.toString());
           bigDecimal = bigDecimal.multiply(new BigDecimal(100)).setScale(0);
           return bigDecimal.intValue();
       }catch (Exception e){
           return 0;
       }
    }
    public static String toPercent(Object source){
        if(source == null){
            return "0.00";
        }
        BigDecimal bigDecimal = new BigDecimal(source.toString());
        bigDecimal = bigDecimal.divide(new BigDecimal(100)).setScale(5);
        return bigDecimal.toString();
    }
    public static String multiply100(Object source){
        if(source == null){
            return "0.00";
        }
        BigDecimal bigDecimal = new BigDecimal(source.toString());
        bigDecimal = bigDecimal.multiply(new BigDecimal(100)).setScale(3);
        return bigDecimal.toString();
    }
    public static String multiply(Object source,Object des){
        if(source == null || des == null){
            return "0.00";
        }
        BigDecimal bigDecimal = new BigDecimal(source.toString());
        bigDecimal = bigDecimal.multiply(new BigDecimal(des.toString())).setScale(0,BigDecimal.ROUND_HALF_UP);
        return bigDecimal.toString();
    }
    public static String add(Object source,Object des){
        if(source == null || des == null){
            return "0.00";
        }
        BigDecimal bigDecimal = new BigDecimal(source.toString());
        bigDecimal = bigDecimal.add(new BigDecimal(des.toString())).setScale(0,BigDecimal.ROUND_HALF_UP);
        return bigDecimal.toString();
    }
}
