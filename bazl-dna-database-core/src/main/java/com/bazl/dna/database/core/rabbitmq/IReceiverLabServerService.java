package com.bazl.dna.database.core.rabbitmq;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.bazl.dna.database.core.constants.LabServerConstants;

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
				value = @Queue(value = LabServerConstants.QUEUE_NAME, autoDelete = "false"), 
				exchange = @Exchange(value = LabServerConstants.QUEUE_TYPE, type = ExchangeTypes.DIRECT), 
				key = LabServerConstants.QUEUE_KEY
		)
	)
public interface IReceiverLabServerService {

	/**
	 * 消息接口接口
	 * @param message
	 */
	@RabbitHandler
	public void receiver(String message);
}
