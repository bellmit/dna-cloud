package com.bazl.dna.lims.core.service.impl;

import com.bazl.dna.lims.core.dao.ErsFileMapper;
import com.bazl.dna.lims.core.model.po.ErsFile;
import com.bazl.dna.lims.core.service.ErsFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wanghaiyang
 * @date 2020/7/20 13:55
 */
@Service
public class ErsFileServiceImpl extends BaseService implements ErsFileService {

    @Autowired
    ErsFileMapper ersFileMapper;

    @Override
    public int deleteByPrimaryKey(String fileId) {
        return ersFileMapper.deleteByPrimaryKey(fileId);
    }

    @Override
    public int insert(ErsFile record) {
        return ersFileMapper.insert(record);
    }

    @Override
    public ErsFile selectByPrimaryKey(String fileId) {
        return ersFileMapper.selectByPrimaryKey(fileId);
    }

    @Override
    public List<ErsFile> selectAll() {
        return ersFileMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(ErsFile record) {
        return ersFileMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<ErsFile> selectList(ErsFile ersFile) {
        return ersFileMapper.selectList(ersFile);
    }
}
