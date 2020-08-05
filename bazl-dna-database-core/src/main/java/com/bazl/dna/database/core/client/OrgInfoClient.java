package com.bazl.dna.database.core.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.database.core.client.fallback.OrgInfoClientFallback;

@FeignClient(value = "bazl-dna-system", fallback = OrgInfoClientFallback.class)
public interface OrgInfoClient {

	@GetMapping(value = "/org/findListByParentId")
	public ResponseData findListByParentId(@RequestParam("parentId") String parentId);

	@GetMapping(value = "/org/get/{id}")
	public ResponseData getById(@PathVariable("id") String id);
}
