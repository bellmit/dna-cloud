package com.bazl.dna.database.service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bazl.dna.annotation.RedisCacheEvict;
import com.bazl.dna.database.service.mapper.RegionInfoMapper;
import com.bazl.dna.database.service.model.po.RegionInfo;
import com.bazl.dna.database.service.model.vo.RegionInfoVo;
import com.bazl.dna.database.service.service.RegionInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionInfoServiceImpl extends ServiceImpl<RegionInfoMapper, RegionInfo> implements RegionInfoService {

	private static final String CACHE_NAME = "RegionInfo";
	private static final String CACHE_KEY_ID = "#id";
	
    @Autowired
    RegionInfoMapper regionInfoMapper;

    @Override
    @RedisCacheEvict(value = CACHE_NAME, key = CACHE_KEY_ID)
	@CacheEvict(value = CACHE_NAME, allEntries = true)
    public int deleteByPrimaryKey(Integer id) {
        return regionInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    @RedisCacheEvict(value = CACHE_NAME, key = CACHE_KEY_ID)
	@CacheEvict(value = CACHE_NAME, allEntries = true)
    public int insert(RegionInfo record) {
        return regionInfoMapper.insert(record);
    }

    @Override
    @RedisCacheEvict(value = CACHE_NAME, key = CACHE_KEY_ID)
	@CacheEvict(value = CACHE_NAME, allEntries = true)
    public int insertSelective(RegionInfo record) {
        return regionInfoMapper.insertSelective(record);
    }

    @Override
    @RedisCacheEvict(value = CACHE_NAME, key = CACHE_KEY_ID)
	@CacheEvict(value = CACHE_NAME, allEntries = true)
    public RegionInfo selectByPrimaryKey(Integer id) {
        return regionInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    @RedisCacheEvict(value = CACHE_NAME, key = CACHE_KEY_ID)
	@CacheEvict(value = CACHE_NAME, allEntries = true)
    public int updateByPrimaryKeySelective(RegionInfo record) {
        return regionInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    @RedisCacheEvict(value = CACHE_NAME, key = CACHE_KEY_ID)
	@CacheEvict(value = CACHE_NAME, allEntries = true)
    public int updateByPrimaryKey(RegionInfo record) {
        return regionInfoMapper.updateByPrimaryKey(record);
    }

    @Override
    @Cacheable(value = CACHE_NAME + ":selectByLevelList", key = "#regionLevel")
    public List<RegionInfo> selectByLevelList(Integer regionLevel) {
        List<RegionInfo> regionInfos = null;
        try {
            regionInfos = regionInfoMapper.selectByLevel(regionLevel);
        } catch (Exception e) {
            log.error("根据级别查询地级区划信息失败！" + e.getMessage());
        }
        return regionInfos;
    }



    @Override
    public List<RegionInfoVo> selectByLevel(Integer regionLevel) {
        List<RegionInfoVo> regionInfos = null;
        try {
            regionInfos = regionInfoMapper.selectByLevelList(regionLevel);
        } catch (Exception e) {
            log.error("根据级别查询地级区划信息失败！" + e.getMessage());
        }
        return regionInfos;
    }


    @Override
    public List<RegionInfoVo> selectByParentRegionCode(String parentRegionCode) {
        try{
           return  regionInfoMapper.selectByParentRegionCode(parentRegionCode);
        }catch (Exception ex){
            log.error("根据区域父级信息查询子机构信息失败!"+ex.getMessage());
        }
        return null ;
    }

    @Override
    public List<RegionInfo> selectByParentCode(String parentRegionCode) {
        try{
            return  regionInfoMapper.selectByParentCode(parentRegionCode);
        }catch (Exception ex){
            log.error("根据区域父级信息查询子机构信息失败!"+ex.getMessage());
        }
        return null ;
    }

    @Override
    public RegionInfo selectByRegionCode(String regionCode) {
       try {
           return regionInfoMapper.selectByRegionCode(regionCode);
       }catch (Exception ex){
           log.error("invoke RegionInfoService.selectByRegionCode error!"+ex.getMessage());
       }
        return null;
    }
}
