package com.bazl.dna.dpznsy.model.po;

import java.util.Date;

public class DictInfo {
    private String dictInfoId;

    private String dictTypeCode;

    private String dictTypeName;

    private Date createDatetime;

    private String createPerson;

    public String getDictInfoId() {
        return dictInfoId;
    }

    public void setDictInfoId(String dictInfoId) {
        this.dictInfoId = dictInfoId;
    }

    public String getDictTypeCode() {
        return dictTypeCode;
    }

    public void setDictTypeCode(String dictTypeCode) {
        this.dictTypeCode = dictTypeCode;
    }

    public String getDictTypeName() {
        return dictTypeName;
    }

    public void setDictTypeName(String dictTypeName) {
        this.dictTypeName = dictTypeName;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }
}