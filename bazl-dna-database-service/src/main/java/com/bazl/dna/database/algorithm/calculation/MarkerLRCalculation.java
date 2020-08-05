package com.bazl.dna.database.algorithm.calculation;

import com.bazl.dna.database.service.model.po.AlleleFrequencyInfo;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 计算基因座似然比工具类
 * Created by Sun on 2019/4/1.
 * edit by lizhihua on 2019/7/29
 */
public class MarkerLRCalculation {

    private List<AlleleFrequencyInfo> allFrequencyList;

    public MarkerLRCalculation(List<AlleleFrequencyInfo> allFrequencyList){
        this.allFrequencyList = allFrequencyList;
    }

    /**
     * 北京法医的算法
     */
    public Double calculateSingleMarkerLR(String markerName, String alleleValue1, String alleleValue2) {
        Double lr = Double.NaN;
        Double p = getAlleleFreqs(markerName, alleleValue1);
        if (!p.isNaN()) {
            if (alleleValue1.compareTo(alleleValue2) == 0) {
                lr = 1 / (p * p);
            } else {
                Double q = getAlleleFreqs(markerName, alleleValue2);
                if(!q.isNaN()) {
                    lr = 1 / (2 * p * q);
                }
            }
        }
        return lr;
    }

    /**
     * 获取基因频率。
     *
     * 若找不到对应基因频率，则认为是稀有基因，根据顶棚原理，取最小值。
     * 即取该基因座下所有等位基因频率中的最小值，假设为p。这种方法将稀有基因的证据作用缩到最大，
     * 因为计算似然比的时候会乘以p的倒数，p最小则其倒数最大，对于总LR值的贡献最大。
     */
    public Double getAlleleFreqs(String markerName, String alleleValue) {
        Double af = GetAlleleFreqs(markerName, alleleValue);
        if (af == 1.0d) {
            af = GetMinAlleleFreq(markerName);
        }
        return af;
    }

    /**
     * Get allele frequency by marker name and allele value.
     *
     * @param markerName        基因座名称
     * @param alleleValue       等位基因值
     * @return allele frequency if found the allele; 1.0d if not
     */
    public double GetAlleleFreqs(String markerName, String alleleValue) {
        for (AlleleFrequencyInfo frequency : allFrequencyList) {
            if (frequency.getLocusName().compareToIgnoreCase(markerName) == 0
                    && frequency.getAlleleValue().compareTo(alleleValue) == 0) {
                return frequency.getProbability().doubleValue();
            }
        }

        return 1.0d;
    }

    /**
     * get minimum allele frequency of the specified marker
     *
     * @param markerName
     * @return allele frequency if succeeded; 1.0d if no marker is found
     */
    public Double GetMinAlleleFreq(String markerName) {
        List<AlleleFrequencyInfo> markerFrequencyList = allFrequencyList.stream().filter(frequency -> frequency.getLocusName().equalsIgnoreCase(markerName)).collect(Collectors.toList());

        if(markerFrequencyList != null && markerFrequencyList.size() > 0){
            return getMinAlleleFreq(markerFrequencyList);
        }else{
            return Double.NaN;
        }
    }

    final int AF_MIN = 0;
    final int AF_MAX = 1;

    public Double getMinAlleleFreq(List<AlleleFrequencyInfo> markerFrequencyList) {
        return getTopAlleleFreq(markerFrequencyList, AF_MIN);
    }
    public Double getMaxAlleleFreq(List<AlleleFrequencyInfo> markerFrequencyList) {
        return getTopAlleleFreq(markerFrequencyList, AF_MAX);
    }

    private Double getTopAlleleFreq(List<AlleleFrequencyInfo> markerFrequencyList, int type) {
        Double af = 0.0d;
        if(markerFrequencyList != null && markerFrequencyList.size() > 0) {
            af = markerFrequencyList.get(0).getProbability().doubleValue();
        }
        for (AlleleFrequencyInfo allele: markerFrequencyList) {
            if (allele.getProbability().doubleValue() > af) {
                if (type == AF_MAX) {
                    af = allele.getProbability().doubleValue();
                }
            } else {
                if (type == AF_MIN) {
                    af = allele.getProbability().doubleValue();
                }
            }
        }
        return (af <= 0.0) ? Double.NaN : af;
    }
}
