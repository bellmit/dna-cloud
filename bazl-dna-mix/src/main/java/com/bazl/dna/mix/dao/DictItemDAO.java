package com.bazl.dna.mix.dao;

import com.bazl.dna.mix.model.po.DictItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DictItemDAO {
    int deleteByPrimaryKey(String dictItemId);

    int insert(DictItem record);

    int insertSelective(DictItem record);

    DictItem selectByPrimaryKey(String dictItemId);

    int updateByPrimaryKeySelective(DictItem record);

    int updateByPrimaryKey(DictItem record);

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

    List<DictItem> selectListByDictType(@Param("dictTypeCode") String dictTypeCode, @Param("createPerson") String createPerson);
}