package com.bazl.dna.database.service.service;

import com.bazl.dna.database.service.model.po.QcSamplePolluteRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bazl.dna.database.service.model.qo.QcSamplePolluteRecordQuery;
import com.bazl.dna.database.service.model.vo.QcSamplePolluteRecordVo;

import java.util.List;

/**
 * <p>
 * 质控污染记录 服务类
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
public interface QcSamplePolluteRecordService extends IService<QcSamplePolluteRecord> {

    QcSamplePolluteRecord selectByPrimaryKey(Integer id);

    //分页 根据质控信息id 获取对应的质控污染记录信息 总计
    int selectListPagingCount(QcSamplePolluteRecordQuery query);
    //分页 根据质控信息id 获取对应的质控污染记录信息
    List<QcSamplePolluteRecordVo> selectListPaging(QcSamplePolluteRecordQuery query);

    //根据质控样本id和污染id查询信息
    QcSamplePolluteRecordVo selectBySampleIdAndId(QcSamplePolluteRecord query);
}
