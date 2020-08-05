package com.bazl.dna.database.transfer.service;


import com.bazl.dna.database.transfer.model.po.CaseInfoModel;
import com.bazl.dna.database.transfer.model.po.ConsignmentModel;

/**
 * Created by Administrator on 2019/6/9.
 */
public interface CaseInfoService {

    /**
     * 根据案件id获取案件信息
     * @param caseId
     * @return
     */
    public CaseInfoModel selectCaseInfoModelByCaseId(String caseId);

    /**
     * 根据案件id获取委托信息
     * @param caseId
     * @return
     */
    public ConsignmentModel selectConsignmentModelByCaseId(String caseId);

    /**
     * 根据委托d获取委托信息
     * @param consignmentId
     * @return
     */
    public ConsignmentModel selectConsignmentModelByConsignmentId(String consignmentId);

    /**
     * 根据案件id更新此案件的国家库编号
     * @param labNo
     * @param caseId
     */
    public void updateLabNoByCaseId(String labNo, String caseId);

    void updateLabNoByCaseId2(String nationCaseNo, String caseId);
}
