package com.bazl.dna.mix.model.vo;

import com.bazl.dna.mix.model.po.SingleSampleGene;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/4.
 */
public class SingleSampleGeneVo extends AbstractBaseVO<SingleSampleGene> implements Serializable, Comparable<SingleSampleGeneVo> {
	
	private static final long serialVersionUID = 1L;
	
    public SingleSampleGeneVo() {
        super();
        this.entity = new SingleSampleGene();
    }

    public SingleSampleGeneVo(SingleSampleGene entity) {
        super();
        this.entity = entity;
    }


    private String caseId;

    private String sampleNo;

    private String sampleName;

    private String sampleType;

    private String sampleTypeName;

    private String sampleFlag;

    private String refPersonId;


    private String caseNo;

    private String caseName;

    private int sameCount; //比中数

    private int sampleCount;//检材数量

    private String geneInfo;

    private String personName;
    private String smpleId;
    private String personId;

    private String singlegeneId;

    private Map<String, Object> geneMap;

    private String ratioSampleGeneImagePath;//比中图片
    private String splitdSampleGeneImagePath;

    //基因图谱
    private String geneImagePath;

    //业务字段  分组名称
    private String groupName;

    private Map<String, Object> newGeneMap;

    public int getSampleCount() {
        return sampleCount;
    }

    public void setSampleCount(int sampleCount) {
        this.sampleCount = sampleCount;
    }

    public Map<String, Object> getNewGeneMap() {
        return newGeneMap;
    }

    public void setNewGeneMap(Map<String, Object> newGeneMap) {
        this.newGeneMap = newGeneMap;
    }

    public String getSplitdSampleGeneImagePath() {
        return splitdSampleGeneImagePath;
    }

    public void setSplitdSampleGeneImagePath(String splitdSampleGeneImagePath) {
        this.splitdSampleGeneImagePath = splitdSampleGeneImagePath;
    }

    @Override
    public int compareTo(SingleSampleGeneVo o) {
        int str = Integer.parseInt(this.groupName.substring(6));
        int otr = Integer.parseInt(o.groupName.substring(6));
        if (str>otr){
            return 1;
        }else if (str<otr){
            return -1;
        }
        return 0;
    }


    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGeneImagePath() {
        return geneImagePath;
    }

    public void setGeneImagePath(String geneImagePath) {
        this.geneImagePath = geneImagePath;
    }

    public String getRatioSampleGeneImagePath() {
        return ratioSampleGeneImagePath;
    }

    public void setRatioSampleGeneImagePath(String ratioSampleGeneImagePath) {
        this.ratioSampleGeneImagePath = ratioSampleGeneImagePath;
    }

    public Map<String, Object> getGeneMap() {
        return geneMap;
    }

    public void setGeneMap(Map<String, Object> geneMap) {
        this.geneMap = geneMap;
    }

    public String getSinglegeneId() {
        return singlegeneId;
    }

    public void setSinglegeneId(String singlegeneId) {
        this.singlegeneId = singlegeneId;
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

    public int getSameCount() {
        return sameCount;
    }

    public void setSameCount(int sameCount) {
        this.sameCount = sameCount;
    }

    public String getGeneInfo() {
        return geneInfo;
    }

    public void setGeneInfo(String geneInfo) {
        this.geneInfo = geneInfo;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getSmpleId() {
        return smpleId;
    }

    public void setSmpleId(String smpleId) {
        this.smpleId = smpleId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getSampleTypeName() {
        return sampleTypeName;
    }

    public void setSampleTypeName(String sampleTypeName) {
        this.sampleTypeName = sampleTypeName;
    }
}
