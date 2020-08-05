package com.bazl.dna.sys.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.common.PageInfo;
import com.bazl.dna.common.PublicConstants;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.sys.constants.ErrorCodes;
import com.bazl.dna.sys.constants.ErrorInfo;
import com.bazl.dna.sys.entity.SysJob;
import com.bazl.dna.sys.service.ISysJobService;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;

/**
 * 用户职位信息
 */
@RestController
@RequestMapping("/job")
public class SysJobContrlloer {

	@Autowired
	public ISysJobService sysJobService;

	/**
	 * 用户职位保存
	 * @param job
	 * @return
	 */
	@PostMapping("/save")
	public ResponseData save(@RequestBody SysJob job) {
		SysJob sysJob = sysJobService.saveJob(job);
		return new ResponseData(sysJob);
	}

	/**
	 * 获取用户职位
	 * @param id
	 * @return
	 */
	@GetMapping("/get/{id}")
	public ResponseData get(@PathVariable String id) {
		if (Strings.isNullOrEmpty(id)) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		SysJob sysJob = sysJobService.getJobInfoById(id);
		return new ResponseData(sysJob);
	}

	/**
	 * 用户职位列表
	 * @param paramJson
	 * @return
	 */
	@PostMapping("/list")
	public ResponseData list(@RequestBody JSONObject paramJson) {
		Page<SysJob> page = sysJobService.findJobList(paramJson);
		if (page != null) {
			List<SysJob> list = page.getContent();
			Map<String, Object> result = Maps.newHashMap();
			result.put(PublicConstants.PARAM_PAGE, new PageInfo(page.getNumber() + 1, page.getTotalElements(), page.getTotalPages()));
			result.put(PublicConstants.PARAM_LIST, list);
			return new ResponseData(result);
		}
		return new ResponseData();
	}

	/**
	 * 删除用户职位
	 * @param id
	 * @return
	 */
	@GetMapping("/delete/{id}")
	public ResponseData delete(@PathVariable String id) {
		if (Strings.isNullOrEmpty(id)) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		int result = sysJobService.deleteById(id);
		return new ResponseData(result);
	}
}
