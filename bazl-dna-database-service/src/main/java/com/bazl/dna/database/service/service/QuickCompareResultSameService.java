package com.bazl.dna.database.service.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bazl.dna.database.service.model.po.QuickCompareResultSame;
import com.bazl.dna.database.service.model.qo.QuickCompareResultSameQuery;
import com.bazl.dna.database.service.model.vo.QuickCompareResultSameVo;

/**
 * <p>
 * 同型快速比对结果表 服务类
 * </p>
 *
 * @author lizhihua
 * @since 2020-05-14
 */
public interface QuickCompareResultSameService extends IService<QuickCompareResultSame> {

    //通过比对id查询比对结果
	public List<QuickCompareResultSameVo> selectByQuickSameQueueId(Integer quickCompareQueueId);
    //条数
    public int selectBySameQueueIdCount(Integer quickCompareQueueId);
    
	public Map<String, Object> findList(QuickCompareResultSameQuery query);
    
}
