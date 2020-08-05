package com.bazl.dna.database.core.response.datamodel;

import com.bazl.dna.database.service.model.vo.CriminalPersonInfoVo;
import com.bazl.dna.database.service.model.vo.CriminalSampleInfoVo;

import java.io.Serializable;
import java.util.List;

/**
 * 建库人员详情对象
 * Created by lizhihua on 2020-04-10.
 */
public class CriminalDetailDataModel implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CriminalPersonInfoVo criminalPersonInfoVo;

    private List<CriminalSampleInfoVo> criminalSampleInfoVoList;

    public CriminalPersonInfoVo getCriminalPersonInfoVo() {
        return criminalPersonInfoVo;
    }

    public void setCriminalPersonInfoVo(CriminalPersonInfoVo criminalPersonInfoVo) {
        this.criminalPersonInfoVo = criminalPersonInfoVo;
    }

    public List<CriminalSampleInfoVo> getCriminalSampleInfoVoList() {
        return criminalSampleInfoVoList;
    }

    public void setCriminalSampleInfoVoList(List<CriminalSampleInfoVo> criminalSampleInfoVoList) {
        this.criminalSampleInfoVoList = criminalSampleInfoVoList;
    }
}
