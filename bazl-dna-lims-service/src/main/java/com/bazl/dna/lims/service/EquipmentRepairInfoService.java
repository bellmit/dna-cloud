package com.bazl.dna.lims.service;

import java.util.List;

import com.bazl.dna.common.PageInfo;
import com.bazl.dna.lims.model.po.EquipmentRepairInfo;
import com.bazl.dna.lims.model.vo.EquipmentRepairInfoVo;

/**
 * @author wanghaiyang
 * @date 2019/3/11.
 */
public interface EquipmentRepairInfoService {

    /**
     * 插入信息
     * @param record
     * @return
     */
    public int insert(EquipmentRepairInfo record);

    /**
     * 根据主键id查询信息
     * @param id
     * @return
     */
    public EquipmentRepairInfo selectByPrimaryKey(String id);

    /**
     * 更新信息
     * @param record
     * @return
     */
    public int updateByPrimaryKey(EquipmentRepairInfo record);

    /**
     * 查询设备维修分页
     * @param equipmentRepairInfoVo
     * @param pageInfo
     * @return
     */
    public List<EquipmentRepairInfoVo> selectEquipmentRepairList(EquipmentRepairInfoVo equipmentRepairInfoVo, PageInfo pageInfo);

    /**
     * 查询设备维修count
     * @param equipmentRepairInfoVo
     * @return
     */
    public int selectEquipmentRepairCount(EquipmentRepairInfoVo equipmentRepairInfoVo);
}
