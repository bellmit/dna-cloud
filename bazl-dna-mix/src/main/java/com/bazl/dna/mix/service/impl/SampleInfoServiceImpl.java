package com.bazl.dna.mix.service.impl;

import com.bazl.dna.mix.model.po.SampleInfo;
import com.bazl.dna.mix.service.SampleInfoService;
import com.bazl.dna.exception.DnaRuntimeException;
import com.bazl.dna.mix.dao.SampleInfoDAO;
import com.bazl.dna.mix.model.vo.SampleInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: lzn
 * @Date: 2019/7/5 16:15
 * @Version: 1.0
 */
@Service
public class SampleInfoServiceImpl extends MixBaseService implements SampleInfoService {
    @Autowired
    SampleInfoDAO sampleInfoDAO;

    /**
     * 根据样本id查询样本信息
     * @return
     */
    @Override
    public SampleInfo selectBySampleId(String sampleId) {
        SampleInfo sampleInfo = new SampleInfo();
        try {
            sampleInfo = sampleInfoDAO.selectBySampleId(sampleId);
        } catch (Exception ex) {
            logger.error("lims：根据样本id查询样本信息失败！！！", ex.getMessage());
        }
        return sampleInfo;
    }

    /**
     * 修改样本信息
     * @param sampleInfo
     */
    @Override
    @Transactional
    public void updateSampleInfo(SampleInfo sampleInfo) {
        try {
            sampleInfoDAO.updateSampleInfo(sampleInfo);
        } catch (Exception e) {
    		logger.error("Error updateSampleInfo: ", e);
            throw new DnaRuntimeException();
    	}
    }

    /**
     * 添加样本信息
     * @param sampleInfo
     */
    @Override
    @Transactional
    public void insertSampleInfo(SampleInfo sampleInfo) {
        try {
            sampleInfoDAO.insert(sampleInfo);
        } catch (Exception e) {
    		logger.error("Error insert: ", e);
            throw new DnaRuntimeException();
    	}
    }

    /**
     * 根据混合id查询样本信息
     * @param mixedSampleGeneId
     * @return
     */
    @Override
    public List<SampleInfoVo> selectSampleInfoList(String mixedSampleGeneId) {
        List<SampleInfoVo> sampleInfoList = null;
        try {
            sampleInfoList = sampleInfoDAO.selectSampleInfoList(mixedSampleGeneId);
        } catch (Exception ex) {
            logger.error("根据混合id查询样本信息失败！！！", ex.getMessage());
        }
        return sampleInfoList;
    }

    @Override
    public List<SampleInfoVo> selectSampleInfoListBySingleGeneId(String singleSampleGeneId) {
        List<SampleInfoVo> sampleInfoList = null;
        try {
            sampleInfoList = sampleInfoDAO.selectSampleInfoListBySingleGeneId(singleSampleGeneId);
        } catch (Exception ex) {
            logger.error("根据混合id查询样本信息失败！！！", ex.getMessage());
        }
        return sampleInfoList;
    }


}
