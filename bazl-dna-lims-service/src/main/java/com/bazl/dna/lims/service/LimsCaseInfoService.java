package com.bazl.dna.lims.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bazl.dna.common.PageInfo;
import com.bazl.dna.lims.model.bo.DelegateDataModel;
import com.bazl.dna.lims.model.po.LimsCaseInfo;
import com.bazl.dna.lims.model.po.LoaUserInfo;
import com.bazl.dna.lims.model.vo.CaseEffectiveRatioVo;
import com.bazl.dna.lims.model.vo.CaseEvidenceDetectionRateVo;
import com.bazl.dna.lims.model.vo.CasePropertyStatsVo;
import com.bazl.dna.lims.model.vo.IdentifyBookVo;
import com.bazl.dna.lims.model.vo.LimsCaseInfoVo;

@Repository
public interface LimsCaseInfoService {

    LimsCaseInfo selectByCaseId(String caseId);
    
    List<LimsCaseInfo> selectByCaseXkNo(String caseXkNo, String acceptOrgId);

    /**
     * 查询案件数量
     */
    public int selectCountByCaseStatus(String status, String acceptOrgId);
    
    /**
     * 查询案件数量（是否补送）
     */
    public int selectCntByCaseStatus(String status, String appendFlag, String loginOrgId);

    /**
     * 根据当前登录人和状态查询案件数量
     * @param caseInfoVo
     * @return
     */
    public int selectCountByCaseStatusAndAcceptorId(LimsCaseInfoVo caseInfoVo);

    /**
     * 根据年份获取各个月份的案件数
     */
    public Map<String, Object> selectMonthCountByYear(String year);

    //查询与补送查询List
    public List<LimsCaseInfoVo> selectCaseInfoList(LimsCaseInfoVo query, PageInfo pageInfo);
    //导出查询与补送查询List
    List<LimsCaseInfoVo> selectVOPaginationExportList(LimsCaseInfoVo query);

    //查询与补送查询count
    public int selectVOCount(LimsCaseInfoVo query);

    //根据现勘编号查询现堪数据
    public Map<String, Object> findCaseSampleInfoByXkNo(String xkNo);

    //查询补送记录
    public List<LimsCaseInfoVo> selectReplacementRecord(LimsCaseInfoVo limsCaseInfoVo);

    public LimsCaseInfo selectByConsignmentId(String consignmentId);

    //确认受理
    public boolean submitAcceptCase(DelegateDataModel delegateDataModel, LoaUserInfo operateUser, String personIds, String sampleIdWzs, String sampleIds);

    /**
     * 查询案件数量根据案件性质分组
     */
    public List<HashMap<String, String>> selectCountGroupProperty(String acceptOrgId);

    //修改案件
    public boolean updateCase(DelegateDataModel delegateDataModel, LoaUserInfo operateUser, String personIds, String sampleIdWzs, String sampleIds);

    /**
     * 根据条件查询案件信息
     * @param limsCaseInfoVo
     * @return
     */
    public List<LimsCaseInfoVo> selectVOCaseInfoList(LimsCaseInfoVo limsCaseInfoVo);

    /**
     * 查询本案比对
     * @param query
     * @param pageInfo
     * @return
     */
    public List<LimsCaseInfoVo> selectCaseCompare(LimsCaseInfoVo query, PageInfo pageInfo);

    /**
     * 查询本案比对count
     * @param query
     * @return
     */
    public int selectCaseCompareCount(LimsCaseInfoVo query);

    public LimsCaseInfoVo selectByCaseIdAndConsignmentId(String caseId, String consignmentId);

    int getExamine(Map<String, Object> map);

    /**
     * 查询入国家库数据
     * @param query
     * @param pageInfo
     * @return
     */
    List<LimsCaseInfoVo> selectFindstate(LimsCaseInfoVo query, PageInfo pageInfo);

    /**
     *  查询入国家库数量
     * @param query
     * @return
     */
    int selectStateCount(LimsCaseInfoVo query);

    /**
     * 查询已受理案件信息
     * @return
     */
    List<LimsCaseInfoVo> selectCaseAll();

    /**
     * 查询已委托案件信息
     * @return
     */
    List<LimsCaseInfoVo> selectCaseEntrust();


    /**
     * 查询案件检出基因型
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
     * 根据案件编号获取信息
     * @param caseNo
     * @return
     */
    public LimsCaseInfo selectByCaseNo(String caseNo);

    /**
     * 混合库获取案件信息
     * @param page
     * @return
     */
    public List<LimsCaseInfo> selectCaseInfoListData(LimsCaseInfoVo query,int page);
    
    /**
     * 案件性质统计受理案件数量
     * @return
     */
    List<CasePropertyStatsVo> caseStatsByCaseProperty(Map<String, Object> queryParams);
    
    /**
     * 二级案件性质统计受理案件数量
     * @return
     */
    List<CasePropertyStatsVo> caseStatsByCaseSubProperty(Map<String, Object> queryParams);

    /**
     * 二级案件性质统计受理案件数量
     * @return
     */
    List<CasePropertyStatsVo> caseStatsByCaseSubPropertyAndOrg(Map<String, Object> queryParams);
    
    /**
     * 查询各分局案件数
     * @return
     */
    List<CasePropertyStatsVo> selectCaseStatsByOrg(Map<String, Object> queryParams);
    
    /**
     * 统计侵财类案件数量
     * @param queryParams
     * @return
     */
    public List<CasePropertyStatsVo> selectAssetsCaseCount(Map<String, Object> queryParams);

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
    
    //通用查询分页list
    public List<LimsCaseInfoVo> selectCaseQueryInfoList(LimsCaseInfoVo query, PageInfo pageInfo);
    
    //通用查询count
    public int selectCaseQueryVOCount(LimsCaseInfoVo query);
    
    /**
     * 根据登录编码查询已出鉴定数数量
     * @param loginOrgId
     * @return
     */
    public int selectCntByAppraisalBook(String loginOrgId);
    
    /**
     * 查询案件总数根据机构编码
     * @param loginOrgId
     * @return
     */
    public int selectCountByOrgCode(String loginOrgId);
    
    /**
     * 根据年份获取各个月份的案件数
     */
    public Map<String, Object> selectMonthCountByYear(String year, String delegateOrgCode);
    
    //添加委托补送信息
    public Map<String, String> submitReplacement(DelegateDataModel delegateDataModel, LoaUserInfo operateUser,String evaluationCenterId);

    //添加非案件委托补送信息
    public Map<String, String> submitNonCaseReplacement(DelegateDataModel delegateDataModel, LoaUserInfo operateUser,String evaluationCenterId);

}