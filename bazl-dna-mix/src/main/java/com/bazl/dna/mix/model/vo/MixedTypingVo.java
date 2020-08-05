package com.bazl.dna.mix.model.vo;

import java.io.Serializable;

public class MixedTypingVo   implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    public int offset; //当前页

    public int rows; //每页条数

    public String caseNo; //案件编号

    public String caseName; //案件名称

    public String sampleNo; //检材编号

    public String sampleName; //检材名称

    private String resultType; //比中类型

    private String mixedSampleId; //混合id

    private String createPerson;//创建人

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public String getMixedSampleId() {
        return mixedSampleId;
    }

    public void setMixedSampleId(String mixedSampleId) {
        this.mixedSampleId = mixedSampleId;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
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

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
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
