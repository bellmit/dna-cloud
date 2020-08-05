package com.bazl.dna.database.service.model.vo;

import java.io.Serializable;

import com.bazl.dna.database.service.model.po.QcSampleInfo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 质控人员Vo
 * @author  lizhihua on 2020-04-12.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class QcSampleInfoVo extends QcSampleInfo implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 实验室名称
     */
    private String labServerName;

    /**
     * 质控样本类型 名称
     */
    private String qcSampleTypeName;

    /**
     * 质控人员类型 名称
     */
    private String qcPersonTypeName;

    /**
     * 性别
     */
    private String qcPersonGenderName;



}
