package com.bazl.dna.database.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bazl.dna.database.service.model.po.PopulationFrequencyInfo;
import com.bazl.dna.database.service.model.qo.PopulationFrequencyQuery;
import com.bazl.dna.database.service.model.vo.PopulationFrequencyInfoVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 种群基因频率信息表 Mapper 接口
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
@Repository
public interface PopulationFrequencyInfoMapper extends BaseMapper<PopulationFrequencyInfo> {

	public List<PopulationFrequencyInfo> findList(@Param("id") Integer id,
			@Param("geneType") String geneType, @Param("populationName") String populationName);

    /**
	 * 分业查询全部种群基因频率信息
	 * @param populationFrequencyQuery
	 * @return
     */
	public List<PopulationFrequencyInfoVo> populationPaginationQuery(PopulationFrequencyQuery populationFrequencyQuery);

	/**
	 * 查询总计全部种群基因频率信息
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
	public List<PopulationFrequencyInfo> selectByGeneType(String geneType);
}
