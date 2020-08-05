package com.bazl.dna.database.algorithm.comparator;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.bazl.dna.database.algorithm.params.YstrCompareParams;
import com.bazl.dna.database.algorithm.result.YstrCompareResult;
import com.bazl.dna.database.algorithm.result.YstrCompareResultAllele;
import com.bazl.dna.database.service.model.po.DnaGeneInfoDetail;

/**
 * YSTR比对算法
 * Created by lizhihua on 2020-03-18.
 */
public class YstrComparator extends CommComparator {

    /**
     * 返回包含基因座似然比的Ystr比对结果
     *
     * 匹配位点个数及详细
     * 差异位点个数及详细
     * @return Ystr比对结果
     * @param srcGeneInfo 待比对样本基因信息
     * @param targetGeneInfo 目标样本基因信息
     * @param ystrCompareParams Ystr比对条件
     *
     */
    public static YstrCompareResult ystrCompare(List<DnaGeneInfoDetail> srcGeneInfo,
                                                List<DnaGeneInfoDetail> targetGeneInfo,
                                                YstrCompareParams ystrCompareParams) {
        try {
            YstrCompareResult result = doYstrCompare(srcGeneInfo, targetGeneInfo,
                    ystrCompareParams);
            return result;
        } catch(Exception ex){
            logger.error("invoke YstrComparator.ystrCompare error.", ex);
            return null;
        }
    }


    private static YstrCompareResult doYstrCompare(List<DnaGeneInfoDetail> srcGeneInfoDetailList,
                                                   List<DnaGeneInfoDetail> targetGeneInfoDetailList,
                                                   YstrCompareParams ystrCompareParams) throws Exception {
        YstrCompareResult result = new YstrCompareResult();

        //比中数
        int sameCount = 0;
        //容差数
        int diffCount = 0;

        List<YstrCompareResultAllele> ystrCompareResultAlleleList = new ArrayList<>();
        YstrCompareResultAllele ystrCompareResultAllele = null;

        //进行循环比对
        DnaGeneInfoDetail targetGeneDetail = null;
        for (DnaGeneInfoDetail srcGeneDetail : srcGeneInfoDetailList) {
            ystrCompareResultAllele = new YstrCompareResultAllele();

            String markerName = srcGeneDetail.getName();
            String srcAlleleVal = srcGeneDetail.getValue();

            ystrCompareResultAllele.setLocusName(markerName);
            ystrCompareResultAllele.setSrcGeneAllele(srcAlleleVal);

            targetGeneDetail = findSameNameLocus(markerName, targetGeneInfoDetailList);
            if(targetGeneDetail == null){
                ystrCompareResultAllele.setDiffAllele(false);
                ystrCompareResultAlleleList.add(ystrCompareResultAllele);
                continue;
            }

            String tarAlleleVal = targetGeneDetail.getValue();
            ystrCompareResultAllele.setTargetGeneAllele(tarAlleleVal);

            //其中一个样本基因座等位基因为空时，跳过不比对
            if(StringUtils.isBlank(srcAlleleVal) || "null".equalsIgnoreCase(srcAlleleVal)
                    || StringUtils.isBlank(tarAlleleVal) || "null".equalsIgnoreCase(tarAlleleVal)){
                ystrCompareResultAllele.setDiffAllele(false);
                ystrCompareResultAlleleList.add(ystrCompareResultAllele);
                continue;
            }

            if(isSameLocusAllele(srcAlleleVal, tarAlleleVal)){
                sameCount++;
                ystrCompareResultAllele.setDiffAllele(false);
            } else {
                diffCount++ ;
                ystrCompareResultAllele.setDiffAllele(true);
            }

            ystrCompareResultAlleleList.add(ystrCompareResultAllele);
        }

        //匹配数大于等于匹配下限、容差数小于等于容差上限，认定为比中
        if(sameCount >= ystrCompareParams.getLowestSameLimit()
                && diffCount <= ystrCompareParams.getMostDiffLimit()){
            result.setYstrCompareResultAlleleList(ystrCompareResultAlleleList);
            result.setMatchedLocusCount(sameCount);
            result.setDiffAlleleCount(diffCount);

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

}
