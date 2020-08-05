package com.bazl.dna.database.core.controller.converter.lims;

import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.compress.utils.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.database.core.constants.ErrorCodes;
import com.bazl.dna.database.core.constants.ErrorInfo;
import com.bazl.dna.database.service.model.po.CaseInfo;
import com.bazl.dna.database.service.model.po.CasePersonInfo;
import com.bazl.dna.database.service.model.po.ConsignmentInfo;
import com.bazl.dna.database.service.model.po.DnaSampleInfo;
import com.bazl.dna.database.service.model.po.DnaStrGeneInfo;
import com.bazl.dna.database.service.model.po.DnaYstrGeneInfo;
import com.bazl.dna.database.service.model.vo.ConverterDataSampleAndPersonVo;
import com.bazl.dna.database.service.model.vo.ConverterDataVo;
import com.bazl.dna.database.service.service.ConverterDataService;
import com.bazl.dna.util.DateUtil;

/**
 * 上报到dna库
 */
@RestController
@RequestMapping("/converter/lims")
public class ConverterLimsController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ConverterLimsController.class);

	@Autowired
	private ConverterDataService service;
	
	@PostMapping("save")
	public ResponseData save(@RequestBody JSONObject json) {
		try {
			// 转换对象
			ConverterDataVo vo = convert(json);
			if (vo == null) {
				return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM); 
			}
			// 保存
			int result = service.saveCase(vo, true);
			return new ResponseData(result);
		} catch (Exception e) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
	}
	
	/**
	 * 转换对象
	 * @param json
	 * @return
	 */
	private ConverterDataVo convert(JSONObject json) {
		try {
			ConverterDataVo vo = new ConverterDataVo();
			
			// 案件信息
			JSONObject caseInfoJson = json.getJSONObject("caseInfo");
			if (caseInfoJson == null) 
				return null;
			// 委托信息
			JSONObject consignmentInfoJson = json.getJSONObject("consignmentInfo");
			if (consignmentInfoJson == null)
				return null;
			
			String labServerNo = consignmentInfoJson.getString("areaOrgCode");
			vo.setCaseInfo(convertCaseInfo(caseInfoJson, labServerNo));
			vo.setConsignmentInfo(convertConsignmentInfo(consignmentInfoJson, labServerNo));
			
			// 样本和人员信息
			JSONArray samplePersonJsonArray = json.getJSONArray("samplePersonList");
			vo.setSamplePersonList(convertSamplePersonList(samplePersonJsonArray, labServerNo));
			
			return vo;
		} catch (Exception e) {
			LOGGER.error("Error convert:", e);
		}
		return null;
	}
	
	private List<ConverterDataSampleAndPersonVo> convertSamplePersonList(JSONArray jsonArray, String labServerNo) {
		List<ConverterDataSampleAndPersonVo> result = Lists.newArrayList();
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject json = jsonArray.getJSONObject(i);
			
			ConverterDataSampleAndPersonVo vo = new ConverterDataSampleAndPersonVo();
			vo.setDnaSampleInfo(convertDnaSample(json.getJSONObject("dnaSampleInfo"), labServerNo));
			vo.setCasePersonInfo(convertCasePersonInfo(json.getJSONObject("casePersonInfo"), json.getJSONObject("casePersonDetail"), labServerNo));
			vo.setDnaStrGeneInfoList(convertDnaStrGeneInfo(json.getJSONArray("strGeneInfoList"), labServerNo));
			vo.setDnaYstrGeneInfoList(convertDnaYstrGeneInfo(json.getJSONArray("ystrGeneInfoList"), labServerNo));
			result.add(vo);
		}
		return result;
	}
	
	private List<DnaYstrGeneInfo> convertDnaYstrGeneInfo(JSONArray jsonArray, String labServerNo) {
		List<DnaYstrGeneInfo> result = Lists.newArrayList();
		if (jsonArray != null && !jsonArray.isEmpty()) {
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject json = jsonArray.getJSONObject(i);
				
				DnaYstrGeneInfo entity = new DnaYstrGeneInfo();
				entity.setDeleteFlag(json.getInteger("deleteFlag"));
				entity.setDnaPanelId(null);
				entity.setGeneImage(json.getString("imagePath"));
				entity.setGeneInfo(json.getString("geneInfo"));
				entity.setKitname(json.getString("panelName"));
				entity.setLabServerNo(labServerNo);
				entity.setLimsYstrUuid(json.getString("geneId"));
				entity.setLocusCount(0);
				entity.setStoreDatetime(DateUtil.dateToLocalDateTime(DateUtil.timestampToDate(new Timestamp(json.getLongValue("auditDatetime")))));
				entity.setStorePersonId(json.getString("updatePerson"));
				entity.setStorePersonName(json.getString("updatePerson"));
				result.add(entity);
			}
		}
		return result;
	}
	
	private List<DnaStrGeneInfo> convertDnaStrGeneInfo(JSONArray jsonArray, String labServerNo) {
		List<DnaStrGeneInfo> result = Lists.newArrayList();
		if (jsonArray != null && !jsonArray.isEmpty()) {
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject json = jsonArray.getJSONObject(i);
				
				DnaStrGeneInfo entity = new DnaStrGeneInfo();
				entity.setDeleteFlag(json.getInteger("deleteFlag"));
				entity.setDnaPanelId(null);
				entity.setGeneImage(json.getString("imagePath"));
				entity.setGeneInfo(json.getString("geneInfo"));
				entity.setKitname(json.getString("panelName"));
				entity.setLabServerNo(labServerNo);
				entity.setLimsStrUuid(json.getString("geneId"));
				entity.setLocusCount(0);
				entity.setStoreDatetime(DateUtil.dateToLocalDateTime(DateUtil.timestampToDate(new Timestamp(json.getLongValue("auditDatetime")))));
				entity.setStorePersonId(json.getString("updatePerson"));
				entity.setStorePersonName(json.getString("updatePerson"));
				
				result.add(entity);
			}
		}
		return result;
	}
	
	private CasePersonInfo convertCasePersonInfo(JSONObject json, JSONObject detailJson, String labServerNo) {
		if (json != null) {
			CasePersonInfo result = new CasePersonInfo();
			result.setDeleteFlag(json.getInteger("deleteFlag"));
			result.setLabServerNo(labServerNo);
			result.setLimsPersonUuid(json.getString("personId"));
			result.setPhoneNumber(null);
			result.setStoreDatetime(DateUtil.dateToLocalDateTime(DateUtil.timestampToDate(new Timestamp(json.getLongValue("createDatetime")))));
			result.setStorePersonId(json.getString("createPerson"));
			result.setStorePersonName(json.getString("updatePerson"));
			
			if (detailJson != null) {
				result.setNationality(detailJson.getString("personNationality"));
				result.setNoneIdcardDesc(detailJson.getString("noIdCardDesc"));
				result.setOtherCentificateNo(null);
				result.setOtherCentificateType(null);
				result.setPersonAddr(detailJson.getString("personCurrentAddress"));
				result.setPersonAge(detailJson.getString("perosnAge"));
				result.setPersonAlias(detailJson.getString("personAlias"));
				result.setPersonGender(detailJson.getString("personGender"));
				result.setPersonHeight(detailJson.getString("personHeight"));
				result.setPersonIdcardNo(detailJson.getString("personIdCard"));
				result.setPersonName(detailJson.getString("personName"));
				result.setPersonRace(detailJson.getString("personRace"));
				result.setPersonType(detailJson.getString("personType"));
				result.setPersonWeight(detailJson.getString("personWeight"));
			}
			return result;
		}
		return null;
	}
	
	private DnaSampleInfo convertDnaSample(JSONObject json, String labServerNo) {
		if (json != null) {
			DnaSampleInfo result = new DnaSampleInfo();
			
			result.setDeleteFlag(json.getInteger("deleteFlag"));
			result.setEvidenceFlag(json.getInteger("instoredFlag"));
			result.setInstoreDataType(json.getString("instoredType"));
			result.setLabServerNo(labServerNo);
			result.setLimsSampleUuid(json.getString("sampleId"));
			result.setSampleCarrier(json.getString("sampleCarrier"));
			result.setSampleDesc(json.getString("sampleDesc"));
			result.setSampleEvidenceNo(json.getString("evidenceNo"));
			result.setSampleLabNo(json.getString("sampleNo"));
			result.setSampleName(json.getString("sampleName"));
			result.setSamplePackage(json.getString("samplePacking"));
			result.setSampleType(json.getString("sampleType"));
			result.setStoreDatetime(DateUtil.dateToLocalDateTime(DateUtil.timestampToDate(new Timestamp(json.getLongValue("instoredDatetime")))));
			result.setStorePersonId(json.getString("createPerson"));
			result.setStorePersonName(json.getString("instoredPerson"));
			
			return result;
		}
		return null;
	}

	/**
	 * 委托信息
	 * @param json
	 * @return
	 */
	private ConsignmentInfo convertConsignmentInfo(JSONObject json, String labServerNo) {
		ConsignmentInfo result = new ConsignmentInfo();
		
		result.setAcceptDatetime(DateUtil.dateToLocalDateTime(DateUtil.timestampToDate(new Timestamp(json.getLongValue("acceptDatetime")))));
		result.setAcceptNo(json.getString("consignmentNo"));
		result.setAcceptPersonId(json.getString("acceptorId"));
		result.setAcceptPersonName(null);
		result.setAppendFlag(json.getInteger("appendFlag"));
		result.setAppendSno(null);
		result.setCaseType("0");
		result.setConsignmentFileNo(json.getString("consignmentNo"));
		result.setConsignmentRegDatetime(DateUtil.dateToLocalDateTime(DateUtil.timestampToDate(new Timestamp(json.getLongValue("delegateDatetime")))));
		result.setConsignOrgAddress(null);
		result.setConsignOrgCode(json.getString("delegateOrgCode"));
		result.setConsignOrgFax(null);
		result.setConsignOrgId(json.getString("acceptOrgId"));
		result.setConsignOrgName(json.getString("delegateOrgName"));
		result.setConsignOrgPhone(null);
		result.setConsignOrgZipCode(json.getString("areaOrgCode"));
		
		result.setConsignPerson1CertificateNo(json.getString("delegator1PaperworkNo"));
		result.setConsignPerson1CertificateType(json.getString("delegator1PaperworkType"));
		result.setConsignPerson1Id(json.getString("delegator1Id"));
		result.setConsignPerson1Name(json.getString("delegator1Name"));
		result.setConsignPerson1Phone(json.getString("delegator1PhoneNumber"));
		result.setConsignPerson1PositionName(json.getString("delegator1Position"));
		
		result.setConsignPerson2CertificateNo(json.getString("delegator2PaperworkNo"));
		result.setConsignPerson2CertificateType(json.getString("delegator2PaperworkType"));
		result.setConsignPerson2Id(json.getString("delegator2Id"));
		result.setConsignPerson2Name(json.getString("delegator2Name"));
		result.setConsignPerson2Phone(json.getString("delegator2PhoneNumber"));
		result.setConsignPerson2PositionName(json.getString("delegator2Position"));
		
		result.setDeleteFlag(json.getInteger("deleteFlag"));
		result.setIdentifyRequirement(json.getString("identifyRequirement"));
		result.setLabServerNo(labServerNo);
		result.setLimsConsignmentUuid(json.getString("consignmentId"));
		result.setMoreRemarks(null);
		result.setStoreDatetime(DateUtil.dateToLocalDateTime(DateUtil.timestampToDate(new Timestamp(json.getLongValue("createDatetime")))));
		result.setStorePersonId(json.getString("createPerson"));
		result.setStorePersonName(null);
		result.setSysXkWtno(null);
	
		return result;
	}

	/**
	 * 案件信息
	 * @param json
	 * @return
	 */
	private CaseInfo convertCaseInfo(JSONObject json, String labServerNo) {
		CaseInfo result = new CaseInfo();
		
		result.setCaseAcceptNo(json.getString("caseNo"));
		result.setCaseLevel(json.getString("majorType"));
		result.setCaseName(json.getString("caseName"));
		result.setCaseProperty(json.getString("caseProperty"));
		result.setCaseStatus(json.getInteger("caseStatus"));
		result.setCaseSubProperty(null);
		result.setCaseSummary(json.getString("caseBrief"));
		result.setCaseType(json.getString("caseType"));
		result.setLabServerNo(labServerNo);
		result.setLimsCaseUuid(json.getString("caseId"));
		result.setMoreRemarks(null);
		result.setNationSysNo(json.getString("consignationXkNo"));
		result.setOccurrenceDatetime(DateUtil.dateToLocalDateTime(DateUtil.timestampToDate(new Timestamp(json.getLongValue("caseDatetime")))));
		result.setScenePlace(json.getString("caseLocation"));
		result.setSceneRegionalism(null);
		result.setStoreDatetime(DateUtil.dateToLocalDateTime(DateUtil.timestampToDate(new Timestamp(json.getLongValue("createDatetime")))));
		result.setStorePersonId(json.getString("createPerson"));
		result.setStorePersonName(json.getString("acceptorName"));
		result.setSysCaseAno(json.getString("xkANo"));
		result.setSysXkNo(json.getString("caseXkNo"));
		result.setDeleteFlag(json.getInteger("deleteFlag"));
		
		return result;
	}
}
