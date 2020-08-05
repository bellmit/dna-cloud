package com.bazl.dna.database.nation.converter.model.vo;

import com.bazl.dna.database.nation.converter.model.po.CaseInfo;

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
