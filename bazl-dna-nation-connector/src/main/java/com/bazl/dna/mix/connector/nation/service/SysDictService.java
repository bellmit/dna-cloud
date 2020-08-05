package com.bazl.dna.mix.connector.nation.service;



import java.util.List;

import com.bazl.dna.mix.connector.nation.model.po.SysDict;

public interface SysDictService {

    public List<SysDict> selectAll();

    SysDict selectByPrimaryKeyBean(SysDict bean);

}
