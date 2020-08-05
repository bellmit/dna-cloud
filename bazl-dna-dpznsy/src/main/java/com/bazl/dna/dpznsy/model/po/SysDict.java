package com.bazl.dna.dpznsy.model.po;

import java.util.Date;

public class SysDict {
    private String id;

    private String dictName;

    private String parentDictName;

    private String rootFlag;

    private String dictKey;

    private String dictValue1;

    private String dictValue2;

    private String dictValue3;

    private Integer dictSort;

    private String isDefault;

    private String openFlag;

    private String remark;

    private String creatorId;

    private Date createAt;

    private String updatorId;

    private Date updateAt;

    private String isReadonly;

    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getParentDictName() {
        return parentDictName;
    }

    public void setParentDictName(String parentDictName) {
        this.parentDictName = parentDictName;
    }

    public String getRootFlag() {
        return rootFlag;
    }

    public void setRootFlag(String rootFlag) {
        this.rootFlag = rootFlag;
    }

    public String getDictKey() {
        return dictKey;
    }

    public void setDictKey(String dictKey) {
        this.dictKey = dictKey;
    }

    public String getDictValue1() {
        return dictValue1;
    }

    public void setDictValue1(String dictValue1) {
        this.dictValue1 = dictValue1;
    }

    public String getDictValue2() {
        return dictValue2;
    }

    public void setDictValue2(String dictValue2) {
        this.dictValue2 = dictValue2;
    }

    public String getDictValue3() {
        return dictValue3;
    }

    public void setDictValue3(String dictValue3) {
        this.dictValue3 = dictValue3;
    }

    public Integer getDictSort() {
        return dictSort;
    }

    public void setDictSort(Integer dictSort) {
        this.dictSort = dictSort;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getOpenFlag() {
        return openFlag;
    }

    public void setOpenFlag(String openFlag) {
        this.openFlag = openFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getUpdatorId() {
        return updatorId;
    }

    public void setUpdatorId(String updatorId) {
        this.updatorId = updatorId;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getIsReadonly() {
        return isReadonly;
    }

    public void setIsReadonly(String isReadonly) {
        this.isReadonly = isReadonly;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}