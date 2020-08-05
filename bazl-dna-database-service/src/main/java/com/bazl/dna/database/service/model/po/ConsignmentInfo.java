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
    * 委托信息表
    * </p>
*
* @author lizhihua
* @since 2020-02-11
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ConsignmentInfo extends Model<ConsignmentInfo> implements Serializable {

    private static final long serialVersionUID = 1L;

            /**
            * 主键id
            */
            @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;


    @TableField("LIMS_CONSIGNMENT_UUID")
    private String limsConsignmentUuid;

            /**
            * 关联案件id
            */
        @TableField("CASE_ID")
    private Integer caseId;

            /**
            * 实验室服务器编号
            */
        @TableField("LAB_SERVER_NO")
    private String labServerNo;

            /**
            * 委托书编号
            */
        @TableField("CONSIGNMENT_FILE_NO")
    private String consignmentFileNo;

            /**
            * 现勘系统委托编号
            */
        @TableField("SYS_XK_WTNO")
    private String sysXkWtno;

            /**
            * 委托单位ID
            */
        @TableField("CONSIGN_ORG_ID")
    private String consignOrgId;

            /**
            * 委托单位机构代码
            */
        @TableField("CONSIGN_ORG_CODE")
    private String consignOrgCode;

            /**
            * 委托单位名称
            */
        @TableField("CONSIGN_ORG_NAME")
    private String consignOrgName;

            /**
            * 委托单位电话
            */
        @TableField("CONSIGN_ORG_PHONE")
    private String consignOrgPhone;

            /**
            * 委托单位传真号码
            */
        @TableField("CONSIGN_ORG_FAX")
    private String consignOrgFax;

            /**
            * 委托单位地址
            */
        @TableField("CONSIGN_ORG_ADDRESS")
    private String consignOrgAddress;

            /**
            * 委托单位邮编
            */
        @TableField("CONSIGN_ORG_ZIP_CODE")
    private String consignOrgZipCode;

            /**
            * 委托人1ID
            */
        @TableField("CONSIGN_PERSON1_ID")
    private String consignPerson1Id;

            /**
            * 委托人1姓名
            */
        @TableField("CONSIGN_PERSON1_NAME")
    private String consignPerson1Name;

            /**
            * 委托人1电话
            */
        @TableField("CONSIGN_PERSON1_PHONE")
    private String consignPerson1Phone;

            /**
            * 委托人1证件类型，字典：CERTIFICATE_TYPE
            */
        @TableField("CONSIGN_PERSON1_CERTIFICATE_TYPE")
    private String consignPerson1CertificateType;

            /**
            * 委托人1证件号码
            */
        @TableField("CONSIGN_PERSON1_CERTIFICATE_NO")
    private String consignPerson1CertificateNo;

            /**
            * 委托人1职务名称
            */
        @TableField("CONSIGN_PERSON1_POSITION_NAME")
    private String consignPerson1PositionName;

            /**
            * 委托人2ID
            */
        @TableField("CONSIGN_PERSON2_ID")
    private String consignPerson2Id;

            /**
            * 委托人2姓名
            */
        @TableField("CONSIGN_PERSON2_NAME")
    private String consignPerson2Name;

            /**
            * 委托人2电话
            */
        @TableField("CONSIGN_PERSON2_PHONE")
    private String consignPerson2Phone;

            /**
            * 委托人2证件类型，字典：CERTIFICATE_TYPE
            */
        @TableField("CONSIGN_PERSON2_CERTIFICATE_TYPE")
    private String consignPerson2CertificateType;

            /**
            * 委托人2证件号码
            */
        @TableField("CONSIGN_PERSON2_CERTIFICATE_NO")
    private String consignPerson2CertificateNo;

            /**
            * 委托人2职务名称
            */
        @TableField("CONSIGN_PERSON2_POSITION_NAME")
    private String consignPerson2PositionName;

            /**
            * 委托登记时间
            */
    @TableField("CONSIGNMENT_REG_DATETIME")
    private transient LocalDateTime consignmentRegDatetime;

            /**
            * 鉴定要求
            */
        @TableField("IDENTIFY_REQUIREMENT")
    private String identifyRequirement;

    @TableField("ACCEPT_NO")
    protected String acceptNo;

            /**
            * 受理人ID
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
    private transient LocalDateTime acceptDatetime;

            /**
            * 补送标记，0-False，1-True
            */
        @TableField("APPEND_FLAG")
    private Integer appendFlag;

            /**
            * 补送顺序号
            */
        @TableField("APPEND_SNO")
    private Integer appendSno;

        @TableField("TRANSFER_FLAG")
    private Integer transferFlag;

    @TableField("TRANSFER_DATETIME")
    private transient LocalDateTime transferDatetime;

        @TableField("TRANSFER_PERSON_ID")
    private String transferPersonId;

        @TableField("TRANSFER_PERSON_NAME")
    private String transferPersonName;

        @TableField("DELETE_FLAG")
    private Integer deleteFlag;

    @TableField("DELETE_DATETIME")
    private transient LocalDateTime deleteDatetime;

        @TableField("DELETE_PERSON_ID")
    private String deletePersonId;

        @TableField("DELETE_PERSON_NAME")
    private String deletePersonName;

            /**
            * 创建时间
            */
    @TableField("STORE_DATETIME")
    private transient LocalDateTime storeDatetime;

            /**
            * 创建人id
            */
        @TableField("STORE_PERSON_ID")
    private String storePersonId;

            /**
            * 创建人姓名
            */
        @TableField("STORE_PERSON_NAME")
    private String storePersonName;

            /**
            * 更新时间
            */
    @TableField("UPDATE_DATETIME")
    private transient LocalDateTime updateDatetime;

            /**
            * 更新人id
            */
        @TableField("UPDATE_PERSON_ID")
    private String updatePersonId;

            /**
            * 更新人姓名
            */
        @TableField("UPDATE_PERSON_NAME")
    private String updatePersonName;

            /**
            * 其他备注信息
            */
        @TableField("MORE_REMARKS")
    private String moreRemarks;
        
        /**
         * 案件类型：失踪人员 身份不明
         */
        @TableField("CASE_TYPE")
        private String caseType;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
