package com.bazl.dna.database.service.model.qo;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MatchStrDatailQuery extends AbstractQuery implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 案件主键id
     */
    private int id;

    private Integer groupId;

    /**
     *案件名称
     */
    private String caseName;

    /**
     *检材名称
     */
    private String sampleName;

    /**
     * 检验人名称
     */
    private String personName;
}
