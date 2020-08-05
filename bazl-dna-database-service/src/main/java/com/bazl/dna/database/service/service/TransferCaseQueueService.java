package com.bazl.dna.database.service.service;

import com.bazl.dna.database.service.model.po.TransferCaseQueue;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 案件数据上报队列 服务类
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-17
 */
public interface TransferCaseQueueService extends IService<TransferCaseQueue> {

	public int saveTransferCaseQueue(TransferCaseQueue entity);
}
