package com.bazl.dna.database.compare.service.impl;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.database.compare.constants.SysMessageConstants;
import com.bazl.dna.database.compare.service.ISendMessageService;
import com.bazl.dna.database.compare.service.entity.SendMessage;

@Service
public class SendMessageServiceImpl implements ISendMessageService {

	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	/**
	 * 对应消息监听 @Exchange（ value = "" key = "")
	 */
	@Override
	public void send(String messageType, String messageName, String typeId, 
			String context, String sendUser, String receiveUser) {
		SendMessage entity = new SendMessage();
		entity.setQueueType(SysMessageConstants.QUEUE_TYPE);
		entity.setQueueKey(SysMessageConstants.QUEUE_KEY);
		entity.setMessageType(messageType);
		entity.setMessageName(messageName);
		entity.setTypeId(typeId);
		entity.setContext(context);
		entity.setCreateTime(new Timestamp(new Date().getTime()));
		entity.setUpdateTime(new Timestamp(new Date().getTime()));
		entity.setSendUser(sendUser);
		entity.setReceiveUser(receiveUser);
		rabbitTemplate.convertAndSend(SysMessageConstants.QUEUE_TYPE, SysMessageConstants.QUEUE_KEY, 
				JSONObject.toJSONString(entity));
	}
}
