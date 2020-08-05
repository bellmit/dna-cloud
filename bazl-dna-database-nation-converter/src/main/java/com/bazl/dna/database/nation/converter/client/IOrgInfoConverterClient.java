package com.bazl.dna.database.nation.converter.client;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.database.nation.converter.client.fallback.OrgInfoConverterFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 样本基因信息转换
 */
@FeignClient(value = "bazl-dna-system", fallback = OrgInfoConverterFallback.class)
public interface IOrgInfoConverterClient {

	@PostMapping(value = "/org/save")
	public ResponseData orgInfoSave(@RequestBody String json);


}
