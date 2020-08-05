package com.bazl.dna.dpznsy.service;


import com.bazl.dna.dpznsy.model.po.DictItem;

import java.util.List;

/**
 * Created by Administrator on 2020/2/14.
 */
public interface DictItemService {

    public List<DictItem> selectByDictTypeCode(String dictTypeCode);
}
