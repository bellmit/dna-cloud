package com.bazl.dna.mix.client;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.mix.client.fallback.LimsServerClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "bazl-dna-lims-connector", fallback = LimsServerClientFallback.class)
public interface LimsServerClient {

    @GetMapping(value = "/mixConnector/caseInfoAndSampelInfo")
    ResponseData getCaseInfoByCaseId(@RequestParam("caseId") String caseId);

    @RequestMapping(value = "/mixConnector/findMixSampelInfo")
    ResponseData getSampelInfoByGeneId(@RequestParam("geneIdList") List<String> geneIdList);
}
