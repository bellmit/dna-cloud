package com.bazl.dna.database.service.model.qo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.bazl.dna.util.JsonDatetimeSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 建库人员查询类
 * @author lizhihua on 2020-04-12.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CriminalPersonInfoQuery extends AbstractQuery implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 建库人员主键id
     */
    private Integer id;

    /**
     * 建库人员类别
     */
    private String criminalPersonType;

    /**
     * 人员姓名
     */
    private String personName;
    private int personNameCondition;

    /**
     * 身份证号
     */
    private String personIdcardNo;
    private int personIdcardNoCondition;

    /**
     * 性别
     */
    private String personSex;

    /**
     * 民族
     */
    private String personRace;


    /**
     * 一级采集单位代码
     */
    private String collectOrgTopCode;

    /**
     * 二级采集单位代码
     */
    private String collectOrgSecondCode;

    /**
     * 采集人姓名
     */
    private String collectPersonName;

    /**
     * 采集人电话
     */
    private String collectPersonPhone;

    /**
     * 采集起止日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date collectDatetimeStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date collectDatetimeEndDate;

    /**
     * 样本dna编号
     */
    private String sampleLabNo;
    private String sampleLabNoCondition;

    /**
     * 样本国家库编号
     */
    private String nationSysNo;
    private String nationSysNoCondition;


    //其他
    /**
     * 受理实验室  （对应下拉框选中的code值）
     */
    private String labServerNo;

    /**
     * 受理起止日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date acceptStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date acceptEndDate;

    /**
     * 受理人
     */
    private String acceptPersonId;
    private String acceptPersonName;

    //DNA实验室下拉框
    private List<String> dnaLabList;


    /**
     * 采集单位集合
     */
    private List<String> clientOrgList;

    private String parentOrgFlag;

}
