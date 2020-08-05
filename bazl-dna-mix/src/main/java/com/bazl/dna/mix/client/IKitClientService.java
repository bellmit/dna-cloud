package com.bazl.dna.mix.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.mix.client.fallback.KitClientServiceFallback;

@FeignClient(value = "bazl-dna-nation-connector", fallback = KitClientServiceFallback.class)
public interface IKitClientService {
	
	@GetMapping(value= "/nationalTreasury/selectKitName")
	ResponseData selectKitNameList();

	@GetMapping(value= "/nationalTreasury/selectByKitIdToLocusID/{reagentKitId}")
	ResponseData selectByKitId(@PathVariable("reagentKitId") String reagentKitId);

	@GetMapping(value= "/nationalTreasury/queryLocusNameVague")
	ResponseData queryLocusNameVague(@RequestParam("locusName") String locusName);
	
	@GetMapping(value = "/nationalTreasury/selectProvinceAndCity")
	ResponseData selectProvinceAndCity(@RequestParam("code") String code);

	@GetMapping(value = "/nationalTreasury/selectByKitNameToLocusName")
	ResponseData selectByKitNameToLocusName(@RequestParam("reagentKitName") String reagentKitName);
}
