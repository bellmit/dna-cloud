package com.bazl.dna.database.service.mapper;

import com.bazl.dna.database.service.model.po.CasePersonInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bazl.dna.database.service.model.vo.CasePersonInfoVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 案件人员信息表 Mapper 接口
 * </p>
 *
 * @author lizhihua
 * @since 2020-02-11
 */
@Repository
public interface CasePersonInfoMapper extends BaseMapper<CasePersonInfo> {

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

    CasePersonInfo selectLabServerNo(String labServerNo);
}
