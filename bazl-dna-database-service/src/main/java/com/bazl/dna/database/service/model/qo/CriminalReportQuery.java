package com.bazl.dna.database.service.model.qo;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.TableField;
import com.bazl.dna.common.PageInfo;
import com.bazl.dna.util.JsonDatetimeSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by Liuchang on 2020/6/9.
 * 违法犯罪人员上报数据查询
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CriminalReportQuery  extends AbstractQuery implements Serializable {


    /**
     *序列化
     */
    private static final long serialVersionUID = 1L;

    /**
     * 建库人员主键id
     */
    private Integer id;

    /**
     * 建库人员类别
     */
    @TableField("CRIMINAL_PERSON_TYPE")
    private String criminalPersonType;

    /**
     *建库人员名称
     */
    @TableField("CRIMINAL_PERSON_NAME")
    private String criminalPersonName;

    /**
     * 人员姓名
     */
    @TableField("PERSON_NAME")
    private String personName;
    private int personNameCondition;

    /**
     * 身份证号
     */
    @TableField("PERSON_IDCARD_NO")
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
    @TableField("SAMPLE_LAB_NO")
    private String sampleLabNo;

    private String sampleLabNoCondition;

    /**
     * 样本国家库编号
     */
    private String nationSysNo;
    private String nationSysNoCondition;


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


    /**
     * 样本采集时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date storeDatetime;

    /**
     * 血卡位置
     */
    @TableField("instore_location")
    private String instoreLocation;

    /**
     * 人员性别名称
     */
    @TableField("PERSON_GENDER_NAME")
    private String personGenderName;

    /**
     * 上报状态 0；待上报 1；上报生成文件成功 -1上报生成文件失败 2上报成功 -2上报失败
     */
    @TableField("transfer_status")
    private String transferStatus;

    /**
     * 引入分页对象
     */
    private PageInfo pageInfo;

}
