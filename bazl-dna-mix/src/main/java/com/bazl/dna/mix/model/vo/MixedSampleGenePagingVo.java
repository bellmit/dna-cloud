package com.bazl.dna.mix.model.vo;

import java.io.Serializable;
import java.util.List;

import com.bazl.dna.mix.config.MixedGeneInfoDetail;


public class MixedSampleGenePagingVo implements Serializable {

	private static final long serialVersionUID = 1L;
    /**
     * 混合库 新mysql
     */
    private String id;
    private String sampleId;
    private String sampleNo;
    private String sampleName;
    private String reagentName;
    private String boardBarcode;
    private String geneInfo;
    private String geneImagePath;
    private String createPerson;
    private String createDatetime;
    private String updatePereson;
    private String updateDatetime;
    private String contributorCount;
    private Integer isDeleted;

    private List<MixedGeneInfoDetail> geneInfoDetail;

    /**
     * 关联其他表的字段信息
     */
    private String caseNo;
    private String caseName;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
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

    public String getGeneInfo() {
        return geneInfo;
    }

    public void setGeneInfo(String geneInfo) {
        this.geneInfo = geneInfo;
    }

    public String getGeneImagePath() {
        return geneImagePath;
    }

    public void setGeneImagePath(String geneImagePath) {
        this.geneImagePath = geneImagePath;
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public String getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(String createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getUpdatePereson() {
        return updatePereson;
    }

    public void setUpdatePereson(String updatePereson) {
        this.updatePereson = updatePereson;
    }

    public String getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(String updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public String getContributorCount() {
        return contributorCount;
    }

    public void setContributorCount(String contributorCount) {
        this.contributorCount = contributorCount;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
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

    public List<MixedGeneInfoDetail> getGeneInfoDetail() {
        return geneInfoDetail;
    }

    public void setGeneInfoDetail(List<MixedGeneInfoDetail> geneInfoDetail) {
        this.geneInfoDetail = geneInfoDetail;
    }
}
