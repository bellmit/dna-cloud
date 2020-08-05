package com.bazl.dna.database.service.service;

import com.bazl.dna.database.service.model.po.QuickCompareResultYstr;
import com.bazl.dna.database.service.model.qo.QuickCompareResultYstrQuery;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bazl.dna.database.service.model.vo.QuickCompareResultYstrVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * ystr快速比对结果表 服务类
 * </p>
 *
 * @author lizhihua
 * @since 2020-05-14
 */
public interface QuickCompareResultYstrService extends IService<QuickCompareResultYstr> {
	/**
	 * 根据序列ID，查询YSTR比中记录
	 * 
	 * @param quickCompareQueueId
	 * @return
	 */
	public List<QuickCompareResultYstrVo> selectByQuickCompareQueueId(Integer quickCompareQueueId);

	/**
	 * 根据序列ID查询条数
	 * 
	 * @param quickCompareQueueId
	 * @return
	 */
	public int selectByQueueIdCount(Integer quickCompareQueueId);

	public Map<String, Object> findList(QuickCompareResultYstrQuery query);
}
