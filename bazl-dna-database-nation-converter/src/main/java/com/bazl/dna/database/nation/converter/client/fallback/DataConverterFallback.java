package com.bazl.dna.database.nation.converter.client.fallback;

import org.springframework.stereotype.Component;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.database.nation.converter.client.IDataConverterClient;
import com.bazl.dna.database.nation.converter.utils.ErrorCodes;
import com.bazl.dna.database.nation.converter.utils.ErrorInfo;
import com.bazl.dna.database.service.model.po.QcSampleInfo;
import com.bazl.dna.database.service.model.vo.ConverterDataVo;

/**
 * 案件信息转换
 */
@Component
public class DataConverterFallback implements IDataConverterClient {

	@Override
	public ResponseData saveCase(ConverterDataVo vo) {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

	@Override
	public ResponseData saveCriminal(ConverterDataVo vo) {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

	@Override
	public ResponseData saveQcSample(QcSampleInfo entity) {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}


}
