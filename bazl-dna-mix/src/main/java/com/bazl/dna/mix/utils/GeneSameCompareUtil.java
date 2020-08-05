package com.bazl.dna.mix.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Sun on 2019/4/1.
 */
@Service
public class GeneSameCompareUtil {
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(GeneSameCompareUtil.class);

    /**
     * @param srcGeneMap 待比对基因型
     * @param tarGeneMap 被比样本基因型
     * @return
     */
//    public Map<String, Object> compareResult(Map<String, List<String>> srcGeneMap, Map<String, List<String>> tarGeneMap) {
//        Map<String, Object> resultMap = new HashMap<String, Object>();
//        int sameCount = 0;  /*比中数*/
//        int diffCount = 0;  /*容差数*/
//
//        Map<String, List<String>> sameGeneMap = new HashMap<>();
//        Map<String, String> sameGeneLRMap = new HashMap<>();
//        List<Map<String, Object>> resultList = new ArrayList<>();
//        //进行循环比对
//        for (Map.Entry<String, List<String>> srcEntry : srcGeneMap.entrySet()) {
//            String markerName = srcEntry.getKey();
//            List<String> srcAlleleList = srcEntry.getValue();
//            List<String> tarAlleleList = tarGeneMap.get(markerName);
//
//            if (ListUtils.isNotNullAndEmptyList(srcAlleleList) && ListUtils.isNotNullAndEmptyList(tarAlleleList)) {
//                String allele1 = "";
//                String allele2 = "";
//                String geneStr1 = "";
//                String geneStr2 = "";
//                if (srcAlleleList.size() == 1) {
//                    allele1 = srcAlleleList.get(0).toString();
//                    allele2 = srcAlleleList.get(0).toString();
//                } else if (srcAlleleList.size() > 1) {
//                    allele1 = srcAlleleList.get(0).toString();
//                    allele2 = srcAlleleList.get(1).toString();
//                }
//
//                if (StringUtils.isNotBlank(allele1) && StringUtils.isNotBlank(allele2)) {
//                    if (allele1.equals(allele2)) {
//                        geneStr1 = allele1;
//                    } else {
//                        geneStr1 = allele1 + "," + allele2;
//                    }
//                }
//
//                if (tarAlleleList.size() == 1) {
//                    geneStr2 = tarAlleleList.get(0).toString();
//                } else if (tarAlleleList.size() > 1) {
//                    if (StringUtils.isNotBlank(tarAlleleList.get(0).toString()) && StringUtils.isNotBlank(tarAlleleList.get(1).toString())) {
//                        if (tarAlleleList.get(0).toString().equals(tarAlleleList.get(1).toString())) {
//                            geneStr2 = tarAlleleList.get(0).toString();
//                        } else {
//                            geneStr2 = tarAlleleList.get(0).toString() + "," + tarAlleleList.get(1).toString();
//                        }
//                    }
//                }
//
//                Map<String, Object> map = new LinkedHashMap<>();
//                if (srcAlleleList.size() == tarAlleleList.size() && geneStr1.equals(geneStr2)) {
//                    sameCount++;
//                    sameGeneMap.put(markerName, srcAlleleList);
//                } else {
//                    diffCount++;
//                    map.put("type", "abnormal");
//                }
//
//                map.put("markerName", markerName);
//                map.put("geneStr1", geneStr1);
//                map.put("geneStr2", geneStr2);
//                resultList.add(map);
//            }
//        }
//        resultMap.put("resultList", resultList);
//        resultMap.put("sameCount", sameCount);
//        resultMap.put("diffCount", diffCount);
//        return resultMap;
//    }


    /**
     * 解析基因型信息
     *
     * @param
     * @return
     */
    public Map<String, Object> analysisGene(Map<String, List<String>> singleSampleGeneInfo) {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> resultMap = new HashMap<String, Object>();

        String allele = null;
        Map<String, Object> map = null;

        for (Map.Entry<String, List<String>> srcEntry : singleSampleGeneInfo.entrySet()) {
            map = new LinkedHashMap<>();
            allele = getAllele(srcEntry.getValue());

            map.put("markerName", srcEntry.getKey());
            map.put("allele", allele);
            list.add(map);
        }
        resultMap.put("resultList",list);
        return resultMap;
    }

    /**
     * 解析基因型信息
     *
     * @param
     * @return
     */
    public Map<String, Object> analysisGeneList(Map<String, List<String>> singleSampleGeneInfo) {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> resultMap = new HashMap<String, Object>();

        String allele = null;
        Map<String, Object> map = null;

        for (Map.Entry<String, List<String>> srcEntry : singleSampleGeneInfo.entrySet()) {
            map = new LinkedHashMap<>();
            allele = getAllele(srcEntry.getValue());

            map.put("geneName", srcEntry.getKey());
            map.put("allele", allele);
            list.add(map);
        }
        resultMap.put("resultList",list);
        return resultMap;
    }

    /**
     * 获取基因位点
     *
     * @param strList
     * @return
     */
    public String getAllele(List<String> strList) {
        String allele = "";

        if (ListUtils.isNotNullAndEmptyList(strList)) {
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
     * 解析基因型信息1
     *
     * @param
     * @return
     */
    public Map<String, Object> analysisGene1(Map<String, List<String>> singleSampleGeneInfo) {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> resultMap = new HashMap<String, Object>();

        List<Map<String, Object>> allele = null;
        Map<String, Object> map = null;

        for (Map.Entry<String, List<String>> srcEntry : singleSampleGeneInfo.entrySet()) {
            map = new LinkedHashMap<>();
            allele = getAllele1(srcEntry.getValue());

            map.put("markerName", srcEntry.getKey());
            map.put("allele", allele);
            list.add(map);
        }
        resultMap.put("resultList", list);
        JSONObject json = new JSONObject(resultMap);
        return json;
    }
    /**
     * 获取基因位点
     *
     * @param strList
     * @return
     */
    public static List<Map<String, Object>> getAllele1(List<String> strList) {
        List<Map<String, Object>> alleleList = new ArrayList<>();
        if (strList!=null && strList.size()>0) {
            for (String strGene : strList) {
                Map<String, Object> alleleMap = new LinkedHashMap<>();
                alleleMap.put("name",strGene);
                alleleList.add(alleleMap);
            }
        }

        return alleleList;
    }

    /*
    *   前端传来基因信息转换
    * */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public String getGeneInfo(String geneInfo){
        String geneInfos = null;
        if (geneInfo != null){
            //基因型转化
            List<Map<String, Object>> jsonArray = (List) JSONObject.parseArray(geneInfo);
            //用来存储到数据库的格式
            //Map<String, Object> geneMap = new HashMap<>();
            LinkedHashMap<String, Object> geneMap = new LinkedHashMap<>();
            //对前台给的json字符串进行格式转换
            for (int i = 0; i < jsonArray.size(); i++) {
                String markerName = jsonArray.get(i).get("geneName").toString();
                String allele = jsonArray.get(i).get("allele").toString();
                String[] allele1 = allele.split(",");
                geneMap.put(markerName, allele1);
            }
            geneInfos = JSON.toJSONString(geneMap);
        }
        return geneInfos;
    }

    /*
   *   前端传来基因信息转换
   * */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public String markerNameGeneInfo(String geneInfo){
        String geneInfos = null;
        if (geneInfo != null){
            //用来存储到数据库的格式
            List<Map<String, Object>> jsonArray = (List) JSONObject.parseArray(geneInfo);
            //Map<String, Object> geneMap = new HashMap<>();
            LinkedHashMap<String, Object> geneMap = new LinkedHashMap<>();
            //对前台给的json字符串进行格式转换
            for (int i = 0; i < jsonArray.size(); i++) {
                //用来存放位点
                List<String> wdlist = new ArrayList<String>();
                String markerName = jsonArray.get(i).get("markerName").toString();
                List<Map<String, String>> list = (List) jsonArray.get(i).get("allele");
                for (int j = 0; j < list.size(); j++) {
                    String allele = list.get(j).get("name");
                    wdlist.add(allele);
                }
                //对list进行排序
                Collections.sort(wdlist);
                geneMap.put(markerName, wdlist);
            }
            geneInfos = JSON.toJSONString(geneMap);
        }
        return geneInfos;
    }

    /*
     *   前端传来基因信息转换
     * */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public String markerNameGeneInfo2(String geneInfo){
        String geneInfos = null;
        if (geneInfo != null){
            //用来存储到数据库的格式
            List<Map<String, Object>> jsonArray = (List) JSONObject.parseArray(geneInfo);
            Map<String, Object> geneMap = new HashMap<>();
            //对前台给的json字符串进行格式转换
            for (int i = 0; i < jsonArray.size(); i++) {
                //用来存放位点
                List<String> wdlist = new ArrayList<String>();
                String markerName = jsonArray.get(i).get("markerName").toString();
                List<Map<String, String>> list = (List) jsonArray.get(i).get("allele");
                String test = "";
                for (int j = 0; j < list.size(); j++) {
                    String allele = list.get(j).get("name");
                    wdlist.add(allele);
                    test = test + allele + ",";
                }
                //对list进行排序
                Collections.sort(wdlist);
                geneMap.put(markerName, test);
            }
            geneInfos = JSON.toJSONString(geneMap);
        }
        return geneInfos;
    }

    /*
   *   前端传来基因信息转换
   *   前端格式
   *        geneInfo="[{\"markerName\":\"D7S820\",\"allele\":[{\"name\":\"18\"}]},{\"markerName\":\"vWA\",\"allele\":[{\"name\":\"14\"}]},{\"markerName\":\"FGA\",\"allele\":[{\"name\":\"25\"},{\"name\":\"20\"},{\"name\":\"21\"}]}]"
   *   目标基因格式
   *        [{"name": "D8S1179", "value": "11/12"}, {"name": "D2S11", "value": "9/10"} ......]
   * */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static String markerNameGene(String geneInfo){
        String geneInfos = null;
        if (StringUtils.isNotBlank(geneInfo)){
            List<Map<String, Object>> geneList = new ArrayList<>();
            //用来存储到数据库的格式
            List<Map<String, Object>> jsonArray = (List) JSONObject.parseArray(geneInfo);
            //对前台给的json字符串进行格式转换
            for (int i = 0; i < jsonArray.size(); i++) {
                LinkedHashMap<String, Object> geneMap = new LinkedHashMap<>();
                //用来存放位点
                String wdlist = "";
                String markerName = jsonArray.get(i).get("markerName").toString();
                List<Map<String, String>> list = (List) jsonArray.get(i).get("allele");
                for (int j = 0; j < list.size(); j++) {
                    String allele = list.get(j).get("name");
                    wdlist += allele + "/";
                }
                if (StringUtils.isNotBlank(wdlist)){
                    wdlist = wdlist.substring(0, wdlist. length()-1);
                }
                geneMap.put("name", markerName);
                geneMap.put("value", wdlist);
                geneList.add(geneMap);
            }
            geneInfos = JSON.toJSONString(geneList);
        }
        return geneInfos;
    }

    /*
    *  前端传来基因信息转换
   *   前端格式
   *        geneInfo="[{\"geneName\":\"D7S820\",\"allele\":[{\"name\":\"18\"}]},{\"markerName\":\"vWA\",\"allele\":[{\"name\":\"14\"}]},{\"markerName\":\"FGA\",\"allele\":[{\"name\":\"25\"},{\"name\":\"20\"},{\"name\":\"21\"}]}]"
   *   目标基因格式
   *        [{"name": "D8S1179", "value": "11/12"}, {"name": "D2S11", "value": "9/10"} ......]
    * */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public String getGeneNameInfo(String geneInfo){
        String geneInfos = null;
        if (geneInfo != null){
            List<Map<String, Object>> geneList = new ArrayList<>();
            //基因型转化
            List<Map<String, Object>> jsonArray = (List) JSONObject.parseArray(geneInfo);
            //对前台给的json字符串进行格式转换
            for (int i = 0; i < jsonArray.size(); i++) {
                //用来存储到数据库的格式
                LinkedHashMap<String, Object> geneMap = new LinkedHashMap<>();
                String markerName = jsonArray.get(i).get("geneName").toString();
                String allele = jsonArray.get(i).get("allele").toString();
                String wdlist = allele.replaceAll(",", "/");
                geneMap.put("name",markerName);
                geneMap.put("value",wdlist);
                geneList.add(geneMap);
            }
            geneInfos = JSON.toJSONString(geneList);
        }
        return geneInfos;
    }

    /*
     *  前端格式
     *        geneInfo="[{\"markerName\":\"D7S820\",\"allele\":[{\"name\":\"18\"}]},{\"markerName\":\"vWA\",\"allele\":[{\"name\":\"14\"}]},{\"markerName\":\"FGA\",\"allele\":[{\"name\":\"25\"},{\"name\":\"20\"},{\"name\":\"21\"}]}]"
     *   目标基因格式
     *       {"AMELOGENIN":["X","Y"],"ABOGroup":[""],"D3S1358":["15","16"],"vWA":["14","17","20"]}
     *
    * */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Map<String, List<String>> NewmarkerNameGene(String geneInfo){
        if (geneInfo != null){
            HashMap<String, List<String>> geneMap = new LinkedHashMap<>();
            //用来存储到数据库的格式
            List<Map<String, Object>> jsonArray = (List) JSONObject.parseArray(geneInfo);
            //对前台给的json字符串进行格式转换
            for (int i = 0; i < jsonArray.size(); i++) {

                //用来存放位点
                List<String> blist= new ArrayList<>();
                String markerName = jsonArray.get(i).get("markerName").toString();
                List<Map<String, String>> list = (List) jsonArray.get(i).get("allele");
                for (int j = 0; j < list.size(); j++) {
                    String allele = list.get(j).get("name");
                    blist.add(allele);

                }
                geneMap.put(markerName, blist);
            }
            return geneMap;
        }
        return null;
    }

    /**
     * 存储基因信息格式转换
     *     后天格式：
     *     [{"name": "AMELOGENIN", "value": "X/Y"}, {"name": "ABOGroup", "value": ""}, {"name": "D3S1358", "value": "16/18"}, {"name": "vWA", "value": "14/14"}, {"name": "FGA", "value": "21/21"}, {"name": "D8S1179", "value": "11/16"}, {"name": "D21S11", "value": "32/32.2"}, {"name": "D18S51", "value": "16/21"}, {"name": "D5S818", "value": "10/11"}, {"name": "D13S317", "value": "8/11"}, {"name": "D7S820", "value": ""}, {"name": "TPOX", "value": ""}, {"name": "CSF1PO", "value": "12/13"}, {"name": "TH01", "value": ""}, {"name": "D16S539", "value": "11/11"}, {"name": "D2S1338", "value": "19/19"}, {"name": "D19S433", "value": "13/17.2"}, {"name": "FESFPS", "value": ""}, {"name": "F13A01", "value": ""}, {"name": "PentaD", "value": ""}, {"name": "PentaE", "value": ""}, {"name": "D6S1043", "value": "18/19"}, {"name": "D1S1656", "value": ""}, {"name": "D12S391", "value": "20/20.3"}]
     *    目标类型 ;
     *      {"AMELOGENIN":["X","Y"],"ABOGroup":[""],"D3S1358":["15","16"],"vWA":["14","17","20"]}
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Map<String, List<String>> NewgetGeneInfoString(String geneInfo) {
        if (geneInfo != null) {
            //基因型转化
            List<Map<String, Object>> jsonArray = (List) JSONObject.parseArray(geneInfo);
            //用来存储到数据库的格式
            HashMap<String, List<String>> geneMap = new LinkedHashMap<>();
            //对前台给的json字符串进行格式转换
            for (int i = 0; i < jsonArray.size(); i++) {
                List<String> list = new ArrayList<>();
                String markerName = jsonArray.get(i).get("name").toString();
                String allele = jsonArray.get(i).get("value").toString();
                String[] allele1 = allele.split("/");
                for (String vlue: allele1){
                    list.add(vlue);
                }
                geneMap.put(markerName, list);
            }
            return geneMap;
        }
       return null;
    }

    /*
   *   库里的基因类型转换给前端
   *   后台格式
   *        [{"name": "D8S1179", "value": "11/12"}, {"name": "D2S11", "value": "9/10"} ......]
   *   目标基因格式
   *        {markerName: "D8S1179", allele: “12,13,14”}
   * */
    public static Map<String, Object> analysisMarkerNameGene(List<Map<String, Object>> jsonArray){
        Map<String, Object> resultMap = new HashMap<>();
        if (ListUtils.isNotNullAndEmptyList(jsonArray)){
            List<Map<String, Object>> geneList = new ArrayList<>();
            //用来存储到数据库的格式
            //对前台给的json字符串进行格式转换
            for (int i = 0; i < jsonArray.size(); i++) {
                LinkedHashMap<String, Object> geneMap = new LinkedHashMap<>();
                String markerName = jsonArray.get(i).get("name").toString();
                String value = jsonArray.get(i).get("value").toString();
                String wdlist = value.replaceAll("/", ",");
                geneMap.put("markerName", markerName);
                geneMap.put("allele", wdlist);
                geneList.add(geneMap);
            }
            resultMap.put("resultList",geneList);
        }
        return resultMap;
    }

    /*
   *   库里的基因类型转换给前端
   *   后台格式
   *        [{"name": "D8S1179", "value": "11/12"}, {"name": "D2S11", "value": "9/10"} ......]
   *   目标基因格式
   *        {markerName: "D8S1179", allele: “[12,13,14]”}
   * */
    @SuppressWarnings({ "unchecked" })
    public static String alleleMarkerNameGene(String geneInfo){
        String s = null;
        if (StringUtils.isNotBlank(geneInfo)){
            //转化为有序列的集合
            List<Map<String, Object>> jsonArray = JSON.parseObject(geneInfo, List.class);
            if (ListUtils.isNotNullAndEmptyList(jsonArray)){
                List<Map<String, Object>> geneList = new ArrayList<>();
                //用来存储到数据库的格式
                //对前台给的json字符串进行格式转换
                for (int i = 0; i < jsonArray.size(); i++) {
                    LinkedHashMap<String, Object> geneMap = new LinkedHashMap<>();
                    String markerName = jsonArray.get(i).get("name").toString();
                    String value = jsonArray.get(i).get("value").toString();
                    String[] split = value.split("/");
                    geneMap.put("name", markerName);
                    geneMap.put("value", split);
                    geneList.add(geneMap);
                }
                s = JSON.toJSONString(geneList);
            }
        }
        return s;
    }

    /**
     *  库里的基因类型转换给前端
     *   后台格式
     *        [{"name": "D8S1179", "value": "11/12"}, {"name": "D2S11", "value": "9/10"} ......]
     *   目标基因格式
     *        {markerName: "D8S1179", allele: [{name: "130"}, {name: "140"}, {name: "160"}]}
     * */
    @SuppressWarnings({ "unchecked" })
    public static Map<String, Object> alleleNameGene(String geneInfo) {
        List<Map<String, Object>> geneList = new ArrayList<>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //获取字符串的第一个字符
        String geneInfos = geneInfo.substring(0, 1);
        if ("[".equals(geneInfos)) {
            try {
                //转化为有序列的集合
                List<Map<String, Object>> sampleGeneInfo = JSON.parseObject(geneInfo, List.class);
                if (sampleGeneInfo != null){
                    //转换格式
                    for (int i = 0; i < sampleGeneInfo.size(); i++) {
                        LinkedHashMap<String, Object> geneMap = new LinkedHashMap<>();
                        List<Map<String, Object>> alleleList = new ArrayList<>();
                        String markerName = sampleGeneInfo.get(i).get("name").toString();
                        String value = sampleGeneInfo.get(i).get("value").toString();
                        String[] split = value.split("/");
                        for (String wdlist : split){
                            LinkedHashMap<String, Object> alleleMap = new LinkedHashMap<>();
                            alleleMap.put("name",wdlist);
                            alleleList.add(alleleMap);
                        }
                        geneMap.put("markerName", markerName);
                        geneMap.put("allele", alleleList);
                        geneList.add(geneMap);
                    }
                    resultMap.put("resultList",geneList);
                }
            } catch (Exception ex) {
                logger.error("数据库存储基因格式转换错误！", ex);
            }
        }
        return resultMap;
    }

    /*
   *   前端传来的基因准换为格式入库
   *   后台格式
   *        {markerName: "D8S1179", allele: “12,13,14”}
   *   目标基因格式
   *        [{"name": "D8S1179", "value": "11/12"}, {"name": "D2S11", "value": "9/10"} ......]
   * */
    @SuppressWarnings("unchecked")
	public static String replaceNameGene(String geneInfo){
        String geneInfos = null;
        if (StringUtils.isNotBlank(geneInfo)){
            //转化为有序列的集合
            List<Map<String, Object>> jsonArray = JSON.parseObject(geneInfo, List.class);
            if (ListUtils.isNotNullAndEmptyList(jsonArray)){
                List<Map<String, Object>> geneList = new ArrayList<>();
                //用来存储到数据库的格式
                //对前台给的json字符串进行格式转换
                for (int i = 0; i < jsonArray.size(); i++) {
                    LinkedHashMap<String, Object> geneMap = new LinkedHashMap<>();
                    String markerName = jsonArray.get(i).get("markerName").toString();
                    String value = jsonArray.get(i).get("allele").toString();
                    String wdlist = value.replaceAll(",", "/");
                    geneMap.put("name", markerName);
                    geneMap.put("value", wdlist);
                    geneList.add(geneMap);
                }
                geneInfos = JSON.toJSONString(geneList);
            }
        }
        return geneInfos;
    }

    /**
     * 存储基因信息格式转换
     *     后台格式：
     *      [{"name": "D8S1179", "value": "11/12"}, {"name": "D2S11", "value": "9/10"}]
     *    目标类型 ;
     *      {"AMELOGENIN":["X","Y"],"ABOGroup":[""],"D3S1358":["15","16"],"vWA":["14","17","20"]}
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static String getGeneInfoString(String geneInfo) {
        String geneInfos = null;
        if (geneInfo != null) {
            //基因型转化
            List<Map<String, Object>> jsonArray = (List) JSONObject.parseArray(geneInfo);
            //用来存储到数据库的格式
            LinkedHashMap<String, Object> geneMap = new LinkedHashMap<>();
            //对前台给的json字符串进行格式转换
            for (int i = 0; i < jsonArray.size(); i++) {
                String markerName = jsonArray.get(i).get("name").toString();
                String allele = jsonArray.get(i).get("value").toString();
                String[] allele1 = allele.split("/");
                geneMap.put(markerName, allele1);
            }
            geneInfos = JSON.toJSONString(geneMap);
        }
        return geneInfos;
    }

    //转换格式{"AMELOGENIN":[""],"D8S1179":["130","140","160"],"D21S11":["280","290","3
    //完成格式 [{markerName:AMELOGENIN,allele:[{name:}]},{markerName:D8S1179,
    @SuppressWarnings({ "unchecked" })
    public static String ngetGeneInfoString(String geneInfo) {
        List<Map<String, Object>> list = new ArrayList<>();
        LinkedHashMap<String, List<String>> sampleGeneInfo = JSON.parseObject(geneInfo, LinkedHashMap.class);

        Map<String, Object> map =null;
        for (Map.Entry<String, List<String>> srcEntry : sampleGeneInfo.entrySet()) {
            map = new LinkedHashMap<>();
            List<HashMap<String, String>> alleleList = new ArrayList<>();
            map.put("markerName", srcEntry.getKey());
            for (String v: srcEntry.getValue()) {
                HashMap<String, String> resultMap = new HashMap<>();
                resultMap.put("name",v.toString());
                alleleList.add(resultMap);
            }
            map.put("allele", alleleList);
            list.add(map);
        }


        return JSON.toJSONString(list);
    }

    //转换格式{"AMELOGENIN":[""],"D8S1179":["130","140","160"],"D21S11":["280","290","3
    //完成格式 [{\"name\": \"D8S1179\", \"value\": \"[11,12]\"}, {\"name\": \"D2S11\", \"value\": \"[9,10]\"} ......]
    @SuppressWarnings({ "unchecked" })
    public static String ngetGeneInfoStringOr(String geneInfo) {
        List<Map<String, Object>> list = new ArrayList<>();
        LinkedHashMap<String, List<String>> sampleGeneInfo = JSON.parseObject(geneInfo, LinkedHashMap.class);

        Map<String, Object> map =null;

        for (Map.Entry<String, List<String>> srcEntry : sampleGeneInfo.entrySet()) {
            map = new LinkedHashMap<>();
            map.put("name", srcEntry.getKey());
            String vs = "";
            for (String v: srcEntry.getValue()) {
                vs =vs+v+",";
            }
            map.put("value", vs.substring(0,vs.length() - 1));
            list.add(map);
        }


        return JSON.toJSONString(list);
    }

}
