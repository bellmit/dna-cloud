package com.bazl.dna.database.service.service;

import com.bazl.dna.database.service.model.po.DnaGeneInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bazl.dna.database.service.model.vo.DnaGeneInfoVo;

import java.util.List;

/**
 * <p>
 * dna基因信息表 服务类
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
public interface DnaGeneInfoService extends IService<DnaGeneInfo> {


    /**
     * 根据样本id获取基因信息列表
     * @param sampleId
     * @return
     */
    public List<DnaGeneInfoVo> selectVoListBySampleId(Integer sampleId);

}
