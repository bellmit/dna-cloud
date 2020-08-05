package com.bazl.dna.database.nation.converter.client.fallback;

import com.bazl.dna.database.service.model.po.*;
import org.springframework.stereotype.Component;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.database.nation.converter.client.ICaseConverterClient;
import com.bazl.dna.database.nation.converter.utils.ErrorCodes;
import com.bazl.dna.database.nation.converter.utils.ErrorInfo;

/**
 * 案件信息转换
 */
@Component
public class CaseConverterFallback implements ICaseConverterClient {

	@Override
	public ResponseData caseInfoSave(CaseInfo caseInfo) {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

	@Override
	public ResponseData caseInfoSaveImage(CaseImage caseImage) {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}
	
	@Override
	public ResponseData casePersonInfoSave(CasePersonInfo casePersonInfo) {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

	@Override
	public ResponseData savePerson(PersonRelativeInfo personRelativeInfo) {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

	@Override
	public ResponseData consignmentInfoSave(ConsignmentInfo consignmentInfo) {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

}
