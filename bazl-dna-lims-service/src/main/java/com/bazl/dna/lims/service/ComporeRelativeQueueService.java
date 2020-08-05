package com.bazl.dna.lims.service;

import java.util.List;

import com.bazl.dna.common.PageInfo;
import com.bazl.dna.lims.model.vo.ComporeRelativeQueueVo;

/**
 * Created by huawei on 2019/8/6.
 */
public interface ComporeRelativeQueueService {

    //查询同一比对队列
    public List<ComporeRelativeQueueVo> selectVOPaginationList(ComporeRelativeQueueVo query, PageInfo pageInfo);

    //查询队列数量
    public int selectVOCount(ComporeRelativeQueueVo query);

}
