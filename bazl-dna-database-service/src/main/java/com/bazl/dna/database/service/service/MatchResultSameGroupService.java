package com.bazl.dna.database.service.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bazl.dna.common.PageInfo;
import com.bazl.dna.database.service.model.po.CompareResult;
import com.bazl.dna.database.service.model.po.MatchResultSameGroup;
import com.bazl.dna.database.service.model.qo.MatchStrDatailQuery;
import com.bazl.dna.database.service.model.qo.MatchStrDatailResultQuery;
import com.bazl.dna.database.service.model.qo.MatchStrResultQuery;

/**
 * <p>
 * 同型比对结果分组信息表 服务类
 * </p>
 *
 * @author lizhihua
 * @since 2020-03-06
 */
public interface MatchResultSameGroupService extends IService<MatchResultSameGroup> {

    List<CompareResult> queryStrCompareResult(PageInfo pageInfo, CompareResult compareResult);

    CompareResult queryStrCompareinfo(String id);

    int resultCount(MatchStrResultQuery query);

    List<MatchStrResultQuery> resultInfo(MatchStrResultQuery query);

    int detailCount(MatchStrDatailQuery query);

    List<MatchStrDatailResultQuery> detailInfo(MatchStrDatailQuery query);
    
    /**
     * 获取sequence
     * @return
     */
    Integer getNextval();

    /**
     * 根据分类查同行比中类型
     * @param groupType
     * @return
     */
    List<MatchResultSameGroup> selectListByGroupType(String groupType);
}
