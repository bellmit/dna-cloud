package com.bazl.dna.database.nation.converter.model.po;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/*
* sys_dict
* 系统字典表
* */
public class SysDict {
    private String id;

    private String dictCategory;

    private String dictSubCategory;

    private Short rootFlag;

    private String dictKey;

    private String dictNationalKey;

    private String dictValue1;

    private String dictValue2;

    private String dictValue3;

    private String dictAlias;

    private String parentKey;

    private Short downloadFlag;

    private Short readonlyFlag;

    private Integer ord;

    private String dictPy;

    private Short activeFlag;

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

    public String getDictCategory() {
        return dictCategory;
    }

    public void setDictCategory(String dictCategory) {
        this.dictCategory = dictCategory == null ? null : dictCategory.trim();
    }

    public String getDictSubCategory() {
        return dictSubCategory;
    }

    public void setDictSubCategory(String dictSubCategory) {
        this.dictSubCategory = dictSubCategory == null ? null : dictSubCategory.trim();
    }

    public Short getRootFlag() {
        return rootFlag;
    }

    public void setRootFlag(Short rootFlag) {
        this.rootFlag = rootFlag;
    }

    public String getDictKey() {
        return dictKey;
    }

    public void setDictKey(String dictKey) {
        this.dictKey = dictKey == null ? null : dictKey.trim();
    }

    public String getDictNationalKey() {
        return dictNationalKey;
    }

    public void setDictNationalKey(String dictNationalKey) {
        this.dictNationalKey = dictNationalKey == null ? null : dictNationalKey.trim();
    }

    public String getDictValue1() {
        return dictValue1;
    }

    public void setDictValue1(String dictValue1) {
        this.dictValue1 = dictValue1 == null ? null : dictValue1.trim();
    }

    public String getDictValue2() {
        return dictValue2;
    }

    public void setDictValue2(String dictValue2) {
        this.dictValue2 = dictValue2 == null ? null : dictValue2.trim();
    }

    public String getDictValue3() {
        return dictValue3;
    }

    public void setDictValue3(String dictValue3) {
        this.dictValue3 = dictValue3 == null ? null : dictValue3.trim();
    }

    public String getDictAlias() {
        return dictAlias;
    }

    public void setDictAlias(String dictAlias) {
        this.dictAlias = dictAlias == null ? null : dictAlias.trim();
    }

    public String getParentKey() {
        return parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey == null ? null : parentKey.trim();
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

    public Integer getOrd() {
        return ord;
    }

    public void setOrd(Integer ord) {
        this.ord = ord;
    }

    public String getDictPy() {
        return dictPy;
    }

    public void setDictPy(String dictPy) {
        this.dictPy = dictPy == null ? null : dictPy.trim();
    }

    public Short getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Short activeFlag) {
        this.activeFlag = activeFlag;
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
        if (!(o instanceof SysDict)) return false;

        SysDict sysDict = (SysDict) o;

        if (id != null ? !id.equals(sysDict.id) : sysDict.id != null) return false;
        if (dictCategory != null ? !dictCategory.equals(sysDict.dictCategory) : sysDict.dictCategory != null)
            return false;
        if (dictSubCategory != null ? !dictSubCategory.equals(sysDict.dictSubCategory) : sysDict.dictSubCategory != null)
            return false;
        if (rootFlag != null ? !rootFlag.equals(sysDict.rootFlag) : sysDict.rootFlag != null) return false;
        if (dictKey != null ? !dictKey.equals(sysDict.dictKey) : sysDict.dictKey != null) return false;
        if (dictNationalKey != null ? !dictNationalKey.equals(sysDict.dictNationalKey) : sysDict.dictNationalKey != null)
            return false;
        if (dictValue1 != null ? !dictValue1.equals(sysDict.dictValue1) : sysDict.dictValue1 != null) return false;
        if (dictValue2 != null ? !dictValue2.equals(sysDict.dictValue2) : sysDict.dictValue2 != null) return false;
        if (dictValue3 != null ? !dictValue3.equals(sysDict.dictValue3) : sysDict.dictValue3 != null) return false;
        if (dictAlias != null ? !dictAlias.equals(sysDict.dictAlias) : sysDict.dictAlias != null) return false;
        if (parentKey != null ? !parentKey.equals(sysDict.parentKey) : sysDict.parentKey != null) return false;
        if (downloadFlag != null ? !downloadFlag.equals(sysDict.downloadFlag) : sysDict.downloadFlag != null)
            return false;
        if (readonlyFlag != null ? !readonlyFlag.equals(sysDict.readonlyFlag) : sysDict.readonlyFlag != null)
            return false;
        if (ord != null ? !ord.equals(sysDict.ord) : sysDict.ord != null) return false;
        if (dictPy != null ? !dictPy.equals(sysDict.dictPy) : sysDict.dictPy != null) return false;
        if (activeFlag != null ? !activeFlag.equals(sysDict.activeFlag) : sysDict.activeFlag != null) return false;
        if (remark != null ? !remark.equals(sysDict.remark) : sysDict.remark != null) return false;
        if (createUser != null ? !createUser.equals(sysDict.createUser) : sysDict.createUser != null) return false;
        if (createDatetime != null ? !createDatetime.equals(sysDict.createDatetime) : sysDict.createDatetime != null)
            return false;
        if (updateUser != null ? !updateUser.equals(sysDict.updateUser) : sysDict.updateUser != null) return false;
        return updateDatetime != null ? updateDatetime.equals(sysDict.updateDatetime) : sysDict.updateDatetime == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (dictCategory != null ? dictCategory.hashCode() : 0);
        result = 31 * result + (dictSubCategory != null ? dictSubCategory.hashCode() : 0);
        result = 31 * result + (rootFlag != null ? rootFlag.hashCode() : 0);
        result = 31 * result + (dictKey != null ? dictKey.hashCode() : 0);
        result = 31 * result + (dictNationalKey != null ? dictNationalKey.hashCode() : 0);
        result = 31 * result + (dictValue1 != null ? dictValue1.hashCode() : 0);
        result = 31 * result + (dictValue2 != null ? dictValue2.hashCode() : 0);
        result = 31 * result + (dictValue3 != null ? dictValue3.hashCode() : 0);
        result = 31 * result + (dictAlias != null ? dictAlias.hashCode() : 0);
        result = 31 * result + (parentKey != null ? parentKey.hashCode() : 0);
        result = 31 * result + (downloadFlag != null ? downloadFlag.hashCode() : 0);
        result = 31 * result + (readonlyFlag != null ? readonlyFlag.hashCode() : 0);
        result = 31 * result + (ord != null ? ord.hashCode() : 0);
        result = 31 * result + (dictPy != null ? dictPy.hashCode() : 0);
        result = 31 * result + (activeFlag != null ? activeFlag.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (createUser != null ? createUser.hashCode() : 0);
        result = 31 * result + (createDatetime != null ? createDatetime.hashCode() : 0);
        result = 31 * result + (updateUser != null ? updateUser.hashCode() : 0);
        result = 31 * result + (updateDatetime != null ? updateDatetime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SysDict{" +
                "id='" + id + '\'' +
                ", dictCategory='" + dictCategory + '\'' +
                ", dictSubCategory='" + dictSubCategory + '\'' +
                ", rootFlag=" + rootFlag +
                ", dictKey='" + dictKey + '\'' +
                ", dictNationalKey='" + dictNationalKey + '\'' +
                ", dictValue1='" + dictValue1 + '\'' +
                ", dictValue2='" + dictValue2 + '\'' +
                ", dictValue3='" + dictValue3 + '\'' +
                ", dictAlias='" + dictAlias + '\'' +
                ", parentKey='" + parentKey + '\'' +
                ", downloadFlag=" + downloadFlag +
                ", readonlyFlag=" + readonlyFlag +
                ", ord=" + ord +
                ", dictPy='" + dictPy + '\'' +
                ", activeFlag=" + activeFlag +
                ", remark='" + remark + '\'' +
                ", createUser='" + createUser + '\'' +
                ", createDatetime=" + createDatetime +
                ", updateUser='" + updateUser + '\'' +
                ", updateDatetime=" + updateDatetime +
                '}';
    }
}