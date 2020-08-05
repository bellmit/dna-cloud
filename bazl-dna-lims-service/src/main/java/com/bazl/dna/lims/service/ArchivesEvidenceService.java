package com.bazl.dna.lims.service;


import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bazl.dna.lims.model.po.DnaEvidenceCount;


/**
 * <p>
 *档案与物证信息表
 *档案与物证服务层
 * </p>
 *
 * @author liuchang
 * @since 2020-07-30
 */

public interface ArchivesEvidenceService extends IService<DnaEvidenceCount> {

    /**
     * 查询全部物证检材信息集合
     * @return
     */
    public List<DnaEvidenceCount> selectAllCount();
}
