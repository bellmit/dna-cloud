package com.bazl.dna.database.nation.converter.constants;

/**
 * @AUTHOR lizhihua on 2020/06/22.
 */
public class ConverterConstants {


    /**
     * 案件委托
     */
    public static final String CONSIGNMENT_TYPE_CASE = "1";

    /**
     * 身份不明人员委托
     */
    public static final String CONSIGNMENT_TYPE_UNKONWN_PERSON = "2";

    /**
     * 失踪人口委托
     */
    public static final String CONSIGNMENT_TYPE_MISSING_PERSON = "3";


    /**
     * 建库委托
     */
    public static final String CONSIGNMENT_TYPE_CRIMINAL_PERSON = "4";


    /**
     * 灾难委托
     */
    public static final String CONSIGNMENT_TYPE_DIASTER_PERSON = "5";

    /**
     * 质控委托
     */
    public static final String CONSIGNMENT_TYPE_QUALITY_PERSON = "6";

    /**
     * 基础对象委托
     */
    public static final String CONSIGNMENT_TYPE_BASIC_PERSON = "8";

    /**
     * 打拐儿童委托
     */
    public static final String CONSIGNMENT_TYPE_ABDUCTION_CHILD = "10";

    /**
     * 打拐父母委托
     */
    public static final String CONSIGNMENT_TYPE_ABDUCTION_PARENT = "11";


    /**
     * 人员关系常量
     */
    /**
     * 人员自身
     */
    public static final String SAMPLE_RELATION_SELF = "9";
    /**
     * 疑似人员自身
     */
    public static final String SAMPLE_RELATION_SELF_PROBABLY = "10";
    /**
     * 生物学父
     */
    public static final String SAMPLE_RELATION_FATHER = "1";
    /**
     * 生物学母
     */
    public static final String SAMPLE_RELATION_MOTHER = "2";
    /**
     * 生物学子女
     */
    public static final String SAMPLE_RELATION_CHILDS = "3";
    /**
     * 生物学兄弟姐妹
     */
    public static final String SAMPLE_RELATION_BROTHERS_SISTERS = "4";
    /**
     * 父系亲属
     */
    public static final String SAMPLE_RELATION_FATHER_RELATIVE = "5";
    /**
     * 母系亲属
     */
    public static final String SAMPLE_RELATION_MOTHER_RELATIVE = "6";
    /**
     * 男性配偶
     */
    public static final String SAMPLE_RELATION_MALE_SPOUSE = "7";
    /**
     * 女性配偶
     */
    public static final String SAMPLE_RELATION_FEMALE_SPOUSE = "8";

    /**
     * 案件样本基因
     */
    public static final int GENE_DATA_SOURCE_CASE = 1;
    /**
     * 建库样本基因
     */
    public static final int GENE_DATA_SOURCE_CRIMINAL = 2;



}
