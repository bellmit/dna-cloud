package com.bazl.dna.mix.compare.service.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class CompareQueue implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String mixedSampleId;

    private String sampleNo;

    private String queueType;

    private String status;

    private String createPerson;

    private Date createDatetime;

    private String updatePereson;

    private Date updateDatetime;

    private String mixsameCount;

    private String sex;

    private String condition;

    private String personType;

    private String district;

    private String geneInfo;

    private String queueFlag;

    private String genePicture;

    private String source;

    private String personTypeName;

    private String districtName;

    private String targetCount;

    private String diqu;
    private String renyuan;

    private Map<String, Object> geneInfos;

    /* 业务字段  时间*/
    private String datetime;

    /* 业务字段  ids*/
    private String ids;

    private String dataType;

    /* 业务字段  geneImagePath*/
    private String  geneImagePath;

    /* 业务字段 混合基因实体*/
    private MixedSampleGene mixedSampleGene;

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public MixedSampleGene getMixedSampleGene() {
        return mixedSampleGene;
    }

    public void setMixedSampleGene(MixedSampleGene mixedSampleGene) {
        this.mixedSampleGene = mixedSampleGene;
    }

    public String getGeneImagePath() {
        return geneImagePath;
    }

    public void setGeneImagePath(String geneImagePath) {
        this.geneImagePath = geneImagePath;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public Map<String, Object> getGeneInfos() {
        return geneInfos;
    }

    public void setGeneInfos(Map<String, Object> geneInfos) {
        this.geneInfos = geneInfos;
    }

    public String getRenyuan() {
        return renyuan;
    }

    public void setRenyuan(String renyuan) {
        this.renyuan = renyuan;
    }

    public String getDiqu() {
        return diqu;
    }

    public void setDiqu(String diqu) {
        this.diqu = diqu;
    }

    public String getTargetCount() {
        return targetCount;
    }

    public void setTargetCount(String targetCount) {
        this.targetCount = targetCount;
    }
    /* 业务字段  比中条数 */
    private String sameCount;

    /* 业务字段  拆分数量 */
    private String splitCount;

    public String getPersonTypeName() {
        return personTypeName;
    }

    public void setPersonTypeName(String personTypeName) {
        this.personTypeName = personTypeName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }


    public String getSplitCount() {
        return splitCount;
    }

    public void setSplitCount(String splitCount) {
        this.splitCount = splitCount;
    }

    public String getSameCount() {
        return sameCount;
    }

    public void setSameCount(String sameCount) {
        this.sameCount = sameCount;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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

    public String getMixedSampleId() {
        return mixedSampleId;
    }

    public void setMixedSampleId(String mixedSampleId) {
        this.mixedSampleId = mixedSampleId == null ? null : mixedSampleId.trim();
    }

    public String getSampleNo() {
        return sampleNo;
    }

    public void setSampleNo(String sampleNo) {
        this.sampleNo = sampleNo == null ? null : sampleNo.trim();
    }

    public String getQueueType() {
        return queueType;
    }

    public void setQueueType(String queueType) {
        this.queueType = queueType == null ? null : queueType.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(String createPerson) {
        this.createPerson = createPerson == null ? null : createPerson.trim();
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
        this.updatePereson = updatePereson == null ? null : updatePereson.trim();
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public String getMixsameCount() {
        return mixsameCount;
    }

    public void setMixsameCount(String mixsameCount) {
        this.mixsameCount = mixsameCount;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition == null ? null : condition.trim();
    }

    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType == null ? null : personType.trim();
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district == null ? null : district.trim();
    }

    public String getGeneInfo() {
        return geneInfo;
    }

    public void setGeneInfo(String geneInfo) {
        this.geneInfo = geneInfo == null ? null : geneInfo.trim();
    }

    public String getQueueFlag() {
        return queueFlag;
    }

    public void setQueueFlag(String queueFlag) {
        this.queueFlag = queueFlag == null ? null : queueFlag.trim();
    }
}