package com.bazl.dna.database.service.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bazl.dna.database.service.model.po.MatchResultYstrGroup;
import com.bazl.dna.database.service.model.qo.MatchYstrDetailQuery;
import com.bazl.dna.database.service.model.qo.MatchYstrDetailResultQuery;
import com.bazl.dna.database.service.model.qo.MatchYstrResultQuery;

/**
 * <p>
 * Y-STR比中结果信息表 Mapper 接口
 * </p>
 *
 * @author lizhihua
 * @since 2020-03-06
 */
public interface MatchResultYstrGroupMapper extends BaseMapper<MatchResultYstrGroup> {
	
	int resultCount(MatchYstrResultQuery query);

    List<MatchYstrResultQuery> resultInfo(MatchYstrResultQuery query);

    int detailCount(MatchYstrDetailQuery query);

    List<MatchYstrDetailResultQuery> detailInfo(MatchYstrDetailQuery query);

	/**
     * 获取sequence
     * @return
     */
    Integer getNextval();
}
