package com.bazl.dna.mix.compare.service.entity;

import java.util.List;

/*
* sys_dict
* 系统字典表
* */
public class SysDict {

    private String dictKey;

    private String dictValue1;

    private List<String> dictValue2;

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

    public List<String> getDictValue2() {
        return dictValue2;
    }

    public void setDictValue2(List<String> dictValue2) {
        this.dictValue2 = dictValue2;
    }
}