package com.bazl.dna.database.service.model.po;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* <p>
    * 建库人员信息表（违法犯罪/前科人员）
    * </p>
*
* @author lizhihua
* @since 2020-02-11
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CriminalPersonInfo extends Model<CriminalPersonInfo> implements Serializable {

    protected static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    protected Integer id;


    /**
     * lims入库id
     */
    @TableField("LIMS_PERSON_UUID")
    protected String limsPersonUuid;


    @TableField("CONSIGNMENT_ID")
    protected Integer consignmentId;

    /**
     * 服务器编号
     */
    @TableField("LAB_SERVER_NO")
    protected String labServerNo;


    /**
    * 建库人员类型，字典：CRIMINAL_PERSON_TYPE
    */
    @TableField("CRIMINAL_PERSON_TYPE")
    protected String criminalPersonType;


    /**
     * 姓名
     */
    @TableField("PERSON_NAME")
    private String personName;

    /**
     * 性别，字典（男、女）
     */
    @TableField("PERSON_GENDER")
    private String personGender;

    /**
     * 身份证号
     */
    @TableField("PERSON_IDCARD_NO")
    private String personIdcardNo;

    /**
     * 民族
     */
    @TableField("PERSON_RACE")
    private String personRace;

    /**
     * 国籍
     */
    @TableField("PERSON_NATION")
    private String personNation;

    /**
     * 籍贯地代码
     */
    @TableField("NATIVE_PLACE_CODE")
    private String nativePlaceCode;

    /**
     * 籍贯地名称
     */
    @TableField("NATIVE_PLACE_NAME")
    private String nativePlaceName;

    /**
     * 现住址详细
     */
    @TableField("PRESENT_ADDRESS")
    private String presentAddress;

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
     * 外貌特征
     */
    @TableField("PERSON_APPEARANCE")
    private String personAppearance;


    /**
     * 备注
     */
    @TableField("REMARKS")
    private String remarks;
    
    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
