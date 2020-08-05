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
 * str基因信息表
 * </p>
 *
 * @author lizhihua
 * @since 2020-05-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DnaStrGeneInfo extends Model<DnaStrGeneInfo> implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 实验室服务器编号
     */
    @TableField("LAB_SERVER_NO")
    private String labServerNo;


    @TableField("LIMS_STR_UUID")
    private String limsStrUuid;
//
//    /**
//     * 基因类型，字典：GENE_TYPE
//     */
//    @TableField("GENE_TYPE")
//    private Integer geneType;

    /**
     * 使用试剂盒ID
     */
    @TableField("DNA_PANEL_ID")
    private Integer dnaPanelId;

    /**
     * 使用试剂盒名称
     */
	@TableField("DNA_PANEL_NAME")
    private String kitname;

    /**
     * 1-案件； 2-建库人员
     */
    @TableField("DATA_SOURCE")
    private Integer dataSource;

    /**
     * 样本id
     */
    @TableField("SAMPLE_ID")
    private Integer sampleId;

    /**
     * 检出位点个数
     */
    @TableField("LOCUS_COUNT")
    private Integer locusCount;

    /**
     * 基因信息
     */
    @TableField("GENE_INFO")
    private String geneInfo;

    /**
     * 基因图谱路径
     */
    @TableField("GENE_IMAGE")
    private String geneImage;

    /**
     * 上报标识
     */
    @TableField("TRANSFER_FLAG")
    private Integer transferFlag;

    /**
     * 上报时间
     */
    @TableField("TRANSFER_DATETIME")
    private transient LocalDateTime transferDatetime;

    /**
     * 上报操作人id
     */
    @TableField("TRANSFER_PERSON_ID")
    private String transferPersonId;

    /**
     * 上报操作人姓名
     */
    @TableField("TRANSFER_PERSON_NAME")
    private String transferPersonName;

    /**
     * 删除标识
     */
    @TableField("DELETE_FLAG")
    private Integer deleteFlag;

    @TableField("DELETE_DATETIME")
    private transient LocalDateTime deleteDatetime;

    @TableField("DELETE_REASON")
    private String deleteReason;

    @TableField("DELETE_PERSON_ID")
    private String deletePersonId;

    @TableField("DELETE_PERSON_NAME")
    private String deletePersonName;

    /**
     * 入库时间
     */
    @TableField("STORE_DATETIME")
    private transient LocalDateTime storeDatetime;

    /**
     * 入库操作人id
     */
    @TableField("STORE_PERSON_ID")
    private String storePersonId;

    /**
     * 入库操作人姓名
     */
    @TableField("STORE_PERSON_NAME")
    private String storePersonName;

    @TableField("UPDATE_DATETIME")
    private transient LocalDateTime updateDatetime;

    @TableField("UPDATE_PERSON_ID")
    private String updatePersonId;

    @TableField("UPDATE_PERSON_NAME")
    private String updatePersonName;

//    private String limsSampleUuid;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
