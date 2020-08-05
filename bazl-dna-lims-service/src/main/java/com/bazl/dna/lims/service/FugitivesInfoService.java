package com.bazl.dna.lims.service;


import java.util.List;

import com.bazl.dna.common.PageInfo;
import com.bazl.dna.lims.model.po.FugitivesInfo;
import com.bazl.dna.lims.model.vo.FugitivesInfoVo;

/**
 * @author huawei
 * @date 2020/6/15.
 */
public interface FugitivesInfoService {

    /**
     * 根据id删除信息
     * @param id
     * @return
     */
    public int deleteByPrimaryKey(String id);

    /**
     * 插入信息
     * @param record
     * @return
     */
    public int insert(FugitivesInfo record);

    /**
     * 根据id查询信息
     * @param id
     * @return
     */
    public FugitivesInfo selectByPrimaryKey(String id);

    /**
     * 查询所有信息
     * @return
     */
    public List<FugitivesInfo> selectAll();

    /**
     * 更新信息
     * @return
     */
    public int updateByPrimaryKey(FugitivesInfo record);

    /**
     * 分页查询信息
     * @param query
     * @param pageInfo
     * @return
     */
    public List<FugitivesInfoVo> selectVOList(FugitivesInfoVo query, PageInfo pageInfo);

    /**
     * 根据条件查询总数
     * @param query
     * @return
     */
    public int selectVOCnt(FugitivesInfoVo query);

    /**
     * 根据条件删除在逃人员信息
     * @param fugitivesInfo
     */
    public void deleteFugitivesInfo(FugitivesInfo fugitivesInfo);

    /**
     * 根据条件查询在逃人员信息
     * @param searchFugitives
     * @return
     */
    public List<FugitivesInfoVo> selectFugitivesList(String searchFugitives);

    /**
     * 根据条件查询在逃人员信息
     * @param fugitivesInfoVo
     * @return
     */
    public List<FugitivesInfoVo> selectListVO(FugitivesInfoVo fugitivesInfoVo);
    
    /**
     * 根据机构ID查询在逃人员信息
     * @param orgId
     * @return
     */
    public int selectCountByOrgId(String orgId);
    
    /**
     * 根据机构编码查询在逃人员亲属总数
     * @param delegateOrgCode
     * @return
     */
    public int selectRelationPersonCnt(String delegateOrgCode);
    
    /**
     * 根据委托id查询在逃人员信息
     * @param consignmentId
     * @return
     */
    public List<FugitivesInfo> selectListByConsignmentId(String consignmentId);
    
    /**
     * 根据人员姓名和身份证号查询在逃人员信息
     * @param personName
     * @param personCard
     * @return
     */
    public List<FugitivesInfo> selectInfoByPersonNameAndCard(String personName, String personCard);
    
    /**
     * 送检委托数量
     * @param status
     * @param appendFlag
     * @param loginOrgId
     * @return
     */
    public int selectCountByCaseStatus(String status, String appendFlag, String loginOrgId);
    
    /**
     * 根据机构编码查询在逃人员出鉴定书数量
     * @param loginOrgId
     * @return
     */
    public int selectCntByAppraisalBook(String loginOrgId);
    
    /**
     * 批量插入在逃人员信息
     * @param fugitivesInfoList
     */
    public void insertBatchFugitives(List<FugitivesInfo> fugitivesInfoList);
}
