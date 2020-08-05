package com.bazl.dna.mix.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.mix.client.fallback.CaseInfoServerClientFallback;

@FeignClient(value = "bazl-dna-nation-connector", fallback = CaseInfoServerClientFallback.class)
public interface CaseInfoServerClient {

	@GetMapping(value = "/nationalTreasury/getCaseInfoByCaseNo")
	ResponseData getCaseInfoByCaseNo(@RequestParam("caseNo") String caseNo);

	@GetMapping(value = "/nationalTreasury/selectByCaseId")
    ResponseData selectByCaseId(@RequestParam("caseId") String caseId);

	@GetMapping(value = "/nationalTreasury/selectBySampleId")
    ResponseData selectBySampleId(@RequestParam("sampleId") String sampleId);
}
