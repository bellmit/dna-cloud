package com.bazl.dna.database.service.model.vo;

import java.io.Serializable;
import java.util.Map;

import com.bazl.dna.database.service.model.po.QuickCompareResultMix;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class QuickCompareResultMixVo extends QuickCompareResultMix implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /*
    *   实验室名称
    * */
    private String labServerName;

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

}
