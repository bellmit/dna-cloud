package com.bazl.dna.database.service.model.po;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* <p>
    * dna基因信息表
    * </p>
*
* @author lizhihua
* @since 2020-02-11
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DnaGeneInfo extends Model<DnaGeneInfo> implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * LAB_SERVER_NO
     */
    @TableField("LAB_SERVER_NO")
    private String labServerNo;

    /**
    * 基因类型，字典：GENE_TYPE
    */
    @TableField("GENE_TYPE")
    private Integer geneType;

    /**
    * 使用试剂盒id
    */
    @TableField("DNA_PANEL_ID")
    private Integer dnaPanelId;


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
    private List<DnaGeneInfoDetail> geneInfoDetail;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("DELETE_DATETIME")
    private transient LocalDateTime deleteDatetime;

    @TableField("DELETE_REASON")
    private String deleteReason;

    /**
     * 删除操作人id
     */
    @TableField("DELETE_PERSON_ID")
    private String deletePersonId;

    /**
     * 删除操作人姓名
     */
    @TableField("DELETE_PERSON_NAME")
    private String deletePersonName;

    /**
    * 入库时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("UPDATE_DATETIME")
    private transient LocalDateTime updateDatetime;

    @TableField("UPDATE_PERSON_ID")
    private String updatePersonId;

    @TableField("UPDATE_PERSON_NAME")
    private String updatePersonName;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
