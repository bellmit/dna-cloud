package com.bazl.dna.mix.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bazl.dna.mix.controller.base.BaseController;
import com.bazl.dna.mix.utils.FtpUtils;
import com.bazl.dna.util.DataUtils;

/**
 *      文件下载
 */
@RestController
@RequestMapping("/download")
public class DownloadFileController extends BaseController {

    @Value("${ftpIp}")
    private String ftpIp;
    @Value("${ftpPort}")
    private String ftpPort;
    @Value("${ftpUser}")
    private String ftpUser;
    @Value("${ftpPassword}")
    private String ftpPassword;
    @Value("${ftpchromePath}")
    private String ftpchromePath;
    @Value("${bendiFilePath}")
    private String bendiFilePath;


    /*
   *    检材编号模糊查询
   * */
    @RequestMapping(value = "/chrome", produces = "application/json;charset=UTF-8")
    public ResponseEntity<byte[]> downloadChrome(String sampleNo) throws Exception {
//        下载
        String s = null;
        try {
            FtpUtils f = new FtpUtils(ftpIp, ftpPort, ftpUser, ftpPassword);
            if(f.open()) {
                String path = bendiFilePath + "/ChromeSetup.exe";
                if (!new File(path).exists()){
                    if(!new File(bendiFilePath).exists())   {
                        new File(bendiFilePath).mkdirs();
                        f.get(ftpchromePath, path);
                    }else {
                        f.get(ftpchromePath, path);
                    }
                }

                /*b = f.get(ftpchromePath, bendiFilePath);
//                s = f.readConfigFileForFTP("/chrome", "ChromeSetup.exe");
                if (b){
                    s = bendiFilePath;
                }*/
                return DataUtils.download(new File(path),"ChromeSetup.exe");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("下载失败！" + e.getMessage());
        }
        System.out.println(s);
        return null;
    }
}
