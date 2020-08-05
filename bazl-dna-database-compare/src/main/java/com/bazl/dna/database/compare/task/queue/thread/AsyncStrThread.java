package com.bazl.dna.database.compare.task.queue.thread;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.database.constants.Constants;
import com.bazl.dna.database.service.model.po.AutoCompareQueue;
import com.bazl.dna.database.service.service.AutoCompareQueueService;
import com.bazl.dna.database.service.service.DnaStrGeneInfoService;
import com.google.common.collect.Lists;

@Component
public class AsyncStrThread {
	
	private static final Logger LOG = LoggerFactory.getLogger(AsyncStrThread.class);
	
	@Autowired
	private DnaStrGeneInfoService geneInfoService;
	
	@Autowired
	private AutoCompareQueueService queueService;

	@Async
    public void executeAsyncTask(int pageIndex, int pageSize) {
		Pageable pageable = PageRequest.of(pageIndex, pageSize);
		LOG.info("Thread: {}, limit:{},{}", Thread.currentThread().getName(), pageable.getOffset(), pageable.getPageSize());
		
		List<Map<String, Object>> list = geneInfoService.findList(pageable.getOffset(), pageable.getPageSize());
		
		List<AutoCompareQueue> entityList = Lists.newArrayList();
		for (Map<String, Object> map : list) {
			AutoCompareQueue queue = new AutoCompareQueue();
			queue.setCompareMode(Constants.COMPARE_MODE_STR);
			JSONObject compareParams = new JSONObject();
			compareParams.put("mostDiffLimit", 2);
			compareParams.put("targetDataType", Lists.newArrayList());
			compareParams.put("lowestSameLimit", 13);
			compareParams.put("targetLabServerNo", Lists.newArrayList());
			queue.setCompareParams(compareParams.toJSONString());
			queue.setCompareStatus(Constants.COMPARE_STATUS_FINISH);
			queue.setCompareWeight(2);
			queue.setCreateDatetime(LocalDateTime.now());
			queue.setGeneId(Integer.parseInt(map.get("id").toString()));
			queue.setPopulationFrequencyId(1);
			entityList.add(queue);
		}
		queueService.saveBatch(entityList);
    }
}
