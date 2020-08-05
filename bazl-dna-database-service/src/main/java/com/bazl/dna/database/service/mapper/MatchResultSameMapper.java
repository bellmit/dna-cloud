package com.bazl.dna.database.service.mapper;

import com.bazl.dna.database.service.model.po.MatchResultSame;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 同型比对结果表 Mapper 接口
 * </p>
 *
 * @author lizhihua
 * @since 2020-03-06
 */
public interface MatchResultSameMapper extends BaseMapper<MatchResultSame> {

	/**
     * 获取 MatchResultGroupNo
     * @param sampleAId
     * @param sampleBId
     * @return
     */
    Integer getMatchResultGroupNo(@Param("sampleAId") Integer sampleAId, @Param("sampleBId") Integer sampleBId);

    /**
     * 查询最新的10组 同型比中数据
     * @param groupType
     * @return
     */
    public List<MatchResultSame> selectLatestTenList(String groupType);

    /**
     * 同型比中详情根据分组ID
     * @param matchGroupId
     * @return
     */
    List<MatchResultSame> selectListByGroupId(Integer matchGroupId);
}
