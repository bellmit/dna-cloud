package com.bazl.dna.database.service.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bazl.dna.database.service.mapper.CaseImageMapper;
import com.bazl.dna.database.service.model.po.CaseImage;
import com.bazl.dna.database.service.service.CaseImageService;

/**
 * <p>
 * 案件照片信息表 服务实现类
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
@Service
public class CaseImageServiceImpl extends ServiceImpl<CaseImageMapper, CaseImage> implements CaseImageService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private static final String CACHE_NAME = "CaseImage";

    @Autowired
    private CaseImageMapper caseImageMapper;

    /**
     * 根据案件id获取所有案件的现场照片
     * @param caseId
     * @return
     */
    @Override
    @Cacheable(value = CACHE_NAME + ":selectListByCaseId", key = "#caseId")
    public List<CaseImage> selectListByCaseId(Integer caseId) {
        try {
            List<CaseImage> caseImageList = caseImageMapper.selectList(new QueryWrapper<CaseImage>().eq("CASE_ID", caseId));
            return caseImageList;
        }catch (Exception ex) {
            logger.error("根据案件id获取所有案件的现场照片失败！", ex);
            return null;
        }
    }
}
