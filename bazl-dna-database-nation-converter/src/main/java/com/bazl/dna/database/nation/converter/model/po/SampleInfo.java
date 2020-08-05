package com.bazl.dna.database.nation.converter.model.po;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/*
*SAMPLE_INFO
*样本信息表
* */
public class SampleInfo {
    private String id;

    private String initServerNo;

    private String labId;

    private String consignmentId;

    private String consignOrgCode;

    private String physicalEvidenceId;

    private String sampleNo;

    private String sampleLabNo;

    private String sampleName;

    private String sampleType;

    private String specialty;

    private String color;

    private String pattern;

    private String texture;

    private String shape;

    private Integer amount;

    private String evidenceSize;

    private String specialSign;

    private Short isCollect;

    private Short isTest;

    private String collectMethod;

    private String collectPos;

    private String collectReason;

    private String collectAgencyCode;

    private String collectAgencyName;

    @JSONField(name = "collectDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date collectDatetime;

    private String collectUser;

    private String testOrgCode;

    private String testOrgName;

    @JSONField(name = "testDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date testDatetime;

    private String tester;

    private String preExamInfo;

    private String preExamMethod;

    private String preExamDesc;

    private String samplePackaging;

    private String sampleDesc;

    private Short ftaFlag;

    private String acceptOpinion;

    private String sampleStatus;

    private String testPhase;

    private Short deleteFlag;

    private String geneStoreFlag;

    private Short transferFlag;

    private String transferUser;

    @JSONField(name = "transferDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date transferDatetime;

    private Short isAppend;

    private Integer specimenSeqNo;

    private String reserve1;

    private String reserve2;

    private String reserve3;

    private String reserve4;

    private String reserve5;

    private String reserve6;

    private String remark;

    private String dataSource;

    private Integer dataLevel;

    private String createUser;

    @JSONField(name = "createDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createDatetime;

    private String updateUser;

    @JSONField(name = "updateDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updateDatetime;

    private String selfObjectId;

    private String relationObjectId;

    private String relation;

    private String personRelationDesc;

    private String collectPurpose;

    private String typeExtraInfo;

    private String specialDealOption;

    private String sampleSerialNo;

    private String extId;

    private String signUpload;

    @JSONField(name = "localStoreDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date localStoreDatetime;

    @JSONField(name = "localCreateDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date localCreateDatetime;

    private String va;

    private String caseID;

    private String sampleFlag;

    private String refPersonId;

    private String instoredFlag;

    private String createPerson;

    private String updatePereson;

    private String initServerNoLike;

    public String getInitServerNoLike() {
        return initServerNoLike;
    }

    public void setInitServerNoLike(String initServerNoLike) {
        this.initServerNoLike = initServerNoLike;
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public String getUpdatePereson() {
        return updatePereson;
    }

    public void setUpdatePereson(String updatePereson) {
        this.updatePereson = updatePereson;
    }

    public String getCaseID() {
        return caseID;
    }

    public void setCaseID(String casrID) {
        this.caseID= casrID;
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

    public String getInstoredFlag() {
        return instoredFlag;
    }

    public void setInstoredFlag(String instoredFlag) {
        this.instoredFlag = instoredFlag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getInitServerNo() {
        return initServerNo;
    }

    public void setInitServerNo(String initServerNo) {
        this.initServerNo = initServerNo == null ? null : initServerNo.trim();
    }

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId == null ? null : labId.trim();
    }

    public String getConsignmentId() {
        return consignmentId;
    }

    public void setConsignmentId(String consignmentId) {
        this.consignmentId = consignmentId == null ? null : consignmentId.trim();
    }

    public String getConsignOrgCode() {
        return consignOrgCode;
    }

    public void setConsignOrgCode(String consignOrgCode) {
        this.consignOrgCode = consignOrgCode == null ? null : consignOrgCode.trim();
    }

    public String getPhysicalEvidenceId() {
        return physicalEvidenceId;
    }

    public void setPhysicalEvidenceId(String physicalEvidenceId) {
        this.physicalEvidenceId = physicalEvidenceId == null ? null : physicalEvidenceId.trim();
    }

    public String getSampleNo() {
        return sampleNo;
    }

    public void setSampleNo(String sampleNo) {
        this.sampleNo = sampleNo == null ? null : sampleNo.trim();
    }

    public String getSampleLabNo() {
        return sampleLabNo;
    }

    public void setSampleLabNo(String sampleLabNo) {
        this.sampleLabNo = sampleLabNo == null ? null : sampleLabNo.trim();
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName == null ? null : sampleName.trim();
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType == null ? null : sampleType.trim();
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty == null ? null : specialty.trim();
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color == null ? null : color.trim();
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern == null ? null : pattern.trim();
    }

    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture == null ? null : texture.trim();
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape == null ? null : shape.trim();
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getEvidenceSize() {
        return evidenceSize;
    }

    public void setEvidenceSize(String evidenceSize) {
        this.evidenceSize = evidenceSize == null ? null : evidenceSize.trim();
    }

    public String getSpecialSign() {
        return specialSign;
    }

    public void setSpecialSign(String specialSign) {
        this.specialSign = specialSign == null ? null : specialSign.trim();
    }

    public Short getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(Short isCollect) {
        this.isCollect = isCollect;
    }

    public Short getIsTest() {
        return isTest;
    }

    public void setIsTest(Short isTest) {
        this.isTest = isTest;
    }

    public String getCollectMethod() {
        return collectMethod;
    }

    public void setCollectMethod(String collectMethod) {
        this.collectMethod = collectMethod == null ? null : collectMethod.trim();
    }

    public String getCollectPos() {
        return collectPos;
    }

    public void setCollectPos(String collectPos) {
        this.collectPos = collectPos == null ? null : collectPos.trim();
    }

    public String getCollectReason() {
        return collectReason;
    }

    public void setCollectReason(String collectReason) {
        this.collectReason = collectReason == null ? null : collectReason.trim();
    }

    public String getCollectAgencyCode() {
        return collectAgencyCode;
    }

    public void setCollectAgencyCode(String collectAgencyCode) {
        this.collectAgencyCode = collectAgencyCode == null ? null : collectAgencyCode.trim();
    }

    public String getCollectAgencyName() {
        return collectAgencyName;
    }

    public void setCollectAgencyName(String collectAgencyName) {
        this.collectAgencyName = collectAgencyName == null ? null : collectAgencyName.trim();
    }

    public Date getCollectDatetime() {
        return collectDatetime;
    }

    public void setCollectDatetime(Date collectDatetime) {
        this.collectDatetime = collectDatetime;
    }

    public String getCollectUser() {
        return collectUser;
    }

    public void setCollectUser(String collectUser) {
        this.collectUser = collectUser == null ? null : collectUser.trim();
    }

    public String getTestOrgCode() {
        return testOrgCode;
    }

    public void setTestOrgCode(String testOrgCode) {
        this.testOrgCode = testOrgCode == null ? null : testOrgCode.trim();
    }

    public String getTestOrgName() {
        return testOrgName;
    }

    public void setTestOrgName(String testOrgName) {
        this.testOrgName = testOrgName == null ? null : testOrgName.trim();
    }

    public Date getTestDatetime() {
        return testDatetime;
    }

    public void setTestDatetime(Date testDatetime) {
        this.testDatetime = testDatetime;
    }

    public String getTester() {
        return tester;
    }

    public void setTester(String tester) {
        this.tester = tester == null ? null : tester.trim();
    }

    public String getPreExamInfo() {
        return preExamInfo;
    }

    public void setPreExamInfo(String preExamInfo) {
        this.preExamInfo = preExamInfo == null ? null : preExamInfo.trim();
    }

    public String getPreExamMethod() {
        return preExamMethod;
    }

    public void setPreExamMethod(String preExamMethod) {
        this.preExamMethod = preExamMethod == null ? null : preExamMethod.trim();
    }

    public String getPreExamDesc() {
        return preExamDesc;
    }

    public void setPreExamDesc(String preExamDesc) {
        this.preExamDesc = preExamDesc == null ? null : preExamDesc.trim();
    }

    public String getSamplePackaging() {
        return samplePackaging;
    }

    public void setSamplePackaging(String samplePackaging) {
        this.samplePackaging = samplePackaging == null ? null : samplePackaging.trim();
    }

    public String getSampleDesc() {
        return sampleDesc;
    }

    public void setSampleDesc(String sampleDesc) {
        this.sampleDesc = sampleDesc == null ? null : sampleDesc.trim();
    }

    public Short getFtaFlag() {
        return ftaFlag;
    }

    public void setFtaFlag(Short ftaFlag) {
        this.ftaFlag = ftaFlag;
    }

    public String getAcceptOpinion() {
        return acceptOpinion;
    }

    public void setAcceptOpinion(String acceptOpinion) {
        this.acceptOpinion = acceptOpinion == null ? null : acceptOpinion.trim();
    }

    public String getSampleStatus() {
        return sampleStatus;
    }

    public void setSampleStatus(String sampleStatus) {
        this.sampleStatus = sampleStatus == null ? null : sampleStatus.trim();
    }

    public String getTestPhase() {
        return testPhase;
    }

    public void setTestPhase(String testPhase) {
        this.testPhase = testPhase == null ? null : testPhase.trim();
    }

    public Short getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Short deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getGeneStoreFlag() {
        return geneStoreFlag;
    }

    public void setGeneStoreFlag(String geneStoreFlag) {
        this.geneStoreFlag = geneStoreFlag == null ? null : geneStoreFlag.trim();
    }

    public Short getTransferFlag() {
        return transferFlag;
    }

    public void setTransferFlag(Short transferFlag) {
        this.transferFlag = transferFlag;
    }

    public String getTransferUser() {
        return transferUser;
    }

    public void setTransferUser(String transferUser) {
        this.transferUser = transferUser == null ? null : transferUser.trim();
    }

    public Date getTransferDatetime() {
        return transferDatetime;
    }

    public void setTransferDatetime(Date transferDatetime) {
        this.transferDatetime = transferDatetime;
    }

    public Short getIsAppend() {
        return isAppend;
    }

    public void setIsAppend(Short isAppend) {
        this.isAppend = isAppend;
    }

    public Integer getSpecimenSeqNo() {
        return specimenSeqNo;
    }

    public void setSpecimenSeqNo(Integer specimenSeqNo) {
        this.specimenSeqNo = specimenSeqNo;
    }

    public String getReserve1() {
        return reserve1;
    }

    public void setReserve1(String reserve1) {
        this.reserve1 = reserve1 == null ? null : reserve1.trim();
    }

    public String getReserve2() {
        return reserve2;
    }

    public void setReserve2(String reserve2) {
        this.reserve2 = reserve2 == null ? null : reserve2.trim();
    }

    public String getReserve3() {
        return reserve3;
    }

    public void setReserve3(String reserve3) {
        this.reserve3 = reserve3 == null ? null : reserve3.trim();
    }

    public String getReserve4() {
        return reserve4;
    }

    public void setReserve4(String reserve4) {
        this.reserve4 = reserve4 == null ? null : reserve4.trim();
    }

    public String getReserve5() {
        return reserve5;
    }

    public void setReserve5(String reserve5) {
        this.reserve5 = reserve5 == null ? null : reserve5.trim();
    }

    public String getReserve6() {
        return reserve6;
    }

    public void setReserve6(String reserve6) {
        this.reserve6 = reserve6 == null ? null : reserve6.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource == null ? null : dataSource.trim();
    }

    public Integer getDataLevel() {
        return dataLevel;
    }

    public void setDataLevel(Integer dataLevel) {
        this.dataLevel = dataLevel;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public String getSelfObjectId() {
        return selfObjectId;
    }

    public void setSelfObjectId(String selfObjectId) {
        this.selfObjectId = selfObjectId == null ? null : selfObjectId.trim();
    }

    public String getRelationObjectId() {
        return relationObjectId;
    }

    public void setRelationObjectId(String relationObjectId) {
        this.relationObjectId = relationObjectId == null ? null : relationObjectId.trim();
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation == null ? null : relation.trim();
    }

    public String getPersonRelationDesc() {
        return personRelationDesc;
    }

    public void setPersonRelationDesc(String personRelationDesc) {
        this.personRelationDesc = personRelationDesc == null ? null : personRelationDesc.trim();
    }

    public String getCollectPurpose() {
        return collectPurpose;
    }

    public void setCollectPurpose(String collectPurpose) {
        this.collectPurpose = collectPurpose == null ? null : collectPurpose.trim();
    }

    public String getTypeExtraInfo() {
        return typeExtraInfo;
    }

    public void setTypeExtraInfo(String typeExtraInfo) {
        this.typeExtraInfo = typeExtraInfo == null ? null : typeExtraInfo.trim();
    }

    public String getSpecialDealOption() {
        return specialDealOption;
    }

    public void setSpecialDealOption(String specialDealOption) {
        this.specialDealOption = specialDealOption == null ? null : specialDealOption.trim();
    }

    public String getSampleSerialNo() {
        return sampleSerialNo;
    }

    public void setSampleSerialNo(String sampleSerialNo) {
        this.sampleSerialNo = sampleSerialNo == null ? null : sampleSerialNo.trim();
    }

    public String getExtId() {
        return extId;
    }

    public void setExtId(String extId) {
        this.extId = extId == null ? null : extId.trim();
    }

    public String getSignUpload() {
        return signUpload;
    }

    public void setSignUpload(String signUpload) {
        this.signUpload = signUpload == null ? null : signUpload.trim();
    }

    public Date getLocalStoreDatetime() {
        return localStoreDatetime;
    }

    public void setLocalStoreDatetime(Date localStoreDatetime) {
        this.localStoreDatetime = localStoreDatetime;
    }

    public Date getLocalCreateDatetime() {
        return localCreateDatetime;
    }

    public void setLocalCreateDatetime(Date localCreateDatetime) {
        this.localCreateDatetime = localCreateDatetime;
    }

    public String getVa() {
        return va;
    }

    public void setVa(String va) {
        this.va = va == null ? null : va.trim();
    }

}