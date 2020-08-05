package com.bazl.dna.database.service.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bazl.dna.database.service.model.po.QuickCompareResultSame;
import com.bazl.dna.database.service.model.qo.QuickCompareResultSameQuery;
import com.bazl.dna.database.service.model.vo.QuickCompareResultSameVo;

/**
 * <p>
 * 同型快速比对结果表 Mapper 接口
 * </p>
 *
 * @author lizhihua
 * @since 2020-05-14
 */
public interface QuickCompareResultSameMapper extends BaseMapper<QuickCompareResultSame> {

    //通过比对id查询比对结果
    List<QuickCompareResultSameVo> selectByQuickSameQueueId(Integer quickCompareQueueId);
    //条数
    int selectBySameQueueIdCount(Integer quickCompareQueueId);
    
	public int countList(@Param("query") QuickCompareResultSameQuery query);
	
	public List<QuickCompareResultSameQuery> findList(@Param("query") QuickCompareResultSameQuery query,
			@Param("offset") int offset, @Param("rows") int rows);
}
