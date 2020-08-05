package com.bazl.dna.database.service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bazl.dna.database.service.mapper.AlleleFrequencyInfoMapper;
import com.bazl.dna.database.service.mapper.PopulationFrequencyInfoMapper;
import com.bazl.dna.database.service.model.bo.FrequencyInfoModel;
import com.bazl.dna.database.service.model.po.AlleleFrequencyInfo;
import com.bazl.dna.database.service.model.po.DnaLocusInfo;
import com.bazl.dna.database.service.model.po.PopulationFrequencyInfo;
import com.bazl.dna.database.service.model.vo.AlleleFrequencyInfoVo;
import com.bazl.dna.database.service.service.AlleleFrequencyInfoService;
import com.bazl.dna.database.service.service.DnaLocusInfoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 等位基因频率信息表 服务实现类
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
@Service
public class AlleleFrequencyInfoServiceImpl extends ServiceImpl<AlleleFrequencyInfoMapper, AlleleFrequencyInfo>
        implements AlleleFrequencyInfoService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private static final String CACHE_NAME = "AlleleFrequencyInfo";

    @Autowired
    private AlleleFrequencyInfoMapper alleleFrequencyInfoMapper;

    @Autowired
    private PopulationFrequencyInfoMapper populationFrequencyInfoMapper;

    @Autowired
    private DnaLocusInfoService dnaLocusInfoService;

    /**
     * 查询默认的种群基因频率列表
     * @return
     */
    @Override
//    @Cacheable(value = CACHE_NAME + ":listOfDefaultPopulation")
    public List<AlleleFrequencyInfo> listOfDefaultPopulation(){
        try{
            List<AlleleFrequencyInfo> list = this.getBaseMapper().listOfDefaultPopulation();
            return list;
        }catch(Exception ex){
            logger.error("invoke AlleleFrequencyInfoService.listOfDefaultPopulation error.", ex);
            return null;
        }
    }

    @Override
    public List<AlleleFrequencyInfoVo> listByPopulationId(Integer populationId){
        return alleleFrequencyInfoMapper.listByPopulationId(populationId);
    }

    @Override
    public List<AlleleFrequencyInfo> listByPopulationIdAndLocusName(Integer populationId,String locusName) {
        return alleleFrequencyInfoMapper.listByPopulationIdAndLocusName(populationId,locusName);
    }

    @Override
    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public void deleteAlleleFrequency(Integer populationFrequencyId, String locusName) {
        try {
            AlleleFrequencyInfo alleleFrequencyInfo = new AlleleFrequencyInfo();
            alleleFrequencyInfo.setLocusName(locusName);
            alleleFrequencyInfo.setPopulationFrequencyId(populationFrequencyId);
            alleleFrequencyInfoMapper.deleteAlleleFrequency(populationFrequencyId,locusName);
        }catch (Exception ex){
            logger.error("invoke AlleleFrequencyInfoService.deleteAlleleFrequency error.",ex);
        }
    }

    @Override
    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public void deleteAlleleFrequencyById(Integer alleleFrequencyId) {
        try {
            alleleFrequencyInfoMapper.deleteAlleleFrequencyById(alleleFrequencyId);
        }catch (Exception ex){
            logger.error("invoke AlleleFrequencyInfoService.deleteAlleleFrequencyById error.",ex);
        }
    }

    @Override
    @SuppressWarnings("all")
    public boolean saveFrequency(FrequencyInfoModel frequencyInfoModel)  throws  Exception {
        if (frequencyInfoModel != null && frequencyInfoModel.getPopulationFrequencyInfo() != null) {
            try {
                //新增种群方案信息
                if (null!=frequencyInfoModel.getPopulationFrequencyInfo()) {
                    PopulationFrequencyInfo populationFrequencyInfo = frequencyInfoModel.getPopulationFrequencyInfo();
                    if (null !=frequencyInfoModel.getPopulationFrequencyInfo().getId()) { //update
                            PopulationFrequencyInfo populationFrequencyInfoAdd = frequencyInfoModel.getPopulationFrequencyInfo();  //修改种群频率信息接口
                            populationFrequencyInfoMapper.updateById(populationFrequencyInfoAdd); //修改种群信息
                            List<AlleleFrequencyInfoVo> alleleFrequencyInfos = frequencyInfoModel.getAlleleFrequencyInfoList();
                            if (null!=alleleFrequencyInfos  && !alleleFrequencyInfos.isEmpty()) {
                                for (AlleleFrequencyInfoVo alleleFrequencyInfo : alleleFrequencyInfos) {
                                    List<AlleleFrequencyInfo> alleleFrequencyInfoVos = alleleFrequencyInfo.getAlleleFrequencyInfoList();
                                    for (AlleleFrequencyInfo allel : alleleFrequencyInfoVos) {
                                        if (allel.getPopulationFrequencyId() == null) {
                                            allel.setPopulationFrequencyId(populationFrequencyInfo.getId());
                                        }
                                        if (allel.getId() == null){ //ID为空新增
                                          if(null==allel.getLocusId()&& StringUtils.isNotBlank(allel.getLocusName())){
                                             DnaLocusInfo locusInfo = dnaLocusInfoService.selectByLocusName(allel.getLocusName());
                                             if (locusInfo!=null){
                                                  allel.setLocusId(locusInfo.getId());//基因座ID
                                              }
                                            }
                                        }
                                        alleleFrequencyInfoMapper.insert(allel);
                                        if (allel.getId()!= null) { //ID不为空时修改
                                         if(null==allel.getLocusId()&& StringUtils.isNotBlank(allel.getLocusName())){
                                             DnaLocusInfo locusInfo = dnaLocusInfoService.selectByLocusName(allel.getLocusName());
                                             if (locusInfo!=null){
                                                 allel.setLocusId(locusInfo.getId());//基因座ID
                                             }
                                          }
                                        }
                                        alleleFrequencyInfoMapper.updateById(allel);
                                    }
                                }
                            }
                     }else{ //新增种群频率信息接口
                        if (populationFrequencyInfo.getPopulationName()==null){  //种群名称
                            populationFrequencyInfo.setPopulationName("无");
                        }
                        if (populationFrequencyInfo.getLocusCount()!=0){
                            populationFrequencyInfo.setLocusCount(populationFrequencyInfo.getLocusCount());//基因座数目
                        }
                        populationFrequencyInfoMapper.insert(populationFrequencyInfo);
                        //新增种群频率信息
                    if (null!=populationFrequencyInfo){
                        List<AlleleFrequencyInfoVo> alleleFrequencyInfos = frequencyInfoModel.getAlleleFrequencyInfoList();
                        if (null != alleleFrequencyInfos && !alleleFrequencyInfos.isEmpty()) {
                            for (AlleleFrequencyInfoVo alleleFrequencyInfo : alleleFrequencyInfos) {
                                List<AlleleFrequencyInfo> alleleFrequencyInfoVos = alleleFrequencyInfo.getAlleleFrequencyInfoList();
                                for (AlleleFrequencyInfo allel : alleleFrequencyInfoVos) {
                                    if (allel.getPopulationFrequencyId() == null) {
                                        allel.setPopulationFrequencyId(populationFrequencyInfo.getId());
                                    }
                                    if (allel.getId() == null){ //ID为空时新增
                                        if (null==allel.getLocusId()&&StringUtils.isNotBlank(allel.getLocusName())){
                                            DnaLocusInfo locusInfo = dnaLocusInfoService.selectByLocusName(allel.getLocusName());
                                            if (null!=locusInfo){
                                                allel.setLocusId(locusInfo.getId());
                                            }
                                        }
                                    }
                                    alleleFrequencyInfoMapper.insert(allel);
                                    if (allel.getId()!= null){ //ID不为空时修改
                                        if (null==allel.getLocusId()&&StringUtils.isNotBlank(allel.getLocusName())){
                                            DnaLocusInfo locusInfo = dnaLocusInfoService.selectByLocusName(allel.getLocusName());
                                            if (null!=locusInfo){
                                                allel.setLocusId(locusInfo.getId());
                                            }
                                        }
                                    }
                                    alleleFrequencyInfoMapper.updateById(allel);
                                    }
                                }
                            }
                        }
                    }
                }

            }catch (Exception ex) {
                logger.error("invoke AlleleFrequencyInfoService.saveFrequency error", ex);
            }
        }
        return false;
    }
}
