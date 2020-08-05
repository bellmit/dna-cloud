package com.bazl.dna.dfs.web.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.dfs.client.FastDFSClient;

/**
 * 文件上传
 * Created by lizhihua on 2020-04-14.
 */
@RestController
@RequestMapping("/fdfs")
public class FastDFSFileController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    FastDFSClient fastDFSClient;

    /**
     * 文件上传 MultipartFile 类型
     * @param MultipartFile file
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/uploadFile")
    public ResponseData uploadFile(@RequestBody MultipartFile file) {
        try {
        	if (file != null) {
	            String url = fastDFSClient.uploadFile(file);
	            return new ResponseData(url);
        	}
        }catch(Exception ex) {
            logger.error("调用FastDFS Client上传文件失败!", ex);
            return new ResponseData("上传失败! ErrorMsg: " + ex.getMessage());
        }
        return new ResponseData();
    }

    /**
     * 文件上传 File 类型
     * @param File file
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/uploadFiles")
    public ResponseData uploadFiles(@RequestBody File file) {
        try {
            if (file != null) {
                String url = fastDFSClient.uploadFile(file);
                return new ResponseData(url);
            }
        }catch(Exception ex) {
            logger.error("调用FastDFS Client上传文件失败!", ex);
            return new ResponseData("上传失败! ErrorMsg: " + ex.getMessage());
        }
        return new ResponseData();
    }
    
    /**
     * 文件下载
     * @param fileUrl  url 开头从组名开始
     * @param response
     * @throws Exception
     */
    @RequestMapping("/download")
    public void download(@RequestParam("fileUrl")String fileUrl, HttpServletResponse response) throws Exception{
        byte[] data = fastDFSClient.download(fileUrl);

        String filename = fileUrl.substring(fileUrl.lastIndexOf("/"));
        if(StringUtils.isBlank(filename)){
            filename = UUID.randomUUID().toString();
        }

        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));

        // 写出
        ServletOutputStream outputStream = response.getOutputStream();
        IOUtils.write(data, outputStream);
    }


}
