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
 * 亲缘快速比对结果表（含亲缘三联体、亲缘单亲比对）
 * </p>
 *
 * @author lizhihua
 * @since 2020-05-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class QuickCompareResultRelative extends Model<QuickCompareResultRelative> implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 快速比对队列表
     */
    @TableField("QUICK_COMPARE_QUEUE_ID")
    private Integer quickCompareQueueId;

    /**
     * 比中亲缘样本1基因id
     */
    @TableField("MATCH_GENE_ID1")
    private Integer matchGeneId1;

    /**
     * 比中亲缘样本2基因id
     */
    @TableField("MATCH_GENE_ID2")
    private Integer matchGeneId2;

    /**
     * 比中位点个数
     */
    @TableField("MATCH_LOCUS_COUNT")
    private Integer matchLocusCount;

    /**
     * 差异位点个数
     */
    @TableField("DIFF_LOCUS_COUNT")
    private Integer diffLocusCount;

    /**
     * 匹配基因详情
     */
    @TableField("MATCHED_GENE_DETAILS")
    private String matchedGeneDetails;

    @TableField("MATCH_DATETIME")
    private transient LocalDateTime matchDatetime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
