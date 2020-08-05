package com.bazl.dna.database.service.mapper;

import com.bazl.dna.database.service.model.po.GeneSyncQueue;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 基因同步队列 Mapper 接口
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-17
 */
public interface GeneSyncQueueMapper extends BaseMapper<GeneSyncQueue> {

    List<GeneSyncQueue> queryGeneInfoList(GeneSyncQueue geneSyncQueue);
}
