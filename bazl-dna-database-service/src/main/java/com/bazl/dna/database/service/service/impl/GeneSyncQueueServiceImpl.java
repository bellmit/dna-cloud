package com.bazl.dna.database.service.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bazl.dna.database.service.mapper.GeneSyncQueueMapper;
import com.bazl.dna.database.service.model.po.GeneSyncQueue;
import com.bazl.dna.database.service.service.GeneSyncQueueService;

/**
 * <p>
 * 基因同步队列 服务实现类
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-17
 */
@Service
public class GeneSyncQueueServiceImpl extends ServiceImpl<GeneSyncQueueMapper, GeneSyncQueue> implements GeneSyncQueueService {
	
    @Autowired
    private GeneSyncQueueMapper geneSyncQueueMapper;

    @Override
    public List<GeneSyncQueue> queryGeneInfoList(GeneSyncQueue geneSyncQueue) {
        try {
            List<GeneSyncQueue> geneSyncQueueList = geneSyncQueueMapper.queryGeneInfoList(geneSyncQueue);
            return geneSyncQueueList;
        }catch(Exception ex){
            log.error("invoke GeneSyncQueueService.queryGeneInfoList error.", ex);
            return null;
        }
    }
}
