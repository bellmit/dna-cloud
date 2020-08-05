package com.bazl.dna.database.service.service;

import com.bazl.dna.database.service.model.po.TransferCaseSample;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 上报案件样本表 服务类
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-17
 */
public interface TransferCaseSampleService extends IService<TransferCaseSample> {
    /*
     * 根据案件ID查询提交入库检材数
     * @param transferCaseQueueId
     * @return
     */
    List<TransferCaseSample> selectByCaseQueueId(int transferCaseQueueId);
}
