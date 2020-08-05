package com.bazl.dna.database.service.model.po;

import lombok.Data;

import java.io.Serializable;

@Data
public class MatterComparePerson implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
    private String caseAcceptNo;
    private String caseName;
    private String caseUncover;
    private String labServerNo;
    private String sampleNo;
    //源检材样本信息
    private String sampleName;
    private String sampleType;
    private String matchRegion;
    private String persionType;
    private String personname;
    private String personIdcardNo;
    private String matchLocusCount;
    //送检单位
    private String consignmentName;
    //案件破获状态
    private int  caseStatus;
    //人员类型名称
    private String personTypeName;
    //样本类型名称
    private String sampleTypeName;
    //比中目标样本名称
    private String targetSampleName;
    //比中目标样本编号
    private String targetSampleNo;
    //比中目标受理编号
    private String targetCaseAcceptNo;
}
