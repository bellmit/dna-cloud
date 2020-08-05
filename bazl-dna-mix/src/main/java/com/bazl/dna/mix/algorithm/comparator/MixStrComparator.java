package com.bazl.dna.mix.algorithm.comparator;

import com.alibaba.fastjson.JSONArray;
import com.bazl.dna.mix.algorithm.constants.AlgorithmConstants;
import com.bazl.dna.mix.algorithm.model.DnaGeneInfoDetail;
import com.bazl.dna.mix.algorithm.params.CompareParams;
import com.bazl.dna.mix.algorithm.result.MixStrCompareResult;
import com.bazl.dna.mix.algorithm.result.MixStrCompareResultAllele;
import com.bazl.dna.mix.utils.ListStringUtils;
import com.bazl.dna.mix.utils.ListUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 混合样本比对算法
 * @author lizhihua on 2020-04-21.
 */
public class MixStrComparator extends CommComparator {

    /*
    public static void main(String[] args){
      // String mixGeneInfoDetail = "[{\"name\": \"AMEL\", \"value\": \"X/Y\"}, {\"name\": \"D8S1179\", \"value\": \"10/11/14/15/16\"}, {\"name\": \"D21S11\", \"value\": \"30/31.2/32/33\"}, {\"name\": \"D7S820\", \"value\": \"10/11/12\"}, {\"name\": \"CSF1PO\", \"value\": \"11/12\"}, {\"name\": \"D3S1358\", \"value\": \"14/15/17/18/19\"}, {\"name\": \"TH01\", \"value\": \"6/7/9/10\"}, {\"name\": \"D13S317\", \"value\": \"9/11/12\"}, {\"name\": \"D16S539\", \"value\": \"10/11/12\"}, {\"name\": \"D2S1338\", \"value\": \"23/24/25\"}, {\"name\": \"D19S433\", \"value\": \"13/14/15\"}, {\"name\": \"vWA\", \"value\": \"14/17\"}, {\"name\": \"TPOX\", \"value\": \"8/11/12/13\"}, {\"name\": \"D18S51\", \"value\": \"14/15\"}, {\"name\": \"D5S818\", \"value\": \"10/11/13/14\"}, {\"name\": \"FGA\", \"value\": \"19/21/22/23/24/25\"}]";
        String mixGeneInfoDetail ="[{\"name\":\"AMELOGENIN\",\"value\":\"X/Y\"},{\"name\":\"ABOGroup\",\"value\":\"\"},{\"name\":\"D3S1358\",\"value\":\"16/16\"},{\"name\":\"vWA\",\"value\":\"18/18\"},{\"name\":\"FGA\",\"value\":\"22/23\"},{\"name\":\"D8S1179\",\"value\":\"12/14\"},{\"name\":\"D21S11\",\"value\":\"30/32.2\"},{\"name\":\"D18S51\",\"value\":\"14/14\"},{\"name\":\"D5S818\",\"value\":\"9/11\"},{\"name\":\"D13S317\",\"value\":\"8/10\"},{\"name\":\"D7S820\",\"value\":\"9/11\"},{\"name\":\"TPOX\",\"value\":\"8/9\"},{\"name\":\"CSF1PO\",\"value\":\"10/12\"},{\"name\":\"TH01\",\"value\":\"8/9.3\"},{\"name\":\"D16S539\",\"value\":\"9/9\"},{\"name\":\"D2S1338\",\"value\":\"19/20\"},{\"name\":\"D19S433\",\"value\":\"13/13\"},{\"name\":\"FESFPS\",\"value\":\"\"},{\"name\":\"F13A01\",\"value\":\"\"},{\"name\":\"PentaD\",\"value\":\"\"},{\"name\":\"PentaE\",\"value\":\"\"},{\"name\":\"D6S1043\",\"value\":\"\"},{\"name\":\"D1S1656\",\"value\":\"\"},{\"name\":\"D12S391\",\"value\":\"\"},{\"name\":\"D2S441\",\"value\":\"\"},{\"name\":\"D22S1045\",\"value\":\"\"},{\"name\":\"SE33\",\"value\":\"\"},{\"name\":\"D1S80\",\"value\":\"\"},{\"name\":\"D10S1248\",\"value\":\"\"},{\"name\":\"D19S253\",\"value\":\"\"},{\"name\":\"D6S474\",\"value\":\"\"},{\"name\":\"D12ATA63\",\"value\":\"\"},{\"name\":\"D1S1677\",\"value\":\"\"},{\"name\":\"D11S4463\",\"value\":\"\"},{\"name\":\"D1S1627\",\"value\":\"\"},{\"name\":\"D3S4529\",\"value\":\"\"},{\"name\":\"D6S1017\",\"value\":\"\"},{\"name\":\"D4S2408\",\"value\":\"\"},{\"name\":\"D17S1301\",\"value\":\"\"},{\"name\":\"D1GATA113\",\"value\":\"\"},{\"name\":\"D18S853\",\"value\":\"\"},{\"name\":\"D20S482\",\"value\":\"\"},{\"name\":\"D14S1434\",\"value\":\"\"},{\"name\":\"D9S1122\",\"value\":\"\"},{\"name\":\"D2S1776\",\"value\":\"\"},{\"name\":\"D10S1435\",\"value\":\"\"},{\"name\":\"D5S2500\",\"value\":\"\"}]";


        Integer contributorCount = 3;

        String contributorGene = "[{\"name\": \"AMEL\", \"value\": \"X\"}, {\"name\": \"D8S1179\", \"value\": \"11/14\"}, {\"name\": \"D21S11\", \"value\": \"31.2\"}, {\"name\": \"D7S820\", \"value\": \"11/12\"}, {\"name\": \"CSF1PO\", \"value\": \"11/12\"}, {\"name\": \"D3S1358\", \"value\": \"15/15\"}, {\"name\": \"D13S317\", \"value\": \"11/12\"}, {\"name\": \"D16S539\", \"value\": \"10/11\"}, {\"name\": \"vWA\", \"value\": \"17/17\"}, {\"name\": \"D18S51\", \"value\": \"14/15\"}, {\"name\": \"D5S818\", \"value\": \"10/11\"}, {\"name\": \"FGA\", \"value\": \"22/23\"}]";
        List<String> contributorList = Arrays.asList(contributorGene);

       // String targetGeneInfoDetail = "[{\"name\": \"AMEL\", \"value\": \"\"}, {\"name\": \"D8S1179\", \"value\": \"10/15\"}, {\"name\": \"D21S11\", \"value\": \"30/32\"}, {\"name\": \"D7S820\", \"value\": \"10/12\"}, {\"name\": \"CSF1PO\", \"value\": \"12/12\"}, {\"name\": \"D3S1358\", \"value\": \"14/17\"}, {\"name\": \"TH01\", \"value\": \"7/9\"}, {\"name\": \"D13S317\", \"value\": \"9/11\"}, {\"name\": \"D16S539\", \"value\": \"10/11\"}, {\"name\": \"D2S1338\", \"value\": \"23/23\"}, {\"name\": \"D19S433\", \"value\": \"13/14\"}, {\"name\": \"vWA\", \"value\": \"14/17\"}, {\"name\": \"TPOX\", \"value\": \"8\"}, {\"name\": \"D18S51\", \"value\": \"14/14\"}, {\"name\": \"D5S818\", \"value\": \"10/13\"}, {\"name\": \"FGA\", \"value\": \"19/21\"}]";
        String targetGeneInfoDetail ="[{\"name\":\"AMELOGENIN\",\"value\":\"X/Y\"},{\"name\":\"ABOGroup\",\"value\":\"\"},{\"name\":\"D3S1358\",\"value\":\"16/16\"},{\"name\":\"vWA\",\"value\":\"18/18\"},{\"name\":\"FGA\",\"value\":\"22/23\"},{\"name\":\"D8S1179\",\"value\":\"12/14\"},{\"name\":\"D21S11\",\"value\":\"30/32.2\"},{\"name\":\"D18S51\",\"value\":\"14/14\"},{\"name\":\"D5S818\",\"value\":\"9/11\"},{\"name\":\"D13S317\",\"value\":\"8/10\"},{\"name\":\"D7S820\",\"value\":\"9/11\"},{\"name\":\"TPOX\",\"value\":\"8/9\"},{\"name\":\"CSF1PO\",\"value\":\"10/12\"},{\"name\":\"TH01\",\"value\":\"8/9.3\"},{\"name\":\"D16S539\",\"value\":\"9/9\"},{\"name\":\"D2S1338\",\"value\":\"19/20\"},{\"name\":\"D19S433\",\"value\":\"13/13\"},{\"name\":\"FESFPS\",\"value\":\"\"},{\"name\":\"F13A01\",\"value\":\"\"},{\"name\":\"PentaD\",\"value\":\"\"},{\"name\":\"PentaE\",\"value\":\"\"},{\"name\":\"D6S1043\",\"value\":\"\"},{\"name\":\"D1S1656\",\"value\":\"\"},{\"name\":\"D12S391\",\"value\":\"\"},{\"name\":\"D2S441\",\"value\":\"\"},{\"name\":\"D22S1045\",\"value\":\"\"},{\"name\":\"SE33\",\"value\":\"\"},{\"name\":\"D1S80\",\"value\":\"\"},{\"name\":\"D10S1248\",\"value\":\"\"},{\"name\":\"D19S253\",\"value\":\"\"},{\"name\":\"D6S474\",\"value\":\"\"},{\"name\":\"D12ATA63\",\"value\":\"\"},{\"name\":\"D1S1677\",\"value\":\"\"},{\"name\":\"D11S4463\",\"value\":\"\"},{\"name\":\"D1S1627\",\"value\":\"\"},{\"name\":\"D3S4529\",\"value\":\"\"},{\"name\":\"D6S1017\",\"value\":\"\"},{\"name\":\"D4S2408\",\"value\":\"\"},{\"name\":\"D17S1301\",\"value\":\"\"},{\"name\":\"D1GATA113\",\"value\":\"\"},{\"name\":\"D18S853\",\"value\":\"\"},{\"name\":\"D20S482\",\"value\":\"\"},{\"name\":\"D14S1434\",\"value\":\"\"},{\"name\":\"D9S1122\",\"value\":\"\"},{\"name\":\"D2S1776\",\"value\":\"\"},{\"name\":\"D10S1435\",\"value\":\"\"},{\"name\":\"D5S2500\",\"value\":\"\"}]";
       // String targetGeneInfoDetail ="[{\"name\":\"AMELOGENIN\",\"value\":\"X/Y\"},{\"name\":\"ABOGroup\",\"value\":\"\"},{\"name\":\"D3S1358\",\"value\":\"16/16\"},{\"name\":\"vWA\",\"value\":\"18/18\"},{\"name\":\"FGA\",\"value\":\"22/23\"},{\"name\":\"D8S1179\",\"value\":\"12/14\"},{\"name\":\"D21S11\",\"value\":\"30/32.2\"},{\"name\":\"D18S51\",\"value\":\"14/14\"},{\"name\":\"D5S818\",\"value\":\"9/11\"},{\"name\":\"D13S317\",\"value\":\"8/10\"},{\"name\":\"D7S820\",\"value\":\"9/11\"},{\"name\":\"TPOX\",\"value\":\"8/9\"},{\"name\":\"CSF1PO\",\"value\":\"10/12\"},{\"name\":\"TH01\",\"value\":\"8/9.3\"},{\"name\":\"D16S539\",\"value\":\"9/9\"},{\"name\":\"D2S1338\",\"value\":\"19/20\"},{\"name\":\"D19S433\",\"value\":\"13/13\"},{\"name\":\"FESFPS\",\"value\":\"\"},{\"name\":\"F13A01\",\"value\":\"\"},{\"name\":\"PentaD\",\"value\":\"\"},{\"name\":\"PentaE\",\"value\":\"\"},{\"name\":\"D6S1043\",\"value\":\"\"},{\"name\":\"D1S1656\",\"value\":\"\"},{\"name\":\"D12S391\",\"value\":\"\"},{\"name\":\"D2S441\",\"value\":\"\"},{\"name\":\"D22S1045\",\"value\":\"\"},{\"name\":\"SE33\",\"value\":\"\"},{\"name\":\"D1S80\",\"value\":\"\"},{\"name\":\"D10S1248\",\"value\":\"\"},{\"name\":\"D19S253\",\"value\":\"\"},{\"name\":\"D6S474\",\"value\":\"\"},{\"name\":\"D12ATA63\",\"value\":\"\"},{\"name\":\"D1S1677\",\"value\":\"\"},{\"name\":\"D11S4463\",\"value\":\"\"},{\"name\":\"D1S1627\",\"value\":\"\"},{\"name\":\"D3S4529\",\"value\":\"\"},{\"name\":\"D6S1017\",\"value\":\"\"},{\"name\":\"D4S2408\",\"value\":\"\"},{\"name\":\"D17S1301\",\"value\":\"\"},{\"name\":\"D1GATA113\",\"value\":\"\"},{\"name\":\"D18S853\",\"value\":\"\"},{\"name\":\"D20S482\",\"value\":\"\"},{\"name\":\"D14S1434\",\"value\":\"\"},{\"name\":\"D9S1122\",\"value\":\"\"},{\"name\":\"D2S1776\",\"value\":\"\"},{\"name\":\"D10S1435\",\"value\":\"\"},{\"name\":\"D5S2500\",\"value\":\"\"}]";
        CompareParams compareParams = new CompareParams();
        compareParams.setLowestSameLimit(11);
        compareParams.setMostDiffLimit(1);

        MixStrCompareResult mixStrCompareResult = compareToSingleStr(mixGeneInfoDetail, contributorCount, contributorList, targetGeneInfoDetail, compareParams);
        if(mixStrCompareResult != null){
            System.out.println(mixStrCompareResult.getMatchedLocusCount());
        }
    }
    */

    /**
     * 混合分型比对
     * @param mixGeneInfoDetail 混合基因位点信息
     * @param contributorCount 标记混合分型的贡献者数量 支持2人混合、3人混合
     * @param contributorList 1个或多个贡献者基因位点信息
     * @param targetGeneInfoDetail 比对目标基因位点信息
     * @param compareParams 比对参数
     * @return
     */
    public static MixStrCompareResult compareToSingleStr(String mixGeneInfoDetail,
                                                         Integer contributorCount,
                                                         List<String> contributorList,
                                                         String targetGeneInfoDetail, CompareParams compareParams){
        List<DnaGeneInfoDetail> mixGeneInfoDetailList = JSONArray.parseArray(mixGeneInfoDetail, DnaGeneInfoDetail.class);
        List<List<DnaGeneInfoDetail>> contributorGeneInfoDetailList = new ArrayList<>();
        if(ListUtils.isNotNullAndEmptyList(contributorList)){
            for(String contributor : contributorList){
                contributorGeneInfoDetailList.add(JSONArray.parseArray(contributor, DnaGeneInfoDetail.class));
            }
        }

        List<DnaGeneInfoDetail> targetGeneInfoDetailList = JSONArray.parseArray(targetGeneInfoDetail.toString(), DnaGeneInfoDetail.class);
        return compareToSingleStr(mixGeneInfoDetailList, contributorCount, contributorGeneInfoDetailList, targetGeneInfoDetailList, compareParams);
    }


    /**
     * 混合分型比对
     * @param mixGeneInfoDetailList 混合基因位点信息
     * @param contributorCount 标记混合分型的贡献者数量 支持2人混合、3人混合
     * @param contributorGeneInfoDetailList 贡献者基因位点信息
     * @param targetGeneInfoDetailList 比对目标基因位点信息
     * @param compareParams 比对参数
     * @return
     */
    private static MixStrCompareResult compareToSingleStr(List<DnaGeneInfoDetail> mixGeneInfoDetailList,
                                                         Integer contributorCount,
                                                         List<List<DnaGeneInfoDetail>> contributorGeneInfoDetailList,
                                                         List<DnaGeneInfoDetail> targetGeneInfoDetailList,
                                                         CompareParams compareParams){
        try {
            MixStrCompareResult result = compare(mixGeneInfoDetailList, targetGeneInfoDetailList, compareParams);

            /**
             * 混合分型找到匹配单一样本，并且存在已知贡献者分型时，通过已知贡献者的分型进行二次比对分析
             */
            if(result != null
                    && AlgorithmConstants.CONTRIBUTOR_COUNT_LIST.contains(contributorCount)
                    && ListUtils.isNotNullAndEmptyList(contributorGeneInfoDetailList)){
                /**
                 *  1、用已知贡献者基因位点从混合基因位点中找出贡献者每个位点中可能出现的组合
                 *  2、遍历所有位点的可能性组合，对照全包含比中的单一样本基因位点，确保匹配的位点均在排除已知贡献者后的组合中
                */
                List<Map<String, List<String>>> unknownAlleleGroupList = findExcludeKnownAlleleList(mixGeneInfoDetailList, contributorCount, contributorGeneInfoDetailList);

                //匹配的基因座个数
                int containsLocusCount = 0;
                //不匹配的基因座个数
                int notContainsLocusCount = 0;
                String locusName = null;
                String geneAlleleVal = null;
                List<String> geneAlleleValList = null;
                List<DnaGeneInfoDetail> dnaGeneInfoDetails = new ArrayList<>();
                for(DnaGeneInfoDetail targetGene : targetGeneInfoDetailList){
                    locusName = targetGene.getName();
                    geneAlleleVal = targetGene.getValue();
                    geneAlleleValList = ListStringUtils.string2List(geneAlleleVal, AlgorithmConstants.GENE_ALLELE_VALUE_SEPORATOR, true);
                    geneAlleleVal = ListStringUtils.list2String(geneAlleleValList, AlgorithmConstants.GENE_ALLELE_VALUE_SEPORATOR);

                    List<String> possibilityAlleleList = new ArrayList<>();
                    DnaGeneInfoDetail dnaGeneInfoBean = new DnaGeneInfoDetail();
                    dnaGeneInfoBean.setName(locusName);
                    for(Map<String, List<String>> map : unknownAlleleGroupList){
                        for(Map.Entry<String, List<String>> entry : map.entrySet()){
                            if(entry.getKey().equalsIgnoreCase(locusName)){
                                String values ="";
                                for (String value :entry.getValue()){
                                    values = value+",";
                                }
                                dnaGeneInfoBean.setValue(values.substring(0,values.length() -1));
                                possibilityAlleleList = entry.getValue();
                            }
                        }

                        if(ListUtils.isNotNullAndEmptyList(possibilityAlleleList)){
                            break;
                        }
                    }
                    dnaGeneInfoDetails.add(dnaGeneInfoBean);
                    if(ListUtils.isNotNullAndEmptyList(possibilityAlleleList)){
                        if(possibilityAlleleList.contains(geneAlleleVal)){
                            containsLocusCount++;
                        }else{
                            notContainsLocusCount++;
                        }
                    }
                }

                if(containsLocusCount >= compareParams.getLowestSameLimit()
                        && notContainsLocusCount <= compareParams.getMostDiffLimit()){
                    result.setMatchedLocusCount(containsLocusCount);
                    result.setDiffLocusCount(notContainsLocusCount);
                    result.setDnaGeneInfoDetailsList(dnaGeneInfoDetails);
                    result.setContributorGenePossibilityList(unknownAlleleGroupList);
                }else{
                    return null;
                }

            }
            return result;
        } catch(Exception ex){
            logger.error("invoke CommComparator.compareToSignleStr error.", ex);
            return null;
        }
    }

    private static MixStrCompareResult compare(List<DnaGeneInfoDetail> mixGeneInfoDetailList, List<DnaGeneInfoDetail> singleGeneInfoDetailList,
                                               CompareParams compareParams){
        MixStrCompareResult result = new MixStrCompareResult();

        //比中数
        int sameCount = 0;
        //容差数
        int diffCount = 0;

        List<MixStrCompareResultAllele> mixStrCompareResultAlleleList = new ArrayList<>();
        MixStrCompareResultAllele mixStrCompareResultAllele = null;

        //进行循环比对
        DnaGeneInfoDetail targetGeneDetail = null;
        for (DnaGeneInfoDetail mixGeneDetail : mixGeneInfoDetailList) {
            mixStrCompareResultAllele = new MixStrCompareResultAllele();

            String markerName = mixGeneDetail.getName();
            String mixAlleleVal = mixGeneDetail.getValue();

            mixStrCompareResultAllele.setLocusName(markerName);
            mixStrCompareResultAllele.setMixGeneAllele(mixAlleleVal);

            targetGeneDetail = findSameNameLocus(markerName, singleGeneInfoDetailList);
            if(targetGeneDetail == null){
                mixStrCompareResultAllele.setDiffAllele(false);
                mixStrCompareResultAlleleList.add(mixStrCompareResultAllele);
                continue;
            }

            String tarAlleleVal = targetGeneDetail.getValue();
            mixStrCompareResultAllele.setTargetGeneAllele(tarAlleleVal);

            //其中一个样本基因座等位基因为空时，跳过不比对
            if(StringUtils.isBlank(mixAlleleVal)
                    || "null".equalsIgnoreCase(mixAlleleVal)
                    || StringUtils.isBlank(tarAlleleVal)
                    || "null".equalsIgnoreCase(tarAlleleVal)){
                mixStrCompareResultAllele.setDiffAllele(false);
                mixStrCompareResultAlleleList.add(mixStrCompareResultAllele);
                continue;
            }

            if(isContainsSingleAllele(mixAlleleVal, tarAlleleVal)){
                sameCount++;
                mixStrCompareResultAllele.setDiffAllele(false);
            } else {
                diffCount++ ;
                mixStrCompareResultAllele.setDiffAllele(true);
            }

            mixStrCompareResultAlleleList.add(mixStrCompareResultAllele);
        }

        //匹配数大于等于匹配下限、容差数小于等于容差上限，认定为比中
        if(sameCount >= compareParams.getLowestSameLimit()
                && diffCount <= compareParams.getMostDiffLimit()){
            result.setMixStrCompareResultAlleleList(mixStrCompareResultAlleleList);
            result.setMatchedLocusCount(sameCount);
            result.setProportionCount(mixStrCompareResultAlleleList.size());
            result.setDiffLocusCount(diffCount);

            return result;
        }

        return null;
    }

    /**
     * 从混合基因位点中找出排除已知贡献者基因位点之后的，所有位点上可能出现的等位基因组合
     * @param mixGeneInfoDetailList
     * @param contributorCount
     * @param knownContributorList
     * @return
     */
    private static List<Map<String, List<String>>> findExcludeKnownAlleleList(List<DnaGeneInfoDetail> mixGeneInfoDetailList,
                                                Integer contributorCount,
                                                List<List<DnaGeneInfoDetail>> knownContributorList) throws Exception {
        List<Map<String, List<String>>> result = splitMixGeneByKnownGene(mixGeneInfoDetailList, contributorCount, knownContributorList);
        return result;
    }


    /**
     * 根据一个已知贡献者拆分2人混合的混合样本分型
     *
     * @param mixGeneInfoDetailList 混合基因分型
     * @param contributorCount 贡献者数量
     * @param knownGeneList 已知贡献者分型
     * @return
     */
    private static List<Map<String, List<String>>> splitMixGeneByKnownGene(
            List<DnaGeneInfoDetail> mixGeneInfoDetailList, Integer contributorCount, List<List<DnaGeneInfoDetail>> knownGeneList) throws Exception {
        List<Map<String, List<String>>> result = new ArrayList<>();

        /**
         * key： 基因座名称
         * value: 该基因座上可能出现的等位基因组合
         */
        Map<String, List<String>> excludeKnownAlleleMap = null;

        String locusName = null;
        String mixGeneAllele = null;
        DnaGeneInfoDetail knownGeneDetail1 = null;
        DnaGeneInfoDetail knownGeneDetail2 = null;
        for(DnaGeneInfoDetail mixGene : mixGeneInfoDetailList){
            locusName = mixGene.getName();
            mixGeneAllele = mixGene.getValue();

            if(StringUtils.isBlank(mixGeneAllele)){
                continue;
            }

            switch (contributorCount){
                default:
                case 2:
                    //混合分型为2人混合时
                    knownGeneDetail1 = findSameNameLocus(locusName, knownGeneList.get(0));
                    if(knownGeneDetail1 == null){
                        break;
                    }

                    excludeKnownAlleleMap = findAllelesSplitByKnownAlleleOfTwoContributors(locusName, mixGeneAllele, knownGeneDetail1.getValue());
                    break;
                case 3:
                    /**
                     * 混合分型为3人混合时，需分别判断1个贡献者、2个贡献者两种情况
                     */
                    knownGeneDetail1 = findSameNameLocus(locusName, knownGeneList.get(0));
                    if(knownGeneList.size() > 1){
                        knownGeneDetail2 = findSameNameLocus(locusName, knownGeneList.get(1));
                    }

                    /**
                     * 2个贡献者的等位基因都为空，继续外部循环判断下一个基因位点
                     */
                    if((knownGeneDetail1 == null || StringUtils.isBlank(knownGeneDetail1.getValue()))
                            && (knownGeneDetail2 == null || StringUtils.isBlank(knownGeneDetail2.getValue()))){
                        continue;
                    }

                    excludeKnownAlleleMap = findAllelesSplitByKnownAlleleOfThreeContributors(locusName, mixGeneAllele, (knownGeneDetail1 == null ? null : knownGeneDetail1.getValue()), (knownGeneDetail2 == null ? null : knownGeneDetail2.getValue()));
                    break;
            }

            if(excludeKnownAlleleMap != null
                    && excludeKnownAlleleMap.size() > 0) {
                result.add(excludeKnownAlleleMap);
            }
        }

        return result;
    }

    /**
     * 2人混合的样本，根据已知贡献者进行拆分组合
     * 排除已知的贡献者等位基因，获取混合等位基因中其他可能出现的组合
     * @param locusName 基因座名称
     * @param mixGeneAllele 混合样本等位基因
     * @param knownGeneAllele 已知样本等位基因
     * @return
     */
    private static Map<String, List<String>> findAllelesSplitByKnownAlleleOfTwoContributors(
            String locusName, String mixGeneAllele, String knownGeneAllele) throws Exception {
        Map<String, List<String>> resultMap = new HashMap<>();

        List<String> mixGeneAlleleList = ListStringUtils.string2List(mixGeneAllele, AlgorithmConstants.GENE_ALLELE_VALUE_SEPORATOR, true);
        List<String> knownGeneAlleleList = ListStringUtils.string2List(knownGeneAllele, AlgorithmConstants.GENE_ALLELE_VALUE_SEPORATOR, true);

        if(ListUtils.isNotNullAndEmptyList(knownGeneAlleleList)
                && !isContainsSingleAllele(mixGeneAlleleList, knownGeneAlleleList)){
            logger.warn("混合样本位点[" + locusName + "]等位基因中不包含已知贡献者的等位基因！");
            return null;
        }

        int mixAlleleSize = mixGeneAlleleList.size();
        int knownAlleleSize = knownGeneAlleleList.size();
        List<String> splitedAlleleList = new ArrayList<>();
        List<String> unknownGeneAllele = null;
        switch(mixAlleleSize){
            case 1:
                splitedAlleleList.add(mixGeneAlleleList.get(0));
                break;
            case 2:
                /**
                 * 已知贡献者等位基因个数为1时， 则混合位点中的另一个等位基因必定出现在未知贡献者的等位基因中，使用该等位基因进行可能性组合
                 *  如， 混合等位基因为11/12， 已知贡献者等位基因为11， 则未知贡献者必定出现等位基因12， 可能出现的组合有：12/12、 11/12
                 *
                 * 已知贡献者等位基因个数也为2时， 则2个等位基因都可能出现在未知贡献者的等位因组合中
                 *  如， 混合等位基因为11/12， 已知贡献者等位基因也是11/12， 则未知贡献者可能出现的组合包括：11/11、12/12、 11/12
                 */
                if(knownAlleleSize == 1){
                    unknownGeneAllele = findUnknownAllelesFromMixAlleles(mixGeneAlleleList, knownGeneAlleleList);

                    splitedAlleleList.add(unknownGeneAllele.get(0));
                    splitedAlleleList.add(sortAlleleValues(unknownGeneAllele.get(0), knownGeneAlleleList.get(0)));
                } else if(knownAlleleSize == 2) {
                    splitedAlleleList.add(knownGeneAlleleList.get(0));
                    splitedAlleleList.add(knownGeneAlleleList.get(1));
                    splitedAlleleList.add(knownGeneAlleleList.get(0) + AlgorithmConstants.GENE_ALLELE_VALUE_SEPORATOR + knownGeneAlleleList.get(1));
                }
                break;
            case 3:
                /**
                 * 已知贡献者等位基因个数为1时， 则混合位点中的另两个等位基因必定来源于未知贡献者
                 *  如， 混合等位基因为11/12/13， 已知贡献者等位基因为11， 则未知贡献者等位基因为12/13
                 *
                 * 已知贡献者等位基因个数为2时， 则混合等位基因中的另1个等位基因都必定出现在未知贡献者的等位因组合中，用该等位基因与其他等位基因进行组合
                 *  如， 混合等位基因为11/12/13， 已知贡献者等位基因也是11/12， 则未知贡献者可能出现的组合包括：13/13、11/13、 12/13
                 */
                if(knownAlleleSize == 1){
                    unknownGeneAllele = findUnknownAllelesFromMixAlleles(mixGeneAlleleList, knownGeneAlleleList);

                    splitedAlleleList.add(sortAlleleValues(unknownGeneAllele.get(0), unknownGeneAllele.get(1)));
                } else if(knownAlleleSize == 2) {
                    unknownGeneAllele = findUnknownAllelesFromMixAlleles(mixGeneAlleleList, knownGeneAlleleList);

                    splitedAlleleList.add(unknownGeneAllele.get(0));
                    splitedAlleleList.add(sortAlleleValues(unknownGeneAllele.get(0), knownGeneAlleleList.get(0)));
                    splitedAlleleList.add(sortAlleleValues(unknownGeneAllele.get(0), knownGeneAlleleList.get(1)));
                }

                break;
            case 4:
                /**
                 * 已知贡献者等位基因个数为1时，会导致混合等位基因有至少一个找不到来源，故不考虑该种场景
                 *  记录WARN日志
                 *
                 * 已知贡献者等位基因个数为2时， 则混合等位基因中的另2个等位基因必定为未知贡献者的等位因组合，不需要再考虑其他组合
                 *  如， 混合等位基因为11/12/13/14， 已知贡献者等位基因也是11/12， 则未知贡献者等位基因为 13/14
                 */
                if(knownAlleleSize == 1){
                    logger.warn("混合样本位点[" + locusName + "]为4个等位基因，已知贡献者只有1个纯合子等位基因，不满足匹配条件（混合等位基因至少一个找不到来源）！");
                    return null;
                }else if(knownAlleleSize == 2){
                    List<String> unknownGeneAllele1 = findUnknownAllelesFromMixAlleles(mixGeneAlleleList, knownGeneAlleleList);
                    splitedAlleleList.add(sortAlleleValues(unknownGeneAllele1.get(0), unknownGeneAllele1.get(1)));
                }

                break;
            default:
                throw new Exception("2个贡献者的混合分型中等位基因数量不在合理范围内。基因座名称：" + locusName);
        }

        resultMap.put(locusName, splitedAlleleList);
        return resultMap;
    }

    /**
     * 3人混合的样本，根据已知贡献者进行拆分组合
     * 排除已知的贡献者等位基因，获取混合等位基因中其他可能出现的组合
     * @param locusName 基因座名称
     * @param mixGeneAllele 混合样本等位基因
     * @param knownGeneAllele1 已知样本等位基因
     * @return
     */
    private static Map<String, List<String>> findAllelesSplitByKnownAlleleOfThreeContributors(
            String locusName, String mixGeneAllele, String knownGeneAllele1, String knownGeneAllele2) throws Exception {
        Map<String, List<String>> resultMap = new HashMap<>();

        List<String> mixGeneAlleleList = ListStringUtils.string2List(mixGeneAllele, AlgorithmConstants.GENE_ALLELE_VALUE_SEPORATOR, true);
        List<String> knownGeneAlleleList1 = ListStringUtils.string2List(knownGeneAllele1, AlgorithmConstants.GENE_ALLELE_VALUE_SEPORATOR, true);
        List<String> knownGeneAlleleList2 = ListStringUtils.string2List(knownGeneAllele2, AlgorithmConstants.GENE_ALLELE_VALUE_SEPORATOR, true);

        if(ListUtils.isNotNullAndEmptyList(knownGeneAlleleList1)
                && ListUtils.isNotNullAndEmptyList(knownGeneAlleleList2)
                && !isContainsSingleAllele(mixGeneAlleleList, knownGeneAlleleList1)
                && !isContainsSingleAllele(mixGeneAlleleList, knownGeneAlleleList2)){
            logger.warn("混合样本位点[" + locusName + "]等位基因中不包含已知贡献者的等位基因！");
            return null;
        }

        int mixAlleleSize = mixGeneAlleleList.size();
        int knownAlleleSize1 = knownGeneAlleleList1.size();
        int knownAlleleSize2 = knownGeneAlleleList2.size();

        List<String> splitedAlleleList = new ArrayList<>();
        List<String> unknownGeneAlleleList = findUnknownAllelesFromMixAlleles(mixGeneAlleleList, knownGeneAlleleList1, knownGeneAlleleList2);

        /**
         * 根据混合样本上的等位基因个数进行分类处理
         */
        switch(mixAlleleSize) {
            case 1:
                splitedAlleleList.add(mixGeneAlleleList.get(0));
                break;
            case 2:
                /**
                 * 如果排除已知贡献者后的等位基因为空，说明混合的2个等位基因均在已知贡献者中出现，则未知贡献者的等位基因为这2个等位基因的组合
                 *
                 * 如果不为空，则最多有一个等位基因未出现在已知贡献者等位基因中，用该等位基因进行组合
                 *
                 */
                if(ListUtils.isNullOrEmptyList(unknownGeneAlleleList)){
                    splitedAlleleList.add(mixGeneAlleleList.get(0));
                    splitedAlleleList.add(mixGeneAlleleList.get(1));
                    splitedAlleleList.add(sortAlleleValues(mixGeneAlleleList.get(0), mixGeneAlleleList.get(1)));
                }else{
                    splitedAlleleList.add(unknownGeneAlleleList.get(0));
                    splitedAlleleList.add(sortAlleleValues(mixGeneAlleleList.get(0), mixGeneAlleleList.get(1)));
                }

                break;
            case 3:
                /**
                 * 如果排除已知贡献者之后的等位基因为空，说明混合的3个等位基因均在已知贡献者中出现，则未知贡献者的等位基因为这3个等位基因的组合
                 *
                 * 如果排除后的等位基因为1， 则用这1个等位基因与混合样本的其他等位基因进行组合排列
                 *
                 * 如果排除后的等位基因为2， 则未知的等位基因确认为这2个的组合
                 *
                 * 不会出现3个或以上的情况，否则不满足前提条件
                 */
                if(ListUtils.isNullOrEmptyList(unknownGeneAlleleList)){
                    splitedAlleleList.add(mixGeneAlleleList.get(0));
                    splitedAlleleList.add(mixGeneAlleleList.get(1));
                    splitedAlleleList.add(mixGeneAlleleList.get(2));
                    splitedAlleleList.add(sortAlleleValues(mixGeneAlleleList.get(0), mixGeneAlleleList.get(1)));
                    splitedAlleleList.add(sortAlleleValues(mixGeneAlleleList.get(0), mixGeneAlleleList.get(2)));
                    splitedAlleleList.add(sortAlleleValues(mixGeneAlleleList.get(1), mixGeneAlleleList.get(2)));
                }else if(unknownGeneAlleleList.size() == 1){
                    String unknownAllele = unknownGeneAlleleList.get(0);
                    splitedAlleleList.add(unknownAllele);

                    if(!unknownAllele.equalsIgnoreCase(mixGeneAlleleList.get(0))){
                        splitedAlleleList.add(sortAlleleValues(unknownAllele, mixGeneAlleleList.get(0)));
                    }

                    if(!unknownAllele.equalsIgnoreCase(mixGeneAlleleList.get(1))){
                        splitedAlleleList.add(sortAlleleValues(unknownAllele, mixGeneAlleleList.get(1)));
                    }

                    if(!unknownAllele.equalsIgnoreCase(mixGeneAlleleList.get(2))){
                        splitedAlleleList.add(sortAlleleValues(unknownAllele, mixGeneAlleleList.get(2)));
                    }
                }else if(unknownGeneAlleleList.size() == 2){
                    splitedAlleleList.add(sortAlleleValues(unknownGeneAlleleList.get(0), unknownGeneAlleleList.get(1)));
                }
                break;
            case 4:
                /**
                 * 如果排除已知贡献者之后的等位基因为空，说明混合的4个等位基因均在已知贡献者中出现，则未知贡献者的等位基因为这4个等位基因的组合
                 *
                 */
                if(ListUtils.isNullOrEmptyList(unknownGeneAlleleList)){
                    splitedAlleleList.add(mixGeneAlleleList.get(0));
                    splitedAlleleList.add(mixGeneAlleleList.get(1));
                    splitedAlleleList.add(mixGeneAlleleList.get(2));
                    splitedAlleleList.add(mixGeneAlleleList.get(3));
                    splitedAlleleList.add(sortAlleleValues(mixGeneAlleleList.get(0), mixGeneAlleleList.get(1)));
                    splitedAlleleList.add(sortAlleleValues(mixGeneAlleleList.get(0), mixGeneAlleleList.get(2)));
                    splitedAlleleList.add(sortAlleleValues(mixGeneAlleleList.get(0), mixGeneAlleleList.get(3)));
                    splitedAlleleList.add(sortAlleleValues(mixGeneAlleleList.get(1), mixGeneAlleleList.get(2)));
                    splitedAlleleList.add(sortAlleleValues(mixGeneAlleleList.get(1), mixGeneAlleleList.get(3)));
                    splitedAlleleList.add(sortAlleleValues(mixGeneAlleleList.get(2), mixGeneAlleleList.get(3)));
                }else if(unknownGeneAlleleList.size() == 1){
                    /**
                     * 如果排除后的等位基因个数为1
                     *  如果只有1个贡献者，说明已知贡献者的等位基因个数为3， 不符合单一样本分型的标准
                     *
                     *  如果有2个贡献者，则用排除贡献者等位基因后的这1个等位基因与混合样本的其他等位基因进行组合排列
                     */
                    if(knownAlleleSize1 == 0 || knownAlleleSize2 == 0){
                        logger.warn("混合样本位点[" + locusName + "]中，已知贡献者的等位基因数不符合要求！");
                        return null;
                    }else{
                        String unknownAllele = unknownGeneAlleleList.get(0);
                        splitedAlleleList.add(unknownAllele);

                        if(!unknownAllele.equalsIgnoreCase(mixGeneAlleleList.get(0))){
                            splitedAlleleList.add(sortAlleleValues(unknownAllele, mixGeneAlleleList.get(0)));
                        }

                        if(!unknownAllele.equalsIgnoreCase(mixGeneAlleleList.get(1))){
                            splitedAlleleList.add(sortAlleleValues(unknownAllele, mixGeneAlleleList.get(1)));
                        }

                        if(!unknownAllele.equalsIgnoreCase(mixGeneAlleleList.get(2))){
                            splitedAlleleList.add(sortAlleleValues(unknownAllele, mixGeneAlleleList.get(2)));
                        }

                        if(!unknownAllele.equalsIgnoreCase(mixGeneAlleleList.get(3))){
                            splitedAlleleList.add(sortAlleleValues(unknownAllele, mixGeneAlleleList.get(3)));
                        }
                    }
                }else if(unknownGeneAlleleList.size() == 2){
                    /**
                     * 如果只有一个已知贡献者，会推断出该已知贡献者的等位基因是2个，
                     *  则剩余的2个等位基因只要有1个出现在未知贡献者中即表示包含关系
                     *
                     * 如果有2个已知贡献者，只剩余的2个等位基因全部来源自未知贡献者
                     */
                    if(knownAlleleSize1 == 0 || knownAlleleSize2 == 0){
                        splitedAlleleList.add(unknownGeneAlleleList.get(0));
                        splitedAlleleList.add(unknownGeneAlleleList.get(1));

                        if(knownAlleleSize1 == 0){
                            splitedAlleleList.add(sortAlleleValues(unknownGeneAlleleList.get(0), knownGeneAlleleList2.get(0)));
                            splitedAlleleList.add(sortAlleleValues(unknownGeneAlleleList.get(0), knownGeneAlleleList2.get(1)));
                            splitedAlleleList.add(sortAlleleValues(unknownGeneAlleleList.get(1), knownGeneAlleleList2.get(0)));
                            splitedAlleleList.add(sortAlleleValues(unknownGeneAlleleList.get(1), knownGeneAlleleList2.get(1)));
                        }else if(knownAlleleSize2 == 0){
                            splitedAlleleList.add(sortAlleleValues(unknownGeneAlleleList.get(0), knownGeneAlleleList1.get(0)));
                            splitedAlleleList.add(sortAlleleValues(unknownGeneAlleleList.get(0), knownGeneAlleleList1.get(1)));
                            splitedAlleleList.add(sortAlleleValues(unknownGeneAlleleList.get(1), knownGeneAlleleList1.get(0)));
                            splitedAlleleList.add(sortAlleleValues(unknownGeneAlleleList.get(1), knownGeneAlleleList1.get(1)));
                        }
                    }else{
                        splitedAlleleList.add(sortAlleleValues(unknownGeneAlleleList.get(0), unknownGeneAlleleList.get(1)));
                    }
                }else if(unknownGeneAlleleList.size() == 3){
                    /**
                     * 如果排除已知贡献者后的等位基因个数为3， 需判断已知贡献者是否只有1个，
                     *      如果只有1个已知贡献者，则未知贡献者的等位基因为这3个等位基因组合与已知贡献者纯合子等位基因的组合；
                     *      如：11/12/13/14， 已知的1个贡献者为11， 则未知贡献者的组合为：12/12、 13/13、 14/14、 11/12、 11/13、 11/14、 12/13、12/14、 13/14
                     *
                     *      如果有2个已知贡献者，则未知贡献者的等位基因为3个等位基因，不满足前提条件，排除此类情况
                     *
                     * 不会出现3个或以上的情况，否则不满足前提条件
                     */
                    if(knownAlleleSize1 == 0 || knownAlleleSize2 == 0){
                        splitedAlleleList.add(unknownGeneAlleleList.get(0));
                        splitedAlleleList.add(unknownGeneAlleleList.get(1));
                        splitedAlleleList.add(unknownGeneAlleleList.get(2));
                        splitedAlleleList.add(sortAlleleValues(unknownGeneAlleleList.get(0), unknownGeneAlleleList.get(1)));
                        splitedAlleleList.add(sortAlleleValues(unknownGeneAlleleList.get(0), unknownGeneAlleleList.get(2)));
                        splitedAlleleList.add(sortAlleleValues(unknownGeneAlleleList.get(1), unknownGeneAlleleList.get(2)));
                    }else{
                        //do nothing
                    }
                }

                break;
            case 5:
                /**
                 * 排除已知贡献者分型之后的等位基因数量为1时， 说明已知贡献者数量肯定为2个且均为杂合子，用排除后的等位基因与已知的四个等位基因进行排列组合即可
                 */
                if(unknownGeneAlleleList.size() == 1){
                    splitedAlleleList.add(unknownGeneAlleleList.get(0));
                    splitedAlleleList.add(sortAlleleValues(unknownGeneAlleleList.get(0), knownGeneAlleleList1.get(0)));
                    splitedAlleleList.add(sortAlleleValues(unknownGeneAlleleList.get(0), knownGeneAlleleList1.get(1)));
                    splitedAlleleList.add(sortAlleleValues(unknownGeneAlleleList.get(0), knownGeneAlleleList2.get(0)));
                    splitedAlleleList.add(sortAlleleValues(unknownGeneAlleleList.get(0), knownGeneAlleleList2.get(1)));
                }else if(unknownGeneAlleleList.size() == 2){
                    /**
                     * 排除已知贡献者分型之后的等位基因数量为1时， 说明已知贡献者数量为2个且其中一个为纯合子，则排除后的2个等位基因直接归为未知贡献者的等位基因
                     */
                    splitedAlleleList.add(sortAlleleValues(unknownGeneAlleleList.get(0), unknownGeneAlleleList.get(1)));
                }else if(unknownGeneAlleleList.size() == 3){
                    /**
                     * 如果已知贡献者数量为1个，说明已知贡献者为杂合子（2个等位基因均来源于该贡献者），剩余3个等位基因至少有一个来源于未知贡献者
                     *
                     * 如果已知贡献者数量为2个， 则未知贡献者等位基因为3个，不符合单一分型的要求，此种情况排除
                     */
                    if(knownAlleleSize1 == 0 || knownAlleleSize2 == 0){
                        splitedAlleleList.add(unknownGeneAlleleList.get(0));
                        splitedAlleleList.add(unknownGeneAlleleList.get(1));
                        splitedAlleleList.add(unknownGeneAlleleList.get(2));

                        if(knownAlleleSize1 == 0){
                            splitedAlleleList.add(sortAlleleValues(unknownGeneAlleleList.get(0), knownGeneAlleleList2.get(0)));
                            splitedAlleleList.add(sortAlleleValues(unknownGeneAlleleList.get(0), knownGeneAlleleList2.get(1)));
                            splitedAlleleList.add(sortAlleleValues(unknownGeneAlleleList.get(1), knownGeneAlleleList2.get(0)));
                            splitedAlleleList.add(sortAlleleValues(unknownGeneAlleleList.get(1), knownGeneAlleleList2.get(1)));
                            splitedAlleleList.add(sortAlleleValues(unknownGeneAlleleList.get(2), knownGeneAlleleList2.get(0)));
                            splitedAlleleList.add(sortAlleleValues(unknownGeneAlleleList.get(2), knownGeneAlleleList2.get(1)));
                        }else if(knownAlleleSize2 == 0){
                            splitedAlleleList.add(sortAlleleValues(unknownGeneAlleleList.get(0), knownGeneAlleleList1.get(0)));
                            splitedAlleleList.add(sortAlleleValues(unknownGeneAlleleList.get(0), knownGeneAlleleList1.get(1)));
                            splitedAlleleList.add(sortAlleleValues(unknownGeneAlleleList.get(1), knownGeneAlleleList1.get(0)));
                            splitedAlleleList.add(sortAlleleValues(unknownGeneAlleleList.get(1), knownGeneAlleleList1.get(1)));
                            splitedAlleleList.add(sortAlleleValues(unknownGeneAlleleList.get(2), knownGeneAlleleList1.get(0)));
                            splitedAlleleList.add(sortAlleleValues(unknownGeneAlleleList.get(2), knownGeneAlleleList1.get(1)));
                        }
                    }else{
                        //do nothing
                    }
                }

                break;
            case 6:
                if(unknownGeneAlleleList.size() == 2){
                    /**
                     * 排除已知贡献者等位基因后，剩余2个等位基因时，说明已知贡献者数量为2个，排除后的2个等位基因直接作为未知贡献者的等位基因
                     */
                    splitedAlleleList.add(sortAlleleValues(unknownGeneAlleleList.get(0), unknownGeneAlleleList.get(1)));
                }else if(unknownGeneAlleleList.size() == 3){
                    /**
                     * 排除已知贡献者等位基因后，剩余3个等位基因时，说明已知贡献者为2个，未知贡献者不存在3个等位基因的可能性，排除此种可能
                     */
                    //do nothing...
                }else{
                    //do nothing
                }

                break;
            default:
                throw new Exception("3个贡献者的混合分型中等位基因数量不在合理范围内。基因座名称：" + locusName);

        }

        resultMap.put(locusName, splitedAlleleList);
        return resultMap;
    }

    private static String sortAlleleValues(String alleleVal1, String alleleVal2){
        try{
            if(Integer.parseInt(alleleVal1) > Integer.parseInt(alleleVal2)){
                return alleleVal2 + AlgorithmConstants.GENE_ALLELE_VALUE_SEPORATOR + alleleVal1;
            }else{
                return alleleVal1 + AlgorithmConstants.GENE_ALLELE_VALUE_SEPORATOR + alleleVal2;
            }
        }catch(Exception ex){
            return alleleVal1.compareTo(alleleVal2) > -1
                    ? (alleleVal2 + AlgorithmConstants.GENE_ALLELE_VALUE_SEPORATOR + alleleVal1)
                    : (alleleVal1 + AlgorithmConstants.GENE_ALLELE_VALUE_SEPORATOR + alleleVal2) ;
        }
    }

    /**
     * 根据已知贡献者等位基因从混合等位基因中筛选出未知贡献者的等位基因集合
     * @param mixGeneAlleleList
     * @param knownGeneAlleleList
     * @return
     */
    private static List<String> findUnknownAllelesFromMixAlleles(List<String> mixGeneAlleleList, List<String> knownGeneAlleleList){
        List<String> unknownAlleleList = new ArrayList<>();

        for(String mixAllele : mixGeneAlleleList){
            if(!knownGeneAlleleList.contains(mixAllele)){
                unknownAlleleList.add(mixAllele);
            }
        }

        return unknownAlleleList;
    }

    private static List<String> findUnknownAllelesFromMixAlleles(List<String> mixGeneAlleleList, List<String> knownGeneAlleleList1, List<String> knownGeneAlleleList2){
        List<String> unknownAlleleList = new ArrayList<>();

        List<String> allKnownAlleleList = new ArrayList<>();
        /**
         * 1、将已知贡献者的等位基因合并到一个大的集合中
         */
        if(ListUtils.isNotNullAndEmptyList(knownGeneAlleleList1)){
            allKnownAlleleList.addAll(knownGeneAlleleList1);
        }
        if(ListUtils.isNotNullAndEmptyList(knownGeneAlleleList2)){
            allKnownAlleleList.addAll(knownGeneAlleleList2);
        }

        /**
         * 2、过滤掉重复等位基因
         */
        allKnownAlleleList = allKnownAlleleList.stream().distinct().collect(Collectors.toList());

        for(String mixAllele : mixGeneAlleleList){
            if(!allKnownAlleleList.contains(mixAllele)){
                unknownAlleleList.add(mixAllele);
            }
        }

        return unknownAlleleList;
    }


    /**
     * 判断同一位点的混合等位基因是否包含目标样本的等位基因
     * @param mixAlleleVal
     * @param singleAlleleVal
     * @return
     */
    private static boolean isContainsSingleAllele(String mixAlleleVal, String singleAlleleVal){
        List<String> mixAlleleList = ListStringUtils.string2List(mixAlleleVal, AlgorithmConstants.GENE_ALLELE_VALUE_SEPORATOR);
        List<String> singleAlleleList = ListStringUtils.string2List(singleAlleleVal, AlgorithmConstants.GENE_ALLELE_VALUE_SEPORATOR, true);

        try {
            return mixAlleleList.containsAll(singleAlleleList);
        } catch(Exception ex) {
            logger.error("判断混合样本的位点是否包含单一样本位点时出现异常。", ex);
            return false;
        }
    }

    /**
     * 判断同一位点的混合等位基因是否包含目标样本的等位基因
     * @param mixAlleleList
     * @param singleAlleleList
     * @return
     */
    private static boolean isContainsSingleAllele(List<String> mixAlleleList, List<String> singleAlleleList){
        try {
            return mixAlleleList.containsAll(singleAlleleList);
        } catch(Exception ex) {
            logger.error("判断混合样本的位点是否包含单一样本位点时出现异常。", ex);
            return false;
        }
    }

}
