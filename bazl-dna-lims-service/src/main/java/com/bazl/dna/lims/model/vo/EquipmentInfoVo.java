package com.bazl.dna.lims.model.vo;

import com.bazl.dna.lims.model.po.EquipmentInfo;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/4.
 */
public class EquipmentInfoVo extends AbstractBaseVo<EquipmentInfo> implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EquipmentInfoVo() {
        super();
        this.entity = new EquipmentInfo();
    }

    public EquipmentInfoVo(EquipmentInfo entity) {
        super();
        this.entity = entity;
    }

    private String equipmentName;

    private String equipmentStatusName;

    private String equipmentNameId;

    public String getEquipmentNameId() {
        return equipmentNameId;
    }

    public void setEquipmentNameId(String equipmentNameId) {
        this.equipmentNameId = equipmentNameId;
    }

    public String getEquipmentStatusName() {
        return equipmentStatusName;
    }

    public void setEquipmentStatusName(String equipmentStatusName) {
        this.equipmentStatusName = equipmentStatusName;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }
}
