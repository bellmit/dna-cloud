package com.bazl.dna.lims.model.vo;

import com.bazl.dna.lims.model.po.SampleTable;

import java.io.Serializable;

/**
 * @Author: chenzeliang
 * @Date: 2020/3/30 17:48
 * @Version: 1.0
 */
public class SampleTableVo extends AbstractBaseVo<SampleTable> implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SampleTableVo() {
        this.entity = new SampleTable();
    }

    public SampleTableVo(SampleTable entity) {
        this.entity = entity;
    }

    private String elutionDefaultName;

    private String sampleNo;

    private String tabValue;

    public String getElutionDefaultName() {
        return elutionDefaultName;
    }

    public void setElutionDefaultName(String elutionDefaultName) {
        this.elutionDefaultName = elutionDefaultName;
    }

    public String getSampleNo() {
        return sampleNo;
    }

    public void setSampleNo(String sampleNo) {
        this.sampleNo = sampleNo;
    }

    public String getTabValue() {
        return tabValue;
    }

    public void setTabValue(String tabValue) {
        this.tabValue = tabValue;
    }
}
