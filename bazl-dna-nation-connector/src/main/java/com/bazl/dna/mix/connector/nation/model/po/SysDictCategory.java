package com.bazl.dna.mix.connector.nation.model.po;
/*
*SYS_DICT_CATEGORY
*系统字典类型表
* */
public class SysDictCategory {
    private String id;

    private String dictCategoryCode;

    private String dictCategoryName;

    private String dictCategoryDesc;

    private Integer ord;

    private String deployLevels;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDictCategoryCode() {
        return dictCategoryCode;
    }

    public void setDictCategoryCode(String dictCategoryCode) {
        this.dictCategoryCode = dictCategoryCode == null ? null : dictCategoryCode.trim();
    }

    public String getDictCategoryName() {
        return dictCategoryName;
    }

    public void setDictCategoryName(String dictCategoryName) {
        this.dictCategoryName = dictCategoryName == null ? null : dictCategoryName.trim();
    }

    public String getDictCategoryDesc() {
        return dictCategoryDesc;
    }

    public void setDictCategoryDesc(String dictCategoryDesc) {
        this.dictCategoryDesc = dictCategoryDesc == null ? null : dictCategoryDesc.trim();
    }

    public Integer getOrd() {
        return ord;
    }

    public void setOrd(Integer ord) {
        this.ord = ord;
    }

    public String getDeployLevels() {
        return deployLevels;
    }

    public void setDeployLevels(String deployLevels) {
        this.deployLevels = deployLevels == null ? null : deployLevels.trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SysDictCategory)) return false;

        SysDictCategory that = (SysDictCategory) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (dictCategoryCode != null ? !dictCategoryCode.equals(that.dictCategoryCode) : that.dictCategoryCode != null)
            return false;
        if (dictCategoryName != null ? !dictCategoryName.equals(that.dictCategoryName) : that.dictCategoryName != null)
            return false;
        if (dictCategoryDesc != null ? !dictCategoryDesc.equals(that.dictCategoryDesc) : that.dictCategoryDesc != null)
            return false;
        if (ord != null ? !ord.equals(that.ord) : that.ord != null) return false;
        return deployLevels != null ? deployLevels.equals(that.deployLevels) : that.deployLevels == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (dictCategoryCode != null ? dictCategoryCode.hashCode() : 0);
        result = 31 * result + (dictCategoryName != null ? dictCategoryName.hashCode() : 0);
        result = 31 * result + (dictCategoryDesc != null ? dictCategoryDesc.hashCode() : 0);
        result = 31 * result + (ord != null ? ord.hashCode() : 0);
        result = 31 * result + (deployLevels != null ? deployLevels.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SysDictCategory{" +
                "id='" + id + '\'' +
                ", dictCategoryCode='" + dictCategoryCode + '\'' +
                ", dictCategoryName='" + dictCategoryName + '\'' +
                ", dictCategoryDesc='" + dictCategoryDesc + '\'' +
                ", ord=" + ord +
                ", deployLevels='" + deployLevels + '\'' +
                '}';
    }
}