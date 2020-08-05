package com.bazl.dna.database.service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bazl.dna.database.service.model.po.RegionInfo;
import com.bazl.dna.database.service.model.vo.RegionInfoVo;

import java.util.List;


public interface RegionInfoService extends IService<RegionInfo> {

    int deleteByPrimaryKey(Integer id);

    int insert(RegionInfo record);

    int insertSelective(RegionInfo record);

    RegionInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RegionInfo record);

    int updateByPrimaryKey(RegionInfo record);

    /*
    *   根据 region_level 等级查询数据
    * */
    List<RegionInfo> selectByLevelList(Integer regionLevel);

    /*
*   根据 region_level 等级查询数据
* */
    List<RegionInfoVo> selectByLevel(Integer regionLevel);

    /**
     * 查询父级区县账户vo
      * @param parentRegionCode
     * @return
     */
    List<RegionInfoVo> selectByParentRegionCode(String parentRegionCode);
    /**
     * 查询父级区县账户
     * @param parentRegionCode
     * @return
     */
    List<RegionInfo> selectByParentCode(String parentRegionCode);

    /**
     *  查询根据code
     * @param regionCode
     * @return
     */
    RegionInfo selectByRegionCode(String regionCode);
}
