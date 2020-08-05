package com.bazl.dna.database.service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bazl.dna.database.service.model.po.PopulationFrequencyInfo;
import com.bazl.dna.database.service.model.qo.PopulationFrequencyQuery;
import com.bazl.dna.database.service.model.vo.PopulationFrequencyInfoVo;

import java.util.List;

/**
 * <p>
 * 种群基因频率信息表 服务类
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
public interface PopulationFrequencyInfoService extends IService<PopulationFrequencyInfo> {

	public List<PopulationFrequencyInfo> findList(Integer id, String geneType, String populationName);

	/**
	 * 分业查询全部种群基因频率信息
	 * @param populationFrequencyQuery
	 * @return
     */
	public List<PopulationFrequencyInfoVo> populationPaginationQuery(PopulationFrequencyQuery populationFrequencyQuery);

    /**
	 * 分业查询总计种群基因频率信息
	 * @param populationFrequencyQuery
	 * @return
     */
	public int populationPaginationCount(PopulationFrequencyQuery populationFrequencyQuery);

	/**
	 * 根据ID查询 种群基因频率信息
	 * @param populationId
	 * @return
     */
	public List<PopulationFrequencyInfoVo> listByPopulationId(Integer populationId);

	/**
	 * 根据种群ID删除种群数据
	 * @param populationId
     */
	public void deleteAlleleFrequencyByPopulationId(Integer populationId);

	/*
	* 	根据基因类型 GENE_TYPE 查询种群频率
	* */
	public List<PopulationFrequencyInfo> selectGeneTypeList(String geneType);

}
