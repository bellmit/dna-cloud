package com.bazl.dna.mix.model.po;

import java.io.Serializable;

public class possibilityInfo implements Serializable {

	private static final long serialVersionUID = 1L;

    private String Id;

    private String compareId;

    private String contributorPossibilityGene;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCompareId() {
        return compareId;
    }

    public void setCompareId(String compareId) {
        this.compareId = compareId;
    }

    public String getContributorPossibilityGene() {
        return contributorPossibilityGene;
    }

    public void setContributorPossibilityGene(String contributorPossibilityGene) {
        this.contributorPossibilityGene = contributorPossibilityGene;
    }
}
