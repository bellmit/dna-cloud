package com.bazl.dna.lims.core.model.vo;

import com.bazl.dna.lims.core.model.po.LabTaskInfo;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/4.
 */
public class LabTaskInfoVo extends AbstractBaseVo<LabTaskInfo> implements Serializable {

    public LabTaskInfoVo() {
        super();
        this.entity = new LabTaskInfo();
    }

    public LabTaskInfoVo(LabTaskInfo entity) {
        super();
        this.entity = entity;
    }

}
