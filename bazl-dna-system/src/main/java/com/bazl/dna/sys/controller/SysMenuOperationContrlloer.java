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
import com.bazl.dna.sys.constants.SysConstants;
import com.bazl.dna.sys.entity.SysMenuOperation;
import com.bazl.dna.sys.service.ISysMenuOperationService;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;

/**
 * 菜单操作权限
 * @author zhaoyong
 *
 */
@RestController
@RequestMapping("/menu/operation")
public class SysMenuOperationContrlloer {
	
	@Autowired
	public ISysMenuOperationService menuOperationService;
	
	/**
	 * 保存
	 * @param menuOperation
	 * @return
	 */
	@PostMapping("/save")
    public ResponseData save(@RequestBody SysMenuOperation menuOperation){
		SysMenuOperation result = menuOperationService.save(menuOperation);
		return new ResponseData(result);
    }
	
	/**
	 * 获取信息
	 * @param id
	 * @return
	 */
	@GetMapping("/get/{id}")
    public ResponseData getById(@PathVariable String id) {
		if(Strings.isNullOrEmpty(id)){
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
        SysMenuOperation result = menuOperationService.getInfoById(id);
        return new ResponseData(result);
    }
	
	/**
	 * 列表
	 * @param paramJson
	 * @return
	 */
	@PostMapping(value = "/list")
	public ResponseData findList(@RequestBody JSONObject paramJson){
	    Page<SysMenuOperation> page = menuOperationService.findList(paramJson);
	    if (page != null) {
	    	List<SysMenuOperation> typeList = page.getContent();
			Map<String, Object> result = Maps.newHashMap();
			result.put(PublicConstants.PARAM_PAGE, new PageInfo(page.getNumber() + 1, page.getTotalElements(), page.getTotalPages()));
			result.put(PublicConstants.PARAM_LIST, typeList);
			return new ResponseData(result);
	    }
	    return new ResponseData();
	}

	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@GetMapping("/delete/{id}")
    public ResponseData delete(@PathVariable String id) {
		if(Strings.isNullOrEmpty(id)){
			return new ResponseData(ErrorCodes.ERR_PARAM,ErrorInfo.ERR_PARAM);
		}
        int result = menuOperationService.deleteById(id);
        return new ResponseData(result);
    }
	
	/**
	 * 判断是否存在
	 * @param dataJson
	 * @return
	 */
	@PostMapping("/checkType")
    public ResponseData checkType(@RequestBody JSONObject json) {
		String id = json.getString(SysConstants.ID);
		String type = json.getString(SysConstants.CODE);
		if(Strings.isNullOrEmpty(type)){
			return new ResponseData(ErrorCodes.ERR_PARAM,ErrorInfo.ERR_PARAM);
		}
        int result = menuOperationService.checkType(id,type);
        if (result > 0) {
        	return new ResponseData(ErrorCodes.ERR_IS_EXISTS, ErrorInfo.ERR_IS_EXISTS);
        }
        return new ResponseData();
    }
}
