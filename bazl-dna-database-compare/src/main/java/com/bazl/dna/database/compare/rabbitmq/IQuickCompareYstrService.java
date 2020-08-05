package com.bazl.dna.database.compare.rabbitmq;

import java.util.List;
import java.util.Set;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.database.compare.constants.QuickCompareConstants;
import com.bazl.dna.database.service.model.po.AlleleFrequencyInfo;
import com.bazl.dna.database.service.model.po.QuickCompareQueue;

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
public interface IQuickCompareYstrService {

	/**
	 * 消息接口 保存比对结果
	 */
	public void saveMatchResult(QuickCompareQueue compareQueue, JSONObject compareJsonObject,
			Set<String> resultGeneIdSet, Integer queueId, List<AlleleFrequencyInfo> frequencyList);
	
	/**
	 * 消息接口 比对
	 */
	@RabbitListener(
		containerFactory = "customerContainerFactory",
		bindings = @QueueBinding(
				value = @Queue(value = QuickCompareConstants.QUEUE_NAME_QUICK_YSTR, autoDelete = "false"), 
				exchange = @Exchange(value = QuickCompareConstants.QUEUE_TYPE_QUICK_YSTR, type = ExchangeTypes.DIRECT), 
				key = QuickCompareConstants.QUEUE_KEY_QUICK_YSTR_COMPARE
		)
	)
	public void compare(QuickCompareQueue compareQueue);
}
