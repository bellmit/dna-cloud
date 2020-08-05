package com.bazl.dna.database.core.rabbitmq.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.database.core.rabbitmq.IReceiverLabServerService;
import com.bazl.dna.database.service.model.po.LabServerInfo;
import com.bazl.dna.database.service.service.LabServerInfoService;

@Service
public class ReceiverLabServerServiceImpl implements IReceiverLabServerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReceiverLabServerServiceImpl.class);
	
	@Autowired
	private LabServerInfoService service;
	
	@Override
	public void receiver(String message) {
		JSONObject json = JSONObject.parseObject(message);
		LOGGER.info("receiver: {}", json);
		
		LabServerInfo entity = new LabServerInfo();
		entity.setId(json.getInteger("id"));
		entity.setOrgId(json.getString("orgId"));
		entity.setLabServerIpaddr(json.getString("serverIpAddr"));
		entity.setLabServerName(json.getString("labName"));
		entity.setLabServerNo(json.getString("serverNumber"));
		service.save(entity);
		
		
	}

}
