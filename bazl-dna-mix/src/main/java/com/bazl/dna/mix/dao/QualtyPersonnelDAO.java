package com.bazl.dna.mix.dao;

import com.bazl.dna.mix.model.vo.SingleSampleGeneVo;
import com.bazl.dna.mix.model.po.QualtyPersonnel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QualtyPersonnelDAO {
    int insert(QualtyPersonnel record);

    int insertSelective(QualtyPersonnel record);

    /**
     * 查询比中的质控人员样本信息
     * @return
     */
    QualtyPersonnel selectMixedSampleQualityDetails(String id);

    /**
     * 根据质控人员id查询质控基因信息
     * @param ratiogeneId
     * @return
     */
    List<QualtyPersonnel> selectMixedSampleQuality(String ratiogeneId);

    List<SingleSampleGeneVo> selecAllSampleQualityList(@Param("startRum") int startRum, @Param("endRum") int endRum);

    /**
     * 根据混合id查询质控人员基因信息
     *
     * @param qusltyId
     * @param sampleName
     * @return
     */
    QualtyPersonnel selecByIdSampleQuality(@Param("qusltyId") String qusltyId, @Param("sampleName") String sampleName);

    /**
     * 查询质控人员信息
     * @return
     */
    List<QualtyPersonnel> selectAll(@Param("startRum") int startRum, @Param("endRum") int endRum);

    /**
     * 查询所有质控人员信息
     * @return
     */
    List<QualtyPersonnel> selectAllList();

}