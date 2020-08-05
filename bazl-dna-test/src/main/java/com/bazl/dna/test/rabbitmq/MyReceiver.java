package com.bazl.dna.test.rabbitmq;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消息接收 
 * 
 * @QueueBinding 监听绑定队列
 * value: @Queue(value = ""): 绑定队列类型名称
 * exchange: @Exchange(value = "", type = "")交换器名称  
 * 		type: 交换器类型
 * 			ExchangeTypes.DIRECT  指定
 * 			ExchangeTypes.TOPIC   群发
 * 			ExchangeTypes.FANOUT  广播
 * key:路由键值
 * 
 * @author zhaoyong
 */
@Component
@RabbitListener(
		bindings = @QueueBinding(
				value = @Queue(value = "myQueue", autoDelete = "false"), 
				exchange = @Exchange(value = "exchange.direct", type = ExchangeTypes.DIRECT), 
				key = "routingkey"
		)
	)
public class MyReceiver {
	
	@RabbitHandler
	public void process(String message) {
		System.out.println("MqReceiver: " + message);
	}
}
