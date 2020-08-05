package com.bazl.dna.database.compare.service;

public interface ISendMessageService {

	public void send(String messageType, String messageName, String typeId, 
			String context, String sendUser, String receiveUser);
}
