package com.bazl.dna.mix.model.po;

import java.io.Serializable;

/**
 * contributor_possibility
 * @author 
 */
public class ContributorPossibility implements Serializable {
    /**
     * 主键id
     */
    private String id;

    /**
     * 比对id
     */
    private String compareId;

    /**
     * 拆分贡献者可能比中基因
     */
    private String geneInfo;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompareId() {
        return compareId;
    }

    public void setCompareId(String compareId) {
        this.compareId = compareId;
    }

    public String getGeneInfo() {
        return geneInfo;
    }

    public void setGeneInfo(String geneInfo) {
        this.geneInfo = geneInfo;
    }
}