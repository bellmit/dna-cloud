package com.bazl.dna.lims.core.dao;

import com.bazl.dna.lims.core.model.po.LabPuzzleMiddle;
import java.util.List;

public interface LabPuzzleMiddleMapper {
    int deleteByPrimaryKey(String id);

    int insert(LabPuzzleMiddle record);

    LabPuzzleMiddle selectByPrimaryKey(String id);

    List<LabPuzzleMiddle> selectAll();

    int updateByPrimaryKey(LabPuzzleMiddle record);

    //根据pcrid查询数据
    LabPuzzleMiddle selectByPcrId(String pcrId);

    //根据taskid查询数据
    List<LabPuzzleMiddle> selectByTaskId(String taskId);

    //根据syid查询数据
    LabPuzzleMiddle selectBySyId(String syId);
}