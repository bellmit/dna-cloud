package com.bazl.dna.mix.connector.nation.model.po;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
/*
* locus_info
* 基因座信息表
* */
public class LocusInfo {
    private String id;

    private String locusType;

    private String locusName;

    private String nationalLocusName;

    private String alias;

    private String valueScope;

    private String remark;

    private Integer ord;

    private Short deleteFlag;

    private Short downloadFlag;

    private Short readonlyFlag;

    private Short coreLocusFlag;

    private String createUser;

    @JSONField(name = "createDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createDatetime;

    private String updateUser;

    @JSONField(name = "updateDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updateDatetime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getLocusType() {
        return locusType;
    }

    public void setLocusType(String locusType) {
        this.locusType = locusType == null ? null : locusType.trim();
    }

    public String getLocusName() {
        return locusName;
    }

    public void setLocusName(String locusName) {
        this.locusName = locusName == null ? null : locusName.trim();
    }

    public String getNationalLocusName() {
        return nationalLocusName;
    }

    public void setNationalLocusName(String nationalLocusName) {
        this.nationalLocusName = nationalLocusName == null ? null : nationalLocusName.trim();
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias == null ? null : alias.trim();
    }

    public String getValueScope() {
        return valueScope;
    }

    public void setValueScope(String valueScope) {
        this.valueScope = valueScope == null ? null : valueScope.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getOrd() {
        return ord;
    }

    public void setOrd(Integer ord) {
        this.ord = ord;
    }

    public Short getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Short deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Short getDownloadFlag() {
        return downloadFlag;
    }

    public void setDownloadFlag(Short downloadFlag) {
        this.downloadFlag = downloadFlag;
    }

    public Short getReadonlyFlag() {
        return readonlyFlag;
    }

    public void setReadonlyFlag(Short readonlyFlag) {
        this.readonlyFlag = readonlyFlag;
    }

    public Short getCoreLocusFlag() {
        return coreLocusFlag;
    }

    public void setCoreLocusFlag(Short coreLocusFlag) {
        this.coreLocusFlag = coreLocusFlag;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LocusInfo)) return false;

        LocusInfo locusInfo = (LocusInfo) o;

        if (id != null ? !id.equals(locusInfo.id) : locusInfo.id != null) return false;
        if (locusType != null ? !locusType.equals(locusInfo.locusType) : locusInfo.locusType != null) return false;
        if (locusName != null ? !locusName.equals(locusInfo.locusName) : locusInfo.locusName != null) return false;
        if (nationalLocusName != null ? !nationalLocusName.equals(locusInfo.nationalLocusName) : locusInfo.nationalLocusName != null)
            return false;
        if (alias != null ? !alias.equals(locusInfo.alias) : locusInfo.alias != null) return false;
        if (valueScope != null ? !valueScope.equals(locusInfo.valueScope) : locusInfo.valueScope != null) return false;
        if (remark != null ? !remark.equals(locusInfo.remark) : locusInfo.remark != null) return false;
        if (ord != null ? !ord.equals(locusInfo.ord) : locusInfo.ord != null) return false;
        if (deleteFlag != null ? !deleteFlag.equals(locusInfo.deleteFlag) : locusInfo.deleteFlag != null) return false;
        if (downloadFlag != null ? !downloadFlag.equals(locusInfo.downloadFlag) : locusInfo.downloadFlag != null)
            return false;
        if (readonlyFlag != null ? !readonlyFlag.equals(locusInfo.readonlyFlag) : locusInfo.readonlyFlag != null)
            return false;
        if (coreLocusFlag != null ? !coreLocusFlag.equals(locusInfo.coreLocusFlag) : locusInfo.coreLocusFlag != null)
            return false;
        if (createUser != null ? !createUser.equals(locusInfo.createUser) : locusInfo.createUser != null) return false;
        if (createDatetime != null ? !createDatetime.equals(locusInfo.createDatetime) : locusInfo.createDatetime != null)
            return false;
        if (updateUser != null ? !updateUser.equals(locusInfo.updateUser) : locusInfo.updateUser != null) return false;
        return updateDatetime != null ? updateDatetime.equals(locusInfo.updateDatetime) : locusInfo.updateDatetime == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (locusType != null ? locusType.hashCode() : 0);
        result = 31 * result + (locusName != null ? locusName.hashCode() : 0);
        result = 31 * result + (nationalLocusName != null ? nationalLocusName.hashCode() : 0);
        result = 31 * result + (alias != null ? alias.hashCode() : 0);
        result = 31 * result + (valueScope != null ? valueScope.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (ord != null ? ord.hashCode() : 0);
        result = 31 * result + (deleteFlag != null ? deleteFlag.hashCode() : 0);
        result = 31 * result + (downloadFlag != null ? downloadFlag.hashCode() : 0);
        result = 31 * result + (readonlyFlag != null ? readonlyFlag.hashCode() : 0);
        result = 31 * result + (coreLocusFlag != null ? coreLocusFlag.hashCode() : 0);
        result = 31 * result + (createUser != null ? createUser.hashCode() : 0);
        result = 31 * result + (createDatetime != null ? createDatetime.hashCode() : 0);
        result = 31 * result + (updateUser != null ? updateUser.hashCode() : 0);
        result = 31 * result + (updateDatetime != null ? updateDatetime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LocusInfo{" +
                "id='" + id + '\'' +
                ", locusType='" + locusType + '\'' +
                ", locusName='" + locusName + '\'' +
                ", nationalLocusName='" + nationalLocusName + '\'' +
                ", alias='" + alias + '\'' +
                ", valueScope='" + valueScope + '\'' +
                ", remark='" + remark + '\'' +
                ", ord=" + ord +
                ", deleteFlag=" + deleteFlag +
                ", downloadFlag=" + downloadFlag +
                ", readonlyFlag=" + readonlyFlag +
                ", coreLocusFlag=" + coreLocusFlag +
                ", createUser='" + createUser + '\'' +
                ", createDatetime=" + createDatetime +
                ", updateUser='" + updateUser + '\'' +
                ", updateDatetime=" + updateDatetime +
                '}';
    }
}