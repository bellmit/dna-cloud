package com.bazl.dna.lims.model.vo;

import java.io.Serializable;
import java.util.List;

import com.bazl.dna.lims.model.po.CodisGeneInfo;
import com.bazl.dna.lims.model.po.LimsSampleGene;

/**
 * @author wanghaiyang
 * @date 2019/2/27.
 */
public class LimsSampleGeneVo extends AbstractBaseVo<LimsSampleGene> implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public LimsSampleGeneVo() {
        super();
        this.entity = new LimsSampleGene();
    }

    public LimsSampleGeneVo(LimsSampleGene entity) {
        super();
        this.entity = entity;
    }
    
    private String geneId;

    public String getGeneId() {
		return geneId;
	}

	public void setGeneId(String geneId) {
		this.geneId = geneId;
	}

	/** 样本编号 */
    private String sampleNo;

    /** 样本类型名称 */
    private String sampleTypeName;

    /** 样本名称 */
    private String sampleName;

    /** 颜色标记 */
    private String colorMark;

    /**关联检材编号*/
    private String relationSampleNo;
    /**是否关联*/
    private String isRelation;
    /**检材来源*/
    private String sampleSource;

    private String delegateOrgName;
    /**
     * 送检样本数
     */
    private int dategateSampleCnt;
    /**
     * 检出基因型数
     */
    private int detectionGeneCnt;
    /**
     * 检出率
     */
    private String detectionRate;
    
    /**
     * lims库中的基因型
     */
    private String geneMaeker;
    
    /**
     * 基因型信息list
     */
    private List<CodisGeneInfo> geneInfoList;
    /**
     * Alims中需要的基因型格式
     */
    private String alimsGeneFormat;
    /**
     * lims中需要的基因型格式
     */
    private String limsGeneFormat;

    public String getGeneMaeker() {
		return geneMaeker;
	}

	public void setGeneMaeker(String geneMaeker) {
		this.geneMaeker = geneMaeker;
	}

	public List<CodisGeneInfo> getGeneInfoList() {
		return geneInfoList;
	}

	public void setGeneInfoList(List<CodisGeneInfo> geneInfoList) {
		this.geneInfoList = geneInfoList;
	}

	public String getAlimsGeneFormat() {
		return alimsGeneFormat;
	}

	public void setAlimsGeneFormat(String alimsGeneFormat) {
		this.alimsGeneFormat = alimsGeneFormat;
	}

	public String getLimsGeneFormat() {
		return limsGeneFormat;
	}

	public void setLimsGeneFormat(String limsGeneFormat) {
		this.limsGeneFormat = limsGeneFormat;
	}

	public String getDelegateOrgName() {
		return delegateOrgName;
	}

	public void setDelegateOrgName(String delegateOrgName) {
		this.delegateOrgName = delegateOrgName;
	}

	public int getDategateSampleCnt() {
		return dategateSampleCnt;
	}

	public void setDategateSampleCnt(int dategateSampleCnt) {
		this.dategateSampleCnt = dategateSampleCnt;
	}

	public int getDetectionGeneCnt() {
		return detectionGeneCnt;
	}

	public void setDetectionGeneCnt(int detectionGeneCnt) {
		this.detectionGeneCnt = detectionGeneCnt;
	}

	public String getDetectionRate() {
		return detectionRate;
	}

	public void setDetectionRate(String detectionRate) {
		this.detectionRate = detectionRate;
	}

	public String getRelationSampleNo() {
        return relationSampleNo;
    }

    public void setRelationSampleNo(String relationSampleNo) {
        this.relationSampleNo = relationSampleNo;
    }

    public String getIsRelation() {
        return isRelation;
    }

    public void setIsRelation(String isRelation) {
        this.isRelation = isRelation;
    }

    public String getSampleSource() {
        return sampleSource;
    }

    public void setSampleSource(String sampleSource) {
        this.sampleSource = sampleSource;
    }

    public String getSampleNo() {
        return sampleNo;
    }

    public void setSampleNo(String sampleNo) {
        this.sampleNo = sampleNo;
    }

    public String getSampleTypeName() {
        return sampleTypeName;
    }

    public void setSampleTypeName(String sampleTypeName) {
        this.sampleTypeName = sampleTypeName;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public String getColorMark() {
        return colorMark;
    }

    public void setColorMark(String colorMark) {
        this.colorMark = colorMark;
    }
}
