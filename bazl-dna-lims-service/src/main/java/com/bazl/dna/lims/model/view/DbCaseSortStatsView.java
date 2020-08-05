package com.bazl.dna.lims.model.view;

import java.io.Serializable;
import java.util.List;

import com.bazl.dna.lims.model.vo.AbstractBaseVo;

/**
 * 案件分类统计视图
 * Created by wangliu on 2019/11/5.
 */
public class DbCaseSortStatsView extends AbstractBaseVo<DbCaseSortStatsView> implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 案件id
     */
    private String caseId;
    /**
     * 案件编号
     */
    private String caseNo;
    /**
     * 案件名称
     */
    private String caseName;
    /**
     * 简要案件
     */
    private String caseDigest;
    /**
     * 委托单位
     */
    private String orgName;
    /**
     * 现勘编号
     */
    private String caseXkNo;
    /**
     * 受理时间
     */
    private String acceptDatetime;

    /**
     * 受理时间（起）
     */
    private String acceptDatetimeStart;
    /**
     * 受理时间（止）
     */
    private String acceptDatetimeEnd;

    /**
     * 案件状态
     */
    private String caseStatus;
    /**
     * 案件状态名称
     */
    private String caseStatusName;
    /**
     * 委托时间
     */
    private String delegateDatetime;
    /**
     * 案件性质
     */
    private String caseType;
    /**
     * 受理人
     */
    private String acceptName;
    /**
     * 案发时间
     */
    private String caseDatetime;
    /**
     * 样本编号
     */
    private String sampleNo;
    /**
     * 样本名称
     */
    private String sampleName;
    /**
     * 入库编号
     */
    private String gjkSysNo;
    /**
     * 比中样本编号
     */
    private String sampleBNo;
    /**
     * 比中样本名称
     */
    private String sampleBName;

    /**
     * 样本数量
     */
    private int sampleCount;

    private List<String> caseTypeList;

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getCaseDigest() {
        return caseDigest;
    }

    public void setCaseDigest(String caseDigest) {
        this.caseDigest = caseDigest;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getCaseXkNo() {
        return caseXkNo;
    }

    public void setCaseXkNo(String caseXkNo) {
        this.caseXkNo = caseXkNo;
    }

    public String getAcceptDatetime() {
        return acceptDatetime;
    }

    public void setAcceptDatetime(String acceptDatetime) {
        this.acceptDatetime = acceptDatetime;
    }

    public String getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(String caseStatus) {
        this.caseStatus = caseStatus;
    }

    public String getCaseStatusName() {
        return caseStatusName;
    }

    public void setCaseStatusName(String caseStatusName) {
        this.caseStatusName = caseStatusName;
    }

    public String getDelegateDatetime() {
        return delegateDatetime;
    }

    public void setDelegateDatetime(String delegateDatetime) {
        this.delegateDatetime = delegateDatetime;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getAcceptName() {
        return acceptName;
    }

    public void setAcceptName(String acceptName) {
        this.acceptName = acceptName;
    }

    public String getCaseDatetime() {
        return caseDatetime;
    }

    public void setCaseDatetime(String caseDatetime) {
        this.caseDatetime = caseDatetime;
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

    public String getGjkSysNo() {
        return gjkSysNo;
    }

    public void setGjkSysNo(String gjkSysNo) {
        this.gjkSysNo = gjkSysNo;
    }

    public String getSampleBNo() {
        return sampleBNo;
    }

    public void setSampleBNo(String sampleBNo) {
        this.sampleBNo = sampleBNo;
    }

    public String getSampleBName() {
        return sampleBName;
    }

    public void setSampleBName(String sampleBName) {
        this.sampleBName = sampleBName;
    }

    public String getAcceptDatetimeStart() {
        return acceptDatetimeStart;
    }

    public void setAcceptDatetimeStart(String acceptDatetimeStart) {
        this.acceptDatetimeStart = acceptDatetimeStart;
    }

    public String getAcceptDatetimeEnd() {
        return acceptDatetimeEnd;
    }

    public void setAcceptDatetimeEnd(String acceptDatetimeEnd) {
        this.acceptDatetimeEnd = acceptDatetimeEnd;
    }

    public int getSampleCount() {
        return sampleCount;
    }

    public void setSampleCount(int sampleCount) {
        this.sampleCount = sampleCount;
    }

    public List<String> getCaseTypeList() {
        return caseTypeList;
    }

    public void setCaseTypeList(List<String> caseTypeList) {
        this.caseTypeList = caseTypeList;
    }
}
