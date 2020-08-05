package com.bazl.dna.lims.core.model.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class LaboratoryInfo implements Serializable {
    private String id;

    private String labName;

    private BigDecimal labTotalCase;

    private String labTemperature;

    private String labHumidity;

    private String labSetupTime;

    private BigDecimal labPersonnel;

    private String labIntroduction;

    private String orgId;

    private String ipSegment;

    private String dnaLabName;

    private List<String> nocaseNumStr=new ArrayList<>();
    private List<String> caseNumberStr=new ArrayList<>();


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName == null ? null : labName.trim();
    }

    public BigDecimal getLabTotalCase() {
        return labTotalCase;
    }

    public void setLabTotalCase(BigDecimal labTotalCase) {
        this.labTotalCase = labTotalCase;
    }

    public String getLabTemperature() {
        return labTemperature;
    }

    public void setLabTemperature(String labTemperature) {
        this.labTemperature = labTemperature == null ? null : labTemperature.trim();
    }

    public String getLabHumidity() {
        return labHumidity;
    }

    public void setLabHumidity(String labHumidity) {
        this.labHumidity = labHumidity == null ? null : labHumidity.trim();
    }

    public String getLabSetupTime() {
        return labSetupTime;
    }

    public void setLabSetupTime(String labSetupTime) {
        this.labSetupTime = labSetupTime == null ? null : labSetupTime.trim();
    }

    public BigDecimal getLabPersonnel() {
        return labPersonnel;
    }

    public void setLabPersonnel(BigDecimal labPersonnel) {
        this.labPersonnel = labPersonnel;
    }

    public String getLabIntroduction() {
        return labIntroduction;
    }

    public void setLabIntroduction(String labIntroduction) {
        this.labIntroduction = labIntroduction == null ? null : labIntroduction.trim();
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getIpSegment() {
        return ipSegment;
    }

    public void setIpSegment(String ipSegment) {
        this.ipSegment = ipSegment == null ? null : ipSegment.trim();
    }

    public List<String> getNocaseNumStr() {
        return nocaseNumStr;
    }

    public void setNocaseNumStr(List<String> nocaseNumStr) {
        this.nocaseNumStr = nocaseNumStr;
    }

    public List<String> getCaseNumberStr() {
        return caseNumberStr;
    }

    public void setCaseNumberStr(List<String> caseNumberStr) {
        this.caseNumberStr = caseNumberStr;
    }

    public String getDnaLabName() {
        return dnaLabName;
    }

    public void setDnaLabName(String dnaLabName) {
        this.dnaLabName = dnaLabName;
    }
}