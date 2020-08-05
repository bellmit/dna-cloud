package com.bazl.dna.database.nation.converter.client.fallback;

import org.springframework.stereotype.Component;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.database.nation.converter.client.ICriminalConverterClient;
import com.bazl.dna.database.nation.converter.utils.ErrorCodes;
import com.bazl.dna.database.nation.converter.utils.ErrorInfo;
import com.bazl.dna.database.service.model.po.CriminalPersonInfo;
import com.bazl.dna.database.service.model.po.CriminalSampleInfo;

/**
 * 违法犯罪人员信息转换
 */
@Component
public class CriminalConverterFallback implements ICriminalConverterClient {

	@Override
	public ResponseData personInfoSave(CriminalPersonInfo caseInfo) {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

	@Override
	public ResponseData sampleInfoSave(CriminalSampleInfo caseInfo) {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

}
