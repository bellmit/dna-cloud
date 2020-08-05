package com.bazl.dna.database.service.model.po;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 数据比对设置表
 * </p>
 *
 * @author lizhihua
 * @since 2020-03-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CompareSettings extends Model<CompareSettings> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键id
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 比对模式
     */
    @TableField("COMPARE_MODE")
    private String compareMode;

    /**
     * 入库数据类型
     */
    @TableField("INSTORE_DATA_TYPE")
    private String instoreDataType;

    /**
     * 是否加入自动比对，0-False， 1-True
     */
    @TableField("AUTO_COMPARE_FLAG")
    private Integer autoCompareFlag;

    /**
     * 匹配下限（最少匹配位点个数）
     */
    @TableField("LOWEST_SAME_LIMIT")
    private Integer lowestSameLimit;

    /**
     * 容差上限（最多差异位点个数）
     */
    @TableField("MOST_DIFF_LIMIT")
    private Integer mostDiffLimit;

    /**
     * 比对目标数据类型，格式如：{[code:"01",checked:true],[code:"02",checked:false]}
     */
    @TableField("TARGET_DATA_TYPE")
    private JSONArray targetDataType;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
