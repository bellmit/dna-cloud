package com.bazl.dna.database.core.controller.comm.dict;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bazl.dna.common.PublicConstants;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.database.constants.Constants;
import com.bazl.dna.database.core.client.OrgInfoClient;
import com.bazl.dna.database.core.constants.ErrorCodes;
import com.bazl.dna.database.core.constants.ErrorInfo;
import com.bazl.dna.database.core.controller.BaseController;
import com.bazl.dna.database.service.model.po.DictItem;
import com.bazl.dna.database.service.model.po.LabServerInfo;
import com.bazl.dna.database.service.model.po.PopulationFrequencyInfo;
import com.bazl.dna.database.service.model.po.RegionInfo;
import com.bazl.dna.database.service.model.vo.RegionInfoVo;
import com.bazl.dna.database.service.service.DictItemService;
import com.bazl.dna.database.service.service.LabServerInfoService;
import com.bazl.dna.database.service.service.PopulationFrequencyInfoService;
import com.bazl.dna.database.service.service.RegionInfoService;
import com.bazl.dna.database.utils.ListUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * <p>
 * 字典条目信息表 前端控制器
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
@RestController
@RequestMapping("/comm/dict")
public class DictItemController extends BaseController {

	@Autowired
	private DictItemService dictItemService;
	@Autowired
	private LabServerInfoService labServerInfoService;
	@Autowired
	private PopulationFrequencyInfoService populationFrequencyInfoService;
	@Autowired
	private RegionInfoService regionInfoService;
	@Autowired
	private OrgInfoClient orgInfoClient;

	/**
	 * 根据字典类型代码查询数据库字典列表
	 * 
	 * @return
	 */
	@PostMapping(value = "/listByTypeCode")
	public ResponseData dictListByTypeCode(@RequestParam String dictTypeCode) {
		try {
			List<DictItem> dictItems = dictItemService
					.list(new QueryWrapper<DictItem>().eq("DICT_TYPE_CODE", dictTypeCode.toUpperCase()));

			if (ListUtils.isNotNullAndEmptyList(dictItems)) {
				dictItems.sort(new Comparator<DictItem>() {
					@Override
					public int compare(DictItem o1, DictItem o2) {
						return o1.getDictCode().compareTo(o2.getDictCode());
					}
				});
			}

			return new ResponseData(dictItems);
		} catch (Exception ex) {
			logger.error("invoke DictItemController.dictListByTypeCode error.", ex);
			return new ResponseData("根据字典类型代码获取字典列表异常！" + ex.getMessage());
		}
	}

	/**
	 * 查询名族字典类型代码数据库字典列表-liuchang
	 */
	@GetMapping(value = "/listByRaceTypeCode")
	public ResponseData dictListByRaceTypeCode() {
		try {
			// 查询字典项名族
			List<DictItem> dictItems = dictItemService.selectDictItemListByType(Constants.DICT_TPYE_RACE);
			return new ResponseData(dictItems);
		} catch (Exception ex) {
			logger.error("invoke dictItemService.listByRaceTypeCode error!", ex);
			return new ResponseData("根据字典类型代码获取名族字典列表异常!" + ex.getMessage());
		}
	}

	/**
	 * 查询国籍字典类型代码数据库字典列表-liuchang
	 */
	@GetMapping(value = "/listByNationalityTypeCode")
	public ResponseData listByNationalityTypeCode() {
		try {
			// 查询字典项国籍
			List<DictItem> dictItems = dictItemService.selectDictItemListByType(Constants.DICT_TYPE_NATIONALITY);
			return new ResponseData(dictItems);
		} catch (Exception ex) {
			logger.error("invoke dictItemService.listByNationalityTypeCode error!", ex);
			return new ResponseData("根据字典类型代码获取国籍字典列表异常!" + ex.getMessage());
		}
	}

	/*
	 * 质控样本类型
	 */
	@RequestMapping(value = "/selectByQcSampleType")
	public ResponseData selectByQcSampleType() {
		List<DictItem> dictItems = null;
		try {
			dictItems = dictItemService.selectDictItemListByType(Constants.DICT_TYPE_QC_SAMPLE_TYPE);
		} catch (Exception e) {
			logger.error("查询质控样本类型错误！" + e.getMessage());
			return new ResponseData(0, "查询质控样本类型错误!");
		}
		return new ResponseData(dictItems);
	}

	/*
	 * 质控人员类型
	 */
	@RequestMapping(value = "/selectByQcPersonType")
	public ResponseData selectByQcPersonType() {
		List<DictItem> dictItems = null;
		try {
			dictItems = dictItemService.selectDictItemListByType(Constants.DICT_TYPE_QC_PERSON_TYPE);
		} catch (Exception e) {
			logger.error("查询质控人员类型错误！" + e.getMessage());
			return new ResponseData(0, "查询质控人员类型错误!");
		}
		return new ResponseData(dictItems);
	}

	/*
	 * 性别查询
	 */
	@RequestMapping(value = "/selectBySex")
	public ResponseData selectBySex() {
		List<DictItem> dictItems = null;
		try {
			dictItems = dictItemService.selectDictItemListByType(Constants.DICT_TYPE_PERSON_GENDER);
		} catch (Exception e) {
			logger.error("查询性别错误！" + e.getMessage());
			return new ResponseData(0, "查询性别错误!");
		}
		return new ResponseData(dictItems);
	}

	/*
	 * 案件类型
	 */
	@RequestMapping(value = "/selectByCaseType")
	public ResponseData selectByCaseType() {
		List<DictItem> dictItems = null;
		try {
			dictItems = dictItemService.selectDictItemListByType(Constants.DICT_TPYE_CASE_PROPERTY);
		} catch (Exception e) {
			logger.error("查询案件类型错误！" + e.getMessage());
			return new ResponseData(0, "查询案件类型错误!");
		}
		return new ResponseData(dictItems);
	}

	/*
	 * 检材类型
	 */
	@RequestMapping(value = "/selectBySampleType")
	public ResponseData selectBySampleType() {
		List<DictItem> dictItems = null;
		try {
			dictItems = dictItemService.selectDictItemListByType(Constants.DICT_TPYE_SAMPLE_TYPE);
		} catch (Exception e) {
			logger.error("查询检材类型错误！" + e.getMessage());
			return new ResponseData(0, "查询检材类型错误!");
		}
		return new ResponseData(dictItems);
	}

	/*
	 * 案件人员类型
	 */
	@RequestMapping(value = "/selectByCasePersonType")
	public ResponseData selectByCasePersonType() {
		List<DictItem> dictItems = null;
		try {
			dictItems = dictItemService.selectDictItemListByType(Constants.DICT_TPYE_CASE_PERSON_TYPE);
		} catch (Exception e) {
			logger.error("查询案件人员类型错误！" + e.getMessage());
			return new ResponseData(0, "查询案件人员类型错误!");
		}
		return new ResponseData(dictItems);
	}
	
	/*
	 * 证件类型
	 */
	@RequestMapping(value = "/selectByCertificateType")
	public ResponseData selectByCertificateType() {
		List<DictItem> dictItems = null;
		try {
			dictItems = dictItemService.selectDictItemListByType(Constants.DICT_TYPE_CERTIFICATE_TYPE);
		} catch (Exception e) {
			logger.error("查询证件类型错误！" + e.getMessage());
			return new ResponseData(0, "查询证件类型错误!");
		}
		return new ResponseData(dictItems);
	}

	/*
	 * DNA实验室
	 */
	@RequestMapping(value = "/selectLabInfoList")
	public ResponseData selectLabInfoList() {
		List<LabServerInfo> labServerInfos = null;
		try {
			labServerInfos = labServerInfoService.selectAll();
		} catch (Exception e) {
			logger.error("查询DNA实验室错误！" + e.getMessage());
			return new ResponseData(0, "查询DNA实验室错误!");
		}
		return new ResponseData(labServerInfos);
	}

	/*
	 * 比对实验室范围
	 */
	@RequestMapping(value = "/selectLabName")
	public ResponseData selectLabNameList() {
		List<LabServerInfo> labServerInfos = null;
		try {
			labServerInfos = labServerInfoService.selectAll();
			if (ListUtils.isNotNullAndEmptyList(labServerInfos)) {
				for (LabServerInfo info : labServerInfos) {
					String labServerNo = info.getLabServerNo();
					String name = "";
					if (StringUtils.isNotBlank(labServerNo) && ("110101").equals(labServerNo)) {
						name = "东城分局";
					} else if (StringUtils.isNotBlank(labServerNo) && ("110102").equals(labServerNo)) {
						name = "西城分局";
					} else if (StringUtils.isNotBlank(labServerNo) && ("110105").equals(labServerNo)) {
						name = "朝阳分局";
					} else if (StringUtils.isNotBlank(labServerNo) && ("110106").equals(labServerNo)) {
						name = "丰台分局";
					} else if (StringUtils.isNotBlank(labServerNo) && ("110107").equals(labServerNo)) {
						name = "石景山分局";
					} else if (StringUtils.isNotBlank(labServerNo) && ("110108").equals(labServerNo)) {
						name = "海淀分局";
					} else if (StringUtils.isNotBlank(labServerNo) && ("110109").equals(labServerNo)) {
						name = "门头沟分局";
					} else if (StringUtils.isNotBlank(labServerNo) && ("110111").equals(labServerNo)) {
						name = "房山分局";
					} else if (StringUtils.isNotBlank(labServerNo) && ("110112").equals(labServerNo)) {
						name = "通州分局";
					} else if (StringUtils.isNotBlank(labServerNo) && ("110113").equals(labServerNo)) {
						name = "顺义分局";
					} else if (StringUtils.isNotBlank(labServerNo) && ("110114").equals(labServerNo)) {
						name = "昌平分局";
					} else if (StringUtils.isNotBlank(labServerNo) && ("110115").equals(labServerNo)) {
						name = "大兴分局";
					} else if (StringUtils.isNotBlank(labServerNo) && ("110116").equals(labServerNo)) {
						name = "怀柔分局";
					} else if (StringUtils.isNotBlank(labServerNo) && ("110117").equals(labServerNo)) {
						name = "平谷分局";
					} else if (StringUtils.isNotBlank(labServerNo) && ("110228").equals(labServerNo)) {
						name = "密云分局";
					} else if (StringUtils.isNotBlank(labServerNo) && ("110229").equals(labServerNo)) {
						name = "延庆分局";
					} else if (StringUtils.isNotBlank(labServerNo) && ("110230").equals(labServerNo)) {
						name = "法医中心";
					} else {
						name = "法医中心";
					}
					info.setLabServerName(name);
				}
			}
		} catch (Exception e) {
			logger.error("查询比对实验室范围错误！" + e.getMessage());
			return new ResponseData(0, "查询比对实验室范围错误!");
		}
		return new ResponseData(labServerInfos);
	}

	/*
	 * 建库人员类型
	 */
	@RequestMapping(value = "/selectByCriminalPersonType")
	public ResponseData selectByCriminalPersonType() {
		List<DictItem> dictItems = null;
		try {
			dictItems = dictItemService.selectDictItemListByType(Constants.DICT_TPYE_CRIMINAL_PERSON_TYPE);
		} catch (Exception e) {
			logger.error("查询建库人员类型错误！" + e.getMessage());
			return new ResponseData(0, "查询建库人员类型错误!");
		}
		return new ResponseData(dictItems);
	}

	/*
	 * 人员亲缘身份
	 */
	@RequestMapping(value = "/selectByPersonRelationType")
	public ResponseData selectByPersonRelationList() {
		List<DictItem> dictItems = null;
		try {
			dictItems = dictItemService.selectDictItemListByType(Constants.DICT_TYPE_PERSON_RELATION_TYPE);
		} catch (Exception e) {
			logger.error("查询建库人员类型错误！" + e.getMessage());
			return new ResponseData(0, "查询建库人员类型错误!");
		}
		return new ResponseData(dictItems);
	}

	/*
	 * 所属分局--单位名称
	 */
	@RequestMapping(value = "/selectByOrgInfo")
	public ResponseData selectOrgInfoList(String parentId) {
		if (!StringUtils.isNotBlank(parentId)) {
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
		return orgInfoClient.findListByParentId(parentId);
	}

	/*
	 * 种群类型
	 */
	@RequestMapping(value = "/selectByPopulationInfo")
	public ResponseData selectByPopulationInfoList(String geneType) {
		if (StringUtils.isNotBlank(geneType)) {
			List<PopulationFrequencyInfo> frequencyInfos = null;
			try {
				frequencyInfos = populationFrequencyInfoService.selectGeneTypeList(geneType);
			} catch (Exception e) {
				logger.error("查询单位名称错误！" + e.getMessage());
				return new ResponseData(0, "查询单位名称错误!");
			}
			return new ResponseData(frequencyInfos);
		} else {
			logger.error("传入参数为空！");
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
	}

	/*
	 * 等级区划
	 */
	@RequestMapping(value = "/selectByRegionInfo")
	public ResponseData selectByRegionInfoList(Integer regionLevel) {
		if (regionLevel != null) {
			List<RegionInfo> regionInfos = null;
			try {
				regionInfos = regionInfoService.selectByLevelList(regionLevel);
			} catch (Exception e) {
				logger.error("查询等级区划错误！" + e.getMessage());
				return new ResponseData(0, "查询等级区划错误!");
			}
			return new ResponseData(regionInfos);
		} else {
			logger.error("传入参数为空！");
			return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
		}
	}

	/*
	 * 目标亲缘关系 TARGET_RELATION
	 */
	@RequestMapping(value = "/selectByTargetReationType")
	public ResponseData selectByTargetReationType() {
		List<DictItem> dictItems = null;
		try {
			dictItems = dictItemService.selectDictItemListByType(Constants.DICT_TYPE_PTARGET_RELATION);
		} catch (Exception e) {
			logger.error("查询质控样本类型错误！" + e.getMessage());
			return new ResponseData(0, "查询质控样本类型错误!");
		}
		return new ResponseData(dictItems);
	}
	/**
	 * 查看全部机构信息 /委托单位信息/受理单位信息--liuchang
	 * @return
	 */
	@GetMapping(value = "/selectRegion")
	public ResponseData selectRegion() {
		int cityLevel = 1;// 城市机构编码
		List<RegionInfoVo> regionInfoList = regionInfoService.selectByLevel(cityLevel);
		if (null != regionInfoList && !regionInfoList.isEmpty()) {
			for (RegionInfoVo regionInfo : regionInfoList) {
				if (null != regionInfo.getParentRegionCode() && !regionInfo.getParentRegionCode().equals("0")) {
					// 查询区域机构信息根据机构单位登记
					List<RegionInfoVo> chirdRegion = regionInfoService.selectByParentRegionCode(regionInfo.getValue());
					if (null != chirdRegion && !chirdRegion.isEmpty()) {
						for (RegionInfoVo sunRegion : chirdRegion) {
							List<RegionInfoVo> sunRegionList = regionInfoService
									.selectByParentRegionCode(sunRegion.getValue());
							for (RegionInfoVo regs : sunRegionList) {
								if (regs.getChildren() == null) {
									regs.setChildren(new ArrayList<RegionInfoVo>());
								}
							}
							sunRegion.setChildren(sunRegionList);
						}
					}
					regionInfo.setChildren(chirdRegion);
				}
			}
		}
		return new ResponseData(regionInfoList);
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = "/selectOrgName")
	public ResponseData selectOrgName(String parentId) {
		if (StringUtils.trimToNull(parentId) == null) {
			parentId = "-1";//根节点
		}
		ResponseData responseData = orgInfoClient.getById(parentId);
		
		List<Map<String, Object>> result = Lists.newArrayList();
		Map<String, Object> resultMap = Maps.newHashMap();
		if (responseData.getCode() == PublicConstants.SUCCESS_CODE) {
			Map<String, Object> map = (Map<String, Object>)responseData.getResult();
			resultMap.put("id", map.get("id").toString());
			resultMap.put("value", map.get("orgCode").toString());
			resultMap.put("label", map.get("orgName").toString());
			// 子节点
			ResponseData orgList = orgInfoClient.findListByParentId(parentId);
			List<Map<String, Object>> array = (List<Map<String, Object>>)orgList.getResult();
			directList(array);
			resultMap.put("children", array);
			result.add(resultMap);
		}
		return new ResponseData(result);
	}
	
	@SuppressWarnings("unchecked")
	private void directList(List<Map<String, Object>> array) {
		array.stream().forEach(map ->{
			ResponseData childList = orgInfoClient.findListByParentId(map.get("id").toString());
			List<Map<String, Object>> childArray = (List<Map<String, Object>>)childList.getResult();
			map.put("value", map.get("orgCode"));
			map.put("label", map.get("orgName"));
			map.put("children", childArray);
			if (!childArray.isEmpty()) {
				directList(childArray);
			}
		});
	}
}
