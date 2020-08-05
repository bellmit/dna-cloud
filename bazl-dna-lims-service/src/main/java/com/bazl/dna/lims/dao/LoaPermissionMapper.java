package com.bazl.dna.lims.dao;

import com.bazl.dna.lims.model.po.LoaPermission;
import com.bazl.dna.lims.model.po.TreeData;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoaPermissionMapper {
    int deleteByPrimaryKey(String permissionId);

    int insert(LoaPermission record);

    LoaPermission selectByPrimaryKey(String permissionId);

    List<LoaPermission> selectAll();

    public List<LoaPermission> selectBySubSystem(@Param("subSystem") String subSystem);

    List<LoaPermission> selectBySubSystemAndUserId(@Param("subSystem") String subSystem, @Param("userId") String userId);

    List<LoaPermission> selectBySubSystemAndRoleId(@Param("subSystem") String subSystem, @Param("roleId") String roleId);

    List<LoaPermission> selectBy(String roleId);

    int updateByPrimaryKey(LoaPermission record);

    /**
     *根据角色id获取权限
     * @param roleId
     * @return
     */
    public List<LoaPermission> listByRoleId(String roleId);


    List<TreeData> selectPermissionByParentId(@Param("parentId") String parentId);

    int updatePermission(@Param("permission") LoaPermission permission);

    int insertPermission(@Param("permission") LoaPermission permission);

    int delPermission(@Param("permission") LoaPermission permission);

    /**
     * 统计系统用的菜单列表
     * @return
     */
    List<LoaPermission> selectDataStats();

}