package com.bazl.dna.database.transfer.mapper;

import com.bazl.dna.database.transfer.model.po.CaseInfoModel;
import com.bazl.dna.database.transfer.model.po.ConsignmentModel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author
 * Created by lizhihua on 2019-05-08.
 */
@Repository
public interface CaseInfoMapper {

    CaseInfoModel selectCaseInfoModelByCaseId(String caseId);

    /**
     * 根据案件id查询新lims案件信息
     * @param caseId
     * @return
     */
    ConsignmentModel selectConsignmentModelByCaseId(String caseId);

    /**
     * 根据委托d获取委托信息
     * @param consignmentId
     * @return
     */
    public ConsignmentModel selectConsignmentModelByConsignmentId(String consignmentId);

    /**
     * 根据案件id更新此案件的国家库编号
     * @param caseInfoModel
     */
    public void updateLabNoByCaseId(CaseInfoModel caseInfoModel);

    void updateLabNoByCaseId2(CaseInfoModel caseInfoModel);
}
