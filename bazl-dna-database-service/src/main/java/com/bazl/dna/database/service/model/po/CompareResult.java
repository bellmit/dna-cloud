package com.bazl.dna.database.service.model.po;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lizhihua 比对结果
 */
@Data
public class CompareResult implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int offset;
    protected int size;
    private String caseName;
    private String caseNo;
    private String sampleNo;
    private String sampleName;
    private String sampleType;
    private Date matchDatetime;
    private String LabserverName;
    private String groupType;
    private String reviewFlag;

}
