package com.bazl.dna.lims.core.service;

import com.bazl.dna.lims.core.model.po.OrgInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Sun on 2018/12/20.
 */
@Repository
public interface OrgInfoService {
    int insert(OrgInfo record);

    List<OrgInfo> selectAll();

    OrgInfo selectByPrimaryKey(String orgId);

    List<OrgInfo> selectDelegateByParentsId(String parentsId);

    /**
     * 根据单位id获取对应实验室名称
     * @param orgId
     * @return
     */
    String selectLabNameById(String orgId);

    /**
     * 查询受理单位
     * @return
     */
    List<OrgInfo> selectAcceptOrgList();

    /**
     * 根据用户id查询机构id
     * @return
     */
    String getUseridByOrgid(String userId);
}
