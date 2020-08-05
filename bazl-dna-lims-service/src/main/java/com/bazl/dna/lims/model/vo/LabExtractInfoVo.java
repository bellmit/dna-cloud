package com.bazl.dna.lims.model.vo;

import com.bazl.dna.lims.model.po.LabExtractInfo;
import com.bazl.dna.lims.utils.JsonDatetimeSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class LabExtractInfoVo extends AbstractBaseVo<LabExtractInfo> implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LabExtractInfoVo() {
        super();
        this.entity = new LabExtractInfo();
    }

    public LabExtractInfoVo(LabExtractInfo entity) {
        super();
        this.entity = entity;
    }
    /**
     * 任务开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date acceptStartDatetime;

    /**
     * 任务终止时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date acceptEndDatetime;

    private String sampleNo;

    /**
     * 样本类型
     */
    private String sampleType;

    /**
     * 已用时
     * @return
     */
    private int UserTime;

    /**
     * 工作任务
     * @return
     */
    private String workTask;

    public String getSampleNo() {
        return sampleNo;
    }

    public void setSampleNo(String sampleNo) {
        this.sampleNo = sampleNo;
    }

    public Date getAcceptStartDatetime() {
        return acceptStartDatetime;
    }

    public void setAcceptStartDatetime(Date acceptStartDatetime) {
        this.acceptStartDatetime = acceptStartDatetime;
    }

    public Date getAcceptEndDatetime() {
        return acceptEndDatetime;
    }

    public void setAcceptEndDatetime(Date acceptEndDatetime) {
        this.acceptEndDatetime = acceptEndDatetime;
    }

    public String getSampleType() { return sampleType; }

    public void setSampleType(String sampleType) { this.sampleType = sampleType; }

    public int getUserTime() { return UserTime; }

    public void setUserTime(int userTime) { UserTime = userTime; }


    public String getWorkTask() { return workTask; }

    public void setWorkTask(String workTask) { this.workTask = workTask; }
}
