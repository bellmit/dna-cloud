package com.bazl.dna.lims.model.vo;

import com.bazl.dna.lims.model.po.LabPcrInfo;
import com.bazl.dna.lims.utils.JsonDatetimeSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class LabPcrInfoVo extends AbstractBaseVo<LabPcrInfo> implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LabPcrInfoVo() {
        super();
        this.entity = new LabPcrInfo();
    }

    public LabPcrInfoVo(LabPcrInfo entity) {
        super();
        this.entity=entity;
    }
    /**
     * 任务开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date acceptStartDatetime;

    /**
     * 工作任务
     */
    private String workTask;

    /**
     * 已用时
     */
    private String usedTime;

    public String getWorkTask() {
        return workTask;
    }

    public void setWorkTask(String workTask) {
        this.workTask = workTask;
    }

    public String getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(String usedTime) {
        this.usedTime = usedTime;
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

    /**
     * 任务终止时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date acceptEndDatetime;
}
