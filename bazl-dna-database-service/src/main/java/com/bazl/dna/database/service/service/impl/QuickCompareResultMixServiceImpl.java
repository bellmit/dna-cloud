package com.bazl.dna.database.service.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bazl.dna.database.service.mapper.QuickCompareResultMixMapper;
import com.bazl.dna.database.service.model.po.QuickCompareResultMix;
import com.bazl.dna.database.service.model.vo.QuickCompareResultMixVo;
import com.bazl.dna.database.service.service.QuickCompareResultMixService;
import com.google.common.collect.Lists;

/**
 * <p>
 * 混合快速比对结果表 服务实现类
 * </p>
 *
 * @author lizhihua
 * @since 2020-05-14
 */
@Service
public class QuickCompareResultMixServiceImpl extends ServiceImpl<QuickCompareResultMixMapper, QuickCompareResultMix> implements QuickCompareResultMixService {
	
    @Autowired
    private QuickCompareResultMixMapper quickCompareResultMixMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return quickCompareResultMixMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(QuickCompareResultMix record) {
        return quickCompareResultMixMapper.insert(record);
    }

    @Override
    public int insertSelective(QuickCompareResultMix record) {
        return quickCompareResultMixMapper.insertSelective(record);
    }

    @Override
    public QuickCompareResultMix selectByPrimaryKey(Integer id) {
        return quickCompareResultMixMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(QuickCompareResultMix record) {
        return quickCompareResultMixMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(QuickCompareResultMix record) {
        return quickCompareResultMixMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<QuickCompareResultMixVo> selectByQuickCompareQueueId(Integer quickCompareQueueId) {
        try {
            return quickCompareResultMixMapper.selectByQuickCompareQueueId(quickCompareQueueId);
        } catch (Exception e) {
        	log.error("selectByQuickCompareQueueId error！" + e.getMessage());
        }
        return Lists.newArrayList();
    }

    @Override
    public int selectByQueueIdCount(Integer quickCompareQueueId) {
        return quickCompareResultMixMapper.selectByQueueIdCount(quickCompareQueueId);
    }
}
