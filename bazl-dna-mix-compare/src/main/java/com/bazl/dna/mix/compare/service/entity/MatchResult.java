package com.bazl.dna.mix.compare.service.entity;

import java.util.Date;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;

import com.bazl.dna.util.JsonDatetimeSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class MatchResult {
    private String id;

    private String compareId;

    private String mixedSampleGeneId;

    private String singleGeneId;

    private String splitedSampleGeneId;

    private String qusltyGeneId;

    private String sampleGeneResultType;

    private int ratio;

    private int splitDigit;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date comparisonTime;

    private String proportionSampleNo;

    private String proportionSampleName;

    private String proportionSiteNum;

    private String geneInfo;

    private String genePicture;
    private String genePicture2;

    private String proportionCaseNo;

    private String proportionCaseName;

    private String proportionPersonName;

    private String proportionPersonnel;

    private String proportionPersonCode;

    //身份证号
    private String idCardNo;

    private String proportionDistrict;

    private String proportionDistrictCode;

    //比中试剂盒
    private String proportionKilName;

    //是有有效
    private String effectFlag;

    //创建人
    private String createPerson;

    private String sampleNo;

    private String sampleName;

    private String siteNum;

    private String thanInsiteNum;

    private String queueSampleNo;

    private int endRum;

    private int startRum;

    /* 业务字段  时间转换 */
    private String datetime;

    //业务字段  分组名称
    private String groupName;

    //业务字段  比中类型
    private String sampleFlag;

    //业务字段  分页字段
    private int offset;

    public String getProportionDistrictCode() {
        return proportionDistrictCode;
    }

    public void setProportionDistrictCode(String proportionDistrictCode) {
        this.proportionDistrictCode = proportionDistrictCode;
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public String getProportionKilName() {
        return proportionKilName;
    }

    public void setProportionKilName(String proportionKilName) {
        this.proportionKilName = proportionKilName;
    }

    public String getProportionPersonName() {
        return proportionPersonName;
    }

    public void setProportionPersonName(String proportionPersonName) {
        this.proportionPersonName = proportionPersonName;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getProportionPersonCode() {
        return proportionPersonCode;
    }

    public void setProportionPersonCode(String proportionPersonCode) {
        this.proportionPersonCode = proportionPersonCode;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getSampleFlag() {
        return sampleFlag;
    }

    public void setSampleFlag(String sampleFlag) {
        this.sampleFlag = sampleFlag;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public String getEffectFlag() {
        return effectFlag;
    }

    public void setEffectFlag(String effectFlag) {
        this.effectFlag = effectFlag;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    private Map<String, Object> geneInfos;


    public String getGenePicture2() {
        return genePicture2;
    }

    public void setGenePicture2(String genePicture2) {
        this.genePicture2 = genePicture2;
    }

    public Map<String, Object> getGeneInfos() {
        return geneInfos;
    }

    public void setGeneInfos(Map<String, Object> geneInfos) {
        this.geneInfos = geneInfos;
    }

    public String getQueueSampleNo() {
        return queueSampleNo;
    }

    public void setQueueSampleNo(String queueSampleNo) {
        this.queueSampleNo = queueSampleNo;
    }

    public int getEndRum() {
        return endRum;
    }

    public void setEndRum(int endRum) {
        this.endRum = endRum;
    }

    public int getStartRum() {
        return startRum;
    }

    public void setStartRum(int startRum) {
        this.startRum = startRum;
    }

    public String getThanInsiteNum() {
        return thanInsiteNum;
    }

    public void setThanInsiteNum(String thanInsiteNum) {
        this.thanInsiteNum = thanInsiteNum;
    }

    public String getSiteNum() {
        return siteNum;
    }

    public void setSiteNum(String siteNum) {
        this.siteNum = siteNum;
    }

    public String getProportionSiteNum() {
        return proportionSiteNum;
    }

    public void setProportionSiteNum(String proportionSiteNum) {
        this.proportionSiteNum = proportionSiteNum;
    }

    public String getProportionCaseNo() {
        return proportionCaseNo;
    }

    public void setProportionCaseNo(String proportionCaseNo) {
        this.proportionCaseNo = proportionCaseNo;
    }

    public String getProportionCaseName() {
        return proportionCaseName;
    }

    public void setProportionCaseName(String proportionCaseName) {
        this.proportionCaseName = proportionCaseName;
    }

    public String getProportionDistrict() {
        return proportionDistrict;
    }

    public void setProportionDistrict(String proportionDistrict) {
        this.proportionDistrict = proportionDistrict;
    }

    public int getRatio() {
        return ratio;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }

    public int getSplitDigit() {
        return splitDigit;
    }

    public void setSplitDigit(int splitDigit) {
        this.splitDigit = splitDigit;
    }




    public String getSampleNo() {
        return sampleNo;
    }

    public void setSampleNo(String sampleNo) {
        this.sampleNo = sampleNo;
    }

    public String getGeneInfo() {
        return geneInfo;
    }

    public void setGeneInfo(String geneInfo) {
        this.geneInfo = geneInfo;
    }

    public String getProportionPersonnel() {
        return proportionPersonnel;
    }

    public void setProportionPersonnel(String proportionPersonnel) {
        this.proportionPersonnel = proportionPersonnel;
    }

    public String getGenePicture() {
        return genePicture;
    }

    public void setGenePicture(String genePicture) {
        this.genePicture = genePicture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCompareId() {
        return compareId;
    }

    public void setCompareId(String compareId) {
        this.compareId = compareId == null ? null : compareId.trim();
    }

    public String getMixedSampleGeneId() {
        return mixedSampleGeneId;
    }

    public void setMixedSampleGeneId(String mixedSampleGeneId) {
        this.mixedSampleGeneId = mixedSampleGeneId == null ? null : mixedSampleGeneId.trim();
    }

    public String getSingleGeneId() {
        return singleGeneId;
    }

    public void setSingleGeneId(String singleGeneId) {
        this.singleGeneId = singleGeneId == null ? null : singleGeneId.trim();
    }

    public String getSplitedSampleGeneId() {
        return splitedSampleGeneId;
    }

    public void setSplitedSampleGeneId(String splitedSampleGeneId) {
        this.splitedSampleGeneId = splitedSampleGeneId == null ? null : splitedSampleGeneId.trim();
    }

    public String getQusltyGeneId() {
        return qusltyGeneId;
    }

    public void setQusltyGeneId(String qusltyGeneId) {
        this.qusltyGeneId = qusltyGeneId == null ? null : qusltyGeneId.trim();
    }

    public String getSampleGeneResultType() {
        return sampleGeneResultType;
    }

    public void setSampleGeneResultType(String sampleGeneResultType) {
        this.sampleGeneResultType = sampleGeneResultType == null ? null : sampleGeneResultType.trim();
    }

    public Date getComparisonTime() {
        return comparisonTime;
    }

    public void setComparisonTime(Date comparisonTime) {
        this.comparisonTime = comparisonTime;
    }

    public String getProportionSampleNo() {
        return proportionSampleNo;
    }

    public void setProportionSampleNo(String proportionSampleNo) {
        this.proportionSampleNo = proportionSampleNo == null ? null : proportionSampleNo.trim();
    }

    public String getProportionSampleName() {
        return proportionSampleName;
    }

    public void setProportionSampleName(String proportionSampleName) {
        this.proportionSampleName = proportionSampleName == null ? null : proportionSampleName.trim();
    }
}