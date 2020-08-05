package com.bazl.dna.database.service.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bazl.dna.common.PageInfo;
import com.bazl.dna.common.PublicConstants;
import com.bazl.dna.database.service.mapper.QuickCompareResultYstrMapper;
import com.bazl.dna.database.service.model.po.QuickCompareResultYstr;
import com.bazl.dna.database.service.model.qo.QuickCompareResultYstrQuery;
import com.bazl.dna.database.service.model.vo.QuickCompareResultYstrVo;
import com.bazl.dna.database.service.service.QuickCompareResultYstrService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * <p>
 * ystr快速比对结果表 服务实现类
 * </p>
 *
 * @author lizhihua
 * @since 2020-05-14
 */
@Service
public class QuickCompareResultYstrServiceImpl extends ServiceImpl<QuickCompareResultYstrMapper, QuickCompareResultYstr>
		implements QuickCompareResultYstrService {

	@Autowired
	private QuickCompareResultYstrMapper mapper;

	@Override
	public List<QuickCompareResultYstrVo> selectByQuickCompareQueueId(Integer quickCompareQueueId) {
		try {
			return mapper.selectByQuickCompareQueueId(quickCompareQueueId);
		} catch (Exception ex) {
			log.error("invoke QuickCompareResultYstrService.selectByQuickCompareQueueId error", ex);
		}
		return Lists.newArrayList();
	}

	@Override
	public int selectByQueueIdCount(Integer quickCompareQueueId) {
		try {
			return mapper.selectByQueueIdCount(quickCompareQueueId);
		} catch (Exception ex) {
			log.error("invoke QuickCompareResultYstrService.selectByQueueIdCount error", ex);
		}
		return 0;
	}
	
	@Override
	public Map<String, Object> findList(QuickCompareResultYstrQuery data) {
		int totalCount = mapper.countList(data);
		List<QuickCompareResultYstrQuery> list = mapper.findList(data, data.getOffset(), data.getRows());
		
		Map<String, Object> result = Maps.newHashMap();
		result.put(PublicConstants.PARAM_PAGE, new PageInfo(data.getPageIndex(), data.getRows(), totalCount));
		result.put(PublicConstants.PARAM_LIST, list);
		return result;
	}
}
