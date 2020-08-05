package com.bazl.dna.database.service.service;

import com.bazl.dna.database.service.model.po.QcSampleInfo;
import com.bazl.dna.database.service.model.vo.ConverterDataVo;

public interface ConverterDataService {

	public int saveCase(ConverterDataVo vo, boolean isTransfer);
	
	public int saveCriminal(ConverterDataVo vo);
	
	public int saveQcSample(QcSampleInfo entity);
	
}
