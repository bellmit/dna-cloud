package com.bazl.dna.mix.client.fallback;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.mix.client.IKitClientService;
import com.bazl.dna.mix.constants.ErrorCodes;
import com.bazl.dna.mix.constants.ErrorInfo;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public class KitClientServiceFallback implements IKitClientService {

	@Override
	public ResponseData selectKitNameList() {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

	@Override
	public ResponseData selectByKitId(String reagentKitId) {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

	@Override
	public ResponseData queryLocusNameVague(@RequestBody String locusName) {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

	@Override
	public ResponseData selectProvinceAndCity(String code) {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

	@Override
	public ResponseData selectByKitNameToLocusName(String reagentKitName) {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}
}
