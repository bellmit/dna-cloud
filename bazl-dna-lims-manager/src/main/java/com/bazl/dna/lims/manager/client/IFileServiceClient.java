package com.bazl.dna.lims.manager.client;

import java.io.File;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.common.filter.FeignSupportConfig;
import com.bazl.dna.lims.manager.client.fallback.FileServiceClientFallback;

@FeignClient(value = "bazl-dna-files", fallback = FileServiceClientFallback.class,
	configuration = FeignSupportConfig.class)
public interface IFileServiceClient {

	/**
	 * 上传文件
	 * 		类型: Content-Type: multipart/form-data
	 * 		方式: Post
	 * 		参数: @RequestPart("name")
	 * 
	 * 		服务端接收方式: @RequestBody
	 * @param file
	 * @return
	 */
	@PostMapping(value="/fdfs/uploadFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	ResponseData upload(@RequestPart("file") MultipartFile file);

	/**
	 * 上传文件 File 类型
	 * @param file
	 * @return
	 */
	@PostMapping(value="/fdfs/uploadFiles")
	ResponseData upload(@RequestBody File file);

}
