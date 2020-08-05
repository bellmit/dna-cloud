package com.bazl.dna.lims.service;

import java.util.List;

import com.bazl.dna.lims.model.po.LaboratoryInfo;

/**
 * 实验室信息接口
 */
public interface LaboratoryInfoService extends BasisInterface<LaboratoryInfo,String>{
	/**
     *查询全部实验室列表
     */
    List<LaboratoryInfo> selectAll();
    
    LaboratoryInfo queryByLab(LaboratoryInfo laboratoryInfo);
    
	List<LaboratoryInfo> findListByMonitorType(Integer monitorType);

	LaboratoryInfo queryById(String id);
}
