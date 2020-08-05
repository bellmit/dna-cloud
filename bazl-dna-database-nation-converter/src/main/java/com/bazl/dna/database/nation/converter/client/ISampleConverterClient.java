package com.bazl.dna.database.nation.converter.client;

import com.bazl.dna.database.service.model.po.QcSampleInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.database.nation.converter.client.fallback.SampleConverterFallback;
import com.bazl.dna.database.service.model.po.DnaSampleImage;
import com.bazl.dna.database.service.model.po.DnaSampleInfo;

/**
 * 样本信息转换
 */
@FeignClient(value = "bazl-dna-database-core", fallback = SampleConverterFallback.class)
public interface ISampleConverterClient {

	@PostMapping(value = "/converter/sample/save")
	public ResponseData save(@RequestBody DnaSampleInfo sampleInfo);
	
	@PostMapping(value = "/converter/sample/saveImage")
	public ResponseData saveImage(@RequestBody DnaSampleImage sampleInfoImage);

	@PostMapping(value = "/converter/sample/qcsave")
	public ResponseData qcsave(@RequestBody QcSampleInfo qcSampleInfo);
	
}
