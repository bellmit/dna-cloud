package com.bazl.dna.mix.utils;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FileUtils {

    /**
     * 获取配置文件的内容
     *
     * @param filePath 文件的地址
     * @param keyWord  key值
     */
    public static String getProperties(String filePath, String keyWord) {
        Properties prop = new Properties();
        String value = null;
        try {
            InputStream inputStream = FileUtils.class.getResourceAsStream(filePath);
            prop.load(inputStream);
            value = prop.getProperty(keyWord);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }


}
