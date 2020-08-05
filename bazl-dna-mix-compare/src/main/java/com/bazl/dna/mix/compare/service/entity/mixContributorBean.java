package com.bazl.dna.mix.compare.service.entity;

import com.bazl.dna.mix.compare.algorithm.model.DnaGeneInfoDetail;

import java.util.List;

public class mixContributorBean {

    private String sampleId;   //检材id
    private String sampleNo;  //检材编号
    private List<DnaGeneInfoDetail> geneInfo; //混合表基因信息
    private String contributorCount; //几人混合

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    public String getSampleNo() {
        return sampleNo;
    }

    public void setSampleNo(String sampleNo) {
        this.sampleNo = sampleNo;
    }

    public List<DnaGeneInfoDetail> getGeneInfo() {
        return geneInfo;
    }

    public void setGeneInfo(List<DnaGeneInfoDetail> geneInfo) {
        this.geneInfo = geneInfo;
    }

    public String getContributorCount() {
        return contributorCount;
    }

    public void setContributorCount(String contributorCount) {
        this.contributorCount = contributorCount;
    }
}
