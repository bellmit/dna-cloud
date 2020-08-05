package com.bazl.dna.lims.connector.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.lims.connector.model.po.LimsCaseInfo;
import com.bazl.dna.lims.connector.service.LimsCaseInfoService;

@RestController
@RequestMapping("/test")
public class TestController {
	
	@Autowired
	private LimsCaseInfoService service;

	@GetMapping("list")
	public ResponseData list(String caseId) {
		LimsCaseInfo result = service.selectByCaseId(caseId);
		return new ResponseData(result);
	}

}
