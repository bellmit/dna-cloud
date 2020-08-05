package com.bazl.dna.test.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 消息发送
 * @author zhaoyong
 */
@Component
public class MySender {
	
	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	public void send(String context) {
		rabbitTemplate.convertAndSend("exchange.direct", "routingkey", context);
	}
}
