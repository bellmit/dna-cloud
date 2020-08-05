package com.bazl.dna.dpznsy.model.po;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.bazl.dna.util.JsonDatetimeSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * consignment_info
 * 委托信息表
 */
public class SampleInfo {
    private String sampleId;

    private String caseBaseId;

    private String consignationId;

    private String major;

    /**
     * 检材名称
     */
    private String sampleName;

    /**
     * SAMPLE_NAME_NAME
     */
    private String sampleNameName;

    /**
     * 检材编号
     */
    private String barcode;

    private String status;

    private String drugUsed;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date lastUsedDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date takeUrineDate;

    private String location;

    private String isRecheck;

    /**
     * 检材类型
     */
    private String sampleType;

    /**
     * 检材类型名称
     */
    private String sampleTypeName;

    private Double amount;

    /**
     * 数量单位
     */
    private String measurementUnit;

    /**
     * 数量单位名称
     */
    private String measurementUnitName;

    private String otherMeasurementUnit;

    /**
     * 颜色
     */
    private String color;

    /**
     * 颜色名称
     */
    private String colorName;

    /**
     * 份数
     */
    private Integer count;

    private String packing;

    private String packingName;

    /**
     * 初检结果
     */
    private String firstResult;

    private String firstResultName;

    /**
     * 性状
     */
    private String sampleCharacter;

    /**
     * SAMPLE_CHARACTER_NAME
     */
    private String sampleCharacterName;

    private String hospital;

    private String testItemsName;

    private String testItems;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date createAt;

    private String creatorId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+08")
    @JsonSerialize(using = JsonDatetimeSerializer.class)
    private Date updateAt;

    private String updatorId;

    private Integer isDelete;

    private String remark;

    private String testItemsOtherName;

    private String isAccept;

    /**
     * 送检数量
     */
    private Double acceptedAmount;

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId == null ? null : sampleId.trim();
    }

    public String getCaseBaseId() {
        return caseBaseId;
    }

    public void setCaseBaseId(String caseBaseId) {
        this.caseBaseId = caseBaseId == null ? null : caseBaseId.trim();
    }

    public String getConsignationId() {
        return consignationId;
    }

    public void setConsignationId(String consignationId) {
        this.consignationId = consignationId == null ? null : consignationId.trim();
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major == null ? null : major.trim();
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName == null ? null : sampleName.trim();
    }

    public String getSampleNameName() {
        return sampleNameName;
    }

    public void setSampleNameName(String sampleNameName) {
        this.sampleNameName = sampleNameName == null ? null : sampleNameName.trim();
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode == null ? null : barcode.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getDrugUsed() {
        return drugUsed;
    }

    public void setDrugUsed(String drugUsed) {
        this.drugUsed = drugUsed == null ? null : drugUsed.trim();
    }

    public Date getLastUsedDate() {
        return lastUsedDate;
    }

    public void setLastUsedDate(Date lastUsedDate) {
        this.lastUsedDate = lastUsedDate;
    }

    public Date getTakeUrineDate() {
        return takeUrineDate;
    }

    public void setTakeUrineDate(Date takeUrineDate) {
        this.takeUrineDate = takeUrineDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public String getIsRecheck() {
        return isRecheck;
    }

    public void setIsRecheck(String isRecheck) {
        this.isRecheck = isRecheck == null ? null : isRecheck.trim();
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType == null ? null : sampleType.trim();
    }

    public String getSampleTypeName() {
        return sampleTypeName;
    }

    public void setSampleTypeName(String sampleTypeName) {
        this.sampleTypeName = sampleTypeName == null ? null : sampleTypeName.trim();
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(String measurementUnit) {
        this.measurementUnit = measurementUnit == null ? null : measurementUnit.trim();
    }

    public String getMeasurementUnitName() {
        return measurementUnitName;
    }

    public void setMeasurementUnitName(String measurementUnitName) {
        this.measurementUnitName = measurementUnitName == null ? null : measurementUnitName.trim();
    }

    public String getOtherMeasurementUnit() {
        return otherMeasurementUnit;
    }

    public void setOtherMeasurementUnit(String otherMeasurementUnit) {
        this.otherMeasurementUnit = otherMeasurementUnit == null ? null : otherMeasurementUnit.trim();
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color == null ? null : color.trim();
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName == null ? null : colorName.trim();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getPacking() {
        return packing;
    }

    public void setPacking(String packing) {
        this.packing = packing == null ? null : packing.trim();
    }

    public String getPackingName() {
        return packingName;
    }

    public void setPackingName(String packingName) {
        this.packingName = packingName == null ? null : packingName.trim();
    }

    public String getFirstResult() {
        return firstResult;
    }

    public void setFirstResult(String firstResult) {
        this.firstResult = firstResult == null ? null : firstResult.trim();
    }

    public String getFirstResultName() {
        return firstResultName;
    }

    public void setFirstResultName(String firstResultName) {
        this.firstResultName = firstResultName == null ? null : firstResultName.trim();
    }

    public String getSampleCharacter() {
        return sampleCharacter;
    }

    public void setSampleCharacter(String sampleCharacter) {
        this.sampleCharacter = sampleCharacter == null ? null : sampleCharacter.trim();
    }

    public String getSampleCharacterName() {
        return sampleCharacterName;
    }

    public void setSampleCharacterName(String sampleCharacterName) {
        this.sampleCharacterName = sampleCharacterName == null ? null : sampleCharacterName.trim();
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital == null ? null : hospital.trim();
    }

    public String getTestItemsName() {
        return testItemsName;
    }

    public void setTestItemsName(String testItemsName) {
        this.testItemsName = testItemsName == null ? null : testItemsName.trim();
    }

    public String getTestItems() {
        return testItems;
    }

    public void setTestItems(String testItems) {
        this.testItems = testItems == null ? null : testItems.trim();
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId == null ? null : creatorId.trim();
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public String getUpdatorId() {
        return updatorId;
    }

    public void setUpdatorId(String updatorId) {
        this.updatorId = updatorId == null ? null : updatorId.trim();
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getTestItemsOtherName() {
        return testItemsOtherName;
    }

    public void setTestItemsOtherName(String testItemsOtherName) {
        this.testItemsOtherName = testItemsOtherName == null ? null : testItemsOtherName.trim();
    }

    public String getIsAccept() {
        return isAccept;
    }

    public void setIsAccept(String isAccept) {
        this.isAccept = isAccept == null ? null : isAccept.trim();
    }

    public Double getAcceptedAmount() {
        return acceptedAmount;
    }

    public void setAcceptedAmount(Double acceptedAmount) {
        this.acceptedAmount = acceptedAmount;
    }
}