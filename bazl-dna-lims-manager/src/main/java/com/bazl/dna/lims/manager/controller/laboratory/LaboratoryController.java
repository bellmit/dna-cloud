package com.bazl.dna.lims.manager.controller.laboratory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bazl.dna.annotation.CurrentUser;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.common.filter.AuthUser;
import com.bazl.dna.lims.manager.constants.ErrorCodes;
import com.bazl.dna.lims.manager.constants.ErrorInfo;
import com.bazl.dna.lims.model.po.LaboratoryInfo;
import com.bazl.dna.lims.service.LaboratoryInfoService;

/**
 * 实验室信息
 * @author zhaoyong
 *
 */
@RestController
@RequestMapping("/laboratory")
public class LaboratoryController {
	
	@Autowired
	private LaboratoryInfoService service;

	@GetMapping("info")
	public ResponseData info(@CurrentUser AuthUser authUser) {
		if (authUser == null) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		
		LaboratoryInfo laboratoryInfo = new LaboratoryInfo();
		laboratoryInfo.setOrgId(authUser.getOrgId());
		LaboratoryInfo result = service.queryByLab(laboratoryInfo);
		return new ResponseData(result);
	}
}
