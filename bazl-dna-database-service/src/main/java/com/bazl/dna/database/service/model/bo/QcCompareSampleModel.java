package com.bazl.dna.database.service.model.bo;

import com.bazl.dna.database.service.model.po.DnaGeneInfoDetail;

import java.io.Serializable;
import java.util.List;

/**
 * 待质控比对样本 模型
 * Created by lizhihua on 2020-03-03.
 */
public class QcCompareSampleModel implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String sampleBoardNo;

    private String sampleNo;

    private String sampleName;

    private List<DnaGeneInfoDetail> sampleGeneInfo;

    public String getSampleBoardNo() {
        return sampleBoardNo;
    }

    public void setSampleBoardNo(String sampleBoardNo) {
        this.sampleBoardNo = sampleBoardNo;
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

    public List<DnaGeneInfoDetail> getSampleGeneInfo() {
        return sampleGeneInfo;
    }

    public void setSampleGeneInfo(List<DnaGeneInfoDetail> sampleGeneInfo) {
        this.sampleGeneInfo = sampleGeneInfo;
    }
}
