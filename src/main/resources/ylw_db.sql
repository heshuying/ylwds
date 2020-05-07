SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- 产品类别
-- ----------------------------
DROP TABLE IF EXISTS `tb_goods_category`;
CREATE TABLE `tb_goods_category`  (
  `category_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '分类id',
  `category_level` tinyint(4) NOT NULL DEFAULT 0 COMMENT '分类级别(1-一级分类 2-二级分类 3-三级分类)',
  `parent_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '父分类id',
  `category_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '分类名称',
  `category_rank` int(11) NOT NULL DEFAULT 0 COMMENT '排序值(字段越大越靠前)',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '删除标识字段(0-未删除 1-已删除)',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` int(11) NOT NULL DEFAULT 0 COMMENT '创建者id',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user` int(11) NULL DEFAULT 0 COMMENT '修改者id',
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 107 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

INSERT INTO `tb_goods_category` VALUES (15, 1, 0, '家电 数码 手机', 100, 0, '2019-09-11 18:45:40', 0, '2019-09-11 18:45:40', 0);
INSERT INTO `tb_goods_category` VALUES (16, 1, 0, '女装 男装 穿搭', 99, 0, '2019-09-11 18:46:07', 0, '2019-09-11 18:46:07', 0);
INSERT INTO `tb_goods_category` VALUES (17, 2, 15, '家电', 10, 0, '2019-09-11 18:46:32', 0, '2019-09-11 18:46:32', 0);
INSERT INTO `tb_goods_category` VALUES (18, 2, 15, '数码', 9, 0, '2019-09-11 18:46:43', 0, '2019-09-11 18:46:43', 0);


-- ----------------------------
-- 产品表
-- ----------------------------
DROP TABLE IF EXISTS `tb_goods_info`;
CREATE TABLE `tb_goods_info`  (
  `goods_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '商品表主键id',
  `goods_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '商品名',
  `goods_intro` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '商品简介',
  `goods_category_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '关联分类id',
  `goods_cover_img` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '/admin/dist/img/no-img.png' COMMENT '商品主图',
  `goods_carousel` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '/admin/dist/img/no-img.png' COMMENT '商品轮播图',
  `goods_detail_content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品详情',
  `original_price` int(11) NOT NULL DEFAULT 1 COMMENT '商品价格',
  `selling_price` int(11) NOT NULL DEFAULT 1 COMMENT '商品实际售价',
  `profit` int(11) NOT NULL DEFAULT 0 COMMENT '平台利润',
   `price` int(11) NOT NULL DEFAULT 1 COMMENT '商品实际售价+平台利润',
  `stock_num` int(11) NOT NULL DEFAULT 0 COMMENT '商品库存数量',
  `goods_sno` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '物料编码',
  `tag` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '商品标签',
  `goods_sell_status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '商品上架状态 0-下架 1-上架',
  `create_sno` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '资源方编码',
  `create_user` int(11) NOT NULL DEFAULT 0 COMMENT '添加者主键id',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '商品添加时间',
  `update_user` int(11) NOT NULL DEFAULT 0 COMMENT '修改者主键id',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '商品修改时间',
  PRIMARY KEY (`goods_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10896 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- 专区表
-- ----------------------------
DROP TABLE IF EXISTS `tb_module`;
CREATE TABLE `tb_module`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `mod_key` varchar(50) not null default '' comment '专区编码'
  `mod_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '专区名称',
  `mod_desc` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '专区描述',
  `mod_rank` int(11) NOT NULL DEFAULT 0 COMMENT '排序值(字段越大越靠前)',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '删除标识字段(0-未删除 1-已删除)',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` int(11) NOT NULL DEFAULT 0 COMMENT '创建者id',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user` int(11) NULL DEFAULT 0 COMMENT '修改者id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


-- ----------------------------
-- 专区详情表
-- ----------------------------
DROP TABLE IF EXISTS `tb_module_detail`;
CREATE TABLE `tb_module_detail`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `mod_id` bigint(20)  NOT NULL DEFAULT 0 COMMENT '专区ID',
  `goods_id` bigint(20)  NOT NULL DEFAULT 0 COMMENT '产品ID',
  `jump_url` varchar(100)  NOT NULL DEFAULT '' COMMENT '调整地址',
  `mod_rank` int(11) NOT NULL DEFAULT 0 COMMENT '排序值(字段越大越靠前)',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '删除标识字段(0-未删除 1-已删除)',
  `desc1` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '推荐理由1',
  `desc2` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '推荐理由2',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` int(11) NOT NULL DEFAULT 0 COMMENT '创建者id',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user` int(11) NULL DEFAULT 0 COMMENT '修改者id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;



-- ----------------------------
-- 订单产品表
-- ----------------------------
DROP TABLE IF EXISTS `tb_order_goodinfo`;
CREATE TABLE `tb_order_goodinfo` (
  `order_id` bigint(20) NOT NULL COMMENT '订单id',
  `good_id` bigint(20) NOT NULL COMMENT '商品id',
  `number` int(11) DEFAULT NULL COMMENT '下单商品数量',
  PRIMARY KEY (`order_id`,`good_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- 订单表
-- ----------------------------
DROP TABLE IF EXISTS `tb_order_orderinfo`;
CREATE TABLE `tb_order_orderinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `status` int(11) DEFAULT NULL COMMENT '订单状态：01-待支付    02-待发货   03-待收货   04-待评价   05-售后中    06-待结算    07-结算中     08-已结算',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_time` datetime DEFAULT NULL COMMENT '下单时间',
  `delivery_address` varchar(255) DEFAULT NULL COMMENT '收货地址',
  `express_company` varchar(255) DEFAULT NULL COMMENT '快递公司',
  `express_id` varchar(255) DEFAULT NULL COMMENT '快递单号',
  `user_remark` varchar(255) DEFAULT NULL COMMENT '用户备注',
  `customer_id` int(11) DEFAULT NULL COMMENT '下单用户的id',
  `supplier_id` int(11) DEFAULT NULL COMMENT '供货商的id',
  `cut_down` decimal(10,2) DEFAULT NULL COMMENT '平台减免金额',
  `total_price` decimal(10,2) DEFAULT NULL COMMENT '平台的售价',
  `gross_profit` decimal(10,2) DEFAULT NULL COMMENT '订单毛利润',
  `buying_price` decimal(10,2) DEFAULT NULL COMMENT '供应商价格',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='订单表（主）';

-- ----------------------------
-- 结算表
-- ----------------------------
DROP TABLE IF EXISTS `tb_settle`;
CREATE TABLE `tb_settle` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '结算表id',
  `status` int(11) DEFAULT NULL COMMENT '订单状态：01-待确认；02-待平台更改；03-待开票   04-待付款   05-已完结',  
  `settle_no` varchar(50)  NOT NULL DEFAULT '' COMMENT '结算单号',
  `settle_name` varchar(80)  NOT NULL DEFAULT '' COMMENT '结算单名称',
  `begin_period` varchar(50)  NOT NULL DEFAULT '' COMMENT '结算开始时间',
  `end_period` varchar(50)  NOT NULL DEFAULT '' COMMENT '结算开始时间',
  `amount` decimal(10,2)  NOT NULL DEFAULT 0 COMMENT '结算金额',
  `supplier_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '供货商的id',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='结算表';

-- ----------------------------
-- 结算明细表
-- ----------------------------
DROP TABLE IF EXISTS `tb_settle_detail`;
CREATE TABLE `tb_settle_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '结算明细表id',
  `settle_id` bigint(20)  NOT NULL DEFAULT 0 COMMENT '结算Id',
  `order_id` bigint(20)  NOT NULL DEFAULT 0 COMMENT '订单id',
  `settle_price` decimal(10,2) NOT NULL DEFAULT 0 COMMENT '结算单价',
  `settle_num` int(11)  NOT NULL DEFAULT 0 COMMENT '结算数量',
  `amount` decimal(10,2)  NOT NULL DEFAULT 0 COMMENT '结算金额',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='结算明细表';

-- ----------------------------
-- 购物车
-- ----------------------------
DROP TABLE IF EXISTS `tb_shopping_cart`;
CREATE TABLE `tb_shopping_cart`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '购物主键id',
  `user_id` bigint(20) NOT NULL COMMENT '用户主键id',
  `goods_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '关联商品id',
  `goods_count` int(11) NOT NULL DEFAULT 0 COMMENT '数量',
  `goods_attr` varchar(80) not null default '' comment '规格',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- 用户表
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户主键id',
  `nick_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户昵称',
  `login_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '登陆名称(默认为手机号)',
  `password_md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT 'MD5加密后的密码',
  `introduce_sign` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '个性签名',
  `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '收货地址',
  `cellphone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '手机号',
  `user_type` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '01:个人客户;02:商户；03平台；04：供应商', 
  `user_status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '0-待审核 1-正常 2-锁定；3-审核不通过',
  `check_msg` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '审核备注',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- 用户信息
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_profile`;
CREATE TABLE `tb_user_profile`  (
  `profile_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint(20) NOT NULL default 0 COMMENT '用户主键id',
  `company_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '公司名',
  `tax_no` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '纳税人识别号',
  `comp_legal_no` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '营业执照号',
  `comp_legal_url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '营业执照附件',
  `province` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '省',
  `city` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '市',
  `area` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '区',
  `detail` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '详细地址',
  `comp_addr` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '注册地址',
  `contactor` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '联系人',
  `contactor_phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '联系人电话',
  `contactor_email` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '联系人邮箱',
  `financer` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '财务联系人',
  `financer_phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '财务联系人电话',
  `tax_type` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '开票类型01-增值；02-普通',
  `bank_holder` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '银行开户全称',
  `bank_acc_no` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '银行账户',
  `bank_name` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '开户行名称',
  `bank_code` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '开户行代码',
  `promise_uri` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '供应商承诺书',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`profile_id`) USING BTREE,
  index idx_profile_user(user_id) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


-- ----------------------------
-- 用户地址
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_addr`;
CREATE TABLE `tb_user_addr`  (
  `addr_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint(20) NOT NULL default 0 COMMENT '用户主键id',
  `province` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '省',
  `city` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '市',
  `area` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '区',
  `street` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '街道',
  `detail` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '详细地址',
  `phone` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '收货人手机号',
  `acceptor` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '收货人',
  `post_code` varchar(50)  NOT NULL DEFAULT '' COMMENT '邮编',
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '状态',
  `is_default` tinyint(1) NOT NULL DEFAULT 0 COMMENT '默认 1；非默认 0',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`addr_id`) USING BTREE,
  index idx_addr_user(user_id) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


-- ----------------------------
-- 用户收藏浏览产品
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_oper`;
CREATE TABLE `tb_user_oper`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` bigint(20) NOT NULL default 0 COMMENT '用户主键id',
  `goods_id` bigint(20) NOT NULL default 0 COMMENT '产品ID',
  `oper` varchar(50) NOT NULL DEFAULT '' COMMENT 'Fav; Click',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  index idx_addr_user(user_id) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

alter table tb_order_goodinfo add goods_attr varchar(80) not null default '' comment '规格'
alter table tb_order_orderinfo add delivery_id bigint(20) not null default 0 comment '发货地址ID'
alter table tb_order_orderinfo add pay_type varchar(20) not null default '' comment '支付方式'
alter table tb_order_orderinfo add delivery_fee decimal(5,2) NOT NULL DEFAULT 0 comment '运费'