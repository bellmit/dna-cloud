package com.bazl.dna.mix.model.po;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * SAMPLE_INFO
 * @author 
 */
public class SampleInfo implements Serializable {
    /**
     * 主键
     */
    private String id;

    /**
     * 案件ID
     */
    private String caseId;

    /**
     * 样本编号
     */
    private String sampleNo;

    /**
     * 样本名称
     */
    private String sampleName;

    /**
     * 样本类型
     */
    private String sampleType;

    /**
     * 样本标识
     */
    private String sampleFlag;

    /**
     * 关联人员ID
     */
    private String refPersonId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    private String createPerson;

    /**
     * 创建时间
     */
//    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
//    @JsonSerialize(using = JsonDatetimeSerializer.class)
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
//    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
//    @JsonSerialize(using = JsonDatetimeSerializer.class)
    @JSONField(name = "createDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updateDatetime;

    //样本id
    private String sampleId;

    private String linkId;

    private String sampleRemark;
    //是否入库
    private String instoredFlag;

    public String getInstoredFlag() {
        return instoredFlag;
    }

    public void setInstoredFlag(String instoredFlag) {
        this.instoredFlag = instoredFlag;
    }

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public String getSampleRemark() {
        return sampleRemark;
    }

    public void setSampleRemark(String sampleRemark) {
        this.sampleRemark = sampleRemark;
    }

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
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

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public String getSampleFlag() {
        return sampleFlag;
    }

    public void setSampleFlag(String sampleFlag) {
        this.sampleFlag = sampleFlag;
    }

    public String getRefPersonId() {
        return refPersonId;
    }

    public void setRefPersonId(String refPersonId) {
        this.refPersonId = refPersonId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    @Override
    public String toString() {
        return "SampleInfo{" +
                "id='" + id + '\'' +
                ", caseId='" + caseId + '\'' +
                ", sampleNo='" + sampleNo + '\'' +
                ", sampleName='" + sampleName + '\'' +
                ", sampleType='" + sampleType + '\'' +
                ", sampleFlag='" + sampleFlag + '\'' +
                ", refPersonId='" + refPersonId + '\'' +
                ", remark='" + remark + '\'' +
                ", createPerson='" + createPerson + '\'' +
                ", createDatetime=" + createDatetime +
                ", updatePereson='" + updatePereson + '\'' +
                ", updateDatetime=" + updateDatetime +
                ", sampleId='" + sampleId + '\'' +
                ", linkId='" + linkId + '\'' +
                ", sampleRemark='" + sampleRemark + '\'' +
                ", instoredFlag='" + instoredFlag + '\'' +
                '}';
    }
}