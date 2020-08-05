package com.bazl.dna.mix.connector.nation.model.po;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
/*
*
*
* */
public class PhysicalEvidence {
    private String id;

    private String initServerNo;

    private String labId;

    private String consignmentId;

    private String consignOrgCode;

    private String physicalEvidenceType;

    private String physicalEvidenceNo;

    private String physicalEvidenceName;

    private String description;

    private Short trustyFlag;

    private String utilization;

    private String secrecy;

    private Short transferFlag;

    private String transferUser;

    @JSONField(name = "transferDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date transferDatetime;

    private Short deleteFlag;

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

    @JSONField(name = "extractDaettime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date extractDaettime;

    private String preExamineDesc;

    private String consignPurpose;

    private String typeExtraInfo;

    private String specialDealOption;

    private String extractMethod;

    private String packageMethod;

    private String sampleSerialNo;

    private String extId;

    @JSONField(name = "localStoreDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date localStoreDatetime;

    @JSONField(name = "localCreateDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date localCreateDatetime;

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

    public String getPhysicalEvidenceType() {
        return physicalEvidenceType;
    }

    public void setPhysicalEvidenceType(String physicalEvidenceType) {
        this.physicalEvidenceType = physicalEvidenceType == null ? null : physicalEvidenceType.trim();
    }

    public String getPhysicalEvidenceNo() {
        return physicalEvidenceNo;
    }

    public void setPhysicalEvidenceNo(String physicalEvidenceNo) {
        this.physicalEvidenceNo = physicalEvidenceNo == null ? null : physicalEvidenceNo.trim();
    }

    public String getPhysicalEvidenceName() {
        return physicalEvidenceName;
    }

    public void setPhysicalEvidenceName(String physicalEvidenceName) {
        this.physicalEvidenceName = physicalEvidenceName == null ? null : physicalEvidenceName.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Short getTrustyFlag() {
        return trustyFlag;
    }

    public void setTrustyFlag(Short trustyFlag) {
        this.trustyFlag = trustyFlag;
    }

    public String getUtilization() {
        return utilization;
    }

    public void setUtilization(String utilization) {
        this.utilization = utilization == null ? null : utilization.trim();
    }

    public String getSecrecy() {
        return secrecy;
    }

    public void setSecrecy(String secrecy) {
        this.secrecy = secrecy == null ? null : secrecy.trim();
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

    public Short getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Short deleteFlag) {
        this.deleteFlag = deleteFlag;
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

    public Date getExtractDaettime() {
        return extractDaettime;
    }

    public void setExtractDaettime(Date extractDaettime) {
        this.extractDaettime = extractDaettime;
    }

    public String getPreExamineDesc() {
        return preExamineDesc;
    }

    public void setPreExamineDesc(String preExamineDesc) {
        this.preExamineDesc = preExamineDesc == null ? null : preExamineDesc.trim();
    }

    public String getConsignPurpose() {
        return consignPurpose;
    }

    public void setConsignPurpose(String consignPurpose) {
        this.consignPurpose = consignPurpose == null ? null : consignPurpose.trim();
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

    public String getExtractMethod() {
        return extractMethod;
    }

    public void setExtractMethod(String extractMethod) {
        this.extractMethod = extractMethod == null ? null : extractMethod.trim();
    }

    public String getPackageMethod() {
        return packageMethod;
    }

    public void setPackageMethod(String packageMethod) {
        this.packageMethod = packageMethod == null ? null : packageMethod.trim();
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
        if (!(o instanceof PhysicalEvidence)) return false;

        PhysicalEvidence that = (PhysicalEvidence) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (initServerNo != null ? !initServerNo.equals(that.initServerNo) : that.initServerNo != null) return false;
        if (labId != null ? !labId.equals(that.labId) : that.labId != null) return false;
        if (consignmentId != null ? !consignmentId.equals(that.consignmentId) : that.consignmentId != null)
            return false;
        if (consignOrgCode != null ? !consignOrgCode.equals(that.consignOrgCode) : that.consignOrgCode != null)
            return false;
        if (physicalEvidenceType != null ? !physicalEvidenceType.equals(that.physicalEvidenceType) : that.physicalEvidenceType != null)
            return false;
        if (physicalEvidenceNo != null ? !physicalEvidenceNo.equals(that.physicalEvidenceNo) : that.physicalEvidenceNo != null)
            return false;
        if (physicalEvidenceName != null ? !physicalEvidenceName.equals(that.physicalEvidenceName) : that.physicalEvidenceName != null)
            return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (trustyFlag != null ? !trustyFlag.equals(that.trustyFlag) : that.trustyFlag != null) return false;
        if (utilization != null ? !utilization.equals(that.utilization) : that.utilization != null) return false;
        if (secrecy != null ? !secrecy.equals(that.secrecy) : that.secrecy != null) return false;
        if (transferFlag != null ? !transferFlag.equals(that.transferFlag) : that.transferFlag != null) return false;
        if (transferUser != null ? !transferUser.equals(that.transferUser) : that.transferUser != null) return false;
        if (transferDatetime != null ? !transferDatetime.equals(that.transferDatetime) : that.transferDatetime != null)
            return false;
        if (deleteFlag != null ? !deleteFlag.equals(that.deleteFlag) : that.deleteFlag != null) return false;
        if (reserve1 != null ? !reserve1.equals(that.reserve1) : that.reserve1 != null) return false;
        if (reserve2 != null ? !reserve2.equals(that.reserve2) : that.reserve2 != null) return false;
        if (reserve3 != null ? !reserve3.equals(that.reserve3) : that.reserve3 != null) return false;
        if (reserve4 != null ? !reserve4.equals(that.reserve4) : that.reserve4 != null) return false;
        if (reserve5 != null ? !reserve5.equals(that.reserve5) : that.reserve5 != null) return false;
        if (reserve6 != null ? !reserve6.equals(that.reserve6) : that.reserve6 != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
        if (dataSource != null ? !dataSource.equals(that.dataSource) : that.dataSource != null) return false;
        if (dataLevel != null ? !dataLevel.equals(that.dataLevel) : that.dataLevel != null) return false;
        if (createUser != null ? !createUser.equals(that.createUser) : that.createUser != null) return false;
        if (createDatetime != null ? !createDatetime.equals(that.createDatetime) : that.createDatetime != null)
            return false;
        if (updateUser != null ? !updateUser.equals(that.updateUser) : that.updateUser != null) return false;
        if (updateDatetime != null ? !updateDatetime.equals(that.updateDatetime) : that.updateDatetime != null)
            return false;
        if (extractDaettime != null ? !extractDaettime.equals(that.extractDaettime) : that.extractDaettime != null)
            return false;
        if (preExamineDesc != null ? !preExamineDesc.equals(that.preExamineDesc) : that.preExamineDesc != null)
            return false;
        if (consignPurpose != null ? !consignPurpose.equals(that.consignPurpose) : that.consignPurpose != null)
            return false;
        if (typeExtraInfo != null ? !typeExtraInfo.equals(that.typeExtraInfo) : that.typeExtraInfo != null)
            return false;
        if (specialDealOption != null ? !specialDealOption.equals(that.specialDealOption) : that.specialDealOption != null)
            return false;
        if (extractMethod != null ? !extractMethod.equals(that.extractMethod) : that.extractMethod != null)
            return false;
        if (packageMethod != null ? !packageMethod.equals(that.packageMethod) : that.packageMethod != null)
            return false;
        if (sampleSerialNo != null ? !sampleSerialNo.equals(that.sampleSerialNo) : that.sampleSerialNo != null)
            return false;
        if (extId != null ? !extId.equals(that.extId) : that.extId != null) return false;
        if (localStoreDatetime != null ? !localStoreDatetime.equals(that.localStoreDatetime) : that.localStoreDatetime != null)
            return false;
        if (localCreateDatetime != null ? !localCreateDatetime.equals(that.localCreateDatetime) : that.localCreateDatetime != null)
            return false;
        return va != null ? va.equals(that.va) : that.va == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (initServerNo != null ? initServerNo.hashCode() : 0);
        result = 31 * result + (labId != null ? labId.hashCode() : 0);
        result = 31 * result + (consignmentId != null ? consignmentId.hashCode() : 0);
        result = 31 * result + (consignOrgCode != null ? consignOrgCode.hashCode() : 0);
        result = 31 * result + (physicalEvidenceType != null ? physicalEvidenceType.hashCode() : 0);
        result = 31 * result + (physicalEvidenceNo != null ? physicalEvidenceNo.hashCode() : 0);
        result = 31 * result + (physicalEvidenceName != null ? physicalEvidenceName.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (trustyFlag != null ? trustyFlag.hashCode() : 0);
        result = 31 * result + (utilization != null ? utilization.hashCode() : 0);
        result = 31 * result + (secrecy != null ? secrecy.hashCode() : 0);
        result = 31 * result + (transferFlag != null ? transferFlag.hashCode() : 0);
        result = 31 * result + (transferUser != null ? transferUser.hashCode() : 0);
        result = 31 * result + (transferDatetime != null ? transferDatetime.hashCode() : 0);
        result = 31 * result + (deleteFlag != null ? deleteFlag.hashCode() : 0);
        result = 31 * result + (reserve1 != null ? reserve1.hashCode() : 0);
        result = 31 * result + (reserve2 != null ? reserve2.hashCode() : 0);
        result = 31 * result + (reserve3 != null ? reserve3.hashCode() : 0);
        result = 31 * result + (reserve4 != null ? reserve4.hashCode() : 0);
        result = 31 * result + (reserve5 != null ? reserve5.hashCode() : 0);
        result = 31 * result + (reserve6 != null ? reserve6.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (dataSource != null ? dataSource.hashCode() : 0);
        result = 31 * result + (dataLevel != null ? dataLevel.hashCode() : 0);
        result = 31 * result + (createUser != null ? createUser.hashCode() : 0);
        result = 31 * result + (createDatetime != null ? createDatetime.hashCode() : 0);
        result = 31 * result + (updateUser != null ? updateUser.hashCode() : 0);
        result = 31 * result + (updateDatetime != null ? updateDatetime.hashCode() : 0);
        result = 31 * result + (extractDaettime != null ? extractDaettime.hashCode() : 0);
        result = 31 * result + (preExamineDesc != null ? preExamineDesc.hashCode() : 0);
        result = 31 * result + (consignPurpose != null ? consignPurpose.hashCode() : 0);
        result = 31 * result + (typeExtraInfo != null ? typeExtraInfo.hashCode() : 0);
        result = 31 * result + (specialDealOption != null ? specialDealOption.hashCode() : 0);
        result = 31 * result + (extractMethod != null ? extractMethod.hashCode() : 0);
        result = 31 * result + (packageMethod != null ? packageMethod.hashCode() : 0);
        result = 31 * result + (sampleSerialNo != null ? sampleSerialNo.hashCode() : 0);
        result = 31 * result + (extId != null ? extId.hashCode() : 0);
        result = 31 * result + (localStoreDatetime != null ? localStoreDatetime.hashCode() : 0);
        result = 31 * result + (localCreateDatetime != null ? localCreateDatetime.hashCode() : 0);
        result = 31 * result + (va != null ? va.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PhysicalEvidence{" +
                "id='" + id + '\'' +
                ", initServerNo='" + initServerNo + '\'' +
                ", labId='" + labId + '\'' +
                ", consignmentId='" + consignmentId + '\'' +
                ", consignOrgCode='" + consignOrgCode + '\'' +
                ", physicalEvidenceType='" + physicalEvidenceType + '\'' +
                ", physicalEvidenceNo='" + physicalEvidenceNo + '\'' +
                ", physicalEvidenceName='" + physicalEvidenceName + '\'' +
                ", description='" + description + '\'' +
                ", trustyFlag=" + trustyFlag +
                ", utilization='" + utilization + '\'' +
                ", secrecy='" + secrecy + '\'' +
                ", transferFlag=" + transferFlag +
                ", transferUser='" + transferUser + '\'' +
                ", transferDatetime=" + transferDatetime +
                ", deleteFlag=" + deleteFlag +
                ", reserve1='" + reserve1 + '\'' +
                ", reserve2='" + reserve2 + '\'' +
                ", reserve3='" + reserve3 + '\'' +
                ", reserve4='" + reserve4 + '\'' +
                ", reserve5='" + reserve5 + '\'' +
                ", reserve6='" + reserve6 + '\'' +
                ", remark='" + remark + '\'' +
                ", dataSource='" + dataSource + '\'' +
                ", dataLevel=" + dataLevel +
                ", createUser='" + createUser + '\'' +
                ", createDatetime=" + createDatetime +
                ", updateUser='" + updateUser + '\'' +
                ", updateDatetime=" + updateDatetime +
                ", extractDaettime=" + extractDaettime +
                ", preExamineDesc='" + preExamineDesc + '\'' +
                ", consignPurpose='" + consignPurpose + '\'' +
                ", typeExtraInfo='" + typeExtraInfo + '\'' +
                ", specialDealOption='" + specialDealOption + '\'' +
                ", extractMethod='" + extractMethod + '\'' +
                ", packageMethod='" + packageMethod + '\'' +
                ", sampleSerialNo='" + sampleSerialNo + '\'' +
                ", extId='" + extId + '\'' +
                ", localStoreDatetime=" + localStoreDatetime +
                ", localCreateDatetime=" + localCreateDatetime +
                ", va='" + va + '\'' +
                '}';
    }
}