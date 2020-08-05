package com.bazl.dna.database.service.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* <p>
    * 质控污染记录
    * </p>
*
* @author lizhihua
* @since 2020-02-11
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class QcSamplePolluteRecord extends Model<QcSamplePolluteRecord> implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    @TableField("QC_SAMPLE_ID")
    private Integer qcSampleId;

    /**
    * 质控样本基因信息（存储该字段用于避免质控样本信息被修改的情况，无法追溯比中信息）
    */
    @TableField("QC_SAMPLE_GENE_INFO")
    private String qcSampleGeneInfo;

    /**
     * 比中样本所属板号
     */
    @TableField("MATCH_BOARD_NO")
    private String matchBoardNo;

    /**
    * 比中样本编号
    */
    @TableField("MATCH_SAMPLE_NO")
    private String matchSampleNo;

    /**
    * 比中样本名称
    */
    @TableField("MATCH_SAMPLE_NAME")
    private String matchSampleName;

    /**
    * 比中样本基因型
    */
    @TableField("MATCH_SAMPLE_GENE_INFO")
    private String matchSampleGeneInfo;

    /**
    * 比中实验室编号
    */
    @TableField("MATCH_LAB_SERVER_NO")
    private String matchLabServerNo;

    /**
    * 匹配位点个数
    */
    @TableField("MATCH_LOCUS_COUNT")
    private Integer matchLocusCount;

    /**
    * 比中时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("MATCH_DATETIME")
    private transient LocalDateTime matchDatetime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
