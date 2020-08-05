package com.bazl.dna.lims.core.dao;

import com.bazl.dna.lims.core.model.po.LimsSampleInfoDna;
import org.springframework.stereotype.Repository;

@Repository
public interface NewLimsSampleInfoDnaMapper {

    /**
     * 更新预实验记录
     * @param sampleInfoDna
     * @return
     */
    int updatePre(LimsSampleInfoDna sampleInfoDna);

}
