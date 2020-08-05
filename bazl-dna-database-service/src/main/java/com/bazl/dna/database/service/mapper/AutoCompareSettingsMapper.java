package com.bazl.dna.database.service.mapper;

import com.bazl.dna.database.service.model.po.AutoCompareSettings;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 自动比对设置表 Mapper 接口
 * </p>
 *
 * @author lizhihua
 * @since 2020-05-13
 */
public interface AutoCompareSettingsMapper extends BaseMapper<AutoCompareSettings> {

    /**
     * 查看全部自动比对设置
     * @return
     */
    public List<AutoCompareSettings> selectAllCompareSetting(String compareMode);

}
