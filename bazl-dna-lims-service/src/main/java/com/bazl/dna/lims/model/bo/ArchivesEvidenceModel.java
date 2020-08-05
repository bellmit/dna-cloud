package com.bazl.dna.lims.model.bo;

import java.io.Serializable;

/**
 * 档案与物证数据总计
 * Created by liuchang on 2020-07-30
 */

public class ArchivesEvidenceModel implements Serializable {
    /**
     * 序列化
     */
    private static final long serialVersionUID = 1L;

    private int archivesCount; //档案总数

    private int extantArchivesCount; //现存档案总数

    private int evidenceCount; //物证总数

    private int extantEvidenceCount;//现存物证总数

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getArchivesCount() {
        return archivesCount;
    }

    public void setArchivesCount(int archivesCount) {
        this.archivesCount = archivesCount;
    }

    public int getExtantArchivesCount() {
        return extantArchivesCount;
    }

    public void setExtantArchivesCount(int extantArchivesCount) {
        this.extantArchivesCount = extantArchivesCount;
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
}
