package com.bazl.dna.database.core.controller.web.frequency;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bazl.dna.common.PageInfo;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.database.core.controller.BaseController;
import com.bazl.dna.database.service.model.bo.AllelFrequencyModel;
import com.bazl.dna.database.service.model.bo.FrequencyInfoModel;
import com.bazl.dna.database.service.model.bo.PopulationLocusNameModel;
import com.bazl.dna.database.service.model.po.AlleleFrequencyInfo;
import com.bazl.dna.database.service.model.po.DnaLocusInfo;
import com.bazl.dna.database.service.model.po.PopulationFrequencyInfo;
import com.bazl.dna.database.service.model.qo.PopulationFrequencyQuery;
import com.bazl.dna.database.service.model.vo.AlleleFrequencyInfoVo;
import com.bazl.dna.database.service.model.vo.PopulationFrequencyInfoVo;
import com.bazl.dna.database.service.service.AlleleFrequencyInfoService;
import com.bazl.dna.database.service.service.DnaLocusInfoService;
import com.bazl.dna.database.service.service.PopulationFrequencyInfoService;

/**
 * <p>
 * 种群基因频率 控制器
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
@RestController
@RequestMapping("/frequency")
public class PopulationFrequencyInfoController extends BaseController {

	@Autowired
	PopulationFrequencyInfoService populationFrequencyInfoService;
	@Autowired
	AlleleFrequencyInfoService alleleFrequencyInfoService;
	@Autowired
	DnaLocusInfoService  dnaLocusInfoService;

	/**
	 * 查询所有的种群频率方案集合
	 *
	 * @return
	 */
	@PostMapping(value = "/list")
	public ResponseData list(Integer id, String geneType, String populationName) {
		try {
			List<PopulationFrequencyInfo> list = populationFrequencyInfoService.findList(id, geneType, populationName);
			return new ResponseData(list);
		} catch (Exception ex) {
			logger.error("invoke PopulationFrequencyInfoController.queryList error.", ex);
			return new ResponseData("查询种群频率信息出现异常！" + ex.getMessage());
		}
	}

	/**
	 * 查询全部种群频率信息接口--liuchang
	 *
	 * @param query
	 * @return
	 */
	@PostMapping(value = "/listAllAlleleFrequency")
	public ResponseData listAllAlleleFrequency(@RequestBody PopulationFrequencyQuery query) {
		try {
			if(StringUtils.isNotBlank(query.getPopulationName())){
				query.setPopulationName(query.getPopulationName().trim());//方案名称
			}
			if (StringUtils.isNotBlank(query.getStatsCountry())){
				query.setStatsCountry(query.getStatsCountry().trim());//统计国家
			}
			if (StringUtils.isNotBlank(query.getStatsRace())){
				query.setStatsRace(query.getStatsRace().trim());//统计名族
			}
			//分页查询全部种群基因座频率信息
			List<PopulationFrequencyInfoVo> allAlleleFrequencyList = populationFrequencyInfoService.populationPaginationQuery(query);
			//查询总计全部种群基因座频率信息
			int totalCount = populationFrequencyInfoService.populationPaginationCount(query);
			//分页信息
			PageInfo pageInfo = new PageInfo();

			pageInfo.setEvePageRecordCnt(query.getRows());//每页显示条数
			pageInfo.setAllRecordCount(totalCount);//总计条数
			if(totalCount >0 && totalCount % pageInfo.getEvePageRecordCnt() == 0){ //总计页码
				pageInfo.setPageCount((int)totalCount/pageInfo.getEvePageRecordCnt());
			}else{
				pageInfo.setPageCount((int)totalCount/pageInfo.getEvePageRecordCnt()+1);
			}
			Map<String, Object> resultObj = new HashMap<>();
			resultObj.put("pageInfo", pageInfo);
			resultObj.put("allAlleleFrequencyList", allAlleleFrequencyList);
			return new ResponseData(resultObj);
		} catch (Exception ex) {
			logger.error("invoke PopulationFrequencyInfoController.listAllAlleleFrequency error", ex);
			return new ResponseData("查询全部种群频率信息出现异常！" + ex.getMessage());
		}
	}

	/**
	 * 根据频率ID删除频率信息--liuchang
     * @Return
	 */
     @GetMapping(value = "/deletePopulationFrequency")
	 public ResponseData deletePopulationFrequencyByPopulationId(Integer populationId){
		 try{
              if(populationId !=null){
				//删除频率信息根据频率ID
                populationFrequencyInfoService.deleteAlleleFrequencyByPopulationId(populationId);
			  }
			 return  new ResponseData();
		 }catch (Exception ex){
			 logger.error("invoke PopulationFrequencyInfoController.deletePopulationFrequency error",ex);
			 return new ResponseData("根据频率ID删除人口频率信息出现异常！"+ex.getMessage());
		 }

	 }

	/**
	 * 根据种群id获取等位基因频率列表--liuchang
	 *
	 * @return
	 */
	@GetMapping(value = "/listAlleleFrequency")
	public ResponseData listAlleleFrequencyByPopulationId(Integer populationId) {
		try {
			List<PopulationFrequencyInfoVo> populationFrequencyInfo = populationFrequencyInfoService.listByPopulationId(populationId);//种群基因频率信息
			List<AlleleFrequencyInfoVo> alleleFrequencyInfo = alleleFrequencyInfoService.listByPopulationId(populationId);//查询种群基因频率信息根据ID
			for (AlleleFrequencyInfoVo alleleFrequency :alleleFrequencyInfo){
				String LocusName = alleleFrequency.getLocusName();
				List<AlleleFrequencyInfo> list = alleleFrequencyInfoService.listByPopulationIdAndLocusName(populationId,LocusName);//根据种群基因频率信息查询
			     alleleFrequency.setAlleleFrequencyInfoList(list);
			}

			Map<String, Object> resultObj = new HashMap<>();
			resultObj.put("populationFrequencyInfo", populationFrequencyInfo);
			resultObj.put("alleleFrequencyInfo", alleleFrequencyInfo);
			return new ResponseData(resultObj);
		} catch (Exception ex) {
			logger.error("invoke PopulationFrequencyInfoController.listAlleleFrequencyByPopulationId error.", ex);
			return new ResponseData("根据种群id获取等位基因频率列表出现异常！" + ex.getMessage());
		}
	}

	/**
	 * (根据种群id和基因座名称删除等位基因频率信息)--liuchang
	 *
	 * @param allelFrequencyModel
	 * @return
	 */
	@PostMapping(value = "/deleteAlleleFrequency")
	public ResponseData deleteAlleleFrequency(@RequestBody AllelFrequencyModel allelFrequencyModel) {
		try {
			//删除方法 根据种群ID和基因座名称
			if (allelFrequencyModel != null) {
				Integer populationFrequencyId =allelFrequencyModel.getPopulationFrequencyId();//种群ID
				String locusName = allelFrequencyModel.getLocusName().trim();//基因座名称
				alleleFrequencyInfoService.deleteAlleleFrequency(populationFrequencyId, locusName);
			}
				return new ResponseData();
			}catch(Exception ex){
				logger.error("invoke  PopulationFrequencyInfoController.deleteAlleleFrequency error.", ex);
				return new ResponseData("根据种群id和基因座名称删除等位基因频率信息出现异常！ " + ex.getMessage());
			}
		}

	/**
	 * 根据种群id和基因座名称 获取其 等位基因和频率--liuchang
	 *
	 * @return
	 */
	@PostMapping(value = "/listByPopulationIdAndLocusName")
	public ResponseData listByPopulationIdAndLocusName(@RequestBody PopulationLocusNameModel populationLocusNameModel) {
		try {
				int populationId = populationLocusNameModel.getPopulationId();//种群频率ID
				String locusName = populationLocusNameModel.getLocusName(); //基因座名称
				List<AlleleFrequencyInfo> list = alleleFrequencyInfoService.listByPopulationIdAndLocusName(populationId, locusName);//根据种群ID和基因座名称获取等位基因和频率
				return new ResponseData(list);
		} catch (Exception ex) {
			logger.error("invoke PopulationFrequencyInfoController.listByPopulationIdAndLocusName error.", ex);
			return new ResponseData("根据种群id和基因座名称获取其等位基因和频率！" + ex.getMessage());
		}
	}

	/**
	 * 根据ID删除等位基因频率信息--liuchang
	 * @Return
	 */
	@GetMapping(value = "/deleteAlleleFrequencyById")
	public ResponseData deleteAlleleFrequencyById(Integer alleleFrequencyId){
		try{
			if (alleleFrequencyId !=null) {
				alleleFrequencyInfoService.deleteAlleleFrequencyById(alleleFrequencyId);
				return new ResponseData();
			}else {
				return new ResponseData("根据ID删除等位基因频率信息异常！id不存在");
			}
		}catch (Exception ex){
			logger.error("invoke PopulationFrequencyInfoController.deleteAlleleFrequencyById ");
			return new ResponseData("根据ID删除等位基因频率信息异常！"+ex.getMessage());
		}
	}

	/**
	 * 保存种群信息基因频率信息--liuchang
	 * @param frequencyInfoModel
	 * @return
     */
	@PostMapping(value ="/saveFrequency")
	public ResponseData saveFrequency(@RequestBody FrequencyInfoModel frequencyInfoModel) {
		try {
            this.alleleFrequencyInfoService.saveFrequency(frequencyInfoModel);//种群基因频率
			return new ResponseData();
		} catch (Exception ex) {
			logger.error("invoke PopulationFrequencyInfoController.saveFrequency error");
			return new ResponseData("保存种群信息基因频率信息异常！" + ex.getMessage());
		}
	}

	/**
	 * 查询STR基因座信息--liuchang
	 * @return
     */
	@GetMapping(value = "/selectAllStrLocusInfo")
	public ResponseData selectAllStrLocusInfo(){
		try {
			String locusType = "1";
			List<DnaLocusInfo> locusInfos = dnaLocusInfoService.selectByLocusType(locusType); //查询STR基因座信息
			return new ResponseData(locusInfos);
		}catch (Exception ex){
			logger.error("invoke PopulationFrequencyInfoController.selectAllStrLocusInfo error");
			return new ResponseData("查询全部STR基因座信息异常!"+ex.getMessage());
		}
	}


}
