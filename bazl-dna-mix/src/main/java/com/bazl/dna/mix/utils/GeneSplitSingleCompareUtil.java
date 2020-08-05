package com.bazl.dna.mix.utils;

import com.bazl.dna.mix.model.po.SingleSampleGene;
import com.bazl.dna.mix.model.po.SplitedSampleGene;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Sun on 2019/4/1.
 */
@Service
public class GeneSplitSingleCompareUtil {

    //本案匹配下限
    @Value("${minSameMixCount}")
    private int minSameMixCount;

    @Autowired
    MarkerLRCalculation markerLRCalculation;

    @Autowired
    GeneMixCompareUtil geneMixCompareUtil;
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(GeneSameCompareUtil.class);

    /**
     * 同型比对
     *
     * @param srcGeneInfo 待比对基因型
     * @param tarGeneInfo 被比样本基因型
     * @return
     */
    public Map<String, Object> compare(String srcGeneInfo, String tarGeneInfo, int matchLimit) {
//        Map<String,String> srcGeneInfotemp = CommonUtils.convertGenoTypeString(srcGeneInfo);
        Map<String, List<String>> srcResult = null;
        try {
            srcResult = GeneformatUtils.mixedSampleGeneformat(srcGeneInfo);
//            srcResult = removeAmel(srcResult);
        } catch (Exception ex) {
            logger.error("解析比对基因分型信息错误!", ex);
            return null;
        }
//        Map<String,String> tarGeneInfotemp = CommonUtils.convertGenoTypeString(tarGeneInfo);
        Map<String, List<String>> tarResult = null;
        try {
            tarResult = GeneformatUtils.mixedSampleGeneformat(tarGeneInfo);
//            tarResult = removeAmel(tarResult);
        } catch (Exception ex) {
            logger.error("解析比对基因分型信息错误！ ", ex);
            return null;
        }
        if (srcResult == null || tarResult == null) {
            return null;
        }
        return compareResult(srcResult, tarResult, matchLimit);
    }

    /**
     * @param srcGeneMap 待比对基因型
     * @param tarGeneMap 被比样本基因型
     * @return
     */
    public Map<String, Object> compareResult(Map<String, List<String>> srcGeneMap, Map<String, List<String>> tarGeneMap, int matchLimit) {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        if (matchLimit == 0) {
            matchLimit = minSameMixCount;
        }

        int sameCount = 0;  /*比中数*/
        int diffCount = 0;  /*容差数*/
        Double probability = 1.0;

        Map<String, List<String>> sameGeneMap = new HashMap<>();
        Map<String, String> sameGeneLRMap = new HashMap<>();
        List<Map<String, Object>> resultList = new ArrayList<>();
        //进行循环比对
        for (Map.Entry<String, List<String>> srcEntry : srcGeneMap.entrySet()) {
            String markerName = srcEntry.getKey();
            List<String> srcAlleleList = srcEntry.getValue();
            List<String> tarAlleleList = tarGeneMap.get(markerName);
            //基因位点比对
            if (ListUtils.isNotNullAndEmptyList(srcAlleleList) && ListUtils.isNotNullAndEmptyList(tarAlleleList)) {
                String allele1 = "";
                String allele2 = "";
                String geneStr1 = "";
                String geneStr2 = "";
                if (srcAlleleList.size() == 1) {
                    allele1 = srcAlleleList.get(0).toString();
                    allele2 = srcAlleleList.get(0).toString();
                } else if (srcAlleleList.size() > 1) {
                    allele1 = srcAlleleList.get(0).toString();
                    allele2 = srcAlleleList.get(1).toString();
                }

                if (StringUtils.isNotBlank(allele1) && StringUtils.isNotBlank(allele2)) {
                    if (allele1.equals(allele2)) {
                        geneStr1 = allele1;
                    } else {
                        geneStr1 = allele1 + "/" + allele2;
                    }
                }

                if (tarAlleleList.size() == 1) {
                    geneStr2 = tarAlleleList.get(0).toString();
                } else if (tarAlleleList.size() > 1) {
                    if (StringUtils.isNotBlank(tarAlleleList.get(0).toString()) && StringUtils.isNotBlank(tarAlleleList.get(1).toString())) {
                        if (tarAlleleList.get(0).toString().equals(tarAlleleList.get(1).toString())) {
                            geneStr2 = tarAlleleList.get(0).toString();
                        } else {
                            geneStr2 = tarAlleleList.get(0).toString() + "/" + tarAlleleList.get(1).toString();
                        }
                    }
                }

                Map<String, Object> map = new LinkedHashMap<>();

                Double alleleFreqs = Double.NaN;
                /*if (srcAlleleList.size() == tarAlleleList.size() && geneStr1.equals(geneStr2)) {
                    sameCount++;
                    sameGeneMap.put(markerName, srcAlleleList);

                    alleleFreqs = markerLRCalculation.calculateSingleMarkerLR(null, markerName, allele1, allele2);
                    if (!Double.isNaN(alleleFreqs)) {
                        probability = probability * alleleFreqs;
                        sameGeneLRMap.put(markerName, alleleFreqs.toString());
                    }

                } else {
                    diffCount++;
                    map.put("type", "abnormal");
                }*/

                //判断混合样本是否包含比对的样本
                if (geneMixCompareUtil.isContainsGeneAll(srcAlleleList, tarAlleleList)) {
                    sameCount++;
                    sameGeneMap.put(markerName, srcAlleleList);

                    alleleFreqs = markerLRCalculation.calculateSingleMarkerLR(null, markerName, allele1, allele2);
                    if (!Double.isNaN(alleleFreqs)) {
                        probability = probability * alleleFreqs;
                        sameGeneLRMap.put(markerName, alleleFreqs.toString());
                    }
                } else {
                    diffCount++;
                    map.put("type", "abnormal");
                }

                map.put("markerName", markerName);
                map.put("geneStr1", geneStr1);
                map.put("geneStr2", geneStr2);
                map.put("alleleFreqs", String.format("%.3f", alleleFreqs));
                resultList.add(map);
            }
        }

        if (sameGeneLRMap != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                String geneLRStr = mapper.writeValueAsString(sameGeneLRMap);
                resultMap.put("geneLRStr", geneLRStr);
            } catch (JsonProcessingException e) {
                logger.error("转换基因分型比中频率信息错误！", e);
                e.printStackTrace();
            }
        }
        resultMap.put("resultList", resultList);
        resultMap.put("sameCount", sameCount);
        resultMap.put("diffCount", diffCount);
        resultMap.put("probability", probability);
        if (sameCount < matchLimit) {
            resultMap.put("matched", Boolean.FALSE);
        } else {
            resultMap.put("matched", Boolean.TRUE);
        }
        return resultMap;
    }

    public Map<String, Object> compareGeneInfo(SplitedSampleGene s1, SingleSampleGene s2, int matchLimit) {
        String geneStr1 = s1.getGeneInfo();
        String geneStr2 = s2.getGeneInfo();

        if (matchLimit == 0) {
            matchLimit = minSameMixCount;
        }

        Map<String, Object> geneDetail = compare(geneStr1, geneStr2, matchLimit);

        if (geneDetail.isEmpty()) {
            return null;
        }

        return geneDetail;
    }
}
