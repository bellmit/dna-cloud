package com.bazl.dna.mix.service;

import java.util.List;

import com.bazl.dna.mix.model.po.ContributorPossibility;

public interface ContributorPossibilityService {

	/**
	 * 按队列id查询
	 * @param compareId
	 * @return
	 */
	List<ContributorPossibility> findListByCompareId(String compareId);

}
