package com.bazl.dna.test.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.common.filter.FeignSupportConfig;
import com.bazl.dna.test.client.fallback.UserServiceFallback;

@FeignClient(value = "bazl-dna-system", fallback = UserServiceFallback.class,
		configuration = FeignSupportConfig.class)
public interface IUserServiceClient {
	
	@PostMapping(value = "/auth/login")
	ResponseData login(String json);

	@GetMapping(value = "/dict/getTypeById/{id}")
	ResponseData getTypeById(@PathVariable("id") String id);
	
	@ResponseBody
	@PostMapping(value = "/dict/findTypeList")
	public ResponseData findTypeList(@RequestBody JSONObject paramJson);
}
