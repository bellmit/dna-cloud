package com.bazl.dna.lims.service;

import java.util.List;

import com.bazl.dna.common.PageInfo;
import com.bazl.dna.lims.model.po.LabTaskInfo;
import com.bazl.dna.lims.model.vo.LabTaskInfoVo;

/**
 * @author wanghaiyang
 * @date 2019/3/8.
 */
public interface LabTaskInfoService {

    /**
     * 根据taskId删除信息
     * @param taskId
     * @return
     */
    public int deleteByPrimaryKey(String taskId);

    /**
     * 插入信息
     * @param record
     * @return
     */
    public int insert(LabTaskInfo record);

    /**
     * 根据taskId查询信息
     * @param taskId
     * @return
     */
    public LabTaskInfo selectByPrimaryKey(String taskId);

    /**
     * 查询所有的信息
     * @return
     */
    public List<LabTaskInfoVo> selectAll(LabTaskInfoVo record, PageInfo pageInfo);

    /**
     * 更新信息
     * @param record
     * @return
     */
    public int updateByPrimaryKey(LabTaskInfo record);

    /**
     * 查询检验任务列表页面
     * @param record
     * @return
     */
    public List<LabTaskInfo> selectLabTaskInfo(LabTaskInfo record);

    /**
     * 查询所有信息count
     * @param
     * @return
     */
    public int selectVOCount(LabTaskInfoVo record);
}
