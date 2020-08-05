package com.bazl.dna.lims.dao;

import com.bazl.dna.lims.model.po.Examiner;

import java.util.List;

/**
 * @author wanghaiyang
 * @date 2019/3/11.
 */
public interface ExaminerMapper {

    /**
     * 插入信息
     * @param examiner
     * @return
     */
    public int insert(Examiner examiner);

    List<Examiner> queryExaminer(String caseId);

    /**
     * 更新信息
     * @param examiner
     * @return
     */
    public int update(Examiner examiner);

    /**
     * 根据案件id查询信息
     * @param caseId
     * @return
     */
    public List<Examiner> selectListByCaseId(String caseId);

}