package com.bazl.dna.database.service.model.po;


import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 入库数据基因位点数设置
 * </p>
 *
 * @author lizhihua
 * @since 2020-05-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class InstoreDataCondition extends Model<InstoreDataCondition> implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    @TableField("INSTORE_DATA_TYPE")
    private String instoreDataType;

    @TableField("STR_LOCUS_COUNT")
    private Integer strLocusCount;

    @TableField("YSTR_LOCUS_COUNT")
    private Integer ystrLocusCount;

    @TableField("INSTORE_DATA_TYPE_NAME")
    private String instoreDataTypeName;

}
