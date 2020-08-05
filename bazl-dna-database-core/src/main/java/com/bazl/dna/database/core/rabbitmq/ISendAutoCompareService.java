package com.bazl.dna.database.core.rabbitmq;

import com.bazl.dna.database.service.model.po.AutoCompareQueue;

/**
 * 自动比对 发送队列
 * @author zhaoyong
 *
 */
public interface ISendAutoCompareService {

	public void sendAutoStrCompare(AutoCompareQueue compareQueue);

	public void sendAutoYstrCompare(AutoCompareQueue compareQueue);

	public void sendAutoMixCompare(AutoCompareQueue compareQueue);

	public void sendAutoRelativeThreeConjoinedCompare(AutoCompareQueue compareQueue);

	public void sendAutoRelativeSingleConjoinedCompare(AutoCompareQueue compareQueue);

}
