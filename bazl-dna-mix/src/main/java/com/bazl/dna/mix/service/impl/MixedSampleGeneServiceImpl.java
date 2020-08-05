package com.bazl.dna.mix.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.common.PageInfo;
import com.bazl.dna.exception.DnaRuntimeException;
import com.bazl.dna.mix.client.CaseInfoServerClient;
import com.bazl.dna.mix.constants.Constants;
import com.bazl.dna.mix.dao.DictCountDAO;
import com.bazl.dna.mix.dao.MatchResultMapper;
import com.bazl.dna.mix.dao.MixedSampleGeneDAO;
import com.bazl.dna.mix.dao.SingleSampleGeneDAO;
import com.bazl.dna.mix.dao.SplitedSampleGeneDAO;
import com.bazl.dna.mix.model.po.CaseInfo;
import com.bazl.dna.mix.model.po.CompareQueue;
import com.bazl.dna.mix.model.po.DictCount;
import com.bazl.dna.mix.model.po.MatchResult;
import com.bazl.dna.mix.model.po.MixedSampleGene;
import com.bazl.dna.mix.model.vo.CaseMixedSampleGeneVo;
import com.bazl.dna.mix.model.vo.MatchResultDetailsListVo;
import com.bazl.dna.mix.model.vo.MatchResultVo;
import com.bazl.dna.mix.model.vo.MixMatchedSupectVo;
import com.bazl.dna.mix.model.vo.MixedSampleGenePagingVo;
import com.bazl.dna.mix.model.vo.MixedSampleGeneVo;
import com.bazl.dna.mix.model.vo.MixedTypingVo;
import com.bazl.dna.mix.service.CompareQueueService;
import com.bazl.dna.mix.service.MixedSampleGeneService;
import com.bazl.dna.mix.utils.InitializationData;
import com.bazl.dna.mix.utils.ListUtils;
import com.bazl.dna.mix.utils.MixMatchedSupectVoUtils;

/**
 * @Author: lzn
 * @Date: 2019/7/5 16:15
 * @Version: 1.0
 */
@Service
public class MixedSampleGeneServiceImpl extends MixBaseService implements MixedSampleGeneService {
    @Autowired
    MixedSampleGeneDAO mixedSampleGeneDAO;

    @Autowired
    SingleSampleGeneDAO singleSampleGeneDAO;

    @Autowired
    DictCountDAO dictCountDAO;

    @Autowired
    MatchResultMapper matchResultMapper;

    @Autowired
    CaseInfoServerClient caseInfoServerClient;
    @Autowired
    SplitedSampleGeneDAO splitedSampleGeneDAO;
    @Autowired
    CompareQueueService compareQueueService;


    @Override
    @Transactional
    public int deleteByPrimaryKey(String id) {
    	try {
    		return mixedSampleGeneDAO.deleteByPrimaryKey(id);
    	} catch (Exception e) {
            logger.error("Error deleteByPrimaryKey: ", e);
            throw new DnaRuntimeException();
        }
    }

    /**
     * 查询混合样本总数
     *
     * @return
     */
    @Override
    public int selectMixedSampleGeneCount() {
        int mixedSampleGeneCount = 0;
        try {
//            mixedSampleGeneCount = mixedSampleGeneDAO.selectMixedSampleGeneCount();

            DictCount  dictCount=dictCountDAO.selectMatchedSuspectCount(Constants.DICT_TYPE_CODE_MIXED_GENE_COUNT);

            //判空
            if(StringUtils.isNotBlank(dictCount.getDictCountNumber())){
                mixedSampleGeneCount=Integer.valueOf(dictCount.getDictCountNumber());
            }

        } catch (Exception ex) {
            logger.error("查询混合样本总数失败！！！", ex.getMessage());
        }
        return mixedSampleGeneCount;
    }

    /**
     * 首页查询混合样本比中嫌疑人的比中列表
     *
     * @param lastestCount
     * @return
     */
    @Override
    public List<MixMatchedSupectVo> selectMixMatchedSupectList(Integer lastestCount, String userId) {
        //初始化查询条件
        List<MixMatchedSupectVo> resultMixMatchedSupectVoList = new ArrayList<>();
        try {
            //首页查询混合样本比中嫌疑人的比中列表
            List<MixedSampleGeneVo> mixedSampleGeneVoList = mixedSampleGeneDAO.selectALLMixMatchedVoList(lastestCount,userId);
            if (ListUtils.isNotNullAndEmptyList(mixedSampleGeneVoList)) {
                for (MixedSampleGeneVo mixedSampleGeneVo : mixedSampleGeneVoList) {
                    MixMatchedSupectVo mixMatchedSupectVo = MixMatchedSupectVoUtils.mixMatchedSupectCommonVoS(mixedSampleGeneVo);
                    resultMixMatchedSupectVoList.add(mixMatchedSupectVo);

                }
            }
        } catch (Exception ex) {
            logger.error("首页查询混合样本比中嫌疑人的比中列表失败！！！", ex.getMessage());
        }
        return resultMixMatchedSupectVoList;
    }

    /**
     * 首页查询混合样本比中嫌疑人的总数
     *
     * @return
     */
    @Override
    public int selectMatchedSuspectCount() {
        int suspectsNumber = 0;
        try {

             DictCount  dictCount=dictCountDAO.selectMatchedSuspectCount(Constants.DICT_TYPE_CODE_SUSPECTS_NUMBER);

            //判空
            if(StringUtils.isNotBlank(dictCount.getDictCountNumber())){
                suspectsNumber=Integer.valueOf(dictCount.getDictCountNumber());
            }

        } catch (Exception ex) {
            logger.error("首页查询混合样本比中嫌疑人的总数失败！！！", ex.getMessage());
        }
        return suspectsNumber;
    }

    /**
     * 首页查询更多混合样本比中嫌疑人的比中列表
     *
     * @param query
     * @param pageInfo
     * @return
     */
    @Override
    public List<MixMatchedSupectVo> selectMoreMatchedSuspectList(MixedSampleGeneVo query, PageInfo pageInfo) {
        List<MixMatchedSupectVo> resultMixMatchedSupectVoList = new ArrayList<>();
        //初始化查询条件
        query = InitializationData.mixedSampleGeneVoVoQuery(query);
        int pageNo;
        int pageSize;
        try {
            pageNo = pageInfo.getCurPage();
            pageSize = pageInfo.getEvePageRecordCnt();
            query.setOffset((pageNo - 1) * pageSize);
            query.setRows(query.getOffset() + pageSize);

            //首页查询更多混合样本比中嫌疑人的比中列表
            List<MixedSampleGeneVo> mixedSampleGeneVoList = mixedSampleGeneDAO.selectMoreMixedSampleGeneVoList(query);
            if (ListUtils.isNotNullAndEmptyList(mixedSampleGeneVoList)) {
                for (MixedSampleGeneVo mixedSampleGeneVo : mixedSampleGeneVoList) {
                    MixMatchedSupectVo mixMatchedSupectVo = MixMatchedSupectVoUtils.mixMatchedSupectCommonVoS(mixedSampleGeneVo);
                    resultMixMatchedSupectVoList.add(mixMatchedSupectVo);
                }
            }
        } catch (Exception ex) {
            logger.error("查询更多混合样本比中嫌疑人的比中列表失败！！！", ex.getMessage());
        }
        return resultMixMatchedSupectVoList;
    }
    
    /**
     * 首页查询全库混合样本条数
     */
    public int selectMixCount(String createPerson) {
    		return mixedSampleGeneDAO.selectMixCount(createPerson);
    }

    /**
     * 首页查询更多混合样本比中嫌疑人的比中条数
     *
     * @param query
     * @return
     */
    @Override
    public int selectMatchedSuspectCountByQuery(MixedSampleGeneVo query) {
        return mixedSampleGeneDAO.selectMatchedSuspectCountByQuery(query);
    }

    /**
     * 首页查询混合基因串并案比中嫌疑人列表
     *
     * @param lastestCount
     * @return
     */
    @Override
    public List<MixMatchedSupectVo> selectDifferentCaseMatchedSuspectList(Integer lastestCount,String userId) {
        List<MixMatchedSupectVo> resultMixMatchedSupectVoList = new ArrayList<>();
        try {
            //首页查询混合基因串并案比中嫌疑人列表
            List<MixedSampleGeneVo> mixedSampleGeneVoList = mixedSampleGeneDAO.selectDifferentCaseMatchedSuspectList(lastestCount,userId);
            if (ListUtils.isNotNullAndEmptyList(mixedSampleGeneVoList)) {
                for (MixedSampleGeneVo mixedSampleGeneVo : mixedSampleGeneVoList) {
                    MixMatchedSupectVo mixMatchedSupectVo = MixMatchedSupectVoUtils.mixMatchedSupectCommonVoS(mixedSampleGeneVo);
                    resultMixMatchedSupectVoList.add(mixMatchedSupectVo);
                }
            }
        } catch (Exception ex) {
            logger.error("首页查询混合基因串并案比中嫌疑人列表失败！！！", ex.getMessage());
        }
        return resultMixMatchedSupectVoList;
    }

    /**
     * 首页查询混合基因串并案比中嫌疑人总数
     *
     * @return
     */
    @Override
    public int selectSerialCaseMixedSampleCount() {

        int serialCaseMixedGeneCount = 0;
        try {
//            serialCaseMixedGeneCount = mixedSampleGeneDAO.selectSerialCaseMixedSampleCount();
            DictCount  dictCount=dictCountDAO.selectMatchedSuspectCount(Constants.DICT_TYPE_CODE_SERIAL_COUNT);

            //判空
            if(StringUtils.isNotBlank(dictCount.getDictCountNumber())){
                serialCaseMixedGeneCount=Integer.valueOf(dictCount.getDictCountNumber());
            }
        } catch (Exception ex) {

            logger.error("首页查询混合基因串并案比中嫌疑人总数失败！！！", ex.getMessage());
        }
        return serialCaseMixedGeneCount;
    }


    /**
     * 首页查询更多混合基因串并案比中嫌疑人列表
     *
     * @param query
     * @param pageInfo
     * @return
     */
    @Override
    public List<MixMatchedSupectVo> selectMoreSerialCaseMixedSampleList(MixedSampleGeneVo query, PageInfo pageInfo) {
        List<MixMatchedSupectVo> resultMixMatchedSupectVoList = new ArrayList<>();
        int pageNo;
        int pageSize;
        try {
            pageNo = pageInfo.getCurPage();
            pageSize = pageInfo.getEvePageRecordCnt();
            query.setOffset((pageNo - 1) * pageSize);
            query.setRows(query.getOffset() + pageSize);

            //首页查询更多混合基因串并案比中嫌疑人列表
            List<MixedSampleGeneVo> mixedSampleGeneVoList = mixedSampleGeneDAO.selectMoreDifferentGeneVoList(query);
            if (ListUtils.isNotNullAndEmptyList(mixedSampleGeneVoList)) {

                for (MixedSampleGeneVo mixedSampleGeneVo : mixedSampleGeneVoList) {

                    MixMatchedSupectVo mixMatchedSupectVo = MixMatchedSupectVoUtils.mixMatchedSupectCommonVoS(mixedSampleGeneVo);
                    resultMixMatchedSupectVoList.add(mixMatchedSupectVo);

                }
            }

        } catch (Exception ex) {
            logger.error("首页查询更多混合基因串并案比中嫌疑人列表失败！！！", ex.getMessage());
        }
        return resultMixMatchedSupectVoList;
    }

    /**
     * 首页查询更多串并案比中的混合样本条数
     *
     * @param query
     * @return
     */
    @Override
    public int selectDifferentSuspectCountByQuery(MixedSampleGeneVo query) {
        return mixedSampleGeneDAO.selectDifferentSuspectCountByQuery(query);
    }

    /**
     * 首页查询最新的混合样本比中质控人员样本列表
     *
     * @param lastestCount
     * @return
     */
    @Override
    public List<MixMatchedSupectVo> selectLatestQualityMixedSampleList(Integer lastestCount,String userId) {

        //初始化查询条件
        List<MixMatchedSupectVo> resultMixMatchedSupectVoList = new ArrayList<>();
        try {
            //首页查询最新的混合样本比中质控人员样本列表
            List<MixedSampleGeneVo> mixedSampleGeneVoList = mixedSampleGeneDAO.selectALLMixMatchedVoQualtyList(lastestCount,userId);

            if (ListUtils.isNotNullAndEmptyList(mixedSampleGeneVoList)) {

                for (MixedSampleGeneVo mixedSampleGeneVo : mixedSampleGeneVoList) {

                    MixMatchedSupectVo mixMatchedSupectVo = MixMatchedSupectVoUtils.mixMatchedSupectCommonVoS(mixedSampleGeneVo);
                    resultMixMatchedSupectVoList.add(mixMatchedSupectVo);

                }
            }

        } catch (Exception ex) {
            logger.error("查询最新的混合样本比中质控人员样本列表失败！！！", ex.getMessage());
        }
        return resultMixMatchedSupectVoList;
    }

    /**
     * 首页查询混合样本比中质控人员的总数
     *
     * @return
     */
    @Override
    public int selectMixedSampleQualityPersonnelCount() {
        int mixedQualityCount = 0;
        try {
//            mixedQualityCount = mixedSampleGeneDAO.selectMoreQualityGeneCount();
            DictCount  dictCount=dictCountDAO.selectMatchedSuspectCount(Constants.DICT_TYPE_CODE_MIXEDQUALITY_COUNT);
            //判空
            if(StringUtils.isNotBlank(dictCount.getDictCountNumber())){
                mixedQualityCount=Integer.valueOf(dictCount.getDictCountNumber());
            }

        } catch (Exception ex) {
            logger.error("首页查询混合样本比中质控人员的总数失败！！！", ex.getMessage());
        }
        return mixedQualityCount;
    }

    /**
     * 2.首页查询更多混合样本比中质控人员样本列表
     *
     * @param query
     * @param pageInfo
     * @return
     */
    @Override
    public List<MixMatchedSupectVo> selectMoreQualityMixedSampleList(MixedSampleGeneVo query, PageInfo pageInfo) {
        List<MixMatchedSupectVo> resultMixMatchedSupectVoList = new ArrayList<>();
        //初始化查询条件
        query = InitializationData.mixedSampleGeneVoVoQuery(query);
        int pageNo;
        int pageSize;

        try {
            pageNo = pageInfo.getCurPage();
            pageSize = pageInfo.getEvePageRecordCnt();
            query.setOffset((pageNo - 1) * pageSize);
            query.setRows(query.getOffset() + pageSize);

            //首页查询更多混合样本比中质控人员样本列表
            List<MixedSampleGeneVo> mixedSampleGeneVoList = mixedSampleGeneDAO.selectMoreQualityGeneVoList(query);
            if (ListUtils.isNotNullAndEmptyList(mixedSampleGeneVoList)) {

                for (MixedSampleGeneVo mixedSampleGeneVo : mixedSampleGeneVoList) {
                    MixMatchedSupectVo mixMatchedSupectVo = MixMatchedSupectVoUtils.mixMatchedSupectCommonVoS(mixedSampleGeneVo);
                    resultMixMatchedSupectVoList.add(mixMatchedSupectVo);

                }
            }

        } catch (Exception ex) {
            logger.error("2.9查询更多混合样本比中质控人员样本列表失败！！！", ex.getMessage());
        }
        return resultMixMatchedSupectVoList;
    }

    /**
     * 查询更多混合样本比中质控人员样本条数
     *
     * @param query
     * @return
     */
    @Override
    public int selectMixedQualityPersonnelCount(MixedSampleGeneVo query) {
        return mixedSampleGeneDAO.selectMixedQualityPersonnelCount(query);
    }

    @Override
    public MixedSampleGene selectBySampleId(String sampleId) {
        return mixedSampleGeneDAO.selectBySampleId(sampleId);
    }


    /**
     * 根据id查询混合审核通过基因型信息
     *
     * @param id
     * @return
     */
    @Override
    public MixedSampleGene selectById(String id) {
        MixedSampleGene mixedSampleGene = new MixedSampleGene();
        try {
            mixedSampleGene = mixedSampleGeneDAO.selectById(id);
        } catch (Exception ex) {
            logger.error("根据id查询混合审核通过基因型信息失败！！！", ex.getMessage());
        }
        return mixedSampleGene;
    }

    /**
     * 修改混合审核通过基因型信息
     *
     * @param mixedSampleGene
     */
    @Override
    @Transactional
    public void updateMixedSampleGene(MixedSampleGene mixedSampleGene) {
        try {
            mixedSampleGeneDAO.updateMixedSampleGene(mixedSampleGene);
        } catch (Exception e) {
            logger.error("Error updateMixedSampleGene: ", e);
            throw new DnaRuntimeException();
        }
    }

    /**
     * 添加混合审核通过基因型信息
     *
     * @param mixedSampleGene
     */
    @Override
    @Transactional
    public void insert(MixedSampleGene mixedSampleGene) {
        try {
            mixedSampleGeneDAO.insert(mixedSampleGene);
        } catch (Exception e) {
            logger.error("Error insert: ", e);
            throw new DnaRuntimeException();
        }
    }

    /**
     * 根据案件id查询混合样本
     *
     * @param caseId
     * @return
     */
    @Override
    public List<MixedSampleGeneVo> selectMixedSampleGeneList(String caseId) {
        List<MixedSampleGeneVo> mixedSampleGeneList = new ArrayList<>();
        try {
            mixedSampleGeneList = mixedSampleGeneDAO.selectMixedSampleGeneList(caseId);
        } catch (Exception ex) {
            logger.error("根据案件id查询混合样本失败！！！", ex.getMessage());
        }
        return mixedSampleGeneList;
    }

    /**
     * 查询案件混合样本信息
     *
     * @param query
     * @param pageInfo
     * @return
     */
    @Override
    public List<CaseMixedSampleGeneVo> selectMixedDateBaseGeneList(CaseMixedSampleGeneVo query, PageInfo pageInfo) {
        List<CaseMixedSampleGeneVo> caseMixedSampleGeneVoList = null;
        int pageNo;
        int pageSize;
        try {
            pageNo = pageInfo.getCurPage();
            pageSize = pageInfo.getEvePageRecordCnt();
            query.setOffset((pageNo - 1) * pageSize);
            query.setRows(query.getOffset() + pageSize);

            caseMixedSampleGeneVoList = mixedSampleGeneDAO.selectMixedDateBaseGeneList(query);
        } catch (Exception ex) {
            logger.error("查询案件混合样本信息失败！！！", ex.getMessage());
        }
        return caseMixedSampleGeneVoList;
    }

    /**
     * 查询混合样本信息
     *
     * @param id
     * @return
     */
    @Override
    public List<MixedSampleGeneVo> selectMixedSampleGeneVoList(String id) {
        List<MixedSampleGeneVo> mixedSampleGeneList = null;
        try {
            mixedSampleGeneList = mixedSampleGeneDAO.selectMixedSampleGeneVoList(id);
        } catch (Exception ex) {
            logger.error("查询混合样本信息失败！！！", ex.getMessage());
        }
        return mixedSampleGeneList;
    }

    /**
     * 根据混合id查询混合样本
     *
     * @param sampleGeneId
     * @return
     */
    @Override
    public List<MixedSampleGene> selectMixedSampleSuspect(String sampleGeneId) {
        List<MixedSampleGene> mixedSampleGeneList = null;
        try {
            mixedSampleGeneList = mixedSampleGeneDAO.selectMixedSampleSuspect(sampleGeneId);
        } catch (Exception ex) {
            logger.error("根据混合id查询混合样本失败！！！", ex.getMessage());
        }
        return mixedSampleGeneList;
    }

    /**
     * 查看混合样本基因分型及图谱
     *
     * @param geneID
     * @return
     */
    @Override
    public List<MixedSampleGene> selectMixedSampleSuspectsDetails(String geneID) {
        List<MixedSampleGene> mixedSampleGeneList = null;
        try {
            mixedSampleGeneList = mixedSampleGeneDAO.selectMixedSampleSuspectsDetails(geneID);
        } catch (Exception ex) {
            logger.error("查看混合样本基因分型及图谱失败！！！", ex.getMessage());
        }
        return mixedSampleGeneList;
    }

    /**
     * 混合样本数据库管理：根据基因id删除案件混合样本
     *
     * @param geneId
     * @return
     */
    @Override
    @Transactional
    public int deleteMixedDateBaseGene(String geneId) {
        try {
            return mixedSampleGeneDAO.deleteMixedDateBaseGene(geneId);
        } catch (Exception e) {
            logger.error("Error deleteMixedDateBaseGene: ", e);
            throw new DnaRuntimeException();
        }
    }

    /**
     * 查询混合条数
     *
     * @param query
     * @return
     */
    @Override
    public int selectMixedDateBaseGeneCount(CaseMixedSampleGeneVo query) {
        int totalCnt = 0;
        try {
            totalCnt = mixedSampleGeneDAO.selectMixedDateBaseGeneCount(query);
        } catch (Exception ex) {
            logger.error("查询混合条数失败！！！", ex.getMessage());
        }
        return totalCnt;
    }

    /**
     * 根据混合样本id查询混合样本信息
     *
     * @param sampleId
     * @return
     */
    @Override
    public List<MixedSampleGene> selectMixedSampleGeneBySampleId(String sampleId) {
        List<MixedSampleGene> mixedSampleGeneList = new ArrayList<>();
        try {
            mixedSampleGeneList = mixedSampleGeneDAO.selectMixedSampleGeneBySampleId(sampleId);
        } catch (Exception ex) {
            logger.error("根据混合样本id查询混合样本信息失败！！！", ex.getMessage());
        }
        return mixedSampleGeneList;
    }

    /**
     * 批量删除混合样本信息
     *
     * @param geneIds
     * @return
     */
    @Override
    @Transactional
    public int batchDeleteMixedDateGene(String geneIds) {
        int batchDeleteStatus = 0;
        try {
            //判断比对类型
            String[] strings = null;
            //替换传过来的
            if (StringUtils.isNotBlank(geneIds)) {
                geneIds = geneIds.replace("'", "");
                geneIds = geneIds.replace("\"", "");
                geneIds = geneIds.replace("]", "");
                geneIds = geneIds.replace("[", "");
                //判断比对类型
                strings = geneIds.split(",");
            }
            if (strings != null) {
                //判断物证、人员、质控
                for (String geneId : strings) {
                    //循环list基因id  进行批量删除
                    batchDeleteStatus = mixedSampleGeneDAO.deleteMixedDateBaseGene(geneId);
                }
            }
        } catch (Exception e) {
            logger.error("Error batchDeleteMixedDateGene: ", e);
            throw new DnaRuntimeException();
        }
        return batchDeleteStatus;
    }






    @Override
    public ArrayList<MixedSampleGenePagingVo> selectListPaging(MixedTypingVo mixedTypingVo) {
        try {
            mixedTypingVo.setSampleNo(StringUtils.isBlank(mixedTypingVo.getSampleNo())?null:mixedTypingVo.getSampleNo());
            mixedTypingVo.setSampleName(StringUtils.isBlank(mixedTypingVo.getSampleName())?null:mixedTypingVo.getSampleName());
            mixedTypingVo.setOffset((mixedTypingVo.getOffset() - 1) * 15);
            ArrayList<MixedSampleGenePagingVo> mixedSampleGeneArrayList = mixedSampleGeneDAO.selectListPaging(mixedTypingVo);
            //关联案件信息
            for(MixedSampleGenePagingVo m:mixedSampleGeneArrayList){
                if(StringUtils.isBlank(m.getSampleId())){
                    continue;
                }
                String data = JSONObject.toJSONString(caseInfoServerClient.selectBySampleId(m.getSampleId()).getResult());
                CaseInfo caseInfo = JSON.parseObject(data, CaseInfo.class);
                //通过样本id关联案件编号 案件名称
//                CaseInfo caseInfo= JSON.parseObject(JSONObject.toJSONString(caseInfoServerClient.selectBySampleId(m.getSampleId()).getResult()),CaseInfo.class);
                if(caseInfo == null){
                    continue;
                }
                m.setCaseNo(caseInfo.getCaseNo());
                m.setCaseName(caseInfo.getCaseName());
            }

            return mixedSampleGeneArrayList;
        }catch(Exception ex) {
            logger.error("invoke MixedSampleGeneServiceImpl.selectListPaging error.", ex);
            return null;
        }
    }

    @Override
    public int selectListPagingCount(MixedTypingVo mixedTypingVo) {
        try {
            int totalCount = mixedSampleGeneDAO.selectListPagingCount(mixedTypingVo);

            return totalCount;
        }catch(Exception ex) {
            logger.error("invoke MixedSampleGeneServiceImpl.selectListPagingCount error.", ex);
            return 0;
        }
    }

    @Override
    @Transactional
    public Boolean deleteById(String id) {
        try {
            return mixedSampleGeneDAO.deleteById(id) != 0 ? true : false;
        }catch (Exception ex){
            logger.error("invoke MixedSampleGeneServiceImpl.deleteById error.", ex);
            throw new DnaRuntimeException();
        }
    }

    @Override
    @Transactional
    public Boolean deleteByIds(String ids) {
        try {
            String[] strings = null;
            //替换传过来的
            if (StringUtils.isNotBlank(ids)) {
                ids = ids.replace("'", "");
                ids = ids.replace("\"", "");
                ids = ids.replace("]", "");
                ids = ids.replace("[", "");
                //判断比对类型
                strings = ids.split(",");
            }
            if (strings != null) {
                List<String> stringList=new ArrayList<>(Arrays.asList(strings));
//                mixedSampleGeneDAO.deleteByIds(stringList);
                for(String s:stringList){
                    mixedSampleGeneDAO.deleteById(s);
                }
            }

            return true;
        }catch (Exception ex){
            logger.error("invoke MixedSampleGeneServiceImpl.deleteById error: ", ex);
            throw new DnaRuntimeException();
        }
    }

    @Override
    public MixedSampleGenePagingVo selectPagingVoById(String id) {
        try {
            MixedSampleGenePagingVo mixedSampleGenePagingVo = mixedSampleGeneDAO.selectPagingVoById(id);

            return mixedSampleGenePagingVo;
        }catch(Exception ex) {
            logger.error("invoke MixedSampleGeneServiceImpl.selectPagingVoById error.", ex);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
	@Override
    public MatchResultDetailsListVo selectMatchResultList(MixedTypingVo mixedTypingVo) {
        MatchResultDetailsListVo matchResultDetailsListVo = new MatchResultDetailsListVo();
        ArrayList<MatchResultVo> matchResultList = new ArrayList<>();
        int splitedCount = 0;
        int mixedCount = 0;
        int splitedMixedCount = 0;
        int count = 0;
        try {
            //查询混合分型的数量
            CompareQueue queue = new CompareQueue();
            queue.setMixedSampleId(mixedTypingVo.getMixedSampleId());
            queue.setQueueFlag(Constants.QUEUE_FLAG_1);//已提交
//            queue.setQueueType(Constants.QUEUE_TYPE_01);//混合
            List<CompareQueue> compareQueues = compareQueueService.selectQueueList(queue);
            if (ListUtils.isNotNullAndEmptyList(compareQueues)){
                String id = compareQueues.get(0).getId();
                //type为0查询 已拆分分型比中单一分型列表，type为1查询 混合分型比中单一分型列表
                MatchResult matchResult = new MatchResult();
                matchResult.setCompareId(id);
                matchResult.setSampleGeneResultType(mixedTypingVo.getResultType());
                matchResult.setOffset((mixedTypingVo.getOffset() - 1) * 15);
                matchResultList = matchResultMapper.selectMatchResultList(matchResult);
                count = matchResultMapper.selectMatchResultListCount(matchResult);
                //页面展示条数
                matchResult.setSampleGeneResultType(Constants.RESULT_TYPE_04);//拆分比中单一
                splitedCount = matchResultMapper.selectMatchResultListCount(matchResult);
                matchResult.setSampleGeneResultType(Constants.RESULT_TYPE_01);//混合比单一
                mixedCount = matchResultMapper.selectMatchResultListCount(matchResult);
                matchResult.setSampleGeneResultType(Constants.RESULT_TYPE_02);//拆分比混合
                splitedMixedCount = matchResultMapper.selectMatchResultListCount(matchResult);
                //位点个数
                if (ListUtils.isNotNullAndEmptyList(matchResultList)) {
                    MixedSampleGene sampleGene = mixedSampleGeneDAO.selectById(mixedTypingVo.getMixedSampleId());
                    if (sampleGene != null){
                        for (MatchResultVo result : matchResultList) {
                            if (StringUtils.isNotBlank(sampleGene.getGeneInfo())) {
                                List<Map<String, Object>> sampleGeneInfo = JSON.parseObject(sampleGene.getGeneInfo(), List.class);
                                result.getEntity().setThanInsiteNum(String.valueOf(sampleGeneInfo.size()));
                            }else {
                                result.getEntity().setThanInsiteNum("0");
                            }
                        }
                    }
                }
            }
        }catch(Exception ex) {
            logger.error("invoke MixedSampleGeneServiceImpl.selectMatchResultList error.", ex);
        }
        matchResultDetailsListVo.setMatchResultList(matchResultList);
        matchResultDetailsListVo.setSplitedCount(splitedCount);
        matchResultDetailsListVo.setMixedCount(mixedCount);
        matchResultDetailsListVo.setSplitedMixedCount(splitedMixedCount);
        //分页数据
        PageInfo pageInfo = new PageInfo();
        pageInfo.setAllRecordCount(count);
        matchResultDetailsListVo.setPageInfo(pageInfo);
        return matchResultDetailsListVo;
    }

    @Override
    @Transactional
    public Boolean updateGenePictureById(MixedSampleGene mixedSampleGene) {
        try {
            return mixedSampleGeneDAO.updateGenePictureById(mixedSampleGene) != 0? true:false;
        }catch(Exception ex) {
            logger.error("invoke MixedSampleGeneServiceImpl.updateGenePictureById error: ", ex);
            throw new DnaRuntimeException();
        }
    }

    @Override
    @Transactional
    public Boolean deleteGenePictureById(String id) {
    	try {
    		return mixedSampleGeneDAO.deleteGenePictureById(id) != 0?true:false;
    	} catch (Exception e) {
    		logger.error("Error deleteGenePictureById: ", e);
            throw new DnaRuntimeException();
    	}
    }


}
