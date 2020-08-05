package com.bazl.dna.lims.core.datamodel;


import com.bazl.dna.lims.core.model.po.LabSyInfo;
import com.bazl.dna.lims.core.model.po.LabSySample;

import java.io.Serializable;
import java.util.List;

/**
 * @author wanghaiyang
 * @date 2019/5/14.
 */
public class SyRecordDataModel implements Serializable {

    private LabSyInfo labSyInfo;

    private List<LabSySample> labSySampleList;

    public LabSyInfo getLabSyInfo() {
        return labSyInfo;
    }

    public void setLabSyInfo(LabSyInfo labSyInfo) {
        this.labSyInfo = labSyInfo;
    }

    public List<LabSySample> getLabSySampleList() {
        return labSySampleList;
    }

    public void setLabSySampleList(List<LabSySample> labSySampleList) {
        this.labSySampleList = labSySampleList;
    }
}
