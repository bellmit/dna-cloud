package com.bazl.dna.lims.core.compare.model;

import com.bazl.dna.lims.core.utils.JsonDatetimeSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lzh
 * @date 2020/7/20.
 */
public class CaseSelfCompareParams implements Serializable {

    /**
     * 同型比对，匹配下限、容差上限
     */
    private int sameCompareMatchLimit;
    private int sameCompareDiffLimit;

    /**
     * 亲缘比对，匹配下限、容差上限
     */
    private int relativeCompareMatchLimit;
    private int relativeCompareDiffLimit;

    /**
     * 混合比对，最多贡献者个数、匹配下限、容差上限
     */
    private int mixContributorsCountLimit;
    private int mixCompareMatchLimit;
    private int mixCompareDiffLimit;

    /**
     * 种群频率id
     */
    private String populationId;

    public int getSameCompareMatchLimit() {
        return sameCompareMatchLimit;
    }

    public void setSameCompareMatchLimit(int sameCompareMatchLimit) {
        this.sameCompareMatchLimit = sameCompareMatchLimit;
    }

    public int getSameCompareDiffLimit() {
        return sameCompareDiffLimit;
    }

    public void setSameCompareDiffLimit(int sameCompareDiffLimit) {
        this.sameCompareDiffLimit = sameCompareDiffLimit;
    }

    public int getRelativeCompareMatchLimit() {
        return relativeCompareMatchLimit;
    }

    public void setRelativeCompareMatchLimit(int relativeCompareMatchLimit) {
        this.relativeCompareMatchLimit = relativeCompareMatchLimit;
    }

    public int getRelativeCompareDiffLimit() {
        return relativeCompareDiffLimit;
    }

    public void setRelativeCompareDiffLimit(int relativeCompareDiffLimit) {
        this.relativeCompareDiffLimit = relativeCompareDiffLimit;
    }

    public int getMixContributorsCountLimit() {
        return mixContributorsCountLimit;
    }

    public void setMixContributorsCountLimit(int mixContributorsCountLimit) {
        this.mixContributorsCountLimit = mixContributorsCountLimit;
    }

    public int getMixCompareMatchLimit() {
        return mixCompareMatchLimit;
    }

    public void setMixCompareMatchLimit(int mixCompareMatchLimit) {
        this.mixCompareMatchLimit = mixCompareMatchLimit;
    }

    public int getMixCompareDiffLimit() {
        return mixCompareDiffLimit;
    }

    public void setMixCompareDiffLimit(int mixCompareDiffLimit) {
        this.mixCompareDiffLimit = mixCompareDiffLimit;
    }

    public String getPopulationId() {
        return populationId;
    }

    public void setPopulationId(String populationId) {
        this.populationId = populationId;
    }
}