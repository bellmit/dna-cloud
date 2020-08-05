package com.bazl.dna.sys.rabbitmq.impl;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bazl.dna.sys.constants.SysLabConstants;
import com.bazl.dna.sys.rabbitmq.ISendLabService;

@Service
public class SendLabServiceImpl implements ISendLabService {
	
	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	/**
	 * 对应消息监听 @Exchange（ value = "" key = "")
	 */
	@Override
	public void send(String message) {
		rabbitTemplate.convertAndSend(SysLabConstants.QUEUE_TYPE, SysLabConstants.QUEUE_KEY, message);
	}
}
