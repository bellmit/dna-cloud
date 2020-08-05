package com.bazl.dna.database.nation.converter.model.po;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/*
*CONSIGNMENT
*委托信息表
* */
public class Consignment {
    private String id;

    private String initServerNo;

    private String labId;

    private String eventId;

    private String category;

    private String consignmentNo;

    private String consignOrgRegionalism;

    private String consignOrgName;

    private String consignOrgPhone;

    private String consignOrgFaxNo;

    private String consignOrgZipCode;

    private String consignOrgAddress;

    @JSONField(name = "consignDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date consignDatetime;

    private String consignBrief;

    private String originalIdentyInfo;

    private String reIdentyReason;

    private String consignerName;

    private String consignerDuty;

    private String consignerPhone;

    private String consignerCertificateType;

    private String consignerCertificateNo;

    private String consignerAddress;

    private String consignerZipCode;

    private String consignerName2;

    private String consignerDuty2;

    private String consignerPhone2;

    private String consignerCertificateType2;

    private String consignerCertificateNo2;

    private String consignerAddress2;

    private String consignerZipCode2;

    private String acceptNo;

    private String acceptorId;

    private String acceptorName;

    private String acceptRegionalism;

    private String acceptOrgName;

    private String acceptOrgPhone;

    @JSONField(name = "acceptDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date acceptDatetime;

    private String identifyRequest;

    private String promise;

    private String specialty;

    private String remark;

    private String acceptOpinion;

    private String identifyResult;

    private String status;

    private Short isAppend;

    private Short transferFlag;

    private String transferUser;

    @JSONField(name = "transferDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date transferDatetime;

    private String updateStatus;

    private Short deleteFlag;

    private String dataSource;

    private Integer sampleSeqNo;

    private Integer dataLevel;

    private String reserve1;

    private String reserve2;

    private String reserve3;

    private String reserve4;

    private String reserve5;

    private String reserve6;

    private String createUser;

    @JSONField(name = "createDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createDatetime;

    private String updateUser;

    @JSONField(name = "updateDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updateDatetime;

    private String samplingRegionalism;

    @JSONField(name = "samplingDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date samplingDatetime;

    private String samplingName;

    private String samplingPhone;

    private String parentType;

    private String singleCase;

    private String mainConsignmentId;

    private BigDecimal retryCount;

    private String errorMsg;

    private String taskNo;

    private String consignerFaxNo;

    private String consignerFaxNo2;

    private String noExaminReason;

    private String extId;

    @JSONField(name = "localStoreDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date localStoreDatetime;

    @JSONField(name = "localCreateDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date localCreateDatetime;

    private String recorderOrgRegionalism;

    private String recorderUnitname;

    private String recorderPerson;

    private String recorderTel;

    private String dgCaseNo;

    private String singleCaseReason;

    private String unitRegionalism;

    private String recorderTel2;

    private String orgStatus;

    private String isMaster;

    private String prospectingTime;

    private String va;

    private String initServerNoLike;

    private String isNull;

    public String getIsNull() {
        return isNull;
    }

    public void setIsNull(String isNull) {
        this.isNull = isNull;
    }

    public String getInitServerNoLike() {
        return initServerNoLike;
    }

    public void setInitServerNoLike(String initServerNoLike) {
        this.initServerNoLike = initServerNoLike;
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

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId == null ? null : eventId.trim();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    public String getConsignmentNo() {
        return consignmentNo;
    }

    public void setConsignmentNo(String consignmentNo) {
        this.consignmentNo = consignmentNo == null ? null : consignmentNo.trim();
    }

    public String getConsignOrgRegionalism() {
        return consignOrgRegionalism;
    }

    public void setConsignOrgRegionalism(String consignOrgRegionalism) {
        this.consignOrgRegionalism = consignOrgRegionalism == null ? null : consignOrgRegionalism.trim();
    }

    public String getConsignOrgName() {
        return consignOrgName;
    }

    public void setConsignOrgName(String consignOrgName) {
        this.consignOrgName = consignOrgName == null ? null : consignOrgName.trim();
    }

    public String getConsignOrgPhone() {
        return consignOrgPhone;
    }

    public void setConsignOrgPhone(String consignOrgPhone) {
        this.consignOrgPhone = consignOrgPhone == null ? null : consignOrgPhone.trim();
    }

    public String getConsignOrgFaxNo() {
        return consignOrgFaxNo;
    }

    public void setConsignOrgFaxNo(String consignOrgFaxNo) {
        this.consignOrgFaxNo = consignOrgFaxNo == null ? null : consignOrgFaxNo.trim();
    }

    public String getConsignOrgZipCode() {
        return consignOrgZipCode;
    }

    public void setConsignOrgZipCode(String consignOrgZipCode) {
        this.consignOrgZipCode = consignOrgZipCode == null ? null : consignOrgZipCode.trim();
    }

    public String getConsignOrgAddress() {
        return consignOrgAddress;
    }

    public void setConsignOrgAddress(String consignOrgAddress) {
        this.consignOrgAddress = consignOrgAddress == null ? null : consignOrgAddress.trim();
    }

    public Date getConsignDatetime() {
        return consignDatetime;
    }

    public void setConsignDatetime(Date consignDatetime) {
        this.consignDatetime = consignDatetime;
    }

    public String getConsignBrief() {
        return consignBrief;
    }

    public void setConsignBrief(String consignBrief) {
        this.consignBrief = consignBrief == null ? null : consignBrief.trim();
    }

    public String getOriginalIdentyInfo() {
        return originalIdentyInfo;
    }

    public void setOriginalIdentyInfo(String originalIdentyInfo) {
        this.originalIdentyInfo = originalIdentyInfo == null ? null : originalIdentyInfo.trim();
    }

    public String getReIdentyReason() {
        return reIdentyReason;
    }

    public void setReIdentyReason(String reIdentyReason) {
        this.reIdentyReason = reIdentyReason == null ? null : reIdentyReason.trim();
    }

    public String getConsignerName() {
        return consignerName;
    }

    public void setConsignerName(String consignerName) {
        this.consignerName = consignerName == null ? null : consignerName.trim();
    }

    public String getConsignerDuty() {
        return consignerDuty;
    }

    public void setConsignerDuty(String consignerDuty) {
        this.consignerDuty = consignerDuty == null ? null : consignerDuty.trim();
    }

    public String getConsignerPhone() {
        return consignerPhone;
    }

    public void setConsignerPhone(String consignerPhone) {
        this.consignerPhone = consignerPhone == null ? null : consignerPhone.trim();
    }

    public String getConsignerCertificateType() {
        return consignerCertificateType;
    }

    public void setConsignerCertificateType(String consignerCertificateType) {
        this.consignerCertificateType = consignerCertificateType == null ? null : consignerCertificateType.trim();
    }

    public String getConsignerCertificateNo() {
        return consignerCertificateNo;
    }

    public void setConsignerCertificateNo(String consignerCertificateNo) {
        this.consignerCertificateNo = consignerCertificateNo == null ? null : consignerCertificateNo.trim();
    }

    public String getConsignerAddress() {
        return consignerAddress;
    }

    public void setConsignerAddress(String consignerAddress) {
        this.consignerAddress = consignerAddress == null ? null : consignerAddress.trim();
    }

    public String getConsignerZipCode() {
        return consignerZipCode;
    }

    public void setConsignerZipCode(String consignerZipCode) {
        this.consignerZipCode = consignerZipCode == null ? null : consignerZipCode.trim();
    }

    public String getConsignerName2() {
        return consignerName2;
    }

    public void setConsignerName2(String consignerName2) {
        this.consignerName2 = consignerName2 == null ? null : consignerName2.trim();
    }

    public String getConsignerDuty2() {
        return consignerDuty2;
    }

    public void setConsignerDuty2(String consignerDuty2) {
        this.consignerDuty2 = consignerDuty2 == null ? null : consignerDuty2.trim();
    }

    public String getConsignerPhone2() {
        return consignerPhone2;
    }

    public void setConsignerPhone2(String consignerPhone2) {
        this.consignerPhone2 = consignerPhone2 == null ? null : consignerPhone2.trim();
    }

    public String getConsignerCertificateType2() {
        return consignerCertificateType2;
    }

    public void setConsignerCertificateType2(String consignerCertificateType2) {
        this.consignerCertificateType2 = consignerCertificateType2 == null ? null : consignerCertificateType2.trim();
    }

    public String getConsignerCertificateNo2() {
        return consignerCertificateNo2;
    }

    public void setConsignerCertificateNo2(String consignerCertificateNo2) {
        this.consignerCertificateNo2 = consignerCertificateNo2 == null ? null : consignerCertificateNo2.trim();
    }

    public String getConsignerAddress2() {
        return consignerAddress2;
    }

    public void setConsignerAddress2(String consignerAddress2) {
        this.consignerAddress2 = consignerAddress2 == null ? null : consignerAddress2.trim();
    }

    public String getConsignerZipCode2() {
        return consignerZipCode2;
    }

    public void setConsignerZipCode2(String consignerZipCode2) {
        this.consignerZipCode2 = consignerZipCode2 == null ? null : consignerZipCode2.trim();
    }

    public String getAcceptNo() {
        return acceptNo;
    }

    public void setAcceptNo(String acceptNo) {
        this.acceptNo = acceptNo == null ? null : acceptNo.trim();
    }

    public String getAcceptorId() {
        return acceptorId;
    }

    public void setAcceptorId(String acceptorId) {
        this.acceptorId = acceptorId == null ? null : acceptorId.trim();
    }

    public String getAcceptorName() {
        return acceptorName;
    }

    public void setAcceptorName(String acceptorName) {
        this.acceptorName = acceptorName == null ? null : acceptorName.trim();
    }

    public String getAcceptRegionalism() {
        return acceptRegionalism;
    }

    public void setAcceptRegionalism(String acceptRegionalism) {
        this.acceptRegionalism = acceptRegionalism == null ? null : acceptRegionalism.trim();
    }

    public String getAcceptOrgName() {
        return acceptOrgName;
    }

    public void setAcceptOrgName(String acceptOrgName) {
        this.acceptOrgName = acceptOrgName == null ? null : acceptOrgName.trim();
    }

    public String getAcceptOrgPhone() {
        return acceptOrgPhone;
    }

    public void setAcceptOrgPhone(String acceptOrgPhone) {
        this.acceptOrgPhone = acceptOrgPhone == null ? null : acceptOrgPhone.trim();
    }

    public Date getAcceptDatetime() {
        return acceptDatetime;
    }

    public void setAcceptDatetime(Date acceptDatetime) {
        this.acceptDatetime = acceptDatetime;
    }

    public String getIdentifyRequest() {
        return identifyRequest;
    }

    public void setIdentifyRequest(String identifyRequest) {
        this.identifyRequest = identifyRequest == null ? null : identifyRequest.trim();
    }

    public String getPromise() {
        return promise;
    }

    public void setPromise(String promise) {
        this.promise = promise == null ? null : promise.trim();
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty == null ? null : specialty.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getAcceptOpinion() {
        return acceptOpinion;
    }

    public void setAcceptOpinion(String acceptOpinion) {
        this.acceptOpinion = acceptOpinion == null ? null : acceptOpinion.trim();
    }

    public String getIdentifyResult() {
        return identifyResult;
    }

    public void setIdentifyResult(String identifyResult) {
        this.identifyResult = identifyResult == null ? null : identifyResult.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Short getIsAppend() {
        return isAppend;
    }

    public void setIsAppend(Short isAppend) {
        this.isAppend = isAppend;
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

    public String getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(String updateStatus) {
        this.updateStatus = updateStatus == null ? null : updateStatus.trim();
    }

    public Short getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Short deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource == null ? null : dataSource.trim();
    }

    public Integer getSampleSeqNo() {
        return sampleSeqNo;
    }

    public void setSampleSeqNo(Integer sampleSeqNo) {
        this.sampleSeqNo = sampleSeqNo;
    }

    public Integer getDataLevel() {
        return dataLevel;
    }

    public void setDataLevel(Integer dataLevel) {
        this.dataLevel = dataLevel;
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

    public String getSamplingRegionalism() {
        return samplingRegionalism;
    }

    public void setSamplingRegionalism(String samplingRegionalism) {
        this.samplingRegionalism = samplingRegionalism == null ? null : samplingRegionalism.trim();
    }

    public Date getSamplingDatetime() {
        return samplingDatetime;
    }

    public void setSamplingDatetime(Date samplingDatetime) {
        this.samplingDatetime = samplingDatetime;
    }

    public String getSamplingName() {
        return samplingName;
    }

    public void setSamplingName(String samplingName) {
        this.samplingName = samplingName == null ? null : samplingName.trim();
    }

    public String getSamplingPhone() {
        return samplingPhone;
    }

    public void setSamplingPhone(String samplingPhone) {
        this.samplingPhone = samplingPhone == null ? null : samplingPhone.trim();
    }

    public String getParentType() {
        return parentType;
    }

    public void setParentType(String parentType) {
        this.parentType = parentType == null ? null : parentType.trim();
    }

    public String getSingleCase() {
        return singleCase;
    }

    public void setSingleCase(String singleCase) {
        this.singleCase = singleCase == null ? null : singleCase.trim();
    }

    public String getMainConsignmentId() {
        return mainConsignmentId;
    }

    public void setMainConsignmentId(String mainConsignmentId) {
        this.mainConsignmentId = mainConsignmentId == null ? null : mainConsignmentId.trim();
    }

    public BigDecimal getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(BigDecimal retryCount) {
        this.retryCount = retryCount;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg == null ? null : errorMsg.trim();
    }

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo == null ? null : taskNo.trim();
    }

    public String getConsignerFaxNo() {
        return consignerFaxNo;
    }

    public void setConsignerFaxNo(String consignerFaxNo) {
        this.consignerFaxNo = consignerFaxNo == null ? null : consignerFaxNo.trim();
    }

    public String getConsignerFaxNo2() {
        return consignerFaxNo2;
    }

    public void setConsignerFaxNo2(String consignerFaxNo2) {
        this.consignerFaxNo2 = consignerFaxNo2 == null ? null : consignerFaxNo2.trim();
    }

    public String getNoExaminReason() {
        return noExaminReason;
    }

    public void setNoExaminReason(String noExaminReason) {
        this.noExaminReason = noExaminReason == null ? null : noExaminReason.trim();
    }

    public String getExtId() {
        return extId;
    }

    public void setExtId(String extId) {
        this.extId = extId == null ? null : extId.trim();
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

    public String getRecorderOrgRegionalism() {
        return recorderOrgRegionalism;
    }

    public void setRecorderOrgRegionalism(String recorderOrgRegionalism) {
        this.recorderOrgRegionalism = recorderOrgRegionalism == null ? null : recorderOrgRegionalism.trim();
    }

    public String getRecorderUnitname() {
        return recorderUnitname;
    }

    public void setRecorderUnitname(String recorderUnitname) {
        this.recorderUnitname = recorderUnitname == null ? null : recorderUnitname.trim();
    }

    public String getRecorderPerson() {
        return recorderPerson;
    }

    public void setRecorderPerson(String recorderPerson) {
        this.recorderPerson = recorderPerson == null ? null : recorderPerson.trim();
    }

    public String getRecorderTel() {
        return recorderTel;
    }

    public void setRecorderTel(String recorderTel) {
        this.recorderTel = recorderTel == null ? null : recorderTel.trim();
    }

    public String getDgCaseNo() {
        return dgCaseNo;
    }

    public void setDgCaseNo(String dgCaseNo) {
        this.dgCaseNo = dgCaseNo == null ? null : dgCaseNo.trim();
    }

    public String getSingleCaseReason() {
        return singleCaseReason;
    }

    public void setSingleCaseReason(String singleCaseReason) {
        this.singleCaseReason = singleCaseReason == null ? null : singleCaseReason.trim();
    }

    public String getUnitRegionalism() {
        return unitRegionalism;
    }

    public void setUnitRegionalism(String unitRegionalism) {
        this.unitRegionalism = unitRegionalism == null ? null : unitRegionalism.trim();
    }

    public String getRecorderTel2() {
        return recorderTel2;
    }

    public void setRecorderTel2(String recorderTel2) {
        this.recorderTel2 = recorderTel2 == null ? null : recorderTel2.trim();
    }

    public String getOrgStatus() {
        return orgStatus;
    }

    public void setOrgStatus(String orgStatus) {
        this.orgStatus = orgStatus == null ? null : orgStatus.trim();
    }

    public String getIsMaster() {
        return isMaster;
    }

    public void setIsMaster(String isMaster) {
        this.isMaster = isMaster == null ? null : isMaster.trim();
    }

    public String getProspectingTime() {
        return prospectingTime;
    }

    public void setProspectingTime(String prospectingTime) {
        this.prospectingTime = prospectingTime == null ? null : prospectingTime.trim();
    }

    public String getVa() {
        return va;
    }

    public void setVa(String va) {
        this.va = va == null ? null : va.trim();
    }



    @Override
    public String toString() {
        return "Consignment{" +
                "id='" + id + '\'' +
                ", initServerNo='" + initServerNo + '\'' +
                ", labId='" + labId + '\'' +
                ", eventId='" + eventId + '\'' +
                ", category='" + category + '\'' +
                ", consignmentNo='" + consignmentNo + '\'' +
                ", consignOrgRegionalism='" + consignOrgRegionalism + '\'' +
                ", consignOrgName='" + consignOrgName + '\'' +
                ", consignOrgPhone='" + consignOrgPhone + '\'' +
                ", consignOrgFaxNo='" + consignOrgFaxNo + '\'' +
                ", consignOrgZipCode='" + consignOrgZipCode + '\'' +
                ", consignOrgAddress='" + consignOrgAddress + '\'' +
                ", consignDatetime=" + consignDatetime +
                ", consignBrief='" + consignBrief + '\'' +
                ", originalIdentyInfo='" + originalIdentyInfo + '\'' +
                ", reIdentyReason='" + reIdentyReason + '\'' +
                ", consignerName='" + consignerName + '\'' +
                ", consignerDuty='" + consignerDuty + '\'' +
                ", consignerPhone='" + consignerPhone + '\'' +
                ", consignerCertificateType='" + consignerCertificateType + '\'' +
                ", consignerCertificateNo='" + consignerCertificateNo + '\'' +
                ", consignerAddress='" + consignerAddress + '\'' +
                ", consignerZipCode='" + consignerZipCode + '\'' +
                ", consignerName2='" + consignerName2 + '\'' +
                ", consignerDuty2='" + consignerDuty2 + '\'' +
                ", consignerPhone2='" + consignerPhone2 + '\'' +
                ", consignerCertificateType2='" + consignerCertificateType2 + '\'' +
                ", consignerCertificateNo2='" + consignerCertificateNo2 + '\'' +
                ", consignerAddress2='" + consignerAddress2 + '\'' +
                ", consignerZipCode2='" + consignerZipCode2 + '\'' +
                ", acceptNo='" + acceptNo + '\'' +
                ", acceptorId='" + acceptorId + '\'' +
                ", acceptorName='" + acceptorName + '\'' +
                ", acceptRegionalism='" + acceptRegionalism + '\'' +
                ", acceptOrgName='" + acceptOrgName + '\'' +
                ", acceptOrgPhone='" + acceptOrgPhone + '\'' +
                ", acceptDatetime=" + acceptDatetime +
                ", identifyRequest='" + identifyRequest + '\'' +
                ", promise='" + promise + '\'' +
                ", specialty='" + specialty + '\'' +
                ", remark='" + remark + '\'' +
                ", acceptOpinion='" + acceptOpinion + '\'' +
                ", identifyResult='" + identifyResult + '\'' +
                ", status='" + status + '\'' +
                ", isAppend=" + isAppend +
                ", transferFlag=" + transferFlag +
                ", transferUser='" + transferUser + '\'' +
                ", transferDatetime=" + transferDatetime +
                ", updateStatus='" + updateStatus + '\'' +
                ", deleteFlag=" + deleteFlag +
                ", dataSource='" + dataSource + '\'' +
                ", sampleSeqNo=" + sampleSeqNo +
                ", dataLevel=" + dataLevel +
                ", reserve1='" + reserve1 + '\'' +
                ", reserve2='" + reserve2 + '\'' +
                ", reserve3='" + reserve3 + '\'' +
                ", reserve4='" + reserve4 + '\'' +
                ", reserve5='" + reserve5 + '\'' +
                ", reserve6='" + reserve6 + '\'' +
                ", createUser='" + createUser + '\'' +
                ", createDatetime=" + createDatetime +
                ", updateUser='" + updateUser + '\'' +
                ", updateDatetime=" + updateDatetime +
                ", samplingRegionalism='" + samplingRegionalism + '\'' +
                ", samplingDatetime=" + samplingDatetime +
                ", samplingName='" + samplingName + '\'' +
                ", samplingPhone='" + samplingPhone + '\'' +
                ", parentType='" + parentType + '\'' +
                ", singleCase='" + singleCase + '\'' +
                ", mainConsignmentId='" + mainConsignmentId + '\'' +
                ", retryCount=" + retryCount +
                ", errorMsg='" + errorMsg + '\'' +
                ", taskNo='" + taskNo + '\'' +
                ", consignerFaxNo='" + consignerFaxNo + '\'' +
                ", consignerFaxNo2='" + consignerFaxNo2 + '\'' +
                ", noExaminReason='" + noExaminReason + '\'' +
                ", extId='" + extId + '\'' +
                ", localStoreDatetime=" + localStoreDatetime +
                ", localCreateDatetime=" + localCreateDatetime +
                ", recorderOrgRegionalism='" + recorderOrgRegionalism + '\'' +
                ", recorderUnitname='" + recorderUnitname + '\'' +
                ", recorderPerson='" + recorderPerson + '\'' +
                ", recorderTel='" + recorderTel + '\'' +
                ", dgCaseNo='" + dgCaseNo + '\'' +
                ", singleCaseReason='" + singleCaseReason + '\'' +
                ", unitRegionalism='" + unitRegionalism + '\'' +
                ", recorderTel2='" + recorderTel2 + '\'' +
                ", orgStatus='" + orgStatus + '\'' +
                ", isMaster='" + isMaster + '\'' +
                ", prospectingTime='" + prospectingTime + '\'' +
                ", va='" + va + '\'' +
                '}';
    }
}