package com.bazl.dna.lims.connector.service.impl;

import com.bazl.dna.lims.connector.dao.LimsSampleGeneMapper;
import com.bazl.dna.lims.connector.model.po.LimsSampleGene;
import com.bazl.dna.lims.connector.model.vo.LimsSampleGeneVo;
import com.bazl.dna.lims.connector.service.LimsSampleGeneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LimsSampleGeneServiceImpl implements LimsSampleGeneService {

    @Autowired
    LimsSampleGeneMapper limsSampleGeneMapper;

    @Override
    public int deleteByPrimaryKey(String geneId) {
        return limsSampleGeneMapper.deleteByPrimaryKey(geneId);
    }

    @Override
    public int insert(LimsSampleGene record) {
        return limsSampleGeneMapper.insert(record);
    }

    @Override
    public LimsSampleGene selectByPrimaryKey(String geneId) {
        return limsSampleGeneMapper.selectByPrimaryKey(geneId);
    }

    @Override
    public List<LimsSampleGene> selectAll() {
        return limsSampleGeneMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(LimsSampleGene record) {
        return limsSampleGeneMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<LimsSampleGeneVo> selectListVoByCaseId (String caseId) {
        return limsSampleGeneMapper.selectListVoByCaseId(caseId);
    }

    @Override
    public int updateBySampleId(String sampleId) {
        return limsSampleGeneMapper.updateBySampleId(sampleId);
    }

    @Override
    public void updateAuditStatus(LimsSampleGene limsSampleGene) {
        limsSampleGeneMapper.updateAuditStatus(limsSampleGene);
    }

    /**
     * 删除标记
     * @param limsSampleGene
     */
    @Override
    public void updateDeleteFlag(LimsSampleGene limsSampleGene) {
        limsSampleGeneMapper.updateDeleteFlag(limsSampleGene);
    }

    /**
     * 更新复检状态
     * @param limsSampleGene
     */
    @Override
    public void updateReviewStatus(LimsSampleGene limsSampleGene) {
        limsSampleGeneMapper.updateReviewStatus(limsSampleGene);
    }

    @Override
    public List<LimsSampleGene> selectAuditedBySampleId(String sampleId) {
        return limsSampleGeneMapper.selectAuditedBySampleId(sampleId);
    }

    @Override
    public LimsSampleGeneVo selectVoListByGeneId(String geneId) {
        return limsSampleGeneMapper.selectVoListByGeneId(geneId);
    }


}
