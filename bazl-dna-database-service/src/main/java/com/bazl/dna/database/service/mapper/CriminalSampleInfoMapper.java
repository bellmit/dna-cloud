package com.bazl.dna.database.service.mapper;

import com.bazl.dna.database.service.model.po.CriminalSampleInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bazl.dna.database.service.model.vo.CriminalSampleInfoVo;
import com.bazl.dna.database.service.model.vo.DnaMixGeneInfoVo;

import java.util.List;

/**
 * <p>
 * 建库人员样本表 Mapper 接口
 * </p>
 *
 * @author lizhihua
 * @since 2020-04-12
 */
public interface CriminalSampleInfoMapper extends BaseMapper<CriminalSampleInfo> {

    /**
     * 根据人员id查询样本列表
     * @return
     */
    public List<CriminalSampleInfoVo> selectVoListByPersonId(Integer personId);

    //通过主键id查询基因信息
    public List<DnaMixGeneInfoVo> selectBySampleIdVoList(Integer personId);
}
