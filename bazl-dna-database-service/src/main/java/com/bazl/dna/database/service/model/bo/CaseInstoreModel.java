package com.bazl.dna.database.service.model.bo;

import com.bazl.dna.database.service.model.po.*;

import java.io.Serializable;
import java.util.List;

/**
 * 案件入库模型
 * Created by lizhihua on 2020/2/11.
 */
public class CaseInstoreModel implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CaseInfo caseInfo;

    private List<ConsignmentInfo> consignmentInfoList;

    private List<CasePersonInfo> casePersonInfoList;

    private List<PersonRelativeInfo> personRelativeInfoList;

    private List<DnaSampleInfo> dnaSampleInfoList;

    private List<DnaGeneInfo> dnaGeneInfoList;

    public CaseInfo getCaseInfo() {
        return caseInfo;
    }

    public void setCaseInfo(CaseInfo caseInfo) {
        this.caseInfo = caseInfo;
    }

    public List<ConsignmentInfo> getConsignmentInfoList() {
        return consignmentInfoList;
    }

    public void setConsignmentInfoList(List<ConsignmentInfo> consignmentInfoList) {
        this.consignmentInfoList = consignmentInfoList;
    }

    public List<DnaSampleInfo> getDnaSampleInfoList() {
        return dnaSampleInfoList;
    }

    public void setDnaSampleInfoList(List<DnaSampleInfo> dnaSampleInfoList) {
        this.dnaSampleInfoList = dnaSampleInfoList;
    }

    public List<CasePersonInfo> getCasePersonInfoList() {
        return casePersonInfoList;
    }

    public void setCasePersonInfoList(List<CasePersonInfo> casePersonInfoList) {
        this.casePersonInfoList = casePersonInfoList;
    }

    public List<PersonRelativeInfo> getPersonRelativeInfoList() {
        return personRelativeInfoList;
    }

    public void setPersonRelativeInfoList(List<PersonRelativeInfo> personRelativeInfoList) {
        this.personRelativeInfoList = personRelativeInfoList;
    }

    public List<DnaGeneInfo> getDnaGeneInfoList() {
        return dnaGeneInfoList;
    }

    public void setDnaGeneInfoList(List<DnaGeneInfo> dnaGeneInfoList) {
        this.dnaGeneInfoList = dnaGeneInfoList;
    }
}
