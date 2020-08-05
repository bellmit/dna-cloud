package com.bazl.dna.database.nation.converter.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.common.filter.FeignSupportConfig;
import com.bazl.dna.database.nation.converter.client.fallback.DataConverterFallback;
import com.bazl.dna.database.service.model.po.QcSampleInfo;
import com.bazl.dna.database.service.model.vo.ConverterDataVo;

/**
 * 信息转换
 */
@FeignClient(value = "bazl-dna-database-core", fallback = DataConverterFallback.class,
		configuration = FeignSupportConfig.class)
public interface IDataConverterClient {

	@PostMapping(value = "/converter/data/saveCase")
	public ResponseData saveCase(@RequestBody ConverterDataVo vo);
	
	@PostMapping(value = "/converter/data/saveCriminal")
	public ResponseData saveCriminal(@RequestBody ConverterDataVo vo);
	
	@PostMapping(value = "/converter/data/saveQcSample")
	public ResponseData saveQcSample(@RequestBody QcSampleInfo entity);
	
}
