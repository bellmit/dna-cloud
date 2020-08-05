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
 * ystr样本比中结果信息表
 * </p>
 *
 * @author lizhihua
 * @since 2020-03-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MatchResultYstr extends Model<MatchResultYstr> implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 比中分组id
     */
    @TableField("MATCH_GROUP_ID")
    private Integer matchGroupId;

    /**
     * 比中时间
     */
    @TableField("MATCH_DATETIME")
    private transient LocalDateTime matchDatetime;

    /**
     * 匹配位点个数
     */
    @TableField("MATCH_LOCUS_COUNT")
    private Integer matchLocusCount;
    
    /**
     * 不匹配位点个数
     */
    @TableField("DIFF_LOCUS_COUNT")
    private Integer diffLocusCount;

    /**
     * 样本A主键id， A表示先入库的样本
     */
    @TableField("SAMPLEA_ID")
    private Integer sampleaId;

    /**
     * 样本B主键id， B表示后入库的样本
     */
    @TableField("SAMPLEB_ID")
    private Integer samplebId;

    /**
     * 比中基因详情
     */
    @TableField("MATCHED_GENE_DETAILS")
    private String matchedGeneDetails;

    /**
     * 复核标记，0-False，1-True
     */
    @TableField("REVIEW_FLAG")
    private Integer reviewFlag;

    /**
     * 复核时间
     */
    @TableField("REVIEW_DATETIME")
    private transient LocalDateTime reviewDatetime;

    /**
     * 复核人id
     */
    @TableField("REVIEW_PERSON_ID")
    private String reviewPersonId;

    /**
     * 复核人姓名
     */
    @TableField("REVIEW_PERSON_NAME")
    private String reviewPersonName;

    /**
     * 复核结果代码，1-确认比中；2-解除关联
     */
    @TableField("REVIEW_RESULT_CODE")
    private String reviewResultCode;

    /**
     * 复核结果说明
     */
    @TableField("REVIEW_RESULT_DESC")
    private String reviewResultDesc;

    @TableField("CREATE_DATETIME")
    private transient LocalDateTime createDatetime;

    @TableField("UPDATE_DATETIME")
    private transient LocalDateTime updateDatetime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
