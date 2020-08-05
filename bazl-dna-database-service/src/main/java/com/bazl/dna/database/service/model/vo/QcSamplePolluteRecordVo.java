package com.bazl.dna.database.service.model.vo;

import com.bazl.dna.database.service.model.po.QcSamplePolluteRecord;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Map;

/**
 * 质控人员Vo
 * Created by lizhihua on 2020-04-12.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QcSamplePolluteRecordVo extends QcSamplePolluteRecord implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /*
   *   实验室名称
   * */
    private String labServerName;

	/**
     * 比中案件编号
     */
    private String matchCaseNo;

    /**
     * 比中案件名称
     */
    private String matchCaseName;

    /**
     * 样本类型
     */
    private String sampleType;

    /**
     * 样本类型名称
     */
    private String sampleTypeName;

    /**
     * 样本类型
     */
    private String panelName;

    /**
     *  比中数据类型
     */
    private String personType;

    /**
     *  比中数据类型名称
     */
    private String personTypeName;

    /**
     * 案件id
     */
    private Integer caseId;

    /*
    *   基因map
    * */
    private Map<String, Object> geneMap;
}
