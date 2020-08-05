package com.bazl.dna.mix.compare.client.fallback;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.mix.compare.client.ISysUserClient;
import com.bazl.dna.mix.compare.constants.ErrorCodes;
import com.bazl.dna.mix.compare.constants.ErrorInfo;
import org.springframework.stereotype.Component;

@Component
public class SysUserClientFallback implements ISysUserClient {

	@Override
	public ResponseData selectByPrimaryKey(String id) {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

}
