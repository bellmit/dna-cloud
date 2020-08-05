package com.bazl.dna.database.algorithm.result;

import java.io.Serializable;
import java.util.List;

/**
 * Ystr比对结果对象
 * Created by lizhihua on 2020-03-18.
 */
public class YstrCompareResult implements Serializable {

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
     * 基因座等位基因比对结果列表
     */
    private List<YstrCompareResultAllele> ystrCompareResultAlleleList;

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

    public List<YstrCompareResultAllele> getYstrCompareResultAlleleList() {
        return ystrCompareResultAlleleList;
    }

    public void setYstrCompareResultAlleleList(List<YstrCompareResultAllele> ystrCompareResultAlleleList) {
        this.ystrCompareResultAlleleList = ystrCompareResultAlleleList;
    }
}
