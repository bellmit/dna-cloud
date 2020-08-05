package com.bazl.dna.database.service.model.po;


import lombok.Data;

import java.io.Serializable;

@Data
public class CurrencyQcResult implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int offset;
    private int size;
    private String qcSampleType;
    private String qcSampleNo;
    private String qcPersonType;
    private String qcSampleName;
    private String qcPersonName;
    private String qcPersonIdcardNo;
    private String qcPersonOrgName;
    private String sex;

}
