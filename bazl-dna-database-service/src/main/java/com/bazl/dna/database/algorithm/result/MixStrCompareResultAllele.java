package com.bazl.dna.database.algorithm.result;

import java.io.Serializable;

import lombok.Data;

/**
 * 混合比对结果基因座信息表
 * @author lizhihua on 2020-04-21.
 */
@Data
public class MixStrCompareResultAllele implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 基因座名称
     */
    private String locusName;

    /**
     * 混合样本等位基因
     */
    private String mixGeneAllele;

    /**
     * 目标样本等位基因
     */
    private String targetGeneAllele;

    /**
     * 标记该基因座是否为差异等位基因
     */
    private boolean diffAllele;

}
