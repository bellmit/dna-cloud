package com.bazl.dna.database.service.mapper;

import com.bazl.dna.database.service.model.po.DnaGeneInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bazl.dna.database.service.model.vo.DnaGeneInfoVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * dna基因信息表 Mapper 接口
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
@Repository
public interface DnaGeneInfoMapper extends BaseMapper<DnaGeneInfo> {

    /**
     * 根据样本id获取基因信息列表
     * @param sampleId
     * @return
     */
    public List<DnaGeneInfoVo> selectVoListBySampleId(Integer sampleId);
}
