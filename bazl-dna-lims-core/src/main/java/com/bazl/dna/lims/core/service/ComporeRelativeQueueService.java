package com.bazl.dna.lims.core.service;

import com.bazl.dna.lims.core.model.PageInfo;
import com.bazl.dna.lims.core.model.vo.ComporeRelativeQueueVo;

import java.util.List;

/**
 * Created by huawei on 2019/8/6.
 */
public interface ComporeRelativeQueueService {

    //查询同一比对队列
    public List<ComporeRelativeQueueVo> selectVOPaginationList(ComporeRelativeQueueVo query, PageInfo pageInfo);

    //查询队列数量
    public int selectVOCount(ComporeRelativeQueueVo query);

}
