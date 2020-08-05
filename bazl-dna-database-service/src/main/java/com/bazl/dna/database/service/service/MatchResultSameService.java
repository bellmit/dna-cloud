package com.bazl.dna.database.service.service;

import com.bazl.dna.database.algorithm.result.StrSameCompareResult;
import com.bazl.dna.database.service.model.po.MatchResultSame;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bazl.dna.database.service.model.po.MatterComparePerson;

import java.util.List;

/**
 * <p>
 * 同型比对结果表 服务类
 * </p>
 *
 * @author lizhihua
 * @since 2020-03-06
 */
public interface MatchResultSameService extends IService<MatchResultSame> {

    void insertMatchResult(StrSameCompareResult strSameCompareResult, String key);

    MatterComparePerson queryCaseInfo(MatchResultSame matchResultSame);

    MatterComparePerson queryMatterCompareMatter(MatchResultSame matchResultSame);
    
    /**
     * 获取 MatchResultSameGroupNo
     * @param sampleAId
     * @param sampleBId
     * @return
     */
    public Integer getMatchResultGroupNo(Integer sampleAId, Integer sampleBId);

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
    public List<MatchResultSame> selectListByGroupId(Integer matchGroupId);
    
    public void updateMatchResultSame(MatchResultSame matchResultSame);
}
