package com.bazl.dna.dpznsy.model.po;

import java.math.BigDecimal;
import java.util.Date;

public class AmCompanyInfo {
    private String companyId;

    private String companyName;

    private String isRoot;

    private String superCompanyName;

    private String superCompanyId;

    private String companyType;

    private Date createAt;

    private String creatorId;

    private Date updateAt;

    private String updatorId;

    private BigDecimal isDelete;

    private String remark;

    private String sysdictId;

    private String companySn;

    private BigDecimal companyOrder;

    private String status;

    private String password;

    private String fullname;

    private String orgCode;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getIsRoot() {
        return isRoot;
    }

    public void setIsRoot(String isRoot) {
        this.isRoot = isRoot == null ? null : isRoot.trim();
    }

    public String getSuperCompanyName() {
        return superCompanyName;
    }

    public void setSuperCompanyName(String superCompanyName) {
        this.superCompanyName = superCompanyName == null ? null : superCompanyName.trim();
    }

    public String getSuperCompanyId() {
        return superCompanyId;
    }

    public void setSuperCompanyId(String superCompanyId) {
        this.superCompanyId = superCompanyId == null ? null : superCompanyId.trim();
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType == null ? null : companyType.trim();
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

    public BigDecimal getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(BigDecimal isDelete) {
        this.isDelete = isDelete;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getSysdictId() {
        return sysdictId;
    }

    public void setSysdictId(String sysdictId) {
        this.sysdictId = sysdictId == null ? null : sysdictId.trim();
    }

    public String getCompanySn() {
        return companySn;
    }

    public void setCompanySn(String companySn) {
        this.companySn = companySn == null ? null : companySn.trim();
    }

    public BigDecimal getCompanyOrder() {
        return companyOrder;
    }

    public void setCompanyOrder(BigDecimal companyOrder) {
        this.companyOrder = companyOrder;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname == null ? null : fullname.trim();
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }
}