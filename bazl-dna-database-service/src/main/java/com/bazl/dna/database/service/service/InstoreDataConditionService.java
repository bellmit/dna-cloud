package com.bazl.dna.database.service.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.bazl.dna.database.service.model.po.InstoreDataCondition;

import java.util.List;

/**
 * Created by Liuchang on 2020/5/13.
 */
public interface InstoreDataConditionService  extends IService<InstoreDataCondition> {

    /**
     * 查询所有的入库数据最少基因位点数
     * @return
     */
    public List<InstoreDataCondition> LeastGeneCountForInstoreDataType();

}
