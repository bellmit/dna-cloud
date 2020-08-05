package com.bazl.dna.dpznsy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bazl.dna.dpznsy.controller.base.BaseController;
import com.bazl.dna.dpznsy.dao.DictItemMapper;
import com.bazl.dna.dpznsy.model.po.DictItem;
import com.bazl.dna.dpznsy.service.DictItemService;


@Service
public class DictItemServiceImpl extends BaseController implements DictItemService {

    @Autowired
    DictItemMapper dictItemMapper;

    @Override
    public List<DictItem> selectByDictTypeCode(String dictTypeCode){
        return dictItemMapper.selectByDictTypeCode(dictTypeCode);
    }
}
