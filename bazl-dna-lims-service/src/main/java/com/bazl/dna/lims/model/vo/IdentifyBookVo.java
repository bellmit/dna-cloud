package com.bazl.dna.lims.model.vo;

import java.io.Serializable;

/**
 * @author wanghaiyang
 * @date 2020/7/30 17:34
 */
public class IdentifyBookVo implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String orgCode;
    private String orgName;

    /** 已出鉴定书数量 */
    private Integer outCount;

    /** 已签发鉴定书数量 */
    private Integer issuedCount;

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Integer getOutCount() {
        return outCount;
    }

    public void setOutCount(Integer outCount) {
        this.outCount = outCount;
    }

    public Integer getIssuedCount() {
        return issuedCount;
    }

    public void setIssuedCount(Integer issuedCount) {
        this.issuedCount = issuedCount;
    }
}
