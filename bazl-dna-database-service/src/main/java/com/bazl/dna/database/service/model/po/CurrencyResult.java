package com.bazl.dna.database.service.model.po;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CurrencyResult implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//案件
    private int offset;
    private int size;
    private String caseName;
    private String caseType;
    private String caseAcceptNo;
    private String caseProperty;
    private String sysXkNo;
    private String scenePlace;
    private String sysCaseAno;
    private Date occurrenceStratDatetime;
    private Date occurrenceEndDatetime;
    private String nationSysNo;
    private String isLifeCase;
    //委托
    private String consignOrgName;
    private String consignPersonName;
    private String consignPersonPhone;
    //检材
    private String sampleName;
    private String sampleType;
    private String sampleEvidenceNo;
    //人员
    private String personType;
    private String peresonName;
    private String personIdcardNo;
    private String personSex;
    private String personRace;
    //其他
    private String serverName;
    private Date acceptStartDate;
    private Date acceptEndDate;
    private String acceptName;

}
