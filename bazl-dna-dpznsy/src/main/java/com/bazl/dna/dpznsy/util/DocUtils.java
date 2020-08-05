package com.bazl.dna.dpznsy.util;

import com.bazl.dna.dpznsy.controller.base.BaseController;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

/**
 * Created by Administrator on 2020/3/24.
 */
@Service
public class DocUtils extends BaseController {

    @Value("${bendiFilePath}")
    private String bendiFilePath;

    //生成文件  指定生成路径 返回文件本地绝对路径
    public String DocFile(HttpServletResponse response, Map<String, Object> params, String templateName, String fileName){
        //获取文件存储路径
        String filePath = bendiFilePath;
        File file = new File(filePath);
        if (!file .exists()  && !file .isDirectory()) {
//            System.out.println("//不存在");
            file.mkdir();
        }
        //生成word文档路径
        String filePathStr = filePath + fileName;
        Writer out = null;
        try {
            @SuppressWarnings("deprecation")
			Configuration cfg = new Configuration();
            cfg.setDefaultEncoding("UTF-8");
            cfg.setClassForTemplateLoading(this.getClass(), "/templates");
            //获取模板
            Template tmp = cfg.getTemplate(templateName, "UTF-8");
            response.setCharacterEncoding("UTF-8");
            // 以下不注释掉打印会弹出下载框
            //文件头，导出的文件名
            response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO-8859-1"));
            //类型
//            response.setContentType("*/*");
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePathStr), "UTF-8"));
            tmp.process(params, out);
        } catch (Exception ex) {
            logger.error(templateName+"下载错误", ex);
            return "";
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return filePathStr;
    }

    //用户自定义生成路径
    public void DocFileNew(HttpServletResponse response, Map<String, Object> params, String templateName, String fileName){
        try {
            @SuppressWarnings("deprecation")
			Configuration cfg = new Configuration();
            cfg.setDefaultEncoding("UTF-8");
            cfg.setClassForTemplateLoading(this.getClass(), "/templates");
            //获取模板
            Template tmp = cfg.getTemplate(templateName, "UTF-8");
            response.setCharacterEncoding("UTF-8");
            // 以下不注释掉打印会弹出下载框
            //文件头，导出的文件名
            response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO-8859-1"));
            //类型
            response.setContentType("application/x-msdownload");
//            response.setContentType("*/*");
            tmp.process(params, response.getWriter());
        } catch (Exception ex) {
            logger.error(templateName + "下载错误", ex);
        }
    }
}
