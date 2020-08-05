package com.bazl.dna.database.algorithm.result;

import java.io.Serializable;
import java.util.List;

/**
 * STR 同型比对结果对象
 * Created by lizhihua on 2019-07-26.
 */
public class StrSameCompareResult implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 匹配基因座个数
     */
    private int matchedLocusCount;

    /**
     * 等位基因容差数
     */
    private int diffAlleleCount;

    /**
     * 累积似然比率
     */
    private Double totalLikelihoodRate;

    /**
     * 基因座等位基因比对结果列表
     */
    private List<StrSameCompareResultAllele> strSameCompareResultAlleleList;


    public int getMatchedLocusCount() {
        return matchedLocusCount;
    }

    public void setMatchedLocusCount(int matchedLocusCount) {
        this.matchedLocusCount = matchedLocusCount;
    }

    public int getDiffAlleleCount() {
        return diffAlleleCount;
    }

    public void setDiffAlleleCount(int diffAlleleCount) {
        this.diffAlleleCount = diffAlleleCount;
    }

    public Double getTotalLikelihoodRate() {
        return totalLikelihoodRate;
    }

    public void setTotalLikelihoodRate(Double totalLikelihoodRate) {
        this.totalLikelihoodRate = totalLikelihoodRate;
    }

    public List<StrSameCompareResultAllele> getStrSameCompareResultAlleleList() {
        return strSameCompareResultAlleleList;
    }

    public void setStrSameCompareResultAlleleList(List<StrSameCompareResultAllele> strSameCompareResultAlleleList) {
        this.strSameCompareResultAlleleList = strSameCompareResultAlleleList;
    }
}
