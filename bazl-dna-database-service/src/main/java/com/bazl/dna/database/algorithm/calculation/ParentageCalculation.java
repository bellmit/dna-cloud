package com.bazl.dna.database.algorithm.calculation;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.bazl.dna.database.service.model.po.AlleleFrequencyInfo;
import com.bazl.dna.database.utils.ListUtils;

/**
 * 亲缘比对亲权指数计算
 *
 * Created by lizhihua on 2020-03-10.
 */
public class ParentageCalculation {

    /**
     * 特定种群的等位基因频率集合
     */
    private List<AlleleFrequencyInfo> allFrequencyList;


    private static double DEFAULT_FREQUENCY = 0.001D;
    private static double PI_MISMATCH = 0.0D / 1;

    public ParentageCalculation(List<AlleleFrequencyInfo> allFrequencyList){
        this.allFrequencyList = allFrequencyList;
    }


    /**
     * 计算父母子亲权指数
     * @param markerName
     * @param fatherAlleleList
     * @param motherAlleleList
     * @param childAlleleList
     * @return
     */
    public Double calculateFmz(String markerName, List<String> fatherAlleleList,
                             List<String> motherAlleleList, List<String> childAlleleList){
        if(ListUtils.isNullOrEmptyList(fatherAlleleList)
                || ListUtils.isNullOrEmptyList(motherAlleleList)
                || ListUtils.isNullOrEmptyList(childAlleleList)){
            return Double.valueOf(0.0D / 1);
        }

        if(fatherAlleleList.size() == 1){
            fatherAlleleList.add(fatherAlleleList.get(0));
        }
        if(motherAlleleList.size() == 1){
            motherAlleleList.add(motherAlleleList.get(0));
        }
        if(childAlleleList.size() == 1){
            childAlleleList.add(childAlleleList.get(0));
        }

        //父亲等位基因是否为纯合子
        boolean faIsHomozygote = isHomozygote(fatherAlleleList);
        //母亲等位基因是否为纯合子
        boolean moIsHomozygote = isHomozygote(motherAlleleList);
        //孩子等位基因是否为纯合子
        boolean chIsHomozygote = isHomozygote(childAlleleList);

        //父子是否为单个等位基因匹配
        boolean fzOnlyOneMatch = isOnlyOneMatch(fatherAlleleList, childAlleleList);
        //母子是否为单个等位基因匹配
        boolean mzOnlyOneMatch = isOnlyOneMatch(motherAlleleList, childAlleleList);

        //父子是否等位基因完全相同
        boolean fzAllMatched = isAllMatch(fatherAlleleList, childAlleleList);
        //母子是否等位基因完全相同
        boolean mzAllMatched = isAllMatch(motherAlleleList, childAlleleList);
        //父母是否等位基因完全相同
        boolean fmAllMatched = isAllMatch(fatherAlleleList, motherAlleleList);


        //父子匹配的等位基因数值
        String fzMatchedAllele = findMatchAlleleVal(fatherAlleleList, childAlleleList);
        //母子匹配的等位基因数值
        String mzMatchedAllele = findMatchAlleleVal(motherAlleleList, childAlleleList);

        double q = 0.0d;
        String p1 = null;

        /**
         * 第1种情况：父母子等位基因均为杂合子，且父子、母子均只有一个等位基因匹配，且父子、母子匹配的等位基因不相同
         */
        if((!faIsHomozygote && !moIsHomozygote && !chIsHomozygote)
                && (fzOnlyOneMatch && mzOnlyOneMatch)
                && (!fzMatchedAllele.equals(mzMatchedAllele))){
            p1 = fzMatchedAllele;
            q = getAlleleFreqs(markerName, p1);
            return Double.valueOf(1.0D / (2.0D * q));
        }

        /**
         * 第2种情况：父、子等位基因为杂合子，母亲等位基因为纯合子，
         *          且父子、母子均只有一个等位基因匹配，且父子、母子匹配的等位基因不相同
         */
        if((!faIsHomozygote && !chIsHomozygote && moIsHomozygote)
                && (fzOnlyOneMatch && mzOnlyOneMatch)
                && (!fzMatchedAllele.equals(mzMatchedAllele))){
            p1 = fzMatchedAllele;
            q = getAlleleFreqs(markerName, p1);
            return Double.valueOf(1.0D / (2.0D * q));
        }

        /**
         * 第3种情况：父、母、子等位基因均为杂合子
         *          且母子只有一个等位基因匹配，父子等位基因完全相同
         */
        if((!faIsHomozygote && !chIsHomozygote && !moIsHomozygote)
                && mzOnlyOneMatch && fzAllMatched){
            p1 = findNotMatchAlleleVal(motherAlleleList, childAlleleList);
            q = getAlleleFreqs(markerName, p1);
            return Double.valueOf(1.0D / (2.0D * q));
        }

        /**
         * 第4种情况：父、子等位基因均为杂合子，母亲等位基因为纯合子
         *          且母子只有一个等位基因匹配，父子等位基因完全相同
         */
        if((!faIsHomozygote && !chIsHomozygote && moIsHomozygote)
                && mzOnlyOneMatch && fzAllMatched){
            p1 = findNotMatchAlleleVal(motherAlleleList, childAlleleList);
            q = getAlleleFreqs(markerName, p1);
            return Double.valueOf(1.0D / (2.0D * q));
        }

        /**
         * 第5种情况：父、母等位基因均为杂合子，孩子等位基因为纯合子
         *          且父子、母子都只有一个等位基因匹配，且父母等位基因不完全相同
         */
        if((!faIsHomozygote && !moIsHomozygote && chIsHomozygote)
                && (mzOnlyOneMatch && fzAllMatched)
                && !fmAllMatched){
            p1 = childAlleleList.get(0);
            q = getAlleleFreqs(markerName, p1);
            return Double.valueOf(1.0D / (2.0D * q));
        }

        /**
         * 第6种情况：父、母等位基因均为杂合子，孩子等位基因为纯合子
         *          且父子、母子都只有一个等位基因匹配，且父母等位基因完全相同
         */
        if((!faIsHomozygote && !moIsHomozygote && chIsHomozygote)
                && (mzOnlyOneMatch && fzAllMatched)
                && fmAllMatched){
            p1 = childAlleleList.get(0);
            q = getAlleleFreqs(markerName, p1);
            return Double.valueOf(1.0D / (2.0D * q));
        }

        /**
         * 第7种情况：父等位基因为杂合子，母亲、孩子等位基因为纯合子
         *          且母子等位基因完全相同，且父子等位基因只有一个匹配
         */
        if((!faIsHomozygote && moIsHomozygote && chIsHomozygote)
                && mzAllMatched && fzOnlyOneMatch){
            p1 = childAlleleList.get(0);
            q = getAlleleFreqs(markerName, p1);
            return Double.valueOf(1.0D / (2.0D * q));
        }

        /**
         * 第8种情况：父等位基因为纯合子，孩子其中一个等位基因来源于父亲,
         *          且孩子其中一个等位基因来源于母亲
         *          且 母亲与孩子等位基因不完全相同 或 母亲、孩子均为纯合子
         */
        if((faIsHomozygote && childAlleleList.contains(fatherAlleleList.get(0)))
                && StringUtils.isNotBlank(mzMatchedAllele)
                && (!mzAllMatched || (moIsHomozygote && chIsHomozygote))){
            p1 = fatherAlleleList.get(0);
            q = getAlleleFreqs(markerName, p1);
            return Double.valueOf(1.0D / q);
        }

        double q1 = 0.0d;
        double q2 = 0.0d;

        /**
         * 第9种情况：父母子等位基因均为杂合子
         *          且母子等位基因完全相同
         *          且父子等位基因不完全相同
         *          且 父子等位基因有一个相同
         */
        if((!faIsHomozygote && !moIsHomozygote && !chIsHomozygote)
                && mzAllMatched && !fzAllMatched && StringUtils.isNotBlank(fzMatchedAllele)){
            q1 = getAlleleFreqs(markerName, childAlleleList.get(0));
            q2 = getAlleleFreqs(markerName, childAlleleList.get(1));
            return Double.valueOf(1.0D / (2.0D * q1 + 2.0D * q2));
        }

        /**
         * 第10种情况：父母子等位基因均为杂合子
         *          且父母子等位基因完全相同
         */
        if((!faIsHomozygote && !moIsHomozygote && !chIsHomozygote)
                && mzAllMatched && fzAllMatched){
            q1 = getAlleleFreqs(markerName, childAlleleList.get(0));
            q2 = getAlleleFreqs(markerName, childAlleleList.get(1));
            return Double.valueOf(1.0D / (q1 + q2));
        }

        /**
         * 第11种情况：父等位基因为纯合子，母子等位基因为杂合子
         *          且母子等位基因完全相同
         *          且孩子等位基因包含父亲等位基因
         */
        if((faIsHomozygote && !moIsHomozygote && !chIsHomozygote)
                && mzAllMatched && childAlleleList.contains(fatherAlleleList.get(0))){
            q1 = getAlleleFreqs(markerName, childAlleleList.get(0));
            q2 = getAlleleFreqs(markerName, childAlleleList.get(1));
            return Double.valueOf(1.0D / (q1 + q2));
        }

        return Double.valueOf(PI_MISMATCH);
    }

    /**
     * 计算父子亲权指数
     * @param markerName
     * @param fatherAlleleList
     * @param childAlleleList
     * @return
     */
    public Double calculateFz(String markerName, List<String> fatherAlleleList,
                               List<String> childAlleleList){
        if(ListUtils.isNullOrEmptyList(fatherAlleleList)
                || ListUtils.isNullOrEmptyList(childAlleleList)){
            return Double.valueOf(0.0D / 1);
        }

        if(fatherAlleleList.size() == 1){
            fatherAlleleList.add(fatherAlleleList.get(0));
        }
        if(childAlleleList.size() == 1){
            childAlleleList.add(childAlleleList.get(0));
        }

        //父亲等位基因是否为纯合子
        boolean faIsHomozygote = isHomozygote(fatherAlleleList);
        //孩子等位基因是否为纯合子
        boolean chIsHomozygote = isHomozygote(childAlleleList);

        //父子是否为单个等位基因匹配
        boolean fzOnlyOneMatch = isOnlyOneMatch(fatherAlleleList, childAlleleList);

        //父子是否等位基因完全相同
        boolean fzAllMatched = isAllMatch(fatherAlleleList, childAlleleList);

        //父子匹配的等位基因数值
        String fzMatchedAllele = findMatchAlleleVal(fatherAlleleList, childAlleleList);

        double q = 0.0d;
        String p1 = null;

        /**
         * 第1种情况：父子等位基因均为杂合子，且父子只有一个等位基因匹配
         */
        if((!faIsHomozygote && !chIsHomozygote) && fzOnlyOneMatch){
            p1 = fzMatchedAllele;
            q = getAlleleFreqs(markerName, p1);
            return Double.valueOf(1.0D / (4.0D * q));
        }

        /**
         * 第2种情况：父子等位基因为杂合子
         *          且父子等位基因完全相同
         */
        if((!faIsHomozygote && !chIsHomozygote) && fzAllMatched){
            p1 = (String)childAlleleList.get(0);
            String p2 = (String)childAlleleList.get(1);
            double q1 = getAlleleFreqs(markerName, p1);
            double q2 = getAlleleFreqs(markerName, p2);
            return Double.valueOf(1.0D / (4.0D * q1) + 1.0D / (4.0D * q2));
        }

        /**
         * 第3种情况：（父等位基因为杂合子且子等位基因为纯合子）或（父等位基因为纯合子且子等位基因为杂合子）
         *          且父子有一个等位基因相同
         */
        if(((!faIsHomozygote && chIsHomozygote) || (faIsHomozygote && !chIsHomozygote))
                && StringUtils.isNotBlank(fzMatchedAllele)){
            p1 = fzMatchedAllele;
            q = getAlleleFreqs(markerName, p1);
            return Double.valueOf(1.0D / (2.0D * q));
        }

        /**
         * 第4种情况：父、子等位基因均为纯合子且完全相同
         */
        if((faIsHomozygote && chIsHomozygote) && fzAllMatched){
            p1 = childAlleleList.get(0);
            q = getAlleleFreqs(markerName, p1);
            return Double.valueOf(1.0D / q);
        }

        return Double.valueOf(PI_MISMATCH);
    }

    /**
     * 计算母子亲权指数
     * @param markerName
     * @param motherAlleleList
     * @param childAlleleList
     * @return
     */
    public Double calculateMz(String markerName, List<String> motherAlleleList,
                              List<String> childAlleleList){
        return calculateFz(markerName, motherAlleleList, childAlleleList);
    }

    /**
     * 获取指定基因座和等位基因的频率值
     */
    public Double getAlleleFreqs(String markerName, String alleleValue) {
        for (AlleleFrequencyInfo frequency : allFrequencyList) {
            if (frequency.getLocusName().compareToIgnoreCase(markerName) == 0
                    && frequency.getAlleleValue().compareTo(alleleValue) == 0) {
                return frequency.getProbability().doubleValue();
            }
        }

        return DEFAULT_FREQUENCY;
    }

    /**
     * 查找父/母与孩子匹配的等位基因数值
     * @param fmAlleleList
     * @param childAlleleList
     * @return
     */
    public static String findMatchAlleleVal(List<String> fmAlleleList, List<String> childAlleleList) {
        for(String childAllele : childAlleleList){
            if(fmAlleleList.contains(childAllele)){
                return childAllele;
            }
        }

        return null;
    }

    /**
     * 查找父/母与孩子不一致的等位基因数值
     * @param fmAlleleList
     * @param childAlleleList
     * @return
     */
    public static String findNotMatchAlleleVal(List<String> fmAlleleList, List<String> childAlleleList) {
        for(String childAllele : childAlleleList){
            if(!fmAlleleList.contains(childAllele)){
                return childAllele;
            }
        }

        return null;
    }

    /**
     * 判断父/母与孩子是否为只有一个等位基因匹配
     * @param fmAlleleList
     * @param childAlleleList
     * @return
     */
    public static boolean isOnlyOneMatch(List<String> fmAlleleList, List<String> childAlleleList) {
        return fmAlleleList.contains(childAlleleList.get(0)) && !fmAlleleList.contains(childAlleleList.get(1))
                || fmAlleleList.contains(childAlleleList.get(1)) && !fmAlleleList.contains(childAlleleList.get(0));
    }

    /**
     * 判断父/母与孩子是否为等位基因完全相同
     * @param fmAlleleList
     * @param childAlleleList
     * @return
     */
    public static boolean isAllMatch(List<String> fmAlleleList, List<String> childAlleleList) {
        return fmAlleleList.containsAll(childAlleleList) && childAlleleList.containsAll(fmAlleleList);
    }


    /**
     * 判断一个基因座上的等位基因是否为纯合子
     * 即：两个等位基因相同，为纯合子，返回True
     *    两个等位基因不同，为杂合子，返回False
     * @return
     */
    private boolean isHomozygote(List<String> alleleList){
        try {
            return (alleleList.get(0).equalsIgnoreCase(alleleList.get(1)));
        }catch(Exception ex){
            return false;
        }
    }

}
