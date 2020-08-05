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
    * 试剂盒信息表
    * </p>
*
* @author lizhihua
* @since 2020-02-11
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DnaPanelInfo extends Model<DnaPanelInfo> implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

            /**
            * 1-STR, 2-YSTR
            */
    @TableField("PANEL_TYPE")
    private Integer panelType;

            /**
            * 试剂盒代码
            */
    @TableField("PANEL_CODE")
    private String panelCode;

            /**
            * 试剂盒名称
            */
    @TableField("PANEL_NAME")
    private String panelName;

            /**
            * 试剂盒全名
            */
    @TableField("PANEL_FULL_NAME")
    private String panelFullName;

            /**
            * 别名
            */
    @TableField("ALIAS_NAME")
    private String aliasName;

            /**
            * 生产商
            */
    @TableField("PRODUCER_NAME")
    private String producerName;

            /**
            * 版本
            */
    @TableField("PANEL_VERSION_NO")
    private String panelVersionNo;

            /**
            * 存储试剂盒对应panel文件路径
            */
    @TableField("PANEL_FILE_PATH")
    private String panelFilePath;

    /**
    * 存储试剂盒对应bins文件路径
    */
    @TableField("BINS_FILE_PATH")
    private String binsFilePath;

    /**
    * 创建时间
    */
    @TableField("CREATE_DATETIME")
    private transient LocalDateTime createDatetime;

    /**
    * 创建人姓名
    */
    @TableField("CREATE_PERSON")
    private String createPerson;

    /**
    * 创建人单位名称
    */
    @TableField("CREATE_PERSON_ORG_NAME")
    private String createPersonOrgName;

    /**
    * 更新时间
    */
    @TableField("UPDATE_DATETIME")
    private transient LocalDateTime updateDatetime;

    /**
    * 更新人姓名
    */
    @TableField("UPDATE_PERSON")
    private String updatePerson;

    /**
    * 更新人单位名称
    */
    @TableField("UPDATE_PERSON_ORG_NAME")
    private String updatePersonOrgName;

    //试剂盒描述
    @TableField("PANEL_DESCRIPTION")
    private String panelDescription;

    @TableField(exist = false)
    private List<DnaLocusInfo> dnaLocusInfoList;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
