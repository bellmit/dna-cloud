package com.bazl.dna.database.nation.converter.client;

import com.bazl.dna.database.service.model.po.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.common.filter.FeignSupportConfig;
import com.bazl.dna.database.nation.converter.client.fallback.CaseConverterFallback;

/**
 * 案件信息转换
 */
@FeignClient(value = "bazl-dna-database-core", fallback = CaseConverterFallback.class,
		configuration = FeignSupportConfig.class)
public interface ICaseConverterClient {

	@PostMapping(value = "/converter/caseinfo/case/save")
	public ResponseData caseInfoSave(@RequestBody CaseInfo caseInfo);
	
	@PostMapping(value = "/converter/caseinfo/case/saveImage")
	public ResponseData caseInfoSaveImage(@RequestBody CaseImage caseImage);
	
	@PostMapping(value = "/converter/caseinfo/person/save")
	public ResponseData casePersonInfoSave(@RequestBody CasePersonInfo casePersonInfo);

	@PostMapping(value = "/converter/caseinfo/person/savePerson")
	public ResponseData savePerson(@RequestBody PersonRelativeInfo personRelativeInfo);
	
	@PostMapping(value = "/converter/caseinfo/consignment/save")
	public ResponseData consignmentInfoSave(@RequestBody ConsignmentInfo consignmentInfo);
}
