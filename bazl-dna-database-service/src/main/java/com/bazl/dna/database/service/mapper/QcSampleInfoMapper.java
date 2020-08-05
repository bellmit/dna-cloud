package com.bazl.dna.database.service.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bazl.dna.database.service.model.po.CurrencyQcResult;
import com.bazl.dna.database.service.model.po.QcSampleInfo;
import com.bazl.dna.database.service.model.qo.QcSampleInfoQuery;
import com.bazl.dna.database.service.model.vo.QcSampleInfoVo;

/**
 * <p>
 * 质控样本信息表 Mapper 接口
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
@Repository
public interface QcSampleInfoMapper extends BaseMapper<QcSampleInfo> {

    List<CurrencyQcResult> currencyCriminalQuery(CurrencyQcResult qcResult);
    //分页 质控查询结果总数
    int selectListPagingCount(QcSampleInfoQuery query);
    //分页 质控列表条件查询
    List<QcSampleInfoVo> selectListPaging(QcSampleInfoQuery query);
    //根据id查询
    QcSampleInfo selectById(Integer qcSampleId);
    //根据id修改
    int updateByIdOverride(QcSampleInfo qcSampleInfo);
    //根据id删除
    int deleteByIdOverride(Integer qcSampleId);
    //新增
    int insertAdd(QcSampleInfo qcSampleInfo);
}
