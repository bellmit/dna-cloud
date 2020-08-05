package com.bazl.dna.lims.model.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class LaboratoryInfo implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

    private String labName;

    private BigDecimal labTotalCase;

    private String labTemperature;

    private String labHumidity;

    private String labSetupTime;

    private BigDecimal labPersonnel;

    private String labIntroduction;

    private String orgId;

    private String ipSegment;

    private String dnaLabName;
    
    /**
     * 实验室压差
     */
    private String labPressure;
    
    /**
     * 实验室通风
     */
    private String labVentilation;
    
    /**
     * 监控类型
     */
    private Integer monitorType;
    
    /**
     * 实验室图片
     */
    private String labImage;

	private List<String> nocaseNumStr=new ArrayList<>();
    private List<String> caseNumberStr=new ArrayList<>();

    /* 案件总数 */
    private int caseSum;

    /* 今年案件数 */
    private int caseYearCount;

    /* 往年案件数 */
    private int caseFormerYearCount;

    /* 同比率 */
    private int percentage;
    /* 同比率标识 */
    private String countFlag;

    public String getCountFlag() {
        return countFlag;
    }

    public void setCountFlag(String countFlag) {
        this.countFlag = countFlag;
    }

    public int getCaseSum() {
        return caseSum;
    }

    public void setCaseSum(int caseSum) {
        this.caseSum = caseSum;
    }

    public int getCaseYearCount() {
        return caseYearCount;
    }

    public void setCaseYearCount(int caseYearCount) {
        this.caseYearCount = caseYearCount;
    }

    public int getCaseFormerYearCount() {
        return caseFormerYearCount;
    }

    public void setCaseFormerYearCount(int caseFormerYearCount) {
        this.caseFormerYearCount = caseFormerYearCount;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName == null ? null : labName.trim();
    }

    public BigDecimal getLabTotalCase() {
        return labTotalCase;
    }

    public void setLabTotalCase(BigDecimal labTotalCase) {
        this.labTotalCase = labTotalCase;
    }

    public String getLabTemperature() {
        return labTemperature;
    }

    public void setLabTemperature(String labTemperature) {
        this.labTemperature = labTemperature == null ? null : labTemperature.trim();
    }

    public String getLabHumidity() {
        return labHumidity;
    }

    public void setLabHumidity(String labHumidity) {
        this.labHumidity = labHumidity == null ? null : labHumidity.trim();
    }

    public String getLabSetupTime() {
        return labSetupTime;
    }

    public void setLabSetupTime(String labSetupTime) {
        this.labSetupTime = labSetupTime == null ? null : labSetupTime.trim();
    }

    public BigDecimal getLabPersonnel() {
        return labPersonnel;
    }

    public void setLabPersonnel(BigDecimal labPersonnel) {
        this.labPersonnel = labPersonnel;
    }

    public String getLabIntroduction() {
        return labIntroduction;
    }

    public void setLabIntroduction(String labIntroduction) {
        this.labIntroduction = labIntroduction == null ? null : labIntroduction.trim();
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getIpSegment() {
        return ipSegment;
    }

    public void setIpSegment(String ipSegment) {
        this.ipSegment = ipSegment == null ? null : ipSegment.trim();
    }

    public List<String> getNocaseNumStr() {
        return nocaseNumStr;
    }

    public void setNocaseNumStr(List<String> nocaseNumStr) {
        this.nocaseNumStr = nocaseNumStr;
    }

    public List<String> getCaseNumberStr() {
        return caseNumberStr;
    }

    public void setCaseNumberStr(List<String> caseNumberStr) {
        this.caseNumberStr = caseNumberStr;
    }

    public String getDnaLabName() {
        return dnaLabName;
    }

    public void setDnaLabName(String dnaLabName) {
        this.dnaLabName = dnaLabName;
    }
    /**
	 * @return the labPressure
	 */
	public String getLabPressure() {
		return labPressure;
	}

	/**
	 * @param labPressure the labPressure to set
	 */
	public void setLabPressure(String labPressure) {
		this.labPressure = labPressure;
	}

	/**
	 * @return the labVentilation
	 */
	public String getLabVentilation() {
		return labVentilation;
	}

	/**
	 * @param labVentilation the labVentilation to set
	 */
	public void setLabVentilation(String labVentilation) {
		this.labVentilation = labVentilation;
	}

	/**
	 * @return the monitorType
	 */
	public Integer getMonitorType() {
		return monitorType;
	}

	/**
	 * @param monitorType the monitorType to set
	 */
	public void setMonitorType(Integer monitorType) {
		this.monitorType = monitorType;
	}
	
	public String getLabImage() {
		return labImage;
	}

	public void setLabImage(String labImage) {
		this.labImage = labImage;
	}
}