package com.bazl.dna.lims.model.po;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.bazl.dna.lims.utils.JsonDatetimeSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 　档案与物证信息表
 *   liuchang
 *   Date:2020-07-30
 */
public class DnaEvidenceCount implements Serializable {

    /*
     * 序列化
     */
    private static final long serialVersionUID = 1L;

     private String id;

     private int archiveCount; //档案总数

     private int extantArchiveCount;//现存档案总数

     private int evidenceCount;//物证总数

     private int extantEvidenceCount;//现存物证总数

     private String centerOrgCode; //单位机构编码

     @DateTimeFormat(pattern = "yyyy-MM-dd")
     @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
     @JsonSerialize(using = JsonDatetimeSerializer.class)
     private Date updateDatetime;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getArchiveCount() {
        return archiveCount;
    }

    public void setArchiveCount(int archiveCount) {
        this.archiveCount = archiveCount;
    }

    public int getExtantArchiveCount() {
        return extantArchiveCount;
    }

    public void setExtantArchiveCount(int extantArchiveCount) {
        this.extantArchiveCount = extantArchiveCount;
    }

    public int getEvidenceCount() {
        return evidenceCount;
    }

    public void setEvidenceCount(int evidenceCount) {
        this.evidenceCount = evidenceCount;
    }

    public int getExtantEvidenceCount() {
        return extantEvidenceCount;
    }

    public void setExtantEvidenceCount(int extantEvidenceCount) {
        this.extantEvidenceCount = extantEvidenceCount;
    }

    public String getCenterOrgCode() {
        return centerOrgCode;
    }

    public void setCenterOrgCode(String centerOrgCode) {
        this.centerOrgCode = centerOrgCode;
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }
}
