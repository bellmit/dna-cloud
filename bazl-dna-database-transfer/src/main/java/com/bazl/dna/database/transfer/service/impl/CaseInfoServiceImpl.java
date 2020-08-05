package com.bazl.dna.database.transfer.service.impl;

import com.bazl.dna.database.transfer.mapper.CaseInfoMapper;
import com.bazl.dna.database.transfer.model.po.CaseInfoModel;
import com.bazl.dna.database.transfer.model.po.ConsignmentModel;
import com.bazl.dna.database.transfer.service.CaseInfoService;
import com.bazl.dna.database.transfer.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2019/6/9.
 */
@Service
public class CaseInfoServiceImpl extends BaseService implements CaseInfoService {

    @Autowired
    CaseInfoMapper caseInfoMapper;

    /**
     * 根据案件id获取案件信息
     * @param caseId
     * @return
     */
    @Override
    public CaseInfoModel selectCaseInfoModelByCaseId(String caseId) {
        try {
            return caseInfoMapper.selectCaseInfoModelByCaseId(caseId);
        }catch (Exception ex) {
            logger.error("invoke CaseInfoService.selectCaseInfoModelByCaseId error, param is " + caseId + ".", ex);
            return null;
        }
    }


    /**
     * 根据案件id获取委托信息
     * @param caseId
     * @return
     */
    @Override
    public ConsignmentModel selectConsignmentModelByCaseId(String caseId) {
        try {
            return caseInfoMapper.selectConsignmentModelByCaseId(caseId);
        }catch (Exception ex) {
            logger.error("invoke CaseInfoService.selectConsignmentModelByCaseId error, param is " + caseId + ".", ex);
            return null;
        }
    }

    @Override
    public ConsignmentModel selectConsignmentModelByConsignmentId(String consignmentId) {
        try {
            return caseInfoMapper.selectConsignmentModelByConsignmentId(consignmentId);
        }catch (Exception e) {
            logger.error("invoke CaseInfoService.selectConsignmentModelByConsignmentId error, param is " + consignmentId + ".", e);
            return null;
        }
    }


    /**
     * 根据案件id更新此案件的国家库编号
     * @param labNo
     * @param caseId
     */
    @Override
    public void updateLabNoByCaseId(String labNo, String caseId) {
        try {
            CaseInfoModel caseInfoModel = new CaseInfoModel();
            caseInfoModel.setCaseId(caseId);
            caseInfoModel.setCaseLabNo(labNo);
            caseInfoMapper.updateLabNoByCaseId(caseInfoModel);
        }catch (Exception ex) {
            logger.error("invoke CaseInfoService.updateLabNoByCaseId error, param is " + caseId + ".", ex);
        }
    }


    @Override
    public void updateLabNoByCaseId2(String labNo, String caseId) {
        try {
            CaseInfoModel caseInfoModel = new CaseInfoModel();
            caseInfoModel.setCaseId(caseId);
            caseInfoModel.setCaseLabNo(labNo);
            caseInfoMapper.updateLabNoByCaseId2(caseInfoModel);
        }catch (Exception ex) {
            logger.error("invoke CaseInfoService.updateLabNoByCaseId error, param is " + caseId + ".", ex);
        }
    }
}
