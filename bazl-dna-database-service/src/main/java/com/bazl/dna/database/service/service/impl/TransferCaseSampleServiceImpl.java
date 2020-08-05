package com.bazl.dna.database.service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bazl.dna.database.service.mapper.TransferCaseSampleMapper;
import com.bazl.dna.database.service.model.po.TransferCaseSample;
import com.bazl.dna.database.service.service.TransferCaseSampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 上报案件样本表 服务实现类
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-17
 */
@Service
public class TransferCaseSampleServiceImpl extends ServiceImpl<TransferCaseSampleMapper, TransferCaseSample> implements TransferCaseSampleService {

    @Autowired
    TransferCaseSampleMapper transferCaseSampleMapper;

    @Override
    public List<TransferCaseSample> selectByCaseQueueId(int transferCaseQueueId) {
        return transferCaseSampleMapper.selectByCaseQueueId(transferCaseQueueId);
    }
}
