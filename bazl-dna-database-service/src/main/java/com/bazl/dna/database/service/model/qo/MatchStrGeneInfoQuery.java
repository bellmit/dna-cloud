package com.bazl.dna.database.service.model.qo;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class MatchStrGeneInfoQuery extends AbstractQuery implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 案件主键id
     */
    private int id;

    /**
     * 审核人姓名
     */
    private String reviewPersonName;

    /**
     *案件名称
     */
    private String caseName;

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
     * 提交人
     */
    private String storePersonName;

    /**
     * 比中时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date compareDatetime;

    /**
     * 案件编号
     */
    private String caseNo;

    /**
     * 审核状态
     */
    private Integer reviewFlag;
    /**
     * 审核时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date reviewDatetime;

    /**
     * 试剂盒名称
     */
    private String panelName;

    /**
     * 基因信息
     */
    private JSONObject geneInfo;
}
