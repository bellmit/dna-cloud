package com.bazl.dna.database.service.service;

import com.bazl.dna.database.service.model.po.GeneSyncQueue;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 基因同步队列 服务类
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-17
 */
public interface GeneSyncQueueService extends IService<GeneSyncQueue> {

    List<GeneSyncQueue> queryGeneInfoList(GeneSyncQueue geneSyncQueue);
}
