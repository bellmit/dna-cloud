package com.bazl.dna.mix.compare.algorithm.constants;

import java.util.Arrays;
import java.util.List;

/**
 * 算法类常量表
 * @author lizhihua on 2020-04-21.
 */
public class AlgorithmConstants {

    public static final String GENE_ALLELE_VALUE_SEPORATOR = "/";

    /**
     * 目前系统支持的混合分型的贡献者数量为2和3，即：2人混合或3人混合的DNA分型
     */
    public static final List<Integer> CONTRIBUTOR_COUNT_LIST = Arrays.asList(2,3);

}
