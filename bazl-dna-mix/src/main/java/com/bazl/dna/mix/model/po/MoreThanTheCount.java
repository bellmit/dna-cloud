package com.bazl.dna.mix.model.po;

import java.io.Serializable;

/**
 * @Author: lzn
 * @Date: 2019/8/19 14:59
 * @Version: 1.
 */
public class MoreThanTheCount implements Serializable {
	
	private static final long serialVersionUID = 1L;
    private int moreThanThevalue;
    private String percent;

    public int getMoreThanThevalue() {
        return moreThanThevalue;
    }

    public void setMoreThanThevalue(int moreThanThevalue) {
        this.moreThanThevalue = moreThanThevalue;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }
}
