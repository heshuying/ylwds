package com.hailian.ylwmall.util;

/**
 * Created by 19012964 on 2020/4/10.
 */
public class Const {

    public static  String Mod_Banner_Key="Banner";
    public static  String Mod_Module_Key="Module";

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

    public static enum UserType {
        Cus_Person("01"),
        Cus_Company("02"),
        Plateform("03"),
        Supplier("04");
        private String key;
        UserType(String key){
            this.key = key;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }


        public static UserType getByKey(String key){
            for(UserType type: UserType.values()){
                if(type.getKey()==key){
                    return type;
                }
            }
            return null;
        }
    }

    public static enum UserStatus {
        Uncheck(0),
        Normal(1),
        Lock(2),
        CheckFail(3),
        Disable(4);
        private Integer key;
        UserStatus(Integer key){
            this.key = key;
        }

        public Integer getKey() {
            return key;
        }

        public void setKey(Integer key) {
            this.key = key;
        }


        public static UserStatus getByKey(Integer key){
            for(UserStatus type: UserStatus.values()){
                if(type.getKey()==key){
                    return type;
                }
            }
            return null;
        }
    }

    public static enum SettleStatus {
        Uncheck(1),
        UnEdit(2),
        UnBill(3),
        UnPay(4),
        Finish(5);
        private Integer key;
        SettleStatus(Integer key){
            this.key = key;
        }

        public Integer getKey() {
            return key;
        }

        public void setKey(Integer key) {
            this.key = key;
        }


        public static SettleStatus getByKey(Integer key){
            for(SettleStatus type: SettleStatus.values()){
                if(type.getKey()==key){
                    return type;
                }
            }
            return null;
        }
    }

    public static enum OrderStatus {
        UnPay(1,"待支付","待支付","待支付"),
        UnDelivery(2, "待发货", "待发货", "待发货"),
        UnAccep(3,"已发货","待收货","待收货"),
        UnComment(4, "待评价", "待评价", "待评价"),
        UnSettle(5, "已完结", "待结算", "待结算"),
        Settling(7,"已完结","结算中","结算中"),
        Settled(8, "已完结", "已结算", "已结算"),
        Cancle(10,"已取消","已取消","已取消"),
        Refund_Confirm(11, "待商家确认", "退货待确认", "退货中待商家确认"),

        Refund_Reject(12,"退货中商家驳回退货","已驳回退货","退货中商家驳回"),
        Refund_Delivery(13, "待退货", "退货中待退回货品", "退货中待退回货品"),
        Refund_Deliveried(14,"退货中","退货已发出","退货已发出"),
        Refund_Edit(15, "商品验收中", "退货中编辑退款金额", "编辑退款金额"),
        Refund_ConfirmMoney(16, "确认退款金额", "待客户确认退款金额", "待客户确认退款金额"),
        Refund_Apply(17,"申诉中","退货中客户申诉","退货中客户申诉"),
        Refunding(18, "退款中", "退款中", "退款中"),
        Refund_Settle(19,"退货退款成功","已退货退款待结算","已退货退款待结算");

        private Integer key;
        private String customrDesc;
        private String supplierDesc;
        private String plateDesc;

        OrderStatus(Integer key, String value1, String value2, String value3){
            this.key = key;
            this.customrDesc=value1;
            this.supplierDesc=value2;
            this.plateDesc=value3;
        }

        public Integer getKey() {
            return key;
        }
        public String getCustomerDesc() {
            return customrDesc;
        }
        public String getSupplierDesc() {
            return supplierDesc;
        }
        public String getPlateDesc() {
            return plateDesc;
        }

        public void setKey(Integer key) {
            this.key = key;
        }


        public static OrderStatus getByKey(Integer key){
            for(OrderStatus type: OrderStatus.values()){
                if(type.getKey()==key){
                    return type;
                }
            }
            return null;
        }
    }
}
