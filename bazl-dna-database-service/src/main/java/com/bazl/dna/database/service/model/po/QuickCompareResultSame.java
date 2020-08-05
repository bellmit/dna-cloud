package com.bazl.dna.database.service.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 同型快速比对结果表
 * </p>
 *
 * @author lizhihua
 * @since 2020-05-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class QuickCompareResultSame extends Model<QuickCompareResultSame> implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 快速比对队列id
     */
    @TableField("QUICK_COMPARE_QUEUE_ID")
    private Integer quickCompareQueueId;

    /**
     * 比中基因id
     */
    @TableField("MATCHED_GENE_ID")
    private Integer matchedGeneId;

    /**
     * 匹配位点个数
     */
    @TableField("MATCHED_LOCUS_COUNT")
    private Integer matchedLocusCount;

    /**
     * 差异位点个数
     */
    @TableField("DIFF_LOCUS_COUNT")
    private Integer diffLocusCount;

    /**
     * 比中基因详情
     */
    @TableField("MATCHED_GENE_DETAILS")
    private String matchedGeneDetails;

    /**
     * 比中时间
     */
    @TableField("MATCHED_DATETIME")
    private transient LocalDateTime matchedDatetime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
