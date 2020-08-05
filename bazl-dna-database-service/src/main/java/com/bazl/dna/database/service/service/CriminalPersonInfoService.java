package com.bazl.dna.database.service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bazl.dna.database.service.model.po.CriminalPersonInfo;
import com.bazl.dna.database.service.model.qo.CriminalPersonInfoQuery;
import com.bazl.dna.database.service.model.qo.CriminalReportQuery;
import com.bazl.dna.database.service.model.vo.CriminalPersonInfoVo;

import java.util.List;

/**
 * <p>
 * 建库人员信息表（违法犯罪/前科人员） 服务类
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
public interface CriminalPersonInfoService extends IService<CriminalPersonInfo> {

    /**
     * 根据人员id查询详细信息
     * @param id
     * @return
     */
    public CriminalPersonInfoVo selectVoById(Integer id);

    /**
     * 通用查询 - 建库人员信息分页结果
     * @param criminalPersonInfoQuery
     * @return
     */
    List<CriminalPersonInfoVo> criminalPaginationQuery(CriminalPersonInfoQuery criminalPersonInfoQuery);


    /**
     * 通用查询 - 建库人员信息总数
     * @param criminalPersonInfoQuery
     * @return
     */
    int criminalPaginationCount(CriminalPersonInfoQuery criminalPersonInfoQuery);

    /**
     * 查询建库上报人员信息
     * @param query
     * @return
     */
    List<CriminalReportQuery> selectCriminalReportList(CriminalReportQuery query);
}
