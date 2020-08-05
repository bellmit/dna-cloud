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
 * 快速比对对队列表
 * </p>
 *
 * @author lizhihua
 * @since 2020-05-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class QuickCompareQueue extends Model<QuickCompareQueue> implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 比对权重
     */
    @TableField("COMPARE_WEIGHT")
    private Integer compareWeight;

    /**
     * 比对模式，字典：COMPARE_MODE
     */
    @TableField("COMPARE_MODE")
    private Integer compareMode;

    /*
    *   目标亲身份  字典：TARGET_RELATION
    * */
    @TableField("TARGET_RELATION")
    private String targetReation;

    /**
     * 待比对检材1编号
     */
    @TableField("PENDING_SAMPLEA_NO")
    private String pendingSampleaNo;

    /**
     * 待比对检材1名称
     */
    @TableField("PENDING_SAMPLEA_NAME")
    private String pendingSampleaName;

    /**
     * 待比对检材1亲缘身份
     */
    @TableField("PENDING_SAMPLEA_RELATION")
    private String pendingSampleaRelation;

    /**
     * 待比对检材1基因信息
     */
    @TableField("PENDING_SAMPLEA_GENE_INFO")
    private String pendingSampleaGeneInfo;

    /**
     * 待比对检材2编号
     */
    @TableField("PENDING_SAMPLEB_NO")
    private String pendingSamplebNo;

    /**
     * 待比对检材2名称
     */
    @TableField("PENDING_SAMPLEB_NAME")
    private String pendingSamplebName;

    /**
     * 待比对检材2亲缘身份
     */
    @TableField("PENDING_SAMPLEB_RELATION")
    private String pendingSamplebRelation;

    /**
     * 待比对检材2基因信息
     */
    @TableField("PENDING_SAMPLEB_GENE_INFO")
    private String pendingSamplebGeneInfo;

    /**
     * 试剂盒1id
     */
    @TableField("PANELA_ID")
    private Integer panelaId;

    /**
     * 试剂盒1名称
     */
    @TableField("PANELA_NAME")
    private String panelaName;


    /**
     * 试剂盒2id
     */
    @TableField("PANELB_ID")
    private Integer panelbId;

    /**
     * 试剂盒2名称
     */
    @TableField("PANELB_NAME")
    private String panelbName;
    
    @TableField("TARGET_RELATION")
    private String targetRelation;

    /**
     * 比对参数
     */
    @TableField("COMPARE_PARAMS")
    private String compareParams;

    /**
     * 种群频率方案id
     */
    @TableField("POPULATION_FREQUENCY_ID")
    private Integer populationFrequencyId;

    /**
     * 比对状态
     */
    @TableField("QUEUE_STATUS")
    private String queueStatus;

    /**
     * 创建时间
     */
    @TableField("CREATE_DATETIME")
    private transient LocalDateTime createDatetime;

    /**
     * 创建人id
     */
    @TableField("CREATE_PERSON_ID")
    private String createPersonId;

    /**
     * 创建人姓名
     */
    @TableField("CREATE_PERSON_NAME")
    private String createPersonName;

    @TableField("UPDATE_DATETIME")
    private transient LocalDateTime updateDatetime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
