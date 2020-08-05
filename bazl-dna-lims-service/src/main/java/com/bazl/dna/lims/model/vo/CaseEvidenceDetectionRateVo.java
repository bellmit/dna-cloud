package com.bazl.dna.lims.model.vo;

import java.io.Serializable;

/**
 * 案件物证检出率统计
 *
 * @author  lizhihua on 2020/7/28
 */
public class CaseEvidenceDetectionRateVo implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String orgCode;
    private String orgName;

    private Integer caseCount;
    private Integer evidenceCount;
    private Integer matchedEvidenceCount;
    private Integer instoreEvidenceCount;

    private double detectionRate;

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

    public Integer getCaseCount() {
        return caseCount;
    }

    public void setCaseCount(Integer caseCount) {
        this.caseCount = caseCount;
    }

    public Integer getEvidenceCount() {
        return evidenceCount;
    }

    public void setEvidenceCount(Integer evidenceCount) {
        this.evidenceCount = evidenceCount;
    }

    public Integer getMatchedEvidenceCount() {
        return matchedEvidenceCount;
    }

    public void setMatchedEvidenceCount(Integer matchedEvidenceCount) {
        this.matchedEvidenceCount = matchedEvidenceCount;
    }

    public Integer getInstoreEvidenceCount() {
        return instoreEvidenceCount;
    }

    public void setInstoreEvidenceCount(Integer instoreEvidenceCount) {
        this.instoreEvidenceCount = instoreEvidenceCount;
    }

    public double getDetectionRate() {
        return detectionRate;
    }

    public void setDetectionRate(double detectionRate) {
        this.detectionRate = detectionRate;
    }
}
