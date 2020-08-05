package com.bazl.dna.mix.utils;


import java.util.List;

/**
 * Created by Administrator on 2017/1/2.
 */
public class ListUtils {

    public static boolean isNotNullAndEmptyList(List<?> list) {
    	return list != null && !list.isEmpty();
    }

    public static boolean isNullOrEmptyList(List<?> list) {
    	return list == null || list.isEmpty();
    }

    public static boolean isNull(String str) {
        boolean result = false;
        if (str != null && !"".equals(str) && !"null".equalsIgnoreCase(str) && !"NaN".equalsIgnoreCase(str)  && !"undefined".equalsIgnoreCase(str)) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

}
