package com.bazl.dna.mix.client.fallback;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.mix.client.FilesServerClient;
import com.bazl.dna.mix.constants.ErrorCodes;
import com.bazl.dna.mix.constants.ErrorInfo;

@Component
public class FilesServerClientFallback implements FilesServerClient {

	@Override
	public ResponseData upload(MultipartFile multipartFile) {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

	@Override
	public ResponseData download(String fileUrl, HttpServletResponse response) {
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

}
