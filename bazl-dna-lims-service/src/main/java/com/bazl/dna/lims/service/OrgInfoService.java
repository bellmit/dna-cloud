package com.bazl.dna.lims.service;

import com.bazl.dna.lims.model.po.OrgInfo;
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

    List<OrgInfo> selectDelegateByParentsId(String parentId);

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
    
    /**
    查询分局（统计系统用）
    */
   List<OrgInfo> selectAllList();
}
