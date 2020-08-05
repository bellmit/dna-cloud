package com.bazl.dna.lims.service.impl;

import com.bazl.dna.lims.dao.CompareSameResultMapper;
import com.bazl.dna.lims.model.po.CompareSameResult;
import com.bazl.dna.lims.service.CompareSameResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wanghaiyang
 * @date 2019/4/19.
 */
@Service
public class CompareSameResultServiceImpl extends BaseService implements CompareSameResultService {

    @Autowired
    CompareSameResultMapper compareSameResultMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int deleteByPrimaryKey(String id) {
        return compareSameResultMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int insert(CompareSameResult record) {
        return compareSameResultMapper.insert(record);
    }

    @Override
    public CompareSameResult selectByPrimaryKey(String id) {
        return compareSameResultMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<CompareSameResult> selectAll() {
        return compareSameResultMapper.selectAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int updateByPrimaryKey(CompareSameResult record) {
        return compareSameResultMapper.updateByPrimaryKey(record);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public int deleteByCaseId(String caseId) {
        return compareSameResultMapper.deleteByCaseId(caseId);
    }

    @Override
    public List<CompareSameResult> selectListByCaseId(String caseId) {
        return compareSameResultMapper.selectListByCaseId(caseId);
    }

    @Override
    public List<CompareSameResult> selectDistinctListByCaseId(String caseId) {
        return compareSameResultMapper.selectDistinctListByCaseId(caseId);
    }

}
