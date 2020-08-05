package com.bazl.dna.lims.model.view;

import java.io.Serializable;

/**
 * Created by wangliu on 2020/4/3.
 */
public class DbStatsCaseInfoView implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 单位code
     */
    private String delegateOrg;
    /**
     * 单位名称
     */
    private String delegateOrgName;
    /**
     * 受理时间
     */
    private String acceptDatetime;

    /**
     * 受理时间（起）
     */
    private String acceptDatetimeStart;
    /**
     * 受理时间（止）
     */
    private String acceptDatetimeEnd;

    /**
     * 案件总数
     */
    private int caseCnt;
    /**
     * 入库案件总数
     */
    private int rkCaseCnt;
    /**
     * 比中嫌疑人案件总数
     */
    private int bzXyrCaseCnt;
    /**
     * 比中物证案件总数
     */
    private int bzWzCaseCnt;
    /**
     * 其他案件总数
     */
    private int otherCaseCnt;

    public String getDelegateOrg() {
        return delegateOrg;
    }

    public void setDelegateOrg(String delegateOrg) {
        this.delegateOrg = delegateOrg;
    }

    public String getDelegateOrgName() {
        return delegateOrgName;
    }

    public void setDelegateOrgName(String delegateOrgName) {
        this.delegateOrgName = delegateOrgName;
    }

    public String getAcceptDatetime() {
        return acceptDatetime;
    }

    public void setAcceptDatetime(String acceptDatetime) {
        this.acceptDatetime = acceptDatetime;
    }

    public String getAcceptDatetimeStart() {
        return acceptDatetimeStart;
    }

    public void setAcceptDatetimeStart(String acceptDatetimeStart) {
        this.acceptDatetimeStart = acceptDatetimeStart;
    }

    public String getAcceptDatetimeEnd() {
        return acceptDatetimeEnd;
    }

    public void setAcceptDatetimeEnd(String acceptDatetimeEnd) {
        this.acceptDatetimeEnd = acceptDatetimeEnd;
    }

    public int getCaseCnt() {
        return caseCnt;
    }

    public void setCaseCnt(int caseCnt) {
        this.caseCnt = caseCnt;
    }

    public int getRkCaseCnt() {
        return rkCaseCnt;
    }

    public void setRkCaseCnt(int rkCaseCnt) {
        this.rkCaseCnt = rkCaseCnt;
    }

    public int getBzXyrCaseCnt() {
        return bzXyrCaseCnt;
    }

    public void setBzXyrCaseCnt(int bzXyrCaseCnt) {
        this.bzXyrCaseCnt = bzXyrCaseCnt;
    }

    public int getBzWzCaseCnt() {
        return bzWzCaseCnt;
    }

    public void setBzWzCaseCnt(int bzWzCaseCnt) {
        this.bzWzCaseCnt = bzWzCaseCnt;
    }

    public int getOtherCaseCnt() {
        return otherCaseCnt;
    }

    public void setOtherCaseCnt(int otherCaseCnt) {
        this.otherCaseCnt = otherCaseCnt;
    }
}
