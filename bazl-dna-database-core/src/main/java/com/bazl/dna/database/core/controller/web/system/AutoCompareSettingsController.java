package com.bazl.dna.database.core.controller.web.system;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.database.constants.Constants;
import com.bazl.dna.database.core.constants.ErrorCodes;
import com.bazl.dna.database.core.constants.ErrorInfo;
import com.bazl.dna.database.core.controller.BaseController;
import com.bazl.dna.database.service.model.po.AutoCompareSettings;
import com.bazl.dna.database.service.service.AutoCompareSettingsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 自动比对设置 Created by Administrator on 2020-05-07
 */
@RestController
@RequestMapping("/system/compare")
public class AutoCompareSettingsController extends BaseController {

	@Autowired
	private AutoCompareSettingsService autoCompareSettingsService;

	/**
	 * 查看所有自动比对设置-liuchang
	 */
	@PostMapping(value = "/listAllCompareSettings")
	public ResponseData listAllCompareSetting() {
		try {
			List<AutoCompareSettings> list = autoCompareSettingsService.selectAllCompareSetting(null);
			return new ResponseData(list);
		} catch (Exception ex) {
			logger.error("invoke AutoCompareSettingsController.listAllCompareSettings error", ex);
			return new ResponseData("查询所有自动化比对设置出现异常 !" + ex.getMessage());
		}
	}


	/**
	 * 查看YSTR自动比对类型设置--liuchang
	 */
	@GetMapping(value = "/listYstrTargetType")
	@SuppressWarnings("all")
	public ResponseData listYstrTargetType() {
		try {
			// YSTR快速比对设置 02
			String COMPARE_MODE_YSTR = "02";
			List<AutoCompareSettings> YstrCompareSettings = autoCompareSettingsService.selectAllCompareSetting(COMPARE_MODE_YSTR);
			//循环取出 YstrCompareSettings 中的json字符串 转成对象发送至ResponseData
			List<Object> ystrCompareSetting = new ArrayList<>();
			String JSONStr = null;
			JSONArray resultList = new JSONArray();
			for (AutoCompareSettings autoCompareSet : YstrCompareSettings) {
				JSONObject sampleJson = new JSONObject(new LinkedHashMap());
				//转换去除空格,去除斜杠
				JSONStr = autoCompareSet.getTargetDataType().replaceAll("\"\"", " ");
				JSONArray jsonArray = JSONArray.parseArray(JSONStr);
				JSONObject targetDataTypeJson = new JSONObject();
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject json = jsonArray.getJSONObject(i);
					if (json.containsValue("01")){
						json.put("code","physicalEvidence");//现场物证
					}else if (json.containsValue("02")){
						json.put("code","mixEvidence");//混合物证
					}else if (json.containsValue("03")){
						json.put("code","criminalsPerson");//违法犯罪人员
					}else if (json.containsValue("05")){
						json.put("code","victimPerson");//受害人
					}else if (json.containsValue("06")){
						json.put("code","missingPerson");//失踪人员
					}else if (json.containsValue("07")){
						json.put("code","unknownCorpses");//无名尸
					}else if (json.containsValue("08")){
						json.put("code","relativesOfSuspects");//嫌疑人亲属
					}else if (json.containsValue("09")){
						json.put("code","relativesOfVictims");//受害人亲属
					}else if (json.containsValue("10")){
						json.put("code","relativesOfMissings");//失踪人口亲属
					}else if (json.containsValue("11")){
						json.put("code","basicLibrary");//基础库
					}
					targetDataTypeJson.put(json.getString("code"), json.getString("checked"));
				}
				targetDataTypeJson.put("instoreDataType",autoCompareSet.getInstoreDataType());//入库类型状态
				targetDataTypeJson.put("instoreDataName",autoCompareSet.getInstoreDataName());//入库类型名称
				targetDataTypeJson.put("id",autoCompareSet.getId());//主键编号
				targetDataTypeJson.put("autoCompareFlag",autoCompareSet.getAutoCompareFlag());//自动比对状态
				targetDataTypeJson.put("lowestSameLimit",autoCompareSet.getLowestSameLimit());//最少相同个数
				targetDataTypeJson.put("mostDiffLimit",autoCompareSet.getMostDiffLimit());//匹配下限
				targetDataTypeJson.put("COMPARE_MODE",autoCompareSet.getCompareMode()); //比中类型
				resultList.add(targetDataTypeJson);

			}
//			JSONObject targetDataType = new JSONObject();
//			targetDataType.put("physicalEvidence","现场物证");
//			targetDataType.put("mixEvidence","混合物证");
//			targetDataType.put("criminalsPerson","违法犯罪人员");
//			targetDataType.put("victimPerson","受害人");
//			targetDataType.put("missingPerson","失踪人员");
//			targetDataType.put("unknownCorpses","无名尸");
//			targetDataType.put("relativesOfSuspects","嫌疑人亲属");
//			targetDataType.put("relativesOfVictims","受害人亲属");
//			targetDataType.put("relativesOfMissings","失踪人口亲属");
//			targetDataType.put("basicLibrary","基础库");
//			resultList.add(targetDataType);
			return new ResponseData(resultList);

		} catch (Exception ex) {
			logger.error("invoke AutoCompareSettingsController.listYstrCompareSettings error", ex);
			return new ResponseData("查询Ystr自动化比对类型设置出现异常 !" + ex.getMessage());
		}
	}

	/**
	 * 修改YSTR自动化比对设置--liuchang
	 * 
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/updateYstrCompareSettings")
	@SuppressWarnings("all")
	public ResponseData updateYstrCompareSettings(HttpServletRequest request,
												  @RequestBody List<AutoCompareSettings> ystrAutoCompareSettings) {
		try {
			AutoCompareSettings  autoCompareSettings  = new AutoCompareSettings();
			if (ystrAutoCompareSettings != null && !ystrAutoCompareSettings.isEmpty()) {
				for (AutoCompareSettings compareSetting : ystrAutoCompareSettings) {
					if (compareSetting.getId() != null) {
						compareSetting.setId(compareSetting.getId());// 编号
					}
					if (compareSetting.getAutoCompareFlag() != null) {
						compareSetting.setAutoCompareFlag(compareSetting.getAutoCompareFlag()); // 是否加入自动比对
					}
					if (compareSetting.getCompareMode() != null) {
						compareSetting.setCompareMode(compareSetting.getCompareMode()); // 比对模式
					}else {
						compareSetting.setCompareMode(Constants.COMPARE_MODE_YSTR);
					}
					if (StringUtils.isNotBlank(compareSetting.getInstoreDataType())) {
						compareSetting.setInstoreDataType(compareSetting.getInstoreDataType());// 入库数据类型
					}
					if (compareSetting.getLowestSameLimit() != null) {
						compareSetting.setLowestSameLimit(compareSetting.getLowestSameLimit());// 匹配下限（最少匹配位点个数）
					}
					if (compareSetting.getMostDiffLimit() != null) {
						compareSetting.setMostDiffLimit(compareSetting.getMostDiffLimit());// 容差上限（最多差异位点个数）
					}
					if (StringUtils.isNotBlank(compareSetting.getInstoreDataName())) {
						compareSetting.setTargetDataType(compareSetting.getInstoreDataName());// 比对目标数据类型
					}

					if (StringUtils.isNotBlank(compareSetting.getTargetDataType())){
						JSONArray jsonArray = JSONArray.parseArray(compareSetting.getTargetDataType());
						JSONArray resultList = new JSONArray();
					for (int i = 0; i < jsonArray.size(); i++) {
						JSONObject json = jsonArray.getJSONObject(i);
							if (!json.containsValue("null")) {
								if (json.containsValue("physicalEvidence")) {
									json.put("code","01");//现场物证
								} else if (json.containsValue("mixEvidence")){
									json.put("code","02");//混合物证
								}else  if (json.containsValue("criminalsPerson")){
									json.put("code","03");//违法犯罪人员
								}else if (json.containsValue("victimPerson")){
									json.put("code","05");//受害人
								}else if (json.containsValue("missingPerson")){
									json.put("code","06");//失踪人员
								}else if (json.containsValue("unknownCorpses")){
									json.put("code","07");//无名尸
								}else if (json.containsValue("relativesOfSuspects")){
									json.put("code","08");//嫌疑人亲属
     							}else if (json.containsValue("relativesOfVictims")){
									json.put("code","09");//失踪人亲属
								}else if (json.containsValue("relativesOfMissings")){
									json.put("code","10");//基础库
								}else if (json.containsValue("basicLibrary")){
									json.put("code","11");
							    }
									if (json.containsValue("true")) {
										json.put("checked", "true");
									}
								resultList.add(json);
							}
						compareSetting.setTargetDataType(String.valueOf(resultList));
					  }		//比对目标数据类型名称
			    	}
					autoCompareSettingsService.updateById(compareSetting);
				}
			}
			return new ResponseData();
		} catch (Exception ex) {
			logger.error("AutoCompareSettingsController.updateYstrCompareSettings error", ex);
			return new ResponseData("修改Ystr自动化比对设置出现异常 !" + ex.getMessage());
		}
	}


	/**
	 * 查看STR自动化比对类型设置--liuchang
	 *
	 * @param
	 * @return
	 */
	@GetMapping(value = "/listStrTargetType")
	@SuppressWarnings("all")
	public ResponseData listStrTargetType() {
		try {
			// STR快速比对设置 XTR 包含 (01STR同型比对,02三联体比对,03单亲比对)
			String COMPARE_MODE_STR = "01";
			List<AutoCompareSettings> YstrCompareSettings = autoCompareSettingsService.selectAllCompareSetting(COMPARE_MODE_STR);
			//循环取出 YstrCompareSettings 中的json字符串 转成对象发送至ResponseData
			List<Object> ystrCompareSetting = new ArrayList<>();
			String JSONStr = null;
			JSONArray resultList = new JSONArray();
			for (AutoCompareSettings autoCompareSet : YstrCompareSettings) {
				if (StringUtils.isNotBlank(autoCompareSet.getTargetDataType())) {
					JSONObject sampleJson = new JSONObject(new LinkedHashMap());
					//转换去除空格,去除斜杠
					JSONStr = autoCompareSet.getTargetDataType().replaceAll("\"\"", " ");
					JSONArray jsonArray = JSONArray.parseArray(JSONStr);
					JSONObject targetDataTypeJson = new JSONObject();
					targetDataTypeJson.put("id", autoCompareSet.getId());
					for (int i = 0; i < jsonArray.size(); i++) {
						JSONObject json = jsonArray.getJSONObject(i);
						if (json.containsValue("01")) {
							json.put("code", "physicalEvidence");//现场物证
						} else if (json.containsValue("02")) {
							json.put("code", "mixEvidence");//混合物证
						} else if (json.containsValue("03")) {
							json.put("code", "criminalsPerson");//违法犯罪人员
						} else if (json.containsValue("05")) {
							json.put("code", "victimPerson");//受害人
						} else if (json.containsValue("06")) {
							json.put("code", "missingPerson");//失踪人员
						} else if (json.containsValue("07")) {
							json.put("code", "unknownCorpses");//无名尸
						} else if (json.containsValue("08")) {
							json.put("code", "relativesOfSuspects");//嫌疑人亲属
						} else if (json.containsValue("09")) {
							json.put("code", "relativesOfVictims");//受害人亲属
						} else if (json.containsValue("10")) {
							json.put("code", "relativesOfMissings");//失踪人口亲属
						} else if (json.containsValue("11")) {
							json.put("code", "basicLibrary");//基础库
						}
						targetDataTypeJson.put(json.getString("code"), json.getString("checked"));
					}

					targetDataTypeJson.put("instoreDataType", autoCompareSet.getInstoreDataType());//入库类型状态
					targetDataTypeJson.put("instoreDataName", autoCompareSet.getInstoreDataName());//入库类型名称
					targetDataTypeJson.put("id", autoCompareSet.getId());//主键编号
					targetDataTypeJson.put("autoCompareFlag", autoCompareSet.getAutoCompareFlag());//自动比对状态
					targetDataTypeJson.put("lowestSameLimit", autoCompareSet.getLowestSameLimit());//最少相同个数
					targetDataTypeJson.put("mostDiffLimit", autoCompareSet.getMostDiffLimit());//匹配下限
					targetDataTypeJson.put("compareMode",autoCompareSet.getCompareMode());//比对类型
					resultList.add(targetDataTypeJson);
				}
			}
				return new ResponseData(resultList);

		} catch (Exception ex) {
			logger.error("invoke AutCompareSettingsController.listStrCompareSettings error", ex);
			return new ResponseData("查询Str自动化比对设置出现异常 !" + ex.getMessage());
		}
	}

	/**
	 * 查看Mix自动化比对设置--liuchang
	 * @return
     */
	@GetMapping(value = "/listMixTargetType")
	public ResponseData listMixTargetType(){
		String COMPARE_MODE_MIX = "03";//比对模式混合
		List<AutoCompareSettings> mixCompareSettings = autoCompareSettingsService.selectAllCompareSetting(COMPARE_MODE_MIX);//查找混合比对模式
        if (mixCompareSettings!=null){
			try{
                return  new ResponseData(mixCompareSettings);
			}catch (Exception ex){
				logger.error("invoke  AutoCompareSettingsController.listMixTargetType error",ex);
				return new ResponseData("查询Mix自动化比对设置出现异常 ！"+ex.getMessage());
			}
		}
		logger.error("传入参数为空！");
		return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
	}

	/**
	 *
	 * 修改MIX自动化比对设置--liuchang
	 * @param request
	 * @param mixAutoCompareSettings
     * @return
     */
	@PostMapping(value = "/updateMixCompareSettings")
	@SuppressWarnings("all")
	public ResponseData updateMixCompareSettings(HttpServletRequest request, @RequestBody List<AutoCompareSettings> mixAutoCompareSettings) {
		try {
			if (mixAutoCompareSettings.size() > 0 && !mixAutoCompareSettings.isEmpty()) {
				for (AutoCompareSettings compareSettings : mixAutoCompareSettings) {
					String COMPARE_MODE_MIX = "03";//比对模式混合
					List<AutoCompareSettings> mixCompareSettings = autoCompareSettingsService.selectAllCompareSetting(COMPARE_MODE_MIX);//查找混合比对模式
                    //Add 新增
					if (mixCompareSettings.size()== 0){
					   compareSettings.setCompareMode(Constants.COMPARE_MODE_MIX_STR);//混合XTR比对认为有
					   if (StringUtils.isNotBlank(compareSettings.getTargetDataType())){
						   compareSettings.setTargetDataType(compareSettings.getTargetDataType());//比对目标数据类型
					   }
					       compareSettings.setInstoreDataType("05");//受害人
					   autoCompareSettingsService.save(compareSettings);
					//Edit 编辑
				   }else if (mixCompareSettings.size()>0 && mixCompareSettings.size() ==1){
						for (AutoCompareSettings compareSettings1: mixCompareSettings) {
							compareSettings.setId(compareSettings1.getId());//获取ID
							compareSettings.setCompareMode(Constants.COMPARE_MODE_MIX_STR);//混合XTR比对认为有
							if (StringUtils.isNotBlank(compareSettings.getTargetDataType())) {
								compareSettings.setTargetDataType(compareSettings.getTargetDataType());// 比对目标数据类型
							}
							if (StringUtils.isBlank(compareSettings.getInstoreDataType())){
								compareSettings.setInstoreDataType("05");//受害人
							}else{
								compareSettings.setInstoreDataType(compareSettings.getInstoreDataType());
							}
						}
					   autoCompareSettingsService.updateById(compareSettings);
				   }
				}
			}
			return  new ResponseData();
		} catch (Exception ex) {

        logger.error("AutoCompareSettingsController.updateMixCompareSettings error! ",ex);
			return new ResponseData("修改自动化比对设置异常！"+ex.getMessage());
		}
	}

	/**
	 * 修改STR自动化比对设置--liuchang
	 * 
	 * @param request
	 * @param strAutoCompareSettings
	 * @return
	 */
	@PostMapping(value = "/updateStrCompareSettings")
	@SuppressWarnings("all")
	public ResponseData updateStrCompareSettings(HttpServletRequest request,
												 @RequestBody List<AutoCompareSettings> strAutoCompareSettings) {
		try {
			if (strAutoCompareSettings != null && !strAutoCompareSettings.isEmpty()) {
				for (AutoCompareSettings compareSetting : strAutoCompareSettings) {
					if (compareSetting.getId() != null) {
						compareSetting.setId(compareSetting.getId());// 编号
					}
					if (compareSetting.getAutoCompareFlag() != null) {
						compareSetting.setAutoCompareFlag(compareSetting.getAutoCompareFlag()); // 是否加入自动比对
					}else{
						compareSetting.setAutoCompareFlag(0);
					}
					if (compareSetting.getCompareMode() != null) {
						compareSetting.setCompareMode(compareSetting.getCompareMode()); // 比对模式
					}else{
						compareSetting.setCompareMode(Constants.COMPARE_MODE_STR);
					}
					if (StringUtils.isNotBlank(compareSetting.getInstoreDataType())) {
						compareSetting.setInstoreDataType(compareSetting.getInstoreDataType());// 入库数据类型
					}
					if (compareSetting.getLowestSameLimit() != null) {
						compareSetting.setLowestSameLimit(compareSetting.getLowestSameLimit());// 匹配下限（最少匹配位点个数）
					}
					if (compareSetting.getMostDiffLimit() != null) {
						compareSetting.setMostDiffLimit(compareSetting.getMostDiffLimit());// 容差上限（最多差异位点个数）
					}

					if (StringUtils.isNotBlank(compareSetting.getTargetDataType())){
						JSONArray jsonArray = JSONArray.parseArray(compareSetting.getTargetDataType());// 比对目标数据类型
						JSONArray resultList = new JSONArray();
						for (int i = 0; i < jsonArray.size(); i++) {
							JSONObject json = jsonArray.getJSONObject(i);
							if (!json.containsValue("null")) {
								if (json.containsValue("physicalEvidence")) {
									json.put("code", "01");//现场物证
								} else if (json.containsValue("mixEvidence")) {
									json.put("code", "02");//混合物证
								} else if (json.containsValue("criminalsPerson")) {
									json.put("code", "03");//违法犯罪人员
								} else if (json.containsValue("victimPerson")) {
									json.put("code", "05");//受害人
								} else if (json.containsValue("missingPerson")) {
									json.put("code", "06");//失踪人员
								} else if (json.containsValue("unknownCorpses")) {
									json.put("code", "07");//无名尸
								} else if (json.containsValue("relativesOfSuspects")) {
									json.put("code", "08");//嫌疑人亲属
								} else if (json.containsValue("relativesOfVictims")) {
									json.put("code", "09");//失踪人亲属
								} else if (json.containsValue("relativesOfMissings")) {
									json.put("code", "10");//基础库
								} else if (json.containsValue("basicLibrary")) {
									json.put("code", "11");
								}
								if (json.containsValue("true")) {
									json.put("checked", "true");
								}
								resultList.add(json);
							}
							compareSetting.setTargetDataType(String.valueOf(resultList));
						}
						}
					autoCompareSettingsService.updateById(compareSetting);
				}
			}
			return new ResponseData();
		} catch (Exception ex) {
			logger.error("AutoCompareSettingsController.updateYstrCompareSettings error", ex);
			return new ResponseData("修改str自动化比对设置出现异常 !" + ex.getMessage());
		}
	}

}
