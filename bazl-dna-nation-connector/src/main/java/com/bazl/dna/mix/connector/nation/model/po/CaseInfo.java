package com.bazl.dna.mix.connector.nation.model.po;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
/*
* case_info
* 案件表
* */
public class CaseInfo  {

    private String id;

    private String initServerNo;

    private String labId;

    private String caseNo;

    private String caseName;

    private String sceneRegionalism;

    private String scenePlace;

    @JSONField(name = "occurrenceDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date occurrenceDatetime;

    private String caseLevel;

    private String caseType;

    private String caseProperty;

    private String harmLevel;

    private String sceneAreaType;

    private String caseInfoOrigin;

    private String caseBrief;

    private String caseStatus;

    private Short deleteFlag;

    private Short transferFlag;

    private String transferUser;

    @JSONField(name = "transferDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date transferDatetime;

    private Short identifyFlag;

    private String identifyRole;

    private String dataSource;

    private Integer dataLevel;

    private Short phyEvidenceFlag;

    private String reserve2;

    private String reserve3;

    private String reserve4;

    private String reserve5;

    private String reserve6;

    private String remark;

    @JSONField(name = "createDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createDatetime;

    private String createUser;

    @JSONField(name = "updateDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updateDatetime;

    private String updateUser;

    private String caseVersion3No;

    private String caseAcceptorPhone;

    private String isUrgent;

    private String isMurder;

    private String involveDeath;

    private Short deathNum;

    private String personInCharge;

    private String picDuty;

    private String picPhone;

    private String picCertificateType;

    private String picCertificateNo;

    private String auditor;

    private String auditorPhone;

    @JSONField(name = "auditDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date auditDatetime;

    private String systemCaseNo;

    private String caseSerialNo;

    private String isRegistered;

    private String extId;

    private String internalMatch;


    @JSONField(name = "localStoreDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date localStoreDatetime;

    @JSONField(name = "localCreateDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date localCreateDatetime;

    private String va;

    private String createPerson;

    private String updatePereson;

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

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo == null ? null : caseNo.trim();
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName == null ? null : caseName.trim();
    }

    public String getSceneRegionalism() {
        return sceneRegionalism;
    }

    public void setSceneRegionalism(String sceneRegionalism) {
        this.sceneRegionalism = sceneRegionalism == null ? null : sceneRegionalism.trim();
    }

    public String getScenePlace() {
        return scenePlace;
    }

    public void setScenePlace(String scenePlace) {
        this.scenePlace = scenePlace == null ? null : scenePlace.trim();
    }

    public Date getOccurrenceDatetime() {
        return occurrenceDatetime;
    }

    public void setOccurrenceDatetime(Date occurrenceDatetime) {
        this.occurrenceDatetime = occurrenceDatetime;
    }

    public String getCaseLevel() {
        return caseLevel;
    }

    public void setCaseLevel(String caseLevel) {
        this.caseLevel = caseLevel == null ? null : caseLevel.trim();
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType == null ? null : caseType.trim();
    }

    public String getCaseProperty() {
        return caseProperty;
    }

    public void setCaseProperty(String caseProperty) {
        this.caseProperty = caseProperty == null ? null : caseProperty.trim();
    }

    public String getHarmLevel() {
        return harmLevel;
    }

    public void setHarmLevel(String harmLevel) {
        this.harmLevel = harmLevel == null ? null : harmLevel.trim();
    }

    public String getSceneAreaType() {
        return sceneAreaType;
    }

    public void setSceneAreaType(String sceneAreaType) {
        this.sceneAreaType = sceneAreaType == null ? null : sceneAreaType.trim();
    }

    public String getCaseInfoOrigin() {
        return caseInfoOrigin;
    }

    public void setCaseInfoOrigin(String caseInfoOrigin) {
        this.caseInfoOrigin = caseInfoOrigin == null ? null : caseInfoOrigin.trim();
    }

    public String getCaseBrief() {
        return caseBrief;
    }

    public void setCaseBrief(String caseBrief) {
        this.caseBrief = caseBrief == null ? null : caseBrief.trim();
    }

    public String getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(String caseStatus) {
        this.caseStatus = caseStatus == null ? null : caseStatus.trim();
    }

    public Short getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Short deleteFlag) {
        this.deleteFlag = deleteFlag;
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

    public Short getIdentifyFlag() {
        return identifyFlag;
    }

    public void setIdentifyFlag(Short identifyFlag) {
        this.identifyFlag = identifyFlag;
    }

    public String getIdentifyRole() {
        return identifyRole;
    }

    public void setIdentifyRole(String identifyRole) {
        this.identifyRole = identifyRole == null ? null : identifyRole.trim();
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

    public Short getPhyEvidenceFlag() {
        return phyEvidenceFlag;
    }

    public void setPhyEvidenceFlag(Short phyEvidenceFlag) {
        this.phyEvidenceFlag = phyEvidenceFlag;
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

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public String getCaseVersion3No() {
        return caseVersion3No;
    }

    public void setCaseVersion3No(String caseVersion3No) {
        this.caseVersion3No = caseVersion3No == null ? null : caseVersion3No.trim();
    }

    public String getCaseAcceptorPhone() {
        return caseAcceptorPhone;
    }

    public void setCaseAcceptorPhone(String caseAcceptorPhone) {
        this.caseAcceptorPhone = caseAcceptorPhone == null ? null : caseAcceptorPhone.trim();
    }

    public String getIsUrgent() {
        return isUrgent;
    }

    public void setIsUrgent(String isUrgent) {
        this.isUrgent = isUrgent == null ? null : isUrgent.trim();
    }

    public String getIsMurder() {
        return isMurder;
    }

    public void setIsMurder(String isMurder) {
        this.isMurder = isMurder == null ? null : isMurder.trim();
    }

    public String getInvolveDeath() {
        return involveDeath;
    }

    public void setInvolveDeath(String involveDeath) {
        this.involveDeath = involveDeath == null ? null : involveDeath.trim();
    }

    public Short getDeathNum() {
        return deathNum;
    }

    public void setDeathNum(Short deathNum) {
        this.deathNum = deathNum;
    }

    public String getPersonInCharge() {
        return personInCharge;
    }

    public void setPersonInCharge(String personInCharge) {
        this.personInCharge = personInCharge == null ? null : personInCharge.trim();
    }

    public String getPicDuty() {
        return picDuty;
    }

    public void setPicDuty(String picDuty) {
        this.picDuty = picDuty == null ? null : picDuty.trim();
    }

    public String getPicPhone() {
        return picPhone;
    }

    public void setPicPhone(String picPhone) {
        this.picPhone = picPhone == null ? null : picPhone.trim();
    }

    public String getPicCertificateType() {
        return picCertificateType;
    }

    public void setPicCertificateType(String picCertificateType) {
        this.picCertificateType = picCertificateType == null ? null : picCertificateType.trim();
    }

    public String getPicCertificateNo() {
        return picCertificateNo;
    }

    public void setPicCertificateNo(String picCertificateNo) {
        this.picCertificateNo = picCertificateNo == null ? null : picCertificateNo.trim();
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor == null ? null : auditor.trim();
    }

    public String getAuditorPhone() {
        return auditorPhone;
    }

    public void setAuditorPhone(String auditorPhone) {
        this.auditorPhone = auditorPhone == null ? null : auditorPhone.trim();
    }

    public Date getAuditDatetime() {
        return auditDatetime;
    }

    public void setAuditDatetime(Date auditDatetime) {
        this.auditDatetime = auditDatetime;
    }

    public String getSystemCaseNo() {
        return systemCaseNo;
    }

    public void setSystemCaseNo(String systemCaseNo) {
        this.systemCaseNo = systemCaseNo == null ? null : systemCaseNo.trim();
    }

    public String getCaseSerialNo() {
        return caseSerialNo;
    }

    public void setCaseSerialNo(String caseSerialNo) {
        this.caseSerialNo = caseSerialNo == null ? null : caseSerialNo.trim();
    }

    public String getIsRegistered() {
        return isRegistered;
    }

    public void setIsRegistered(String isRegistered) {
        this.isRegistered = isRegistered == null ? null : isRegistered.trim();
    }

    public String getExtId() {
        return extId;
    }

    public void setExtId(String extId) {
        this.extId = extId == null ? null : extId.trim();
    }

    public String getInternalMatch() {
        return internalMatch;
    }

    public void setInternalMatch(String internalMatch) {
        this.internalMatch = internalMatch == null ? null : internalMatch.trim();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CaseInfo)) return false;

        CaseInfo caseInfo = (CaseInfo) o;

        if (id != null ? !id.equals(caseInfo.id) : caseInfo.id != null) return false;
        if (initServerNo != null ? !initServerNo.equals(caseInfo.initServerNo) : caseInfo.initServerNo != null)
            return false;
        if (labId != null ? !labId.equals(caseInfo.labId) : caseInfo.labId != null) return false;
        if (caseNo != null ? !caseNo.equals(caseInfo.caseNo) : caseInfo.caseNo != null) return false;
        if (caseName != null ? !caseName.equals(caseInfo.caseName) : caseInfo.caseName != null) return false;
        if (sceneRegionalism != null ? !sceneRegionalism.equals(caseInfo.sceneRegionalism) : caseInfo.sceneRegionalism != null)
            return false;
        if (scenePlace != null ? !scenePlace.equals(caseInfo.scenePlace) : caseInfo.scenePlace != null) return false;
        if (occurrenceDatetime != null ? !occurrenceDatetime.equals(caseInfo.occurrenceDatetime) : caseInfo.occurrenceDatetime != null)
            return false;
        if (caseLevel != null ? !caseLevel.equals(caseInfo.caseLevel) : caseInfo.caseLevel != null) return false;
        if (caseType != null ? !caseType.equals(caseInfo.caseType) : caseInfo.caseType != null) return false;
        if (caseProperty != null ? !caseProperty.equals(caseInfo.caseProperty) : caseInfo.caseProperty != null)
            return false;
        if (harmLevel != null ? !harmLevel.equals(caseInfo.harmLevel) : caseInfo.harmLevel != null) return false;
        if (sceneAreaType != null ? !sceneAreaType.equals(caseInfo.sceneAreaType) : caseInfo.sceneAreaType != null)
            return false;
        if (caseInfoOrigin != null ? !caseInfoOrigin.equals(caseInfo.caseInfoOrigin) : caseInfo.caseInfoOrigin != null)
            return false;
        if (caseBrief != null ? !caseBrief.equals(caseInfo.caseBrief) : caseInfo.caseBrief != null) return false;
        if (caseStatus != null ? !caseStatus.equals(caseInfo.caseStatus) : caseInfo.caseStatus != null) return false;
        if (deleteFlag != null ? !deleteFlag.equals(caseInfo.deleteFlag) : caseInfo.deleteFlag != null) return false;
        if (transferFlag != null ? !transferFlag.equals(caseInfo.transferFlag) : caseInfo.transferFlag != null)
            return false;
        if (transferUser != null ? !transferUser.equals(caseInfo.transferUser) : caseInfo.transferUser != null)
            return false;
        if (transferDatetime != null ? !transferDatetime.equals(caseInfo.transferDatetime) : caseInfo.transferDatetime != null)
            return false;
        if (identifyFlag != null ? !identifyFlag.equals(caseInfo.identifyFlag) : caseInfo.identifyFlag != null)
            return false;
        if (identifyRole != null ? !identifyRole.equals(caseInfo.identifyRole) : caseInfo.identifyRole != null)
            return false;
        if (dataSource != null ? !dataSource.equals(caseInfo.dataSource) : caseInfo.dataSource != null) return false;
        if (dataLevel != null ? !dataLevel.equals(caseInfo.dataLevel) : caseInfo.dataLevel != null) return false;
        if (phyEvidenceFlag != null ? !phyEvidenceFlag.equals(caseInfo.phyEvidenceFlag) : caseInfo.phyEvidenceFlag != null)
            return false;
        if (reserve2 != null ? !reserve2.equals(caseInfo.reserve2) : caseInfo.reserve2 != null) return false;
        if (reserve3 != null ? !reserve3.equals(caseInfo.reserve3) : caseInfo.reserve3 != null) return false;
        if (reserve4 != null ? !reserve4.equals(caseInfo.reserve4) : caseInfo.reserve4 != null) return false;
        if (reserve5 != null ? !reserve5.equals(caseInfo.reserve5) : caseInfo.reserve5 != null) return false;
        if (reserve6 != null ? !reserve6.equals(caseInfo.reserve6) : caseInfo.reserve6 != null) return false;
        if (remark != null ? !remark.equals(caseInfo.remark) : caseInfo.remark != null) return false;
        if (createDatetime != null ? !createDatetime.equals(caseInfo.createDatetime) : caseInfo.createDatetime != null)
            return false;
        if (createUser != null ? !createUser.equals(caseInfo.createUser) : caseInfo.createUser != null) return false;
        if (updateDatetime != null ? !updateDatetime.equals(caseInfo.updateDatetime) : caseInfo.updateDatetime != null)
            return false;
        if (updateUser != null ? !updateUser.equals(caseInfo.updateUser) : caseInfo.updateUser != null) return false;
        if (caseVersion3No != null ? !caseVersion3No.equals(caseInfo.caseVersion3No) : caseInfo.caseVersion3No != null)
            return false;
        if (caseAcceptorPhone != null ? !caseAcceptorPhone.equals(caseInfo.caseAcceptorPhone) : caseInfo.caseAcceptorPhone != null)
            return false;
        if (isUrgent != null ? !isUrgent.equals(caseInfo.isUrgent) : caseInfo.isUrgent != null) return false;
        if (isMurder != null ? !isMurder.equals(caseInfo.isMurder) : caseInfo.isMurder != null) return false;
        if (involveDeath != null ? !involveDeath.equals(caseInfo.involveDeath) : caseInfo.involveDeath != null)
            return false;
        if (deathNum != null ? !deathNum.equals(caseInfo.deathNum) : caseInfo.deathNum != null) return false;
        if (personInCharge != null ? !personInCharge.equals(caseInfo.personInCharge) : caseInfo.personInCharge != null)
            return false;
        if (picDuty != null ? !picDuty.equals(caseInfo.picDuty) : caseInfo.picDuty != null) return false;
        if (picPhone != null ? !picPhone.equals(caseInfo.picPhone) : caseInfo.picPhone != null) return false;
        if (picCertificateType != null ? !picCertificateType.equals(caseInfo.picCertificateType) : caseInfo.picCertificateType != null)
            return false;
        if (picCertificateNo != null ? !picCertificateNo.equals(caseInfo.picCertificateNo) : caseInfo.picCertificateNo != null)
            return false;
        if (auditor != null ? !auditor.equals(caseInfo.auditor) : caseInfo.auditor != null) return false;
        if (auditorPhone != null ? !auditorPhone.equals(caseInfo.auditorPhone) : caseInfo.auditorPhone != null)
            return false;
        if (auditDatetime != null ? !auditDatetime.equals(caseInfo.auditDatetime) : caseInfo.auditDatetime != null)
            return false;
        if (systemCaseNo != null ? !systemCaseNo.equals(caseInfo.systemCaseNo) : caseInfo.systemCaseNo != null)
            return false;
        if (caseSerialNo != null ? !caseSerialNo.equals(caseInfo.caseSerialNo) : caseInfo.caseSerialNo != null)
            return false;
        if (isRegistered != null ? !isRegistered.equals(caseInfo.isRegistered) : caseInfo.isRegistered != null)
            return false;
        if (extId != null ? !extId.equals(caseInfo.extId) : caseInfo.extId != null) return false;
        if (internalMatch != null ? !internalMatch.equals(caseInfo.internalMatch) : caseInfo.internalMatch != null)
            return false;
        if (localStoreDatetime != null ? !localStoreDatetime.equals(caseInfo.localStoreDatetime) : caseInfo.localStoreDatetime != null)
            return false;
        if (localCreateDatetime != null ? !localCreateDatetime.equals(caseInfo.localCreateDatetime) : caseInfo.localCreateDatetime != null)
            return false;
        return va != null ? va.equals(caseInfo.va) : caseInfo.va == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (initServerNo != null ? initServerNo.hashCode() : 0);
        result = 31 * result + (labId != null ? labId.hashCode() : 0);
        result = 31 * result + (caseNo != null ? caseNo.hashCode() : 0);
        result = 31 * result + (caseName != null ? caseName.hashCode() : 0);
        result = 31 * result + (sceneRegionalism != null ? sceneRegionalism.hashCode() : 0);
        result = 31 * result + (scenePlace != null ? scenePlace.hashCode() : 0);
        result = 31 * result + (occurrenceDatetime != null ? occurrenceDatetime.hashCode() : 0);
        result = 31 * result + (caseLevel != null ? caseLevel.hashCode() : 0);
        result = 31 * result + (caseType != null ? caseType.hashCode() : 0);
        result = 31 * result + (caseProperty != null ? caseProperty.hashCode() : 0);
        result = 31 * result + (harmLevel != null ? harmLevel.hashCode() : 0);
        result = 31 * result + (sceneAreaType != null ? sceneAreaType.hashCode() : 0);
        result = 31 * result + (caseInfoOrigin != null ? caseInfoOrigin.hashCode() : 0);
        result = 31 * result + (caseBrief != null ? caseBrief.hashCode() : 0);
        result = 31 * result + (caseStatus != null ? caseStatus.hashCode() : 0);
        result = 31 * result + (deleteFlag != null ? deleteFlag.hashCode() : 0);
        result = 31 * result + (transferFlag != null ? transferFlag.hashCode() : 0);
        result = 31 * result + (transferUser != null ? transferUser.hashCode() : 0);
        result = 31 * result + (transferDatetime != null ? transferDatetime.hashCode() : 0);
        result = 31 * result + (identifyFlag != null ? identifyFlag.hashCode() : 0);
        result = 31 * result + (identifyRole != null ? identifyRole.hashCode() : 0);
        result = 31 * result + (dataSource != null ? dataSource.hashCode() : 0);
        result = 31 * result + (dataLevel != null ? dataLevel.hashCode() : 0);
        result = 31 * result + (phyEvidenceFlag != null ? phyEvidenceFlag.hashCode() : 0);
        result = 31 * result + (reserve2 != null ? reserve2.hashCode() : 0);
        result = 31 * result + (reserve3 != null ? reserve3.hashCode() : 0);
        result = 31 * result + (reserve4 != null ? reserve4.hashCode() : 0);
        result = 31 * result + (reserve5 != null ? reserve5.hashCode() : 0);
        result = 31 * result + (reserve6 != null ? reserve6.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (createDatetime != null ? createDatetime.hashCode() : 0);
        result = 31 * result + (createUser != null ? createUser.hashCode() : 0);
        result = 31 * result + (updateDatetime != null ? updateDatetime.hashCode() : 0);
        result = 31 * result + (updateUser != null ? updateUser.hashCode() : 0);
        result = 31 * result + (caseVersion3No != null ? caseVersion3No.hashCode() : 0);
        result = 31 * result + (caseAcceptorPhone != null ? caseAcceptorPhone.hashCode() : 0);
        result = 31 * result + (isUrgent != null ? isUrgent.hashCode() : 0);
        result = 31 * result + (isMurder != null ? isMurder.hashCode() : 0);
        result = 31 * result + (involveDeath != null ? involveDeath.hashCode() : 0);
        result = 31 * result + (deathNum != null ? deathNum.hashCode() : 0);
        result = 31 * result + (personInCharge != null ? personInCharge.hashCode() : 0);
        result = 31 * result + (picDuty != null ? picDuty.hashCode() : 0);
        result = 31 * result + (picPhone != null ? picPhone.hashCode() : 0);
        result = 31 * result + (picCertificateType != null ? picCertificateType.hashCode() : 0);
        result = 31 * result + (picCertificateNo != null ? picCertificateNo.hashCode() : 0);
        result = 31 * result + (auditor != null ? auditor.hashCode() : 0);
        result = 31 * result + (auditorPhone != null ? auditorPhone.hashCode() : 0);
        result = 31 * result + (auditDatetime != null ? auditDatetime.hashCode() : 0);
        result = 31 * result + (systemCaseNo != null ? systemCaseNo.hashCode() : 0);
        result = 31 * result + (caseSerialNo != null ? caseSerialNo.hashCode() : 0);
        result = 31 * result + (isRegistered != null ? isRegistered.hashCode() : 0);
        result = 31 * result + (extId != null ? extId.hashCode() : 0);
        result = 31 * result + (internalMatch != null ? internalMatch.hashCode() : 0);
        result = 31 * result + (localStoreDatetime != null ? localStoreDatetime.hashCode() : 0);
        result = 31 * result + (localCreateDatetime != null ? localCreateDatetime.hashCode() : 0);
        result = 31 * result + (va != null ? va.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CaseInfo{" +
                "id='" + id + '\'' +
                ", initServerNo='" + initServerNo + '\'' +
                ", labId='" + labId + '\'' +
                ", caseNo='" + caseNo + '\'' +
                ", caseName='" + caseName + '\'' +
                ", sceneRegionalism='" + sceneRegionalism + '\'' +
                ", scenePlace='" + scenePlace + '\'' +
                ", occurrenceDatetime=" + occurrenceDatetime +
                ", caseLevel='" + caseLevel + '\'' +
                ", caseType='" + caseType + '\'' +
                ", caseProperty='" + caseProperty + '\'' +
                ", harmLevel='" + harmLevel + '\'' +
                ", sceneAreaType='" + sceneAreaType + '\'' +
                ", caseInfoOrigin='" + caseInfoOrigin + '\'' +
                ", caseBrief='" + caseBrief + '\'' +
                ", caseStatus='" + caseStatus + '\'' +
                ", deleteFlag=" + deleteFlag +
                ", transferFlag=" + transferFlag +
                ", transferUser='" + transferUser + '\'' +
                ", transferDatetime=" + transferDatetime +
                ", identifyFlag=" + identifyFlag +
                ", identifyRole='" + identifyRole + '\'' +
                ", dataSource='" + dataSource + '\'' +
                ", dataLevel=" + dataLevel +
                ", phyEvidenceFlag=" + phyEvidenceFlag +
                ", reserve2='" + reserve2 + '\'' +
                ", reserve3='" + reserve3 + '\'' +
                ", reserve4='" + reserve4 + '\'' +
                ", reserve5='" + reserve5 + '\'' +
                ", reserve6='" + reserve6 + '\'' +
                ", remark='" + remark + '\'' +
                ", createDatetime=" + createDatetime +
                ", createUser='" + createUser + '\'' +
                ", updateDatetime=" + updateDatetime +
                ", updateUser='" + updateUser + '\'' +
                ", caseVersion3No='" + caseVersion3No + '\'' +
                ", caseAcceptorPhone='" + caseAcceptorPhone + '\'' +
                ", isUrgent='" + isUrgent + '\'' +
                ", isMurder='" + isMurder + '\'' +
                ", involveDeath='" + involveDeath + '\'' +
                ", deathNum=" + deathNum +
                ", personInCharge='" + personInCharge + '\'' +
                ", picDuty='" + picDuty + '\'' +
                ", picPhone='" + picPhone + '\'' +
                ", picCertificateType='" + picCertificateType + '\'' +
                ", picCertificateNo='" + picCertificateNo + '\'' +
                ", auditor='" + auditor + '\'' +
                ", auditorPhone='" + auditorPhone + '\'' +
                ", auditDatetime=" + auditDatetime +
                ", systemCaseNo='" + systemCaseNo + '\'' +
                ", caseSerialNo='" + caseSerialNo + '\'' +
                ", isRegistered='" + isRegistered + '\'' +
                ", extId='" + extId + '\'' +
                ", internalMatch='" + internalMatch + '\'' +
                ", localStoreDatetime=" + localStoreDatetime +
                ", localCreateDatetime=" + localCreateDatetime +
                ", va='" + va + '\'' +
                '}';
    }
}