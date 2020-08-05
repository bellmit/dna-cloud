package com.bazl.dna.database.service.model.po;

    import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* <p>
    * DNA样本信息表
    * </p>
*
* @author lizhihua
* @since 2020-02-11
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DnaSampleInfo extends Model<DnaSampleInfo> implements Serializable {

    private static final long serialVersionUID = 1L;

            @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;


    @TableField("LIMS_SAMPLE_UUID")
    private String limsSampleUuid;


            /**
            * 实验室服务器编号
            */
        @TableField("LAB_SERVER_NO")
    private String labServerNo;



    /**
     * 样本入库类型
     */
    @TableField("INSTORE_DATA_TYPE")
    private String instoreDataType;

            /**
            * 样本id
            */
        @TableField("CASE_ID")
    private Integer caseId;

            /**
            * 委托id
            */
        @TableField("CONSIGNMENT_ID")
    private Integer consignmentId;

            /**
            * 是否为物证样本， 1-True，2-False
            */
        @TableField("EVIDENCE_FLAG")
    private Integer evidenceFlag;

            /**
            * 关联人员id
            */
            @TableField("REF_PERSON_ID")
    private Integer refPersonId;

            /**
            * 样本类型，字典: SAMPLE_TYPE
            */
        @TableField("SAMPLE_TYPE")
    private String sampleType;

        /**
        * 样本实验室编号
        */
    @TableField("SAMPLE_LAB_NO")
    private String sampleLabNo;

        /**
        * 物证编号
        */
    @TableField("SAMPLE_EVIDENCE_NO")
    private String sampleEvidenceNo;

        /**
        * 样本名称
        */
    @TableField("SAMPLE_NAME")
    private String sampleName;

    /**
    * 样本描述
    */
    @TableField("SAMPLE_DESC")
    private String sampleDesc;

    /**
    * 样本包装
    */
    @TableField("SAMPLE_PACKAGE")
    private String samplePackage;

    /**
    * 样本载体
    */
    @TableField("SAMPLE_CARRIER")
    private String sampleCarrier;

    @TableField("TRANSFER_FLAG")
    private Integer transferFlag;

    @TableField("TRANSFER_DATETIME")
    private transient LocalDateTime transferDatetime;

    @TableField("TRANSFER_PERSON_ID")
    private String transferPersonId;

    @TableField("TRANSFER_PERSON_NAME")
    private String transferPersonName;

    /**
    * 删除标记，0-False， 1-True
    */
    @TableField("DELETE_FLAG")
    private Integer deleteFlag;

        /**
        * 删除原因
        */
    @TableField("DELETE_REASON")
    private String deleteReason;

            /**
            * 删除时间
            */
    @TableField("DELETE_DATETIME")
    private transient LocalDateTime deleteDatetime;

    /**
    * 删除操作人姓名
    */
    @TableField("DELETE_PERSON_NAME")
    private String deletePersonName;

    @TableField("STORE_DATETIME")
    private transient LocalDateTime storeDatetime;

    @TableField("STORE_PERSON_ID")
    private String storePersonId;

    @TableField("STORE_PERSON_NAME")
    private String storePersonName;

    @TableField("UPDATE_DATETIME")
    private transient LocalDateTime updateDatetime;

    @TableField("UPDATE_PERSON_ID")
    private String updatePersonId;

    @TableField("UPDATE_PERSON_NAME")
    private String updatePersonName;

    @TableField(exist = false)
    private List<DnaSampleImage> dnaSampleImageList;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
