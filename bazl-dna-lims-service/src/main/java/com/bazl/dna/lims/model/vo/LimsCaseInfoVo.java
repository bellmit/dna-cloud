package com.bazl.dna.lims.model.vo;

import com.bazl.dna.lims.model.po.LimsCaseInfo;
import com.bazl.dna.lims.utils.JsonDatetimeSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/1/4.
 */
public class LimsCaseInfoVo extends AbstractBaseVo<LimsCaseInfo> implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LimsCaseInfoVo() {
        super();
        this.entity = new LimsCaseInfo();
    }

    public LimsCaseInfoVo(LimsCaseInfo entity) {
        super();
        this.entity = entity;
    }

    private String consignmentId;

    private String consignmentNo;

    //委托类型，0：案件委托；1：在逃人员委托
    private String consignmentType;
    
    private String consignationXkNo;//现勘系统委托编号

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date delegateStartDatetime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date delegateEndDatetime;

    private String delegator1Id;

    private String delegator1Name;

    private String delegator2Id;

    private String delegator2Name;

    private Date delegateDatetime;

    private String delegateOrgCode;

    private String delegateOrgName;

    private String caseTypeName;

    private String casePropertyName;

    private String caseStatusName;

    private String createPerson;
    private String caseProperty;
    private String dictCode;

    //创建变量，存入当前用户的orgId，和委托表accept_org_id进行判断
    private String userOrdId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date createDatetime;

    private String status;

    private String appendFlag;

    //受理时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date acceptDatetime;

    /**
     * 受理开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date acceptStartDatetime;

    /**
     * 受理终止时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date acceptEndDatetime;

    /**
     * 鉴定中心id(受理单位)
     * @return
     */
    private String acceptOrgId;

    private String queryType;

    private String personName;

    private String personIdCard;

    private List<String> caseStatusList;

    /**
     * 复核数量
     */
    private Integer checkCount;

    private String auditStatus;
    
    private String xkStatus;

    //受理id
    private String acceptorId;
    private String fullName;

    /**
     * 受理人姓名
     */
    private String acceptorName;

    //已入库
    private int storage;

    //未入库
    private int notStorage;

    //检出
    private int examine;

    /**
     * 入库样本总数
     * @return
     */
    private Integer backUpCount;

    /**
     * 等待入库样本总数
     * @return
     */
    private Integer backUpWaitForCount;

    /**
     * 入库样本成功数
     * @return
     */
    private Integer backSuccessCount;

    /**
     * 入库样本失败数
     * @return
     */
    private Integer backFailCount;
    /**
     * 案件id
     * @return
     */
    private String caseId;

    /**
     *   样本编号
     */
    private String sampleNo;

    /**
     * 物证编号
     * @return
     */
    private String wzSampleNo;

    /**
     *   样本名称
     */
    private String sampleName;

    private String queueType;

    private String firstCheckerName;

    /**
     * 案件数
     */
    private  int caseCnt;
    /**
     * 补送数
     */
    private  int appendCnt;
    /**
     * 物证样本数
     */
    private  int sampleCnt;
    /**
     * 人员样本数
     */
    private  int personSampleCnt;

    /**
     * 查询状态（点击查询按钮）
     */
    private String queryFlag;

    //在逃人员姓名
    private String fugitivesName;

    //在逃人员证件号码
    private String fugitivesCard;

    //在逃人员亲属姓名
    private String fugitivesRelationName;

    //在逃人员亲属证件号码
    private String fugitivesRelationCard;
    
    /**
     * 案件人员证件号码
     * @return
     */
    private String casePersonCard;
    /**
     * 案件人员名称
     * @return
     */
    private String casePersonName;
    /**
     * 委托人联系电话
     * @return
     */
    private String  phone;
    /**
     * 所属辖区
     * @return
     */
    private String areaOrgCode;
    
    //疑似在逃人员检材信息
    private String suspectSampleFlag;
    //疑似在逃人员使用物品
    private String suspectUseFlag;
    //在逃人员性别
    private String fugitivesGender;
    //是否有亲属信息
    private String relationFlag;
    //鉴定中心
    private String orgQualification;
    //在逃人员性别
    private String fugitivesGenderName;
    //在逃人员民族
    private String fugitivesRace;
    //在逃人员籍贯
    private String fugitivesNativePlace;
    //委托开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date wtStartDate;

    private String workTask;

    private String usedTime;

    /**
     * 首次样本检验时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date firstSampleTesttime;

    public String getWorkTask() {
        return workTask;
    }

    public void setWorkTask(String workTask) {
        this.workTask = workTask;
    }

    public String getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(String usedTime) {
        this.usedTime = usedTime;
    }

    public Date getFirstSampleTesttime() {
        return firstSampleTesttime;
    }

    public void setFirstSampleTesttime(Date firstSampleTesttime) {
        this.firstSampleTesttime = firstSampleTesttime;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public Integer getBackUpCount() {
        return backUpCount;
    }

    public void setBackUpCount(Integer backUpCount) {
        this.backUpCount = backUpCount;
    }

    public Integer getBackSuccessCount() {
        return backSuccessCount;
    }

    public void setBackSuccessCount(Integer backSuccessCount) {
        this.backSuccessCount = backSuccessCount;
    }

    public Integer getBackFailCount() {
        return backFailCount;
    }

    public void setBackFailCount(Integer backFailCount) {
        this.backFailCount = backFailCount;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public int getNotStorage() {
        return notStorage;
    }

    public void setNotStorage(int notStorage) {
        this.notStorage = notStorage;
    }

    public int getExamine() {
        return examine;
    }

    public void setExamine(int examine) {
        this.examine = examine;
    }

    public String getAcceptorName() {
        return acceptorName;
    }

    public void setAcceptorName(String acceptorName) {
        this.acceptorName = acceptorName;
    }

    public String getAcceptOrgId() {
        return acceptOrgId;
    }

    public void setAcceptOrgId(String acceptOrgId) {
        this.acceptOrgId = acceptOrgId;
    }

    public Date getAcceptStartDatetime() {
        return acceptStartDatetime;
    }

    public void setAcceptStartDatetime(Date acceptStartDatetime) {
        this.acceptStartDatetime = acceptStartDatetime;
    }

    public Date getAcceptEndDatetime() {
        return acceptEndDatetime;
    }

    public void setAcceptEndDatetime(Date acceptEndDatetime) {
        this.acceptEndDatetime = acceptEndDatetime;
    }

    public Date getAcceptDatetime() {
        return acceptDatetime;
    }

    public void setAcceptDatetime(Date acceptDatetime) {
        this.acceptDatetime = acceptDatetime;
    }

    public String getConsignmentId() {
        return consignmentId;
    }

    public void setConsignmentId(String consignmentId) {
        this.consignmentId = consignmentId;
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

    public Date getDelegateStartDatetime() {
        return delegateStartDatetime;
    }

    public void setDelegateStartDatetime(Date delegateStartDatetime) {
        this.delegateStartDatetime = delegateStartDatetime;
    }

    public Date getDelegateEndDatetime() {
        return delegateEndDatetime;
    }

    public void setDelegateEndDatetime(Date delegateEndDatetime) {
        this.delegateEndDatetime = delegateEndDatetime;
    }

    public String getDelegator1Id() {
        return delegator1Id;
    }

    public void setDelegator1Id(String delegator1Id) {
        this.delegator1Id = delegator1Id;
    }

    public String getDelegator1Name() {
        return delegator1Name;
    }

    public void setDelegator1Name(String delegator1Name) {
        this.delegator1Name = delegator1Name;
    }

    public String getDelegator2Id() {
        return delegator2Id;
    }

    public void setDelegator2Id(String delegator2Id) {
        this.delegator2Id = delegator2Id;
    }

    public String getDelegator2Name() {
        return delegator2Name;
    }

    public void setDelegator2Name(String delegator2Name) {
        this.delegator2Name = delegator2Name;
    }

    public Date getDelegateDatetime() {
        return delegateDatetime;
    }

    public void setDelegateDatetime(Date delegateDatetime) {
        this.delegateDatetime = delegateDatetime;
    }

    public String getDelegateOrgCode() {
        return delegateOrgCode;
    }

    public void setDelegateOrgCode(String delegateOrgCode) {
        this.delegateOrgCode = delegateOrgCode;
    }

    public String getDelegateOrgName() {
        return delegateOrgName;
    }

    public void setDelegateOrgName(String delegateOrgName) {
        this.delegateOrgName = delegateOrgName;
    }

    public String getCaseTypeName() {
        return caseTypeName;
    }

    public void setCaseTypeName(String caseTypeName) {
        this.caseTypeName = caseTypeName;
    }

    public String getCasePropertyName() {
        return casePropertyName;
    }

    public void setCasePropertyName(String casePropertyName) {
        this.casePropertyName = casePropertyName;
    }

    public String getCaseStatusName() {
        return caseStatusName;
    }

    public void setCaseStatusName(String caseStatusName) {
        this.caseStatusName = caseStatusName;
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getUserOrdId() {
        return userOrdId;
    }

    public void setUserOrdId(String userOrdId) {
        this.userOrdId = userOrdId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAppendFlag() {
        return appendFlag;
    }

    public void setAppendFlag(String appendFlag) {
        this.appendFlag = appendFlag;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonIdCard() {
        return personIdCard;
    }

    public void setPersonIdCard(String personIdCard) {
        this.personIdCard = personIdCard;
    }

    public List<String> getCaseStatusList() {
        return caseStatusList;
    }

    public void setCaseStatusList(List<String> caseStatusList) {
        this.caseStatusList = caseStatusList;
    }

    public Integer getCheckCount() {
        return checkCount;
    }

    public void setCheckCount(Integer checkCount) {
        this.checkCount = checkCount;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getAcceptorId() {
        return acceptorId;
    }

    public void setAcceptorId(String acceptorId) {
        this.acceptorId = acceptorId;
    }

    public Integer getBackUpWaitForCount() {
        return backUpWaitForCount;
    }

    public void setBackUpWaitForCount(Integer backUpWaitForCount) {
        this.backUpWaitForCount = backUpWaitForCount;
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

    public String getQueueType() {
        return queueType;
    }

    public void setQueueType(String queueType) {
        this.queueType = queueType;
    }

    public String getFirstCheckerName() {
        return firstCheckerName;
    }

    public void setFirstCheckerName(String firstCheckerName) {
        this.firstCheckerName = firstCheckerName;
    }

    public int getCaseCnt() {
        return caseCnt;
    }

    public void setCaseCnt(int caseCnt) {
        this.caseCnt = caseCnt;
    }

    public int getAppendCnt() {
        return appendCnt;
    }

    public void setAppendCnt(int appendCnt) {
        this.appendCnt = appendCnt;
    }

    public int getSampleCnt() {
        return sampleCnt;
    }

    public void setSampleCnt(int sampleCnt) {
        this.sampleCnt = sampleCnt;
    }

    public int getPersonSampleCnt() {
        return personSampleCnt;
    }

    public void setPersonSampleCnt(int personSampleCnt) {
        this.personSampleCnt = personSampleCnt;
    }

    public String getQueryFlag() {
        return queryFlag;
    }

    public void setQueryFlag(String queryFlag) {
        this.queryFlag = queryFlag;
    }

    public String getCaseProperty() {
        return caseProperty;
    }

    public void setCaseProperty(String caseProperty) {
        this.caseProperty = caseProperty;
    }

    public String getDictCode() {
        return dictCode;
    }

    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
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

    public void setFugitivesRelationName(String fugitivesRelationName) {
        this.fugitivesRelationName = fugitivesRelationName;
    }

    public String getFugitivesRelationCard() {
        return fugitivesRelationCard;
    }

    public void setFugitivesRelationCard(String fugitivesRelationCard) {
        this.fugitivesRelationCard = fugitivesRelationCard;
    }

	/**
	 * @return the consignationXkNo
	 */
	public String getConsignationXkNo() {
		return consignationXkNo;
	}

	/**
	 * @param consignationXkNo the consignationXkNo to set
	 */
	public void setConsignationXkNo(String consignationXkNo) {
		this.consignationXkNo = consignationXkNo;
	}

	/**
	 * @return the casePersonCard
	 */
	public String getCasePersonCard() {
		return casePersonCard;
	}

	/**
	 * @param casePersonCard the casePersonCard to set
	 */
	public void setCasePersonCard(String casePersonCard) {
		this.casePersonCard = casePersonCard;
	}

	/**
	 * @return the casePersonName
	 */
	public String getCasePersonName() {
		return casePersonName;
	}

	/**
	 * @param casePersonName the casePersonName to set
	 */
	public void setCasePersonName(String casePersonName) {
		this.casePersonName = casePersonName;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the areaOrgCode
	 */
	public String getAreaOrgCode() {
		return areaOrgCode;
	}

	/**
	 * @param areaOrgCode the areaOrgCode to set
	 */
	public void setAreaOrgCode(String areaOrgCode) {
		this.areaOrgCode = areaOrgCode;
	}

	/**
	 * @return the fugitivesGenderName
	 */
	public String getFugitivesGenderName() {
		return fugitivesGenderName;
	}

	/**
	 * @param fugitivesGenderName the fugitivesGenderName to set
	 */
	public void setFugitivesGenderName(String fugitivesGenderName) {
		this.fugitivesGenderName = fugitivesGenderName;
	}

	/**
	 * @return the fugitivesNativePlace
	 */
	public String getFugitivesNativePlace() {
		return fugitivesNativePlace;
	}

	/**
	 * @param fugitivesNativePlace the fugitivesNativePlace to set
	 */
	public void setFugitivesNativePlace(String fugitivesNativePlace) {
		this.fugitivesNativePlace = fugitivesNativePlace;
	}

	/**
	 * @return the fugitivesRace
	 */
	public String getFugitivesRace() {
		return fugitivesRace;
	}

	/**
	 * @param fugitivesRace the fugitivesRace to set
	 */
	public void setFugitivesRace(String fugitivesRace) {
		this.fugitivesRace = fugitivesRace;
	}

	/**
	 * @return the orgQualification
	 */
	public String getOrgQualification() {
		return orgQualification;
	}

	/**
	 * @param orgQualification the orgQualification to set
	 */
	public void setOrgQualification(String orgQualification) {
		this.orgQualification = orgQualification;
	}

	/**
	 * @return the relationFlag
	 */
	public String getRelationFlag() {
		return relationFlag;
	}

	/**
	 * @param relationFlag the relationFlag to set
	 */
	public void setRelationFlag(String relationFlag) {
		this.relationFlag = relationFlag;
	}

	/**
	 * @return the suspectSampleFlag
	 */
	public String getSuspectSampleFlag() {
		return suspectSampleFlag;
	}

	/**
	 * @param suspectSampleFlag the suspectSampleFlag to set
	 */
	public void setSuspectSampleFlag(String suspectSampleFlag) {
		this.suspectSampleFlag = suspectSampleFlag;
	}

	/**
	 * @return the suspectUseFlag
	 */
	public String getSuspectUseFlag() {
		return suspectUseFlag;
	}

	/**
	 * @param suspectUseFlag the suspectUseFlag to set
	 */
	public void setSuspectUseFlag(String suspectUseFlag) {
		this.suspectUseFlag = suspectUseFlag;
	}

	/**
	 * @return the fugitivesGender
	 */
	public String getFugitivesGender() {
		return fugitivesGender;
	}

	/**
	 * @param fugitivesGender the fugitivesGender to set
	 */
	public void setFugitivesGender(String fugitivesGender) {
		this.fugitivesGender = fugitivesGender;
	}

	/**
	 * @return the wtStartDate
	 */
	public Date getWtStartDate() {
		return wtStartDate;
	}

	/**
	 * @param wtStartDate the wtStartDate to set
	 */
	public void setWtStartDate(Date wtStartDate) {
		this.wtStartDate = wtStartDate;
	}

	/**
	 * @return the wzSampleNo
	 */
	public String getWzSampleNo() {
		return wzSampleNo;
	}

	/**
	 * @param wzSampleNo the wzSampleNo to set
	 */
	public void setWzSampleNo(String wzSampleNo) {
		this.wzSampleNo = wzSampleNo;
	}

	/**
	 * @return the xkStatus
	 */
	public String getXkStatus() {
		return xkStatus;
	}

	/**
	 * @param xkStatus the xkStatus to set
	 */
	public void setXkStatus(String xkStatus) {
		this.xkStatus = xkStatus;
	}
}
