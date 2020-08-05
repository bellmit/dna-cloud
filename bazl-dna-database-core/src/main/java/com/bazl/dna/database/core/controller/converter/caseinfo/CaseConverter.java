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
import com.bazl.dna.database.service.model.po.CaseImage;
import com.bazl.dna.database.service.model.po.CaseInfo;
import com.bazl.dna.database.service.service.CaseImageService;
import com.bazl.dna.database.service.service.CaseInfoService;

/**
 * 案件信息转换
 */
@RestController
@RequestMapping("/converter/caseinfo/case")
public class CaseConverter extends BaseController {
	
	@Autowired
	private CaseInfoService caseInfoService;
	
	@Autowired
	private CaseImageService caseImageService;
	
	/**
	 * 案件信息保存
	 * @param caseInfo
	 * @return
	 */
	@PostMapping("save")
	public ResponseData save(@RequestBody CaseInfo caseInfo) {
		logger.info("invoking /converter/caseinfo/case/save....");
		if (caseInfo == null) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		try {
			logger.info("invoking /converter/caseinfo/case/save...., caseAcceptNo = " + caseInfo.getCaseAcceptNo());

		    if (null != caseInfo.getCaseAcceptNo()){
                int i = caseInfoService.selectAcceptNoCount(caseInfo.getCaseAcceptNo());

				logger.info("invoking /converter/caseinfo/case/save...., selectAcceptNoCount = " + i);
                if (i == 0){
                    caseInfoService.save(caseInfo);
                    return new ResponseData(caseInfo);
                }else{
                    return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_IS_EXISTS);
                }
            }else{
                caseInfoService.save(caseInfo);
                return new ResponseData(caseInfo);
            }
        }catch (Exception e){
            return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
        }

	}
	
	/**
	 * 案件图片保存
	 * @param caseImage
	 * @return
	 */
	@PostMapping("saveImage")
	public ResponseData saveImage(@RequestBody CaseImage caseImage) {
		if (caseImage == null) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		caseImageService.save(caseImage);
		return new ResponseData(caseImage);
	}
}
