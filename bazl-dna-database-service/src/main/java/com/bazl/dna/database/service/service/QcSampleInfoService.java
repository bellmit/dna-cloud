package com.bazl.dna.database.service.service;


import java.util.List;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bazl.dna.database.service.model.po.CurrencyQcResult;
import com.bazl.dna.database.service.model.po.QcSampleInfo;
import com.bazl.dna.database.service.model.qo.QcSampleInfoQuery;
import com.bazl.dna.database.service.model.vo.QcSampleInfoVo;

/**
 * <p>
 * 质控样本信息表 服务类
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
public interface QcSampleInfoService extends IService<QcSampleInfo> {

    List<CurrencyQcResult> currencyCriminalQuery(CurrencyQcResult currencyQcResult);
    //分页 质控查询结果总数
    int selectListPagingCount(QcSampleInfoQuery query);
    //分页 质控列表条件查询
    List<QcSampleInfoVo> selectListPaging(QcSampleInfoQuery query);
    //根据id查询
    QcSampleInfo selectById(Integer id);
    //新增
    Boolean insertAdd(QcSampleInfo qcSampleInfo);
    //根据id修改
    Boolean updateByIdOverride(QcSampleInfo qcSampleInfo);
    //根据id删除
    Boolean deleteByIdOverride(Integer id);
}
