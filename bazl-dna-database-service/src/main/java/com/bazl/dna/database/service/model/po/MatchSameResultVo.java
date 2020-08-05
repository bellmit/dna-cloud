package com.bazl.dna.database.service.model.po;


import java.io.Serializable;

import lombok.Data;

/**
 * Created by Administrator on 2017/1/4.
 */
@Data
public class MatchSameResultVo implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String caseName;

    private String sampleName;

    private String sampleNo;

    private String delegateOrgName;

    private String instoredType;

    private String comparisonType;

    private String comparisonCategory;

    private String caseNo;

    private String delegateOrgCode;

    private String caseId;

    private String compareType;

}
