package com.bazl.dna.database.nation.converter.task;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by lizhihua on 2020-01-29.
 */
@Component
@Configuration
@ConfigurationProperties(prefix = "task", ignoreInvalidFields = true)
public class ConverterConfigs {

    /**
     * 是否开启案件数据转换
     */
    private boolean caseConvertOpen;
    /**
     * 是否开启未知身份人员数据转换
     */
    private boolean unknownPersonConvertOpen;
    /**
     * 是否开启失踪人员数据转换
     */
    private boolean missingPersonConvertOpen;
    /**
     * 是否开启建库人员数据转换
     */
    private boolean criminalPersonConvertOpen;
    /**
     * 是否开启灾难人员数据转换
     */
    private boolean disasterPersonConvertOpen;
    /**
     * 是否开启质控人员数据转换
     */
    private boolean qualityPersonConvertOpen;
    /**
     * 是否开启基础库人员数据转换
     */
    private boolean basicPersonConvertOpen;
    /**
     * 是否开启打拐儿童数据转换
     */
    private boolean abductionChildConvertOpen;
    /**
     * 是否开启打拐父母数据转换
     */
    private boolean abductionParentConvertOpen;


    private Map<String, String> labServerMap;

    private Map<String, String> instoreDataTypeMap;



/*
    private Integer CaseInfoCount;
    *//**
     * 数据总条数
     *//*
    private Integer totalCount;

    */

    /**
     * 每页处理数据条数
     */
    private Integer pageSize;

    public Map<String, String> getLabServerMap() {
        return labServerMap;
    }

    public void setLabServerMap(Map<String, String> labServerMap) {
        this.labServerMap = labServerMap;
    }

    public Map<String, String> getInstoreDataTypeMap() {
        return instoreDataTypeMap;
    }

    public void setInstoreDataTypeMap(Map<String, String> instoreDataTypeMap) {
        this.instoreDataTypeMap = instoreDataTypeMap;
    }

    public boolean isCaseConvertOpen() {
        return caseConvertOpen;
    }

    public void setCaseConvertOpen(boolean caseConvertOpen) {
        this.caseConvertOpen = caseConvertOpen;
    }

    public boolean isUnknownPersonConvertOpen() {
        return unknownPersonConvertOpen;
    }

    public void setUnknownPersonConvertOpen(boolean unknownPersonConvertOpen) {
        this.unknownPersonConvertOpen = unknownPersonConvertOpen;
    }

    public boolean isMissingPersonConvertOpen() {
        return missingPersonConvertOpen;
    }

    public void setMissingPersonConvertOpen(boolean missingPersonConvertOpen) {
        this.missingPersonConvertOpen = missingPersonConvertOpen;
    }

    public boolean isCriminalPersonConvertOpen() {
        return criminalPersonConvertOpen;
    }

    public void setCriminalPersonConvertOpen(boolean criminalPersonConvertOpen) {
        this.criminalPersonConvertOpen = criminalPersonConvertOpen;
    }

    public boolean isDisasterPersonConvertOpen() {
        return disasterPersonConvertOpen;
    }

    public void setDisasterPersonConvertOpen(boolean disasterPersonConvertOpen) {
        this.disasterPersonConvertOpen = disasterPersonConvertOpen;
    }

    public boolean isQualityPersonConvertOpen() {
        return qualityPersonConvertOpen;
    }

    public void setQualityPersonConvertOpen(boolean qualityPersonConvertOpen) {
        this.qualityPersonConvertOpen = qualityPersonConvertOpen;
    }

    public boolean isBasicPersonConvertOpen() {
        return basicPersonConvertOpen;
    }

    public void setBasicPersonConvertOpen(boolean basicPersonConvertOpen) {
        this.basicPersonConvertOpen = basicPersonConvertOpen;
    }

    public boolean isAbductionChildConvertOpen() {
        return abductionChildConvertOpen;
    }

    public void setAbductionChildConvertOpen(boolean abductionChildConvertOpen) {
        this.abductionChildConvertOpen = abductionChildConvertOpen;
    }

    public boolean isAbductionParentConvertOpen() {
        return abductionParentConvertOpen;
    }

    public void setAbductionParentConvertOpen(boolean abductionParentConvertOpen) {
        this.abductionParentConvertOpen = abductionParentConvertOpen;
    }

    /*
    public Integer getCaseInfoCount() {
        return CaseInfoCount;
    }

    public void setCaseInfoCount(Integer caseInfoCount) {
        CaseInfoCount = caseInfoCount;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
*/
    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}
