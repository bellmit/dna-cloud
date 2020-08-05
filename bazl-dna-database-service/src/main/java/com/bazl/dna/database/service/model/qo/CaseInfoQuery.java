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
 * 案件查询类
 * @author  lizhihua on 2020-04-12.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CaseInfoQuery extends AbstractQuery implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 案件主键id
     */
    private Integer id;

    /**
     *案件名称
     */
    private String caseName;
    private int caseNameCondition;

    /**
     *案件类型
     */
    private String caseType;

    /**
     *案件受理号
     */
    private String caseAcceptNo;
    private int caseAcceptNoCondition;

    /**
     *案件性质
     */
    private String caseProperty;

     /*
    *   案件性质多选
    * */
    private List<String> casePropertyList;


    /**
     *案件K号
     */
    private String sysXkNo;
    private int sysXkNoCondition;

    /**
     * 案发地点代码       （字典）
     */
    private String sceneRegionalism;

    /**
     * 案发地点详址
     */
    private String scenePlace;

    /**
     * 案件A号
     */
    private String sysCaseAno;
    private int sysCaseAnoCondition;

    /**
     * 案发起止时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date occurrenceStratDatetime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date occurrenceEndDatetime;

    /**
     * 国家库案件号
     */
    private String nationSysNo;
    private int nationSysNoCondition;

    /**
     * 是否为命案
     */
    private Integer isLifeCase;

    /**
     * 委托单位代码 （对应下拉框）
     */
    private String consignOrgId;

    /**
     * 委托单位名称
     */
    private String consignOrgName;
    /**
     * 委托人姓名
     */
    private String consignPersonName;
    /**
     * 委托人电话
     */
    private String consignPersonPhone;

    /**
     * 检材编号
     */
    private String sampleNo;
    private int sampleNoCondition;

    /**
     * 检材名称
     */
    private String sampleName;
    private int sampleNameCondition;


    /**
     * 物证编号
     */
    private String sampleEvidenceNo;
    private int sampleEvidenceNoCondition;

    /**
     * 检材类型
     */
    private String sampleType;

    /**
     * 检材类型list
     */
    private List<String> sampleTypeList;

    //人员
    /**
     * 人员类别
     */
    private String personType;

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
    private String acceptorName;

    /**
     * DNA实验室集合
     */
    private List<String> dnaLabList;

    /**
     * 委托单位集合
     */
    private List<String> clientOrgList;

    private String parentOrgFlag;

}
