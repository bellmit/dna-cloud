package com.bazl.dna.mix.model.vo;

import com.bazl.dna.mix.model.po.SplitedSampleGene;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/4.
 */
public class SplitedSampleGeneVo extends AbstractBaseVO<SplitedSampleGene> implements Serializable {

	private static final long serialVersionUID = 1L;
	
    public SplitedSampleGeneVo() {
        super();
        this.entity = new SplitedSampleGene();
    }

    public SplitedSampleGeneVo(SplitedSampleGene entity) {
        super();
        this.entity = entity;
    }

    private String compareQueueId;

    private String sampleNo;

    private String sampleName;

    private String geneInfo;

    private Map<String, Object> geneMap;

    public Map<String, Object> getGeneMap() {
        return geneMap;
    }

    public void setGeneMap(Map<String, Object> geneMap) {
        this.geneMap = geneMap;
    }

    public String getCompareQueueId() {
        return compareQueueId;
    }

    public void setCompareQueueId(String compareQueueId) {
        this.compareQueueId = compareQueueId;
    }

    public String getGeneInfo() {
        return geneInfo;
    }

    public void setGeneInfo(String geneInfo) {
        this.geneInfo = geneInfo;
    }

    public String getSampleNo() {
        return sampleNo;
    }

    public void setSampleNo(String sampleNo) {
        this.sampleNo = sampleNo;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }
}
