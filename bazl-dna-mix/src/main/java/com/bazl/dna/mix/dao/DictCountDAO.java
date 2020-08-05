package com.bazl.dna.mix.dao;


import com.bazl.dna.mix.model.po.DictCount;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DictCountDAO {
    int deleteByPrimaryKey(String id);

    int insert(DictCount record);

    DictCount selectByPrimaryKey(String id);

    List<DictCount> selectAll();

    int updateByPrimaryKey(DictCount record);

    DictCount selectMatchedSuspectCount(String dictTypeCode);

    void updateDictCount(DictCount dictCount);

    DictCount selectByYears(DictCount dictCount);
}