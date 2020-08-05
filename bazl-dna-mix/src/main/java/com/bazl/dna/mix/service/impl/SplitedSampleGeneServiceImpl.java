package com.bazl.dna.mix.service.impl;

import com.bazl.dna.exception.DnaRuntimeException;
import com.bazl.dna.mix.dao.SplitedSampleGeneDAO;
import com.bazl.dna.mix.model.po.SplitedSampleGene;
import com.bazl.dna.mix.service.SplitedSampleGeneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: lzn
 * @Date: 2019/7/5 16:15
 * @Version: 1.0
 */
@Service
public class SplitedSampleGeneServiceImpl extends MixBaseService implements SplitedSampleGeneService {
    @Autowired
    SplitedSampleGeneDAO splitedSampleGeneDAO;

    @Override
    @Transactional
    public int deleteByPrimaryKey(String id) {
    	try {
    		return splitedSampleGeneDAO.deleteByPrimaryKey(id);
    	} catch (Exception e) {
    		logger.error("Error deleteByPrimaryKey: ", e);
            throw new DnaRuntimeException();
    	}
    }

    @Override
    public SplitedSampleGene selectByPrimaryKey(String id) {
        SplitedSampleGene splitedSampleGene =  null;
        try {
            splitedSampleGene = splitedSampleGeneDAO.selectByPrimaryKey(id);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询拆分信息失败！"+e.getMessage());
        }
        return splitedSampleGene;
    }

    /**
     * 查询已拆分样本总数
     * @return
     */
    @Override
    public int selectSplitedSampleGeneCount () {
        int splitedSampleGeneCount = 0;
        try {
            splitedSampleGeneCount = splitedSampleGeneDAO.selectSplitedSampleGeneCount();
        } catch (Exception ex) {
            logger.error("查询已拆分样本总数失败！！！", ex.getMessage());
        }
        return splitedSampleGeneCount;
    }

    /**
     * 入库拆分单一基因信息
     * @param splitedSampleGene
     */
    @Override
    @Transactional
    public int insert(SplitedSampleGene splitedSampleGene) {
        try {
            return splitedSampleGeneDAO.insert(splitedSampleGene);
        } catch (Exception e) {
    		logger.error("Error insert: ", e);
            throw new DnaRuntimeException();
    	}
    }

    /**
     * 根据混合id查询拆分基因信息
     * @param mixedSampleGeneId
     * @return
     */
    @Override
    public List<SplitedSampleGene> selectSplitedSampleGeneByMixedSampleGeneId(String mixedSampleGeneId) {
        List<SplitedSampleGene> splitedSampleGeneList = new ArrayList<>();
        try {
            splitedSampleGeneList = splitedSampleGeneDAO.selectSplitedSampleGeneByMixedSampleGeneId(mixedSampleGeneId);
        } catch (Exception ex) {
            logger.error("根据混合id查询拆分基因信息失败！！！", ex.getMessage());
        }
        return splitedSampleGeneList;
    }

    /**
     * 修改拆分基因信息
     * @param splitedSampleGene
     */
    @Override
    @Transactional
    public void update(SplitedSampleGene splitedSampleGene) {
        try {
            splitedSampleGeneDAO.update(splitedSampleGene);
        } catch (Exception e) {
    		logger.error("Error update: ", e);
            throw new DnaRuntimeException();
    	}
    }

    /**
     * 查询拆分样本基因信息
     * @return
     * @param page
     * @param size
     */
    @Override
    public List<SplitedSampleGene> selectAll(int page,int size) {
        List<SplitedSampleGene> splitedSampleGeneList = null;
        int startRum = page*size;
        int endRum = (page+1)*size;
        try {
            splitedSampleGeneList = splitedSampleGeneDAO.selectAll(startRum, endRum);
        } catch (Exception ex) {
            logger.error("查询拆分样本基因信息失败！！！", ex.getMessage());
        }
        return splitedSampleGeneList;
    }

    /**
     * 根据拆分id查询拆分基因信息
     * @param sampleGeneId
     * @return
     */
    @Override
    public List<SplitedSampleGene> selectSplitedSampleGeneList(String sampleGeneId) {
        List<SplitedSampleGene> splitedSampleGeneList = null;
        try {
            splitedSampleGeneList = splitedSampleGeneDAO.selectSplitedSampleGeneList(sampleGeneId);
        } catch (Exception ex) {
            logger.error("查询拆分样本基因信息失败！！！", ex.getMessage());
        }
        return splitedSampleGeneList;
    }

    @Override
    public SplitedSampleGene selectByCompareQueueId(String compareQueueId) {
        SplitedSampleGene splitedSampleGene = null;
        try {
            splitedSampleGene = splitedSampleGeneDAO.selectByCompareQueueId(compareQueueId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("通过队列id查询拆分基因信息失败！"+e.getMessage());
        }
        return splitedSampleGene;
    }
}
