package com.bazl.dna.database.service.mapper;

import com.bazl.dna.database.service.model.po.CriminalPersonInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bazl.dna.database.service.model.qo.CriminalPersonInfoQuery;
import com.bazl.dna.database.service.model.qo.CriminalReportQuery;
import com.bazl.dna.database.service.model.vo.CriminalPersonInfoVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 建库人员信息表（违法犯罪/前科人员） Mapper 接口
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
@Repository
public interface CriminalPersonInfoMapper extends BaseMapper<CriminalPersonInfo> {

    /**
     * 通用查询 - 建库人员信息分页结果
     * @param criminalPersonInfoQuery
     * @return
     */
    List<CriminalPersonInfoVo> paginationQuery(CriminalPersonInfoQuery criminalPersonInfoQuery);



    /**
     * 通用查询 - 建库人员信息总数
     * @param criminalPersonInfoQuery
     * @return
     */
    public int paginationCount(CriminalPersonInfoQuery criminalPersonInfoQuery);

    //通用主键id查询信息
    CriminalPersonInfoVo selectById(Integer id);

    /**
     * 查询前科人员上报信息
     * @param query
     * @return
     */
    List<CriminalReportQuery> selectCriminalReportList(CriminalReportQuery query);
}
