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
 * 同型比对结果分组信息表
 * </p>
 *
 * @author lizhihua
 * @since 2020-03-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MatchResultSameGroup extends Model<MatchResultSameGroup> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键ID
     */
    @TableId(value = "ID", type = IdType.AUTO)
    private Integer id;

    /**
     * 分组编号
     */
    @TableField("GROUP_NO")
    private Integer groupNo;

    /**
     * 分组类型，1：人员-物证比中、2：物证-物证比中、3：人员比中
     */
    @TableField("GROUP_TYPE")
    private String groupType;

    /**
     * 最早比中时间
     */
    @TableField("FIRST_MATCH_TIME")
    private transient LocalDateTime firstMatchTime;

    /**
     * 最新比中时间
     */
    @TableField("LATEST_MATCH_TIME")
    private transient LocalDateTime latestMatchTime;

    /**
     * 分组样本个数
     */
    @TableField("GROUP_SAMPLE_COUNT")
    private Integer groupSampleCount;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
