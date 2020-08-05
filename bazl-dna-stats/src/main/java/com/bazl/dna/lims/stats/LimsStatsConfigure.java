package com.bazl.dna.lims.stats;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author lizhihua on 2020/6/9.
 */
@Component
@Configuration
public class LimsStatsConfigure {

    @Value("${defaultDnaLabName}")
    private String labName;

    @Value("${defaultDnaLabOrgId}")
    private String defaultDnaLabOrgId;

    @Value("#{'${assetsCaseSubPropertyList}'.split(',')}")
    private List<String> assetsCaseSubPropertyList;

    @Value("#{'${fenjuOrgList}'.split(',')}")
    private List<String> fenjuOrgList;

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    public String getDefaultDnaLabOrgId() {
        return defaultDnaLabOrgId;
    }

    public void setDefaultDnaLabOrgId(String defaultDnaLabOrgId) {
        this.defaultDnaLabOrgId = defaultDnaLabOrgId;
    }

    public List<String> getAssetsCaseSubPropertyList() {
        return assetsCaseSubPropertyList;
    }

    public void setAssetsCaseSubPropertyList(List<String> assetsCaseSubPropertyList) {
        this.assetsCaseSubPropertyList = assetsCaseSubPropertyList;
    }

    public List<String> getFenjuOrgList() {
        return fenjuOrgList;
    }

    public void setFenjuOrgList(List<String> fenjuOrgList) {
        this.fenjuOrgList = fenjuOrgList;
    }
}
