package com.bazl.dna.database.compare.rabbitmq;

import java.util.Set;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.bazl.dna.database.compare.constants.AutoCompareConstants;
import com.bazl.dna.database.service.model.po.AutoCompareQueue;
import com.bazl.dna.database.service.model.po.DnaMixGeneInfo;

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
public interface IAutoCompareMixService {

	/**
	 * 消息接口 保存比对结果
	 */
	public void saveMatchResult(AutoCompareQueue compareQueue, DnaMixGeneInfo compareGeneInfo, Set<String> resultGeneIdSet);
	
	/**
	 * 消息接口 比对
	 */
	@RabbitListener(
		containerFactory = "customerContainerFactory",
		bindings = @QueueBinding(
				value = @Queue(value = AutoCompareConstants.QUEUE_NAME_AUTO_MIX, autoDelete = "false"), 
				exchange = @Exchange(value = AutoCompareConstants.QUEUE_TYPE_AUTO_MIX, type = ExchangeTypes.DIRECT), 
				key = AutoCompareConstants.QUEUE_KEY_AUTO_MIX_COMPARE
		)
	)
	public void compare(AutoCompareQueue compareQueue);
}
