package com.bazl.dna.lims.model.view;

import java.io.Serializable;

/**
 * DNA综合作用率统计公用
 * Created by wangliu on 2019/12/13.
 */
public class DbIvDnaActionRateView implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 单位code
     */
    private String delegateOrgCode;
    /**
     * 单位名称
     */
    private String delegateOrgName;
    /**
     * 送检日期
     */
    private String delagateDatetime;

    /**
     * 送检日期（起）
     */
    private String delagateDatetimeStart;
    /**
     * 送检日期（止）
     */
    private String delagateDatetimeEnd;
    /**
     * 送检案件数
     */
    private String sjCaseCount;
    /**
     * 出鉴定案件数
     */
    private String issueCaseCount;
    /**
     * 有物证入库但未出鉴定书案件数
     */
    private String rkNotIssueCaseCount;
    /**
     * 未出鉴定案件数
     */
    private String notIssueCaseCount;
    /**
     * 有物证入库案件数
     */
    private String rkCaseCount;
    /**
     * 计算结果
     */
    private String computedResult;

    public String getDelegateOrgCode() {
        return delegateOrgCode;
    }

    public void setDelegateOrgCode(String delegateOrgCode) {
        this.delegateOrgCode = delegateOrgCode;
    }

    public String getDelegateOrgName() {
        return delegateOrgName;
    }

    public void setDelegateOrgName(String delegateOrgName) {
        this.delegateOrgName = delegateOrgName;
    }

    public String getDelagateDatetime() {
        return delagateDatetime;
    }

    public void setDelagateDatetime(String delagateDatetime) {
        this.delagateDatetime = delagateDatetime;
    }

    public String getDelagateDatetimeStart() {
        return delagateDatetimeStart;
    }

    public void setDelagateDatetimeStart(String delagateDatetimeStart) {
        this.delagateDatetimeStart = delagateDatetimeStart;
    }

    public String getDelagateDatetimeEnd() {
        return delagateDatetimeEnd;
    }

    public void setDelagateDatetimeEnd(String delagateDatetimeEnd) {
        this.delagateDatetimeEnd = delagateDatetimeEnd;
    }

    public String getSjCaseCount() {
        return sjCaseCount;
    }

    public void setSjCaseCount(String sjCaseCount) {
        this.sjCaseCount = sjCaseCount;
    }

    public String getIssueCaseCount() {
        return issueCaseCount;
    }

    public void setIssueCaseCount(String issueCaseCount) {
        this.issueCaseCount = issueCaseCount;
    }

    public String getRkNotIssueCaseCount() {
        return rkNotIssueCaseCount;
    }

    public void setRkNotIssueCaseCount(String rkNotIssueCaseCount) {
        this.rkNotIssueCaseCount = rkNotIssueCaseCount;
    }

    public String getNotIssueCaseCount() {
        return notIssueCaseCount;
    }

    public void setNotIssueCaseCount(String notIssueCaseCount) {
        this.notIssueCaseCount = notIssueCaseCount;
    }

    public String getRkCaseCount() {
        return rkCaseCount;
    }

    public void setRkCaseCount(String rkCaseCount) {
        this.rkCaseCount = rkCaseCount;
    }

    public String getComputedResult() {
        return computedResult;
    }

    public void setComputedResult(String computedResult) {
        this.computedResult = computedResult;
    }
}
