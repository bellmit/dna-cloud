package com.bazl.dna.database.nation.converter.service.impl;

import com.bazl.dna.database.nation.converter.constants.ConverterConstants;
import com.bazl.dna.database.nation.converter.dao.PersonInfoMapper;
import com.bazl.dna.database.nation.converter.model.po.PersonInfo;
import com.bazl.dna.database.nation.converter.service.PersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonInfoServiceImpl extends BaseService implements PersonInfoService {


    @Autowired
    private PersonInfoMapper personInfoMapper;

    @Override
    public List<PersonInfo> selectAuditPersonByCaseId(String caseId) {
      try{
            return personInfoMapper.selectAuditPersonByCaseId(caseId);
      }catch (Exception e) {
            e.getStackTrace();
            logger.error("查询国家库案件相关人员信息失败！" + e);
            return null;
      }
    }


    /**
     * 根据委托id查询真实的人员列表（排除掉物证对象）
     * @param consignmentId
     * @return
     */
    @Override
    public List<PersonInfo> selectRealPersonByConsignmentId(String consignmentId) {
        try{
            return personInfoMapper.selectRealPersonByConsignmentId(consignmentId, ConverterConstants.SAMPLE_RELATION_SELF, ConverterConstants.SAMPLE_RELATION_SELF_PROBABLY);
        }catch(Exception ex){
            logger.error("invoke PersonInfoService.selectRealPersonByConsignmentId error.", ex);
            return null;
        }
    }

    /**
     * 根据委托id查询全部的人员对象列表
     * @param consignmentId
     * @return
     */
    @Override
    public List<PersonInfo> selectAllPersonByConsignmentId(String consignmentId) {
        try{
            return personInfoMapper.selectAllPersonByConsignmentId(consignmentId);
        }catch(Exception ex){
            logger.error("invoke PersonInfoService.selectAllPersonByConsignmentId error.", ex);
            return null;
        }
    }

}
