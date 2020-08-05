package com.bazl.dna.database.nation.converter.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.common.filter.FeignSupportConfig;
import com.bazl.dna.database.nation.converter.client.fallback.CriminalConverterFallback;
import com.bazl.dna.database.service.model.po.CriminalPersonInfo;
import com.bazl.dna.database.service.model.po.CriminalSampleInfo;

/**
 * 违法犯罪人员信息转换
 */
@FeignClient(value = "bazl-dna-database-core", fallback = CriminalConverterFallback.class, 
	configuration = FeignSupportConfig.class)
public interface ICriminalConverterClient {

	@PostMapping(value = "/converter/criminal/person/save")
	public ResponseData personInfoSave(@RequestBody CriminalPersonInfo info);

	@PostMapping(value = "/converter/criminal/sample/save")
	public ResponseData sampleInfoSave(@RequestBody CriminalSampleInfo info);
}
