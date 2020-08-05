package com.bazl.dna.lims.core.service;

import com.bazl.dna.lims.core.model.po.ErsFile;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wanghaiyang
 * @date 2020/7/20 13:54
 */
@Repository
public interface ErsFileService {

    /**
     * 根据id删除信息
     * @param fileId
     * @return
     */
    public int deleteByPrimaryKey(String fileId);

    /**
     * 插入
     * @param record
     * @return
     */
    public int insert(ErsFile record);

    /**
     * 根据id查询
     * @param fileId
     * @return
     */
    public ErsFile selectByPrimaryKey(String fileId);

    /**
     * 查询全部信息
     * @return
     */
    public List<ErsFile> selectAll();

    /**
     * 更新
     * @param record
     * @return
     */
    public int updateByPrimaryKey(ErsFile record);

    /**
     * 根据条件查询文件信息
     * @param ersFile
     * @return
     */
    public List<ErsFile> selectList(ErsFile ersFile);
}
