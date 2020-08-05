package com.bazl.dna.database.nation.converter.model.po;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/*
* LIMS_COMPARE_INFO
*LIMS样本比中表
* */
public class LimsCompareInfo {
    private String id;

    private String initServerNo;

    private String labId;

    private String matchGeneType;

    private String matchMode;

    @JSONField(name = "matchTime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date matchTime;

    private String srcObjectId;

    private String matchObjectId;

    private String compareAlgorithm;

    private String matchedDetail;

    private String lrValue;

    private String reviewType;

    private String reviewUser;

    @JSONField(name = "reviewDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date reviewDatetime;

    private String reviewDesc;

    private Short transferFlag;

    @JSONField(name = "transferDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date transferDatetime;

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

    private Integer ifBlindthan;

    private String notificationId;

    private BigDecimal retryCount;

    private String errorMsg;

    private BigDecimal delayTimes;

    @JSONField(name = "localStoreDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date localStoreDatetime;

    private String lrcResultId;

    private String reviewConclusion;

    @JSONField(name = "localCreateDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date localCreateDatetime;

    private String confirmedClueType;

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

    public String getMatchGeneType() {
        return matchGeneType;
    }

    public void setMatchGeneType(String matchGeneType) {
        this.matchGeneType = matchGeneType == null ? null : matchGeneType.trim();
    }

    public String getMatchMode() {
        return matchMode;
    }

    public void setMatchMode(String matchMode) {
        this.matchMode = matchMode == null ? null : matchMode.trim();
    }

    public Date getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(Date matchTime) {
        this.matchTime = matchTime;
    }

    public String getSrcObjectId() {
        return srcObjectId;
    }

    public void setSrcObjectId(String srcObjectId) {
        this.srcObjectId = srcObjectId == null ? null : srcObjectId.trim();
    }

    public String getMatchObjectId() {
        return matchObjectId;
    }

    public void setMatchObjectId(String matchObjectId) {
        this.matchObjectId = matchObjectId == null ? null : matchObjectId.trim();
    }

    public String getCompareAlgorithm() {
        return compareAlgorithm;
    }

    public void setCompareAlgorithm(String compareAlgorithm) {
        this.compareAlgorithm = compareAlgorithm == null ? null : compareAlgorithm.trim();
    }

    public String getMatchedDetail() {
        return matchedDetail;
    }

    public void setMatchedDetail(String matchedDetail) {
        this.matchedDetail = matchedDetail == null ? null : matchedDetail.trim();
    }

    public String getLrValue() {
        return lrValue;
    }

    public void setLrValue(String lrValue) {
        this.lrValue = lrValue == null ? null : lrValue.trim();
    }

    public String getReviewType() {
        return reviewType;
    }

    public void setReviewType(String reviewType) {
        this.reviewType = reviewType == null ? null : reviewType.trim();
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

    public String getReviewDesc() {
        return reviewDesc;
    }

    public void setReviewDesc(String reviewDesc) {
        this.reviewDesc = reviewDesc == null ? null : reviewDesc.trim();
    }

    public Short getTransferFlag() {
        return transferFlag;
    }

    public void setTransferFlag(Short transferFlag) {
        this.transferFlag = transferFlag;
    }

    public Date getTransferDatetime() {
        return transferDatetime;
    }

    public void setTransferDatetime(Date transferDatetime) {
        this.transferDatetime = transferDatetime;
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

    public Integer getIfBlindthan() {
        return ifBlindthan;
    }

    public void setIfBlindthan(Integer ifBlindthan) {
        this.ifBlindthan = ifBlindthan;
    }

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId == null ? null : notificationId.trim();
    }

    public BigDecimal getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(BigDecimal retryCount) {
        this.retryCount = retryCount;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg == null ? null : errorMsg.trim();
    }

    public BigDecimal getDelayTimes() {
        return delayTimes;
    }

    public void setDelayTimes(BigDecimal delayTimes) {
        this.delayTimes = delayTimes;
    }

    public Date getLocalStoreDatetime() {
        return localStoreDatetime;
    }

    public void setLocalStoreDatetime(Date localStoreDatetime) {
        this.localStoreDatetime = localStoreDatetime;
    }

    public String getLrcResultId() {
        return lrcResultId;
    }

    public void setLrcResultId(String lrcResultId) {
        this.lrcResultId = lrcResultId == null ? null : lrcResultId.trim();
    }

    public String getReviewConclusion() {
        return reviewConclusion;
    }

    public void setReviewConclusion(String reviewConclusion) {
        this.reviewConclusion = reviewConclusion == null ? null : reviewConclusion.trim();
    }

    public Date getLocalCreateDatetime() {
        return localCreateDatetime;
    }

    public void setLocalCreateDatetime(Date localCreateDatetime) {
        this.localCreateDatetime = localCreateDatetime;
    }

    public String getConfirmedClueType() {
        return confirmedClueType;
    }

    public void setConfirmedClueType(String confirmedClueType) {
        this.confirmedClueType = confirmedClueType == null ? null : confirmedClueType.trim();
    }
}