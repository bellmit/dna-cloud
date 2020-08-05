package com.bazl.dna.database.core.controller.web.matched;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bazl.dna.annotation.CurrentUser;
import com.bazl.dna.common.OpenErrorCodes;
import com.bazl.dna.common.PageInfo;
import com.bazl.dna.common.PublicConstants;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.common.filter.AuthUser;
import com.bazl.dna.database.core.constants.ErrorCodes;
import com.bazl.dna.database.core.constants.ErrorInfo;
import com.bazl.dna.database.core.controller.BaseController;
import com.bazl.dna.database.service.model.po.CaseInfo;
import com.bazl.dna.database.service.model.po.ConsignmentInfo;
import com.bazl.dna.database.service.model.po.DnaSampleInfo;
import com.bazl.dna.database.service.model.po.MatchResultRelative;
import com.bazl.dna.database.service.model.qo.MatchRelativeDetailQuery;
import com.bazl.dna.database.service.model.qo.MatchRelativeDetailResultQuery;
import com.bazl.dna.database.service.model.qo.MatchRelativeGeneInfoQuery;
import com.bazl.dna.database.service.model.qo.MatchRelativeResultQuery;
import com.bazl.dna.database.service.model.qo.MatchResultCodeQuery;
import com.bazl.dna.database.service.service.CaseInfoService;
import com.bazl.dna.database.service.service.ConsignmentInfoService;
import com.bazl.dna.database.service.service.DictItemService;
import com.bazl.dna.database.service.service.DnaSampleInfoService;
import com.bazl.dna.database.service.service.MatchResultRelativeService;
import com.bazl.dna.util.DateUtil;

/**
 * 亲缘比中信息控制器
 * 
 * @author lizhihua on 2020/2/12.
 */
@RestController
@RequestMapping("/match/relative")
public class RelativeMatchedController extends BaseController {

	@Autowired
	private MatchResultRelativeService matchResultService;

	@Autowired
	private DnaSampleInfoService dnaSampleInfoService;

	@Autowired
	private CaseInfoService caseInfoService;
	
	@Autowired
	private DictItemService dictItemService;

	@Autowired
	private ConsignmentInfoService consignmentInfoService;

	@PostMapping(value = "/resultQuery")
	public ResponseData relativeResultQuery(@RequestBody MatchRelativeResultQuery query) {
		try {
			int count = matchResultService.relativeResultCount(query);
			List<MatchRelativeResultQuery> queryList = matchResultService.relativeResultInfo(query);

			List<List<MatchRelativeResultQuery>> resultList = new ArrayList<>();

            Set<Integer> groupIdList = new HashSet<>();
            
			for (MatchRelativeResultQuery resultQuery : queryList) {
				resultQuery.setSampleTypeName(dictItemService.selectNameByTypeCodeAndCode("SAMPLE_TYPE", resultQuery.getSampleType()));
				DnaSampleInfo matchSample = dnaSampleInfoService.getById(resultQuery.getMatchSampleId());
				resultQuery.setMatchSampleNo(matchSample.getSampleLabNo());
				resultQuery.setMatchSampleName(matchSample.getSampleName());
				CaseInfo caseInfo = caseInfoService.getById(matchSample.getCaseId());
				if (caseInfo != null) {
					resultQuery.setMatchCaseName(caseInfo.getCaseName());
				}
				
				// 获取委托案件多个
				if (resultQuery.getCaseId() != null) {
		            	ConsignmentInfo consignmentInfo = consignmentInfoService.selectMainConsignmentByCaseId(resultQuery.getCaseId());
					if (consignmentInfo != null) {
						resultQuery.setOrgCode(consignmentInfo.getConsignOrgCode());
						resultQuery.setOrgName(consignmentInfo.getConsignOrgName());
					}
				}
				groupIdList.add(resultQuery.getGroupId());
			}
			
			queryList.stream().collect(Collectors.groupingBy(MatchRelativeResultQuery::getGroupId,Collectors.toList()))
            .forEach((groupId,fooListByAge)->{
                resultList.add(fooListByAge);
            });

		    List<Object> list1 = new ArrayList<>();
		    for (Integer groupId : groupIdList) {
		        Map<String, Object> inMap = new HashMap<>();
		        inMap.put("groupId", groupId);
		        List<MatchRelativeResultQuery>  inList = new ArrayList<>();
		        for (List<MatchRelativeResultQuery> resultQuery : resultList) {
		            for (MatchRelativeResultQuery matchResultQuery : resultQuery) {
		                if (groupId.equals(matchResultQuery.getGroupId())){
		                    inList.add(matchResultQuery);
		                }
		            }
		
		        }
		        inMap.put("groupList", inList);
		        list1.add(inMap);
		    }

			Map<String, Object> resultObj = new HashMap<>();
			resultObj.put(PublicConstants.PARAM_PAGE, new PageInfo(query.getPageIndex(), query.getRows(), count));
			resultObj.put("resultList", list1);

			return new ResponseData(resultObj);
		} catch (Exception ex) {
			logger.error("invoke SameMatchedController.resultQuery error.", ex);
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
	}

	@PostMapping(value = "/detailQuery")
	public ResponseData relativeDetailQuery(@RequestBody MatchRelativeDetailQuery query) {
		try {
			int totalCount = matchResultService.relativeDatailCount(query);
			List<MatchRelativeDetailResultQuery> matchStrRustltQueryList = matchResultService.relativeDatailInfo(query);

			Map<String, Object> resultObj = new HashMap<>();
			resultObj.put("pageIndex", query.getPageIndex());
			resultObj.put("rows", query.getRows());
			resultObj.put("totalCount", totalCount);
			resultObj.put("resultList", matchStrRustltQueryList);

			return new ResponseData(resultObj);
		} catch (Exception ex) {
			logger.error("invoke RelativeMatchedController.relativeDetailQuery error.", ex);
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
	}

	/**
	 * 查询详情
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/geneQuery")
	public ResponseData relativeGeneQuery(String id) {
		try {
			MatchResultRelative matchResult = matchResultService
					.getOne(new QueryWrapper<MatchResultRelative>().eq("ID", id));
			if (matchResult != null) {
				// 查询基因信息
				JSONObject geneDetailsJson = JSONObject.parseObject(matchResult.getMatchedGeneDetails());
				geneDetailsJson.put("id", id);
				geneDetailsJson.put("reviewPersonName", matchResult.getReviewPersonName());// 审核人
				geneDetailsJson.put("reviewResultCode", matchResult.getReviewResultCode());// 审核状态
				geneDetailsJson.put("reviewTime", "");
				if (matchResult.getReviewDatetime() != null) {
					geneDetailsJson.put("reviewTime", DateUtil.dateToString(DateUtil.localDateTimeToDate(matchResult.getReviewDatetime()),DateUtil.DATE_FORMAT));//审核时间
				}
				geneDetailsJson.put("matchTime", DateUtil.dateToString(DateUtil.localDateTimeToDate(matchResult.getMatchDatetime()), DateUtil.DATE_FORMAT));// 比中时间

				List<MatchRelativeGeneInfoQuery> geneInfoQueries = new ArrayList<>();
				if (matchResult.getSampleFatherId() != null) {
					// 查询检材信息
					DnaSampleInfo dnaSampleInfo = dnaSampleInfoService
							.getOne(new QueryWrapper<DnaSampleInfo>().eq("ID", matchResult.getSampleFatherId()));
					// 查询案件信息
					CaseInfo caseInfo = caseInfoService
							.getOne(new QueryWrapper<CaseInfo>().eq("ID", dnaSampleInfo.getCaseId()));
					//委托信息
					ConsignmentInfo consignmentInfo = consignmentInfoService.selectByPrimaryKey(dnaSampleInfo.getConsignmentId());
					MatchRelativeGeneInfoQuery matchGeneInfoQuery = new MatchRelativeGeneInfoQuery();
					matchGeneInfoQuery.setCaseName(caseInfo.getCaseName());
					matchGeneInfoQuery.setCompareDatetime(DateUtil.localDateTimeToDate(matchResult.getCreateDatetime()));
					matchGeneInfoQuery.setSampleName(dnaSampleInfo.getSampleName());
					matchGeneInfoQuery.setSampleType(dnaSampleInfo.getSampleType());
					matchGeneInfoQuery.setSampleTypeName(
							dictItemService.selectNameByTypeCodeAndCode("SAMPLE_TYPE", dnaSampleInfo.getSampleType()));
					if (consignmentInfo != null){
						matchGeneInfoQuery.setStorePersonName(consignmentInfo.getAcceptPersonName());
					}
					matchGeneInfoQuery.setReviewDatetime(DateUtil.localDateTimeToDate(matchResult.getReviewDatetime()));
					matchGeneInfoQuery.setRelativeName("父亲");
					geneInfoQueries.add(matchGeneInfoQuery);
				}
				if (matchResult.getSampleMotherId() != null) {
					// 查询检材信息
					DnaSampleInfo dnaSampleInfo = dnaSampleInfoService
							.getOne(new QueryWrapper<DnaSampleInfo>().eq("ID", matchResult.getSampleMotherId()));
					// 查询案件信息
					CaseInfo caseInfo = caseInfoService
							.getOne(new QueryWrapper<CaseInfo>().eq("ID", dnaSampleInfo.getCaseId()));
					//委托信息
					ConsignmentInfo consignmentInfo = consignmentInfoService.selectByPrimaryKey(dnaSampleInfo.getConsignmentId());
					MatchRelativeGeneInfoQuery matchGeneInfoQuery = new MatchRelativeGeneInfoQuery();
					matchGeneInfoQuery.setCaseName(caseInfo.getCaseName());
					matchGeneInfoQuery.setCompareDatetime(DateUtil.localDateTimeToDate(matchResult.getCreateDatetime()));
					matchGeneInfoQuery.setSampleName(dnaSampleInfo.getSampleName());
					matchGeneInfoQuery.setSampleType(dnaSampleInfo.getSampleType());
					matchGeneInfoQuery.setSampleTypeName(
							dictItemService.selectNameByTypeCodeAndCode("SAMPLE_TYPE", dnaSampleInfo.getSampleType()));
//					matchGeneInfoQuery.setStorePersonName(caseInfo.getStorePersonName());
					if (consignmentInfo != null){
						matchGeneInfoQuery.setStorePersonName(consignmentInfo.getAcceptPersonName());
					}
					matchGeneInfoQuery.setReviewDatetime(DateUtil.localDateTimeToDate(matchResult.getReviewDatetime()));
					matchGeneInfoQuery.setRelativeName("母亲");
					geneInfoQueries.add(matchGeneInfoQuery);
				}
				if (matchResult.getSampleChildId() != null) {
					// 查询检材信息
					DnaSampleInfo dnaSampleInfo = dnaSampleInfoService
							.getOne(new QueryWrapper<DnaSampleInfo>().eq("ID", matchResult.getSampleChildId()));
					// 查询案件信息
					CaseInfo caseInfo = caseInfoService
							.getOne(new QueryWrapper<CaseInfo>().eq("ID", dnaSampleInfo.getCaseId()));
					//委托信息
					ConsignmentInfo consignmentInfo = consignmentInfoService.selectByPrimaryKey(dnaSampleInfo.getConsignmentId());
					MatchRelativeGeneInfoQuery matchGeneInfoQuery = new MatchRelativeGeneInfoQuery();
					matchGeneInfoQuery.setCaseName(caseInfo.getCaseName());
					matchGeneInfoQuery.setCompareDatetime(DateUtil.localDateTimeToDate(matchResult.getCreateDatetime()));
					matchGeneInfoQuery.setSampleName(dnaSampleInfo.getSampleName());
					matchGeneInfoQuery.setSampleType(dnaSampleInfo.getSampleType());
					matchGeneInfoQuery.setSampleTypeName(
							dictItemService.selectNameByTypeCodeAndCode("SAMPLE_TYPE", dnaSampleInfo.getSampleType()));
					if (consignmentInfo != null){
						matchGeneInfoQuery.setStorePersonName(consignmentInfo.getAcceptPersonName());
					}
//					matchGeneInfoQuery.setStorePersonName(caseInfo.getStorePersonName());
					matchGeneInfoQuery.setReviewDatetime(DateUtil.localDateTimeToDate(matchResult.getReviewDatetime()));
					matchGeneInfoQuery.setRelativeName("子女");
					geneInfoQueries.add(matchGeneInfoQuery);
				}

				Map<String, Object> resultObj = new HashMap<>();
				resultObj.put("resultList", geneInfoQueries);
				resultObj.put("resultGeneInfo", geneDetailsJson);
				return new ResponseData(resultObj);
			}
		} catch (Exception ex) {
			logger.error("invoke RelativeMatchedController.relativeGeneQuery error.", ex);
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		return new ResponseData();
	}

	/**
	 * 更新比中状态
	 * 
	 * @return
	 */
	@PostMapping("updateResultCode")
	public ResponseData updateResultCode(@CurrentUser AuthUser user, @RequestBody MatchResultCodeQuery query) {
		try {
			if (user == null) {
				return new ResponseData(OpenErrorCodes.ERR_USER_ACCESS_TOKEN_INVALID, ErrorInfo.ERR_USER_NAME_NOT_NULL);
			}
			if (query.getIds() == null || query.getResultCode() == null) {
				return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
			}
			for (int id : query.getIds()) {
				MatchResultRelative entity = matchResultService.getById(id);
				entity.setReviewFlag(1);
				entity.setReviewDatetime(LocalDateTime.now());
				entity.setReviewResultCode(String.valueOf(query.getResultCode()));
				entity.setReviewResultDesc(query.getReviewResultDesc());
				entity.setReviewPersonId(user.getId());
				entity.setReviewPersonName(user.getUsername());
				matchResultService.updateById(entity);
			}
		} catch (Exception e) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		return new ResponseData();
	}
}
