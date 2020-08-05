package com.bazl.dna.database.service.model.vo;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class MatchResultVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String idCard;
	private String personName;
	private String consignOrgName;
	private String thirdNumber;
	private String caseNo;
	private String caseDesc;
	private String orgName;
	private String matchSampleName;

}
