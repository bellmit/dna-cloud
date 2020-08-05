package com.bazl.dna.database.service.mapper;

import com.bazl.dna.database.service.model.po.MatchResultMix;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * dna混合比对结果信息表 Mapper 接口
 * </p>
 *
 * @author lizhihua
 * @since 2020-03-06
 */
public interface MatchResultMixMapper extends BaseMapper<MatchResultMix> {

	/**
     * 获取 MatchResultGroupNo
     * @param sampleAId
     * @param sampleBId
     * @return
     */
    Integer getMatchResultGroupNo(@Param("sampleAId") Integer sampleAId, @Param("sampleBId") Integer sampleBId);
}
