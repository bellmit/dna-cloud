package com.bazl.dna.database.core.controller.converter.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.database.core.constants.ErrorCodes;
import com.bazl.dna.database.core.constants.ErrorInfo;
import com.bazl.dna.database.core.controller.BaseController;
import com.bazl.dna.database.service.model.po.QcSampleInfo;
import com.bazl.dna.database.service.model.vo.ConverterDataVo;
import com.bazl.dna.database.service.service.ConverterDataService;

@RestController
@RequestMapping("/converter/data")
public class ConverterDataController extends BaseController {
	
	@Autowired
	private ConverterDataService service;

	@PostMapping("saveCase")
	public ResponseData saveCase(@RequestBody ConverterDataVo vo) {
		try {
			if (vo == null) {
				return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM); 
			}
			int result = service.saveCase(vo, false);
			return new ResponseData(result);
		} catch (Exception e) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
	}
	
	@PostMapping("saveCriminal")
	public ResponseData saveCriminal(@RequestBody ConverterDataVo vo) {
		try {
			if (vo == null) {
				return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM); 
			}
			int result = service.saveCriminal(vo);
			return new ResponseData(result);
		} catch (Exception e) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
	}
	
	@PostMapping("saveQcSample")
	public ResponseData saveQcSample(@RequestBody QcSampleInfo entity) {
		try {
			if (entity == null) {
				return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM); 
			}
			int result = service.saveQcSample(entity);
			return new ResponseData(result);
		} catch (Exception e) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
	} 
}
