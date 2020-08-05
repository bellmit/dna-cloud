package com.bazl.dna.lims.model.po;

import java.io.Serializable;

/**
 * @Author: lzn
 * @Date: 2020/3/31 14:10
 * @Version: 1.0
 */
public class DnaNoSeq implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String code;

    private String currentVal;

    private String numIncrement;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCurrentVal() {
        return currentVal;
    }

    public void setCurrentVal(String currentVal) {
        this.currentVal = currentVal;
    }

    public String getNumIncrement() {
        return numIncrement;
    }

    public void setNumIncrement(String numIncrement) {
        this.numIncrement = numIncrement;
    }
}
