package com.bazl.dna.dpznsy.model.vo;

import com.bazl.dna.dpznsy.model.po.CaseInfo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2020/2/14.
 */
public class CaseInfoVo extends AbstractBaseVo<CaseInfo> implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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

    /**
     * 检材类型
     */
    private String sampleType;

    /**
     * 检材类型名称
     */
    private String sampleTypeName;

    /**
     * 数量单位
     */
    private String measurementUnit;

    /**
     * 数量单位名称
     */
    private String measurementUnitName;

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

    /**
     * 初检结果
     */
    private String firstResult;

    /**
     * 性状
     */
    private String sampleCharacter;

    /**
     * SAMPLE_CHARACTER_NAME
     */
    private String sampleCharacterName;

    /**
     * 送检数量
     */
    private Double acceptedAmount;

    /**
     * 姓名
     */
    private String memberName;

    /**
     * 性别
     */
    private String gender;

    /**
     * 性别名称
     */
    private String genderName;

    /**
     * 籍贯
     */
    private String birthplace;

    /**
     * 出生日期
     */
    private Date birthdate;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 国籍
     */
    private String country;

    /**
     * 国籍名称
     */
    private String countryName;

    /**
     * 民族
     */
    private String nation;

    /**
     * 民族名称
     */
    private String nationName;

    /**
     * 身份
     */
    private String identity;

    /**
     * 身份名称
     */
    private String identityName;

    /**
     * 身份证
     */
    private String idCard;

    /**
     * 单位
     */
    private String company;

    /**
     * 职务
     */
    private String job;

    /**
     * 地址
     */
    private String address;

    /**
     * 未提供身份证原因
     */
    private String noIdReason;

    /**
     * 声明
     */
    private String declare;

    /**
     * remark
     */
    private String remark;

    private String packing;

    private String packingName;

    private int page;

    private String firstResultName;

    private String sampleId;

    private BigDecimal amount;

    private String sjCount;

    private String testResult;

    public String getTestResult() {
        return testResult;
    }

    public void setTestResult(String testResult) {
        this.testResult = testResult;
    }

    public String getSjCount() {
        return sjCount;
    }

    public void setSjCount(String sjCount) {
        this.sjCount = sjCount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    public String getFirstResultName() {
        return firstResultName;
    }

    public void setFirstResultName(String firstResultName) {
        this.firstResultName = firstResultName;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public String getSampleNameName() {
        return sampleNameName;
    }

    public void setSampleNameName(String sampleNameName) {
        this.sampleNameName = sampleNameName;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public String getSampleTypeName() {
        return sampleTypeName;
    }

    public void setSampleTypeName(String sampleTypeName) {
        this.sampleTypeName = sampleTypeName;
    }

    public String getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(String measurementUnit) {
        this.measurementUnit = measurementUnit;
    }

    public String getMeasurementUnitName() {
        return measurementUnitName;
    }

    public void setMeasurementUnitName(String measurementUnitName) {
        this.measurementUnitName = measurementUnitName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getFirstResult() {
        return firstResult;
    }

    public void setFirstResult(String firstResult) {
        this.firstResult = firstResult;
    }

    public String getSampleCharacter() {
        return sampleCharacter;
    }

    public void setSampleCharacter(String sampleCharacter) {
        this.sampleCharacter = sampleCharacter;
    }

    public String getSampleCharacterName() {
        return sampleCharacterName;
    }

    public void setSampleCharacterName(String sampleCharacterName) {
        this.sampleCharacterName = sampleCharacterName;
    }

    public Double getAcceptedAmount() {
        return acceptedAmount;
    }

    public void setAcceptedAmount(Double acceptedAmount) {
        this.acceptedAmount = acceptedAmount;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGenderName() {
        return genderName;
    }

    public void setGenderName(String genderName) {
        this.genderName = genderName;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getNationName() {
        return nationName;
    }

    public void setNationName(String nationName) {
        this.nationName = nationName;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getIdentityName() {
        return identityName;
    }

    public void setIdentityName(String identityName) {
        this.identityName = identityName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNoIdReason() {
        return noIdReason;
    }

    public void setNoIdReason(String noIdReason) {
        this.noIdReason = noIdReason;
    }

    public String getDeclare() {
        return declare;
    }

    public void setDeclare(String declare) {
        this.declare = declare;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPacking() {
        return packing;
    }

    public void setPacking(String packing) {
        this.packing = packing;
    }

    public String getPackingName() {
        return packingName;
    }

    public void setPackingName(String packingName) {
        this.packingName = packingName;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
