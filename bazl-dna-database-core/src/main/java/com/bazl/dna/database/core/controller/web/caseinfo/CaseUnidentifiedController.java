package com.bazl.dna.database.core.controller.web.caseinfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.database.core.controller.BaseController;
import com.bazl.dna.database.service.model.po.CaseInfo;
import com.bazl.dna.database.service.model.po.CasePersonInfo;
import com.bazl.dna.database.service.model.po.ConsignmentInfo;
import com.bazl.dna.database.service.model.po.DnaSampleInfo;
import com.bazl.dna.database.service.model.po.PersonRelativeInfo;
import com.bazl.dna.database.service.model.qo.CaseUnidentifiedQuery;
import com.bazl.dna.database.service.service.CaseInfoService;
import com.bazl.dna.database.service.service.CasePersonInfoService;
import com.bazl.dna.database.service.service.CaseUnidentifiedService;
import com.bazl.dna.database.service.service.ConsignmentInfoService;
import com.bazl.dna.database.service.service.DnaSampleInfoService;
import com.bazl.dna.database.service.service.PersonRelationInfoService;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * 身份不明人员案件
 * @author zhaoyong
 *
 */
@RestController
@RequestMapping("/caseunidentified")
public class CaseUnidentifiedController extends BaseController {

	@Autowired
	private CaseUnidentifiedService caseUnidentifiedService;
	
	@Autowired
	private ConsignmentInfoService consignmentInfoService;
	
	@Autowired
	private CaseInfoService caseInfoService;
	
	@Autowired
	private CasePersonInfoService casePersonInfoService;
	
	@Autowired
	private PersonRelationInfoService personRelationInfoService;
	
	@Autowired
	private DnaSampleInfoService dnaSampleInfoService;
	
	/**
	 * 列表
	 * @param paramJson
	 * @return
	 */
	@PostMapping("list")
	public ResponseData list(@RequestBody CaseUnidentifiedQuery query) {
		// 案件信息 委托信息列表
		Map<String, Object> result = caseUnidentifiedService.findList(query);
		return new ResponseData(result);
	}
	
	@GetMapping("get/{id}")
	public ResponseData get(@PathVariable Integer id) {
		Map<String, Object> result = Maps.newHashMap();
		
		// 委托信息
		ConsignmentInfo consignmentInfo = consignmentInfoService.getById(id);
		result.put("consignmentInfo", consignmentInfo);
		
		// 案件信息 
		CaseInfo caseInfo = caseInfoService.getById(consignmentInfo.getCaseId());
		result.put("caseInfo", caseInfo);
		
		// 失踪人员列表
		Map<String, Object> missMap = Maps.newHashMap();
		missMap.put("CASE_ID", caseInfo.getId());
		missMap.put("CONSIGNMENT_ID", consignmentInfo.getId());
		
		List<CasePersonInfo> unidentifiedList = casePersonInfoService.listByMap(missMap);
		result.put("unidentifiedList", unidentifiedList);
		
		// 失踪人员亲属列表
		Set<Integer> set = Sets.newHashSet();
		Map<Integer, String> relativeMap = Maps.newHashMap();
		List<Map<String, Object>> relativeList = Lists.newArrayList();
		for (CasePersonInfo casePersonInfo : unidentifiedList) {
			Map<String, Object> relationMap = Maps.newHashMap();
			relationMap.put("TARGET_PERSON_UUID", String.valueOf(casePersonInfo.getId()));
			List<PersonRelativeInfo> relationList = personRelationInfoService.listByMap(relationMap);
			for (PersonRelativeInfo relation : relationList) {
				set.add(Integer.parseInt(relation.getRelationPersonUuid()));
				relativeMap.put(Integer.parseInt(relation.getRelationPersonUuid()), relation.getRelationTypeCode());
			}
		}
		for (Integer s : set) {
			CasePersonInfo casePersonInfo = casePersonInfoService.getById(s);
			DnaSampleInfo dnaSampleInfo = dnaSampleInfoService.getOne(new QueryWrapper<DnaSampleInfo>().eq("REF_PERSON_ID", casePersonInfo.getId()));
			
			Map<String, Object> map = Maps.newHashMap();
			map.put("casePersonInfo", casePersonInfo);
			map.put("dnaSampleInfo", dnaSampleInfo);
			map.put("relationTypeCode", relativeMap.get(s));
			relativeList.add(map);
		}
		result.put("unidentifiedRelativeList", relativeList);
		
		return new ResponseData(result);
	}
	
	/**
	 * 身份不明人员详情
	 * @param id
	 * @return
	 */
	@GetMapping("getInfo/{id}")
	public ResponseData getInfo(@PathVariable Integer id) {
		CasePersonInfo casePersonInfo = casePersonInfoService.getById(id);
		DnaSampleInfo dnaSampleInfo = dnaSampleInfoService.getOne(new QueryWrapper<DnaSampleInfo>().eq("REF_PERSON_ID", casePersonInfo.getId()));
		
		Map<String, Object> result = Maps.newHashMap();
		result.put("casePersonInfo", casePersonInfo);
		result.put("dnaSampleInfo", dnaSampleInfo);
		
		return new ResponseData(result);
	}
	
	/**
	 * 身份不明人员亲属详情
	 * @param id
	 * @return
	 */
	@GetMapping("getRelativeInfo/{id}")
	public ResponseData getRelativeInfo(@PathVariable Integer id) {
		CasePersonInfo casePersonInfo = casePersonInfoService.getById(id);
		DnaSampleInfo dnaSampleInfo = dnaSampleInfoService.getOne(new QueryWrapper<DnaSampleInfo>().eq("REF_PERSON_ID", casePersonInfo.getId()));
		
		Map<String, Object> result = Maps.newHashMap();
		result.put("casePersonInfo", casePersonInfo);
		result.put("dnaSampleInfo", dnaSampleInfo);
		
		return new ResponseData(result);
	}
	
}
