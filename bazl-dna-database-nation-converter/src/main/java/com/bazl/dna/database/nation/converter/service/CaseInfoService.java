package com.bazl.dna.database.nation.converter.service;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.bazl.dna.database.nation.converter.model.po.CaseInfo;
import com.bazl.dna.database.nation.converter.model.po.Consignment;

@Repository
public interface CaseInfoService {

    /**
     * 根据委托类别查询委托列表
     * @param category 委托类型
     * @param pageIndex 页码
     * @param pageSize 每页显示条数
     * @return
     */
    List<Consignment> selectListByCategory(String category, String initServerNoLike, int pageIndex, int pageSize);



    /**
     * 获取案件委托及相关联信息
     */
    void selectCaseInfoData(String labServerNoPrefix);

    /**
     * 混合库获取案件信息
     */
   void selectCaseInfoListData(Integer pageNo,Integer pageSize,String initServerNoLike);

   void selectInfoListData( Integer page,Integer pagesize,String initServerNoIn);

   void selectOrgInfoDataBase(Integer page,String initServerNoLike);

   void reagentDataBase();


    /**
     * 根据案件编号获取信息
     * @param caseNo
     * @return
     */
    public CaseInfo selectByCaseNo(String caseNo);
    //根据案件id查询案件信息
    CaseInfo selectByCaseId(String caseId);
    //根据样本id获取
    CaseInfo selectBySampleId(String sampleId);
}
