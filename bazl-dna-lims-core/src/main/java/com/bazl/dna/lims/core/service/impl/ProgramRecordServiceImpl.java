package com.bazl.dna.lims.core.service.impl;

import com.bazl.dna.lims.core.dao.ProgramRecordMapper;
import com.bazl.dna.lims.core.model.po.ProgramRecord;
import com.bazl.dna.lims.core.service.ProgramRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: chenzeliang
 * @Date: 2020/4/2 10:24
 * @Version: 1.0
 */
@Service
public class ProgramRecordServiceImpl extends BaseService implements ProgramRecordService {
    @Autowired
    ProgramRecordMapper programRecordDao;

    @Override
    public int deleteByPrimaryKey(String id) {
        try {
            return programRecordDao.deleteByPrimaryKey(id);
        }catch(Exception ex){
            logger.error("根据id删除信息错误！", ex);
            throw ex;
        }
    }

    @Override
    public int insert(ProgramRecord record) {
        try {
            return programRecordDao.insert(record);
        }catch(Exception ex){
            logger.error("添加信息错误！", ex);
            throw ex;
        }
    }

    @Override
    public ProgramRecord selectByPrimaryKey(String id) {
        try {
            return programRecordDao.selectByPrimaryKey(id);
        }catch(Exception ex){
            logger.error("根据id查询信息错误！", ex);
            throw ex;
        }
    }

    @Override
    public List<ProgramRecord> selectAll() {
        try {
            return programRecordDao.selectAll();
        }catch(Exception ex){
            logger.error("查询所有信息错误！", ex);
            throw ex;
        }
    }

    @Override
    public int updateByPrimaryKey(ProgramRecord record) {
        try {
            return programRecordDao.updateByPrimaryKey(record);
        }catch(Exception ex){
            logger.error("更新信息错误！", ex);
            throw ex;
        }
    }
}
