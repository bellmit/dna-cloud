package com.bazl.dna.mix.model.vo;

import java.io.Serializable;
import java.util.List;

import com.bazl.dna.common.PageInfo;

/**
 * Created by Administrator on 2020/1/19.
 */
public class MatchResultDetailsListVo implements Serializable {

	private static final long serialVersionUID = 1L;
    private List<MatchResultVo> matchResultList;
    private Integer splitedCount;
    private Integer mixedCount;
    private Integer splitedMixedCount;
    private PageInfo pageInfo;

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<MatchResultVo> getMatchResultList() {
        return matchResultList;
    }

    public void setMatchResultList(List<MatchResultVo> matchResultList) {
        this.matchResultList = matchResultList;
    }

    public Integer getSplitedMixedCount() {
        return splitedMixedCount;
    }

    public void setSplitedMixedCount(Integer splitedMixedCount) {
        this.splitedMixedCount = splitedMixedCount;
    }

    public Integer getSplitedCount() {
        return splitedCount;
    }

    public void setSplitedCount(Integer splitedCount) {
        this.splitedCount = splitedCount;
    }

    public Integer getMixedCount() {
        return mixedCount;
    }

    public void setMixedCount(Integer mixedCount) {
        this.mixedCount = mixedCount;
    }


}
