package com.bazl.dna.database.service.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bazl.dna.database.service.mapper.QcSampleInfoMapper;
import com.bazl.dna.database.service.model.po.CurrencyQcResult;
import com.bazl.dna.database.service.model.po.QcSampleInfo;
import com.bazl.dna.database.service.model.qo.QcSampleInfoQuery;
import com.bazl.dna.database.service.model.vo.QcSampleInfoVo;
import com.bazl.dna.database.service.service.QcSampleInfoService;

/**
 * <p>
 * 质控样本信息表 服务实现类
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
@Service
public class QcSampleInfoServiceImpl extends ServiceImpl<QcSampleInfoMapper, QcSampleInfo> implements QcSampleInfoService {
	
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private QcSampleInfoMapper qcSampleInfoMapper;

    @Override
    public List<CurrencyQcResult> currencyCriminalQuery(CurrencyQcResult currencyQcResult) {

        List<CurrencyQcResult>  qcResults =  qcSampleInfoMapper.currencyCriminalQuery(currencyQcResult);

        return qcResults;
    }

    @Override
    public int selectListPagingCount(QcSampleInfoQuery query) {
        try {
            int totalCount = qcSampleInfoMapper.selectListPagingCount(query);

            return totalCount;
        }catch(Exception ex) {
            logger.error("invoke QcSampleInfoService.selectListPagingCount error.", ex);
            return 0;
        }
    }

    @Override
    public List<QcSampleInfoVo> selectListPaging(QcSampleInfoQuery query) {
        try {
            //分页计算
            query.setOffset((query.getPageIndex() - 1) * query.getRows());
            List<QcSampleInfoVo> qcSampleInfoList = qcSampleInfoMapper.selectListPaging(query);
//            for(QcSampleInfoVo q:qcSampleInfoList){
//                //质控样本类型 质控人员类型 页面回显值转换
//                q.setQcPersonTypeName(dictItemMapper.selectNameByTypeCodeAndCode("QC_PERSON_TYPE",q.getQcPersonType()));
//                q.setQcSampleTypeName(dictItemMapper.selectNameByTypeCodeAndCode("QC_SAMPLE_TYPE",q.getQcSampleType()));
//                q.setQcPersonGender(dictItemMapper.selectNameByTypeCodeAndCode("PERSON_GENDER",q.getQcPersonGender()));
//            }

            return qcSampleInfoList;
        }catch(Exception ex) {
            logger.error("invoke QcSampleInfoService.selectListPaging error.", ex);
            return null;
        }
    }

    @Override
    public QcSampleInfo selectById(Integer id) {
        try {
            QcSampleInfo qcSampleInfoVo = qcSampleInfoMapper.selectById(id);
            return qcSampleInfoVo;
        }catch(Exception ex) {
            logger.error("invoke QcSampleInfoService.selectVoById error.", ex);
            return null;
        }
    }

    @Override
    public Boolean insertAdd(QcSampleInfo qcSampleInfo) {
        try {
            return qcSampleInfoMapper.insertAdd(qcSampleInfo) ==1 ? true : false;
        }catch(Exception ex) {
            logger.error("invoke QcSampleInfoService.insertAdd error.", ex);
            return false;
        }
    }

    @Override
    public Boolean updateByIdOverride(QcSampleInfo qcSampleInfo) {
        try{
            return qcSampleInfoMapper.updateByIdOverride(qcSampleInfo)!=0?true:false;
        }catch (Exception ex){
            logger.error("invoke QcSampleInfoService.updateByIdOverride error.", ex);
            return false;
        }

    }

    @Override
    public Boolean deleteByIdOverride(Integer id) {
        try {
            return qcSampleInfoMapper.deleteByIdOverride(id) != 0 ? true : false;
        }catch (Exception ex){
            logger.error("invoke QcSampleInfoService.deleteByIdOverride error.", ex);
            return false;
        }
    }

}
