package com.bazl.dna.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * Created by cyj on 2020/5/18.
 */
public class GeneTransFormUtils {
	
    private static final Logger logger = LoggerFactory.getLogger(GeneTransFormUtils.class);

    private GeneTransFormUtils() {
    	
    }
    
    /**
     *   转换为给前端的基因格式
     *   转换格式
     *      {"D7S820": "10/11", "D12S391": "18/18", "D13S317": "11/12", "D16S539": "10/13"}
     *   目标类型
     *    [{"name": "D8S1179", "value": "[11,12]"}, {"name": "D2S11", "value": "[9,10]"} ......]
     */
    @SuppressWarnings("unchecked")
	public static String geneFormatList(String geneInfo) {
        List<LinkedHashMap<Object, Object>> geneList = new ArrayList<>();
        String geneInfos = null;
        if (StringUtils.isNotBlank(geneInfo)){
            try {
                Map<String, String> jsonObject = (Map<String, String>)JSON.parse(geneInfo);
                for (Map.Entry<String,String> entry : jsonObject.entrySet()) {
                    LinkedHashMap<Object, Object> map = new LinkedHashMap<>();
                    String str = entry.getValue();
                    String[] split = str.split("/");
                    map.put("name", entry.getKey());
                    map.put("value", split);
                    geneList.add(map);
                }
                geneInfos = JSON.toJSONString(geneList);
            } catch (Exception e) {
            		logger.error("Error geneFormatList:", e);
            }
        }

        return geneInfos;
    }

    public static String geneFormatLists(String geneInfo) {
        List<LinkedHashMap<Object, Object>> geneList = new ArrayList<>();
        String geneInfos = null;
        if (StringUtils.isNotBlank(geneInfo)){
            try {
                @SuppressWarnings("unchecked")
				Map<String, String> jsonObject = (Map<String, String>)JSON.parse(geneInfo);
                for (Map.Entry<String,String> entry : jsonObject.entrySet()) {
                    LinkedHashMap<Object, Object> map = new LinkedHashMap<>();
                    String str = entry.getValue();
                    map.put("name", entry.getKey());
                    map.put("value", str.replace(",","/"));
                    geneList.add(map);
                }
                geneInfos = JSON.toJSONString(geneList);
            } catch (Exception e) {
                logger.error("Error geneFormatList:", e);
            }
        }

        return geneInfos;
    }


    
    /**
     *   转换为给前端的基因格式
     *   转换格式
     *      {"D7S820": "10/11", "D12S391": "18/18", "D13S317": "11/12", "D16S539": "10/13"}
     *   目标类型
     *    [{"name": "D8S1179", "value": "11/12"}, {"name": "D2S11", "value": "9/10"} ......]
     */
    @SuppressWarnings("unchecked")
	public static String geneFormatString2(String geneInfo) {
        List<LinkedHashMap<Object, Object>> geneList = new ArrayList<>();
        String geneInfos = null;
        if (StringUtils.isNotBlank(geneInfo)){
            try {
                Map<String, String> jsonObject = (Map<String, String>)JSON.parse(geneInfo);
                for (Map.Entry<String,String> entry : jsonObject.entrySet()) {
                    LinkedHashMap<Object, Object> map = new LinkedHashMap<>();
                    map.put("name", entry.getKey());
                    map.put("value", entry.getValue());
                    geneList.add(map);
                }
                geneInfos = JSON.toJSONString(geneList);
            } catch (Exception e) {
            	logger.error("Error geneFormatString2:", e);
            }
        }

        return geneInfos;
    }

    /**
     *   转换为给前端的基因格式
     *   转换格式
     *      {"D7S820": "10/11", "D12S391": "18/18", "D13S317": "11/12", "D16S539": "10/13"}
     *   目标类型
     *    [{"name": "D8S1179", "value": "11/12"}, {"name": "D2S11", "value": "9/10"} ......]
     */
    @SuppressWarnings("unchecked")
    public static String geneForma(String geneInfo) {
        List<LinkedHashMap<Object, Object>> geneList = new ArrayList<>();
        String geneInfos = null;
        if (StringUtils.isNotBlank(geneInfo)){
            try {
                Map<String, String> jsonObject = (Map<String, String>)JSON.parse(geneInfo);
                for (Map.Entry<String,String> entry : jsonObject.entrySet()) {
                    LinkedHashMap<Object, Object> map = new LinkedHashMap<>();
                    map.put("name", entry.getKey());
                    map.put("value", entry.getValue());
                    geneList.add(map);
                }
                geneInfos = JSON.toJSONString(geneList);
            } catch (Exception e) {
                logger.error("Error geneFormatString2:", e);
            }
        }

        return geneInfos;
    }

    /**
     *   转换为给前端的基因格式
     *   转换格式
     *      [{"name": "D8S1179", "value": "11,12"}, {"name": "D2S11", "value": "9,10"} ......]
     *   目标类型
     *      {"D7S820": "10/11", "D12S391": "18/18", "D13S317": "11/12", "D16S539": "10/13"}
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String geneFormatString(String geneInfo) {
        String geneInfos = null;
        if (StringUtils.isNotBlank(geneInfo)){
            try {
                List<Map<String, String>> jsonObject = (List)JSON.parseArray(geneInfo);
                LinkedHashMap<Object, Object> map = new LinkedHashMap<>();
                for (Map<String,String> entry : jsonObject) {
                    map.put(entry.get("name"),entry.get("value"));
                }
                geneInfos = JSON.toJSONString(map);
            } catch (Exception e) {
            	logger.error("Error geneFormatString:", e);
            }
        }

        return geneInfos;
    }

    /**
     *   转换为给前端的基因格式
     *   转换格式
     *      {"D7S820": "10/11", "D12S391": "18/18", "D13S317": "11/12", "D16S539": "10/13"}
     *   目标类型
     *    [{"D8S1179": "[11,12]"}, {"D2S11": "[9,10]"} ......]
     */
    @SuppressWarnings("unchecked")
	public static String geneExchange(String geneInfo) {
        String geneInfos = null;
        LinkedHashMap<Object, Object> map = new LinkedHashMap<>();
        if (StringUtils.isNotBlank(geneInfo)){
            try {
                Map<String, String> jsonObject = (Map<String, String>)JSON.parse(geneInfo);
                for (Map.Entry<String,String> entry : jsonObject.entrySet()) {
                    String str = entry.getValue();
                    String[] split = str.split("/");
                    map.put(entry.getKey(), split);
                }
                geneInfos = JSON.toJSONString(map);
            } catch (Exception e) {
            	logger.error("Error geneExchange:", e);
            }
        }
        return geneInfos;
    }

    /**
     *     基因信息查看比对  判断基因是否比中   并标识 flag 1:比中。0：未比中
     * @param srcGene 待比对基因型
     * @param tarGene 被比样本基因型
     */
    @SuppressWarnings("unchecked")
	public static Map<String, Object> compareResultFlag(String srcGene, String tarGene, int matchLimit) {
        Map<String, Object> resultMap = new HashMap<>();
        if (StringUtils.isNotBlank(srcGene) && StringUtils.isNotBlank(tarGene)){
            Map<String, List<String>> srcGeneMap = JSON.parseObject(srcGene, LinkedHashMap.class);
            Map<String, List<String>> tarGeneMap = JSON.parseObject(tarGene, LinkedHashMap.class);
        /*比中数*/
            int sameCount = 0;
        /*容差数*/
            int diffCount = 0;

            List<Map<String, Object>> resultList = new ArrayList<>();
            String srcAlleles = null;
            String tarAlleles = null;
            Map<String, Object> map = null;
            //进行循环比对
            for (Map.Entry<String, List<String>> srcEntry : srcGeneMap.entrySet()) {
                String markerName = srcEntry.getKey();
                List<String> srcAlleleList = srcEntry.getValue();
                List<String> tarAlleleList = tarGeneMap.get(markerName);

                if (srcAlleleList != null && !srcAlleleList.isEmpty() && tarAlleleList != null && !tarAlleleList.isEmpty()) {
                    //判断混合样本是否包含比对的样本
                    map = new LinkedHashMap<>();
                    if (isContainsGeneAll(srcAlleleList, tarAlleleList)) {
                        srcAlleles = getAllele(srcAlleleList);
                        tarAlleles = getAllele(tarAlleleList);
                        map.put("name", markerName);
                        map.put("gene1", srcAlleles);
                        map.put("gene2", tarAlleles);
                        map.put("flag","1");
                        resultList.add(map);
                        sameCount++;
                    } else {
                        srcAlleles = getAllele(srcAlleleList);
                        tarAlleles = getAllele(tarAlleleList);
                        map.put("name", markerName);
                        map.put("gene1", srcAlleles);
                        map.put("gene2", tarAlleles);
                        map.put("flag","0");
                        resultList.add(map);
                        diffCount++;
                    }
                }
            }
            if (sameCount < matchLimit) {
                resultMap.put("matched", Boolean.FALSE);
            } else {
                resultMap.put("matched", Boolean.TRUE);
            }
            resultMap.put("resultList", resultList);
            resultMap.put("sameCount", sameCount);
            resultMap.put("diffCount", diffCount);
        }
        return resultMap;
    }

    /**
     * 判断混合分型是否包含此分型
     *
     * @param srcAlleleList
     * @param tarAlleleList
     * @return
     */
    public static boolean isContainsGeneAll(List<String> srcAlleleList, List<String> tarAlleleList) {
        boolean flag = true;

        String isExist = null;
        if (tarAlleleList != null && !tarAlleleList.isEmpty()) {
            for (String tarStr : tarAlleleList) {
                if (!srcAlleleList.contains(tarStr)) {
                    isExist = "notExist";
                    break;
                }
            }
        }

        if (StringUtils.isNotBlank(isExist)) {
            flag = false;
        }

        return flag;
    }

    /**
     * 获取基因位点
     *
     * @param strList
     * @return
     */
    public static String getAllele(List<String> strList) {
        String allele = "";

        if (strList != null && !strList.isEmpty()) {
            for (String strGene : strList) {
                if (StringUtils.isBlank(allele)) {
                    allele = strGene;
                } else {
                    if (!allele.contains(strGene)) {
                        allele += "," + strGene;
                    }
                }
            }
        }
        return allele;
    }

    /**
     * 国家库基因信息格式转换
     */
    public static String geneFormatConversion(String geneInfo, List<Map<String,String>> stringList) {
        LinkedHashMap<Object, Object> sampleGeneInfo = new LinkedHashMap<>();
        String geneInfos = null;
        try {
            String[] split = geneInfo.split(";",-1);
            if (split.length>0){
                for (int i = 0;i<split.length &&i<stringList.size();i++){
                    String s = split[i];
                    sampleGeneInfo.put(stringList.get(i).get("locusName"),s);
                }
            }
            geneInfos = JSON.toJSONString(sampleGeneInfo);
        } catch (Exception e) {
        		logger.error("Error geneFormatConversion:", e);
        }
        return geneInfos;
    }

    public static void main(String[] args) {

        String geneInfo1="[{\"name\": \"D8S1179\", \"value\": \"11/12\"}, {\"name\": \"D2S11\", \"value\": \"9/10\"}]";
        String s = geneFormatString(geneInfo1);
        System.out.println(s);

    }

}
