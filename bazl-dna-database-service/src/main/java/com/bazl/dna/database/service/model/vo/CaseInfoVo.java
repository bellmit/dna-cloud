package com.bazl.dna.database.service.model.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.bazl.dna.database.service.model.po.CaseInfo;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by lizhihua on 2020-04-10.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CaseInfoVo extends CaseInfo implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 案件性质 （一级、二级）
     */
    private String casePropertyName;
    private String caseSubPropertyName;

    /**
     * 委托编号
     */
    private String consignmentId;


    /**
     * 委托单位名称
     */
    private String consignOrgName;

    /**
     * 委托登记时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private transient LocalDateTime consignmentRegDatetime;

    /**
     * 受理人姓名
     */
    private String acceptPersonName;

    /**
     * 受理时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private transient LocalDateTime acceptDatetime;

    /**
     * 实验室名称（受理单位名称）
     */
    private String labServerName;

    /**
     * 案件破获状态
     */
    private String caseCrackStatusName;

    /**
     * 案发时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    protected transient LocalDateTime casrDatetime;

}
