package com.bazl.dna.database.nation.converter.model.po;
/*
*SAMPLE_PERSON_MAP
*样本与对象关系表
* */
public class SamplePersonMap {
    private String id;

    private String personObjectId;

    private String sampleId;

    private String relation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPersonObjectId() {
        return personObjectId;
    }

    public void setPersonObjectId(String personObjectId) {
        this.personObjectId = personObjectId == null ? null : personObjectId.trim();
    }

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId == null ? null : sampleId.trim();
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation == null ? null : relation.trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SamplePersonMap)) return false;

        SamplePersonMap that = (SamplePersonMap) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (personObjectId != null ? !personObjectId.equals(that.personObjectId) : that.personObjectId != null)
            return false;
        if (sampleId != null ? !sampleId.equals(that.sampleId) : that.sampleId != null) return false;
        return relation != null ? relation.equals(that.relation) : that.relation == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (personObjectId != null ? personObjectId.hashCode() : 0);
        result = 31 * result + (sampleId != null ? sampleId.hashCode() : 0);
        result = 31 * result + (relation != null ? relation.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SamplePersonMap{" +
                "id='" + id + '\'' +
                ", personObjectId='" + personObjectId + '\'' +
                ", sampleId='" + sampleId + '\'' +
                ", relation='" + relation + '\'' +
                '}';
    }
}