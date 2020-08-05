package com.bazl.dna.lims.service;

import java.util.List;

import com.bazl.dna.common.PageInfo;
import com.bazl.dna.lims.model.po.EquipmentTypeInfo;
import com.bazl.dna.lims.model.vo.EquipmentTypeInfoVo;

/**
 * @author sxr
 * @date 2019/4/9.
 */
public interface EquipmentTypeInfoService {

    public List<EquipmentTypeInfo> selectAllList();

    public void insert(EquipmentTypeInfo equipmentTypeInfo);

    public EquipmentTypeInfo selectByPrimaryKey(String id);

    public List<EquipmentTypeInfo> selectBy(String orgId);

    public void update(EquipmentTypeInfo equipmentTypeInfo);

    public List<EquipmentTypeInfoVo> selectEquipmentTypeList(EquipmentTypeInfoVo equipmentTypeInfoVo, PageInfo pageInfo);

    public int selectEquipmentTypeCount(EquipmentTypeInfoVo equipmentTypeInfoVo);

    /**
     * 根据条件查询信息
     * @param equipmentTypeInfo
     * @return
     */
    public List<EquipmentTypeInfo> selectList(EquipmentTypeInfo equipmentTypeInfo);

    List<EquipmentTypeInfo> selectLabEquipmentTypeList(EquipmentTypeInfo equipmentTypeInfo);

    /**
     * 设备类型id查询设备名称
     * @param id
     * @return
     */
    List<EquipmentTypeInfo> selectEquipmentById(String id);
}
