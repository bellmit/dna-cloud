package com.bazl.dna.mix.model.po;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * CASE_INFO
 * @author 
 */
public class CaseInfo implements Serializable {

	private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private String id;

    /**
     * 案件编号
     */
    private String caseNo;

    /**
     * 案件名称
     */
    private String caseName;

    /**
     * 案件简要案情
     */
    private String caseBrief;

    /**
     * 案发地点
     */
    private String caseLocation;

    /**
     * 案发时间
     */
//    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
//    @JsonSerialize(using = JsonDatetimeSerializer.class)
    @JSONField(name = "caseDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date caseDatetime;

    /**
     * 案件类型
     */
    private String caseType;

    /**
     * 案件性质
     */
    private String caseProperty;

    /**
     * 案件级别
     */
    private String caseLevel;

    /**
     * 案件状态
     */
    private String caseStatus;

    /**
     * 创建人
     */
    private String createPerson;

    /**
     * 创建时间
     */
//    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
//    @JsonSerialize(using = JsonDatetimeSerializer.class)
    @JSONField(name = "createDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createDatetime;

    /**
     * 更新人
     */
    private String updatePereson;

    /**
     * 更新时间
     */
//    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
//    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
//    @JsonSerialize(using = JsonDatetimeSerializer.class)
    @JSONField(name = "updateDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updateDatetime;

    /**
     * 更新时间
     */
    private String orgId;

    //案件id
    private String caseId;

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getCaseBrief() {
        return caseBrief;
    }

    public void setCaseBrief(String caseBrief) {
        this.caseBrief = caseBrief;
    }

    public String getCaseLocation() {
        return caseLocation;
    }

    public void setCaseLocation(String caseLocation) {
        this.caseLocation = caseLocation;
    }

    public Date getCaseDatetime() {
        return caseDatetime;
    }

    public void setCaseDatetime(Date caseDatetime) {
        this.caseDatetime = caseDatetime;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getCaseProperty() {
        return caseProperty;
    }

    public void setCaseProperty(String caseProperty) {
        this.caseProperty = caseProperty;
    }

    public String getCaseLevel() {
        return caseLevel;
    }

    public void setCaseLevel(String caseLevel) {
        this.caseLevel = caseLevel;
    }

    public String getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(String caseStatus) {
        this.caseStatus = caseStatus;
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
        CaseInfo other = (CaseInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCaseNo() == null ? other.getCaseNo() == null : this.getCaseNo().equals(other.getCaseNo()))
            && (this.getCaseName() == null ? other.getCaseName() == null : this.getCaseName().equals(other.getCaseName()))
            && (this.getCaseBrief() == null ? other.getCaseBrief() == null : this.getCaseBrief().equals(other.getCaseBrief()))
            && (this.getCaseLocation() == null ? other.getCaseLocation() == null : this.getCaseLocation().equals(other.getCaseLocation()))
            && (this.getCaseDatetime() == null ? other.getCaseDatetime() == null : this.getCaseDatetime().equals(other.getCaseDatetime()))
            && (this.getCaseType() == null ? other.getCaseType() == null : this.getCaseType().equals(other.getCaseType()))
            && (this.getCaseProperty() == null ? other.getCaseProperty() == null : this.getCaseProperty().equals(other.getCaseProperty()))
            && (this.getCaseLevel() == null ? other.getCaseLevel() == null : this.getCaseLevel().equals(other.getCaseLevel()))
            && (this.getCaseStatus() == null ? other.getCaseStatus() == null : this.getCaseStatus().equals(other.getCaseStatus()))
            && (this.getCreatePerson() == null ? other.getCreatePerson() == null : this.getCreatePerson().equals(other.getCreatePerson()))
            && (this.getCreateDatetime() == null ? other.getCreateDatetime() == null : this.getCreateDatetime().equals(other.getCreateDatetime()))
            && (this.getUpdatePereson() == null ? other.getUpdatePereson() == null : this.getUpdatePereson().equals(other.getUpdatePereson()))
            && (this.getUpdateDatetime() == null ? other.getUpdateDatetime() == null : this.getUpdateDatetime().equals(other.getUpdateDatetime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCaseNo() == null) ? 0 : getCaseNo().hashCode());
        result = prime * result + ((getCaseName() == null) ? 0 : getCaseName().hashCode());
        result = prime * result + ((getCaseBrief() == null) ? 0 : getCaseBrief().hashCode());
        result = prime * result + ((getCaseLocation() == null) ? 0 : getCaseLocation().hashCode());
        result = prime * result + ((getCaseDatetime() == null) ? 0 : getCaseDatetime().hashCode());
        result = prime * result + ((getCaseType() == null) ? 0 : getCaseType().hashCode());
        result = prime * result + ((getCaseProperty() == null) ? 0 : getCaseProperty().hashCode());
        result = prime * result + ((getCaseLevel() == null) ? 0 : getCaseLevel().hashCode());
        result = prime * result + ((getCaseStatus() == null) ? 0 : getCaseStatus().hashCode());
        result = prime * result + ((getCreatePerson() == null) ? 0 : getCreatePerson().hashCode());
        result = prime * result + ((getCreateDatetime() == null) ? 0 : getCreateDatetime().hashCode());
        result = prime * result + ((getUpdatePereson() == null) ? 0 : getUpdatePereson().hashCode());
        result = prime * result + ((getUpdateDatetime() == null) ? 0 : getUpdateDatetime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", caseNo=").append(caseNo);
        sb.append(", caseName=").append(caseName);
        sb.append(", caseBrief=").append(caseBrief);
        sb.append(", caseLocation=").append(caseLocation);
        sb.append(", caseDatetime=").append(caseDatetime);
        sb.append(", caseType=").append(caseType);
        sb.append(", caseProperty=").append(caseProperty);
        sb.append(", caseLevel=").append(caseLevel);
        sb.append(", caseStatus=").append(caseStatus);
        sb.append(", createPerson=").append(createPerson);
        sb.append(", createDatetime=").append(createDatetime);
        sb.append(", updatePereson=").append(updatePereson);
        sb.append(", updateDatetime=").append(updateDatetime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}