package com.bazl.dna.database.core.controller.web.system;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.database.core.controller.BaseController;
import com.bazl.dna.database.service.model.po.InstoreDataCondition;
import com.bazl.dna.database.service.model.po.InstoreDataType;
import com.bazl.dna.database.service.service.InstoreDataConditionService;
import com.bazl.dna.database.service.service.InstoreDataTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 入库数据类型、入库基因位点设置等
 * 
 * @author lizhihua on 2020/2/19.
 */
@RestController
@RequestMapping("/system/instore")
public class InstoreDataController extends BaseController {

	@Autowired
	InstoreDataTypeService instoreDataTypeService;
	@Autowired
	InstoreDataConditionService instoreDataConditionService;

	/**
	 * 查询所有的入库数据类型
	 * 
	 * @param
	 * @return
	 */
	@PostMapping(value = "/listAllTypes")
	public ResponseData listAllInstoreDataType() {
		try {
			List<InstoreDataType> instoreDataTypeList = instoreDataTypeService.list();
			return new ResponseData(instoreDataTypeList);
		} catch (Exception ex) {
			logger.error("invoke InstoreDataController.listAllInstoreDataType error.", ex);
			return new ResponseData("查询所有的入库数据类型出现异常！" + ex.getMessage());
		}
	}

	/**
	 * 查询所有的入库数据最少基因位点数设置-liuchang
	 * 
	 * @param
	 * @return
	 */

	@PostMapping(value = "/instoreConditions")
    public ResponseData listInstoreConditions(){
        try {
            List<InstoreDataCondition>  leastGeneCountList =  instoreDataConditionService.LeastGeneCountForInstoreDataType();
            return new ResponseData(leastGeneCountList);
        }catch(Exception ex){
            logger.error("invoke InstoreDataController.listInstoreConditions error.", ex);
            return new ResponseData("查询所有的入库数据最少基因位点数设置出现异常！" + ex.getMessage());
        }
    }

	/**
	 * 编辑入库的最少基因位点数设置-liuchang
	 * 
	 * @return
	 */
	@RequestMapping(value = "/updateInstoreConditions", method = RequestMethod.POST)
	public ResponseData updateInstoreConditions(HttpServletRequest request,
			@RequestBody List<InstoreDataCondition> instoreDataCondition) {
		try {
			if (instoreDataCondition.size() > 0 && instoreDataCondition != null) {
				for (InstoreDataCondition instoreData : instoreDataCondition) {
					if (instoreData.getId() != null) {
						instoreData.setId(instoreData.getId());
					}
					if (instoreData.getInstoreDataType() != null) {
						instoreData.setInstoreDataType(instoreData.getInstoreDataType());
					}
					if (instoreData.getStrLocusCount() != null) {
						instoreData.setStrLocusCount(instoreData.getStrLocusCount());
					}
					if (instoreData.getYstrLocusCount() != null) {
						instoreData.setYstrLocusCount(instoreData.getYstrLocusCount());
					}
					instoreDataConditionService.updateById(instoreData);
				}
			}
			return new ResponseData();
		} catch (Exception ex) {
			logger.error("invoke InstoreDataController.updateInstoreConditions error.", ex);
			return new ResponseData("修改入库最少基因位点数设置出现异常! " + ex.getMessage());
		}
	}
}
