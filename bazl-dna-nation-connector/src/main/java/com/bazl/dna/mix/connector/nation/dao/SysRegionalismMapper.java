package com.bazl.dna.mix.connector.nation.dao;

import com.bazl.dna.mix.connector.nation.model.po.SysRegionalism;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SysRegionalismMapper {

    int deleteByPrimaryKey(String id);

    int insert(SysRegionalism record);

    SysRegionalism selectByPrimaryKey(String id);

    List<SysRegionalism> selectAll();

    int updateByPrimaryKey(SysRegionalism record);

    List<SysRegionalism> selectProvinceAndCity(@Param("code") List<String> code);

    List<SysRegionalism> selectCitys(String code);

}