package com.bazl.dna.database.service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bazl.dna.database.service.mapper.DictItemMapper;
import com.bazl.dna.database.service.model.po.DictItem;
import com.bazl.dna.database.service.service.DictItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 字典条目信息表 服务实现类
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
@Service
public class DictItemServiceImpl extends ServiceImpl<DictItemMapper, DictItem> implements DictItemService {
	
	private static final String CACHE_NAME = "DictItem";
	
    @Autowired
    private DictItemMapper dictItemMapper;

    @Override
    @Cacheable(value = CACHE_NAME + ":selectDictItemListByType", key = "#dictTypeCode")
    public List<DictItem> selectDictItemListByType(String dictTypeCode) {
        return dictItemMapper.selectListByDictType(dictTypeCode);
    }
    
    @Override
    @Cacheable(value = CACHE_NAME + ":selectNameByTypeCodeAndCode", key = "#dictTypeCode+':'+#dictCode")
    public String selectNameByTypeCodeAndCode(String dictTypeCode, String dictCode) {
    		return dictItemMapper.selectNameByTypeCodeAndCode(dictTypeCode, dictCode);
    }
}
