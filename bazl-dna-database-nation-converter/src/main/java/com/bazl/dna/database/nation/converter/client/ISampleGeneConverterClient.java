package com.bazl.dna.database.nation.converter.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.database.nation.converter.client.fallback.SampleGeneConverterFallback;
import com.bazl.dna.database.service.model.po.DnaMixGeneInfo;
import com.bazl.dna.database.service.model.po.DnaStrGeneInfo;
import com.bazl.dna.database.service.model.po.DnaYstrGeneInfo;

/**
 * 样本基因信息转换
 */
@FeignClient(value = "bazl-dna-database-core", fallback = SampleGeneConverterFallback.class)
public interface ISampleGeneConverterClient {

	@PostMapping(value = "/converter/gene/strGeneInfoSave")
	public ResponseData strGeneInfoSave(@RequestBody DnaStrGeneInfo strGeneInfo);
	
	@PostMapping(value = "/converter/gene/ystrGeneInfoSave")
	public ResponseData ystrGeneInfoSave(@RequestBody DnaYstrGeneInfo ystrGeneInfo);
	
	@PostMapping(value = "/converter/gene/mixGeneInfoSave")
	public ResponseData mixGeneInfoSave(@RequestBody DnaMixGeneInfo ystrGeneInfo);
	
}
