package com.bazl.dna.mix.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.mix.client.fallback.SampleDataServicrFallback;

@FeignClient(value = "bazl-dna-nation-connector", fallback = SampleDataServicrFallback.class)
public interface SampleDataServicr {

    @RequestMapping(value = "/collection/selectSampleNo")
    ResponseData selectSampleNo(@RequestParam("sampleNo") String sampleNo);
    
    @RequestMapping(value = "/collection/selectSampleId")
    ResponseData selectSampleId(@RequestParam("sampleId") String sampleId);

    @GetMapping(value= "/nationalTreasury/selectPersonCategory")
    ResponseData selectPersonCategory();

    @GetMapping(value= "/nationalTreasury/selectMixedSampleGeneList")
    ResponseData selectMixedSampleGeneList(@RequestParam("caseId") String caseId);
}
