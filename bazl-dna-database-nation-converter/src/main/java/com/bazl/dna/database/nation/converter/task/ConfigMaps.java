package com.bazl.dna.database.nation.converter.task;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Created by lizhihua on 2020-01-29.
 */
@Component
@Configuration
@ConfigurationProperties(prefix = "consignmentcount", ignoreInvalidFields = true)
public class ConfigMaps {

    private Integer CaseInfoCount;
    /**
     * 数据总条数
     */
    private Integer totalCount;

    /**
     * 每页处理数据条数
     */
    private Integer pageSize;

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

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}
