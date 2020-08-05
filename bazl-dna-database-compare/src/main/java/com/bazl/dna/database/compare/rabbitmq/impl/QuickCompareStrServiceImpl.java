package com.bazl.dna.database.compare.rabbitmq.impl;

import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Arrays;
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
import com.bazl.dna.database.compare.rabbitmq.IQuickCompareStrService;
import com.bazl.dna.database.compare.task.config.CombineAlgorithm;
import com.bazl.dna.database.constants.Constants;
import com.bazl.dna.database.service.model.po.AlleleFrequencyInfo;
import com.bazl.dna.database.service.model.po.CompareParams;
import com.bazl.dna.database.service.model.po.DnaStrGeneInfo;
import com.bazl.dna.database.service.model.po.QuickCompareQueue;
import com.bazl.dna.database.service.model.po.QuickCompareResultSame;
import com.bazl.dna.database.service.service.AlleleFrequencyInfoService;
import com.bazl.dna.database.service.service.DnaStrGeneInfoService;
import com.bazl.dna.database.service.service.QuickCompareQueueService;
import com.bazl.dna.database.service.service.QuickCompareResultSameService;
import com.bazl.dna.exception.DnaRuntimeException;
import com.bazl.dna.util.DataFormat;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;
import com.google.common.math.IntMath;

@Service
public class QuickCompareStrServiceImpl implements IQuickCompareStrService {

	private static final Logger LOG = LoggerFactory.getLogger(QuickCompareStrServiceImpl.class);
	
	@Autowired
	private DnaStrGeneInfoService geneInfoService;
	
	@Autowired
	private QuickCompareResultSameService resultService;
	
	@Autowired
	private QuickCompareQueueService queueService;
	
	@Autowired
	private AlleleFrequencyInfoService frequencyInfoService;
	
	private Set<String> geneIdSet = Sets.newHashSet();
			
	@Override
	@Transactional
	public void saveMatchResult(QuickCompareQueue compareQueue, JSONObject compareJsonObject,
			Set<String> resultGeneIdSet, Integer queueId, List<AlleleFrequencyInfo> frequencyList) {
		try {
			if (!resultGeneIdSet.isEmpty()) {
				LOG.info("Result: {}", resultGeneIdSet);
				// 保存比对结果组
				Set<String> compareSet = compareJsonObject.keySet();
				// 保存比对结果
				List<QuickCompareResultSame> entityList = Lists.newArrayList();
				for (String geneId : resultGeneIdSet) {
					DnaStrGeneInfo matchResultGeneInfo = geneInfoService.getById(geneId);
					
					JSONObject matchResultJsonObject = JSONObject.parseObject(matchResultGeneInfo.getGeneInfo());
					Set<String> matchResultSet = matchResultJsonObject.keySet();
					
					SetView<String> unionKeySet = Sets.union(compareSet, matchResultSet); //合集
					int matchLocusCount = Sets.intersection(compareSet, matchResultSet).size(); //交集
					int diffLocusCount = Sets.symmetricDifference(compareSet, matchResultSet).size(); //差集
					
					QuickCompareResultSame matchResult = new QuickCompareResultSame();
					matchResult.setMatchedDatetime(LocalDateTime.now());
					matchResult.setMatchedLocusCount(matchLocusCount);
					matchResult.setDiffLocusCount(diffLocusCount);
					matchResult.setMatchedGeneId(matchResultGeneInfo.getId());
					matchResult.setQuickCompareQueueId(queueId);
					
					JSONObject json = new JSONObject();
			        json.put("diffLocusCount", diffLocusCount);
			        json.put("matchLocusCount", matchLocusCount);
			        JSONArray array = new JSONArray(unionKeySet.size());
					//等位基因集合
					double totalLR = 1;
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
										Double lr = new MarkerLRCalculation(frequencyList).calculateSingleMarkerLR(key, allele[0], allele[1]);
										if (Double.isNaN(lr)){
											alleleJson.put("LR", "");
										}else {
											String str = DataFormat.formatDecimal(lr == null ? 0.0 : lr, 3, 1, true);//转换科学计数法
											alleleJson.put("LR", str);  //似然比科学计数法转换
											totalLR = totalLR * lr;
										}
					        		}
				        		}else{
									alleleJson.put("LR", "");
								}
				        	}
						array.add(alleleJson);
			        }
			        if (Double.NaN == totalLR) {
	                		totalLR = 0;
	                }
					if (totalLR!=1) {
						String totalStr = (DataFormat.formatDecimal(totalLR == 0 ? 0.0 : totalLR, 3, 1, true));//总计似然比科学计数法转换
						json.put("totalLR",totalStr);
					}else {
						json.put("totalLR","");
					}
			        matchResult.setMatchedGeneDetails(json.toJSONString());
					entityList.add(matchResult);
				}
				resultService.saveBatch(entityList);
			}
			// 7.更新比对队列
			compareQueue.setQueueStatus(Constants.COMPARE_STATUS_FINISH);
			compareQueue.setUpdateDatetime(LocalDateTime.now());
			queueService.updateById(compareQueue);
			LOG.info("快速比对:队列完成比中样本Str: {}", compareQueue.getId());
		} catch (Exception e) {
			LOG.error("Error save: ", e);
			throw new DnaRuntimeException();
		}
	}
	
	@Async
	@Override
	@Transactional
	public void compare(QuickCompareQueue compareQueue) {
		try {
			LOG.info("快速比对:开始 STR比对 {}", compareQueue.getId());
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
				String geneInfo = compareQueue.getPendingSampleaGeneInfo();
				
				// 4. 根据匹配下限获取查询条件 比如：匹配下限为13，基因座数量为16 需获取所有查询条件
				JSONObject geneInfoJsonObject = JSONObject.parseObject(geneInfo, Feature.OrderedField);
				if (!geneInfoJsonObject.isEmpty()) {
					// 5. 按照查询条件进行比对
					compareResult(geneInfoJsonObject, compareParams);
					
					// 6. 将比对结果保存在对应的  matchResultStr matchResultStrGroup
					saveMatchResult(compareQueue, geneInfoJsonObject, geneIdSet, compareQueue.getId(), frequencyList);
				} else {
					// 7.更新比对队列
					compareQueue.setQueueStatus(Constants.COMPARE_STATUS_FINISH);
					compareQueue.setUpdateDatetime(LocalDateTime.now());
					queueService.updateById(compareQueue);
					LOG.info("快速比对:没有比中的样本STR: {}", compareQueue.getId());
				}
			}
			LOG.info("快速比对:完成 STR比对 {}", compareQueue.getId());
		} catch (Exception e) {
			LOG.error("Error compare: ", e);
			throw new DnaRuntimeException();
		}
	}
	
	/**
	 * 同一比对
	 */
	private void compareResult(JSONObject geneInfoJsonObject, CompareParams compareParams) {
		try {
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
			LOG.info("快速比对:基因座数: {} 匹配下限: {}", geneInfoSet.size(), geneInfoSet.size() - most);
			if (strs.length < limit) {
				limit = geneInfoSet.size();
			} 
			CombineAlgorithm ca = new CombineAlgorithm(strs, geneInfoSet.size() - most);
			List<Object[]> objList = Arrays.asList(ca.getResult());
			List<List<Object[]>> parts = Lists.partition(objList , IntMath.divide(objList.size(), 10, RoundingMode.CEILING));
			
			// 5. 按照查询条件进行比对
			parts.parallelStream().forEach(list -> 
				process(list, geneInfoJsonObject)
			);
			
		} catch (Exception e) {
			LOG.error("Error: ", e);
		}
	}
	
	private void process(List<Object[]> list, JSONObject geneInfoJsonObject) {
		for (Object[] o : list) {
			List<Object> l = Arrays.asList(o);
			Map<String, Object> paramsMap = Maps.newHashMap();
			for (Object obj : l) {
				if (!"".equals(geneInfoJsonObject.get(obj))) {
					paramsMap.put("v_" + obj, geneInfoJsonObject.get(obj));
				}
			}
			List<Map<String, Object>> strList = geneInfoService.findQuickCompareList(paramsMap);
			for (Map<String, Object> m : strList) {
				geneIdSet.add(m.get("id").toString());
			}
		}
	}

}
