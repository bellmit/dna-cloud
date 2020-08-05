package com.bazl.dna.database.compare.task;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bazl.dna.database.service.service.DnaMixGeneInfoService;
import com.bazl.dna.database.service.service.DnaStrGeneInfoService;
import com.bazl.dna.database.service.service.DnaYstrGeneInfoService;

@Component
public class QueueTask {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(QueueTask.class);
	
	@Autowired
	private DnaStrGeneInfoService strGeneInfoService;
	
	@Autowired
	private DnaYstrGeneInfoService ystrGeneInfoService;
	
	@Autowired
	private DnaMixGeneInfoService mixGeneInfoService;

	@Scheduled(cron = "0 0 0 */1 * ? ")
    public void execute() {
		LOGGER.info("Clean virtual index: {}", new Date());
		
		deleteStrVirtualIndex();
		deleteYstrVirtualIndex();
		deleteMixVirtualIndex();
	}
	
	@Async
	public void deleteStrVirtualIndex() {
		List<String> strList = strGeneInfoService.findVirtualIndexList();
		for (String columnName : strList) {
			strGeneInfoService.deleteVirtualIndex(columnName);
		}
	}
	
	@Async
	public void deleteYstrVirtualIndex() {
		List<String> ystrList = ystrGeneInfoService.findVirtualIndexList();
		for (String columnName : ystrList) {
			ystrGeneInfoService.deleteVirtualIndex(columnName);
		}
	}
	
	@Async
	public void deleteMixVirtualIndex() {
		List<String> mixList = mixGeneInfoService.findVirtualIndexList();
		for (String columnName : mixList) {
			mixGeneInfoService.deleteVirtualIndex(columnName);
		}
	}
	
	
	
}
