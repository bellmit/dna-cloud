package com.bazl.dna.database.core.controller.converter.criminal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.database.core.constants.ErrorCodes;
import com.bazl.dna.database.core.constants.ErrorInfo;
import com.bazl.dna.database.core.controller.BaseController;
import com.bazl.dna.database.service.model.po.CriminalSampleInfo;
import com.bazl.dna.database.service.service.CriminalSampleInfoService;

/**
 * 违法犯罪人员样本信息转换
 */
@RestController
@RequestMapping("/converter/criminal/sample")
public class CriminalSampleInfoConverter extends BaseController {

	@Autowired
	private CriminalSampleInfoService service;
	
	/**
	 * 保存
	 */
	@PostMapping("save")
	public ResponseData save(@RequestBody CriminalSampleInfo info) {
		if (info == null) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		try {
			service.save(info);
			return new ResponseData(info);
		}catch (Exception e){
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
	}
}
