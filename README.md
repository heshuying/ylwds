
## 页面展示

以下为商城项目的部分页面，由于篇幅所限，无法一一列举，重要节点及重要功能的页面都已整理在下方。

### 商城页面预览

- 商城首页 1

	![index](https://images.gitee.com/uploads/images/2019/1215/232410_c576a209_5249807.gif)

- 商城首页 2

	![index](https://images.gitee.com/uploads/images/2019/1106/173406_98e57170_5249807.png)

- 商品搜索

	![search](https://images.gitee.com/uploads/images/2019/1106/173406_5eecced6_5249807.png)

- 购物车

	![cart](https://images.gitee.com/uploads/images/2019/1106/173406_90c8f2a0_5249807.png)
	
- 订单结算

	![settle](https://images.gitee.com/uploads/images/2019/1106/173406_caa4e890_5249807.png)
			
- 订单列表

	![orders](https://images.gitee.com/uploads/images/2019/1106/173406_b3c86350_5249807.png)	
	
- 支付页面

	![settle](https://images.gitee.com/uploads/images/2019/1106/173406_638e680d_5249807.png)


### 后台管理页面

- 登录页

	![login](https://images.gitee.com/uploads/images/2019/1106/173406_2268bfd1_5249807.png)

- 轮播图管理

	![carousel](https://images.gitee.com/uploads/images/2019/1106/173406_4baf9084_5249807.png)
	
- 新品上线

    ![config](https://images.gitee.com/uploads/images/2019/1106/173406_503cca1e_5249807.png)

- 分类管理

	![category](https://images.gitee.com/uploads/images/2019/1106/173406_3d43de1c_5249807.png)

- 商品管理

	![goods](https://images.gitee.com/uploads/images/2019/1106/173406_1c2b26d9_5249807.png)

- 商品编辑

	![edit](https://images.gitee.com/uploads/images/2019/1106/173406_9dbb70a2_5249807.png)

- 订单管理

	![order](https://images.gitee.com/uploads/images/2019/1106/173406_5cc854a0_5249807.png)

![newbee-mall-info](https://images.gitee.com/uploads/images/2019/1106/173406_5d7ace62_5249807.png)

## 码值
用户类型： 01:个人客户;02:商户；03平台；04：供应商
用户状态：0-待审核 1-正常 2-锁定；3-审核不通过；4-禁用

订单状态：
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

结算状态： 1-待确认；2-待平台更改；3-待开票   4-待付款   5-已完结
