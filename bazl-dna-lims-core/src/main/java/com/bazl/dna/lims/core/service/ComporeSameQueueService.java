package com.bazl.dna.lims.core.service;

import com.bazl.dna.lims.core.model.PageInfo;
import com.bazl.dna.lims.core.model.vo.ComporeSameQueueVo;

import java.util.List;

/**
 * Created by huawei on 2019/8/6.
 */
public interface ComporeSameQueueService {

    //查询同一比对队列
    public List<ComporeSameQueueVo> selectVOPaginationList(ComporeSameQueueVo query, PageInfo pageInfo);

    //查询队列数量
    public int selectVOCount(ComporeSameQueueVo query);

}
