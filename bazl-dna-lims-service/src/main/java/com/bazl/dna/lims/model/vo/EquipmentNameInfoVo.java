package com.bazl.dna.lims.model.vo;

import com.bazl.dna.lims.model.po.EquipmentNameInfo;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/4.
 */
public class EquipmentNameInfoVo extends AbstractBaseVo<EquipmentNameInfo> implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EquipmentNameInfoVo() {
        super();
        this.entity = new EquipmentNameInfo();
    }

    public EquipmentNameInfoVo(EquipmentNameInfo entity) {
        super();
        this.entity = entity;
    }

    private String equipmentTypeName;

    public String getEquipmentTypeName() {
        return equipmentTypeName;
    }

    public void setEquipmentTypeName(String equipmentTypeName) {
        this.equipmentTypeName = equipmentTypeName;
    }
}
