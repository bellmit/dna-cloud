package com.bazl.dna.dpznsy.model.bo;

import java.io.Serializable;

public class MajorTypeModel implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String majorType;//专业类型
    private String majorTypeName;//专业名称

    public String getMajorType() {
        return majorType;
    }

    public void setMajorType(String majorType) {
        this.majorType = majorType;
    }

    public String getMajorTypeName() {
        return majorTypeName;
    }

    public void setMajorTypeName(String majorTypeName) {
        this.majorTypeName = majorTypeName;
    }
}
