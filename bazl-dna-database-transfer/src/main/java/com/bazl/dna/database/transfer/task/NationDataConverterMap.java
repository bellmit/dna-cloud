package com.bazl.dna.database.transfer.task;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author lizhihua on 2020-05-13.
 */
@Configuration
@ConfigurationProperties(prefix="convertmaps", ignoreUnknownFields = true)
public class NationDataConverterMap {

    /**
     * 案件性质
     */
    private Map<String, String> casePropertyMap;

    public Map<String, String> getCasePropertyMap() {
        return casePropertyMap;
    }

    public void setCasePropertyMap(Map<String, String> casePropertyMap) {
        this.casePropertyMap = casePropertyMap;
    }
}
