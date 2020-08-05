package com.bazl.dna.database.service.model.vo;

import com.bazl.dna.database.service.model.po.QuickCompareResultSame;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = false)
public class QuickCompareResultSameVo extends QuickCompareResultSame implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /*
    *   实验室名称
    * */
    private String labServerName;
    private String labServerNo;

    /**
     *  比中数据类型
     */
    private String personType;

    /**
     *  比中数据类型名称
     */
    private String personTypeName;

    /*
    *   比中样本编号
    * */
    private String matchSampleNo;

    /**
     * 比中样本名称
     */
    private String matchSampleName;

    /**
     * 比中案件编号
     */
    private String matchCaseNo;

    /**
     * 比中案件名称
     */
    private String matchCaseName;

    /*
    *  是否为物证样本
    * */
    private String evidenceFlag;

    /*
    *   基因map
    * */
    private Map<String, Object> geneMap;
    
    /**
     * 样本编号
     */
    private Integer sampleId;
}
