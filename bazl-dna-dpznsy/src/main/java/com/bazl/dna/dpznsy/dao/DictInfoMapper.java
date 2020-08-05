package com.bazl.dna.dpznsy.dao;


import com.bazl.dna.dpznsy.model.po.DictInfo;

import java.util.ArrayList;
import java.util.List;

public interface DictInfoMapper {
    int insert(DictInfo record);

    List<DictInfo> selectAll();
    //根据类型获取
    ArrayList<String> selectByTypeName(String typeName);
    //查询对象
    List<DictInfo> selectObjByTypeName(String typeName);
    //根据上级id获取
    List<DictInfo> selectObjByParentId(String id);
}