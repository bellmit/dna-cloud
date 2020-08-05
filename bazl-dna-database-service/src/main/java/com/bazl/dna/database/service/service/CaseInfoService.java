package com.bazl.dna.database.service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bazl.dna.database.service.model.bo.CaseInstoreModel;
import com.bazl.dna.database.service.model.po.CaseInfo;
import com.bazl.dna.database.service.model.qo.CaseInfoQuery;
import com.bazl.dna.database.service.model.qo.CaseReportQuery;
import com.bazl.dna.database.service.model.vo.CaseInfoVo;

import java.util.List;

/**
 * <p>
 * 案件信息表 服务类
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
public interface CaseInfoService extends IService<CaseInfo> {

    /**
     * 根据案件id获取案件基本信息
     */
    CaseInfo selectByPrimaryKey(Integer id);

    /**
     * 根据案件id获取案件详细信息
     */
    CaseInfoVo selectByIdDetail(Integer id);

    /**
     * 保存入本地库的案件和样本信息
     * @param caseInstoreModel
     * @return
     */
    public boolean saveInstoreCaseAndSamples(CaseInstoreModel caseInstoreModel) throws Exception;

    public boolean  insertWarehousingNum(CaseInfo caseInfo);

    /**
     * 通用查询 - 案件信息分页结果
     * @param caseInfoQuery
     * @return
     */
    List<CaseInfoVo> casePaginationQuery(CaseInfoQuery caseInfoQuery);


    /**
     * 通用查询 - 案件信息总数
     * @param caseInfoQuery
     * @return
     */
    int casePaginationCount(CaseInfoQuery caseInfoQuery);

    /**
     * 查询案件入国家库状态
     * @param transferNationFlag
     * @return
     */
    List<CaseInfo> selectByCaseTransferFlag(int transferNationFlag);

    /**
     * 查询实验室编号获取案件信息
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
     * 查询总计案件上报信息
     * @param query
     * @return
     */
    int caseReportpaginationCount(CaseReportQuery query);
}
