package com.bazl.dna.mix.algorithm.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 基因信息详情
 * @author lizhihua on 2020-03-10.
 */
@Data
public class DnaGeneInfoDetail implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
    private String value;

}
