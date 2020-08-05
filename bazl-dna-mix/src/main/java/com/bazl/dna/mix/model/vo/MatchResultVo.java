package com.bazl.dna.mix.model.vo;

import com.bazl.dna.mix.model.po.MatchResult;

import java.io.Serializable;

/**
 * Created by Administrator on 2020/1/19.
 */
public class MatchResultVo extends AbstractBaseVO<MatchResult> implements Serializable {

	private static final long serialVersionUID = 1L;
	
    public MatchResultVo() {
        super();
        this.entity = new MatchResult();
    }

    public MatchResultVo(MatchResult entity) {
        super();
        this.entity = entity;
    }

    //混合样本编号
    private String mixedSampleNo;
    //混合样本名称
    private String mixedSampleName;

    //拆分样本数
    private Integer splitedSampleCount;
    //混合样本数
    private Integer mixedSampleCount;

    //混合样本id
    private String mixedId;
    //拆分样本id
    private String splitedId;

    public String getMixedSampleNo() {
        return mixedSampleNo;
    }

    public void setMixedSampleNo(String mixedSampleNo) {
        this.mixedSampleNo = mixedSampleNo;
    }

    public String getMixedSampleName() {
        return mixedSampleName;
    }

    public void setMixedSampleName(String mixedSampleName) {
        this.mixedSampleName = mixedSampleName;
    }

    public Integer getSplitedSampleCount() {
        return splitedSampleCount;
    }

    public void setSplitedSampleCount(Integer splitedSampleCount) {
        this.splitedSampleCount = splitedSampleCount;
    }

    public Integer getMixedSampleCount() {
        return mixedSampleCount;
    }

    public void setMixedSampleCount(Integer mixedSampleCount) {
        this.mixedSampleCount = mixedSampleCount;
    }

    public String getMixedId() {
        return mixedId;
    }

    public void setMixedId(String mixedId) {
        this.mixedId = mixedId;
    }

    public String getSplitedId() {
        return splitedId;
    }

    public void setSplitedId(String splitedId) {
        this.splitedId = splitedId;
    }
}
