package com.bazl.dna.lims.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bazl.dna.lims.model.po.DnaEvidenceCount;

/**
 * Created by Administrator on 2018/12/21.
 */
@Repository
public interface ArchivesEvidenceMapper extends BaseMapper<DnaEvidenceCount> {

    /**
     * 查询全部物证和检材信息数据
     * liuchang
     * 2020-07-30
     * @return
     */
    public List<DnaEvidenceCount> selectAllCount();
}
