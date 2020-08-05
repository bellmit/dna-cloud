package com.bazl.dna.mix.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.mix.controller.base.BaseController;
import com.bazl.dna.mix.model.po.CompareQueue;
import com.bazl.dna.mix.model.po.ContributorInfo;
import com.bazl.dna.mix.model.po.ContributorPossibility;
import com.bazl.dna.mix.model.po.MatchResult;
import com.bazl.dna.mix.model.po.MixedSampleGene;
import com.bazl.dna.mix.service.CompareQueueService;
import com.bazl.dna.mix.service.ContributorInfoService;
import com.bazl.dna.mix.service.ContributorPossibilityService;
import com.bazl.dna.mix.service.MatchResulService;
import com.bazl.dna.mix.service.MixedSampleGeneService;
import com.bazl.dna.mix.utils.ListUtils;
import com.bazl.dna.util.excel.ExportExcelUtils;

/**
 * 导出Excel
 * @author zhaoyong
 *
 */
@RestController
@RequestMapping("/export")
public class ExportController extends BaseController {
	
	@Autowired
    CompareQueueService compareQueueService;
	
	@Autowired
	MixedSampleGeneService mixedSampleGeneService;
	
	@Autowired
	MatchResulService matchResultService;
	
	@Autowired
	ContributorInfoService contributorInfoService;
	
	@Autowired
	ContributorPossibilityService contributorPossibilityService;
	
	@Value("${filePath}")
	private String filePath;
	
	/**
	 * 导出比中记录
	 * @param compareId 队列id
	 * @return
	 */
	@GetMapping(value = "/exportCompare")
	public ResponseEntity<byte[]> exportCompare(String compareId) {
		// 队列信息
		CompareQueue compareQueue = compareQueueService.selectByPrimaryKey(compareId);
		if (compareQueue != null) {
			// 混合样本
			MixedSampleGene mixSampleGene = mixedSampleGeneService.selectById(compareQueue.getMixedSampleId());
			if (mixSampleGene != null) {
				// 同案样本分型 已知贡献者
				List<ContributorInfo> contributorInfoList = contributorInfoService.findListByMixSampleGeneId(mixSampleGene.getId());
				// 可能贡献者位点（拆分）
				List<ContributorPossibility> contributorPossibilityList = null;
//				List<ContributorPossibility> contributorPossibilityList = contributorPossibilityService.findListByCompareId(compareId);
				// 比对结果
				MatchResult matchResult = new MatchResult();
				matchResult.setCompareId(compareId);
				List<MatchResult> matchResultList = matchResultService.selectMatchList(matchResult);
				
				// 构建文件数据
				JSONObject json = buildCompareData(compareQueue, mixSampleGene, 
						contributorInfoList, contributorPossibilityList, matchResultList);
				
				// 构建文件
				String fileName = "compare_" + new Date().getTime() +".xls";
				return buildFile(json, fileName);
			}
		}
		return null;
	}
	
	private ResponseEntity<byte[]> buildFile(JSONObject json, String fileName) {
		try {
			HSSFWorkbook workbook = ExportExcelUtils.mixExportExcel(fileName, json.getJSONObject("titleJson"),
					json.getJSONObject("mixJson"), json.getJSONArray("contributorList"), 
					json.getJSONArray("possibilityList"), json.getJSONArray("sampleList"));
			
			File file = new File(filePath + fileName);
			if(!file.exists()){
				//先得到文件的上级目录，并创建上级目录，在创建文件
				file.getParentFile().mkdir();
				try {
					//创建文件
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			FileOutputStream fileOut = new FileOutputStream(file);
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
			workbook.close();
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentLength(file.length());
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);  
			headers.setContentDispositionFormData("attachment", URLEncoder.encode(fileName, "utf-8")); 
			return new ResponseEntity<>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	private JSONObject buildCompareData(CompareQueue compareQueue,
			MixedSampleGene mixSampleGene, 
			List<ContributorInfo> contributorInfoList, 
			List<ContributorPossibility> contributorPossibilityList, 
			List<MatchResult> matchResultList) {
		
		JSONObject result = new JSONObject();
		
		// 标题
		// ---------------------------------------------------------------------------
        JSONObject titleJson = new JSONObject();
        titleJson.put("sampleCode", "样本实验室编号");
        titleJson.put("sampleName", "样本名称");
        titleJson.put("card", "身份证号");
        titleJson.put("type", "样本库别");
        titleJson.put("server", "服务器");
        titleJson.put("identify", "试剂盒");
        titleJson.put("num", "匹配个数");
        
        JSONArray titleJsonArray = new JSONArray();
        
        // 混合样本
        // ---------------------------------------------------------------------------
        JSONObject mixJson = new JSONObject();
        mixJson.put("sampleCode", mixSampleGene.getSampleNo());
        mixJson.put("sampleName", mixSampleGene.getSampleName());
        mixJson.put("card", "--");
        mixJson.put("type", "--");
        mixJson.put("server", "--");
        mixJson.put("identify", mixSampleGene.getReagentName());
		mixJson.put("num", "");
        
        if (mixSampleGene.getGeneInfo() != null) {
	        JSONArray mixGeneInfoJsonArray = JSONArray.parseArray(mixSampleGene.getGeneInfo());
	        JSONObject mixGeneJson = new JSONObject();
			for (int i = 0; i < mixGeneInfoJsonArray.size(); i++) {
				JSONObject json = mixGeneInfoJsonArray.getJSONObject(i);
				
				titleJsonArray.add(json.getString("name"));
				mixGeneJson.put(json.getString("name"), json.getString("value"));
			}
			titleJson.put("gene", titleJsonArray);
	        result.put("titleJson", titleJson);
	        mixJson.put("gene", mixGeneJson);
	        result.put("mixJson", mixJson);
        }
        
        // 同案样本分型 已知贡献者
        // ---------------------------------------------------------------------------
        JSONArray contributorList = new JSONArray();
        if (ListUtils.isNotNullAndEmptyList(contributorInfoList)) {
	        for (ContributorInfo entity : contributorInfoList) {
	        		JSONObject sampleJson = new JSONObject();
	        		JSONArray jsonArray = JSONArray.parseArray(entity.getGeneInfo());
	            JSONObject sampleGeneJson = new JSONObject();
	    			for (int i = 0; i < jsonArray.size(); i++) {
	    				JSONObject json = jsonArray.getJSONObject(i);
	    				sampleGeneJson.put(json.getString("name"), json.getString("value"));
	    			}
	    			sampleJson.put("gene", sampleGeneJson);
	    			contributorList.add(sampleJson);
	        }
        }
	    result.put("contributorList", contributorList);
        
        // 可能贡献者位点（拆分）
        // ---------------------------------------------------------------------------
        JSONArray possibilityList = new JSONArray();
        if (contributorPossibilityList != null) {
        		for (ContributorPossibility entity : contributorPossibilityList) {
        			JSONObject sampleJson = new JSONObject();
        			JSONArray jsonArray = JSONArray.parseArray(entity.getGeneInfo());
        			JSONObject sampleGeneJson = new JSONObject();
	    			for (int i = 0; i < jsonArray.size(); i++) {
	    				JSONObject json = jsonArray.getJSONObject(i);
	    				sampleGeneJson.put(json.getString("name"), json.getString("value"));
	    			}
	    			sampleJson.put("gene", sampleGeneJson);
	    			possibilityList.add(sampleJson);
        		}
        }
        result.put("possibilityList", possibilityList);
        
        // 样本列表
        // ---------------------------------------------------------------------------
        JSONArray sampleList = new JSONArray();
        if (matchResultList != null) {
	        for (MatchResult entity : matchResultList) {
				JSONObject sampleJson = new JSONObject();
				sampleJson.put("sampleCode", entity.getProportionSampleNo());
	            sampleJson.put("sampleName", entity.getProportionSampleName());
	            sampleJson.put("card", entity.getIdCardNo());
	            sampleJson.put("type", entity.getProportionPersonnel());
	            sampleJson.put("server", entity.getProportionDistrict());
	            sampleJson.put("identify", entity.getProportionKilName());
	            sampleJson.put("num", entity.getRatio());
	            
	            if (entity.getGeneInfo() != null) {
		            JSONArray jsonArray = JSONArray.parseArray(entity.getGeneInfo());
		            JSONObject sampleGeneJson = new JSONObject();
					for (int i = 0; i < jsonArray.size(); i++) {
						JSONObject json = jsonArray.getJSONObject(i);
						sampleGeneJson.put(json.getString("name"), json.getString("value"));
					}
		        		sampleJson.put("gene", sampleGeneJson);
		            sampleList.add(sampleJson);
	            }
	        }
        }
        result.put("sampleList", sampleList);
        
        return result;
	}
	
	
}
