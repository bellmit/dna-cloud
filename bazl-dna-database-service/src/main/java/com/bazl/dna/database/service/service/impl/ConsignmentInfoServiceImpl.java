package com.bazl.dna.database.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bazl.dna.database.constants.Constants;
import com.bazl.dna.database.service.model.po.ConsignmentInfo;
import com.bazl.dna.database.service.mapper.ConsignmentInfoMapper;
import com.bazl.dna.database.service.model.vo.ConsignmentInfoVo;
import com.bazl.dna.database.service.service.ConsignmentInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 委托信息表 服务实现类
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
@Service
public class ConsignmentInfoServiceImpl extends ServiceImpl<ConsignmentInfoMapper, ConsignmentInfo> implements ConsignmentInfoService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private static final String CACHE_NAME = "ConsignmentInfo";

    @Autowired
    private ConsignmentInfoMapper consignmentInfoMapper;

    @Override
    @Cacheable(value = CACHE_NAME + ":selectByPrimaryKey", key = "#id")
    public ConsignmentInfo selectByPrimaryKey(Integer id) {
        return consignmentInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public ConsignmentInfoVo selectByIdVo(Integer id) {
        return consignmentInfoMapper.selectById(id);
    }

    @Override
    public int selectByPrimaryKeyCount(String id){
        return consignmentInfoMapper.selectByPrimaryKeyCount(id);
    }

    /**
     * 根据案件id查询主委托对象
     * @param caseId
     * @return
     */
    @Override
    @Cacheable(value = CACHE_NAME + ":selectMainConsignmentByCaseId", key = "#caseId")
    public ConsignmentInfo selectMainConsignmentByCaseId(Integer caseId) {
        try {
            ConsignmentInfo consignmentInfo = consignmentInfoMapper.selectOne(new QueryWrapper<ConsignmentInfo>().eq("CASE_ID", caseId).eq("APPEND_FLAG", Constants.FLAG_FALSE_INT));
            return consignmentInfo;
        }catch (Exception ex) {
            logger.error("根据案件id查询主委托对象失败！", ex);
            return null;
        }
    }

}
