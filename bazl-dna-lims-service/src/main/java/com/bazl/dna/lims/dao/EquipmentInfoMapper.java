package com.bazl.dna.lims.dao;

import com.bazl.dna.lims.model.po.EquipmentInfo;
import com.bazl.dna.lims.model.vo.EquipmentInfoVo;

import java.util.List;

/**
 * @author wanghaiyang
 * @date 2019/3/11.
 */
public interface EquipmentInfoMapper {

    /**
     * 查询设备分页
     * @param equipmentInfoVo
     * @return
     */
    public List<EquipmentInfoVo> selectVOPaginationList(EquipmentInfoVo equipmentInfoVo);

    /**
     * 查询设备count
     * @param equipmentInfoVo
     * @return
     */
    public int selectVOCount(EquipmentInfoVo equipmentInfoVo);

    public EquipmentInfo selectByPrimaryKey(String id);

    public void update(EquipmentInfo equipmentInfo);

    public void insert(EquipmentInfo equipmentInfo);

    public List<EquipmentInfo> selectAll();

    /**
     * 查询报废设备
     * @param equipmentInfoVo
     * @return
     */
    public List<EquipmentInfoVo> selectEquipmentScrapInfoList(EquipmentInfoVo equipmentInfoVo);

    /**
     * 查询报废设备count
     * @param equipmentInfoVo
     * @return
     */
    public int selectEquipmentScrapInfoCount(EquipmentInfoVo equipmentInfoVo);

    public EquipmentInfoVo selectById(String id);
}