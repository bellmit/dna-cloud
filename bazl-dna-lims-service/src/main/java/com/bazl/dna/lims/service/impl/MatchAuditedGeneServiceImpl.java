package com.bazl.dna.lims.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bazl.dna.lims.dao.MatchAuditedGeneMapper;
import com.bazl.dna.lims.model.po.MatchAuditedGene;
import com.bazl.dna.lims.model.vo.MatchAuditedGeneVo;
import com.bazl.dna.lims.service.MatchAuditedGeneService;

/**
 * Created by Administrator on 2019/3/26.
 */

@Service
public class MatchAuditedGeneServiceImpl implements MatchAuditedGeneService {

    @Autowired
    private MatchAuditedGeneMapper matchAuditedGeneMapper;

    @Override
    public int deleteByPrimaryKey(String id) {
        return matchAuditedGeneMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(MatchAuditedGene record) {
        return matchAuditedGeneMapper.insert(record);
    }

    @Override
    public MatchAuditedGene selectByPrimaryKey(String id) {
        return matchAuditedGeneMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<MatchAuditedGene> selectAll() {
        return matchAuditedGeneMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(MatchAuditedGene record) {
        return matchAuditedGeneMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<MatchAuditedGene> selectBySampleId(String sampleId) {
        return matchAuditedGeneMapper.selectBySampleId(sampleId);
    }

    @Override
    public List<MatchAuditedGene> selectByCaseId(String caseId) {
        return matchAuditedGeneMapper.selectByCaseId(caseId);
    }

    @Override
    public List<MatchAuditedGene> selectByMatchAuditedGene(MatchAuditedGene matchAuditedGene) {
        return matchAuditedGeneMapper.selectByMatchAuditedGene(matchAuditedGene);
    }

    @Override
    public List<MatchAuditedGene> selectGeneByPage(int page,int size, String sampleFlag) {
        int startRum = page*size;
        int endRum = (page+1)*size;
        return matchAuditedGeneMapper.selectGeneByPage(startRum, endRum, sampleFlag);
    }

    @Override
    public List<MatchAuditedGene> selectListBySampleNo(String sampleNo) {
        return matchAuditedGeneMapper.selectListBySampleNo(sampleNo);
    }

    @Override
    public int updateBySampleId(MatchAuditedGene matchAuditedGene) {
        return matchAuditedGeneMapper.updateBySampleId(matchAuditedGene);
    }

    @Override
    public List<MatchAuditedGene> selectDetectedMatchAuditedGene() {
        return matchAuditedGeneMapper.selectDetectedMatchAuditedGene();
    }

    @Override
    public List<MatchAuditedGene> selectByMatchAuditedGeneList(MatchAuditedGeneVo query, int page) {
        List<MatchAuditedGene> queuestateList = null;
        int pageNo;
        int pageSize;
        try {
            pageNo = page;
            pageSize = 1000;
            query.setOffset((pageNo - 1) * pageSize);
            query.setRows(query.getOffset() + pageSize);
            queuestateList = matchAuditedGeneMapper.selectByMatchAuditedGeneList(query);
        } catch(Exception ex) {
            System.out.println("查询入混合库数据报错");
        }
        return queuestateList;
    }

}
