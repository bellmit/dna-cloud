package com.bazl.dna.lims.model.po;

import java.io.Serializable;
import java.util.Date;

/**
 * @author huawei
 * @date 2020/7/9.
 */
public class FugitivesMiddleTable implements Serializable {

    private static final long serialVersionUID = 4508395802353005286L;

    /** 主键id */
    private String id;

    /** 在逃人员主键id */
    private String fugitivesInfoId;

    /** 委托主键id */
    private String consignmentInfoId;

    /** 案件主键id */
    private String caseId;

    /** 创建时间 */
    private Date createDatetime;

    /** 创建人 */
    private String createPerson;

    /** 更新时间 */
    private Date updateDatetime;

    /** 更新人 */
    private String updatePerson;

    /** 删除标记：0，未删除；1，已删除 */
    private String deleteFlag;

    /** 删除时间 */
    private Date deleteDatetime;

    /** 删除人 */
    private String deletePerson;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFugitivesInfoId() {
        return fugitivesInfoId;
    }

    public void setFugitivesInfoId(String fugitivesInfoId) {
        this.fugitivesInfoId = fugitivesInfoId;
    }

    public String getConsignmentInfoId() {
        return consignmentInfoId;
    }

    public void setConsignmentInfoId(String consignmentInfoId) {
        this.consignmentInfoId = consignmentInfoId;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Date getDeleteDatetime() {
        return deleteDatetime;
    }

    public void setDeleteDatetime(Date deleteDatetime) {
        this.deleteDatetime = deleteDatetime;
    }

    public String getDeletePerson() {
        return deletePerson;
    }

    public void setDeletePerson(String deletePerson) {
        this.deletePerson = deletePerson;
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public String getUpdatePerson() {
        return updatePerson;
    }

    public void setUpdatePerson(String updatePerson) {
        this.updatePerson = updatePerson;
    }
}