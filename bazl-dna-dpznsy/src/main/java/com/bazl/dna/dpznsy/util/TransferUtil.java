package com.bazl.dna.dpznsy.util;

import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Sun on 2019/1/29.
 */
public class TransferUtil {

    /**
     * 调取接案平台数据
     * @param paramMap
     * @return
     * @throws IOException
     */
    public static String getJaptInfo(Map<String, Object> paramMap, String url) throws IOException {
        String result = "" ;
        try {
            URL realUrl = new URL(url);
            // 打开与URL之间的链接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept","*/*");
            conn.setRequestProperty("connection","Keep-Alive");
            conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)\"");
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");;
            //发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            // 设置请求属性
            JSONObject resultJson= new JSONObject();
            Iterator<String> it = paramMap.keySet().iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                resultJson.put(key, paramMap.get(key));
            }
            // 发送请求参数
            out.print(resultJson);
            // flush输出流缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
            String line ;
            while((line=reader.readLine())!=null){
                result +=line;
            }
            FileWriter fw= new FileWriter("E:/接案平台数据.txt",true);
            fw.write("接收接案平台数据并打印到文件里："+ result +"\r\n");
            fw.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
