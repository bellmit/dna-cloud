package com.bazl.dna.database.service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bazl.dna.database.service.mapper.DnaLocusInfoMapper;
import com.bazl.dna.database.service.mapper.DnaPanelLocusMapper;
import com.bazl.dna.database.service.model.po.*;
import com.bazl.dna.database.service.mapper.DnaPanelInfoMapper;
import com.bazl.dna.database.service.model.qo.DnaPanelInfoQuery;
import com.bazl.dna.database.service.service.DnaPanelInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 试剂盒信息表 服务实现类
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
@Service
@Transactional
public class DnaPanelInfoServiceImpl extends ServiceImpl<DnaPanelInfoMapper, DnaPanelInfo> implements DnaPanelInfoService {
	
	private static final String CACHE_NAME = "DnaPanelInfo";

    @Autowired
    private DnaPanelInfoMapper dnaPanelInfoMapper;
    @Autowired
    private DnaPanelLocusMapper dnaPanelLocusMapper;
    @Autowired
    private DnaLocusInfoMapper dnaLocusInfoMapper;

    @Override
    @Cacheable(value = CACHE_NAME + ":panelInfoQuery", keyGenerator="keyGenerator")
    public List<PanelInfoQuery> panelInfoQuery(DnaPanelInfo dnaPanelInfo) {
        ArrayList<PanelInfoQuery> panelInfoQueries = new ArrayList<>();
        if (dnaPanelInfo != null){
            List<DnaPanelInfo> dnaPanelInfoList =  dnaPanelInfoMapper.panelInfoQueryList(dnaPanelInfo);
            for (DnaPanelInfo panelInfo : dnaPanelInfoList) {
                DnaPanelLocus panelId = dnaPanelLocusMapper.selectOne(new QueryWrapper<DnaPanelLocus>().eq("PANEL_ID", panelInfo.getId()));
                DnaLocusInfo dnaLocusInfo = dnaLocusInfoMapper.selectOne(new QueryWrapper<DnaLocusInfo>().eq("id", panelId.getLocusId()));
                PanelInfoQuery panelInfoQuery = new PanelInfoQuery();
                panelInfoQuery.setPanelName(panelInfo.getPanelName());
                panelInfoQuery.setCoreLocusFlag(panelId.getLocusOrd().toString());
                panelInfoQuery.setLocusOrd(dnaLocusInfo.getCoreLocusFlag().toString());
                panelInfoQueries.add(panelInfoQuery);
            }
        }
        return panelInfoQueries;
    }

    @Override
    @Cacheable(value = CACHE_NAME + ":selectById", key = "#dnaPanelId")
    public DnaPanelInfo selectById(String dnaPanelId) {
        return dnaPanelInfoMapper.selectOne(new QueryWrapper<DnaPanelInfo>().eq("id", dnaPanelId));
    }

    @Override
    public List<DnaPanelInfo> dnaPanelInfoPaginationQuery(DnaPanelInfoQuery dnaPanelInfoQuery) {
        try{
            return dnaPanelInfoMapper.panelPaginationQuery(dnaPanelInfoQuery);
        }catch (Exception ex){
            log.error("invoke DnaPanelInfoService.dnaLocusInfoPaginationQuery.error! ",ex);
            return null;
        }

    }

    @Override
    public int insert(DnaPanelInfo panelInfo) {
        return dnaPanelInfoMapper.insert(panelInfo);
    }

    @Override
    public List<DnaPanelInfo> panelInfoList(DnaPanelInfo panelInfo) {
        return dnaPanelInfoMapper.panelInfoQueryList(panelInfo);
    }


    @Override
    public int panelPaginationCount(DnaPanelInfoQuery dnaPanelInfoQuery) {
        try{
            int count = dnaPanelInfoMapper.panelPaginationCount(dnaPanelInfoQuery);
            return count;
        }catch (Exception ex){
            log.error("invoke DnaPanelInfoService.panelPaginationCount.error! ",ex);
            return 0;
        }
    }
}
