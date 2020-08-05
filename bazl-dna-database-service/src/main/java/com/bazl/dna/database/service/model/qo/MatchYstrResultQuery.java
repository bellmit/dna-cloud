package com.bazl.dna.database.service.model.qo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class MatchYstrResultQuery extends AbstractQuery implements Serializable {

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
    private Integer caseId;

    /**
     *样本条码
     */
    private String sampleNo;

    /**
     *检材名称
     */
    private String sampleName;

    /**
     *检材类型
     */
    private String sampleType;
    private String sampleTypeName;

    /**
     *匹配位点个数
     */
    private Integer matchLocusCount;

    /**
     * 单位名称
     */
    private String unitName;

    /**
     * 比中起止时间
     */
    private Date compareStartDatetime;
    private Date compareEndDatetime;
    private Date caseAcceptStartDatetime;
    private Date caseAcceptEndDatetime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date matchDateTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date caseAcceptDateTime;
    
    private Integer matchSampleId;
    private String matchSampleNo;
    private String matchSampleName;
    private String matchCaseName;
    
    private String personName;
    private String personCard;
    private String orgCode;
    private String orgName;
    
    /**
     * 采集单位集合
     */
    private List<String> clientOrgList;
    
    /**
     * 案件编号
     */
    private String caseAcceptNo;
    
    /**
     * 案件性质
     */
    private String caseProperty;
    
    /**
     * 查询条件
     */
    private String[] sampleTypes;
    private String[] casePropertys;
    
    /**
     * 现场勘验编号
     */
    private String sysXkNo;

    /**
     * 比中状态
     */
    private Integer reviewFlag;
    
    /**
     * 复核状态
     */
    private String reviewResultCode;

    /**
     * 比对结果类别
     */
    private String groupType;

    /**
     * 案件破获状态
     */
    private String caseStates;
}
