package com.bazl.dna.mix.client.fallback;

import org.springframework.stereotype.Component;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.mix.client.SampleDataServicr;
import com.bazl.dna.mix.constants.ErrorCodes;
import com.bazl.dna.mix.constants.ErrorInfo;

@Component
public class SampleDataServicrFallback implements SampleDataServicr {
    @Override
    public ResponseData selectSampleNo(String sampleNo) {
        return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
    }

    @Override
    public ResponseData selectPersonCategory() {
        return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
    }

    @Override
    public ResponseData selectMixedSampleGeneList(String caseId) {
        return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
    }


    @Override
	public ResponseData selectSampleId(String sampleId) {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}
}
