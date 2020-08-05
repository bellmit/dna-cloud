package com.bazl.dna.lims.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bazl.dna.lims.model.po.LimsCaseInfo;
import com.bazl.dna.lims.model.vo.CaseEffectiveRatioVo;
import com.bazl.dna.lims.model.vo.CaseEvidenceDetectionRateVo;
import com.bazl.dna.lims.model.vo.CasePropertyStatsVo;
import com.bazl.dna.lims.model.vo.IdentifyBookVo;
import com.bazl.dna.lims.model.vo.LimsCaseInfoVo;

@Repository
public interface LimsCaseInfoMapper {

    void insertCaseInfo(LimsCaseInfo caseInfo);

    void deleteCaseInfo(LimsCaseInfo caseInfo);

    LimsCaseInfo selectByCaseId(String caseId);
    
    List<LimsCaseInfo> selectByCaseXkNo(@Param("caseXkNo")String caseXkNo, @Param("acceptOrgId")String acceptOrgId);

    int selectCountByCaseStatus(@Param("status")String status, @Param("acceptOrgId")String acceptOrgId);

    /**
     * 根据当前登录人和状态查询案件数量
     * @param caseInfoVo
     * @return
     */
    public int selectCountByCaseStatusAndAcceptorId(LimsCaseInfoVo caseInfoVo);

    Map<String, Object> selectMonthCountByYear(@Param("year")String year);

    void updateCaseInfoDna(LimsCaseInfo limsCaseInfo);

    List<LimsCaseInfoVo> selectVOPaginationList(LimsCaseInfoVo consignationInfoVo);
    /**
     * 导出查询与补送查询List
     */
    List<LimsCaseInfoVo> selectVOPaginationExportList(LimsCaseInfoVo query);


    int selectVOCount(LimsCaseInfoVo consignationInfoVo);

    void updateHasAppendFlagByCaseId(LimsCaseInfo limsCaseInfo);

    List<LimsCaseInfoVo> selectReplacementRecord(LimsCaseInfoVo limsCaseInfoVo);

    public LimsCaseInfo selectByConsignmentId(String consignmentId);

    public List<HashMap<String, String>> selectCountGroupProperty(@Param("acceptOrgId")String acceptOrgId);

    /**
     * 根据条件查询案件信息
     * @param limsCaseInfoVo
     * @return
     */
    public List<LimsCaseInfoVo> selectVOCaseInfoList(LimsCaseInfoVo limsCaseInfoVo);

    /**
     * 查询本案比对
     * @param limsCaseInfoVo
     * @return
     */
    public List<LimsCaseInfoVo> selectCaseComparePaginationList(LimsCaseInfoVo limsCaseInfoVo);

    /**
     * 查询本案比对count
     * @param limsCaseInfoVo
     * @return
     */
    public int selectCaseCompareCount(LimsCaseInfoVo limsCaseInfoVo);

    public LimsCaseInfoVo selectByCaseIdAndConsignmentId(@Param("caseId")String caseId, @Param("consignmentId")String consignmentId);

    int getExamine(Map<String, Object> map);

    /**
     * 查询入国家库
     * @param query
     * @return
     */
    List<LimsCaseInfoVo> selectFindstate(LimsCaseInfoVo query);

    /**
     * 查询国家库数据数量
     * @param query
     * @return
     */
    int selectStateCount(LimsCaseInfoVo query);

    List<LimsCaseInfoVo> selectCaseAll();

    List<LimsCaseInfoVo> selectCaseEntrust();

    /**
     * 查询检出基因型的数据
     * @return
     */
    List<LimsCaseInfoVo> selectCaseMatch();

    /*
    *   案件统计
    * */
    int selectFirsInsCount(LimsCaseInfoVo query);
    /*
    *   案件入库统计
    * */
    int selectInstoredCount(LimsCaseInfoVo query);

    /**
     * 根据案件编号查询案件信息
     * @param caseNo
     * @return
     */
    public List<LimsCaseInfo> selectByCaseNo(String caseNo);

    /**
     * 混合库获取案件信息
     * @return
     */
    public List<LimsCaseInfo> selectCaseInfoListData(LimsCaseInfoVo query);

    /**
     * 查询已入库的所有案件
     */

    List<LimsCaseInfo> selectAllCase();
    
    /**
     * 案件性质统计受理案件数量
     * @return
     */
    public List<CasePropertyStatsVo> caseStatsByCaseProperty(Map<String, Object> queryParams);
    
    /**
     * 查询各分局案件数
     * @return
     */
    List<CasePropertyStatsVo> selectCaseStatsByOrg(Map<String, Object> queryParams);
    
    /**
     * 二级案件性质统计受理案件数量
     * @return
     */
    public List<CasePropertyStatsVo> caseStatsByCaseSubProperty(Map<String, Object> queryParams);



    /**
     * 二级案件性质统计受理各分局案件数量
     * @return
     */
    public List<CasePropertyStatsVo> caseStatsByCaseSubPropertyAndOrg(Map<String, Object> queryParams);
    
    /**
     * 统计案件物证检出率
     * @param queryParams
     * @return
     */
    public List<CaseEvidenceDetectionRateVo> selectEvidenceDetectionRate(Map<String, Object> queryParams);

    /**
     * 根据条件查询有效比中率
     * @param queryParams
     * @return
     */
    public List<CaseEffectiveRatioVo> selectCaseEffectiveRatio(Map<String, Object> queryParams);

    /**
     * 统计侵财类案件数量
     * @param queryParams
     * @return
     */
    public List<CasePropertyStatsVo> selectAssetsCaseCount(Map<String, Object> queryParams);
    

    /**
     * 鉴定书统计
     * @param queryParams
     * @return
     */
    List<IdentifyBookVo> selectIdentifyBookStatistics(Map<String, Object> queryParams);


    /**
     * 根据单位code查询已签发鉴定书数量
     * @param queryParams
     * @return
     */
    int selectIssuedCount(Map<String, Object> queryParams);

    /**
     * 根据单位code查询已签发鉴定书总数量
     * @param queryParams
     * @return
     */
    int selectTotalIssuedCount(Map<String, Object> queryParams);
    
    List<LimsCaseInfoVo> selectCaseQueryInfoList(LimsCaseInfoVo caseInfoVo);
    
    int selectCaseQueryVOCount(LimsCaseInfoVo consignationInfoVo);
    
    /**
     * 查询案件总数根据案件状态(是否补送)
     * @param status
     * @param appendFlag
     * @param loginOrgId
     * @return
     */
    public int selectCntByCaseStatus(@Param("status") String status, @Param("appendFlag") String appendFlag,@Param("loginOrgId") String loginOrgId);
    
    /**
     * 根据委托单位信息查询已出鉴定书委托数目
     * @param loginOrgId
     * @return
     */
    public int selectCntByAppraisalBook(@Param("loginOrgId") String loginOrgId);
    
    /**
     * 根据机构编码查询案件总数
     * @param loginOrgId
     * @return
     */
    public int selectCountByOrgCode(@Param("loginOrgId") String loginOrgId);
    
    Map<String, Object> selectMonthCountByYear(@Param("year")String year, @Param("delegateOrgCode")String delegateOrgCode);
    
}