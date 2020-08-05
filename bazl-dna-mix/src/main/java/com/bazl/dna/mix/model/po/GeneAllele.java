package com.bazl.dna.mix.model.po;

import java.io.Serializable;

/**
 * @Author: lzn
 * @Date: 2019/7/25 19:55
 * @Version: 1.0
 */
public class GeneAllele implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    //基因位点
    private String allele;
    //权重
    private String weights;
    //大于99%权重
    private String ninetyNinePercentWeights;

    public String getNinetyNinePercentWeights() {
        return ninetyNinePercentWeights;
    }

    public void setNinetyNinePercentWeights(String ninetyNinePercentWeights) {
        this.ninetyNinePercentWeights = ninetyNinePercentWeights;
    }

    public String getAllele() {
        return allele;
    }

    public void setAllele(String allele) {
        this.allele = allele;
    }

    public String getWeights() {
        return weights;
    }

    public void setWeights(String weights) {
        this.weights = weights;
    }
}
