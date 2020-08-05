package com.bazl.dna.database.algorithm.comparator;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.bazl.dna.database.algorithm.calculation.ParentageCalculation;
import com.bazl.dna.database.algorithm.params.StrRelativeCompareParams;
import com.bazl.dna.database.algorithm.result.StrRelativeCompareResult;
import com.bazl.dna.database.algorithm.result.StrRelativeCompareResultAllele;
import com.bazl.dna.database.constants.GeneConstants;
import com.bazl.dna.database.service.model.po.AlleleFrequencyInfo;
import com.bazl.dna.database.service.model.po.DnaGeneInfoDetail;
import com.bazl.dna.database.utils.ListStringUtils;
import com.bazl.dna.database.utils.ListUtils;

/**
 *
 * STR 亲缘比对工具类
 * Created by lizhihua on 2020-03-10.
 */
public class StrRelativeComparator extends CommComparator {

    /**
     * 亲缘比对 - 三联体（父母子）比对，并进行亲权指数计算
     * @param fatherGeneInfo 父亲基因分型信息
     * @param motherGeneInfo 母亲基因分型信息
     * @param childGeneInfo 孩子基因分型信息
     * @param strRelativeCompareParams 三联体比中条件信息
     * @param populationAlleleFrequencyList  种群等位基因频率集合
     * @return  三联体比中结果信息
     */
    public static StrRelativeCompareResult fmzCompare(
            List<DnaGeneInfoDetail> fatherGeneInfo,
            List<DnaGeneInfoDetail> motherGeneInfo,
            List<DnaGeneInfoDetail> childGeneInfo,
            StrRelativeCompareParams strRelativeCompareParams,
            List<AlleleFrequencyInfo> populationAlleleFrequencyList) {
        try {
            StrRelativeCompareResult result = fmz(fatherGeneInfo, motherGeneInfo, childGeneInfo,
                    strRelativeCompareParams, populationAlleleFrequencyList);
            return result;
        } catch(Exception ex){
            logger.error("invoke StrRelativeComparator.fmzCompareIncludePICalculate error.", ex);
            return null;
        }
    }


    /**
     * 亲缘比对 - 单亲（父子/母子）比对，并进行亲权指数计算
     * @param parentGeneInfo 父/母亲基因分型信息
     * @param childGeneInfo 孩子基因分型信息
     * @param strRelativeCompareParams 单亲比中条件信息
     * @param populationAlleleFrequencyList  种群等位基因频率集合
     * @return  单亲比中结果信息
     */
    public static StrRelativeCompareResult singleParentCompare(
            List<DnaGeneInfoDetail> parentGeneInfo,
            List<DnaGeneInfoDetail> childGeneInfo,
            StrRelativeCompareParams strRelativeCompareParams,
            List<AlleleFrequencyInfo> populationAlleleFrequencyList) {
        try {
            StrRelativeCompareResult result = singleParent(parentGeneInfo, childGeneInfo,
                    strRelativeCompareParams, populationAlleleFrequencyList);
            return result;
        } catch(Exception ex){
            logger.error("invoke StrRelativeComparator.fmzCompareIncludePICalculate error.", ex);
            return null;
        }
    }



    /**
     * 亲缘比对 - 单亲比对，并进行亲权指数计算
     * @param parentGeneInfoList 父母基因分型信息
     * @param childGeneInfoList 孩子基因分型信息
     * @param strRelativeCompareParams 单亲比中条件信息
     * @param populationAlleleFrequencyList  种群等位基因频率集合
     * @return  单亲比中结果信息
     * @throws Exception
     */
    private static StrRelativeCompareResult singleParent(
            List<DnaGeneInfoDetail> parentGeneInfoList,
            List<DnaGeneInfoDetail> childGeneInfoList,
            StrRelativeCompareParams strRelativeCompareParams,
            List<AlleleFrequencyInfo> populationAlleleFrequencyList) throws Exception {

        StrRelativeCompareResult result = new StrRelativeCompareResult();
        result.setSingleRelative(true);

        ParentageCalculation parentageCalculation = new ParentageCalculation(populationAlleleFrequencyList);

        //比中位点个数
        int sameCount = 0;
        //差异位点个数
        int diffCount = 0;

        List<Double> totalPiValList = new ArrayList<>();

        List<StrRelativeCompareResultAllele> relativeResultAlleleList = new ArrayList<>();
        StrRelativeCompareResultAllele resultAllele = null;
        DnaGeneInfoDetail parent = null;
        for(DnaGeneInfoDetail child : childGeneInfoList){
            resultAllele = new StrRelativeCompareResultAllele();
            resultAllele.setDiffAllele(false);

            String markerName = child.getName();
            String childAlleleVal = child.getValue();

            resultAllele.setLocusName(markerName);
            resultAllele.setChildGeneAllele(childAlleleVal);

            parent = findSameNameLocus(markerName, parentGeneInfoList);

            /**
             * 父/母亲无对应名称的基因座
             */
            if(parent == null){
                resultAllele.setSingleParentGeneAllele(parent == null ? null : parent.getValue());
                resultAllele.setPiVal(Double.NaN);
                relativeResultAlleleList.add(resultAllele);

                continue;
            }

            String parentAlleleVal = parent.getValue();
            resultAllele.setSingleParentGeneAllele(parentAlleleVal);

            /**
             * 其中一个样本等位基因为空时，跳过不比对
             */
            if(StringUtils.isBlank(childAlleleVal)
                    || StringUtils.isBlank(parentAlleleVal)) {
                resultAllele.setPiVal(Double.NaN);
                relativeResultAlleleList.add(resultAllele);
                continue;
            }

            parentAlleleVal = parentAlleleVal.toUpperCase();
            childAlleleVal = childAlleleVal.toUpperCase();
            if(isSingleParentAllele(parentAlleleVal, childAlleleVal)){
                if(ListUtils.isNotNullAndEmptyList(populationAlleleFrequencyList)) {
                    List<String> parentAlleleList = ListStringUtils.string2List(parentAlleleVal, GeneConstants.GENE_ALLELE_SEPARATOR_CHAR);
                    List<String> childAlleleList = ListStringUtils.string2List(childAlleleVal, GeneConstants.GENE_ALLELE_SEPARATOR_CHAR);

                    Double piVal = parentageCalculation.calculateFz(markerName, parentAlleleList, childAlleleList);
                    resultAllele.setPiVal(piVal);

                    if(piVal != null && piVal > 0.0D){
                        totalPiValList.add(piVal);
                    }
                }

                sameCount++;
            } else {
                diffCount++ ;
                resultAllele.setDiffAllele(true);
            }

            relativeResultAlleleList.add(resultAllele);
        }

        //匹配数大于等于匹配下限、容差数小于等于容差上限，认定为比中
        if(sameCount >= strRelativeCompareParams.getLowestSameLimit()
                && diffCount <= strRelativeCompareParams.getMostDiffLimit()){
            result.setStrRelativeCompareResultAlleleList(relativeResultAlleleList);
            result.setTotalMatchedPiCount(totalPiValList.size());
            result.setTotalPiVal(calculateTotalPiVal(totalPiValList));

            result.setMatchedLocusCount(sameCount);
            result.setDiffAlleleCount(diffCount);

            return result;
        }
        return null;
    }

    /**
     * 亲缘比对 - 三联体（父母子）比对，并进行亲权指数计算
     * @param fatherGeneInfoList 父亲基因分型信息
     * @param motherGeneInfoList 母亲基因分型信息
     * @param childGeneInfoList 孩子基因分型信息
     * @param strRelativeCompareParams 三联体比中条件信息
     * @param populationAlleleFrequencyList  种群等位基因频率集合
     * @return  三联体比中结果信息
     * @throws Exception
     */
    private static StrRelativeCompareResult fmz(
            List<DnaGeneInfoDetail> fatherGeneInfoList,
            List<DnaGeneInfoDetail> motherGeneInfoList,
            List<DnaGeneInfoDetail> childGeneInfoList,
            StrRelativeCompareParams strRelativeCompareParams,
            List<AlleleFrequencyInfo> populationAlleleFrequencyList) throws Exception {

        StrRelativeCompareResult result = new StrRelativeCompareResult();

        ParentageCalculation parentageCalculation = new ParentageCalculation(populationAlleleFrequencyList);

        //比中位点个数
        int sameCount = 0;
        //差异位点个数
        int diffCount = 0;

        List<Double> totalPiValList = new ArrayList<>();
        List<Double> fatherPiValList = new ArrayList<>();
        List<Double> motherPiValList = new ArrayList<>();

        List<StrRelativeCompareResultAllele> relativeResultAlleleList = new ArrayList<>();
        StrRelativeCompareResultAllele resultAllele = null;
        DnaGeneInfoDetail father = null;
        DnaGeneInfoDetail mother = null;
        for(DnaGeneInfoDetail child : childGeneInfoList){
            resultAllele = new StrRelativeCompareResultAllele();
            resultAllele.setDiffAllele(false);

            String markerName = child.getName();
            String childAlleleVal = child.getValue();

            resultAllele.setLocusName(markerName);
            resultAllele.setChildGeneAllele(childAlleleVal);

            father = findSameNameLocus(markerName, fatherGeneInfoList);
            mother = findSameNameLocus(markerName, motherGeneInfoList);

            /**
             * 父亲或母亲无对应名称的基因座
             */
            if(father == null || mother == null){
                resultAllele.setFatherGeneAllele(father == null ? null : father.getValue());
                resultAllele.setMotherGeneAllele(mother == null ? null : mother.getValue());
                resultAllele.setPiVal(Double.NaN);
                resultAllele.setFatherPiVal(Double.NaN);
                resultAllele.setMotherPiVal(Double.NaN);
                relativeResultAlleleList.add(resultAllele);

                continue;
            }

            String fatherAlleleVal = father.getValue();
            String motherAlleleVal = mother.getValue();


            resultAllele.setFatherGeneAllele(fatherAlleleVal);
            resultAllele.setMotherGeneAllele(motherAlleleVal);

            /**
             * 其中一个样本等位基因为空时，跳过不比对
             */
            if(StringUtils.isBlank(childAlleleVal)
                    || StringUtils.isBlank(fatherAlleleVal)
                    || StringUtils.isBlank(motherAlleleVal)) {
                resultAllele.setPiVal(Double.NaN);
                resultAllele.setFatherPiVal(Double.NaN);
                resultAllele.setMotherPiVal(Double.NaN);
                relativeResultAlleleList.add(resultAllele);
                continue;
            }

            fatherAlleleVal = fatherAlleleVal.toUpperCase();
            motherAlleleVal = motherAlleleVal.toUpperCase();
            childAlleleVal = childAlleleVal.toUpperCase();
            if(isFmzAllele(fatherAlleleVal, motherAlleleVal, childAlleleVal)){
                if(ListUtils.isNotNullAndEmptyList(populationAlleleFrequencyList)) {
                    List<String> fatherAlleleList = ListStringUtils.string2List(fatherAlleleVal, GeneConstants.GENE_ALLELE_SEPARATOR_CHAR);
                    List<String> motherAlleleList = ListStringUtils.string2List(motherAlleleVal, GeneConstants.GENE_ALLELE_SEPARATOR_CHAR);
                    List<String> childAlleleList = ListStringUtils.string2List(childAlleleVal, GeneConstants.GENE_ALLELE_SEPARATOR_CHAR);

                    Double piVal = parentageCalculation.calculateFmz(markerName, fatherAlleleList, motherAlleleList, childAlleleList);
                    Double fzPiVal = parentageCalculation.calculateFz(markerName, fatherAlleleList, childAlleleList);
                    Double mzPiVal = parentageCalculation.calculateMz(markerName, motherAlleleList, childAlleleList);

                    resultAllele.setPiVal(piVal);
                    resultAllele.setFatherPiVal(fzPiVal);
                    resultAllele.setMotherPiVal(mzPiVal);
                    if(piVal != null && piVal > 0.0D){
                        totalPiValList.add(piVal);
                    }
                    if(fzPiVal != null && fzPiVal > 0.0D){
                        fatherPiValList.add(fzPiVal);
                    }
                    if(mzPiVal != null && mzPiVal > 0.0D){
                        motherPiValList.add(mzPiVal);
                    }
                }

                sameCount++;
            } else {
                diffCount++ ;
                resultAllele.setDiffAllele(true);
            }

            relativeResultAlleleList.add(resultAllele);
        }

        //匹配数大于等于匹配下限、容差数小于等于容差上限，认定为比中
        if(sameCount >= strRelativeCompareParams.getLowestSameLimit()
                && diffCount <= strRelativeCompareParams.getMostDiffLimit()){
            result.setStrRelativeCompareResultAlleleList(relativeResultAlleleList);
            result.setTotalMatchedPiCount(totalPiValList.size());
            result.setFatherMatchedPiCount(fatherPiValList.size());
            result.setMotherMatchedPiCount(motherPiValList.size());

            result.setTotalPiVal(calculateTotalPiVal(totalPiValList));
            result.setTotalFatherPiVal(calculateTotalPiVal(fatherPiValList));
            result.setTotalMotherPiVal(calculateTotalPiVal(motherPiValList));

            result.setMatchedLocusCount(sameCount);
            result.setDiffAlleleCount(diffCount);
            result.setMatchedFlag(true);
            return result;
        }

        return null;
    }

    private static Double calculateTotalPiVal(List<Double> piValList){
        double result = 1.0D;

        for(Double d : piValList) {
            result *= d.doubleValue();
        }

        return result;
    }

    /**
     * 判断同一位点的等位基因是否属于父母子匹配
     * @param fatherAlleleVal
     * @param motherAlleleVal
     * @param childAlleleVal
     * @return
     */
    private static boolean isFmzAllele(String fatherAlleleVal, String motherAlleleVal, String childAlleleVal){

        List<String> childAlleleList = ListStringUtils.string2List(childAlleleVal, GeneConstants.GENE_ALLELE_SEPARATOR_CHAR);

        if(childAlleleList.size() == 1){
            if(fatherAlleleVal.contains(childAlleleList.get(0))
                    && motherAlleleVal.contains(childAlleleList.get(0))){
                return true;
            }else{
                return false;
            }
        }

        if(childAlleleList.size() == 2){
            if((fatherAlleleVal.contains(childAlleleList.get(0)) && motherAlleleVal.contains(childAlleleList.get(1)))
               || (fatherAlleleVal.contains(childAlleleList.get(1)) && motherAlleleVal.contains(childAlleleList.get(0)))){
                return true;
            }else{
                return false;
            }
        }

        return false;
    }

    /**
     * 判断同一位点的等位基因是否属于单亲匹配
     * @param parentAlleleVal
     * @param childAlleleVal
     * @return
     */
    private static boolean isSingleParentAllele(String parentAlleleVal, String childAlleleVal){

        List<String> childAlleleList = ListStringUtils.string2List(childAlleleVal, GeneConstants.GENE_ALLELE_SEPARATOR_CHAR);

        if(childAlleleList.size() == 1){
            if(parentAlleleVal.contains(childAlleleList.get(0))){
                return true;
            }else{
                return false;
            }
        }

        if(childAlleleList.size() == 2){
            if(parentAlleleVal.contains(childAlleleList.get(0))
                    || parentAlleleVal.contains(childAlleleList.get(1))){
                return true;
            }else{
                return false;
            }
        }

        return false;
    }


}
