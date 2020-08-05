package com.bazl.dna.database.nation.converter.model.po;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/*
*SAMPLE_DNA_GENE
*样本基因DNA库信息表
* */
public class SampleDnaGene {
    private String id;

    private String initServerNo;

    private String labId;

    private String sampleId;

    private String sampleFlag;

    private String geneType;

    private String reagentKit;

    private Integer alleleCount;

    private Short storeFlag;

    @JSONField(name = "storeDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date storeDatetime;

    private String storeUser;

    private String reviewStatus;

    private String reviewIdea;

    private String reviewUser;

    @JSONField(name = "reviewDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date reviewDatetime;

    private Short transferFlag;

    private String transferUser;

    @JSONField(name = "transferDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date transferDatetime;

    private String matchedStatus;

    private Short mixFlag;

    private String remark;

    private Short deleteFlag;

    private String dataSource;

    private Integer dataLevel;

    private String createUser;

    @JSONField(name = "createDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createDatetime;

    private String updateUser;

    @JSONField(name = "updateDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updateDatetime;

    private String extId;

    @JSONField(name = "localCreateDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date localCreateDatetime;

    private String reviewUserId;

    private String geneInfo;


    private String reagentName;

    private String boardBarcode;

    private String genePicture;

    private String geneImagePath;

    private String createPerson;

    private String updatePereson;

    private String initServerNoLike;

    private String kitname;

    public String getKitname() {
        return kitname;
    }

    public void setKitname(String kitname) {
        this.kitname = kitname;
    }

    public String getInitServerNoLike() {
        return initServerNoLike;
    }

    public void setInitServerNoLike(String initServerNoLike) {
        this.initServerNoLike = initServerNoLike;
    }


    public String getGeneImagePath() {
        return geneImagePath;
    }

    public void setGeneImagePath(String geneImagePath) {
        this.geneImagePath = geneImagePath;
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson;
    }

    public String getUpdatePereson() {
        return updatePereson;
    }

    public void setUpdatePereson(String updatePereson) {
        this.updatePereson = updatePereson;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getInitServerNo() {
        return initServerNo;
    }

    public void setInitServerNo(String initServerNo) {
        this.initServerNo = initServerNo == null ? null : initServerNo.trim();
    }

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId == null ? null : labId.trim();
    }

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId == null ? null : sampleId.trim();
    }

    public String getSampleFlag() {
        return sampleFlag;
    }

    public void setSampleFlag(String sampleFlag) {
        this.sampleFlag = sampleFlag == null ? null : sampleFlag.trim();
    }

    public String getGeneType() {
        return geneType;
    }

    public void setGeneType(String geneType) {
        this.geneType = geneType == null ? null : geneType.trim();
    }

    public String getReagentKit() {
        return reagentKit;
    }

    public void setReagentKit(String reagentKit) {
        this.reagentKit = reagentKit == null ? null : reagentKit.trim();
    }

    public Integer getAlleleCount() {
        return alleleCount;
    }

    public void setAlleleCount(Integer alleleCount) {
        this.alleleCount = alleleCount;
    }

    public Short getStoreFlag() {
        return storeFlag;
    }

    public void setStoreFlag(Short storeFlag) {
        this.storeFlag = storeFlag;
    }

    public Date getStoreDatetime() {
        return storeDatetime;
    }

    public void setStoreDatetime(Date storeDatetime) {
        this.storeDatetime = storeDatetime;
    }

    public String getStoreUser() {
        return storeUser;
    }

    public void setStoreUser(String storeUser) {
        this.storeUser = storeUser == null ? null : storeUser.trim();
    }

    public String getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus == null ? null : reviewStatus.trim();
    }

    public String getReviewIdea() {
        return reviewIdea;
    }

    public void setReviewIdea(String reviewIdea) {
        this.reviewIdea = reviewIdea == null ? null : reviewIdea.trim();
    }

    public String getReviewUser() {
        return reviewUser;
    }

    public void setReviewUser(String reviewUser) {
        this.reviewUser = reviewUser == null ? null : reviewUser.trim();
    }

    public Date getReviewDatetime() {
        return reviewDatetime;
    }

    public void setReviewDatetime(Date reviewDatetime) {
        this.reviewDatetime = reviewDatetime;
    }

    public Short getTransferFlag() {
        return transferFlag;
    }

    public void setTransferFlag(Short transferFlag) {
        this.transferFlag = transferFlag;
    }

    public String getTransferUser() {
        return transferUser;
    }

    public void setTransferUser(String transferUser) {
        this.transferUser = transferUser == null ? null : transferUser.trim();
    }

    public Date getTransferDatetime() {
        return transferDatetime;
    }

    public void setTransferDatetime(Date transferDatetime) {
        this.transferDatetime = transferDatetime;
    }

    public String getMatchedStatus() {
        return matchedStatus;
    }

    public void setMatchedStatus(String matchedStatus) {
        this.matchedStatus = matchedStatus == null ? null : matchedStatus.trim();
    }

    public Short getMixFlag() {
        return mixFlag;
    }

    public void setMixFlag(Short mixFlag) {
        this.mixFlag = mixFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Short getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Short deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource == null ? null : dataSource.trim();
    }

    public Integer getDataLevel() {
        return dataLevel;
    }

    public void setDataLevel(Integer dataLevel) {
        this.dataLevel = dataLevel;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public String getExtId() {
        return extId;
    }

    public void setExtId(String extId) {
        this.extId = extId == null ? null : extId.trim();
    }

    public Date getLocalCreateDatetime() {
        return localCreateDatetime;
    }

    public void setLocalCreateDatetime(Date localCreateDatetime) {
        this.localCreateDatetime = localCreateDatetime;
    }

    public String getReviewUserId() {
        return reviewUserId;
    }

    public void setReviewUserId(String reviewUserId) {
        this.reviewUserId = reviewUserId == null ? null : reviewUserId.trim();
    }

    public String getGeneInfo() {
        return geneInfo;
    }

    public void setGeneInfo(String geneInfo) {
        this.geneInfo = geneInfo == null ? null : geneInfo.trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SampleDnaGene)) return false;

        SampleDnaGene that = (SampleDnaGene) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (initServerNo != null ? !initServerNo.equals(that.initServerNo) : that.initServerNo != null) return false;
        if (labId != null ? !labId.equals(that.labId) : that.labId != null) return false;
        if (sampleId != null ? !sampleId.equals(that.sampleId) : that.sampleId != null) return false;
        if (sampleFlag != null ? !sampleFlag.equals(that.sampleFlag) : that.sampleFlag != null) return false;
        if (geneType != null ? !geneType.equals(that.geneType) : that.geneType != null) return false;
        if (reagentKit != null ? !reagentKit.equals(that.reagentKit) : that.reagentKit != null) return false;
        if (alleleCount != null ? !alleleCount.equals(that.alleleCount) : that.alleleCount != null) return false;
        if (storeFlag != null ? !storeFlag.equals(that.storeFlag) : that.storeFlag != null) return false;
        if (storeDatetime != null ? !storeDatetime.equals(that.storeDatetime) : that.storeDatetime != null)
            return false;
        if (storeUser != null ? !storeUser.equals(that.storeUser) : that.storeUser != null) return false;
        if (reviewStatus != null ? !reviewStatus.equals(that.reviewStatus) : that.reviewStatus != null) return false;
        if (reviewIdea != null ? !reviewIdea.equals(that.reviewIdea) : that.reviewIdea != null) return false;
        if (reviewUser != null ? !reviewUser.equals(that.reviewUser) : that.reviewUser != null) return false;
        if (reviewDatetime != null ? !reviewDatetime.equals(that.reviewDatetime) : that.reviewDatetime != null)
            return false;
        if (transferFlag != null ? !transferFlag.equals(that.transferFlag) : that.transferFlag != null) return false;
        if (transferUser != null ? !transferUser.equals(that.transferUser) : that.transferUser != null) return false;
        if (transferDatetime != null ? !transferDatetime.equals(that.transferDatetime) : that.transferDatetime != null)
            return false;
        if (matchedStatus != null ? !matchedStatus.equals(that.matchedStatus) : that.matchedStatus != null)
            return false;
        if (mixFlag != null ? !mixFlag.equals(that.mixFlag) : that.mixFlag != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
        if (deleteFlag != null ? !deleteFlag.equals(that.deleteFlag) : that.deleteFlag != null) return false;
        if (dataSource != null ? !dataSource.equals(that.dataSource) : that.dataSource != null) return false;
        if (dataLevel != null ? !dataLevel.equals(that.dataLevel) : that.dataLevel != null) return false;
        if (createUser != null ? !createUser.equals(that.createUser) : that.createUser != null) return false;
        if (createDatetime != null ? !createDatetime.equals(that.createDatetime) : that.createDatetime != null)
            return false;
        if (updateUser != null ? !updateUser.equals(that.updateUser) : that.updateUser != null) return false;
        if (updateDatetime != null ? !updateDatetime.equals(that.updateDatetime) : that.updateDatetime != null)
            return false;
        if (extId != null ? !extId.equals(that.extId) : that.extId != null) return false;
        if (localCreateDatetime != null ? !localCreateDatetime.equals(that.localCreateDatetime) : that.localCreateDatetime != null)
            return false;
        if (reviewUserId != null ? !reviewUserId.equals(that.reviewUserId) : that.reviewUserId != null) return false;
        return geneInfo != null ? geneInfo.equals(that.geneInfo) : that.geneInfo == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (initServerNo != null ? initServerNo.hashCode() : 0);
        result = 31 * result + (labId != null ? labId.hashCode() : 0);
        result = 31 * result + (sampleId != null ? sampleId.hashCode() : 0);
        result = 31 * result + (sampleFlag != null ? sampleFlag.hashCode() : 0);
        result = 31 * result + (geneType != null ? geneType.hashCode() : 0);
        result = 31 * result + (reagentKit != null ? reagentKit.hashCode() : 0);
        result = 31 * result + (alleleCount != null ? alleleCount.hashCode() : 0);
        result = 31 * result + (storeFlag != null ? storeFlag.hashCode() : 0);
        result = 31 * result + (storeDatetime != null ? storeDatetime.hashCode() : 0);
        result = 31 * result + (storeUser != null ? storeUser.hashCode() : 0);
        result = 31 * result + (reviewStatus != null ? reviewStatus.hashCode() : 0);
        result = 31 * result + (reviewIdea != null ? reviewIdea.hashCode() : 0);
        result = 31 * result + (reviewUser != null ? reviewUser.hashCode() : 0);
        result = 31 * result + (reviewDatetime != null ? reviewDatetime.hashCode() : 0);
        result = 31 * result + (transferFlag != null ? transferFlag.hashCode() : 0);
        result = 31 * result + (transferUser != null ? transferUser.hashCode() : 0);
        result = 31 * result + (transferDatetime != null ? transferDatetime.hashCode() : 0);
        result = 31 * result + (matchedStatus != null ? matchedStatus.hashCode() : 0);
        result = 31 * result + (mixFlag != null ? mixFlag.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (deleteFlag != null ? deleteFlag.hashCode() : 0);
        result = 31 * result + (dataSource != null ? dataSource.hashCode() : 0);
        result = 31 * result + (dataLevel != null ? dataLevel.hashCode() : 0);
        result = 31 * result + (createUser != null ? createUser.hashCode() : 0);
        result = 31 * result + (createDatetime != null ? createDatetime.hashCode() : 0);
        result = 31 * result + (updateUser != null ? updateUser.hashCode() : 0);
        result = 31 * result + (updateDatetime != null ? updateDatetime.hashCode() : 0);
        result = 31 * result + (extId != null ? extId.hashCode() : 0);
        result = 31 * result + (localCreateDatetime != null ? localCreateDatetime.hashCode() : 0);
        result = 31 * result + (reviewUserId != null ? reviewUserId.hashCode() : 0);
        result = 31 * result + (geneInfo != null ? geneInfo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SampleDnaGene{" +
                "id='" + id + '\'' +
                ", initServerNo='" + initServerNo + '\'' +
                ", labId='" + labId + '\'' +
                ", sampleId='" + sampleId + '\'' +
                ", sampleFlag='" + sampleFlag + '\'' +
                ", geneType='" + geneType + '\'' +
                ", reagentKit='" + reagentKit + '\'' +
                ", alleleCount=" + alleleCount +
                ", storeFlag=" + storeFlag +
                ", storeDatetime=" + storeDatetime +
                ", storeUser='" + storeUser + '\'' +
                ", reviewStatus='" + reviewStatus + '\'' +
                ", reviewIdea='" + reviewIdea + '\'' +
                ", reviewUser='" + reviewUser + '\'' +
                ", reviewDatetime=" + reviewDatetime +
                ", transferFlag=" + transferFlag +
                ", transferUser='" + transferUser + '\'' +
                ", transferDatetime=" + transferDatetime +
                ", matchedStatus='" + matchedStatus + '\'' +
                ", mixFlag=" + mixFlag +
                ", remark='" + remark + '\'' +
                ", deleteFlag=" + deleteFlag +
                ", dataSource='" + dataSource + '\'' +
                ", dataLevel=" + dataLevel +
                ", createUser='" + createUser + '\'' +
                ", createDatetime=" + createDatetime +
                ", updateUser='" + updateUser + '\'' +
                ", updateDatetime=" + updateDatetime +
                ", extId='" + extId + '\'' +
                ", localCreateDatetime=" + localCreateDatetime +
                ", reviewUserId='" + reviewUserId + '\'' +
                ", geneInfo='" + geneInfo + '\'' +
                '}';
    }
}