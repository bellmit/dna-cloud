package com.bazl.dna.mix.connector.nation.model.po;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
/*
* person_Info
* 人员对象表
* */
public class PersonInfo {

    private String id;

    private String initServerNo;

    private String labId;

    private String consignmentId;

    private String consignOrgCode;

    private String inputCategory;

    private String dbCategory;

    private String subCategory;

    private String personNo;

    private String personName;

    private String generateMode;

    private String alias;

    private String gender;

    @JSONField(name = "matchDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birthDatetime;

    private int age;

    private String idCardNo;

    private String certificateType;

    private String certificateNo;

    private String race;

    private String nationality;

    private String mobilePhone;

    private String homePhone;

    private String email;

    private String educationLevel;

    private String identity;

    private String occupation;

    private String nativePlaceRegionalism;

    private String nativePlaceAddr;

    private String residenceRegionalism;

    private String residenceAddr;

    private String fingerprintNo;

    private String bloodType;

    private String height;

    private String bodilyForm;

    @JSONField(name = "matchDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birthDateFrom;

    @JSONField(name = "matchDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birthDateTo;

    private String roughAge;

    @JSONField(name = "matchDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date roughlyBirthdate;

    private String missingType;

    @JSONField(name = "matchDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date missingTime;

    private String missingPlace;

    private String extrinsicSign;

    private String specialSign;

    private String involvedCaseName;

    private String involvedCaseNo;

    private String caseProperty;

    private String prisonType;

    private String prisonNo;

    private int deathFlag;

    private int deleteFlag;

    private String indexFlag;

    private int transferFlag;

    private String transferUser;

    @JSONField(name = "matchDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date transferDatetime;

    private String dataSource;

    private int dataLevel;

    private String reserve1;

    private String reserve2;

    private String reserve3;

    private String reserve4;

    private String reserve5;

    private String reserve6;

    private String remark;

    private String createUser;

    @JSONField(name = "matchDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createDatetime;

    private String updateUser;

    @JSONField(name = "matchDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date updateDatetime;

    private String personLabel;

    private String abductType;

    private String ifSampling;

    private String samplingDatetime;

    private String samplingRegionalism;

    private String ifTestdna;

    @JSONField(name = "matchDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date testDatetime;

    private String testRegionalism;

    private String unit;

    private String isArrested;

    @JSONField(name = "matchDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date arrestDaettime;

    private String isOffender;

    private String extId;

    private String familyNo;

    private String familyName;

    @JSONField(name = "matchDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date localStoreDatetime;

    @JSONField(name = "matchDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date localCreateDatetime;

    private String hiredate;

    private String dgCaseName;

    private String ifFinger;

    private String dgDataType;

    private String post;

    private String communicationAddress;

    @JSONField(name = "matchDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date guardianDate;

    private String communicationRegionalism;

    private String memory;

    private String otherInfo;

    private String missingPlaceRegionalism;

    private String pStatus;

    private String pMsg;

    @JSONField(name = "matchDatetime",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date pDate;

    private int isInferAge;

    private String va;

    //caseid
    private String caseID;

    private String idCard;

    private String personType;

    private String createPerson;

    private String updatePereson;

    private String sex;

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCaseID() {
        return caseID;
    }

    public void setCaseID(String caseID) {
        this.caseID = caseID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInitServerNo() {
        return initServerNo;
    }

    public void setInitServerNo(String initServerNo) {
        this.initServerNo = initServerNo;
    }

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }

    public String getConsignmentId() {
        return consignmentId;
    }

    public void setConsignmentId(String consignmentId) {
        this.consignmentId = consignmentId;
    }

    public String getConsignOrgCode() {
        return consignOrgCode;
    }

    public void setConsignOrgCode(String consignOrgCode) {
        this.consignOrgCode = consignOrgCode;
    }

    public String getInputCategory() {
        return inputCategory;
    }

    public void setInputCategory(String inputCategory) {
        this.inputCategory = inputCategory;
    }

    public String getDbCategory() {
        return dbCategory;
    }

    public void setDbCategory(String dbCategory) {
        this.dbCategory = dbCategory;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getPersonNo() {
        return personNo;
    }

    public void setPersonNo(String personNo) {
        this.personNo = personNo;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getGenerateMode() {
        return generateMode;
    }

    public void setGenerateMode(String generateMode) {
        this.generateMode = generateMode;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDatetime() {
        return birthDatetime;
    }

    public void setBirthDatetime(Date birthDatetime) {
        this.birthDatetime = birthDatetime;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(String certificateType) {
        this.certificateType = certificateType;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getNativePlaceRegionalism() {
        return nativePlaceRegionalism;
    }

    public void setNativePlaceRegionalism(String nativePlaceRegionalism) {
        this.nativePlaceRegionalism = nativePlaceRegionalism;
    }

    public String getNativePlaceAddr() {
        return nativePlaceAddr;
    }

    public void setNativePlaceAddr(String nativePlaceAddr) {
        this.nativePlaceAddr = nativePlaceAddr;
    }

    public String getResidenceRegionalism() {
        return residenceRegionalism;
    }

    public void setResidenceRegionalism(String residenceRegionalism) {
        this.residenceRegionalism = residenceRegionalism;
    }

    public String getResidenceAddr() {
        return residenceAddr;
    }

    public void setResidenceAddr(String residenceAddr) {
        this.residenceAddr = residenceAddr;
    }

    public String getFingerprintNo() {
        return fingerprintNo;
    }

    public void setFingerprintNo(String fingerprintNo) {
        this.fingerprintNo = fingerprintNo;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getBodilyForm() {
        return bodilyForm;
    }

    public void setBodilyForm(String bodilyForm) {
        this.bodilyForm = bodilyForm;
    }

    public Date getBirthDateFrom() {
        return birthDateFrom;
    }

    public void setBirthDateFrom(Date birthDateFrom) {
        this.birthDateFrom = birthDateFrom;
    }

    public Date getBirthDateTo() {
        return birthDateTo;
    }

    public void setBirthDateTo(Date birthDateTo) {
        this.birthDateTo = birthDateTo;
    }

    public String getRoughAge() {
        return roughAge;
    }

    public void setRoughAge(String roughAge) {
        this.roughAge = roughAge;
    }

    public Date getRoughlyBirthdate() {
        return roughlyBirthdate;
    }

    public void setRoughlyBirthdate(Date roughlyBirthdate) {
        this.roughlyBirthdate = roughlyBirthdate;
    }

    public String getMissingType() {
        return missingType;
    }

    public void setMissingType(String missingType) {
        this.missingType = missingType;
    }

    public Date getMissingTime() {
        return missingTime;
    }

    public void setMissingTime(Date missingTime) {
        this.missingTime = missingTime;
    }

    public String getMissingPlace() {
        return missingPlace;
    }

    public void setMissingPlace(String missingPlace) {
        this.missingPlace = missingPlace;
    }

    public String getExtrinsicSign() {
        return extrinsicSign;
    }

    public void setExtrinsicSign(String extrinsicSign) {
        this.extrinsicSign = extrinsicSign;
    }

    public String getSpecialSign() {
        return specialSign;
    }

    public void setSpecialSign(String specialSign) {
        this.specialSign = specialSign;
    }

    public String getInvolvedCaseName() {
        return involvedCaseName;
    }

    public void setInvolvedCaseName(String involvedCaseName) {
        this.involvedCaseName = involvedCaseName;
    }

    public String getInvolvedCaseNo() {
        return involvedCaseNo;
    }

    public void setInvolvedCaseNo(String involvedCaseNo) {
        this.involvedCaseNo = involvedCaseNo;
    }

    public String getCaseProperty() {
        return caseProperty;
    }

    public void setCaseProperty(String caseProperty) {
        this.caseProperty = caseProperty;
    }

    public String getPrisonType() {
        return prisonType;
    }

    public void setPrisonType(String prisonType) {
        this.prisonType = prisonType;
    }

    public String getPrisonNo() {
        return prisonNo;
    }

    public void setPrisonNo(String prisonNo) {
        this.prisonNo = prisonNo;
    }

    public int getDeathFlag() {
        return deathFlag;
    }

    public void setDeathFlag(int deathFlag) {
        this.deathFlag = deathFlag;
    }

    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getIndexFlag() {
        return indexFlag;
    }

    public void setIndexFlag(String indexFlag) {
        this.indexFlag = indexFlag;
    }

    public int getTransferFlag() {
        return transferFlag;
    }

    public void setTransferFlag(int transferFlag) {
        this.transferFlag = transferFlag;
    }

    public String getTransferUser() {
        return transferUser;
    }

    public void setTransferUser(String transferUser) {
        this.transferUser = transferUser;
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
        this.dataSource = dataSource;
    }

    public int getDataLevel() {
        return dataLevel;
    }

    public void setDataLevel(int dataLevel) {
        this.dataLevel = dataLevel;
    }

    public String getReserve1() {
        return reserve1;
    }

    public void setReserve1(String reserve1) {
        this.reserve1 = reserve1;
    }

    public String getReserve2() {
        return reserve2;
    }

    public void setReserve2(String reserve2) {
        this.reserve2 = reserve2;
    }

    public String getReserve3() {
        return reserve3;
    }

    public void setReserve3(String reserve3) {
        this.reserve3 = reserve3;
    }

    public String getReserve4() {
        return reserve4;
    }

    public void setReserve4(String reserve4) {
        this.reserve4 = reserve4;
    }

    public String getReserve5() {
        return reserve5;
    }

    public void setReserve5(String reserve5) {
        this.reserve5 = reserve5;
    }

    public String getReserve6() {
        return reserve6;
    }

    public void setReserve6(String reserve6) {
        this.reserve6 = reserve6;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
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
        this.updateUser = updateUser;
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public String getPersonLabel() {
        return personLabel;
    }

    public void setPersonLabel(String personLabel) {
        this.personLabel = personLabel;
    }

    public String getAbductType() {
        return abductType;
    }

    public void setAbductType(String abductType) {
        this.abductType = abductType;
    }

    public String getIfSampling() {
        return ifSampling;
    }

    public void setIfSampling(String ifSampling) {
        this.ifSampling = ifSampling;
    }

    public String getSamplingDatetime() {
        return samplingDatetime;
    }

    public void setSamplingDatetime(String samplingDatetime) {
        this.samplingDatetime = samplingDatetime;
    }

    public String getSamplingRegionalism() {
        return samplingRegionalism;
    }

    public void setSamplingRegionalism(String samplingRegionalism) {
        this.samplingRegionalism = samplingRegionalism;
    }

    public String getIfTestdna() {
        return ifTestdna;
    }

    public void setIfTestdna(String ifTestdna) {
        this.ifTestdna = ifTestdna;
    }

    public Date getTestDatetime() {
        return testDatetime;
    }

    public void setTestDatetime(Date testDatetime) {
        this.testDatetime = testDatetime;
    }

    public String getTestRegionalism() {
        return testRegionalism;
    }

    public void setTestRegionalism(String testRegionalism) {
        this.testRegionalism = testRegionalism;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getIsArrested() {
        return isArrested;
    }

    public void setIsArrested(String isArrested) {
        this.isArrested = isArrested;
    }

    public Date getArrestDaettime() {
        return arrestDaettime;
    }

    public void setArrestDaettime(Date arrestDaettime) {
        this.arrestDaettime = arrestDaettime;
    }

    public String getIsOffender() {
        return isOffender;
    }

    public void setIsOffender(String isOffender) {
        this.isOffender = isOffender;
    }

    public String getExtId() {
        return extId;
    }

    public void setExtId(String extId) {
        this.extId = extId;
    }

    public String getFamilyNo() {
        return familyNo;
    }

    public void setFamilyNo(String familyNo) {
        this.familyNo = familyNo;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public Date getLocalStoreDatetime() {
        return localStoreDatetime;
    }

    public void setLocalStoreDatetime(Date localStoreDatetime) {
        this.localStoreDatetime = localStoreDatetime;
    }

    public Date getLocalCreateDatetime() {
        return localCreateDatetime;
    }

    public void setLocalCreateDatetime(Date localCreateDatetime) {
        this.localCreateDatetime = localCreateDatetime;
    }

    public String getHiredate() {
        return hiredate;
    }

    public void setHiredate(String hiredate) {
        this.hiredate = hiredate;
    }

    public String getDgCaseName() {
        return dgCaseName;
    }

    public void setDgCaseName(String dgCaseName) {
        this.dgCaseName = dgCaseName;
    }

    public String getIfFinger() {
        return ifFinger;
    }

    public void setIfFinger(String ifFinger) {
        this.ifFinger = ifFinger;
    }

    public String getDgDataType() {
        return dgDataType;
    }

    public void setDgDataType(String dgDataType) {
        this.dgDataType = dgDataType;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getCommunicationAddress() {
        return communicationAddress;
    }

    public void setCommunicationAddress(String communicationAddress) {
        this.communicationAddress = communicationAddress;
    }

    public Date getGuardianDate() {
        return guardianDate;
    }

    public void setGuardianDate(Date guardianDate) {
        this.guardianDate = guardianDate;
    }

    public String getCommunicationRegionalism() {
        return communicationRegionalism;
    }

    public void setCommunicationRegionalism(String communicationRegionalism) {
        this.communicationRegionalism = communicationRegionalism;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }

    public String getMissingPlaceRegionalism() {
        return missingPlaceRegionalism;
    }

    public void setMissingPlaceRegionalism(String missingPlaceRegionalism) {
        this.missingPlaceRegionalism = missingPlaceRegionalism;
    }

    public String getpStatus() {
        return pStatus;
    }

    public void setpStatus(String pStatus) {
        this.pStatus = pStatus;
    }

    public String getpMsg() {
        return pMsg;
    }

    public void setpMsg(String pMsg) {
        this.pMsg = pMsg;
    }

    public Date getpDate() {
        return pDate;
    }

    public void setpDate(Date pDate) {
        this.pDate = pDate;
    }

    public int getIsInferAge() {
        return isInferAge;
    }

    public void setIsInferAge(int isInferAge) {
        this.isInferAge = isInferAge;
    }

    public String getVa() {
        return va;
    }

    public void setVa(String va) {
        this.va = va;
    }

}