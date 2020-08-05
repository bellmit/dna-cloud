package com.bazl.dna.mix.model;

import com.bazl.dna.mix.model.po.CompareQueue;
import com.bazl.dna.mix.model.po.ContributorInfo;

import java.util.List;

public class CompareQueueModel {

    private List<CompareQueue> compareQueueList;

    private CompareQueue compare;

    private List<String> ids;

    private String compareId;

    private int page;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getCompareId() {
        return compareId;
    }

    public void setCompareId(String compareId) {
        this.compareId = compareId;
    }

    private List<ContributorInfo> contributorInfoList;

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public List<ContributorInfo> getContributorInfoList() {
        return contributorInfoList;
    }

    public void setContributorInfoList(List<ContributorInfo> contributorInfoList) {
        this.contributorInfoList = contributorInfoList;
    }

    public List<CompareQueue> getCompareQueueList() {
        return compareQueueList;
    }

    public void setCompareQueueList(List<CompareQueue> compareQueueList) {
        this.compareQueueList = compareQueueList;
    }

    public CompareQueue getCompare() {
        return compare;
    }

    public void setCompare(CompareQueue compare) {
        this.compare = compare;
    }
}
