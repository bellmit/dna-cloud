package com.bazl.dna.database.compare.rabbitmq.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.database.algorithm.comparator.StrRelativeComparator;
import com.bazl.dna.database.algorithm.params.StrRelativeCompareParams;
import com.bazl.dna.database.algorithm.result.StrRelativeCompareResult;
import com.bazl.dna.database.compare.rabbitmq.IQuickCompareRelativeSingleConjoinedService;
import com.bazl.dna.database.compare.task.config.RedisUtils;
import com.bazl.dna.database.constants.Constants;
import com.bazl.dna.database.service.model.po.AlleleFrequencyInfo;
import com.bazl.dna.database.service.model.po.CompareParams;
import com.bazl.dna.database.service.model.po.DnaGeneInfoDetail;
import com.bazl.dna.database.service.model.po.QuickCompareQueue;
import com.bazl.dna.database.service.model.po.QuickCompareResultRelative;
import com.bazl.dna.database.service.service.AlleleFrequencyInfoService;
import com.bazl.dna.database.service.service.QuickCompareQueueService;
import com.bazl.dna.database.service.service.QuickCompareResultRelativeService;
import com.bazl.dna.exception.DnaRuntimeException;
import com.bazl.dna.util.GeneTransFormUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Service
public class QuickCompareRelativeSingleConjoinedServiceImpl implements IQuickCompareRelativeSingleConjoinedService {
	
	private static final Logger LOG = LoggerFactory.getLogger(QuickCompareRelativeSingleConjoinedServiceImpl.class);

	@Autowired
	private QuickCompareQueueService queueService;
	
	@Autowired
	private QuickCompareResultRelativeService resultService;
	
	@Autowired
	private AlleleFrequencyInfoService frequencyInfoService;
	
	@Autowired
	private RedisUtils redisUtils;
	
	@Override
	@Transactional
	public void saveMatchResult(QuickCompareQueue compareQueue, List<AlleleFrequencyInfo> frequencyList, 
			StrRelativeCompareParams fmzCompareParams, Integer id, String geneInfo) {
		try {
			LOG.info("Quick Compare Relative Single: {}", id);
			List<DnaGeneInfoDetail> geneInfoList = JSONObject.parseArray(GeneTransFormUtils.geneFormatString2(geneInfo), DnaGeneInfoDetail.class);
			
			String relationa = compareQueue.getPendingSampleaRelation();
	        List<DnaGeneInfoDetail> fatherGeneInfoList = null;
	        List<DnaGeneInfoDetail> motherGeneInfoList = null;
	        List<DnaGeneInfoDetail> childGeneInfoList = null;
	        
	        if (Constants.COMPARE_TARGET_RELATION_FATHER.equals(relationa)) {
		    		// 父
		    		fatherGeneInfoList = JSONObject.parseArray(GeneTransFormUtils.geneFormatString2(compareQueue.getPendingSampleaGeneInfo()), DnaGeneInfoDetail.class);
		    } else if (Constants.COMPARE_TARGET_RELATION_MOTHER.equals(relationa)) { 
		    		// 母
		    		motherGeneInfoList = JSONObject.parseArray(GeneTransFormUtils.geneFormatString2(compareQueue.getPendingSampleaGeneInfo()), DnaGeneInfoDetail.class);
		    } else {
		    		// 子
		    		childGeneInfoList = JSONObject.parseArray(GeneTransFormUtils.geneFormatString2(compareQueue.getPendingSampleaGeneInfo()), DnaGeneInfoDetail.class);
		    }
	        
	        // 目标基因类型
	        // TODO 不能找双亲
	        if (Constants.COMPARE_TARGET_RELATION_FATHER.equals(compareQueue.getTargetRelation())) {
		    		fatherGeneInfoList = geneInfoList;
		    	} else if (Constants.COMPARE_TARGET_RELATION_MOTHER.equals(compareQueue.getTargetRelation())) {
		    		motherGeneInfoList = geneInfoList;
		    	} else {
		    		childGeneInfoList = geneInfoList;
		    	}
	        
	        LOG.info("father:{}", fatherGeneInfoList);
	        LOG.info("child:{}", childGeneInfoList);
	        // 基因信息
	     	StrRelativeCompareResult strRelativeCompareResult = null;
	    		// 单亲比对
			if (fatherGeneInfoList != null) {
	    			// 单亲父比对
	        		strRelativeCompareResult = StrRelativeComparator.singleParentCompare(fatherGeneInfoList, 
	        				childGeneInfoList, fmzCompareParams, frequencyList);
	    		} else {
	    			// 单亲母比对
	        		strRelativeCompareResult = StrRelativeComparator.singleParentCompare(motherGeneInfoList, 
	        				childGeneInfoList, fmzCompareParams, frequencyList);
	    		}
				
			
	        // 比中结果
			if (strRelativeCompareResult != null) {
				LOG.info("Result: {}", id);
				QuickCompareResultRelative matchResult = new QuickCompareResultRelative();
				matchResult.setMatchDatetime(LocalDateTime.now());
				matchResult.setMatchLocusCount(strRelativeCompareResult.getMatchedLocusCount());
				matchResult.setDiffLocusCount(strRelativeCompareResult.getDiffAlleleCount());
				matchResult.setQuickCompareQueueId(compareQueue.getId());
				matchResult.setMatchedGeneDetails(JSONObject.toJSONString(strRelativeCompareResult));
				
				matchResult.setMatchGeneId1(id);
				matchResult.setMatchGeneId2(null);
				resultService.save(matchResult);
				
				// 7.更新比对队列
				compareQueue.setQueueStatus(Constants.COMPARE_STATUS_FINISH);
				compareQueue.setUpdateDatetime(LocalDateTime.now());
				queueService.updateById(compareQueue);
			}
		} catch (Exception e) {
			LOG.error("Error saveMatchResult: ", e);
			throw new DnaRuntimeException();
		}
	}
	
	@Async
	@Override
	@Transactional
	public void compare(QuickCompareQueue compareQueue) {
		try {
			LOG.info("快速比对:开始 亲缘比对 {}", compareQueue.getId());
			
			// 2.获取比对条件
			CompareParams compareParams = JSONObject.parseObject(compareQueue.getCompareParams(), CompareParams.class);
			if (!compareParams.getTargetLabServerNo().isEmpty()) {
				StrRelativeCompareParams compareParamsString = StrRelativeCompareParams.DEFAULT_FMZ_PARAMS();
				compareParamsString.setLowestSameLimit(compareParams.getLowestSameLimit());
				compareParamsString.setMostDiffLimit(compareParams.getMostDiffLimit());
				// 种群
				List<AlleleFrequencyInfo> frequencyList = Lists.newArrayList();
				if (compareQueue.getPopulationFrequencyId() != null) {
					Map<String, Object> frequencyMap = Maps.newHashMap();
					frequencyMap.put("POPULATION_FREQUENCY_ID", compareQueue.getPopulationFrequencyId());
					frequencyList = frequencyInfoService.listByMap(frequencyMap);
				}

				// 3. 获取对应基因型 dnaStrGene 需要优化
				for (String labServerNo : compareParams.getTargetLabServerNo()) {
					String keys = "1-*" + labServerNo + "-*";
					List<String> arrayList = redisUtils.keys(keys);
					LOG.info("Redis keys: {}", arrayList.size());
					for (String key : arrayList) {
						// 5. 按照查询条件进行比对
						// 6. 将比对结果保存在对应的 matchResult
						String geneInfo = redisUtils.get(key);
						String id = StringUtils.substringAfterLast(key, "-");
						saveMatchResult(compareQueue, frequencyList, compareParamsString,
								Integer.parseInt(id), geneInfo);
					}
				}

			}
			// 7.更新比对队列
			compareQueue.setQueueStatus(Constants.COMPARE_STATUS_FINISH);
			compareQueue.setUpdateDatetime(LocalDateTime.now());
			queueService.updateById(compareQueue);
			
			LOG.info("快速比对:完成 亲缘比对 {}", compareQueue.getId());
		} catch (Exception e) {
			LOG.error("Error task: ", e);
			throw new DnaRuntimeException();
		}
	}

}
