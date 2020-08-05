package com.bazl.dna.database.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bazl.dna.database.service.model.po.InstoreDataCondition;

import java.util.List;

/**
 * <p>
 * 入库数据类型表 Mapper 接口
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-19
 */
public interface InstoreDataConditionMapper extends BaseMapper<InstoreDataCondition> {
    /**
     * 查询所有的入库数据最少基因位点数
     * @return
     */
    public List<InstoreDataCondition> LeastGeneCountForInstoreDataType();
}
