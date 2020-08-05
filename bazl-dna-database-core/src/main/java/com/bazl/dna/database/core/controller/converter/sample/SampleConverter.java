package com.bazl.dna.database.core.controller.converter.sample;

import com.bazl.dna.database.service.model.po.QcSampleInfo;
import com.bazl.dna.database.service.service.QcSampleInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.database.core.constants.ErrorCodes;
import com.bazl.dna.database.core.constants.ErrorInfo;
import com.bazl.dna.database.core.controller.BaseController;
import com.bazl.dna.database.service.model.po.DnaSampleImage;
import com.bazl.dna.database.service.model.po.DnaSampleInfo;
import com.bazl.dna.database.service.service.DnaSampleImageService;
import com.bazl.dna.database.service.service.DnaSampleInfoService;

import java.util.HashMap;

/**
 * 样本信息转换
 */
@RestController
@RequestMapping("/converter/sample")
public class SampleConverter extends BaseController {

	@Autowired
	private DnaSampleInfoService sampleInfoService;
	
	@Autowired
	private DnaSampleImageService sampleImageService;

	@Autowired
	private QcSampleInfoService qcsampleInfo;
	
	/**
	 * 样本信息保存
	 * @param sampleInfo
	 * @return
	 */
	@PostMapping("save")
	public ResponseData save(@RequestBody DnaSampleInfo sampleInfo) {
		if (sampleInfo == null) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		sampleInfoService.save(sampleInfo);
        HashMap<String, String> map = new HashMap<>();
        map.put("id",sampleInfo.getId().toString());
        return new ResponseData(map);
	}

	/**
	 * 样本信息保存
	 */
	@PostMapping("qcsave")
	public ResponseData qcsave(@RequestBody QcSampleInfo qcSampleInfo) {
		if (qcSampleInfo == null) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		qcsampleInfo.save(qcSampleInfo);
		HashMap<String, String> map = new HashMap<>();
		map.put("id",qcSampleInfo.getId().toString());
		return new ResponseData(map);
	}

	/**
	 * 样本信息图片保存
	 * @param sampleImage
	 * @return
	 */
	@PostMapping("saveImage")
	public ResponseData saveImage(@RequestBody DnaSampleImage sampleImage) {
		if (sampleImage == null) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		sampleImageService.save(sampleImage);
		return new ResponseData(sampleImage);
	}
	
}
