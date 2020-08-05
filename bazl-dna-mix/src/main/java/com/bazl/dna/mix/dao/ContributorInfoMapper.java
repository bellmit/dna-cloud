package com.bazl.dna.mix.dao;

import java.util.List;

import com.bazl.dna.mix.model.po.ContributorInfo;

public interface ContributorInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(ContributorInfo record);

    int insertSelective(ContributorInfo record);

    ContributorInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ContributorInfo record);

    int updateByPrimaryKey(ContributorInfo record);
    
    /**
	 * 按样本基因 id查询
	 * @param mixedSampleGeneId
	 * @return
	 */
    List<ContributorInfo> findListByMixSampleGeneId(String mixedSampleGeneId);
}