package com.bazl.dna.database.sync.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.database.sync.client.fallback.GeneListServerClientFallback;

import java.util.Date;

@FeignClient(value = "bazl-dna-nation-connector", fallback = GeneListServerClientFallback.class)
public interface GeneListServerClient {

	@RequestMapping(value = "/nationalTreasury/selectSingleGeneByCwsdPage")
	ResponseData selectSingleGeneByCwsdPage(@RequestParam("page") String page,@RequestParam("pageSize") int pageSize);

	@RequestMapping(value = "/nationalTreasury/selectSingleGeneByCwsd")
	ResponseData selectSingleGeneByCwsd();

	@RequestMapping(value = "/nationalTreasury/selectMixGeneByCwsd")
	ResponseData selectMixGeneByCwsd();

	@RequestMapping(value = "/nationalTreasury/selectByLocusName")
	ResponseData selectByLocusName();

	@RequestMapping(value = "/nationalTreasury/selectCountByDate")
	ResponseData selectCountByDate(@RequestParam("date") Date date);

	@RequestMapping(value = "/nationalTreasury/selectByDateSingList")
	ResponseData selectByDateSingList(@RequestParam("page") String page,@RequestParam("pageSize") int pageSize, @RequestParam("date") Date date);

}
