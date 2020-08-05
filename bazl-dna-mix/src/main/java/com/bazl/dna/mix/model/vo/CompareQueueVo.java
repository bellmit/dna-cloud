package com.bazl.dna.mix.model.vo;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.bazl.dna.mix.model.po.CompareQueue;
import com.bazl.dna.util.JsonDatetimeSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by Administrator on 2019/11/19.
 */
public class CompareQueueVo  extends AbstractBaseVO<CompareQueue> implements Serializable {

	private static final long serialVersionUID = 1L;
	
    /**
     * 样本编号
     */
    private String sampleNo;

    /**
     * 样本名称
     */
    private String sampleName;

    //开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date startDatetime;

    //结束时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date endDatetime;

    private int  spliteCount;

    //当前页数
    private int page;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public Date getStartDatetime() {
        return startDatetime;
    }

    public void setStartDatetime(Date startDatetime) {
        this.startDatetime = startDatetime;
    }

    public Date getEndDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(Date endDatetime) {
        this.endDatetime = endDatetime;
    }

    public int getSpliteCount() {
        return spliteCount;
    }

    public void setSpliteCount(int spliteCount) {
        this.spliteCount = spliteCount;
    }

    public String getSampleNo() {
        return sampleNo;
    }

    public void setSampleNo(String sampleNo) {
        this.sampleNo = sampleNo;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public CompareQueueVo() {
        super();
        this.entity = new CompareQueue();
    }

    public CompareQueueVo(CompareQueue entity) {
        super();
        this.entity = entity;
    }
}
