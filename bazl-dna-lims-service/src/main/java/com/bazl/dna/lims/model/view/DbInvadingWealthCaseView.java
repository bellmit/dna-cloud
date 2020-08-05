package com.bazl.dna.lims.model.view;

import java.io.Serializable;
import java.util.List;

import com.bazl.dna.lims.model.vo.AbstractBaseVo;

/**
 * 侵财类案件统计（侵财统计视图公用）
 * Created by wangliu on 2019/11/7.
 */
public class DbInvadingWealthCaseView extends AbstractBaseVo<DbInvadingWealthCaseView> implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 侵财类案件性质
     */
    private String caseType;
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
     * 案件个数
     */
    private String caseCount;

    private int    number;

    private List<String> caseTypeList;

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

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

    public String getCaseCount() {
        return caseCount;
    }

    public void setCaseCount(String caseCount) {
        this.caseCount = caseCount;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<String> getCaseTypeList() {
        return caseTypeList;
    }

    public void setCaseTypeList(List<String> caseTypeList) {
        this.caseTypeList = caseTypeList;
    }
}
