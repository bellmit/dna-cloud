package com.bazl.dna.mix.model.po;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


/**
 * SINGLE_SAMPLE_GENE
 * @author
 */
public class SingleSampleGene implements Serializable {
    /**
     * 主键
     */
    private String id;

    /**
     * 样本ID
     */
    private String sampleId;

    /**
     * 试剂盒
     */
    private String reagentName;

    /**
     * 电泳板号
     */
    private String boardBarcode;

    /**
     * 基因图谱
     */
    private String genePicture;

    /**
     * 创建人
     */
    private String createPerson;

    /**
     * 创建时间
     */
    @JSONField(name = "createDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createDatetime;

    /**
     * 更新人
     */
    private String updatePereson;

    /**
     * 更新时间
     */
    @JSONField(name = "updateDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updateDatetime;

    /**
     * 基因信息
     */
    private String geneInfo;

    //审核基因id
    private String labAnalysisInfoId;

    //试剂盒名称
    private String panelName;

    //试剂盒
    private String panelId;


    //基因图谱
    private String imagePath;

    //电泳板号
    private String boardNo;

    //案件id
    private String caseID;

    //lims库的主键id
    private String geneId;

    public String getPanelName() {
        return panelName;
    }

    public void setPanelName(String panelName) {
        this.panelName = panelName;
    }

    public String getGeneId() {
        return geneId;
    }

    public void setGeneId(String geneId) {
        this.geneId = geneId;
    }

    public String getLabAnalysisInfoId() {
        return labAnalysisInfoId;
    }

    public void setLabAnalysisInfoId(String labAnalysisInfoId) {
        this.labAnalysisInfoId = labAnalysisInfoId;
    }

    public String getPanelId() {
        return panelId;
    }

    public void setPanelId(String panelId) {
        this.panelId = panelId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getBoardNo() {
        return boardNo;
    }

    public void setBoardNo(String boardNo) {
        this.boardNo = boardNo;
    }

    private static final long serialVersionUID = 1L;

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

    public String getGenePicture() {
        return genePicture;
    }

    public void setGenePicture(String genePicture) {
        this.genePicture = genePicture;
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getUpdatePereson() {
        return updatePereson;
    }

    public void setUpdatePereson(String updatePereson) {
        this.updatePereson = updatePereson;
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public String getGeneInfo() {
        return geneInfo;
    }

    public void setGeneInfo(String geneInfo) {
        this.geneInfo = geneInfo;
    }

    public String getCaseID() {
        return caseID;
    }

    public void setCaseID(String caseID) {
        this.caseID = caseID;
    }

    @Override
    public String toString() {
        return "SingleSampleGene{" +
                "id='" + id + '\'' +
                ", sampleId='" + sampleId + '\'' +
                ", reagentName='" + reagentName + '\'' +
                ", boardBarcode='" + boardBarcode + '\'' +
                ", genePicture='" + genePicture + '\'' +
                ", createPerson='" + createPerson + '\'' +
                ", createDatetime=" + createDatetime +
                ", updatePereson='" + updatePereson + '\'' +
                ", updateDatetime=" + updateDatetime +
                ", geneInfo='" + geneInfo + '\'' +
                ", labAnalysisInfoId='" + labAnalysisInfoId + '\'' +
                ", panelId='" + panelId + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", boardNo='" + boardNo + '\'' +
                ", caseID='" + caseID + '\'' +
                '}';
    }
}