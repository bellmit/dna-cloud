package com.bazl.dna.lims.manager.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.lims.manager.constants.ErrorCodes;
import com.bazl.dna.lims.manager.constants.ErrorInfo;

/**
 * 外部接口
 */
@RestController
@RequestMapping("/api")
public class ApiController {
	
	@GetMapping("test")
	public ResponseData test(String data) {
		if (data == null) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		System.out.println(data);
		return new ResponseData(data);
	}

}
