package com.bazl.dna.lims.blood.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bazl.dna.common.ResponseData;

@RestController
@RequestMapping("/api")
public class ApiController {

	@GetMapping("test")
	public ResponseData test(String data) {
		System.out.println(data);
		return new ResponseData(data);
	}

}
