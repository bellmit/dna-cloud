package com.bazl.dna.mix.client;

import javax.servlet.http.HttpServletResponse;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.mix.client.fallback.FilesServerClientFallback;
import com.bazl.dna.common.filter.FeignSupportConfig;

@FeignClient(value = "bazl-dna-files", fallback = FilesServerClientFallback.class,
	configuration = FeignSupportConfig.class)
public interface FilesServerClient {

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

	@RequestMapping(value = "/fdfs/download")
    ResponseData download(@RequestParam("fileUrl") String fileUrl, HttpServletResponse response);
}
