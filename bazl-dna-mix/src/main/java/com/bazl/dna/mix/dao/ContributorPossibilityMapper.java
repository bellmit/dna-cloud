package com.bazl.dna.mix.dao;

import java.util.List;

import com.bazl.dna.mix.model.po.ContributorPossibility;

public interface ContributorPossibilityMapper {
    int deleteByPrimaryKey(String id);

    int insert(ContributorPossibility record);

    int insertSelective(ContributorPossibility record);

    ContributorPossibility selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ContributorPossibility record);

    int updateByPrimaryKey(ContributorPossibility record);
    
    /**
	 * 按队列id查询
	 * @param compareId
	 * @return
	 */
	List<ContributorPossibility> findListByCompareId(String compareId);
}