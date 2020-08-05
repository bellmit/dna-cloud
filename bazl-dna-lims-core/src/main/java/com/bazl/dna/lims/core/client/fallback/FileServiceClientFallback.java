package com.bazl.dna.lims.core.client.fallback;

import java.io.File;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.lims.core.client.IFileServiceClient;
import com.bazl.dna.lims.core.constants.ErrorCodes;
import com.bazl.dna.lims.core.constants.ErrorInfo;

@Component
public class FileServiceClientFallback implements IFileServiceClient {

	@Override
	public ResponseData upload(MultipartFile multipartFile) {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

	@Override
	public ResponseData upload(File file) {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

}
