package com.bazl.dna.mix.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.mix.model.po.*;
import com.bazl.dna.mix.model.vo.RapiGeneComparisonVo;
import com.bazl.dna.mix.model.vo.SingleSampleGeneVo;
import com.bazl.dna.mix.model.vo.StrMixRapiGeneComparisonListVo;
import com.bazl.dna.mix.constants.Constants;
import com.bazl.dna.mix.service.SingleSampleGeneService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author wanghaiyang
 * @date 2019/4/1.
 */
@Service
public class GeneMixCompareUtil {

    @Autowired
    SingleSampleGeneService singleSampleGeneService;

    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(GeneMixCompareUtil.class);


    /**
     * @param srcGeneMap 待比对基因型
     * @param tarGeneMap 被比样本基因型
     * @param matchLimit
     * @return
     */
    public Map<String, Object> compareResult(Map<String, List<String>> srcGeneMap, Map<String, List<String>> tarGeneMap, int matchLimit) {
        Map<String, Object> resultMap = new HashMap<String, Object>();

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

            if (ListUtils.isNotNullAndEmptyList(srcAlleleList) && ListUtils.isNotNullAndEmptyList(tarAlleleList)) {
                //判断混合样本是否包含比对的样本
                if (isContainsGeneAll(srcAlleleList, tarAlleleList)) {
                    map = new LinkedHashMap<>();
                    srcAlleles = getAllele(srcAlleleList);
                    tarAlleles = getAllele(tarAlleleList);

                    map.put("markerName", markerName);
                    map.put("geneStr1", srcAlleles);
                    map.put("geneStr2", tarAlleles);
                    resultList.add(map);
                    sameCount++;
                } else {
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
        return resultMap;
    }

    /**
     *      判断基因是否比中   并标识 flag 1:比中。0：未比中
     * @param srcGeneMap 待比对基因型
     * @param tarGeneMap 被比样本基因型
     */
    public Map<String, Object> compareResultFlag(Map<String, List<String>> srcGeneMap, Map<String, List<String>> tarGeneMap, int matchLimit) {
        Map<String, Object> resultMap = new HashMap<String, Object>();

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

            if (ListUtils.isNotNullAndEmptyList(srcAlleleList) && ListUtils.isNotNullAndEmptyList(tarAlleleList)) {
                //判断混合样本是否包含比对的样本
                map = new LinkedHashMap<>();
                if (isContainsGeneAll(srcAlleleList, tarAlleleList)) {
                    srcAlleles = getAllele(srcAlleleList);
                    tarAlleles = getAllele(tarAlleleList);
                    map.put("markerName", markerName);
                    map.put("geneStr1", srcAlleles);
                    map.put("geneStr2", tarAlleles);
                    map.put("flag","1");
                    resultList.add(map);
                    sameCount++;
                } else {
                    srcAlleles = getAllele(srcAlleleList);
                    tarAlleles = getAllele(tarAlleleList);
                    map.put("markerName", markerName);
                    map.put("geneStr1", srcAlleles);
                    map.put("geneStr2", tarAlleles);
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
        return resultMap;
    }

    /**
     * 判断混合分型是否包含此分型
     *
     * @param srcAlleleList
     * @param tarAlleleList
     * @return
     */
    public boolean isContainsGeneAll(List<String> srcAlleleList, List<String> tarAlleleList) {
        boolean flag = true;

        String isExist = null;
        if (ListUtils.isNotNullAndEmptyList(srcAlleleList)) {
            for (String tarStr : tarAlleleList) {
                if (srcAlleleList.contains(tarStr)) {
                    //此处指的是将与tarAlleleList中重复的元素删除
//                    srcAlleleList.remove(tarStr);
                    continue;
                } else {
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
     * 混合快速strMix解析
     *
     * @param resultList
     * @param rapiGeneComparisonVo
     * @return
     */
    public Map<String, Object> compareStitchingResultStrMix(List<Map<String, Object>> resultList, RapiGeneComparisonVo rapiGeneComparisonVo) {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        //混合基因信息
        List<Map<String, Object>> mixedSampleGeneList = new ArrayList<>();

        Map<String, Object> map = null;

        //获取贡献者信息
        List<Contributor> contributorList = rapiGeneComparisonVo.getContributorList();
        try {
            //贡献者的数量
            int contributorCount = rapiGeneComparisonVo.getContributorCount();

            //贡献者和混合基因名称相同后返回的数据
            List<StrMixRapiGeneComparisonListVo> strMixRapiGeneComparisonListVos = new ArrayList<>();

            StrMixRapiGeneComparisonListVo strMixRapiGeneComparisonListVo = null;
            //快速比对的基因信息
            List<Map<String, Object>> moreThanTheGeneList0 = null;
            List<Map<String, Object>> moreThanTheGeneList1 = null;
            List<Map<String, Object>> moreThanTheGeneList2 = null;
            List<Map<String, Object>> moreThanTheGeneList3 = null;
            List<Map<String, Object>> moreThanTheGeneList4 = null;
            List<Map<String, Object>> moreThanTheGeneList5 = null;
            List<Map<String, Object>> moreThanTheGeneList6 = null;
            List<Map<String, Object>> moreThanTheGeneList7 = null;
            List<Map<String, Object>> moreThanTheGeneList8 = null;
            List<Map<String, Object>> moreThanTheGeneList9 = null;

            //从90%-99%的权重比
            MoreThanTheCount more0 = null;
            MoreThanTheCount more1 = null;
            MoreThanTheCount more2 = null;
            MoreThanTheCount more3 = null;
            MoreThanTheCount more4 = null;
            MoreThanTheCount more5 = null;
            MoreThanTheCount more6 = null;
            MoreThanTheCount more7 = null;
            MoreThanTheCount more8 = null;
            MoreThanTheCount more9 = null;
            //基因信息list
            List<ContributorGene> contributorGeneVoList = null;
            //基因信息
            ContributorGene contributorGenes = null;
            //混合基因分型
            String mixedSampleGeneAlleles = null;

            //每个贡献者大于90的集合
            List<MoreThanTheCount> moreThanTheCountList = null;

            if (ListUtils.isNotNullAndEmptyList(contributorList)) {
                //循环贡献者的信息
                for (int i = 0; i < contributorList.size(); i++) {
                    // 获取贡献者的占比
                    String contributorWeight = null;
                    // 获取贡献者的名字
                    String contributorName = null;
                    //每个贡献者大于90的集合
                    moreThanTheCountList = new ArrayList<>();
                    //每个贡献者大于99%的位点数量
                    int moreThanTheCount1 = 0;
                    int moreThanTheCount2 = 0;
                    int moreThanTheCount3 = 0;
                    int moreThanTheCount4 = 0;
                    int moreThanTheCount5 = 0;
                    int moreThanTheCount6 = 0;
                    int moreThanTheCount7 = 0;
                    int moreThanTheCount8 = 0;
                    int moreThanTheCount9 = 0;
                    int moreThanTheCount10 = 0;

                    contributorGeneVoList = new ArrayList<>();
                    //快速比对的基因信息
                    moreThanTheGeneList0= new ArrayList<>();
                    moreThanTheGeneList1 = new ArrayList<>();
                    moreThanTheGeneList2 = new ArrayList<>();
                    moreThanTheGeneList3 = new ArrayList<>();
                    moreThanTheGeneList4 = new ArrayList<>();
                    moreThanTheGeneList5 = new ArrayList<>();
                    moreThanTheGeneList6 = new ArrayList<>();
                    moreThanTheGeneList7 = new ArrayList<>();
                    moreThanTheGeneList8 = new ArrayList<>();
                    moreThanTheGeneList9 = new ArrayList<>();
                    //进行循环比对
                    for (Map<String, Object> srcEntry : resultList) {
                        String markerName = srcEntry.get("markerName").toString();

                        //获取贡献者的基因信息
                        List<ContributorGene> contributorGeneList = contributorList.get(i).getContributorGeneList();

                        contributorWeight = contributorList.get(i).getContributorWeight();
                        contributorName = contributorList.get(i).getContributorName();

                        if (ListUtils.isNotNullAndEmptyList(contributorGeneList)) {
                            for (ContributorGene contributorGene : contributorGeneList) {

                                //判断基因名称是否相等
                                if (contributorGene.getGeneName().equals(markerName)) {
                                    //混合基因分型
                                    mixedSampleGeneAlleles = srcEntry.get("allele").toString();
                                    //基因信息
                                    List<GeneAllele> geneAlleleList = contributorGene.getGeneAlleleList();

                                    contributorGenes = new ContributorGene();

                                    String weights = null;
                                    String digital = null;
                                    //循环基因位点
                                    for (int j = 0; j < geneAlleleList.size(); j++) {

                                        double dninetyNinePercentWeights;
                                        //获取比中百分比
                                        weights = geneAlleleList.get(j).getWeights();
                                        //去掉最后一个%
                                        digital = weights.substring(0, weights.length() - 1);
                                        dninetyNinePercentWeights = Double.parseDouble(digital);
                                        //判断当前百分比大于自定义的权重百分比
                                        if (dninetyNinePercentWeights > Constants.FDNINETY_NINEPERCENT_WEIGHTS_90) {
                                            geneAlleleList.get(j).setNinetyNinePercentWeights(geneAlleleList.get(j).getAllele());
                                            map = new LinkedHashMap<>();
                                            map.put("geneName", markerName);
                                            map.put("allele", geneAlleleList.get(j).getAllele());
                                            moreThanTheGeneList0.add(map);
                                            moreThanTheCount1++;
                                        }
                                        //判断当前百分比大于自定义的权重百分比
                                        if (dninetyNinePercentWeights > Constants.FDNINETY_NINEPERCENT_WEIGHTS_91) {
                                            geneAlleleList.get(j).setNinetyNinePercentWeights(geneAlleleList.get(j).getAllele());
                                            map = new LinkedHashMap<>();
                                            map.put("geneName", markerName);
                                            map.put("allele", geneAlleleList.get(j).getAllele());
                                            moreThanTheGeneList1.add(map);
                                            moreThanTheCount2++;
                                        }
                                        //判断当前百分比大于自定义的权重百分比
                                        if (dninetyNinePercentWeights > Constants.FDNINETY_NINEPERCENT_WEIGHTS_92) {
                                            geneAlleleList.get(j).setNinetyNinePercentWeights(geneAlleleList.get(j).getAllele());
                                            map = new LinkedHashMap<>();
                                            map.put("geneName", markerName);
                                            map.put("allele", geneAlleleList.get(j).getAllele());
                                            moreThanTheGeneList2.add(map);
                                            moreThanTheCount3++;
                                        }
                                        //判断当前百分比大于自定义的权重百分比
                                        if (dninetyNinePercentWeights > Constants.FDNINETY_NINEPERCENT_WEIGHTS_93) {
                                            geneAlleleList.get(j).setNinetyNinePercentWeights(geneAlleleList.get(j).getAllele());
                                            map = new LinkedHashMap<>();
                                            map.put("geneName", markerName);
                                            map.put("allele", geneAlleleList.get(j).getAllele());
                                            moreThanTheGeneList3.add(map);
                                            moreThanTheCount4++;
                                        }
                                        //判断当前百分比大于自定义的权重百分比
                                        if (dninetyNinePercentWeights > Constants.FDNINETY_NINEPERCENT_WEIGHTS_94) {
                                            geneAlleleList.get(j).setNinetyNinePercentWeights(geneAlleleList.get(j).getAllele());
                                            map = new LinkedHashMap<>();
                                            map.put("geneName", markerName);
                                            map.put("allele", geneAlleleList.get(j).getAllele());
                                            moreThanTheGeneList4.add(map);
                                            moreThanTheCount5++;
                                        }
                                        //判断当前百分比大于自定义的权重百分比
                                        if (dninetyNinePercentWeights > Constants.FDNINETY_NINEPERCENT_WEIGHTS_95) {
                                            geneAlleleList.get(j).setNinetyNinePercentWeights(geneAlleleList.get(j).getAllele());
                                            map = new LinkedHashMap<>();
                                            map.put("geneName", markerName);
                                            map.put("allele", geneAlleleList.get(j).getAllele());
                                            moreThanTheGeneList5.add(map);
                                            moreThanTheCount6++;
                                        }
                                        //判断当前百分比大于自定义的权重百分比
                                        if (dninetyNinePercentWeights > Constants.FDNINETY_NINEPERCENT_WEIGHTS_96) {
                                            geneAlleleList.get(j).setNinetyNinePercentWeights(geneAlleleList.get(j).getAllele());
                                            map = new LinkedHashMap<>();
                                            map.put("geneName", markerName);
                                            map.put("allele", geneAlleleList.get(j).getAllele());
                                            moreThanTheGeneList6.add(map);
                                            moreThanTheCount7++;
                                        }
                                        //判断当前百分比大于自定义的权重百分比
                                        if (dninetyNinePercentWeights > Constants.FDNINETY_NINEPERCENT_WEIGHTS_97) {
                                            geneAlleleList.get(j).setNinetyNinePercentWeights(geneAlleleList.get(j).getAllele());
                                            map = new LinkedHashMap<>();
                                            map.put("geneName", markerName);
                                            map.put("allele", geneAlleleList.get(j).getAllele());
                                            moreThanTheGeneList7.add(map);
                                            moreThanTheCount8++;
                                        }
                                        //判断当前百分比大于自定义的权重百分比
                                        if (dninetyNinePercentWeights > Constants.FDNINETY_NINEPERCENT_WEIGHTS_98) {
                                            geneAlleleList.get(j).setNinetyNinePercentWeights(geneAlleleList.get(j).getAllele());
                                            map = new LinkedHashMap<>();
                                            map.put("geneName", markerName);
                                            map.put("allele", geneAlleleList.get(j).getAllele());
                                            moreThanTheGeneList8.add(map);
                                            moreThanTheCount9++;
                                        }
                                        //判断当前百分比大于自定义的权重百分比
                                        if (dninetyNinePercentWeights > Constants.FDNINETY_NINEPERCENT_WEIGHTS_99) {
                                            geneAlleleList.get(j).setNinetyNinePercentWeights(geneAlleleList.get(j).getAllele());
                                            map = new LinkedHashMap<>();
                                            map.put("geneName", markerName);
                                            map.put("allele", geneAlleleList.get(j).getAllele());
                                            moreThanTheGeneList9.add(map);
                                            moreThanTheCount10++;
                                        }
                                    }

                                    contributorGenes.setGeneAlleleList(geneAlleleList);
                                    contributorGenes.setGeneName(markerName);
                                    contributorGeneVoList.add(contributorGenes);
                                    map = new LinkedHashMap<>();
                                    map.put("markerName", markerName);
                                    map.put("geneStr1", mixedSampleGeneAlleles);

                                }
                            }

                        }

                        mixedSampleGeneList.add(map);
                    }
                    more0 = new MoreThanTheCount();
                    more0.setMoreThanThevalue(moreThanTheCount1);
                    more0.setPercent(Constants.FDNINETY_WEIGHTS_90);
                    moreThanTheCountList.add(more0);

                    more1 = new MoreThanTheCount();
                    more1.setMoreThanThevalue(moreThanTheCount2);
                    more1.setPercent(Constants.FDNINETY_WEIGHTS_91);
                    moreThanTheCountList.add(more1);

                    more2 = new MoreThanTheCount();
                    more2.setMoreThanThevalue(moreThanTheCount3);
                    more2.setPercent(Constants.FDNINETY_WEIGHTS_92);
                    moreThanTheCountList.add(more2);

                    more3 = new MoreThanTheCount();
                    more3.setMoreThanThevalue(moreThanTheCount4);
                    more3.setPercent(Constants.FDNINETY_WEIGHTS_93);
                    moreThanTheCountList.add(more3);

                    more4 = new MoreThanTheCount();
                    more4.setMoreThanThevalue(moreThanTheCount5);
                    more4.setPercent(Constants.FDNINETY_WEIGHTS_94);
                    moreThanTheCountList.add(more4);

                    more5 = new MoreThanTheCount();
                    more5.setMoreThanThevalue(moreThanTheCount6);
                    more5.setPercent(Constants.FDNINETY_WEIGHTS_95);
                    moreThanTheCountList.add(more5);

                    more6 = new MoreThanTheCount();
                    more6.setMoreThanThevalue(moreThanTheCount7);
                    more6.setPercent(Constants.FDNINETY_WEIGHTS_96);
                    moreThanTheCountList.add(more6);

                    more7 = new MoreThanTheCount();
                    more7.setMoreThanThevalue(moreThanTheCount8);
                    more7.setPercent(Constants.FDNINETY_WEIGHTS_97);
                    moreThanTheCountList.add(more7);

                    more8 = new MoreThanTheCount();
                    more8.setMoreThanThevalue(moreThanTheCount9);
                    more8.setPercent(Constants.FDNINETY_WEIGHTS_98);
                    moreThanTheCountList.add(more8);

                    more9 = new MoreThanTheCount();
                    more9.setMoreThanThevalue(moreThanTheCount10);
                    more9.setPercent(Constants.FDNINETY_WEIGHTS_99);
                    moreThanTheCountList.add(more9);

                    strMixRapiGeneComparisonListVo = new StrMixRapiGeneComparisonListVo();
                    strMixRapiGeneComparisonListVo.setContributorName(contributorName);
                    strMixRapiGeneComparisonListVo.setContributorWeight(contributorWeight);
                    strMixRapiGeneComparisonListVo.setMoreThanTheCountList(moreThanTheCountList);
                    strMixRapiGeneComparisonListVo.setContributorGeneList(contributorGeneVoList);
                    strMixRapiGeneComparisonListVo.setMoreGeneList0(moreThanTheGeneList0);
                    strMixRapiGeneComparisonListVo.setMoreGeneList1(moreThanTheGeneList1);
                    strMixRapiGeneComparisonListVo.setMoreGeneList2(moreThanTheGeneList2);
                    strMixRapiGeneComparisonListVo.setMoreGeneList3(moreThanTheGeneList3);
                    strMixRapiGeneComparisonListVo.setMoreGeneList4(moreThanTheGeneList4);
                    strMixRapiGeneComparisonListVo.setMoreGeneList5(moreThanTheGeneList5);
                    strMixRapiGeneComparisonListVo.setMoreGeneList6(moreThanTheGeneList6);
                    strMixRapiGeneComparisonListVo.setMoreGeneList7(moreThanTheGeneList7);
                    strMixRapiGeneComparisonListVo.setMoreGeneList8(moreThanTheGeneList8);
                    strMixRapiGeneComparisonListVo.setMoreGeneList9(moreThanTheGeneList9);
                    strMixRapiGeneComparisonListVos.add(strMixRapiGeneComparisonListVo);

                }
            }
            //list去除重复的数据
            for (int i = 0; i < mixedSampleGeneList.size() - 1; i++) {
                for (int j = mixedSampleGeneList.size() - 1; j > i; j--) {
                    if (mixedSampleGeneList.get(j).equals(mixedSampleGeneList.get(i))) {
                        mixedSampleGeneList.remove(j);
                    }
                }
            }
            resultMap.put("mixedSampleGeneList", mixedSampleGeneList);
            resultMap.put("contributorCount", contributorCount);
            resultMap.put("strMixRapiGeneComparisonListVos", strMixRapiGeneComparisonListVos);
        } catch (Exception e) {
            logger.error("混合快速strMix解析失败" + e.getMessage());
        }

        return resultMap;
    }

    /**
     * STRmix拆分报告解析接口
     * 贡献者的信息
     *
     * @param mixedSampleGeneInfo
     * @param rapiGeneComparisonVo
     * @param caseId               //案件id
     * @param geneId
     */
    public Map<String, Object> compareResultStrMix(Map<String, List<String>> mixedSampleGeneInfo,
                                                   RapiGeneComparisonVo rapiGeneComparisonVo, String caseId, String geneId) {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        //混合基因信息
        List<Map<String, Object>> mixedSampleGeneList = new ArrayList<>();

        Map<String, Object> map = null;

        //获取贡献者信息
        List<Contributor> contributorList = rapiGeneComparisonVo.getContributorList();
        try {
            //贡献者的数量
            int contributorCount = rapiGeneComparisonVo.getContributorCount();

            //贡献者和混合基因名称相同后返回的数据
            List<StrMixRapiGeneComparisonListVo> strMixRapiGeneComparisonListVos = new ArrayList<>();

            StrMixRapiGeneComparisonListVo strMixRapiGeneComparisonListVo = null;
            //快速比对的基因信息
            List<Map<String, Object>> moreThanTheGeneList0 = null;
            List<Map<String, Object>> moreThanTheGeneList1 = null;
            List<Map<String, Object>> moreThanTheGeneList2 = null;
            List<Map<String, Object>> moreThanTheGeneList3 = null;
            List<Map<String, Object>> moreThanTheGeneList4 = null;
            List<Map<String, Object>> moreThanTheGeneList5 = null;
            List<Map<String, Object>> moreThanTheGeneList6 = null;
            List<Map<String, Object>> moreThanTheGeneList7 = null;
            List<Map<String, Object>> moreThanTheGeneList8 = null;
            List<Map<String, Object>> moreThanTheGeneList9 = null;

            //从90%-99%的权重比
            MoreThanTheCount more0 = null;
            MoreThanTheCount more1 = null;
            MoreThanTheCount more2 = null;
            MoreThanTheCount more3 = null;
            MoreThanTheCount more4 = null;
            MoreThanTheCount more5 = null;
            MoreThanTheCount more6 = null;
            MoreThanTheCount more7 = null;
            MoreThanTheCount more8 = null;
            MoreThanTheCount more9 = null;
            //基因信息list
            List<ContributorGene> contributorGeneVoList = null;
            //基因信息
            ContributorGene contributorGenes = null;
            //混合基因分型
            String mixedSampleGeneAlleles = null;
            if (ListUtils.isNotNullAndEmptyList(contributorList)) {
                //循环贡献者的信息
                for (int i = 0; i < contributorList.size(); i++) {
                    // 获取贡献者的占比
                    String contributorWeight = null;
                    // 获取贡献者的名字
                    String contributorName = null;
                    //每个贡献者大于90的集合
                    List<MoreThanTheCount> moreThanTheCountList = new ArrayList<>();
                    //每个贡献者大于99%的位点数量
                    int moreThanTheCount1 = 0;
                    int moreThanTheCount2 = 0;
                    int moreThanTheCount3 = 0;
                    int moreThanTheCount4 = 0;
                    int moreThanTheCount5 = 0;
                    int moreThanTheCount6 = 0;
                    int moreThanTheCount7 = 0;
                    int moreThanTheCount8 = 0;
                    int moreThanTheCount9 = 0;
                    int moreThanTheCount10 = 0;
                    //快速比对的基因信息
                    moreThanTheGeneList0 = new ArrayList<>();
                    moreThanTheGeneList1 = new ArrayList<>();
                    moreThanTheGeneList2 = new ArrayList<>();
                    moreThanTheGeneList3 = new ArrayList<>();
                    moreThanTheGeneList4 = new ArrayList<>();
                    moreThanTheGeneList5 = new ArrayList<>();
                    moreThanTheGeneList6 = new ArrayList<>();
                    moreThanTheGeneList7 = new ArrayList<>();
                    moreThanTheGeneList8 = new ArrayList<>();
                    moreThanTheGeneList9 = new ArrayList<>();

                    contributorGeneVoList = new ArrayList<>();
                    //进行循环比对
                    for (Map.Entry<String, List<String>> srcEntry : mixedSampleGeneInfo.entrySet()) {
                        String markerName = srcEntry.getKey();
                        List<String> srcAlleleList = srcEntry.getValue();

                        //获取贡献者的基因信息
                        List<ContributorGene> contributorGeneList = contributorList.get(i).getContributorGeneList();

                        contributorWeight = contributorList.get(i).getContributorWeight();
                        contributorName = contributorList.get(i).getContributorName();

                        if (ListUtils.isNotNullAndEmptyList(contributorGeneList)) {
                            for (ContributorGene contributorGene : contributorGeneList) {

                                //判断基因名称是否相等
                                if (contributorGene.getGeneName().equals(markerName)) {
                                    //混合基因分型
                                    mixedSampleGeneAlleles = getAllele(srcAlleleList);
                                    //基因信息
                                    List<GeneAllele> geneAlleleList = contributorGene.getGeneAlleleList();

                                    contributorGenes = new ContributorGene();

                                    String weights = null;
                                    String digital = null;

                                    //循环基因位点
                                    for (int j = 0; j < geneAlleleList.size(); j++) {
                                        double dninetyNinePercentWeights;
                                        //获取比中百分比
                                        weights = geneAlleleList.get(j).getWeights();
                                        //去掉最后一个%
                                        digital = weights.substring(0, weights.length() - 1);
                                        dninetyNinePercentWeights = Double.parseDouble(digital);

                                        //判断当前百分比大于自定义的权重百分比
                                        if (dninetyNinePercentWeights > Constants.FDNINETY_NINEPERCENT_WEIGHTS_90) {
                                            geneAlleleList.get(j).setNinetyNinePercentWeights(geneAlleleList.get(j).getAllele());
                                            map = new LinkedHashMap<>();
                                            map.put("geneName", markerName);
                                            map.put("allele", geneAlleleList.get(j).getAllele());
                                            moreThanTheGeneList0.add(map);
                                            moreThanTheCount1++;
                                        }

                                        if (dninetyNinePercentWeights > Constants.FDNINETY_NINEPERCENT_WEIGHTS_91) {
                                            geneAlleleList.get(j).setNinetyNinePercentWeights(geneAlleleList.get(j).getAllele());
                                            map = new LinkedHashMap<>();
                                            map.put("geneName", markerName);
                                            map.put("allele", geneAlleleList.get(j).getAllele());
                                            moreThanTheGeneList1.add(map);
                                            moreThanTheCount2++;
                                        }

                                        if (dninetyNinePercentWeights > Constants.FDNINETY_NINEPERCENT_WEIGHTS_92) {
                                            geneAlleleList.get(j).setNinetyNinePercentWeights(geneAlleleList.get(j).getAllele());
                                            map = new LinkedHashMap<>();
                                            map.put("geneName", markerName);
                                            map.put("allele", geneAlleleList.get(j).getAllele());
                                            moreThanTheGeneList2.add(map);
                                            moreThanTheCount3++;
                                        }

                                        if (dninetyNinePercentWeights > Constants.FDNINETY_NINEPERCENT_WEIGHTS_93) {
                                            geneAlleleList.get(j).setNinetyNinePercentWeights(geneAlleleList.get(j).getAllele());
                                            map = new LinkedHashMap<>();
                                            map.put("geneName", markerName);
                                            map.put("allele", geneAlleleList.get(j).getAllele());
                                            moreThanTheGeneList3.add(map);
                                            moreThanTheCount4++;
                                        }

                                        if (dninetyNinePercentWeights > Constants.FDNINETY_NINEPERCENT_WEIGHTS_94) {
                                            geneAlleleList.get(j).setNinetyNinePercentWeights(geneAlleleList.get(j).getAllele());
                                            map = new LinkedHashMap<>();
                                            map.put("geneName", markerName);
                                            map.put("allele", geneAlleleList.get(j).getAllele());
                                            moreThanTheGeneList4.add(map);
                                            moreThanTheCount5++;
                                        }
                                        //判断当前百分比大于自定义的权重百分比
                                        if (dninetyNinePercentWeights > Constants.FDNINETY_NINEPERCENT_WEIGHTS_95) {
                                            geneAlleleList.get(j).setNinetyNinePercentWeights(geneAlleleList.get(j).getAllele());
                                            map = new LinkedHashMap<>();
                                            map.put("geneName", markerName);
                                            map.put("allele", geneAlleleList.get(j).getAllele());
                                            moreThanTheGeneList5.add(map);
                                            moreThanTheCount6++;
                                        }

                                        if (dninetyNinePercentWeights > Constants.FDNINETY_NINEPERCENT_WEIGHTS_96) {
                                            geneAlleleList.get(j).setNinetyNinePercentWeights(geneAlleleList.get(j).getAllele());
                                            map = new LinkedHashMap<>();
                                            map.put("geneName", markerName);
                                            map.put("allele", geneAlleleList.get(j).getAllele());
                                            moreThanTheGeneList6.add(map);
                                            moreThanTheCount7++;
                                        }

                                        if (dninetyNinePercentWeights > Constants.FDNINETY_NINEPERCENT_WEIGHTS_97) {
                                            geneAlleleList.get(j).setNinetyNinePercentWeights(geneAlleleList.get(j).getAllele());
                                            map = new LinkedHashMap<>();
                                            map.put("geneName", markerName);
                                            map.put("allele", geneAlleleList.get(j).getAllele());
                                            moreThanTheGeneList7.add(map);
                                            moreThanTheCount8++;
                                        }

                                        if (dninetyNinePercentWeights > Constants.FDNINETY_NINEPERCENT_WEIGHTS_98) {
                                            geneAlleleList.get(j).setNinetyNinePercentWeights(geneAlleleList.get(j).getAllele());
                                            map = new LinkedHashMap<>();
                                            map.put("geneName", markerName);
                                            map.put("allele", geneAlleleList.get(j).getAllele());
                                            moreThanTheGeneList8.add(map);
                                            moreThanTheCount9++;
                                        }

                                        if (dninetyNinePercentWeights > Constants.FDNINETY_NINEPERCENT_WEIGHTS_99) {
                                            geneAlleleList.get(j).setNinetyNinePercentWeights(geneAlleleList.get(j).getAllele());
                                            map = new LinkedHashMap<>();
                                            map.put("geneName", markerName);
                                            map.put("allele", geneAlleleList.get(j).getAllele());
                                            moreThanTheGeneList9.add(map);
                                            moreThanTheCount10++;
                                        }
                                    }

                                    contributorGenes.setGeneAlleleList(geneAlleleList);
                                    contributorGenes.setGeneName(markerName);
                                    contributorGeneVoList.add(contributorGenes);
                                    map = new LinkedHashMap<>();
                                    map.put("markerName", markerName);
                                    map.put("geneStr1", mixedSampleGeneAlleles);

                                }
                            }

                        }
                        mixedSampleGeneList.add(map);
                    }
                    more0 = new MoreThanTheCount();
                    more0.setMoreThanThevalue(moreThanTheCount1);
                    more0.setPercent(Constants.FDNINETY_WEIGHTS_90);
                    moreThanTheCountList.add(more0);

                    more1 = new MoreThanTheCount();
                    more1.setMoreThanThevalue(moreThanTheCount2);
                    more1.setPercent(Constants.FDNINETY_WEIGHTS_91);
                    moreThanTheCountList.add(more1);

                    more2 = new MoreThanTheCount();
                    more2.setMoreThanThevalue(moreThanTheCount3);
                    more2.setPercent(Constants.FDNINETY_WEIGHTS_92);
                    moreThanTheCountList.add(more2);

                    more3 = new MoreThanTheCount();
                    more3.setMoreThanThevalue(moreThanTheCount4);
                    more3.setPercent(Constants.FDNINETY_WEIGHTS_93);
                    moreThanTheCountList.add(more3);

                    more4 = new MoreThanTheCount();
                    more4.setMoreThanThevalue(moreThanTheCount5);
                    more4.setPercent(Constants.FDNINETY_WEIGHTS_94);
                    moreThanTheCountList.add(more4);

                    more5 = new MoreThanTheCount();
                    more5.setMoreThanThevalue(moreThanTheCount6);
                    more5.setPercent(Constants.FDNINETY_WEIGHTS_95);
                    moreThanTheCountList.add(more5);

                    more6 = new MoreThanTheCount();
                    more6.setMoreThanThevalue(moreThanTheCount7);
                    more6.setPercent(Constants.FDNINETY_WEIGHTS_96);
                    moreThanTheCountList.add(more6);

                    more7 = new MoreThanTheCount();
                    more7.setMoreThanThevalue(moreThanTheCount8);
                    more7.setPercent(Constants.FDNINETY_WEIGHTS_97);
                    moreThanTheCountList.add(more7);

                    more8 = new MoreThanTheCount();
                    more8.setMoreThanThevalue(moreThanTheCount9);
                    more8.setPercent(Constants.FDNINETY_WEIGHTS_98);
                    moreThanTheCountList.add(more8);

                    more9 = new MoreThanTheCount();
                    more9.setMoreThanThevalue(moreThanTheCount10);
                    more9.setPercent(Constants.FDNINETY_WEIGHTS_99);
                    moreThanTheCountList.add(more9);

                    strMixRapiGeneComparisonListVo = new StrMixRapiGeneComparisonListVo();
                    strMixRapiGeneComparisonListVo.setCaseId(caseId);
                    strMixRapiGeneComparisonListVo.setGeneId(geneId);
                    strMixRapiGeneComparisonListVo.setContributorName(contributorName);
                    strMixRapiGeneComparisonListVo.setContributorWeight(contributorWeight);
                    strMixRapiGeneComparisonListVo.setMoreThanTheCountList(moreThanTheCountList);
                    strMixRapiGeneComparisonListVo.setContributorGeneList(contributorGeneVoList);
                    strMixRapiGeneComparisonListVo.setMoreGeneList0(moreThanTheGeneList0);
                    strMixRapiGeneComparisonListVo.setMoreGeneList1(moreThanTheGeneList1);
                    strMixRapiGeneComparisonListVo.setMoreGeneList2(moreThanTheGeneList2);
                    strMixRapiGeneComparisonListVo.setMoreGeneList3(moreThanTheGeneList3);
                    strMixRapiGeneComparisonListVo.setMoreGeneList4(moreThanTheGeneList4);
                    strMixRapiGeneComparisonListVo.setMoreGeneList5(moreThanTheGeneList5);
                    strMixRapiGeneComparisonListVo.setMoreGeneList6(moreThanTheGeneList6);
                    strMixRapiGeneComparisonListVo.setMoreGeneList7(moreThanTheGeneList7);
                    strMixRapiGeneComparisonListVo.setMoreGeneList8(moreThanTheGeneList8);
                    strMixRapiGeneComparisonListVo.setMoreGeneList9(moreThanTheGeneList9);
                    strMixRapiGeneComparisonListVos.add(strMixRapiGeneComparisonListVo);

                }
            }
            //去空
            List<Map<String, Object>> mixedSampleGenes = new ArrayList<>();
            for (Map<String, Object> map1 : mixedSampleGeneList) {
                if (map1 != null){
                    mixedSampleGenes.add(map1);
                }
            }
            //list去除重复的数据
            for (int i = 0; i < mixedSampleGenes.size() - 1; i++) {
                for (int j = mixedSampleGenes.size() - 1; j > i; j--) {
                    if (mixedSampleGenes.get(j).equals(mixedSampleGenes.get(i))) {
                        mixedSampleGenes.remove(j);
                    }
                }
            }

            resultMap.put("mixedSampleGeneList", mixedSampleGenes);
            resultMap.put("contributorCount", contributorCount);
            resultMap.put("strMixRapiGeneComparisonListVos", strMixRapiGeneComparisonListVos);
        } catch (Exception e) {
            logger.error("STRmix拆分报告解析接口贡献者的信息失败" + e.getMessage());
        }

        return resultMap;
    }

    /**
     * 快速比对  权重大于99% 的基因信息 比对 全库基因信息  或者 本案基因信息
     *
     * @param geneInfo              //待比对
     * @param singleSampleGeneLists //比对的
     * @param condition             //容差
     * @return
     */
    public List<SingleSampleGeneVo> fastComparisonGene(String geneInfo, List<SingleSampleGeneVo> singleSampleGeneLists,
                                                       int condition) throws Exception {

        Map<String, List<String>> srcGeneInfoMap = comparisonGeneInfoMap(geneInfo);

        List<SingleSampleGeneVo> singleSampleGeneVoList = new ArrayList<>();
        String targetGeneInfo = null;
        if (ListUtils.isNotNullAndEmptyList(singleSampleGeneLists)) {
            for (int i = 0; i < singleSampleGeneLists.size(); i++) {
                if (null != singleSampleGeneLists.get(i).getCaseId()){
                    SingleSampleGene singleSampleGene = singleSampleGeneService.selectById(singleSampleGeneLists.get(i).getEntity().getId());
                    targetGeneInfo = singleSampleGene.getGeneInfo();
                }else{
                    targetGeneInfo = singleSampleGeneLists.get(i).getEntity().getGeneInfo();
                }
                Map<String, List<String>> targetGeneInfoMap = null;
                targetGeneInfoMap = GeneformatUtils.mixedSampleGeneformat(targetGeneInfo);

                /*比中数*/
                int sameCount = 0;
                  /*容差数*/
                int diffCount = 0;
                //进行循环比对
                for (Map.Entry<String, List<String>> srcEntry : srcGeneInfoMap.entrySet()) {
                    String markerName = srcEntry.getKey();
                    List<String> srcAlleleList = srcEntry.getValue();
                    List<String> tarAlleleList = targetGeneInfoMap.get(markerName);

                    if (ListUtils.isNotNullAndEmptyList(srcAlleleList) && ListUtils.isNotNullAndEmptyList(tarAlleleList)) {

                        //判断快速比对分型是否包含此分型
                        if (isFastContainsAll(srcAlleleList, tarAlleleList)) {

                            sameCount++;
                        } else {

                            diffCount++;
                        }
                    }

                }
                //判断当前容差数是否大设定容差数
                if (diffCount <= condition) {
                    singleSampleGeneLists.get(i).setSameCount(sameCount);
                    singleSampleGeneVoList.add(singleSampleGeneLists.get(i));
                }
            }
        }

        return singleSampleGeneVoList;

    }

    /**
     * 快速比对分型是否包含此分型
     *
     * @param srcAlleleList
     * @param tarAlleleList
     * @return
     */
    public boolean isFastContainsAll(List<String> srcAlleleList, List<String> tarAlleleList) {
        boolean flag = true;

        String isExist = null;
        if (ListUtils.isNotNullAndEmptyList(srcAlleleList)) {
            for (String tarStr : srcAlleleList) {
                if (tarAlleleList.contains(tarStr)) {

                    continue;
                } else {
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
     * 快速比对  查看比中详情
     *
     * @param srcGeneMap
     * @param geneInfo
     * @return
     * @throws Exception
     */
    public Map<String, Object> comparisonResult(Map<String, List<String>> srcGeneMap, String geneInfo) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();

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
            //去解析快速比对的基因信息
            Map<String, List<String>> srcGeneInfoMap = comparisonGeneInfoMap(geneInfo);
            List<String> tarAlleleList = srcGeneInfoMap.get(markerName);

            if (ListUtils.isNotNullAndEmptyList(srcAlleleList) && ListUtils.isNotNullAndEmptyList(tarAlleleList)) {
                //判断混合样本是否包含比对的样本
                if (isContainsGeneAll(srcAlleleList, tarAlleleList)) {
                    map = new LinkedHashMap<>();
                    srcAlleles = getAllele(srcAlleleList);
                    tarAlleles = getAllele(tarAlleleList);

                    map.put("markerName", markerName);
                    map.put("geneStr1", srcAlleles);
                    map.put("geneStr2", tarAlleles);
                    resultList.add(map);
                    sameCount++;
                } else {
                    diffCount++;
                }
            }
        }
        resultMap.put("resultList", resultList);
        resultMap.put("sameCount", sameCount);
        resultMap.put("diffCount", diffCount);
        return resultMap;
    }

    public Map<String, Object> comparisonResult2(String geneInfo) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        List<Map<String, Object>> resultList = new ArrayList<>();
        String srcAlleles = null;
        Map<String, Object> map = new HashMap<String, Object>();
        //进行循环比对
            String markerName = "";
            //去解析快速比对的基因信息
//            Map<String, List<String>> srcGeneInfoMap = comparisonGeneInfoMap2(geneInfo);
//            List<String> tarAlleleList = srcGeneInfoMap.get(markerName);
            map.put("markerName", markerName);
            map.put("geneStr1", srcAlleles);
            resultList.add(map);

        resultMap.put("resultList", resultList);
        return resultMap;
    }

    /**
     * 解析快速比对  大于99%的基因信息
     *
     * @param geneInfo
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, List<String>> comparisonGeneInfoMap(String geneInfo) throws Exception {
        Map<String, List<String>> srcGeneInfoMap = null;
        List<Map<String, Object>> jsonArray = null;
        try {
            //前端传过来的基因信息转成list
            jsonArray = (List) JSONObject.parseArray(geneInfo);
            //用来存储到数据库的格式
            Map<String, Object> geneMap = new HashMap<>();
            //对前台给的json字符串进行格式转换
            for (int i = 0; i < jsonArray.size(); i++) {
                String markerName = jsonArray.get(i).get("geneName").toString();
                String allele = jsonArray.get(i).get("allele").toString();
                String[] allele1 = allele.split(",");
                geneMap.put(markerName, allele1);
            }

            String geneInfos = JSON.toJSONString(geneMap);

            srcGeneInfoMap = (Map) JSON.parse(geneInfos);
        } catch (Exception ex) {
            throw new Exception("解析快速比对大于99%基因信息错误!" + ex.getMessage());
        }
        return srcGeneInfoMap;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, List<String>> comparisonGeneInfoMap2(String geneInfo) throws Exception {
        Map<String, List<String>> srcGeneInfoMap = null;
        List<Map<String, Object>> jsonArray = null;
        try {
            //前端传过来的基因信息转成list
            jsonArray = (List) JSONObject.parseArray(geneInfo);
            //用来存储到数据库的格式
            Map<String, Object> geneMap = new HashMap<>();
            //对前台给的json字符串进行格式转换
            for (int i = 0; i < jsonArray.size(); i++) {
                String markerName = jsonArray.get(i).get("geneName").toString();
                String allele = jsonArray.get(i).get("allele").toString();
                String[] allele1 = allele.split(",");
                geneMap.put(markerName, allele1);
            }

            String geneInfos = JSON.toJSONString(geneMap);

            srcGeneInfoMap = (Map) JSON.parse(geneInfos);
        } catch (Exception ex) {
            throw new Exception("解析快速比对大于99%基因信息错误!" + ex.getMessage());
        }
        return srcGeneInfoMap;
    }

    /**
     * 快速比对  权重大于99% 的基因信息 比对 全库基因信息  或者 本案基因信息
     *
     * @param geneInfo              //待比对
     * @param singleSampleGene //比对的
     * @param condition             //容差
     * @return
     */
    public Map<String, Object> fastComparisonGeneAll(String geneInfo, String singleSampleGene, int condition) throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
//        Map<String, List<String>> srcGeneInfoMap = comparisonGeneInfoMap(geneInfo);
        Map<String, List<String>> srcGeneInfoMap = GeneformatUtils.mixedSampleGeneformat(geneInfo);
        Map<String, List<String>> targetGeneInfoMap = GeneformatUtils.mixedSampleGeneformat(singleSampleGene);
        /*比中数*/
        int sameCount = 0;
        /*容差数*/
        int diffCount = 0;
        //进行循环比对
        for (Map.Entry<String, List<String>> srcEntry : srcGeneInfoMap.entrySet()) {
            String markerName = srcEntry.getKey();
            List<String> srcAlleleList = srcEntry.getValue();
            List<String> tarAlleleList = targetGeneInfoMap.get(markerName);
            if (ListUtils.isNotNullAndEmptyList(srcAlleleList) ) {
                if (ListUtils.isNotNullAndEmptyList(tarAlleleList)){
                    //判断快速比对分型是否包含此分型
                    if (isFastContainsAll(srcAlleleList, tarAlleleList)) {
                        sameCount++;
                    } else {
                        diffCount++;
                    }
                }else{
                    diffCount++;
                }
            }

        }
        resultMap.put("sameCount", sameCount);
        resultMap.put("diffCount", diffCount);
        //判断当前容差数是否大设定容差数
        if (diffCount <= condition) {
            /*singleSampleGeneLists.get(i).setSameCount(sameCount);
              singleSampleGeneVoList.add(singleSampleGeneLists.get(i));*/
            resultMap.put("matched", Boolean.TRUE);
        }else{
            resultMap.put("matched", Boolean.FALSE);
        }
        return resultMap;
    }
}
