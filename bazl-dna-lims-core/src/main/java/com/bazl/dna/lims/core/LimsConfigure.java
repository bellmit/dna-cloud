package com.bazl.dna.lims.core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author lizhihua on 2020/6/9.
 */
@Component
@Configuration
public class LimsConfigure {

    @Value("${samplePhotosRootDir}")
    private String samplePhotosRootDir;
    @Value("${laboratory_record_cover}")
    private String laboratoryRecordCover;

    /** 检验人1 */
    @Value("${inspector1}")
    private String inspector1;

    /** 检验人1职务 */
    @Value("${inspector1Post}")
    private String inspector1Post;

    /** 检验人2 */
    @Value("${inspector2}")
    private String inspector2;

    /** 检验人2职务 */
    @Value("${inspector2Post}")
    private String inspector2Post;

    /**检验人3*/
    @Value("${inspector3}")
    private String inspector3;

    /** 检验人3职务 */
    @Value("${inspector3Post}")
    private String inspector3Post;

    @Value("${proxyImg}")
    private String proxyImg;
    @Value("${appointImg}")
    private String appointImg;
    @Value("${ftpIp}")
    private String ftpIp;
    @Value("${ftpPort}")
    private String ftpPort;
    @Value("${ftpProxyImg}")
    private String ftpProxyImg;
    @Value("${ftpAppointImg}")
    private String ftpAppointImg;
    @Value("${ftpUser}")
    private String ftpUser;
    @Value("${ftpPassword}")
    private String ftpPassword;
    @Value("${ftpFilePath}")
    private String ftpFilePath;
    //    @Value("${printUrl}")
//    private String printUrl;
    @Value("${page}")
    private String page;
    @Value("${bendiFilePath}")
    private String bendiFilePath;

    @Value("${ftpCaseFilePath}")
    private String ftpCaseFilePath;

    @Value("${Is_App_Inform}")
    private int isAppInform;
    @Value("${Is_App_Url}")
    private String AppUrl;

    @Value("${querenshu_control_no}")
    private String querenshuControlNo;
    @Value("${busong_querenshu_control_no}")
    private String busongQuerenshuControlNo;
    @Value("${yushiyan_control_no}")
    private String yushiyanControlNo;
    @Value("${amplified_records_no}")
    private String amplifiedRecordsNo;
    //入库单
    @Value("${warehouse_receipt_no}")
    private String warehouseReceiptNo;

    @Value("${defaultDnaLabName}")
    private String labName;

    @Value("${defaultDnaLabOrgId}")
    private String defaultDnaLabOrgId;

    @Value("${commissionSystemUrL}")
    private String commissionSystemUrL;
    @Value("${commissionAssesUrL}")
    private String commissionAssesUrL;

    @Value("${shTime}")
    private Long shTime;

    @Value("${routine_extraction_records_no}")
    private String routineExtractionNo;

    @Value("${seminal_extraction_no}")
    private String seminalExtractionNo;

    @Value("${electrophoretic_recording_no}")
    private String electrophoreticRecordingNo;

    @Value("${electrophoretic_automatic_no}")
    private String electrophoreticAutomaticNo;


    //入库匹配下限
    @Value("${minSameCount}")
    private int minSameCount;
    //本案匹配下限
    @Value("${minSameCaseCount}")
    private int minSameCaseCount;
    //亲缘匹配下限
    @Value("${minSameRelationCount}")
    private int minSameRelationCount;
    //混合匹配下限
    @Value("${minSameMixCount}")
    private int minSameMixCount;
    //容差
    @Value("${halfDiffCount}")
    private int halfDiffCount;


    /**
     * 同型比对，匹配下限、容差上限
     */
    @Value("${sameCompareMatchLimit}")
    private int sameCompareMatchLimit;
    @Value("${sameCompareDiffLimit}")
    private int sameCompareDiffLimit;

    /**
     * 亲缘比对，匹配下限、容差上限
     */
    @Value("${relativeCompareMatchLimit}")
    private int relativeCompareMatchLimit;
    @Value("${relativeCompareDiffLimit}")
    private int relativeCompareDiffLimit;

    /**
     * 混合比对，最多贡献者个数、匹配下限、容差上限
     */
    @Value("${mixContributorsCountLimit}")
    private int mixContributorsCountLimit;
    @Value("${mixCompareMatchLimit}")
    private int mixCompareMatchLimit;
    @Value("${mixCompareDiffLimit}")
    private int mixCompareDiffLimit;


    @Value("${inspectCourseUrl}")
    private String inspectCourseUrl;

    @Value("${limsDataStatsUrl}")
    private String limsDataStatsUrl;

    @Value("${dnaReagentUrl}")
    private String dnaReagentUrl;

    @Value("${strInstoreGeneCountLimit}")
    private int strInstoreGeneCountLimit;

    @Value("${ystrInstoreGeneCountLimit}")
    private int ystrInstoreGeneCountLimit;

    @Value("${getTokenUrl}")
    private String getTokenUrl;
    @Value("${uploadIdentifyBookUrl}")
    private String uploadIdentifyBookUrl;
    @Value("${jumpPageUrl}")
    private String jumpPageUrl;


    public String getInspector1() {
        return inspector1;
    }

    public void setInspector1(String inspector1) {
        this.inspector1 = inspector1;
    }

    public String getInspector1Post() {
        return inspector1Post;
    }

    public void setInspector1Post(String inspector1Post) {
        this.inspector1Post = inspector1Post;
    }

    public String getInspector2() {
        return inspector2;
    }

    public void setInspector2(String inspector2) {
        this.inspector2 = inspector2;
    }

    public String getInspector2Post() {
        return inspector2Post;
    }

    public void setInspector2Post(String inspector2Post) {
        this.inspector2Post = inspector2Post;
    }

    public String getInspector3() {
        return inspector3;
    }

    public void setInspector3(String inspector3) {
        this.inspector3 = inspector3;
    }

    public String getInspector3Post() {
        return inspector3Post;
    }

    public void setInspector3Post(String inspector3Post) {
        this.inspector3Post = inspector3Post;
    }

    public String getProxyImg() {
        return proxyImg;
    }

    public void setProxyImg(String proxyImg) {
        this.proxyImg = proxyImg;
    }

    public String getAppointImg() {
        return appointImg;
    }

    public void setAppointImg(String appointImg) {
        this.appointImg = appointImg;
    }

    public String getFtpIp() {
        return ftpIp;
    }

    public void setFtpIp(String ftpIp) {
        this.ftpIp = ftpIp;
    }

    public String getFtpPort() {
        return ftpPort;
    }

    public void setFtpPort(String ftpPort) {
        this.ftpPort = ftpPort;
    }

    public String getFtpProxyImg() {
        return ftpProxyImg;
    }

    public void setFtpProxyImg(String ftpProxyImg) {
        this.ftpProxyImg = ftpProxyImg;
    }

    public String getFtpAppointImg() {
        return ftpAppointImg;
    }

    public void setFtpAppointImg(String ftpAppointImg) {
        this.ftpAppointImg = ftpAppointImg;
    }

    public String getFtpUser() {
        return ftpUser;
    }

    public void setFtpUser(String ftpUser) {
        this.ftpUser = ftpUser;
    }

    public String getFtpPassword() {
        return ftpPassword;
    }

    public void setFtpPassword(String ftpPassword) {
        this.ftpPassword = ftpPassword;
    }

    public String getFtpFilePath() {
        return ftpFilePath;
    }

    public void setFtpFilePath(String ftpFilePath) {
        this.ftpFilePath = ftpFilePath;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getBendiFilePath() {
        return bendiFilePath;
    }

    public void setBendiFilePath(String bendiFilePath) {
        this.bendiFilePath = bendiFilePath;
    }

    public int getIsAppInform() {
        return isAppInform;
    }

    public void setIsAppInform(int isAppInform) {
        this.isAppInform = isAppInform;
    }

    public String getAppUrl() {
        return AppUrl;
    }

    public void setAppUrl(String appUrl) {
        AppUrl = appUrl;
    }

    public String getFtpCaseFilePath() {
        return ftpCaseFilePath;
    }

    public void setFtpCaseFilePath(String ftpCaseFilePath) {
        this.ftpCaseFilePath = ftpCaseFilePath;
    }

    public String getQuerenshuControlNo() {
        return querenshuControlNo;
    }

    public void setQuerenshuControlNo(String querenshuControlNo) {
        this.querenshuControlNo = querenshuControlNo;
    }

    public String getBusongQuerenshuControlNo() {
        return busongQuerenshuControlNo;
    }

    public void setBusongQuerenshuControlNo(String busongQuerenshuControlNo) {
        this.busongQuerenshuControlNo = busongQuerenshuControlNo;
    }

    public String getYushiyanControlNo() {
        return yushiyanControlNo;
    }

    public void setYushiyanControlNo(String yushiyanControlNo) {
        this.yushiyanControlNo = yushiyanControlNo;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    public String getAmplifiedRecordsNo() {
        return amplifiedRecordsNo;
    }

    public void setAmplifiedRecordsNo(String amplifiedRecordsNo) {
        this.amplifiedRecordsNo = amplifiedRecordsNo;
    }

    public String getWarehouseReceiptNo() {
        return warehouseReceiptNo;
    }

    public void setWarehouseReceiptNo(String warehouseReceiptNo) {
        this.warehouseReceiptNo = warehouseReceiptNo;
    }

    public String getDefaultDnaLabOrgId() {
        return defaultDnaLabOrgId;
    }

    public void setDefaultDnaLabOrgId(String defaultDnaLabOrgId) {
        this.defaultDnaLabOrgId = defaultDnaLabOrgId;
    }

    public String getCommissionSystemUrL() {
        return commissionSystemUrL;
    }

    public void setCommissionSystemUrL(String commissionSystemUrL) {
        this.commissionSystemUrL = commissionSystemUrL;
    }

    public String getCommissionAssesUrL() {
        return commissionAssesUrL;
    }

    public void setCommissionAssesUrL(String commissionAssesUrL) {
        this.commissionAssesUrL = commissionAssesUrL;
    }

    public Long getShTime() {
        return shTime;
    }

    public void setShTime(Long shTime) {
        this.shTime = shTime;
    }

    public String getRoutineExtractionNo() {
        return routineExtractionNo;
    }

    public void setRoutineExtractionNo(String routineExtractionNo) {
        this.routineExtractionNo = routineExtractionNo;
    }

    public String getSeminalExtractionNo() {
        return seminalExtractionNo;
    }

    public void setSeminalExtractionNo(String seminalExtractionNo) {
        this.seminalExtractionNo = seminalExtractionNo;
    }

    public String getElectrophoreticRecordingNo() {
        return electrophoreticRecordingNo;
    }

    public void setElectrophoreticRecordingNo(String electrophoreticRecordingNo) {
        this.electrophoreticRecordingNo = electrophoreticRecordingNo;
    }

    public String getElectrophoreticAutomaticNo() {
        return electrophoreticAutomaticNo;
    }

    public void setElectrophoreticAutomaticNo(String electrophoreticAutomaticNo) {
        this.electrophoreticAutomaticNo = electrophoreticAutomaticNo;
    }

    public String getSamplePhotosRootDir() {
        return samplePhotosRootDir;
    }

    public void setSamplePhotosRootDir(String samplePhotosRootDir) {
        this.samplePhotosRootDir = samplePhotosRootDir;
    }

    public String getLaboratoryRecordCover() {
        return laboratoryRecordCover;
    }

    public void setLaboratoryRecordCover(String laboratoryRecordCover) {
        this.laboratoryRecordCover = laboratoryRecordCover;
    }

    public int getMinSameCount() {
        return minSameCount;
    }

    public void setMinSameCount(int minSameCount) {
        this.minSameCount = minSameCount;
    }

    public int getMinSameCaseCount() {
        return minSameCaseCount;
    }

    public void setMinSameCaseCount(int minSameCaseCount) {
        this.minSameCaseCount = minSameCaseCount;
    }

    public int getMinSameRelationCount() {
        return minSameRelationCount;
    }

    public void setMinSameRelationCount(int minSameRelationCount) {
        this.minSameRelationCount = minSameRelationCount;
    }

    public int getMinSameMixCount() {
        return minSameMixCount;
    }

    public void setMinSameMixCount(int minSameMixCount) {
        this.minSameMixCount = minSameMixCount;
    }

    public int getHalfDiffCount() {
        return halfDiffCount;
    }

    public void setHalfDiffCount(int halfDiffCount) {
        this.halfDiffCount = halfDiffCount;
    }

    public String getInspectCourseUrl() {
        return inspectCourseUrl;
    }

    public void setInspectCourseUrl(String inspectCourseUrl) {
        this.inspectCourseUrl = inspectCourseUrl;
    }

    public int getStrInstoreGeneCountLimit() {
        return strInstoreGeneCountLimit;
    }

    public void setStrInstoreGeneCountLimit(int strInstoreGeneCountLimit) {
        this.strInstoreGeneCountLimit = strInstoreGeneCountLimit;
    }

    public int getYstrInstoreGeneCountLimit() {
        return ystrInstoreGeneCountLimit;
    }

    public void setYstrInstoreGeneCountLimit(int ystrInstoreGeneCountLimit) {
        this.ystrInstoreGeneCountLimit = ystrInstoreGeneCountLimit;
    }

    public int getSameCompareMatchLimit() {
        return sameCompareMatchLimit;
    }

    public void setSameCompareMatchLimit(int sameCompareMatchLimit) {
        this.sameCompareMatchLimit = sameCompareMatchLimit;
    }

    public int getSameCompareDiffLimit() {
        return sameCompareDiffLimit;
    }

    public void setSameCompareDiffLimit(int sameCompareDiffLimit) {
        this.sameCompareDiffLimit = sameCompareDiffLimit;
    }

    public int getRelativeCompareMatchLimit() {
        return relativeCompareMatchLimit;
    }

    public void setRelativeCompareMatchLimit(int relativeCompareMatchLimit) {
        this.relativeCompareMatchLimit = relativeCompareMatchLimit;
    }

    public int getRelativeCompareDiffLimit() {
        return relativeCompareDiffLimit;
    }

    public void setRelativeCompareDiffLimit(int relativeCompareDiffLimit) {
        this.relativeCompareDiffLimit = relativeCompareDiffLimit;
    }

    public int getMixContributorsCountLimit() {
        return mixContributorsCountLimit;
    }

    public void setMixContributorsCountLimit(int mixContributorsCountLimit) {
        this.mixContributorsCountLimit = mixContributorsCountLimit;
    }

    public int getMixCompareMatchLimit() {
        return mixCompareMatchLimit;
    }

    public void setMixCompareMatchLimit(int mixCompareMatchLimit) {
        this.mixCompareMatchLimit = mixCompareMatchLimit;
    }

    public int getMixCompareDiffLimit() {
        return mixCompareDiffLimit;
    }

    public void setMixCompareDiffLimit(int mixCompareDiffLimit) {
        this.mixCompareDiffLimit = mixCompareDiffLimit;
    }

    public String getGetTokenUrl() {
        return getTokenUrl;
    }

    public void setGetTokenUrl(String getTokenUrl) {
        this.getTokenUrl = getTokenUrl;
    }

    public String getUploadIdentifyBookUrl() {
        return uploadIdentifyBookUrl;
    }

    public void setUploadIdentifyBookUrl(String uploadIdentifyBookUrl) {
        this.uploadIdentifyBookUrl = uploadIdentifyBookUrl;
    }

    public String getJumpPageUrl() {
        return jumpPageUrl;
    }

    public void setJumpPageUrl(String jumpPageUrl) {
        this.jumpPageUrl = jumpPageUrl;
    }

    public String getLimsDataStatsUrl() {
        return limsDataStatsUrl;
    }

    public void setLimsDataStatsUrl(String limsDataStatsUrl) {
        this.limsDataStatsUrl = limsDataStatsUrl;
    }

    public String getDnaReagentUrl() {
        return dnaReagentUrl;
    }

    public void setDnaReagentUrl(String dnaReagentUrl) {
        this.dnaReagentUrl = dnaReagentUrl;
    }
}
