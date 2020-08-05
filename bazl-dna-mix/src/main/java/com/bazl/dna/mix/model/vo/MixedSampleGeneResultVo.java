package com.bazl.dna.mix.model.vo;

import java.io.Serializable;

/**
 * @Author: lzn
 * @Date: 2019/7/17 17:33
 * @Version: 1.0
 */
public class MixedSampleGeneResultVo implements Serializable {

	private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private String id;

    /**
     * 案件编号
     */
    private String caseNo;

    /**
     * 案件名称
     */
    private String caseName;

    /**
     * 检材id
     */
    private String sampleid;

    /**
     * 检材编号
     */
    private String sampleNo;

    /**
     * 检材名称
     */
    private String sampleName;


    /**
     * 人员id
     */
    private String personId;


    /**
     * 人员姓名
     */
    private String personName;

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getSampleid() {
        return sampleid;
    }

    public void setSampleid(String sampleid) {
        this.sampleid = sampleid;
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

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }
}
