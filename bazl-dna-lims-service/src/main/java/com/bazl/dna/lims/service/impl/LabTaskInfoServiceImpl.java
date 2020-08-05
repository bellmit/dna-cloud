package com.bazl.dna.lims.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bazl.dna.common.PageInfo;
import com.bazl.dna.lims.dao.LabTaskInfoMapper;
import com.bazl.dna.lims.model.po.LabTaskInfo;
import com.bazl.dna.lims.model.vo.LabTaskInfoVo;
import com.bazl.dna.lims.service.LabTaskInfoService;

/**
 * @author wanghaiyang
 * @date 2019/3/8.
 */
@Service
public class LabTaskInfoServiceImpl extends BaseService implements LabTaskInfoService {

    @Autowired
    LabTaskInfoMapper labTaskInfoMapper;

    @Override
    public int deleteByPrimaryKey(String taskId) {
        return labTaskInfoMapper.deleteByPrimaryKey(taskId);
    }

    @Override
    public int insert(LabTaskInfo record) {
        return labTaskInfoMapper.insert(record);
    }

    @Override
    public LabTaskInfo selectByPrimaryKey(String taskId) {
        return labTaskInfoMapper.selectByPrimaryKey(taskId);
    }

    @Override
    public List<LabTaskInfoVo> selectAll(LabTaskInfoVo record, PageInfo pageInfo) {
        List<LabTaskInfoVo> caseInfoVOList = null;
        int pageNo;
        int pageSize;
        try {
            pageNo = pageInfo.getPage();
            pageSize = pageInfo.getEvePageRecordCnt();
            record.setOffset((pageNo - 1) * pageSize);
            record.setRows(record.getOffset() + pageSize);

            caseInfoVOList = labTaskInfoMapper.selectAll(record);
        } catch(Exception ex) {
            System.out.println(ex);
            logger.info("查询与补送报错："+ex);
            return null;
        }
        return caseInfoVOList;
    }

    @Override
    public int updateByPrimaryKey(LabTaskInfo record) {
        return labTaskInfoMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<LabTaskInfo> selectLabTaskInfo(LabTaskInfo record) {
        return labTaskInfoMapper.selectLabTaskInfo(record);
    }

    /**
     * 查询与补送查询count
     * @param
     * @return
     */
    @Override
    public int selectVOCount(LabTaskInfoVo record) {
        return labTaskInfoMapper.selectVOCount(record);
    }

}
