package com.bazl.dna.lims.service.impl;

import java.util.List;

import com.google.gson.internal.$Gson$Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bazl.dna.common.PageInfo;
import com.bazl.dna.lims.dao.LabExtractInfoMapper;
import com.bazl.dna.lims.model.po.LabExtractInfo;
import com.bazl.dna.lims.model.vo.LabExtractInfoVo;
import com.bazl.dna.lims.service.LabExtractInfoService;

/**
 * @author wanghaiyang
 * @date 2019/3/8.
 */
@Service
public class LabExtractInfoServiceImpl  extends BaseService implements LabExtractInfoService {

    @Autowired
    LabExtractInfoMapper labExtractInfoMapper;

    @Override
    public int deleteByPrimaryKey(String extractId) {
        return labExtractInfoMapper.deleteByPrimaryKey(extractId);
    }

    @Override
    public int insert(LabExtractInfo record) {
        return labExtractInfoMapper.insert(record);
    }

    @Override
    public LabExtractInfo selectByPrimaryKey(String extractId) {
        return labExtractInfoMapper.selectByPrimaryKey(extractId);
    }

    @Override
    public List<LabExtractInfo> selectAll() {
        return labExtractInfoMapper.selectAll();
    }

    @Override
    public int updateByPrimaryKey(LabExtractInfo record) {
        return labExtractInfoMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<LabExtractInfo> selectByTaskId(String taskId) {
        return labExtractInfoMapper.selectByTaskId(taskId);
    }

    @Override
    public List<LabExtractInfoVo> selectVoList(LabExtractInfoVo query, PageInfo pageInfo) {
        List<LabExtractInfoVo> labExtractInfoVoList = null;
        int pageNo;
        int pageSize;
        try {
            pageNo = pageInfo.getPage();
            pageSize = pageInfo.getEvePageRecordCnt();
            query.setOffset((pageNo - 1) * pageSize);
            query.setRows(query.getOffset() + pageSize);

            labExtractInfoVoList = labExtractInfoMapper.selectVoList(query);
        } catch(Exception ex) {
            logger.info("查询手动提取报错："+ex);
            return null;
        }

        return labExtractInfoVoList;
    }

    @Override
    public int selectVOCount(LabExtractInfoVo labExtractInfoVo) {
        return labExtractInfoMapper.selectVOCount(labExtractInfoVo);
    }

    @Override
    public List<LabExtractInfo> selectByCaseId(String caseId) {
        return labExtractInfoMapper.selectByCaseId(caseId);
    }

    @Override
    public int selectExtractCount(String orgId, String extractStage) {
        try{
            int count = labExtractInfoMapper.selectExtractCount(orgId,extractStage);
            return count;
        }catch (Exception ex){
            logger.error("invoke LabExtractInfoServiceImpl.selectExtractCount error ",ex.getMessage());
        }
        return 0;
    }

    @Override
    public List<LabExtractInfoVo> selectPaginationQuery(LabExtractInfoVo query, PageInfo pageInfo) {
        List<LabExtractInfoVo> labExtractInfoVoList = null;
        int pageNo;
        int pageSize;
        try {
            pageNo = pageInfo.getPage();
            pageSize = pageInfo.getEvePageRecordCnt();
            query.setOffset((pageNo - 1) * pageSize);
            query.setRows(query.getOffset() + pageSize);

            labExtractInfoVoList = labExtractInfoMapper.selectPaginationQuery(query);
        } catch(Exception ex) {
            logger.info("查询手动提取报错："+ex);
            return null;
        }

        return labExtractInfoVoList;
    }


}
