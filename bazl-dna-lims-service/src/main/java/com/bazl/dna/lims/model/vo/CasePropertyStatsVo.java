package com.bazl.dna.lims.model.vo;

import java.io.Serializable;

/**
 * 案件性质统计实体
 *
 * Created by Administrator on 2017/1/4.
 */
public class CasePropertyStatsVo implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String orgCode;
    private String orgName;
    private String caseProperty;
    private String casePropertyName;
    private int caseCount;
    private int sampleCount;

    public int getSampleCount() {
        return sampleCount;
    }

    public void setSampleCount(int sampleCount) {
        this.sampleCount = sampleCount;
    }

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

    public String getCaseProperty() {
        return caseProperty;
    }

    public void setCaseProperty(String caseProperty) {
        this.caseProperty = caseProperty;
    }

    public String getCasePropertyName() {
        return casePropertyName;
    }

    public void setCasePropertyName(String casePropertyName) {
        this.casePropertyName = casePropertyName;
    }

    public int getCaseCount() {
        return caseCount;
    }

    public void setCaseCount(int caseCount) {
        this.caseCount = caseCount;
    }
}
