package com.bazl.dna.lims.model.bo;

import java.io.Serializable;

/**
 * 建库数据总计
 * Created by liuchang on 2020-07-30
 */

public class BuildDataModel implements Serializable {
    /**
     * 序列化
     */
    private static final long serialVersionUID = 1L;

    private String allCollectPersonCnt; //被采集人员总数

    private String accpetCollectPersonCnt;//送检总数

    private String sameCardPersonCnt;//相同身份证号码的被采集人数

    private String inspectionRate;//送检率

    private String sameRate; //重复率

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getAllCollectPersonCnt() {
        return allCollectPersonCnt;
    }

    public void setAllCollectPersonCnt(String allCollectPersonCnt) {
        this.allCollectPersonCnt = allCollectPersonCnt;
    }

    public String getAccpetCollectPersonCnt() {
        return accpetCollectPersonCnt;
    }

    public void setAccpetCollectPersonCnt(String accpetCollectPersonCnt) {
        this.accpetCollectPersonCnt = accpetCollectPersonCnt;
    }

    public String getInspectionRate() {
        return inspectionRate;
    }

    public void setInspectionRate(String inspectionRate) {
        this.inspectionRate = inspectionRate;
    }

    public String getSameRate() {
        return sameRate;
    }

    public void setSameRate(String sameRate) {
        this.sameRate = sameRate;
    }

    public String getSameCardPersonCnt() {
        return sameCardPersonCnt;
    }

    public void setSameCardPersonCnt(String sameCardPersonCnt) {
        this.sameCardPersonCnt = sameCardPersonCnt;
    }
}
