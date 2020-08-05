package com.bazl.dna.database.compare.rabbitmq.impl;

import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.bazl.dna.database.algorithm.calculation.MarkerLRCalculation;
import com.bazl.dna.database.compare.rabbitmq.IAutoCompareStrService;
import com.bazl.dna.database.compare.task.config.CombineAlgorithm;
import com.bazl.dna.database.constants.Constants;
import com.bazl.dna.database.service.model.po.AlleleFrequencyInfo;
import com.bazl.dna.database.service.model.po.AutoCompareQueue;
import com.bazl.dna.database.service.model.po.CompareParams;
import com.bazl.dna.database.service.model.po.DnaSampleInfo;
import com.bazl.dna.database.service.model.po.DnaStrGeneInfo;
import com.bazl.dna.database.service.model.po.MatchResultSame;
import com.bazl.dna.database.service.model.po.MatchResultSameGroup;
import com.bazl.dna.database.service.service.AlleleFrequencyInfoService;
import com.bazl.dna.database.service.service.AutoCompareQueueService;
import com.bazl.dna.database.service.service.DnaSampleInfoService;
import com.bazl.dna.database.service.service.DnaStrGeneInfoService;
import com.bazl.dna.database.service.service.MatchResultSameGroupService;
import com.bazl.dna.database.service.service.MatchResultSameService;
import com.bazl.dna.exception.DnaRuntimeException;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;
import com.google.common.math.IntMath;

@Service
public class AutoCompareStrServiceImpl implements IAutoCompareStrService {

	private static final Logger LOG = LoggerFactory.getLogger(AutoCompareStrServiceImpl.class);
	
	@Autowired
	private DnaStrGeneInfoService geneInfoService;
	
	@Autowired
	private MatchResultSameService resultService;
	
	@Autowired
	private MatchResultSameGroupService resultGroupService;
	
	@Autowired
	private DnaSampleInfoService dnaSampleInfoService;
	
	@Autowired
	private AutoCompareQueueService queueService;
	
	@Autowired
	private AlleleFrequencyInfoService frequencyInfoService;
	
	private Set<String> geneIdSet = Sets.newHashSet();
	
	@Override
	@Transactional
	public void saveMatchResult(AutoCompareQueue compareQueue, DnaStrGeneInfo compareGeneInfo,
			Set<String> resultGeneIdSet, List<AlleleFrequencyInfo> frequencyList) {
		try {
			if (!resultGeneIdSet.isEmpty()) {
				LOG.info("Result: {}", resultGeneIdSet);
				// 保存比对结果组
				JSONObject compareJsonObject = JSONObject.parseObject(compareGeneInfo.getGeneInfo());
				Set<String> compareSet = compareJsonObject.keySet();
				// 保存比对结果
				List<MatchResultSame> entityList = Lists.newArrayList();
				for (String geneId : resultGeneIdSet) {
					DnaStrGeneInfo matchResultGeneInfo = geneInfoService.getById(geneId);
					
					// 保存比对组
					DnaSampleInfo compareSampleInfo = dnaSampleInfoService.getById(compareGeneInfo.getSampleId());
					DnaSampleInfo matchResultSampleInfo = dnaSampleInfoService.getById(matchResultGeneInfo.getSampleId());
					
					Integer groupId = getMatchResultGroupNo(compareGeneInfo, matchResultGeneInfo, compareSampleInfo, matchResultSampleInfo);
					
					JSONObject matchResultJsonObject = JSONObject.parseObject(matchResultGeneInfo.getGeneInfo());
					Set<String> matchResultSet = matchResultJsonObject.keySet();
					
					SetView<String> unionKeySet = Sets.union(compareSet, matchResultSet); //合集
					int matchLocusCount = Sets.intersection(compareSet, matchResultSet).size(); //交集
					int diffLocusCount = Sets.symmetricDifference(compareSet, matchResultSet).size(); //差集
					
					MatchResultSame matchResult = new MatchResultSame();
			        matchResult.setCreateDatetime(LocalDateTime.now());
			        matchResult.setMatchDatetime(LocalDateTime.now());
			        matchResult.setMatchLocusCount(matchLocusCount); 
			        matchResult.setDiffLocusCount(diffLocusCount);
			        matchResult.setMatchGroupId(groupId);
			        matchResult.setReviewFlag(0);
			        matchResult.setReviewResultCode("0");
			        matchResult.setSampleaId(compareGeneInfo.getSampleId());
			        matchResult.setSamplebId(matchResultGeneInfo.getSampleId());
			        
			        JSONObject json = new JSONObject();
			        json.put("sampleNo", compareSampleInfo.getSampleLabNo());
			        json.put("matchSampleNo", matchResultSampleInfo.getSampleLabNo());
			        json.put("diffLocusCount", diffLocusCount);
			        json.put("matchLocusCount", matchLocusCount);
			        JSONArray array = new JSONArray(unionKeySet.size());
			        double totalLR = 1d;
			        json.put("alleleList", array);
			        for (String key : unionKeySet) {
				        	JSONObject alleleJson = new JSONObject();
				        	alleleJson.put("alleleKey", key);
				        	alleleJson.put("alleleValue", compareJsonObject.getString(key));
				        	alleleJson.put("matchAlleleValue", matchResultJsonObject.getString(key));
				        	alleleJson.put("LR","");
				        	alleleJson.put("diffAllele", true);
				        	if (compareJsonObject.getString(key) != null && compareJsonObject.getString(key).equals(matchResultJsonObject.getString(key))) {
				        		alleleJson.put("diffAllele", false);
				        		String alleleValue = compareJsonObject.getString(key);
				        		if (!"".equals(alleleValue)) {
					        		String[] allele = StringUtils.split(alleleValue, "/");
					        		if (allele.length > 1) {
					        			double lr = new MarkerLRCalculation(frequencyList).calculateSingleMarkerLR(key, allele[0], allele[1]);
					        			alleleJson.put("LR", lr);
					        			totalLR = totalLR * lr;
					        		}
				        		}
				        	}
						array.add(alleleJson);
			        }
			        if (Double.NaN == totalLR) {
	                		totalLR = 0;
	                }
			        json.put("totalLR", totalLR);
			        
			        matchResult.setMatchedGeneDetails(json.toJSONString());
					entityList.add(matchResult);
				}
				resultService.saveBatch(entityList);
			}
			
			// 7.更新比对队列
			compareQueue.setCompareStatus(Constants.COMPARE_STATUS_FINISH);
			compareQueue.setUpdateDatetime(LocalDateTime.now());
			queueService.updateById(compareQueue);
			
			LOG.info("自动比对:队列完成样本STR: {}", compareQueue.getId());
		} catch (Exception e) {
			LOG.error("Error save: ", e);
			throw new DnaRuntimeException();
		}
	}
	
	private Integer getMatchResultGroupNo(DnaStrGeneInfo compareGeneInfo, DnaStrGeneInfo matchResultGeneInfo,
			DnaSampleInfo compareSampleInfo, DnaSampleInfo matchResultSampleInfo) {
		Integer groupId = resultService.getMatchResultGroupNo(compareGeneInfo.getSampleId(), matchResultGeneInfo.getSampleId());
		LocalDateTime time = LocalDateTime.now();
		if (groupId == null || groupId == 0) {
			// 保存组
			MatchResultSameGroup entity = new MatchResultSameGroup();
			entity.setFirstMatchTime(time);
			entity.setGroupNo(resultGroupService.getNextval());
			entity.setGroupSampleCount(2);
			
			String compareDataType = compareSampleInfo.getInstoreDataType();
			String matchResultDataType = matchResultSampleInfo.getInstoreDataType();
			Set<String> objectSet = ImmutableSet.of("01","02"); // 物证
			int a = Collections.frequency(objectSet, compareDataType);
			int b = Collections.frequency(objectSet, matchResultDataType);
			if (a == 1 && b == 1) { // 物证 && 物证
				entity.setGroupType(Constants.COMPARE_GROUP_TYPE_OBJECT_OBJECT);
			} else if (a == 0 && b == 0) { // 人证 && 认证
				entity.setGroupType(Constants.COMPARE_GROUP_TYPE_PERSON_PERSON);
			} else {
				entity.setGroupType(Constants.COMPARE_GROUP_TYPE_PERSON_OBJECT);
			}
			entity.setLatestMatchTime(time);
			resultGroupService.save(entity);
			groupId = entity.getId();
		} else {
			// 更新组
			MatchResultSameGroup group = resultGroupService.getById(groupId);
			group.setLatestMatchTime(time);
			group.setGroupSampleCount(group.getGroupSampleCount() + 1);
			resultGroupService.updateById(group);
		}
			
		return groupId;
	}

	@Async
	@Override
	@Transactional
	public void compare(AutoCompareQueue compareQueue) {
		try {
			LOG.info("自动比对:开始 STR比对 {}", compareQueue.getId());
			if (compareQueue.getCompareParams() != null) {
				// 2.获取比对条件
				CompareParams compareParams = JSONObject.parseObject(compareQueue.getCompareParams(), CompareParams.class);
				// 种群
				List<AlleleFrequencyInfo> frequencyList = Lists.newArrayList();
				if (compareQueue.getPopulationFrequencyId() != null) {
					Map<String, Object> frequencyMap = Maps.newHashMap();
					frequencyMap.put("POPULATION_FREQUENCY_ID", compareQueue.getPopulationFrequencyId());
					frequencyList = frequencyInfoService.listByMap(frequencyMap);
				}
				// 3. 获取对应基因型 dnaStrGene
				DnaStrGeneInfo geneInfo = geneInfoService.getById(compareQueue.getGeneId());
				
				// 4. 根据匹配下限获取查询条件 比如：匹配下限为13，基因座数量为16 需获取所有查询条件
				if (geneInfo != null) {
					// 5. 按照查询条件进行比对
					compareResult(compareQueue.getId(), geneInfo, compareParams);
					
					// 6. 将比对结果保存在对应的  matchResultStr matchResultStrGroup
					saveMatchResult(compareQueue, geneInfo, geneIdSet, frequencyList);
				} else {
					// 7.更新比对队列
					compareQueue.setCompareStatus(Constants.COMPARE_STATUS_FINISH);
					compareQueue.setUpdateDatetime(LocalDateTime.now());
					queueService.updateById(compareQueue);
					
					LOG.info("自动比对:完成样本STR: {}", compareQueue.getId());
				}
			}
			LOG.info("自动比对:完成 STR比对 {}", compareQueue.getId());
		} catch (Exception e) {
			LOG.error("Error compare: ", e);
			throw new DnaRuntimeException();
		}
	}
	
	/**
	 * 同一比对
	 */
	private void compareResult(Integer compareId, DnaStrGeneInfo geneInfo, CompareParams compareParams) {
		try {
			JSONObject geneInfoJsonObject = JSONObject.parseObject(geneInfo.getGeneInfo(), Feature.OrderedField);
			Set<String> geneInfoSet = Sets.newHashSet();
	        geneInfoJsonObject.forEach((k, v) -> {
        		if (StringUtils.trimToNull(String.valueOf(v)) != null) {
        			geneInfoSet.add(k);
        		}
	        });
			// 4. 根据匹配下限获取查询条件
			for (String columnName : geneInfoSet) {
				String replaceName = StringUtils.replace(columnName, " ", "");
				int checkColumn = geneInfoService.checkVirtualColumn("v_" + replaceName);
				if (checkColumn == 0) {
					geneInfoService.insertVirtualColumn(replaceName);
				}
				int checkIndex = geneInfoService.checkVirtualIndex("idx_g_" + replaceName);
				if (checkIndex == 0) {
					geneInfoService.insertVirtualIndex(replaceName);
				}
			}
			
			Object[] strs = geneInfoSet.toArray();
			int limit = compareParams.getLowestSameLimit();
			int most = compareParams.getMostDiffLimit();
			LOG.info("自动比对:基因座数: {} 匹配下限: {}", geneInfoSet.size(), geneInfoSet.size() - most);
			if (strs.length < limit) {
				limit = geneInfoSet.size();
			} 
			CombineAlgorithm ca = new CombineAlgorithm(strs, geneInfoSet.size() - most);
			List<Object[]> objList = Arrays.asList(ca.getResult());
			List<List<Object[]>> parts = Lists.partition(objList , IntMath.divide(objList.size(), 10, RoundingMode.CEILING));
			
			// 5. 按照查询条件进行比对
			parts.parallelStream().forEach(list -> 
				process(list, geneInfoJsonObject, geneInfo.getId(), compareParams.getTargetLabServerNo(), compareParams.getTargetDataType())
			);
			
		} catch (Exception e) {
			LOG.info("Error: compareId:{}", compareId);
			LOG.error("Error quick mix compareResult:", e);
		}
	}
	
	private void process(List<Object[]> list, JSONObject geneInfoJsonObject, 
			Integer id, List<String> serverNo, List<String> dataType) {
		for (Object[] o : list) {
			List<Object> l = Arrays.asList(o);
			Map<String, Object> paramsMap = Maps.newHashMap();
			for (Object obj : l) {
				if (!"".equals(geneInfoJsonObject.get(obj))) {
					paramsMap.put("v_" + obj, geneInfoJsonObject.get(obj));
				}
			}
			List<Map<String, Object>> strList = geneInfoService.findCompareList(id, serverNo, dataType, paramsMap);
			for (Map<String, Object> m : strList) {
				geneIdSet.add(m.get("id").toString());
			}
		}
	}
}
