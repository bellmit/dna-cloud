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
import com.bazl.dna.database.service.model.po.MatchResultYstr;
import com.bazl.dna.database.service.model.po.MatchResultYstrGroup;
import com.bazl.dna.database.service.model.qo.MatchResultCodeQuery;
import com.bazl.dna.database.service.model.qo.MatchYstrDetailQuery;
import com.bazl.dna.database.service.model.qo.MatchYstrDetailResultQuery;
import com.bazl.dna.database.service.model.qo.MatchYstrGeneInfoQuery;
import com.bazl.dna.database.service.model.qo.MatchYstrResultQuery;
import com.bazl.dna.database.service.service.CaseInfoService;
import com.bazl.dna.database.service.service.ConsignmentInfoService;
import com.bazl.dna.database.service.service.DictItemService;
import com.bazl.dna.database.service.service.DnaSampleInfoService;
import com.bazl.dna.database.service.service.MatchResultYstrGroupService;
import com.bazl.dna.database.service.service.MatchResultYstrService;
import com.bazl.dna.util.DateUtil;
import com.google.common.collect.Sets;

/**
 * YSTR比中信息控制器
 * @author  lizhihua on 2020/5/7.
 */
@RestController
@RequestMapping("/match/ystr")
public class YstrMatchedController extends BaseController {

	@Autowired
    private MatchResultYstrGroupService matchResultYstrGroupService;

    @Autowired
    private MatchResultYstrService matchResultService;

    @Autowired
    private DnaSampleInfoService dnaSampleInfoService;

    @Autowired
    private CaseInfoService caseInfoService;
    @Autowired
    private ConsignmentInfoService consignmentInfoService;
    @Autowired
    private DictItemService dictItemService;

    @PostMapping(value = "/resultQuery")
    public ResponseData resultQuery(@RequestBody MatchYstrResultQuery query){
        try {
            List<MatchYstrResultQuery> queryList = matchResultYstrGroupService.resultInfo(query);
            int count = matchResultYstrGroupService.resultCount(query);
            
            List<List<MatchYstrResultQuery>> resultList = new ArrayList<>();

            Set<Integer> groupIdList = new HashSet<>();

            for (MatchYstrResultQuery resultQuery : queryList) {
	            	DnaSampleInfo matchSample = dnaSampleInfoService.getById(resultQuery.getMatchSampleId()); 
	            	if (matchSample != null) {
		            	resultQuery.setMatchSampleName(matchSample.getSampleName());
		            	resultQuery.setMatchSampleNo(matchSample.getSampleLabNo());
		            	CaseInfo caseInfo = caseInfoService.getById(matchSample.getCaseId());
		            	if (caseInfo != null) {
		            		resultQuery.setMatchCaseName(caseInfo.getCaseName());
		            	}
		            	resultQuery.setSampleTypeName(dictItemService.selectNameByTypeCodeAndCode("SAMPLE_TYPE", resultQuery.getSampleType()));
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

            queryList.stream().collect(Collectors.groupingBy(MatchYstrResultQuery::getGroupId,Collectors.toList()))
                    .forEach((groupId,fooListByAge)->{
                        resultList.add(fooListByAge);
                    });

            List<Object> list1 = new ArrayList<>();
            for (Integer groupId : groupIdList) {
                Map<String, Object> inMap = new HashMap<>();
                inMap.put("groupId", groupId);
                MatchResultYstrGroup group = matchResultYstrGroupService.getById(groupId);
                String matchTime = null;
                if (group.getLatestMatchTime() != null) {
                		matchTime = DateUtil.dateToString(DateUtil.localDateTimeToDate(group.getLatestMatchTime()),DateUtil.DATE_FORMAT);
                }
                inMap.put("groupMatchTime", matchTime);//审核时间
                inMap.put("groupMatchLocusCount", group.getGroupSampleCount());
                List<MatchYstrResultQuery>  inList = new ArrayList<>();
                for (List<MatchYstrResultQuery> resultQuery : resultList) {
                    for (MatchYstrResultQuery matchResultQuery : resultQuery) {
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
        }catch(Exception ex){
            logger.error("invoke YstrMatchedController.resultQuery error.", ex);
            return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
        }
    }

    @PostMapping(value = "/detailQuery")
    public ResponseData detailQuery(@RequestBody MatchYstrDetailQuery query){
        try {
            int totalCount = matchResultYstrGroupService.detailCount(query);
            List<MatchYstrDetailResultQuery> queryList = matchResultYstrGroupService.detailInfo(query);
            
            Set<String> setCase = Sets.newHashSet();
            int countMember = 0;
            int countSample = 0;
            for (MatchYstrDetailResultQuery q : queryList) {
            		setCase.add(q.getCaseName());
            		if (q.getEvidenceFlag().equals("1")) {
            			countMember++;
            		} else {
            			countSample++;
            		}
            }

            //分页信息
            PageInfo pageInfo = new PageInfo();
            pageInfo.setEvePageRecordCnt(query.getRows());//每页显示条数
            pageInfo.setAllRecordCount(totalCount);//总计条数
            if(totalCount >0 && totalCount % query.getRows() == 0){ //总计页码
                pageInfo.setPageCount((int)totalCount/query.getRows());
            }else{
                pageInfo.setPageCount((int)totalCount/query.getRows()+1);
            }
            Map<String, Object> resultObj = new HashMap<>();
            resultObj.put("pageInfo", pageInfo);
            resultObj.put("resultList", queryList);
            // 比中案件 比中人员 物证
            resultObj.put("countCase", setCase.size());
            resultObj.put("countMember", countMember);
            resultObj.put("countSample", countSample);

            return new ResponseData(resultObj);
        }catch(Exception ex){
            logger.error("invoke YstrMatchedController.detailQuery error.", ex);
            return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
        }
    }

    /**
     * 查询详情
     * @param id
     * @return
     */
    @GetMapping(value = "/geneQuery")
    public ResponseData geneQuery(String id){
        try {
            MatchResultYstr matchResult = matchResultService.getOne(new QueryWrapper<MatchResultYstr>().eq("ID", id));
            //查询基因信息
            if (matchResult != null) {
				JSONObject geneDetailsJson = JSONObject.parseObject(matchResult.getMatchedGeneDetails());
				geneDetailsJson.put("reviewPersonName", matchResult.getReviewPersonName());//审核人
				geneDetailsJson.put("reviewResultCode", matchResult.getReviewResultCode());//审核状态
				geneDetailsJson.put("reviewTime", "");
				if (matchResult.getReviewDatetime() != null) {
					geneDetailsJson.put("reviewTime", DateUtil.dateToString(DateUtil.localDateTimeToDate(matchResult.getReviewDatetime()),DateUtil.DATE_FORMAT));//审核时间
				}
				geneDetailsJson.put("matchTime", DateUtil.dateToString(DateUtil.localDateTimeToDate(matchResult.getMatchDatetime()),DateUtil.DATE_FORMAT));//比中时间
	            
	            List<MatchYstrGeneInfoQuery> geneInfoQueries = new ArrayList<>();
				if (matchResult.getSampleaId() != null) {
					MatchYstrGeneInfoQuery matchGeneInfoQuery = new MatchYstrGeneInfoQuery();
	                //查询检材信息
	                DnaSampleInfo dnaSampleInfo = dnaSampleInfoService.getOne(new QueryWrapper<DnaSampleInfo>().eq("ID", matchResult.getSampleaId()));
	                if (dnaSampleInfo != null) {
		                //查询案件信息
		                CaseInfo caseInfo = caseInfoService.getOne(new QueryWrapper<CaseInfo>().eq("ID", dnaSampleInfo.getCaseId()));
		                matchGeneInfoQuery.setCaseName(caseInfo.getCaseName());
	                    //委托信息
	                    ConsignmentInfo consignmentInfo = consignmentInfoService.selectByPrimaryKey(dnaSampleInfo.getConsignmentId());
		                if (consignmentInfo != null){
	                        matchGeneInfoQuery.setStorePersonName(consignmentInfo.getAcceptPersonName());
	                    }
		                matchGeneInfoQuery.setSampleName(dnaSampleInfo.getSampleName());
		                matchGeneInfoQuery.setSampleType(dnaSampleInfo.getSampleType());
		                matchGeneInfoQuery.setSampleTypeName(dictItemService.selectNameByTypeCodeAndCode("SAMPLE_TYPE", dnaSampleInfo.getSampleType()));
	                }
	                matchGeneInfoQuery.setCompareDatetime(DateUtil.localDateTimeToDate(matchResult.getCreateDatetime()));
	                matchGeneInfoQuery.setReviewDatetime(DateUtil.localDateTimeToDate(matchResult.getReviewDatetime()));
	                geneInfoQueries.add(matchGeneInfoQuery);
				} 
				if (matchResult.getSamplebId() != null) {
					MatchYstrGeneInfoQuery matchGeneInfoQuery = new MatchYstrGeneInfoQuery();
	                //查询检材信息
	                DnaSampleInfo dnaSampleInfo = dnaSampleInfoService.getOne(new QueryWrapper<DnaSampleInfo>().eq("ID", matchResult.getSamplebId()));
	                if (dnaSampleInfo != null) {
		                //查询案件信息
		                CaseInfo caseInfo = caseInfoService.getOne(new QueryWrapper<CaseInfo>().eq("ID", dnaSampleInfo.getCaseId()));
		                matchGeneInfoQuery.setCaseName(caseInfo.getCaseName());
	                    //委托信息
	                    ConsignmentInfo consignmentInfo = consignmentInfoService.selectByPrimaryKey(dnaSampleInfo.getConsignmentId());
	                    if (consignmentInfo != null){
	                        matchGeneInfoQuery.setStorePersonName(consignmentInfo.getAcceptPersonName());
	                    }
	                    matchGeneInfoQuery.setSampleName(dnaSampleInfo.getSampleName());
		                matchGeneInfoQuery.setSampleType(dnaSampleInfo.getSampleType());
		                matchGeneInfoQuery.setSampleTypeName(dictItemService.selectNameByTypeCodeAndCode("SAMPLE_TYPE", dnaSampleInfo.getSampleType()));
	                }
	                
	                matchGeneInfoQuery.setCompareDatetime(DateUtil.localDateTimeToDate(matchResult.getCreateDatetime()));
	                matchGeneInfoQuery.setReviewDatetime(DateUtil.localDateTimeToDate(matchResult.getReviewDatetime()));
	                geneInfoQueries.add(matchGeneInfoQuery);
	            }
	
				Map<String, Object> resultObj = new HashMap<>();
				resultObj.put("groupId", matchResult.getMatchGroupId());
	            resultObj.put("resultList", geneInfoQueries);
	            resultObj.put("resultGeneInfo", geneDetailsJson);
	            return new ResponseData(resultObj);
            }
		} catch (Exception ex) {
            logger.error("invoke YstrMatchedController.geneQuery error.", ex);
            return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
        }
        return new ResponseData();
    }
    
    /**
     * 更新比中状态
     * @return
     */
    @RequestMapping("updateResultCode")
    public ResponseData updateResultCode(@CurrentUser AuthUser user, @RequestBody MatchResultCodeQuery query) {
    		try {
	    		if (user == null) {
	    			return new ResponseData(OpenErrorCodes.ERR_USER_ACCESS_TOKEN_INVALID, ErrorInfo.ERR_USER_NAME_NOT_NULL);
	    		}
	    		if (query.getIds() == null || query.getResultCode() == null) {
	    			return new ResponseData(ErrorCodes.ERR_PARAM,ErrorInfo.ERR_PARAM);
	    		}
	    		for(int id : query.getIds()) {
		    		MatchResultYstr entity = matchResultService.getById(id);
	    			entity.setReviewFlag(1);
	    			entity.setReviewDatetime(LocalDateTime.now());
	    			entity.setReviewResultCode(String.valueOf(query.getResultCode()));
	    			entity.setReviewResultDesc(query.getReviewResultDesc());
	    			entity.setReviewPersonId(user.getId());
	    			entity.setReviewPersonName(user.getUsername());
	    			matchResultService.updateById(entity);
	    		}
    		} catch (Exception e) {
    			return new ResponseData(ErrorCodes.ERR_PARAM,ErrorInfo.ERR_PARAM);
    		}
    		return new ResponseData();
    }
    
}
