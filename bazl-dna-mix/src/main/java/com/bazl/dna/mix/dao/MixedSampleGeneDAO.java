package com.bazl.dna.mix.dao;

import com.bazl.dna.mix.model.po.MixedSampleGene;
import com.bazl.dna.mix.model.vo.CaseMixedSampleGeneVo;
import com.bazl.dna.mix.model.vo.MixedSampleGenePagingVo;
import com.bazl.dna.mix.model.vo.MixedSampleGeneVo;
import com.bazl.dna.mix.model.vo.MixedTypingVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface MixedSampleGeneDAO {

    int deleteByPrimaryKey(String id);

    int insert(MixedSampleGene record);

    int insertSelective(MixedSampleGene record);


    /**
     * 首页查询混合样本比中嫌疑人的总数
     * @return
     */
    int selectMatchedSuspectCount();

    /**
     * 查询混合样本总数
     * @return
     */
    int selectMixedSampleGeneCount();

    /**
     * 根据id查询混合审核通过基因型信息
     * @param id
     * @return
     */
    MixedSampleGene selectById(String id);

    /**
     * 修改混合审核通过基因型信息
     * @param mixedSampleGene
     */
    void updateMixedSampleGene(MixedSampleGene mixedSampleGene);

    /**
     *根据案件id查询混合样本库信息
     * @param caseId
     * @return
     */
    List<MixedSampleGeneVo> selectMixedSampleGeneList(String caseId);
    /**
     * 查询案件混合样本库信息
     * @return
     */
    List<CaseMixedSampleGeneVo> selectMixedDateBaseGeneList(CaseMixedSampleGeneVo query);

    /**
     * 查询混合样本信息
     * @param id
     * @return
     */
    List<MixedSampleGeneVo> selectMixedSampleGeneVoList(String id);

    /**
     * 根据混合id查询混合样本
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
     * 查询混合条数
     * @param query
     * @return
     */
    int selectMixedDateBaseGeneCount(CaseMixedSampleGeneVo query);

    /**
     * 根据样本id查询混合样本数据
     * @param sampleId
     * @return
     */
    List<MixedSampleGene> selectMixedSampleGeneBySampleId(String sampleId);

    /**
     * 首页查询更多混合样本比中嫌疑人的比中列表
     * @param query
     * @return
     */
    List<MixedSampleGeneVo> selectMoreMixedSampleGeneVoList(MixedSampleGeneVo query);

    /**
     * 首页查询最新的混合样本比中嫌疑人的比中列表
     * @param lastestCount
     * @return
     */
    List<MixedSampleGeneVo> selectALLMixMatchedVoList(@Param("lastestCount") Integer lastestCount, @Param("userId") String userId);

    /**
     * 首页查询混合样本比中质控人员样本列表
     * @param lastestCount
     * @return
     */
    List<MixedSampleGeneVo> selectALLMixMatchedVoQualtyList(@Param("lastestCount") Integer lastestCount, @Param("userId") String userId);

    /**
     * 首页查询更多混合样本比中质控人员样本列表
     * @param query
     * @return
     */
    List<MixedSampleGeneVo> selectMoreQualityGeneVoList(MixedSampleGeneVo query);

    /**
     * 首页查询更多混合样本比中嫌疑人的比中条数
     * @param query
     * @return
     */
    int selectMatchedSuspectCountByQuery(MixedSampleGeneVo query);

    /**
     * 查询更多混合样本比中质控人员样本条数
     * @param query
     * @return
     */
    int selectMixedQualityPersonnelCount(MixedSampleGeneVo query);

    /**
     * 首页查询混合样本比中质控人员的总数
     * @return
     */
    int selectMoreQualityGeneCount();

    /**
     * 首页查询混合基因串并案比中嫌疑人列表
     * @param lastestCount
     * @return
     */
    List<MixedSampleGeneVo> selectDifferentCaseMatchedSuspectList(@Param("lastestCount") Integer lastestCount, @Param("userId") String userId);

    /**
     * 首页查询更多混合基因串并案比中嫌疑人列表
     * @param query
     * @return
     */
    List<MixedSampleGeneVo> selectMoreDifferentGeneVoList(MixedSampleGeneVo query);

    /**
     * 首页查询混合基因串并案比中嫌疑人总数
     * @return
     */
    int selectSerialCaseMixedSampleCount();

    /**
     * 首页查询更多串并案比中的混合样本条数
     * @param query
     * @return
     */
    int selectDifferentSuspectCountByQuery(MixedSampleGeneVo query);

    /**
     * 根据检材id查询混合信息
     * @param sampleId
     * @return
     */
    public MixedSampleGene selectBySampleId(String sampleId);

    /**
     * 首页查询全库混合样本条数
     */
    int selectMixCount(String createPerson);

    //首页列表查询
    ArrayList<MixedSampleGenePagingVo> selectListPaging(MixedTypingVo mixedTypingVo);
    //分页总计
    int selectListPagingCount(MixedTypingVo mixedTypingVo);
    //根据id删除
    int deleteById(String id);
    //批量删除
    void deleteByIds(List<String> stringList);
    //根据id获取单条
    MixedSampleGenePagingVo selectPagingVoById(String id);
    //修改基因图谱
    int updateGenePictureById(MixedSampleGene mixedSampleGene);
    //根据id删除
    int deleteGenePictureById(String id);
}