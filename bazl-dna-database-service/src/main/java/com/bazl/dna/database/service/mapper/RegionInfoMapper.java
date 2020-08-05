package com.bazl.dna.database.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bazl.dna.database.service.model.po.RegionInfo;
import com.bazl.dna.database.service.model.vo.RegionInfoVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegionInfoMapper extends BaseMapper<RegionInfo> {
    int deleteByPrimaryKey(Integer id);

    int insert(RegionInfo record);

    int insertSelective(RegionInfo record);

    RegionInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RegionInfo record);

    int updateByPrimaryKey(RegionInfo record);

    //根据 region_level 等级查询数据
    List<RegionInfo> selectByLevel(Integer regionLevel);


    //根据 region_level 等级查询数据
    List<RegionInfoVo> selectByLevelList(Integer regionLevel);

    /**
     * 根据parent_region_code 查询子级信息 查询vo
      * @param parentRegionCode
     * @return
     */
    List<RegionInfoVo> selectByParentRegionCode(String parentRegionCode);
    /**
     * 根据parent_region_code 查询子级信息 查询po
     * @param parentRegionCode
     * @return
     */
    List<RegionInfo> selectByParentCode(String parentRegionCode);

    RegionInfo selectByRegionCode(String regionCode);
}