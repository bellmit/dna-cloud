package com.bazl.dna.mix.model.po;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: lzn
 * @Date: 2019/7/26 19:40
 * @Version: 1.0
 */
public class Contributor implements Serializable {

	private static final long serialVersionUID = 1L;
	private String contributorName;
    //贡献者占比
    private String contributorWeight;
    //基因数量
    private int geneCount;
   //基因信息
    private List<ContributorGene> contributorGeneList;

    public String getContributorName() {
        return contributorName;
    }

    public void setContributorName(String contributorName) {
        this.contributorName = contributorName;
    }

    public int getGeneCount() {
        return geneCount;
    }

    public void setGeneCount(int geneCount) {
        this.geneCount = geneCount;
    }

    public List<ContributorGene> getContributorGeneList() {
        return contributorGeneList;
    }

    public void setContributorGeneList(List<ContributorGene> contributorGeneList) {
        this.contributorGeneList = contributorGeneList;
    }

    public String getContributorWeight() {
        return contributorWeight;
    }

    public void setContributorWeight(String contributorWeight) {
        this.contributorWeight = contributorWeight;
    }
}
