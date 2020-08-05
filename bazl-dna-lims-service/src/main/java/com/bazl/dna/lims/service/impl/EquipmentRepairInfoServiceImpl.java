package com.bazl.dna.lims.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bazl.dna.common.PageInfo;
import com.bazl.dna.lims.dao.EquipmentRepairInfoMapper;
import com.bazl.dna.lims.model.po.EquipmentRepairInfo;
import com.bazl.dna.lims.model.vo.EquipmentRepairInfoVo;
import com.bazl.dna.lims.service.EquipmentRepairInfoService;

/**
 * @author wanghaiyang
 * @date 2019/3/11.
 */
@Service
public class EquipmentRepairInfoServiceImpl extends BaseService implements EquipmentRepairInfoService {

    @Autowired
    EquipmentRepairInfoMapper equipmentRepairInfoMapper;

    @Override
    public int insert(EquipmentRepairInfo record) {
        return equipmentRepairInfoMapper.insert(record);
    }

    @Override
    public EquipmentRepairInfo selectByPrimaryKey(String id) {
        return equipmentRepairInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(EquipmentRepairInfo record) {
        return equipmentRepairInfoMapper.updateByPrimaryKey(record);
    }

    /**
     * 查询设备维修分页
     * @param query
     * @param pageInfo
     * @return
     */
    @Override
    public List<EquipmentRepairInfoVo> selectEquipmentRepairList(EquipmentRepairInfoVo query, PageInfo pageInfo) {
        List<EquipmentRepairInfoVo> equipmenRepairInfoVOList = null;
        int pageNo;
        int pageSize;
        try {
            pageNo = pageInfo.getPage();
            pageSize = pageInfo.getEvePageRecordCnt();
            query.setOffset((pageNo - 1) * pageSize);
            query.setRows(query.getOffset() + pageSize);

            equipmenRepairInfoVOList = equipmentRepairInfoMapper.selectVOPaginationList(query);
        } catch(Exception ex) {
            logger.info("查询设备名称报错："+ex);
            return null;
        }

        return equipmenRepairInfoVOList;
    }

    /**
     * 查询设备维修count
     * @param equipmentRepairInfoVo
     * @return
     */
    @Override
    public int selectEquipmentRepairCount(EquipmentRepairInfoVo equipmentRepairInfoVo) {
        return equipmentRepairInfoMapper.selectVOCount(equipmentRepairInfoVo);
    }

}
