package com.bazl.dna.database.core.helper.codis;

import java.io.Serializable;
import java.util.List;

/**
 *@author renyunpeng
 * @date 2020/4/16.
 */
public class CodisQcModel implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String sampleNo;

    private List<GeneInfoDetail> geneInfo;

    private Integer locusCount;

    private List<Object> geneInfoList;

    private String uuidString;

    private String mixFlag;

    public String getUuidString() {
        return uuidString;
    }

    public void setUuidString(String uuidString) {
        this.uuidString = uuidString;
    }

    public String getMixFlag() {
        return mixFlag;
    }

    public void setMixFlag(String mixFlag) {
        this.mixFlag = mixFlag;
    }

    public List<Object> getGeneInfoList() {
        return geneInfoList;
    }

    public void setGeneInfoList(List<Object> geneInfoList) {
        this.geneInfoList = geneInfoList;
    }

    public String getSampleNo() {
        return sampleNo;
    }

    public void setSampleNo(String sampleNo) {
        this.sampleNo = sampleNo;
    }

    public List<GeneInfoDetail> getGeneInfo() {
        return geneInfo;
    }

    public void setGeneInfo(List<GeneInfoDetail> geneInfo) {
        this.geneInfo = geneInfo;
    }

    public Integer getLocusCount() {
        return locusCount;
    }

    public void setLocusCount(Integer locusCount) {
        this.locusCount = locusCount;
    }

    public static class GeneInfoDetail {
        private String locusName;
        private String alleleOne;
        private String alleleTwo;


        public String getLocusName() {
            return locusName;
        }

        public void setLocusName(String locusName) {
            this.locusName = locusName;
        }

        public String getAlleleOne() {
            return alleleOne;
        }

        public void setAlleleOne(String alleleOne) {
            this.alleleOne = alleleOne;
        }

        public String getAlleleTwo() {
            return alleleTwo;
        }

        public void setAlleleTwo(String alleleTwo) {
            this.alleleTwo = alleleTwo;
        }
    }
}
