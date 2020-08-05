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
    * DNA基因座信息表

    * </p>
*
* @author lizhihua
* @since 2020-02-11
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DnaLocusInfo extends Model<DnaLocusInfo> implements Serializable {

    private static final long serialVersionUID = 1L;

            @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

            /**
            * 1-STR; 2-YSTR
            */
        @TableField("LOCUS_TYPE")
    private String locusType;

            /**
            * 基因座名称
            */
        @TableField("LOCUS_NAME")
    private String locusName;

            /**
            * 对应国家库的基因座名称
            */
        @TableField("NATIONAL_LOCUS_NAME")
    private String nationalLocusName;

            /**
            * 别名（以逗号分隔）
            */
        @TableField("LOCUS_ALIAS")
    private String locusAlias;

            /**
            * 取值范围
            */
        @TableField("VALUE_SCOPE")
    private String valueScope;

            /**
            * 是否为核心基因座，0-False，1-True
            */
        @TableField("CORE_LOCUS_FLAG")
    private Integer coreLocusFlag;

            /**
            * 基因座顺序号
            */
        @TableField("LOCUS_ORD")
    private Integer locusOrd;

            /**
            * 创建时间
            */
    @TableField("CREATE_DATETIME")
    private transient LocalDateTime createDatetime;

    /**
    * 更新时间
    */
    @TableField("UPDATE_DATETIME")
    private transient LocalDateTime updateDatetime;

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
