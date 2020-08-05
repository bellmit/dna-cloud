package com.bazl.dna.lims.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bazl.dna.lims.model.po.DnaEvidenceCount;
import com.bazl.dna.lims.service.ArchivesEvidenceService;
import com.bazl.dna.lims.dao.ArchivesEvidenceMapper;


@Service
public class ArchivesEvidenceServiceImpl extends BaseService implements ArchivesEvidenceService {
    @Autowired
    ArchivesEvidenceMapper archivesEvidenceMapper;


    @Override
    public List<DnaEvidenceCount> selectAllCount() {
        try{
             return archivesEvidenceMapper.selectAllCount();
        }catch (Exception ex){
            logger.error("invoke ArchivesEvidenceService.selectAllCount error!!",ex.getMessage());
        }
        return null;
    }


    @Override
    public boolean saveBatch(Collection<DnaEvidenceCount> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<DnaEvidenceCount> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean updateBatchById(Collection<DnaEvidenceCount> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdate(DnaEvidenceCount entity) {
        return false;
    }

    @Override
    public DnaEvidenceCount getOne(Wrapper<DnaEvidenceCount> queryWrapper, boolean throwEx) {
        return null;
    }

    @Override
    public Map<String, Object> getMap(Wrapper<DnaEvidenceCount> queryWrapper) {
        return null;
    }

    @Override
    public <V> V getObj(Wrapper<DnaEvidenceCount> queryWrapper, Function<? super Object, V> mapper) {
        return null;
    }

    @Override
    public BaseMapper<DnaEvidenceCount> getBaseMapper() {
        return null;
    }
}
