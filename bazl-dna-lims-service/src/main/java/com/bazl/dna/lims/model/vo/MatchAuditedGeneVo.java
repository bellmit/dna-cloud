package com.bazl.dna.lims.model.vo;

import com.bazl.dna.lims.model.po.MatchAuditedGene;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/11/6.
 */
public class MatchAuditedGeneVo extends AbstractBaseVo<MatchAuditedGene> implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MatchAuditedGeneVo() {
        super();
        this.entity = new MatchAuditedGene();
    }

    public MatchAuditedGeneVo(MatchAuditedGene entity) {
        super();
        this.entity = entity;
    }
}
