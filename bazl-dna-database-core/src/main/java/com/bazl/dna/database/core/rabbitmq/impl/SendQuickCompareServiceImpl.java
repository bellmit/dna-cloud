package com.bazl.dna.database.core.rabbitmq.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bazl.dna.database.core.constants.QuickCompareConstants;
import com.bazl.dna.database.core.rabbitmq.ISendQuickCompareService;
import com.bazl.dna.database.service.model.po.QuickCompareQueue;

@Service
public class SendQuickCompareServiceImpl implements ISendQuickCompareService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SendQuickCompareServiceImpl.class);

	@Autowired
	private AmqpTemplate rabbitTemplate;

	@Override
	public void sendQuickStrCompare(QuickCompareQueue compareQueue) {
		LOGGER.info("send quick str compare: {}", compareQueue.getId());
		rabbitTemplate.convertAndSend(QuickCompareConstants.QUEUE_TYPE_QUICK_STR,
				QuickCompareConstants.QUEUE_KEY_QUICK_STR_COMPARE, compareQueue);
	}

	@Override
	public void sendQuickYstrCompare(QuickCompareQueue compareQueue) {
		LOGGER.info("send quick ystr compare: {}", compareQueue.getId());
		rabbitTemplate.convertAndSend(QuickCompareConstants.QUEUE_TYPE_QUICK_YSTR,
				QuickCompareConstants.QUEUE_KEY_QUICK_YSTR_COMPARE, compareQueue);
	}

	@Override
	public void sendQuickMixCompare(QuickCompareQueue compareQueue) {
		LOGGER.info("send quick mix compare: {}", compareQueue.getId());
		rabbitTemplate.convertAndSend(QuickCompareConstants.QUEUE_TYPE_QUICK_MIX,
				QuickCompareConstants.QUEUE_KEY_QUICK_MIX_COMPARE, compareQueue);
	}

	@Override
	public void sendQuickRelativeThreeConjoinedCompare(QuickCompareQueue compareQueue) {
		LOGGER.info("send quick relative three compare: {}", compareQueue.getId());
		rabbitTemplate.convertAndSend(QuickCompareConstants.QUEUE_TYPE_QUICK_RELATIVE_THREE_CONJOINED,
				QuickCompareConstants.QUEUE_KEY_QUICK_RELATIVE_THREE_CONJOINED_COMPARE, compareQueue);
	}

	@Override
	public void sendQuickRelativeSingleConjoinedCompare(QuickCompareQueue compareQueue) {
		LOGGER.info("send quick relative single compare: {}", compareQueue.getId());
		rabbitTemplate.convertAndSend(QuickCompareConstants.QUEUE_TYPE_QUICK_RELATIVE_SINGLE_CONJOINED,
				QuickCompareConstants.QUEUE_KEY_QUICK_RELATIVE_SINGLE_CONJOINED_COMPARE, compareQueue);
	}
	
	
}
