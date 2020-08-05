package com.bazl.dna.database.service.mapper;

import com.bazl.dna.database.service.model.po.QcSamplePolluteRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bazl.dna.database.service.model.qo.QcSamplePolluteRecordQuery;
import com.bazl.dna.database.service.model.vo.QcSamplePolluteRecordVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 质控污染记录 Mapper 接口
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
@Repository
public interface QcSamplePolluteRecordMapper extends BaseMapper<QcSamplePolluteRecord> {

    QcSamplePolluteRecord selectByPrimaryKey(Integer id);

    //分页 根据质控信息id 获取对应的质控污染记录信息 总计
    int selectListPagingCount(QcSamplePolluteRecordQuery query);
    //分页 根据质控信息id 获取对应的质控污染记录信息
    List<QcSamplePolluteRecordVo> selectListPaging(QcSamplePolluteRecordQuery query);

    //根据质控样本id和污染id查询信息
    QcSamplePolluteRecordVo selectBySampleIdAndId(QcSamplePolluteRecord query);
}
