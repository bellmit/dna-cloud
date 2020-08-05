package com.bazl.dna.database.service.model.bo;

import java.io.Serializable;

/**
 * Created by Liuchang on 2020/6/7.
 */
public class AllelFrequencyModel implements Serializable {

    /**
     * 序列化
     */
    private static final long serialVersionUID = 1L;

    private  int populationFrequencyId;//种群ID
    private  String locusName;//试剂盒名称

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getPopulationFrequencyId() {
        return populationFrequencyId;
    }

    public void setPopulationFrequencyId(int populationFrequencyId) {
        this.populationFrequencyId = populationFrequencyId;
    }

    public String getLocusName() {
        return locusName;
    }

    public void setLocusName(String locusName) {
        this.locusName = locusName;
    }
}
