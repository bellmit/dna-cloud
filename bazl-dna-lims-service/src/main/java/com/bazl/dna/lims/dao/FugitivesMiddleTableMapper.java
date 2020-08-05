package com.bazl.dna.lims.dao;

import com.bazl.dna.lims.model.po.FugitivesMiddleTable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author huawei
 * @date 2020/7/9.
 */
@Repository
public interface FugitivesMiddleTableMapper {

    /**
     * 根据id删除信息
     * @param id
     * @return
     */
    public int deleteByPrimaryKey(String id);

    /**
     * 插入信息
     * @param record
     * @return
     */
    public int insert(FugitivesMiddleTable record);

    /**
     * 根据id查询信息
     * @param id
     * @return
     */
    public FugitivesMiddleTable selectByPrimaryKey(String id);

    /**
     * 查询所有信息
     * @return
     */
    public List<FugitivesMiddleTable> selectAll();

    /**
     * 更新信息
     * @return
     */
    public int updateByPrimaryKey(FugitivesMiddleTable record);
}