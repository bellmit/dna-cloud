package com.bazl.dna.mix.compare.service.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * MIXED_SAMPLE_GENE
 * @author
 */
public class MixedSampleGene implements Serializable , Comparable<MixedSampleGene> {
    /**
     * 主键
     */
    private String id;

    /**
     * 样本ID
     */
    private String sampleId;

    /**
     * 样本编号
     */
    private String sampleNo;

    /**
     * 样本编号
     */
    private String sampleName;

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

    /*
    *   混合人数 CONTRIBUTOR_COUNT
    * */
    private String contributorCount;

    /**
     * 基因信息
     */
    private String geneInfo;

    /*
    * 是否删除
    * */
    private String isDeleted;

    //审核基因id
    private String labAnalysisInfoId;

    //试剂盒
    private String panelId;

    //基因图谱
    private String imagePath;

    //电泳板号
    private String boardNo;
    //案件id
    private String caseID;

    //业务字段  分组名称
    private String groupName;

    //业务字段  比中类型
    private String sampleFlag;

    private String ratio;

    private String sumCount;

    //转换的基因信息
    private Map<String, Object> geneInfos;

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Map<String, Object> getGeneInfos() {
        return geneInfos;
    }

    public void setGeneInfos(Map<String, Object> geneInfos) {
        this.geneInfos = geneInfos;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }

    public String getSumCount() {
        return sumCount;
    }

    public void setSumCount(String sumCount) {
        this.sumCount = sumCount;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getSampleFlag() {
        return sampleFlag;
    }

    public void setSampleFlag(String sampleFlag) {
        this.sampleFlag = sampleFlag;
    }

    public String getContributorCount() {
        return contributorCount;
    }

    public void setContributorCount(String contributorCount) {
        this.contributorCount = contributorCount;
    }

    public String getSampleNo() {
        return sampleNo;
    }

    public void setSampleNo(String sampleNo) {
        this.sampleNo = sampleNo;
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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    private Map<String, Object> resultGeneInfo;

    public Map<String, Object> getResultGeneInfo() {
        return resultGeneInfo;
    }

    public void setResultGeneInfo(Map<String, Object> resultGeneInfo) {
        this.resultGeneInfo = resultGeneInfo;
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
    public int compareTo(MixedSampleGene o) {
        int str = Integer.parseInt(this.groupName.substring(6));
        int otr = Integer.parseInt(o.groupName.substring(6));
        if (str>otr){
            return 1;
        }else if (str<otr){
            return -1;
        }
        return 0;
    }
}