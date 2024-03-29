package com.bazl.dna.lims.model.po;

import java.io.Serializable;
import java.util.List;

import com.bazl.dna.lims.model.vo.SampleInfoVo;

/**
 * @Author: chenzeliang
 * @Date: 2020/3/31 9:41
 * @Version: 1.0
 */
public class SampleInfoModel implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<SampleInfoVo> sampleInfoVoList;

    private List<SampleInfo> sampleInfoList;

    private String parameter;

    private SampleTable sampleTable;

    public List<SampleInfoVo> getSampleInfoVoList() {
        return sampleInfoVoList;
    }

    public void setSampleInfoVoList(List<SampleInfoVo> sampleInfoVoList) {
        this.sampleInfoVoList = sampleInfoVoList;
    }

    public List<SampleInfo> getSampleInfoList() {
        return sampleInfoList;
    }

    public void setSampleInfoList(List<SampleInfo> sampleInfoList) {
        this.sampleInfoList = sampleInfoList;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public SampleTable getSampleTable() {
        return sampleTable;
    }

    public void setSampleTable(SampleTable sampleTable) {
        this.sampleTable = sampleTable;
    }
}
