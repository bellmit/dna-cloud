package com.bazl.dna.database.service.service;

import com.bazl.dna.database.service.model.po.AutoCompareSettings;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 自动比对设置表 服务类
 * </p>
 *
 * @author lizhihua
 * @since 2020-05-13
 */
public interface AutoCompareSettingsService extends IService<AutoCompareSettings> {
    /**
     *  查看全部自动比对设置
      * @return
     */
    public List<AutoCompareSettings> selectAllCompareSetting(String compareMode);

}
