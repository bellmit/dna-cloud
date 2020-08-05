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

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.bazl.dna.database.compare.rabbitmq.IAutoCompareMixService;
import com.bazl.dna.database.compare.task.config.CombineAlgorithm;
import com.bazl.dna.database.constants.Constants;
import com.bazl.dna.database.service.model.po.AutoCompareQueue;
import com.bazl.dna.database.service.model.po.CompareParams;
import com.bazl.dna.database.service.model.po.DnaMixGeneInfo;
import com.bazl.dna.database.service.model.po.MatchResultMix;
import com.bazl.dna.database.service.model.po.MatchResultMixGroup;
import com.bazl.dna.database.service.service.AutoCompareQueueService;
import com.bazl.dna.database.service.service.DnaMixGeneInfoService;
import com.bazl.dna.database.service.service.MatchResultMixGroupService;
import com.bazl.dna.database.service.service.MatchResultMixService;
import com.bazl.dna.exception.DnaRuntimeException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.math.IntMath;

@Service
public class AutoCompareMixServiceImpl implements IAutoCompareMixService {

	private static final Logger LOG = LoggerFactory.getLogger(AutoCompareMixServiceImpl.class);
	
	@Autowired
	private DnaMixGeneInfoService geneInfoService;
	
	@Autowired
	private MatchResultMixService resultService;
	
	@Autowired
	private MatchResultMixGroupService resultGroupService;
	
	@Autowired
	private AutoCompareQueueService queueService;
	
	private Set<String> geneIdSet = Sets.newHashSet();
	
	@Override
	@Transactional
	public void saveMatchResult(AutoCompareQueue compareQueue, 
			DnaMixGeneInfo compareGeneInfo, Set<String> resultGeneIdSet) {
		try {
			if (!resultGeneIdSet.isEmpty()) {
				LOG.info("Result: {}", resultGeneIdSet);
				// 保存比对结果
				List<MatchResultMix> entityList = Lists.newArrayList();
				for (String geneId : resultGeneIdSet) {
					DnaMixGeneInfo matchResultGeneInfo = geneInfoService.getById(geneId);
					
					// 保存比对组
					Integer groupNo = getMatchResultGroupNo(matchResultGeneInfo);
					MatchResultMix matchResult = new MatchResultMix();
					matchResult.setMatchGroupId(groupNo);
					entityList.add(matchResult);
				}
				resultService.saveBatch(entityList);
			}
			
			// 7.更新比对队列
			compareQueue.setCompareStatus(Constants.COMPARE_STATUS_FINISH);
			compareQueue.setUpdateDatetime(LocalDateTime.now());
			queueService.updateById(compareQueue);
			LOG.info("自动比对:队列完成比中样本Mix: {}", compareQueue.getId());
		} catch (Exception e) {
    		LOG.error("Error saveMatchResult: ", e);
            throw new DnaRuntimeException();
    	}
	}
	
	private Integer getMatchResultGroupNo(DnaMixGeneInfo matchResultGeneInfo) {
		LOG.info("geneId:{}", matchResultGeneInfo.getId());
		// 保存组
		MatchResultMixGroup entity = new MatchResultMixGroup();
		entity.setGroupNo(resultGroupService.getNextval());
		resultGroupService.save(entity);
		return entity.getGroupNo();
	}
	
	@Async
	@Override
	@Transactional
	public void compare(AutoCompareQueue compareQueue) {
		try {
			LOG.info("自动比对:开始 混合比对 {}", compareQueue.getId());
			if (compareQueue.getCompareParams() != null) {
				// 2.获取比对条件
				CompareParams compareParams = JSONObject.parseObject(compareQueue.getCompareParams(), CompareParams.class);
				
				// 3. 获取对应基因型 dnaMixGene
				DnaMixGeneInfo geneInfo = geneInfoService.getById(compareQueue.getGeneId());
				
				// 4. 根据匹配下限获取查询条件 比如：匹配下限为13，基因座数量为16 需获取所有查询条件
				if (geneInfo != null) {
					// 5. 按照查询条件进行比对
					compareResult(compareQueue.getId(), geneInfo, compareParams);
					
					// 6. 将比对结果保存在对应的  matchResultStr matchResultStrGroup
					saveMatchResult(compareQueue, geneInfo, geneIdSet);
				} else {
					// 7.更新比对队列
					compareQueue.setCompareStatus(Constants.COMPARE_STATUS_FINISH);
					compareQueue.setUpdateDatetime(LocalDateTime.now());
					queueService.updateById(compareQueue);
					LOG.info("自动比对:完成比中样本Mix: {}", compareQueue.getId());
				}
			}
			LOG.info("自动比对:完成 混合比对 {}", compareQueue.getId());
		} catch (Exception e) {
			LOG.error("Error compare: ", e);
		    throw new DnaRuntimeException();
		}
	}
	
	/**
	 * 同一比对
	 */
	private void compareResult(Integer compareId, DnaMixGeneInfo geneInfo, CompareParams compareParams) {
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
			LOG.info("自动比对: id:{} 基因座数: {} 匹配下限: {}", compareId, geneInfoSet.size(), geneInfoSet.size() - most);
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
			LOG.error("Error mix compareResult:", e);
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
