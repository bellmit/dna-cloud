package com.bazl.dna.database.service.model.qo;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MatchResultCodeQuery extends AbstractQuery implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int[] ids;
	private Integer resultCode;
	private String reviewResultDesc;
}
