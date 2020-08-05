package com.bazl.dna.database.service.model.po;

    import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
* <p>
    * 等位基因频率信息表
    * </p>
*
* @author lizhihua
* @since 2020-02-11
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AlleleFrequencyInfo extends Model<AlleleFrequencyInfo> implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
    * 种群id
    */
    @TableField("POPULATION_FREQUENCY_ID")
    private Integer populationFrequencyId;

    /**
    * 基因座id
    */
    @TableField("LOCUS_ID")
    private Integer locusId;

    /**
     * 基因座名称
     */
    @TableField("LOCUS_NAME")
    private String locusName;

    /**
    * 等位基因
    */
    @TableField("ALLELE_VALUE")
    private String alleleValue;

    /**
    * 取值概率
    */
    @TableField("PROBABILITY")
    private BigDecimal probability;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
