package com.bazl.dna.database.service.service;

import com.bazl.dna.database.service.model.po.AutoCompareQueue;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 自动比对队列表 服务类
 * </p>
 *
 * @author lizhihua
 * @since 2020-05-13
 */
public interface AutoCompareQueueService extends IService<AutoCompareQueue> {

	public List<AutoCompareQueue> findListByCompareMode(int compareMode, String compareStatus, int pageSize);
}
