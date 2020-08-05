package com.bazl.dna.mix.model.vo;

import com.bazl.dna.mix.model.po.MixedSampleGene;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/4.
 */
public class MixedSampleGeneVo extends AbstractBaseVO<MixedSampleGene> implements Serializable {

	private static final long serialVersionUID = 1L;
	
    public MixedSampleGeneVo() {
        super();
        this.entity = new MixedSampleGene();
    }

    public MixedSampleGeneVo(MixedSampleGene entity) {
        super();
        this.entity = entity;
    }

    private String caseId;

    private String sampleNo;

    private String sampleNobizhong;

    private String sampleName;

    private String sampleType;

    private String sampleTypeName;

    private String sampleFlag;

    private String refPersonId;

    private String remark;

    private String caseNo;

    private String caseName;

    private String orgId;

    private String userId;

    private Map<String, Object> resultGeneInfo;

    private Map<String, Object> geneMap;

    private String newGeneInfo;

    private String geneImagePath;

    /**
     * 混合id
     */
    private String mixedSampleGendId;
    /**
     * 比中id
     */
    private String singlegeneId;
    /**
     * 名称
     */
    private String personName;
    /**
     * 比中数
     */
    private Short ratio;

    /**
     * 差异数
     */
    private Short splitDigit;

    /**
     * 比中时间
     */
    private Date comparisonTime;

    public String getNewGeneInfo() {
        return newGeneInfo;
    }

    public void setNewGeneInfo(String newGeneInfo) {
        this.newGeneInfo = newGeneInfo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public Map<String, Object> getResultGeneInfo() {
        return resultGeneInfo;
    }

    public void setResultGeneInfo(Map<String, Object> resultGeneInfo) {
        this.resultGeneInfo = resultGeneInfo;
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

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
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

    public String getSampleFlag() {
        return sampleFlag;
    }

    public void setSampleFlag(String sampleFlag) {
        this.sampleFlag = sampleFlag;
    }

    public String getRefPersonId() {
        return refPersonId;
    }

    public void setRefPersonId(String refPersonId) {
        this.refPersonId = refPersonId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMixedSampleGendId() {
        return mixedSampleGendId;
    }

    public void setMixedSampleGendId(String mixedSampleGendId) {
        this.mixedSampleGendId = mixedSampleGendId;
    }

    public String getSinglegeneId() {
        return singlegeneId;
    }

    public void setSinglegeneId(String singlegeneId) {
        this.singlegeneId = singlegeneId;
    }

    public Short getRatio() {
        return ratio;
    }

    public void setRatio(Short ratio) {
        this.ratio = ratio;
    }

    public Short getSplitDigit() {
        return splitDigit;
    }

    public void setSplitDigit(Short splitDigit) {
        this.splitDigit = splitDigit;
    }

    public Date getComparisonTime() {
        return comparisonTime;
    }

    public void setComparisonTime(Date comparisonTime) {
        this.comparisonTime = comparisonTime;
    }

    public String getSampleNobizhong() {
        return sampleNobizhong;
    }

    public void setSampleNobizhong(String sampleNobizhong) {
        this.sampleNobizhong = sampleNobizhong;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public Map<String, Object> getGeneMap() {
        return geneMap;
    }

    public void setGeneMap(Map<String, Object> geneMap) {
        this.geneMap = geneMap;
    }

    public String getGeneImagePath() {
        return geneImagePath;
    }

    public void setGeneImagePath(String geneImagePath) {
        this.geneImagePath = geneImagePath;
    }

    public String getSampleTypeName() {
        return sampleTypeName;
    }

    public void setSampleTypeName(String sampleTypeName) {
        this.sampleTypeName = sampleTypeName;
    }
}
