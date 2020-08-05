package com.bazl.dna.mix.client.fallback;

import org.springframework.stereotype.Component;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.mix.client.ISysUserClient;
import com.bazl.dna.mix.constants.ErrorCodes;
import com.bazl.dna.mix.constants.ErrorInfo;

@Component
public class SysUserClientFallback implements ISysUserClient {

	@Override
	public ResponseData selectByPrimaryKey(String id) {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

}
