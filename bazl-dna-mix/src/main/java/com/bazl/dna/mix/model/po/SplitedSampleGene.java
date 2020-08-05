package com.bazl.dna.mix.model.po;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * SPLITED_SAMPLE_GENE
 * @author 
 */
public class SplitedSampleGene implements Serializable {
    /**
     * 主键ID
     */
    private String id;

    /**
     * 混合ID
     */
    private String mixedSampleGeneId;

    /**
     * 创建人
     */
    private String createPerson;

    /**
     * 创建时间
     */
    @JSONField(name = "caseDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createDatetime;

    /**
     * 更新人
     */
    private String updatePereson;

    /**
     * 更新时间
     */
    @JSONField(name = "caseDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updateDatetime;

    /**
     * 基因信息
     */
    private String geneInfo;

    /*
    *   队列id
    * */
    private String compareQueueId;

    private static final long serialVersionUID = 1L;

    public String getCompareQueueId() {
        return compareQueueId;
    }

    public void setCompareQueueId(String compareQueueId) {
        this.compareQueueId = compareQueueId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMixedSampleGeneId() {
        return mixedSampleGeneId;
    }

    public void setMixedSampleGeneId(String mixedSampleGeneId) {
        this.mixedSampleGeneId = mixedSampleGeneId;
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getUpdatePereson() {
        return updatePereson;
    }

    public void setUpdatePereson(String updatePereson) {
        this.updatePereson = updatePereson;
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public String getGeneInfo() {
        return geneInfo;
    }

    public void setGeneInfo(String geneInfo) {
        this.geneInfo = geneInfo;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        SplitedSampleGene other = (SplitedSampleGene) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getMixedSampleGeneId() == null ? other.getMixedSampleGeneId() == null : this.getMixedSampleGeneId().equals(other.getMixedSampleGeneId()))
                && (this.getCreatePerson() == null ? other.getCreatePerson() == null : this.getCreatePerson().equals(other.getCreatePerson()))
                && (this.getCreateDatetime() == null ? other.getCreateDatetime() == null : this.getCreateDatetime().equals(other.getCreateDatetime()))
                && (this.getUpdatePereson() == null ? other.getUpdatePereson() == null : this.getUpdatePereson().equals(other.getUpdatePereson()))
                && (this.getUpdateDatetime() == null ? other.getUpdateDatetime() == null : this.getUpdateDatetime().equals(other.getUpdateDatetime()))
                && (this.getGeneInfo() == null ? other.getGeneInfo() == null : this.getGeneInfo().equals(other.getGeneInfo()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMixedSampleGeneId() == null) ? 0 : getMixedSampleGeneId().hashCode());
        result = prime * result + ((getCreatePerson() == null) ? 0 : getCreatePerson().hashCode());
        result = prime * result + ((getCreateDatetime() == null) ? 0 : getCreateDatetime().hashCode());
        result = prime * result + ((getUpdatePereson() == null) ? 0 : getUpdatePereson().hashCode());
        result = prime * result + ((getUpdateDatetime() == null) ? 0 : getUpdateDatetime().hashCode());
        result = prime * result + ((getGeneInfo() == null) ? 0 : getGeneInfo().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", mixedSampleGeneId=").append(mixedSampleGeneId);
        sb.append(", createPerson=").append(createPerson);
        sb.append(", createDatetime=").append(createDatetime);
        sb.append(", updatePereson=").append(updatePereson);
        sb.append(", updateDatetime=").append(updateDatetime);
        sb.append(", geneInfo=").append(geneInfo);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}