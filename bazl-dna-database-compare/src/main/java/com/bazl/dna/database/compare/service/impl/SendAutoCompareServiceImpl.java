package com.bazl.dna.database.compare.service.impl;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bazl.dna.database.compare.constants.AutoCompareConstants;
import com.bazl.dna.database.compare.service.ISendAutoCompareService;
import com.bazl.dna.database.service.model.po.AutoCompareQueue;

@Service
public class SendAutoCompareServiceImpl implements ISendAutoCompareService {

	@Autowired
	private AmqpTemplate rabbitTemplate;

	@Override
	public void sendAutoStrCompare(AutoCompareQueue compareQueue) {
		rabbitTemplate.convertAndSend(AutoCompareConstants.QUEUE_TYPE_AUTO_STR,
				AutoCompareConstants.QUEUE_KEY_AUTO_STR_COMPARE, compareQueue);
	}

	@Override
	public void sendAutoYstrCompare(AutoCompareQueue compareQueue) {
		rabbitTemplate.convertAndSend(AutoCompareConstants.QUEUE_TYPE_AUTO_YSTR,
				AutoCompareConstants.QUEUE_KEY_AUTO_YSTR_COMPARE, compareQueue);
	}

	@Override
	public void sendAutoMixCompare(AutoCompareQueue compareQueue) {
		rabbitTemplate.convertAndSend(AutoCompareConstants.QUEUE_TYPE_AUTO_MIX,
				AutoCompareConstants.QUEUE_KEY_AUTO_MIX_COMPARE, compareQueue);
	}

	@Override
	public void sendAutoRelativeThreeConjoinedCompare(AutoCompareQueue compareQueue) {
		rabbitTemplate.convertAndSend(AutoCompareConstants.QUEUE_TYPE_AUTO_RELATIVE_THREE_CONJOINED,
				AutoCompareConstants.QUEUE_KEY_AUTO_RELATIVE_THREE_CONJOINED_COMPARE, compareQueue);
	}

	@Override
	public void sendAutoRelativeSingleConjoinedCompare(AutoCompareQueue compareQueue) {
		rabbitTemplate.convertAndSend(AutoCompareConstants.QUEUE_TYPE_AUTO_RELATIVE_SINGLE_CONJOINED,
				AutoCompareConstants.QUEUE_KEY_AUTO_RELATIVE_SINGLE_CONJOINED_COMPARE, compareQueue);
	}
	
	
}
