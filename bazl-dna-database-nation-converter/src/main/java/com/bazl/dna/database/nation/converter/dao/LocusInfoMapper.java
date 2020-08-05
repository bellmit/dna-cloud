package com.bazl.dna.database.nation.converter.dao;


import com.bazl.dna.database.nation.converter.model.po.LocusInfo;

import java.util.List;
import java.util.Map;

public interface LocusInfoMapper {

    public List<LocusInfo> selectByLocusType(String locusType);

    public List<String> selectNameByLocusType(String locusType);

    int deleteByPrimaryKey(String id);

    int insert(LocusInfo record);

    //根据基因座id查询相应基因座信息
    LocusInfo selectByPrimaryKey(String id);

    //基因座信息列表
    List<LocusInfo> selectAll();

    int updateByPrimaryKey(LocusInfo record);

    //根据基因座id查询基因座名称
    LocusInfo selectLocusInfo(String id);

    //查询所有的基因座名称
    List<Map<String, Object>> selectByLocusName();

    //查询所有的基因座名称有序
    List<LocusInfo> selectLocusInfoList(String reagentKitId);


    //根据试剂盒名称查询基因座名称
    List<Map<String, Object>> selectLocusNameList(String reagentKitName);

    //基因座名称模糊查询
    List<LocusInfo> selectByNameVague(String locusName);
}