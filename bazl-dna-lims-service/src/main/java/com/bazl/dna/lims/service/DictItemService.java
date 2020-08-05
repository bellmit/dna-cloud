package com.bazl.dna.lims.service;

import com.bazl.dna.lims.model.po.DictItem;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DictItemService {

    /**
     * 根据dictCode查询出对应字典项内容
     * @param dictCode
     * @return List<SysDictSeed>
     */
    public List<DictItem> selectListByDictCode(String dictCode);

    /**
     * 查询所有字典项内容
     */
    public List<DictItem> selectAllCode();

    /**
     * 根据dictTypeCode查询出对应字典项内容
     * @param dictTypeCode
     * @return
     */
    public List<DictItem> selectListByDictTypeCode(String dictTypeCode);

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
    public DictItem selectDictNameByTypeCode(String dictTypeCode,String dictCode);
    
    /**
     * 根据parentId查询出对应字典项内容
     * @param parentId
     * @return
     */
    public List<DictItem> selectListByParentId(String parentId);
}