package com.bazl.dna.mix.connector.nation.model.po;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Consignment)) return false;

        Consignment that = (Consignment) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (initServerNo != null ? !initServerNo.equals(that.initServerNo) : that.initServerNo != null) return false;
        if (labId != null ? !labId.equals(that.labId) : that.labId != null) return false;
        if (eventId != null ? !eventId.equals(that.eventId) : that.eventId != null) return false;
        if (category != null ? !category.equals(that.category) : that.category != null) return false;
        if (consignmentNo != null ? !consignmentNo.equals(that.consignmentNo) : that.consignmentNo != null)
            return false;
        if (consignOrgRegionalism != null ? !consignOrgRegionalism.equals(that.consignOrgRegionalism) : that.consignOrgRegionalism != null)
            return false;
        if (consignOrgName != null ? !consignOrgName.equals(that.consignOrgName) : that.consignOrgName != null)
            return false;
        if (consignOrgPhone != null ? !consignOrgPhone.equals(that.consignOrgPhone) : that.consignOrgPhone != null)
            return false;
        if (consignOrgFaxNo != null ? !consignOrgFaxNo.equals(that.consignOrgFaxNo) : that.consignOrgFaxNo != null)
            return false;
        if (consignOrgZipCode != null ? !consignOrgZipCode.equals(that.consignOrgZipCode) : that.consignOrgZipCode != null)
            return false;
        if (consignOrgAddress != null ? !consignOrgAddress.equals(that.consignOrgAddress) : that.consignOrgAddress != null)
            return false;
        if (consignDatetime != null ? !consignDatetime.equals(that.consignDatetime) : that.consignDatetime != null)
            return false;
        if (consignBrief != null ? !consignBrief.equals(that.consignBrief) : that.consignBrief != null) return false;
        if (originalIdentyInfo != null ? !originalIdentyInfo.equals(that.originalIdentyInfo) : that.originalIdentyInfo != null)
            return false;
        if (reIdentyReason != null ? !reIdentyReason.equals(that.reIdentyReason) : that.reIdentyReason != null)
            return false;
        if (consignerName != null ? !consignerName.equals(that.consignerName) : that.consignerName != null)
            return false;
        if (consignerDuty != null ? !consignerDuty.equals(that.consignerDuty) : that.consignerDuty != null)
            return false;
        if (consignerPhone != null ? !consignerPhone.equals(that.consignerPhone) : that.consignerPhone != null)
            return false;
        if (consignerCertificateType != null ? !consignerCertificateType.equals(that.consignerCertificateType) : that.consignerCertificateType != null)
            return false;
        if (consignerCertificateNo != null ? !consignerCertificateNo.equals(that.consignerCertificateNo) : that.consignerCertificateNo != null)
            return false;
        if (consignerAddress != null ? !consignerAddress.equals(that.consignerAddress) : that.consignerAddress != null)
            return false;
        if (consignerZipCode != null ? !consignerZipCode.equals(that.consignerZipCode) : that.consignerZipCode != null)
            return false;
        if (consignerName2 != null ? !consignerName2.equals(that.consignerName2) : that.consignerName2 != null)
            return false;
        if (consignerDuty2 != null ? !consignerDuty2.equals(that.consignerDuty2) : that.consignerDuty2 != null)
            return false;
        if (consignerPhone2 != null ? !consignerPhone2.equals(that.consignerPhone2) : that.consignerPhone2 != null)
            return false;
        if (consignerCertificateType2 != null ? !consignerCertificateType2.equals(that.consignerCertificateType2) : that.consignerCertificateType2 != null)
            return false;
        if (consignerCertificateNo2 != null ? !consignerCertificateNo2.equals(that.consignerCertificateNo2) : that.consignerCertificateNo2 != null)
            return false;
        if (consignerAddress2 != null ? !consignerAddress2.equals(that.consignerAddress2) : that.consignerAddress2 != null)
            return false;
        if (consignerZipCode2 != null ? !consignerZipCode2.equals(that.consignerZipCode2) : that.consignerZipCode2 != null)
            return false;
        if (acceptNo != null ? !acceptNo.equals(that.acceptNo) : that.acceptNo != null) return false;
        if (acceptorId != null ? !acceptorId.equals(that.acceptorId) : that.acceptorId != null) return false;
        if (acceptorName != null ? !acceptorName.equals(that.acceptorName) : that.acceptorName != null) return false;
        if (acceptRegionalism != null ? !acceptRegionalism.equals(that.acceptRegionalism) : that.acceptRegionalism != null)
            return false;
        if (acceptOrgName != null ? !acceptOrgName.equals(that.acceptOrgName) : that.acceptOrgName != null)
            return false;
        if (acceptOrgPhone != null ? !acceptOrgPhone.equals(that.acceptOrgPhone) : that.acceptOrgPhone != null)
            return false;
        if (acceptDatetime != null ? !acceptDatetime.equals(that.acceptDatetime) : that.acceptDatetime != null)
            return false;
        if (identifyRequest != null ? !identifyRequest.equals(that.identifyRequest) : that.identifyRequest != null)
            return false;
        if (promise != null ? !promise.equals(that.promise) : that.promise != null) return false;
        if (specialty != null ? !specialty.equals(that.specialty) : that.specialty != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
        if (acceptOpinion != null ? !acceptOpinion.equals(that.acceptOpinion) : that.acceptOpinion != null)
            return false;
        if (identifyResult != null ? !identifyResult.equals(that.identifyResult) : that.identifyResult != null)
            return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (isAppend != null ? !isAppend.equals(that.isAppend) : that.isAppend != null) return false;
        if (transferFlag != null ? !transferFlag.equals(that.transferFlag) : that.transferFlag != null) return false;
        if (transferUser != null ? !transferUser.equals(that.transferUser) : that.transferUser != null) return false;
        if (transferDatetime != null ? !transferDatetime.equals(that.transferDatetime) : that.transferDatetime != null)
            return false;
        if (updateStatus != null ? !updateStatus.equals(that.updateStatus) : that.updateStatus != null) return false;
        if (deleteFlag != null ? !deleteFlag.equals(that.deleteFlag) : that.deleteFlag != null) return false;
        if (dataSource != null ? !dataSource.equals(that.dataSource) : that.dataSource != null) return false;
        if (sampleSeqNo != null ? !sampleSeqNo.equals(that.sampleSeqNo) : that.sampleSeqNo != null) return false;
        if (dataLevel != null ? !dataLevel.equals(that.dataLevel) : that.dataLevel != null) return false;
        if (reserve1 != null ? !reserve1.equals(that.reserve1) : that.reserve1 != null) return false;
        if (reserve2 != null ? !reserve2.equals(that.reserve2) : that.reserve2 != null) return false;
        if (reserve3 != null ? !reserve3.equals(that.reserve3) : that.reserve3 != null) return false;
        if (reserve4 != null ? !reserve4.equals(that.reserve4) : that.reserve4 != null) return false;
        if (reserve5 != null ? !reserve5.equals(that.reserve5) : that.reserve5 != null) return false;
        if (reserve6 != null ? !reserve6.equals(that.reserve6) : that.reserve6 != null) return false;
        if (createUser != null ? !createUser.equals(that.createUser) : that.createUser != null) return false;
        if (createDatetime != null ? !createDatetime.equals(that.createDatetime) : that.createDatetime != null)
            return false;
        if (updateUser != null ? !updateUser.equals(that.updateUser) : that.updateUser != null) return false;
        if (updateDatetime != null ? !updateDatetime.equals(that.updateDatetime) : that.updateDatetime != null)
            return false;
        if (samplingRegionalism != null ? !samplingRegionalism.equals(that.samplingRegionalism) : that.samplingRegionalism != null)
            return false;
        if (samplingDatetime != null ? !samplingDatetime.equals(that.samplingDatetime) : that.samplingDatetime != null)
            return false;
        if (samplingName != null ? !samplingName.equals(that.samplingName) : that.samplingName != null) return false;
        if (samplingPhone != null ? !samplingPhone.equals(that.samplingPhone) : that.samplingPhone != null)
            return false;
        if (parentType != null ? !parentType.equals(that.parentType) : that.parentType != null) return false;
        if (singleCase != null ? !singleCase.equals(that.singleCase) : that.singleCase != null) return false;
        if (mainConsignmentId != null ? !mainConsignmentId.equals(that.mainConsignmentId) : that.mainConsignmentId != null)
            return false;
        if (retryCount != null ? !retryCount.equals(that.retryCount) : that.retryCount != null) return false;
        if (errorMsg != null ? !errorMsg.equals(that.errorMsg) : that.errorMsg != null) return false;
        if (taskNo != null ? !taskNo.equals(that.taskNo) : that.taskNo != null) return false;
        if (consignerFaxNo != null ? !consignerFaxNo.equals(that.consignerFaxNo) : that.consignerFaxNo != null)
            return false;
        if (consignerFaxNo2 != null ? !consignerFaxNo2.equals(that.consignerFaxNo2) : that.consignerFaxNo2 != null)
            return false;
        if (noExaminReason != null ? !noExaminReason.equals(that.noExaminReason) : that.noExaminReason != null)
            return false;
        if (extId != null ? !extId.equals(that.extId) : that.extId != null) return false;
        if (localStoreDatetime != null ? !localStoreDatetime.equals(that.localStoreDatetime) : that.localStoreDatetime != null)
            return false;
        if (localCreateDatetime != null ? !localCreateDatetime.equals(that.localCreateDatetime) : that.localCreateDatetime != null)
            return false;
        if (recorderOrgRegionalism != null ? !recorderOrgRegionalism.equals(that.recorderOrgRegionalism) : that.recorderOrgRegionalism != null)
            return false;
        if (recorderUnitname != null ? !recorderUnitname.equals(that.recorderUnitname) : that.recorderUnitname != null)
            return false;
        if (recorderPerson != null ? !recorderPerson.equals(that.recorderPerson) : that.recorderPerson != null)
            return false;
        if (recorderTel != null ? !recorderTel.equals(that.recorderTel) : that.recorderTel != null) return false;
        if (dgCaseNo != null ? !dgCaseNo.equals(that.dgCaseNo) : that.dgCaseNo != null) return false;
        if (singleCaseReason != null ? !singleCaseReason.equals(that.singleCaseReason) : that.singleCaseReason != null)
            return false;
        if (unitRegionalism != null ? !unitRegionalism.equals(that.unitRegionalism) : that.unitRegionalism != null)
            return false;
        if (recorderTel2 != null ? !recorderTel2.equals(that.recorderTel2) : that.recorderTel2 != null) return false;
        if (orgStatus != null ? !orgStatus.equals(that.orgStatus) : that.orgStatus != null) return false;
        if (isMaster != null ? !isMaster.equals(that.isMaster) : that.isMaster != null) return false;
        if (prospectingTime != null ? !prospectingTime.equals(that.prospectingTime) : that.prospectingTime != null)
            return false;
        return va != null ? va.equals(that.va) : that.va == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (initServerNo != null ? initServerNo.hashCode() : 0);
        result = 31 * result + (labId != null ? labId.hashCode() : 0);
        result = 31 * result + (eventId != null ? eventId.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (consignmentNo != null ? consignmentNo.hashCode() : 0);
        result = 31 * result + (consignOrgRegionalism != null ? consignOrgRegionalism.hashCode() : 0);
        result = 31 * result + (consignOrgName != null ? consignOrgName.hashCode() : 0);
        result = 31 * result + (consignOrgPhone != null ? consignOrgPhone.hashCode() : 0);
        result = 31 * result + (consignOrgFaxNo != null ? consignOrgFaxNo.hashCode() : 0);
        result = 31 * result + (consignOrgZipCode != null ? consignOrgZipCode.hashCode() : 0);
        result = 31 * result + (consignOrgAddress != null ? consignOrgAddress.hashCode() : 0);
        result = 31 * result + (consignDatetime != null ? consignDatetime.hashCode() : 0);
        result = 31 * result + (consignBrief != null ? consignBrief.hashCode() : 0);
        result = 31 * result + (originalIdentyInfo != null ? originalIdentyInfo.hashCode() : 0);
        result = 31 * result + (reIdentyReason != null ? reIdentyReason.hashCode() : 0);
        result = 31 * result + (consignerName != null ? consignerName.hashCode() : 0);
        result = 31 * result + (consignerDuty != null ? consignerDuty.hashCode() : 0);
        result = 31 * result + (consignerPhone != null ? consignerPhone.hashCode() : 0);
        result = 31 * result + (consignerCertificateType != null ? consignerCertificateType.hashCode() : 0);
        result = 31 * result + (consignerCertificateNo != null ? consignerCertificateNo.hashCode() : 0);
        result = 31 * result + (consignerAddress != null ? consignerAddress.hashCode() : 0);
        result = 31 * result + (consignerZipCode != null ? consignerZipCode.hashCode() : 0);
        result = 31 * result + (consignerName2 != null ? consignerName2.hashCode() : 0);
        result = 31 * result + (consignerDuty2 != null ? consignerDuty2.hashCode() : 0);
        result = 31 * result + (consignerPhone2 != null ? consignerPhone2.hashCode() : 0);
        result = 31 * result + (consignerCertificateType2 != null ? consignerCertificateType2.hashCode() : 0);
        result = 31 * result + (consignerCertificateNo2 != null ? consignerCertificateNo2.hashCode() : 0);
        result = 31 * result + (consignerAddress2 != null ? consignerAddress2.hashCode() : 0);
        result = 31 * result + (consignerZipCode2 != null ? consignerZipCode2.hashCode() : 0);
        result = 31 * result + (acceptNo != null ? acceptNo.hashCode() : 0);
        result = 31 * result + (acceptorId != null ? acceptorId.hashCode() : 0);
        result = 31 * result + (acceptorName != null ? acceptorName.hashCode() : 0);
        result = 31 * result + (acceptRegionalism != null ? acceptRegionalism.hashCode() : 0);
        result = 31 * result + (acceptOrgName != null ? acceptOrgName.hashCode() : 0);
        result = 31 * result + (acceptOrgPhone != null ? acceptOrgPhone.hashCode() : 0);
        result = 31 * result + (acceptDatetime != null ? acceptDatetime.hashCode() : 0);
        result = 31 * result + (identifyRequest != null ? identifyRequest.hashCode() : 0);
        result = 31 * result + (promise != null ? promise.hashCode() : 0);
        result = 31 * result + (specialty != null ? specialty.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (acceptOpinion != null ? acceptOpinion.hashCode() : 0);
        result = 31 * result + (identifyResult != null ? identifyResult.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (isAppend != null ? isAppend.hashCode() : 0);
        result = 31 * result + (transferFlag != null ? transferFlag.hashCode() : 0);
        result = 31 * result + (transferUser != null ? transferUser.hashCode() : 0);
        result = 31 * result + (transferDatetime != null ? transferDatetime.hashCode() : 0);
        result = 31 * result + (updateStatus != null ? updateStatus.hashCode() : 0);
        result = 31 * result + (deleteFlag != null ? deleteFlag.hashCode() : 0);
        result = 31 * result + (dataSource != null ? dataSource.hashCode() : 0);
        result = 31 * result + (sampleSeqNo != null ? sampleSeqNo.hashCode() : 0);
        result = 31 * result + (dataLevel != null ? dataLevel.hashCode() : 0);
        result = 31 * result + (reserve1 != null ? reserve1.hashCode() : 0);
        result = 31 * result + (reserve2 != null ? reserve2.hashCode() : 0);
        result = 31 * result + (reserve3 != null ? reserve3.hashCode() : 0);
        result = 31 * result + (reserve4 != null ? reserve4.hashCode() : 0);
        result = 31 * result + (reserve5 != null ? reserve5.hashCode() : 0);
        result = 31 * result + (reserve6 != null ? reserve6.hashCode() : 0);
        result = 31 * result + (createUser != null ? createUser.hashCode() : 0);
        result = 31 * result + (createDatetime != null ? createDatetime.hashCode() : 0);
        result = 31 * result + (updateUser != null ? updateUser.hashCode() : 0);
        result = 31 * result + (updateDatetime != null ? updateDatetime.hashCode() : 0);
        result = 31 * result + (samplingRegionalism != null ? samplingRegionalism.hashCode() : 0);
        result = 31 * result + (samplingDatetime != null ? samplingDatetime.hashCode() : 0);
        result = 31 * result + (samplingName != null ? samplingName.hashCode() : 0);
        result = 31 * result + (samplingPhone != null ? samplingPhone.hashCode() : 0);
        result = 31 * result + (parentType != null ? parentType.hashCode() : 0);
        result = 31 * result + (singleCase != null ? singleCase.hashCode() : 0);
        result = 31 * result + (mainConsignmentId != null ? mainConsignmentId.hashCode() : 0);
        result = 31 * result + (retryCount != null ? retryCount.hashCode() : 0);
        result = 31 * result + (errorMsg != null ? errorMsg.hashCode() : 0);
        result = 31 * result + (taskNo != null ? taskNo.hashCode() : 0);
        result = 31 * result + (consignerFaxNo != null ? consignerFaxNo.hashCode() : 0);
        result = 31 * result + (consignerFaxNo2 != null ? consignerFaxNo2.hashCode() : 0);
        result = 31 * result + (noExaminReason != null ? noExaminReason.hashCode() : 0);
        result = 31 * result + (extId != null ? extId.hashCode() : 0);
        result = 31 * result + (localStoreDatetime != null ? localStoreDatetime.hashCode() : 0);
        result = 31 * result + (localCreateDatetime != null ? localCreateDatetime.hashCode() : 0);
        result = 31 * result + (recorderOrgRegionalism != null ? recorderOrgRegionalism.hashCode() : 0);
        result = 31 * result + (recorderUnitname != null ? recorderUnitname.hashCode() : 0);
        result = 31 * result + (recorderPerson != null ? recorderPerson.hashCode() : 0);
        result = 31 * result + (recorderTel != null ? recorderTel.hashCode() : 0);
        result = 31 * result + (dgCaseNo != null ? dgCaseNo.hashCode() : 0);
        result = 31 * result + (singleCaseReason != null ? singleCaseReason.hashCode() : 0);
        result = 31 * result + (unitRegionalism != null ? unitRegionalism.hashCode() : 0);
        result = 31 * result + (recorderTel2 != null ? recorderTel2.hashCode() : 0);
        result = 31 * result + (orgStatus != null ? orgStatus.hashCode() : 0);
        result = 31 * result + (isMaster != null ? isMaster.hashCode() : 0);
        result = 31 * result + (prospectingTime != null ? prospectingTime.hashCode() : 0);
        result = 31 * result + (va != null ? va.hashCode() : 0);
        return result;
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