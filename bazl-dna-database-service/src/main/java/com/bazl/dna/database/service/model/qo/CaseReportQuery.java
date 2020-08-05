package com.bazl.dna.database.service.model.qo;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.bazl.dna.common.PageInfo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Created by Liuchang on 2020/6/8.
 * 案件上报信息查询 query
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CaseReportQuery extends AbstractQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "ID", type = IdType.AUTO)
    protected Integer id;

    @TableField("LIMS_CASE_UUID")
    protected String limsCaseUuid;

    /**
     * 实验室服务器编号
     */
    @TableField("LAB_SERVER_NO")
    protected String labServerNo;

    /**
     * 案件现场勘验编号
     */
    @TableField("SYS_XK_NO")
    protected String sysXkNo;

    /**
     * 案件A号
     */
    @TableField("SYS_CASE_ANO")
    protected String sysCaseAno;

    /**
     * 案件受理编号
     */
    @TableField("CASE_ACCEPT_NO")
    protected String caseAcceptNo;

    /**
     * 案件受理时间
     */
    @TableField("ACCEPT_DATETIME")
    protected transient LocalDateTime acceptDatetime;

    /**
     * 案件名称
     */
    @TableField("CASE_NAME")
    protected String caseName;

    /**
     * 案件类型，字典：CASE_TYPE
     */
    @TableField("CASE_TYPE")
    protected String caseType;

    /**
     * 一级案件性质，字典：CASE_PROPERTY
     */
    @TableField("CASE_PROPERTY")
    protected String caseProperty;

    /**
     * 一级案件性质名称，字典：CASE_PROPERTY
     */
    @TableField("CASE_PROPERTY_NAME")
    protected String casePropertyName;


    /**
     * 二级案件性质，字典：CASE_PROPERTY
     */
    @TableField("CASE_SUB_PROPERTY")
    protected String caseSubProperty;

    /**
     * 案件级别，字典：CASE_LEVEL
     */
    @TableField("CASE_LEVEL")
    protected String caseLevel;

    /**
     * 危害程度，字典：HARM_LEVEL
     */
    @TableField("HARM_LEVEL")
    protected String harmLevel;

    /**
     * 案发地区划代码 （行政区划表）
     */
    @TableField("SCENE_REGIONALISM")
    protected String sceneRegionalism;

    /**
     * 案发地详址
     */
    @TableField("SCENE_PLACE")
    protected String scenePlace;

    /**
     * 案发时间
     */
    @TableField("OCCURRENCE_DATETIME")
    protected transient LocalDateTime occurrenceDatetime;

    /**
     * 简要案情
     */
    @TableField("CASE_SUMMARY")
    protected String caseSummary;

    /**
     * 其他备注信息
     */
    @TableField("MORE_REMARKS")
    protected String moreRemarks;

    /**
     * 入国家库状态，0-Flase, 1-True
     */
    @TableField("TRANSFER_NATION_FLAG")
    protected Integer transferNationFlag;

    /**
     * 首次入国家库时间
     */
    protected transient LocalDateTime firstTransferNationTime;

    /**
     * 最后入国家库时间
     */
    @TableField("LAST_TRANSFER_NATION_TIME")
    protected transient LocalDateTime lastTransferNationTime;

    @TableField("TRANSFER_PERSON_ID")
    protected String transferPersonId;

    @TableField("TRANSFER_PERSON_NAME")
    protected String transferPersonName;

    /**
     * 国家库系统案件编号
     */
    @TableField("NATION_SYS_NO")
    protected String nationSysNo;

    /**
     * 删除标记，0-Flase， 1-True
     */
    @TableField("DELETE_FLAG")
    protected Integer deleteFlag;

    /**
     * 删除时间
     */
    @TableField("DELETE_DATETIME")
    protected transient LocalDateTime deleteDatetime;

    /**
     * 删除操作人姓名
     */
    @TableField("DELETE_PERSON")
    protected String deletePerson;

    /**
     * 删除原因
     */
    @TableField("DELETE_REASON")
    protected String deleteReason;

    /**
     * 案件创建时间
     */
    @TableField("STORE_DATETIME")
    protected transient LocalDateTime storeDatetime;

    /**
     * 案件创建人id
     */
    @TableField("STORE_PERSON_ID")
    protected String storePersonId;

    /**
     * 案件创建人姓名
     */
    @TableField("STORE_PERSON_NAME")
    protected String storePersonName;

    /**
     * 更新时间
     */
    @TableField("UPDATE_DATETIME")
    protected transient LocalDateTime updateDatetime;

    /**
     * 更新人id
     */
    @TableField("UPDATE_PERSON_ID")
    protected String updatePersonId;

    /**
     * 更新人姓名
     */
    @TableField("UPDATE_PERSON_NAME")
    protected String updatePersonName;

    /*
    *   是否命案
    * */
    @TableField("IS_LIFE_CASE")
    protected Integer isLifeCase;

    /*
     * 案件状态 是否破案 0：未破案； 1 已破案
     */
    @TableField("CASE_STATUS")
    protected Integer caseStatus;

    /*
     * 上报状态，字典：TRANSFER_STATUS
     */
    @TableField("TRANSFER_STATUS")
    protected String transferStatus;

    /*
     * 上报文件根目录
     */
    @TableField("TRANSFER_FILE_ROOT_PATH")
    protected String transferFileRootPath;

    /*
     * 上报文件名称
     */
    @TableField("TRANSFER_FILE_NAME")
    protected String transferFileName;

    /*
     * 上报失败信息
     */
    @TableField("TRANSFER_ERROR_MSG")
    protected String transferErrorMsg;

    /*
     * 检材编号
     */
    @TableField("SAMPLE_NO")
    protected String sampleNo;

    /*
     * 检材名称
     */
    @TableField("SAMPLE_NAME")
    protected String sampleName;

    /*
     * 受理人姓名
     */
    @TableField("ACCEPT_PERSON_NAME")
    protected String acceptPersonName;

    /*
     * 受理检材总数
     */
    protected int acceptSampleCount;

    /*
     * 提交入库时间
     */
    @TableField("SUMBIT_DATETIME")
    protected transient LocalDateTime sumbitDatetime;

    /*
     * 分页信息
     */
    private PageInfo pageInfo;

    /*
     * 提交入库检材数
     */
    private int sumbitSampleCount;

    /*
     * 转换案件序列ID
     */
    private int transferCaseQueueId;

    /*
     * 入库成功数
     */
    private int successTransferSampleCount;

    /*
     *入库失败数
     */
    private int failTransferSampleCount;

    /**
     * 待上报检材总数
     */
    private int waitReportSampleCount;

    /**
     * 上报失败检材数
     */
    private int failReportSampleCount;

    /**
     * 上报成功检材数
     */
    private int successReportSampleCount;

}
