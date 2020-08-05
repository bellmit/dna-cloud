package com.bazl.dna.database.core.client.fallback;

import org.springframework.stereotype.Component;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.database.core.client.OrgInfoClient;
import com.bazl.dna.database.core.constants.ErrorCodes;
import com.bazl.dna.database.core.constants.ErrorInfo;

@Component
public class OrgInfoClientFallback implements OrgInfoClient {

	@Override
	public ResponseData findListByParentId(String parentId) {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

	@Override
	public ResponseData getById(String id) {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

}
