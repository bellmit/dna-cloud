/*
 Navicat MySQL Data Transfer

 Source Server         : 39.100.79.169
 Source Server Type    : MySQL
 Source Server Version : 50729
 Source Host           : 39.100.79.169:3316
 Source Schema         : dnamix

 Target Server Type    : MySQL
 Target Server Version : 50729
 File Encoding         : 65001

 Date: 14/07/2020 18:06:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for compare_queue
-- ----------------------------
DROP TABLE IF EXISTS `compare_queue`;
CREATE TABLE `compare_queue` (
  `ID` varchar(64) NOT NULL COMMENT '主键id',
  `MIXED_SAMPLE_ID` varchar(64) DEFAULT NULL COMMENT '混合样本id',
  `SAMPLE_NO` varchar(64) DEFAULT NULL COMMENT '样本编号',
  `QUEUE_TYPE` varchar(8) DEFAULT NULL COMMENT '队列类型（1:混合，2:拆分）',
  `STATUS` varchar(8) DEFAULT NULL COMMENT '队列状态(1.比对2.未比对3:入库比)',
  `CREATE_PERSON` varchar(64) DEFAULT NULL COMMENT '创建人',
  `CREATE_DATETIME` timestamp(6) NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  `UPDATE_PERESON` varchar(64) DEFAULT NULL COMMENT '更新人',
  `UPDATE_DATETIME` timestamp(6) NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '更新时间',
  `MIXSAME_COUNT` varchar(8) DEFAULT NULL COMMENT '匹配下线',
  `SEX` varchar(255) DEFAULT NULL COMMENT '比对人员性别（1.男  2 女）',
  `CONDITION` varchar(8) DEFAULT NULL COMMENT '容差',
  `PERSON_TYPE` varchar(4000) DEFAULT NULL COMMENT '比对人员类型编号',
  `DISTRICT` varchar(4000) DEFAULT NULL COMMENT '比对地区编号',
  `QUEUE_FLAG` varchar(8) DEFAULT NULL COMMENT '是否比对(0:否,1:是)',
  `TARGET_COUNT` varchar(8) DEFAULT NULL COMMENT '比对进度',
  `SOURCE` varchar(8) DEFAULT NULL COMMENT '来源(1:拆分，2：STR导入)',
  `PERSON_TYPE_NAME` varchar(4000) DEFAULT NULL COMMENT '比对人员类型名称',
  `DISTRICT_NAME` varchar(4000) DEFAULT NULL COMMENT '比对地区编号名称',
  `SERVE_NO` varchar(4000) NOT NULL DEFAULT '0' COMMENT '匹配服务器编号',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='比对队列信息表';

-- ----------------------------
-- Records of compare_queue
-- ----------------------------
BEGIN;
INSERT INTO `compare_queue` VALUES ('3deddae9-adfe-4e38-857b-0d6b6d98fd9c', 'f1c0bf32-e0b5-4764-8fa5-304f92f1077a', 'GZ200003-11', '1', '1', '1', '2020-07-13 17:17:20.300000', '1', '2020-07-13 17:25:02.852000', '10', '[\"1\",\"2\"]', '2', '[\"010109\"]', '[\"620000\",\"620100\",\"620200\",\"620300\",\"620400\",\"620500\",\"620600\",\"620700\",\"620800\",\"620900\",\"621000\",\"621100\",\"621200\",\"622900\",\"623000\"]', '1', '100%', '1', '其他建库人员', '甘肃省,兰州市,嘉峪关市,金昌市,白银市,天水市,武威市,张掖市,平凉市,酒泉市,庆阳市,定西市,陇南市,临夏回族自治州,甘南藏族自治州', '620000');
INSERT INTO `compare_queue` VALUES ('42bbe606-b3d1-45db-ab87-4fa7348dfdcb', '42412447-f8d3-4dfa-988a-0faf5e44f47c', 'SG-2020-247', '2', '1', '1', '2020-07-13 17:18:39.110000', NULL, '2020-07-13 17:36:21.750000', '5', '[\"1\",\"2\"]', '5', '[\"020101\",\"020102\",\"020103\",\"010201\",\"010202\"]', '[\"620000\",\"620100\",\"620200\",\"620300\",\"620400\",\"620500\",\"620600\",\"620700\",\"620800\",\"620900\",\"621000\",\"621100\",\"621200\",\"622900\",\"623000\"]', '1', '100%', '2', '现场物品,身份不明个体,嫌疑父亲,现场检材,强奸致孕者', '甘肃省,兰州市,嘉峪关市,金昌市,白银市,天水市,武威市,张掖市,平凉市,酒泉市,庆阳市,定西市,陇南市,临夏回族自治州,甘南藏族自治州', '620000');
INSERT INTO `compare_queue` VALUES ('52881188-bc09-4973-846e-b0315687b7f5', '8a32811d23b610f60124c714666f2afd', 'W6200000002009110600011', '2', '1', '1', '2020-07-13 17:19:33.985000', NULL, '2020-07-13 17:21:41.216000', '5', '[\"1\",\"2\"]', '5', '[\"030101\",\"030102\",\"030103\",\"030104\",\"030106\",\"030107\",\"030108\",\"030109\",\"060101\",\"060102\",\"060103\",\"060105\",\"060106\"]', '[\"110000\",\"110100\",\"620000\",\"620100\",\"620200\",\"620300\",\"620400\",\"620500\",\"620600\",\"620700\",\"620800\",\"620900\",\"621000\",\"621100\",\"621200\",\"622900\",\"623000\"]', '1', '100%', '1', '现场勘查人员,实验室检验人员,刑侦专家,试剂耗材生产人员,试剂耗材研发人员,试剂耗材质检人员,试剂耗材保洁人员,试剂耗材保卫人员,人员,物证,失踪人员,身份不明个体,其他', '北京市,北京市市辖,甘肃省,兰州市,嘉峪关市,金昌市,白银市,天水市,武威市,张掖市,平凉市,酒泉市,庆阳市,定西市,陇南市,临夏回族自治州,甘南藏族自治州', '110000,620000');
INSERT INTO `compare_queue` VALUES ('7d2ac29b-b508-4f4d-8a75-61d544b95684', '8a32811d23b610f60124c714666f2afd', 'W6200000002009110600011', '1', '1', '1', '2020-07-13 17:19:36.905000', NULL, '2020-07-13 17:21:40.052000', '5', '[\"1\",\"2\"]', '5', '[\"030101\",\"030102\",\"030103\",\"030104\",\"030106\",\"030107\",\"030108\",\"030109\",\"060101\",\"060102\",\"060103\",\"060105\",\"060106\"]', '[\"110000\",\"110100\",\"620000\",\"620100\",\"620200\",\"620300\",\"620400\",\"620500\",\"620600\",\"620700\",\"620800\",\"620900\",\"621000\",\"621100\",\"621200\",\"622900\",\"623000\"]', '1', '100%', '1', '现场勘查人员,实验室检验人员,刑侦专家,试剂耗材生产人员,试剂耗材研发人员,试剂耗材质检人员,试剂耗材保洁人员,试剂耗材保卫人员,人员,物证,失踪人员,身份不明个体,其他', '北京市,北京市市辖,甘肃省,兰州市,嘉峪关市,金昌市,白银市,天水市,武威市,张掖市,平凉市,酒泉市,庆阳市,定西市,陇南市,临夏回族自治州,甘南藏族自治州', '110000,620000');
INSERT INTO `compare_queue` VALUES ('8be01d30-5aef-4964-80c4-0feb53f84fa6', 'e33043d7-fe2b-4b35-9529-e00e4b82242e', 'SY190132-01', '1', '2', '1', '2020-07-13 17:16:11.902000', NULL, NULL, '5', '[\"1\",\"2\"]', '2', '[\"020101\",\"020102\",\"020103\",\"010201\",\"010202\"]', '[\"110000\",\"110100\"]', '0', '0%', '1', '现场物品,身份不明个体,嫌疑父亲,现场检材,强奸致孕者', '北京市,北京市市辖', '110000');
INSERT INTO `compare_queue` VALUES ('d2a25a15-8e70-485d-8514-6e0b582d5422', 'a6b4377a-2ab7-433e-9e08-358b4e02a0f1', 'SY20WZ0379-01', '1', '4', '1', '2020-07-14 15:54:05.985000', '1', '2020-07-14 16:28:14.562000', '16', '[\"1\",\"2\"]', '2', '[\"010109\"]', '[\"620000\",\"620100\",\"620200\",\"620300\",\"620400\",\"620500\",\"620600\",\"620700\",\"620800\",\"620900\",\"621000\",\"621100\",\"621200\",\"622900\",\"623000\"]', '1', '55%', '1', '其他建库人员', '甘肃省,兰州市,嘉峪关市,金昌市,白银市,天水市,武威市,张掖市,平凉市,酒泉市,庆阳市,定西市,陇南市,临夏回族自治州,甘南藏族自治州', '620000');
INSERT INTO `compare_queue` VALUES ('d637339a-11d9-45fc-94dc-7258d7eb5c20', '0b595fc3-1468-4dca-96bc-62645b463b9f', 'GZ200003-11', '1', '1', '1', '2020-07-13 17:18:11.477000', '1', '2020-07-13 17:47:04.895000', '5', '[\"1\",\"2\"]', '5', '[\"010301\",\"010302\",\"010303\",\"010304\",\"010305\",\"010306\",\"010307\",\"010308\",\"010309\",\"010310\",\"010311\"]', '[\"110000\",\"110100\"]', '1', '100%', '1', '疑似被侵害失踪人员,失踪儿童,疑似灾难失踪人员,负案在逃人员,疑似走失人员,其他失踪人员,失踪儿童父亲,失踪儿童母亲,失踪儿童采血申请人,失踪儿童用品,失踪儿童同胞', '北京市,北京市市辖', '110000');
INSERT INTO `compare_queue` VALUES ('e7297f59-8be1-4b9b-a792-2317089618a8', '3055bfd6-c995-4909-ab84-df493b99ca67', 'SY200345-01', '1', '4', '1', '2020-07-14 15:53:13.834000', '1', '2020-07-14 18:06:10.542000', '16', '[\"1\",\"2\"]', '2', '[\"010102\",\"010109\"]', '[\"110000\",\"110100\",\"620000\",\"620100\",\"620200\",\"620300\",\"620400\",\"620500\",\"620600\",\"620700\",\"620800\",\"620900\",\"621000\",\"621100\",\"621200\",\"622900\",\"623000\"]', '1', '3%', '1', '违法犯罪人员,其他建库人员', '北京市,北京市市辖,甘肃省,兰州市,嘉峪关市,金昌市,白银市,天水市,武威市,张掖市,平凉市,酒泉市,庆阳市,定西市,陇南市,临夏回族自治州,甘南藏族自治州', '110000,620000');
COMMIT;

-- ----------------------------
-- Table structure for contributor_info
-- ----------------------------
DROP TABLE IF EXISTS `contributor_info`;
CREATE TABLE `contributor_info` (
  `ID` varchar(64) NOT NULL COMMENT '主键id',
  `MIXED_SAMPLE_GENE_ID` varchar(64) DEFAULT NULL COMMENT '混合样本id',
  `SAMPLE_NO` varchar(64) DEFAULT NULL COMMENT '样本编号',
  `SAMPLE_NAME` varchar(64) DEFAULT NULL COMMENT '样本名称',
  `GENE_INFO` json DEFAULT NULL COMMENT '基因信息',
  `GENE_PICTURE` varchar(1000) DEFAULT NULL COMMENT '基因图谱',
  `GENE_COUNT` varchar(255) DEFAULT NULL COMMENT '位点个数',
  `CREATE_PERSON` varchar(64) DEFAULT NULL COMMENT '创建人',
  `CREATE_DATETIME` timestamp(6) NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  `UPDATE_PERESON` varchar(64) DEFAULT NULL COMMENT '更新人',
  `UPDATE_DATETIME` timestamp(6) NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '更新时间',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='贡献者信息表';

-- ----------------------------
-- Table structure for contributor_possibility
-- ----------------------------
DROP TABLE IF EXISTS `contributor_possibility`;
CREATE TABLE `contributor_possibility` (
  `ID` varchar(64) CHARACTER SET latin1 NOT NULL COMMENT '主键id',
  `COMPARE_ID` varchar(64) CHARACTER SET latin1 DEFAULT NULL COMMENT '比对id',
  `GENE_INFO` json DEFAULT NULL COMMENT '拆分贡献者可能比中基因',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='贡献者可能比中表';

-- ----------------------------
-- Table structure for dict_count
-- ----------------------------
DROP TABLE IF EXISTS `dict_count`;
CREATE TABLE `dict_count` (
  `ID` varchar(64) NOT NULL COMMENT '主键id',
  `DICT_TYPE_CODE` varchar(64) DEFAULT NULL COMMENT '字典项类型 ',
  `DICT_COUNT_NUMBER` varchar(64) DEFAULT NULL COMMENT '字典项统计数据',
  `YEARS` varchar(64) DEFAULT NULL COMMENT '年份',
  `MONTHS` varchar(64) DEFAULT NULL COMMENT '月',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of dict_count
-- ----------------------------
BEGIN;
INSERT INTO `dict_count` VALUES ('3ecc2c37-32a6-4a62-a085-29c0a3b27a52', 'SAMPLESINGCOUNT', '19741', '2020', '4');
INSERT INTO `dict_count` VALUES ('7412756b-df2c-4d9a-9b11-797cdfa2bc9f', 'SAMPLEMIXCOUNT', '1076', '2020', '7');
INSERT INTO `dict_count` VALUES ('7d8c4406-44ef-4d32-bd4e-c832e01f6efa', 'SAMPLESINGCOUNT', '262503', '2020', '5');
INSERT INTO `dict_count` VALUES ('7e8692e5-28d2-4f87-98fc-4fa2209fac57', 'SAMPLEMIXCOUNT', '49', '2020', '4');
INSERT INTO `dict_count` VALUES ('db4b388c-811c-4f22-a1c0-aba5077200c5', 'SAMPLESINGCOUNT', '4932192', '2020', '6');
INSERT INTO `dict_count` VALUES ('ea565b75-805c-4745-8e40-935a0fc573a8', 'SAMPLEMIXCOUNT', '1076', '2020', '6');
INSERT INTO `dict_count` VALUES ('f6582739-d422-4326-993f-bf39851f5c60', 'SAMPLEMIXCOUNT', '49', '2020', '5');
INSERT INTO `dict_count` VALUES ('fe7e0bc3-a149-45ed-a93e-3645962aa95f', 'SAMPLESINGCOUNT', '4932192', '2020', '7');
COMMIT;

-- ----------------------------
-- Table structure for dict_info
-- ----------------------------
DROP TABLE IF EXISTS `dict_info`;
CREATE TABLE `dict_info` (
  `DICT_INFO_ID` varchar(64) NOT NULL COMMENT '字典信息主键ID',
  `DICT_TYPE_CODE` varchar(64) DEFAULT NULL COMMENT '字典项类型',
  `DICT_TYPE_NAME` varchar(64) DEFAULT NULL COMMENT '字典项名称',
  `CREATE_DATETIME` timestamp(6) NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  `CREATE_PERSON` varchar(64) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`DICT_INFO_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for dict_item
-- ----------------------------
DROP TABLE IF EXISTS `dict_item`;
CREATE TABLE `dict_item` (
  `DICT_ITEM_ID` varchar(64) NOT NULL COMMENT '字典信息主键id',
  `DICT_INFO_ID` varchar(64) DEFAULT NULL COMMENT '字典类型主键id',
  `DICT_TYPE_CODE` varchar(64) DEFAULT NULL COMMENT '字典类型编号',
  `DICT_CODE` varchar(64) DEFAULT NULL COMMENT '字典编号',
  `DICT_NAME` varchar(64) DEFAULT NULL COMMENT '字典名称',
  `DICT_DESC` varchar(64) DEFAULT NULL COMMENT '字典描述',
  `CREATE_DATETIME` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  `CREATE_PERSON` varchar(255) NOT NULL COMMENT '创建人',
  PRIMARY KEY (`DICT_ITEM_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of dict_item
-- ----------------------------
BEGIN;
INSERT INTO `dict_item` VALUES ('566156a4-0eb2-49ad-8d1b-48b4e429c048', '4093ce95-97f5-4ca9-bdbd-f086682cf4ce', 'SAMPLE_NO', '247', '样品编号', NULL, '2020-04-30 21:28:31.856000', '1');
INSERT INTO `dict_item` VALUES ('a556bd22-5cdf-418d-a932-950b612cc484', '80e4dfe9-0ba2-432f-8a08-05d1f00e3408', 'SAMPLE_NO', '5', '样品编号', NULL, '2020-06-16 18:17:28.868000', 'ff80808172a7b2ce0172bacaf5b10006');
COMMIT;

-- ----------------------------
-- Table structure for match_result
-- ----------------------------
DROP TABLE IF EXISTS `match_result`;
CREATE TABLE `match_result` (
  `ID` varchar(64) NOT NULL COMMENT '主键id',
  `COMPARE_ID` varchar(64) DEFAULT NULL COMMENT '比对id',
  `MIXED_SAMPLE_GENE_ID` varchar(64) DEFAULT NULL COMMENT '混合id',
  `SINGLE_GENE_ID` varchar(64) DEFAULT NULL COMMENT '单一id',
  `SPLITED_SAMPLE_GENE_ID` varchar(64) DEFAULT NULL COMMENT '拆分id',
  `QUSLTY_GENE_ID` varchar(64) DEFAULT NULL COMMENT '质控id',
  `SAMPLE_GENE_RESULT_TYPE` varchar(8) DEFAULT NULL COMMENT '结果类型(混合比对单一:01,拆分比混合:02,混合比质控:03.拆分比单一04) ',
  `RATIO` varchar(8) DEFAULT NULL COMMENT '比中数',
  `SPLIT_DIGIT` varchar(8) DEFAULT NULL COMMENT '差异数',
  `COMPARISON_TIME` timestamp(6) NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '比中时间',
  `PROPORTION_SAMPLE_NO` varchar(64) DEFAULT NULL COMMENT '比中样本编号',
  `PROPORTION_SAMPLE_NAME` varchar(64) DEFAULT NULL COMMENT '比中样本名称',
  `PROPORTION_SITE_NUM` varchar(64) DEFAULT NULL COMMENT '比中样本位点数',
  `GENE_INFO` json DEFAULT NULL COMMENT '比中样本基因信息',
  `GENE_PICTURE` varchar(1000) DEFAULT NULL COMMENT '比中基因图谱',
  `PROPORTION_CASE_NO` varchar(64) DEFAULT NULL COMMENT '比中案件编号',
  `PROPORTION_CASE_NAME` varchar(200) DEFAULT NULL COMMENT '比中案件名称',
  `PROPORTION_PERSON_NAME` varchar(255) DEFAULT NULL COMMENT '比中人员姓名',
  `PROPORTION_PERSONNEL` varchar(100) DEFAULT NULL COMMENT '比中人员类型名称',
  `PROPORTION_PERSON_CODE` varchar(400) DEFAULT NULL COMMENT '比中人员类型编号',
  `ID_CARD_NO` varchar(250) DEFAULT NULL COMMENT '比中人员身份证号',
  `PROPORTION_DISTRICT` varchar(200) DEFAULT NULL COMMENT '比中地区',
  `PROPORTION_KIL_NAME` varchar(255) DEFAULT NULL COMMENT '比中试剂盒',
  `EFFECT_FLAG` varchar(64) DEFAULT '1' COMMENT '是否有效（0：否，1：是）',
  `CREATE_PERSON` varchar(255) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='匹配结果表';

-- ----------------------------
-- Records of match_result
-- ----------------------------
BEGIN;
INSERT INTO `match_result` VALUES ('2f14f3a3-c7ee-404b-a398-6556c1824365', '42bbe606-b3d1-45db-ab87-4fa7348dfdcb', '42412447-f8d3-4dfa-988a-0faf5e44f47c', 'W4300000002006081109657HHHHHHHHH', NULL, NULL, '04', '7', '4', '2020-07-13 17:34:13.812000', '2006119501', '湖南华容20060808民政局发生一起盗窃案-报纸上血迹', '15', '[{\"name\": \"vWA\", \"value\": \"14/16\"}, {\"name\": \"FGA\", \"value\": \"22/24\"}, {\"name\": \"D7S820\", \"value\": \"\"}, {\"name\": \"D1S1656\", \"value\": \"\"}, {\"name\": \"TPOX\", \"value\": \"11/11\"}, {\"name\": \"D19S433\", \"value\": \"13/13\"}, {\"name\": \"AMELOGENIN\", \"value\": \"X/Y\"}, {\"name\": \"ABOGroup\", \"value\": \"\"}, {\"name\": \"D5S818\", \"value\": \"10/10\"}, {\"name\": \"F13A01\", \"value\": \"\"}, {\"name\": \"D6S1043\", \"value\": \"\"}, {\"name\": \"D2S1338\", \"value\": \"\"}, {\"name\": \"D21S11\", \"value\": \"29/29\"}, {\"name\": \"D12S391\", \"value\": \"\"}, {\"name\": \"D18S51\", \"value\": \"12/15\"}, {\"name\": \"TH01\", \"value\": \"9/9\"}, {\"name\": \"FESFPS\", \"value\": \"\"}, {\"name\": \"D16S539\", \"value\": \"\"}, {\"name\": \"D13S317\", \"value\": \"8/11\"}, {\"name\": \"PentaD\", \"value\": \"\"}, {\"name\": \"PentaE\", \"value\": \"\"}, {\"name\": \"CSF1PO\", \"value\": \"\"}, {\"name\": \"D3S1358\", \"value\": \"14/17\"}, {\"name\": \"D8S1179\", \"value\": \"15/15\"}]', NULL, 'A4300000002006081109656', '湖南华容20060808民政局发生一起盗窃案', '湖南华容20060808民政局发生一起盗窃案-报纸上血迹', '现场检材', '010201', NULL, '湖南', NULL, NULL, '1');
INSERT INTO `match_result` VALUES ('2f66ef65-ca1e-467b-9c7b-e671724772da', '3deddae9-adfe-4e38-857b-0d6b6d98fd9c', 'f1c0bf32-e0b5-4764-8fa5-304f92f1077a', '8a32891d3c2e3c3b013d1f9ddd756dfd', NULL, NULL, '01', '14', '0', '2020-07-13 17:20:18.846000', '2013-00017', '刘全禄', '16', '[{\"name\": \"vWA\", \"value\": \"16/18\"}, {\"name\": \"FGA\", \"value\": \"18/22\"}, {\"name\": \"D7S820\", \"value\": \"8/12\"}, {\"name\": \"D1S1656\", \"value\": \"\"}, {\"name\": \"TPOX\", \"value\": \"8/8\"}, {\"name\": \"D19S433\", \"value\": \"13.2/16.2\"}, {\"name\": \"AMELOGENIN\", \"value\": \"X/Y\"}, {\"name\": \"ABOGroup\", \"value\": \"\"}, {\"name\": \"D5S818\", \"value\": \"11/13\"}, {\"name\": \"F13A01\", \"value\": \"\"}, {\"name\": \"D6S1043\", \"value\": \"\"}, {\"name\": \"D2S1338\", \"value\": \"18/23\"}, {\"name\": \"D21S11\", \"value\": \"31/32\"}, {\"name\": \"D12S391\", \"value\": \"\"}, {\"name\": \"D18S51\", \"value\": \"13/14\"}, {\"name\": \"TH01\", \"value\": \"7/9\"}, {\"name\": \"FESFPS\", \"value\": \"\"}, {\"name\": \"D16S539\", \"value\": \"9/10\"}, {\"name\": \"D13S317\", \"value\": \"12/13\"}, {\"name\": \"PentaD\", \"value\": \"\"}, {\"name\": \"PentaE\", \"value\": \"\"}, {\"name\": \"CSF1PO\", \"value\": \"10/11\"}, {\"name\": \"D3S1358\", \"value\": \"15/17\"}, {\"name\": \"D8S1179\", \"value\": \"10/16\"}]', NULL, NULL, NULL, '刘全禄', '其他建库人员', '010109', NULL, '甘肃', 'SinoFiler', NULL, '1');
INSERT INTO `match_result` VALUES ('7678d8ba-c72c-4457-b770-04c0578d78f0', 'd637339a-11d9-45fc-94dc-7258d7eb5c20', '0b595fc3-1468-4dca-96bc-62645b463b9f', '0044455F464E1D2AA973629FADB2554E', NULL, NULL, '01', '14', '0', '2020-07-13 17:25:01.309000', '113471-01', '杨秋歆', '16', '[{\"name\": \"vWA\", \"value\": \"16/18\"}, {\"name\": \"FGA\", \"value\": \"18/22\"}, {\"name\": \"D7S820\", \"value\": \"8/12\"}, {\"name\": \"D1S1656\", \"value\": \"\"}, {\"name\": \"TPOX\", \"value\": \"8/8\"}, {\"name\": \"D19S433\", \"value\": \"13.2/16.2\"}, {\"name\": \"AMELOGENIN\", \"value\": \"X/X\"}, {\"name\": \"ABOGroup\", \"value\": \"\"}, {\"name\": \"D5S818\", \"value\": \"11/13\"}, {\"name\": \"F13A01\", \"value\": \"\"}, {\"name\": \"D6S1043\", \"value\": \"\"}, {\"name\": \"D2S1338\", \"value\": \"18/23\"}, {\"name\": \"D21S11\", \"value\": \"31/32\"}, {\"name\": \"D12S391\", \"value\": \"\"}, {\"name\": \"D18S51\", \"value\": \"13/14\"}, {\"name\": \"TH01\", \"value\": \"7/9\"}, {\"name\": \"FESFPS\", \"value\": \"\"}, {\"name\": \"D16S539\", \"value\": \"9/10\"}, {\"name\": \"D13S317\", \"value\": \"12/13\"}, {\"name\": \"PentaD\", \"value\": \"\"}, {\"name\": \"PentaE\", \"value\": \"\"}, {\"name\": \"CSF1PO\", \"value\": \"10/11\"}, {\"name\": \"D3S1358\", \"value\": \"15/17\"}, {\"name\": \"D8S1179\", \"value\": \"10/16\"}]', NULL, 'A1100000002011080901171', 'FYB1103471-2011WZ3471黄建伟走失案', '杨秋歆', '其他建库人员', '010109', '41302419771001002X', '北京', NULL, NULL, '1');
INSERT INTO `match_result` VALUES ('c7baf427-25de-4f6c-9439-499eae7b7d50', '3deddae9-adfe-4e38-857b-0d6b6d98fd9c', 'f1c0bf32-e0b5-4764-8fa5-304f92f1077a', '9B18441D682F118EDBE7E3894EC2A58D', NULL, NULL, '01', '14', '0', '2020-07-13 17:18:59.258000', 'FT153169-01', '虎俊发血样', '16', '[{\"name\": \"vWA\", \"value\": \"16/18\"}, {\"name\": \"FGA\", \"value\": \"18/22\"}, {\"name\": \"D7S820\", \"value\": \"8/12\"}, {\"name\": \"D1S1656\", \"value\": \"\"}, {\"name\": \"TPOX\", \"value\": \"8/8\"}, {\"name\": \"D19S433\", \"value\": \"13.2/16.2\"}, {\"name\": \"AMELOGENIN\", \"value\": \"X/X\"}, {\"name\": \"ABOGroup\", \"value\": \"\"}, {\"name\": \"D5S818\", \"value\": \"11/13\"}, {\"name\": \"F13A01\", \"value\": \"\"}, {\"name\": \"D6S1043\", \"value\": \"\"}, {\"name\": \"D2S1338\", \"value\": \"18/23\"}, {\"name\": \"D21S11\", \"value\": \"31/32\"}, {\"name\": \"D12S391\", \"value\": \"\"}, {\"name\": \"D18S51\", \"value\": \"13/14\"}, {\"name\": \"TH01\", \"value\": \"7/9\"}, {\"name\": \"FESFPS\", \"value\": \"\"}, {\"name\": \"D16S539\", \"value\": \"9/10\"}, {\"name\": \"D13S317\", \"value\": \"12/13\"}, {\"name\": \"PentaD\", \"value\": \"\"}, {\"name\": \"PentaE\", \"value\": \"\"}, {\"name\": \"CSF1PO\", \"value\": \"10/11\"}, {\"name\": \"D3S1358\", \"value\": \"15/17\"}, {\"name\": \"D8S1179\", \"value\": \"10/16\"}]', NULL, 'A1100000002015102400003', 'FT2015WZ3169苏有芳走失', '虎俊发血样', '其他建库人员', '010109', NULL, '北京', NULL, NULL, '1');
INSERT INTO `match_result` VALUES ('e8afa55e-b0e3-48aa-960a-a9b17b377668', '42bbe606-b3d1-45db-ab87-4fa7348dfdcb', '42412447-f8d3-4dfa-988a-0faf5e44f47c', '8ac146c21540b38301194b1fbf12467d', NULL, NULL, '04', '5', '4', '2020-07-13 17:26:56.144000', '2008-0356-W02', '死者左手指甲一份', '15', '[{\"name\": \"vWA\", \"value\": \"17/17\"}, {\"name\": \"FGA\", \"value\": \"\"}, {\"name\": \"D7S820\", \"value\": \"11/11\"}, {\"name\": \"D1S1656\", \"value\": \"\"}, {\"name\": \"TPOX\", \"value\": \"\"}, {\"name\": \"D19S433\", \"value\": \"\"}, {\"name\": \"AMELOGENIN\", \"value\": \"X/Y\"}, {\"name\": \"ABOGroup\", \"value\": \"\"}, {\"name\": \"D5S818\", \"value\": \"9/11\"}, {\"name\": \"F13A01\", \"value\": \"\"}, {\"name\": \"D6S1043\", \"value\": \"\"}, {\"name\": \"D2S1338\", \"value\": \"\"}, {\"name\": \"D21S11\", \"value\": \"\"}, {\"name\": \"D12S391\", \"value\": \"\"}, {\"name\": \"D18S51\", \"value\": \"16/18\"}, {\"name\": \"TH01\", \"value\": \"9/9\"}, {\"name\": \"FESFPS\", \"value\": \"\"}, {\"name\": \"D16S539\", \"value\": \"9/11\"}, {\"name\": \"D13S317\", \"value\": \"\"}, {\"name\": \"PentaD\", \"value\": \"9/11\"}, {\"name\": \"PentaE\", \"value\": \"5/17\"}, {\"name\": \"CSF1PO\", \"value\": \"12/12\"}, {\"name\": \"D3S1358\", \"value\": \"16/17\"}, {\"name\": \"D8S1179\", \"value\": \"15/15\"}]', NULL, 'A5101000002008041400003', '2008-4-12温江杀人案', '死者左手指甲一份', '现场检材', '010201', NULL, '四川成都', 'PowerPlex16', NULL, '1');
COMMIT;

-- ----------------------------
-- Table structure for mixed_sample_gene
-- ----------------------------
DROP TABLE IF EXISTS `mixed_sample_gene`;
CREATE TABLE `mixed_sample_gene` (
  `ID` varchar(64) NOT NULL COMMENT '主键id',
  `SAMPLE_ID` varchar(64) DEFAULT NULL COMMENT '样本ID',
  `SAMPLE_NO` varchar(64) DEFAULT NULL COMMENT '样本编号',
  `SAMPLE_NAME` varchar(64) DEFAULT NULL COMMENT '样本名称',
  `REAGENT_NAME` varchar(64) DEFAULT NULL COMMENT '试剂盒',
  `BOARD_BARCODE` varchar(64) DEFAULT NULL COMMENT '电泳板号',
  `GENE_INFO` json DEFAULT NULL COMMENT '基因信息',
  `GENE_PICTURE` varchar(1000) DEFAULT NULL COMMENT '基因图谱',
  `CREATE_PERSON` varchar(64) DEFAULT NULL COMMENT '创建人',
  `CREATE_DATETIME` timestamp(6) NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  `UPDATE_PERESON` varchar(64) DEFAULT NULL COMMENT '更新人',
  `UPDATE_DATETIME` timestamp(6) NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '更新时间',
  `CONTRIBUTOR_COUNT` varchar(64) DEFAULT NULL COMMENT '混合人数量',
  `IS_DELETED` int(3) DEFAULT '0' COMMENT '0-未删除,1-删除',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='混合样本基因表';

-- ----------------------------
-- Records of mixed_sample_gene
-- ----------------------------
BEGIN;
INSERT INTO `mixed_sample_gene` VALUES ('0b595fc3-1468-4dca-96bc-62645b463b9f', NULL, 'GZ200003-11', NULL, NULL, NULL, '[{\"name\": \"AMELOGENIN\", \"value\": \"X/Y\"}, {\"name\": \"D3S1358\", \"value\": \"15/17/18\"}, {\"name\": \"vWA\", \"value\": \"16/18/19\"}, {\"name\": \"FGA\", \"value\": \"18/22\"}, {\"name\": \"D8S1179\", \"value\": \"10/16\"}, {\"name\": \"D21S11\", \"value\": \"31/32/32.2\"}, {\"name\": \"D18S51\", \"value\": \"13/14/16\"}, {\"name\": \"D5S818\", \"value\": \"11/13\"}, {\"name\": \"D13S317\", \"value\": \"12/13\"}, {\"name\": \"D7S820\", \"value\": \"8/12/13\"}, {\"name\": \"CSF1PO\", \"value\": \"10/11\"}, {\"name\": \"D16S539\", \"value\": \"9/10/13\"}, {\"name\": \"D2S1338\", \"value\": \"18/23/24\"}, {\"name\": \"D19S433\", \"value\": \"13.2/16.2\"}, {\"name\": \"D6S1043\", \"value\": \"12/18\"}, {\"name\": \"D12S391\", \"value\": \"18/19/20\"}]', NULL, '1', '2020-07-13 17:18:11.408000', NULL, NULL, '2', 0);
INSERT INTO `mixed_sample_gene` VALUES ('3055bfd6-c995-4909-ab84-df493b99ca67', NULL, 'SY200345-01', NULL, NULL, NULL, '[{\"name\": \"Amel\", \"value\": \"X/Y\"}, {\"name\": \"D3S1358\", \"value\": \"15/16/17\"}, {\"name\": \"vWA\", \"value\": \"14/18/19\"}, {\"name\": \"FGA\", \"value\": \"21/22\"}, {\"name\": \"D8S1179\", \"value\": \"10/15\"}, {\"name\": \"D21S11\", \"value\": \"29/30/32\"}, {\"name\": \"D18S51\", \"value\": \"14/15/17\"}, {\"name\": \"D5S818\", \"value\": \"9/11\"}, {\"name\": \"D13S317\", \"value\": \"8/13\"}, {\"name\": \"D7S820\", \"value\": \"9/10/12\"}, {\"name\": \"CSF1PO\", \"value\": \"11/12\"}, {\"name\": \"D16S539\", \"value\": \"11/12/17\"}, {\"name\": \"D2S1338\", \"value\": \"22/23/24\"}, {\"name\": \"D19S433\", \"value\": \"14/15.2\"}, {\"name\": \"D6S1043\", \"value\": \"13/14\"}, {\"name\": \"D12S391\", \"value\": \"18/19/20\"}]', NULL, '1', '2020-07-14 15:53:13.724000', NULL, NULL, '2', 0);
INSERT INTO `mixed_sample_gene` VALUES ('42412447-f8d3-4dfa-988a-0faf5e44f47c', NULL, 'SG-2020-247', NULL, NULL, NULL, '[{\"name\": \"D8S1179\", \"value\": \"10/13/12/15/16/11/14\"}, {\"name\": \"D21S11\", \"value\": \"28.2/32.2/31/29/28/30\"}, {\"name\": \"D7S820\", \"value\": \"7/9/11/12/10/8\"}, {\"name\": \"CSF1PO\", \"value\": \"9/11/10/12/8\"}, {\"name\": \"D3S1358\", \"value\": \"15/17/14/18/16/13\"}, {\"name\": \"TH01\", \"value\": \"7/6/9/8\"}, {\"name\": \"D13S317\", \"value\": \"10/12/8/9/13/11\"}, {\"name\": \"D16S539\", \"value\": \"9/12/13/10/11\"}, {\"name\": \"D2S1338\", \"value\": \"23/18/25/24/17/22/16\"}, {\"name\": \"D19S433\", \"value\": \"13.2/14/13/15.2/12/14.2/15\"}, {\"name\": \"vWA\", \"value\": \"14/17/16/15/18\"}, {\"name\": \"TPOX\", \"value\": \"10/9/12/11/8/7\"}, {\"name\": \"D18S51\", \"value\": \"15/19/14/21/16/17/13/20\"}, {\"name\": \"D5S818\", \"value\": \"7/9/10/13/11/12\"}, {\"name\": \"FGA\", \"value\": \"23/27/26/24/22/18/21\"}]', NULL, '1', '2020-07-13 17:18:39.036000', NULL, NULL, '3', 0);
INSERT INTO `mixed_sample_gene` VALUES ('8a32811d23b610f60124c714666f2afd', '8a32811d23b610f60124c714666f2afd', 'W6200000002009110600011', '关康裤管上点状血迹', 'Identifiler', NULL, '[{\"name\": \"AMELOGENIN\", \"value\": \"\"}, {\"name\": \"D8S1179\", \"value\": \"130/140/160\"}, {\"name\": \"D21S11\", \"value\": \"280/290/300/320\"}, {\"name\": \"D7S820\", \"value\": \"080/100/110/120\"}, {\"name\": \"CSF1PO\", \"value\": \"080/100/120/130\"}, {\"name\": \"D3S1358\", \"value\": \"160\"}, {\"name\": \"TH01\", \"value\": \"080\"}, {\"name\": \"D13S317\", \"value\": \"080/130/140\"}, {\"name\": \"D16S539\", \"value\": \"090\"}, {\"name\": \"D2S1338\", \"value\": \"190\"}, {\"name\": \"D19S433\", \"value\": \"130/140/150\"}, {\"name\": \"vWA\", \"value\": \"160/190\"}, {\"name\": \"TPOX\", \"value\": \"080\"}, {\"name\": \"D18S51\", \"value\": \"140/170/220\"}, {\"name\": \"D5S818\", \"value\": \"100/110/120\"}, {\"name\": \"FGA\", \"value\": \"210/240\"}]', NULL, '1', NULL, NULL, NULL, '2', 0);
INSERT INTO `mixed_sample_gene` VALUES ('a6b4377a-2ab7-433e-9e08-358b4e02a0f1', NULL, 'SY20WZ0379-01', NULL, NULL, NULL, '[{\"name\": \"Amel\", \"value\": \"X/Y\"}, {\"name\": \"D5S818\", \"value\": \"21/25/27/30/31/45\"}, {\"name\": \"D21S11\", \"value\": \"15/17/21\"}, {\"name\": \"D7S820\", \"value\": \"10/17\"}, {\"name\": \"CSF1PO\", \"value\": \"9/14\"}, {\"name\": \"D2S1338\", \"value\": \"19/20\"}, {\"name\": \"D3S1358\", \"value\": \"16/16\"}, {\"name\": \"vWA\", \"value\": \"18/20\"}, {\"name\": \"D8S1179\", \"value\": \"13/18\"}, {\"name\": \"D16S539\", \"value\": \"9/13\"}, {\"name\": \"Penta E\", \"value\": \"15/19\"}, {\"name\": \"TPOX\", \"value\": \"8/11\"}, {\"name\": \"TH01\", \"value\": \"6/9.3\"}, {\"name\": \"D19S433\", \"value\": \"13/15.2\"}, {\"name\": \"D18S51\", \"value\": \"12/19\"}, {\"name\": \"FGA\", \"value\": \"21/25\"}, {\"name\": \"D6S1043\", \"value\": \"18/19\"}, {\"name\": \"D13S317\", \"value\": \"9/11\"}, {\"name\": \"D12S391\", \"value\": \"19/20\"}]', NULL, '1', '2020-07-14 15:54:05.887000', NULL, NULL, '3', 0);
INSERT INTO `mixed_sample_gene` VALUES ('f1c0bf32-e0b5-4764-8fa5-304f92f1077a', NULL, 'GZ200003-11', NULL, NULL, NULL, '[{\"name\": \"AMELOGENIN\", \"value\": \"X/Y\"}, {\"name\": \"D3S1358\", \"value\": \"15/17/18\"}, {\"name\": \"vWA\", \"value\": \"16/18/19\"}, {\"name\": \"FGA\", \"value\": \"18/22\"}, {\"name\": \"D8S1179\", \"value\": \"10/16\"}, {\"name\": \"D21S11\", \"value\": \"31/32/32.2\"}, {\"name\": \"D18S51\", \"value\": \"13/14/16\"}, {\"name\": \"D5S818\", \"value\": \"11/13\"}, {\"name\": \"D13S317\", \"value\": \"12/13\"}, {\"name\": \"D7S820\", \"value\": \"8/12/13\"}, {\"name\": \"CSF1PO\", \"value\": \"10/11\"}, {\"name\": \"D16S539\", \"value\": \"9/10/13\"}, {\"name\": \"D2S1338\", \"value\": \"18/23/24\"}, {\"name\": \"D19S433\", \"value\": \"13.2/16.2\"}, {\"name\": \"D6S1043\", \"value\": \"12/18\"}, {\"name\": \"D12S391\", \"value\": \"18/19/20\"}]', NULL, '1', '2020-07-13 17:17:20.245000', NULL, NULL, '2', 0);
COMMIT;

-- ----------------------------
-- Table structure for mobile_news
-- ----------------------------
DROP TABLE IF EXISTS `mobile_news`;
CREATE TABLE `mobile_news` (
  `ID` varchar(64) NOT NULL COMMENT '消息表主键id',
  `TITLE` varchar(64) DEFAULT NULL COMMENT '信息标题',
  `COMPARE_ID` varchar(64) DEFAULT NULL COMMENT '比对id',
  `CONTENT` varchar(400) DEFAULT NULL COMMENT '消息内容',
  `STATE` varchar(8) DEFAULT NULL COMMENT '查看状态0:未读 1:已阅读',
  `CREATE_DATETIME` timestamp(6) NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  `UPDATE_DATETIME` timestamp(6) NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '修改时间',
  `TYPE` varchar(8) DEFAULT NULL COMMENT '信息类型(1-6手机信息)(10顺义消息)',
  `USERID` varchar(64) DEFAULT NULL COMMENT '用户id',
  `CASEID` varchar(64) DEFAULT NULL COMMENT '案件id',
  `USERORGID` varchar(64) DEFAULT NULL COMMENT '用户机构id',
  `MESSAGETYPE` varchar(8) DEFAULT NULL COMMENT '消息类型1app,2pc,3智能分析,4快比',
  `MOBILE_FLAG` varchar(10) DEFAULT NULL COMMENT '是否忽略（0：否，1：是，用来标记新消息提醒）',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='消息提示表';

-- ----------------------------
-- Records of mobile_news
-- ----------------------------
BEGIN;
INSERT INTO `mobile_news` VALUES ('19d76326-5a38-498d-919f-7231509b45eb', '比对消息提示', '42bbe606-b3d1-45db-ab87-4fa7348dfdcb', '超管提交比对的混合分型比中 [现场检材死者左手指甲一份编号2008-0356-W02]', '0', '2020-07-13 17:26:56.000000', NULL, '11', '1', NULL, NULL, '3', '1');
INSERT INTO `mobile_news` VALUES ('562e84a2-e076-43e3-b9a6-5b0a66fcc51f', '比对消息提示', 'd637339a-11d9-45fc-94dc-7258d7eb5c20', '超管提交比对的混合分型比中 [其他建库人员杨秋歆编号113471-01]', '0', '2020-07-13 17:25:01.000000', NULL, '11', '1', NULL, NULL, '3', '1');
INSERT INTO `mobile_news` VALUES ('d5e367a6-a953-4b4d-a5cf-e5206c310c6a', '比对消息提示', '42bbe606-b3d1-45db-ab87-4fa7348dfdcb', '超管提交比对的混合分型比中 [现场检材湖南华容20060808民政局发生一起盗窃案-报纸上血迹编号2006119501]', '0', '2020-07-13 17:34:14.000000', NULL, '11', '1', NULL, NULL, '3', '1');
INSERT INTO `mobile_news` VALUES ('dceabb08-83c6-41ca-8fd0-62d6246c6746', '比对消息提示', '3deddae9-adfe-4e38-857b-0d6b6d98fd9c', '超管提交比对的混合分型比中 [其他建库人员刘全禄编号2013-00017]', '0', '2020-07-13 17:20:18.000000', NULL, '11', '1', NULL, NULL, '3', '1');
INSERT INTO `mobile_news` VALUES ('e4cd4d07-fdbb-4ac3-844a-582b01980268', '比对消息提示', '3deddae9-adfe-4e38-857b-0d6b6d98fd9c', '超管提交比对的混合分型比中 [其他建库人员虎俊发血样编号FT153169-01]', '0', '2020-07-13 17:18:59.000000', NULL, '11', '1', NULL, NULL, '3', '1');
COMMIT;

-- ----------------------------
-- Table structure for quality_personnel
-- ----------------------------
DROP TABLE IF EXISTS `quality_personnel`;
CREATE TABLE `quality_personnel` (
  `ID` varchar(64) NOT NULL COMMENT '主键id',
  `SAMPLE_NO` varchar(64) NOT NULL COMMENT '样本编号',
  `SAMPLE_NAME` varchar(64) DEFAULT NULL COMMENT '样本名称',
  `REAGENT_NAME` varchar(64) DEFAULT NULL COMMENT '试剂盒',
  `BOARD_BARCODE` varchar(64) DEFAULT NULL COMMENT '电泳板号',
  `GENE_INFO` json DEFAULT NULL COMMENT '基因信息',
  `GENE_PICTURE` varchar(1000) DEFAULT NULL COMMENT '基因图谱',
  `CREATE_PERSON` varchar(64) DEFAULT NULL COMMENT '创建人',
  `CREATE_DATETIME` timestamp(6) NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  `UPDATE_PERESON` varchar(64) DEFAULT NULL COMMENT '更新人',
  `UPDATE_DATETIME` timestamp(6) NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '更新时间',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='质控人员表';

-- ----------------------------
-- Table structure for splited_sample_gene
-- ----------------------------
DROP TABLE IF EXISTS `splited_sample_gene`;
CREATE TABLE `splited_sample_gene` (
  `ID` varchar(64) NOT NULL COMMENT '主键id',
  `MIXED_SAMPLE_GENE_ID` varchar(64) DEFAULT NULL COMMENT '混合id',
  `GENE_INFO` json DEFAULT NULL COMMENT '基因信息',
  `CREATE_PERSON` varchar(64) DEFAULT NULL COMMENT '创建人',
  `CREATE_DATETIME` timestamp(6) NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '创建时间',
  `UPDATE_PERESON` varchar(64) DEFAULT NULL COMMENT '更新人',
  `UPDATE_DATETIME` timestamp(6) NULL DEFAULT CURRENT_TIMESTAMP(6) COMMENT '更新时间',
  `COMPARE_QUEUE_ID` varchar(64) DEFAULT NULL COMMENT '比对任务主键',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='拆分样本基因表';

-- ----------------------------
-- Records of splited_sample_gene
-- ----------------------------
BEGIN;
INSERT INTO `splited_sample_gene` VALUES ('050e1ff0-e7c8-406d-98c1-fc88552aed81', '42412447-f8d3-4dfa-988a-0faf5e44f47c', '[{\"name\": \"D8S1179\", \"value\": \"12/15\"}, {\"name\": \"D21S11\", \"value\": \"29/29\"}, {\"name\": \"D7S820\", \"value\": \"8/11\"}, {\"name\": \"CSF1PO\", \"value\": \"10/12\"}, {\"name\": \"D3S1358\", \"value\": \"14/17\"}, {\"name\": \"TH01\", \"value\": \"8/9\"}, {\"name\": \"D13S317\", \"value\": \"9/13\"}, {\"name\": \"D16S539\", \"value\": \"12/13\"}, {\"name\": \"D2S1338\", \"value\": \"23/24\"}, {\"name\": \"D19S433\", \"value\": \"13/14\"}, {\"name\": \"vWA\", \"value\": \"16/17\"}, {\"name\": \"TPOX\", \"value\": \"8/11\"}, {\"name\": \"D18S51\", \"value\": \"14/17\"}, {\"name\": \"D5S818\", \"value\": \"11/12\"}, {\"name\": \"FGA\", \"value\": \"22/24\"}]', '1', '2020-07-13 17:18:39.157000', NULL, NULL, '42bbe606-b3d1-45db-ab87-4fa7348dfdcb');
INSERT INTO `splited_sample_gene` VALUES ('1a9a815c-d1a7-4ee1-aa39-8e09cbcebfa7', '8a32811d23b610f60124c714666f2afd', '[{\"name\": \"AMELOGENIN\", \"value\": \"\"}, {\"name\": \"D8S1179\", \"value\": \"\"}, {\"name\": \"D21S11\", \"value\": \"\"}, {\"name\": \"D7S820\", \"value\": \"080/100\"}, {\"name\": \"CSF1PO\", \"value\": \"100/120\"}, {\"name\": \"D3S1358\", \"value\": \"\"}, {\"name\": \"TH01\", \"value\": \"080\"}, {\"name\": \"D13S317\", \"value\": \"130\"}, {\"name\": \"D16S539\", \"value\": \"\"}, {\"name\": \"D2S1338\", \"value\": \"190\"}, {\"name\": \"D19S433\", \"value\": \"130/140\"}, {\"name\": \"vWA\", \"value\": \"\"}, {\"name\": \"TPOX\", \"value\": \"080\"}, {\"name\": \"D18S51\", \"value\": \"140/170\"}, {\"name\": \"D5S818\", \"value\": \"\"}, {\"name\": \"FGA\", \"value\": \"210/240\"}]', '1', '2020-07-13 17:19:34.066000', NULL, NULL, '52881188-bc09-4973-846e-b0315687b7f5');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
