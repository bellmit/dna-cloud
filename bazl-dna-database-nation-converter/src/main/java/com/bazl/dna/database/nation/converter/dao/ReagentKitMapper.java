package com.bazl.dna.database.nation.converter.dao;


import com.bazl.dna.database.nation.converter.model.po.ReagentKit;

import java.util.List;
import java.util.Map;

public interface ReagentKitMapper {

    int deleteByPrimaryKey(String id);

    int insert(ReagentKit record);

    ReagentKit selectByPrimaryKey(String id);

    //查询全部的试剂盒信息
    List<ReagentKit> selectAll();

    int updateByPrimaryKey(ReagentKit record);

    //查询全部的试剂盒id，名称
    List<Map<String, Object>> selectKitName();
}