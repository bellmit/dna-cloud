package com.bazl.dna.mix.controller.codis;

import java.io.Serializable;
import java.util.List;

/**
 *@author wanghaiyang
 * @date 2019/5/14.
 */
public class CodisContentModel implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String mixedSampleId;

    private String sampleNo;

    private String importFlag;

    private String failedReason;

    private String geneInfo;

    private String geneid;

    private String genenum;

    private List<Object> geneInfoList;

    private List<Object> geneInfoBeanList;

    private String geneImagePath;

    private String mixId;

    private Integer geneCount;

    public String getMixedSampleId() {
        return mixedSampleId;
    }

    public void setMixedSampleId(String mixedSampleId) {
        this.mixedSampleId = mixedSampleId;
    }

    public List<Object> getGeneInfoBeanList() {
        return geneInfoBeanList;
    }

    public void setGeneInfoBeanList(List<Object> geneInfoBeanList) {
        this.geneInfoBeanList = geneInfoBeanList;
    }

    public List<Object> getGeneInfoList() {
        return geneInfoList;
    }

    public void setGeneInfoList(List<Object> geneInfoList) {
        this.geneInfoList = geneInfoList;
    }

    public String getGenenum() {
        return genenum;
    }

    public void setGenenum(String genenum) {
        this.genenum = genenum;
    }

    public String getGeneid() {
        return geneid;
    }

    public void setGeneid(String geneid) {
        this.geneid = geneid;
    }

    public String getSampleNo() {
        return sampleNo;
    }

    public void setSampleNo(String sampleNo) {
        this.sampleNo = sampleNo;
    }

    public String getImportFlag() {
        return importFlag;
    }

    public void setImportFlag(String importFlag) {
        this.importFlag = importFlag;
    }

    public String getFailedReason() {
        return failedReason;
    }

    public void setFailedReason(String failedReason) {
        this.failedReason = failedReason;
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

    public String getMixId() {
        return mixId;
    }

    public void setMixId(String mixId) {
        this.mixId = mixId;
    }

    public Integer getGeneCount() {
        return geneCount;
    }

    public void setGeneCount(Integer geneCount) {
        this.geneCount = geneCount;
    }
}
