package com.bazl.dna.lims.service.impl;

import com.bazl.dna.lims.dao.FugitivesMiddleTableMapper;
import com.bazl.dna.lims.model.po.FugitivesMiddleTable;
import com.bazl.dna.lims.service.FugitivesMiddleTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wanghaiyang
 * @date 2020/7/9 18:29
 */
public class FugitivesMiddleTableServiceImpl extends BaseService implements FugitivesMiddleTableService {

    @Autowired
    FugitivesMiddleTableMapper fugitivesMiddleTableMapper;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public int deleteByPrimaryKey(String id) {
        try {
            return fugitivesMiddleTableMapper.deleteByPrimaryKey(id);
        }catch(Exception e){
            e.getStackTrace();
            logger.error(id + "删除失败！");
            return 0;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public int insert(FugitivesMiddleTable record) {
        try {
            return fugitivesMiddleTableMapper.insert(record);
        }catch (Exception e) {
            e.getStackTrace();
            logger.error("插入失败！");
            return 0;
        }
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public FugitivesMiddleTable selectByPrimaryKey(String id) {
        try {
            return fugitivesMiddleTableMapper.selectByPrimaryKey(id);
        }catch (Exception e) {
            e.getStackTrace();
            logger.error(id + "查询失败！");
            return null;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public List<FugitivesMiddleTable> selectAll() {
        try {
            return fugitivesMiddleTableMapper.selectAll();
        }catch (Exception e) {
            e.getStackTrace();
            logger.error("查询失败！");
            return null;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public int updateByPrimaryKey(FugitivesMiddleTable record) {
        try {
            return fugitivesMiddleTableMapper.updateByPrimaryKey(record);
        }catch (Exception e) {
            e.getStackTrace();
            logger.error("更新失败！");
            return 0;
        }
    }
}
