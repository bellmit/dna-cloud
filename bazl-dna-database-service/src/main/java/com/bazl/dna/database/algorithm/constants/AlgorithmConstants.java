package com.bazl.dna.database.algorithm.constants;

import java.util.Arrays;
import java.util.List;

/**
 * DNA算法 常量定义类
 * Created by lizhihua on 2020/2/26.
 */
public class AlgorithmConstants {

    //父子单亲比对
    public static final String SINGLE_RELATIVE_FATHER_CHILD = "1";
    //母子单亲比对
    public static final String SINGLE_RELATIVE_MOTHER_CHILD = "2";


    public static final String GENE_ALLELE_VALUE_SEPORATOR = "/";

    /**
     * 目前系统支持的混合分型的贡献者数量为2和3，即：2人混合或3人混合的DNA分型
     */
    public static final List<Integer> CONTRIBUTOR_COUNT_LIST = Arrays.asList(2,3);

}
