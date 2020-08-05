package com.bazl.dna.lims.model.vo;


import com.bazl.dna.lims.model.po.LabSyInfo;
import com.bazl.dna.lims.utils.JsonDatetimeSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wanghaiyang
 * @date 2019/5/13.
 */
public class LabSyInfoVo extends AbstractBaseVo<LabSyInfo> implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LabSyInfoVo() {
        super();
        this.entity = new LabSyInfo();
    }

    public LabSyInfoVo(LabSyInfo entity) {
        super();
        this.entity = entity;
    }

    /**
     * 任务开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date acceptStartDatetime;

    /**
     * 任务终止时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date acceptEndDatetime;

    /**
     * 工作任务
     */
    private String workTask;

    /**
     * 已用时
     */
    private String usedTime;

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
}
