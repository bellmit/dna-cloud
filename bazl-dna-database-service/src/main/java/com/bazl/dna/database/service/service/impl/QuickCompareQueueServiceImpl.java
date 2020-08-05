package com.bazl.dna.database.service.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bazl.dna.database.service.mapper.QuickCompareQueueMapper;
import com.bazl.dna.database.service.model.po.QuickCompareQueue;
import com.bazl.dna.database.service.service.QuickCompareQueueService;
import com.google.common.collect.Lists;

/**
 * <p>
 * 快速比对对队列表 服务实现类
 * </p>
 *
 * @author lizhihua
 * @since 2020-05-14
 */
@Service
public class QuickCompareQueueServiceImpl extends ServiceImpl<QuickCompareQueueMapper, QuickCompareQueue> implements QuickCompareQueueService {
	
	private static final Logger LOG = LoggerFactory.getLogger(QuickCompareQueueServiceImpl.class);

    @Autowired
    private QuickCompareQueueMapper quickCompareQueueMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return quickCompareQueueMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(QuickCompareQueue record) {
        return quickCompareQueueMapper.insert(record);
    }

    @Override
    public int insertSelective(QuickCompareQueue record) {
        return quickCompareQueueMapper.insertSelective(record);
    }

    @Override
    public QuickCompareQueue selectByPrimaryKey(Integer id) {
        return quickCompareQueueMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(QuickCompareQueue record) {
        return quickCompareQueueMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(QuickCompareQueue record) {
        return quickCompareQueueMapper.updateByPrimaryKey(record);
    }

	@Override
	public List<QuickCompareQueue> findListByCompareMode(int compareMode, String compareStatus, int pageSize) {
		try {
			return quickCompareQueueMapper.findListByCompareMode(compareMode, compareStatus, pageSize);
		} catch (Exception e) {
			LOG.error("Error findListByCompareMode:" + e);
		}
		return Lists.newArrayList();
	}
}
