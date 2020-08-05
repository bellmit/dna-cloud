package com.bazl.dna.lims.model.bo;

import com.bazl.dna.lims.model.po.LabSySample;

import java.io.Serializable;
import java.util.List;

/**
 * Created by LX on 2019/9/26.
 */
public class SyLiistModel implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<LabSySample> labSySampleList;

    private List<List<LabSySample>> listList;

    public List<LabSySample> getLabSySampleList() {
        return labSySampleList;
    }

    public void setLabSySampleList(List<LabSySample> labSySampleList) {
        this.labSySampleList = labSySampleList;
    }

    public List<List<LabSySample>> getListList() {
        return listList;
    }

    public void setListList(List<List<LabSySample>> listList) {
        this.listList = listList;
    }
}
