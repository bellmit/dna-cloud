package com.bazl.dna.database.service.mapper;

import com.bazl.dna.database.service.model.po.MatchResultMixGroup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * dna混合比对结果分组信息表 Mapper 接口
 * </p>
 *
 * @author lizhihua
 * @since 2020-03-06
 */
public interface MatchResultMixGroupMapper extends BaseMapper<MatchResultMixGroup> {

	/**
     * 获取sequence
     * @return
     */
    Integer getNextval();
}
