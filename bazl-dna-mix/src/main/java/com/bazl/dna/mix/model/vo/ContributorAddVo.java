package com.bazl.dna.mix.model.vo;

import java.io.Serializable;

public class ContributorAddVo implements Serializable {

	private static final long serialVersionUID = 1L;
	
    //样本编号
    private String sampleNo;
    //样本名称
    private String sampleName;
    //样本类型
    private String sampleType;
    //样本类型名称
    private String sampleTypeName;
    //案件编号
    private String caseNo;
    //案件名称
    private String caseName;
    //试剂盒名称
    private String panelName;
    //试剂盒id
    private String panelId;
    //位点个数
    private Integer geneCount;
    //基因串信息
//    private Map<String, Object> geneMap;
    private String geneInfo;

    public String getSampleTypeName() {
        return sampleTypeName;
    }

    public void setSampleTypeName(String sampleTypeName) {
        this.sampleTypeName = sampleTypeName;
    }

    public Integer getGeneCount() {
        return geneCount;
    }

    public void setGeneCount(Integer geneCount) {
        this.geneCount = geneCount;
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

    public String getPanelName() {
        return panelName;
    }

    public void setPanelName(String panelName) {
        this.panelName = panelName;
    }

//    public Map<String, Object> getGeneMap() {
//        return geneMap;
//    }
//
//    public void setGeneMap(Map<String, Object> geneMap) {
//        this.geneMap = geneMap;
//    }


    public String getGeneInfo() {
        return geneInfo;
    }

    public void setGeneInfo(String geneInfo) {
        this.geneInfo = geneInfo;
    }

    public String getPanelId() {
        return panelId;
    }

    public void setPanelId(String panelId) {
        this.panelId = panelId;
    }
}
