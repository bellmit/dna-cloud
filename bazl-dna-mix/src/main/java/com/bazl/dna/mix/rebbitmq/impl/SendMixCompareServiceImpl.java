package com.bazl.dna.mix.rebbitmq.impl;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bazl.dna.mix.constants.MixCompareConstants;
import com.bazl.dna.mix.rebbitmq.ISendMixCompareService;

@Service
public class SendMixCompareServiceImpl implements ISendMixCompareService {

	@Autowired
	private AmqpTemplate rabbitTemplate;


	@Override
	public void sendCompare(String json) {
		rabbitTemplate.convertAndSend(MixCompareConstants.QUEUE_TYPE,
				MixCompareConstants.QUEUE_KEY_COMPARE, json);
	}

}
