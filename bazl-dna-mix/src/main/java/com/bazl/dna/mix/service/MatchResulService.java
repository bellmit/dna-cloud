package com.bazl.dna.mix.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bazl.dna.common.PageInfo;
import com.bazl.dna.mix.model.po.MatchResult;
import com.bazl.dna.mix.model.po.possibilityInfo;
import com.bazl.dna.mix.model.vo.MatchResultVo;

/**
 * Created by Administrator on 2019/12/19.
 */
@Repository
public interface MatchResulService {
    int deleteByPrimaryKey(String id);

    int insert(MatchResult record);

    MatchResult selectByPrimaryKey(String id);

    List<MatchResult> selectAll();

    int updateByPrimaryKey(MatchResult matchResult);

    List<MatchResult> selectByMatchResult(MatchResult matchResult);

    MatchResult queryMatchResultInfo(String id);
    
    int selectMatchResultByEffect(String effectFlag,String createPerson);

    //根据队列id查询比对结果条数
    int selectByMatchCount(String compareId);

    //查看比对结果
    MatchResult selectByMatch(MatchResult matchResult);

    //条件查询
    List<MatchResult> selectMatchList(MatchResult matchResult);

    //条件分页查
    List<MatchResult> selectByMatchResultVo(MatchResultVo query, PageInfo pageInfo);
    //分页条数
    int selectByMatchResultCount(MatchResultVo query);

    int insertPossibilityGene(possibilityInfo bean);

    //查询最新的前30条
    List<MatchResult> selectByThirtyMatchList(String createPerson);
    //比中详情 拆分比中单一和混合比中单一 列表 查看比中详情按钮
    HashMap<Object, Object> selectMatchDetails(String id, String matchId, String type);
}
