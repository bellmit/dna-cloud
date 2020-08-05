package com.bazl.dna.lims.connector.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bazl.dna.lims.connector.dao.LimsSampleInfoDnaMapper;
import com.bazl.dna.lims.connector.model.po.LimsSampleInfoDna;
import com.bazl.dna.lims.connector.model.vo.LimsSampleInfoDnaVo;
import com.bazl.dna.lims.connector.service.LimsSampleInfoDnaService;


@Service
public class LimsSampleInfoDnaServiceImpl extends BaseService implements LimsSampleInfoDnaService {

    @Autowired
    LimsSampleInfoDnaMapper limsSampleInfoDnaMapper;

    /**
     * 根据案件id查询物证信息
     * @param caseId
     * @return
     */
    @Override
    public List<LimsSampleInfoDna> selectByCaseId(String caseId) {
        List<LimsSampleInfoDna> limsPersonInfoList = limsSampleInfoDnaMapper.selectByCaseId(caseId);
        return limsPersonInfoList;
    }

    @Override
    public List<LimsSampleInfoDna> selectListByConsignmentId(String consignmentId) {
        List<LimsSampleInfoDna> limsSampleInfoDnaList = limsSampleInfoDnaMapper.selectListByConsignmentId(consignmentId);
        return limsSampleInfoDnaList;
    }


    /**
     * 根据检材编号查询物证信息
     * @param sampleNo
     * @return
     */
    @Override
    public List<LimsSampleInfoDna> selectBySampleNo(String sampleNo) {
        List<LimsSampleInfoDna> limsPersonInfoList = limsSampleInfoDnaMapper.selectBySampleNo(sampleNo);
        return limsPersonInfoList;
    }

    @Override
    public List<LimsSampleInfoDna> selectList(LimsSampleInfoDna limsSampleInfoDna) {
        return limsSampleInfoDnaMapper.selectList(limsSampleInfoDna);
    }


    @Override
    public List<LimsSampleInfoDnaVo> selectSampleInfos(String caseId) {
        return limsSampleInfoDnaMapper.selectSampleInfos(caseId);
    }

    @Override
    public List<LimsSampleInfoDna> selectByConsignmentId(String consignmentId) {
        return limsSampleInfoDnaMapper.selectByConsignmentId(consignmentId);
    }

    @Override
    public List<LimsSampleInfoDna> selectBySampleId(String sampleId) {
        return limsSampleInfoDnaMapper.selectBySampleId(sampleId);
    }


    @Override
    public boolean isStoredBySampleId(String sampleId) {
        LimsSampleInfoDna limsSampleInfoDna = limsSampleInfoDnaMapper.isStoredBySampleId(sampleId);
        if (limsSampleInfoDna == null) {
            return false;
        }else {
            return true;
        }
    }



}
