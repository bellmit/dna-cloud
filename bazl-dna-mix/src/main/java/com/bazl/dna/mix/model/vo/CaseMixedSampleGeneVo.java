package com.bazl.dna.mix.model.vo;

import com.bazl.dna.mix.model.po.MixedSampleGene;

import java.io.Serializable;

/**
 * @Author: lzn
 * @Date: 2019/7/12 9:59
 * @Version: 1.0
 */
public class CaseMixedSampleGeneVo extends AbstractBaseVO<MixedSampleGene> implements Serializable {

	private static final long serialVersionUID = 1L;
	
    /**
     * 检材id
     */
    private String sampleid;
    /**
     * 案件id
     */
    private String caseId;
    /**
     * 基因id
     */
    private String geneId;
    /**
     * 试剂盒
     */
    private String reagentName;

    /**
     * 电泳板号
     */
    private String boardBarcode;

    /**
     * 案件编号
     */
    private String caseNo;

    /**
     * 案件名称
     */
    private String caseName;

    /**
     * 样本编号
     */
    private String sampleNo;

    /**
     * 样本名称
     */
    private String sampleName;

    public String getGeneId() {
        return geneId;
    }

    public void setGeneId(String geneId) {
        this.geneId = geneId;
    }

    public String getReagentName() {
        return reagentName;
    }

    public void setReagentName(String reagentName) {
        this.reagentName = reagentName;
    }

    public String getBoardBarcode() {
        return boardBarcode;
    }

    public void setBoardBarcode(String boardBarcode) {
        this.boardBarcode = boardBarcode;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
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

    public String getSampleid() {
        return sampleid;
    }

    public void setSampleid(String sampleid) {
        this.sampleid = sampleid;
    }
}
