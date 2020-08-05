package com.bazl.dna.mix.compare.client;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.mix.compare.client.fallback.GeneListServerClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "bazl-dna-nation-connector", fallback = GeneListServerClientFallback.class)
public interface GeneListServerClient {

	@RequestMapping(value = "/nationalTreasury/selectSingleGeneByCwsdPage")
	ResponseData selectSingleGeneByCwsdPage(@RequestParam("page") String page,@RequestParam("pageSize") int pageSize);

	@RequestMapping(value = "/nationalTreasury/selectSingleGeneByCwsd")
	ResponseData selectSingleGeneByCwsd();

	@RequestMapping(value = "/nationalTreasury/selectByLocusName")
	ResponseData selectByLocusName();

	@RequestMapping(value = "/nationalTreasury/getCaseInfoSampleNo")
	ResponseData getCaseInfoSampleNo(@RequestParam("sampleNo") String sampleNo,@RequestParam("datatype") String datatype, @RequestParam("personCodeType") String personCodeType);

	@GetMapping(value = "/nationalTreasury/selectSingleGeneBySampleNo")
	ResponseData selectSingleGeneBySampleNo(@RequestParam("sampleNo") String sampleNo);

	@RequestMapping(value = "/nationalTreasury/updateSampleDnaGeneDF/{sampleGeneId}")
	ResponseData updateSampleDnaGeneDF(@PathVariable("sampleGeneId") String sampleGeneId);

	@RequestMapping(value = "/queryMixedSampleGenes")
	ResponseData queryMixedSampleGenes(@RequestParam("page") String page, @RequestParam("caseNo") String caseNo,
                                       @RequestParam("caseName") String caseName, @RequestParam("sampleNo") String sampleNo, @RequestParam("sampleName") String sampleName);
	
	@RequestMapping(value = "/nationalTreasury/selectMixGeneByCwsd")
	ResponseData selectMixGeneByCwsd();
	
}
