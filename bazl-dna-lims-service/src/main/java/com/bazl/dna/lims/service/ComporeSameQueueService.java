package com.bazl.dna.lims.service;

import java.util.List;

import com.bazl.dna.common.PageInfo;
import com.bazl.dna.lims.model.vo.ComporeSameQueueVo;

/**
 * Created by huawei on 2019/8/6.
 */
public interface ComporeSameQueueService {

    //查询同一比对队列
    public List<ComporeSameQueueVo> selectVOPaginationList(ComporeSameQueueVo query, PageInfo pageInfo);

    //查询队列数量
    public int selectVOCount(ComporeSameQueueVo query);

}
