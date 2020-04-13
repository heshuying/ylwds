package ltd.newbee.mall.util;

/**
 * Created by 19012964 on 2020/4/10.
 */
public class Const {
    public static enum EnumPayType {
        PAY_TYPE_01("01","快捷通"),
        PAY_TYPE_02("02","积分支付"),
        PAY_TYPE_03("03","快捷通-微信"),
        PAY_TYPE_04("04","快捷通-支付宝");
        private String key;
        private String value;
        EnumPayType(String key, String value){
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public static EnumPayType getByKey(String key){
            for(EnumPayType type:EnumPayType.values()){
                if(type.getKey().equals(key)){
                    return type;
                }
            }
            return null;
        }
    }
}
