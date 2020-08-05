package com.bazl.dna.mix.compare.client.fallback;

import org.springframework.stereotype.Component;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.mix.compare.client.CompareQueueClient;
import com.bazl.dna.mix.compare.constants.ErrorCodes;
import com.bazl.dna.mix.compare.constants.ErrorInfo;

@Component
public class CompareQueueClientFallback implements CompareQueueClient {


	@Override
	public ResponseData updateStatus(String Id,String Status, String targetCount) {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

	@Override
	public ResponseData updateByPrimaryKey( String id, String argetCount) {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

	@Override
	public ResponseData contributorGene(String MixedSampleId) {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

	@Override
	public ResponseData getMixSampleInfo(String MixedSampleId) {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

	@Override
	public ResponseData selectByMatch(String CompareId,String SingleGeneId,String ResultType) {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

	@Override
	public ResponseData updateMatchResult(String matchResult) {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

	@Override
	public ResponseData insert( String match) {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

	@Override
	public ResponseData insertMobile(String mobileNews) {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

	@Override
	public ResponseData selectByCompareQueueId(String Id) {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}
}
