package com.bazl.dna.database.nation.converter.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bazl.dna.database.nation.converter.constants.ConverterConstants;
import com.bazl.dna.database.nation.converter.dao.SampleInfoMapper;
import com.bazl.dna.database.nation.converter.model.po.SampleInfo;
import com.bazl.dna.database.nation.converter.service.SampleInfoService;

@Service
public class SampleInfoServiceImpl extends BaseService implements SampleInfoService {

    @Autowired
    private SampleInfoMapper sampleInfoMapper;


    /**
     * 根据委托id查询所有的检材列表
     * @param consignmentId
     * @return
     */
    @Override
    public List<SampleInfo> selectSelfSampleByConsignmentId(String consignmentId) {
        try {
            return sampleInfoMapper.selectSelfSampleByConsignmentId(consignmentId, ConverterConstants.SAMPLE_RELATION_SELF, ConverterConstants.SAMPLE_RELATION_SELF_PROBABLY);
        }catch(Exception ex){
            logger.error("invoke SampleInfoService.selectSelfSampleByConsignmentId error.", ex);
            return null;
        }
    }

    /**
     * 根据委托id查询所有的样本列表
     * @param consignmentId
     * @return
     */
    @Override
    public List<SampleInfo> selectAllSampleByConsignmentId(String consignmentId) {
        try {
            return sampleInfoMapper.selectAllSampleByConsignmentId(consignmentId);
        }catch(Exception ex){
            logger.error("invoke SampleInfoService.selectAllSampleByConsignment error.", ex);
            return null;
        }
    }

    @Override
    public List<SampleInfo> selectAuditSampleByCaseId(String caseId) {
      try{
          return sampleInfoMapper.selectAuditSampleByCaseId(caseId) ;
      }catch (Exception e) {
            e.getStackTrace();
            logger.error("查询国家库审核通过检材信息错误！" + e);
            return null;
      }
    }


	@Override
	public SampleInfo selectByPrimaryKey(String id) {
		return sampleInfoMapper.selectByPrimaryKey(id);
	}
}
