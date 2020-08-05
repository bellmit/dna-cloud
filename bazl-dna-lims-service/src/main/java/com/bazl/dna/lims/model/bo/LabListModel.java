package com.bazl.dna.lims.model.bo;

import com.bazl.dna.lims.model.po.LabExtractSample;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LX on 2019/9/26.
 */
public class LabListModel implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<LabExtractSample> labExtractSampleList;

    private List<List<LabExtractSample>> listList;

    public List<LabExtractSample> getLabExtractSampleList() {
        return labExtractSampleList;
    }

    public void setLabExtractSampleList(List<LabExtractSample> labExtractSampleList) {
        this.labExtractSampleList = labExtractSampleList;
    }

    public List<List<LabExtractSample>> getListList() {
        return listList;
    }

    public void setListList(List<List<LabExtractSample>> listList) {
        this.listList = listList;
    }
}
