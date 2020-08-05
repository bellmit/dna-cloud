package com.bazl.dna.lims.model.bo;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.bazl.dna.lims.utils.JsonDatetimeSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 数据库工作量统计
 * Created by wangliu on 2019/12/18.
 */
public class WorkloadStatsModel implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 单位
     */
    private String orgCode;

    /**
     * 实验员
     */
    private String tester;

    /**
     * 实验完成时间(起)
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date testFulfilDateTimeStart;
    /**
     * 实验完成时间（止）
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date testFulfilDateTimeEnd;

    /**
     * 审核状态
     */
    private String auditState;
    /**
     * 入库状态
     */
    private String isStore;

    /**
     * 完成板数（以审核）
     */
    private int auditStateCnt;
    /**
     * 完成板数（未审核）
     */
    private int notAuditStateCnt;
    /**
     * 首次试验样本总数
     */
    private int oneTestSampleCnt;
    /**
     * 首次试验样本入库成功样本总数
     */
    private int oneTestSampleRkFulfilCnt;
    /**
     * 首次试验样本入库失败样本总数
     */
    private int oneTestSampleRkFailCnt;
    /**
     * 首次试验成功率
     */
    private String oneTestFulfilRate;
    /**
     * 重做试验样本总数
     */
    private int twoTestSampleCnt;
    /**
     * 重做试验样本入库成功样本总数
     */
    private int twoTestSampleRkFulfilCnt;
    /**
     * 重做试验成功率
     */
    private String twoTestFulfilRate;
    /**
     * 需要重做试验样本数
     */
    private int needTestSampleCnt;

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getTester() {
        return tester;
    }

    public void setTester(String tester) {
        this.tester = tester;
    }

    public Date getTestFulfilDateTimeStart() {
        return testFulfilDateTimeStart;
    }

    public void setTestFulfilDateTimeStart(Date testFulfilDateTimeStart) {
        this.testFulfilDateTimeStart = testFulfilDateTimeStart;
    }

    public Date getTestFulfilDateTimeEnd() {
        return testFulfilDateTimeEnd;
    }

    public void setTestFulfilDateTimeEnd(Date testFulfilDateTimeEnd) {
        this.testFulfilDateTimeEnd = testFulfilDateTimeEnd;
    }

    public String getAuditState() {
        return auditState;
    }

    public void setAuditState(String auditState) {
        this.auditState = auditState;
    }

    public String getIsStore() {
        return isStore;
    }

    public void setIsStore(String isStore) {
        this.isStore = isStore;
    }

    public int getAuditStateCnt() {
        return auditStateCnt;
    }

    public void setAuditStateCnt(int auditStateCnt) {
        this.auditStateCnt = auditStateCnt;
    }

    public int getNotAuditStateCnt() {
        return notAuditStateCnt;
    }

    public void setNotAuditStateCnt(int notAuditStateCnt) {
        this.notAuditStateCnt = notAuditStateCnt;
    }

    public int getOneTestSampleCnt() {
        return oneTestSampleCnt;
    }

    public void setOneTestSampleCnt(int oneTestSampleCnt) {
        this.oneTestSampleCnt = oneTestSampleCnt;
    }

    public int getOneTestSampleRkFulfilCnt() {
        return oneTestSampleRkFulfilCnt;
    }

    public void setOneTestSampleRkFulfilCnt(int oneTestSampleRkFulfilCnt) {
        this.oneTestSampleRkFulfilCnt = oneTestSampleRkFulfilCnt;
    }

    public int getOneTestSampleRkFailCnt() {
        return oneTestSampleRkFailCnt;
    }

    public void setOneTestSampleRkFailCnt(int oneTestSampleRkFailCnt) {
        this.oneTestSampleRkFailCnt = oneTestSampleRkFailCnt;
    }

    public String getOneTestFulfilRate() {
        return oneTestFulfilRate;
    }

    public void setOneTestFulfilRate(String oneTestFulfilRate) {
        this.oneTestFulfilRate = oneTestFulfilRate;
    }

    public int getTwoTestSampleCnt() {
        return twoTestSampleCnt;
    }

    public void setTwoTestSampleCnt(int twoTestSampleCnt) {
        this.twoTestSampleCnt = twoTestSampleCnt;
    }

    public int getTwoTestSampleRkFulfilCnt() {
        return twoTestSampleRkFulfilCnt;
    }

    public void setTwoTestSampleRkFulfilCnt(int twoTestSampleRkFulfilCnt) {
        this.twoTestSampleRkFulfilCnt = twoTestSampleRkFulfilCnt;
    }

    public String getTwoTestFulfilRate() {
        return twoTestFulfilRate;
    }

    public void setTwoTestFulfilRate(String twoTestFulfilRate) {
        this.twoTestFulfilRate = twoTestFulfilRate;
    }

    public int getNeedTestSampleCnt() {
        return needTestSampleCnt;
    }

    public void setNeedTestSampleCnt(int needTestSampleCnt) {
        this.needTestSampleCnt = needTestSampleCnt;
    }
}
