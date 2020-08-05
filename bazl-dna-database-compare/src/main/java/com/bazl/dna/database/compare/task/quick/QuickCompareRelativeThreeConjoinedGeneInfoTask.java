package com.bazl.dna.database.compare.task.quick;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bazl.dna.database.compare.constants.QuickCompareConstants;
import com.bazl.dna.database.compare.service.ISendQuickCompareService;
import com.bazl.dna.database.constants.Constants;
import com.bazl.dna.database.service.model.po.QuickCompareQueue;
import com.bazl.dna.database.service.service.QuickCompareQueueService;

/**
 * 快速比对队列 亲缘三联体队列 定时任务
 * 
 * @author zhaoyong
 */
@Component
public class QuickCompareRelativeThreeConjoinedGeneInfoTask {
	
	@Autowired
	private ISendQuickCompareService compareService;
	
	@Autowired
	private QuickCompareQueueService queueService;
	
	@Value("${compare.quick.enabled}")
	private boolean isEnabled;
	
	@Scheduled(initialDelay = 100000, fixedDelay = 1000 * 60 * 3)
    public void execute() {
		if (isEnabled) {
			// 1.获取需要比对队列的样本 compareQueue 条件: 同一比对 未比对
			List<QuickCompareQueue> compareQueueList = queueService.findListByCompareMode(
					Constants.COMPARE_MODE_RELATIVE_THREE_CONJOINED, Constants.COMPARE_STATUS_NO,
					QuickCompareConstants.COMPARE_PAGE_SIZE);
			for (QuickCompareQueue compareQueue : compareQueueList) {
				// 线程执行
				compareService.sendQuickRelativeThreeConjoinedCompare(compareQueue);
				
				// 7.更新比对队列
				compareQueue.setQueueStatus(Constants.COMPARE_STATUS_WAITING);
				compareQueue.setUpdateDatetime(LocalDateTime.now());
				queueService.updateById(compareQueue);
			}
		}
    }
	
}
