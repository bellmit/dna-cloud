package com.bazl.dna.database.service.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bazl.dna.database.service.model.po.CriminalSampleInfo;
import com.bazl.dna.database.service.model.vo.CriminalSampleInfoVo;
import com.bazl.dna.database.service.model.vo.DnaMixGeneInfoVo;

/**
 * <p>
 * 建库人员样本表 服务类
 * </p>
 *
 * @author lizhihua
 * @since 2020-04-12
 */
public interface CriminalSampleInfoService extends IService<CriminalSampleInfo> {


    /**
     * 根据人员id查询样本列表
     * @return
     */
    public List<CriminalSampleInfoVo> selectVoListByPersonId(Integer personId);

    /*
    *   通过主键id查询基因信息
    * */
    public List<DnaMixGeneInfoVo> selectVoListByBySampleId(Integer sampleId);
}
