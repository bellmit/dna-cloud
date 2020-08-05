package com.bazl.dna.database.nation.converter.client;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.database.nation.converter.client.fallback.ReagentConverterFallback;
import com.bazl.dna.database.service.model.po.DnaLocusInfo;
import com.bazl.dna.database.service.model.po.DnaPanelInfo;
import com.bazl.dna.database.service.model.po.DnaPanelLocus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 样本基因信息转换
 */
@FeignClient(value = "bazl-dna-database-core", fallback = ReagentConverterFallback.class)
public interface IReagentConverterClient {

	@PostMapping(value = "/converter/reagent/save")
	public ResponseData reagentSave(@RequestBody DnaPanelInfo panelInfo);

	@PostMapping(value = "/converter/reagent/panelLocusSave")
	public ResponseData panelLocusSave(@RequestBody DnaPanelLocus dnaPanelLocus);

	@PostMapping(value = "/converter/reagent/locusInfoSave")
	public ResponseData locusInfoSave(@RequestBody DnaLocusInfo dnaLocusInfo);


}
