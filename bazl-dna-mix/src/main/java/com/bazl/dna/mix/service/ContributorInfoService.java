package com.bazl.dna.mix.service;

import java.util.List;

import com.bazl.dna.mix.model.po.ContributorInfo;



public interface ContributorInfoService {

    int deleteByPrimaryKey(String id);

    int insert(ContributorInfo record);

    int insertSelective(ContributorInfo record);

    ContributorInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ContributorInfo record);

    int updateByPrimaryKey(ContributorInfo record);
    
    List<ContributorInfo> findListByMixSampleGeneId(String mixedSampleGeneId);
}
