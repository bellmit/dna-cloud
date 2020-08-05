package com.bazl.dna.lims.connector.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bazl.dna.lims.connector.dao.LimsCaseInfoMapper;
import com.bazl.dna.lims.connector.dao.LimsSampleInfoDnaMapper;
import com.bazl.dna.lims.connector.model.po.LimsCaseInfo;
import com.bazl.dna.lims.connector.service.LimsCaseInfoService;
import com.bazl.dna.lims.connector.utils.ListUtils;


@Service
public class LimsCaseInfoServiceImpl extends BaseService implements LimsCaseInfoService {

    @Autowired
    LimsCaseInfoMapper limsCaseInfoMapper;


    @Autowired
    LimsSampleInfoDnaMapper limsSampleInfoDnaMapper;

    /**
     * 根据案件id查询案件信息
     * @param caseId
     * @return
     */
    @Override
    public LimsCaseInfo selectByCaseId(String caseId) {
        LimsCaseInfo caseInfoDna = limsCaseInfoMapper.selectByCaseId(caseId);
        return caseInfoDna;
    }




    public String xkTypeToLimsType(String bioEvidenceType) {
        int bioEvidenctTypeInt = Integer.parseInt(bioEvidenceType);
        switch (bioEvidenctTypeInt) {
            case 2002:
                return "01";
            case 2003:
                return "03";
            case 2004:
                return "02";
            case 2005:
                return "06";
            case 2011:
                return "07";
        }
        return "12";
    }



    @Override
    public LimsCaseInfo selectByConsignmentId(String consignmentId) {
        return limsCaseInfoMapper.selectByConsignmentId(consignmentId);
    }


    @Override
    public LimsCaseInfo selectByCaseNo(String caseNo) {
        try {
            List<LimsCaseInfo> caseInfoList = limsCaseInfoMapper.selectByCaseNo(caseNo);
            LimsCaseInfo limsCaseInfo = null;
            if (ListUtils.isNotNullAndEmptyList(caseInfoList)) {
                limsCaseInfo = caseInfoList.get(0);
            }
            return limsCaseInfo;
        }catch (Exception e) {
            e.printStackTrace();
            logger.info("获取案件信息错误错误"+e);
            return null;
        }
    }


}
