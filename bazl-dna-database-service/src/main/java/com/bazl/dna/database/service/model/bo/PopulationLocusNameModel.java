package com.bazl.dna.database.service.model.bo;

import java.io.Serializable;

/**
 * Created by Liuchang on 2020/6/4.
 */
public class PopulationLocusNameModel implements Serializable{

    /**
     * 序列化
     */
    private static final long serialVersionUID = 1L;

    private  String locusName;
    private  int  populationId;

    public String getLocusName() {
        return locusName;
    }

    public void setLocusName(String locusName) {
        this.locusName = locusName;
    }

    public int getPopulationId() {
        return populationId;
    }

    public void setPopulationId(int populationId) {
        this.populationId = populationId;
    }
}
