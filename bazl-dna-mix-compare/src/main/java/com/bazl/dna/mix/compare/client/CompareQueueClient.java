package com.bazl.dna.mix.compare.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.mix.compare.client.fallback.CompareQueueClientFallback;

@FeignClient(value = "bazl-dna-mix", fallback = CompareQueueClientFallback.class)
public interface CompareQueueClient {

	@PostMapping(value = "/CompareQueue/updateStatus")
	ResponseData updateStatus(@RequestParam("Id") String Id,@RequestParam("Status") String Status,@RequestParam("targetCount") String targetCount);
	@PostMapping(value = "/CompareQueue/updateCompareQueue")
	ResponseData updateByPrimaryKey(@RequestParam("Id") String id, @RequestParam("argetCount") String argetCount);
	@RequestMapping(value = "/CompareQueue/contributorGene")
	ResponseData contributorGene(@RequestParam("MixedSampleId") String MixedSampleId);
	@RequestMapping(value = "/CompareQueue/getMixSampleInfo")
	ResponseData getMixSampleInfo(@RequestParam("MixedSampleId") String MixedSampleId);

	@PostMapping(value = "/CompareQueue/selectByMatch")
	ResponseData selectByMatch(@RequestParam("CompareId") String CompareId, @RequestParam("SingleGeneId") String SingleGeneId, @RequestParam("ResultType") String ResultType);
	@PostMapping(value = "/CompareQueue/updateByPrimaryKey")
	ResponseData updateMatchResult(@RequestParam("matchResult") String matchResult);
	@PostMapping(value = "/CompareQueue/insert")
	ResponseData insert(@RequestParam("match") String match);
	@PostMapping(value = "/CompareQueue/insertMobile")
	ResponseData insertMobile(@RequestParam("mobileNews") String mobileNews);

	@RequestMapping(value = "/CompareQueue/selectByCompareQueueId")
	ResponseData selectByCompareQueueId(@RequestParam("Id") String Id);

}
