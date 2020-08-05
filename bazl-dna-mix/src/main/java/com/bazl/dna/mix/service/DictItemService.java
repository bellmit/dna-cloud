package com.bazl.dna.mix.service;

import com.bazl.dna.mix.model.po.DictItem;
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

    List<DictItem> selectListByDictType(String dictTypeCode, String createPerson);

    int updateSampleNoVal(DictItem dictItem);

    int insertSampleNoVal(DictItem dictItem);
}