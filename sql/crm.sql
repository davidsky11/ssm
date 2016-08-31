/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50525
Source Host           : localhost:3306
Source Database       : crm

Target Server Type    : MYSQL
Target Server Version : 50525
File Encoding         : 65001

Date: 2016-07-26 09:13:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `id` varchar(50) DEFAULT NULL COMMENT '用户编号',
  `username` varchar(50) DEFAULT NULL COMMENT '用户名称',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `userType` smallint(1) DEFAULT NULL COMMENT '用户类别，0-管理员，1-商户，2-普通用户',
  `generateName` varchar(60) DEFAULT NULL COMMENT '自动拼接的名字，格式username_userType',
  UNIQUE KEY `UNIQUE_USERNAME` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES ('888', 'kevin', '123', null, null);
INSERT INTO `account` VALUES ('afe91da54d8a11e695f33417eb90ce17', 'try', 'try', null, null);

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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of activity
-- ----------------------------
INSERT INTO `activity` VALUES ('147b08e14ea211e6ace23417eb90ce17', '羽毛球比赛', null, '2016-07-21 01:30:10', '2016-07-31 01:35:13', '公司羽毛球比赛', '2', 'kevin', null);
INSERT INTO `activity` VALUES ('74e4fd48528d11e6a1933417eb90ce17', 'test', 'sfs', '2016-07-26 01:29:05', '2016-07-28 01:29:08', 'sgsgs', '2', 'kevin', null);
INSERT INTO `activity` VALUES ('sfsfsfs', '测试', 'testdd', '2016-07-19 12:44:12', '2016-07-28 12:44:08', 'test', '1', 'admin', null);

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
  `amount` double(255,0) DEFAULT NULL COMMENT '奖项金额'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of award
-- ----------------------------
INSERT INTO `award` VALUES ('3eae1ee74e9b11e6ace23417eb90ce17', '特等奖', 'AWARD_SPEC', '只有一个特等奖', '1', '1', '188');
INSERT INTO `award` VALUES ('56b578704e9b11e6ace23417eb90ce17', '一等奖', 'AWARD_FIRST', '一等奖可以有多个', '2', '2', '138');
INSERT INTO `award` VALUES ('8cac6ee94e9b11e6ace23417eb90ce17', '二等奖', 'SWARD_SECOND', '二等奖可以有多个', '3', '3', '88');
INSERT INTO `award` VALUES ('9ab366dc4e9b11e6ace23417eb90ce17', '三等奖', 'SWARD_THIRD', null, '4', '4', '58');
INSERT INTO `award` VALUES ('b30ee1144e9b11e6ace23417eb90ce17', '幸运奖', 'SWARD_LUCK', '幸运奖是最后抽出的', '5', '5', '38');

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
  `accountId` varchar(50) DEFAULT NULL COMMENT '前端用户编号',
  `exchangeTime` datetime DEFAULT NULL,
  `innerCode` varchar(100) DEFAULT NULL COMMENT '内码',
  `outerCode` varchar(100) DEFAULT NULL COMMENT '瓶身码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of exchange
-- ----------------------------
INSERT INTO `exchange` VALUES ('849d606b528711e6a1933417eb90ce17', '888', '2016-07-26 00:46:42', '123', '234');

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
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8 COMMENT='权限关联表';

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES ('1', '1', '2');
INSERT INTO `role_menu` VALUES ('2', '1', '3');
INSERT INTO `role_menu` VALUES ('3', '1', '201');
INSERT INTO `role_menu` VALUES ('4', '1', '202');
INSERT INTO `role_menu` VALUES ('5', '1', '301');
INSERT INTO `role_menu` VALUES ('8', '2', '202');
INSERT INTO `role_menu` VALUES ('9', '1', '101');
INSERT INTO `role_menu` VALUES ('10', '2', '101');
INSERT INTO `role_menu` VALUES ('11', '1', '1');
INSERT INTO `role_menu` VALUES ('12', '2', '1');
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
INSERT INTO `role_menu` VALUES ('31', '2', '3');
INSERT INTO `role_menu` VALUES ('32', '2', '4');
INSERT INTO `role_menu` VALUES ('38', '2', '10');
INSERT INTO `role_menu` VALUES ('39', '1', '10');
INSERT INTO `role_menu` VALUES ('40', '1', '904');
INSERT INTO `role_menu` VALUES ('42', '2', '102');
INSERT INTO `role_menu` VALUES ('43', '2', '301');
INSERT INTO `role_menu` VALUES ('45', '2', '303');
INSERT INTO `role_menu` VALUES ('46', '2', '401');
INSERT INTO `role_menu` VALUES ('47', '2', '402');
INSERT INTO `role_menu` VALUES ('48', '2', '403');
INSERT INTO `role_menu` VALUES ('55', '2', '904');
INSERT INTO `role_menu` VALUES ('57', '1', '905');
INSERT INTO `role_menu` VALUES ('58', '2', '905');
INSERT INTO `role_menu` VALUES ('59', '1', '906');
INSERT INTO `role_menu` VALUES ('60', '2', '906');
INSERT INTO `role_menu` VALUES ('61', '1', '907');
INSERT INTO `role_menu` VALUES ('62', '2', '907');

-- ----------------------------
-- Table structure for scan_record
-- ----------------------------
DROP TABLE IF EXISTS `scan_record`;
CREATE TABLE `scan_record` (
  `id` varchar(50) NOT NULL,
  `accountId` varchar(50) DEFAULT NULL,
  `accountName` varchar(50) DEFAULT NULL,
  `longitude` double(15,0) DEFAULT NULL,
  `latitude` double(15,0) DEFAULT NULL,
  `scanTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of scan_record
-- ----------------------------
INSERT INTO `scan_record` VALUES ('01cb4a48528a11e6a1933417eb90ce17', null, 'kevin', '13', '124', '2016-07-26 14:46:53');
INSERT INTO `scan_record` VALUES ('95fc671d528a11e6a1933417eb90ce17', null, 'try', '12', '23', '2016-07-26 01:08:38');
INSERT INTO `scan_record` VALUES ('b8940731528f11e6a1933417eb90ce17', null, 'abc', '11', '12', '2016-07-26 01:45:21');
INSERT INTO `scan_record` VALUES ('c826c2b5528f11e6a1933417eb90ce17', null, 'ag', '2', '3', '2016-07-26 01:45:47');

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
) ENGINE=InnoDB AUTO_INCREMENT=908 DEFAULT CHARSET=utf8 COMMENT='后台菜单表';

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
INSERT INTO `sysmenu` VALUES ('101', '资源管理', '1', '101', 'icon-nav', '/menu/list', '1');
INSERT INTO `sysmenu` VALUES ('102', '菜单树', '1', '102', 'icon-set', '/menu/listtree', '1');
INSERT INTO `sysmenu` VALUES ('201', '客户管理', '2', '201', 'icon-role', '/customer/list', '1');
INSERT INTO `sysmenu` VALUES ('301', '后台用户管理', '3', '301', 'icon-log', '/user/list', '1');
INSERT INTO `sysmenu` VALUES ('302', '员工管理', '3', '302', 'icon-person', '/user/listtree', '1');
INSERT INTO `sysmenu` VALUES ('303', '前端用户管理', '3', '303', 'icon-person', '/account/list', '1');
INSERT INTO `sysmenu` VALUES ('304', '系统用户管理', '3', '305', 'icon-person', '', '1');
INSERT INTO `sysmenu` VALUES ('401', '权限管理', '4', '401', 'icon-msg', '/menu/list', '1');
INSERT INTO `sysmenu` VALUES ('402', '角色管理', '4', '402', 'icon-set', '/menu/list', '1');
INSERT INTO `sysmenu` VALUES ('403', '系统通知', '4', '403', 'icon-help', '/menu/list', '1');
INSERT INTO `sysmenu` VALUES ('404', '部门管理', '4', '404', 'icon-role', '/systemdef/listtree', '1');
INSERT INTO `sysmenu` VALUES ('501', '系统报表', '5', '501', 'icon-email', '/menu/list', '1');
INSERT INTO `sysmenu` VALUES ('601', '合同查询', '6', '601', 'icon-ht', '/menu/list', '1');
INSERT INTO `sysmenu` VALUES ('701', '资源查询', '7', '701', 'icon-tongji', '/menu/list', '1');
INSERT INTO `sysmenu` VALUES ('801', '绩效查询', '8', '801', 'icon-bb', '/menu/list', '1');
INSERT INTO `sysmenu` VALUES ('901', '差旅查询', '9', '901', 'icon-money', '/menu/list', '1');
INSERT INTO `sysmenu` VALUES ('904', '活动维护', '10', '904', 'icon-set', '/activity/list', '1');
INSERT INTO `sysmenu` VALUES ('905', '奖项设置', '10', '905', 'icon-set', '/award/list', '1');
INSERT INTO `sysmenu` VALUES ('906', '扫描记录', '10', '906', 'icon-bb', '/scanRecord/list', '1');
INSERT INTO `sysmenu` VALUES ('907', '兑奖记录', '10', '907', 'icon-tongji', '/exchange/list', '1');

-- ----------------------------
-- Table structure for sysrole
-- ----------------------------
DROP TABLE IF EXISTS `sysrole`;
CREATE TABLE `sysrole` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) NOT NULL DEFAULT '' COMMENT '角色名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='后台角色表';

-- ----------------------------
-- Records of sysrole
-- ----------------------------
INSERT INTO `sysrole` VALUES ('1', '超级管理员');
INSERT INTO `sysrole` VALUES ('2', '普通用户');

-- ----------------------------
-- Table structure for system
-- ----------------------------
DROP TABLE IF EXISTS `system`;
CREATE TABLE `system` (
  `field_id` int(11) NOT NULL,
  `field_index` int(11) DEFAULT NULL,
  `table_name` varchar(40) NOT NULL COMMENT '表名',
  `field_name` varchar(40) NOT NULL COMMENT '字段名，类似ID/NAME',
  `field_caption` varchar(40) NOT NULL COMMENT '字段名称，类似 编号/名称',
  `field_label` varchar(60) DEFAULT NULL COMMENT '前端显示的字段名称',
  `field_type` varchar(40) DEFAULT NULL COMMENT '字段类型，类似 INT/VARCHAR',
  `field_width` smallint(6) DEFAULT NULL COMMENT '字段宽度',
  `field_scale` smallint(6) DEFAULT NULL COMMENT '字段精度',
  `field_visible` smallint(6) DEFAULT NULL COMMENT '字段可见度，0不可见，1可见',
  `field_mark` smallint(6) DEFAULT NULL COMMENT '字段活性：1-关键字/2-固定项/3-自定义固定相/4-被定义固定项/5-可选已选且固定项/6-自定义项/7-可选且已选项/8-可选且未选项/9-自定义可选且未选项  3,6,9可转换/5,7,8可转换/1,2,4不可修改',
  `field_lookup` varchar(40) DEFAULT NULL COMMENT '关联代码',
  `display_format` varchar(30) DEFAULT NULL COMMENT '显示格式',
  `grid_field` char(1) DEFAULT NULL COMMENT '是否列表显示列（0：不是，1：是）',
  `is_static` varchar(10) DEFAULT NULL,
  `sub_field` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`field_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system
-- ----------------------------

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
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL DEFAULT '' COMMENT '登录名（匿名）',
  `password` varchar(100) NOT NULL DEFAULT '' COMMENT '登录密码',
  `sysid` int(11) NOT NULL COMMENT '所属部门',
  `email` varchar(50) DEFAULT NULL COMMENT '邮件',
  `name` varchar(20) DEFAULT NULL COMMENT '真实姓名',
  `idcard` varchar(20) NOT NULL COMMENT '身份证',
  `gender` varchar(2) NOT NULL DEFAULT '男' COMMENT '性别1男2女',
  `qq` varchar(20) DEFAULT NULL COMMENT 'qq',
  `weixin` varchar(50) DEFAULT NULL,
  `regtime` date NOT NULL COMMENT '入职日期',
  PRIMARY KEY (`id`,`idcard`),
  KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8 COMMENT='用户信息表';

-- ----------------------------
-- Records of sysuser
-- ----------------------------
INSERT INTO `sysuser` VALUES ('1', 'admin', '123456', '101', null, null, '1', '男', '905891460', null, '2015-05-01');
INSERT INTO `sysuser` VALUES ('2', 'kevin', '123456', '101', null, null, '2', '男', '123456784', null, '2015-05-02');
INSERT INTO `sysuser` VALUES ('3', 'test1', '123456', '101', null, null, '3', '女', null, null, '0000-00-00');
INSERT INTO `sysuser` VALUES ('4', 'test2', '123456', '101', null, null, '4', '女', null, null, '0000-00-00');
INSERT INTO `sysuser` VALUES ('5', 'test3', '123456', '101', 'sdasda@163.com', null, '5', '男', '7777777', null, '2015-05-03');
INSERT INTO `sysuser` VALUES ('6', 'test4', '123456', '104', '322@qq.com', null, '6', '女', '8888888', null, '2015-05-03');
INSERT INTO `sysuser` VALUES ('7', 'test5', '123456', '104', null, null, '7', '女', '6666666', null, '2015-05-03');
INSERT INTO `sysuser` VALUES ('8', 'test6', '123456', '104', null, null, '8', '男', null, null, '0000-00-00');
INSERT INTO `sysuser` VALUES ('9', 'test7', '123456', '104', 'walk_hai@163.com', null, '9', '女', null, null, '0000-00-00');
INSERT INTO `sysuser` VALUES ('13', 'asdasdsa', '123456', '10201', '11@qq.com', null, '10', '女', null, null, '0000-00-00');
INSERT INTO `sysuser` VALUES ('14', 'test10', '', '10201', 'walk_hai@163.com', null, '11', '男', '333333', null, '2015-05-04');
INSERT INTO `sysuser` VALUES ('15', 'aaa', '', '10201', null, null, '12', '女', null, null, '0000-00-00');
INSERT INTO `sysuser` VALUES ('16', 'a', '', '10201', null, null, '13', '男', '444444', null, '2015-05-04');
INSERT INTO `sysuser` VALUES ('17', 'ttt', '', '10201', 'ttt@sss.com', null, '14', '女', null, null, '0000-00-00');
INSERT INTO `sysuser` VALUES ('18', 'aaa', 'aaa', '10201', 'adf@sdfa.com', null, '23413245', '女', '89898989', 'adfadad', '2015-05-06');
INSERT INTO `sysuser` VALUES ('19', '1', '1', '10201', null, null, '15', '1', null, null, '2015-05-05');
INSERT INTO `sysuser` VALUES ('20', '1', '1', '30201', null, null, '16', '1', null, null, '2015-01-01');
INSERT INTO `sysuser` VALUES ('21', '1', '1', '30201', 'wa@ads.com', null, '17', '男', '888888', 'sssss', '2015-05-09');
INSERT INTO `sysuser` VALUES ('22', 'aaaa', 'aaaa', '30201', 'adsfadsf@asdf.com', null, '123123123123', '男', '132423', 'adsfasdfadffsdfadfadsf', '2015-04-05');
INSERT INTO `sysuser` VALUES ('23', 'ahahhaha', '123456', '301', 'sdfadsf@aa.com', null, '23111111111111', '男', '7979798', 'dasdgads', '2015-05-12');
INSERT INTO `sysuser` VALUES ('24', 'ddd', 'ddd', '301', '1@qq.com', null, '12', '男', '111', '122', '2015-07-13');
INSERT INTO `sysuser` VALUES ('25', 'dddss', 'dddss', '10501', '1@qq.com', '', '12', '男', '111', '122', '2015-07-13');
INSERT INTO `sysuser` VALUES ('26', 'ddddff', 'dddfff', '10501', '1@qq.com', '', '12', '男', '111', '122', '2015-07-13');
INSERT INTO `sysuser` VALUES ('27', 'dddgg', 'dddgg', '10502', '1@qq.com', '', '12', '男', '111', '122', '2015-07-13');

-- ----------------------------
-- Table structure for sys_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `sys_dictionary`;
CREATE TABLE `sys_dictionary` (
  `ID` int(11) NOT NULL,
  `ENTRYCODE` varchar(30) NOT NULL,
  `ENTRYVALUE` varchar(100) DEFAULT NULL,
  `ADDITIONAL` varchar(100) DEFAULT NULL,
  `ADDITIONAL2` varchar(100) DEFAULT NULL,
  `DESCRIPTION` varchar(300) DEFAULT NULL,
  `CLASSNAME` varchar(100) DEFAULT NULL,
  `CLASSCODE` varchar(30) DEFAULT NULL,
  `CLASSNAMEAB` varchar(20) DEFAULT NULL,
  `DADISTATUS` char(1) DEFAULT NULL,
  `DORDER` int(11) DEFAULT NULL,
  `ISLEAF` char(1) DEFAULT NULL,
  `LEVELNO` int(11) DEFAULT NULL,
  `P_GUID` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_dictionary
-- ----------------------------

-- ----------------------------
-- Table structure for tabname
-- ----------------------------
DROP TABLE IF EXISTS `tabname`;
CREATE TABLE `tabname` (
  `table_name` varchar(20) NOT NULL COMMENT '表名',
  `table_index` int(8) DEFAULT NULL COMMENT '表序号',
  `table_caption` varchar(40) DEFAULT NULL,
  `preserve` varchar(10) DEFAULT NULL,
  `table_visible` smallint(8) DEFAULT NULL COMMENT '0-不可见   1-可见可重构（自定义表默认为1）  2-可见不可删   3-可见不可改',
  `relation` varchar(20) DEFAULT NULL COMMENT '20位的字符串，由0/1组成，该字段的每一位代表一个，没有用',
  `source_relationi_id` smallint(6) DEFAULT NULL COMMENT '没有用，1表示该表被这一模块使用。',
  PRIMARY KEY (`table_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tabname
-- ----------------------------

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` int(11) NOT NULL,
  `roleId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`),
  KEY `roleId` (`roleId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='后台角色与用户关联表';

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1', '1');
INSERT INTO `user_role` VALUES ('2', '2', '2');

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
