package com.bazl.dna.database.service.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bazl.dna.database.service.mapper.AutoCompareQueueMapper;
import com.bazl.dna.database.service.model.po.AutoCompareQueue;
import com.bazl.dna.database.service.service.AutoCompareQueueService;
import com.google.common.collect.Lists;

/**
 * <p>
 * 自动比对队列表 服务实现类
 * </p>
 *
 * @author lizhihua
 * @since 2020-05-13
 */
@Service
public class AutoCompareQueueServiceImpl extends ServiceImpl<AutoCompareQueueMapper, AutoCompareQueue> implements AutoCompareQueueService {

	private static final Logger LOG = LoggerFactory.getLogger(AutoCompareQueueServiceImpl.class);
	
	@Autowired
	private AutoCompareQueueMapper mapper; 
	
	@Override
	public List<AutoCompareQueue> findListByCompareMode(int compareMode, String compareStatus, int pageSize) {
		try {
			return mapper.findListByCompareMode(compareMode, compareStatus, pageSize);
		} catch (Exception e) {
			LOG.error("Error findListByCompareMode:" + e);
		}
		return Lists.newArrayList();
	}

}
