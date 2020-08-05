package com.bazl.dna.sys.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.bazl.dna.sys.entity.DataSourceConfig;
import com.bazl.dna.sys.service.IDataSourceConfigService;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;

/**
 * 数据源
 * @author zhaoyong
 *
 */
@RestController
@RequestMapping("/datasource")
public class DataSourceConfigController {

	@Autowired
	private IDataSourceConfigService service;
	
	/**
	 * 列表
	 */
	@PostMapping("/list")
	public ResponseData findList(@RequestBody JSONObject json) {
		Page<DataSourceConfig> page = service.findList(json);
		if (page != null) {
			List<DataSourceConfig> list = page.getContent();
			Map<String, Object> result = Maps.newHashMap();
			result.put(PublicConstants.PARAM_PAGE,
					new PageInfo(page.getNumber() + 1, page.getTotalElements(), page.getTotalPages()));
			result.put(PublicConstants.PARAM_LIST, list);
			return new ResponseData(result);
		}
		return new ResponseData();
	}
	
	/**
	 * 按id获取信息
	 */
	@GetMapping("/get/{id}")
	public ResponseData get(@PathVariable String id) {
		if (id == null) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		DataSourceConfig result = service.getById(id);
		return new ResponseData(result);
	}
	
	/**
	 * 按 connectName 获取信息
	 */
	@GetMapping("/getConnectName")
	public ResponseData getConnectName(String connectName) {
		if (connectName == null) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		DataSourceConfig result = service.getConnectName(connectName);
		return new ResponseData(result);
	}
	
	/**
	 * 保存
	 */
	@PostMapping("/save")
	public ResponseData save(@RequestBody DataSourceConfig entity) {
		DataSourceConfig result = service.save(entity);
		return new ResponseData(result);
	}
	
	/**
	 * 删除
	 */
	@DeleteMapping("/delete/{id}")
	public ResponseData delete(@PathVariable String id) {
		if (Strings.isNullOrEmpty(id)) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		int result = service.deleteById(id);
		return new ResponseData(result);
	}
}
