package com.bazl.dna.database.core.response.datamodel;


import com.bazl.dna.database.service.model.po.CaseImage;
import com.bazl.dna.database.service.model.po.CaseInfo;
import com.bazl.dna.database.service.model.po.ConsignmentInfo;
import com.bazl.dna.database.service.model.vo.CaseInfoVo;
import com.bazl.dna.database.service.model.vo.CasePersonInfoVo;
import com.bazl.dna.database.service.model.vo.ConsignmentInfoVo;
import com.bazl.dna.database.service.model.vo.DnaSampleInfoVo;

import java.io.Serializable;
import java.util.List;

/**
 * 案件详情对象
 * Created by lizhihua on 2020-04-10.
 */
public class CaseInfoDetailDataModel implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CaseInfo caseInfo;

    CaseInfoVo caseInfoVo = new CaseInfoVo();

    private ConsignmentInfo consignmentInfo;

    private ConsignmentInfoVo consignmentInfoVo;

    private List<CasePersonInfoVo> casePersonInfoList;

    private List<DnaSampleInfoVo> personDnaSampleInfoList;

    private List<DnaSampleInfoVo> evidenceDnaSampleInfoList;

    private List<CaseImage> caseImageList;

    /**
     * 委托单位集合
     */
    private List<String> clientOrgList;


    public CaseInfo getCaseInfo() {
        return caseInfo;
    }

    public ConsignmentInfoVo getConsignmentInfoVo() {
        return consignmentInfoVo;
    }

    public void setConsignmentInfoVo(ConsignmentInfoVo consignmentInfoVo) {
        this.consignmentInfoVo = consignmentInfoVo;
    }

    public void setCaseInfo(CaseInfo caseInfo) {
        this.caseInfo = caseInfo;
    }

    public void setCaseInfo(CaseInfoVo caseInfo) {
        this.caseInfo = caseInfo;
    }

    public ConsignmentInfo getConsignmentInfo() {
        return consignmentInfo;
    }

    public void setConsignmentInfo(ConsignmentInfo consignmentInfo) {
        this.consignmentInfo = consignmentInfo;
    }

    public List<CasePersonInfoVo> getCasePersonInfoList() {
        return casePersonInfoList;
    }

    public void setCasePersonInfoList(List<CasePersonInfoVo> casePersonInfoList) {
        this.casePersonInfoList = casePersonInfoList;
    }

    public List<DnaSampleInfoVo> getPersonDnaSampleInfoList() {
        return personDnaSampleInfoList;
    }

    public void setPersonDnaSampleInfoList(List<DnaSampleInfoVo> personDnaSampleInfoList) {
        this.personDnaSampleInfoList = personDnaSampleInfoList;
    }

    public List<DnaSampleInfoVo> getEvidenceDnaSampleInfoList() {
        return evidenceDnaSampleInfoList;
    }

    public void setEvidenceDnaSampleInfoList(List<DnaSampleInfoVo> evidenceDnaSampleInfoList) {
        this.evidenceDnaSampleInfoList = evidenceDnaSampleInfoList;
    }

    public List<CaseImage> getCaseImageList() {
        return caseImageList;
    }

    public void setCaseImageList(List<CaseImage> caseImageList) {
        this.caseImageList = caseImageList;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public CaseInfoVo getCaseInfoVo() {
        return caseInfoVo;
    }

    public void setCaseInfoVo(CaseInfoVo caseInfoVo) {
        this.caseInfoVo = caseInfoVo;
    }

    public List<String> getClientOrgList() {
        return clientOrgList;
    }

    public void setClientOrgList(List<String> clientOrgList) {
        this.clientOrgList = clientOrgList;
    }
}
