package com.bazl.dna.database.service.model.bo;

import com.bazl.dna.database.service.model.po.*;

import java.io.Serializable;

/**
 * Created by Liuchang on 2020/6/1.
 */
public class LikeLiHoodCalcModel implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private DnaSampleInfo  dnaSampleInfo;//样本信息实体类

    private DnaYstrGeneInfo ystrSampleAGeneInfo;//样本A ystr分型信息
    private DnaYstrGeneInfo ystrSampleBGeneInfo;//样本B ystr分型信息

    private DnaStrGeneInfo strSampleAGeneInfo;//样本A str分型信息
    private DnaStrGeneInfo strSampleBGeneInfo;//样本B str分型信息

    private DnaMixGeneInfo mixSampleAGeneInfo;//样本A  mix分型信息
    private DnaMixGeneInfo mixSampleBGeneInfo;//样本B  mix分型信息

    private DnaStrGeneInfo strFatherGeneInfo;//父亲基因信息
    private DnaStrGeneInfo strMotherGeneInfo;//目前基因信息
    private DnaStrGeneInfo strChildGeneInfo;//孩子基因信息
    private DnaStrGeneInfo strParentGeneInfo;//双亲基因信息

    private  int  sampleRelation; //样本关系

    private  int  populationFrequencyId;  //种群频率ID

    private AlleleFrequencyInfo alleleFrequencyInfo; //等位基因频率信息表

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public DnaSampleInfo getDnaSampleInfo() {
        return dnaSampleInfo;
    }

    public void setDnaSampleInfo(DnaSampleInfo dnaSampleInfo) {
        this.dnaSampleInfo = dnaSampleInfo;
    }

    public DnaYstrGeneInfo getYstrSampleAGeneInfo() {
        return ystrSampleAGeneInfo;
    }

    public void setYstrSampleAGeneInfo(DnaYstrGeneInfo ystrSampleAGeneInfo) {this.ystrSampleAGeneInfo = ystrSampleAGeneInfo;}

    public DnaYstrGeneInfo getYstrSampleBGeneInfo() {
        return ystrSampleBGeneInfo;
    }

    public void setYstrSampleBGeneInfo(DnaYstrGeneInfo ystrSampleBGeneInfo) {this.ystrSampleBGeneInfo = ystrSampleBGeneInfo;}

    public DnaStrGeneInfo getStrSampleAGeneInfo() {
        return strSampleAGeneInfo;
    }

    public void setStrSampleAGeneInfo(DnaStrGeneInfo strSampleAGeneInfo) {this.strSampleAGeneInfo = strSampleAGeneInfo;}

    public DnaStrGeneInfo getStrSampleBGeneInfo() {
        return strSampleBGeneInfo;
    }

    public void setStrSampleBGeneInfo(DnaStrGeneInfo strSampleBGeneInfo) {this.strSampleBGeneInfo = strSampleBGeneInfo;}

    public DnaMixGeneInfo getMixSampleAGeneInfo() {
        return mixSampleAGeneInfo;
    }

    public void setMixSampleAGeneInfo(DnaMixGeneInfo mixSampleAGeneInfo) {this.mixSampleAGeneInfo = mixSampleAGeneInfo;}

    public DnaMixGeneInfo getMixSampleBGeneInfo() {
        return mixSampleBGeneInfo;
    }

    public void setMixSampleBGeneInfo(DnaMixGeneInfo mixSampleBGeneInfo) {this.mixSampleBGeneInfo = mixSampleBGeneInfo;}

    public AlleleFrequencyInfo getAlleleFrequencyInfo() {
        return alleleFrequencyInfo;
    }

    public void setAlleleFrequencyInfo(AlleleFrequencyInfo alleleFrequencyInfo) {this.alleleFrequencyInfo = alleleFrequencyInfo;}

    public int getPopulationFrequencyId() {
        return populationFrequencyId;
    }

    public void setPopulationFrequencyId(int populationFrequencyId) {this.populationFrequencyId = populationFrequencyId;}

    public DnaStrGeneInfo getStrFatherGeneInfo() {return strFatherGeneInfo;}

    public void setStrFatherGeneInfo(DnaStrGeneInfo strFatherGeneInfo) {this.strFatherGeneInfo = strFatherGeneInfo;}

    public DnaStrGeneInfo getStrMotherGeneInfo() {return strMotherGeneInfo;}

    public void setStrMotherGeneInfo(DnaStrGeneInfo strMotherGeneInfo) {this.strMotherGeneInfo = strMotherGeneInfo;}

    public DnaStrGeneInfo getStrChildGeneInfo() {return strChildGeneInfo;}

    public void setStrChildGeneInfo(DnaStrGeneInfo strChildGeneInfo) {this.strChildGeneInfo = strChildGeneInfo;}

    public DnaStrGeneInfo getStrParentGeneInfo() {return strParentGeneInfo;}

    public void setStrParentGeneInfo(DnaStrGeneInfo strParentGeneInfo) {this.strParentGeneInfo = strParentGeneInfo;}

    public int getSampleRelation() {return sampleRelation;}

    public void setSampleRelation(int sampleRelation) {this.sampleRelation = sampleRelation;}
}
