package com.bazl.dna.database.service.model.qo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.bazl.dna.database.service.model.po.AlleleFrequencyInfo;
import com.bazl.dna.util.JsonDatetimeSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 查看全部种群设置 qo
 * Created by Liuchang on 2020/5/26.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class  PopulationFrequencyQuery extends AbstractQuery implements Serializable {


    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 是否为默认使用的种群频率
     */
    @TableField("DEFAULT_FLAG")
    private Integer defaultFlag;

    /**
     * 种群名称
     */
    @TableField("POPULATION_NAME")
    private String populationName;

    /**
     * * 1-STR, 2-YSTR
     */
    @TableField("GENE_TYPE")
    private String geneType;

    /**
     * 基因座数量
     * */
    @TableField("LOCUS_COUNT")
    private Integer locusCount;

    /**
     * 数据来源
     * */
    @TableField("DATA_SOURCE")
    private String dataSource;

    /**
     * 统计时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    @TableField("STATS_DATETIME")
    private transient LocalDateTime statsDatetime;

    /**
     * 统计国家
     */
    @TableField("STATS_COUNTRY")
    private String statsCountry;

    /**
     * 统计名族
     */
    @TableField("STATS_RACE")
    private String statsRace;

    /**
     * 统计地区
     */
    @TableField("STATS_AREA")
    private String statsArea;

    /**
     * 累积个人识别能力
     */
    @TableField("CDP_VAL")
    private Float cdpVal;

    /**
     * 累积非父排除率
     */
    @TableField("CPE")
    private Float cpe;

    /**
     * 描述（说明）
     */
    @TableField("POPULATION_DESC")
    private String populationDesc;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private transient LocalDateTime createDatetime;

    @TableField("CREATE_PERSON_ID")
    private String createPersonId;

    @TableField("CREATE_PERSON_NAME")
    private String createPersonName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    @TableField("UPDATE_DATETIME")
    private transient LocalDateTime updateDatetime;

    @TableField("UPDATE_PERSON_ID")
    private String updatePersonId;

    @TableField("UPDATE_PERSON_NAME")
    private String updatePersonName;

    private List<AlleleFrequencyInfo> alleleFrequencyInfoList;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
