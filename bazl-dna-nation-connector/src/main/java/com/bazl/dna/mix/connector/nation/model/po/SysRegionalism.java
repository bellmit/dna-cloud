package com.bazl.dna.mix.connector.nation.model.po;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/*
*SYS_REGIONALISM
*行政区划表
* */
public class SysRegionalism {
    private String id;

    private String regionalismCode;

    private String regionalismName;

    private Short rootFlag;

    private String parentCode;

    private String spellShort;

    private Short activeFlag;

    private Short deleteFlag;

    private String remark;

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

    public String getRegionalismCode() {
        return regionalismCode;
    }

    public void setRegionalismCode(String regionalismCode) {
        this.regionalismCode = regionalismCode == null ? null : regionalismCode.trim();
    }

    public String getRegionalismName() {
        return regionalismName;
    }

    public void setRegionalismName(String regionalismName) {
        this.regionalismName = regionalismName == null ? null : regionalismName.trim();
    }

    public Short getRootFlag() {
        return rootFlag;
    }

    public void setRootFlag(Short rootFlag) {
        this.rootFlag = rootFlag;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode == null ? null : parentCode.trim();
    }

    public String getSpellShort() {
        return spellShort;
    }

    public void setSpellShort(String spellShort) {
        this.spellShort = spellShort == null ? null : spellShort.trim();
    }

    public Short getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Short activeFlag) {
        this.activeFlag = activeFlag;
    }

    public Short getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Short deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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
        if (!(o instanceof SysRegionalism)) return false;

        SysRegionalism that = (SysRegionalism) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (regionalismCode != null ? !regionalismCode.equals(that.regionalismCode) : that.regionalismCode != null)
            return false;
        if (regionalismName != null ? !regionalismName.equals(that.regionalismName) : that.regionalismName != null)
            return false;
        if (rootFlag != null ? !rootFlag.equals(that.rootFlag) : that.rootFlag != null) return false;
        if (parentCode != null ? !parentCode.equals(that.parentCode) : that.parentCode != null) return false;
        if (spellShort != null ? !spellShort.equals(that.spellShort) : that.spellShort != null) return false;
        if (activeFlag != null ? !activeFlag.equals(that.activeFlag) : that.activeFlag != null) return false;
        if (deleteFlag != null ? !deleteFlag.equals(that.deleteFlag) : that.deleteFlag != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
        if (createUser != null ? !createUser.equals(that.createUser) : that.createUser != null) return false;
        if (createDatetime != null ? !createDatetime.equals(that.createDatetime) : that.createDatetime != null)
            return false;
        if (updateUser != null ? !updateUser.equals(that.updateUser) : that.updateUser != null) return false;
        return updateDatetime != null ? updateDatetime.equals(that.updateDatetime) : that.updateDatetime == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (regionalismCode != null ? regionalismCode.hashCode() : 0);
        result = 31 * result + (regionalismName != null ? regionalismName.hashCode() : 0);
        result = 31 * result + (rootFlag != null ? rootFlag.hashCode() : 0);
        result = 31 * result + (parentCode != null ? parentCode.hashCode() : 0);
        result = 31 * result + (spellShort != null ? spellShort.hashCode() : 0);
        result = 31 * result + (activeFlag != null ? activeFlag.hashCode() : 0);
        result = 31 * result + (deleteFlag != null ? deleteFlag.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (createUser != null ? createUser.hashCode() : 0);
        result = 31 * result + (createDatetime != null ? createDatetime.hashCode() : 0);
        result = 31 * result + (updateUser != null ? updateUser.hashCode() : 0);
        result = 31 * result + (updateDatetime != null ? updateDatetime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SysRegionalism{" +
                "id='" + id + '\'' +
                ", regionalismCode='" + regionalismCode + '\'' +
                ", regionalismName='" + regionalismName + '\'' +
                ", rootFlag=" + rootFlag +
                ", parentCode='" + parentCode + '\'' +
                ", spellShort='" + spellShort + '\'' +
                ", activeFlag=" + activeFlag +
                ", deleteFlag=" + deleteFlag +
                ", remark='" + remark + '\'' +
                ", createUser='" + createUser + '\'' +
                ", createDatetime=" + createDatetime +
                ", updateUser='" + updateUser + '\'' +
                ", updateDatetime=" + updateDatetime +
                '}';
    }
}