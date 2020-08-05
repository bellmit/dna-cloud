package com.bazl.dna.lims.core.model.po;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class ErsReportInfo implements Serializable {
    private String id;

    private String caseBaseId;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date createAt;

    private String creatorId;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date updateAt;

    private String updatorId;

    private String isDelete;

    private String consignationMajorSn;

    private String caseName;

    private String superOrgId;

    private String superOrgName;

    private String status;

    private String inCharge;

    private String childOrgId;

    private String childOrgName;

    private String lastFileId;

    private Date printOverTime;

    private String signerId;

    private String printer;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date signAt;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private Date printAt;

    private String securityCode;

    private String isAppend;

    private String cancelRemarks;

    private String returnReason;

    private String needZgbCode;

    private String identifyBookPath;

    private Date delegateAt;

    private String fetchorPicture;

    private String operateType;

    private String maSeal;

    private String cnasSeal;
    private String signerPicture;
    private String signerInfo;

    /**
     * 输入年份
     */
    private String majorYear;
    /**
     * 输入编号
     */
    private String majorNumber;
    /**
     * 选中专业
     */
    private String majorName;
    /**
     * 封皮文档id
     */
    private String envelopeId;
    /**
     * 图片路径数组
     */
    private String imgArray;

    /**
     * 业务字段 领取单位名称
     */
    private String companyname;

    /**
     * 业务字段 鉴定打印时间
     */
    private Date printTime;
    /**
     * 业务字段 鉴定打印时间
     */
    private String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    public Date getPrintTime() {
        return printTime;
    }

    public void setPrintTime(Date printTime) {
        this.printTime = printTime;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }


    public String getImgArray() {
        return imgArray;
    }

    public void setImgArray(String imgArray) {
        this.imgArray = imgArray;
    }

    public String getEnvelopeId() {
        return envelopeId;
    }

    public void setEnvelopeId(String envelopeId) {
        this.envelopeId = envelopeId;
    }

    public String getMajorYear() {
        return majorYear;
    }

    public void setMajorYear(String majorYear) {
        this.majorYear = majorYear;
    }

    public String getMajorNumber() {
        return majorNumber;
    }

    public void setMajorNumber(String majorNumber) {
        this.majorNumber = majorNumber;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getSignerPicture() {
        return signerPicture;
    }

    public void setSignerPicture(String signerPicture) {
        this.signerPicture = signerPicture;
    }

    public String getSignerInfo() {
        return signerInfo;
    }

    public void setSignerInfo(String signerInfo) {
        this.signerInfo = signerInfo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCaseBaseId() {
        return caseBaseId;
    }

    public void setCaseBaseId(String caseBaseId) {
        this.caseBaseId = caseBaseId;
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
        this.creatorId = creatorId;
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
        this.updatorId = updatorId;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getConsignationMajorSn() {
        return consignationMajorSn;
    }

    public void setConsignationMajorSn(String consignationMajorSn) {
        this.consignationMajorSn = consignationMajorSn;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getSuperOrgId() {
        return superOrgId;
    }

    public void setSuperOrgId(String superOrgId) {
        this.superOrgId = superOrgId;
    }

    public String getSuperOrgName() {
        return superOrgName;
    }

    public void setSuperOrgName(String superOrgName) {
        this.superOrgName = superOrgName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInCharge() {
        return inCharge;
    }

    public void setInCharge(String inCharge) {
        this.inCharge = inCharge;
    }

    public String getChildOrgId() {
        return childOrgId;
    }

    public void setChildOrgId(String childOrgId) {
        this.childOrgId = childOrgId;
    }

    public String getChildOrgName() {
        return childOrgName;
    }

    public void setChildOrgName(String childOrgName) {
        this.childOrgName = childOrgName;
    }

    public String getLastFileId() {
        return lastFileId;
    }

    public void setLastFileId(String lastFileId) {
        this.lastFileId = lastFileId;
    }

    public Date getPrintOverTime() {
        return printOverTime;
    }

    public void setPrintOverTime(Date printOverTime) {
        this.printOverTime = printOverTime;
    }

    public String getSignerId() {
        return signerId;
    }

    public void setSignerId(String signerId) {
        this.signerId = signerId;
    }

    public String getPrinter() {
        return printer;
    }

    public void setPrinter(String printer) {
        this.printer = printer;
    }

    public Date getSignAt() {
        return signAt;
    }

    public void setSignAt(Date signAt) {
        this.signAt = signAt;
    }

    public Date getPrintAt() {
        return printAt;
    }

    public void setPrintAt(Date printAt) {
        this.printAt = printAt;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getIsAppend() {
        return isAppend;
    }

    public void setIsAppend(String isAppend) {
        this.isAppend = isAppend;
    }

    public String getCancelRemarks() {
        return cancelRemarks;
    }

    public void setCancelRemarks(String cancelRemarks) {
        this.cancelRemarks = cancelRemarks;
    }

    public String getReturnReason() {
        return returnReason;
    }

    public void setReturnReason(String returnReason) {
        this.returnReason = returnReason;
    }

    public String getNeedZgbCode() {
        return needZgbCode;
    }

    public void setNeedZgbCode(String needZgbCode) {
        this.needZgbCode = needZgbCode;
    }

    public String getIdentifyBookPath() {
        return identifyBookPath;
    }

    public void setIdentifyBookPath(String identifyBookPath) {
        this.identifyBookPath = identifyBookPath;
    }

    public Date getDelegateAt() {
        return delegateAt;
    }

    public void setDelegateAt(Date delegateAt) {
        this.delegateAt = delegateAt;
    }

    public String getFetchorPicture() {
        return fetchorPicture;
    }

    public void setFetchorPicture(String fetchorPicture) {
        this.fetchorPicture = fetchorPicture;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getMaSeal() {
        return maSeal;
    }

    public void setMaSeal(String maSeal) {
        this.maSeal = maSeal;
    }

    public String getCnasSeal() {
        return cnasSeal;
    }

    public void setCnasSeal(String cnasSeal) {
        this.cnasSeal = cnasSeal;
    }
}