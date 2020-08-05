package com.bazl.dna.database.service.model.qo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 建库人员查询类
 * Created by lizhihua on 2020-04-12.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QcSampleInfoQuery extends AbstractQuery implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 质控样本主键id
     */
    private Integer id;

    /**
     * 质控样本类型，字典：QC_SAMPLE_TYPE
     */
    private String qcSampleType;

    /**
     * 样本编号
     */
    private String qcSampleNo;
    private int qcSampleNoCondition;

    /**
     * 样本名称
     */
    private String qcSampleName;
    private int qcSampleNameCondition;

    /**
     * 人员类别
     */
    private String qcPersonType;

    /**
     * 性别
     */
    private String qcPersonGender;

    /**
     * 姓名
     */
    private String qcPersonName;
    private int qcPersonNameCondition;

    /**
     * 人员身份证号
     */
    private String qcPersonIdcardNo;
    private int qcPersonIdcardNoCondition;

    /**
     * 所属单位名称
     */
    private String qcPersonOrgName;


    /**
     * 所属单位编码
     */
    private String qcPersonOrgCode;

    /**
     * 委托单位集合
     */
    private List<String> clientOrgList;

    private String parentOrgFlag;
}
