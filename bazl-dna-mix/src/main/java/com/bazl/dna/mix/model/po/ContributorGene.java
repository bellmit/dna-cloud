package com.bazl.dna.mix.model.po;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: lzn
 * @Date: 2019/7/25 17:48
 * @Version: 1.0
 */
public class ContributorGene implements Serializable {

	private static final long serialVersionUID = 1L;

	//基因名称
    private String geneName;

    private List<GeneAllele> geneAlleleList;

    public String getGeneName() {
        return geneName;
    }

    public void setGeneName(String geneName) {
        this.geneName = geneName;
    }

    public List<GeneAllele> getGeneAlleleList() {
        return geneAlleleList;
    }

    public void setGeneAlleleList(List<GeneAllele> geneAlleleList) {
        this.geneAlleleList = geneAlleleList;
    }


}
