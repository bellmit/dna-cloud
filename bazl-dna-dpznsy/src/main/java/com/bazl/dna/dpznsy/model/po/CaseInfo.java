package com.bazl.dna.dpznsy.model.po;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.bazl.dna.util.JsonDatetimeSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * case_info
 * 案件信息表
 */
public class CaseInfo {
    private String baseId;

    private String caseId;

    private String consignationId;

    /**
     * 案件名称
     */
    private String name;

    /**
     * 委托书编号
     */
    private String tempSn;

    /**
     * 案件受理编号
     */
    private String caseSn;

    private String consignationMajorSn;

    private String status;

    private String statusName;

    private String major;

    private String majorName;

    private String isAppend;

    private String appendSn;

    /**
     * 案件性质
     */
    private String property;

    /**
     * 案件性质_名称
     */
    private String propertyName;

    /**
     * 案发地点
     */
    private String location;

    /**
     * 案发时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date crimeAt;

    /**
     * 简要案情
     */
    private String brief;

    private String type;

    /**
     * 委托人1姓名
     */
    private String consignationName1;

    /**
     * 证件名称及号码1
     */
    private String cardid1;

    /**
     * 委托人联系电话1
     */
    private String phone1;

    /**
     * 委托人1姓名2
     */
    private String consignationName2;

    /**
     * 证件名称及号码2
     */
    private String cardid2;

    /**
     * 委托人联系电话2
     */
    private String phone2;

    /**
     * 委托时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date delegateAt;

    /**
     * 委托单位name
     */
    private String createOrgName;

    /**
     * 委托单位id
     */
    private String createOrgId;

    private String acceptorName;

    private String acceptorId;

    private String isAccept;

    private String refuseReason;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date acceptAt;

    private String acceptComment;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date overtimeAt;

    private String overtimeReason;

    private String authorityMajor;

    private String criticalDepartment;

    private String resultQuerySn;

    private String captcha;

    private String testerId;

    private String testerName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date createAt;

    private String creatorId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date updateAt;

    private String updatorId;

    private String isDelete;

    private String remark;

    /**
     * 三版本编号
     */
    private String jzSn;

    /**
     * 现勘编号
     */
    private String xkSn;

    private String typeName;

    private String isHomicide;

    private String isCriminal;

    private String createOrgFullname;

    private String isTransfer;

    private String transferBaseId;

    private String overtimeReason2;

    private String overtimeReason3;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date overtimeAt2;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date overtimeAt3;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private String acceptAtStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private String acceptAtEnd;

    private String superCompanyName;

    private String superCompanyId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date acceptAtStart1;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date acceptAtEnd1;

    public Date getAcceptAtStart1() {
        return acceptAtStart1;
    }

    public void setAcceptAtStart1(Date acceptAtStart1) {
        this.acceptAtStart1 = acceptAtStart1;
    }

    public Date getAcceptAtEnd1() {
        return acceptAtEnd1;
    }

    public void setAcceptAtEnd1(Date acceptAtEnd1) {
        this.acceptAtEnd1 = acceptAtEnd1;
    }

    public String getSuperCompanyName() {
        return superCompanyName;
    }

    public void setSuperCompanyName(String superCompanyName) {
        this.superCompanyName = superCompanyName;
    }

    public String getSuperCompanyId() {
        return superCompanyId;
    }

    public void setSuperCompanyId(String superCompanyId) {
        this.superCompanyId = superCompanyId;
    }

    public String getAcceptAtStart() {
        return acceptAtStart;
    }

    public void setAcceptAtStart(String acceptAtStart) {
        this.acceptAtStart = acceptAtStart;
    }

    public String getAcceptAtEnd() {
        return acceptAtEnd;
    }

    public void setAcceptAtEnd(String acceptAtEnd) {
        this.acceptAtEnd = acceptAtEnd;
    }

    public String getBaseId() {
        return baseId;
    }

    public void setBaseId(String baseId) {
        this.baseId = baseId == null ? null : baseId.trim();
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId == null ? null : caseId.trim();
    }

    public String getConsignationId() {
        return consignationId;
    }

    public void setConsignationId(String consignationId) {
        this.consignationId = consignationId == null ? null : consignationId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getTempSn() {
        return tempSn;
    }

    public void setTempSn(String tempSn) {
        this.tempSn = tempSn == null ? null : tempSn.trim();
    }

    public String getCaseSn() {
        return caseSn;
    }

    public void setCaseSn(String caseSn) {
        this.caseSn = caseSn == null ? null : caseSn.trim();
    }

    public String getConsignationMajorSn() {
        return consignationMajorSn;
    }

    public void setConsignationMajorSn(String consignationMajorSn) {
        this.consignationMajorSn = consignationMajorSn == null ? null : consignationMajorSn.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName == null ? null : statusName.trim();
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major == null ? null : major.trim();
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName == null ? null : majorName.trim();
    }

    public String getIsAppend() {
        return isAppend;
    }

    public void setIsAppend(String isAppend) {
        this.isAppend = isAppend == null ? null : isAppend.trim();
    }

    public String getAppendSn() {
        return appendSn;
    }

    public void setAppendSn(String appendSn) {
        this.appendSn = appendSn == null ? null : appendSn.trim();
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property == null ? null : property.trim();
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName == null ? null : propertyName.trim();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public Date getCrimeAt() {
        return crimeAt;
    }

    public void setCrimeAt(Date crimeAt) {
        this.crimeAt = crimeAt;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief == null ? null : brief.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getConsignationName1() {
        return consignationName1;
    }

    public void setConsignationName1(String consignationName1) {
        this.consignationName1 = consignationName1 == null ? null : consignationName1.trim();
    }

    public String getCardid1() {
        return cardid1;
    }

    public void setCardid1(String cardid1) {
        this.cardid1 = cardid1 == null ? null : cardid1.trim();
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1 == null ? null : phone1.trim();
    }

    public String getConsignationName2() {
        return consignationName2;
    }

    public void setConsignationName2(String consignationName2) {
        this.consignationName2 = consignationName2 == null ? null : consignationName2.trim();
    }

    public String getCardid2() {
        return cardid2;
    }

    public void setCardid2(String cardid2) {
        this.cardid2 = cardid2 == null ? null : cardid2.trim();
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2 == null ? null : phone2.trim();
    }

    public Date getDelegateAt() {
        return delegateAt;
    }

    public void setDelegateAt(Date delegateAt) {
        this.delegateAt = delegateAt;
    }

    public String getCreateOrgName() {
        return createOrgName;
    }

    public void setCreateOrgName(String createOrgName) {
        this.createOrgName = createOrgName == null ? null : createOrgName.trim();
    }

    public String getCreateOrgId() {
        return createOrgId;
    }

    public void setCreateOrgId(String createOrgId) {
        this.createOrgId = createOrgId == null ? null : createOrgId.trim();
    }

    public String getAcceptorName() {
        return acceptorName;
    }

    public void setAcceptorName(String acceptorName) {
        this.acceptorName = acceptorName == null ? null : acceptorName.trim();
    }

    public String getAcceptorId() {
        return acceptorId;
    }

    public void setAcceptorId(String acceptorId) {
        this.acceptorId = acceptorId == null ? null : acceptorId.trim();
    }

    public String getIsAccept() {
        return isAccept;
    }

    public void setIsAccept(String isAccept) {
        this.isAccept = isAccept == null ? null : isAccept.trim();
    }

    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason == null ? null : refuseReason.trim();
    }

    public Date getAcceptAt() {
        return acceptAt;
    }

    public void setAcceptAt(Date acceptAt) {
        this.acceptAt = acceptAt;
    }

    public String getAcceptComment() {
        return acceptComment;
    }

    public void setAcceptComment(String acceptComment) {
        this.acceptComment = acceptComment == null ? null : acceptComment.trim();
    }

    public Date getOvertimeAt() {
        return overtimeAt;
    }

    public void setOvertimeAt(Date overtimeAt) {
        this.overtimeAt = overtimeAt;
    }

    public String getOvertimeReason() {
        return overtimeReason;
    }

    public void setOvertimeReason(String overtimeReason) {
        this.overtimeReason = overtimeReason == null ? null : overtimeReason.trim();
    }

    public String getAuthorityMajor() {
        return authorityMajor;
    }

    public void setAuthorityMajor(String authorityMajor) {
        this.authorityMajor = authorityMajor == null ? null : authorityMajor.trim();
    }

    public String getCriticalDepartment() {
        return criticalDepartment;
    }

    public void setCriticalDepartment(String criticalDepartment) {
        this.criticalDepartment = criticalDepartment == null ? null : criticalDepartment.trim();
    }

    public String getResultQuerySn() {
        return resultQuerySn;
    }

    public void setResultQuerySn(String resultQuerySn) {
        this.resultQuerySn = resultQuerySn == null ? null : resultQuerySn.trim();
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha == null ? null : captcha.trim();
    }

    public String getTesterId() {
        return testerId;
    }

    public void setTesterId(String testerId) {
        this.testerId = testerId == null ? null : testerId.trim();
    }

    public String getTesterName() {
        return testerName;
    }

    public void setTesterName(String testerName) {
        this.testerName = testerName == null ? null : testerName.trim();
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId == null ? null : creatorId.trim();
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getUpdatorId() {
        return updatorId;
    }

    public void setUpdatorId(String updatorId) {
        this.updatorId = updatorId == null ? null : updatorId.trim();
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete == null ? null : isDelete.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getJzSn() {
        return jzSn;
    }

    public void setJzSn(String jzSn) {
        this.jzSn = jzSn == null ? null : jzSn.trim();
    }

    public String getXkSn() {
        return xkSn;
    }

    public void setXkSn(String xkSn) {
        this.xkSn = xkSn == null ? null : xkSn.trim();
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    public String getIsHomicide() {
        return isHomicide;
    }

    public void setIsHomicide(String isHomicide) {
        this.isHomicide = isHomicide == null ? null : isHomicide.trim();
    }

    public String getIsCriminal() {
        return isCriminal;
    }

    public void setIsCriminal(String isCriminal) {
        this.isCriminal = isCriminal == null ? null : isCriminal.trim();
    }

    public String getCreateOrgFullname() {
        return createOrgFullname;
    }

    public void setCreateOrgFullname(String createOrgFullname) {
        this.createOrgFullname = createOrgFullname == null ? null : createOrgFullname.trim();
    }

    public String getIsTransfer() {
        return isTransfer;
    }

    public void setIsTransfer(String isTransfer) {
        this.isTransfer = isTransfer == null ? null : isTransfer.trim();
    }

    public String getTransferBaseId() {
        return transferBaseId;
    }

    public void setTransferBaseId(String transferBaseId) {
        this.transferBaseId = transferBaseId == null ? null : transferBaseId.trim();
    }

    public String getOvertimeReason2() {
        return overtimeReason2;
    }

    public void setOvertimeReason2(String overtimeReason2) {
        this.overtimeReason2 = overtimeReason2 == null ? null : overtimeReason2.trim();
    }

    public String getOvertimeReason3() {
        return overtimeReason3;
    }

    public void setOvertimeReason3(String overtimeReason3) {
        this.overtimeReason3 = overtimeReason3 == null ? null : overtimeReason3.trim();
    }

    public Date getOvertimeAt2() {
        return overtimeAt2;
    }

    public void setOvertimeAt2(Date overtimeAt2) {
        this.overtimeAt2 = overtimeAt2;
    }

    public Date getOvertimeAt3() {
        return overtimeAt3;
    }

    public void setOvertimeAt3(Date overtimeAt3) {
        this.overtimeAt3 = overtimeAt3;
    }
}