package com.bazl.dna.lims.stats.controller.data;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.lims.model.po.LaboratoryInfo;
import com.bazl.dna.lims.service.LaboratoryInfoService;
import com.bazl.dna.lims.stats.controller.BaseController;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 环境监控
 */
@RestController
@RequestMapping("/stats/environmentMonitor")
public class StatsEnvironmentMonitorController extends BaseController {
	
	@Autowired
	private LaboratoryInfoService service;

	/**
	 * 查找未监控和已监控列表
	 * @param monitorType
	 * @return
	 */
	@GetMapping("findList")
	public ResponseData findList() {
		List<LaboratoryInfo> laboratoryInfoList = service.selectAll();
		
		List<Map<String, Object>> monitorList = Lists.newArrayList();
		List<Map<String, Object>> monitorNotList = Lists.newArrayList();
		laboratoryInfoList.stream().forEach(info ->{
			if (info.getMonitorType() == 1) {
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", info.getId());
				map.put("name", info.getDnaLabName());
				monitorList.add(map);
			} else {
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", info.getId());
				map.put("name", info.getDnaLabName());
				monitorNotList.add(map);
			}
		});
		
		Map<String, Object> result = Maps.newHashMap();
		result.put("monitorList", monitorList);
		result.put("monitorNotList", monitorNotList);
		return new ResponseData(result);
	}
	
	/**
	 * 获取指定实验室信息
	 * @param id
	 * @return
	 */
	@GetMapping("getLabId")
	public ResponseData getLabId(String id) {
		LaboratoryInfo result = service.queryById(id);
		return new ResponseData(result);
	}
}
