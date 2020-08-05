package com.bazl.dna.database.service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bazl.dna.database.service.mapper.CasePersonInfoMapper;
import com.bazl.dna.database.service.model.po.CasePersonInfo;
import com.bazl.dna.database.service.model.vo.CasePersonInfoVo;
import com.bazl.dna.database.service.service.CasePersonInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 案件人员信息表 服务实现类
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
@Service
public class CasePersonInfoServiceImpl extends ServiceImpl<CasePersonInfoMapper, CasePersonInfo> implements CasePersonInfoService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CasePersonInfoMapper casePersonInfoMapper;

    private static final String CACHE_NAME = "CasePersonInfo";

    @Override
    @Cacheable(value = CACHE_NAME + ":queryCasePerson",keyGenerator="keyGenerator")
    public List<CasePersonInfo> queryCasePerson(CasePersonInfo casePersonInfo) {
        List<CasePersonInfo> casePersonInfoList = casePersonInfoMapper.queryCasePerson(casePersonInfo);
        return casePersonInfoList;
    }

    @Override
    public CasePersonInfo selectVoListById(Integer id) {
        return casePersonInfoMapper.selectVoListById(id);
    }

    /**
     * 根据案件id查询所有的案件人员信息
     * @param caseId
     * @return
     */
    @Override
   // @Cacheable(value = CACHE_NAME + ":selectVoListByCaseId", key = "#caseId")
    public List<CasePersonInfoVo> selectVoListByCaseId(Integer caseId){
        try {
            List<CasePersonInfoVo> casePersonInfoList = casePersonInfoMapper.selectVoListByCaseId(caseId);
            return casePersonInfoList;
        }catch (Exception ex) {
            logger.error("根据案件id查询所有的案件人员信息！" +ex.getMessage());
            return null;
        }
    }

    @Override
    @Cacheable(value = CACHE_NAME + ":selectByPersonTransferFlag", key = "#transferFlag")
    public List<CasePersonInfo> selectByPersonTransferFlag(int transferFlag) {
        try {
            return casePersonInfoMapper.selectByPersonTransferFlag(transferFlag);
        } catch (Exception ex) {
            logger.error("根据未上报人员信息异常！"+ ex.getMessage());
            return null;
        }
    }

    @Override
    @Cacheable(value = CACHE_NAME + ":selectByLabServerNo", key = "#labServerNo")
    public List<CasePersonInfo> selectByLabServerNo(String labServerNo) {
        try {
            return casePersonInfoMapper.selectByLabServerNo(labServerNo);
        } catch (Exception ex) {
            log.error("根据实验室编号查询案件人员信息异常！"+ ex.getMessage());
            return null;
        }
    }

    @Override
    @Cacheable(value = CACHE_NAME + ":selectAllCasePersonCount",keyGenerator="keyGenerator")
    public int selectAllCasePersonCount() {
        try{
            int count = casePersonInfoMapper.selectAllCasePersonCount();
            return count;
        }catch (Exception ex){
            log.error("查询全部案件人员数异常！"+ ex.getMessage());
            return 0;
        }
    }

    @Override
    public CasePersonInfo selectLabServerNo(String labServerNo) {
        try {
            return  casePersonInfoMapper.selectLabServerNo(labServerNo);
        } catch (Exception ex) {
            log.error("查询实验室编号异常！"+ex.getMessage());
            return null;
        }
    }
}
