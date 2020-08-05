package com.bazl.dna.database.nation.converter.client.fallback;

import org.springframework.stereotype.Component;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.database.nation.converter.client.ISampleGeneConverterClient;
import com.bazl.dna.database.nation.converter.utils.ErrorCodes;
import com.bazl.dna.database.nation.converter.utils.ErrorInfo;
import com.bazl.dna.database.service.model.po.DnaMixGeneInfo;
import com.bazl.dna.database.service.model.po.DnaStrGeneInfo;
import com.bazl.dna.database.service.model.po.DnaYstrGeneInfo;

/**
 * 样本基因信息转换
 */
@Component
public class SampleGeneConverterFallback implements ISampleGeneConverterClient {

	@Override
	public ResponseData strGeneInfoSave(DnaStrGeneInfo strGeneInfo) {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

	@Override
	public ResponseData ystrGeneInfoSave(DnaYstrGeneInfo ystrGeneInfo) {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

	@Override
	public ResponseData mixGeneInfoSave(DnaMixGeneInfo ystrGeneInfo) {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

}
