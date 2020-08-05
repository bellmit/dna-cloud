package com.bazl.dna.database.core.response.datamodel;

import java.io.Serializable;

/**
 * 本地库数据情况
 * by lizhihua 2020-4-10
 */
public class ComprehensiveInfo implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int caseCount;

    private int matterCount;

    private int personCount;

    private int strCount;

    private int ystrCount;

    private int blendCount;

    public int getCaseCount() {
        return caseCount;
    }

    public void setCaseCount(int caseCount) {
        this.caseCount = caseCount;
    }

    public int getMatterCount() {
        return matterCount;
    }

    public void setMatterCount(int matterCount) {
        this.matterCount = matterCount;
    }

    public int getPersonCount() {
        return personCount;
    }

    public void setPersonCount(int personCount) {
        this.personCount = personCount;
    }

    public int getStrCount() {
        return strCount;
    }

    public void setStrCount(int strCount) {
        this.strCount = strCount;
    }

    public int getYstrCount() {
        return ystrCount;
    }

    public void setYstrCount(int ystrCount) {
        this.ystrCount = ystrCount;
    }

    public int getBlendCount() {
        return blendCount;
    }

    public void setBlendCount(int blendCount) {
        this.blendCount = blendCount;
    }
}
