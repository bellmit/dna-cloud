package com.bazl.dna.database.service.model.bo;

import com.bazl.dna.database.service.model.po.DnaLocusInfo;
import com.bazl.dna.database.service.model.po.PopulationFrequencyInfo;
import com.bazl.dna.database.service.model.vo.AlleleFrequencyInfoVo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Liuchang on 2020/6/7.
 */
public class FrequencyInfoModel implements Serializable {
    /**
     * 序列化
     */
    private static final long serialVersionUID = 1L;

    private PopulationFrequencyInfo populationFrequencyInfo;//种群信息

    private DnaLocusInfo  dnaLocusInfo;//基因座信息

    private List<AlleleFrequencyInfoVo> alleleFrequencyInfoList;//基因频率

    public static long getSerialVersionUID() {return serialVersionUID;}

    public PopulationFrequencyInfo getPopulationFrequencyInfo() {return populationFrequencyInfo;}

    public void setPopulationFrequencyInfo(PopulationFrequencyInfo populationFrequencyInfo) {this.populationFrequencyInfo = populationFrequencyInfo;}

    public DnaLocusInfo getDnaLocusInfo() {return dnaLocusInfo;}

    public void setDnaLocusInfo(DnaLocusInfo dnaLocusInfo) {this.dnaLocusInfo = dnaLocusInfo;}

    public List<AlleleFrequencyInfoVo> getAlleleFrequencyInfoList() {return alleleFrequencyInfoList;}

    public void setAlleleFrequencyInfoList(List<AlleleFrequencyInfoVo> alleleFrequencyInfoList) {this.alleleFrequencyInfoList = alleleFrequencyInfoList;}

}
