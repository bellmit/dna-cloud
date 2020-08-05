package com.bazl.dna.database.core.rabbitmq.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bazl.dna.database.core.constants.AutoCompareConstants;
import com.bazl.dna.database.core.rabbitmq.ISendAutoCompareService;
import com.bazl.dna.database.service.model.po.AutoCompareQueue;

@Service
public class SendAutoCompareServiceImpl implements ISendAutoCompareService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SendAutoCompareServiceImpl.class);

	@Autowired
	private AmqpTemplate rabbitTemplate;

	@Override
	public void sendAutoStrCompare(AutoCompareQueue compareQueue) {
		LOGGER.info("send auto str compare: {}", compareQueue.getId());
		rabbitTemplate.convertAndSend(AutoCompareConstants.QUEUE_TYPE_AUTO_STR,
				AutoCompareConstants.QUEUE_KEY_AUTO_STR_COMPARE, compareQueue);
	}

	@Override
	public void sendAutoYstrCompare(AutoCompareQueue compareQueue) {
		LOGGER.info("send auto ystr compare: {}", compareQueue.getId());
		rabbitTemplate.convertAndSend(AutoCompareConstants.QUEUE_TYPE_AUTO_YSTR,
				AutoCompareConstants.QUEUE_KEY_AUTO_YSTR_COMPARE, compareQueue);
	}

	@Override
	public void sendAutoMixCompare(AutoCompareQueue compareQueue) {
		LOGGER.info("send auto str mix: {}", compareQueue.getId());
		rabbitTemplate.convertAndSend(AutoCompareConstants.QUEUE_TYPE_AUTO_MIX,
				AutoCompareConstants.QUEUE_KEY_AUTO_MIX_COMPARE, compareQueue);
	}

	@Override
	public void sendAutoRelativeThreeConjoinedCompare(AutoCompareQueue compareQueue) {
		LOGGER.info("send auto relative three compare: {}", compareQueue.getId());
		rabbitTemplate.convertAndSend(AutoCompareConstants.QUEUE_TYPE_AUTO_RELATIVE_THREE_CONJOINED,
				AutoCompareConstants.QUEUE_KEY_AUTO_RELATIVE_THREE_CONJOINED_COMPARE, compareQueue);
	}

	@Override
	public void sendAutoRelativeSingleConjoinedCompare(AutoCompareQueue compareQueue) {
		LOGGER.info("send auto relative single compare: {}", compareQueue.getId());
		rabbitTemplate.convertAndSend(AutoCompareConstants.QUEUE_TYPE_AUTO_RELATIVE_SINGLE_CONJOINED,
				AutoCompareConstants.QUEUE_KEY_AUTO_RELATIVE_SINGLE_CONJOINED_COMPARE, compareQueue);
	}
	
	
}
