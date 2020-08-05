    package com.bazl.dna.lims.core.service;

    import com.bazl.dna.lims.core.model.po.LoaPermission;
    import com.bazl.dna.lims.core.model.po.TreeData;

    import java.util.List;

    public interface LoaPermissionService {
        int deleteByPrimaryKey(String permissionId);

        int insert(LoaPermission record);

        LoaPermission selectByPrimaryKey(String permissionId);

        List<LoaPermission> selectAll();

        int updateByPrimaryKey(LoaPermission record);

        /**
         *根据角色id获取权限
         * @param roleId
         * @return
         */
        public List<LoaPermission> listByRoleId(String roleId);

        List<LoaPermission> selectBySubSystem(String subSystem);

        List<LoaPermission> selectBySubSystemAndUserId(String subSystem, String userId);

        List<LoaPermission> selectBySubSystemAndRoleId(String subSystem, String roleId);

        List<LoaPermission> selectBy(String roleId);

        List<TreeData> selectPermissionList();

        int saveMenu(LoaPermission permission);

        int delPermission(LoaPermission permission);
    }