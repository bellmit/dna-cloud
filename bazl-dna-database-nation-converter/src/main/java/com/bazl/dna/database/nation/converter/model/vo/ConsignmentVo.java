package com.bazl.dna.database.nation.converter.model.vo;

import java.io.Serializable;

import com.bazl.dna.database.nation.converter.model.po.Consignment;

public class ConsignmentVo extends AbstractBaseVo<Consignment> implements Serializable {

	private static final long serialVersionUID = 1L;

	public ConsignmentVo() {
		super();
		this.entity = new Consignment();
	}

	public ConsignmentVo(Consignment entity) {
		super();
		this.entity = entity;
	}

}
