package com.bazl.dna.lims.model.vo;

import com.bazl.dna.lims.model.po.ComporeSameQueue;

import java.io.Serializable;

/**
 * Created by admin on 2019/8/6.
 */
public class ComporeSameQueueVo  extends AbstractBaseVo<ComporeSameQueue> implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ComporeSameQueueVo() {
        super();
        this.entity = new ComporeSameQueue();
    }

    public ComporeSameQueueVo(ComporeSameQueue entity) {
        super();
        this.entity = entity;
    }

    private String sampleNo;

    private String sampleName;

    public String getSampleNo() {
        return sampleNo;
    }

    public void setSampleNo(String sampleNo) {
        this.sampleNo = sampleNo;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }
}
