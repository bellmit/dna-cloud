package com.bazl.dna.mix.compare.constants;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2016/12/28.
 */
public class Constants {

    public static final String GUANGDONG_ORG_CODE = "440000000000";
    public static final String USER_SESSION = "USER_SESSION";

    //入库类型
    public static final String INSTORED_TYPE = "INSTORED_TYPE";
    //比对类型
    public static final String COMPARISON_TYPE = "COMPARISON_TYPE";
    //比对类别
    public static final String COMPARISON_CATEGORY = "COMPARISON_CATEGORY";
    // 比对状态
    public static final String COMPARE_STATUS = "COMPARE_STATUS";
    // 快速比对状态
    public static final String QUEUE_STATUS = "QUEUE_STATUS";
    //待比对
    public static final String COMPARE_STATUS_NO = "0";
    //比对完成
    public static final String COMPARE_STATUS_FINISH = "1";
    //比对完成
    public static final String COMPARE_STATUS_WAITING = "2";

    // 比对模式
    public static final String COMPARE_MODE = "COMPARE_MODE";
    // STR同型比对
    public static final int COMPARE_MODE_STR = 1;
    // 亲缘三联体比对
    public static final int COMPARE_MODE_RELATIVE_THREE_CONJOINED = 2;
    // 亲缘单亲比对
    public static final int COMPARE_MODE_RELATIVE_SINGLE_CONJOINED = 3;
    // Y-STR比对
    public static final int COMPARE_MODE_YSTR = 4;
    // 混合STR比对
    public static final int COMPARE_MODE_MIX_STR = 5;
    
    // 比对分组类型
    public static final String COMPARE_GROUP_TYPE = "GROUP_TYPE";
    // 人员-物证比中
    public static final String COMPARE_GROUP_TYPE_PERSON_OBJECT = "1";
    // 物证-物证比中
    public static final String COMPARE_GROUP_TYPE_OBJECT_OBJECT = "2";
    // 人员比中
    public static final String COMPARE_GROUP_TYPE_PERSON_PERSON = "3";
    
    // 亲缘比对目标类型
    public static final String COMPARE_TARGET_RELATION_FATHER = "01";
    public static final String COMPARE_TARGET_RELATION_MOTHER = "02";
    public static final String COMPARE_TARGET_RELATION_CHILD = "03";


    public static final String DB_TYPE_FAMILY_PERSON = "1";
    public static final String DB_TYPE_CRIMINAL_PERSON = "2";
    public static final String DB_TYPE_CASE_PERSON = "4";
    public static final String DB_TYPE_EVIDENCE = "5";

    public static final String ORG_LEVEL_SHENGTING = "0";
    public static final String ORG_LEVEL_CITY = "1";
    public static final String ORG_LEVEL_QUXIAN = "2";
    public static final String ORG_LEVEL_PAICHUSUO = "3";

    public static final boolean SYNC_DNA36_TASK_STARTED = false;

    public static final String FLAG_TRUE = "1";
    public static final String FLAG_FALSE= "0";


    public static final Integer FLAG_TRUE_INT = 1;
    public static final Integer FLAG_FALSE_INT= 0;

    public static final String SAMPLE_FLAG_EVIDENCE = "1";
    public static final String SAMPLE_FLAG_PERSON = "2";

    public static final int CASE_NO_LENGTH = 5;
    public static final String PERSON_NO_PREFIX = "R";
    public static final String SAMPLE_NO_PREFIX = "S";

    public static final String TQ_TASK_PREFIX = "TQ";
    public static final String PCR_TASK_PREFIX = "KZ";
    public static final String SY_TASK_PREFIX = "SY";

    public static final String GENE_SAMPLE_TYPE_FAMILY = "1";
    public static final String GENE_SAMPLE_TYPE_CRIMINAL = "2";
    public static final String GENE_SAMPLE_TYPE_CASEPERSON = "3";
    public static final String GENE_SAMPLE_TYPE_PHYSICAL_EVIDENCE = "4";

    public static final String OPERATE_TYPE_ADD = "1";
    public static final String OPERATE_TYPE_EDIT = "2";
    public static final String OPERATE_TYPE_DEL = "3";

    public static final String ADD_PERSON_RELATION_FUQIN = "1";
    public static final String ADD_PERSON_RELATION_SHUBAI = "2";
    public static final String ADD_PERSON_RELATION_XIONGDI = "3";
    public static final String ADD_PERSON_RELATION_TANGXIONGDI = "4";
    public static final String ADD_PERSON_RELATION_ERZI = "5";
    public static final String ADD_PERSON_RELATION_ZHIZI = "6";

    public static final String VILLAGE_TYPE_CHENGZHEN = "1"; //城镇
    public static final String VILLAGE_TYPE_NONGCUN = "2";   //农村

    public static final String GENE_TYPE_STR = "1";         //STR
    public static final String GENE_TYPE_YSTR = "2";         //YSTR
    public static final String GENE_TYPE_MIX = "3";         //MIX

    public static final int SAME_MATCH_LIMIT_DEFAULT = 10;      //同型匹配下限

    /*  比对优先级 */
    public static final String COMPARE_QUEUE_PRIORITY_CASE = "1";           //案件样本
    public static final String COMPARE_QUEUE_PRIORITY_CRIMINAL = "2";       //违法犯罪人员样本
    public static final String COMPARE_QUEUE_PRIORITY_FAMILY = "3";         //家系人员样本

    /*  DICT */
    public static final String DICT_TPYE_RACE = "PERSON_RACE";             //名族
    public static final String DICT_TPYE_YHZGX = "YHZGX";
    public static final String DICT_TYPE_PERSON_GENDER = "PERSON_GENDER";  //性别
    public static final String DICT_TYPE_LIVE_STATUS = "LIVE_STATUS";
    public static final String DICT_TYPE_NATIONALITY = "PERSON_NATION";  //国籍

    public static final String DICT_TYPE_DB_TYPE = "DB_TYPE";
    public static final String DICT_TYPE_YHZGX = "YHZGX";                                                     //与户主关系
    public static final String DICT_TYPE_MODE_INTO_FAMILY = "MODE_INTO_FAMILY";                           //进出家系方式
    public static final String DICT_TYPE_RACE = "RACE";                                                       //民族
    public static final String DICT_TYPE_USER_TYPE = "USER_TYPE";
    public static final String DICT_TPYE_CASE_TYPE = "CASE_TYPE";
    public static final String DICT_TPYE_CASE_PROPERTY = "CASE_PROPERTY";//案件性质
    public static final String DICT_TPYE_CASE_LEVEL = "CASE_LEVEL";
    public static final String DICT_TPYE_CRIMINAL_PERSON_TYPE = "CRIMINAL_PERSON_TYPE";//建库人员类型
    public static final String DICT_TPYE_CASE_PERSON_TYPE = "CASE_PERSON_TYPE";//案件人员类型
    public static final String DICT_TPYE_PERSON_RELATION = "PERSON_RELATION";
    public static final String DICT_TPYE_SAMPLE_TYPE = "SAMPLE_TYPE";//检材类型
    public static final String DICT_TYPE_CASE_STATUS = "CASE_STATUS";
    public static final String DICT_TYPE_EXTRACT_METHOD = "EXTRACT_METHOD";         //提取方法
    public static final String DICT_TYPE_PCR_PROGRAM_NO = "PCR_PROGRAM_NO";         //扩增程序号
    public static final String DICT_TYPE_PCR_REAGENT_KIT = "PCR_REAGENT_KIT";         //扩增试剂盒
    public static final String DICT_TYPE_PCR_SYSTEM = "PCR_SYSTEM";         //扩增体系
    public static final String DICT_TYPE_PCR_INSTRUMENT = "PCR_INSTRUMENT";         //扩增仪
    public static final String DICT_TYPE_SAMPLE_PROPERTIES = "SAMPLE_PROPERTIES";   //样本性状
    public static final String DICT_TYPE_CODIES_FILE_PATH = "CODIES_FILE_PATH";
    public static final String DICT_TYPE_CODIES_READ_FILE_PATH = "CODIES_READ_FILE_PATH";
    public static final String DICT_TYPE_SERVER_PATH = "SERVER_PATH";
    public static final String DICT_TYPE_QC_SAMPLE_TYPE = "QC_SAMPLE_TYPE"; //质控样本类型
    public static final String DICT_TYPE_QC_PERSON_TYPE = "QC_PERSON_TYPE"; //质控人员类型
    public static final String DICT_TYPE_QUALITY_SAME_LIMIT = "QUALITY_SAME_LIMIT"; //污染比对匹配数
    public static final String DICT_TYPE_QUALITY_DIFF_LIMIT = "QUALITY_DIFF_LIMIT"; //污染比对容差数
    public static final String DICT_TYPE_PERSON_RELATION_TYPE = "PERSON_RELATION_TYPE"; //人员亲缘身份
    public static final String DICT_TYPE_PTARGET_RELATION = "TARGET_RELATION"; //比对亲缘关系
    public static final String DICT_TYPE_TRANSFER_STATS = "TRANSFER_STATUS"; //上报数据状态
    public static final String DICT_TYPE_CERTIFICATE_TYPE = "CERTIFICATE_TYPE";//证件类型


    public static final String DICT_TPYE_GENE_SAMPLE_TYPE = "GENE_SAMPLE_TYPE";//codis导入时的样本类型

    public static final String DICT_TYPE_SAVE_PATH = "SAVE_PATH";
    public static final String DICT_ITEM_PERSON_PHOTO_SAVE_PATH = "PERSON_PHOTO_SAVE_PATH";

    public static final String DICT_TYPE_CHANWU_UL = "CHANWU_UL";       //产物ul
    public static final String DICT_TYPE_JIAXIANAN_UL = "JIAXIANAN_UL";       //甲酰胺ul
    public static final String DICT_TYPE_NEIBIAO_UL = "NEIBIAO_UL";       //内标ul
    public static final String DICT_TYPE_NEIBIAO = "NEIBIAO";       //内标
    public static final String DICT_TYPE_ELEC_INSTRUMENT = "ELEC_INSTRUMENT";       //电泳仪

    public static final String[] SY_STANDARD_SAMPLE_ARR = new String[]{"9947A","YIN","LAD"};
    public static final List<String> SY_STANDARD_SAMPLE_LIST = Arrays.asList("9947A","YIN","LAD");

    public static final String[] PCR_STANDARD_SAMPLE_ARR = new String[]{"9947","Water"};
    public static final List<String> PCR_STANDARD_SAMPLE_LIST = Arrays.asList("9947","Water");


    public static final String PANEL_NAME_IDENTIFILER = "Identifiler";


    public static final String DICT_ITEM_OTHER_CODE = "9999";

    public static final String DICT_ITEM_SAMPLE_TYPE_BLOOD = "01";              //血痕/血液斑
    public static final String DICT_ITEM_SAMPLE_TYPE_SEMINAL = "02";            //精斑
    public static final String DICT_ITEM_SAMPLE_TYPE_HAIR = "03";               //毛发
    public static final String DICT_ITEM_SAMPLE_TYPE_NAIL = "04";               //指甲
    public static final String DICT_ITEM_SAMPLE_TYPE_BUTT = "05";               //烟蒂
    public static final String DICT_ITEM_SAMPLE_TYPE_SALIVA = "06";              //唾液（斑）
    public static final String DICT_ITEM_SAMPLE_TYPE_BONES_TEETH = "07";              //骨骼/牙齿
    public static final String DICT_ITEM_SAMPLE_TYPE_MUSCLE = "08";              //肌肉（组织）
    public static final String DICT_ITEM_SAMPLE_TYPE_TISSUE = "09";              //组织
    public static final String DICT_ITEM_SAMPLE_TYPE_FINGERPRINT = "10";              //指纹
    public static final String DICT_ITEM_SAMPLE_TYPE_CASTOFF_CELLS = "11";              //脱落细胞
    public static final String DICT_ITEM_SAMPLE_TYPE_OTHERS = "99";              //其他

    //提取方法
    public static final String DICT_ITEM_EXTRACT_METHOD_A_BLOOD = "A";              //血斑类
    public static final String DICT_ITEM_EXTRACT_METHOD_B_ATOMBLOOD = "B";          //微量血斑类
    public static final String DICT_ITEM_EXTRACT_METHOD_C_TISSUE = "C";             //组织类
    public static final String DICT_ITEM_EXTRACT_METHOD_D_CELLS = "D";              //口腔脱落上皮细胞类/  脱落细胞类
    public static final String DICT_ITEM_EXTRACT_METHOD_E_SEMINAL = "E";            //精斑类


    public static final String HOUSEHOLDER_RELATION_BENREN = "01";      //本人
    public static final String HOUSEHOLDER_RELATION_HUZHU = "02";       //户主
    public static final String HOUSEHOLDER_RELATION_PEIOU = "10";       //配偶
    public static final String HOUSEHOLDER_RELATION_ZHANGFU = "11";       //夫
    public static final String HOUSEHOLDER_RELATION_ERZI = "20";       //子
    public static final String HOUSEHOLDER_RELATION_DUSHENGZI = "21";       //独生子
    public static final String HOUSEHOLDER_RELATION_ZHANGZI = "22";       //长子
    public static final String HOUSEHOLDER_RELATION_CIZI = "23";       //次子
    public static final String HOUSEHOLDER_RELATION_SANZI = "24";       //三子
    public static final String HOUSEHOLDER_RELATION_SIZI = "25";       //四子
    public static final String HOUSEHOLDER_RELATION_WUZI = "26";       //五子
    public static final String HOUSEHOLDER_RELATION_JIZI = "27";       //继子或养子
    public static final String HOUSEHOLDER_RELATION_NVXU = "28";       //女婿
    public static final String HOUSEHOLDER_RELATION_SUNZI = "41";       //孙子
    public static final String HOUSEHOLDER_RELATION_FUMU = "50";       //父母
    public static final String HOUSEHOLDER_RELATION_FUQIN = "51";       //父亲
    public static final String HOUSEHOLDER_RELATION_ZUFU = "61";       //祖父
    public static final String HOUSEHOLDER_RELATION_XIONGDIJIEMEI = "70";       //兄弟姐妹
    public static final String HOUSEHOLDER_RELATION_XIONG = "71";       //兄
    public static final String HOUSEHOLDER_RELATION_DI = "73";       //弟
    public static final String HOUSEHOLDER_RELATION_BOFU = "81";       //伯父
    public static final String HOUSEHOLDER_RELATION_SHUFU = "83";       //叔父

    /*  比对参数解析json或map时，对应的key名称 */
    public static final String COMPARE_PARAM_KEY_MAX_DIFF_COUNT = "maxDiffCount";
    public static final String COMPARE_PARAM_KEY_MUTATION_STEP_COUNT = "mutationStepCount";

    public static final String COMPARE_PARAM_INGORE_MUTATION = "N";         //N表示不计算突变步数

    public static final String MATCHED_TYPE_FAMILY = "1";            //家系人员比中
    public static final String MATCHED_TYPE_FAMILYANDOTHER = "2";   //家系人员与其他人员比中
    public static final String MATCHED_TYPE_OTHER = "3";             //其他人员比中


    public static final String[] SYTABLE_POSTION_ARR = new String[]{
            "A01","A02","A03","A04","A05","A06","A07","A08","A09","A10","A11","A12",
            "B01","B02","B03","B04","B05","B06","B07","B08","B09","B10","B11","B12",
            "C01","C02","C03","C04","C05","C06","C07","C08","C09","C10","C11","C12",
            "D01","D02","D03","D04","D05","D06","D07","D08","D09","D10","D11","D12",
            "E01","E02","E03","E04","E05","E06","E07","E08","E09","E10","E11","E12",
            "F01","F02","F03","F04","F05","F06","F07","F08","F09","F10","F11","F12",
            "G01","G02","G03","G04","G05","G06","G07","G08","G09","G10","G11","G12",
            "H01","H02","H03","H04","H05","H06","H07","H08","H09","H10","H11","H12"
    };


    public static final String[] SYTABLE_POSTION_ARR_VER = new String[]{
            "A01","B01","C01","D01","E01","F01","G01","H01",
            "A02","B02","C02","D02","E02","F02","G02","H02",
            "A03","B03","C03","D03","E03","F03","G03","H03",
            "A04","B04","C04","D04","E04","F04","G04","H04",
            "A05","B05","C05","D05","E05","F05","G05","H05",
            "A06","B06","C06","D06","E06","F06","G06","H06",
            "A07","B07","C07","D07","E07","F07","G07","H07",
            "A08","B08","C08","D08","E08","F08","G08","H08",
            "A09","B09","C09","D09","E09","F09","G09","H09",
            "A10","B10","C10","D10","E10","F10","G10","H10",
            "A11","B11","C11","D11","E11","F11","G11","H11",
            "A12","B12","C12","D12","E12","F12","G12","H12",
    };

    public static final String GENDER_MALE_CODE = "1";
    public static final String GENDER_FEMALE_CODE = "2";
    public static final String GENDER_OTHERS_CODE = "3";


    public static final String GENDER_MALE_NAME = "男";
    public static final String GENDER_FEMALE_NAME = "女";
    public static final String GENDER_OTHERS_NAME = "其他";



    public static final String LIVE_STATUS_ALIVE_CODE = "1";
    public static final String LIVE_STATUS_DEATH_CODE = "2";
    public static final String LIVE_STATUS_UNKNOWN_CODE = "09";

    public static final String LIVE_STATUS_ALIVE_NAME = "在世";
    public static final String LIVE_STATUS_DEATH_NAME = "去世";
    public static final String LIVE_STATUS_UNKNOWN_NAME = "未知";


    public static final String HUJI_STATUS_INVALID_CODE = "0";
    public static final String HUJI_STATUS_VALID_CODE = "1";
    public static final String HUJI_STATUS_UNREGISTE_CODE = "2";

    public static final String HUJI_STATUS_INVALID_NAME = "无效";
    public static final String HUJI_STATUS_VALID_NAME = "有效";
    public static final String HUJI_STATUS_UNREGISTE_NAME = "未入户";

    public static final String PERSON_DATA_SOURCE_HUJI = "0";           //户籍
    public static final String PERSON_DATA_SOURCE_MANUAL = "1";           //手动输入
    public static final String PERSON_DATA_SOURCE_OTHER = "2";           //其他


    public static final String INTO_FAMILY_MODE_DISHENG = "01";         //嫡生
    public static final String INTO_FAMILY_MODE_SHOUYANG = "02";        //收养
    public static final String INTO_FAMILY_MODE_BAOYANG = "03";         //抱养
    public static final String INTO_FAMILY_MODE_FEIHUNSHENG = "04";     //非婚生
    public static final String INTO_FAMILY_MODE_RUZHUI = "05";          //入赘
    public static final String INTO_FAMILY_MODE_QURUZHUI = "06";        //去入赘
    public static final String INTO_FAMILY_MODE_SUIMURU = "07";         //随母入
    public static final String INTO_FAMILY_MODE_SUIMUCHU = "08";         //随母出
    public static final String INTO_FAMILY_MODE_QIANCHU = "09";         //迁出

    public static final String USER_TYPE_ADMIN = "1";   //管理员
    public static final String USER_TYPE_LAB = "2";     //实验室
    public static final String USER_TYPE_DCY = "3";     //调查员


    public static final String VILLAGE_CODE_UNKNOWN = "9999999999";         //未知村

    public static final String INTO_FAMILY_MODE_DEFAULT = "01";             //嫡生

    public static final String SAMPLE_STATUS_DEFAULT = "0";                   //未送检
    public static final String SAMPLE_STATUS_DELEGATED = "1";                 //已送检
    public static final String SAMPLE_STATUS_ACCEPTED = "2";                 //已受理
    public static final String SAMPLE_STATUS_REFUSED = "3";                 //不受理

    //混合类型：国家库类型为：1
    public static final String GENE_MIXED = "4";
    //Y基因型类型：国家库类型为：2
    public static final String GENE_YSTR = "5";
    //常规类型：国家库类型为：1
    public static final String GENE_NORMAL = "0";

    //上报状态
    public static final String TRANSFER_STATUS_SUCCESS = "2";              //上报成功
    public static final String TRANSFER_STATUS_FAILED = "-2";              //上报失败
    public static final String TRANSFER_STATUS_WAITED = "0";               //待上报
    public static final String TRANSFER_STATUS_GENERATE_SUCCESS = "1";     //生成文件成功
    public static final String TRANSFER_STATUS_GENERATE_FAILED = "-1";     //生成文件失败


    /**
     * 案件样本基因
     */
    public static final int GENE_DATA_SOURCE_CASE = 1;
    /**
     * 建库样本基因
     */
    public static final int GENE_DATA_SOURCE_CRIMINAL = 2;


    //已比对
    public static final String MATCH_STATUS_01 = "1";
    //未比对
    public static final String MATCH_STATUS_02 = "2";
    //入库比
    public static final String MATCH_STATUS_03 = "3";
    //比对中
    public static final String MATCH_STATUS_04 = "4";

    /* 新的比对结果类型 */
    //混合比单一
    public static final String RESULT_TYPE_01= "01";
    //拆分比混合
    public static final String RESULT_TYPE_02= "02";
    //混合比质控
    public static final String RESULT_TYPE_03= "03";
    //拆分比单一
    public static final String RESULT_TYPE_04= "04";


    /*
     *   消息标记
     * */
    //是否忽略：  否
    public static final String MOBILE_FLAG_0="0";
    //是否忽略：  是
    public static final String MOBILE_FLAG_1="1";

}
