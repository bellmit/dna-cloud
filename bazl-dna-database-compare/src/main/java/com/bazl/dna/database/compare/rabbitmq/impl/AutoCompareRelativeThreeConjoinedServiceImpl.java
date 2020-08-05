package com.bazl.dna.database.compare.rabbitmq.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bazl.dna.database.algorithm.comparator.StrRelativeComparator;
import com.bazl.dna.database.algorithm.params.StrRelativeCompareParams;
import com.bazl.dna.database.algorithm.result.StrRelativeCompareResult;
import com.bazl.dna.database.compare.rabbitmq.IAutoCompareRelativeThreeConjoinedService;
import com.bazl.dna.database.compare.task.config.RedisUtils;
import com.bazl.dna.database.constants.Constants;
import com.bazl.dna.database.service.model.po.AlleleFrequencyInfo;
import com.bazl.dna.database.service.model.po.AutoCompareQueue;
import com.bazl.dna.database.service.model.po.CompareParams;
import com.bazl.dna.database.service.model.po.DnaGeneInfoDetail;
import com.bazl.dna.database.service.model.po.DnaSampleInfo;
import com.bazl.dna.database.service.model.po.DnaStrGeneInfo;
import com.bazl.dna.database.service.model.po.MatchResultRelative;
import com.bazl.dna.database.service.model.po.PersonRelativeInfo;
import com.bazl.dna.database.service.service.AlleleFrequencyInfoService;
import com.bazl.dna.database.service.service.AutoCompareQueueService;
import com.bazl.dna.database.service.service.DnaSampleInfoService;
import com.bazl.dna.database.service.service.DnaStrGeneInfoService;
import com.bazl.dna.database.service.service.MatchResultRelativeService;
import com.bazl.dna.database.service.service.PersonRelationInfoService;
import com.bazl.dna.exception.DnaRuntimeException;
import com.bazl.dna.util.GeneTransFormUtils;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;

@Service
public class AutoCompareRelativeThreeConjoinedServiceImpl implements IAutoCompareRelativeThreeConjoinedService {
	
	private static final Logger LOG = LoggerFactory.getLogger(AutoCompareRelativeThreeConjoinedServiceImpl.class);
	
	@Autowired
	private DnaStrGeneInfoService geneInfoService;
	
	@Autowired
	private MatchResultRelativeService resultService;
	
	@Autowired
	private DnaSampleInfoService sampleInfoService;
	
	@Autowired
	private PersonRelationInfoService personRelationInfoService;
	
	@Autowired
	private AutoCompareQueueService queueService;
	
	@Autowired
	private AlleleFrequencyInfoService frequencyInfoService;
	
	@Autowired
	private RedisUtils redisUtils;

	@Override
	@Transactional
	public void saveMatchResult(AutoCompareQueue compareQueue, List<AlleleFrequencyInfo> frequencyList,
			StrRelativeCompareParams fmzCompareParams, Integer id, Integer sampleId, String geneInfo) {
		try {
			LOG.info("Auto Compare Relative Three: {}", id);
			// 目标基因
			List<DnaGeneInfoDetail> geneInfoList = JSONObject.parseArray(GeneTransFormUtils.geneFormatString2(geneInfo), DnaGeneInfoDetail.class);
			// 队列的基因
			DnaStrGeneInfo compareGeneInfo = geneInfoService.getById(compareQueue.getGeneId());
			DnaSampleInfo compareSampleInfo = sampleInfoService.getById(compareGeneInfo.getSampleId());
			
			Map<String, Object> targetRelationMap = Maps.newHashMap();
			targetRelationMap.put("RELATION_PERSON_UUID", compareSampleInfo.getRefPersonId());
			List<PersonRelativeInfo> targetRelationInfoList = personRelationInfoService.listByMap(targetRelationMap);
			
			// 获取亲缘关系 多组就是多组比对
			for (PersonRelativeInfo targetRelationInfo : targetRelationInfoList) {
				// 获取目标亲缘关系
				String compareRelation = null; // 比对类型 父母子 
				String targetRelation = null; // 目标类型
				
				String relationb = null; // 三联体比对类型 父母子
				String relationbGeneInfo = null; // 三联体基因
				DnaSampleInfo sampleb = null;
				Set<String> fmSet = Sets.newHashSet();
				Set<String> targetSet = Sets.newHashSet();
				fmSet.add(Constants.COMPARE_TARGET_RELATION_FATHER);
				fmSet.add(Constants.COMPARE_TARGET_RELATION_MOTHER);
				
				Map<String, Object> relationMap = Maps.newHashMap();
				relationMap.put("TARGET_PERSON_UUID", targetRelationInfo.getTargetPersonUuid());
				List<PersonRelativeInfo> relationList = personRelationInfoService.listByMap(relationMap);
				for (PersonRelativeInfo relation : relationList) {
					if (targetRelationInfo.getRelationPersonUuid().equals(relation.getRelationPersonUuid())) {
						compareRelation = relation.getRelationTypeCode();
					} else {
						relationb = relation.getRelationTypeCode();
						sampleb = sampleInfoService.getOne(new QueryWrapper<DnaSampleInfo>().eq("REF_PERSON_ID", relation.getRelationPersonUuid()));
						relationbGeneInfo = geneInfoService.getOne(new QueryWrapper<DnaStrGeneInfo>().eq("SAMPLE_ID", sampleb.getId())).getGeneInfo();
					}
					targetSet.add(relation.getRelationTypeCode());
				}
				
				LOG.info("比对样本基因:{} sample:{}", compareQueue.getGeneId(), compareSampleInfo.getId());
				LOG.info(" 关系为：{} - 亲缘关系为：{} 亲缘样本: {}", compareRelation, relationb, sampleb);
				LOG.info("可能比中样本基因:{} sample:{}", id, sampleId);
				SetView<String> diffSet = Sets.difference(fmSet, targetSet);
				if (!diffSet.isEmpty() && relationb != null) {
					targetRelation = Iterables.getFirst(diffSet, "");
				}
				List<DnaGeneInfoDetail> fatherGeneInfoList = null;
		        List<DnaGeneInfoDetail> motherGeneInfoList = null;
		        List<DnaGeneInfoDetail> childGeneInfoList = null;
		        Integer sampleFatherId = null;
		        Integer sampleMotherId = null;
		        Integer sampleChildId = null;
		        
				if (Constants.COMPARE_TARGET_RELATION_FATHER.equals(compareRelation)) {
			    		// 父
			    		fatherGeneInfoList = JSONObject.parseArray(GeneTransFormUtils.geneFormatString2(compareGeneInfo.getGeneInfo()), DnaGeneInfoDetail.class);
			    		sampleFatherId = compareGeneInfo.getSampleId();
			    } else if (Constants.COMPARE_TARGET_RELATION_MOTHER.equals(compareRelation)) { 
			    		// 母
			    		motherGeneInfoList = JSONObject.parseArray(GeneTransFormUtils.geneFormatString2(compareGeneInfo.getGeneInfo()), DnaGeneInfoDetail.class);
			    		sampleMotherId = compareGeneInfo.getSampleId();
			    } else {
			    		// 子
			    		childGeneInfoList = JSONObject.parseArray(GeneTransFormUtils.geneFormatString2(compareGeneInfo.getGeneInfo()), DnaGeneInfoDetail.class);
			    		sampleChildId = compareGeneInfo.getSampleId();
			    }
		        
		        // 目标基因类型
		        if (Constants.COMPARE_TARGET_RELATION_FATHER.equals(targetRelation)) {
			    		fatherGeneInfoList = geneInfoList;
			    		sampleFatherId = sampleId;
			    	} else if (Constants.COMPARE_TARGET_RELATION_MOTHER.equals(targetRelation)) {
			    		motherGeneInfoList = geneInfoList;
			    		sampleMotherId = sampleId;
			    	} else {
			    		childGeneInfoList = geneInfoList;
			    		sampleChildId = sampleId;
			    	}
		        
		        // 基因信息
		     	StrRelativeCompareResult strRelativeCompareResult = null;
		        if (relationb != null) {
		        		// 三联体比对
			        	if (Constants.COMPARE_TARGET_RELATION_FATHER.equals(relationb)) {
			        		// 父
			        		fatherGeneInfoList = JSONObject.parseArray(GeneTransFormUtils.geneFormatString2(relationbGeneInfo), DnaGeneInfoDetail.class);
			        		sampleFatherId = sampleb.getId();
			        } else if (Constants.COMPARE_TARGET_RELATION_MOTHER.equals(relationb)) { 
			        		// 母
			        		motherGeneInfoList = JSONObject.parseArray(GeneTransFormUtils.geneFormatString2(relationbGeneInfo), DnaGeneInfoDetail.class);
			        		sampleMotherId = sampleb.getId();
			        } else {
			        		// 子
			        		childGeneInfoList = JSONObject.parseArray(GeneTransFormUtils.geneFormatString2(relationbGeneInfo), DnaGeneInfoDetail.class);
			        		sampleChildId = sampleb.getId();
			        }
			        	
			        	LOG.info("F: {} M: {} Z: {}", sampleFatherId, sampleMotherId, sampleChildId);
			        	strRelativeCompareResult = StrRelativeComparator.fmzCompare(
			    				fatherGeneInfoList, motherGeneInfoList, childGeneInfoList,
			    				fmzCompareParams, frequencyList);
		        } else {
		        		// 单亲比对
					if (fatherGeneInfoList != null) {
		        		// 单亲父比对
						LOG.info("F: {} Z: {}", sampleFatherId, sampleChildId);
	            		strRelativeCompareResult = StrRelativeComparator.singleParentCompare(fatherGeneInfoList, 
	            				childGeneInfoList, fmzCompareParams, frequencyList);
	        		} else {
	        			// 单亲母比对
	        			LOG.info("M: {} Z: {}", sampleMotherId, sampleChildId);
	            		strRelativeCompareResult = StrRelativeComparator.singleParentCompare(motherGeneInfoList, 
	            				childGeneInfoList, fmzCompareParams, frequencyList);
	        		}
					
		        }
				
		        // 比中结果
				if (strRelativeCompareResult != null) {
					LOG.info("Result: {}", id);
					MatchResultRelative matchResult = new MatchResultRelative();
					matchResult.setMatchDatetime(LocalDateTime.now());
					matchResult.setMatchLocusCount(strRelativeCompareResult.getMatchedLocusCount());
					matchResult.setDiffLocusCount(strRelativeCompareResult.getDiffAlleleCount());
					matchResult.setCompareQueueId(compareQueue.getId());
					matchResult.setMatchedGeneDetails(JSONObject.toJSONString(strRelativeCompareResult));
					matchResult.setReviewResultCode("0");
					matchResult.setReviewFlag(0);
					matchResult.setCreateDatetime(LocalDateTime.now());
					
					matchResult.setSampleFatherId(sampleFatherId);
					matchResult.setSampleMotherId(sampleMotherId);
					matchResult.setSampleChildId(sampleChildId);
					matchResult.setMatchedSampleId(sampleId);
					
					resultService.save(matchResult);
					
					// 7.更新比对队列
					compareQueue.setCompareStatus(Constants.COMPARE_STATUS_FINISH);
					compareQueue.setUpdateDatetime(LocalDateTime.now());
					queueService.updateById(compareQueue);
				}
			}
		} catch (Exception e) {
			LOG.error("Error relative compareResult:", e);
			throw new DnaRuntimeException();
		}
	}
	
	@Async
	@Override
	@Transactional
	public void compare(AutoCompareQueue compareQueue) {
		try {
			LOG.info("自动比对:开始 亲缘比对 {}", compareQueue.getId());
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
						DnaStrGeneInfo g = geneInfoService.getById(id);
						saveMatchResult(compareQueue, frequencyList, compareParamsString,
								Integer.parseInt(id), g.getSampleId(), geneInfo);
					}
				}

			}
			// 7.更新比对队列
			compareQueue.setCompareStatus(Constants.COMPARE_STATUS_FINISH);
			compareQueue.setUpdateDatetime(LocalDateTime.now());
			queueService.updateById(compareQueue);

			LOG.info("自动比对:完成 亲缘比对 {}", compareQueue.getId());
		} catch (Exception e) {
			LOG.error("Error task: ", e);
			throw new DnaRuntimeException();
		}
	}

}
