package com.bazl.dna.database.core.controller.web.matched;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
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
import com.bazl.dna.database.service.model.po.LabServerInfo;
import com.bazl.dna.database.service.model.po.MatchResultSame;
import com.bazl.dna.database.service.model.po.MatchResultSameGroup;
import com.bazl.dna.database.service.model.qo.MatchResultCodeQuery;
import com.bazl.dna.database.service.model.qo.MatchStrDatailQuery;
import com.bazl.dna.database.service.model.qo.MatchStrDatailResultQuery;
import com.bazl.dna.database.service.model.qo.MatchStrGeneInfoQuery;
import com.bazl.dna.database.service.model.qo.MatchStrResultQuery;
import com.bazl.dna.database.service.service.CaseInfoService;
import com.bazl.dna.database.service.service.ConsignmentInfoService;
import com.bazl.dna.database.service.service.DictItemService;
import com.bazl.dna.database.service.service.DnaSampleInfoService;
import com.bazl.dna.database.service.service.LabServerInfoService;
import com.bazl.dna.database.service.service.MatchResultSameGroupService;
import com.bazl.dna.database.service.service.MatchResultSameService;
import com.bazl.dna.util.DateUtil;
import com.google.common.collect.Sets;

/**
 * 同型比中信息控制器
 * @author  lizhihua on 2020/2/12.
 */
@RestController
@RequestMapping("/match/same")
public class SameMatchedController extends BaseController {

    @Autowired
    private MatchResultSameGroupService matchResultGroupService;

    @Autowired
    private MatchResultSameService matchResultService;

    @Autowired
    private DnaSampleInfoService dnaSampleInfoService;

    @Autowired
    private CaseInfoService caseInfoService;
    @Autowired
    private ConsignmentInfoService consignmentInfoService;
    @Autowired
    private DictItemService dictItemService;
    @Autowired
    private LabServerInfoService labServerInfoService;

    /**
     * 比中信息管理-同型比中查询结果展示接口
     * @param query
     * @return
     */
    @PostMapping(value = "/resultQuery")
    public ResponseData resultQuery(@RequestBody MatchStrResultQuery query){

        try {
            if (query!=null){
                query = trimMatchStrResultQueryPrams(query); //去除空格查询
            }else{
                query = new MatchStrResultQuery();
            }
            List<MatchStrResultQuery> queryList = matchResultGroupService.resultInfo(query);
            int count = matchResultGroupService.resultCount(query);
            List<List<MatchStrResultQuery>> resultList = new ArrayList<>();

            Set<Integer> groupIdList = new HashSet<>();

            for (MatchStrResultQuery resultQuery : queryList) {
                        //机构信息名称
                        if (StringUtils.isNotBlank(resultQuery.getOrgCode())){
                            LabServerInfo labServerInfo  = labServerInfoService.selectByOrgCode(resultQuery.getOrgCode());
                            if (null!=labServerInfo) {
                                resultQuery.setOrgName(labServerInfo.getLabServerName().trim());
                            }else{
                                resultQuery.setOrgName("");
                            }
                        }
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

            queryList.stream().collect(Collectors.groupingBy(MatchStrResultQuery::getGroupId,Collectors.toList()))
                    .forEach((groupId,fooListByAge)->{
                        resultList.add(fooListByAge);
                    });

            List<Object> list1 = new ArrayList<>();
            for (Integer groupId : groupIdList) {
                Map<String, Object> inMap = new HashMap<>();
                inMap.put("groupId", groupId);
                MatchResultSameGroup group = matchResultGroupService.getById(groupId);
                inMap.put("groupMatchType", group.getGroupType());
                String matchTime = null;
                if (group.getLatestMatchTime() != null) {
                		matchTime = DateUtil.dateToString(DateUtil.localDateTimeToDate(group.getLatestMatchTime()),DateUtil.DATE_FORMAT);
                }
                inMap.put("groupMatchTime", matchTime);//审核时间
                inMap.put("groupMatchLocusCount", group.getGroupSampleCount());
                List<MatchStrResultQuery>  inList = new ArrayList<>();
                for (List<MatchStrResultQuery> resultQuery : resultList) {
                    for (MatchStrResultQuery matchResultQuery : resultQuery) {
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
            logger.error("invoke SameMatchedController.resultQuery error.", ex);
            return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
        }
    }

    //查询项检索去除空格
    private MatchStrResultQuery trimMatchStrResultQueryPrams(MatchStrResultQuery query) {
        if (StringUtils.isNotBlank(query.getSampleName())){
            query.setSampleName(query.getSampleName().replaceAll("\\s*", ""));//样本名称
        }
        if (StringUtils.isNotBlank(query.getPersonName())){
            query.setPersonName(query.getPersonName().replaceAll("\\s*", ""));//人员姓名
        }
        if (StringUtils.isNotBlank(query.getCaseName())){
            query.setCaseName(query.getCaseName().replaceAll("\\s*", ""));//案件名称
        }
        if (StringUtils.isNotBlank(query.getCaseAcceptNo())){
            query.setCaseAcceptNo(query.getCaseAcceptNo().replaceAll("\\s*", ""));//案件受理编号
        }
        if (StringUtils.isNotBlank(query.getSampleNo())){
            query.setSampleNo(query.getSampleNo().replaceAll("\\s*", ""));//样本编号
        }
        if (StringUtils.isNotBlank(query.getPersonCard())){
            query.setPersonCard(query.getPersonCard().replaceAll("\\s*", ""));//人员身份证
        }
        if (StringUtils.isNotBlank(query.getSysXkNo())){
            query.setSysXkNo(query.getSysXkNo().replaceAll("\\s*", ""));//现场勘验调研号
        }

        return  query;
    }

    @PostMapping(value = "/detailQuery")
    public ResponseData detailQuery(@RequestBody MatchStrDatailQuery query){
        try {
        	
            int totalCount = matchResultGroupService.detailCount(query);
            List<MatchStrDatailResultQuery> queryList = matchResultGroupService.detailInfo(query);
            
            Set<String> setCase = Sets.newHashSet();
            int countMember = 0;
            int countSample = 0;
            for (MatchStrDatailResultQuery q : queryList) {
            		setCase.add(q.getCaseName());
            		if (q.getEvidenceFlag().equals("1")) {
            			countMember++;
            		} else {
            			countSample++;
            		}
            }

            //分页信息
            PageInfo pageInfo = new PageInfo();
            pageInfo.setAllRecordCount(totalCount);//总计条数
            if(totalCount >0 && totalCount % pageInfo.getEvePageRecordCnt() == 0){ //总计页码
                pageInfo.setPageCount((int)totalCount/pageInfo.getEvePageRecordCnt());
            }else{
                pageInfo.setPageCount((int)totalCount/pageInfo.getEvePageRecordCnt()+1);
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
            logger.error("invoke SameMatchedController.detailQuery error.", ex);
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
            MatchResultSame matchResult = matchResultService.getOne(new QueryWrapper<MatchResultSame>().eq("ID", id));
            if (matchResult != null) {
	            //查询基因信息
				JSONObject geneDetailsJson = JSONObject.parseObject(matchResult.getMatchedGeneDetails());
				geneDetailsJson.put("reviewPersonName", matchResult.getReviewPersonName());//审核人
				geneDetailsJson.put("reviewResultCode", matchResult.getReviewResultCode());//审核状态
				geneDetailsJson.put("reviewTime", "");
				if (matchResult.getReviewDatetime() != null) {
					geneDetailsJson.put("reviewTime", DateUtil.dateToString(DateUtil.localDateTimeToDate(matchResult.getReviewDatetime()),DateUtil.DATE_FORMAT));//审核时间
				}
				geneDetailsJson.put("matchTime", DateUtil.dateToString(DateUtil.localDateTimeToDate(matchResult.getMatchDatetime()),DateUtil.DATE_FORMAT));//比中时间
				
	            List<MatchStrGeneInfoQuery> geneInfoQueries = new ArrayList<>();
				if (matchResult.getSampleaId() != null) {
					MatchStrGeneInfoQuery matchGeneInfoQuery = new MatchStrGeneInfoQuery();
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
					MatchStrGeneInfoQuery matchGeneInfoQuery = new MatchStrGeneInfoQuery();
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
            logger.error("invoke SameMatchedController.geneQuery error.", ex);
            return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
        }
        return new ResponseData();
    }
    
    /**
     * 更新比中状态
     * @param groupId
     * @param flag
     * @return
     */
    @PostMapping("updateResultCode")
    public ResponseData updateResultCode(@CurrentUser AuthUser user, @RequestBody MatchResultCodeQuery query) {
    		try {
	    		if (user == null) {
	    			return new ResponseData(OpenErrorCodes.ERR_USER_ACCESS_TOKEN_INVALID, ErrorInfo.ERR_USER_NAME_NOT_NULL);
	    		}
	    		if (query.getIds() == null || query.getResultCode() == null) {
	    			return new ResponseData(ErrorCodes.ERR_PARAM,ErrorInfo.ERR_PARAM);
	    		}
	    		for(int id : query.getIds()) {
		    		MatchResultSame entity = matchResultService.getById(id);
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
