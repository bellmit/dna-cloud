package com.bazl.dna.database.service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bazl.dna.database.service.model.po.CaseInfo;
import com.bazl.dna.database.service.model.qo.CaseInfoQuery;
import com.bazl.dna.database.service.model.qo.CaseReportQuery;
import com.bazl.dna.database.service.model.vo.CaseInfoVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 案件信息表 Mapper 接口
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
@Repository
public interface CaseInfoMapper extends BaseMapper<CaseInfo> {

    //根据案件id获取案件基本信息
    CaseInfo selectByPrimaryKey(Integer id);

    //根据案件id获取案件详细信息
    CaseInfoVo selectByIdDetail(Integer id);

    /**
     * 分页查询案件信息
     * @param caseInfoQuery
     * @return
     */
    List<CaseInfoVo> paginationQuery(CaseInfoQuery caseInfoQuery);


    /**
     * 分页查询案件信息 总数
     * @param caseInfoQuery
     * @return
     */
    int paginationCount(CaseInfoQuery caseInfoQuery);

    /**
     * 查询案件入国家库状态
     * @param transferNationFlag
     * @return
     */
    List<CaseInfo> selectByCaseTransferFlag(int transferNationFlag);

    /**
     * 根据实验室编号查询案件信息
     * @param labServerNo
     * @return
     */
    List<CaseInfo> selectCaseByLabServerNo(String labServerNo);

    /**
     * 查询全部案件信息
     * @return
     */
     public int selectAllCaseCount();

     public int selectAcceptNoCount(String id);

    /**
     * 查询案件上报信息
     * @param query
     * @return
     */
    List<CaseReportQuery> selectCaseReportList(CaseReportQuery query);

    /**
     * 查询案件上报总计条数
     * @param query
     * @return
     */
    int caseReportpaginationCount(CaseReportQuery query);
}
