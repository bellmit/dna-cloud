package com.bazl.dna.lims.model.view;

import java.io.Serializable;
import java.util.List;

/**
 * 综合统计视图
 * Created by wangliu on 2019/11/11.
 */
public class DbComprehensiveCaseCountView implements Serializable {

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
     * 案件性质
     */
    private String caseType;
    /**
     * 受理时间
     */
    private String acceptAt;

    /**
     * 受理时间（起）
     */
    private String acceptDatetimeStart;
    /**
     * 受理时间（止）
     */
    private String acceptDatetimeEnd;
    /**
     * 总数
     */
    private String caseCount;
    /**
     * 受理案件数
     */
    private int caseNumber;
    /**
     * 受理物证数
     */
    private int wzSampleNumber;
    /**
     * 出鉴定数
     */
    private int caseBooksNumber;

    private List<String> caseTypeList;
    private int    number;

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

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getAcceptAt() {
        return acceptAt;
    }

    public void setAcceptAt(String acceptAt) {
        this.acceptAt = acceptAt;
    }

    public String getCaseCount() {
        return caseCount;
    }

    public void setCaseCount(String caseCount) {
        this.caseCount = caseCount;
    }

    public List<String> getCaseTypeList() {
        return caseTypeList;
    }

    public void setCaseTypeList(List<String> caseTypeList) {
        this.caseTypeList = caseTypeList;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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

    public int getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(int caseNumber) {
        this.caseNumber = caseNumber;
    }

    public int getWzSampleNumber() {
        return wzSampleNumber;
    }

    public void setWzSampleNumber(int wzSampleNumber) {
        this.wzSampleNumber = wzSampleNumber;
    }

    public int getCaseBooksNumber() {
        return caseBooksNumber;
    }

    public void setCaseBooksNumber(int caseBooksNumber) {
        this.caseBooksNumber = caseBooksNumber;
    }
}
