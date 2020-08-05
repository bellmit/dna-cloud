package com.bazl.dna.mix.service;

import java.util.ArrayList;
import java.util.List;

import com.bazl.dna.common.PageInfo;
import com.bazl.dna.mix.model.po.MixedSampleGene;
import com.bazl.dna.mix.model.vo.CaseMixedSampleGeneVo;
import com.bazl.dna.mix.model.vo.MatchResultDetailsListVo;
import com.bazl.dna.mix.model.vo.MixMatchedSupectVo;
import com.bazl.dna.mix.model.vo.MixedSampleGenePagingVo;
import com.bazl.dna.mix.model.vo.MixedSampleGeneVo;
import com.bazl.dna.mix.model.vo.MixedTypingVo;

/**
 * @Author: lzn
 * @Date: 2019/7/5 16:14
 * @Version: 1.0
 */
public interface MixedSampleGeneService {

    int deleteByPrimaryKey(String id);

    /**
     * 查询混合样本总数
     * @return
     */
    public int selectMixedSampleGeneCount();

    /**
     * 首页查询混合基因串并案比中嫌疑人列表
     * @param lastestCount
     * @return
     */
    List<MixMatchedSupectVo> selectDifferentCaseMatchedSuspectList(Integer lastestCount, String userId);

    /**
     * 首页查询混合基因串并案比中嫌疑人总数
     * @return
     */
    int selectSerialCaseMixedSampleCount();

    /**
     * 	首页查询更多混合基因串并案比中嫌疑人列表
     * @return
     * @param query
     * @param pageInfo
     */
     List<MixMatchedSupectVo> selectMoreSerialCaseMixedSampleList(MixedSampleGeneVo query, PageInfo pageInfo);


    /**
     * 根据id查询混合审核通过基因型信息
     * @param id
     * @return
     */
    public MixedSampleGene selectById(String id);

    /**
     * 修改混合审核通过基因型信息
     * @param mixedSampleGene
     */
    public void updateMixedSampleGene(MixedSampleGene mixedSampleGene);

    /**
     * 添加混合审核通过基因型信息
     * @param mixedSampleGene
     */
    public void insert(MixedSampleGene mixedSampleGene);


    /**
     * 根据案件id查询混合样本
     * @param caseId
     * @return
     */
    List<MixedSampleGeneVo> selectMixedSampleGeneList(String caseId);

    /**
     * 查询混合样本信息
     * @param query
     * @param pageInfo
     * @return
     */
   List<CaseMixedSampleGeneVo> selectMixedDateBaseGeneList(CaseMixedSampleGeneVo query, PageInfo pageInfo);

    /**
     * 查询混合样本信息
     * @param id
     * @return
     */
    List<MixedSampleGeneVo> selectMixedSampleGeneVoList(String id);

    /**
     * 根据混合id查询混合样本编号
     * @param sampleGeneId
     * @return
     */
    List<MixedSampleGene> selectMixedSampleSuspect(String sampleGeneId);

    /**
     * 查看混合样本基因分型及图谱
     * @param geneID
     * @return
     */
    List<MixedSampleGene> selectMixedSampleSuspectsDetails(String geneID);

    /**
     * 混合样本数据库管理：根据基因id删除案件混合样本
     * @param geneId
     * @return
     */
    int deleteMixedDateBaseGene(String geneId);

    /**
     * 混合样本数据库管理
     * @param query
     * @return
     */
    int selectMixedDateBaseGeneCount(CaseMixedSampleGeneVo query);

    /**
     * 根据混合样本id查询混合样本信息
     * @param sampleId
     * @return
     */
    List<MixedSampleGene> selectMixedSampleGeneBySampleId(String sampleId);

    /**
     * 批量删除
     * @param geneIds
     * @return
     */
     int batchDeleteMixedDateGene(String geneIds);

    /**
     * 首页查询更多串并案比中的混合样本条数
     * @param query
     * @return
     */
    int selectDifferentSuspectCountByQuery(MixedSampleGeneVo query);

    /**
     * 首页查询混合样本比中嫌疑人的比中列表
     * @param lastestCount
     * @return
     */
    List<MixMatchedSupectVo> selectMixMatchedSupectList(Integer lastestCount, String userId);

    /**
     * 首页查询混合样本比中嫌疑人的总数
     * @return
     */
    int selectMatchedSuspectCount();

    /**
     * 首页查询更多混合样本比中嫌疑人的比中列表
     * @param query
     * @param pageInfo
     * @return
     */
    List<MixMatchedSupectVo> selectMoreMatchedSuspectList(MixedSampleGeneVo query, PageInfo pageInfo);

    /**
     * 首页查询更多混合样本比中嫌疑人的比中条数
     * @param query
     * @return
     */
    int selectMatchedSuspectCountByQuery(MixedSampleGeneVo query);

    /**
     *首页查询最新的混合样本比中质控人员样本列表
     * @param lastestCount
     * @return
     */
    List<MixMatchedSupectVo> selectLatestQualityMixedSampleList(Integer lastestCount, String userId);

    /**
     * 首页查询混合样本比中质控人员的总数
     * @return
     */
    int selectMixedSampleQualityPersonnelCount();

    /**
     * 首页查询更多混合样本比中质控人员样本列表
     * @return
     * @param query
     * @param pageInfo
     */
    List<MixMatchedSupectVo> selectMoreQualityMixedSampleList(MixedSampleGeneVo query, PageInfo pageInfo);

    /**
     * 查询更多混合样本比中质控人员样本条数
     * @param query
     * @return
     */
    int selectMixedQualityPersonnelCount(MixedSampleGeneVo query);

    /**
     * 根据检材id查询混合信息
     * @param sampleId
     * @return
     */
    public MixedSampleGene selectBySampleId(String sampleId);
    
    /**
     * 首页查询全库混合样本条数
     * @return
     */
    int selectMixCount(String createPerson);

    //首页列表查询
    ArrayList<MixedSampleGenePagingVo> selectListPaging(MixedTypingVo mixedTypingVo);
    //首页列表查询 分页总数
    int selectListPagingCount(MixedTypingVo mixedTypingVo);
    //根据id删除
    Boolean deleteById(String id);
    //根据ids删除
    Boolean deleteByIds(String ids);
    //根据id获取单条
    MixedSampleGenePagingVo selectPagingVoById(String id);
    //比中详情列表
    MatchResultDetailsListVo selectMatchResultList(MixedTypingVo mixedTypingVo);
    //修改基因图谱
    Boolean updateGenePictureById(MixedSampleGene mixedSampleGene);
    //根据id删除图片
    Boolean deleteGenePictureById(String id);
}
