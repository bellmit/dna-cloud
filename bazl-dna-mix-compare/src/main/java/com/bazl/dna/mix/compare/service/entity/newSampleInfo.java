package com.bazl.dna.mix.compare.service.entity;

/*
*SAMPLE_INFO
*样本信息表
* */
public class newSampleInfo {
    private String id;

    private String labId;

    private String sampleNo;

    private String sampleGene;

    private String sampleLabNo;

    private String sampleName;

    private String sampleType;

    private String caseId;

    private String caseNo;

    private String caseName;

    private String personTypeName;

    private String personTypeCode;

    //身份证号
    private String idCardNo;

    private String personName;

    //地区
    private String labServerName;

    private String kilName;//试剂盒名称

    public String getKilName() {
        return kilName;
    }

    public void setKilName(String kilName) {
        this.kilName = kilName;
    }

    public String getLabServerName() {
        return labServerName;
    }

    public void setLabServerName(String labServerName) {
        this.labServerName = labServerName;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getPersonTypeCode() {
        return personTypeCode;
    }

    public void setPersonTypeCode(String personTypeCode) {
        this.personTypeCode = personTypeCode;
    }

    public String getPersonTypeName() {
        return personTypeName;
    }

    public void setPersonTypeName(String personTypeName) {
        this.personTypeName = personTypeName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }

    public String getSampleNo() {
        return sampleNo;
    }

    public void setSampleNo(String sampleNo) {
        this.sampleNo = sampleNo;
    }

    public String getSampleGene() {
        return sampleGene;
    }

    public void setSampleGene(String sampleGene) {
        this.sampleGene = sampleGene;
    }

    public String getSampleLabNo() {
        return sampleLabNo;
    }

    public void setSampleLabNo(String sampleLabNo) {
        this.sampleLabNo = sampleLabNo;
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
}