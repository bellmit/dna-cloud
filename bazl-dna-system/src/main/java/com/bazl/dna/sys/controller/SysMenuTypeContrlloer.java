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
import com.bazl.dna.sys.entity.SysMenuType;
import com.bazl.dna.sys.service.ISysMenuTypeService;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;

/**
 * 菜单类型管理
 * @author zhaoyong
 *
 */
@RestController
@RequestMapping("/menu/type")
public class SysMenuTypeContrlloer {
	
	@Autowired
	public ISysMenuTypeService menuTypeService;
	
	/**
	 * 保存
	 * @param menuType
	 * @return
	 */
	@PostMapping("/save")
    public ResponseData save(@RequestBody SysMenuType menuType){
		SysMenuType result = menuTypeService.save(menuType);
		return new ResponseData(result);
    }
	
	/**
	 * 获取信息
	 * @param id
	 * @return
	 */
	@GetMapping("/get/{id}")
    public ResponseData get(@PathVariable String id) {
		if(Strings.isNullOrEmpty(id)){
			return new ResponseData(ErrorCodes.ERR_PARAM,ErrorInfo.ERR_PARAM);
		}
        SysMenuType result = menuTypeService.getById(id);
        return new ResponseData(result);
    }
	
	/**
	 * 列表
	 * @param paramJson
	 * @return
	 */
	@PostMapping(value = "/list")
	public ResponseData list(@RequestBody JSONObject paramJson){
	    Page<SysMenuType> page = menuTypeService.findList(paramJson);
	    if (page != null) {
	    	List<SysMenuType> typeList = page.getContent();
			Map<String, Object> result = Maps.newHashMap();
			result.put(PublicConstants.PARAM_PAGE, new PageInfo(page.getNumber() + 1, page.getTotalElements(), page.getTotalPages()));
			result.put(PublicConstants.PARAM_LIST, typeList);
			return new ResponseData(result);
	    }
	    return new ResponseData();
	}

	/**
	 * 修改状态
	 * @param dataJson
	 * @return
	 */
	@PostMapping("/editStatusById")
    public ResponseData editStatusById(@RequestBody JSONObject json) {
		String status = json.getString(SysConstants.STATUS);
		if(Strings.isNullOrEmpty(status)){
			return new ResponseData(ErrorCodes.ERR_PARAM,ErrorInfo.ERR_PARAM);
		}
		String id = json.getString(SysConstants.ID);
		if(Strings.isNullOrEmpty(id)){
			return new ResponseData(ErrorCodes.ERR_PARAM,ErrorInfo.ERR_PARAM);
		}
        int result = menuTypeService.editStatusById(id,status);
        return new ResponseData(result);
    }
	
	/**
	 * 判断是否存在
	 * @param dataJson
	 * @return
	 */
	@PostMapping("/checkCode")
    public ResponseData checkCode(@RequestBody JSONObject json) {
		String id = json.getString(SysConstants.ID);
		String code = json.getString(SysConstants.CODE);
		if(Strings.isNullOrEmpty(code)){
			return new ResponseData(ErrorCodes.ERR_PARAM,ErrorInfo.ERR_PARAM);
		}
        int result = menuTypeService.checkCode(id,code);
        if (result > 0) {
        	return new ResponseData(ErrorCodes.ERR_IS_EXISTS, ErrorInfo.ERR_IS_EXISTS);
        }
        return new ResponseData();
    }
}
