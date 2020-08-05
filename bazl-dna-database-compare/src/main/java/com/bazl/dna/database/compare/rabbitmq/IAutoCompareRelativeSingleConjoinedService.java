package com.bazl.dna.database.compare.rabbitmq;

import java.util.List;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.bazl.dna.database.algorithm.params.StrRelativeCompareParams;
import com.bazl.dna.database.compare.constants.AutoCompareConstants;
import com.bazl.dna.database.service.model.po.AlleleFrequencyInfo;
import com.bazl.dna.database.service.model.po.AutoCompareQueue;

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
public interface IAutoCompareRelativeSingleConjoinedService {

	/**
	 * 消息接口接口
	 * @param message
	 */
	public void saveMatchResult(AutoCompareQueue compareQueue, List<AlleleFrequencyInfo> frequencyList, 
			StrRelativeCompareParams fmzCompareParams, Integer id, Integer sampleId, String geneInfo);
	
	/**
	 * 消息接口 比对
	 */
	@RabbitListener(
		containerFactory = "customerContainerFactory",
		bindings = @QueueBinding(
				value = @Queue(value = AutoCompareConstants.QUEUE_NAME_AUTO_RELATIVE_SINGLE_CONJOINED, autoDelete = "false"), 
				exchange = @Exchange(value = AutoCompareConstants.QUEUE_TYPE_AUTO_RELATIVE_SINGLE_CONJOINED, type = ExchangeTypes.DIRECT), 
				key = AutoCompareConstants.QUEUE_KEY_AUTO_RELATIVE_SINGLE_CONJOINED_COMPARE
		)
	)
	public void compare(AutoCompareQueue compareQueue);
}
