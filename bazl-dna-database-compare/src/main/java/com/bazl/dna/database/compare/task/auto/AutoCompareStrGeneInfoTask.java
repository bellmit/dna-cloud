package com.bazl.dna.database.compare.task.auto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bazl.dna.database.compare.constants.AutoCompareConstants;
import com.bazl.dna.database.compare.service.ISendAutoCompareService;
import com.bazl.dna.database.constants.Constants;
import com.bazl.dna.database.service.model.po.AutoCompareQueue;
import com.bazl.dna.database.service.service.AutoCompareQueueService;

/**
 * 自动比对队列 STR比对 定时任务
 * 
 * @author zhaoyong
 */
@Component
public class AutoCompareStrGeneInfoTask {
	
	@Autowired
	private ISendAutoCompareService compareService;
	
	@Autowired
	private AutoCompareQueueService queueService;
	
	@Value("${compare.auto.enabled}")
    private boolean isEnabled;
	
	@Scheduled(initialDelay = 10000, fixedDelay = 1000 * 60 * 3)
    public void execute() {
		if (isEnabled) {
			// 1.获取需要比对队列的样本 compareQueue 条件: 同一比对 未比对
			List<AutoCompareQueue> compareQueueList = queueService.findListByCompareMode(
					Constants.COMPARE_MODE_STR, Constants.COMPARE_STATUS_NO, 
					AutoCompareConstants.COMPARE_PAGE_SIZE);
			for (AutoCompareQueue compareQueue : compareQueueList) {
				compareService.sendAutoStrCompare(compareQueue);
				
				// 7.更新比对队列
				compareQueue.setCompareStatus(Constants.COMPARE_STATUS_WAITING);
				compareQueue.setUpdateDatetime(LocalDateTime.now());
				queueService.updateById(compareQueue);
			}
			
		}
    }
	
}
