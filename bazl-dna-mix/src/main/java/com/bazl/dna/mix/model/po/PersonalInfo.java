package com.bazl.dna.mix.model.po;

import java.io.Serializable;
import java.util.Date;

public class PersonalInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
    private String personalId;

    private String picture;

    private String fullName;

    private String gender;

    private Date bornDate;

    private String idCard;

    private String level;

    private String race;

    private String politicalOutlook;

    private String residenceAddress;

    private String position;

    private String policeNo;

    private String paperworkType;

    private String paperworkNo;

    private String phoneNumber;

    private String orgId;

    private String qualificationLevel;

    private Date startWorkingHours;

    private Date alarmTime;

    private String highestTitle;

    private Date titleGainTime;

    private Date titleValidity;

    private String titlePicture;

    private String workNow;

    private String workYears;

    private String majorAdoptYears;

    private String majorAdoptScore;

    private String appraiserAptitude;

    private String orgPhone;

    private String orgName;

    private String personalEducation;

    private Date educationGainTime;

    private String personalMajor;

    private String personalDegree;

    private Date degreeGainTime;

    private String degreeMajor;

    private String personalEducationSchool;

    private String personalDegreeSchool;

    private String noPosition;

    private String qualification;

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId == null ? null : personalId.trim();
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture == null ? null : picture.trim();
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName == null ? null : fullName.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public Date getBornDate() {
        return bornDate;
    }

    public void setBornDate(Date bornDate) {
        this.bornDate = bornDate;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race == null ? null : race.trim();
    }

    public String getPoliticalOutlook() {
        return politicalOutlook;
    }

    public void setPoliticalOutlook(String politicalOutlook) {
        this.politicalOutlook = politicalOutlook == null ? null : politicalOutlook.trim();
    }

    public String getResidenceAddress() {
        return residenceAddress;
    }

    public void setResidenceAddress(String residenceAddress) {
        this.residenceAddress = residenceAddress == null ? null : residenceAddress.trim();
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    public String getPoliceNo() {
        return policeNo;
    }

    public void setPoliceNo(String policeNo) {
        this.policeNo = policeNo == null ? null : policeNo.trim();
    }

    public String getPaperworkType() {
        return paperworkType;
    }

    public void setPaperworkType(String paperworkType) {
        this.paperworkType = paperworkType == null ? null : paperworkType.trim();
    }

    public String getPaperworkNo() {
        return paperworkNo;
    }

    public void setPaperworkNo(String paperworkNo) {
        this.paperworkNo = paperworkNo == null ? null : paperworkNo.trim();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getQualificationLevel() {
        return qualificationLevel;
    }

    public void setQualificationLevel(String qualificationLevel) {
        this.qualificationLevel = qualificationLevel == null ? null : qualificationLevel.trim();
    }

    public Date getStartWorkingHours() {
        return startWorkingHours;
    }

    public void setStartWorkingHours(Date startWorkingHours) {
        this.startWorkingHours = startWorkingHours;
    }

    public Date getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(Date alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getHighestTitle() {
        return highestTitle;
    }

    public void setHighestTitle(String highestTitle) {
        this.highestTitle = highestTitle == null ? null : highestTitle.trim();
    }

    public Date getTitleGainTime() {
        return titleGainTime;
    }

    public void setTitleGainTime(Date titleGainTime) {
        this.titleGainTime = titleGainTime;
    }

    public Date getTitleValidity() {
        return titleValidity;
    }

    public void setTitleValidity(Date titleValidity) {
        this.titleValidity = titleValidity;
    }

    public String getTitlePicture() {
        return titlePicture;
    }

    public void setTitlePicture(String titlePicture) {
        this.titlePicture = titlePicture == null ? null : titlePicture.trim();
    }

    public String getWorkNow() {
        return workNow;
    }

    public void setWorkNow(String workNow) {
        this.workNow = workNow == null ? null : workNow.trim();
    }

    public String getWorkYears() {
        return workYears;
    }

    public void setWorkYears(String workYears) {
        this.workYears = workYears == null ? null : workYears.trim();
    }

    public String getMajorAdoptYears() {
        return majorAdoptYears;
    }

    public void setMajorAdoptYears(String majorAdoptYears) {
        this.majorAdoptYears = majorAdoptYears == null ? null : majorAdoptYears.trim();
    }

    public String getMajorAdoptScore() {
        return majorAdoptScore;
    }

    public void setMajorAdoptScore(String majorAdoptScore) {
        this.majorAdoptScore = majorAdoptScore == null ? null : majorAdoptScore.trim();
    }

    public String getAppraiserAptitude() {
        return appraiserAptitude;
    }

    public void setAppraiserAptitude(String appraiserAptitude) {
        this.appraiserAptitude = appraiserAptitude == null ? null : appraiserAptitude.trim();
    }

    public String getOrgPhone() {
        return orgPhone;
    }

    public void setOrgPhone(String orgPhone) {
        this.orgPhone = orgPhone == null ? null : orgPhone.trim();
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getPersonalEducation() {
        return personalEducation;
    }

    public void setPersonalEducation(String personalEducation) {
        this.personalEducation = personalEducation == null ? null : personalEducation.trim();
    }

    public Date getEducationGainTime() {
        return educationGainTime;
    }

    public void setEducationGainTime(Date educationGainTime) {
        this.educationGainTime = educationGainTime;
    }

    public String getPersonalMajor() {
        return personalMajor;
    }

    public void setPersonalMajor(String personalMajor) {
        this.personalMajor = personalMajor == null ? null : personalMajor.trim();
    }

    public String getPersonalDegree() {
        return personalDegree;
    }

    public void setPersonalDegree(String personalDegree) {
        this.personalDegree = personalDegree == null ? null : personalDegree.trim();
    }

    public Date getDegreeGainTime() {
        return degreeGainTime;
    }

    public void setDegreeGainTime(Date degreeGainTime) {
        this.degreeGainTime = degreeGainTime;
    }

    public String getDegreeMajor() {
        return degreeMajor;
    }

    public void setDegreeMajor(String degreeMajor) {
        this.degreeMajor = degreeMajor == null ? null : degreeMajor.trim();
    }

    public String getPersonalEducationSchool() {
        return personalEducationSchool;
    }

    public void setPersonalEducationSchool(String personalEducationSchool) {
        this.personalEducationSchool = personalEducationSchool == null ? null : personalEducationSchool.trim();
    }

    public String getPersonalDegreeSchool() {
        return personalDegreeSchool;
    }

    public void setPersonalDegreeSchool(String personalDegreeSchool) {
        this.personalDegreeSchool = personalDegreeSchool == null ? null : personalDegreeSchool.trim();
    }

    public String getNoPosition() {
        return noPosition;
    }

    public void setNoPosition(String noPosition) {
        this.noPosition = noPosition == null ? null : noPosition.trim();
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification == null ? null : qualification.trim();
    }
}