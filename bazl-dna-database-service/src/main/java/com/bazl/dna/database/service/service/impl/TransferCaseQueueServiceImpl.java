package com.bazl.dna.database.service.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bazl.dna.database.service.mapper.TransferCaseQueueMapper;
import com.bazl.dna.database.service.model.po.TransferCaseQueue;
import com.bazl.dna.database.service.service.TransferCaseQueueService;
import com.bazl.dna.exception.DnaRuntimeException;

/**
 * <p>
 * 案件数据上报队列 服务实现类
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-17
 */
@Service
public class TransferCaseQueueServiceImpl extends ServiceImpl<TransferCaseQueueMapper, TransferCaseQueue> implements TransferCaseQueueService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TransferCaseQueueServiceImpl.class);
	
	@Autowired
	private TransferCaseQueueMapper mapper;
	
	@Override
	@Transactional
	public int saveTransferCaseQueue(TransferCaseQueue entity) {
		try {
			return mapper.insert(entity);
		} catch (Exception e) {
			LOGGER.error("saveCase error: ", e);
			throw new DnaRuntimeException();
		}
		
	}

}
