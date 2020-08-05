package com.bazl.dna.database.service.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bazl.dna.common.PageInfo;
import com.bazl.dna.common.PublicConstants;
import com.bazl.dna.database.service.mapper.QuickCompareResultSameMapper;
import com.bazl.dna.database.service.model.po.QuickCompareResultSame;
import com.bazl.dna.database.service.model.qo.QuickCompareResultSameQuery;
import com.bazl.dna.database.service.model.vo.QuickCompareResultSameVo;
import com.bazl.dna.database.service.service.QuickCompareResultSameService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * <p>
 * 同型快速比对结果表 服务实现类
 * </p>
 *
 * @author lizhihua
 * @since 2020-05-14
 */
@Service
public class QuickCompareResultSameServiceImpl extends ServiceImpl<QuickCompareResultSameMapper, QuickCompareResultSame> implements QuickCompareResultSameService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(QuickCompareResultSameServiceImpl.class);
	
    @Autowired
    private QuickCompareResultSameMapper mapper;

    @Override
    public List<QuickCompareResultSameVo> selectByQuickSameQueueId(Integer quickCompareQueueId) {
        try {
            return mapper.selectByQuickSameQueueId(quickCompareQueueId);
        } catch (Exception e) {
        	LOGGER.error("selectByQuickSameQueueId error:", e);
        }
        return Lists.newArrayList();
    }

    @Override
    public int selectBySameQueueIdCount(Integer quickCompareQueueId) {
        try {
            return mapper.selectBySameQueueIdCount(quickCompareQueueId);
        } catch (Exception e) {
        	LOGGER.error("selectBySameQueueIdCount error:", e);
        }
        return 0;
    }

	@Override
	public Map<String, Object> findList(QuickCompareResultSameQuery data) {
		int totalCount = mapper.countList(data);
		List<QuickCompareResultSameQuery> list = mapper.findList(data, data.getOffset(), data.getRows());
		
		Map<String, Object> result = Maps.newHashMap();
		result.put(PublicConstants.PARAM_PAGE, new PageInfo(data.getPageIndex(), data.getRows(), totalCount));
		result.put(PublicConstants.PARAM_LIST, list);
		return result;
	}
}
