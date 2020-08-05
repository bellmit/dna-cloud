package com.bazl.dna.mix.compare.algorithm.result;

import com.bazl.dna.mix.compare.algorithm.model.DnaGeneInfoDetail;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 混合比对结果
 * @author on 2020-04-21.
 */
@Data
public class MixStrCompareResult implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
     * 匹配基因座个数
     */
    private int matchedLocusCount;

    /**
     * 等位基因容差数
     */
    private int diffLocusCount;

    /**
     * 比对目标位点数
     */
    private int proportionCount;

    /**
     * 基因座等位基因比对结果列表
     */
    private List<MixStrCompareResultAllele> mixStrCompareResultAlleleList;

    /**
     * 所有位点上根据混合分型拆分出的可能性组合
     */
    private List<Map<String, List<String>>> contributorGenePossibilityList;

    //所有位点上根据混合分型拆分出的可能性组合----格式转换----
    private List<DnaGeneInfoDetail> dnaGeneInfoDetailsList;

}
