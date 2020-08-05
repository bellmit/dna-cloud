package com.bazl.dna.lims.model.vo;

import com.bazl.dna.lims.model.po.AmPersonalInfo;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/4.
 */
public class AmPersonalInfoVo extends AbstractBaseVo<AmPersonalInfo> implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AmPersonalInfoVo() {
        super();
        this.entity = new AmPersonalInfo();
    }

    public AmPersonalInfoVo(AmPersonalInfo entity) {
        super();
        this.entity = entity;
    }

    private String positionName;

    private String genderName;

    public String getGenderName() {
        return genderName;
    }

    public void setGenderName(String genderName) {
        this.genderName = genderName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }
}
