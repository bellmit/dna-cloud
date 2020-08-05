package com.bazl.dna.database.service.model.vo;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.bazl.dna.database.service.model.po.CriminalPersonInfo;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 建库人员Vo
 * @author lizhihua on 2020-04-12.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CriminalPersonInfoVo extends CriminalPersonInfo implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    /*
    *   样本主键id
    * */
    private Integer sampleId;

	/**
     * 人员类别名称
     */
    private String criminalPersonTypeName;

    /**
     * 性别（名称）
     */
    private String personGenderName;

    /**
     * 民族
     */
    private String personRaceName;

    /**
     * 样本实验室编号
     */
    private String sampleLabNo;

    /**
     * 样本名称
     */
    private String sampleName;

    /**
     * 样本包装
     */
    private String samplePackage;

    /**
     * 样本类型
     */
    private String sampleType;


    /**
     * 样本类型名称
     */
    private String sampleTypeName;

    /**
     * 数据入库类型名称
     */
    private String instoreDataTypeName;

    /**
     * 数据入国家库编号
     */
    private String nationSysNo;

    /**
     * 采集单位
     */
    private String collectOrgName;

    /**
     * 采集时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private transient LocalDateTime collectDatetime;

    /**
     * 采集人
     */
    private String collectPerson;

    /**
     * 受理时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private transient LocalDateTime acceptDatetime;

}
