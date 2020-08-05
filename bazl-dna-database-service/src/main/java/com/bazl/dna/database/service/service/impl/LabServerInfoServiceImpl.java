package com.bazl.dna.database.service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bazl.dna.database.service.mapper.LabServerInfoMapper;
import com.bazl.dna.database.service.model.po.LabServerInfo;
import com.bazl.dna.database.service.service.LabServerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 实验室服务器信息 服务实现类
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-19
 */
@Service
public class LabServerInfoServiceImpl extends ServiceImpl<LabServerInfoMapper, LabServerInfo> implements LabServerInfoService {
	
    @Autowired
    private LabServerInfoMapper labServerInfoMapper;

    @Override
//    @Cacheable(value = CACHE_NAME + ":selectAll")
    public List<LabServerInfo> selectAll() {
        return labServerInfoMapper.selectAll();
    }

    @Override
    public LabServerInfo selectByOrgCode(String orgId) {
        try {
            return labServerInfoMapper.selectByOrgCode(orgId);
        }catch (Exception ex){
            log.error("invoke ,LabServerInfoService.selectByOrgCode error!"+ex.getMessage());
        }
        return null;
    }
}

