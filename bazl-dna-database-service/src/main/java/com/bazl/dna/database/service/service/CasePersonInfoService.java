package com.bazl.dna.database.service.service;

import com.bazl.dna.database.service.model.po.CasePersonInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bazl.dna.database.service.model.vo.CasePersonInfoVo;

import java.util.List;

/**
 * <p>
 * 案件人员信息表 服务类
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
public interface CasePersonInfoService extends IService<CasePersonInfo> {

    List<CasePersonInfo> queryCasePerson(CasePersonInfo casePersonInfo);

    CasePersonInfo selectVoListById(Integer id);

    /**
     * 根据案件id查询所有的案件人员信息
     * @param caseId
     * @return
     */
    public List<CasePersonInfoVo> selectVoListByCaseId(Integer caseId);

    /**
     * 查询未上报的案件人员信息
      * @param transferFlag
     * @return
     */
    List<CasePersonInfo> selectByPersonTransferFlag(int transferFlag);

    /**
     * 查询案件人员信息根据实验室编号
     * @param labServerNo
     * @return
     */
    List<CasePersonInfo> selectByLabServerNo(String labServerNo);

    /**
     * 查询全部案件人员数
      * @return
     */
    public int selectAllCasePersonCount();

    /**
     * 查询根据实验室编号
      * @param labServerNo
     * @return
     */
    CasePersonInfo selectLabServerNo(String labServerNo);
}
