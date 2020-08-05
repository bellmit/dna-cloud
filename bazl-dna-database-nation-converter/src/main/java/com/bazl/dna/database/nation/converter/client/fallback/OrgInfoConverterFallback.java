package com.bazl.dna.database.nation.converter.client.fallback;

import org.springframework.stereotype.Component;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.database.nation.converter.client.IOrgInfoConverterClient;
import com.bazl.dna.database.nation.converter.utils.ErrorCodes;
import com.bazl.dna.database.nation.converter.utils.ErrorInfo;

/**
 * 样本基因信息转换
 */
@Component
public class OrgInfoConverterFallback implements IOrgInfoConverterClient {

	@Override
	public ResponseData orgInfoSave(String json) {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

}
