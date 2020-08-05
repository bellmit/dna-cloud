package com.bazl.dna.database.service.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 上报案件样本表
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TransferCaseSample extends Model<TransferCaseSample> implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 队列id
     */
    @TableField("TRANSFER_CASE_QUEUE_ID")
    private Integer transferCaseQueueId;

    /**
     * dna样本id
     */
    @TableField("DNA_SAMPLE_ID")
    private Integer dnaSampleId;

    /**
     * 上报状态，0待上报;1上报成功;2上报失败
     */
    @TableField("TRANSFER_STATS")
    private Integer transferStats;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
