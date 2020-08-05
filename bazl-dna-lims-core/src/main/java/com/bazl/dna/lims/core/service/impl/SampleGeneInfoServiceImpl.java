package com.bazl.dna.lims.core.service.impl;

import com.bazl.dna.lims.core.dao.SampleGeneInfoMapper;
import com.bazl.dna.lims.core.model.po.SampleGeneInfo;
import com.bazl.dna.lims.core.service.SampleGeneInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wanghaiyang
 * @date 2019/3/31.
 */
@Service
public class SampleGeneInfoServiceImpl extends BaseService implements SampleGeneInfoService {

    @Autowired
    SampleGeneInfoMapper sampleGeneInfoMapper;

    @Override
    public int deleteByPrimaryKey(String id) {
        return sampleGeneInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SampleGeneInfo record) {
        return sampleGeneInfoMapper.insert(record);
    }

    @Override
    public SampleGeneInfo selectByPrimaryKey(String id) {
        return sampleGeneInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SampleGeneInfo> selectAll() {
        return sampleGeneInfoMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(SampleGeneInfo record) {
        return sampleGeneInfoMapper.updateByPrimaryKey(record);
    }

}
