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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.common.PageInfo;
import com.bazl.dna.common.PublicConstants;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.sys.constants.ErrorCodes;
import com.bazl.dna.sys.constants.ErrorInfo;
import com.bazl.dna.sys.constants.SysConstants;
import com.bazl.dna.sys.entity.SysDictItem;
import com.bazl.dna.sys.entity.SysDictItemType;
import com.bazl.dna.sys.service.ISysDictItemService;
import com.bazl.dna.sys.service.ISysDictItemTypeService;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;

/**
 * 字典信息
 */
@RestController
@RequestMapping("/dict")
public class SysDictItemContrlloer {

	@Autowired
	public ISysDictItemTypeService sysDictItemTypeService;

	@Autowired
	public ISysDictItemService sysDictItemService;

	/**
	 * 字典类别保存
	 * 
	 * @param type
	 * @return
	 */
	@PostMapping("/saveDictItemType")
	public ResponseData saveDictItemType(@RequestBody SysDictItemType type) {
		SysDictItemType sysDictItemType = sysDictItemTypeService.save(type);
		return new ResponseData(sysDictItemType);
	}

	/**
	 * 判断类别id 是否存在
	 * 
	 * @param dataJson
	 * @return
	 */
	@PostMapping("/checkTypeId")
	public ResponseData checkTypeId(@RequestBody JSONObject json) {
		String id = json.getString(SysConstants.ID);
		String code = json.getString(SysConstants.CODE);
		if (Strings.isNullOrEmpty(code)) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		int result = sysDictItemTypeService.checkTypeId(id, code);
		if (result > 0) {
			return new ResponseData(ErrorCodes.ERR_IS_EXISTS, ErrorInfo.ERR_IS_EXISTS);
		}
		return new ResponseData();
	}

	/**
	 * 获取类别
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/getTypeById/{id}")
	public ResponseData getTypeById(@PathVariable String id) {
		if (Strings.isNullOrEmpty(id)) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		SysDictItemType sysDictItemType = sysDictItemTypeService.getById(id);
		return new ResponseData(sysDictItemType);
	}

	/**
	 * 获取类别中字典列表
	 * 
	 * @param typeId
	 * @return
	 */
	@GetMapping("/findTypeInfoById/{typeId}")
	public ResponseData findTypeInfoById(@PathVariable String typeId) {
		if (Strings.isNullOrEmpty(typeId)) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		List<SysDictItem> itemList = sysDictItemService.findTypeInfoById(typeId);
		Map<String, Object> result = Maps.newHashMap();
		result.put(PublicConstants.PARAM_PAGE, new PageInfo(1, itemList.size(), 1));
		result.put(PublicConstants.PARAM_LIST, itemList);
		return new ResponseData(result);
	}

	/**
	 * 获取类别字典信息
	 * 
	 * @param paramJson
	 * @return
	 */
	@PostMapping("/findTypeInfoByCodes")
	public ResponseData findTypeInfoByCodes(@RequestBody JSONObject paramJson) {
		JSONArray array = paramJson.getJSONArray("codes");
		if (array.isEmpty()) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		Map<String, Object> result = Maps.newHashMap();
		array.stream().forEach( str -> {
			List<SysDictItem> itemList = sysDictItemService.findTypeInfoById(str.toString());
			result.put(str.toString(), itemList);
		});
		return new ResponseData(result);
	}

	/**
	 * 获取类别列表
	 * 
	 * @param paramJson
	 * @return
	 */
	@PostMapping(value = "/findTypeList")
	public ResponseData findTypeList(@RequestBody JSONObject paramJson) {
		Page<SysDictItemType> page = sysDictItemTypeService.findList(paramJson);
		if (page != null) {
			List<SysDictItemType> typeList = page.getContent();
			Map<String, Object> result = Maps.newHashMap();
			result.put(PublicConstants.PARAM_PAGE,
					new PageInfo(page.getNumber() + 1, page.getTotalElements(), page.getTotalPages()));
			result.put(PublicConstants.PARAM_LIST, typeList);
			return new ResponseData(result);
		}
		return new ResponseData();
	}

	/**
	 * 保存字典信息
	 * 
	 * @param item
	 * @return
	 */
	@PostMapping("/saveDictItem")
	public ResponseData saveDictItem(@RequestBody SysDictItem item) {
		SysDictItem sysDictItem = sysDictItemService.save(item);
		return new ResponseData(sysDictItem);
	}

	/**
	 * 获取字典信息
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/getItemById/{id}")
	public ResponseData getItemById(@PathVariable String id) {
		if (Strings.isNullOrEmpty(id)) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		SysDictItem sysDictItem = sysDictItemService.getById(id);
		return new ResponseData(sysDictItem);
	}

	/**
	 * 删除字典信息
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/deleteItemById/{id}")
	public ResponseData deleteItemById(@PathVariable String id) {
		if (Strings.isNullOrEmpty(id)) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		int result = sysDictItemService.deleteById(id);
		return new ResponseData(result);
	}

	/**
	 * 删除字典类别
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/deleteTypeById/{id}")
	public ResponseData deleteTypeById(@PathVariable String id) {
		if (Strings.isNullOrEmpty(id)) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		int result = sysDictItemTypeService.deleteById(id);
		return new ResponseData(result);
	}

	/**
	 * 获取类别列表
	 * 
	 * @param json
	 * @return
	 */
	@PostMapping("/findListByCodes")
	public ResponseData findListByCode(@RequestBody JSONObject json) {
		List<SysDictItemType> list = sysDictItemTypeService.findListByCodes(json);
		Map<String, Object> result = Maps.newHashMap();
		result.put(PublicConstants.PARAM_LIST, list);
		return new ResponseData(result);
	}
}
