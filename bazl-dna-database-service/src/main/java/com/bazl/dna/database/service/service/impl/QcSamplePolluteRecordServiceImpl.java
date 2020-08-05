package com.bazl.dna.database.service.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bazl.dna.database.service.mapper.QcSamplePolluteRecordMapper;
import com.bazl.dna.database.service.model.po.QcSamplePolluteRecord;
import com.bazl.dna.database.service.model.qo.QcSamplePolluteRecordQuery;
import com.bazl.dna.database.service.model.vo.QcSamplePolluteRecordVo;
import com.bazl.dna.database.service.service.QcSamplePolluteRecordService;

/**
 * <p>
 * 质控污染记录 服务实现类
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
@Service
public class QcSamplePolluteRecordServiceImpl extends ServiceImpl<QcSamplePolluteRecordMapper, QcSamplePolluteRecord> implements QcSamplePolluteRecordService {
	
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private QcSamplePolluteRecordMapper qcSamplePolluteRecordMapper;

    @Override
    public QcSamplePolluteRecord selectByPrimaryKey(Integer id) {
        return qcSamplePolluteRecordMapper.selectByPrimaryKey(id);
    }

    @Override
    public int selectListPagingCount(QcSamplePolluteRecordQuery query) {
        try {
            int totalCount = qcSamplePolluteRecordMapper.selectListPagingCount(query);

            return totalCount;
        }catch(Exception ex) {
            logger.error("invoke QcSamplePolluteRecordService.selectListPagingCount error.", ex);
            return 0;
        }
    }

    @Override
    public List<QcSamplePolluteRecordVo> selectListPaging(QcSamplePolluteRecordQuery query) {
        try {
            return qcSamplePolluteRecordMapper.selectListPaging(query);
        }catch(Exception ex) {
            logger.error("invoke QcSamplePolluteRecordService.selectListPaging error.", ex);
            return null;
        }
    }

    @Override
    public QcSamplePolluteRecordVo selectBySampleIdAndId(QcSamplePolluteRecord query) {
        return qcSamplePolluteRecordMapper.selectBySampleIdAndId(query);
    }
}
