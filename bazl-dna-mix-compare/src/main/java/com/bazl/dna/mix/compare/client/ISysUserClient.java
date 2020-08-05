package com.bazl.dna.mix.compare.client;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.mix.compare.client.fallback.SysUserClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "bazl-dna-system", fallback = SysUserClientFallback.class)
public interface ISysUserClient {

	@GetMapping(value= "/user/selectByPrimaryKey")
	public ResponseData selectByPrimaryKey(@RequestParam("id") String id);
}

