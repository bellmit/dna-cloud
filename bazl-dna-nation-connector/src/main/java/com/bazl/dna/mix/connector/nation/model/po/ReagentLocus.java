package com.bazl.dna.mix.connector.nation.model.po;
/*
* reagent_locus
* 试剂盒基因座表
* */
public class ReagentLocus {
    private String id;

    private String reagentKitId;

    private String locusId;

    private Integer ord;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getReagentKitId() {
        return reagentKitId;
    }

    public void setReagentKitId(String reagentKitId) {
        this.reagentKitId = reagentKitId == null ? null : reagentKitId.trim();
    }

    public String getLocusId() {
        return locusId;
    }

    public void setLocusId(String locusId) {
        this.locusId = locusId == null ? null : locusId.trim();
    }

    public Integer getOrd() {
        return ord;
    }

    public void setOrd(Integer ord) {
        this.ord = ord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReagentLocus)) return false;

        ReagentLocus that = (ReagentLocus) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (reagentKitId != null ? !reagentKitId.equals(that.reagentKitId) : that.reagentKitId != null) return false;
        if (locusId != null ? !locusId.equals(that.locusId) : that.locusId != null) return false;
        return ord != null ? ord.equals(that.ord) : that.ord == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (reagentKitId != null ? reagentKitId.hashCode() : 0);
        result = 31 * result + (locusId != null ? locusId.hashCode() : 0);
        result = 31 * result + (ord != null ? ord.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ReagentLocus{" +
                "id='" + id + '\'' +
                ", reagentKitId='" + reagentKitId + '\'' +
                ", locusId='" + locusId + '\'' +
                ", ord=" + ord +
                '}';
    }
}