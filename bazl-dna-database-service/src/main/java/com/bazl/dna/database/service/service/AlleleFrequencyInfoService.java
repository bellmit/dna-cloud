package com.bazl.dna.database.service.service;

import com.bazl.dna.database.service.model.bo.FrequencyInfoModel;
import com.bazl.dna.database.service.model.po.AlleleFrequencyInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bazl.dna.database.service.model.vo.AlleleFrequencyInfoVo;

import java.util.List;

/**
 * <p>
 * 等位基因频率信息表 服务类
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
public interface AlleleFrequencyInfoService extends IService<AlleleFrequencyInfo> {

    /**
     * 查询默认的种群基因频率列表
     * @return
     */
    public List<AlleleFrequencyInfo> listOfDefaultPopulation();
    //根据种群id获取等位基因频率列表
    List<AlleleFrequencyInfoVo> listByPopulationId(Integer populationId);
    //根据种群id和基因座id获取其等位基因和频率
    List<AlleleFrequencyInfo> listByPopulationIdAndLocusName(Integer populationId,String locusName);

    /**
     * 删除等位基因信息根据基因座名称和种群ID
     * @param populationFrequencyId
     * @param locusName
     */
    public void deleteAlleleFrequency(Integer populationFrequencyId, String locusName);

    /**
     * 根据ID删除等位基因频率信息
     * @param alleleFrequencyId
     */
    public void deleteAlleleFrequencyById(Integer alleleFrequencyId);

    /**
     * 保存种群基因频率信息
     * @param frequencyInfoModel
     */
    public boolean saveFrequency(FrequencyInfoModel frequencyInfoModel) throws Exception;
}
