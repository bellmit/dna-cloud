package com.bazl.dna.lims.model.vo;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.bazl.dna.lims.model.po.FugitivesInfo;
import com.bazl.dna.lims.utils.JsonDatetimeSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author wanghaiyang
 * @date 2020/6/15
 */
public class FugitivesInfoVo extends AbstractBaseVo<FugitivesInfo> implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FugitivesInfoVo() {
        super();
        this.entity = new FugitivesInfo();
    }

    public FugitivesInfoVo(FugitivesInfo entity) {
        super();
        this.entity = entity;
    }

    private String personTypeName;

    private String personGenderName;

    //送检状态 02 已送检 01未送检 03已完成
    private String status;
    //委托单位编号
    private String delegateOrgCode;
    //是否补送,0：未补送；1：补送
    private String appendFlag;
    //案件编号
    private String caseId;
    //委托ID
    private String consignmentId;


    /**
     * 所属辖区
     *
     * @return
     */
    private String areaOrgCode;

    /**
     * 案件人员名称
     *
     * @return
     */
    private String casePersonName;

    /**
     * 案件人员证件号码
     *
     * @return
     */
    private String casePersonCard;

    /**
     * 物证编号
     *
     * @return
     */
    private String wzSampleNo;

    /**
     * 检材编号
     *
     * @return
     */
    private String sampleNo;

    /**
     * 检材名称
     *
     * @return
     */
    private String sampleName;

    /**
     * 委托人联系电话
     *
     * @return
     */
    private String phone;

    //在逃人员姓名
    private String fugitivesName;

    //在逃人员证件号码
    private String fugitivesCard;

    //在逃人员亲属姓名
    private String fugitivesRelationName;

    //在逃人员亲属证件号码
    private String fugitivesRelationCard;

    //在逃人员民族
    private String fugitivesRace;

    //在逃人员籍贯
    private String fugitivesNativePlace;

    //疑似在逃人员检材信息
    private String suspectSampleFlag;

    //疑似在逃人员使用物品
    private String suspectUseFlag;

    //在逃人员性别
    private String fugitivesGender;

    //委托开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date wtStartDate;

    //委托结束时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date delegateEndDatetime;

    private String consignmentNo;

    private String consignmentType;

    private String delegator1Id;

    private String consignationXkNo;//现勘系统委托编号

    //委托开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date delegateStartDatetime;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date delegateDatetime;

    //受理时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date acceptDatetime;

    //案件名称
    private String caseName;

    //受理编号
    private String caseNo;

    //委托人姓名
    private String delegator1Name;

    //受理单位ID
    private String acceptOrgId;

    public String getPersonTypeName() {
        return personTypeName;
    }

    public void setPersonTypeName(String personTypeName) {
        this.personTypeName = personTypeName;
    }

    public String getPersonGenderName() {
        return personGenderName;
    }

    public void setPersonGenderName(String personGenderName) {
        this.personGenderName = personGenderName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDelegateOrgCode() {
        return delegateOrgCode;
    }

    public void setDelegateOrgCode(String delegateOrgCode) {
        this.delegateOrgCode = delegateOrgCode;
    }

    public String getAppendFlag() {
        return appendFlag;
    }

    public void setAppendFlag(String appendFlag) {
        this.appendFlag = appendFlag;
    }

    public Date getDelegateEndDatetime() {
        return delegateEndDatetime;
    }

    public void setDelegateEndDatetime(Date delegateEndDatetime) {
        this.delegateEndDatetime = delegateEndDatetime;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getConsignmentId() {
        return consignmentId;
    }

    public void setConsignmentId(String consignmentId) {
        this.consignmentId = consignmentId;
    }

    public String getAreaOrgCode() {
        return areaOrgCode;
    }

    public void setAreaOrgCode(String areaOrgCode) {
        this.areaOrgCode = areaOrgCode;
    }

    public String getCasePersonName() {
        return casePersonName;
    }

    public void setCasePersonName(String casePersonName) {
        this.casePersonName = casePersonName;
    }

    public String getCasePersonCard() {
        return casePersonCard;
    }

    public void setCasePersonCard(String casePersonCard) {
        this.casePersonCard = casePersonCard;
    }

    public String getWzSampleNo() {
        return wzSampleNo;
    }

    public void setWzSampleNo(String wzSampleNo) {
        this.wzSampleNo = wzSampleNo;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFugitivesName() {
        return fugitivesName;
    }

    public void setFugitivesName(String fugitivesName) {
        this.fugitivesName = fugitivesName;
    }

    public String getFugitivesCard() {
        return fugitivesCard;
    }

    public void setFugitivesCard(String fugitivesCard) {
        this.fugitivesCard = fugitivesCard;
    }

    public String getFugitivesRelationName() {
        return fugitivesRelationName;
    }

    public void setFugitivesRelationName(String fugitivesRelationName) {this.fugitivesRelationName = fugitivesRelationName;}

    public String getFugitivesRelationCard() {
        return fugitivesRelationCard;
    }

    public void setFugitivesRelationCard(String fugitivesRelationCard) {this.fugitivesRelationCard = fugitivesRelationCard;}

    public String getFugitivesRace() {
        return fugitivesRace;
    }

    public void setFugitivesRace(String fugitivesRace) {
        this.fugitivesRace = fugitivesRace;
    }

    public String getFugitivesNativePlace() {
        return fugitivesNativePlace;
    }

    public void setFugitivesNativePlace(String fugitivesNativePlace) {this.fugitivesNativePlace = fugitivesNativePlace;}

    public String getSuspectSampleFlag() {
        return suspectSampleFlag;
    }

    public void setSuspectSampleFlag(String suspectSampleFlag) {
        this.suspectSampleFlag = suspectSampleFlag;
    }

    public String getSuspectUseFlag() {
        return suspectUseFlag;
    }

    public void setSuspectUseFlag(String suspectUseFlag) {
        this.suspectUseFlag = suspectUseFlag;
    }

    public String getFugitivesGender() {
        return fugitivesGender;
    }

    public void setFugitivesGender(String fugitivesGender) {
        this.fugitivesGender = fugitivesGender;
    }

    public Date getWtStartDate() {
        return wtStartDate;
    }

    public void setWtStartDate(Date wtStartDate) {
        this.wtStartDate = wtStartDate;
    }

    public String getConsignmentNo() {
        return consignmentNo;
    }

    public void setConsignmentNo(String consignmentNo) {
        this.consignmentNo = consignmentNo;
    }

    public String getConsignmentType() {
        return consignmentType;
    }

    public void setConsignmentType(String consignmentType) {
        this.consignmentType = consignmentType;
    }

    public String getDelegator1Id() {
        return delegator1Id;
    }

    public void setDelegator1Id(String delegator1Id) {
        this.delegator1Id = delegator1Id;
    }

    public String getConsignationXkNo() {
        return consignationXkNo;
    }

    public void setConsignationXkNo(String consignationXkNo) {
        this.consignationXkNo = consignationXkNo;
    }

    public Date getDelegateStartDatetime() {return delegateStartDatetime;}

    public void setDelegateStartDatetime(Date delegateStartDatetime) {this.delegateStartDatetime = delegateStartDatetime;}

    public String getCaseName() {return caseName;}

    public void setCaseName(String caseName) {this.caseName = caseName;}

    public String getCaseNo() {return caseNo;}

    public void setCaseNo(String caseNo) {this.caseNo = caseNo;}

    public Date getAcceptDatetime() {return acceptDatetime;}

    public void setAcceptDatetime(Date acceptDatetime) {this.acceptDatetime = acceptDatetime;}

    public String getDelegator1Name() {return delegator1Name;}

    public void setDelegator1Name(String delegator1Name) {this.delegator1Name = delegator1Name;}

    public String getAcceptOrgId() {return acceptOrgId;}

    public void setAcceptOrgId(String acceptOrgId) {this.acceptOrgId = acceptOrgId;}

    public Date getDelegateDatetime() {return delegateDatetime;}

    public void setDelegateDatetime(Date delegateDatetime) {this.delegateDatetime = delegateDatetime;}
}
