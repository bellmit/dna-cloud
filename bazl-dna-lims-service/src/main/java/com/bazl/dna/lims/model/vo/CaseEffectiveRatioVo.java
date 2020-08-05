package com.bazl.dna.lims.model.vo;

import java.io.Serializable;

/**
 * @author wanghaiyang
 * @date 2020/7/31 13:40
 */
public class CaseEffectiveRatioVo implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String orgCode;
    private String orgName;

    private Integer caseTotalCount;
    private Integer matchSuspectedCaseCount;
    private Integer matchSampleCaseCount;
    private Integer otherInStorgeCaseCount;

    private double medianRate;

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Integer getCaseTotalCount() {
        return caseTotalCount;
    }

    public void setCaseTotalCount(Integer caseTotalCount) {
        this.caseTotalCount = caseTotalCount;
    }

    public Integer getMatchSuspectedCaseCount() {
        return matchSuspectedCaseCount;
    }

    public void setMatchSuspectedCaseCount(Integer matchSuspectedCaseCount) {
        this.matchSuspectedCaseCount = matchSuspectedCaseCount;
    }

    public Integer getMatchSampleCaseCount() {
        return matchSampleCaseCount;
    }

    public void setMatchSampleCaseCount(Integer matchSampleCaseCount) {
        this.matchSampleCaseCount = matchSampleCaseCount;
    }

    public Integer getOtherInStorgeCaseCount() {
        return otherInStorgeCaseCount;
    }

    public void setOtherInStorgeCaseCount(Integer otherInStorgeCaseCount) {
        this.otherInStorgeCaseCount = otherInStorgeCaseCount;
    }

    public double getMedianRate() {
        return medianRate;
    }

    public void setMedianRate(double medianRate) {
        this.medianRate = medianRate;
    }
}
