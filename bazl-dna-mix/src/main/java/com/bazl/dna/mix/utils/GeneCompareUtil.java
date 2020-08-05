package com.bazl.dna.mix.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wanghaiyang
 * @date 2019/4/1.
 */
@Service
public class GeneCompareUtil {

    //混合匹配下限
    @Value("${minSameMixCount}")
    private int minSameMixCount;

    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(GeneCompareUtil.class);

    /**
     * 自动比对
     * @param geneStr1
     * @param geneStr2
     * @param mixSameCount
     * @return
     */
    public  Map<String, Object> compareGeneInfo(String geneStr1, String geneStr2, int mixSameCount) {

        if (mixSameCount == 0) {
            mixSameCount = minSameMixCount;
        }
        Map<String, Object> geneDetail =null;
        if (StringUtils.isNotBlank(geneStr1)&&StringUtils.isNotBlank(geneStr2)){
            geneDetail = compare(geneStr1, geneStr2, mixSameCount);

        }
        /*if(geneDetail.isEmpty()) {
            return false;
        }*/

        return geneDetail;
    }

    /**
     * 同型比对
     * @param srcGeneInfo	     待比对基因型
     * @param tarGeneInfo	     被比样本基因型
     * @return
     */
    public Map<String, Object> compare(String srcGeneInfo, String tarGeneInfo, int matchLimit) {
        Map<String, List<String>> srcResult = null;
        try {
            srcResult =GeneformatUtils.mixedSampleGeneformat(srcGeneInfo);
//            srcResult =(Map) JSON.parse(CommonUtils.transfromGeneFormat(srcGeneInfotemp));
        } catch (Exception ex) {
            logger.error("解析比对基因分型信息错误！", ex);
            return null;
        }
        Map<String, List<String>> tarResult = null;

        try {
            tarResult =GeneformatUtils.mixedSampleGeneformat(tarGeneInfo);
//            tarResult = removeAmel(tarResult);
        } catch (Exception ex) {
            logger.error("解析比对基因分型信息错误！", ex);
            return null;
        }
        if(srcResult == null || tarResult == null){
            return null;
        }
        return compareResult(srcResult, tarResult, matchLimit);
    }


    /**
     * @param srcGeneMap		 待比对基因型
     * @param tarGeneMap	     被比样本基因型
     * @return
     */
    public Map<String, Object> compareResult(Map<String, List<String>> srcGeneMap, Map<String, List<String>> tarGeneMap, int matchLimit){
        Map<String, Object> resultMap = new HashMap<String, Object>();
        //目标库的样本位点个数
        int sumCount = srcGeneMap.size();
        resultMap.put("sumCount", sumCount);

        int sameCount = 0;  /*比中数*/
        int diffCount = 0;  /*容差数*/
//        Double probability = 1.0;

        //进行循环比对
        for (Map.Entry<String, List<String>> srcEntry : srcGeneMap.entrySet()) {
            String markerName = srcEntry.getKey();
            List<String> srcAlleleList = srcEntry.getValue();
            List<String> tarAlleleList = tarGeneMap.get(markerName);
            if(null != srcAlleleList && srcAlleleList.size()>0 && null != tarAlleleList && tarAlleleList.size() >0){
                if (!srcAlleleList.get(0).equals("") && !tarAlleleList.get(0).equals("")){
                    if(isContainsAll(srcAlleleList, tarAlleleList)){
                        sameCount++;
                    }else {
                        diffCount++;
                    }
                }
            }
        }

        resultMap.put("sameCount", sameCount);
        resultMap.put("diffCount", diffCount);
        //匹配下线和容差同时满足
        if(sameCount < matchLimit){
            resultMap.put("matched", Boolean.FALSE);
        } else{
            resultMap.put("matched", Boolean.TRUE);
        }
        return resultMap;
    }

    /**
     * 判断混合分型是否包含此分型
     * @param srcAlleleList
     * @param tarAlleleList
     * @return
     */
    public boolean isContainsAll(List<String> srcAlleleList, List<String> tarAlleleList) {
        boolean flag = true;

        String isExist = null;
        if (ListUtils.isNotNullAndEmptyList(srcAlleleList)) {
            for (String tarStr : tarAlleleList) {
                if (srcAlleleList.contains(tarStr)) {
                    //此处指的是将与tarAlleleList中重复的元素删除
//                    srcAlleleList.remove(tarStr);
                    continue;
                }else {
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
     * 判断否是重复数据  公共方法
     *
     * @param geneInfo1
     * @param geneInfo2
     * @return
     */
    public boolean repeatingUtils(String geneInfo1, String geneInfo2) {
        boolean isFinish = true;
        //对数据库基因信息转为Map对象
        Map<String, Object> geneMap1 = JSON.parseObject(GeneSameCompareUtil.getGeneInfoString(geneInfo1));
        Map<String, Object> geneMap2 = JSON.parseObject(GeneSameCompareUtil.getGeneInfoString(geneInfo2));
        //首先判断两个map的key的数量是否相同，不相同的话，统计循环次数，跳出循环
        if (geneMap1.keySet().size() != geneMap2.keySet().size()) {
            isFinish = false;
            return isFinish;
        } else {
            //如果相同那么循环比较各个value值
            for (String key : geneMap1.keySet()) {
                //如果前台的key在数据库的基因信息不存在则跳出循环
                if (geneMap2.get(key) == null) {
                    isFinish = false;
                    return isFinish;
                } else {
                    //判断基因value值
                    if (match(geneMap1.get(key).toString(), geneMap2.get(key).toString())) {
                        continue;
                    } else {
                        isFinish = false;
                        return isFinish;
                    }
                }
            }

        }
        return isFinish;
    }

    /**
     * 判断否是重复数据  公共方法
     *
     * @param geneInfo1
     * @param geneInfo2
     * @return
     */
    public boolean NewrepeatingUtils(String geneInfo1, String geneInfo2) {
        boolean isFinish = true;
        //对数据库基因信息转为Map对象
        Map<String, Object> geneMap1 = JSON.parseObject(geneInfo1);
        Map<String, Object> geneMap2 = JSON.parseObject(geneInfo2);
        //首先判断两个map的key的数量是否相同，不相同的话，统计循环次数，跳出循环
        if (geneMap1.keySet().size() != geneMap2.keySet().size()) {
            isFinish = false;
            return isFinish;
        } else {
            //如果相同那么循环比较各个value值
            for (String key : geneMap1.keySet()) {
                //如果前台的key在数据库的基因信息不存在则跳出循环
                if (geneMap2.get(key) == null) {
                    isFinish = false;
                    return isFinish;
                } else {
                    //判断基因value值
                    if (match(geneMap1.get(key).toString(), geneMap2.get(key).toString())) {
                        continue;
                    } else {
                        isFinish = false;
                        return isFinish;
                    }
                }
            }

        }
        return isFinish;
    }
    /**
     * 不考虑字符串顺序，判断字符串是否相同  公共方法
     *
     * @param o
     * @param o1
     * @return
     */
    private static boolean match(String o, String o1) {
        boolean flag = false;
        String[] split = o.split(",");
        String[] split1 = o1.split(",");
        Arrays.sort(split);
        Arrays.sort(split1);
        if (split.length == split1.length) {
            if (Arrays.equals(split, split1)) {
                flag = true;
            }
        }
        return flag;
    }

}
