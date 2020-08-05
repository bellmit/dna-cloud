package com.bazl.dna.database.sync.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.common.PublicConstants;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.database.sync.client.GeneListServerClient;
import com.bazl.dna.database.sync.task.thread.SyncMixGeneThread;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component
public class SyncMixGeneTask {

	private static final Logger LOG = LoggerFactory.getLogger(SyncMixGeneTask.class);

	@Autowired
	private SyncMixGeneThread thread;

	@Autowired
	private GeneListServerClient geneListServerClient;
	
	@Value("${compare.mix.enabled}")
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
				List<Map<String, String>> listLocusName = new ArrayList<Map<String, String>>();
				ResponseData locusInfo = geneListServerClient.selectByLocusName();
				if (PublicConstants.SUCCESS_CODE == locusInfo.getCode()) {
					List<Map<String, String>> llocus = new Gson().fromJson(locusInfo.getResult().toString(),
							new TypeToken<List<Map<String, String>>>() {}.getType());
					for (Map<String, String> object : llocus) {
						listLocusName.add(object);
					}
				}
				ResponseData singleGeneData = geneListServerClient.selectSingleGeneByCwsd();
				if (singleGeneData != null && singleGeneData.getCode() == PublicConstants.SUCCESS_CODE) {
					JSONObject singleJson = JSONObject.parseObject(JSONObject.toJSONString(singleGeneData.getResult()));
					int total = singleJson.getIntValue("singleGeneCount");
					totalPage = total / pageSize + 1;
					LOG.info("total: {}, totalPage:{}", total, totalPage);
					for (int i = 0; i < threadNumber; i++) {
						if (totalPage == pageIndex) {
							break;
						}
						thread.execute(listLocusName, pageIndex, pageSize);
						pageIndex++;
					}
				}
			}
		} catch (Exception e) {
			LOG.error("Error task: ", e);
		}
		LOG.info("执行定时任务结束：{}", new Date());

	}
}
