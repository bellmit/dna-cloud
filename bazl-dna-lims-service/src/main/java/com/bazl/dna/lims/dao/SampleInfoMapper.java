package com.bazl.dna.lims.dao;

import com.bazl.dna.lims.model.po.SampleInfo;
import com.bazl.dna.lims.model.vo.SampleInfoVo;

import java.util.List;

/**
 * @Author: lzn
 * @Date: 2020/3/31 11:19
 * @Version: 1.0
 */
public interface SampleInfoMapper {
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
    public int insert(SampleInfo record);

    /**
     * 根据id查询信息
     * @param id
     * @return
     */
    public SampleInfo selectByPrimaryKey(String id);

    /**
     * 查询所有信息
     * @return
     */
    public List<SampleInfo> selectAll();

    /**
     * 更新信息
     * @param record
     * @return
     */
    public int updateByPrimaryKey(SampleInfo record);

    /**
     * 根据条件查询检材信息
     * @param query
     * @return
     */
    public List<SampleInfoVo> selectVoListBySampleInfo(SampleInfoVo query);

    /**
     * 根据条件查询不重复检材信息
     * @param query
     * @return
     */
    public List<SampleInfoVo> selectListVo(SampleInfoVo query);

    /**
     * 根据id删除信息
     * @param id
     * @return
     */
    public int deleteFlagById(String id);

    /**
     * 根据样本表id更新信息
     * @param sampleInfo
     * @return
     */
    public int updateBySampleTableId(SampleInfo sampleInfo);

    /**
     * 根据提取表id更新信息
     * @param sampleInfo
     * @return
     */
    public int updateByExtractPlateId(SampleInfo sampleInfo);

    /**
     * 根据扩增表id更新信息
     * @param sampleInfo
     * @return
     */
    public int updateByPcrPlateId(SampleInfo sampleInfo);
      /*
    * 条件查询
    * */
    public List<SampleInfoVo> selectPaginationList(SampleInfoVo sampleInfoVo);
    /*
   * 条件查询数量
   * */
    public int selectCount(SampleInfoVo sampleInfoVo);
}
