/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50713
Source Host           : localhost:3306
Source Database       : winker0

Target Server Type    : MYSQL
Target Server Version : 50713
File Encoding         : 65001

Date: 2017-03-25 23:19:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for material
-- ----------------------------
DROP TABLE IF EXISTS `material`;
CREATE TABLE `material` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `MaterialName` varchar(30) DEFAULT NULL,
  `MaterialQuantity` int(11) DEFAULT NULL,
  `ApplicableModels` varchar(30) DEFAULT NULL,
  `MaterialPrice` varchar(30) DEFAULT NULL,
  `Customer` varchar(30) DEFAULT NULL,
  `Leader` varchar(30) DEFAULT NULL,
  `Remark` varchar(300) DEFAULT NULL,
  `EntryTime` datetime DEFAULT NULL,
  `OutTime` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of material
-- ----------------------------

-- ----------------------------
-- Table structure for winker_user
-- ----------------------------
DROP TABLE IF EXISTS `winker_user`;
CREATE TABLE `winker_user` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `User_Name` varchar(100) NOT NULL,
  `Password` varchar(100) NOT NULL,
  `Email` varchar(100) NOT NULL,
  `Login_ID` varchar(100) NOT NULL,
  `address` varchar(100) DEFAULT NULL,
  `sex` bit(1) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `org_id` int(11) DEFAULT NULL,
  `department_id` int(255) DEFAULT NULL,
  `pic_path` varchar(50) DEFAULT NULL,
  `qq` bigint(20) DEFAULT NULL,
  `mobile` varchar(50) DEFAULT NULL,
  `create_user_id` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_user_id` bigint(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of winker_user
-- ----------------------------
INSERT INTO `winker_user` VALUES ('1', '刘同华', '1314251', '12312312', 'ahua1314251', '1123', '', '2016-03-24', '12', '3', '1', '495505394', '15256084771', '1', '2016-11-09 16:36:09', '1', '2016-11-25 16:36:18');
INSERT INTO `winker_user` VALUES ('2', '刘同华', '1314251', '12312312', 'ahua13142511', '1123', '', '2016-03-24', '12', '3', '1', '495505394', '15256084771', '1', '2016-11-09 16:36:09', '1', '2016-11-25 16:36:18');
INSERT INTO `winker_user` VALUES ('3', 'shanfu.liu', '123123', '23123123', '12312', 'qweqw', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `winker_user` VALUES ('4', 'shanfu.liu', '123123', '23123123', '12312', 'qweqw', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `winker_user` VALUES ('5', 'shanfu.liu', '123123', '23123123', '12312', 'qweqw', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `winker_user` VALUES ('6', 'shanfu.liu', '123123', '23123123', '12312', 'qweqw', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `winker_user` VALUES ('7', 'shanfu.liu', '123123', '23123123', '12312', 'qweqw', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `winker_user` VALUES ('8', 'shanfu.liu', '123123', '23123123', '12312', 'qweqw', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `winker_user` VALUES ('9', 'shanfu.liu', '123123', '23123123', '12312', 'qweqw', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `winker_user` VALUES ('10', 'shanfu.liu', '123123', '23123123', '12312', 'qweqw', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `winker_user` VALUES ('11', 'shanfu.liu', '123123', '23123123', '12312', 'qweqw', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `winker_user` VALUES ('12', 'shanfu.liu18', '123123', '23123123', '12312', 'qweqw', null, null, null, null, null, null, null, null, null, null, '2017-03-25 18:03:34');
INSERT INTO `winker_user` VALUES ('13', 'shanfu.liu18', '123123', '23123123', '12312', 'qweqw', null, null, null, null, null, null, null, null, null, null, '2017-03-19 22:15:12');
INSERT INTO `winker_user` VALUES ('14', 'shanfu.liu18', '123123', '23123123', '12312', 'qweqw', null, null, null, null, null, null, null, null, null, null, '2017-03-20 03:07:17');
INSERT INTO `winker_user` VALUES ('15', 'shanfu.liu12', '123123', '23123123', '12312', 'qweqw', null, null, null, null, null, null, null, null, null, null, null);
