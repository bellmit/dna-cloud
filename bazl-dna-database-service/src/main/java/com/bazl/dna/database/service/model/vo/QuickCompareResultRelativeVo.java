package com.bazl.dna.database.service.model.vo;

import com.bazl.dna.database.service.model.po.QuickCompareResultRelative;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = false)
public class QuickCompareResultRelativeVo extends QuickCompareResultRelative implements Serializable {

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
    *   基因map
    * */
    private Map<String, Object> geneMap;

    /*
    *   待比对样本标记  1：样本1,2：样本2
    * */
    private String sampleFlag;
}
