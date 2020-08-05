package com.bazl.dna.database.service.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bazl.dna.database.service.mapper.QuickCompareResultRelativeMapper;
import com.bazl.dna.database.service.model.po.QuickCompareResultRelative;
import com.bazl.dna.database.service.model.vo.QuickCompareResultRelativeVo;
import com.bazl.dna.database.service.service.QuickCompareResultRelativeService;
import com.google.common.collect.Lists;

/**
 * <p>
 * 亲缘快速比对结果表（含亲缘三联体、亲缘单亲比对） 服务实现类
 * </p>
 *
 * @author lizhihua
 * @since 2020-05-14
 */
@Service
public class QuickCompareResultRelativeServiceImpl extends ServiceImpl<QuickCompareResultRelativeMapper, QuickCompareResultRelative> implements QuickCompareResultRelativeService {
	
    @Autowired
    private QuickCompareResultRelativeMapper quickCompareResultRelativeMapper;

    @Override
    public List<QuickCompareResultRelativeVo> selectByQuickCompareQueueId(Integer quickCompareQueueId) {
        try {
            return quickCompareResultRelativeMapper.selectByQuickCompareQueueId(quickCompareQueueId);
        } catch (Exception e) {
            log.error("根据队列id查询比中亲缘结果错误！" + e.getMessage());
        }
        return Lists.newArrayList();
    }

    @Override
    public int selectByQueueIdCount(Integer quickCompareQueueId) {
        try {
            return quickCompareResultRelativeMapper.selectBySTRQueueIdCount(quickCompareQueueId);
        } catch (Exception e) {
            log.error("根据队列id查询比中亲缘总数错误！" + e.getMessage());
        }
        return 0;
    }
}
