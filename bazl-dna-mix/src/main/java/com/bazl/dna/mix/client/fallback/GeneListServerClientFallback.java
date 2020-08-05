package com.bazl.dna.mix.client.fallback;

import org.springframework.stereotype.Component;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.mix.client.GeneListServerClient;
import com.bazl.dna.mix.constants.ErrorCodes;
import com.bazl.dna.mix.constants.ErrorInfo;

@Component
public class GeneListServerClientFallback implements GeneListServerClient {

	@Override
	public ResponseData selectSingleGeneByCwsdPage(String page,int pageSize) {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

	@Override
	public ResponseData selectSingleGeneByCwsd() {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

    @Override
    public ResponseData selectByLocusName() {
        return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
    }

	@Override
	public ResponseData getCaseInfoSampleNo(String SampleId, String datatype, String personCodeType) {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

	@Override
	public ResponseData selectSingleGeneBySampleNo(String SampleNo) {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

	@Override
	public ResponseData updateSampleDnaGeneDF(String sampleGeneId) {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

	@Override
	public ResponseData queryMixedSampleGenes(String page, String caseNo, String caseName, String sampleNo,
			String sampleName) {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

	@Override
	public ResponseData selectMixGeneByCwsd() {
		// TODO Auto-generated method stub
		return null;
	}

}
