package com.bazl.dna.mix.client.fallback;

import org.springframework.stereotype.Component;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.mix.client.CaseInfoServerClient;
import com.bazl.dna.mix.constants.ErrorCodes;
import com.bazl.dna.mix.constants.ErrorInfo;

@Component
public class CaseInfoServerClientFallback implements CaseInfoServerClient {

	@Override
	public ResponseData getCaseInfoByCaseNo(String caseNo) {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

	@Override
	public ResponseData selectByCaseId(String caseId) {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

	@Override
	public ResponseData selectBySampleId(String sampleId) {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}
}
