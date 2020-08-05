package com.bazl.dna.database.service.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bazl.dna.database.service.mapper.CriminalSampleInfoMapper;
import com.bazl.dna.database.service.model.po.CriminalSampleInfo;
import com.bazl.dna.database.service.model.vo.CriminalSampleInfoVo;
import com.bazl.dna.database.service.model.vo.DnaMixGeneInfoVo;
import com.bazl.dna.database.service.service.CriminalSampleInfoService;
import com.google.common.collect.Lists;

/**
 * <p>
 * 建库人员样本表 服务实现类
 * </p>
 *
 * @author lizhihua
 * @since 2020-04-12
 */
@Service
public class CriminalSampleInfoServiceImpl extends ServiceImpl<CriminalSampleInfoMapper, CriminalSampleInfo> implements CriminalSampleInfoService {

    Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private CriminalSampleInfoMapper criminalSampleInfoMapper;

    /**
     * 根据人员id查询样本列表
     * @return
     */
    @Override
    public List<CriminalSampleInfoVo> selectVoListByPersonId(Integer personId) {
        try {
            return criminalSampleInfoMapper.selectVoListByPersonId(personId);
        }catch(Exception ex) {
            logger.error("invoke CriminalSampleInfoService.selectVoListByPersonId error.", ex);
        }
        return Lists.newArrayList();
    }

    @Override
    public List<DnaMixGeneInfoVo> selectVoListByBySampleId(Integer sampleId) {
        try {
            return criminalSampleInfoMapper.selectBySampleIdVoList(sampleId);
        } catch (Exception e) {
            logger.error("根据建库人员样本id查询基因信息错误！" + e.getMessage());
        }
        return Lists.newArrayList();
    }
}
