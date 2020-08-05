package com.bazl.dna.mix.connector.nation.model.vo;

import com.bazl.dna.mix.connector.nation.model.po.SampleInfo;

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
