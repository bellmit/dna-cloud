package com.bazl.dna.mix.connector.nation.model.vo;

import com.bazl.dna.mix.connector.nation.model.po.CaseInfo;

import java.io.Serializable;

public class CaseInfoVo extends AbstractBaseVo<CaseInfo> implements Serializable {

	private static final long serialVersionUID = 1L;

	public CaseInfoVo() {
		super();
		this.entity = new CaseInfo();
	}

	public CaseInfoVo(CaseInfo entity) {
		super();
		this.entity = entity;
	}

}
