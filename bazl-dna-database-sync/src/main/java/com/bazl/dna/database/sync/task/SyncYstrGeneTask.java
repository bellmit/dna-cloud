package com.bazl.dna.database.sync.task;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bazl.dna.database.service.service.DnaYstrGeneInfoService;
import com.bazl.dna.database.sync.task.thread.SyncYstrGeneThread;

/**
 * Created by lizhihua on 2020-01-31.
 */
@Component
public class SyncYstrGeneTask {

	private static final Logger LOG = LoggerFactory.getLogger(SyncYstrGeneTask.class);

	@Autowired
	private SyncYstrGeneThread thread;

	@Autowired
	private DnaYstrGeneInfoService geneInfoService;
	
	@Value("${compare.ystr.enabled}")
    private boolean isEnabled;

	@Value("${thread.number}")
    private int threadNumber;
	
	@Value("${thread.size}")
    private int pageSize;

	private int totalPage = 0;
	private int pageIndex = 0;

	/**
	 * 执行任务
	 */
	@Scheduled(initialDelay = 10000, fixedDelay = 1000 * 60 * 1)
	public void execute() throws Exception {

		try {
			if (isEnabled) {
				int total = geneInfoService.count();
				totalPage = total / pageSize + 1;
				LOG.info("total: {}, totalPage:{}", total, totalPage);
				for (int i = 0; i < threadNumber; i++) {
					if (totalPage == pageIndex) {
						break;
					}
					thread.execute(pageIndex, pageSize);
					pageIndex++;
				}
			}
		} catch (Exception e) {
			LOG.error("Error task: ", e);
		}
		LOG.info("执行定时任务结束：{}", new Date());

	}

}
