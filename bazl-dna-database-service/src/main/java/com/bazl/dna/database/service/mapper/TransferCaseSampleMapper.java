package com.bazl.dna.database.service.mapper;

import com.bazl.dna.database.service.model.po.TransferCaseSample;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 上报案件样本表 Mapper 接口
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-17
 */
public interface TransferCaseSampleMapper extends BaseMapper<TransferCaseSample> {
    /**
     * 根据案件ID查询提交入库检材数
      * @param transferCaseQueueId
     * @return
     */
    List<TransferCaseSample> selectByCaseQueueId(int transferCaseQueueId);
}
