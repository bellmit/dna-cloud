package com.bazl.dna.database.constants;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * 基因相关常量定义
 * Created by lizhihua on 2020-03-10.
 */
public class GeneConstants implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 等位基因分隔符
     * 如：12/13， 标识某一基因位点上的两个等位基因12和13， 以"/"分隔
     */
    public static final String GENE_ALLELE_SEPARATOR_CHAR = "/";

    /**
     * 性别基因座名称集合
     */
    public static final List<String> GENDER_LOCUS_NAME_LIST = Arrays.asList(
        "amel", "Amel", "AMEL", "am", "Am", "AM", "amelogenin", "AMELOGENIN", "Amelogenin"
    );

}
