package com.bazl.dna.lims.model.bo;

import java.io.Serializable;
import java.util.List;

import com.bazl.dna.lims.model.po.FugitivesInfo;
import com.bazl.dna.lims.model.po.LimsCaseInfo;
import com.bazl.dna.lims.model.po.LimsConsignmentInfo;
import com.bazl.dna.lims.model.po.LimsPersonInfo;
import com.bazl.dna.lims.model.po.LimsSampleInfoDna;

/**
 * Created by Administrator on 2018-06-24.
 */
public class DelegateDataModel implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private LimsConsignmentInfo consignatioInfo; //委托信息

    private LimsCaseInfo caseInfoDna; //案件信息

    private List<LimsPersonInfo> limsPersonInfoList; //案件人员

    private List<LimsSampleInfoDna> sampleInfoDnaList; //样本信息

    private LimsConsignmentInfo  acceptInfo; //受理信息
    
    //在逃人员
    private FugitivesInfo fugitivesInfo;

    public LimsConsignmentInfo getConsignatioInfo() {
        return consignatioInfo;
    }

    public void setConsignatioInfo(LimsConsignmentInfo consignatioInfo) {
        this.consignatioInfo = consignatioInfo;
    }

    public LimsCaseInfo getCaseInfoDna() {
        return caseInfoDna;
    }

    public void setCaseInfoDna(LimsCaseInfo caseInfoDna) {
        this.caseInfoDna = caseInfoDna;
    }

    public List<LimsPersonInfo> getLimsPersonInfoList() {
        return limsPersonInfoList;
    }

    public void setLimsPersonInfoList(List<LimsPersonInfo> limsPersonInfoList) {this.limsPersonInfoList = limsPersonInfoList;}

    public List<LimsSampleInfoDna> getSampleInfoDnaList() {
        return sampleInfoDnaList;
    }

    public void setSampleInfoDnaList(List<LimsSampleInfoDna> sampleInfoDnaList) {this.sampleInfoDnaList = sampleInfoDnaList;}

    public LimsConsignmentInfo getAcceptInfo() {return acceptInfo;}

    public void setAcceptInfo(LimsConsignmentInfo acceptInfo) {this.acceptInfo = acceptInfo;}
    
    public FugitivesInfo getFugitivesInfo() {
        return fugitivesInfo;
    }

    public void setFugitivesInfo(FugitivesInfo fugitivesInfo) {
        this.fugitivesInfo = fugitivesInfo;
    }
}
