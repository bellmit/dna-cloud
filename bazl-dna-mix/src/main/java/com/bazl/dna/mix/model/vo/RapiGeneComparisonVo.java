package com.bazl.dna.mix.model.vo;

import com.bazl.dna.mix.model.po.Contributor;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: lzn
 * @Date: 2019/7/26 9:04
 * @Version: 1.0
 */
public class RapiGeneComparisonVo implements Serializable {

	private static final long serialVersionUID = 1L;
        //贡献者数量
       private  int contributorCount;

        //贡献者基因信息
       private List<Contributor>  contributorList;

    public int getContributorCount() {
        return contributorCount;
    }

    public void setContributorCount(int contributorCount) {
        this.contributorCount = contributorCount;
    }

    public List<Contributor> getContributorList() {
        return contributorList;
    }

    public void setContributorList(List<Contributor> contributorList) {
        this.contributorList = contributorList;
    }
}
