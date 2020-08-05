package com.bazl.dna.mix.model.po;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class ContributorInfo implements Serializable {
    /**
     * 主键id
     */
    private String id;

    /**
     * 混合样本id
     */
    private String mixedSampleGeneId;

    /*
    *  样本编号
    * */
    private String sampleNo;

    /*
   *  样本名称
   * */
    private String sampleName;

    /**
     * 基因信息
     */
    private String geneInfo;

    /**
     * 基因图谱
     */
    private String genePicture;

    /*
    *  位点个数
    * */
    private String geneCount;

    /**
     * 创建人
     */
    private String createPerson;

    /**
     * 创建时间
     */
    private Date createDatetime;

    /**
     * 更新人
     */
    private String updatePereson;

    /**
     * 更新时间
     */
    private Date updateDatetime;

    //转换的基因信息
    private Map<String, Object> geneInfos;

    public String getGeneCount() {
        return geneCount;
    }

    public void setGeneCount(String geneCount) {
        this.geneCount = geneCount;
    }

    public Map<String, Object> getGeneInfos() {
        return geneInfos;
    }

    public void setGeneInfos(Map<String, Object> geneInfos) {
        this.geneInfos = geneInfos;
    }

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMixedSampleGeneId() {
        return mixedSampleGeneId;
    }

    public void setMixedSampleGeneId(String mixedSampleGeneId) {
        this.mixedSampleGeneId = mixedSampleGeneId;
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

    public String getGeneInfo() {
        return geneInfo;
    }

    public void setGeneInfo(String geneInfo) {
        this.geneInfo = geneInfo;
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

}