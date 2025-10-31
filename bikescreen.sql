/*
 Navicat Premium Data Transfer

 Source Server         : 本地mysql
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : bikescreen

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 22/10/2024 08:40:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bike
-- ----------------------------
DROP TABLE IF EXISTS `bike`;
CREATE TABLE `bike`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '单车id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单车名称',
  `platform` int(11) NULL DEFAULT NULL COMMENT '单车平台',
  `first_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '起步价（元）',
  `first_distance` int(11) NULL DEFAULT NULL COMMENT '起步时长(分钟）',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '计时价(元/分钟）',
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单车图片',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '介绍',
  `count` int(11) NULL DEFAULT 0 COMMENT '单车投放总数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '单车' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bike
-- ----------------------------
INSERT INTO `bike` VALUES (1, '摩拜单车', 1, 10.00, 60, 1.00, '摩拜', '2024-07-02 18:58:11', NULL, '', 613000);
INSERT INTO `bike` VALUES (2, 'ofo小黄车', 2, 11.00, 50, 1.00, 'ofo', '2024-07-02 19:08:32', NULL, '', 879000);
INSERT INTO `bike` VALUES (3, '哈啰单车', 3, 55.00, 11, 1.20, '哈啰', '2024-07-02 19:09:25', NULL, '', 260000);
INSERT INTO `bike` VALUES (4, '永安行', 4, 50.00, 11, 0.80, '永安行', '2024-07-02 19:17:03', NULL, '', 198000);
INSERT INTO `bike` VALUES (5, '青桔单车', 5, 9.00, 70, 1.20, '青桔', '2024-07-02 19:17:19', NULL, '', 357000);
INSERT INTO `bike` VALUES (6, '蜜蜂单车', 6, 10.00, 10, 1.00, '蜜蜂', '2024-07-02 19:17:34', NULL, '', 1057000);

-- ----------------------------
-- Table structure for bikeorder
-- ----------------------------
DROP TABLE IF EXISTS `bikeorder`;
CREATE TABLE `bikeorder`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `order_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '订单编号',
  `user` int(11) NULL DEFAULT NULL COMMENT '下单用户',
  `bike` int(11) NULL DEFAULT NULL COMMENT '使用单车',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '开始使用时间',
  `money` decimal(11, 2) NULL DEFAULT NULL COMMENT '订单金额',
  `status` int(11) NULL DEFAULT 0 COMMENT '订单状态，0已下单，未确认完成，1，已完成确认，未支付，2已支付',
  `finish_time` datetime NULL DEFAULT NULL COMMENT '结束单车使用时间',
  `use_time` int(11) NULL DEFAULT 0 COMMENT '使用时长',
  `pay_time` datetime NULL DEFAULT NULL COMMENT '支付时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '单车订单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bikeorder
-- ----------------------------
INSERT INTO `bikeorder` VALUES (1, 'LY1720006511622674', 2, 1, '2024-07-14 09:12:40', 26.00, 2, '2024-07-03 20:02:41', 26, '2024-07-03 20:12:44');
INSERT INTO `bikeorder` VALUES (2, 'LY1720006581249768', 2, 2, '2024-07-14 09:12:40', 26.00, 2, '2024-07-03 20:02:39', 26, '2024-07-03 20:12:42');
INSERT INTO `bikeorder` VALUES (3, 'LY1720006593350194', 2, 3, '2024-07-14 09:12:40', 25.00, 2, '2024-07-03 20:02:36', 25, '2024-07-03 20:12:40');
INSERT INTO `bikeorder` VALUES (4, 'LY1720006605558459', 2, 4, '2024-07-14 09:12:40', 25.00, 2, '2024-07-03 20:02:34', 25, '2024-07-03 20:11:12');
INSERT INTO `bikeorder` VALUES (5, 'LY1720006616001627', 2, 5, '2024-07-14 09:12:40', 25.00, 2, '2024-07-03 20:02:31', 25, '2024-07-03 20:10:02');
INSERT INTO `bikeorder` VALUES (6, 'LY1720006626307661', 2, 6, '2024-07-14 09:12:40', 24.00, 2, '2024-07-03 20:01:39', 24, '2024-07-03 20:09:58');
INSERT INTO `bikeorder` VALUES (7, 'LY1720833606565789', 2, 1, '2024-07-14 09:12:40', 90.00, 2, '2024-07-13 11:40:48', 140, '2024-07-13 11:41:13');
INSERT INTO `bikeorder` VALUES (8, 'LY1720833608891451', 2, 4, '2024-07-14 09:12:40', 64.40, 2, '2024-07-13 09:49:28', 29, '2024-07-13 09:49:32');
INSERT INTO `bikeorder` VALUES (11, 'LY1720916440340328', 2, 3, '2024-07-14 09:12:40', 55.00, 2, '2024-07-14 08:23:21', 1, '2024-07-14 08:49:50');
INSERT INTO `bikeorder` VALUES (18, 'LY1721739914315561', 2, 5, '2024-07-30 12:32:57', 90.00, 2, '2024-07-29 21:05:59', 100, '2024-08-09 14:58:02');
INSERT INTO `bikeorder` VALUES (20, 'LY1723186699682226', 2, 3, '2024-08-09 14:58:20', 55.00, 2, '2024-08-09 15:00:28', 2, '2024-08-09 15:00:30');
INSERT INTO `bikeorder` VALUES (21, 'LY1723186701270394', 2, 5, '2024-08-09 14:58:21', 9.00, 2, '2024-08-09 15:00:23', 2, '2024-08-13 14:39:20');
INSERT INTO `bikeorder` VALUES (22, 'LY1723186704785707', 2, 6, '2024-08-09 14:58:25', 10.00, 2, '2024-08-09 15:00:20', 1, '2024-08-09 15:00:26');
INSERT INTO `bikeorder` VALUES (23, 'LY1723530977208306', 2, 3, '2024-08-13 14:36:17', 55.00, 2, '2024-08-13 14:38:27', 2, '2024-08-13 14:39:18');
INSERT INTO `bikeorder` VALUES (24, 'LY1726916149400293', 2, 1, '2024-09-21 18:55:49', 10.00, 2, '2024-09-21 18:56:06', 0, '2024-09-21 18:56:08');
INSERT INTO `bikeorder` VALUES (25, 'LY1726916151786664', 2, 3, '2024-09-21 18:55:52', 55.00, 1, '2024-09-21 18:56:03', 0, NULL);
INSERT INTO `bikeorder` VALUES (26, 'LY1726916153709538', 2, 4, '2024-09-21 18:55:54', 50.00, 1, '2024-09-21 18:56:01', 0, NULL);
INSERT INTO `bikeorder` VALUES (27, 'LY1726916155435795', 2, 5, '2024-09-21 18:55:55', 9.00, 1, '2024-09-21 18:55:59', 0, NULL);

-- ----------------------------
-- Table structure for income
-- ----------------------------
DROP TABLE IF EXISTS `income`;
CREATE TABLE `income`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '收益id',
  `user` int(11) NULL DEFAULT NULL COMMENT '用户',
  `bike` int(11) NULL DEFAULT NULL COMMENT '单车',
  `bikeorder` int(11) NULL DEFAULT NULL COMMENT '订单',
  `money` decimal(11, 2) NULL DEFAULT NULL COMMENT '金额',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '收益时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '收益' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of income
-- ----------------------------
INSERT INTO `income` VALUES (1, 2, 6, 6, 24.00, '2024-07-14 09:11:11');
INSERT INTO `income` VALUES (2, 2, 5, 5, 25.00, '2024-07-14 09:11:11');
INSERT INTO `income` VALUES (3, 2, 4, 4, 25.00, '2024-07-14 09:11:11');
INSERT INTO `income` VALUES (4, 2, 3, 3, 25.00, '2024-07-14 09:11:11');
INSERT INTO `income` VALUES (5, 2, 2, 2, 26.00, '2024-07-14 09:11:11');
INSERT INTO `income` VALUES (6, 2, 1, 1, 26.00, '2024-07-14 09:11:11');
INSERT INTO `income` VALUES (7, 2, 4, 8, 64.40, '2024-07-14 09:11:11');
INSERT INTO `income` VALUES (9, 2, 1, 7, 90.00, '2024-07-14 09:11:11');
INSERT INTO `income` VALUES (11, 2, 3, 11, 55.00, '2024-07-14 09:11:11');
INSERT INTO `income` VALUES (16, 2, 5, 18, 90.00, '2024-07-30 12:29:28');
INSERT INTO `income` VALUES (17, 2, 5, 18, 90.00, '2024-08-09 14:58:02');
INSERT INTO `income` VALUES (20, 2, 6, 22, 10.00, '2024-08-09 15:00:26');
INSERT INTO `income` VALUES (21, 2, 3, 20, 55.00, '2024-08-09 15:00:30');
INSERT INTO `income` VALUES (22, 2, 3, 23, 55.00, '2024-08-13 14:39:18');
INSERT INTO `income` VALUES (23, 2, 5, 21, 9.00, '2024-08-13 14:39:20');
INSERT INTO `income` VALUES (24, 2, 1, 24, 10.00, '2024-09-21 18:56:08');

-- ----------------------------
-- Table structure for invert
-- ----------------------------
DROP TABLE IF EXISTS `invert`;
CREATE TABLE `invert`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '投放id',
  `bike` int(11) NULL DEFAULT NULL COMMENT '投放单车',
  `count` int(11) NULL DEFAULT NULL COMMENT '投放量',
  `create_time` datetime NULL DEFAULT NULL COMMENT '投放时间',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '投放地址',
  `province` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '投放省份',
  `city` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '投放城市',
  `lon` decimal(10, 7) NULL DEFAULT NULL COMMENT '经度',
  `lat` decimal(10, 7) NULL DEFAULT NULL COMMENT '纬度',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '单车投放' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of invert
-- ----------------------------
INSERT INTO `invert` VALUES (1, 2, 779000, '2012-06-01 00:00:00', '湖南省长沙市长沙县黄花镇长郡自贸临空高级中学', '湖南省', '长沙市', 113.1927810, 28.2078830);
INSERT INTO `invert` VALUES (3, 1, 98000, '2024-07-01 00:00:00', '上海市闵行区七宝镇城市花园新区优诗美地闵行区万科双语幼儿园', '上海市', '', 121.3374570, 31.1602000);
INSERT INTO `invert` VALUES (4, 2, 80000, '2024-07-01 00:00:00', '福建省福州市闽清县塔庄镇鹿兰头', '福建省', '福州市', 118.8106020, 26.0508930);
INSERT INTO `invert` VALUES (5, 4, 98000, '2024-07-01 00:00:00', '广东省广州市从化区太平镇尖峰岭', '广东省', '广州市', 113.5591370, 23.3765800);
INSERT INTO `invert` VALUES (6, 5, 79000, '2024-07-01 00:00:00', '北京市门头沟区大台街道010县道', '北京市', '', 115.8602010, 39.9308630);
INSERT INTO `invert` VALUES (7, 6, 79000, '2024-07-01 00:00:00', '陕西省咸阳市礼泉县阡东镇G3021临兴高速', '陕西省', '咸阳市', 108.6751420, 34.4930420);
INSERT INTO `invert` VALUES (8, 3, 80000, '2024-07-01 00:00:00', '重庆市巴南区南泉街道渝湘复线高速重庆迎龙峡景区', '重庆市', '', 106.6699610, 29.4505100);
INSERT INTO `invert` VALUES (9, 6, 779000, '2024-07-11 00:00:00', '新疆维吾尔自治区巴音郭楞蒙古自治州若羌县罗布泊镇', '新疆维吾尔自治区', '巴音郭楞蒙古自治州', 88.2742400, 40.0655210);
INSERT INTO `invert` VALUES (10, 1, 99000, '2024-07-16 00:00:00', '山东省潍坊市临朐县九山镇荻崖', '山东省', '潍坊市', 118.4559620, 36.2077280);
INSERT INTO `invert` VALUES (11, 1, 99000, '2024-07-23 00:00:00', '河北省邯郸市武安市矿山镇', '河北省', '邯郸市', 114.0632170, 36.8296330);
INSERT INTO `invert` VALUES (12, 3, 100000, '2024-07-23 00:00:00', '云南省昆明市晋宁区宝峰街道', '云南省', '昆明市', 102.4990630, 24.5725310);
INSERT INTO `invert` VALUES (13, 5, 99000, '2024-07-23 00:00:00', '广西壮族自治区来宾市金秀瑶族自治县大樟乡六鸦坪', '广西壮族自治区', '来宾市', 109.8599030, 23.7304700);
INSERT INTO `invert` VALUES (14, 5, 79000, '2024-07-23 00:00:00', '山东省日照市莒县碁山镇天洛线', '山东省', '日照市', 118.8686920, 35.7870080);
INSERT INTO `invert` VALUES (15, 6, 79000, '2024-07-23 00:00:00', '吉林省吉林市桦甸市横道河子乡', '吉林省', '吉林市', 126.6470130, 43.1694720);
INSERT INTO `invert` VALUES (16, 1, 99000, '2024-07-23 00:00:00', '黑龙江省绥化市绥棱县绥棱林业局', '黑龙江省', '绥化市', 127.9214270, 48.0526960);
INSERT INTO `invert` VALUES (17, 2, 10000, '2024-08-11 00:00:00', '河北省石家庄市赵县北王里镇腾发彩钢厂', '河北省', '石家庄市', 114.6278530, 37.7726990);
INSERT INTO `invert` VALUES (18, 1, 98000, '2024-08-11 00:00:00', '云南省文山壮族苗族自治州丘北县新店彝族乡凉水井', '云南省', '文山壮族苗族自治州', 103.6956740, 24.0264330);
INSERT INTO `invert` VALUES (19, 3, 80000, '2024-08-11 00:00:00', '广东省清远市佛冈县水头镇', '广东省', '清远市', 113.7591510, 23.8657820);
INSERT INTO `invert` VALUES (20, 4, 100000, '2024-08-11 00:00:00', '浙江省金华市兰溪市马涧镇水塘垄', '浙江省', '金华市', 119.6478230, 29.2864340);
INSERT INTO `invert` VALUES (21, 5, 100000, '2024-08-11 00:00:00', '湖北省襄阳市南漳县薛坪镇王家埫', '湖北省', '襄阳市', 111.6058300, 31.5598490);
INSERT INTO `invert` VALUES (22, 6, 120000, '2024-08-11 00:00:00', '甘肃省陇南市西和县苏合镇红罗寺', '甘肃省', '陇南市', 105.2337600, 34.1072900);
INSERT INTO `invert` VALUES (23, 1, 10000, '2024-10-01 00:00:00', '湖北省武汉市江夏区江夏区经济开发区大桥新区街道武汉楚能新能源有限公司', '湖北省', '武汉市', 114.2703950, 30.4021720);
INSERT INTO `invert` VALUES (24, 1, 10000, '2024-10-01 00:00:00', '湖北省武汉市江夏区江夏区经济开发区大桥新区街道何家湖街9号武汉楚能新能源有限公司', '湖北省', '武汉市', 114.2724970, 30.4022460);
INSERT INTO `invert` VALUES (25, 1, 100000, '2024-10-01 00:00:00', '湖北省武汉市江夏区江夏区经济开发区大桥新区街道武汉楚能新能源有限公司', '湖北省', '武汉市', 114.2709740, 30.4025240);
INSERT INTO `invert` VALUES (26, 2, 10000, '2024-10-02 00:00:00', '湖南省株洲市荷塘区桂花街道湖南省株洲市第四中学', '湖南省', '', 113.1769670, 27.8616350);

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` int(11) NOT NULL COMMENT '菜单ID',
  `menuCode` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单编码',
  `menuName` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名字',
  `menuLevel` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单级别',
  `menuParentCode` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单的父code',
  `menuClick` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '点击触发的函数',
  `menuRight` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限 0超级管理员，1表示管理员，2表示普通用户，可以用逗号组合使用',
  `menuComponent` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单主键',
  `menuIcon` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, '001', '系统首页', '1', NULL, 'Home', '0,1,2', 'system/SystemHome.vue', 'el-icon-s-home');
INSERT INTO `menu` VALUES (2, '002', '用户管理', '1', NULL, 'User', '0,1', 'user/UserManager.vue', 'el-icon-s-shop');
INSERT INTO `menu` VALUES (3, '003', '平台管理', '1', NULL, 'Platform', '0,1', 'bike/PlatformManager.vue', 'el-icon-office-building');
INSERT INTO `menu` VALUES (4, '004', '单车管理', '1', NULL, 'Bike', '0,1,2', 'bike/BikeManager.vue', 'el-icon-menu');
INSERT INTO `menu` VALUES (5, '005', '投放管理 ', '1', NULL, 'Invert', '0,1', 'bike/InvertManager.vue', 'el-icon-s-management');
INSERT INTO `menu` VALUES (6, '006', '回收管理', '1', NULL, 'Recovery', '0,1', 'bike/RecoveryManager.vue', 'el-icon-s-order');
INSERT INTO `menu` VALUES (7, '007', '订单管理', '1', NULL, 'Bikeorder', '0,1,2', 'bike/BikeorderManager.vue', 'el-icon-box');
INSERT INTO `menu` VALUES (8, '008', '收益管理', '1', NULL, 'Income', '0,1', 'bike/IncomeManager.vue', 'el-icon-collection');
INSERT INTO `menu` VALUES (9, '009', '通知管理', '1', NULL, 'Notice', '0,1', 'system/NoticeManager.vue', 'el-icon-pie-chart');
INSERT INTO `menu` VALUES (10, '010', '管理员管理', '1', NULL, 'Admin', '0', 'user/AdminManager.vue', 'el-icon-user-solid');
INSERT INTO `menu` VALUES (11, '011', '个人中心', '1', NULL, 'Setting', '0,1,2', 'system/SystemSetting.vue', 'el-icon-setting');

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '公告id',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公告标题',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公告内容',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '发布时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '公告通知' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES (1, '公告通知', '公告通知来了，敬请期待', '2024-07-03 12:33:03');
INSERT INTO `notice` VALUES (2, '公告调整', '公告通知要来了，大家好', '2024-07-03 14:37:39');

-- ----------------------------
-- Table structure for platform
-- ----------------------------
DROP TABLE IF EXISTS `platform`;
CREATE TABLE `platform`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '分类名',
  `remark` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `property` int(11) NULL DEFAULT NULL COMMENT '资产，单位/万亿元',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '平台' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of platform
-- ----------------------------
INSERT INTO `platform` VALUES (1, '摩拜科技', '摩拜科技有限公司。摩拜单车是一款提倡绿色出行的单车预约平台，通过手机就能在线轻松完成预约，到点直接提车。', '2024-07-04 11:36:47', 10000);
INSERT INTO `platform` VALUES (2, '东峡大通', '东峡大通（北京）管理咨询有限公司。ofo小黄车以其黄色车身和方便的操作在校园和城市中广受欢迎，为学生和市民提供便捷的出行方式。', '2024-07-04 11:36:54', 9000);
INSERT INTO `platform` VALUES (3, '上海钧正', '上海钧正网络科技有限公司。哈啰单车不仅提供单车服务，还加入了网约车功能，为用户提供更加丰富的出行选择。', '2024-07-04 11:36:57', 8000);
INSERT INTO `platform` VALUES (4, '常州永安', '常州永安公共自行车系统股份有限公司。永安行是一款绿色共享出行的服务平台，提供自行车、助力车、汽车的共享出行服务。', '2024-07-04 11:37:00', 7000);
INSERT INTO `platform` VALUES (5, '滴滴出行', '滴滴出行旗下。青桔单车致力于提供便捷、可靠、普惠、绿色的共享单车服务，提升城市整体交通安全和出行效率。', '2024-07-04 11:37:03', 6000);
INSERT INTO `platform` VALUES (6, '其他平台', '蜜蜂出行、芒果电单车、7号电单车、小蜜单车、7ma出行等，它们各自拥有不同的特色和服务，以满足不同用户的出行需求。', '2024-07-04 11:37:06', 5050);

-- ----------------------------
-- Table structure for recovery
-- ----------------------------
DROP TABLE IF EXISTS `recovery`;
CREATE TABLE `recovery`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '回收id',
  `bike` int(11) NULL DEFAULT NULL COMMENT '回收单车',
  `count` int(11) NULL DEFAULT NULL COMMENT '回收量',
  `create_time` datetime NULL DEFAULT NULL COMMENT '回收时间',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '回收地址',
  `province` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '回收省份',
  `city` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '回收城市',
  `lon` decimal(10, 7) NULL DEFAULT NULL COMMENT '经度',
  `lat` decimal(10, 7) NULL DEFAULT NULL COMMENT '纬度',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '回收' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of recovery
-- ----------------------------
INSERT INTO `recovery` VALUES (1, 2, 1000, '2024-07-02 00:00:00', '湖南省长沙市长沙县黄花镇长郡自贸临空高级中学', '湖南省', '长沙市', 113.1927810, 28.2078830);
INSERT INTO `recovery` VALUES (2, 1, 1000, '2024-07-02 00:00:00', '上海市闵行区七宝镇城市花园新区优诗美地闵行区万科双语幼儿园', '上海市', '', 121.3374570, 31.1602000);
INSERT INTO `recovery` VALUES (3, 1, 1000, '2024-07-02 00:00:00', '上海市闵行区七宝镇城市花园新区优诗美地闵行区万科双语幼儿园', '上海市', '', 121.3374570, 31.1602000);
INSERT INTO `recovery` VALUES (4, 1, 1000, '2024-07-02 00:00:00', '江西省南昌市新建区石埠镇毛上段', '江西省', '南昌市', 115.6976630, 28.5322590);
INSERT INTO `recovery` VALUES (5, 1, 1000, '2024-07-02 00:00:00', '江西省南昌市新建区石埠镇毛上段', '江西省', '南昌市', 115.6976630, 28.5322590);
INSERT INTO `recovery` VALUES (6, 1, 10000, '2024-07-17 00:00:00', '江西省南昌市新建区石埠镇毛上段', '江西省', '南昌市', 115.6976630, 28.5322590);
INSERT INTO `recovery` VALUES (7, 4, 1000, '2024-07-17 00:00:00', '广东省广州市从化区太平镇尖峰岭', '广东省', '广州市', 113.5591370, 23.3765800);
INSERT INTO `recovery` VALUES (8, 4, 1000, '2024-07-17 00:00:00', '广东省广州市从化区太平镇尖峰岭', '广东省', '广州市', 113.5591370, 23.3765800);
INSERT INTO `recovery` VALUES (9, 6, 1000, '2024-07-17 00:00:00', '陕西省咸阳市礼泉县阡东镇G3021临兴高速', '陕西省', '咸阳市', 108.6751420, 34.4930420);
INSERT INTO `recovery` VALUES (10, 6, 1000, '2024-07-17 00:00:00', '新疆维吾尔自治区巴音郭楞蒙古自治州若羌县罗布泊镇', '新疆维吾尔自治区', '巴音郭楞蒙古自治州', 88.2742400, 40.0655210);
INSERT INTO `recovery` VALUES (11, 1, 1000, '2024-07-17 00:00:00', '山东省潍坊市临朐县九山镇荻崖', '山东省', '潍坊市', 118.4559620, 36.2077280);
INSERT INTO `recovery` VALUES (12, 5, 1000, '2024-07-17 00:00:00', '北京市门头沟区大台街道010县道', '北京市', '', 115.8602010, 39.9308630);
INSERT INTO `recovery` VALUES (13, 1, 1000, '2024-07-23 00:00:00', '江西省南昌市新建区石埠镇毛上段', '江西省', '南昌市', 115.6976630, 28.5322590);
INSERT INTO `recovery` VALUES (14, 1, 1000, '2024-07-24 00:00:00', '河北省邯郸市武安市矿山镇', '河北省', '邯郸市', 114.0632170, 36.8296330);
INSERT INTO `recovery` VALUES (15, 5, 1000, '2024-07-24 00:00:00', '山东省日照市莒县碁山镇天洛线', '山东省', '日照市', 118.8686920, 35.7870080);
INSERT INTO `recovery` VALUES (16, 6, 1000, '2024-07-24 00:00:00', '吉林省吉林市桦甸市横道河子乡', '吉林省', '吉林市', 126.6470130, 43.1694720);
INSERT INTO `recovery` VALUES (17, 5, 1000, '2024-07-24 00:00:00', '广西壮族自治区来宾市金秀瑶族自治县大樟乡六鸦坪', '广西壮族自治区', '来宾市', 109.8599030, 23.7304700);
INSERT INTO `recovery` VALUES (18, 1, 1000, '2024-07-24 00:00:00', '黑龙江省绥化市绥棱县绥棱林业局', '黑龙江省', '绥化市', 127.9214270, 48.0526960);
INSERT INTO `recovery` VALUES (19, 1, 200000, '2024-08-12 00:00:00', '江西省南昌市新建区石埠镇毛上段', '江西省', '南昌市', 115.6976630, 28.5322590);
INSERT INTO `recovery` VALUES (20, 1, 787000, '2024-08-12 00:00:00', '江西省南昌市新建区石埠镇毛上段', '江西省', '南昌市', 115.6976630, 28.5322590);
INSERT INTO `recovery` VALUES (21, 1, 2000, '2024-08-12 00:00:00', '云南省文山壮族苗族自治州丘北县新店彝族乡凉水井', '云南省', '文山壮族苗族自治州', 103.6956740, 24.0264330);
INSERT INTO `recovery` VALUES (22, 2, 20000, '2024-08-12 00:00:00', '福建省福州市闽清县塔庄镇鹿兰头', '福建省', '福州市', 118.8106020, 26.0508930);
INSERT INTO `recovery` VALUES (23, 2, 20000, '2024-08-12 00:00:00', '湖南省长沙市长沙县黄花镇长郡自贸临空高级中学', '湖南省', '长沙市', 113.1927810, 28.2078830);
INSERT INTO `recovery` VALUES (24, 2, 200000, '2024-08-12 00:00:00', '湖南省长沙市长沙县黄花镇长郡自贸临空高级中学', '湖南省', '长沙市', 113.1927810, 28.2078830);
INSERT INTO `recovery` VALUES (25, 3, 20000, '2024-08-12 00:00:00', '重庆市巴南区南泉街道渝湘复线高速重庆迎龙峡景区', '重庆市', '', 106.6699610, 29.4505100);
INSERT INTO `recovery` VALUES (26, 3, 20000, '2024-08-12 00:00:00', '广东省清远市佛冈县水头镇', '广东省', '清远市', 113.7591510, 23.8657820);
INSERT INTO `recovery` VALUES (27, 4, 20000, '2024-08-12 00:00:00', '浙江省金华市兰溪市马涧镇水塘垄', '浙江省', '金华市', 119.6478230, 29.2864340);
INSERT INTO `recovery` VALUES (28, 5, 20000, '2024-08-12 00:00:00', '湖北省襄阳市南漳县薛坪镇王家埫', '湖北省', '襄阳市', 111.6058300, 31.5598490);
INSERT INTO `recovery` VALUES (29, 5, 20000, '2024-08-12 00:00:00', '山东省日照市莒县碁山镇天洛线', '山东省', '日照市', 118.8686920, 35.7870080);
INSERT INTO `recovery` VALUES (30, 5, 20000, '2024-08-12 00:00:00', '北京市门头沟区大台街道010县道', '北京市', '', 115.8602010, 39.9308630);
INSERT INTO `recovery` VALUES (31, 6, 20000, '2024-08-12 00:00:00', '吉林省吉林市桦甸市横道河子乡', '吉林省', '吉林市', 126.6470130, 43.1694720);
INSERT INTO `recovery` VALUES (32, 6, 20000, '2024-08-12 00:00:00', '新疆维吾尔自治区巴音郭楞蒙古自治州若羌县罗布泊镇', '新疆维吾尔自治区', '巴音郭楞蒙古自治州', 88.2742400, 40.0655210);
INSERT INTO `recovery` VALUES (33, 6, 20000, '2024-08-12 00:00:00', '陕西省咸阳市礼泉县阡东镇G3021临兴高速', '陕西省', '咸阳市', 108.6751420, 34.4930420);
INSERT INTO `recovery` VALUES (34, 6, 200000, '2024-08-12 00:00:00', '新疆维吾尔自治区巴音郭楞蒙古自治州若羌县罗布泊镇', '新疆维吾尔自治区', '巴音郭楞蒙古自治州', 88.2742400, 40.0655210);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名字',
  `password` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `telephone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话',
  `age` int(11) NULL DEFAULT NULL COMMENT '年龄',
  `sex` int(11) NOT NULL DEFAULT 1 COMMENT '性别',
  `role` int(11) NOT NULL DEFAULT 2 COMMENT '角色 0超级管理员，1管理员，2用户',
  `status` int(11) NOT NULL DEFAULT 1 COMMENT '是否有效，1正常，0注销',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT 'default' COMMENT '头像',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '注册时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', 'admin', '18175269584', 21, 1, 0, 1, 'avatar', '2024-08-09 14:05:13', '2024-07-18 20:37:55');
INSERT INTO `user` VALUES (2, 'user', '123456', '18175524748', 18, 1, 2, 1, 'avatar', '2024-07-14 09:09:58', NULL);
INSERT INTO `user` VALUES (3, '管理员', '123456', '18152255474', 18, 1, 1, 1, 'avatar', '2024-07-14 09:10:11', NULL);
INSERT INTO `user` VALUES (4, '李刚', '123456', '18175225547', 40, 1, 2, 1, 'avatar', '2024-08-06 12:03:23', NULL);
INSERT INTO `user` VALUES (5, '77', '123456', '18153547415', 18, 1, 2, 1, 'avatar', '2024-08-12 15:17:08', NULL);

SET FOREIGN_KEY_CHECKS = 1;
