package com.bazl.dna.lims.dao;

import com.bazl.dna.lims.model.po.ComporeSameQueue;
import com.bazl.dna.lims.model.vo.ComporeSameQueueVo;

import java.util.List;

public interface ComporeSameQueueMapper {
    int deleteByPrimaryKey(String id);

    int insert(ComporeSameQueue record);

    ComporeSameQueue selectByPrimaryKey(String id);

    List<ComporeSameQueue> selectAll();

    int updateByPrimaryKey(ComporeSameQueue record);

    //查询同一比对队列
    public List<ComporeSameQueueVo> selectVOPaginationList(ComporeSameQueueVo query);

    //查询队列数量
    public int selectVOCount(ComporeSameQueueVo query);
}