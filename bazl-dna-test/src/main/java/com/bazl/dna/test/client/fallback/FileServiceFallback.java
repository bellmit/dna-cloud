package com.bazl.dna.test.client.fallback;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.test.client.IFileServiceClient;

@Component
public class FileServiceFallback implements IFileServiceClient {

	@Override
	public ResponseData upload(MultipartFile multipartFile) {
		return null;
	}

}
