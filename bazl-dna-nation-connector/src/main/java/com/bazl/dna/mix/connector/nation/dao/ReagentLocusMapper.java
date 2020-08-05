package com.bazl.dna.mix.connector.nation.dao;



import com.bazl.dna.mix.connector.nation.model.po.ReagentLocus;

import java.util.List;

public interface ReagentLocusMapper {
    int deleteByPrimaryKey(String id);

    int insert(ReagentLocus record);

    ReagentLocus selectByPrimaryKey(String id);

    List<ReagentLocus> selectAll();

    int updateByPrimaryKey(ReagentLocus record);

    //根据所有的试剂盒id 查询到所有的基因座id；
    List<ReagentLocus>  selectByKitIdToLocusID(String reagentKitId);
}