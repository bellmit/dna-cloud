package com.bazl.dna.mix.constants;

/**
 * Created by Sun on 2016/12/28.
 */
public class Constants {

    //检材类型
    public static final String SAMPLE_TYPE = "SAMPLE_TYPE";

    //是否比对（0：否）
    public static final String QUEUE_FLAG_0 = "0";
    //1：是
    public static final String QUEUE_FLAG_1 = "1";

    //已比对
    public static final String MATCH_STATUS_01 = "1";
    //未比对
    public static final String MATCH_STATUS_02 = "2";
    //入库比
    public static final String MATCH_STATUS_03 = "3";
    //比对中
    public static final String MATCH_STATUS_04 = "4";


    //删除标识
    //删除
    public static final String IS_DELETED_1 = "1";
    //未删除
    public static final String IS_DELETED_0 = "0";

    //入库标识（0：未入库）
    public static final String INSTORED_FLAG_STATUS_0 = "0";
    // 1：已入本地库
    public static final String INSTORED_FLAG_STATUS_1 = "1";

    //结果类型(1.混合比对单一)
    public static final String SAMPLEGENERESULT_TYPE_1= "1";
    // 2.混合比对拆分
    public static final String SAMPLEGENERESULT_TYPE_2 = "2";
    //全库比对结果类型(比对单一)
    public static final String SAMPLEGENERESULT_TYPE_3= "3";
    // (比对质控)
    public static final String SAMPLEGENERESULT_TYPE_4 = "4";

    /* 新的比对结果类型 */
    //混合比单一
    public static final String RESULT_TYPE_01= "01";
    //拆分比混合
    public static final String RESULT_TYPE_02= "02";
    //混合比质控
    public static final String RESULT_TYPE_03= "03";
    //拆分比单一
    public static final String RESULT_TYPE_04= "04";

    //混合基因分型
    public static final String QUEUE_TYPE_01 = "1";
    //拆分基因分型
    public static final String QUEUE_TYPE_02 = "2";

    //大于99%的位点的权重；自定义权重的百分比
    public static final double FDNINETY_NINEPERCENT_WEIGHTS_90=90.00;
    public static final double FDNINETY_NINEPERCENT_WEIGHTS_91 =91.00;
    public static final double FDNINETY_NINEPERCENT_WEIGHTS_92 =92.00;
    public static final double FDNINETY_NINEPERCENT_WEIGHTS_93=93.00;
    public static final double FDNINETY_NINEPERCENT_WEIGHTS_94=94.00;
    public static final double FDNINETY_NINEPERCENT_WEIGHTS_95=95.00;
    public static final double FDNINETY_NINEPERCENT_WEIGHTS_96=96.00;
    public static final double FDNINETY_NINEPERCENT_WEIGHTS_97=97.00;
    public static final double FDNINETY_NINEPERCENT_WEIGHTS_98=98.00;
    public static final double FDNINETY_NINEPERCENT_WEIGHTS_99=99.00;
    //大于99%的位点的权重；自定义权重的百分比
    public static final String FDNINETY_WEIGHTS_90="90%";
    public static final String FDNINETY_WEIGHTS_91 ="91%";
    public static final String FDNINETY_WEIGHTS_92 ="92%";
    public static final String FDNINETY_WEIGHTS_93="93%";
    public static final String FDNINETY_WEIGHTS_94="94%";
    public static final String FDNINETY_WEIGHTS_95="95%";
    public static final String FDNINETY_WEIGHTS_96="96%";
    public static final String FDNINETY_WEIGHTS_97="97%";
    public static final String FDNINETY_WEIGHTS_98="98%";
    public static final String FDNINETY_WEIGHTS_99="99%";

    //判断混合是否为空
    public static final String AUTOMATIC_STATUS_0 ="0";

    public static final String AUTOMATIC_STATUS_1 ="1";
    //自动拆分(区分混合点数 1 和单一点数 0)
    public static final String IDENTIFICATION_0 ="0";

    public static final String IDENTIFICATION_1 ="1";

    //自定义获取最新混合基因信息个数
    public static final int GENECOUNT =100;

    //首页根据类型查询统计数量
    //首页查询混合样本比中嫌疑人的总数
    public static final String DICT_TYPE_CODE_SUSPECTS_NUMBER ="SUSPECTS_NUMBER";

   //首页查询混合基因串并案比中嫌疑人总数
    public static final String DICT_TYPE_CODE_SERIAL_COUNT="SERIAL_COUNT";

    //首页查询混合样本比中质控人员的总数
    public static final String DICT_TYPE_CODE_MIXEDQUALITY_COUNT="MIXEDQUALITY_COUNT";

    //查询混合样本总数
    public static final String DICT_TYPE_CODE_MIXED_GENE_COUNT="MIXED_GENE_COUNT";

    //查询单一样本总数
    public static final String DICT_TYPE_CODE_SINGLE_GENE_COUNT="SINGLE_GENE_COUNT";
    //样品编号
    public static final String SAMPLE_NO="SAMPLE_NO";

    /*
    *   消息标记
    * */
    //是否忽略：  否
    public static final String MOBILE_FLAG_0="0";
    //是否忽略：  是
    public static final String MOBILE_FLAG_1="1";


    /*
    *   数量类型
    * */
    //混合样本
    public static final String DICT_TYPE_MIXCOUNT_SAMPLE= "SAMPLEMIXCOUNT";
    //单一样本
    public static final String DICT_TYPE_SINGCOUNT_SAMPLE = "SAMPLESINGCOUNT";
    //拆分分型
    public static final String DICT_TYPE_SPLITCOUNT_TYPING= "TYPINGSPLITCOUNT";
    //混合分型
    public static final String DICT_TYPE_MIXCOUNT_TYPING = "TYPINGMIXCOUNT";


    /* 比对列表数据来源 */
    //DNA分型快速拆分
    public static final String SOURCE_TYPE_1 = "1";
    //STR导入
    public static final String SOURCE_TYPE_2 = "2";

    /* 比对结果表有效标识 */
    //无效
    public static final String EFFECT_FLAG_0 = "0";
    //有效
    public static final String EFFECT_FLAG_1 = "1";


    //混合库人员类型
    public static final String PERSON_TYPE_1 = "质控人员";
    public static final String PERSON_TYPE_2 = "违法犯罪人员";
    public static final String PERSON_TYPE_3 = "嫌疑人";
    public static final String PERSON_TYPE_4 = "受害人";
    public static final String PERSON_TYPE_5 = "现场物证";
    public static final String PERSON_TYPE_6 = "未知名尸体";
    public static final String PERSON_TYPE_7= "失踪人员物品";
    //混合库人员类型key
    public static final String PERSON_TYPE_1_CODE= "01";
    public static final String PERSON_TYPE_2_CODE = "02";
    public static final String PERSON_TYPE_3_CODE = "03";
    public static final String PERSON_TYPE_4_CODE = "04";
    public static final String PERSON_TYPE_5_CODE = "05";
    public static final String PERSON_TYPE_6_CODE = "06";
    public static final String PERSON_TYPE_7_CODE=  "07";

    //国家库人员类型DICT_key
    public static final String PERSON_TYPE_1_DICT= "03,0301";
    public static final String PERSON_TYPE_2_DICT = "010102";
    public static final String PERSON_TYPE_3_DICT = "020203";
    public static final String PERSON_TYPE_4_DICT = "020201";
    public static final String PERSON_TYPE_5_DICT = "010201,020101";
    public static final String PERSON_TYPE_6_DICT = "010401";
    public static final String PERSON_TYPE_7_DICT =  "0103,010301,010302,010303,010306,010307,010308,010309,010310,010311,020202,020204,060103";


}
