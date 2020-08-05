package com.bazl.dna.mix.model.po;

import java.io.Serializable;
import java.util.Date;

/**
 * QUALTY_PERSONNEL
 * @author
 */
public class QualtyPersonnel implements Serializable {
    /**
     * 主键
     */
    private String id;

    /**
     * 样本编号
     */
    private String sampleNo;

    /**
     * 样本名称
     */
    private String sampleName;

    /**
     * 试剂盒
     */
    private String reagentName;

    /**
     * 电泳板号
     */
    private String boardBarcode;

    /**
     * 基因图谱
     */
    private String genePicture;

    /**
     * 创建人
     */
    private String createPerson;

    /**
     * 创建时间
     */
    private Date createDatetime;

    /**
     * 更新人
     */
    private String updatePereson;

    /**
     * 更新时间
     */
    private Date updateDatetime;

    /**
     * 基因信息
     */
    private String geneInfo;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSampleNo() {
        return sampleNo;
    }

    public void setSampleNo(String sampleNo) {
        this.sampleNo = sampleNo;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public String getReagentName() {
        return reagentName;
    }

    public void setReagentName(String reagentName) {
        this.reagentName = reagentName;
    }

    public String getBoardBarcode() {
        return boardBarcode;
    }

    public void setBoardBarcode(String boardBarcode) {
        this.boardBarcode = boardBarcode;
    }

    public String getGenePicture() {
        return genePicture;
    }

    public void setGenePicture(String genePicture) {
        this.genePicture = genePicture;
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
        QualtyPersonnel other = (QualtyPersonnel) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getSampleNo() == null ? other.getSampleNo() == null : this.getSampleNo().equals(other.getSampleNo()))
                && (this.getSampleName() == null ? other.getSampleName() == null : this.getSampleName().equals(other.getSampleName()))
                && (this.getReagentName() == null ? other.getReagentName() == null : this.getReagentName().equals(other.getReagentName()))
                && (this.getBoardBarcode() == null ? other.getBoardBarcode() == null : this.getBoardBarcode().equals(other.getBoardBarcode()))
                && (this.getGenePicture() == null ? other.getGenePicture() == null : this.getGenePicture().equals(other.getGenePicture()))
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
        result = prime * result + ((getSampleNo() == null) ? 0 : getSampleNo().hashCode());
        result = prime * result + ((getSampleName() == null) ? 0 : getSampleName().hashCode());
        result = prime * result + ((getReagentName() == null) ? 0 : getReagentName().hashCode());
        result = prime * result + ((getBoardBarcode() == null) ? 0 : getBoardBarcode().hashCode());
        result = prime * result + ((getGenePicture() == null) ? 0 : getGenePicture().hashCode());
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
        sb.append(", sampleNo=").append(sampleNo);
        sb.append(", sampleName=").append(sampleName);
        sb.append(", reagentName=").append(reagentName);
        sb.append(", boardBarcode=").append(boardBarcode);
        sb.append(", genePicture=").append(genePicture);
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