package com.bazl.dna.database.core.test;

import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.database.service.model.po.CaseInfo;
import com.bazl.dna.database.service.model.po.CasePersonInfo;
import com.bazl.dna.database.service.model.po.ConsignmentInfo;
import com.bazl.dna.database.service.model.po.DnaSampleInfo;
import com.bazl.dna.database.service.model.po.DnaStrGeneInfo;
import com.bazl.dna.database.service.model.po.DnaYstrGeneInfo;
import com.bazl.dna.database.service.model.vo.ConverterDataSampleAndPersonVo;
import com.bazl.dna.database.service.model.vo.ConverterDataVo;
import com.bazl.dna.util.DateUtil;

public class TestMain {

	public static void main(String[] args) {
		String s = "{\"samplePersonList\":[{\"casePersonInfo\":{\"caseId\":\"d7f379c3-a274-4dfb-aa65-1af2939d7b67\",\"consignmentId\":\"938b9e70-cb17-432a-a8d4-e761578aef29\",\"createDatetime\":1594888734001,\"createPerson\":\"shy\",\"deleteFlag\":\"0\",\"personDetailId\":\"41abe524-e842-4d80-b3e6-f0719de70d51\",\"personId\":\"dd70f775-14fd-4bdf-8452-3c4058c5a702\",\"personName\":\"地方\",\"personType\":\"04\",\"personTypeName\":\"受害人亲属\",\"relationType\":\"03\"},\"ystrGeneInfoList\":[],\"strGeneInfoList\":[{\"auditDatetime\":1588512411511,\"auditStatus\":\"1\",\"auditor\":\"yxn\",\"boardNo\":\"2020-04-09-YXN\",\"boardPosition\":\"D06\",\"caseId\":\"c06e2530-678d-4df6-9627-6bc704c6b953\",\"consignmentId\":\"c401eadf-112a-4e21-8109-7e2704450e72\",\"deleteFlag\":\"0\",\"geneId\":\"10bac699-9635-4c85-9b15-7aff69557c20\",\"geneInfo\":\"{\\\"AMEL\\\":[\\\"X\\\",\\\"Y\\\"],\\\"CSF1PO\\\":[\\\"12\\\",\\\"12\\\"],\\\"D13S317\\\":[\\\"9\\\",\\\"9\\\"],\\\"D16S539\\\":[\\\"11\\\",\\\"11\\\"],\\\"D18S51\\\":[\\\"14\\\",\\\"14\\\"],\\\"D19S433\\\":[\\\"13.2\\\",\\\"15.2\\\"],\\\"D21S11\\\":[\\\"29\\\",\\\"33.2\\\"],\\\"D2S1338\\\":[\\\"19\\\",\\\"20\\\"],\\\"D3S1358\\\":[\\\"15\\\",\\\"16\\\"],\\\"D5S818\\\":[\\\"12\\\",\\\"12\\\"],\\\"D7S820\\\":[\\\"12\\\",\\\"12\\\"],\\\"D8S1179\\\":[\\\"17\\\",\\\"17\\\"],\\\"FGA\\\":[\\\"22\\\",\\\"22\\\"],\\\"TH01\\\":[\\\"7\\\",\\\"9\\\"],\\\"TPOX\\\":[\\\"8\\\",\\\"11\\\"],\\\"vWA\\\":[\\\"14\\\",\\\"17\\\"]}\",\"geneType\":\"0\",\"imagePath\":\"ftp://14.168.81.131//upload//2020-04-09-YXN_2020-04-09-YXN_SY200309-02_D06_12.png\",\"labAnalysisInfoId\":\"74911e85-ea3f-4b0f-b76a-c88ad3f9fd9f\",\"panelName\":\"Identifiler\",\"sampleId\":\"49c2e1f2-e307-4b16-8042-a622ff22bfa9\",\"updateDatetime\":1588512411511,\"updatePerson\":\"yxn\"}],\"dnaSampleInfo\":{\"caseId\":\"75488d06-3aeb-4dda-b38d-fc30c948250e\",\"consignmentId\":\"c401eadf-112a-4e21-8109-7e2704450e72\",\"coreTakenStats\":\"0\",\"createDatetime\":1586310492254,\"createPerson\":\"shy\",\"deleteFlag\":\"0\",\"evidenceNo\":\"W11011301000020200400212005003\",\"extractDatetime\":1586275200000,\"extractMethod\":\"04\",\"extractPerson\":\"yxn\",\"instoredDatetime\":1595213751184,\"instoredFlag\":\"1\",\"instoredPerson\":\"dch\",\"instoredType\":\"01\",\"isRetain\":\"0\",\"linkId\":\"dd70f775-14fd-4bdf-8452-3c4058c5a702\",\"sampleCarrier\":\"03\",\"sampleDesc\":\"便道上捡取\",\"sampleFlag\":\"0\",\"sampleId\":\"49c2e1f2-e307-4b16-8042-a622ff22bfa9\",\"sampleName\":\"无牌烟蒂\",\"sampleNo\":\"SY200309-02\",\"samplePacking\":\"01\",\"samplePurpose\":\"DNA检验\",\"sampleStatus\":\"02\",\"sampleType\":\"04\",\"updateDatetime\":1588512452773,\"updatePerson\":\"yxn\"}}],\"consignmentInfo\":{\"acceptDatetime\":1589164787370,\"acceptOrgId\":\"110101000000\",\"acceptorId\":\"8cc160c8-a274-473c-82f7-54ec942d459b\",\"appendFlag\":\"0\",\"areaOrgCode\":\"110101001017\",\"caseId\":\"75488d06-3aeb-4dda-b38d-fc30c948250e\",\"consignmentId\":\"adb86f1a-f1ae-42b1-ba4e-017784951f6c\",\"consignmentNo\":\"2020WZ0257\",\"createDatetime\":1589163211496,\"createPerson\":\"dc_d4tz\",\"delegateDatetime\":1589164787370,\"delegateOrgCode\":\"110101001062\",\"delegateOrgName\":\"东城分局第四探组\",\"delegator1Id\":\"a8544f57-abc9-4369-b60c-0782b7ab6260\",\"delegator1Name\":\"郭康健\",\"delegator1PaperworkNo\":\"022317\",\"delegator1PaperworkType\":\"警官证\",\"delegator1PhoneNumber\":\"13581686693\",\"delegator1Position\":\"08\",\"delegator2Id\":\"d29a4b4b-c6ee-4068-8c83-817ad9aa5ac8\",\"delegator2Name\":\"李兴\",\"delegator2PaperworkNo\":\"022987\",\"delegator2PaperworkType\":\"警官证\",\"delegator2PhoneNumber\":\"15810467313\",\"delegator2Position\":\"08\",\"deleteFlag\":\"0\",\"identifyRequirement\":\"01\",\"identifyType\":\"DNA鉴定\",\"reidentifyCount\":0,\"status\":\"02\",\"updateDatetime\":1589164787370,\"updatePerson\":\"022474\"},\"caseInfo\":{\"acceptorName\":\"李玉珊\",\"caseBrief\":\"2020年5月4日，李建军报警称：停放于北京市东城区和平里中街3号院8号楼东北侧的银色途观汽车（车牌号：京JG0453）车窗破损。\",\"caseDatetime\":1588435200000,\"caseId\":\"75488d06-3aeb-4dda-b38d-fc30c948250e\",\"caseLocation\":\"北京市东城区和平里中街3号院8号楼东北侧\",\"caseName\":\"李建军被故意损毁财物案\",\"caseNo\":\"DC2020WZ0207\",\"caseProperty\":\"20\",\"caseStatus\":\"02\",\"caseType\":\"01\",\"caseUrgentFlag\":\"0\",\"caseXkNo\":\"K1101011406002020050005\",\"consignationXkNo\":\"WT1101011406002020050019\",\"createDatetime\":1589163211199,\"createPerson\":\"dc_d4tz\",\"deleteFlag\":\"0\",\"entrustStatus\":\"0\",\"firstChecker\":\"8cc160c8-a274-473c-82f7-54ec942d459b\",\"hasAppendFlag\":\"0\",\"majorNo\":\"DNA鉴定\",\"majorType\":\"01\",\"updateDatetime\":1589164787354,\"updatePerson\":\"022474\",\"xkANo\":\"A1101015200002020055020\"},\"orgId\":\"110101000000\"}";
		JSONObject json = JSON.parseObject(s);
		ConverterDataVo vo = new ConverterDataVo();
		
		JSONObject caseInfoJson = json.getJSONObject("caseInfo");
		vo.setCaseInfo(convertCaseInfo(caseInfoJson));
		
		JSONObject consignmentInfoJson = json.getJSONObject("consignmentInfo");
		vo.setConsignmentInfo(convertConsignmentInfo(consignmentInfoJson));
		
		JSONArray samplePersonJsonArray = json.getJSONArray("samplePersonList");
		vo.setSamplePersonList(convertSamplePersonList(samplePersonJsonArray));
		
		System.out.println(vo);
	}

	private static List<ConverterDataSampleAndPersonVo> convertSamplePersonList(JSONArray jsonArray) {
		List<ConverterDataSampleAndPersonVo> result = Lists.newArrayList();
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject json = jsonArray.getJSONObject(i);
			
			ConverterDataSampleAndPersonVo vo = new ConverterDataSampleAndPersonVo();
			vo.setDnaSampleInfo(convertDnaSample(json.getJSONObject("dnaSampleInfo")));
			vo.setCasePersonInfo(convertCasePersonInfo(json.getJSONObject("casePersonInfo"), json.getJSONObject("casePersonDetail")));
			vo.setDnaStrGeneInfoList(convertDnaStrGeneInfo(json.getJSONArray("strGeneInfoList")));
			vo.setDnaYstrGeneInfoList(convertDnaYstrGeneInfo(json.getJSONArray("ystrGeneInfoList")));
			result.add(vo);
		}
		return result;
	}
	
	private static List<DnaYstrGeneInfo> convertDnaYstrGeneInfo(JSONArray jsonArray) {
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
				entity.setLabServerNo(null);
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
	
	private static List<DnaStrGeneInfo> convertDnaStrGeneInfo(JSONArray jsonArray) {
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
				entity.setLabServerNo(null);
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
	
	private static CasePersonInfo convertCasePersonInfo(JSONObject json, JSONObject detailJson) {
		if (json != null) {
			CasePersonInfo result = new CasePersonInfo();
			
			result.setDeleteFlag(json.getInteger("deleteFlag"));
			result.setLabServerNo(null);
			result.setLimsPersonUuid(json.getString("personId"));
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
			result.setPhoneNumber(null);
			result.setStoreDatetime(DateUtil.dateToLocalDateTime(DateUtil.timestampToDate(new Timestamp(json.getLongValue("createDatetime")))));
			result.setStorePersonId(json.getString("createPerson"));
			result.setStorePersonName(json.getString("updatePerson"));
			
			return result;
		}
		return null;
	}
	
	private static DnaSampleInfo convertDnaSample(JSONObject json) {
		if (json != null) {
			DnaSampleInfo result = new DnaSampleInfo();
			
			result.setDeleteFlag(json.getInteger("deleteFlag"));
			result.setEvidenceFlag(json.getInteger("instoredFlag"));
			result.setInstoreDataType(json.getString("instoredType"));
			result.setLabServerNo(null);
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

	private static ConsignmentInfo convertConsignmentInfo(JSONObject json) {
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
		result.setLabServerNo(null);
		result.setLimsConsignmentUuid(json.getString("consignmentId"));
		result.setMoreRemarks(null);
		result.setStoreDatetime(DateUtil.dateToLocalDateTime(DateUtil.timestampToDate(new Timestamp(json.getLongValue("createDatetime")))));
		result.setStorePersonId(json.getString("createPerson"));
		result.setStorePersonName(null);
		result.setSysXkWtno(null);
		
		return result;
	}

	private static CaseInfo convertCaseInfo(JSONObject json) {
		CaseInfo result = new CaseInfo();
		
		result.setCaseAcceptNo(json.getString("caseNo"));
		result.setCaseLevel(json.getString("majorType"));
		result.setCaseName(json.getString("caseName"));
		result.setCaseProperty(json.getString("caseProperty"));
		result.setCaseStatus(json.getInteger("caseStatus"));
		result.setCaseSubProperty(null);
		result.setCaseSummary(json.getString("caseBrief"));
		result.setCaseType(json.getString("caseType"));
		result.setLabServerNo(null);
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
