package com.bazl.dna.database.core.controller.converter.caseinfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.database.core.constants.ErrorCodes;
import com.bazl.dna.database.core.constants.ErrorInfo;
import com.bazl.dna.database.core.controller.BaseController;
import com.bazl.dna.database.service.model.po.ConsignmentInfo;
import com.bazl.dna.database.service.service.ConsignmentInfoService;

/**
 * 案件受理转换
 * @author zhaoyong
 *
 */
@RestController
@RequestMapping("/converter/caseinfo/consignment")
public class ConsignmentInfoConverter extends BaseController {
	
	@Autowired
	private ConsignmentInfoService service;
	
	/**
	 * 委托案件信息保存
	 * @param consignmentInfo
	 * @return
	 */
	@PostMapping("save")
	public ResponseData save(@RequestBody ConsignmentInfo consignmentInfo) {
		logger.info("invoking /converter/caseinfo/consignment/save....");

		if (consignmentInfo == null) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}

		try {
			logger.info("invoking /converter/caseinfo/consignment/save...., consignmentAcceptNo = " + consignmentInfo.getAcceptNo());

			int count = service.selectByPrimaryKeyCount(consignmentInfo.getAcceptNo());
			logger.info("invoking /converter/caseinfo/consignment/save...., selectAcceptNoCount = " + count);

			if (count == 0){
				service.save(consignmentInfo);
				return new ResponseData(consignmentInfo);
			}
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}catch (Exception e){
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
	}
	
}
