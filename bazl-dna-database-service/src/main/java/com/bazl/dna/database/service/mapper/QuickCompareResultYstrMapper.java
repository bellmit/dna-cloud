package com.bazl.dna.database.service.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bazl.dna.database.service.model.po.QuickCompareResultYstr;
import com.bazl.dna.database.service.model.qo.QuickCompareResultYstrQuery;
import com.bazl.dna.database.service.model.vo.QuickCompareResultYstrVo;

/**
 * <p>
 * ystr快速比对结果表 Mapper 接口
 * </p>
 *
 * @author lizhihua
 * @since 2020-05-14
 */
public interface QuickCompareResultYstrMapper extends BaseMapper<QuickCompareResultYstr> {

    /**
     * 根据序列ID，查询YSTR比中记录
     * @param quickCompareQueueId
     * @return
     */
    public List<QuickCompareResultYstrVo> selectByQuickCompareQueueId(Integer quickCompareQueueId);

    /**
     * 根据序列ID查询总计数
     * @param quickCompareQueueId
     * @return
     */
    public int selectByQueueIdCount(Integer quickCompareQueueId);
    
public int countList(@Param("query") QuickCompareResultYstrQuery query);
	
	public List<QuickCompareResultYstrQuery> findList(@Param("query") QuickCompareResultYstrQuery query,
			@Param("offset") int offset, @Param("rows") int rows);
}
