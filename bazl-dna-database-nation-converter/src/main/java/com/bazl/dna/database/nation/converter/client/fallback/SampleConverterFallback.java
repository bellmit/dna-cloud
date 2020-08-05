package com.bazl.dna.database.nation.converter.client.fallback;

import com.bazl.dna.database.service.model.po.QcSampleInfo;
import org.springframework.stereotype.Component;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.database.nation.converter.client.ISampleConverterClient;
import com.bazl.dna.database.nation.converter.utils.ErrorCodes;
import com.bazl.dna.database.nation.converter.utils.ErrorInfo;
import com.bazl.dna.database.service.model.po.DnaSampleImage;
import com.bazl.dna.database.service.model.po.DnaSampleInfo;

@Component
public class SampleConverterFallback implements ISampleConverterClient {

	@Override
	public ResponseData save(DnaSampleInfo sampleInfo) {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

	@Override
	public ResponseData saveImage(DnaSampleImage sampleInfoImage) {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

	@Override
	public ResponseData qcsave(QcSampleInfo QcSampleInfo) {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}


}
