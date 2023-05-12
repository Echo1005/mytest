/*
Navicat MySQL Data Transfer

Source Server         : mydb
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : bookshopdb

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2023-05-05 23:56:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for cartb
-- ----------------------------
DROP TABLE IF EXISTS `cartb`;
CREATE TABLE `cartb` (
  `carId` int(11) NOT NULL AUTO_INCREMENT,
  `carShopId` int(11) DEFAULT NULL,
  `carUserId` int(11) DEFAULT NULL,
  PRIMARY KEY (`carId`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cartb
-- ----------------------------

-- ----------------------------
-- Table structure for jijiantb
-- ----------------------------
DROP TABLE IF EXISTS `jijiantb`;
CREATE TABLE `jijiantb` (
  `jijianId` int(11) NOT NULL AUTO_INCREMENT,
  `jijianName` varchar(255) DEFAULT NULL,
  `jijianAddresse` varchar(255) DEFAULT NULL,
  `jijianPhone` varchar(255) DEFAULT NULL,
  `jijianUserId` int(11) DEFAULT NULL,
  PRIMARY KEY (`jijianId`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of jijiantb
-- ----------------------------
INSERT INTO `jijiantb` VALUES ('1', '小明', '西安市雁塔区', '15249248888', '17');
INSERT INTO `jijiantb` VALUES ('2', '小花', '西安市', '15249249999', '17');
INSERT INTO `jijiantb` VALUES ('3', '小萝莉', '上海市', '15249246969', '17');
INSERT INTO `jijiantb` VALUES ('4', '小品', '西安市', '15249248888', '17');
INSERT INTO `jijiantb` VALUES ('5', '小明', '西安市莲湖区', '15249249988', '26');
INSERT INTO `jijiantb` VALUES ('6', '小米', '西安市雁塔区', '15249243002', '92');
INSERT INTO `jijiantb` VALUES ('7', '小虎虎', '西安市雁塔区', '15249243002', '92');
INSERT INTO `jijiantb` VALUES ('8', '小花', '西安市雁塔区', '15249248877', '92');
INSERT INTO `jijiantb` VALUES ('9', 'like', '西安市', '15249248989', '92');
INSERT INTO `jijiantb` VALUES ('10', '画画', '西安市雁塔区', '15249240022', '93');
INSERT INTO `jijiantb` VALUES ('11', '小花', '西安市雁塔区西路', '15249241003', '94');
INSERT INTO `jijiantb` VALUES ('12', '画画', '西安市', '15249241006', '96');
INSERT INTO `jijiantb` VALUES ('13', '揪揪', '西安市', '15249241008', '97');

-- ----------------------------
-- Table structure for ordertb
-- ----------------------------
DROP TABLE IF EXISTS `ordertb`;
CREATE TABLE `ordertb` (
  `orderId` int(11) NOT NULL AUTO_INCREMENT,
  `orderMessageId` varchar(100) DEFAULT NULL,
  `orderMessageMoney` varchar(255) DEFAULT NULL,
  `orderUserId` varchar(100) DEFAULT NULL,
  `orderUserName` varchar(255) DEFAULT NULL,
  `orderAddress` varchar(500) DEFAULT NULL,
  `orderCreatime` varchar(100) DEFAULT NULL,
  `orderNo` varchar(500) DEFAULT NULL,
  `orderState` varchar(255) DEFAULT NULL,
  `orderRemark` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`orderId`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ordertb
-- ----------------------------
INSERT INTO `ordertb` VALUES ('50', '33', '30', '92', '玥溪', '小虎虎,15249243002,西安市雁塔区', '2023-05-05 23:50', 'NO20230505235051', '2', '66666666');

-- ----------------------------
-- Table structure for reviewtb
-- ----------------------------
DROP TABLE IF EXISTS `reviewtb`;
CREATE TABLE `reviewtb` (
  `reviewId` int(11) NOT NULL AUTO_INCREMENT,
  `reviewMessageId` int(11) DEFAULT NULL,
  `reviewContent` varchar(255) DEFAULT NULL,
  `reviewUserId` int(11) DEFAULT NULL,
  `reviewUserName` varchar(255) DEFAULT NULL,
  `reviewTime` varchar(100) DEFAULT NULL,
  `reviewSendUserId` varchar(255) DEFAULT NULL,
  `reviewMessageName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`reviewId`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of reviewtb
-- ----------------------------
INSERT INTO `reviewtb` VALUES ('66', '36', '很不错哦的书子', '92', '玥溪', '2022-04-04 22:00', null, null);

-- ----------------------------
-- Table structure for shoptb
-- ----------------------------
DROP TABLE IF EXISTS `shoptb`;
CREATE TABLE `shoptb` (
  `shopId` int(50) NOT NULL AUTO_INCREMENT,
  `shopName` varchar(255) DEFAULT NULL,
  `shopTypeId` int(11) DEFAULT NULL,
  `shopTypeName` varchar(255) DEFAULT NULL,
  `shopMoney` varchar(255) DEFAULT NULL,
  `shopNumber` varchar(11) DEFAULT NULL,
  `shopMessage` varchar(2000) DEFAULT NULL,
  `shopImg` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`shopId`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of shoptb
-- ----------------------------
INSERT INTO `shoptb` VALUES ('33', '感冒灵颗粒', '21', '感冒发烧', '15', '14', '功能主治：止血，镇痛，消炎，愈创，用于小面积开放性创伤。', 'drug1.jpg');

-- ----------------------------
-- Table structure for typetb
-- ----------------------------
DROP TABLE IF EXISTS `typetb`;
CREATE TABLE `typetb` (
  `typeId` int(50) NOT NULL AUTO_INCREMENT,
  `typeName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`typeId`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of typetb
-- ----------------------------
INSERT INTO `typetb` VALUES ('21', '感冒发烧');
INSERT INTO `typetb` VALUES ('22', '清热解毒');
INSERT INTO `typetb` VALUES ('25', '肠胃用药');
INSERT INTO `typetb` VALUES ('26', '皮肤用药');
INSERT INTO `typetb` VALUES ('27', '抗菌消炎');
INSERT INTO `typetb` VALUES ('28', '心脑血管');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `uid` int(255) NOT NULL AUTO_INCREMENT,
  `uname` varchar(200) CHARACTER SET utf8 NOT NULL,
  `uphone` varchar(100) NOT NULL,
  `upswd` varchar(200) NOT NULL,
  `utime` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `uImg` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=99 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('92', '玥溪', '15249241001', '123456', '2022-04-04 21:56', 'chongwu_6.jpg');
INSERT INTO `user` VALUES ('93', '小花', '15249241002', '123456', '2022-04-04 21:56', null);
INSERT INTO `user` VALUES ('94', 'xiaohua ', '15249241003', '123456', '2022-04-04 21:56', null);
INSERT INTO `user` VALUES ('96', '画画', '15249241006', '123456', '2022-04-04 21:56', '20170908172539.png');
INSERT INTO `user` VALUES ('97', '揪揪', '15249241008', '123456', '2022-04-04 21:56', '20170908172539.png');
