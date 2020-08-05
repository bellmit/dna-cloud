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
 * 比对队列表
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CompareQueue extends Model<CompareQueue> implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 比对权重，数值越小，优先级越高
     */
    @TableField("COMPARE_WEIGHT")
    private Integer compareWeight;

    /**
     * 比对模式，字典：COMPARE_MODE
     */
    @TableField("COMPARE_MODE")
    private Integer compareMode;

    /**
     * 待比对geneId
     */
    @TableField("GENE_ID")
    private Integer geneId;

    /**
     * 比对条件
     */
    @TableField("COMPARE_PARAMS")
    private CompareParams compareParams;

    /**
     * 队列状态，字典：COMPARE_STATUS
     */
    @TableField("COMPARE_STATUS")
    private String compareStatus;

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


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
