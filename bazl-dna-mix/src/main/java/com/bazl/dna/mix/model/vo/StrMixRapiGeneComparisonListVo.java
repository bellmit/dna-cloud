package com.bazl.dna.mix.model.vo;

import com.bazl.dna.mix.model.po.ContributorGene;
import com.bazl.dna.mix.model.po.MoreThanTheCount;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Author: lzn
 * @Date: 2019/7/28 16:12
 * @Version: 1.0
 */
public class StrMixRapiGeneComparisonListVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
    //贡献者的名字
    private String contributorName;

    //贡献者的权重
    private String contributorWeight;
    //案件id
    private String caseId;
    //基因id
    private String geneId;


    //贡献者的权重大于99%的数量
    private List<MoreThanTheCount> moreThanTheCountList;
    //基因信息list
    private List<ContributorGene> contributorGeneList;
    //快速比对大于99%的基因信息
    private List<Map<String, Object>> moreGeneList0;
    private List<Map<String, Object>> moreGeneList1;
    private List<Map<String, Object>> moreGeneList2;
    private List<Map<String, Object>> moreGeneList3;
    private List<Map<String, Object>> moreGeneList4;
    private List<Map<String, Object>> moreGeneList5;
    private List<Map<String, Object>> moreGeneList6;
    private List<Map<String, Object>> moreGeneList7;
    private List<Map<String, Object>> moreGeneList8;
    private List<Map<String, Object>> moreGeneList9;


    public String getContributorWeight() {
        return contributorWeight;
    }

    public void setContributorWeight(String contributorWeight) {
        this.contributorWeight = contributorWeight;
    }

    public List<ContributorGene> getContributorGeneList() {
        return contributorGeneList;
    }

    public void setContributorGeneList(List<ContributorGene> contributorGeneList) {
        this.contributorGeneList = contributorGeneList;
    }

    public String getContributorName() {
        return contributorName;
    }

    public void setContributorName(String contributorName) {
        this.contributorName = contributorName;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getGeneId() {
        return geneId;
    }

    public void setGeneId(String geneId) {
        this.geneId = geneId;
    }

    public List<Map<String, Object>> getMoreGeneList1() {
        return moreGeneList1;
    }

    public void setMoreGeneList1(List<Map<String, Object>> moreGeneList1) {
        this.moreGeneList1 = moreGeneList1;
    }

    public List<Map<String, Object>> getMoreGeneList2() {
        return moreGeneList2;
    }

    public void setMoreGeneList2(List<Map<String, Object>> moreGeneList2) {
        this.moreGeneList2 = moreGeneList2;
    }

    public List<Map<String, Object>> getMoreGeneList3() {
        return moreGeneList3;
    }

    public void setMoreGeneList3(List<Map<String, Object>> moreGeneList3) {
        this.moreGeneList3 = moreGeneList3;
    }

    public List<Map<String, Object>> getMoreGeneList4() {
        return moreGeneList4;
    }

    public void setMoreGeneList4(List<Map<String, Object>> moreGeneList4) {
        this.moreGeneList4 = moreGeneList4;
    }

    public List<Map<String, Object>> getMoreGeneList5() {
        return moreGeneList5;
    }

    public void setMoreGeneList5(List<Map<String, Object>> moreGeneList5) {
        this.moreGeneList5 = moreGeneList5;
    }

    public List<Map<String, Object>> getMoreGeneList6() {
        return moreGeneList6;
    }

    public void setMoreGeneList6(List<Map<String, Object>> moreGeneList6) {
        this.moreGeneList6 = moreGeneList6;
    }

    public List<Map<String, Object>> getMoreGeneList7() {
        return moreGeneList7;
    }

    public void setMoreGeneList7(List<Map<String, Object>> moreGeneList7) {
        this.moreGeneList7 = moreGeneList7;
    }

    public List<Map<String, Object>> getMoreGeneList8() {
        return moreGeneList8;
    }

    public void setMoreGeneList8(List<Map<String, Object>> moreGeneList8) {
        this.moreGeneList8 = moreGeneList8;
    }

    public List<Map<String, Object>> getMoreGeneList9() {
        return moreGeneList9;
    }

    public void setMoreGeneList9(List<Map<String, Object>> moreGeneList9) {
        this.moreGeneList9 = moreGeneList9;
    }

    public List<Map<String, Object>> getMoreGeneList0() {
        return moreGeneList0;
    }

    public void setMoreGeneList0(List<Map<String, Object>> moreGeneList0) {
        this.moreGeneList0 = moreGeneList0;
    }

    public List<MoreThanTheCount> getMoreThanTheCountList() {
        return moreThanTheCountList;
    }

    public void setMoreThanTheCountList(List<MoreThanTheCount> moreThanTheCountList) {
        this.moreThanTheCountList = moreThanTheCountList;
    }
}
