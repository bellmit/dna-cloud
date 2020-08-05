package com.bazl.dna.mix.service.impl;

import com.bazl.dna.exception.DnaRuntimeException;
import com.bazl.dna.mix.dao.CaseInfoDAO;
import com.bazl.dna.mix.model.po.CaseInfo;
import com.bazl.dna.mix.service.CaseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: lzn
 * @Date: 2019/7/5 16:15
 * @Version: 1.0
 */
@Service
public class CaseInfoServiceImpl extends MixBaseService implements CaseInfoService {
    @Autowired
    CaseInfoDAO caseInfoDAO;

    /**
     * lims根据案件id查询案件信息
     * @return
     */
    @Override
    public CaseInfo selectByCaseId(String caseId) {
        CaseInfo caseInfo = null;
        try {
            caseInfo = caseInfoDAO.selectByCaseId(caseId);
        } catch (Exception ex) {
            logger.error("lims：根据案件id查询案件信息失败！！！", ex.getMessage());
        }
        return caseInfo;
    }

    /**
     * 修改案件信息
     * @param caseInfo
     */
    @Override
    @Transactional
    public void updateCaseInfo(CaseInfo caseInfo) {
        try {
            caseInfoDAO.updateCaseInfo(caseInfo);
        } catch (Exception ex) {
            logger.error("修改从lims获取案件信息案件信息失败！！！"+ ex.getMessage());
        	throw new DnaRuntimeException();
        }
    }

    /**
     * 添加案件信息
     * @param caseInfo
     */
    @Override
    @Transactional
    public void insertCaseInfo(CaseInfo caseInfo) {
        try {
            caseInfoDAO.insert(caseInfo);
        } catch (Exception ex) {
            logger.error("添加从lims获取案件案件信息失败！！！"+ ex.getMessage());
            throw new DnaRuntimeException();
        }
    }
}
