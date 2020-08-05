package com.bazl.dna.database.service.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bazl.dna.database.service.model.po.MatchResultYstrGroup;
import com.bazl.dna.database.service.model.qo.MatchYstrDetailQuery;
import com.bazl.dna.database.service.model.qo.MatchYstrDetailResultQuery;
import com.bazl.dna.database.service.model.qo.MatchYstrResultQuery;

/**
 * <p>
 * Y-STR比中结果信息表 服务类
 * </p>
 *
 * @author lizhihua
 * @since 2020-03-06
 */
public interface MatchResultYstrGroupService extends IService<MatchResultYstrGroup> {
	
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
