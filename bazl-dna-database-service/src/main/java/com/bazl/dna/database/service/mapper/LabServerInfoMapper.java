package com.bazl.dna.database.service.mapper;

import com.bazl.dna.database.service.model.po.LabServerInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 实验室服务器信息 Mapper 接口
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-19
 */
public interface LabServerInfoMapper extends BaseMapper<LabServerInfo> {

    List<LabServerInfo> selectAll();

    /**
     * 查询实验室信息根据机构编号
     * @param orgId
     * @return
     */
    LabServerInfo selectByOrgCode(String orgId);
}
