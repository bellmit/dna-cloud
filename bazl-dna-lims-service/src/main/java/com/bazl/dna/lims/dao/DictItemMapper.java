package com.bazl.dna.lims.dao;


import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bazl.dna.lims.model.po.DictItem;

@Repository
public interface DictItemMapper {


    List<DictItem> selectListByDictCode(String dictCode);

    /**
     * 查询所有字典项内容
     * @return
     */
    List<DictItem> selectAllCode();

    /**
     * 根据dictTypeCode查询出对应字典项内容
     * @param dictTypeCode
     * @return
     */
    List<DictItem> selectListByDictTypeCode(String dictTypeCode);

    int deleteByPrimaryKey(String dictItemId);

    int insert(DictItem record);

    DictItem selectByPrimaryKey(String dictItemId);

    int updateByPrimaryKey(DictItem record);
    
    /**
     * 查询案件性质
     * @return
     */
    List<DictItem> selectCaseTypeList();
    
    List<DictItem> selectCaseTypeBydictItemIdList(String dictItemId);
    
    /**
     * 根据字典类型和code查询对应名称
     * @param dictTypeCode
     * @param dictCode
     * @return
     */
    public DictItem selectDictNameByTypeCode(@Param("dictTypeCode") String dictTypeCode, @Param("dictCode") String dictCode);
    
    /**
     * 根据parentId查询出对应字典项内容
     * @param parentId
     * @return
     */
    public List<DictItem> selectListByParentId(String parentId);
}