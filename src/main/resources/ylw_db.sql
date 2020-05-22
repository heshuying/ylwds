
-- ----------------------------
-- Table structure for tb_goods_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_goods_category`;
CREATE TABLE `tb_goods_category` (
  `category_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '分类id',
  `category_level` tinyint(4) NOT NULL DEFAULT '0' COMMENT '分类级别(1-一级分类 2-二级分类 3-三级分类)',
  `parent_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '父分类id',
  `category_name` varchar(50) NOT NULL DEFAULT '' COMMENT '分类名称',
  `validity_time` int(11) DEFAULT '0' COMMENT '售后有效期',
  `category_rank` int(11) NOT NULL DEFAULT '0' COMMENT '排序值(字段越大越靠前)',
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识字段(0-未删除 1-已删除)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` int(11) NOT NULL DEFAULT '0' COMMENT '创建者id',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user` int(11) DEFAULT '0' COMMENT '修改者id',
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for tb_goods_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_goods_info`;
CREATE TABLE `tb_goods_info` (
  `goods_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '商品表主键id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `goods_name` varchar(200) NOT NULL DEFAULT '' COMMENT '商品名',
  `goods_intro` varchar(200) DEFAULT '' COMMENT '商品简介',
  `goods_category_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '关联分类id',
  `goods_cover_img` varchar(200) DEFAULT '/admin/dist/img/no-img.png' COMMENT '商品主图',
  `goods_carousel` varchar(500) DEFAULT '/admin/dist/img/no-img.png' COMMENT '商品轮播图',
  `goods_detail_content` text COMMENT '商品详情',
  `original_price` decimal(11,2) DEFAULT '0.00' COMMENT '商品价格',
  `selling_price` decimal(11,2) DEFAULT '0.00' COMMENT '商品实际售价',
  `profit` decimal(11,2) DEFAULT '0.00' COMMENT '平台利润',
  `price` decimal(11,2) DEFAULT '0.00' COMMENT '商品实际售价+平台利润',
  `transit_money` decimal(10,2) DEFAULT '0.00' COMMENT '运费',
  `stock_num` int(11) DEFAULT '0' COMMENT '商品库存数量',
  `sale_total` int(11) DEFAULT '0' COMMENT '销量',
  `goods_sno` varchar(80) DEFAULT '' COMMENT '物料编码',
  `tag` varchar(20) DEFAULT '' COMMENT '商品标签',
  `goods_attribute` varchar(200) DEFAULT NULL COMMENT '可选规格：多个以逗号分隔',
  `goods_sell_status` tinyint(4) DEFAULT '0' COMMENT '商品状态：1 仓库中 2 审核中待上架 3 销售中 4 销售中申请下架 5 销售中申请下架已前端下架 6 已下架仓库中',
  `create_sno` varchar(80) NOT NULL DEFAULT '' COMMENT '资源方编码',
  `msg_offline` varchar(200) DEFAULT NULL COMMENT '下架原因',
  `msg_reject` varchar(200) DEFAULT NULL COMMENT '上架驳回原因',
  `create_user` bigint(11) DEFAULT '0' COMMENT '添加者主键id',
  `create_time` datetime DEFAULT NULL COMMENT '商品添加时间',
  `update_user` bigint(11) DEFAULT '0' COMMENT '修改者主键id',
  `update_time` datetime DEFAULT NULL COMMENT '商品修改时间',
  `online_time` datetime DEFAULT NULL COMMENT '上架时间',
  PRIMARY KEY (`goods_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for tb_module
-- ----------------------------
DROP TABLE IF EXISTS `tb_module`;
CREATE TABLE `tb_module` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `mod_name` varchar(50) NOT NULL DEFAULT '' COMMENT '专区名称',
  `mod_desc` varchar(100) NOT NULL DEFAULT '' COMMENT '专区描述',
  `mod_rank` int(11) NOT NULL DEFAULT '0' COMMENT '排序值(字段越大越靠前)',
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识字段(0-未删除 1-已删除)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` int(11) NOT NULL DEFAULT '0' COMMENT '创建者id',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user` int(11) DEFAULT '0' COMMENT '修改者id',
  `mod_key` varchar(50) NOT NULL DEFAULT '' COMMENT '专区编码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for tb_module_detail
-- ----------------------------
DROP TABLE IF EXISTS `tb_module_detail`;
CREATE TABLE `tb_module_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `mod_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '专区ID',
  `goods_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '产品ID',
  `img_url` varchar(100) DEFAULT NULL COMMENT '商品展示图片地址',
  `jump_url` varchar(100) NOT NULL DEFAULT '' COMMENT '调整地址',
  `is_head` int(11) DEFAULT '0' COMMENT '是否是最左边推荐位：0 否 1 是',
  `mod_rank` int(11) NOT NULL DEFAULT '0' COMMENT '排序值(字段越大越靠前)',
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识字段(0-未删除 1-已删除)',
  `desc1` varchar(100) NOT NULL DEFAULT '' COMMENT '推荐理由1',
  `desc2` varchar(100) NOT NULL DEFAULT '' COMMENT '推荐理由2',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user` int(11) NOT NULL DEFAULT '0' COMMENT '创建者id',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user` int(11) DEFAULT '0' COMMENT '修改者id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for tb_order_goodinfo
-- ----------------------------
DROP TABLE IF EXISTS `tb_order_goodinfo`;
CREATE TABLE `tb_order_goodinfo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint(20) NOT NULL COMMENT '订单id',
  `good_id` bigint(20) NOT NULL COMMENT '商品id',
  `number` int(11) DEFAULT NULL COMMENT '下单商品数量',
  `goods_attr` varchar(80) NOT NULL DEFAULT '' COMMENT '规格',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_order_orderinfo
-- ----------------------------
DROP TABLE IF EXISTS `tb_order_orderinfo`;
CREATE TABLE `tb_order_orderinfo` (
  `id` bigint(20) NOT NULL COMMENT '订单id',
  `status` int(11) DEFAULT NULL COMMENT '订单状态\r\n1-待支付    2-待发货   3-待收货   4-待评价   5-售后中    6-待结算    7-结算中     8-已结算   9-已取消',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_time` datetime DEFAULT NULL COMMENT '下单时间',
  `delivery_address` varchar(255) DEFAULT NULL COMMENT '收货地址',
  `express_company` varchar(255) DEFAULT NULL COMMENT '快递公司',
  `express_id` varchar(255) DEFAULT NULL COMMENT '快递单号',
  `user_remark` varchar(255) DEFAULT NULL COMMENT '用户备注',
  `customer_id` int(11) DEFAULT NULL COMMENT '下单用户的id',
  `supplier_id` int(11) DEFAULT NULL COMMENT '供货商的id',
  `cut_down` decimal(10,2) DEFAULT NULL COMMENT '平台减免金额',
  `total_price` decimal(10,2) DEFAULT NULL COMMENT '订单原售价',
  `gross_profit` decimal(10,2) DEFAULT NULL COMMENT '订单毛利润',
  `buying_price` decimal(10,2) DEFAULT NULL COMMENT '订单进货价',
  `real_price` decimal(10,2) DEFAULT NULL COMMENT '订单最终售价',
  `delivery_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '发货地址ID',
  `pay_type` varchar(20) NOT NULL DEFAULT '' COMMENT '支付方式',
  `delivery_fee` decimal(5,2) NOT NULL DEFAULT '0.00' COMMENT '运费',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表（主）';

-- ----------------------------
-- Table structure for tb_order_pay
-- ----------------------------
DROP TABLE IF EXISTS `tb_order_pay`;
CREATE TABLE `tb_order_pay` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `order_id` bigint(20) DEFAULT NULL COMMENT '订单id',
  `out_trade_no` varchar(50) DEFAULT NULL COMMENT '商户交易订单号',
  `trade_no` varchar(50) DEFAULT NULL COMMENT '快捷通生成的交易订单号',
  `pay_status` int(11) DEFAULT '0' COMMENT '-1支付失败 0支付中 1待付款 2待达成 3支付成功 4已退款',
  `pay_type` int(11) DEFAULT NULL COMMENT '支付方式（1：银联支付 2：协议支付 3：扫码支付）',
  `token_id` varchar(50) DEFAULT NULL COMMENT '协议号（协议支付使用）',
  `pay_token` varchar(50) DEFAULT NULL COMMENT '支付令牌（协议支付使用）',
  `pay_time` datetime DEFAULT NULL COMMENT '支付提交时间',
  `payconfirm_time` datetime DEFAULT NULL COMMENT '支付确认时间',
  `fail_msg` varchar(200) DEFAULT NULL COMMENT '支付失败原因',
  `fail_code` varchar(50) DEFAULT NULL COMMENT '支付失败编码',
  `create_time` datetime DEFAULT NULL COMMENT '发起时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `need_smsconfirm` varchar(10) DEFAULT NULL COMMENT '是否需要短信确认：M需要 S不需要',
  `is_deleted` tinyint(4) DEFAULT '0' COMMENT '删除标识字段(0-未删除 1-已删除)',
  `pay_money` decimal(10,2) DEFAULT NULL COMMENT '支付金额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_pay_refund
-- ----------------------------
DROP TABLE IF EXISTS `tb_pay_refund`;
CREATE TABLE `tb_pay_refund` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `order_id` bigint(20) DEFAULT NULL COMMENT '订单号',
  `out_trade_no` varchar(50) DEFAULT NULL COMMENT '退款商户交易号',
  `orig_out_trade_no` varchar(50) DEFAULT NULL COMMENT '原始商户交易号',
  `trade_no` varchar(50) DEFAULT NULL COMMENT '快捷通退款订单号',
  `refund_amount` decimal(10,2) DEFAULT NULL COMMENT '退款金额',
  `refund_status` varchar(10) DEFAULT NULL COMMENT '退款状态：Q请求中 S成功 P处理中 F失败',
  `fail_msg` varchar(200) DEFAULT NULL COMMENT '失败原因',
  `fail_code` varchar(20) DEFAULT NULL COMMENT '失败编码',
  `create_time` datetime DEFAULT NULL COMMENT '添加时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_pay_revlog
-- ----------------------------
DROP TABLE IF EXISTS `tb_pay_revlog`;
CREATE TABLE `tb_pay_revlog` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `out_trade_no` varchar(50) DEFAULT NULL COMMENT '商户外部订单号',
  `service_name` varchar(50) DEFAULT NULL COMMENT '服务名',
  `biz_content` varchar(2000) DEFAULT NULL COMMENT '业务请求参数',
  `rev_json` varchar(2000) DEFAULT NULL COMMENT '返回原始json',
  `status_call` int(11) DEFAULT NULL COMMENT '状态',
  `create_time` datetime DEFAULT NULL COMMENT '添加时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_pay_senlog
-- ----------------------------
DROP TABLE IF EXISTS `tb_pay_senlog`;
CREATE TABLE `tb_pay_senlog` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `out_trade_no` varchar(50) DEFAULT NULL COMMENT '商户外部订单号',
  `service_name` varchar(50) DEFAULT NULL COMMENT '服务名',
  `biz_content` varchar(1000) DEFAULT NULL COMMENT '业务请求参数',
  `status_call` int(11) DEFAULT NULL COMMENT '接口请求状态 0请求中',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_settle
-- ----------------------------
DROP TABLE IF EXISTS `tb_settle`;
CREATE TABLE `tb_settle` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '结算表id',
  `status` int(11) DEFAULT NULL COMMENT '订单状态：01-待确认；02-待平台更改；03-待开票   04-待付款   05-已完结',
  `settle_no` varchar(50) NOT NULL DEFAULT '' COMMENT '结算单号',
  `settle_name` varchar(80) NOT NULL DEFAULT '' COMMENT '结算单名称',
  `begin_period` varchar(50) NOT NULL DEFAULT '' COMMENT '结算开始时间',
  `end_period` varchar(50) NOT NULL DEFAULT '' COMMENT '结算开始时间',
  `amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '结算金额',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `supplier_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '供货商的id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='结算表';

-- ----------------------------
-- Table structure for tb_settle_detail
-- ----------------------------
DROP TABLE IF EXISTS `tb_settle_detail`;
CREATE TABLE `tb_settle_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '结算明细表id',
  `settle_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '结算Id',
  `order_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '订单id',
  `settle_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '结算单价',
  `settle_num` int(11) NOT NULL DEFAULT '0' COMMENT '结算数量',
  `amount` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '结算金额',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='结算明细表';

-- ----------------------------
-- Table structure for tb_shopping_cart
-- ----------------------------
DROP TABLE IF EXISTS `tb_shopping_cart`;
CREATE TABLE `tb_shopping_cart` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '购物主键id',
  `user_id` bigint(20) NOT NULL COMMENT '用户主键id',
  `goods_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '关联商品id',
  `goods_count` int(11) NOT NULL DEFAULT '0' COMMENT '数量',
  `goods_attr` varchar(80) NOT NULL DEFAULT '' COMMENT '规格',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户主键id',
  `nick_name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户昵称',
  `login_name` varchar(50) NOT NULL DEFAULT '' COMMENT '登陆名称(默认为手机号)',
  `password_md5` varchar(32) NOT NULL DEFAULT '' COMMENT 'MD5加密后的密码',
  `introduce_sign` varchar(100) NOT NULL DEFAULT '' COMMENT '个性签名',
  `address` varchar(100) NOT NULL DEFAULT '' COMMENT '收货地址',
  `cellphone` varchar(20) NOT NULL DEFAULT '' COMMENT '手机号',
  `user_type` varchar(2) NOT NULL DEFAULT '' COMMENT '01:个人客户;02:商户；03平台；04：供应商',
  `user_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0-待审核 1-正常 2-锁定；3-审核不通过',
  `check_msg` varchar(100) NOT NULL DEFAULT '' COMMENT '审核备注',
  `create_time` datetime DEFAULT NULL COMMENT '注册时间',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for tb_user_addr
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_addr`;
CREATE TABLE `tb_user_addr` (
  `addr_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户主键id',
  `province` varchar(50) NOT NULL DEFAULT '' COMMENT '省',
  `city` varchar(50) NOT NULL DEFAULT '' COMMENT '市',
  `area` varchar(50) NOT NULL DEFAULT '' COMMENT '区',
  `detail` varchar(100) NOT NULL DEFAULT '' COMMENT '详细地址',
  `phone` varchar(50) NOT NULL DEFAULT '' COMMENT '收货人手机号',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `acceptor` varchar(50) NOT NULL DEFAULT '' COMMENT '收货人',
  `street` varchar(50) NOT NULL DEFAULT '' COMMENT '街道',
  `is_default` tinyint(1) NOT NULL DEFAULT '0' COMMENT '默认 1；非默认 0',
  PRIMARY KEY (`addr_id`) USING BTREE,
  KEY `idx_addr_user` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for tb_user_oper
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_oper`;
CREATE TABLE `tb_user_oper` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户主键id',
  `goods_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '产品ID',
  `oper` varchar(50) NOT NULL DEFAULT '' COMMENT 'Fav; Click',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_addr_user` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for tb_user_pay
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_pay`;
CREATE TABLE `tb_user_pay` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `token_id` varchar(50) DEFAULT NULL COMMENT '协议号',
  `bank_card_no` varchar(50) DEFAULT NULL COMMENT '银行卡号',
  `bank_account_name` varchar(50) DEFAULT NULL COMMENT '银行卡账户名',
  `cvv2` varchar(50) DEFAULT NULL COMMENT '安全码，信用卡必传',
  `valid_date` varchar(50) DEFAULT NULL COMMENT '信用卡有效期 YYYY/MM',
  `phone_num` varchar(50) DEFAULT NULL COMMENT '手机号码，数字',
  `certificates_type` varchar(10) DEFAULT NULL COMMENT '证件类型参考',
  `certificates_number` varchar(50) DEFAULT NULL COMMENT '证件号码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `token_isvalid` tinyint(4) DEFAULT '0' COMMENT 'token是否已验证：0未验证 1已验证',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tb_user_profile
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_profile`;
CREATE TABLE `tb_user_profile` (
  `profile_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户主键id',
  `company_name` varchar(100) NOT NULL DEFAULT '' COMMENT '公司名',
  `tax_no` varchar(50) NOT NULL DEFAULT '' COMMENT '纳税人识别号',
  `comp_legal_no` varchar(50) NOT NULL DEFAULT '' COMMENT '营业执照号',
  `comp_legal_url` varchar(100) NOT NULL DEFAULT '' COMMENT '营业执照附件',
  `province` varchar(50) NOT NULL DEFAULT '' COMMENT '省',
  `city` varchar(50) NOT NULL DEFAULT '' COMMENT '市',
  `area` varchar(50) NOT NULL DEFAULT '' COMMENT '区',
  `detail` varchar(100) NOT NULL DEFAULT '' COMMENT '详细地址',
  `comp_addr` varchar(500) NOT NULL DEFAULT '' COMMENT '注册地址',
  `contactor` varchar(50) NOT NULL DEFAULT '' COMMENT '联系人',
  `contactor_phone` varchar(20) NOT NULL DEFAULT '' COMMENT '联系人电话',
  `contactor_email` varchar(80) NOT NULL DEFAULT '' COMMENT '联系人邮箱',
  `financer` varchar(50) NOT NULL DEFAULT '' COMMENT '财务联系人',
  `financer_phone` varchar(20) NOT NULL DEFAULT '' COMMENT '财务联系人电话',
  `tax_type` varchar(2) NOT NULL DEFAULT '' COMMENT '开票类型01-增值；02-普通',
  `bank_holder` varchar(100) NOT NULL DEFAULT '' COMMENT '银行开户全称',
  `bank_acc_no` varchar(20) NOT NULL DEFAULT '' COMMENT '银行账户',
  `bank_name` varchar(80) NOT NULL DEFAULT '' COMMENT '开户行名称',
  `bank_code` varchar(20) NOT NULL DEFAULT '' COMMENT '开户行代码',
  `promise_uri` varchar(80) NOT NULL DEFAULT '' COMMENT '供应商承诺书',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`profile_id`) USING BTREE,
  KEY `idx_profile_user` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

INSERT INTO `tb_user` VALUES ('1', 'admin', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '', '平台用户', '', '03', '1', '', '2020-04-16 11:38:35');

INSERT INTO `tb_module` VALUES ('1', '轮播大图', '轮播大图', '100', '0', '2020-04-23 14:19:49', '1', '2020-04-23 14:19:49', '1', 'Banner');
INSERT INTO `tb_module` VALUES ('2', '日用百货', '生活必需品', '0', '0', '2020-04-24 14:25:04', '0', '2020-04-24 14:25:04', '0', 'Module');
INSERT INTO `tb_module` VALUES ('3', '冰洗专区', '生活必需品', '1', '0', '2020-04-26 16:11:11', '0', '2020-04-26 16:11:11', '0', 'Module');

alter table tb_order_orderinfo add cutdown_img varchar(100) not null default '' comment '减价图片';
alter table tb_order_orderinfo add confirm_date datetime DEFAULT NULL COMMENT '确认收货时间';
alter table tb_order_orderinfo add express_code varchar(80) not null default '' comment '快递公司编码';

alter table tb_order_goodinfo add has_comment char(1) not null default '0' comment '是否评价';
alter table tb_order_goodinfo add refund_id bigint(20) not null default 0 comment '退货ID';


-- 评价表
CREATE TABLE `tb_goods_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `goods_id` bigint(20) DEFAULT NULL COMMENT '商品id',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `order_id` bigint(20) DEFAULT NULL COMMENT '订单id',
  `user_name` varchar(100) DEFAULT NULL COMMENT '用户名',
  `content` varchar(1000) DEFAULT NULL COMMENT '评价内容',
  `score` int(11) DEFAULT '5' COMMENT '评分：1 2 3 4 5',
  `goods_attribute` varchar(200) DEFAULT NULL COMMENT '规格',
  `pic_url` varchar(500) DEFAULT NULL COMMENT '图片地址,逗号分隔',
  `is_auto` int(11) DEFAULT '0' COMMENT '是否自动评价：0是 1否',
  `is_anonymous` int(11) DEFAULT '0' COMMENT '是否匿名：0是 1否',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

-- 退货表
CREATE TABLE `tb_order_refund` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `goods_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '商品id',
  `user_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '用户id',
  `supplier_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '供应商ID',
  `order_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '订单id',
   `order_goods_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '商品订单表Id',
   `goods_attribute` varchar(200) NOT NULL DEFAULT '' COMMENT '规格',
  `refund_num` int(4) not null default 0 comment '退货数量',
  `refund_reason` varchar(80) not null default '' comment '退货原因代码',
  `refund_reason_desc` varchar(80) not null default '' comment '退货原因描述',
  `refund_detail` varchar(1000) not null default '' comment '退货详细原因',
  `reject_reason` varchar(80) not null default '' comment '拒绝原因',
  `open_comment` varchar(80) not null default '' comment '备注',
  `refund_amount` decimal(10,2) NOT NULL DEFAULT '0.00' comment '退货金额',
  `refund_actual_amount` decimal(10,2) NOT NULL DEFAULT '0.00' comment '实际退货金额',
  `delivery_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '发货地址ID',
  `express_code` varchar(80) not null default '' comment '快递公司编码',
  `express_company` varchar(255) DEFAULT NULL COMMENT '快递公司',
  `express_id` varchar(255) DEFAULT NULL COMMENT '快递单号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

ALTER TABLE tb_user ADD out_tax_code VARCHAR(100) DEFAULT NULL COMMENT 'mdm返回的税号';
ALTER TABLE tb_user ADD mdm_code VARCHAR(100) DEFAULT NULL COMMENT '采购/供应商mdm编码';

