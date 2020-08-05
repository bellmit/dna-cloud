package com.bazl.dna.database.service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bazl.dna.database.service.model.po.LabServerInfo;

import java.util.List;

/**
 * <p>
 * 实验室服务器信息 服务类
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-19
 */
public interface LabServerInfoService extends IService<LabServerInfo> {

	List<LabServerInfo> selectAll();

	/**
	 * 根据机构编号查询所属实验室
 	 * @param orgCode
	 * @return
     */
	LabServerInfo selectByOrgCode(String orgId);
}
