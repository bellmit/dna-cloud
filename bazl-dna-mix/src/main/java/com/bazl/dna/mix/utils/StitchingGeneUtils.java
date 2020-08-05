package com.bazl.dna.mix.utils;

import com.bazl.dna.mix.model.po.GeneAllele;
import com.bazl.dna.mix.model.po.Contributor;
import com.bazl.dna.mix.model.po.ContributorGene;
import com.bazl.dna.mix.model.vo.RapiGeneComparisonVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @Author: lzn
 * @Date: 2019/8/9 11:13
 * @Version: 1.0
 * 解析贡献者拼接成混合样本
 */
public class StitchingGeneUtils {
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(GeneMixCompareUtil.class);

    /**
     * 解析贡献者拼接成混合样本基因
     * @param rapiGeneComparisonVo
     * @return
     */
    public static  List<Map<String, Object>> geneComparisonVo(RapiGeneComparisonVo rapiGeneComparisonVo) {
        //获取贡献者信息
        List<Contributor> contributorList = rapiGeneComparisonVo.getContributorList();
        List<Map<String, Object>> mixedSampleGeneList3= new ArrayList<>();
        List<Map<String, Object>> mixedSampleGeneList6= new ArrayList<>();
        List<Map<String, Object>> mixedSampleGeneList7= new ArrayList<>();
        try {

            if (ListUtils.isNotNullAndEmptyList(contributorList)) {
                int countList = contributorList.size();
                //如果为两个贡献者
                if (countList == 2) {
                    //混合基因信息
                    List<Map<String, Object>> mixedSampleGeneList1 = new ArrayList<>();
                    List<Map<String, Object>> mixedSampleGeneList2 = new ArrayList<>();
                    //获取第一个贡献者的基因信息
                    List<ContributorGene> contributorGeneList1 = contributorList.get(0).getContributorGeneList();
                    mixedSampleGeneList1=commonGeneUtils(contributorGeneList1);

                    //获取第二个贡献者的基因信息
                    List<ContributorGene> contributorGeneList2 = contributorList.get(1).getContributorGeneList();
                    mixedSampleGeneList2=commonGeneUtils(contributorGeneList2);
                    //两个贡献者比对
                    mixedSampleGeneList6=commonMixedSampleGeneUtils(mixedSampleGeneList1,mixedSampleGeneList2);

                }
                //如果为三个贡献者
                if (countList == 3) {
                    //混合基因信息
                    List<Map<String, Object>> mixedSampleGeneList1 = new ArrayList<>();
                    List<Map<String, Object>> mixedSampleGeneList2 = new ArrayList<>();
                    List<Map<String, Object>> mixedSampleGeneList4= new ArrayList<>();
                    //获取第一个贡献者的基因信息
                    List<ContributorGene> contributorGeneList1 = contributorList.get(0).getContributorGeneList();
                    mixedSampleGeneList1=commonGeneUtils(contributorGeneList1);

                    //获取第二个贡献者的基因信息
                    List<ContributorGene> contributorGeneList2 = contributorList.get(1).getContributorGeneList();
                    mixedSampleGeneList2=commonGeneUtils(contributorGeneList2);
                    //两个贡献者比对
                    mixedSampleGeneList3=commonMixedSampleGeneUtils(mixedSampleGeneList1,mixedSampleGeneList2);

                    //获取第三个贡献者的基因信息
                    List<ContributorGene> contributorGeneList3 = contributorList.get(2).getContributorGeneList();
                    mixedSampleGeneList4=commonGeneUtils(contributorGeneList3);

                    //判空
                    if (ListUtils.isNotNullAndEmptyList(mixedSampleGeneList4)&& ListUtils.isNotNullAndEmptyList(mixedSampleGeneList3)) {
                        //两个贡献者比对
                        mixedSampleGeneList6=commonMixedSampleGeneUtils(mixedSampleGeneList4,mixedSampleGeneList3);
                    }
                }
                //如果为四个贡献者
                if (countList == 4) {
                    //混合基因信息
                    List<Map<String, Object>> mixedSampleGeneList1 = new ArrayList<>();
                    List<Map<String, Object>> mixedSampleGeneList2 = new ArrayList<>();
                    List<Map<String, Object>> mixedSampleGeneList4= new ArrayList<>();
                    List<Map<String, Object>> mixedSampleGeneList5= new ArrayList<>();
                    //获取第一个贡献者的基因信息
                    List<ContributorGene> contributorGeneList1 = contributorList.get(0).getContributorGeneList();
                    mixedSampleGeneList1=commonGeneUtils(contributorGeneList1);

                    //获取第二个贡献者的基因信息
                    List<ContributorGene> contributorGeneList2 = contributorList.get(1).getContributorGeneList();
                    mixedSampleGeneList2=commonGeneUtils(contributorGeneList2);
                    //两个贡献者比对
                    mixedSampleGeneList3=commonMixedSampleGeneUtils(mixedSampleGeneList1,mixedSampleGeneList2);

                    //获取第三个贡献者的基因信息
                    List<ContributorGene> contributorGeneList3 = contributorList.get(2).getContributorGeneList();
                    mixedSampleGeneList4=commonGeneUtils(contributorGeneList3);
                    //判空
                    if (ListUtils.isNotNullAndEmptyList(mixedSampleGeneList4)&& ListUtils.isNotNullAndEmptyList(mixedSampleGeneList3)) {
                        //两个贡献者比对
                        mixedSampleGeneList7=commonMixedSampleGeneUtils(mixedSampleGeneList4,mixedSampleGeneList3);
                    }
                    //获取第四个贡献者的基因信息
                    List<ContributorGene> contributorGeneList4= contributorList.get(2).getContributorGeneList();
                    mixedSampleGeneList5=commonGeneUtils(contributorGeneList4);

                    //判空
                    if (ListUtils.isNotNullAndEmptyList(mixedSampleGeneList7)&& ListUtils.isNotNullAndEmptyList(mixedSampleGeneList5)) {
                        //两个贡献者比对
                        mixedSampleGeneList6=commonMixedSampleGeneUtils(mixedSampleGeneList7,mixedSampleGeneList5);
                    }
                }
            }
        }catch (Exception e){
            logger.error("解析贡献者拼接成混合样本基因失败！！"+e);
        }

        return mixedSampleGeneList6;
    }

    /**
     * 两个贡献者比对
     * @param mixedSampleGeneList1
     * @param mixedSampleGeneList2
     * @return
     */
    private static List<Map<String, Object>> commonMixedSampleGeneUtils(List<Map<String, Object>> mixedSampleGeneList1,
                                                                        List<Map<String, Object>> mixedSampleGeneList2) {
        List<Map<String, Object>> mixedSampleGeneList3 = new ArrayList<>();
        Map<String, Object> map = null;
        if (ListUtils.isNotNullAndEmptyList(mixedSampleGeneList1)
                && ListUtils.isNotNullAndEmptyList(mixedSampleGeneList2)) {

            if (ListUtils.isNotNullAndEmptyList(mixedSampleGeneList1)) {
                for (Map<String, Object> tarStr: mixedSampleGeneList1) {
                    String markerName1 = tarStr.get("markerName").toString();
                    for (Map<String, Object> strStr: mixedSampleGeneList2) {
                        String markerName2= strStr.get("markerName").toString();
                        if(markerName1.equals(markerName2)){
                            String allele1 = tarStr.get("allele")+","+ strStr.get("allele");
                            String[] strArr = allele1.split(",");
                            //去重重复的数据
                            List<String> list = Arrays.asList(strArr);
                            List<String> listTemp = new ArrayList<>();
                            for (int i = 0; i < list.size(); i++) {
                                if (!listTemp.contains(list.get(i))) {
                                    listTemp.add(list.get(i));
                                }
                            }
                            //删除字母Q
                            List<String> arrList = new ArrayList<>(listTemp);
                            arrList.remove("Q");
                            //重新拼接
                            String genesb = String.join(",", arrList);
                            map = new LinkedHashMap<>();
                            map.put("markerName", markerName2);
                            map.put("allele", genesb);
                            //组成新的list集合
                            mixedSampleGeneList3.add(map);
                        }
                    }
                }
            }

        }
        return mixedSampleGeneList3;
    }

    /**
     * 公共方法
     * @param contributorGeneList1
     * @return
     */
    private static List<Map<String, Object>> commonGeneUtils(List<ContributorGene> contributorGeneList1) {
        List<Map<String, Object>> mixedSampleGeneList1 = new ArrayList<>();
        Map<String, Object> map = null;
        for (ContributorGene contributorGene : contributorGeneList1) {
            //基因信息
            List<GeneAllele> geneAlleleList = contributorGene.getGeneAlleleList();
            if (ListUtils.isNotNullAndEmptyList(geneAlleleList)) {
                String geneName = contributorGene.getGeneName();
                StringBuffer sb = new StringBuffer();
                String allele = null;
                //循环基因位点
                for (int j = 0; j < geneAlleleList.size(); j++) {
                    allele = geneAlleleList.get(j).getAllele();
                    sb.append(allele).append(",");
                }
                String str = sb.toString();
                //去除，
                String srcAllele2 = str.substring(0, str.length() - 1);
                String[] strArr = srcAllele2.split(",");
                //去重重复的数据
                List<String> list = Arrays.asList(strArr);
                List<String> listTemp = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    if (!listTemp.contains(list.get(i))) {
                        listTemp.add(list.get(i));

                    }
                }
                //删除字母Q
                List<String> arrList = new ArrayList<>(listTemp);
                arrList.remove("Q");
                //重新拼接
                String genesb = String.join(",", listTemp);
                map = new LinkedHashMap<>();
                map.put("markerName", geneName);
                map.put("allele", genesb);
                //组成新的list集合
                mixedSampleGeneList1.add(map);
            }
        }
        return mixedSampleGeneList1;
    }
}
