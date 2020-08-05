package com.bazl.dna.database.service.model.qo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author
 */

public class MatchStrResultQuery extends AbstractQuery implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 案件主键id
     */
    private int id;

    private Integer groupId;

    /**
     *案件名称
     */
    private String caseName;
    private Integer caseId;

    /**
     *样本条码
     */
    private String sampleNo;

    /**
     *检材名称
     */
    private String sampleName;

    /**
     *检材类型
     */
    private String sampleType;
    private String sampleTypeName;

    /**
     *匹配位点个数
     */
    private Integer matchLocusCount;

    /**
     * 单位名称
     */
    private String unitName;

    /**
     * 比中起止时间
     */
    private Date compareStartDatetime;
    private Date compareEndDatetime;
    private Date caseAcceptStartDatetime;
    private Date caseAcceptEndDatetime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date matchDateTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date caseAcceptDateTime;
    
    private Integer matchSampleId;
    private String matchSampleNo;
    private String matchSampleName;
    private String matchCaseName;
    
    private String personName;
    private String personCard;
    private String orgCode;
    private String orgName;
    
    /**
     * 采集单位集合
     */
    private List<String> clientOrgList;
    /**
     * 案件编号
     */
    private String caseAcceptNo;
    
    /**
     * 案件性质
     */
    private String caseProperty;
    
    /**
     * 查询条件
     */
    private String[] sampleTypes;
    private String[] casePropertys;
    
    /**
     * 现场勘验编号
     */
    private String sysXkNo;

    /**
     * 比中状态
     */
    private Integer reviewFlag;
    
    /**
     * 复核状态
     */
    private String reviewResultCode;

    /**
     * 比对结果类别
     */
    private String groupType;

    /**
     * 案件破获状态
     */
    private String caseStates;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public Integer getCaseId() {
        return caseId;
    }

    public void setCaseId(Integer caseId) {
        this.caseId = caseId;
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

    public String getSampleTypeName() {
        return sampleTypeName;
    }

    public void setSampleTypeName(String sampleTypeName) {
        this.sampleTypeName = sampleTypeName;
    }

    public Integer getMatchLocusCount() {
        return matchLocusCount;
    }

    public void setMatchLocusCount(Integer matchLocusCount) {
        this.matchLocusCount = matchLocusCount;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Date getCompareStartDatetime() {
        return compareStartDatetime;
    }

    public void setCompareStartDatetime(Date compareStartDatetime) {
        this.compareStartDatetime = compareStartDatetime;
    }

    public Date getCompareEndDatetime() {
        return compareEndDatetime;
    }

    public void setCompareEndDatetime(Date compareEndDatetime) {
        this.compareEndDatetime = compareEndDatetime;
    }

    public Date getCaseAcceptStartDatetime() {
        return caseAcceptStartDatetime;
    }

    public void setCaseAcceptStartDatetime(Date caseAcceptStartDatetime) {
        this.caseAcceptStartDatetime = caseAcceptStartDatetime;
    }

    public Date getCaseAcceptEndDatetime() {
        return caseAcceptEndDatetime;
    }

    public void setCaseAcceptEndDatetime(Date caseAcceptEndDatetime) {
        this.caseAcceptEndDatetime = caseAcceptEndDatetime;
    }

    public Date getMatchDateTime() {
        return matchDateTime;
    }

    public void setMatchDateTime(Date matchDateTime) {
        this.matchDateTime = matchDateTime;
    }

    public Date getCaseAcceptDateTime() {
        return caseAcceptDateTime;
    }

    public void setCaseAcceptDateTime(Date caseAcceptDateTime) {
        this.caseAcceptDateTime = caseAcceptDateTime;
    }

    public Integer getMatchSampleId() {
        return matchSampleId;
    }

    public void setMatchSampleId(Integer matchSampleId) {
        this.matchSampleId = matchSampleId;
    }

    public String getMatchSampleNo() {
        return matchSampleNo;
    }

    public void setMatchSampleNo(String matchSampleNo) {
        this.matchSampleNo = matchSampleNo;
    }

    public String getMatchSampleName() {
        return matchSampleName;
    }

    public void setMatchSampleName(String matchSampleName) {
        this.matchSampleName = matchSampleName;
    }

    public String getMatchCaseName() {
        return matchCaseName;
    }

    public void setMatchCaseName(String matchCaseName) {
        this.matchCaseName = matchCaseName;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonCard() {
        return personCard;
    }

    public void setPersonCard(String personCard) {
        this.personCard = personCard;
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

    public String getCaseAcceptNo() {
        return caseAcceptNo;
    }

    public void setCaseAcceptNo(String caseAcceptNo) {
        this.caseAcceptNo = caseAcceptNo;
    }

    public String getCaseProperty() {
        return caseProperty;
    }

    public void setCaseProperty(String caseProperty) {
        this.caseProperty = caseProperty;
    }

    public String[] getSampleTypes() {
        return sampleTypes;
    }

    public void setSampleTypes(String[] sampleTypes) {
        this.sampleTypes = sampleTypes;
    }

    public String[] getCasePropertys() {
        return casePropertys;
    }

    public void setCasePropertys(String[] casePropertys) {
        this.casePropertys = casePropertys;
    }

    public String getSysXkNo() {
        return sysXkNo;
    }

    public void setSysXkNo(String sysXkNo) {
        this.sysXkNo = sysXkNo;
    }

    public Integer getReviewFlag() {
        return reviewFlag;
    }

    public void setReviewFlag(Integer reviewFlag) {
        this.reviewFlag = reviewFlag;
    }

    public String getReviewResultCode() {
        return reviewResultCode;
    }

    public void setReviewResultCode(String reviewResultCode) {
        this.reviewResultCode = reviewResultCode;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public String getCaseStates() {
        return caseStates;
    }

    public void setCaseStates(String caseStates) {
        this.caseStates = caseStates;
    }

	/**
	 * @return the clientOrgList
	 */
	public List<String> getClientOrgList() {
		return clientOrgList;
	}

	/**
	 * @param clientOrgList the clientOrgList to set
	 */
	public void setClientOrgList(List<String> clientOrgList) {
		this.clientOrgList = clientOrgList;
	}
}
