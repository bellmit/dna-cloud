package com.bazl.dna.database.nation.converter.utils;

import java.util.Map;
import java.util.stream.Collectors;

public class LikeKeyUtils {

    /**
     * 从map中查询想要的map项，根据key
     */
    public static Map<String, Object> parseMapForFilter(Map<String, Object> map, String filters) {
        if (map == null) {
            return null;
        } else {
            map = map.entrySet().stream()
                    .filter((e) -> checkKey(e.getKey(),filters))
                    .collect(Collectors.toMap(
                            (e) -> (String) e.getKey(),
                            (e) -> e.getValue()
                    ));
        }
        return map;
    }

    /**
     * 通过indexof匹配想要查询的字符
     */
    private static boolean checkKey(String key,String filters) {
        if (key.indexOf(filters) > -1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取map中第一个key值
     *
     * @param map 数据源
     * @return
     */
    public static String getKeyOrNull(Map<String, Object> map) {
        String obj = null;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            obj = entry.getKey();
            if (obj != null) {
                break;
            }
        }
        return  obj;
    }


    /**
     * 获取map中第一个数据值
     *
     * @param map 数据源
     * @return
     */
    public static Object getFirstOrNull(Map<String, Object> map) {
        Object obj = null;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            obj = entry.getValue();
            if (obj != null) {
                break;
            }
        }
        return  obj;
    }


}
