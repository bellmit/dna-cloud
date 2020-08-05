package com.bazl.dna.database.compare.service.impl;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bazl.dna.database.compare.constants.QuickCompareConstants;
import com.bazl.dna.database.compare.service.ISendQuickCompareService;
import com.bazl.dna.database.service.model.po.QuickCompareQueue;

@Service
public class SendQuickCompareServiceImpl implements ISendQuickCompareService {

	@Autowired
	private AmqpTemplate rabbitTemplate;

	@Override
	public void sendQuickStrCompare(QuickCompareQueue compareQueue) {
		rabbitTemplate.convertAndSend(QuickCompareConstants.QUEUE_TYPE_QUICK_STR,
				QuickCompareConstants.QUEUE_KEY_QUICK_STR_COMPARE, compareQueue);
	}

	@Override
	public void sendQuickYstrCompare(QuickCompareQueue compareQueue) {
		rabbitTemplate.convertAndSend(QuickCompareConstants.QUEUE_TYPE_QUICK_YSTR,
				QuickCompareConstants.QUEUE_KEY_QUICK_YSTR_COMPARE, compareQueue);
	}

	@Override
	public void sendQuickMixCompare(QuickCompareQueue compareQueue) {
		rabbitTemplate.convertAndSend(QuickCompareConstants.QUEUE_TYPE_QUICK_MIX,
				QuickCompareConstants.QUEUE_KEY_QUICK_MIX_COMPARE, compareQueue);
	}

	@Override
	public void sendQuickRelativeThreeConjoinedCompare(QuickCompareQueue compareQueue) {
		rabbitTemplate.convertAndSend(QuickCompareConstants.QUEUE_TYPE_QUICK_RELATIVE_THREE_CONJOINED,
				QuickCompareConstants.QUEUE_KEY_QUICK_RELATIVE_THREE_CONJOINED_COMPARE, compareQueue);
	}

	@Override
	public void sendQuickRelativeSingleConjoinedCompare(QuickCompareQueue compareQueue) {
		rabbitTemplate.convertAndSend(QuickCompareConstants.QUEUE_TYPE_QUICK_RELATIVE_SINGLE_CONJOINED,
				QuickCompareConstants.QUEUE_KEY_QUICK_RELATIVE_SINGLE_CONJOINED_COMPARE, compareQueue);
	}
	
	
}
