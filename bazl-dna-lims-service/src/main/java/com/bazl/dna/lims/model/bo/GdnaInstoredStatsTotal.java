package com.bazl.dna.lims.model.bo;

import java.io.Serializable;

/**
 * Created by wangliu on 2019/12/11.
 */
public class GdnaInstoredStatsTotal implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 名称
     */
    private String name;

    /**
     *现场物证入库数总数
     */
    private int wzInstoredStatsTotal;
    /**
     *违法犯罪人员入库数 （案件）总数
     */
    private int caseIllegalCrimeTotal;
    /**
     *违法犯罪人员入库数（建库）总数
     */
    private int jkIllegalCrimeTotal;
    /**
     * 嫌疑人入库数总数
     */
    private int suspectInstoredStatsTotal;
    /**
     * 受害人入库数总数
     */
    private int victimInstoredStatsTotal;
    /**
     * 失踪人员入库数总数
     */
    private int beMissingInstoredStatsTotal;
    /**
     * 无名尸入库数总数
     */
    private int namelessCorpseInstoredStatsTotal;
    /**
     * 嫌疑人亲属入库数总数
     */
    private int suspectRelativesInstoredStatsTotal;

    /**
     * 失踪人员亲属入库数总数
     */
    private int beMissingRelativesInstoredStatsTotal;
    /**
     * 总计
     */
    private int totalCount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWzInstoredStatsTotal() {
        return wzInstoredStatsTotal;
    }

    public void setWzInstoredStatsTotal(int wzInstoredStatsTotal) {
        this.wzInstoredStatsTotal = wzInstoredStatsTotal;
    }

    public int getCaseIllegalCrimeTotal() {
        return caseIllegalCrimeTotal;
    }

    public void setCaseIllegalCrimeTotal(int caseIllegalCrimeTotal) {
        this.caseIllegalCrimeTotal = caseIllegalCrimeTotal;
    }

    public int getJkIllegalCrimeTotal() {
        return jkIllegalCrimeTotal;
    }

    public void setJkIllegalCrimeTotal(int jkIllegalCrimeTotal) {
        this.jkIllegalCrimeTotal = jkIllegalCrimeTotal;
    }

    public int getSuspectInstoredStatsTotal() {
        return suspectInstoredStatsTotal;
    }

    public void setSuspectInstoredStatsTotal(int suspectInstoredStatsTotal) {
        this.suspectInstoredStatsTotal = suspectInstoredStatsTotal;
    }

    public int getVictimInstoredStatsTotal() {
        return victimInstoredStatsTotal;
    }

    public void setVictimInstoredStatsTotal(int victimInstoredStatsTotal) {
        this.victimInstoredStatsTotal = victimInstoredStatsTotal;
    }

    public int getBeMissingInstoredStatsTotal() {
        return beMissingInstoredStatsTotal;
    }

    public void setBeMissingInstoredStatsTotal(int beMissingInstoredStatsTotal) {
        this.beMissingInstoredStatsTotal = beMissingInstoredStatsTotal;
    }

    public int getNamelessCorpseInstoredStatsTotal() {
        return namelessCorpseInstoredStatsTotal;
    }

    public void setNamelessCorpseInstoredStatsTotal(int namelessCorpseInstoredStatsTotal) {
        this.namelessCorpseInstoredStatsTotal = namelessCorpseInstoredStatsTotal;
    }

    public int getSuspectRelativesInstoredStatsTotal() {
        return suspectRelativesInstoredStatsTotal;
    }

    public void setSuspectRelativesInstoredStatsTotal(int suspectRelativesInstoredStatsTotal) {
        this.suspectRelativesInstoredStatsTotal = suspectRelativesInstoredStatsTotal;
    }

    public int getBeMissingRelativesInstoredStatsTotal() {
        return beMissingRelativesInstoredStatsTotal;
    }

    public void setBeMissingRelativesInstoredStatsTotal(int beMissingRelativesInstoredStatsTotal) {
        this.beMissingRelativesInstoredStatsTotal = beMissingRelativesInstoredStatsTotal;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
