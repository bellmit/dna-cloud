package com.bazl.dna.database.algorithm.result;

import java.io.Serializable;
import java.util.List;

/**
 *
 * 亲缘比对结果
 *
 * Created by lizhihua on 2020-03-10.
 */
public class StrRelativeCompareResult implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 是否比中标记
     */
    private boolean matchedFlag;

    /**
     * 匹配基因座个数
     */
    private int matchedLocusCount;

    /**
     * 等位基因容差数
     */
    private int diffAlleleCount;

    /**
     * 累积父子亲权指数
     */
    private Double totalFatherPiVal;

    /**
     * 累积母子亲权指数
     */
    private Double totalMotherPiVal;

    /**
     * 累积亲权指数
     */
    private Double totalPiVal;

    /**
     * 根据pi值计算出的总匹配个数
     */
    private int totalMatchedPiCount;
    /**
     * 根据pi值计算出的父子匹配个数
     */
    private int fatherMatchedPiCount;
    /**
     * 根据pi值计算出的母子匹配个数
     */
    private int motherMatchedPiCount;

    /**
     * 单亲比对标记
     */
    private boolean singleRelative;


    /**
     * 基因座等位基因比对结果列表
     */
    private List<StrRelativeCompareResultAllele> strRelativeCompareResultAlleleList;

    public boolean isMatchedFlag() {
        return matchedFlag;
    }

    public void setMatchedFlag(boolean matchedFlag) {
        this.matchedFlag = matchedFlag;
    }

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

    public Double getTotalFatherPiVal() {
        return totalFatherPiVal;
    }

    public void setTotalFatherPiVal(Double totalFatherPiVal) {
        this.totalFatherPiVal = totalFatherPiVal;
    }

    public Double getTotalMotherPiVal() {
        return totalMotherPiVal;
    }

    public void setTotalMotherPiVal(Double totalMotherPiVal) {
        this.totalMotherPiVal = totalMotherPiVal;
    }

    public Double getTotalPiVal() {
        return totalPiVal;
    }

    public void setTotalPiVal(Double totalPiVal) {
        this.totalPiVal = totalPiVal;
    }

    public boolean isSingleRelative() {
        return singleRelative;
    }

    public void setSingleRelative(boolean singleRelative) {
        this.singleRelative = singleRelative;
    }

    public int getTotalMatchedPiCount() {
        return totalMatchedPiCount;
    }

    public void setTotalMatchedPiCount(int totalMatchedPiCount) {
        this.totalMatchedPiCount = totalMatchedPiCount;
    }

    public int getFatherMatchedPiCount() {
        return fatherMatchedPiCount;
    }

    public void setFatherMatchedPiCount(int fatherMatchedPiCount) {
        this.fatherMatchedPiCount = fatherMatchedPiCount;
    }

    public int getMotherMatchedPiCount() {
        return motherMatchedPiCount;
    }

    public void setMotherMatchedPiCount(int motherMatchedPiCount) {
        this.motherMatchedPiCount = motherMatchedPiCount;
    }

    public List<StrRelativeCompareResultAllele> getStrRelativeCompareResultAlleleList() {
        return strRelativeCompareResultAlleleList;
    }

    public void setStrRelativeCompareResultAlleleList(List<StrRelativeCompareResultAllele> strRelativeCompareResultAlleleList) {
        this.strRelativeCompareResultAlleleList = strRelativeCompareResultAlleleList;
    }
}
