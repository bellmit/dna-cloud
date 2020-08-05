package com.bazl.dna.database.core.controller.converter.caseinfo;

import com.bazl.dna.database.service.model.po.PersonRelativeInfo;
import com.bazl.dna.database.service.service.PersonRelationInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.database.core.constants.ErrorCodes;
import com.bazl.dna.database.core.constants.ErrorInfo;
import com.bazl.dna.database.core.controller.BaseController;
import com.bazl.dna.database.service.model.po.CasePersonInfo;
import com.bazl.dna.database.service.service.CasePersonInfoService;

/**
 * 案件人员信息
 */
@RestController
@RequestMapping("/converter/caseinfo/person")
public class CasePersonInfoConverter extends BaseController {
	
	@Autowired
	private CasePersonInfoService casePersonInfoService;
	@Autowired
	private PersonRelationInfoService personRelationInfoService;

	/**
	 * 案件人员信息
	 * @param casePersonInfo
	 * @return
	 */
	@PostMapping("save")
	public ResponseData save(@RequestBody CasePersonInfo casePersonInfo) {
		logger.info("invoking /converter/caseinfo/person/save....");

		if (casePersonInfo == null) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}

		try {
			casePersonInfoService.save(casePersonInfo);
			return new ResponseData(casePersonInfo);
		}catch (Exception e){
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
	}

	/**
	 * 人员信息关系表
	 */
	@PostMapping("savePerson")
	public ResponseData savePerson(@RequestBody PersonRelativeInfo personRelativeInfo) {
		if (personRelativeInfo == null) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		try {
			personRelationInfoService.save(personRelativeInfo);
			return new ResponseData(personRelativeInfo);
		}catch (Exception e){
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
	}
}
