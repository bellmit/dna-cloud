package com.bazl.dna.database.sync.client.fallback;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.database.sync.client.GeneListServerClient;
import com.bazl.dna.database.sync.constants.ErrorCodes;
import com.bazl.dna.database.sync.constants.ErrorInfo;
import org.springframework.stereotype.Component;

import java.util.Date;

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
	public ResponseData selectMixGeneByCwsd() {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

	@Override
	public ResponseData selectByLocusName() {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

	@Override
	public ResponseData selectCountByDate(Date date) {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

	@Override
	public ResponseData selectByDateSingList(String page, int pageSize, Date date) {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

}
