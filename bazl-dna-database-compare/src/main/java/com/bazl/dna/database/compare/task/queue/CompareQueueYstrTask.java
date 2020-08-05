package com.bazl.dna.database.compare.task.queue;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bazl.dna.database.compare.task.queue.thread.AsyncYstrThread;
import com.bazl.dna.database.service.service.DnaYstrGeneInfoService;

@Component
public class CompareQueueYstrTask {
	
	private static final Logger LOG = LoggerFactory.getLogger(CompareQueueYstrTask.class);
	
	@Autowired
	private DnaYstrGeneInfoService geneInfoService;
	
	@Autowired
	private AsyncYstrThread asyncTask;
	
	@Value("${compare.queue.enabled}")
    private boolean isEnabled;
	
	@Value("${thread.number}")
    private int threadNumber;
	
	@Value("${thread.size}")
    private int pageSize;

	private int totalPage = 0;
	private int pageIndex = 0;
	
	@Scheduled(initialDelay = 10000, fixedDelay = 1000 * 60 * 1)
	public void task() {
		if (isEnabled) {
			LOG.info("执行定时任务开始：{}", new Date());
			try {
				int total = geneInfoService.count();
				totalPage = total / pageSize + 1;
				for (int i = 0; i < threadNumber; i++) {
					if (totalPage == pageIndex) {
						break;
					}
					asyncTask.executeAsyncTask(pageIndex, pageSize);
					pageIndex++;
				}
			} catch (Exception e) {
				LOG.error("Error task: ", e);
			}
			LOG.info("执行定时任务结束：{}", new Date());
		}
	}
	
}
