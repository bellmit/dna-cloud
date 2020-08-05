package com.bazl.dna.mix.connector.nation.service.impl;

import com.bazl.dna.mix.connector.nation.dao.SysDictMapper;
import com.bazl.dna.mix.connector.nation.model.po.SysDict;
import com.bazl.dna.mix.connector.nation.service.SysDictService;
import com.google.common.collect.Lists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SysDictServiceImpl extends BaseService implements SysDictService {

    @Autowired
    SysDictMapper sysDictMapper;


    /**
     * 查询所有字典数据
     * @return
     */
    @Override
    public List<SysDict> selectAll() {
        try {
            return sysDictMapper.selectAll();
        }catch(Exception e) {
        	logger.error("Error selectAll: ", e);
        }
        return Lists.newArrayList();
    }

    @Override
    public SysDict selectByPrimaryKeyBean(SysDict bean) {
        try {
            return sysDictMapper.selectByPrimaryKeyBean(bean);
        } catch (Exception e) {
            logger.error("Error selectByPrimaryKeyBean: ", e);
        }
        return null;
    }

}
