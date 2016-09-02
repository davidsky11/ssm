/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50525
Source Host           : localhost:3306
Source Database       : hld

Target Server Type    : MYSQL
Target Server Version : 50525
File Encoding         : 65001

Date: 2016-09-03 00:37:29
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for activity
-- ----------------------------
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity` (
  `id` varchar(50) NOT NULL,
  `title` varchar(50) DEFAULT NULL,
  `description` varchar(50) DEFAULT NULL,
  `startTime` timestamp NULL DEFAULT NULL COMMENT '开始时间',
  `endTime` timestamp NULL DEFAULT NULL COMMENT '结束时间',
  `content` varchar(255) DEFAULT NULL COMMENT '活动内容',
  `publisherId` int(10) DEFAULT NULL COMMENT '发布者(后端用户）',
  `publisherName` varchar(50) DEFAULT NULL,
  `enable` smallint(1) DEFAULT NULL COMMENT '是否激活',
  `publicCode` varchar(100) DEFAULT NULL,
  `image` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of activity
-- ----------------------------
INSERT INTO `activity` VALUES ('74e4fd48528d11e6a1933417eb90ce17', 'test', 'sfs', '2016-07-26 01:29:05', '2016-07-28 01:29:08', 'sgsgs', '2', 'kevin', null, null, null);
INSERT INTO `activity` VALUES ('sfsfsfs', '测试', 'testdd', '2016-07-19 12:44:12', '2016-07-28 12:44:08', 'test', '1', 'admin', '1', '123321', null);

-- ----------------------------
-- Table structure for attachment
-- ----------------------------
DROP TABLE IF EXISTS `attachment`;
CREATE TABLE `attachment` (
  `id` varchar(45) NOT NULL,
  `user_id` varchar(45) NOT NULL,
  `attach_name` varchar(150) NOT NULL,
  `file_type` varchar(45) NOT NULL,
  `file_length` bigint(20) NOT NULL,
  `file_path` varchar(255) NOT NULL,
  `file_url` varchar(255) NOT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `owner_type` varchar(45) DEFAULT NULL,
  `owner_id` varchar(45) DEFAULT NULL,
  `create_data_time` timestamp NULL DEFAULT NULL,
  `create_data_username` varchar(45) DEFAULT NULL,
  `update_data_time` timestamp NULL DEFAULT NULL,
  `update_data_username` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of attachment
-- ----------------------------

-- ----------------------------
-- Table structure for award
-- ----------------------------
DROP TABLE IF EXISTS `award`;
CREATE TABLE `award` (
  `id` varchar(50) DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL COMMENT '奖项名称',
  `serialNo` varchar(50) DEFAULT NULL COMMENT '奖项编码',
  `description` varchar(255) DEFAULT NULL COMMENT '奖项描述',
  `hierarchy` int(10) DEFAULT NULL COMMENT '层级',
  `sort` int(10) DEFAULT NULL COMMENT '排序',
  `amount` double(8,2) DEFAULT NULL COMMENT '奖项金额',
  `activityId` varchar(50) DEFAULT NULL,
  `total` int(3) DEFAULT NULL COMMENT '总共数量',
  `remain` int(3) DEFAULT NULL COMMENT '剩余数量'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of award
-- ----------------------------

-- ----------------------------
-- Table structure for cache
-- ----------------------------
DROP TABLE IF EXISTS `cache`;
CREATE TABLE `cache` (
  `id` varchar(50) NOT NULL,
  `account` varchar(60) DEFAULT NULL,
  `sessionId` varchar(100) DEFAULT NULL,
  `lastTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cache
-- ----------------------------

-- ----------------------------
-- Table structure for contact
-- ----------------------------
DROP TABLE IF EXISTS `contact`;
CREATE TABLE `contact` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customerid` int(11) NOT NULL COMMENT '客户id',
  `name` varchar(20) NOT NULL COMMENT '姓名',
  `position` varchar(20) DEFAULT NULL COMMENT '职务',
  `tel` varchar(20) DEFAULT NULL COMMENT '固话',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机',
  `qq` varchar(20) DEFAULT NULL,
  `weixin` varchar(20) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`,`customerid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of contact
-- ----------------------------
INSERT INTO `contact` VALUES ('1', '1', '张三', '经理', '110110110110', '11111111111', '905891467', '9058weixin', 'walk_hai@163.com');
INSERT INTO `contact` VALUES ('2', '1', '李四', '主任', '2232323', '454345324534', '909090909', 'sssweinxin', 'lisi@163.com');
INSERT INTO `contact` VALUES ('3', '2', '刘德华', '总经理', '121321', '78787878', '888888', 'aksdfsadf', 'sdfsdf@163.com');
INSERT INTO `contact` VALUES ('4', '2', '李胜基', '助理', '1132413', '123423423', '222222', 'asdweixin', 'asdfsdf@163.com');
INSERT INTO `contact` VALUES ('5', '2', '戴安琪', '秘书', '132132', '787878', '8787878', 'ssssweinxi', 'asdfadf@163.com');

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL COMMENT '名称',
  `address` varchar(200) NOT NULL COMMENT '地址',
  `zipcode` varchar(20) DEFAULT NULL COMMENT '邮编',
  `fax` varchar(20) DEFAULT NULL COMMENT '传真',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer` VALUES ('1', '单位1', '地址1', '1000000', '12345678');
INSERT INTO `customer` VALUES ('2', '单位2', '地址2', '1000001', '22222222');
INSERT INTO `customer` VALUES ('3', '单位3', '地址3', '1000002', '33333333');
INSERT INTO `customer` VALUES ('4', '单位4', '地址4', '1000003', '44444444');
INSERT INTO `customer` VALUES ('5', '单位55555', '地址5', '1000004', '55555555');
INSERT INTO `customer` VALUES ('6', '啊啊啊啊', '八佰伴八佰伴', '1313', '2352435345');
INSERT INTO `customer` VALUES ('7', '保存处处长2', '啊都是法师打发斯蒂芬2', '1111000', '222222000');
INSERT INTO `customer` VALUES ('8', '1', '2', '33', '3');
INSERT INTO `customer` VALUES ('9', '呀呀呀呀呀呀', '谁谁谁', '1212', '1312');

-- ----------------------------
-- Table structure for exchange
-- ----------------------------
DROP TABLE IF EXISTS `exchange`;
CREATE TABLE `exchange` (
  `id` varchar(50) NOT NULL,
  `userId` varchar(50) DEFAULT NULL COMMENT '前端用户编号',
  `exchangeTime` datetime DEFAULT NULL,
  `waresId` varchar(100) DEFAULT NULL COMMENT '公共编码',
  `longitude` decimal(8,2) DEFAULT NULL COMMENT '经度',
  `latitude` decimal(8,2) DEFAULT NULL COMMENT '纬度',
  `flagCode` varchar(100) DEFAULT NULL COMMENT '客户端硬件标识码',
  `publicCode` varchar(100) DEFAULT NULL,
  `privateCode` varchar(100) DEFAULT NULL,
  `insideCode` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='兑奖';

-- ----------------------------
-- Records of exchange
-- ----------------------------

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL,
  `menu_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `role_id` (`role_id`),
  KEY `menu_id` (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8 COMMENT='权限关联表';

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES ('1', '1', '2');
INSERT INTO `role_menu` VALUES ('2', '1', '3');
INSERT INTO `role_menu` VALUES ('3', '1', '201');
INSERT INTO `role_menu` VALUES ('4', '1', '202');
INSERT INTO `role_menu` VALUES ('5', '1', '301');
INSERT INTO `role_menu` VALUES ('9', '1', '101');
INSERT INTO `role_menu` VALUES ('11', '1', '1');
INSERT INTO `role_menu` VALUES ('13', '1', '4');
INSERT INTO `role_menu` VALUES ('14', '1', '5');
INSERT INTO `role_menu` VALUES ('15', '1', '401');
INSERT INTO `role_menu` VALUES ('16', '1', '402');
INSERT INTO `role_menu` VALUES ('17', '1', '403');
INSERT INTO `role_menu` VALUES ('18', '1', '501');
INSERT INTO `role_menu` VALUES ('19', '1', '6');
INSERT INTO `role_menu` VALUES ('20', '1', '7');
INSERT INTO `role_menu` VALUES ('21', '1', '8');
INSERT INTO `role_menu` VALUES ('22', '1', '9');
INSERT INTO `role_menu` VALUES ('23', '1', '601');
INSERT INTO `role_menu` VALUES ('24', '1', '701');
INSERT INTO `role_menu` VALUES ('25', '1', '801');
INSERT INTO `role_menu` VALUES ('26', '1', '901');
INSERT INTO `role_menu` VALUES ('27', '1', '302');
INSERT INTO `role_menu` VALUES ('28', '1', '102');
INSERT INTO `role_menu` VALUES ('29', '1', '404');
INSERT INTO `role_menu` VALUES ('30', '1', '303');
INSERT INTO `role_menu` VALUES ('39', '1', '10');
INSERT INTO `role_menu` VALUES ('40', '1', '904');
INSERT INTO `role_menu` VALUES ('55', '2', '1001');
INSERT INTO `role_menu` VALUES ('57', '1', '1002');
INSERT INTO `role_menu` VALUES ('58', '2', '1003');
INSERT INTO `role_menu` VALUES ('59', '1', '1003');
INSERT INTO `role_menu` VALUES ('60', '2', '10');
INSERT INTO `role_menu` VALUES ('63', '0', '1');
INSERT INTO `role_menu` VALUES ('64', '0', '2');
INSERT INTO `role_menu` VALUES ('65', '0', '3');
INSERT INTO `role_menu` VALUES ('66', '0', '4');
INSERT INTO `role_menu` VALUES ('67', '0', '5');
INSERT INTO `role_menu` VALUES ('68', '0', '6');
INSERT INTO `role_menu` VALUES ('69', '0', '7');
INSERT INTO `role_menu` VALUES ('70', '0', '8');
INSERT INTO `role_menu` VALUES ('71', '0', '9');
INSERT INTO `role_menu` VALUES ('72', '0', '10');
INSERT INTO `role_menu` VALUES ('73', '0', '101');
INSERT INTO `role_menu` VALUES ('74', '0', '102');
INSERT INTO `role_menu` VALUES ('75', '0', '201');
INSERT INTO `role_menu` VALUES ('76', '0', '301');
INSERT INTO `role_menu` VALUES ('77', '0', '302');
INSERT INTO `role_menu` VALUES ('78', '0', '303');
INSERT INTO `role_menu` VALUES ('79', '0', '304');
INSERT INTO `role_menu` VALUES ('80', '0', '401');
INSERT INTO `role_menu` VALUES ('81', '0', '402');
INSERT INTO `role_menu` VALUES ('82', '0', '403');
INSERT INTO `role_menu` VALUES ('83', '0', '404');
INSERT INTO `role_menu` VALUES ('84', '0', '501');
INSERT INTO `role_menu` VALUES ('85', '0', '601');
INSERT INTO `role_menu` VALUES ('86', '0', '701');
INSERT INTO `role_menu` VALUES ('87', '0', '801');
INSERT INTO `role_menu` VALUES ('88', '0', '901');
INSERT INTO `role_menu` VALUES ('89', '0', '1001');
INSERT INTO `role_menu` VALUES ('90', '0', '1002');
INSERT INTO `role_menu` VALUES ('91', '0', '1003');
INSERT INTO `role_menu` VALUES ('92', '3', '1003');
INSERT INTO `role_menu` VALUES ('93', '3', '12');
INSERT INTO `role_menu` VALUES ('94', '3', '1201');
INSERT INTO `role_menu` VALUES ('95', '3', '1202');
INSERT INTO `role_menu` VALUES ('96', '2', '11');
INSERT INTO `role_menu` VALUES ('97', '2', '1101');
INSERT INTO `role_menu` VALUES ('98', '2', '1102');
INSERT INTO `role_menu` VALUES ('99', '2', '1103');
INSERT INTO `role_menu` VALUES ('100', '3', '10');

-- ----------------------------
-- Table structure for sale
-- ----------------------------
DROP TABLE IF EXISTS `sale`;
CREATE TABLE `sale` (
  `id` int(10) NOT NULL,
  `year` int(10) DEFAULT NULL,
  `month` int(10) DEFAULT NULL,
  `amount` double(20,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sale
-- ----------------------------
INSERT INTO `sale` VALUES ('1', '2015', '8', '132456.00');
INSERT INTO `sale` VALUES ('2', '2015', '9', '138356.00');
INSERT INTO `sale` VALUES ('3', '2015', '10', '6489978.00');
INSERT INTO `sale` VALUES ('4', '2015', '11', '649841655.00');
INSERT INTO `sale` VALUES ('5', '2015', '12', '68236498.00');
INSERT INTO `sale` VALUES ('6', '2016', '1', '4646468.00');
INSERT INTO `sale` VALUES ('7', '2016', '2', '8666.00');
INSERT INTO `sale` VALUES ('8', '2016', '3', '56466889.00');
INSERT INTO `sale` VALUES ('9', '2016', '4', '648.00');
INSERT INTO `sale` VALUES ('10', '2016', '5', '498556.00');
INSERT INTO `sale` VALUES ('11', '2016', '6', '246.00');
INSERT INTO `sale` VALUES ('12', '2016', '7', '456565.00');
INSERT INTO `sale` VALUES ('13', '2016', '8', '1658898.00');

-- ----------------------------
-- Table structure for scan_record
-- ----------------------------
DROP TABLE IF EXISTS `scan_record`;
CREATE TABLE `scan_record` (
  `id` varchar(50) NOT NULL,
  `userId` varchar(50) DEFAULT NULL,
  `userName` varchar(50) DEFAULT NULL,
  `longitude` double(15,2) DEFAULT NULL,
  `latitude` double(15,2) DEFAULT NULL,
  `scanTime` datetime DEFAULT NULL,
  `country` varchar(30) DEFAULT NULL,
  `province` varchar(30) DEFAULT NULL,
  `city` varchar(30) DEFAULT NULL,
  `distance` varchar(30) DEFAULT NULL,
  `street` varchar(30) DEFAULT NULL,
  `sematicDescription` varchar(100) DEFAULT NULL,
  `formattedAddress` varchar(100) DEFAULT NULL,
  `userType` varchar(50) DEFAULT NULL,
  `waresId` varchar(50) DEFAULT NULL,
  `publicCode` varchar(100) DEFAULT NULL,
  `privateCode` varchar(100) DEFAULT NULL,
  `insideCode` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of scan_record
-- ----------------------------
INSERT INTO `scan_record` VALUES ('1', '2', 'kevin', '114.30', '30.60', '2016-09-01 14:51:30', '中国', '湖北省', '武汉市', '黄陂区', '博士街', null, null, '2', '61d0947170de11e6b1fc3417eb90ce17', '123', '789', null);
INSERT INTO `scan_record` VALUES ('2', '28', 'app', '114.50', '30.80', '2016-09-02 14:53:27', '中国', '湖北省', '武汉市', '洪山区', '关山街', null, null, '3', '61d0947170de11e6b1fc3417eb90ce17', '123', '789', null);
INSERT INTO `scan_record` VALUES ('3', '2', 'kevin', '114.00', '30.00', '2016-09-02 17:03:12', null, '湖北省', null, null, null, null, null, '2', '61d0947170de11e6b1fc3417eb90ce17', '123', null, null);
INSERT INTO `scan_record` VALUES ('4', '2', 'kevin', null, null, '2016-09-02 17:22:51', null, '广东省', null, null, null, null, null, '2', '61d0947170de11e6b1fc3417eb90ce17', '123', null, null);

-- ----------------------------
-- Table structure for sysmenu
-- ----------------------------
DROP TABLE IF EXISTS `sysmenu`;
CREATE TABLE `sysmenu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '菜单名称',
  `parentid` int(11) NOT NULL DEFAULT '0' COMMENT '父级菜单ID 0表示根节点',
  `sequence` int(6) NOT NULL DEFAULT '0' COMMENT '菜单顺序',
  `iconCls` varchar(20) DEFAULT '' COMMENT '菜单图标样式',
  `url` varchar(255) NOT NULL DEFAULT '' COMMENT '菜单链接地址 总是以‘/’开头，相对于项目根路径',
  `enable` int(1) NOT NULL DEFAULT '1' COMMENT '是否可用 1:正常，0：禁用',
  PRIMARY KEY (`id`),
  KEY `parentId` (`parentid`),
  KEY `sequence` (`sequence`)
) ENGINE=InnoDB AUTO_INCREMENT=1203 DEFAULT CHARSET=utf8 COMMENT='后台菜单表';

-- ----------------------------
-- Records of sysmenu
-- ----------------------------
INSERT INTO `sysmenu` VALUES ('1', '菜单管理', '0', '1', 'icon-bottom', '', '1');
INSERT INTO `sysmenu` VALUES ('2', '客户管理', '0', '2', 'icon-bottom', '', '1');
INSERT INTO `sysmenu` VALUES ('3', '用户管理', '0', '3', 'icon-bottom', '', '1');
INSERT INTO `sysmenu` VALUES ('4', '系统设置', '0', '4', 'icon-bottom', '', '1');
INSERT INTO `sysmenu` VALUES ('5', '报表统计', '0', '5', 'icon-bottom', '', '1');
INSERT INTO `sysmenu` VALUES ('6', '合同管理', '0', '6', 'icon-bottom', '', '1');
INSERT INTO `sysmenu` VALUES ('7', '资源管理', '0', '7', 'icon-bottom', '', '1');
INSERT INTO `sysmenu` VALUES ('8', '考评管理', '0', '8', 'icon-bottom', '', '1');
INSERT INTO `sysmenu` VALUES ('9', '差旅管理', '0', '9', 'icon-bottom', '', '1');
INSERT INTO `sysmenu` VALUES ('10', '活动管理', '0', '10', 'icon-set', '', '1');
INSERT INTO `sysmenu` VALUES ('11', '商户目录', '0', '11', 'icon-set', '', '1');
INSERT INTO `sysmenu` VALUES ('12', '用户菜单', '12', '12', 'icon-set', '', '1');
INSERT INTO `sysmenu` VALUES ('101', '资源管理', '1', '101', 'icon-nav', '/menu/list', '1');
INSERT INTO `sysmenu` VALUES ('102', '菜单树', '1', '102', 'icon-set', '/menu/listtree', '1');
INSERT INTO `sysmenu` VALUES ('201', '客户管理', '2', '201', 'icon-role', '/customer/list', '1');
INSERT INTO `sysmenu` VALUES ('301', '后台用户管理', '3', '301', 'icon-log', '/user/list', '1');
INSERT INTO `sysmenu` VALUES ('302', '员工管理', '3', '302', 'icon-person', '/user/listtree', '1');
INSERT INTO `sysmenu` VALUES ('303', '前端用户管理', '3', '303', 'icon-person', '/account/list', '1');
INSERT INTO `sysmenu` VALUES ('304', '系统用户管理', '3', '305', 'icon-person', '', '1');
INSERT INTO `sysmenu` VALUES ('401', '权限管理', '4', '401', 'icon-msg', '/menu/list', '1');
INSERT INTO `sysmenu` VALUES ('402', '角色管理', '4', '402', 'icon-set', '/role/list', '1');
INSERT INTO `sysmenu` VALUES ('403', '系统通知', '4', '403', 'icon-help', '/menu/list', '1');
INSERT INTO `sysmenu` VALUES ('404', '部门管理', '4', '404', 'icon-role', '/systemdef/listtree', '1');
INSERT INTO `sysmenu` VALUES ('501', '系统报表', '5', '501', 'icon-email', '/menu/list', '1');
INSERT INTO `sysmenu` VALUES ('601', '合同查询', '6', '601', 'icon-ht', '/menu/list', '1');
INSERT INTO `sysmenu` VALUES ('701', '资源查询', '7', '701', 'icon-tongji', '/menu/list', '1');
INSERT INTO `sysmenu` VALUES ('801', '绩效查询', '8', '801', 'icon-bb', '/menu/list', '1');
INSERT INTO `sysmenu` VALUES ('901', '差旅查询', '9', '901', 'icon-money', '/menu/list', '1');
INSERT INTO `sysmenu` VALUES ('1001', '活动维护', '10', '904', 'icon-set', '/activity/list', '1');
INSERT INTO `sysmenu` VALUES ('1002', '奖项设置', '10', '905', 'icon-set', '/award/list', '1');
INSERT INTO `sysmenu` VALUES ('1003', '商品信息', '10', '906', 'icon-bb', '/wares/list', '1');
INSERT INTO `sysmenu` VALUES ('1101', '扫码记录', '11', '1101', 'icon-tongji', '', '1');
INSERT INTO `sysmenu` VALUES ('1102', '销售统计', '11', '1102', 'icon-tongji', '', '1');
INSERT INTO `sysmenu` VALUES ('1103', '兑奖统计', '11', '1103', 'icon-tongji', '', '1');
INSERT INTO `sysmenu` VALUES ('1201', '扫码记录', '12', '1202', 'icon-bb', '/scanRecord/list', '1');
INSERT INTO `sysmenu` VALUES ('1202', '兑奖记录', '12', '1201', 'icon-bb', '/exchange/list', '1');

-- ----------------------------
-- Table structure for sysrole
-- ----------------------------
DROP TABLE IF EXISTS `sysrole`;
CREATE TABLE `sysrole` (
  `id` varchar(11) NOT NULL,
  `name` varchar(60) NOT NULL DEFAULT '' COMMENT '角色名称',
  `roleDesc` varchar(100) DEFAULT NULL COMMENT '角色描述',
  `enabled` int(2) DEFAULT NULL COMMENT '是否可用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台角色表';

-- ----------------------------
-- Records of sysrole
-- ----------------------------
INSERT INTO `sysrole` VALUES ('0', '超级用户', '超级用户', '1');
INSERT INTO `sysrole` VALUES ('1', '系统账户', '登录系统进行一些配置', '1');
INSERT INTO `sysrole` VALUES ('2', '商户账户', '经销商账户', '1');
INSERT INTO `sysrole` VALUES ('3', '移动端账户', 'APP端账户', '1');

-- ----------------------------
-- Table structure for systemdef
-- ----------------------------
DROP TABLE IF EXISTS `systemdef`;
CREATE TABLE `systemdef` (
  `id` int(11) NOT NULL COMMENT '部门编号',
  `name` char(20) NOT NULL COMMENT '部门名称',
  `parentid` int(11) NOT NULL DEFAULT '0' COMMENT '所属二级部门【父类（上级部门）】',
  `iconCls` varchar(20) DEFAULT NULL COMMENT '样式',
  `ptopid` int(11) NOT NULL COMMENT '所属一级部门【顶级父类】',
  `pthird` int(11) DEFAULT NULL COMMENT '所属三级部门【扩展备用】',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of systemdef
-- ----------------------------
INSERT INTO `systemdef` VALUES ('1', '上海总部', '0', null, '0', null);
INSERT INTO `systemdef` VALUES ('2', '北京分公司', '0', null, '0', null);
INSERT INTO `systemdef` VALUES ('3', '成都分公司', '0', null, '0', null);
INSERT INTO `systemdef` VALUES ('4', '广州分公司', '0', null, '0', null);
INSERT INTO `systemdef` VALUES ('101', '总裁办', '1', null, '1', null);
INSERT INTO `systemdef` VALUES ('102', '行政部', '1', null, '1', null);
INSERT INTO `systemdef` VALUES ('103', '销售部', '1', null, '1', null);
INSERT INTO `systemdef` VALUES ('104', '产品部', '1', null, '1', null);
INSERT INTO `systemdef` VALUES ('105', '研发部', '1', null, '1', null);
INSERT INTO `systemdef` VALUES ('106', '运维部', '2', null, '2', null);
INSERT INTO `systemdef` VALUES ('201', '行政部', '2', null, '2', null);
INSERT INTO `systemdef` VALUES ('202', '销售部', '2', null, '2', null);
INSERT INTO `systemdef` VALUES ('203', '运维部', '2', null, '2', null);
INSERT INTO `systemdef` VALUES ('301', '行政部', '3', '', '3', null);
INSERT INTO `systemdef` VALUES ('302', '销售部', '3', null, '3', null);
INSERT INTO `systemdef` VALUES ('303', '运维部', '3', null, '3', null);
INSERT INTO `systemdef` VALUES ('401', '行政部', '4', null, '4', null);
INSERT INTO `systemdef` VALUES ('402', '销售部', '4', null, '4', null);
INSERT INTO `systemdef` VALUES ('403', '运维部', '4', null, '4', null);
INSERT INTO `systemdef` VALUES ('10201', '管理部', '102', null, '1', null);
INSERT INTO `systemdef` VALUES ('10202', '后勤部', '102', null, '1', null);
INSERT INTO `systemdef` VALUES ('10301', '销售1部', '103', null, '1', null);
INSERT INTO `systemdef` VALUES ('10302', '销售2部', '103', null, '1', null);
INSERT INTO `systemdef` VALUES ('10303', '销售3部', '103', null, '1', null);
INSERT INTO `systemdef` VALUES ('10501', '研发1部', '105', null, '1', null);
INSERT INTO `systemdef` VALUES ('10502', '研发2部', '105', null, '1', null);
INSERT INTO `systemdef` VALUES ('30201', '销售1部', '302', null, '3', null);
INSERT INTO `systemdef` VALUES ('30202', '销售2部', '302', null, '3', null);

-- ----------------------------
-- Table structure for sysuser
-- ----------------------------
DROP TABLE IF EXISTS `sysuser`;
CREATE TABLE `sysuser` (
  `id` varchar(50) NOT NULL,
  `username` varchar(100) NOT NULL DEFAULT '' COMMENT '登录名（匿名）',
  `password` varchar(100) NOT NULL DEFAULT '' COMMENT '登录密码',
  `sysid` int(11) DEFAULT '1' COMMENT '所属部门',
  `email` varchar(50) DEFAULT NULL COMMENT '邮件',
  `name` varchar(20) DEFAULT NULL COMMENT '真实姓名',
  `idcard` varchar(20) DEFAULT NULL COMMENT '身份证',
  `gender` varchar(2) DEFAULT '男' COMMENT '性别1男2女',
  `qq` varchar(20) DEFAULT NULL COMMENT 'qq',
  `weixin` varchar(50) DEFAULT NULL,
  `regtime` date DEFAULT NULL COMMENT '入职日期',
  `userType` varchar(50) DEFAULT NULL,
  `generateName` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息表';

-- ----------------------------
-- Records of sysuser
-- ----------------------------
INSERT INTO `sysuser` VALUES ('094f8b5769cc11e684473417eb90ce17', '13007102580', 'wwwwww', '1', null, null, null, '男', null, null, null, '3', null);
INSERT INTO `sysuser` VALUES ('0dfc21bc69c511e684473417eb90ce17', '13007103929', 'qwerty', '1', null, null, null, '男', null, null, null, '3', null);
INSERT INTO `sysuser` VALUES ('1', 'admin', '123456', '101', null, null, '1', '男', '905891460', null, '2015-05-01', '0', 'admin_0');
INSERT INTO `sysuser` VALUES ('18', 'aaa', 'aaa', '10201', 'adf@sdfa.com', null, '23413245', '女', '89898989', 'adfadad', '2015-05-06', null, null);
INSERT INTO `sysuser` VALUES ('19434f6d69c511e684473417eb90ce17', 'kevin', '2323323232', '1', null, null, null, '男', null, null, null, '3', null);
INSERT INTO `sysuser` VALUES ('1a72d8fc69c711e684473417eb90ce17', 'kevin11', '123456', '1', null, null, null, '男', null, null, null, '2', null);
INSERT INTO `sysuser` VALUES ('2', 'kevin', '123456', '101', null, null, '2', '男', '123456784', null, '2015-05-02', '2', 'kevin_2');
INSERT INTO `sysuser` VALUES ('22', 'aaaa', 'aaaa', '30201', 'adsfadsf@asdf.com', null, '123123123123', '男', '132423', 'adsfasdfadffsdfadfadsf', '2015-04-05', null, null);
INSERT INTO `sysuser` VALUES ('28', 'app', '123456', '101', 'app@qq.com', null, '11', '男', null, null, '2016-08-05', '3', 'app_3');
INSERT INTO `sysuser` VALUES ('2920f19f69c611e684473417eb90ce17', '15825896633', 'qwerty', '1', null, null, null, '男', null, null, null, '3', null);
INSERT INTO `sysuser` VALUES ('2c38fb6a5ba811e6bd453417eb90ce17', 'kkkfff', '123456', '1', null, null, null, '男', null, null, null, '1', null);
INSERT INTO `sysuser` VALUES ('2c3f918469d011e684473417eb90ce17', 'dm', '0617', '1', null, null, null, '男', null, null, null, '3', null);
INSERT INTO `sysuser` VALUES ('3', 'test', '123456', '101', null, null, '3', '女', null, null, '0000-00-00', '3', 'test_3');
INSERT INTO `sysuser` VALUES ('316286a75ba511e6bd453417eb90ce17', 'kkk', '123456', '1', null, null, null, '男', null, null, null, '2', null);
INSERT INTO `sysuser` VALUES ('3b44973a5ddf11e6b2043417eb90ce17', 'gaoxi', '324', '1', null, null, null, '男', null, null, null, '2', null);
INSERT INTO `sysuser` VALUES ('3d2507ae69c611e684473417eb90ce17', 'kiiii', '234234', '1', null, null, null, '男', null, null, null, '2', null);
INSERT INTO `sysuser` VALUES ('572841f869d111e684473417eb90ce17', 'gggggg', '123456', '1', null, null, null, '男', null, null, null, '2', null);
INSERT INTO `sysuser` VALUES ('58392de56f5911e69b343417eb90ce17', '13247199568', 'qwerty', '1', null, null, null, '男', null, null, null, '3', null);
INSERT INTO `sysuser` VALUES ('5a038f5969c611e684473417eb90ce17', 'kiiii', '234234', '1', null, null, null, '男', null, null, null, '3', null);
INSERT INTO `sysuser` VALUES ('6148766a5dd111e6b2043417eb90ce17', 'gaoxile', '123456', '1', null, null, null, '男', null, null, null, '2', null);
INSERT INTO `sysuser` VALUES ('6d9516bb69c811e684473417eb90ce17', '13585261398', 'ertyui', '1', null, null, null, '男', null, null, null, '3', null);
INSERT INTO `sysuser` VALUES ('6e3199006f5111e69b343417eb90ce17', '15071493575', '123456', '1', null, null, null, '男', null, null, null, '3', null);
INSERT INTO `sysuser` VALUES ('6f68fe365ba811e6bd453417eb90ce17', 'daimin', '323232', '1', null, null, null, '男', null, null, null, '3', null);
INSERT INTO `sysuser` VALUES ('74ce0bd45de311e6b2043417eb90ce17', 'gao', '123456', '1', null, null, null, '男', null, null, null, '2', null);
INSERT INTO `sysuser` VALUES ('77c353de69d211e684473417eb90ce17', 'aaa', '123456', '1', null, null, null, '男', null, null, null, '2', null);
INSERT INTO `sysuser` VALUES ('858b5a8669d211e684473417eb90ce17', 'plmn', '123456879', '1', null, null, null, '男', null, null, null, '2', null);
INSERT INTO `sysuser` VALUES ('8805f89a69c511e684473417eb90ce17', 'kitty', '123456', '1', null, null, null, '男', null, null, null, '2', null);
INSERT INTO `sysuser` VALUES ('9', 'root', '123456', '104', 'walk_hai@163.com', null, '9', '女', null, null, '0000-00-00', '1', 'root_1');
INSERT INTO `sysuser` VALUES ('9054b94f6a8f11e6acf23417eb90ce17', 'jjPPTVls', 'bjdiidjd', '1', null, null, null, '男', null, null, null, '2', null);
INSERT INTO `sysuser` VALUES ('959eb58669c611e684473417eb90ce17', '457746646', '2323323232', '1', null, null, null, '男', null, null, null, '3', null);
INSERT INTO `sysuser` VALUES ('97651d8169d611e684473417eb90ce17', 'mbcdtik', 'gfdvjk', '1', null, null, null, '男', null, null, null, '2', null);
INSERT INTO `sysuser` VALUES ('b8ae4e4f5ba711e6bd453417eb90ce17', 'daimin', '12123131', '1', null, null, null, '男', null, null, null, '1', null);
INSERT INTO `sysuser` VALUES ('c443dd9a69d211e684473417eb90ce17', 'fjkfc', 'fjnvfd', '1', null, null, null, '男', null, null, null, '2', null);
INSERT INTO `sysuser` VALUES ('db6f2cc4702111e6b5883417eb90ce17', '13007103929', 'qwerty', '1', null, null, null, '男', null, null, null, '2', null);
INSERT INTO `sysuser` VALUES ('f62dc25c69d211e684473417eb90ce17', 'test', '123456', '1', null, null, null, '男', null, null, null, '2', null);
INSERT INTO `sysuser` VALUES ('fab4d7325d3b11e6b87f3417eb90ce17', 'kkkkkk', '111', '1', null, null, null, '男', null, null, null, '2', null);
INSERT INTO `sysuser` VALUES ('ffs23f3f2g0sd9fd7s3f87s88seb90ce17', 'vender', '123456', '1', null, null, null, '男', null, null, null, '2', 'vender_2');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` varchar(50) NOT NULL,
  `userId` varchar(50) NOT NULL,
  `roleId` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`),
  KEY `roleId` (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台角色与用户关联表';

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1', '0');
INSERT INTO `user_role` VALUES ('2', '2', '2');
INSERT INTO `user_role` VALUES ('3', '28', '3');
INSERT INTO `user_role` VALUES ('4', '9', '1');
INSERT INTO `user_role` VALUES ('5', 'ffs23f3f2g0sd9fd7s3f87s88seb90ce17', '2');

-- ----------------------------
-- Table structure for visitlog
-- ----------------------------
DROP TABLE IF EXISTS `visitlog`;
CREATE TABLE `visitlog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customerid` int(11) DEFAULT NULL COMMENT '客户id',
  `idcard` varchar(20) DEFAULT NULL,
  `vdate` date DEFAULT NULL COMMENT '拜访日期',
  `vtype` varchar(20) DEFAULT NULL COMMENT '拜访方式',
  `vremark` varchar(300) DEFAULT NULL COMMENT '拜访记录',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of visitlog
-- ----------------------------

-- ----------------------------
-- Table structure for wares
-- ----------------------------
DROP TABLE IF EXISTS `wares`;
CREATE TABLE `wares` (
  `id` varchar(50) NOT NULL,
  `name` varchar(30) DEFAULT NULL,
  `creater` varchar(50) DEFAULT NULL COMMENT '创建者',
  `publicCode` varchar(100) DEFAULT NULL,
  `privateCode` varchar(100) DEFAULT NULL,
  `insideCode` varchar(255) DEFAULT NULL COMMENT '瓶盖内码',
  `status` varchar(10) DEFAULT '0' COMMENT '兑奖状态(0：未兑，1：未中，2：已兑)',
  `createTime` datetime DEFAULT NULL,
  `awardId` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of wares
-- ----------------------------
INSERT INTO `wares` VALUES ('61d0947170de11e6b1fc3417eb90ce17', null, null, '123', '789', null, '0', null, null);
