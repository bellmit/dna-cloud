package com.bazl.dna.lims.datamodel;

import com.bazl.dna.lims.model.po.LabPcrInfo;
import com.bazl.dna.lims.model.po.LabPcrSample;

import java.io.Serializable;
import java.util.List;

/**
 * @author wanghaiyang
 * @date 2019/5/12.
 */
public class PcrRecordDataModel implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private LabPcrInfo labPcrInfo;

    private List<LabPcrSample> labPcrSampleList;

    public LabPcrInfo getLabPcrInfo() {
        return labPcrInfo;
    }

    public void setLabPcrInfo(LabPcrInfo labPcrInfo) {
        this.labPcrInfo = labPcrInfo;
    }

    public List<LabPcrSample> getLabPcrSampleList() {
        return labPcrSampleList;
    }

    public void setLabPcrSampleList(List<LabPcrSample> labPcrSampleList) {
        this.labPcrSampleList = labPcrSampleList;
    }
}
