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
 * dna混合比对结果分组信息表
 * </p>
 *
 * @author lizhihua
 * @since 2020-03-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MatchResultMixGroup extends Model<MatchResultMixGroup> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键id
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 分组编号
     */
    @TableField("GROUP_NO")
    private Integer groupNo;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
