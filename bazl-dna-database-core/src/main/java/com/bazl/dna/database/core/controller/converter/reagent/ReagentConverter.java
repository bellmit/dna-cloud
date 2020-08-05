package com.bazl.dna.database.core.controller.converter.reagent;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.database.core.constants.ErrorCodes;
import com.bazl.dna.database.core.constants.ErrorInfo;
import com.bazl.dna.database.core.controller.BaseController;
import com.bazl.dna.database.service.model.po.*;
import com.bazl.dna.database.service.service.DnaLocusInfoService;
import com.bazl.dna.database.service.service.DnaPanelInfoService;
import com.bazl.dna.database.service.service.DnaPanelLocusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 试剂盒基因座
 */
@RestController
@RequestMapping("/converter/reagent")
public class ReagentConverter extends BaseController {

	@Autowired
	private DnaPanelInfoService dnaPanelInfoService;
	@Autowired
	private DnaPanelLocusService dnaPanelLocusService;
	@Autowired
	private DnaLocusInfoService dnaLocusInfoService;

	/**
	 * 试剂盒信息保存
	 */
	@PostMapping("save")
	public ResponseData save(@RequestBody DnaPanelInfo panelInfo) {
		if (panelInfo == null) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		try {
			DnaPanelInfoCop bean = new DnaPanelInfoCop();
			List<DnaPanelInfo> dnaPanelInfos = dnaPanelInfoService.panelInfoList(panelInfo);
			if (dnaPanelInfos.size() == 0){
				dnaPanelInfoService.save(panelInfo);
				bean.setId(panelInfo.getId());
			}else{
				bean.setId(dnaPanelInfos.get(0).getId());
			}
			bean.setPanelType(panelInfo.getPanelType());
			bean.setPanelCode(panelInfo.getPanelCode());
			bean.setPanelName(panelInfo.getPanelName());
			bean.setPanelFullName(panelInfo.getPanelFullName());
			bean.setPanelDescription(panelInfo.getPanelDescription());
			return new ResponseData(bean);

		}catch (Exception e){
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}

	}

	/**
	 * 试剂盒和基因座关系表保存
	 */
	@PostMapping("panelLocusSave")
	public ResponseData panelLocusSave(@RequestBody DnaPanelLocus dnaPanelLocus) {
		if (dnaPanelLocus == null) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		try {
			int selectId = dnaPanelLocusService.isSelectId(dnaPanelLocus);
			if (selectId == 0){
				dnaPanelLocusService.save(dnaPanelLocus);
			}
			return new ResponseData(dnaPanelLocus);
		}catch (Exception e){
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}

	}

	/**
	 * 基因座信息保存
	 */
	@PostMapping("locusInfoSave")
	public ResponseData locusInfoSave(@RequestBody DnaLocusInfo dnaLocusInfo) {
		if (dnaLocusInfo == null) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		try {
			String Sid = dnaLocusInfoService.selectById(dnaLocusInfo);
			if (null != Sid){
				dnaLocusInfo.setId(Integer.parseInt(Sid));
				return new ResponseData(dnaLocusInfo);
			}else{
				dnaLocusInfoService.save(dnaLocusInfo);
				return new ResponseData(dnaLocusInfo);
			}

		}catch (Exception e){
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}

	}

}
