package com.bazl.dna.mix.model;

import com.bazl.dna.mix.model.po.MixedSampleGene;

public class MixedSplitedSampleModel {

    //混合ID
    private String mixedSampleGeneId;
    //样本ID
    private String sampleId;
    //样本编号
    private String sampleNo;
    //样本名称
    private String sampleName;
    //试剂盒名称
    private String reagentName;
    //电泳板号
    private String boardBarcode;
    //基因信息
    private String geneInfo;
    //基因图谱
    private String genePicture;
    //性别
    private String sex;
    //地区编号
    private String district;
    //地区名称
    private String districtName;
    //地区服务器编号
    private String serveNo;
    //人员编号
    private String personType;
    //人员名称
    private String personTypeName;
    //匹配下线
    private int mixsameCount;
    //容差
    private String condition;
    //分型类型  混合还是拆分
    private String queueType;
    //混合人数 CONTRIBUTOR_COUNT
    private String contributorCount;
    //混合基因实体
    private MixedSampleGene mixedSampleGene;

    public String getServeNo() {
        return serveNo;
    }

    public void setServeNo(String serveNo) {
        this.serveNo = serveNo;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public MixedSampleGene getMixedSampleGene() {
        return mixedSampleGene;
    }

    public void setMixedSampleGene(MixedSampleGene mixedSampleGene) {
        this.mixedSampleGene = mixedSampleGene;
    }

    public String getContributorCount() {
        return contributorCount;
    }

    public void setContributorCount(String contributorCount) {
        this.contributorCount = contributorCount;
    }

    public String getSampleNo() {
        return sampleNo;
    }

    public void setSampleNo(String sampleNo) {
        this.sampleNo = sampleNo;
    }

    public String getQueueType() {
        return queueType;
    }

    public void setQueueType(String queueType) {
        this.queueType = queueType;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
    }

    public String getPersonTypeName() {
        return personTypeName;
    }

    public void setPersonTypeName(String personTypeName) {
        this.personTypeName = personTypeName;
    }

    public int getMixsameCount() {
        return mixsameCount;
    }

    public void setMixsameCount(int mixsameCount) {
        this.mixsameCount = mixsameCount;
    }

    public String getMixedSampleGeneId() {
        return mixedSampleGeneId;
    }

    public void setMixedSampleGeneId(String mixedSampleGeneId) {
        this.mixedSampleGeneId = mixedSampleGeneId;
    }

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
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

    public String getGeneInfo() {
        return geneInfo;
    }

    public void setGeneInfo(String geneInfo) {
        this.geneInfo = geneInfo;
    }

    public String getGenePicture() {
        return genePicture;
    }

    public void setGenePicture(String genePicture) {
        this.genePicture = genePicture;
    }
}
