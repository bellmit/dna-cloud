package com.bazl.dna.database.core.rabbitmq;

import com.bazl.dna.database.service.model.po.QuickCompareQueue;

/**
 * 快速比对 发送队列
 * @author zhaoyong
 *
 */
public interface ISendQuickCompareService {

	public void sendQuickStrCompare(QuickCompareQueue compareQueue);

	public void sendQuickYstrCompare(QuickCompareQueue compareQueue);

	public void sendQuickMixCompare(QuickCompareQueue compareQueue);

	public void sendQuickRelativeThreeConjoinedCompare(QuickCompareQueue compareQueue);

	public void sendQuickRelativeSingleConjoinedCompare(QuickCompareQueue compareQueue);

}
