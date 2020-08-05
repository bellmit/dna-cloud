package com.bazl.dna.mix.service;

import com.bazl.dna.mix.model.po.QualtyPersonnel;
import com.bazl.dna.mix.model.vo.SingleSampleGeneVo;

import java.util.List;

/**
 * @Author: lzn
 * @Date: 2019/7/10 16:31
 * @Version: 1.0
 */
public interface QualtyPersonnelService{
    /**
     * 查询比中的质控人员样本信息
     * @param qusltyId
     * @return
     */
    QualtyPersonnel selectMixedSampleQualityDetails(String qusltyId);

    /**
     * 根据质控人员id查询质控样本
     * @param ratiogeneId
     * @return
     */
    List<QualtyPersonnel> selectMixedSampleQuality(String ratiogeneId);

    /**
     * 查询质控人员
     * @return
     * @param fromIndex
     * @param toIndex
     */
    List<QualtyPersonnel> selectAll(int fromIndex, int toIndex);

    /**
     * 查询质控人员
     * @param page
     * @param size
     * @return
     */
    List<SingleSampleGeneVo> selecAllSampleQualityList(int page, int size);

    /**
     * 查询所有质控人员
     * @return
     */
    List<QualtyPersonnel> selectAllList();
}
