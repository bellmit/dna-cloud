package com.bazl.dna.database.nation.converter.model.vo;

import com.bazl.dna.database.nation.converter.model.po.SampleInfo;

import java.io.Serializable;

public class SampleInfoVo extends AbstractBaseVo<SampleInfo> implements Serializable {

	private static final long serialVersionUID = 1L;

	public SampleInfoVo() {
		super();
		this.entity = new SampleInfo();
	}

	public SampleInfoVo(SampleInfo entity) {
		super();
		this.entity = entity;
	}

}
