package com.bazl.dna.lims.model.view;

import java.io.Serializable;

/**
 * 检材检出率 物证入库率公用
 * Created by wangliu on 2019/12/13.
 */
public class DbIvDetectionRateView implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 单位code
     */
    private String delegateOrg;
    /**
     * 单位名称
     */
    private String delegateOrgName;
    /**
     * 送检日期
     */
    private String delegateAt;

    /**
     * 送检日期（起）
     */
    private String delegateAtStart;
    /**
     * 送检日期（止）
     */
    private String delegateAtEnd;
    /**
     * 样本编号
     */
    private String sampleNo;
    /**
     * 样本类型
     */
    private String sampleType;


    /**
     * 有比中数
     */
    private String comparisonCount;
    /**
     * （未比中）已入库的个数
     */
    private String notComparisonRkCount;
    /**
     * 总的样本数
     */
    private String sampleCount;
    /**
     * 计算结果
     */
    private String computedResult;

    public String getDelegateOrg() {
        return delegateOrg;
    }

    public void setDelegateOrg(String delegateOrg) {
        this.delegateOrg = delegateOrg;
    }

    public String getDelegateOrgName() {
        return delegateOrgName;
    }

    public void setDelegateOrgName(String delegateOrgName) {
        this.delegateOrgName = delegateOrgName;
    }

    public String getDelegateAt() {
        return delegateAt;
    }

    public void setDelegateAt(String delegateAt) {
        this.delegateAt = delegateAt;
    }

    public String getDelegateAtStart() {
        return delegateAtStart;
    }

    public void setDelegateAtStart(String delegateAtStart) {
        this.delegateAtStart = delegateAtStart;
    }

    public String getDelegateAtEnd() {
        return delegateAtEnd;
    }

    public void setDelegateAtEnd(String delegateAtEnd) {
        this.delegateAtEnd = delegateAtEnd;
    }

    public String getSampleNo() {
        return sampleNo;
    }

    public void setSampleNo(String sampleNo) {
        this.sampleNo = sampleNo;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public String getComparisonCount() {
        return comparisonCount;
    }

    public void setComparisonCount(String comparisonCount) {
        this.comparisonCount = comparisonCount;
    }

    public String getNotComparisonRkCount() {
        return notComparisonRkCount;
    }

    public void setNotComparisonRkCount(String notComparisonRkCount) {
        this.notComparisonRkCount = notComparisonRkCount;
    }

    public String getSampleCount() {
        return sampleCount;
    }

    public void setSampleCount(String sampleCount) {
        this.sampleCount = sampleCount;
    }

    public String getComputedResult() {
        return computedResult;
    }

    public void setComputedResult(String computedResult) {
        this.computedResult = computedResult;
    }

}
