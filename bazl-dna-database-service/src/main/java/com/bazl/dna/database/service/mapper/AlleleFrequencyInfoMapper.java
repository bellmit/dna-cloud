package com.bazl.dna.database.service.mapper;

import com.bazl.dna.database.service.model.po.AlleleFrequencyInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bazl.dna.database.service.model.vo.AlleleFrequencyInfoVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 等位基因频率信息表 Mapper 接口
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
@Repository
public interface AlleleFrequencyInfoMapper extends BaseMapper<AlleleFrequencyInfo> {

    /**
     * 获取默认种群的等位基因频率集合
     * @return
     */
    public List<AlleleFrequencyInfo> listOfDefaultPopulation();
    //根据种群id获取等位基因频率列表
    List<AlleleFrequencyInfoVo> listByPopulationId(Integer populationId);
    //根据种群id和基因座id获取其等位基因和频率
    List<AlleleFrequencyInfo> listByPopulationIdAndLocusName(@Param("populationId") Integer populationId,@Param("locusName") String locusName);
    //删除等位基因和频率
    void deleteAlleleFrequency(@Param("populationFrequencyId") Integer populationFrequencyId,@Param("locusName")  String locusName);
    //删除等位基因和频率根据ID
    void deleteAlleleFrequencyById(Integer alleleFrequencyId);
}
