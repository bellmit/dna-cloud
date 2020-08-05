package com.bazl.dna.database.service.model.qo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基因座查询类
 * Created by Liuchang on 2020/5/26.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LocusInfoQuery extends AbstractQuery implements Serializable {

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("CREATE_DATETIME")
    private transient LocalDateTime createDatetime;

    /**
     * 更新时间
     */
    @TableField("UPDATE_DATETIME")
    private String updateDatetime;

    /**
     * 备注
     */
    @TableField("REMARKS")
    private String remarks;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
