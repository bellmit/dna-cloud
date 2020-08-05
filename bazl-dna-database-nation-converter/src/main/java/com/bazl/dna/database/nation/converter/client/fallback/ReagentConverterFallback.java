package com.bazl.dna.database.nation.converter.client.fallback;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.database.nation.converter.client.IReagentConverterClient;
import com.bazl.dna.database.nation.converter.utils.ErrorCodes;
import com.bazl.dna.database.nation.converter.utils.ErrorInfo;
import com.bazl.dna.database.service.model.po.DnaLocusInfo;
import com.bazl.dna.database.service.model.po.DnaPanelInfo;
import com.bazl.dna.database.service.model.po.DnaPanelLocus;
import org.springframework.stereotype.Component;

/**
 * 样本基因信息转换
 */
@Component
public class ReagentConverterFallback implements IReagentConverterClient {

	@Override
	public ResponseData reagentSave(DnaPanelInfo panelInfo) {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

	@Override
	public ResponseData panelLocusSave(DnaPanelLocus dnaPanelLocus) {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

	@Override
	public ResponseData locusInfoSave(DnaLocusInfo dnaLocusInfo) {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

}
