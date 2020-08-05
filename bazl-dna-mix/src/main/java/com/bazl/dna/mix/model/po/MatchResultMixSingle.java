package com.bazl.dna.mix.model.po;

import java.io.Serializable;
import java.util.Date;

/**
 * MATCH_RESULT_MIX_SINGLE
 * @author 
 */
public class MatchResultMixSingle implements Serializable, Comparable<MatchResultMixSingle> {
    /**
     * 主键
     */
    private String id;
    /**
     * 混合id
     */
    private String mixedSampleGendId;

    /**
     * 单一id
     */
    private String singlegeneId;

    /**
     * 比中数
     */
    private Short ratio;

    /**
     * 差异数
     */
    private Short splitDigit;

    /**
     * 比中时间
     */
    private Date comparisonTime;

    /**
     * 基因座数量
     */
    private Integer sumCount;

    /**
     * 拆分id
     */
    private String splitedSampleGeneId;

    /**
     * 结果类型(1.混合比对单一2.混合比对拆分)
     */
    private String sampleGeneResultType;

    private String sampleNo;

    private String sampleName;

    private String sampleType;

    //业务字段  分组名称
    private String groupName;

    //业务字段  比中类型
    private String sampleFlag;

    public String getSampleFlag() {
        return sampleFlag;
    }

    public void setSampleFlag(String sampleFlag) {
        this.sampleFlag = sampleFlag;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getSplitedSampleGeneId() {
        return splitedSampleGeneId;
    }

    public void setSplitedSampleGeneId(String splitedSampleGeneId) {
        this.splitedSampleGeneId = splitedSampleGeneId;
    }

    public String getSampleGeneResultType() {
        return sampleGeneResultType;
    }

    public void setSampleGeneResultType(String sampleGeneResultType) {
        this.sampleGeneResultType = sampleGeneResultType;
    }

    public String getSampleNo() {
        return sampleNo;
    }

    public void setSampleNo(String sampleNo) {
        this.sampleNo = sampleNo;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public Integer getSumCount() {
        return sumCount;
    }

    public void setSumCount(Integer sumCount) {
        this.sumCount = sumCount;
    }

    private static final long serialVersionUID = 1L;

    public String getMixedSampleGendId() {
        return mixedSampleGendId;
    }

    public void setMixedSampleGendId(String mixedSampleGendId) {
        this.mixedSampleGendId = mixedSampleGendId;
    }

    public String getSinglegeneId() {
        return singlegeneId;
    }

    public void setSinglegeneId(String singlegeneId) {
        this.singlegeneId = singlegeneId;
    }

    public Short getRatio() {
        return ratio;
    }

    public void setRatio(Short ratio) {
        this.ratio = ratio;
    }

    public Short getSplitDigit() {
        return splitDigit;
    }

    public void setSplitDigit(Short splitDigit) {
        this.splitDigit = splitDigit;
    }

    public Date getComparisonTime() {
        return comparisonTime;
    }

    public void setComparisonTime(Date comparisonTime) {
        this.comparisonTime = comparisonTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "MatchResultMixSingle{" +
                "id='" + id + '\'' +
                ", mixedSampleGendId='" + mixedSampleGendId + '\'' +
                ", singlegeneId='" + singlegeneId + '\'' +
                ", ratio=" + ratio +
                ", splitDigit=" + splitDigit +
                ", comparisonTime=" + comparisonTime +
                '}';
    }

    @Override
    public int compareTo(MatchResultMixSingle o) {
        int str = Integer.parseInt(this.groupName.substring(6));
        int otr = Integer.parseInt(o.groupName.substring(6));
        if (str>otr){
            return 1;
        }else if (str<otr){
        return -1;
        }
        return 0;
    }
}