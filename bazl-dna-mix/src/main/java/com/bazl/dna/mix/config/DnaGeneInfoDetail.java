package com.bazl.dna.mix.config;

import java.io.Serializable;

/**
 * 基因信息详情
 * Created by lizhihua on 2020-03-10.
 */
public class DnaGeneInfoDetail implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}
