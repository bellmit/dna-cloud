package com.bazl.dna.database.service.model.po;

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
 * 入库数据类型表
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class InstoreDataType extends Model<InstoreDataType> implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    @TableField("TYPE_CODE")
    private String typeCode;

    @TableField("TYPE_NAME")
    private String typeName;

    /**
     * 对应国家库入库类型代码
     */
    @TableField("NATION_TYPE_CODE")
    private String nationTypeCode;

    /**
     * 对应国家库入库类型名称
     */
    @TableField("NATION_TYPE_NAME")
    private String nationTypeName;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
