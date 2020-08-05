package com.bazl.dna.database.service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bazl.dna.database.service.mapper.AutoCompareSettingsMapper;
import com.bazl.dna.database.service.model.po.AutoCompareSettings;
import com.bazl.dna.database.service.service.AutoCompareSettingsService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 自动比对设置表 服务实现类
 * </p>
 *
 * @author lizhihua
 * @since 2020-05-13
 */
@Service
public class AutoCompareSettingsServiceImpl extends ServiceImpl<AutoCompareSettingsMapper, AutoCompareSettings> implements AutoCompareSettingsService {

    @Autowired
    private  AutoCompareSettingsMapper  autoCompareSettingsMapper;
	
    /**
     * 查看自动比对设置
     * @return
     */
    @Override
    public List<AutoCompareSettings> selectAllCompareSetting(String compareMode) {
        try{
            return autoCompareSettingsMapper.selectAllCompareSetting(compareMode);
        }catch(Exception ex){
            log.error("AutoCompareSettingsService.selectAllCompareSetting error.", ex);
        }
        return Lists.newArrayList();
    }
}
