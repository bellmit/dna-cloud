package com.bazl.dna.caseinfo.reg.controller;

import com.bazl.dna.caseinfo.reg.client.FastDFSClient;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author wanghaiyang
 * @date 2020/7/13 17:02
 */
@Controller
@RequestMapping("/fdfs")
public class FastDFSFileController extends BaseController {

    @Autowired
    FastDFSClient fastDFSClient;

    /**
     * 文件上传
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/uploadFile")
    @ResponseBody
    public Map<String, Object> handleFileUpload(@RequestBody MultipartFile file) {
        Map<String, Object> result = new HashMap<>();
        String url = null;
        try {
            if (file != null) {
                url = fastDFSClient.uploadFile(file);
                result.put("success", true);
            }
        }catch(Exception ex) {
            logger.error("调用FastDFS Client上传文件失败!" + url, ex);
            result.put("success", false);
        }

        return result;
    }

    /**
     * 文件下载
     * @param fileUrl  url 开头从组名开始
     * @param response
     * @throws Exception
     */
    @RequestMapping("/download")
    public void download(HttpServletResponse response, String fileUrl) throws Exception{
        try {
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
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("调用FastDFS Client文件下载失败!", e);
        }
    }

}
