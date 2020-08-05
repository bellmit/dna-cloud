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
    * 试剂盒信息表
    * </p>
*
* @author lizhihua
* @since 2020-02-11
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DnaPanelInfoCop extends Model<DnaPanelInfoCop> implements Serializable {

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


    //试剂盒描述
    @TableField("PANEL_DESCRIPTION")
    private String panelDescription;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
