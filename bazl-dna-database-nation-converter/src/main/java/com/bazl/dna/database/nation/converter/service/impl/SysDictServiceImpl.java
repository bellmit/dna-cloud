package com.bazl.dna.database.nation.converter.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bazl.dna.database.nation.converter.dao.SysDictMapper;
import com.bazl.dna.database.nation.converter.model.po.SysDict;
import com.bazl.dna.database.nation.converter.service.SysDictService;


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
        }catch(Exception ex) {
            logger.error("invoke sysDictService.selectAll error.", ex);
            return null;
        }
    }

}
