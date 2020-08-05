package com.bazl.dna.lims.model.vo;

import java.io.Serializable;

/**
 * Created by wangliu on 2019/12/5.
 */
public class SampleTypeCountVo implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int zjTotal = 0;
    int ggTotal = 0;
    int zzTotal = 0;
    int xyTotal = 0;
    int qtTotal = 0;
    int tlxbTotal = 0;
    int jbTotal = 0;
    int mfTotal = 0;
    int ycTotal = 0;
    int tybTotal = 0;

    public int getZjTotal() {
        return zjTotal;
    }

    public void setZjTotal(int zjTotal) {
        this.zjTotal = zjTotal;
    }

    public int getGgTotal() {
        return ggTotal;
    }

    public void setGgTotal(int ggTotal) {
        this.ggTotal = ggTotal;
    }

    public int getZzTotal() {
        return zzTotal;
    }

    public void setZzTotal(int zzTotal) {
        this.zzTotal = zzTotal;
    }

    public int getXyTotal() {
        return xyTotal;
    }

    public void setXyTotal(int xyTotal) {
        this.xyTotal = xyTotal;
    }

    public int getQtTotal() {
        return qtTotal;
    }

    public void setQtTotal(int qtTotal) {
        this.qtTotal = qtTotal;
    }

    public int getTlxbTotal() {
        return tlxbTotal;
    }

    public void setTlxbTotal(int tlxbTotal) {
        this.tlxbTotal = tlxbTotal;
    }

    public int getJbTotal() {
        return jbTotal;
    }

    public void setJbTotal(int jbTotal) {
        this.jbTotal = jbTotal;
    }

    public int getMfTotal() {
        return mfTotal;
    }

    public void setMfTotal(int mfTotal) {
        this.mfTotal = mfTotal;
    }

    public int getYcTotal() {
        return ycTotal;
    }

    public void setYcTotal(int ycTotal) {
        this.ycTotal = ycTotal;
    }

    public int getTybTotal() {
        return tybTotal;
    }

    public void setTybTotal(int tybTotal) {
        this.tybTotal = tybTotal;
    }
}
