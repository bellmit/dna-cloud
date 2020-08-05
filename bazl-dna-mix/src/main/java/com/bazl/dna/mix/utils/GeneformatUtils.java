package com.bazl.dna.mix.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.parser.Feature;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author: lzn
 * @Date: 2019/8/13 14:20
 * @Version: 1.0
 * 公共lims基因格式转换
 */
@Service
public class GeneformatUtils {

    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(GeneformatUtils.class);

    /**
     * 混合基因和单一基因lims格式转换
     *
     * @param geneInfo
     * @return
     */
    @SuppressWarnings("unchecked")
	public static Map<String, List<String>> mixedSampleGeneformat(String geneInfo) {
       // JSONObject jsonObj = new JSONObject(true);
       // Map<String, List<String>> sampleGeneInfo = new LinkedHashMap<>();
        LinkedHashMap<String, List<String>> sampleGeneInfo = null;
        if (StringUtils.isNotBlank(geneInfo)){
            try {
                //获取字符串的第一个字符
                String geneInfos = geneInfo.substring(0, 1);
                if ("{".equals(geneInfos)) {
                    try {
//                    sampleGeneInfo = (Map) JSON.parse(geneInfo);
                        //转化为有序列的集合
                        sampleGeneInfo = JSON.parseObject(geneInfo, LinkedHashMap.class);
                        //sampleGeneInfo = jsonObj.parseObject(geneInfo, LinkedHashMap.class);
                    } catch (Exception ex) {
                        logger.error("解析新的基因分型信息错误！", ex);
                    }
                } else if ("[".equals(geneInfos)) {
                    try {
                        //老lims基因数据格式转换
                        Map<String, String> temp = CommonUtils.convertGenoTypeString(geneInfo);
//                    sampleGeneInfo = (Map) JSON.parse(CommonUtils.transfromGeneFormat(temp));
                        sampleGeneInfo = JSON.parseObject(CommonUtils.transfromGeneFormat(temp), LinkedHashMap.class, Feature.OrderedField);
                    } catch (Exception ex) {
                        logger.error("解析老的的基因分型信息错误！", ex);
                    }
                }

            } catch (Exception e) {
                logger.error("混合基因和单一基因lims格式转换" + e);
            }
        }else {
            logger.info("混合基因或单一基因信息为空！");
        }
        return sampleGeneInfo;
    }
    
    /**
     * 混合基因 拆分报告解析
     */
    public static Map<String, List<String>> mixedSampleGeneformater(String geneInfo) {
    		Map<String, List<String>> result = new HashMap<String, List<String>>();
    		try {
    			
    			JSONArray jsonArray = JSONArray.parseArray(geneInfo);
    			for (int i = 0; i < jsonArray.size(); i++) {
                String markerName = jsonArray.getJSONObject(i).getString("name");
                String value = jsonArray.getJSONObject(i).getString("value");
                String wdlist = value.replaceAll("/", ",");
                List<String> list = new ArrayList<String>();
	        		for (String s : wdlist.split(",")) {
	        			list.add(s);
	        		}
                result.put(markerName, list);
            }
    			
        } catch (Exception e) {
            logger.error("混合基因 拆分报告解析" + e);
        }

        return result;
    }


    /**
     * 四川国家库基因信息格式转换
     */
    public String geneFormatConversionNew(String geneInfo, List<Map<String,String>> stringList) {
        LinkedHashMap<Object, Object> sampleGeneInfo = new LinkedHashMap<>();
       /* String geneInfo = "x/y;15/16/23;;;16/18;;";
        List<String> stringList = new ArrayList<>();
        stringList.add("a");
        stringList.add("b");
        stringList.add("c");
        stringList.add("d");
        stringList.add("e");
        stringList.add("f");*/
        String geneInfos = null;
        try {
            String[] split = geneInfo.split(";",-1);
            if (split.length>0){
                for (int i = 0;i<split.length &i<stringList.size();i++){
                    String s = split[i];
//                    if (!"".equals(s)){
                        String[] split1 = s.split("/");
                        sampleGeneInfo.put(stringList.get(i).get("locusName"),split1);
//                    }
                }
            }
            geneInfos = JSON.toJSONString(sampleGeneInfo);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("=======国家库基因信息转换失败========");
        }
        return geneInfos;
    }


    /**
     * 国家库基因信息格式转换
     *   目标类型
     *    [{"name": "D8S1179", "value": "[11,12]"}, {"name": "D2S11", "value": "[9,10]"} ......]
     */
    public String geneFormatList(String geneInfo, List<Map<String,String>> stringList) {
        List<Map<Object, Object>> geneList = new ArrayList<>();
        String geneInfos = null;
        try {
            String[] split = geneInfo.split(";",-1);
            if (split.length>0){
                for (int i = 0;i<split.length &i<stringList.size();i++){
                    LinkedHashMap<Object, Object> sampleGeneInfo = new LinkedHashMap<>();
                    sampleGeneInfo.put("name",stringList.get(i).get("locusName"));
                    String s = split[i];
                    String[] split1 = s.split("/");
                    sampleGeneInfo.put("value",split1);
                    geneList.add(sampleGeneInfo);
                }
            }
            geneInfos = JSON.toJSONString(geneList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("=======国家库基因信息转换失败========");
        }
        return geneInfos;
    }

    /**
     * 数据库存储基因格式转换
     */
    @SuppressWarnings("unchecked")
	public static Map<String, Object> sampleformatList(String geneInfo) {
        List<Map<String, Object>> sampleGeneInfo = null;
        Map<String, Object> stringObjectMap = null;
        try {
            //获取字符串的第一个字符
            String geneInfos = geneInfo.substring(0, 1);
            if ("[".equals(geneInfos)) {
                try {
                    //转化为有序列的集合
                    sampleGeneInfo = JSON.parseObject(geneInfo, List.class);
                    if (sampleGeneInfo != null){
                        //转换格式
                        stringObjectMap = GeneSameCompareUtil.analysisMarkerNameGene(sampleGeneInfo);
                    }
                } catch (Exception ex) {
                    logger.error("数据库存储基因格式转换错误！", ex);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("数据库存储基因格式转换错误！" + e.getMessage());
        }

        return stringObjectMap;
    }


    /**
     * 国家库基因信息格式转换
     *   目标类型 ;
     *    [{"name": "D8S1179", "value": "11/12"}, {"name": "D2S11", "value": "9/10"} ......]
     */
    public String geneFormatListTask(String geneInfo, List<Map<String,String>> stringList) {
        List<Map<Object, Object>> geneList = new ArrayList<>();
        String geneInfos = null;
        try {
            String[] split = geneInfo.split(";",-1);
            if (split.length>0){
                for (int i = 0;i<split.length &i<stringList.size();i++){
                    LinkedHashMap<Object, Object> sampleGeneInfo = new LinkedHashMap<>();
                    sampleGeneInfo.put("name",stringList.get(i).get("locusName"));
                    String s = split[i];
                    sampleGeneInfo.put("value",s);
                    geneList.add(sampleGeneInfo);
                }
            }
            geneInfos = JSON.toJSONString(geneList);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("=======国家库基因信息转换失败========");
        }
        return geneInfos;
    }

    public static void main(String[] args) {
        //" [{\"name\": \"D8S1179\", \"value\": \"[11,12]\"}, {\"name\": \"D2S11\", \"value\": \"[9,10]\"} ......]"
        String geneInfo="{\"AMELOGENIN\":[\"\"],\"D8S1179\":[\"130\",\"140\"],\"D21S11\":[\"280\",\"290\"],\"D7S820\":[\"110\",\"120\"],\"CSF1PO\":[\"120\",\"130\"],\"D3S1358\":[\"160\"],\"TH01\":[\"080\"],\"D13S317\":[\"080\",\"130\"],\"D16S539\":[\"090\"],\"D2S1338\":[\"190\"],\"D19S433\":[\"140\",\"150\"],\"vWA\":[\"160\",\"190\"],\"TPOX\":[\"080\"],\"D18S51\":[\"140\",\"170\"],\"D5S818\":[\"100\",\"110\"],\"FGA\":[\"210\",\"240\"]}";

        Map<String, Object> stringObjectMap = sampleformatList(geneInfo);
        System.out.println(stringObjectMap);
    }
}
