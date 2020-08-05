package com.bazl.dna.mix.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * String List工具类
 * @author lizhihua on 2019-07-29.
 */
public class ListStringUtils {

    /**
     * 将字符串list用连接符连接起来
     * @param list
     * @param separator
     * @return
     */
    public static String list2String(List<String> list, String separator){
        if(list == null || list.isEmpty()){
            return StringUtils.EMPTY;
        }

        StringBuffer sb = new StringBuffer();

        for(String str : list){
            sb.append(str).append(separator);
        }
        String result = sb.toString();
        return result.substring(0, result.length() - 1);
    }


    /**
     * 将字符串list用连接符连接起来，自动排除Null和空字符串
     * @param alleleValue   待转换基因位点信息
     * @param separator     分隔符
     * @return
     */
    public static List<String> string2List(String alleleValue, String separator){
        List<String> list = new ArrayList<>();
        if(alleleValue.contains("/")){
            String[] splitedArr = alleleValue.split("/");
            for(String str : splitedArr){
                if(StringUtils.isNotBlank(str)){
                    list.add(str);
                }
            }
        }else{
            list.add(alleleValue);
        }
        return list;
    }

    /**
     * 将字符串list用连接符连接起来，自动排除Null和空字符串， 并根据参数判断是否需要判重
     * @param alleleValue 待转换基因位点信息
     * @param separator   分隔符
     * @param filterRepeat  是否去重
     * @return
     */
    public static List<String> string2List(String alleleValue, String separator, boolean filterRepeat){
        if(StringUtils.isBlank(alleleValue)){
            return new ArrayList<>();
        }

        List<String> list = string2List(alleleValue, separator);

        return list.stream().distinct().collect(Collectors.toList());
    }

}
