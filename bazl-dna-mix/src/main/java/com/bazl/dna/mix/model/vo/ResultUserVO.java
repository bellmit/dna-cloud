package com.bazl.dna.mix.model.vo;

import java.io.Serializable;

/**
 * @author wanghaiyang
 * @date 2018/4/1.
 */
public class ResultUserVO implements Serializable {

	private static final long serialVersionUID = 1L;
    public String regionalismName;
    public String regionalismCode;
    public Boolean flag;
    public Boolean bOn;
    public String id;
    public String dictValue1;
    public String dictNationalKey;
    public String dictKey;

    public String getDictNationalKey() {
        return dictNationalKey;
    }

    public void setDictNationalKey(String dictNationalKey) {
        this.dictNationalKey = dictNationalKey;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public Boolean getbOn() {
        return bOn;
    }

    public void setbOn(Boolean bOn) {
        this.bOn = bOn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDictValue1() {
        return dictValue1;
    }

    public void setDictValue1(String dictValue1) {
        this.dictValue1 = dictValue1;
    }

    public String getDictKey() {
        return dictKey;
    }

    public void setDictKey(String dictKey) {
        this.dictKey = dictKey;
    }

    public String getRegionalismCode() {
        return regionalismCode;
    }

    public void setRegionalismCode(String regionalismCode) {
        this.regionalismCode = regionalismCode;
    }

    public String getRegionalismName() {
        return regionalismName;
    }

    public void setRegionalismName(String regionalismName) {
        this.regionalismName = regionalismName;
    }

}
