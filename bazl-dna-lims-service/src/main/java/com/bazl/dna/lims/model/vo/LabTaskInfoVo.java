package com.bazl.dna.lims.model.vo;

import com.bazl.dna.lims.model.po.LabTaskInfo;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/4.
 */
public class LabTaskInfoVo extends AbstractBaseVo<LabTaskInfo> implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LabTaskInfoVo() {
        super();
        this.entity = new LabTaskInfo();
    }

    public LabTaskInfoVo(LabTaskInfo entity) {
        super();
        this.entity = entity;
    }

}
