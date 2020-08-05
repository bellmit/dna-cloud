package com.bazl.dna.lims.model.vo;

import com.bazl.dna.lims.model.po.EquipmentTypeInfo;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/4.
 */
public class EquipmentTypeInfoVo extends AbstractBaseVo<EquipmentTypeInfo> implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EquipmentTypeInfoVo() {
        super();
        this.entity = new EquipmentTypeInfo();
    }

    public EquipmentTypeInfoVo(EquipmentTypeInfo entity) {
        super();
        this.entity = entity;
    }

    private String experimentalStageName;

    public String getExperimentalStageName() {
        return experimentalStageName;
    }

    public void setExperimentalStageName(String experimentalStageName) {
        this.experimentalStageName = experimentalStageName;
    }
}
