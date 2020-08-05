package com.bazl.dna.mix.dao;

import com.bazl.dna.mix.model.po.MatchResult;
import com.bazl.dna.mix.model.po.possibilityInfo;
import com.bazl.dna.mix.model.vo.MatchResultVo;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface MatchResultMapper {
    int deleteByPrimaryKey(String id);

    int insert(MatchResult record);

    MatchResult selectByPrimaryKey(String id);

    List<MatchResult> selectAll();

    int updateByPrimaryKey(MatchResult matchResult);

    List<MatchResult> selectByMatchResult(MatchResult matchResult);

    MatchResult queryMatchResultInfo(String id);

    List<MatchResult> querymatchResultSort(@Param("personType")String personType,@Param("createPerson")String createPerson);

    //根据队列id查询比对结果条数
    int selectByMatchCount(String compareId);
    
    int selectMatchResultByEffect(@Param("effectFlag") String effectFlag,@Param("createPerson")String createPerson);

    //条件查询
    MatchResult selectByMatch(MatchResult matchResult);

    //条件分页查询
    List<MatchResult> selectByMatchResultVo(MatchResultVo query);
    //分页条数
    int selectByMatchResultCount(MatchResultVo query);

    int selectMatchResultCount(String compareId);

    List<MatchResult> selectByMatchResult2(MatchResult matchResult);

    List<MatchResult> selectByMatchResultinfo(MatchResult matchResult);

    int selectMatchinfoCount(MatchResult matchResult);

    List<MatchResult> selectMatchList(MatchResult matchResult);

    int insertPossibilityGene(possibilityInfo bean);

    //查询最新的前30条
    List<MatchResult> selectByThirtyMatchList(String createPerson);

    //比中结果查询
    ArrayList<MatchResultVo> selectMatchResultList(MatchResult matchResult);
    //比中结果数量
    int selectMatchResultListCount(MatchResult matchResult);
    //根据拆分id获取
    MatchResult selectBySplitedId(String id);
    //根据混合id获取比中基因信息
    MatchResult selectByMixedId(String id);
    //根据id获取
    MatchResult selectById(String matchId);
}