package com.bazl.dna.database.nation.converter.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bazl.dna.database.nation.converter.dao.LocusInfoMapper;
import com.bazl.dna.database.nation.converter.model.po.LocusInfo;
import com.bazl.dna.database.nation.converter.service.LocusInfoService;


@Service
public class LocusInfoServiceImpl extends BaseService implements LocusInfoService {

    @Autowired
    LocusInfoMapper locusInfoMapper;


    /**
     * 查询所有STR数据
     * @return
     */
    @Override
    public List<LocusInfo> selectByLocusType(String locusType) {
        try {
            return locusInfoMapper.selectByLocusType(locusType);
        }catch(Exception ex){
            logger.error("invoke LocusInfoService.selectByLocusType error.", ex);
            return null;
        }
    }

    public List<String> selectNameByLocusType(String locusType){
        try {
            return locusInfoMapper.selectNameByLocusType(locusType);
        }catch(Exception ex){
            logger.error("invoke LocusInfoService.selectNameByLocusType error.", ex);
            return null;
        }
    }

}
