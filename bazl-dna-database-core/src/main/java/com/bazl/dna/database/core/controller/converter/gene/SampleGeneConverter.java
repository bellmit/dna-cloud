package com.bazl.dna.database.core.controller.converter.gene;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.database.core.constants.ErrorCodes;
import com.bazl.dna.database.core.constants.ErrorInfo;
import com.bazl.dna.database.core.controller.BaseController;
import com.bazl.dna.database.service.model.po.DnaMixGeneInfo;
import com.bazl.dna.database.service.model.po.DnaStrGeneInfo;
import com.bazl.dna.database.service.model.po.DnaYstrGeneInfo;
import com.bazl.dna.database.service.service.DnaMixGeneInfoService;
import com.bazl.dna.database.service.service.DnaStrGeneInfoService;
import com.bazl.dna.database.service.service.DnaYstrGeneInfoService;

/**
 * 样本基因信息转换
 */
@RestController
@RequestMapping("/converter/gene")
public class SampleGeneConverter extends BaseController {

	@Autowired
	private DnaStrGeneInfoService strGeneInfoService;
	
	@Autowired
	private DnaYstrGeneInfoService ystrGeneInfoService;
	
	@Autowired
	private DnaMixGeneInfoService mixGeneInfoService;
	
	/**
	 * str基因信息保存
	 * @param geneInfo
	 * @return
	 */
	@PostMapping("strGeneInfoSave")
	public ResponseData strGeneInfoSave(@RequestBody DnaStrGeneInfo geneInfo) {
		if (geneInfo == null) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		strGeneInfoService.save(geneInfo);
		return new ResponseData(geneInfo);
	}
	
	/**
	 * ystr基因信息保存
	 * @param geneInfo
	 * @return
	 */
	@PostMapping("ystrGeneInfoSave")
	public ResponseData strGeneInfoSave(@RequestBody DnaYstrGeneInfo geneInfo) {
		if (geneInfo == null) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		ystrGeneInfoService.save(geneInfo);
		return new ResponseData(geneInfo);
	}
	
	/**
	 * mix基因信息保存
	 * @param geneInfo
	 * @return
	 */
	@PostMapping("mixGeneInfoSave")
	public ResponseData mixGeneInfoSave(@RequestBody DnaMixGeneInfo geneInfo) {
		if (geneInfo == null) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		mixGeneInfoService.save(geneInfo);
		return new ResponseData(geneInfo);
	}
	
}
