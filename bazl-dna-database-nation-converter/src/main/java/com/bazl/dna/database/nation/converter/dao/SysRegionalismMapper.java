package com.bazl.dna.database.nation.converter.dao;

import com.bazl.dna.database.nation.converter.model.po.SysRegionalism;

import java.util.List;

public interface SysRegionalismMapper {

    int deleteByPrimaryKey(String id);

    int insert(SysRegionalism record);

    SysRegionalism selectByPrimaryKey(String id);

    List<SysRegionalism> selectAll();

    int updateByPrimaryKey(SysRegionalism record);

    List<SysRegionalism> selectProvinceAndCity(String code);

    List<SysRegionalism> selectCitys(String code);

}