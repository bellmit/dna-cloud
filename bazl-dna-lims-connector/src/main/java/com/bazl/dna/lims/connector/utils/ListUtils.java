package com.bazl.dna.lims.connector.utils;


import java.util.List;

public class ListUtils {

    public static boolean isNotNullAndEmptyList(List<?> list) {
        return list != null && list.size() > 0;
    }

    public static boolean isNullOrEmptyList(List<?> list) {
        return list == null || list.size() == 0;
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
