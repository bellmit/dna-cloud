/*
 Navicat MySQL Data Transfer

 Source Server         : 39.100.79.169
 Source Server Type    : MySQL
 Source Server Version : 50729
 Source Host           : 39.100.79.169:3316
 Source Schema         : dnacloud

 Target Server Type    : MySQL
 Target Server Version : 50729
 File Encoding         : 65001

 Date: 14/07/2020 18:06:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='序列';

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
BEGIN;
INSERT INTO `hibernate_sequence` VALUES (1);
COMMIT;

-- ----------------------------
-- Table structure for nt_data_source_config
-- ----------------------------
DROP TABLE IF EXISTS `nt_data_source_config`;
CREATE TABLE `nt_data_source_config` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `connect_name` varchar(50) NOT NULL COMMENT '数据源连接名称',
  `db_type` varchar(20) DEFAULT NULL COMMENT '数据库类型:mysql|oracle',
  `ds_type` varchar(20) DEFAULT NULL COMMENT 'db|redis',
  `ip_address` varchar(20) DEFAULT NULL COMMENT '数据库连接地址',
  `db_name` varchar(50) DEFAULT '' COMMENT '数据库名称',
  `port` int(10) DEFAULT NULL COMMENT '端口号',
  `user_name` varchar(20) DEFAULT NULL COMMENT '用户名',
  `password` varchar(32) DEFAULT NULL COMMENT '密码',
  `driver_name` varchar(255) DEFAULT NULL COMMENT '驱动名称',
  `url` varchar(255) DEFAULT NULL COMMENT '地址',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `dept_code` varchar(40) DEFAULT NULL COMMENT '部门编码',
  `dept_name` varchar(40) DEFAULT NULL COMMENT '部门名称',
  PRIMARY KEY (`id`),
  KEY `uk_connect_name` (`connect_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据源';

-- ----------------------------
-- Records of nt_data_source_config
-- ----------------------------
BEGIN;
INSERT INTO `nt_data_source_config` VALUES ('1', 'localhost', 'mysql', 'db', 'localhost', 'dnacloud', 3306, 'root', '1234', 'com.mysql.cj.jdbc.Driver', 'jdbc:mysql://localhost:3306/dnacloud?useUnicode=true&characterEncoding=utf8&autoReconnect=true&useSSL=false&serverTimezone=GMT%2B8', '2020-06-08 16:16:47', '2020-07-06 11:07:11', NULL, NULL);
INSERT INTO `nt_data_source_config` VALUES ('2', '110', 'oracle', 'db', '192.168.1.131', NULL, 1521, 'admin', '1', 'oracle.jdbc.driver.OracleDriver', 'jdbc:oracle:thin:@192.168.1.131:1521:null', '2020-07-03 11:48:35', '2020-07-13 11:00:43', NULL, NULL);
INSERT INTO `nt_data_source_config` VALUES ('3', 'redis_192.168.1.151', 'redis', 'cache', '192.168.1.151', '0', 6379, '', 'pw', NULL, NULL, '2020-06-29 16:05:31', '2020-07-03 18:15:56', NULL, NULL);
INSERT INTO `nt_data_source_config` VALUES ('4', 'redis_47.92.219.7', 'redis', 'cache', '47.92.219.7', '1', 6379, '', 'pw', NULL, NULL, '2020-06-29 16:06:27', '2020-06-29 17:58:39', NULL, NULL);
INSERT INTO `nt_data_source_config` VALUES ('5', '440000', 'redis', 'cache', '192.168.1.151', '6', 6379, NULL, 'pw', NULL, NULL, '2020-06-30 10:31:50', '2020-07-03 19:25:35', NULL, NULL);
INSERT INTO `nt_data_source_config` VALUES ('6', '620000', 'redis', 'cache', '47.92.219.7', '6', 6379, NULL, 'pw', NULL, NULL, '2020-06-30 10:32:25', '2020-07-03 18:30:52', NULL, NULL);
INSERT INTO `nt_data_source_config` VALUES ('7', '110000', 'redis', 'cache', '47.92.219.7', '5', 6379, NULL, 'pw', NULL, NULL, NULL, '2020-07-03 18:31:25', NULL, NULL);
INSERT INTO `nt_data_source_config` VALUES ('8', '620000', 'oracle', 'db', '192.168.1.121', 'orcl', 1521, 'gdna', 'gdna', 'oracle.jdbc.driver.OracleDriver', 'jdbc:oracle:thin:@192.168.1.121:1521:orcl', '2020-07-06 10:23:32', '2020-07-06 15:07:17', NULL, NULL);
INSERT INTO `nt_data_source_config` VALUES ('9', '110000', 'oracle', 'db', '192.168.1.121', 'orcl', 1521, 'BJ_GDNA2', 'DNA', 'oracle.jdbc.driver.OracleDriver', 'jdbc:oracle:thin:@192.168.1.121:1521:orcl', '2020-07-06 12:02:12', '2020-07-06 15:07:27', NULL, NULL);
INSERT INTO `nt_data_source_config` VALUES ('ff8080817312c2a9017312cb51880002', '300001', 'oracle', 'db', '39.100.79.169', 'orcl', 1521, 'bjdna', 'dna', 'oracle.jdbc.driver.OracleDriver', 'jdbc:oracle:thin:@39.100.79.169:1521:ORCL', '2020-07-03 11:49:41', '2020-07-06 15:02:32', NULL, NULL);
INSERT INTO `nt_data_source_config` VALUES ('ff8080817345c9900173469c80910006', '110000000001', 'oracle', 'db', NULL, NULL, 0, NULL, NULL, 'oracle.jdbc.driver.OracleDriver', 'jdbc:oracle:thin:@null:0:null', '2020-07-13 13:18:48', '2020-07-13 13:18:48', NULL, NULL);
INSERT INTO `nt_data_source_config` VALUES ('ff8080817346ef72017346f1a4de0000', '110000000000', 'oracle', 'db', '192.168.1.131', NULL, 1521, 'admin', '1', 'oracle.jdbc.driver.OracleDriver', 'jdbc:oracle:thin:@192.168.1.131:1521:null', '2020-07-13 14:51:48', '2020-07-13 14:51:48', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_dict_item
-- ----------------------------
DROP TABLE IF EXISTS `nt_sys_dict_item`;
CREATE TABLE `nt_sys_dict_item` (
  `id` varchar(255) NOT NULL,
  `dict_item_code` varchar(32) NOT NULL,
  `dict_item_name` varchar(32) DEFAULT NULL,
  `dict_item_order` int(11) DEFAULT NULL,
  `dict_item_type_id` varchar(32) NOT NULL,
  `parent_id` varchar(255) DEFAULT NULL,
  `del_status` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_dict_item_type_id` (`dict_item_type_id`) USING BTREE,
  CONSTRAINT `fk_dict_item_type_id` FOREIGN KEY (`dict_item_type_id`) REFERENCES `nt_sys_dict_item_type` (`dict_type_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典';

-- ----------------------------
-- Records of nt_sys_dict_item
-- ----------------------------
BEGIN;
INSERT INTO `nt_sys_dict_item` VALUES ('1', '0', '未同步', NULL, 'GENE_SYNC_STATUS', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('10', '0', 'FLASE', NULL, 'TRANSFER_NATION_FLAG', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('100', '04', '藏族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('101', '05', '维吾尔族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('102', '06', '苗族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('103', '07', '彝族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('104', '08', '壮族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('105', '09', '布依族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('106', '10', '朝鲜族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('107', '11', '满族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('108', '12', '侗族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('109', '13', '瑶族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('11', '1', 'TRUE', NULL, 'TRANSFER_NATION_FLAG', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('110', '14', '白族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('111', '15', '土家族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('112', '16', '哈尼族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('113', '17', '哈萨克族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('114', '18', '傣族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('115', '19', '黎族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('116', '20', '僳僳族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('117', '21', '佤族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('118', '22', '畲族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('119', '23', '高山族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('12', '0', 'FLASE', NULL, 'DELETE_FLAG', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('120', '24', '拉祜族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('121', '25', '水族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('122', '26', '东乡族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('123', '27', '纳西族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('124', '28', '景颇族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('125', '29', '柯尔克孜族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('126', '30', '土族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('127', '31', '达斡尔族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('128', '32', '仫佬族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('129', '33', '羌族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('13', '1', 'TRUE', NULL, 'DELETE_FLAG', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('130', '34', '布朗族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('131', '35', '撒拉族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('132', '36', '毛南族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('133', '37', '仡佬族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('134', '38', '锡伯族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('135', '39', '阿昌族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('136', '40', '普米族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('137', '41', '塔吉克族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('138', '42', '怒族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('139', '43', '乌孜别克族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('14', '1', '血痕', NULL, 'SAMPLE_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('140', '44', '俄罗斯族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('141', '45', '鄂温克族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('142', '46', '德昂族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('143', '47', '保安族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('144', '48', '裕固族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('145', '49', '京族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('146', '50', '塔塔尔族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('147', '51', '独龙族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('148', '52', '鄂伦春族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('149', '53', '赫哲族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('15', '2', '精斑', NULL, 'SAMPLE_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('150', '54', '门巴族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('151', '55', '珞巴族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('152', '56', '基诺族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('153', '97', '其他', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('154', '98', '外国血统(中国籍人士)', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('155', '004', '阿富汗', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('156', '008', '阿尔巴尼亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('157', '010', '南极洲', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('158', '012', '国籍', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('159', '016', '美属萨摩亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('16', '3', '毛发', NULL, 'SAMPLE_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('160', '020', '安道尔', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('161', '024', '安哥拉', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('162', '028', '安提瓜', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('163', '032', '阿根廷', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('164', '036', '澳大利亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('165', '040', '奥地利', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('166', '044', '巴哈马', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('167', '048', '巴林', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('168', '050', '孟加拉', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('169', '052', '巴巴多斯', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('17', '4', '骨骼', NULL, 'SAMPLE_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('170', '056', '比利时', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('171', '060', '百慕大', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('172', '064', '不丹', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('173', '068', '玻利维亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('174', '072', '博茨瓦纳', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('175', '074', '布维岛', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('176', '076', '巴西', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('177', '084', '伯利兹', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('178', '086', '英属印度洋领土', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('179', '090', '所罗门群岛', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('18', '5', '唾液斑', NULL, 'SAMPLE_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('180', '092', '英属维尔京群岛', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('181', '096', '文莱', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('182', '100', '保加利亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('183', '1001', '阿尔巴尼亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('184', '1002', '阿尔及利亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('185', '1003', '阿富汗', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('186', '1004', '阿根廷', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('187', '1005', '阿拉伯联合酋长国', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('188', '1006', '阿拉伯叙利亚共和国', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('189', '1007', '阿曼', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('19', '6', '脱落细胞', NULL, 'SAMPLE_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('190', '1008', '阿塞拜疆', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('191', '1009', '埃及', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('192', '1010', '埃塞俄比亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('193', '1011', '爱尔兰', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('194', '1012', '爱沙尼亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('195', '1013', '安道尔', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('196', '1014', '安哥拉', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('197', '1015', '安提瓜', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('198', '1016', '巴布达', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('199', '1017', '奥地利', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('2', '1', '同步中', NULL, 'GENE_SYNC_STATUS', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('20', '7', '其他', NULL, 'SAMPLE_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('200', '1018', '澳大利亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('201', '1019', '巴巴多斯', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('202', '1020', '巴布亚新几内亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('203', '1021', '巴哈马', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('204', '1022', '巴基斯坦', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('205', '1023', '巴拉圭', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('206', '1024', '巴林', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('207', '1025', '巴拿马', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('208', '1026', '巴西', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('209', '1027', '白俄罗斯', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('21', '1', '前科', NULL, 'MEMBER_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('210', '1028', '保加利亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('211', '1029', '贝宁', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('212', '1030', '比利时', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('213', '1031', '秘鲁', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('214', '1032', '冰岛', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('215', '1033', '波兰', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('216', '1034', '波斯尼亚和黑塞哥维那', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('217', '1035', '玻利维亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('218', '1036', '伯利兹', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('219', '1037', '博茨瓦纳', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('22', '2', '嫌疑人', NULL, 'MEMBER_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('220', '1038', '不丹', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('221', '1039', '布基纳法索', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('222', '104', '缅甸', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('223', '1040', '布隆迪', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('224', '1041', '朝鲜民主主义人民共和国', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('225', '1042', '赤道几内亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('226', '1043', '大不列颠及北爱尔兰联合王国', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('227', '1044', '大韩民国', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('228', '1045', '丹麦', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('229', '1046', '德国', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('23', '3', '涉黑', NULL, 'MEMBER_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('230', '1047', '东帝汶', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('231', '1048', '多哥', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('232', '1049', '多米尼加共和国', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('233', '1050', '多米尼克', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('234', '1051', '俄罗斯联邦', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('235', '1052', '厄瓜多尔', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('236', '1053', '厄立特里亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('237', '1054', '法国', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('238', '1055', '菲律宾', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('239', '1056', '斐济', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('24', '4', '被害人、身份不明人', NULL, 'MEMBER_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('240', '1057', '芬兰', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('241', '1058', '佛得角', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('242', '1059', '冈比亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('243', '1060', '刚果', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('244', '1061', '刚果民主共和国', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('245', '1062', '哥伦比亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('246', '1063', '格林纳达', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('247', '1064', '格鲁吉亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('248', '1065', '哥斯达黎加', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('249', '1066', '古巴', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('25', '5', '失踪人员亲属库', NULL, 'MEMBER_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('250', '1067', '圭亚那', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('251', '1068', '哈萨克斯坦', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('252', '1069', '海地', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('253', '1070', '荷兰', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('254', '1071', '黑山共和国', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('255', '1072', '洪都拉斯', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('256', '1073', '基里巴斯共和国', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('257', '1074', '吉布提', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('258', '1075', '吉尔吉斯斯坦', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('259', '1076', '几内亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('26', '6', '未破案现场', NULL, 'MEMBER_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('260', '1077', '几内亚比绍', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('261', '1078', '加拿大', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('262', '1079', '加纳', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('263', '108', '布隆迪', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('264', '1080', '加蓬', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('265', '1081', '柬埔寨', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('266', '1082', '捷克共和国', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('267', '1083', '津巴布韦', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('268', '1084', '喀麦隆', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('269', '1085', '卡塔尔', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('27', '7', '被鉴定人', NULL, 'MEMBER_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('270', '1086', '科摩罗', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('271', '1087', '科特迪瓦', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('272', '1088', '科威特', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('273', '1089', '克罗地亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('274', '1090', '肯尼亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('275', '1091', '拉脱维亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('276', '1092', '莱索托', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('277', '1093', '老挝人民民主共和国', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('278', '1094', '黎巴嫩', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('279', '1095', '立陶宛', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('28', '8', '鉴定人', NULL, 'MEMBER_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('280', '1096', '利比里亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('281', '1097', '利比亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('282', '1098', '列支敦士登', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('283', '1099', '卢森堡', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('284', '1100', '卢旺达', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('285', '1101', '罗马尼亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('286', '1102', '马达加斯加', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('287', '1103', '马尔代夫', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('288', '1104', '马耳他', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('289', '1105', '马拉维', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('29', '9', '违法犯罪人员', NULL, 'MEMBER_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('290', '1106', '马来西亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('291', '1107', '马里', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('292', '1108', '前南斯拉夫的马其顿共和国', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('293', '1109', '马绍尔群岛', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('294', '1110', '毛里求斯', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('295', '1111', '毛里塔尼亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('296', '1112', '美利坚合众国', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('297', '1113', '蒙古', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('298', '1114', '孟加拉国', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('299', '1115', '密克罗尼西亚联邦', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('3', '2', '同步成功', NULL, 'GENE_SYNC_STATUS', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('30', '10', '质控人员', NULL, 'MEMBER_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('300', '1116', '缅甸', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('301', '1117', '摩尔多瓦共和国', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('302', '1118', '摩洛哥', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('303', '1119', '摩纳哥', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('304', '112', '白俄罗斯', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('305', '1120', '莫桑比克', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('306', '1121', '墨西哥', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('307', '1122', '纳米比亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('308', '1123', '南非', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('309', '1124', '南苏丹', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('310', '1125', '瑙鲁', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('311', '1126', '尼泊尔', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('312', '1127', '尼加拉瓜', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('313', '1128', '尼日尔', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('314', '1129', '尼日利亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('315', '1130', '挪威', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('316', '1131', '帕劳', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('317', '1132', '葡萄牙', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('318', '1133', '日本', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('319', '1134', '瑞典', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('320', '1135', '瑞士', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('321', '1136', '萨尔瓦多', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('322', '1137', '萨摩亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('323', '1138', '塞尔维亚共和国', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('324', '1139', '塞拉利昂', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('325', '1140', '塞内加尔', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('326', '1141', '塞浦路斯', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('327', '1142', '塞舌尔', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('328', '1143', '沙特阿拉伯', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('329', '1144', '圣多美和普林西比', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('33', '0', '新增', NULL, 'GENE_SYNC_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('330', '1145', '圣基茨和尼维斯', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('331', '1146', '圣卢西亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('332', '1147', '圣马力诺', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('333', '1148', '圣文森特和格林纳丁斯', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('334', '1149', '斯里兰卡', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('335', '1150', '斯洛伐克', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('336', '1151', '斯洛文尼亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('337', '1152', '斯威士兰', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('338', '1153', '苏丹', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('339', '1154', '苏里南', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('34', '1', '更新', NULL, 'GENE_SYNC_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('340', '1155', '所罗门群岛', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('341', '1156', '索马里', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('342', '1157', '塔吉克斯坦', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('343', '1158', '泰国', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('344', '1159', '坦桑尼亚联合共和国', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('345', '116', '柬埔寨', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('346', '1160', '汤加', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('347', '1161', '特立尼达和多巴哥', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('348', '1162', '突尼斯', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('349', '1163', '图瓦卢', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('35', '-1', '删除', NULL, 'GENE_SYNC_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('350', '1164', '土耳其', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('351', '1165', '土库曼斯坦', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('352', '1166', '瓦努阿图', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('353', '1167', '危地马拉', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('354', '1168', '委内瑞拉', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('355', '1169', '文莱达鲁萨兰国', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('356', '1170', '乌干达', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('357', '1171', '乌克兰', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('358', '1172', '乌拉圭', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('359', '1173', '乌兹别克斯坦', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('36', '01', 'STR同型比对', NULL, 'COMPARE_MODE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('360', '1174', '西班牙', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('361', '1175', '希腊', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('362', '1176', '新加坡', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('363', '1177', '新西兰', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('364', '1178', '匈牙利', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('365', '1179', '牙买加', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('366', '1180', '亚美尼亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('367', '1181', '也门', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('368', '1182', '伊拉克', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('369', '1183', '伊朗伊斯兰共和国', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('37', '02', '亲缘三联体比对', NULL, 'COMPARE_MODE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('370', '1184', '以色列', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('371', '1185', '意大利', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('372', '1186', '印度', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('373', '1187', '印度尼西亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('374', '1188', '约旦', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('375', '1189', '越南', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('376', '1190', '赞比亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('377', '1191', '乍得', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('378', '1192', '智利', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('379', '1193', '中非共和国', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('38', '03', '亲缘单亲比对', NULL, 'COMPARE_MODE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('380', '1194', '中国', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('381', '120', '喀麦隆', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('382', '124', '加拿大', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('383', '128', '坎顿和恩德贝里群岛', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('384', '132', '佛得角', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('385', '136', '开曼群岛', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('386', '140', '中非', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('387', '144', '斯里兰卡', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('388', '148', '乍得', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('389', '152', '智利', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('39', '04', 'Y-STR比对', NULL, 'COMPARE_MODE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('390', '156', '中国', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('391', '162', '圣诞岛', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('392', '166', '可可(基林)群岛', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('393', '170', '哥伦比亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('394', '174', '科摩罗', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('395', '178', '刚果', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('396', '180', '扎伊尔', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('397', '184', '库克群岛', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('398', '188', '哥斯达黎加', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('399', '192', '古巴', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('4', '-1', '同步失败', NULL, 'GENE_SYNC_STATUS', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('40', '05', '混合STR比对', NULL, 'COMPARE_MODE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('400', '196', '塞浦路斯', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('401', '200', '捷克斯洛伐克', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('402', '204', '贝宁', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('403', '208', '丹麦', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('404', '212', '多米尼加', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('405', '214', '多米尼加共和国', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('406', '216', '毛德地', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('407', '218', '厄瓜多尔', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('408', '222', '萨尔瓦多', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('409', '226', '赤道几内亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('41', '01', '凶杀', NULL, 'CASE_PROPERTY', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('410', '230', '埃塞俄比亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('411', '234', '法罗', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('412', '238', '福克兰群岛(马尔维纳斯)', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('413', '242', '斐济', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('414', '246', '芬兰', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('415', '250', '法国', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('416', '254', '法属圭亚那', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('417', '258', '法属波利尼西亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('418', '262', '吉布提', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('419', '266', '加蓬', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('42', '02', '伤害', NULL, 'CASE_PROPERTY', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('420', '270', '冈比亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('421', '278', '德意志民主共和国', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('422', '280', '德意志联邦共和国', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('423', '288', '加纳', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('424', '292', '直布罗陀', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('425', '296', '基里巴斯', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('426', '300', '希腊', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('427', '304', '格陵兰', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('428', '308', '格林纳达', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('429', '312', '瓜德罗普', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('43', '03', '其他盗窃', NULL, 'CASE_PROPERTY', '570', 0);
INSERT INTO `nt_sys_dict_item` VALUES ('430', '316', '关岛', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('431', '320', '危地马拉', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('432', '324', '几内亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('433', '328', '圭亚那', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('434', '332', '海地', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('435', '334', '赫德岛', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('436', '336', '梵蒂酋', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('437', '340', '洪都拉斯', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('438', '348', '匈牙利', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('439', '352', '冰岛', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('44', '04', '抢劫', NULL, 'CASE_PROPERTY', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('440', '356', '印度', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('441', '360', '印度尼西亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('442', '364', '伊朗', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('443', '368', '伊拉克', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('444', '372', '爱尔兰', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('445', '374', '巴基斯坦', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('446', '378', '以色列', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('447', '380', '意大利', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('448', '384', '象牙海岸', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('449', '388', '牙买加', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('45', '05', '强奸', NULL, 'CASE_PROPERTY', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('450', '392', '日本', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('451', '396', '约斡斯顿岛', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('452', '400', '约旦', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('453', '404', '肯尼亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('454', '408', '朝鲜', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('455', '410', '韩国', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('456', '414', '科威特', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('457', '418', '老挝', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('458', '422', '黎巴嫩', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('459', '426', '莱索托', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('46', '06', '非正常死亡', NULL, 'CASE_PROPERTY', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('460', '430', '利比里亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('461', '434', '利比亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('462', '438', '列支敦士登', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('463', '442', '卢森堡', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('464', '450', '马达加斯加', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('465', '454', '马拉维', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('466', '458', '马来西亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('467', '462', '马尔代夫', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('468', '466', '马里', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('469', '470', '马耳他', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('47', '07', '伤害致死', NULL, 'CASE_PROPERTY', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('470', '474', '马提尼克', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('471', '478', '毛里塔尼亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('472', '480', '毛里求斯', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('473', '484', '墨西哥', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('474', '488', '中途岛', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('475', '492', '摩纳哥', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('476', '496', '蒙古', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('477', '500', '蒙特塞拉特', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('478', '504', '摩洛哥', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('479', '508', '莫桑比克', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('48', '08', '治安', NULL, 'CASE_PROPERTY', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('480', '512', '阿曼', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('481', '516', '纳米比亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('482', '520', '瑙鲁', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('483', '524', '尼泊尔', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('484', '528', '荷兰', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('485', '532', '荷属安的列斯', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('486', '536', '中间地带', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('487', '540', '新喀里多尼亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('488', '548', '瓦努阿图', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('489', '550', '新西兰', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('49', '09', '走失人口', NULL, 'CASE_PROPERTY', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('490', '558', '尼加拉瓜', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('491', '562', '尼日尔', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('492', '566', '尼日利亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('493', '570', '纽埃岛', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('494', '574', '诺福克岛', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('495', '578', '挪威', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('496', '582', '太平洋群岛', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('497', '586', '巴基斯坦', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('498', '590', '巴拿马', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('499', '598', '巴布亚新几内亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('5', '01', '刑事案件', NULL, 'CASE_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('50', '10', '打拐', NULL, 'CASE_PROPERTY', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('500', '600', '巴拉圭', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('501', '604', '秘鲁', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('502', '608', '菲律宾', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('503', '612', '皮特凯恩岛', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('504', '616', '波兰', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('505', '620', '葡萄牙', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('506', '624', '几内亚比绍', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('507', '626', '东帝汶', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('508', '630', '波多黎各', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('509', '634', '卡诺尔', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('51', '11', '交通事故', NULL, 'CASE_PROPERTY', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('510', '638', '留尼汪', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('511', '642', '罗马尼亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('512', '646', '卢旺达', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('513', '654', '圣赫勒拿', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('514', '658', '圣基茨一尼维斯', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('515', '660', '安圭拉', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('516', '662', '圣卢西亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('517', '666', '圣皮埃尔和密克隆', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('518', '670', '圣文森特和格林纳丁斯', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('519', '674', '圣马力诺', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('52', '12', '尸源认定', NULL, 'CASE_PROPERTY', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('520', '678', '圣多美和普林西比', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('521', '682', '沙特阿拉伯', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('522', '686', '塞内加尔', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('523', '690', '塞舌尔', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('524', '694', '塞拉利昂', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('525', '702', '新加坡', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('526', '704', '越南', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('527', '706', '索马里', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('528', '710', '南非(阿札尼亚)', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('529', '716', '津巴布韦', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('53', '13', '入室盗窃', NULL, 'CASE_PROPERTY', '570', 0);
INSERT INTO `nt_sys_dict_item` VALUES ('530', '720', '民主也门', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('531', '724', '西班牙', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('532', '732', '西撤哈拉', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('533', '736', '苏丹', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('534', '740', '苏里南', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('535', '744', '斯瓦巴德群岛', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('536', '748', '斯威士兰', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('537', '752', '瑞典', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('538', '756', '瑞士', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('539', '760', '叙利亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('54', '14', '盗窃机动车', NULL, 'CASE_PROPERTY', '570', 0);
INSERT INTO `nt_sys_dict_item` VALUES ('540', '764', '泰国', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('541', '768', '多哥', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('542', '772', '托克劳', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('543', '776', '汤加', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('544', '780', '特立尼达和多哥巴', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('545', '784', '阿拉伯联合酋长国', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('546', '788', '突尼斯', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('547', '792', '土耳其', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('548', '796', '特克斯和凯科斯群岛', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('549', '798', '图瓦卢', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('55', '15', '盗窃车内财物', NULL, 'CASE_PROPERTY', '570', 0);
INSERT INTO `nt_sys_dict_item` VALUES ('550', '800', '乌干达', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('551', '804', '乌克兰', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('552', '810', '俄罗斯', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('553', '818', '埃及', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('554', '826', '英国', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('555', '834', '坦桑尼亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('556', '840', '美国', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('557', '849', '美属太平洋群岛', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('558', '850', '美属维尔市群岛', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('559', '854', '上沃尔特', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('56', '16', '盗抢工地', NULL, 'CASE_PROPERTY', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('560', '858', '乌拉圭', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('561', '862', '委内瑞拉', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('562', '872', '威克岛', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('563', '876', '瓦利斯和富图纳群岛', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('564', '882', '西萨摩亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('565', '886', '也门', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('566', '890', '南斯拉夫', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('567', '894', '赞比亚', NULL, 'PERSON_NATION', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('568', '192', '高空坠物', NULL, 'CASE_PROPERTY', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('569', '131', '入室盗窃（划片开锁）', NULL, 'CASE_PROPERTY', '570', 0);
INSERT INTO `nt_sys_dict_item` VALUES ('57', '17', '入室盗窃（爬楼钻窗）', NULL, 'CASE_PROPERTY', '570', 0);
INSERT INTO `nt_sys_dict_item` VALUES ('570', '100', '盗窃', NULL, 'CASE_PROPERTY', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('572', '192', '高空坠物', NULL, 'CASE_PROPERTY', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('573', '312', '盗窃电缆', NULL, 'CASE_PROPERTY', '570', 0);
INSERT INTO `nt_sys_dict_item` VALUES ('574', '31', '扒窃', NULL, 'CASE_PROPERTY', '570', 0);
INSERT INTO `nt_sys_dict_item` VALUES ('575', '01', '人员血样', NULL, 'QC_SAMPLE_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('576', '01', '实验室人员', NULL, 'QC_PERSON_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('58', '18', '盗窃保险柜', NULL, 'CASE_PROPERTY', '570', 0);
INSERT INTO `nt_sys_dict_item` VALUES ('59', '19', '亲缘鉴定', NULL, 'CASE_PROPERTY', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('6', '02', '民事案件', NULL, 'CASE_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('60', '20', '其他', NULL, 'CASE_PROPERTY', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('61', '21', '诈骗', NULL, 'CASE_PROPERTY', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('62', '22', '抢夺', NULL, 'CASE_PROPERTY', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('63', '01', '嫌疑人', NULL, 'CASE_PERSON_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('64', '02', '嫌疑人亲属', NULL, 'CASE_PERSON_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('65', '03', '受害人', NULL, 'CASE_PERSON_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('66', '04', '受害人亲属', NULL, 'CASE_PERSON_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('67', '05', '失踪人员', NULL, 'CASE_PERSON_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('68', '06', '失踪人员亲属', NULL, 'CASE_PERSON_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('69', '99', '其它人员', NULL, 'CASE_PERSON_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('7', '1', 'STR', NULL, 'GENE_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('70', '1', '男', NULL, 'PERSON_GENDER', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('71', '2', '女', NULL, 'PERSON_GENDER', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('72', '3', '未知', NULL, 'PERSON_GENDER', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('73', '1', '警官证', NULL, 'CERTIFICATE_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('74', '2', '身份证', NULL, 'CERTIFICATE_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('75', '3', '士官证', NULL, 'CERTIFICATE_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('76', '4', '学生证', NULL, 'CERTIFICATE_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('77', '5', '驾驶证', NULL, 'CERTIFICATE_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('78', '6', '护照', NULL, 'CERTIFICATE_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('79', '7', '港澳通行证', NULL, 'CERTIFICATE_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('8', '2', 'Y-STR', NULL, 'GENE_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('82', 'STR-0', 'str比对分组编号', NULL, 'STR_GROUP_NO', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('83', '1', '人员-物证比中', NULL, 'GROUP_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('84', '2', '物证-物证比中', NULL, 'GROUP_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('85', '3', '人员比中', NULL, 'GROUP_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('86', 'KINSHIP-0', '亲缘比对分组编号', NULL, 'KINSHIP_GROUP_NO', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('87', '01', '父亲', NULL, 'PERSON_RELATION_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('88', '02', '母亲', NULL, 'PERSON_RELATION_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('89', '03', '丈夫', NULL, 'PERSON_RELATION_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('9', '3', '混合STR', NULL, 'GENE_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('90', '04', '妻子', NULL, 'PERSON_RELATION_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('91', '05', '兄弟', NULL, 'PERSON_RELATION_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('92', '06', '姐妹', NULL, 'PERSON_RELATION_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('93', '07', '儿子', NULL, 'PERSON_RELATION_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('94', '08', '女儿', NULL, 'PERSON_RELATION_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('95', '09', '本人', NULL, 'PERSON_RELATION_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('96', '01', 'xx', NULL, 'CRIMINAL_PERSON_TYPE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('97', '01', '汉族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('98', '02', '蒙古族', NULL, 'PERSON_RACE', NULL, 0);
INSERT INTO `nt_sys_dict_item` VALUES ('99', '03', '回族', NULL, 'PERSON_RACE', NULL, 0);
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_dict_item_type
-- ----------------------------
DROP TABLE IF EXISTS `nt_sys_dict_item_type`;
CREATE TABLE `nt_sys_dict_item_type` (
  `id` varchar(32) NOT NULL COMMENT '编号',
  `dict_type_code` varchar(32) NOT NULL,
  `dict_item_type_name` varchar(32) DEFAULT NULL COMMENT '分类名称',
  `dict_item_type_des` varchar(255) DEFAULT NULL COMMENT '分类描述',
  `dict_item_type_order` int(10) DEFAULT NULL COMMENT '排序',
  `del_status` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_dict_type_code` (`dict_type_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典分类';

-- ----------------------------
-- Records of nt_sys_dict_item_type
-- ----------------------------
BEGIN;
INSERT INTO `nt_sys_dict_item_type` VALUES ('4028e591723fd3de01723fd5ca960000', '135', '测试', NULL, NULL, '0');
INSERT INTO `nt_sys_dict_item_type` VALUES ('4028e591723fd3de01723fdbc17d0003', '1', '测试1111', 'desc', NULL, '0');
INSERT INTO `nt_sys_dict_item_type` VALUES ('CASE_PERSON_TYPE', 'CASE_PERSON_TYPE', '人员类型', NULL, NULL, '0');
INSERT INTO `nt_sys_dict_item_type` VALUES ('CASE_PROPERTY', 'CASE_PROPERTY', '案件性质', NULL, NULL, '0');
INSERT INTO `nt_sys_dict_item_type` VALUES ('CASE_TYPE', 'CASE_TYPE', '案件类型', NULL, NULL, '0');
INSERT INTO `nt_sys_dict_item_type` VALUES ('CERTIFICATE_TYPE', 'CERTIFICATE_TYPE', '证件类型', NULL, NULL, '0');
INSERT INTO `nt_sys_dict_item_type` VALUES ('COMPARE_MODE', 'COMPARE_MODE', '比对模式', NULL, NULL, '0');
INSERT INTO `nt_sys_dict_item_type` VALUES ('CRIMINAL_PERSON_TYPE', 'CRIMINAL_PERSON_TYPE', '建库人员类别', NULL, NULL, '0');
INSERT INTO `nt_sys_dict_item_type` VALUES ('DELETE_FLAG', 'DELETE_FLAG', '删除标识', NULL, NULL, '0');
INSERT INTO `nt_sys_dict_item_type` VALUES ('GENE_SYNC_STATUS', 'GENE_SYNC_STATUS', '基因同步队列状态', NULL, NULL, '0');
INSERT INTO `nt_sys_dict_item_type` VALUES ('GENE_SYNC_TYPE', 'GENE_SYNC_TYPE', '基因同步队列类型', NULL, NULL, '0');
INSERT INTO `nt_sys_dict_item_type` VALUES ('GENE_TYPE', 'GENE_TYPE', '基因类型', NULL, NULL, '0');
INSERT INTO `nt_sys_dict_item_type` VALUES ('GROUP_TYPE', 'GROUP_TYPE', '同型比对：分组类型', NULL, NULL, '0');
INSERT INTO `nt_sys_dict_item_type` VALUES ('KINSHIP_GROUP_NO', 'KINSHIP_GROUP_NO', '亲缘比对分组编号', NULL, NULL, '0');
INSERT INTO `nt_sys_dict_item_type` VALUES ('MEMBER_TYPE', 'MEMBER_TYPE', '人员类型', NULL, NULL, '0');
INSERT INTO `nt_sys_dict_item_type` VALUES ('PERSON_GENDER', 'PERSON_GENDER', '性别', NULL, NULL, '0');
INSERT INTO `nt_sys_dict_item_type` VALUES ('PERSON_NATION', 'PERSON_NATION', '国籍', NULL, NULL, '0');
INSERT INTO `nt_sys_dict_item_type` VALUES ('PERSON_RACE', 'PERSON_RACE', '民族', NULL, NULL, '0');
INSERT INTO `nt_sys_dict_item_type` VALUES ('PERSON_RELATION_TYPE', 'PERSON_RELATION_TYPE', '人员亲缘身份', NULL, NULL, '0');
INSERT INTO `nt_sys_dict_item_type` VALUES ('QC_PERSON_TYPE', 'QC_PERSON_TYPE', '质控人员类型', NULL, NULL, '0');
INSERT INTO `nt_sys_dict_item_type` VALUES ('QC_SAMPLE_TYPE', 'QC_SAMPLE_TYPE', '质控样本类型', NULL, NULL, '0');
INSERT INTO `nt_sys_dict_item_type` VALUES ('SAMPLE_TYPE', 'SAMPLE_TYPE', '样品类型', NULL, NULL, '0');
INSERT INTO `nt_sys_dict_item_type` VALUES ('STR_GROUP_NO', 'STR_GROUP_NO', 'str比对分组编号', NULL, NULL, '0');
INSERT INTO `nt_sys_dict_item_type` VALUES ('TRANSFER_NATION_FLAG', 'TRANSFER_NATION_FLAG', '入国家库状态', NULL, NULL, '0');
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_job
-- ----------------------------
DROP TABLE IF EXISTS `nt_sys_job`;
CREATE TABLE `nt_sys_job` (
  `id` varchar(255) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `job_info` varchar(32) DEFAULT NULL,
  `job_logo` varchar(32) DEFAULT NULL,
  `job_logo_url` varchar(32) DEFAULT NULL,
  `job_name` varchar(32) NOT NULL,
  `job_order` int(11) NOT NULL,
  `parent_id` varchar(32) NOT NULL,
  `status` varchar(32) NOT NULL,
  `update_time` datetime DEFAULT NULL,
  `del_status` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `fk_sys_job_parent_id` (`parent_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户职位';

-- ----------------------------
-- Records of nt_sys_job
-- ----------------------------
BEGIN;
INSERT INTO `nt_sys_job` VALUES ('1', '2020-04-24 14:31:41', NULL, NULL, NULL, '管理员', 1, '1', '1', '2020-04-24 14:32:05', 0);
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_lab
-- ----------------------------
DROP TABLE IF EXISTS `nt_sys_lab`;
CREATE TABLE `nt_sys_lab` (
  `id` int(64) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `org_id` varchar(255) NOT NULL COMMENT '单位编号',
  `identify_name` varchar(64) DEFAULT NULL COMMENT '鉴定中心全称',
  `identify_phone` varchar(32) DEFAULT NULL COMMENT '鉴定中心电话',
  `identify_address` varchar(255) DEFAULT NULL COMMENT '鉴定中心地址',
  `lab_name` varchar(64) DEFAULT NULL COMMENT 'DNA实验室名称',
  `lab_level` varchar(32) DEFAULT NULL COMMENT '实验室级别',
  `lab_user` varchar(32) DEFAULT NULL COMMENT '实验室负责人',
  `lab_phone` varchar(32) DEFAULT NULL COMMENT '实验室负责人电话',
  `server_number` varchar(32) DEFAULT NULL COMMENT '服务器编号',
  `server_ip` varchar(32) DEFAULT NULL COMMENT '服务器ip',
  `server_ip_left` varchar(32) DEFAULT NULL COMMENT '服务器ip',
  `server_ip_right` varchar(32) DEFAULT NULL COMMENT '服务器ip',
  `server_ip_addr` varchar(64) DEFAULT NULL COMMENT '授权ip范围',
  `status` varchar(8) DEFAULT NULL COMMENT '状态',
  `create_user` varchar(64) DEFAULT NULL COMMENT '账号',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_sys_lab_org_id` (`org_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='实验室管理';

-- ----------------------------
-- Records of nt_sys_lab
-- ----------------------------
BEGIN;
INSERT INTO `nt_sys_lab` VALUES (1, '110000000001', '法医中心DNA实验室', '0100101', '东城区', '北京市公安司法鉴定中心', '2', 'admin1', '121212', '110100', '192.168.1.100', '192.168.1.164', '192.168.1.220', '192.168.1.164-192.168.1.220', '1', '1', '2020-06-09 19:28:29', '2020-07-09 16:46:36');
INSERT INTO `nt_sys_lab` VALUES (2, '110101000000', '东城分局DNA实验室', NULL, '东城区', '北京市东城区公安司法鉴定中心', NULL, '123', NULL, '110101', NULL, NULL, NULL, 'null-null', NULL, '1', '2020-06-30 17:31:27', '2020-07-09 16:46:42');
INSERT INTO `nt_sys_lab` VALUES (3, '110102000000', '西城分局DNA实验室', NULL, '西城区', '北京市西城区公安司法鉴定中心', NULL, NULL, NULL, '110102', NULL, NULL, NULL, 'null-null', '1', '1', '2020-06-13 12:17:24', '2020-07-09 16:46:48');
INSERT INTO `nt_sys_lab` VALUES (4, '110105000000', '朝阳分局DNA实验室', NULL, '朝阳区', '北京市朝阳区公安司法鉴定中心', NULL, NULL, NULL, '110105', NULL, NULL, NULL, 'null-null', '1', '1', '2020-06-13 12:21:31', '2020-07-09 16:46:52');
INSERT INTO `nt_sys_lab` VALUES (5, '110106000000', '丰台分局DNA实验室', NULL, '丰台区', '北京市丰台区公安司法鉴定中心', NULL, NULL, NULL, '110106', NULL, NULL, NULL, 'null-null', '1', '1', '2020-06-13 12:22:08', '2020-07-09 16:46:57');
INSERT INTO `nt_sys_lab` VALUES (6, '110107000000', '石景山分局DNA实验室', NULL, '石景山区', '北京市石景山区公安司法鉴定中心', NULL, NULL, NULL, '110107', NULL, NULL, NULL, 'null-null', '1', '1', '2020-06-13 12:22:40', '2020-07-09 16:47:03');
INSERT INTO `nt_sys_lab` VALUES (7, '110108000000', '海淀分局DNA实验室', '', '海淀区', '北京市海淀区公安司法鉴定中心', '33', '33', '33', '110108', '33', '33', '333', '33-333', '1', '1', '2020-06-17 15:41:16', '2020-07-09 16:47:09');
INSERT INTO `nt_sys_lab` VALUES (8, '110112000000', '通州分局DNA实验室', NULL, '通州区', '北京市通州区公安司法鉴定中心', NULL, NULL, NULL, '110112', NULL, NULL, NULL, 'null-null', NULL, '1', '2020-06-30 17:31:31', '2020-07-09 16:47:15');
INSERT INTO `nt_sys_lab` VALUES (9, '110113000000', '顺义分局DNA实验室', NULL, '顺义', '北京市顺义区公安司法鉴定中心', NULL, NULL, NULL, '110113', NULL, NULL, NULL, 'null-null', NULL, '1', '2020-06-30 17:31:34', '2020-07-09 16:47:21');
INSERT INTO `nt_sys_lab` VALUES (10, '110114000000', '昌平分局DNA实验室', NULL, NULL, '北京市昌平区公安司法鉴定中心', NULL, NULL, NULL, '110114', NULL, NULL, NULL, NULL, NULL, NULL, '2020-06-30 17:31:37', '2020-07-09 16:47:27');
INSERT INTO `nt_sys_lab` VALUES (20, '110', '测试实验人员', '123', '123', '1231', '123', '12312', '123', '3123', '1231', '23', '123', '23-123', '1', '1', '2020-07-10 16:20:51', '2020-07-10 16:20:51');
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_lab_user
-- ----------------------------
DROP TABLE IF EXISTS `nt_sys_lab_user`;
CREATE TABLE `nt_sys_lab_user` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `lab_id` int(11) NOT NULL COMMENT '实验室编号',
  `nick_name` varchar(32) DEFAULT NULL COMMENT '姓名',
  `sex` int(2) DEFAULT NULL COMMENT '性别',
  `id_card` varchar(32) DEFAULT NULL COMMENT '身份证号',
  `police_card` varchar(32) DEFAULT NULL COMMENT '警号',
  `user_phone` varchar(32) DEFAULT NULL COMMENT '电话',
  `education` varchar(8) DEFAULT NULL COMMENT '学历',
  `user_school` varchar(64) DEFAULT NULL COMMENT '毕业院校',
  `user_post` varchar(64) DEFAULT NULL COMMENT '职务',
  `user_job` varchar(64) DEFAULT NULL COMMENT '职称',
  `auth_sign` varchar(64) DEFAULT NULL COMMENT '授权人签字',
  `cert_path` varchar(64) DEFAULT NULL COMMENT '资质证书',
  `system_user_name` varchar(64) DEFAULT NULL COMMENT '系统登录名',
  `system_role_name` varchar(64) DEFAULT NULL COMMENT '系统角色',
  `status` varchar(8) DEFAULT NULL COMMENT '状态',
  `create_user` varchar(64) DEFAULT NULL COMMENT '账号',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_sys_lab_user_lab_id` (`lab_id`),
  CONSTRAINT `fk_sys_lab_user_lab_id` FOREIGN KEY (`lab_id`) REFERENCES `nt_sys_lab` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='实验室用户';

-- ----------------------------
-- Records of nt_sys_lab_user
-- ----------------------------
BEGIN;
INSERT INTO `nt_sys_lab_user` VALUES ('ff80808172dae8ee0172db117a240001', 1, '战歌', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', 'admin', NULL, '1', '1', '2020-06-22 16:07:35', '2020-06-22 16:07:35');
INSERT INTO `nt_sys_lab_user` VALUES ('ff80808173047cc30173048754ce0000', 8, '张3', 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', NULL, NULL, '1', '1', '2020-06-30 17:20:45', '2020-06-30 17:20:45');
INSERT INTO `nt_sys_lab_user` VALUES ('ff80808173048dc40173048f419d0000', 8, '李4', 2, '', '', '', '', '', '', '', '', '', '', '', '1', '1', '2020-06-30 17:29:24', '2020-06-30 17:29:24');
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `nt_sys_menu`;
CREATE TABLE `nt_sys_menu` (
  `id` varchar(255) NOT NULL,
  `menu_type_id` varchar(255) DEFAULT NULL,
  `menu_action` varchar(32) NOT NULL,
  `menu_name` varchar(32) NOT NULL,
  `menu_order` int(11) NOT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `parent_id` varchar(32) NOT NULL,
  `menu_description` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `update_time` datetime DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `another_name` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_sys_menu_menu_type_id` (`menu_type_id`) USING BTREE,
  KEY `idx_sys_menu_parent_id` (`parent_id`) USING BTREE,
  CONSTRAINT `fk_sys_menu_menu_type_id` FOREIGN KEY (`menu_type_id`) REFERENCES `nt_sys_menu_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统菜单';

-- ----------------------------
-- Records of nt_sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `nt_sys_menu` VALUES ('101', '1', 'system', '系统设置', 1, 'system', '-1', '系统设置', 1, '2020-05-11 14:00:25', '2020-05-11 14:00:21', NULL);
INSERT INTO `nt_sys_menu` VALUES ('1011', '1', 'system/org', '机构管理', 11, 'org', '101', '机构管理', 1, '2020-05-11 14:03:11', '2020-05-11 14:03:06', NULL);
INSERT INTO `nt_sys_menu` VALUES ('1012', '1', 'system/role', '角色管理', 12, 'role', '101', '角色管理', 1, '2020-05-11 14:08:43', '2020-05-11 14:08:39', NULL);
INSERT INTO `nt_sys_menu` VALUES ('1013', '1', 'system/job', '用户职位管理', 13, 'job', '101', '用户职位管理', 1, '2020-05-11 14:09:59', '2020-05-11 14:09:56', NULL);
INSERT INTO `nt_sys_menu` VALUES ('1014', '1', 'system/user', '用户管理', 14, 'user', '101', '用户管理', 1, '2020-05-11 14:12:44', '2020-05-11 14:12:41', NULL);
INSERT INTO `nt_sys_menu` VALUES ('1015', '1', 'system/menu/type', '菜单类型管理', 15, 'type', '101', '菜单类型管理', 1, '2020-05-11 14:13:45', '2020-05-11 14:13:39', NULL);
INSERT INTO `nt_sys_menu` VALUES ('1016', '1', 'system/menu/operation', '菜单操作管理', 16, 'operation', '101', '菜单操作管理', 1, '2020-05-11 14:15:13', '2020-05-11 14:15:09', NULL);
INSERT INTO `nt_sys_menu` VALUES ('1017', '1', 'system/menu', '菜单管理', 17, 'menu', '101', '菜单管理', 1, '2020-05-11 14:15:42', '2020-05-11 14:15:38', NULL);
INSERT INTO `nt_sys_menu` VALUES ('301', '3', '/home', '首页', 1, 'iconshouye', '-1', '首页', 1, '2013-01-01 10:00:00', '2013-01-01 10:00:00', NULL);
INSERT INTO `nt_sys_menu` VALUES ('302', '3', 'database/data', '数据综合管理', 2, 'iconshujuzongheguanli', '-1', '数据综合管理', 1, '2013-01-01 10:00:00', '2013-01-01 10:00:00', NULL);
INSERT INTO `nt_sys_menu` VALUES ('3021', '3', '/datamanage/case', '案件数据管理', 21, NULL, '302', '案件数据管理', 1, '2013-01-01 10:00:00', '2013-01-01 10:00:00', NULL);
INSERT INTO `nt_sys_menu` VALUES ('3022', '3', '/datamanage/builddatabase', '建库数据管理', 22, NULL, '302', '建库数据管理', 1, '2020-05-11 13:28:01', '2020-05-11 13:27:27', NULL);
INSERT INTO `nt_sys_menu` VALUES ('3023', '3', '/datamanage/QualityControl', '质控数据管理', 23, NULL, '302', '质控数据管理', 1, '2020-05-11 13:30:00', '2020-05-11 13:29:28', NULL);
INSERT INTO `nt_sys_menu` VALUES ('3024', '3', '/datamanage/miss', '失踪人员', 24, NULL, '302', '失踪人员', 1, '2020-07-13 09:38:39', '2020-07-13 09:38:46', NULL);
INSERT INTO `nt_sys_menu` VALUES ('3025', '3', '/datamanage/unknown', '身份不明人员', 25, NULL, '302', '身份不明人员', 1, '2020-07-13 09:39:26', '2020-07-13 09:39:32', NULL);
INSERT INTO `nt_sys_menu` VALUES ('303', '3', 'database/info', '比中信息管理', 3, 'iconbizhongxinxiguanli', '-1', '比中信息管理', 1, '2020-05-11 13:30:32', '2020-05-11 13:30:10', NULL);
INSERT INTO `nt_sys_menu` VALUES ('3031', '3', '/thanin/homotype', '同型比中', 31, NULL, '303', '同型比中', 1, '2020-05-11 13:32:09', '2020-05-11 13:31:39', NULL);
INSERT INTO `nt_sys_menu` VALUES ('3032', '3', '/thanin/kinship', '亲缘比中', 32, NULL, '303', '亲缘比中', 1, '2020-05-11 13:33:48', '2020-05-11 13:32:21', NULL);
INSERT INTO `nt_sys_menu` VALUES ('3034', '3', '/thanin/ystr', 'Y-STR 比中', 34, NULL, '303', 'Y-STR 比中', 1, '2020-05-11 13:36:30', '2020-05-11 13:36:25', NULL);
INSERT INTO `nt_sys_menu` VALUES ('304', '3', 'database/quick', '快速比对管理', 4, 'iconkuaisubidui', '-1', '快速比对管理', 1, '2020-05-11 13:38:19', '2020-05-11 13:38:14', NULL);
INSERT INTO `nt_sys_menu` VALUES ('3041', '3', '/quickmanage/Homotype', '同型比对', 41, NULL, '304', '同型比对', 1, '2020-05-11 13:32:09', '2020-05-11 13:31:39', NULL);
INSERT INTO `nt_sys_menu` VALUES ('3042', '3', '/quickmanage/Kinship', '亲缘比对', 42, NULL, '304', '亲缘比对', 1, '2020-05-11 13:33:48', '2020-05-11 13:32:21', NULL);
INSERT INTO `nt_sys_menu` VALUES ('3043', '3', '/quickmanage/STR', '混合 STR 比对', 43, NULL, '304', '混合 STR 比对', 1, '2020-05-11 13:35:40', '2020-05-11 13:35:35', NULL);
INSERT INTO `nt_sys_menu` VALUES ('3044', '3', '/quickmanage/Y_STR', 'Y-STR 比对', 44, NULL, '304', 'Y-STR 比对', 1, '2020-05-11 13:36:30', '2020-05-11 13:36:25', NULL);
INSERT INTO `nt_sys_menu` VALUES ('305', '3', 'database/gene', '基础遗传数据管理', 5, 'iconjiyinshujuguanli', '-1', '基础遗传数据管理', 1, '2020-05-11 13:41:07', '2020-05-11 13:41:03', NULL);
INSERT INTO `nt_sys_menu` VALUES ('3051', '3', '/geneticmanage/STR', 'STR 基因座管理', 51, NULL, '305', 'STR 基因座管理', 1, '2020-05-11 13:42:14', '2020-05-11 13:42:10', NULL);
INSERT INTO `nt_sys_menu` VALUES ('3052', '3', '/geneticmanage/YSTR', 'Y-STR 基因座管理', 52, NULL, '305', 'Y-STR 基因座管理', 1, '2020-05-11 13:42:53', '2020-05-11 13:42:49', NULL);
INSERT INTO `nt_sys_menu` VALUES ('3053', '3', '/geneticmanage/Kit', '试剂盒管理', 53, NULL, '305', '试剂盒管理', 1, '2020-05-11 13:43:24', '2020-05-11 13:43:21', NULL);
INSERT INTO `nt_sys_menu` VALUES ('3054', '3', '/geneticmanage/PopulationGene', '种群基因频率管理', 54, NULL, '305', '种群基因频率管理', 1, '2020-05-11 13:44:18', '2020-05-11 13:44:15', NULL);
INSERT INTO `nt_sys_menu` VALUES ('3055', '3', '/geneticmanage/LikelihoodRatio', '似然率管理', 55, NULL, '308', '似然率管理', 1, '2020-06-09 15:58:15', '2020-06-09 15:57:55', NULL);
INSERT INTO `nt_sys_menu` VALUES ('3056', '3', '/geneticmanage/Paternity', '亲权指数管理', 56, NULL, '308', '亲权指数管理', 1, '2020-06-09 16:00:27', '2020-06-09 16:00:00', NULL);
INSERT INTO `nt_sys_menu` VALUES ('306', '3', 'database/monitor', '数据监控管理', 6, 'iconshujujiankongguanli', '-1', '数据监控管理', 1, '2020-05-11 13:46:34', '2020-05-11 13:46:28', NULL);
INSERT INTO `nt_sys_menu` VALUES ('3061', '3', '/monitoring/case', '案件上报监控', 61, NULL, '306', '案件上报监控', 1, '2020-05-11 13:47:34', '2020-05-11 13:47:31', NULL);
INSERT INTO `nt_sys_menu` VALUES ('3062', '3', '/monitoring/database', '建库上报监控', 62, NULL, '306', '建库上报监控', 1, '2020-05-11 13:48:19', '2020-05-11 13:48:15', NULL);
INSERT INTO `nt_sys_menu` VALUES ('3063', '3', '/monitoring/compare', '数据比对监控', 63, NULL, '306', '数据比对监控', 1, '2020-05-11 13:48:57', '2020-05-11 13:48:50', NULL);
INSERT INTO `nt_sys_menu` VALUES ('307', '3', 'database/system', '系统管理', 7, 'iconxitongguanli', '-1', '系统管理', 1, '2020-05-11 13:50:47', '2020-05-11 13:50:44', NULL);
INSERT INTO `nt_sys_menu` VALUES ('3071', '3', '/setting/genelocus', '入库条件设置', 71, NULL, '307', '入库条件设置', 1, '2020-05-11 13:51:28', '2020-05-11 13:51:24', NULL);
INSERT INTO `nt_sys_menu` VALUES ('3072', '3', '#', '自动比对设置', 72, NULL, '307', '自动比对设置', 1, '2020-06-07 14:17:41', '2020-06-07 11:03:26', NULL);
INSERT INTO `nt_sys_menu` VALUES ('30721', '3', '/setting/warehousing/str', 'STR比对设置', 30721, NULL, '3072', 'STR比对设置', 1, '2020-05-11 13:52:05', '2020-05-11 13:52:01', NULL);
INSERT INTO `nt_sys_menu` VALUES ('30722', '3', '/setting/warehousing/ystr', 'Y-STR比对设置', 30722, NULL, '3072', 'Y-STR比对设置', 1, '2020-05-11 13:52:43', '2020-05-11 13:52:39', NULL);
INSERT INTO `nt_sys_menu` VALUES ('30723', '3', '/setting/warehousing/mixstr', '混合STR比对设置', 30723, NULL, '3072', '混合STR比对设置', 1, '2020-05-11 13:53:18', '2020-05-11 13:53:13', NULL);
INSERT INTO `nt_sys_menu` VALUES ('3073', '3', '/setting/laboratory', '实验室管理', 73, NULL, '307', '实验室管理', 1, '2020-05-26 18:04:04', '2020-05-26 18:03:38', NULL);
INSERT INTO `nt_sys_menu` VALUES ('3074', '3', '/setting/user', '用户管理', 74, NULL, '307', '用户管理', 1, '2020-05-26 18:02:54', '2020-05-26 18:02:19', NULL);
INSERT INTO `nt_sys_menu` VALUES ('3075', '3', '/settings/authorityEdit', '权限管理', 75, NULL, '307', '权限管理', 1, '2020-05-26 18:03:29', '2020-05-26 18:03:04', NULL);
INSERT INTO `nt_sys_menu` VALUES ('3076', '3', '/setting/configuration', '数据转换配置', 76, 'iconxitongpeizhi', '307', '数据转换配置', 1, '2020-05-26 18:01:45', '2020-05-26 18:00:28', NULL);
INSERT INTO `nt_sys_menu` VALUES ('308', '3', '#', '计算工具', 5, 'iconjisuangongju', '-1', '计算工具', 1, '2020-06-24 14:25:40', '2020-06-24 14:24:44', NULL);
INSERT INTO `nt_sys_menu` VALUES ('401', '4', 'case/#', '委托受理管理', 1, NULL, '-1', '委托受理管理', 1, '2020-05-12 09:17:57', '2020-05-12 09:16:03', NULL);
INSERT INTO `nt_sys_menu` VALUES ('4011', '4', 'case/#', '待送检案件', 11, NULL, '401', '待送检案件', 1, '2020-05-12 09:20:03', '2020-05-12 09:18:49', NULL);
INSERT INTO `nt_sys_menu` VALUES ('4012', '4', 'case/#', '待送检补送案件', 12, NULL, '401', '待送检补送案件', 1, '2020-05-12 09:21:15', '2020-05-12 09:20:17', NULL);
INSERT INTO `nt_sys_menu` VALUES ('4013', '4', 'case/#', '超时未送检管理', 13, NULL, '401', '超时未送检管理', 1, '2020-05-12 09:22:46', '2020-05-12 09:21:46', NULL);
INSERT INTO `nt_sys_menu` VALUES ('4014', '4', 'case/#', '已受理案件管理', 14, NULL, '401', '已受理案件管理', 1, '2020-05-12 09:23:45', '2020-05-12 09:23:01', NULL);
INSERT INTO `nt_sys_menu` VALUES ('4015', '4', 'case/#', '已受理补送案件管理', 15, NULL, '401', '已受理补送案件管理', 1, '2020-05-12 09:25:13', '2020-05-12 09:24:39', NULL);
INSERT INTO `nt_sys_menu` VALUES ('402', '4', 'case/#', '委托转送管理', 2, NULL, '-1', '委托转送管理', 1, '2020-05-12 09:25:42', '2020-05-12 09:25:23', NULL);
INSERT INTO `nt_sys_menu` VALUES ('4021', '4', 'case/#', '转送列表', 21, NULL, '402', '转送列表', 1, '2020-05-12 09:27:42', '2020-05-12 09:27:17', NULL);
INSERT INTO `nt_sys_menu` VALUES ('403', '4', 'case/#', '会诊管理', 3, NULL, '-1', '会诊管理', 1, '2020-05-12 09:26:17', '2020-05-12 09:26:00', NULL);
INSERT INTO `nt_sys_menu` VALUES ('4031', '4', 'case/#', '会诊列表', 31, NULL, '403', '会诊列表', 1, '2020-05-12 09:28:49', '2020-05-12 09:28:32', NULL);
INSERT INTO `nt_sys_menu` VALUES ('404', '4', 'case/#', '通用查询', 4, NULL, '-1', '通用查询', 1, '2020-05-12 09:26:40', '2020-05-12 09:26:27', NULL);
INSERT INTO `nt_sys_menu` VALUES ('405', '4', 'system/message', '消息管理', 5, NULL, '-1', '消息管理', 1, '2020-05-12 10:05:40', '2020-05-12 10:05:20', NULL);
INSERT INTO `nt_sys_menu` VALUES ('4051', '4', 'system/message/list', '消息列表', 51, NULL, '405', '消息列表', 1, '2020-05-12 10:06:19', '2020-05-12 10:05:51', NULL);
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_menu_oper
-- ----------------------------
DROP TABLE IF EXISTS `nt_sys_menu_oper`;
CREATE TABLE `nt_sys_menu_oper` (
  `id` varchar(255) NOT NULL,
  `menu_id` varchar(32) NOT NULL,
  `operation_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_sys_menu_oper_menu_id` (`menu_id`) USING BTREE,
  KEY `idx_sys_menu_oper_operation_id` (`operation_id`) USING BTREE,
  CONSTRAINT `fk_sys_menu_oper_menu_id` FOREIGN KEY (`menu_id`) REFERENCES `nt_sys_menu` (`id`),
  CONSTRAINT `fk_sys_menu_oper_operation_id` FOREIGN KEY (`operation_id`) REFERENCES `nt_sys_operation` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统菜单权限';

-- ----------------------------
-- Table structure for nt_sys_menu_type
-- ----------------------------
DROP TABLE IF EXISTS `nt_sys_menu_type`;
CREATE TABLE `nt_sys_menu_type` (
  `id` varchar(255) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `type_code` varchar(32) NOT NULL,
  `type_name` varchar(32) NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统菜单类型';

-- ----------------------------
-- Records of nt_sys_menu_type
-- ----------------------------
BEGIN;
INSERT INTO `nt_sys_menu_type` VALUES ('1', '2013-01-01 10:00:00', 1, 'nt_sys', '统一用户认证', '2013-01-01 10:00:00');
INSERT INTO `nt_sys_menu_type` VALUES ('2', '2013-01-01 10:00:00', 1, 'nt_mix', '混合库', '2013-01-01 10:00:00');
INSERT INTO `nt_sys_menu_type` VALUES ('3', '2013-01-01 10:00:00', 1, 'nt_database', 'DNA数据库比对管理系统', '2013-01-01 10:00:00');
INSERT INTO `nt_sys_menu_type` VALUES ('4', '2020-05-12 09:14:12', 1, 'nt_case', '综合受理接案平台', '2020-05-12 09:14:42');
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_message
-- ----------------------------
DROP TABLE IF EXISTS `nt_sys_message`;
CREATE TABLE `nt_sys_message` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `queue_type` varchar(64) NOT NULL COMMENT '队列类型',
  `queue_key` varchar(64) NOT NULL COMMENT '队列标识',
  `message_type` varchar(32) NOT NULL COMMENT '类型',
  `message_name` varchar(64) DEFAULT NULL COMMENT '名称',
  `type_id` varchar(64) DEFAULT NULL COMMENT '字段id',
  `context` varchar(255) DEFAULT NULL COMMENT '内容',
  `send_user` varchar(32) DEFAULT NULL COMMENT '发送者',
  `receive_user` varchar(32) DEFAULT NULL COMMENT '接收者',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_sys_message_type` (`message_type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统消息';

-- ----------------------------
-- Records of nt_sys_message
-- ----------------------------
BEGIN;
INSERT INTO `nt_sys_message` VALUES ('4028e59172e009c00172e030f0140000', 'sysMessageDirect', 'sysMessageKey', 'test', '测试', NULL, '内容', 'admin', 'admin', '2020-06-23 16:00:03', '2020-06-23 16:00:03');
INSERT INTO `nt_sys_message` VALUES ('ff80808172dfeeb70172e02601310000', 'sysMessageDirect', 'sysMessageKey', 'test', '测试', NULL, '内容', 'admin', 'admin', '2020-06-23 15:47:25', '2020-06-23 15:47:25');
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_operation
-- ----------------------------
DROP TABLE IF EXISTS `nt_sys_operation`;
CREATE TABLE `nt_sys_operation` (
  `id` varchar(255) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `operation_type` varchar(32) NOT NULL,
  `operation_name` varchar(32) NOT NULL,
  `status` int(11) NOT NULL DEFAULT '0',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统操作';

-- ----------------------------
-- Records of nt_sys_operation
-- ----------------------------
BEGIN;
INSERT INTO `nt_sys_operation` VALUES ('1', '2013-01-01 10:00:00', 'select', '查询', 1, '2013-01-01 10:00:00');
INSERT INTO `nt_sys_operation` VALUES ('2', '2013-01-01 10:00:00', 'add', '添加', 1, '2013-01-01 10:00:00');
INSERT INTO `nt_sys_operation` VALUES ('3', '2013-01-01 10:00:00', 'edit', '编辑', 1, '2013-01-01 10:00:00');
INSERT INTO `nt_sys_operation` VALUES ('4', '2013-01-01 10:00:00', 'save', '保存', 1, '2013-01-01 10:00:00');
INSERT INTO `nt_sys_operation` VALUES ('5', '2013-01-01 10:00:00', 'delete', '删除', 1, '2013-01-01 10:00:00');
INSERT INTO `nt_sys_operation` VALUES ('6', '2013-01-01 10:00:00', 'imported', '导入', 1, '2013-01-01 10:00:00');
INSERT INTO `nt_sys_operation` VALUES ('7', '2013-01-01 10:00:00', 'exported', '导出', 1, '2013-01-01 10:00:00');
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_organization
-- ----------------------------
DROP TABLE IF EXISTS `nt_sys_organization`;
CREATE TABLE `nt_sys_organization` (
  `id` varchar(255) NOT NULL,
  `org_name` varchar(255) NOT NULL COMMENT '单位名称',
  `org_code` varchar(255) NOT NULL COMMENT '机构代码',
  `parent_id` varchar(32) DEFAULT NULL COMMENT '父节点',
  `org_area_code` varchar(255) DEFAULT NULL COMMENT '行政区划代码',
  `org_credit_code` varchar(255) DEFAULT NULL COMMENT '统一社会信用代码',
  `org_logo` varchar(64) DEFAULT NULL COMMENT 'Logo',
  `org_logo_url` varchar(64) DEFAULT NULL COMMENT 'Logo地址',
  `org_order` int(11) DEFAULT NULL COMMENT '排序',
  `org_type` varchar(32) DEFAULT NULL,
  `status` varchar(32) NOT NULL COMMENT '状态',
  `create_time` datetime DEFAULT NULL COMMENT '时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `business_name` varchar(32) DEFAULT NULL COMMENT '业务负责人姓名',
  `business_phone` varchar(32) DEFAULT NULL COMMENT '业务负责人电话',
  `technical_name` varchar(32) DEFAULT NULL COMMENT '技术负责人姓名',
  `technical_phone` varchar(32) DEFAULT NULL COMMENT '技术负责人电话',
  `del_status` int(11) DEFAULT NULL,
  `config_id` varchar(64) DEFAULT NULL,
  `create_user` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `fk_org_code` (`org_code`) USING BTREE,
  KEY `fk_config` (`config_id`),
  CONSTRAINT `FKlp6cmbgd5pyv4kamkn2kjm77h` FOREIGN KEY (`config_id`) REFERENCES `nt_data_source_config` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='机构管理';

-- ----------------------------
-- Records of nt_sys_organization
-- ----------------------------
BEGIN;
INSERT INTO `nt_sys_organization` VALUES ('-1', '公安部物证鉴定中心', '00000000000', '0', NULL, NULL, NULL, NULL, 0, NULL, '1', '2020-07-10 15:47:37', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `nt_sys_organization` VALUES ('1', '北京市', '110', '-1', NULL, NULL, NULL, NULL, 1, NULL, '1', '2020-07-10 12:37:38', NULL, NULL, NULL, NULL, NULL, 0, '2', NULL);
INSERT INTO `nt_sys_organization` VALUES ('110000', '北京市公安局', '110000000000', '1', NULL, NULL, NULL, NULL, 1, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `nt_sys_organization` VALUES ('110100', '北京市法医中心实验室', '110000000001', '3', NULL, NULL, NULL, NULL, 1, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `nt_sys_organization` VALUES ('110101', '北京市公安局东城分局', '110101000000', '110000', NULL, NULL, NULL, NULL, 1, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `nt_sys_organization` VALUES ('110102', '北京市公安局西城分局', '110102000000', '110000', NULL, NULL, NULL, NULL, 2, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `nt_sys_organization` VALUES ('110105', '北京市公安局朝阳分局', '110105000000', '110000', NULL, NULL, NULL, NULL, 3, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `nt_sys_organization` VALUES ('110106', '北京市公安局丰台分局', '110106000000', '110000', NULL, NULL, NULL, NULL, 4, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `nt_sys_organization` VALUES ('110107', '北京市公安局石景山分局', '110107000000', '110000', NULL, NULL, NULL, NULL, 5, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `nt_sys_organization` VALUES ('110108', '北京市公安局海淀分局', '110108000000', '110000', NULL, NULL, NULL, NULL, 6, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `nt_sys_organization` VALUES ('110109', '北京市公安局门头沟分局', '110109000000', '110000', NULL, NULL, NULL, NULL, 7, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `nt_sys_organization` VALUES ('110111', '北京市公安局房山分局', '110111000000', '110000', NULL, NULL, NULL, NULL, 8, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `nt_sys_organization` VALUES ('110112', '北京市公安局通州分局', '110112000000', '110000', NULL, NULL, NULL, NULL, 9, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `nt_sys_organization` VALUES ('110113', '北京市公安局顺义分局', '110113000000', '110000', NULL, NULL, NULL, NULL, 10, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `nt_sys_organization` VALUES ('110114', '北京市公安局昌平分局', '110114000000', '110000', NULL, NULL, NULL, NULL, 11, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `nt_sys_organization` VALUES ('110115', '北京市公安局大兴分局', '110115000000', '110000', NULL, NULL, NULL, NULL, 12, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `nt_sys_organization` VALUES ('110116', '北京市公安局怀柔分局', '110116000000', '110000', NULL, NULL, NULL, NULL, 13, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `nt_sys_organization` VALUES ('110117', '北京市公安局平谷分局', '110117000000', '110000', NULL, NULL, NULL, NULL, 14, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `nt_sys_organization` VALUES ('110228', '北京市公安局密云分局', '110228000000', '110000', NULL, NULL, NULL, NULL, 15, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `nt_sys_organization` VALUES ('110229', '北京市公安局延庆分局', '110229000000', '110000', NULL, NULL, NULL, NULL, 16, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `nt_sys_organization` VALUES ('13', '北京市公安局治安管理总队', '110000200000', '110000', NULL, NULL, NULL, NULL, 17, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `nt_sys_organization` VALUES ('14', '北京市公安局刑事侦查总队', '110000210000', '110000', NULL, NULL, NULL, NULL, 18, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `nt_sys_organization` VALUES ('15', '北京市公安局公共交通安全保卫总队', '110084000000', '110000', NULL, NULL, NULL, NULL, 19, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `nt_sys_organization` VALUES ('16', '北京市公安局内部单位保卫局', '110086000000', '110000', NULL, NULL, NULL, NULL, 20, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `nt_sys_organization` VALUES ('17', '北京市公安局经济犯罪侦查总队', '110317000000', '110000', NULL, NULL, NULL, NULL, 21, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `nt_sys_organization` VALUES ('18', '北京市公安局便衣侦查总队', '110318000000', '110000', NULL, NULL, NULL, NULL, 22, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `nt_sys_organization` VALUES ('19', '北京市公安局禁毒总队', '110319000000', '110000', NULL, NULL, NULL, NULL, 23, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `nt_sys_organization` VALUES ('2', '四川市', '440', '-1', NULL, NULL, NULL, NULL, 2, NULL, '1', '2020-07-10 15:47:22', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `nt_sys_organization` VALUES ('20', '北京市公安局公安交通管理局', '110320000000', '110000', NULL, NULL, NULL, NULL, 24, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `nt_sys_organization` VALUES ('200001', '四川汶川公安局', '44000000001', '2', NULL, NULL, NULL, NULL, NULL, NULL, '1', '2020-07-10 15:50:45', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `nt_sys_organization` VALUES ('21', '北京海关缉私局', '110000H00500', '110000', NULL, NULL, NULL, NULL, 25, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `nt_sys_organization` VALUES ('22', '北京市森林公安局', '110000S00500', '110000', NULL, NULL, NULL, NULL, 26, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `nt_sys_organization` VALUES ('3', '北京市法医中心', '111000000000', '1', NULL, NULL, NULL, NULL, 2, NULL, '1', '2020-06-12 13:12:49', NULL, NULL, NULL, NULL, NULL, 0, '2', NULL);
INSERT INTO `nt_sys_organization` VALUES ('31', '北京市公安局天安门地区分局', '110091000000', '110000', NULL, NULL, NULL, NULL, 27, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `nt_sys_organization` VALUES ('32', '北京市公安局北京西站分局', '110092000000', '110000', NULL, NULL, NULL, NULL, 28, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `nt_sys_organization` VALUES ('33', '北京市公安局经济技术开发区分局', '110095000000', '110000', NULL, NULL, NULL, NULL, 29, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
INSERT INTO `nt_sys_organization` VALUES ('34', '北京市公安局清河分局', '110097000000', '110000', NULL, NULL, NULL, NULL, 30, NULL, '1', '2020-06-11 17:33:58', NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_role
-- ----------------------------
DROP TABLE IF EXISTS `nt_sys_role`;
CREATE TABLE `nt_sys_role` (
  `id` varchar(255) NOT NULL,
  `role_code` varchar(32) DEFAULT NULL,
  `role_name` varchar(32) NOT NULL,
  `role_des` varchar(32) DEFAULT NULL,
  `status` varchar(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_sys_role_role_name` (`role_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统角色';

-- ----------------------------
-- Records of nt_sys_role
-- ----------------------------
BEGIN;
INSERT INTO `nt_sys_role` VALUES ('1', '1591608048381', '超管', '权限最大用户', '1', '2018-10-10 16:08:02', '2018-10-10 16:08:16');
INSERT INTO `nt_sys_role` VALUES ('ff80808172a150110172a159d9c40000', '1591844919746', '职员', '普通用户', '1', '2020-06-11 11:08:40', '2020-06-11 11:08:40');
INSERT INTO `nt_sys_role` VALUES ('ff80808172a624c00172a67c70c30007', '1591931072706', '警员', '一般警员', '1', '2020-06-12 11:04:33', '2020-06-12 11:04:33');
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `nt_sys_role_menu`;
CREATE TABLE `nt_sys_role_menu` (
  `id` varchar(255) NOT NULL,
  `menu_id` varchar(255) DEFAULT NULL,
  `role_id` varchar(255) NOT NULL,
  `menu_type_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_sys_role_menu_menu_id` (`menu_id`) USING BTREE,
  KEY `idx_sys_role_menu_role_id` (`role_id`) USING BTREE,
  KEY `idx_sys_role_menu_menu_type_id` (`menu_type_id`) USING BTREE,
  CONSTRAINT `fk_sys_role_menu_menu_id` FOREIGN KEY (`menu_id`) REFERENCES `nt_sys_menu` (`id`),
  CONSTRAINT `fk_sys_role_menu_menu_type_id` FOREIGN KEY (`menu_type_id`) REFERENCES `nt_sys_menu_type` (`id`),
  CONSTRAINT `fk_sys_role_menu_role_id` FOREIGN KEY (`role_id`) REFERENCES `nt_sys_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色菜单权限';

-- ----------------------------
-- Records of nt_sys_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `nt_sys_role_menu` VALUES ('11101', '101', '1', '1');
INSERT INTO `nt_sys_role_menu` VALUES ('111011', '1011', '1', '1');
INSERT INTO `nt_sys_role_menu` VALUES ('111012', '1012', '1', '1');
INSERT INTO `nt_sys_role_menu` VALUES ('111013', '1013', '1', '1');
INSERT INTO `nt_sys_role_menu` VALUES ('111014', '1014', '1', '1');
INSERT INTO `nt_sys_role_menu` VALUES ('111015', '1015', '1', '1');
INSERT INTO `nt_sys_role_menu` VALUES ('111016', '1016', '1', '1');
INSERT INTO `nt_sys_role_menu` VALUES ('111017', '1017', '1', '1');
INSERT INTO `nt_sys_role_menu` VALUES ('13301', '301', '1', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('13302', '302', '1', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('133021', '3021', '1', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('133022', '3022', '1', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('133023', '3023', '1', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('133024', '3024', '1', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('133025', '3025', '1', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('13303', '303', '1', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('133031', '3031', '1', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('133032', '3032', '1', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('133034', '3034', '1', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('13304', '304', '1', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('133041', '3041', '1', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('133042', '3042', '1', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('133043', '3043', '1', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('133044', '3044', '1', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('13305', '305', '1', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('133051', '3051', '1', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('133052', '3052', '1', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('133053', '3053', '1', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('133054', '3054', '1', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('133055', '3055', '1', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('133056', '3056', '1', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('13306', '306', '1', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('133061', '3061', '1', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('133062', '3062', '1', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('133063', '3063', '1', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('13307', '307', '1', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('133071', '3071', '1', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('133072', '3072', '1', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('1330721', '30721', '1', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('1330722', '30722', '1', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('1330723', '30723', '1', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('133073', '3073', '1', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('133074', '3074', '1', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('133075', '3075', '1', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('133076', '3076', '1', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('13308', '308', '1', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('14401', '401', '1', '4');
INSERT INTO `nt_sys_role_menu` VALUES ('144011', '4011', '1', '4');
INSERT INTO `nt_sys_role_menu` VALUES ('144012', '4012', '1', '4');
INSERT INTO `nt_sys_role_menu` VALUES ('144013', '4013', '1', '4');
INSERT INTO `nt_sys_role_menu` VALUES ('144014', '4014', '1', '4');
INSERT INTO `nt_sys_role_menu` VALUES ('144015', '4015', '1', '4');
INSERT INTO `nt_sys_role_menu` VALUES ('14402', '402', '1', '4');
INSERT INTO `nt_sys_role_menu` VALUES ('144021', '4021', '1', '4');
INSERT INTO `nt_sys_role_menu` VALUES ('14403', '403', '1', '4');
INSERT INTO `nt_sys_role_menu` VALUES ('144031', '4031', '1', '4');
INSERT INTO `nt_sys_role_menu` VALUES ('14404', '404', '1', '4');
INSERT INTO `nt_sys_role_menu` VALUES ('14405', '405', '1', '4');
INSERT INTO `nt_sys_role_menu` VALUES ('144051', '4051', '1', '4');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a150110172a159d9c400003302', '302', 'ff80808172a150110172a159d9c40000', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a150110172a159d9c4000033021', '3021', 'ff80808172a150110172a159d9c40000', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a150110172a159d9c4000033022', '3022', 'ff80808172a150110172a159d9c40000', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a150110172a159d9c4000033023', '3023', 'ff80808172a150110172a159d9c40000', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a150110172a159d9c400003303', '303', 'ff80808172a150110172a159d9c40000', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a150110172a159d9c4000033031', '3031', 'ff80808172a150110172a159d9c40000', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a150110172a159d9c4000033032', '3032', 'ff80808172a150110172a159d9c40000', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a150110172a159d9c4000033034', '3034', 'ff80808172a150110172a159d9c40000', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a150110172a159d9c400003304', '304', 'ff80808172a150110172a159d9c40000', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a150110172a159d9c4000033041', '3041', 'ff80808172a150110172a159d9c40000', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a150110172a159d9c4000033042', '3042', 'ff80808172a150110172a159d9c40000', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a150110172a159d9c4000033043', '3043', 'ff80808172a150110172a159d9c40000', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a150110172a159d9c4000033044', '3044', 'ff80808172a150110172a159d9c40000', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a150110172a159d9c400003305', '305', 'ff80808172a150110172a159d9c40000', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a150110172a159d9c4000033051', '3051', 'ff80808172a150110172a159d9c40000', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a150110172a159d9c4000033052', '3052', 'ff80808172a150110172a159d9c40000', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a150110172a159d9c4000033053', '3053', 'ff80808172a150110172a159d9c40000', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a150110172a159d9c4000033054', '3054', 'ff80808172a150110172a159d9c40000', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a150110172a159d9c4000033055', '3055', 'ff80808172a150110172a159d9c40000', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a150110172a159d9c4000033056', '3056', 'ff80808172a150110172a159d9c40000', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a150110172a159d9c400003306', '306', 'ff80808172a150110172a159d9c40000', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a150110172a159d9c4000033061', '3061', 'ff80808172a150110172a159d9c40000', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a150110172a159d9c4000033062', '3062', 'ff80808172a150110172a159d9c40000', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a150110172a159d9c4000033063', '3063', 'ff80808172a150110172a159d9c40000', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a150110172a159d9c400003307', '307', 'ff80808172a150110172a159d9c40000', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a150110172a159d9c4000033071', '3071', 'ff80808172a150110172a159d9c40000', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a150110172a159d9c4000033072', '3072', 'ff80808172a150110172a159d9c40000', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a150110172a159d9c40000330721', '30721', 'ff80808172a150110172a159d9c40000', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a150110172a159d9c40000330722', '30722', 'ff80808172a150110172a159d9c40000', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a150110172a159d9c40000330723', '30723', 'ff80808172a150110172a159d9c40000', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a624c00172a67c70c300073302', '302', 'ff80808172a624c00172a67c70c30007', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a624c00172a67c70c3000733021', '3021', 'ff80808172a624c00172a67c70c30007', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a624c00172a67c70c300073303', '303', 'ff80808172a624c00172a67c70c30007', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a624c00172a67c70c3000733031', '3031', 'ff80808172a624c00172a67c70c30007', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a624c00172a67c70c3000733032', '3032', 'ff80808172a624c00172a67c70c30007', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a624c00172a67c70c3000733034', '3034', 'ff80808172a624c00172a67c70c30007', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a624c00172a67c70c300073304', '304', 'ff80808172a624c00172a67c70c30007', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a624c00172a67c70c3000733041', '3041', 'ff80808172a624c00172a67c70c30007', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a624c00172a67c70c3000733042', '3042', 'ff80808172a624c00172a67c70c30007', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a624c00172a67c70c3000733043', '3043', 'ff80808172a624c00172a67c70c30007', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a624c00172a67c70c3000733044', '3044', 'ff80808172a624c00172a67c70c30007', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a624c00172a67c70c300073306', '306', 'ff80808172a624c00172a67c70c30007', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a624c00172a67c70c3000733061', '3061', 'ff80808172a624c00172a67c70c30007', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a624c00172a67c70c3000733062', '3062', 'ff80808172a624c00172a67c70c30007', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a624c00172a67c70c3000733063', '3063', 'ff80808172a624c00172a67c70c30007', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a624c00172a67c70c300073307', '307', 'ff80808172a624c00172a67c70c30007', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a624c00172a67c70c3000733072', '3072', 'ff80808172a624c00172a67c70c30007', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a624c00172a67c70c30007330722', '30722', 'ff80808172a624c00172a67c70c30007', '3');
INSERT INTO `nt_sys_role_menu` VALUES ('ff80808172a624c00172a67c70c30007330723', '30723', 'ff80808172a624c00172a67c70c30007', '3');
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_role_menu_oper
-- ----------------------------
DROP TABLE IF EXISTS `nt_sys_role_menu_oper`;
CREATE TABLE `nt_sys_role_menu_oper` (
  `id` varchar(255) NOT NULL,
  `menu_id` varchar(255) NOT NULL,
  `operation_id` varchar(255) NOT NULL,
  `role_id` varchar(255) NOT NULL,
  `menu_type_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_role_menu_oper_menu_id` (`menu_id`) USING BTREE,
  KEY `idx_role_menu_oper_operation_id` (`operation_id`) USING BTREE,
  KEY `idx_role_menu_oper_role_id` (`role_id`) USING BTREE,
  KEY `idx_role_menu_oper_menu_type_id` (`menu_type_id`) USING BTREE,
  CONSTRAINT `fk_role_menu_oper_menu_id` FOREIGN KEY (`menu_id`) REFERENCES `nt_sys_menu` (`id`),
  CONSTRAINT `fk_role_menu_oper_menu_type_id` FOREIGN KEY (`menu_type_id`) REFERENCES `nt_sys_menu_type` (`id`),
  CONSTRAINT `fk_role_menu_oper_operation_id` FOREIGN KEY (`operation_id`) REFERENCES `nt_sys_menu_oper` (`id`),
  CONSTRAINT `fk_role_menu_oper_role_id` FOREIGN KEY (`role_id`) REFERENCES `nt_sys_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色操作权限';

-- ----------------------------
-- Table structure for nt_sys_sequence
-- ----------------------------
DROP TABLE IF EXISTS `nt_sys_sequence`;
CREATE TABLE `nt_sys_sequence` (
  `id` varchar(32) NOT NULL COMMENT '编号',
  `table_name` varchar(32) DEFAULT NULL COMMENT '表名称',
  `sequence` int(11) DEFAULT NULL COMMENT '序列',
  `status` varchar(8) DEFAULT NULL COMMENT '状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='序列';

-- ----------------------------
-- Table structure for nt_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `nt_sys_user`;
CREATE TABLE `nt_sys_user` (
  `id` varchar(255) NOT NULL COMMENT '主键id',
  `user_name` varchar(32) NOT NULL COMMENT '账号',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `real_name` varchar(32) DEFAULT NULL COMMENT '姓名',
  `email` varchar(32) DEFAULT NULL COMMENT '邮箱',
  `id_card` varchar(32) DEFAULT NULL COMMENT '证件号码',
  `last_ip` varchar(32) DEFAULT NULL,
  `last_time` datetime DEFAULT NULL,
  `phone` varchar(32) DEFAULT NULL COMMENT '电话',
  `user_code` varchar(32) DEFAULT NULL,
  `user_type` varchar(2) DEFAULT NULL,
  `id_type` varchar(8) DEFAULT NULL COMMENT '证件类型',
  `remark` varchar(255) DEFAULT NULL COMMENT '描述',
  `status` varchar(10) DEFAULT NULL COMMENT '状态1有效, 0无效',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `default_password` varchar(32) DEFAULT NULL,
  `is_admin` varchar(8) DEFAULT NULL COMMENT '是否为管理员',
  `active_status` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_sys_user_user_name_password` (`user_name`,`password`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='登录用户';

-- ----------------------------
-- Records of nt_sys_user
-- ----------------------------
BEGIN;
INSERT INTO `nt_sys_user` VALUES ('1', 'admin', 'c4ca4238a0b923820dcc509a6f75849b', '超管', 'admin@qq.com', '110103197707250933', '', '2018-10-09 14:21:01', '13800138000', '', '1', '73', '123', '1', '2018-10-09 14:19:44', '123456', '1', '1');
INSERT INTO `nt_sys_user` VALUES ('ff80808172a7b2ce0172bacaf5b10006', '四川省厅', 'b2576d1bf55b5c4db837bcdd1410a06e', '四川省厅', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', '2020-06-16 09:42:43', NULL, NULL, '1');
INSERT INTO `nt_sys_user` VALUES ('ff80808172a7b2ce0172bacc3b290008', '成都市局', 'b2576d1bf55b5c4db837bcdd1410a06e', '成都市局', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '1', '2020-06-16 09:44:06', NULL, NULL, '1');
INSERT INTO `nt_sys_user` VALUES ('ff80808173380f920173381270bd0000', 'bj', 'c4ca4238a0b923820dcc509a6f75849b', '北京市', '123', '12', '', NULL, '3123', '', '', '73', '1', '1', '2020-07-10 17:33:19', '', '', '1');
INSERT INTO `nt_sys_user` VALUES ('ff80808173380f92017338130e5e0003', 'dc', 'c4ca4238a0b923820dcc509a6f75849b', '东城区', '123', '1', '', NULL, '123', '', '', '74', '123', '1', '2020-07-10 17:34:00', '', '', '1');
INSERT INTO `nt_sys_user` VALUES ('ff80808173380f92017338138c300006', 'sc', 'c4ca4238a0b923820dcc509a6f75849b', '四川省', '123', '12', '', NULL, '312', '', '', '73', '123', '1', '2020-07-10 17:34:32', '', '', '1');
INSERT INTO `nt_sys_user` VALUES ('ff8080817347550c01734758d8a90000', 'xf', 'c4ca4238a0b923820dcc509a6f75849b', '肖飞', NULL, NULL, NULL, NULL, '15111111111', NULL, NULL, NULL, NULL, '1', '2020-07-13 16:44:32', NULL, NULL, '1');
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_user_org_job
-- ----------------------------
DROP TABLE IF EXISTS `nt_sys_user_org_job`;
CREATE TABLE `nt_sys_user_org_job` (
  `id` varchar(255) NOT NULL,
  `position_order` int(11) NOT NULL,
  `status` varchar(32) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `job_id` varchar(255) DEFAULT NULL,
  `org_id` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_sys_user_org_job_job_id` (`job_id`) USING BTREE,
  KEY `idx_sys_user_org_org_id` (`org_id`) USING BTREE,
  KEY `idx_sys_user_org_user_id` (`user_id`) USING BTREE,
  CONSTRAINT `fk_sys_user_org_job_job_id` FOREIGN KEY (`job_id`) REFERENCES `nt_sys_job` (`id`),
  CONSTRAINT `fk_sys_user_org_org_id` FOREIGN KEY (`org_id`) REFERENCES `nt_sys_organization` (`id`),
  CONSTRAINT `fk_sys_user_org_user_id` FOREIGN KEY (`user_id`) REFERENCES `nt_sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户职位权限';

-- ----------------------------
-- Records of nt_sys_user_org_job
-- ----------------------------
BEGIN;
INSERT INTO `nt_sys_user_org_job` VALUES ('1', 1, '1', '2020-07-07 14:48:38', '2020-07-07 14:48:41', '1', '-1', '1');
INSERT INTO `nt_sys_user_org_job` VALUES ('ff80808173380f920173381271140002', 1, '1', '2020-07-10 17:33:20', '2020-07-10 17:33:20', '1', '1', 'ff80808173380f920173381270bd0000');
INSERT INTO `nt_sys_user_org_job` VALUES ('ff80808173380f92017338130e950005', 1, '1', '2020-07-10 17:34:00', '2020-07-10 17:34:00', '1', '110101', 'ff80808173380f92017338130e5e0003');
INSERT INTO `nt_sys_user_org_job` VALUES ('ff80808173380f92017338138c6a0008', 1, '1', '2020-07-10 17:34:32', '2020-07-10 17:34:32', '1', '2', 'ff80808173380f92017338138c300006');
INSERT INTO `nt_sys_user_org_job` VALUES ('ff8080817347550c01734758d9520002', 1, '1', '2020-07-13 16:44:32', '2020-07-13 16:44:32', '1', '110100', 'ff8080817347550c01734758d8a90000');
COMMIT;

-- ----------------------------
-- Table structure for nt_sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `nt_sys_user_role`;
CREATE TABLE `nt_sys_user_role` (
  `id` varchar(255) NOT NULL,
  `role_id` varchar(255) NOT NULL,
  `user_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_sys_user_role_role_id` (`role_id`) USING BTREE,
  KEY `idx_sys_user_role_user_id` (`user_id`) USING BTREE,
  CONSTRAINT `fk_sys_user_role_role_id` FOREIGN KEY (`role_id`) REFERENCES `nt_sys_role` (`id`),
  CONSTRAINT `fk_sys_user_role_user_id` FOREIGN KEY (`user_id`) REFERENCES `nt_sys_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色权限';

-- ----------------------------
-- Records of nt_sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `nt_sys_user_role` VALUES ('ff80808172a7b2ce0172bacaf6370007', '1', 'ff80808172a7b2ce0172bacaf5b10006');
INSERT INTO `nt_sys_user_role` VALUES ('ff80808172e4a55d0172e4bc9c6c0000', '1', '1');
INSERT INTO `nt_sys_user_role` VALUES ('ff80808172e4a55d0172e4bc9ca40001', 'ff80808172a150110172a159d9c40000', '1');
INSERT INTO `nt_sys_user_role` VALUES ('ff80808173380f920173381270f00001', 'ff80808172a150110172a159d9c40000', 'ff80808173380f920173381270bd0000');
INSERT INTO `nt_sys_user_role` VALUES ('ff80808173380f92017338130e750004', 'ff80808172a624c00172a67c70c30007', 'ff80808173380f92017338130e5e0003');
INSERT INTO `nt_sys_user_role` VALUES ('ff80808173380f92017338138c480007', 'ff80808172a150110172a159d9c40000', 'ff80808173380f92017338138c300006');
INSERT INTO `nt_sys_user_role` VALUES ('ff8080817347550c01734758d9200001', '1', 'ff8080817347550c01734758d8a90000');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
