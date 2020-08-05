package com.bazl.dna.database.service.model.qo;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 建库人员查询类
 * Created by lizhihua on 2020-04-12.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QcSamplePolluteRecordQuery extends AbstractQuery implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 质控样本主键id
     */
    private Integer id;

    /**
     * 质控样本类型 名称
     */
    private String qcSampleTypeName;

    /*
    *    案件受理编号
    * */
    private String caseAcceptNo;

    /*
    *    案件名称
    * */
    private String caseName;
}
