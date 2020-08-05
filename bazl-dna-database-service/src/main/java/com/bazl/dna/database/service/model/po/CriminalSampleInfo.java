package com.bazl.dna.database.service.model.po;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 建库人员样本表
 * </p>
 *
 * @author lizhihua
 * @since 2020-04-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CriminalSampleInfo extends Model<CriminalSampleInfo> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    protected Integer id;

    /**
     * LIMS样本主键ID
     */
    @TableField("LIMS_SAMPLE_UUID")
    protected String limsSampleUuid;

    @TableField("CONSIGNMENT_ID")
    protected Integer consignmentId;

    /**
     * 实验室服务器编号
     */
    @TableField("LAB_SERVER_NO")
    protected String labServerNo;

    /**
     * 入库数据类型
     */
    @TableField("INSTORE_DATA_TYPE")
    protected String instoreDataType;

    /**
     * 关联人员id
     */
    @TableField("REF_PERSON_ID")
    protected Integer refPersonId;

    /**
     * 样本类型，字典: SAMPLE_TYPE
     */
    @TableField("SAMPLE_TYPE")
    protected String sampleType;

    /**
     * 样本实验室编号
     */
    @TableField("SAMPLE_LAB_NO")
    protected String sampleLabNo;

    /**
     * 样本名称
     */
    @TableField("SAMPLE_NAME")
    protected String sampleName;

    /**
     * 样本描述
     */
    @TableField("SAMPLE_DESC")
    protected String sampleDesc;

    /**
     * 样本包装
     */
    @TableField("SAMPLE_PACKAGE")
    protected String samplePackage;

    /**
     * 样本载体
     */
    @TableField("SAMPLE_CARRIER")
    protected String sampleCarrier;

    /**
     * 采集单位代码
     */
    @TableField("COLLECT_ORG_CODE")
    private String collectOrgCode;

    /**
     * 采集单位名称
     */
    @TableField("COLLECT_ORG_NAME")
    private String collectOrgName;

    /**
     * 采集时间
     */
    @TableField("COLLECT_DATETIME")
    private transient LocalDateTime collectDatetime;

    /**
     * 采集人姓名
     */
    @TableField("COLLECT_PERSON")
    private String collectPerson;

    /**
     * 采集人电话
     */
    @TableField("COLLECT_PHONE")
    private String collectPhone;

    /**
     * 受理单位代码
     */
    @TableField("ACCEPT_ORG_CODE")
    private String acceptOrgCode;

    /**
     * 受理单位名称
     */
    @TableField("ACCEPT_ORG_NAME")
    private String acceptOrgName;

    /**
     * 受理人id
     */
    @TableField("ACCEPT_PERSON_ID")
    private String acceptPersonId;
    /**
     * 受理人姓名
     */
    @TableField("ACCEPT_PERSON_NAME")
    private String acceptPersonName;

    /**
     * 受理时间
     */
    @TableField("ACCEPT_DATETIME")
    private String acceptDatetime;

    /**
     * 样本入国家库编号
     */
    @TableField("TRANSFER_FLAG")
    protected Integer transferFlag;

    @TableField("NATION_SYS_NO")
    protected String nationSysNo;

    @TableField("TRANSFER_DATETIME")
    protected transient LocalDateTime transferDatetime;

    @TableField("TRANSFER_PERSON_ID")
    protected String transferPersonId;

    @TableField("TRANSFER_PERSON_NAME")
    protected String transferPersonName;

    /**
     * 删除标记，0-False， 1-True
     */
    @TableField("DELETE_FLAG")
    protected Integer deleteFlag;

    /**
     * 删除原因
     */
    @TableField("DELETE_REASON")
    protected String deleteReason;

    /**
     * 删除时间
     */
    @TableField("DELETE_DATETIME")
    protected transient LocalDateTime deleteDatetime;

    /**
     * 删除操作人姓名
     */
    @TableField("DELETE_PERSON_NAME")
    protected String deletePersonName;

    @TableField("STORE_DATETIME")
    protected transient LocalDateTime storeDatetime;

    @TableField("STORE_PERSON_ID")
    protected String storePersonId;

    @TableField("STORE_PERSON_NAME")
    protected String storePersonName;

    @TableField("UPDATE_DATETIME")
    protected transient LocalDateTime updateDatetime;

    @TableField("UPDATE_PERSON_ID")
    protected String updatePersonId;

    @TableField("UPDATE_PERSON_NAME")
    protected String updatePersonName;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
