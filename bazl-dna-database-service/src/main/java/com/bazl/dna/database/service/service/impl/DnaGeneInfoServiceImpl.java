package com.bazl.dna.database.service.service.impl;

import com.bazl.dna.database.service.model.po.DnaGeneInfo;
import com.bazl.dna.database.service.mapper.DnaGeneInfoMapper;
import com.bazl.dna.database.service.model.vo.DnaGeneInfoVo;
import com.bazl.dna.database.service.service.DnaGeneInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * dna基因信息表 服务实现类
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
@Service
public class DnaGeneInfoServiceImpl extends ServiceImpl<DnaGeneInfoMapper, DnaGeneInfo> implements DnaGeneInfoService {

    Logger logger = LoggerFactory.getLogger(getClass());
    
    private static final String CACHE_NAME = "DnaGeneInfo";

    @Autowired
    private DnaGeneInfoMapper dnaGeneInfoMapper;

    /**
     * 根据样本id获取基因信息列表
     * @param sampleId
     * @return
     */
    @Override
    @Cacheable(value = CACHE_NAME + ":selectVoListBySampleId", key = "#sampleId")
    public List<DnaGeneInfoVo> selectVoListBySampleId(Integer sampleId) {
        try{
            List<DnaGeneInfoVo> list = dnaGeneInfoMapper.selectVoListBySampleId(sampleId);
            return list;
        }catch(Exception ex){
            logger.error("根据样本id获取基因信息列表失败", ex);
            return null;
        }
    }
}
