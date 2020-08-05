package com.bazl.dna.lims.core.dao;

import com.bazl.dna.lims.core.model.po.AmTopicPerson;
import java.util.List;

public interface AmTopicPersonMapper {
    int deleteByPrimaryKey(String id);

    int insert(AmTopicPerson record);

    AmTopicPerson selectByPrimaryKey(String id);

    List<AmTopicPerson> selectAll();

    int updateByPrimaryKey(AmTopicPerson record);
}