package com.bazl.dna.database.service.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bazl.dna.database.service.model.po.DictItem;

/**
 * <p>
 * 字典条目信息表 服务类
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
public interface DictItemService extends IService<DictItem> {

    /**
     * 获取指定类型的字典列表
     * @return
     */
    public List<DictItem> selectDictItemListByType(String dictTypeCode);

    String selectNameByTypeCodeAndCode(String dictTypeCode, String dictCode);

}
