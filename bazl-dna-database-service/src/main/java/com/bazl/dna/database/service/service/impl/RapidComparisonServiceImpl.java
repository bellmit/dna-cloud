package com.bazl.dna.database.service.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bazl.dna.database.algorithm.comparator.StrRelativeComparator;
import com.bazl.dna.database.algorithm.comparator.StrSameComparator;
import com.bazl.dna.database.algorithm.comparator.YstrComparator;
import com.bazl.dna.database.algorithm.params.StrRelativeCompareParams;
import com.bazl.dna.database.algorithm.params.StrSameCompareParams;
import com.bazl.dna.database.algorithm.params.YstrCompareParams;
import com.bazl.dna.database.algorithm.result.StrRelativeCompareResult;
import com.bazl.dna.database.algorithm.result.StrSameCompareResult;
import com.bazl.dna.database.algorithm.result.YstrCompareResult;
import com.bazl.dna.database.service.mapper.AlleleFrequencyInfoMapper;
import com.bazl.dna.database.service.mapper.PopulationFrequencyInfoMapper;
import com.bazl.dna.database.service.model.po.AlleleFrequencyInfo;
import com.bazl.dna.database.service.model.po.DnaGeneInfoDetail;
import com.bazl.dna.database.service.model.po.KinshipCompare;
import com.bazl.dna.database.service.model.po.PopulationFrequencyInfo;
import com.bazl.dna.database.service.model.po.StrCompare;
import com.bazl.dna.database.service.service.RapidComparisonService;
import com.google.common.collect.Lists;

@Service
@Transactional
@SuppressWarnings("unchecked")
public  class RapidComparisonServiceImpl  implements RapidComparisonService {

	@SuppressWarnings("rawtypes")
	@Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    PopulationFrequencyInfoMapper populationFrequencyInfoMapper;
    @Autowired
    AlleleFrequencyInfoMapper alleleFrequencyInfoMapper;

	@Override
    public ArrayList<StrSameCompareResult> compareStrGeneInfo(StrCompare strCompare) {
//todo log
        if (StringUtils.isNotBlank(strCompare.getGeneInfo()) && StringUtils.isNotBlank(strCompare.getInstoreDataType()) && StringUtils.isNotBlank(strCompare.getLowestSameLimit()) && StringUtils.isNotBlank(strCompare.getMostDiffLimit())){
            ArrayList<StrSameCompareResult> strSameCompareResults = new ArrayList<>();
            Set<String> keys = redisTemplate.keys("*");
            for (String key : keys) {
                String[] split = key.split("-");
                String[] strings = split[1].split("_");
                String instoreDataType  = strings[0];
                String labServer = split[2];
                if (strCompare.getInstoreDataType().contains(instoreDataType) && strCompare.getLabServerInfo().contains(labServer)){
                    String value = (String) redisTemplate.opsForValue().get(key);
                    //获取redis geneinfo
                    List<DnaGeneInfoDetail> geneInfoDetail = JSONObject.parseArray(value, DnaGeneInfoDetail.class);
                    //获取源geneinfo
                    List<DnaGeneInfoDetail> geneInfoDetail1 = JSONObject.parseArray(strCompare.getGeneInfo(), DnaGeneInfoDetail.class);
                    PopulationFrequencyInfo populationFrequencyInfo = populationFrequencyInfoMapper.selectOne(new QueryWrapper<PopulationFrequencyInfo>().eq("POPULATION_NAME", strCompare.getPopulationName()));
                    List<AlleleFrequencyInfo> alleleFrequencyInfoList = alleleFrequencyInfoMapper.selectList(new QueryWrapper<AlleleFrequencyInfo>().eq("ID", populationFrequencyInfo.getId()));
                    StrSameCompareParams strSameCompareParams = new StrSameCompareParams(
                    		Integer.valueOf(strCompare.getLowestSameLimit()), Integer.valueOf(strCompare.getMostDiffLimit()));
                    StrSameCompareResult strSameCompareResult = StrSameComparator.sameCompareIncludeLikelihood(geneInfoDetail, geneInfoDetail1, strSameCompareParams, alleleFrequencyInfoList);
                    strSameCompareResults.add(strSameCompareResult);
                }
            }
            return strSameCompareResults;
        }

        return Lists.newArrayList();
    }

    @Override
    public ArrayList<StrRelativeCompareResult> compareKinshipGeneInfo(KinshipCompare kinshipCompare) {

        ArrayList<StrRelativeCompareResult> strRelativeCompareResults = new ArrayList<>();
        Set<String> keys = redisTemplate.keys("*");
        for (String key : keys) {
            String[] split = key.split("-");
            String[] strings = split[1].split("_");
            String instoreDataType  = strings[0];
            String labServer = split[2];
            if (kinshipCompare.getInstoreDataType().contains(instoreDataType) && kinshipCompare.getLabServerInfo().contains(labServer)){
                String value = (String) redisTemplate.opsForValue().get(key);
                //获取redis geneinfo
                PopulationFrequencyInfo populationFrequencyInfo = populationFrequencyInfoMapper.selectOne(new QueryWrapper<PopulationFrequencyInfo>().eq("POPULATION_NAME", kinshipCompare.getPopulationName()));
                List<AlleleFrequencyInfo> alleleFrequencyInfoList = alleleFrequencyInfoMapper.selectList(new QueryWrapper<AlleleFrequencyInfo>().eq("ID", populationFrequencyInfo.getId()));
                List<DnaGeneInfoDetail> childGeneInfoList = JSONObject.parseArray(value, DnaGeneInfoDetail.class);
                List<DnaGeneInfoDetail> fatherGeneInfoList = JSONObject.parseArray(kinshipCompare.getFatherGeneInfo(), DnaGeneInfoDetail.class);
                List<DnaGeneInfoDetail> motherGeneInfoList = JSONObject.parseArray(kinshipCompare.getMotherGeneInfo(), DnaGeneInfoDetail.class);
                StrRelativeCompareParams strRelativeCompareParams = new StrRelativeCompareParams(
                		Integer.valueOf(kinshipCompare.getLowestSameLimit()), Integer.valueOf(kinshipCompare.getMostDiffLimit()));
                StrRelativeCompareResult strRelativeCompareResult = StrRelativeComparator.fmzCompare(fatherGeneInfoList, motherGeneInfoList, childGeneInfoList, strRelativeCompareParams, alleleFrequencyInfoList);
                strRelativeCompareResults.add(strRelativeCompareResult);
            }
        }
        return strRelativeCompareResults;
    }


    @Override
    public ArrayList<YstrCompareResult> compareYstrGeneInfo(StrCompare strCompare) {
        if (StringUtils.isNotBlank(strCompare.getGeneInfo()) && StringUtils.isNotBlank(strCompare.getInstoreDataType()) && StringUtils.isNotBlank(strCompare.getLowestSameLimit()) && StringUtils.isNotBlank(strCompare.getMostDiffLimit())){
            ArrayList<YstrCompareResult> ystrCompareResults = new ArrayList<>();
            Set<String> keys = redisTemplate.keys("*");
            for (String key : keys) {
                String[] split = key.split("-");
                String[] strings = split[1].split("_");
                String instoreDataType  = strings[0];
                String labServer = split[2];
                if (strCompare.getInstoreDataType().contains(instoreDataType) && strCompare.getLabServerInfo().contains(labServer)){
                    String value = (String) redisTemplate.opsForValue().get(key);
                    //获取redis geneinfo
                    List<DnaGeneInfoDetail> geneInfoDetail = JSONObject.parseArray(value, DnaGeneInfoDetail.class);
                    //获取源geneinfo
                    List<DnaGeneInfoDetail> geneInfoDetail1 = JSONObject.parseArray(strCompare.getGeneInfo(), DnaGeneInfoDetail.class);
                    YstrCompareParams ystrCompareParams = new YstrCompareParams(
                    		Integer.valueOf(strCompare.getLowestSameLimit()), Integer.valueOf(strCompare.getMostDiffLimit()));
                    YstrCompareResult ystrCompareResult = YstrComparator.ystrCompare(geneInfoDetail, geneInfoDetail1, ystrCompareParams);
                    ystrCompareResults.add(ystrCompareResult);
                }
            }
            return ystrCompareResults;
        }
        return Lists.newArrayList();
    }
}
