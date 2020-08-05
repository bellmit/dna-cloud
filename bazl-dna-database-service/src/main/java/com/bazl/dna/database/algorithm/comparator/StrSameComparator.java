package com.bazl.dna.database.algorithm.comparator;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.bazl.dna.database.algorithm.calculation.MarkerLRCalculation;
import com.bazl.dna.database.algorithm.params.StrSameCompareParams;
import com.bazl.dna.database.algorithm.result.StrSameCompareResult;
import com.bazl.dna.database.algorithm.result.StrSameCompareResultAllele;
import com.bazl.dna.database.constants.GeneConstants;
import com.bazl.dna.database.service.model.po.AlleleFrequencyInfo;
import com.bazl.dna.database.service.model.po.DnaGeneInfoDetail;
import com.bazl.dna.database.utils.ListStringUtils;
import com.bazl.dna.database.utils.ListUtils;

/**
 * STR DNA分型比对工具类
 * Created by lizhihua on 2020-03-03.
 */
public class StrSameComparator extends CommComparator {

    /**
     * 返回包含基因座似然比的同型比对结果
     *
     * 匹配位点个数及详细
     * 差异位点个数及详细
     *
     */
    public static StrSameCompareResult sameCompareIncludeLikelihood(List<DnaGeneInfoDetail> srcGeneInfo, List<DnaGeneInfoDetail> targetGeneInfo,
                                                                    StrSameCompareParams strSameCompareParams, List<AlleleFrequencyInfo> populationAlleleFrequencyList) {
        try {
            StrSameCompareResult result = sameCompare(srcGeneInfo, targetGeneInfo,
                    strSameCompareParams, populationAlleleFrequencyList);
            return result;
        } catch(Exception ex){
            logger.error("invoke StrSameComparator.sameCompareIncludeLikelihood error.", ex);
            return null;
        }
    }

    /**
     * 返回不包含基因座似然比的同型比对结果
     *
     * 匹配位点个数及详细
     * 差异位点个数及详细
     */
    public static StrSameCompareResult sameCompareExcludeLikelihood(List<DnaGeneInfoDetail> srcGeneInfo, List<DnaGeneInfoDetail> targetGeneInfo, StrSameCompareParams strSameCompareParams)
                            throws Exception {
        StrSameCompareResult result = sameCompare(srcGeneInfo, targetGeneInfo, strSameCompareParams, null);
        return result;

    }

    /**
     * STR同型比对
     * @param srcGeneInfoDetailList
     * @param targetGeneInfoDetailList
     * @param strSameCompareParams
     * @param populationAlleleFrequencyList
     * @return
     * @throws Exception
     */
    private static StrSameCompareResult sameCompare(List<DnaGeneInfoDetail> srcGeneInfoDetailList, List<DnaGeneInfoDetail> targetGeneInfoDetailList,
                                                    StrSameCompareParams strSameCompareParams,
                                                    List<AlleleFrequencyInfo> populationAlleleFrequencyList)
                            throws Exception {
        StrSameCompareResult result = new StrSameCompareResult();

        /*
        JSONObject srcGeneInfoMap = convertGeneInfoStrToMap(srcGeneInfo);
        JSONObject targetGeneInfoMap = convertGeneInfoStrToMap(targetGeneInfo);
        */

        //比中数
        int sameCount = 0;
        //容差数
        int diffCount = 0;
        //似然比率
        Double totalLR = 1.0;

        MarkerLRCalculation markerLRCalculation = new MarkerLRCalculation(populationAlleleFrequencyList);

        List<StrSameCompareResultAllele> strSameCompareResultAlleleList = new ArrayList<>();
        StrSameCompareResultAllele strSameCompareResultAllele = null;

        //进行循环比对
        DnaGeneInfoDetail targetGeneDetail = null;
        for (DnaGeneInfoDetail srcGeneDetail : srcGeneInfoDetailList) {
            strSameCompareResultAllele = new StrSameCompareResultAllele();

            String markerName = srcGeneDetail.getName();
            String srcAlleleVal = srcGeneDetail.getValue();

            strSameCompareResultAllele.setLocusName(markerName);
            strSameCompareResultAllele.setSrcGeneAllele(srcAlleleVal);

            targetGeneDetail = findSameNameLocus(markerName, targetGeneInfoDetailList);
            if(targetGeneDetail == null){
                strSameCompareResultAllele.setDiffAllele(false);
                strSameCompareResultAllele.setLikelihoodRate(Double.NaN);
                strSameCompareResultAlleleList.add(strSameCompareResultAllele);
                continue;
            }

            String tarAlleleVal = targetGeneDetail.getValue();
            strSameCompareResultAllele.setTargetGeneAllele(tarAlleleVal);

            //其中一个样本基因座等位基因为空时，跳过不比对
            if(StringUtils.isBlank(srcAlleleVal) || "null".equalsIgnoreCase(srcAlleleVal)
                    || StringUtils.isBlank(tarAlleleVal) || "null".equalsIgnoreCase(tarAlleleVal)){
                strSameCompareResultAllele.setDiffAllele(false);
                strSameCompareResultAllele.setLikelihoodRate(Double.NaN);
                strSameCompareResultAlleleList.add(strSameCompareResultAllele);
                continue;
            }

            Double alleleFreqs = Double.NaN;
            if(isSameLocusAllele(srcAlleleVal, tarAlleleVal)){
                sameCount++;
                strSameCompareResultAllele.setDiffAllele(false);

                if(ListUtils.isNotNullAndEmptyList(populationAlleleFrequencyList)) {
                    List<String> srcAlleleList = ListStringUtils.string2List(srcAlleleVal, GeneConstants.GENE_ALLELE_SEPARATOR_CHAR);
                    alleleFreqs = markerLRCalculation.calculateSingleMarkerLR(markerName, srcAlleleList.get(0), srcAlleleList.size() > 1 ? srcAlleleList.get(1) : srcAlleleList.get(0));
                    strSameCompareResultAllele.setLikelihoodRate(alleleFreqs);
                    if (!Double.isNaN(alleleFreqs)) {
                        totalLR = totalLR * alleleFreqs;
                    }
                }
            } else {
                diffCount++ ;
                strSameCompareResultAllele.setDiffAllele(true);
            }

            strSameCompareResultAlleleList.add(strSameCompareResultAllele);
        }

        //匹配数大于等于匹配下限、容差数小于等于容差上限，认定为比中
        if(sameCount >= strSameCompareParams.getLowestSameLimit()
                && diffCount <= strSameCompareParams.getMostDiffLimit()){
            result.setStrSameCompareResultAlleleList(strSameCompareResultAlleleList);
            result.setMatchedLocusCount(sameCount);
            result.setDiffAlleleCount(diffCount);
            result.setTotalLikelihoodRate(totalLR);

            return result;
        }

        return null;
    }



    /**
     * 判断同一位点的等位基因是否匹配
     * @param alleleVal1
     * @param alleleVal2
     * @return
     */
    private static boolean isSameLocusAllele(String alleleVal1, String alleleVal2){
        return alleleVal1.equals(alleleVal2);
    }

    /**
     * 判断同一位点的等位基因是否匹配
     * @param alleleList1
     * @param alleleList2
     * @return
     */
    public static boolean isSameLocusAllele(List<String> alleleList1, List<String> alleleList2){
        boolean sameFlag1 = alleleList1.get(0).equalsIgnoreCase(alleleList2.get(0)) && alleleList1.get(1).equalsIgnoreCase(alleleList2.get(1));
        boolean sameFlag2 = alleleList1.get(0).equalsIgnoreCase(alleleList2.get(1)) && alleleList1.get(1).equalsIgnoreCase(alleleList2.get(0));

        return sameFlag1 || sameFlag2;
    }

    /**
     * 判断同一位点的等位基因是否属于单亲匹配
     * @param childAlleleList1
     * @param parentAlleleList2
     * @return
     */
    public static boolean isSingleRelativeLocusAllele(List<String> childAlleleList1, List<String> parentAlleleList2){
        for(String childAllele : childAlleleList1){
            if(parentAlleleList2.contains(childAllele)){
                return true;
            }
        }

        return false;
    }

    /**
     * 判断同一位点的等位基因是否属于父母子匹配
     * @param childAlleleList
     * @param fatherAlleleList
     * @param motherAlleleList
     * @return
     */
    public static boolean isFmzRelativeLocusAllele(List<String> childAlleleList, List<String> fatherAlleleList, List<String> motherAlleleList){
        //其中一个等位基因来源于父亲、其中一个等位基因来源于母亲
        if((fatherAlleleList.contains(childAlleleList.get(0))
                && motherAlleleList.contains(childAlleleList.get(1)))
           || (fatherAlleleList.contains(childAlleleList.get(1))
                && motherAlleleList.contains(childAlleleList.get(0)))){
            return true;
        }

        return false;
    }


}
