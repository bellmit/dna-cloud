package com.bazl.dna.database.nation.converter.model.po;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/*
* NOTIFICATION
*线索通报表
* */
public class Notification {
    private String id;

    private String matchGeneType;

    private String notificationNo;

    @JSONField(name = "matchDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date matchDatetime;

    private String matchMode;

    private String matchMethod;

    private String matchType;

    private String comparisonAlgorithm;

    private String matchDetail;

    private String lrValue;

    private String srcDataId;

    private String srcServerNo;

    private String srcClueType;

    private String srcReviewType;

    private String srcReviewDesc;

    private String srcReviewUser;

    @JSONField(name = "srcReviewDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date srcReviewDatetime;

    private String srcJudgeType;

    private String srcJudgeDesc;

    private String srcJudgeUser;

    @JSONField(name = "srcJudgeDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date srcJudgeDatetime;

    private String matchDataId;

    private String matchServerNo;

    private String matchClueType;

    private String matchReviewType;

    private String matchReviewDesc;

    private String matchReviewUser;

    @JSONField(name = "matchReviewDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date matchReviewDatetime;

    private String matchJudgeType;

    private String matchJudgeDesc;

    private String matchJudgeUser;

    @JSONField(name = "matchJudgeDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date matchJudgeDatetime;

    private String confirmedClueType;

    private String confirmedJudgeType;

    private String confirmedJudgeUser;

    @JSONField(name = "confirmedJudgeDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date confirmedJudgeDatetime;

    private String confirmedJudgeDesc;

    private String notificationStatus;

    private String remark;

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

    private String reserve1;

    private String reserve2;

    private String reserve3;

    private String reserve4;

    private String reserve5;

    private String reserve6;

    private String reserve7;

    private String reserve8;

    private Integer srcIfBlindthan;

    private Integer matchIfBlindthan;

    private Integer nationIfBlindthan;

    @JSONField(name = "localStoreDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date localStoreDatetime;

    private String lrcResultId;

    private String srcReviewConclusion;

    private String matchReviewConclusion;

    @JSONField(name = "srcReceiveDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date srcReceiveDatetime;

    @JSONField(name = "matchReceiveDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date matchReceiveDatetime;

    @JSONField(name = "localCreateDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date localCreateDatetime;

    private String pollutionMatchFlag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getMatchGeneType() {
        return matchGeneType;
    }

    public void setMatchGeneType(String matchGeneType) {
        this.matchGeneType = matchGeneType == null ? null : matchGeneType.trim();
    }

    public String getNotificationNo() {
        return notificationNo;
    }

    public void setNotificationNo(String notificationNo) {
        this.notificationNo = notificationNo == null ? null : notificationNo.trim();
    }

    public Date getMatchDatetime() {
        return matchDatetime;
    }

    public void setMatchDatetime(Date matchDatetime) {
        this.matchDatetime = matchDatetime;
    }

    public String getMatchMode() {
        return matchMode;
    }

    public void setMatchMode(String matchMode) {
        this.matchMode = matchMode == null ? null : matchMode.trim();
    }

    public String getMatchMethod() {
        return matchMethod;
    }

    public void setMatchMethod(String matchMethod) {
        this.matchMethod = matchMethod == null ? null : matchMethod.trim();
    }

    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType == null ? null : matchType.trim();
    }

    public String getComparisonAlgorithm() {
        return comparisonAlgorithm;
    }

    public void setComparisonAlgorithm(String comparisonAlgorithm) {
        this.comparisonAlgorithm = comparisonAlgorithm == null ? null : comparisonAlgorithm.trim();
    }

    public String getMatchDetail() {
        return matchDetail;
    }

    public void setMatchDetail(String matchDetail) {
        this.matchDetail = matchDetail == null ? null : matchDetail.trim();
    }

    public String getLrValue() {
        return lrValue;
    }

    public void setLrValue(String lrValue) {
        this.lrValue = lrValue == null ? null : lrValue.trim();
    }

    public String getSrcDataId() {
        return srcDataId;
    }

    public void setSrcDataId(String srcDataId) {
        this.srcDataId = srcDataId == null ? null : srcDataId.trim();
    }

    public String getSrcServerNo() {
        return srcServerNo;
    }

    public void setSrcServerNo(String srcServerNo) {
        this.srcServerNo = srcServerNo == null ? null : srcServerNo.trim();
    }

    public String getSrcClueType() {
        return srcClueType;
    }

    public void setSrcClueType(String srcClueType) {
        this.srcClueType = srcClueType == null ? null : srcClueType.trim();
    }

    public String getSrcReviewType() {
        return srcReviewType;
    }

    public void setSrcReviewType(String srcReviewType) {
        this.srcReviewType = srcReviewType == null ? null : srcReviewType.trim();
    }

    public String getSrcReviewDesc() {
        return srcReviewDesc;
    }

    public void setSrcReviewDesc(String srcReviewDesc) {
        this.srcReviewDesc = srcReviewDesc == null ? null : srcReviewDesc.trim();
    }

    public String getSrcReviewUser() {
        return srcReviewUser;
    }

    public void setSrcReviewUser(String srcReviewUser) {
        this.srcReviewUser = srcReviewUser == null ? null : srcReviewUser.trim();
    }

    public Date getSrcReviewDatetime() {
        return srcReviewDatetime;
    }

    public void setSrcReviewDatetime(Date srcReviewDatetime) {
        this.srcReviewDatetime = srcReviewDatetime;
    }

    public String getSrcJudgeType() {
        return srcJudgeType;
    }

    public void setSrcJudgeType(String srcJudgeType) {
        this.srcJudgeType = srcJudgeType == null ? null : srcJudgeType.trim();
    }

    public String getSrcJudgeDesc() {
        return srcJudgeDesc;
    }

    public void setSrcJudgeDesc(String srcJudgeDesc) {
        this.srcJudgeDesc = srcJudgeDesc == null ? null : srcJudgeDesc.trim();
    }

    public String getSrcJudgeUser() {
        return srcJudgeUser;
    }

    public void setSrcJudgeUser(String srcJudgeUser) {
        this.srcJudgeUser = srcJudgeUser == null ? null : srcJudgeUser.trim();
    }

    public Date getSrcJudgeDatetime() {
        return srcJudgeDatetime;
    }

    public void setSrcJudgeDatetime(Date srcJudgeDatetime) {
        this.srcJudgeDatetime = srcJudgeDatetime;
    }

    public String getMatchDataId() {
        return matchDataId;
    }

    public void setMatchDataId(String matchDataId) {
        this.matchDataId = matchDataId == null ? null : matchDataId.trim();
    }

    public String getMatchServerNo() {
        return matchServerNo;
    }

    public void setMatchServerNo(String matchServerNo) {
        this.matchServerNo = matchServerNo == null ? null : matchServerNo.trim();
    }

    public String getMatchClueType() {
        return matchClueType;
    }

    public void setMatchClueType(String matchClueType) {
        this.matchClueType = matchClueType == null ? null : matchClueType.trim();
    }

    public String getMatchReviewType() {
        return matchReviewType;
    }

    public void setMatchReviewType(String matchReviewType) {
        this.matchReviewType = matchReviewType == null ? null : matchReviewType.trim();
    }

    public String getMatchReviewDesc() {
        return matchReviewDesc;
    }

    public void setMatchReviewDesc(String matchReviewDesc) {
        this.matchReviewDesc = matchReviewDesc == null ? null : matchReviewDesc.trim();
    }

    public String getMatchReviewUser() {
        return matchReviewUser;
    }

    public void setMatchReviewUser(String matchReviewUser) {
        this.matchReviewUser = matchReviewUser == null ? null : matchReviewUser.trim();
    }

    public Date getMatchReviewDatetime() {
        return matchReviewDatetime;
    }

    public void setMatchReviewDatetime(Date matchReviewDatetime) {
        this.matchReviewDatetime = matchReviewDatetime;
    }

    public String getMatchJudgeType() {
        return matchJudgeType;
    }

    public void setMatchJudgeType(String matchJudgeType) {
        this.matchJudgeType = matchJudgeType == null ? null : matchJudgeType.trim();
    }

    public String getMatchJudgeDesc() {
        return matchJudgeDesc;
    }

    public void setMatchJudgeDesc(String matchJudgeDesc) {
        this.matchJudgeDesc = matchJudgeDesc == null ? null : matchJudgeDesc.trim();
    }

    public String getMatchJudgeUser() {
        return matchJudgeUser;
    }

    public void setMatchJudgeUser(String matchJudgeUser) {
        this.matchJudgeUser = matchJudgeUser == null ? null : matchJudgeUser.trim();
    }

    public Date getMatchJudgeDatetime() {
        return matchJudgeDatetime;
    }

    public void setMatchJudgeDatetime(Date matchJudgeDatetime) {
        this.matchJudgeDatetime = matchJudgeDatetime;
    }

    public String getConfirmedClueType() {
        return confirmedClueType;
    }

    public void setConfirmedClueType(String confirmedClueType) {
        this.confirmedClueType = confirmedClueType == null ? null : confirmedClueType.trim();
    }

    public String getConfirmedJudgeType() {
        return confirmedJudgeType;
    }

    public void setConfirmedJudgeType(String confirmedJudgeType) {
        this.confirmedJudgeType = confirmedJudgeType == null ? null : confirmedJudgeType.trim();
    }

    public String getConfirmedJudgeUser() {
        return confirmedJudgeUser;
    }

    public void setConfirmedJudgeUser(String confirmedJudgeUser) {
        this.confirmedJudgeUser = confirmedJudgeUser == null ? null : confirmedJudgeUser.trim();
    }

    public Date getConfirmedJudgeDatetime() {
        return confirmedJudgeDatetime;
    }

    public void setConfirmedJudgeDatetime(Date confirmedJudgeDatetime) {
        this.confirmedJudgeDatetime = confirmedJudgeDatetime;
    }

    public String getConfirmedJudgeDesc() {
        return confirmedJudgeDesc;
    }

    public void setConfirmedJudgeDesc(String confirmedJudgeDesc) {
        this.confirmedJudgeDesc = confirmedJudgeDesc == null ? null : confirmedJudgeDesc.trim();
    }

    public String getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(String notificationStatus) {
        this.notificationStatus = notificationStatus == null ? null : notificationStatus.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    public String getReserve1() {
        return reserve1;
    }

    public void setReserve1(String reserve1) {
        this.reserve1 = reserve1 == null ? null : reserve1.trim();
    }

    public String getReserve2() {
        return reserve2;
    }

    public void setReserve2(String reserve2) {
        this.reserve2 = reserve2 == null ? null : reserve2.trim();
    }

    public String getReserve3() {
        return reserve3;
    }

    public void setReserve3(String reserve3) {
        this.reserve3 = reserve3 == null ? null : reserve3.trim();
    }

    public String getReserve4() {
        return reserve4;
    }

    public void setReserve4(String reserve4) {
        this.reserve4 = reserve4 == null ? null : reserve4.trim();
    }

    public String getReserve5() {
        return reserve5;
    }

    public void setReserve5(String reserve5) {
        this.reserve5 = reserve5 == null ? null : reserve5.trim();
    }

    public String getReserve6() {
        return reserve6;
    }

    public void setReserve6(String reserve6) {
        this.reserve6 = reserve6 == null ? null : reserve6.trim();
    }

    public String getReserve7() {
        return reserve7;
    }

    public void setReserve7(String reserve7) {
        this.reserve7 = reserve7 == null ? null : reserve7.trim();
    }

    public String getReserve8() {
        return reserve8;
    }

    public void setReserve8(String reserve8) {
        this.reserve8 = reserve8 == null ? null : reserve8.trim();
    }

    public Integer getSrcIfBlindthan() {
        return srcIfBlindthan;
    }

    public void setSrcIfBlindthan(Integer srcIfBlindthan) {
        this.srcIfBlindthan = srcIfBlindthan;
    }

    public Integer getMatchIfBlindthan() {
        return matchIfBlindthan;
    }

    public void setMatchIfBlindthan(Integer matchIfBlindthan) {
        this.matchIfBlindthan = matchIfBlindthan;
    }

    public Integer getNationIfBlindthan() {
        return nationIfBlindthan;
    }

    public void setNationIfBlindthan(Integer nationIfBlindthan) {
        this.nationIfBlindthan = nationIfBlindthan;
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

    public String getSrcReviewConclusion() {
        return srcReviewConclusion;
    }

    public void setSrcReviewConclusion(String srcReviewConclusion) {
        this.srcReviewConclusion = srcReviewConclusion == null ? null : srcReviewConclusion.trim();
    }

    public String getMatchReviewConclusion() {
        return matchReviewConclusion;
    }

    public void setMatchReviewConclusion(String matchReviewConclusion) {
        this.matchReviewConclusion = matchReviewConclusion == null ? null : matchReviewConclusion.trim();
    }

    public Date getSrcReceiveDatetime() {
        return srcReceiveDatetime;
    }

    public void setSrcReceiveDatetime(Date srcReceiveDatetime) {
        this.srcReceiveDatetime = srcReceiveDatetime;
    }

    public Date getMatchReceiveDatetime() {
        return matchReceiveDatetime;
    }

    public void setMatchReceiveDatetime(Date matchReceiveDatetime) {
        this.matchReceiveDatetime = matchReceiveDatetime;
    }

    public Date getLocalCreateDatetime() {
        return localCreateDatetime;
    }

    public void setLocalCreateDatetime(Date localCreateDatetime) {
        this.localCreateDatetime = localCreateDatetime;
    }

    public String getPollutionMatchFlag() {
        return pollutionMatchFlag;
    }

    public void setPollutionMatchFlag(String pollutionMatchFlag) {
        this.pollutionMatchFlag = pollutionMatchFlag == null ? null : pollutionMatchFlag.trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Notification)) return false;

        Notification that = (Notification) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (matchGeneType != null ? !matchGeneType.equals(that.matchGeneType) : that.matchGeneType != null)
            return false;
        if (notificationNo != null ? !notificationNo.equals(that.notificationNo) : that.notificationNo != null)
            return false;
        if (matchDatetime != null ? !matchDatetime.equals(that.matchDatetime) : that.matchDatetime != null)
            return false;
        if (matchMode != null ? !matchMode.equals(that.matchMode) : that.matchMode != null) return false;
        if (matchMethod != null ? !matchMethod.equals(that.matchMethod) : that.matchMethod != null) return false;
        if (matchType != null ? !matchType.equals(that.matchType) : that.matchType != null) return false;
        if (comparisonAlgorithm != null ? !comparisonAlgorithm.equals(that.comparisonAlgorithm) : that.comparisonAlgorithm != null)
            return false;
        if (matchDetail != null ? !matchDetail.equals(that.matchDetail) : that.matchDetail != null) return false;
        if (lrValue != null ? !lrValue.equals(that.lrValue) : that.lrValue != null) return false;
        if (srcDataId != null ? !srcDataId.equals(that.srcDataId) : that.srcDataId != null) return false;
        if (srcServerNo != null ? !srcServerNo.equals(that.srcServerNo) : that.srcServerNo != null) return false;
        if (srcClueType != null ? !srcClueType.equals(that.srcClueType) : that.srcClueType != null) return false;
        if (srcReviewType != null ? !srcReviewType.equals(that.srcReviewType) : that.srcReviewType != null)
            return false;
        if (srcReviewDesc != null ? !srcReviewDesc.equals(that.srcReviewDesc) : that.srcReviewDesc != null)
            return false;
        if (srcReviewUser != null ? !srcReviewUser.equals(that.srcReviewUser) : that.srcReviewUser != null)
            return false;
        if (srcReviewDatetime != null ? !srcReviewDatetime.equals(that.srcReviewDatetime) : that.srcReviewDatetime != null)
            return false;
        if (srcJudgeType != null ? !srcJudgeType.equals(that.srcJudgeType) : that.srcJudgeType != null) return false;
        if (srcJudgeDesc != null ? !srcJudgeDesc.equals(that.srcJudgeDesc) : that.srcJudgeDesc != null) return false;
        if (srcJudgeUser != null ? !srcJudgeUser.equals(that.srcJudgeUser) : that.srcJudgeUser != null) return false;
        if (srcJudgeDatetime != null ? !srcJudgeDatetime.equals(that.srcJudgeDatetime) : that.srcJudgeDatetime != null)
            return false;
        if (matchDataId != null ? !matchDataId.equals(that.matchDataId) : that.matchDataId != null) return false;
        if (matchServerNo != null ? !matchServerNo.equals(that.matchServerNo) : that.matchServerNo != null)
            return false;
        if (matchClueType != null ? !matchClueType.equals(that.matchClueType) : that.matchClueType != null)
            return false;
        if (matchReviewType != null ? !matchReviewType.equals(that.matchReviewType) : that.matchReviewType != null)
            return false;
        if (matchReviewDesc != null ? !matchReviewDesc.equals(that.matchReviewDesc) : that.matchReviewDesc != null)
            return false;
        if (matchReviewUser != null ? !matchReviewUser.equals(that.matchReviewUser) : that.matchReviewUser != null)
            return false;
        if (matchReviewDatetime != null ? !matchReviewDatetime.equals(that.matchReviewDatetime) : that.matchReviewDatetime != null)
            return false;
        if (matchJudgeType != null ? !matchJudgeType.equals(that.matchJudgeType) : that.matchJudgeType != null)
            return false;
        if (matchJudgeDesc != null ? !matchJudgeDesc.equals(that.matchJudgeDesc) : that.matchJudgeDesc != null)
            return false;
        if (matchJudgeUser != null ? !matchJudgeUser.equals(that.matchJudgeUser) : that.matchJudgeUser != null)
            return false;
        if (matchJudgeDatetime != null ? !matchJudgeDatetime.equals(that.matchJudgeDatetime) : that.matchJudgeDatetime != null)
            return false;
        if (confirmedClueType != null ? !confirmedClueType.equals(that.confirmedClueType) : that.confirmedClueType != null)
            return false;
        if (confirmedJudgeType != null ? !confirmedJudgeType.equals(that.confirmedJudgeType) : that.confirmedJudgeType != null)
            return false;
        if (confirmedJudgeUser != null ? !confirmedJudgeUser.equals(that.confirmedJudgeUser) : that.confirmedJudgeUser != null)
            return false;
        if (confirmedJudgeDatetime != null ? !confirmedJudgeDatetime.equals(that.confirmedJudgeDatetime) : that.confirmedJudgeDatetime != null)
            return false;
        if (confirmedJudgeDesc != null ? !confirmedJudgeDesc.equals(that.confirmedJudgeDesc) : that.confirmedJudgeDesc != null)
            return false;
        if (notificationStatus != null ? !notificationStatus.equals(that.notificationStatus) : that.notificationStatus != null)
            return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
        if (dataSource != null ? !dataSource.equals(that.dataSource) : that.dataSource != null) return false;
        if (dataLevel != null ? !dataLevel.equals(that.dataLevel) : that.dataLevel != null) return false;
        if (createUser != null ? !createUser.equals(that.createUser) : that.createUser != null) return false;
        if (createDatetime != null ? !createDatetime.equals(that.createDatetime) : that.createDatetime != null)
            return false;
        if (updateUser != null ? !updateUser.equals(that.updateUser) : that.updateUser != null) return false;
        if (updateDatetime != null ? !updateDatetime.equals(that.updateDatetime) : that.updateDatetime != null)
            return false;
        if (reserve1 != null ? !reserve1.equals(that.reserve1) : that.reserve1 != null) return false;
        if (reserve2 != null ? !reserve2.equals(that.reserve2) : that.reserve2 != null) return false;
        if (reserve3 != null ? !reserve3.equals(that.reserve3) : that.reserve3 != null) return false;
        if (reserve4 != null ? !reserve4.equals(that.reserve4) : that.reserve4 != null) return false;
        if (reserve5 != null ? !reserve5.equals(that.reserve5) : that.reserve5 != null) return false;
        if (reserve6 != null ? !reserve6.equals(that.reserve6) : that.reserve6 != null) return false;
        if (reserve7 != null ? !reserve7.equals(that.reserve7) : that.reserve7 != null) return false;
        if (reserve8 != null ? !reserve8.equals(that.reserve8) : that.reserve8 != null) return false;
        if (srcIfBlindthan != null ? !srcIfBlindthan.equals(that.srcIfBlindthan) : that.srcIfBlindthan != null)
            return false;
        if (matchIfBlindthan != null ? !matchIfBlindthan.equals(that.matchIfBlindthan) : that.matchIfBlindthan != null)
            return false;
        if (nationIfBlindthan != null ? !nationIfBlindthan.equals(that.nationIfBlindthan) : that.nationIfBlindthan != null)
            return false;
        if (localStoreDatetime != null ? !localStoreDatetime.equals(that.localStoreDatetime) : that.localStoreDatetime != null)
            return false;
        if (lrcResultId != null ? !lrcResultId.equals(that.lrcResultId) : that.lrcResultId != null) return false;
        if (srcReviewConclusion != null ? !srcReviewConclusion.equals(that.srcReviewConclusion) : that.srcReviewConclusion != null)
            return false;
        if (matchReviewConclusion != null ? !matchReviewConclusion.equals(that.matchReviewConclusion) : that.matchReviewConclusion != null)
            return false;
        if (srcReceiveDatetime != null ? !srcReceiveDatetime.equals(that.srcReceiveDatetime) : that.srcReceiveDatetime != null)
            return false;
        if (matchReceiveDatetime != null ? !matchReceiveDatetime.equals(that.matchReceiveDatetime) : that.matchReceiveDatetime != null)
            return false;
        if (localCreateDatetime != null ? !localCreateDatetime.equals(that.localCreateDatetime) : that.localCreateDatetime != null)
            return false;
        return pollutionMatchFlag != null ? pollutionMatchFlag.equals(that.pollutionMatchFlag) : that.pollutionMatchFlag == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (matchGeneType != null ? matchGeneType.hashCode() : 0);
        result = 31 * result + (notificationNo != null ? notificationNo.hashCode() : 0);
        result = 31 * result + (matchDatetime != null ? matchDatetime.hashCode() : 0);
        result = 31 * result + (matchMode != null ? matchMode.hashCode() : 0);
        result = 31 * result + (matchMethod != null ? matchMethod.hashCode() : 0);
        result = 31 * result + (matchType != null ? matchType.hashCode() : 0);
        result = 31 * result + (comparisonAlgorithm != null ? comparisonAlgorithm.hashCode() : 0);
        result = 31 * result + (matchDetail != null ? matchDetail.hashCode() : 0);
        result = 31 * result + (lrValue != null ? lrValue.hashCode() : 0);
        result = 31 * result + (srcDataId != null ? srcDataId.hashCode() : 0);
        result = 31 * result + (srcServerNo != null ? srcServerNo.hashCode() : 0);
        result = 31 * result + (srcClueType != null ? srcClueType.hashCode() : 0);
        result = 31 * result + (srcReviewType != null ? srcReviewType.hashCode() : 0);
        result = 31 * result + (srcReviewDesc != null ? srcReviewDesc.hashCode() : 0);
        result = 31 * result + (srcReviewUser != null ? srcReviewUser.hashCode() : 0);
        result = 31 * result + (srcReviewDatetime != null ? srcReviewDatetime.hashCode() : 0);
        result = 31 * result + (srcJudgeType != null ? srcJudgeType.hashCode() : 0);
        result = 31 * result + (srcJudgeDesc != null ? srcJudgeDesc.hashCode() : 0);
        result = 31 * result + (srcJudgeUser != null ? srcJudgeUser.hashCode() : 0);
        result = 31 * result + (srcJudgeDatetime != null ? srcJudgeDatetime.hashCode() : 0);
        result = 31 * result + (matchDataId != null ? matchDataId.hashCode() : 0);
        result = 31 * result + (matchServerNo != null ? matchServerNo.hashCode() : 0);
        result = 31 * result + (matchClueType != null ? matchClueType.hashCode() : 0);
        result = 31 * result + (matchReviewType != null ? matchReviewType.hashCode() : 0);
        result = 31 * result + (matchReviewDesc != null ? matchReviewDesc.hashCode() : 0);
        result = 31 * result + (matchReviewUser != null ? matchReviewUser.hashCode() : 0);
        result = 31 * result + (matchReviewDatetime != null ? matchReviewDatetime.hashCode() : 0);
        result = 31 * result + (matchJudgeType != null ? matchJudgeType.hashCode() : 0);
        result = 31 * result + (matchJudgeDesc != null ? matchJudgeDesc.hashCode() : 0);
        result = 31 * result + (matchJudgeUser != null ? matchJudgeUser.hashCode() : 0);
        result = 31 * result + (matchJudgeDatetime != null ? matchJudgeDatetime.hashCode() : 0);
        result = 31 * result + (confirmedClueType != null ? confirmedClueType.hashCode() : 0);
        result = 31 * result + (confirmedJudgeType != null ? confirmedJudgeType.hashCode() : 0);
        result = 31 * result + (confirmedJudgeUser != null ? confirmedJudgeUser.hashCode() : 0);
        result = 31 * result + (confirmedJudgeDatetime != null ? confirmedJudgeDatetime.hashCode() : 0);
        result = 31 * result + (confirmedJudgeDesc != null ? confirmedJudgeDesc.hashCode() : 0);
        result = 31 * result + (notificationStatus != null ? notificationStatus.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (dataSource != null ? dataSource.hashCode() : 0);
        result = 31 * result + (dataLevel != null ? dataLevel.hashCode() : 0);
        result = 31 * result + (createUser != null ? createUser.hashCode() : 0);
        result = 31 * result + (createDatetime != null ? createDatetime.hashCode() : 0);
        result = 31 * result + (updateUser != null ? updateUser.hashCode() : 0);
        result = 31 * result + (updateDatetime != null ? updateDatetime.hashCode() : 0);
        result = 31 * result + (reserve1 != null ? reserve1.hashCode() : 0);
        result = 31 * result + (reserve2 != null ? reserve2.hashCode() : 0);
        result = 31 * result + (reserve3 != null ? reserve3.hashCode() : 0);
        result = 31 * result + (reserve4 != null ? reserve4.hashCode() : 0);
        result = 31 * result + (reserve5 != null ? reserve5.hashCode() : 0);
        result = 31 * result + (reserve6 != null ? reserve6.hashCode() : 0);
        result = 31 * result + (reserve7 != null ? reserve7.hashCode() : 0);
        result = 31 * result + (reserve8 != null ? reserve8.hashCode() : 0);
        result = 31 * result + (srcIfBlindthan != null ? srcIfBlindthan.hashCode() : 0);
        result = 31 * result + (matchIfBlindthan != null ? matchIfBlindthan.hashCode() : 0);
        result = 31 * result + (nationIfBlindthan != null ? nationIfBlindthan.hashCode() : 0);
        result = 31 * result + (localStoreDatetime != null ? localStoreDatetime.hashCode() : 0);
        result = 31 * result + (lrcResultId != null ? lrcResultId.hashCode() : 0);
        result = 31 * result + (srcReviewConclusion != null ? srcReviewConclusion.hashCode() : 0);
        result = 31 * result + (matchReviewConclusion != null ? matchReviewConclusion.hashCode() : 0);
        result = 31 * result + (srcReceiveDatetime != null ? srcReceiveDatetime.hashCode() : 0);
        result = 31 * result + (matchReceiveDatetime != null ? matchReceiveDatetime.hashCode() : 0);
        result = 31 * result + (localCreateDatetime != null ? localCreateDatetime.hashCode() : 0);
        result = 31 * result + (pollutionMatchFlag != null ? pollutionMatchFlag.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id='" + id + '\'' +
                ", matchGeneType='" + matchGeneType + '\'' +
                ", notificationNo='" + notificationNo + '\'' +
                ", matchDatetime=" + matchDatetime +
                ", matchMode='" + matchMode + '\'' +
                ", matchMethod='" + matchMethod + '\'' +
                ", matchType='" + matchType + '\'' +
                ", comparisonAlgorithm='" + comparisonAlgorithm + '\'' +
                ", matchDetail='" + matchDetail + '\'' +
                ", lrValue='" + lrValue + '\'' +
                ", srcDataId='" + srcDataId + '\'' +
                ", srcServerNo='" + srcServerNo + '\'' +
                ", srcClueType='" + srcClueType + '\'' +
                ", srcReviewType='" + srcReviewType + '\'' +
                ", srcReviewDesc='" + srcReviewDesc + '\'' +
                ", srcReviewUser='" + srcReviewUser + '\'' +
                ", srcReviewDatetime=" + srcReviewDatetime +
                ", srcJudgeType='" + srcJudgeType + '\'' +
                ", srcJudgeDesc='" + srcJudgeDesc + '\'' +
                ", srcJudgeUser='" + srcJudgeUser + '\'' +
                ", srcJudgeDatetime=" + srcJudgeDatetime +
                ", matchDataId='" + matchDataId + '\'' +
                ", matchServerNo='" + matchServerNo + '\'' +
                ", matchClueType='" + matchClueType + '\'' +
                ", matchReviewType='" + matchReviewType + '\'' +
                ", matchReviewDesc='" + matchReviewDesc + '\'' +
                ", matchReviewUser='" + matchReviewUser + '\'' +
                ", matchReviewDatetime=" + matchReviewDatetime +
                ", matchJudgeType='" + matchJudgeType + '\'' +
                ", matchJudgeDesc='" + matchJudgeDesc + '\'' +
                ", matchJudgeUser='" + matchJudgeUser + '\'' +
                ", matchJudgeDatetime=" + matchJudgeDatetime +
                ", confirmedClueType='" + confirmedClueType + '\'' +
                ", confirmedJudgeType='" + confirmedJudgeType + '\'' +
                ", confirmedJudgeUser='" + confirmedJudgeUser + '\'' +
                ", confirmedJudgeDatetime=" + confirmedJudgeDatetime +
                ", confirmedJudgeDesc='" + confirmedJudgeDesc + '\'' +
                ", notificationStatus='" + notificationStatus + '\'' +
                ", remark='" + remark + '\'' +
                ", dataSource='" + dataSource + '\'' +
                ", dataLevel=" + dataLevel +
                ", createUser='" + createUser + '\'' +
                ", createDatetime=" + createDatetime +
                ", updateUser='" + updateUser + '\'' +
                ", updateDatetime=" + updateDatetime +
                ", reserve1='" + reserve1 + '\'' +
                ", reserve2='" + reserve2 + '\'' +
                ", reserve3='" + reserve3 + '\'' +
                ", reserve4='" + reserve4 + '\'' +
                ", reserve5='" + reserve5 + '\'' +
                ", reserve6='" + reserve6 + '\'' +
                ", reserve7='" + reserve7 + '\'' +
                ", reserve8='" + reserve8 + '\'' +
                ", srcIfBlindthan=" + srcIfBlindthan +
                ", matchIfBlindthan=" + matchIfBlindthan +
                ", nationIfBlindthan=" + nationIfBlindthan +
                ", localStoreDatetime=" + localStoreDatetime +
                ", lrcResultId='" + lrcResultId + '\'' +
                ", srcReviewConclusion='" + srcReviewConclusion + '\'' +
                ", matchReviewConclusion='" + matchReviewConclusion + '\'' +
                ", srcReceiveDatetime=" + srcReceiveDatetime +
                ", matchReceiveDatetime=" + matchReceiveDatetime +
                ", localCreateDatetime=" + localCreateDatetime +
                ", pollutionMatchFlag='" + pollutionMatchFlag + '\'' +
                '}';
    }
}