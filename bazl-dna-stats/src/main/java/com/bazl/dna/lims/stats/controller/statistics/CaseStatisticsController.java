package com.bazl.dna.lims.stats.controller.statistics;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.lims.stats.LimsStatsConfigure;
import com.bazl.dna.lims.common.Constants;
import com.bazl.dna.lims.stats.controller.BaseController;
import com.bazl.dna.lims.model.ParameterQuery;
import com.bazl.dna.lims.model.po.LaboratoryInfo;
import com.bazl.dna.lims.model.vo.CaseEffectiveRatioVo;
import com.bazl.dna.lims.model.vo.CaseEvidenceDetectionRateVo;
import com.bazl.dna.lims.model.vo.IdentifyBookVo;
import com.bazl.dna.lims.service.LaboratoryInfoService;
import com.bazl.dna.lims.service.LimsCaseInfoService;
import com.bazl.dna.lims.utils.MathUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 案件统计相关信息
 * </p>
 *
 * @author wanghaiyang
 * @since 2020-02-11
 */
@RestController
@RequestMapping("/stats/statistics")
public class CaseStatisticsController extends BaseController {

	@Autowired
	LimsCaseInfoService limsCaseInfoService;
	@Autowired
	LaboratoryInfoService laboratoryInfoService;
	@Autowired
	LimsStatsConfigure limsStatsConfigure;

	/**
	 * 物证检出率统计
	 * 
	 * @return
	 */
	@PostMapping(value = "/evidenceDetectionRateStatistics")
	public ResponseData evidenceDetectionRateStatistics(@RequestBody ParameterQuery parameterQuery) {
		try {
			//设置保留位数
			DecimalFormat df = new DecimalFormat("0.00");
			NumberFormat nf = NumberFormat.getPercentInstance();
			nf.setMaximumFractionDigits(2);

			Map<String, Object> queryParams = new HashMap<>();
			if(StringUtils.isNotBlank(parameterQuery.getAcceptOrgId())) {
				queryParams.put("acceptOrgId", parameterQuery.getAcceptOrgId());
			}
			if(StringUtils.isNotBlank(parameterQuery.getStartDate())) {
				queryParams.put("startDate", parameterQuery.getStartDate());
			}
			if(StringUtils.isNotBlank(parameterQuery.getEndDate())) {
				queryParams.put("endDate", parameterQuery.getEndDate());
			}
			queryParams.put("fenjuOrgList", limsStatsConfigure.getFenjuOrgList());
			List<CaseEvidenceDetectionRateVo> caseEvidenceDetectionRateVoList = limsCaseInfoService.selectEvidenceDetectionRate(queryParams);
			for(CaseEvidenceDetectionRateVo cedr : caseEvidenceDetectionRateVoList){
				if(cedr.getEvidenceCount() > 0) {
					try {
						int detectedCnt = cedr.getMatchedEvidenceCount() + cedr.getInstoreEvidenceCount();
						double rateVal2 = new BigDecimal((float) detectedCnt / cedr.getEvidenceCount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
						double rateVal = new BigDecimal(rateVal2 * 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
						cedr.setDetectionRate(rateVal);
					} catch (Exception exx) {
						logger.error("计算物证检出率错误。", exx);
						cedr.setDetectionRate(0.00);
					}
				}else{
					cedr.setDetectionRate(0.00);
				}
			}

			//实验室信息表
			List<LaboratoryInfo> laboratoryInfoList = laboratoryInfoService.selectAll();
			Map<String, Object> resultObj = new HashMap<>();
			resultObj.put("caseEvidenceDetectionRateVoList",caseEvidenceDetectionRateVoList);
			resultObj.put("parameterQuery", parameterQuery);
			resultObj.put("laboratoryInfoList", laboratoryInfoList);
			return new ResponseData(resultObj);
		} catch (Exception ex) {
			logger.error("invoke CaseInfoController.caseQuery error.", ex);
			return new ResponseData("物证检出率统计出现异常！" + ex.getMessage());
		}
	}

	/**
	 * 案件有效比中率统计
	 *
	 * @return
	 */
	@PostMapping(value = "/caseMedianRatioStatistics")
	public ResponseData caseMedianRatioStatistics(@RequestBody ParameterQuery parameterQuery) {
		try {
			//设置保留位数
			DecimalFormat df = new DecimalFormat("0.00");
			NumberFormat nf = NumberFormat.getPercentInstance();
			nf.setMaximumFractionDigits(2);

			Map<String, Object> queryParams = new HashMap<>();
			if(StringUtils.isNotBlank(parameterQuery.getStartDate())) {
				queryParams.put("startDate", parameterQuery.getStartDate());
			}
			if(StringUtils.isNotBlank(parameterQuery.getEndDate())) {
				queryParams.put("endDate", parameterQuery.getEndDate());
			}
			queryParams.put("fenjuOrgList", limsStatsConfigure.getFenjuOrgList());
			List<CaseEffectiveRatioVo> caseEffectiveRatioVoList = limsCaseInfoService.selectCaseEffectiveRatio(queryParams);
			for(CaseEffectiveRatioVo cerv : caseEffectiveRatioVoList){
				if(cerv.getCaseTotalCount() > 0) {
					try {
						int caseTotalCount = cerv.getCaseTotalCount();
						int matchSampleCaseCount = cerv.getMatchSampleCaseCount();
						int matchSuspectedCaseCount = cerv.getMatchSuspectedCaseCount();
						int inStorgeCaseCount = cerv.getOtherInStorgeCaseCount();
						double matchSuspectedRateVal = new BigDecimal((float) matchSuspectedCaseCount / caseTotalCount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
						double matchSampleRateVal = new BigDecimal((float) matchSampleCaseCount / caseTotalCount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
						double inStorgeRateVal = new BigDecimal((float) inStorgeCaseCount / caseTotalCount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

						double rateVal1 = MathUtils.add(matchSuspectedRateVal * 70, matchSampleRateVal * 70);
						double rateVal2 = MathUtils.add(rateVal1, inStorgeRateVal * 30);

						double rateVal = new BigDecimal(rateVal2 * 100).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
						cerv.setMedianRate(rateVal);
						System.out.println("========" + cerv.getMedianRate() + "========");
					} catch (Exception exx) {
						logger.error("计算案件有效比中率统计错误。", exx);
						cerv.setMedianRate(0.00);
					}
				}else{
					cerv.setMedianRate(0.00);
				}
			}

			//实验室信息表
			List<LaboratoryInfo> laboratoryInfoList = laboratoryInfoService.selectAll();
			Map<String, Object> resultObj = new HashMap<>();
			resultObj.put("caseEffectiveRatioVoList",caseEffectiveRatioVoList);
			resultObj.put("parameterQuery", parameterQuery);
			resultObj.put("laboratoryInfoList", laboratoryInfoList);
			return new ResponseData(resultObj);
		} catch (Exception ex) {
			logger.error("invoke CaseInfoController.caseQuery error.", ex);
			return new ResponseData("案件有效比中率统计出现异常！" + ex.getMessage());
		}
	}



	/**
	 * 鉴定书统计
	 *
	 * @return
	 */
	@PostMapping(value = "/identifyBookStatistics")
	public ResponseData identifyBookStatistics(@RequestBody ParameterQuery parameterQuery) {
		try {
			Map<String, Object> queryParams = new HashMap<>();
			if(StringUtils.isNotBlank(parameterQuery.getAcceptOrgId())) {
				queryParams.put("acceptOrgId", parameterQuery.getAcceptOrgId());
			}
			if(StringUtils.isNotBlank(parameterQuery.getStartDate())) {
				queryParams.put("startDate", parameterQuery.getStartDate());
			}
			if(StringUtils.isNotBlank(parameterQuery.getEndDate())) {
				queryParams.put("endDate", parameterQuery.getEndDate());
			}
			queryParams.put("fenjuOrgList", limsStatsConfigure.getFenjuOrgList());
			List<IdentifyBookVo> identifyBookVoList = limsCaseInfoService.selectIdentifyBookStatistics(queryParams);
			int outTotalCount = 0;
			for (IdentifyBookVo ibvo : identifyBookVoList) {
				//判断受理中心是否是法医
				if (Constants.FORENSIC_CENTER_ORG_ID.equals(ibvo.getOrgCode())) {
					//查询已签发鉴定书数量
					queryParams.put("acceptOrgId", ibvo.getOrgCode());
					int issuedCount = limsCaseInfoService.selectIssuedCount(queryParams);
					ibvo.setIssuedCount(issuedCount);
				}else {
					if (ibvo.getOutCount() == null) {
						ibvo.setOutCount(0);
					}
					ibvo.setIssuedCount(ibvo.getOutCount());
				}

				outTotalCount += ibvo.getOutCount();
			}

			//实验室信息表
			List<LaboratoryInfo> laboratoryInfoList = laboratoryInfoService.selectAll();
			Map<String, Object> resultObj = new HashMap<>();
			resultObj.put("identifyBookVoList",identifyBookVoList);
			resultObj.put("parameterQuery", parameterQuery);
			resultObj.put("laboratoryInfoList", laboratoryInfoList);
			//已出鉴定书总数
			resultObj.put("outTotalCount", outTotalCount);
			//已签发鉴定书总数
			resultObj.put("issuedTotalCount", limsCaseInfoService.selectTotalIssuedCount(queryParams));
			return new ResponseData(resultObj);
		} catch (Exception ex) {
			logger.error("invoke CaseInfoController.caseQuery error.", ex);
			return new ResponseData("鉴定书统计出现异常！" + ex.getMessage());
		}
	}

}
