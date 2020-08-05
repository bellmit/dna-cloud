package com.bazl.dna.database.core.controller.web.caseinfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bazl.dna.common.PageInfo;
import com.bazl.dna.common.PublicConstants;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.database.constants.Constants;
import com.bazl.dna.database.core.constants.ErrorCodes;
import com.bazl.dna.database.core.constants.ErrorInfo;
import com.bazl.dna.database.core.controller.BaseController;
import com.bazl.dna.database.core.response.datamodel.CaseInfoDetailDataModel;
import com.bazl.dna.database.service.model.bo.CaseInstoreModel;
import com.bazl.dna.database.service.model.po.CaseImage;
import com.bazl.dna.database.service.model.po.CaseInfo;
import com.bazl.dna.database.service.model.po.CasePersonInfo;
import com.bazl.dna.database.service.model.po.RegionInfo;
import com.bazl.dna.database.service.model.qo.CaseInfoQuery;
import com.bazl.dna.database.service.model.vo.CaseInfoVo;
import com.bazl.dna.database.service.model.vo.CasePersonInfoVo;
import com.bazl.dna.database.service.model.vo.ConsignmentInfoVo;
import com.bazl.dna.database.service.model.vo.DnaSampleInfoVo;
import com.bazl.dna.database.service.service.CaseImageService;
import com.bazl.dna.database.service.service.CaseInfoService;
import com.bazl.dna.database.service.service.CasePersonInfoService;
import com.bazl.dna.database.service.service.ConsignmentInfoService;
import com.bazl.dna.database.service.service.DictItemService;
import com.bazl.dna.database.service.service.DnaMixGeneInfoService;
import com.bazl.dna.database.service.service.DnaSampleInfoService;
import com.bazl.dna.database.service.service.DnaStrGeneInfoService;
import com.bazl.dna.database.service.service.DnaYstrGeneInfoService;
import com.bazl.dna.database.service.service.RegionInfoService;
import com.bazl.dna.database.utils.ListUtils;
import com.bazl.dna.util.GeneTransFormUtils;

/**
 * <p>
 * 案件信息表 前端控制器
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
@RestController
@RequestMapping("/caseinfo")
public class CaseInfoController extends BaseController {

	@Autowired
	CaseInfoService caseInfoService;
	@Autowired
	CaseImageService caseImageService;
	@Autowired
	ConsignmentInfoService consignmentInfoService;
	@Autowired
	CasePersonInfoService casePersonInfoService;
	@Autowired
	DnaSampleInfoService dnaSampleInfoService;
	@Autowired
    DnaMixGeneInfoService dnaMixGeneInfoService;
    @Autowired
    DnaStrGeneInfoService dnaStrGeneInfoService;
    @Autowired
    DnaYstrGeneInfoService dnaYstrGeneInfoService;
    @Autowired
    DictItemService dictItemService;
	@Autowired
	RegionInfoService  regionInfoService;

	/**
	 * 查询案件信息
	 * 
	 * @return
	 */
	@PostMapping(value = "/caseQuery")
	public ResponseData caseQuery(@RequestBody CaseInfoQuery query) {
		try {
			if (query!=null ) {
				query = trimCaseInfoQueryPrams(query); //查询去除空格项
			}else {
				query = new CaseInfoQuery();
			}
			List<CaseInfoVo> caseInfoVoList = caseInfoService.casePaginationQuery(query);
            if (ListUtils.isNotNullAndEmptyList(caseInfoVoList)){
                for (CaseInfoVo infoVo : caseInfoVoList){
                    //一级案件性质
                    String propertyName = dictItemService.selectNameByTypeCodeAndCode(Constants.DICT_TPYE_CASE_PROPERTY, infoVo.getCaseProperty());
                    infoVo.setCasePropertyName(propertyName);
                    //二级案件性质
                    String subPropertyName = dictItemService.selectNameByTypeCodeAndCode(Constants.DICT_TPYE_CASE_PROPERTY, infoVo.getCaseSubProperty());
                    infoVo.setCaseSubPropertyName(subPropertyName);
                }
            }
			int totalCount = caseInfoService.casePaginationCount(query);
			Map<String, Object> resultObj = new HashMap<>();
			resultObj.put(PublicConstants.PARAM_PAGE, new PageInfo(query.getPageIndex(), query.getRows(), totalCount));
			resultObj.put("caseInfoVoList", caseInfoVoList);
			return new ResponseData(resultObj);
		} catch (Exception ex) {
			logger.error("invoke CaseInfoController.caseQuery error.", ex);
			return new ResponseData("查询案件信息出现异常！" + ex.getMessage());
		}
	}

    /**
	 * 	查询去除空格条件--liuchang
	 * @param query
	 * @return
     */
	private CaseInfoQuery trimCaseInfoQueryPrams(CaseInfoQuery query) {

		if (StringUtils.isNotBlank(query.getAcceptorName())){
			query.setAcceptorName(query.getAcceptorName().replaceAll("\\s*", "")); //受理人
		}
		if (StringUtils.isNotBlank(query.getCaseAcceptNo())){
			query.setCaseAcceptNo(query.getCaseAcceptNo().replaceAll("\\s*", ""));//案件受理编号
		}
		if (StringUtils.isNotBlank(query.getSysXkNo())){
			query.setSysXkNo(query.getSysXkNo().replaceAll("\\s*", ""));//案件现勘编号
		}
		if (StringUtils.isNotBlank(query.getSysCaseAno())){
			query.setSysCaseAno(query.getSysCaseAno().replaceAll("\\s*", ""));//案件A号
		}
		if(StringUtils.isNotBlank(query.getNationSysNo())){
			query.setNationSysNo(query.getNationSysNo().replaceAll("\\s*", ""));//国家库案件号
		}
        if (StringUtils.isNotBlank(query.getScenePlace())){
			query.setScenePlace(query.getScenePlace().replaceAll("\\s*", ""));//案发地址
		}
		if (StringUtils.isNotBlank(query.getPersonName())){
			query.setPersonName(query.getPersonName().replaceAll("\\s*", ""));//人员姓名
		}
		if (StringUtils.isNotBlank(query.getPersonIdcardNo())){
			query.setPersonIdcardNo(query.getPersonIdcardNo().replaceAll("\\s*", ""));//人员身份证号码
		}
		if (StringUtils.isNotBlank(query.getCaseName())){
			query.setCaseName(query.getCaseName().replaceAll("\\s*", ""));//案件名称
		}

		if(query.getClientOrgList()!=null && !query.getClientOrgList().isEmpty()) {
            //最大单位
			if (query.getClientOrgList().size() == 1) {
				String orgCode = "ALLCODE";
				query.setConsignOrgId(orgCode.replaceAll("\\s*", "")); //委托单位编码
			} else {
				List<String> clientOrg = query.getClientOrgList();
				String orgCode = clientOrg.get(clientOrg.size() - 1);
				query.setConsignOrgId(orgCode.replaceAll("\\s*", "")); //委托单位编码
				RegionInfo  reg  = regionInfoService.selectByRegionCode(orgCode); //委托单位信息
                if(reg.getRegionLevel() ==3){
					query.setParentOrgFlag(Constants.FLAG_FALSE);
				}else {
					List<RegionInfo> allRegionList = regionInfoService.selectByParentCode(orgCode);//查询区县
					if (allRegionList != null && !allRegionList.isEmpty()) {
						query.setParentOrgFlag(Constants.FLAG_TRUE);
					}
				}
			}
		}
		if (StringUtils.isNotBlank(query.getConsignOrgName())){
			query.setConsignOrgName(query.getConsignOrgName().replaceAll("\\s*",""));//委托单位名称
		}
		return query;
	}

	/**
	 * 查看案件详情
	 * 
	 * @return
	 */
	@GetMapping(value = "/caseDetails")
	@SuppressWarnings("all")
		public ResponseData caseDetails(Integer caseId, Integer consignmentId) {
		if (caseId != null && consignmentId != null){
			CaseInfoDetailDataModel caseInfoDetailDataModel = new CaseInfoDetailDataModel();
			try {
				//案件信息
				CaseInfoVo caseInfo = caseInfoService.selectByIdDetail(caseId);
				//案件图片信息
				List<CaseImage> caseImageList = caseImageService.selectListByCaseId(caseId);
				//委托信息
				ConsignmentInfoVo consignmentInfoVo = consignmentInfoService.selectByIdVo(consignmentId);
				if (consignmentInfoVo != null){
					//受理时间
					consignmentInfoVo.setAcceptDate(consignmentInfoVo.getAcceptDatetime());
					//委托时间
					consignmentInfoVo.setConsignmentDatetime(consignmentInfoVo.getConsignmentRegDatetime());
				}
				List<String>  clientOrg = new ArrayList<>();//委托单位三级菜单展示
				if(consignmentInfoVo!=null && StringUtils.isNotBlank(consignmentInfoVo.getConsignOrgId())) {
					String parentOrg = consignmentInfoVo.getConsignOrgId();
					clientOrg.add(parentOrg);
					RegionInfo regionInfo = regionInfoService.selectByRegionCode(parentOrg);
					if (regionInfo != null && regionInfo.getRegionLevel() == 3) {
						RegionInfo parentRegionInfo = regionInfoService.selectByRegionCode(regionInfo.getParentRegionCode());
						if (parentRegionInfo != null && StringUtils.isNotBlank(parentRegionInfo.getParentRegionCode())) {
							clientOrg.add(parentRegionInfo.getRegionCode());
							RegionInfo grandRegionInfo = regionInfoService.selectByRegionCode(parentRegionInfo.getParentRegionCode());
							if (grandRegionInfo != null && StringUtils.isNotBlank(grandRegionInfo.getParentRegionCode())) {
								clientOrg.add(grandRegionInfo.getRegionCode());
							}
						}
					} else if (regionInfo != null && regionInfo.getRegionLevel() == 2) {
						RegionInfo regionInfo2 = regionInfoService.selectByRegionCode(regionInfo.getParentRegionCode());
						if (regionInfo2 != null && StringUtils.isNotBlank(regionInfo2.getParentRegionCode())) {
							clientOrg.add(regionInfo2.getRegionCode());
						}
					}
				}
				//倒序排序
				Collections.reverse(clientOrg);
				//人员信息
				List<CasePersonInfoVo> casePersonInfoVoList = casePersonInfoService.selectVoListByCaseId(caseId);
				//检材信息
				List<DnaSampleInfoVo> dnaSampleInfoList = dnaSampleInfoService.selectByCaseId(caseId);
				//人员样本
				List<DnaSampleInfoVo> personDnaSampleInfoList = new ArrayList<>();
				//物证样本
				List<DnaSampleInfoVo> evidenceDnaSampleInfoList = new ArrayList<>();
				//区分人员物证样本
				if (ListUtils.isNotNullAndEmptyList(dnaSampleInfoList)){
					for (DnaSampleInfoVo infoVo : dnaSampleInfoList){
						if (infoVo.getEvidenceFlag() ==1 ){
                            //物证
							evidenceDnaSampleInfoList.add(infoVo);
						}else {
                            //人员
							personDnaSampleInfoList.add(infoVo);
						}
					}
				}
				caseInfoDetailDataModel.setCaseInfo(caseInfo);
				caseInfoDetailDataModel.setCaseImageList(caseImageList);
				caseInfoDetailDataModel.setConsignmentInfoVo(consignmentInfoVo);
				caseInfoDetailDataModel.setCasePersonInfoList(casePersonInfoVoList);
				caseInfoDetailDataModel.setPersonDnaSampleInfoList(personDnaSampleInfoList);
				caseInfoDetailDataModel.setEvidenceDnaSampleInfoList(evidenceDnaSampleInfoList);
				caseInfoDetailDataModel.setClientOrgList(clientOrg);
				return new ResponseData(caseInfoDetailDataModel);
			} catch (Exception ex) {
				logger.error("invoke CurrencyQueryController.queryCaseById error.", ex);
				return new ResponseData("查询案件详情信息出现异常！" + ex.getMessage());
			}
		}else {
			logger.error("传入参数为空！");
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
	}

	/*
	* 	查看人员详情
	* */@GetMapping(value = "/findPersonInfo")
	public ResponseData findPersonInfo(Integer personId) {
        Map<String, Object> result = new HashMap<>();
		if (personId != null){
            try {
                CasePersonInfo casePersonInfo = casePersonInfoService.selectVoListById(personId);
                DnaSampleInfoVo dnaSampleInfoVo = null;
                if (casePersonInfo != null){
                    List<DnaSampleInfoVo> dnaSampleInfoVos = dnaSampleInfoService.selectByPersonId(casePersonInfo.getId());
                    if (ListUtils.isNotNullAndEmptyList(dnaSampleInfoVos)){
                        dnaSampleInfoVo = dnaSampleInfoVos.get(0);
                        //没有从STR基因表查询
						DnaSampleInfoVo dnaSampleInfoSTR = dnaStrGeneInfoService.selectSampleByDataSource(dnaSampleInfoVo.getId(),Constants.GENE_DATA_SOURCE_CASE); //根据分型类型和编号查询分型信息
                            //基因信息格式转换
                            if (dnaSampleInfoSTR != null){
                                String gene = GeneTransFormUtils.geneFormatString(dnaSampleInfoSTR.getGeneInfo());
                                dnaSampleInfoSTR.setGeneInfo(gene);
                            }
                        result.put("STR",dnaSampleInfoSTR);
                        //查询 Y-STR 基因信息
						DnaSampleInfoVo dnaSampleInfoYSTR = dnaYstrGeneInfoService.selectSampleByDataSource(dnaSampleInfoVo.getId(),Constants.GENE_DATA_SOURCE_CASE);
                        //基因信息格式转换
                        if (dnaSampleInfoYSTR != null){
                            String gene = GeneTransFormUtils.geneFormatString(dnaSampleInfoYSTR.getGeneInfo());
                            dnaSampleInfoYSTR.setGeneInfo(gene);
                        }
                        result.put("YSTR",dnaSampleInfoYSTR);
                    }
                }
                result.put("casePersonInfo",casePersonInfo);
                result.put("sampleInfo",dnaSampleInfoVo);
            } catch (Exception ex) {
                logger.error("invoke CaseInfoController.findPersonInfo error.", ex);
                return new ResponseData("查询人员详情信息出现异常！" + ex.getMessage());
            }
            return new ResponseData(result);
		}else {
			logger.error("传入参数为空！");
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
	}

	/**
	 * 查看样本详细基因详情
	 * 
	 * @return
	 */
	@GetMapping(value = "/findSampleInfo")
	public ResponseData findSampleInfo(Integer sampleId) {
        Map<String, Object> result = new HashMap<>();
        if (sampleId != null){
            try {
                DnaSampleInfoVo sampleInfoVo = dnaSampleInfoService.selectById(sampleId);                //查询检材信息
				DnaSampleInfoVo dnaSampleInfoSTR = dnaStrGeneInfoService.selectSampleByDataSource(sampleId,Constants.GENE_DATA_SOURCE_CASE); //根据分型类型和编号查询分型信息
                    //基因信息格式转换
                    if (dnaSampleInfoSTR != null){
                        String gene = GeneTransFormUtils.geneFormatString(dnaSampleInfoSTR.getGeneInfo());
                        dnaSampleInfoSTR.setGeneInfo(gene);
                }
	                result.put("STR",dnaSampleInfoSTR);
				//查询 Y-STR 基因信息
				DnaSampleInfoVo dnaSampleInfoYSTR = dnaYstrGeneInfoService.selectSampleByDataSource(sampleId,Constants.GENE_DATA_SOURCE_CASE);
				//基因信息格式转换
                if (dnaSampleInfoYSTR != null){
                    String gene = GeneTransFormUtils.geneFormatString(dnaSampleInfoYSTR.getGeneInfo());
                    dnaSampleInfoYSTR.setGeneInfo(gene);
                }
                result.put("YSTR",dnaSampleInfoYSTR);
                result.put("sampleInfo",sampleInfoVo);
            } catch (Exception ex) {
                logger.error("invoke CurrencyQueryController.geneInfo error.", ex);
                return new ResponseData("查询基因详情信息出现异常！" + ex.getMessage());
            }
            return new ResponseData(result);
        }else {
            logger.error("传入参数为空！");
            return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
        }
	}

	/**
	 * 根据案件id查询案件信息·
	 * 
	 * @param caseId
	 * @return
	 */
	@GetMapping(value = "/{caseId}")
	public ResponseData queryCaseById(@PathVariable Integer caseId) {
		try {
			CaseInfo caseInfo = caseInfoService.getById(caseId);
			return new ResponseData(caseInfo);
		} catch (Exception ex) {
			logger.error("invoke CaseInfoController.queryCaseById error.", ex);
			return new ResponseData("查询案件信息出现异常！" + ex.getMessage());
		}
	}

	/**
	 *
	 * 处理lims案件入库请求
	 *
	 * @return
	 */

	@PostMapping(value = "/instore")
	public ResponseData instoreLimsCaseSamples(@RequestBody CaseInstoreModel caseInstoreModel) {
		try {

			// 判断案件、委托、人员、物证、基因信息是否为null
			if (caseInstoreModel.getCaseInfo() != null && caseInstoreModel.getCasePersonInfoList() != null
					&& caseInstoreModel.getConsignmentInfoList() != null
					&& caseInstoreModel.getDnaGeneInfoList() != null && caseInstoreModel.getDnaSampleInfoList() != null
					&& caseInstoreModel.getPersonRelativeInfoList() != null) {
				this.caseInfoService.saveInstoreCaseAndSamples(caseInstoreModel);
			}
			return new ResponseData();
		} catch (Exception ex) {
			logger.error("invoke CaseInfoControllerinstoreLimsCaseSamples.instoreLimsCaseSamples error.", ex);
			return new ResponseData("处理案件信息入库出现异常！" + ex.getMessage());
		}
	}

	/**
	 *
	 * 处理lims案件入库号请求
	 *
	 * @return
	 */
	@PostMapping(value = "/insertWarehousingNum")
	public ResponseData insertWarehousingNum(@RequestBody CaseInfo caseInfo) {
		try {
			if (caseInfo != null) {
				this.caseInfoService.insertWarehousingNum(caseInfo);
			}
			return new ResponseData();
		} catch (Exception ex) {
			logger.error("invoke CaseInfoController.insertWarehousingNum error.", ex);
			return new ResponseData("新增入库号出现异常！" + ex.getMessage());
		}
	}

}
