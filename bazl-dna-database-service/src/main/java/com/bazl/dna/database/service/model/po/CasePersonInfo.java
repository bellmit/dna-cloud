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
    * 案件人员信息表
    * </p>
*
* @author lizhihua
* @since 2020-02-11
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CasePersonInfo extends Model<CasePersonInfo> implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;


    @TableField("LIMS_PERSON_UUID")
    private String limsPersonUuid;

    @TableField("LAB_SERVER_NO")
    private String labServerNo;

    @TableField("CASE_ID")
    private Integer caseId;

    @TableField("CONSIGNMENT_ID")
    private Integer consignmentId;

            /**
            * 人员类型，字典：CASE_PERSON_TYPE
            */
    @TableField("PERSON_TYPE")
    private String personType;

            /**
            * 姓名
            */
    @TableField("PERSON_NAME")
    private String personName;

            /**
            * 别名、绰号
            */
    @TableField("PERSON_ALIAS")
    private String personAlias;

            /**
            * 性别，字典：PERSON_GENDER
            */
    @TableField("PERSON_GENDER")
    private String personGender;

            /**
            * 身份证号码
            */
    @TableField("PERSON_IDCARD_NO")
    private String personIdcardNo;

            /**
            * 年龄
            */
    @TableField("PERSON_AGE")
    private String personAge;

            /**
            * 无身份证说明
            */
    @TableField("NONE_IDCARD_DESC")
    private String noneIdcardDesc;

            /**
            * 其他证件类型，字典：CENTIFICATE_TYPE
            */
    @TableField("OTHER_CENTIFICATE_TYPE")
    private String otherCentificateType;

            /**
            * 证件号码
            */
    @TableField("OTHER_CENTIFICATE_NO")
    private String otherCentificateNo;

            /**
            * 国籍，字典：NATIONALITY
            */
    private String nationality;

            /**
            * 民族，字典：RACE
            */
    @TableField("PERSON_RACE")
    private String personRace;

            /**
            * 住址
            */
    @TableField("PERSON_ADDR")
    private String personAddr;

            /**
            * 身高
            */
    @TableField("PERSON_HEIGHT")
    private String personHeight;

            /**
            * 体重
            */
    @TableField("PERSON_WEIGHT")
    private String personWeight;

            /**
            * 电话号码
            */
    @TableField("PHONE_NUMBER")
    private String phoneNumber;

    @TableField("TRANSFER_FLAG")
    private Integer transferFlag;

    @TableField("TRANSFER_DATETIME")
    private transient LocalDateTime transferDatetime;

    @TableField("TRANSFER_PERSON_ID")
    private String transferPersonId;

    @TableField("TRANSFER_PERESON_NAME")
    private String transferPeresonName;

    @TableField("DELETE_FLAG")
    private Integer deleteFlag;

    @TableField("DELETE_DATETIME")
    private transient LocalDateTime deleteDatetime;

    @TableField("DELETE_REASON")
    private String deleteReason;

    @TableField("DELETE_PERSON_ID")
    private String deletePersonId;

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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }



}
