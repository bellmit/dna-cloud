package com.bazl.dna.mix.service.impl;

import com.bazl.dna.mix.model.vo.SingleSampleGeneVo;
import com.bazl.dna.exception.DnaRuntimeException;
import com.bazl.dna.mix.constants.Constants;
import com.bazl.dna.mix.dao.DictCountDAO;
import com.bazl.dna.mix.dao.QualtyPersonnelDAO;
import com.bazl.dna.mix.dao.SingleSampleGeneDAO;
import com.bazl.dna.mix.model.po.DictCount;
import com.bazl.dna.mix.model.po.SingleSampleGene;
import com.bazl.dna.mix.service.SingleSampleGeneService;
import com.bazl.dna.mix.utils.GeneMixCompareUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: lzn
 * @Date: 2019/7/5 16:15
 * @Version: 1.0
 */
@Service
public class SingleSampleGeneServiceImpl extends MixBaseService implements SingleSampleGeneService {

    @Autowired
    SingleSampleGeneDAO singleSampleGeneDAO;

    @Autowired
    QualtyPersonnelDAO qualtyPersonnelDAO;

    @Autowired
    GeneMixCompareUtil geneMixCompareUtil;

    @Autowired
    DictCountDAO dictCountDAO;

    /**
     * 查询单一样本总数
     *
     * @return
     */
    @Override
    public int selectSingleSampleGeneCount() {
        int singleSampleGeneCount = 0;
        try {
//            singleSampleGeneCount = singleSampleGeneDAO.selectSingleSampleGeneCount();
            DictCount dictCount = dictCountDAO.selectMatchedSuspectCount(Constants.DICT_TYPE_CODE_SINGLE_GENE_COUNT);

            //判空
            if(StringUtils.isNotBlank(dictCount.getDictCountNumber())){
                singleSampleGeneCount=Integer.valueOf(dictCount.getDictCountNumber());
            }

        } catch (Exception ex) {
            logger.error("查询单一样本总数失败！！！", ex.getMessage());
        }
        return singleSampleGeneCount;
    }

    /**
     * 根据id查询单一审核通过基因型信息
     *
     * @param id
     * @return
     */
    @Override
    public SingleSampleGene selectById(String id) {
        SingleSampleGene singleSampleGene = new SingleSampleGene();
        try {
            singleSampleGene = singleSampleGeneDAO.selectById(id);
        } catch (Exception ex) {
            logger.error("根据id查询单一审核通过基因型信息失败！！！", ex.getMessage());
        }
        return singleSampleGene;
    }

    /**
     * 修改查询单一审核通过基因型信息
     *
     * @param singleSampleGene
     */
    @Override
    @Transactional
    public void updateSingleSampleGene(SingleSampleGene singleSampleGene) {
        try {
            singleSampleGeneDAO.updateSingleSampleGene(singleSampleGene);
        } catch (Exception e) {
    		logger.error("Error updateSingleSampleGene: ", e);
            throw new DnaRuntimeException();
    	}
    }

    /**
     * 添加查询单一审核通过基因型信息
     *
     * @param singleSampleGene
     */
    @Override
    @Transactional
    public void insert(SingleSampleGene singleSampleGene) {
        try {
            singleSampleGeneDAO.insert(singleSampleGene);
        } catch (Exception e) {
    		logger.error("Error insert: ", e);
            throw new DnaRuntimeException();
    	}
    }

    /**
     * 查看单一样本基因分型及图谱
     *
     * @param geneID
     * @return
     */
    @Override
    public List<SingleSampleGene> selectSingleSampleGeneDetails(String geneID) {
        List<SingleSampleGene> singleSampleGeneList = null;
        try {
            singleSampleGeneList = singleSampleGeneDAO.selectSingleSampleGeneDetails(geneID);
        } catch (Exception ex) {
            logger.error("查看单一样本基因分型及图谱失败！！！", ex.getMessage());
        }
        return singleSampleGeneList;
    }

    /**
     * 根据案件id查询单一样本
     *
     * @param caseId
     * @return
     */
    @Override
    public List<SingleSampleGeneVo> selectSingleSampleGeneList(String caseId) {
        List<SingleSampleGeneVo> singleSampleGeneList = new ArrayList<>();
        try {
            singleSampleGeneList = singleSampleGeneDAO.selectSingleSampleGeneList(caseId);
        } catch (Exception ex) {
            logger.error("根据案件id查询单一样本失败！！！", ex.getMessage());
        }
        return singleSampleGeneList;
    }

    /**
     * 查询单一样本信息
     *
     * @param id
     * @return
     */
    @Override
    public List<SingleSampleGeneVo> selectSingleSampleGeneVoList(String id) {
        List<SingleSampleGeneVo> singleSampleGeneList = new ArrayList<>();
        try {
            singleSampleGeneList = singleSampleGeneDAO.selectSingleSampleGeneVoList(id);
        } catch (Exception ex) {
            logger.error("查询单一样本信息失败！！！", ex.getMessage());
        }
        return singleSampleGeneList;
    }

    /**
     * 根据比中id查询单一样本
     *
     * @param ratiogeneId
     * @return
     */
    @Override
    public List<SingleSampleGene> selectSingleSampleGene(String ratiogeneId) {
        List<SingleSampleGene> singleSampleGeneList = null;
        try {
            singleSampleGeneList = singleSampleGeneDAO.selectSingleSampleGeneBySampleNo(ratiogeneId);
        } catch (Exception ex) {
            logger.error("根据比中id查询单一样本失败！！！", ex.getMessage());
        }
        return singleSampleGeneList;
    }

    /**
     * 查询单一样本基因表
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public List<SingleSampleGene> selectSingleSampleGeneAll(int page, int size) {
        List<SingleSampleGene> singleSampleGeneList = null;
        int startRum = page * size;
        int endRum = (page + 1) * size;
        try {

            singleSampleGeneList = singleSampleGeneDAO.selectSingleSampleGeneAll(startRum, endRum);
        } catch (Exception ex) {
            logger.error("查询单一样本基因表失败！！！", ex.getMessage());
        }
        return singleSampleGeneList;
    }


    /**
     * 提交本案查询物证信息list
     *
     * @param sampleflag
     * @param caseId
     * @param page
     * @param size
     * @return
     */
    @Override
    public List<SingleSampleGeneVo> selectSampleTypeCaseIdById(String sampleflag, String caseId, int page, int size) {
        Map<String, Object> map = new HashMap<>();
        List<SingleSampleGeneVo> singleSampleGeneVoList = null;
        int startRum = page * size;
        int endRum = (page + 1) * size;
        try {
            map.put("sampleflag", sampleflag);
            map.put("caseId", caseId);
            map.put("startRum", startRum);
            map.put("endRum", endRum);
            singleSampleGeneVoList = singleSampleGeneDAO.selectSampleTypeCaseIdById(map);
        } catch (Exception ex) {
            logger.error("提交本案查询物证信息list失败！！！", ex.getMessage());
        }
        return singleSampleGeneVoList;
    }

    /**
     * 1:人员查询物证信息list
     *
     * @param sampleflag
     * @param caseId
     * @param page
     * @param size
     * @return
     */
    @Override
    public List<SingleSampleGeneVo> selectPersonTypeCaseIdById(String sampleflag, String caseId, int page, int size) {
        Map<String, Object> map = new HashMap<>();
        List<SingleSampleGeneVo> singleSampleGeneVoList = null;
        int startRum = page * size;
        int endRum = (page + 1) * size;
        try {
            map.put("sampleflag", sampleflag);
            map.put("caseId", caseId);
            map.put("startRum", startRum);
            map.put("endRum", endRum);
            singleSampleGeneVoList = singleSampleGeneDAO.selectPersonTypeCaseIdById(map);
        } catch (Exception ex) {
            logger.error("提交本案人员查询物证信息list失败！！！", ex.getMessage());
        }
        return singleSampleGeneVoList;
    }

    /**
     * 提交全库 0：物证信息list
     *
     * @param sampleflag
     * @param page
     * @param size
     * @return
     */
    @Override
    public List<SingleSampleGeneVo> selectSampleTypeSampleflag(String sampleflag, int page, int size) {
        List<SingleSampleGeneVo> singleSampleGeneVoList = null;
        Map<String, Object> map = new HashMap<>();
        int startRum = page * size;
        int endRum = (page + 1) * size;
        try {
            map.put("sampleflag", sampleflag);
            map.put("startRum", startRum);
            map.put("endRum", endRum);
            singleSampleGeneVoList = singleSampleGeneDAO.selectSampleTypeSampleflag(map);
        } catch (Exception ex) {
            logger.error("提交全库物证信息list失败！！！", ex.getMessage());
        }
        return singleSampleGeneVoList;
    }

    /**
     * 提交全库 0：物证信息list
     *
     * @param sampleflag
     * @return
     */
    @Override
    public List<SingleSampleGeneVo> selectSampleTypeSample(String sampleflag) {
        List<SingleSampleGeneVo> singleSampleGeneVoList = null;
        try {
            singleSampleGeneVoList = singleSampleGeneDAO.selectSampleTypeSample(sampleflag);
        } catch (Exception ex) {
            logger.error("提交全库物证信息list失败！！！", ex.getMessage());
        }
        return singleSampleGeneVoList;
    }


    /**
     * 提交本库人员查询物证信息list
     *
     * @param sampleflag
     * @param page
     * @param size
     * @return
     */
    @Override
    public List<SingleSampleGeneVo> selectPersonSampleflag(String sampleflag, int page, int size) {
        Map<String, Object> map = new HashMap<>();
        List<SingleSampleGeneVo> singleSampleGeneVoList = null;
        int startRum = page * size;
        int endRum = (page + 1) * size;
        try {
            map.put("sampleflag", sampleflag);
            map.put("startRum", startRum);
            map.put("endRum", endRum);
            singleSampleGeneVoList = singleSampleGeneDAO.selectPersonSampleflag(map);
        } catch (Exception ex) {
            logger.error("提交本库人员查询物证信息list失败！！！", ex.getMessage());
        }
        return singleSampleGeneVoList;
    }

    /**
     * 提交本库人员查询物证信息list
     *
     * @param sampleflag
     * @return
     */
    @Override
    public List<SingleSampleGeneVo> selectPersonSample(String sampleflag) {
        List<SingleSampleGeneVo> singleSampleGeneVoList = null;
        try {
            singleSampleGeneVoList = singleSampleGeneDAO.selectPersonSample(sampleflag);
        } catch (Exception ex) {
            logger.error("提交本库人员查询物证信息list失败！！！", ex.getMessage());
        }
        return singleSampleGeneVoList;
    }

    @Override
    public List<SingleSampleGeneVo> selectComparisonAll(SingleSampleGeneVo query) {
        List<SingleSampleGeneVo> queuestate = null;
        try {
            queuestate = singleSampleGeneDAO.selectComparisonAll(query);
        } catch(Exception ex) {
            logger.info("查询全库比对详情数据："+ex);
            System.out.println("查询全库比对详情数据报错：");
            return null;
        }
        return queuestate;
    }

    @Override
    public SingleSampleGene selectBySampleId(String sampleId) {
        return singleSampleGeneDAO.selectBySampleId(sampleId);
    }

}
