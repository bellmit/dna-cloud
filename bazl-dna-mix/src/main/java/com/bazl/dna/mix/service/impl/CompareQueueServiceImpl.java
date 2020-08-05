package com.bazl.dna.mix.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bazl.dna.common.PageInfo;
import com.bazl.dna.exception.DnaRuntimeException;
import com.bazl.dna.mix.constants.Constants;
import com.bazl.dna.mix.dao.CompareQueueDAO;
import com.bazl.dna.mix.dao.CompareQueueMapper;
import com.bazl.dna.mix.model.po.CompareQueue;
import com.bazl.dna.mix.model.po.mixContributorBean;
import com.bazl.dna.mix.model.vo.CompareQueueVo;
import com.bazl.dna.mix.service.CompareQueueService;
import com.google.common.collect.Lists;

/**
 * @Author: lzn
 * @Date: 2019/7/5 16:15
 * @Version: 1.0
 */
@Service
public class CompareQueueServiceImpl extends MixBaseService implements CompareQueueService {

    @Autowired
    CompareQueueDAO compareQueueDAO;
    @Autowired
    CompareQueueMapper compareQueueMapper;



    /**
     * 添加比对队列表
     * @param compareQueue
     */
    @Override
    @Transactional
    public void insertCompareQueue(CompareQueue compareQueue) {
        try {
            compareQueue.setId(UUID.randomUUID().toString());
            compareQueue.setStatus(Constants.MATCH_STATUS_02);
            //compareQueue.setCreatePerson();
            compareQueue.setCreateDatetime(new Date());
            compareQueueDAO.insertCompareQueue(compareQueue);
        } catch (Exception ex) {
            logger.error("添加比对队列表失败！！！", ex);
            throw new DnaRuntimeException();
        }
    }

    /**
     * 根据混合样本id查询比对队列
     * @param compareQueue
     * @return
     */
    @Override
    public List<CompareQueue> selectBySampleId(CompareQueue compareQueue) {
        try {
            return compareQueueDAO.selectBySampleId(compareQueue);
        } catch (Exception ex) {
            logger.error("根据混合样本id查询比对队列失败！！！", ex);
        }
        return Lists.newArrayList();
    }

    /**
     * 查询比对队列表中未比对的数据
     * @param status
     */
    @Override
    public List<CompareQueue> selectNotMixedSampleGeneCompareQueue(String status, String queueType,int page,int size) {
        int startRum = page*size;
        int endRum = (page+1)*size;
        try {
            return compareQueueDAO.selectNotMixedSampleGeneCompareQueue(status, queueType,startRum,endRum);
        } catch (Exception ex) {
            logger.error("查询比对队列表中未比对的数据失败！！！", ex);
        }
        return Lists.newArrayList();
    }

    /**
     * 更该比对状态
     * @param compareQueue
     */
    @Override
    @Transactional
    public void updateStatus(CompareQueue compareQueue) {
        try {
            compareQueueDAO.updateStatus(compareQueue);
        } catch (Exception ex) {
            logger.error("更该比对状态失败！！！", ex);
            throw new DnaRuntimeException();
        }
    }

    @Override
    public List<CompareQueue> selectByMixSameCount(CompareQueue compareQueue) {
        try {
            return compareQueueDAO.selectByMixSameCount(compareQueue);
        } catch (Exception ex) {
            logger.error("根据匹配下线查询比对队列失败！！！", ex);
        }
        return Lists.newArrayList();
    }

    @Override
    @Transactional
    public int deleteByPrimaryKey(String id) {
    	try {
    		return compareQueueMapper.deleteByPrimaryKey(id);
    	} catch (Exception e) {
    		logger.error("Error deleteByPrimaryKey: ", e);
    		throw new DnaRuntimeException();
    	}
    }

    @Override
    @Transactional
    public int insert(CompareQueue record) {
    	try {
    		return compareQueueMapper.insert(record);
    	} catch (Exception e) {
    		logger.error("Error insert: ", e);
    		throw new DnaRuntimeException();
    	}
    }

    @Override
    public CompareQueue selectByPrimaryKey(String id) {
        try {
            return compareQueueMapper.selectByPrimaryKey(id);
        } catch (Exception e) {
    		logger.error("Error selectByPrimaryKey: ", e);
        }
        return null;
    }

    @Override
    public List<CompareQueue> selectAll() {
        return compareQueueMapper.selectAll();
    }

    @Override
    @Transactional
    public int updateByPrimaryKey(CompareQueue record) {
    	try {
    		return compareQueueMapper.updateByPrimaryKey(record);
    	} catch (Exception e) {
    		logger.error("Error updateByPrimaryKey: ", e);
    		throw new DnaRuntimeException();
    	}
    }

    @Override
    public List<CompareQueue> selectQueueList(CompareQueue compareQueue) {
        try {
            return compareQueueMapper.selectQueueList(compareQueue);
        } catch (Exception e) {
        	logger.error("Error selectQueueList: ", e);
        }
        return Lists.newArrayList();
    }

    @Override
    @Transactional
    public void addLocusListInfoForkitId(CompareQueue compareQueue) {
    	try {
    		compareQueueMapper.insert(compareQueue);
    	} catch (Exception e) {
    		logger.error("Error addLocusListInfoForkitId: ", e);
    		throw new DnaRuntimeException();
    	}
    }

    @Override
    public List<CompareQueue> selectBySampleNo(String sampleNo) {
        return compareQueueMapper.selectBySampleNo(sampleNo);
    }

    @Override
    public List<CompareQueue>  selectByMixedSampleId(CompareQueue compareQueue) {
        return compareQueueMapper.selectByMixedSampleId(compareQueue);
    }

    @Override
    public List<CompareQueue> selectByQueueVoList(CompareQueueVo query, PageInfo  pageInfo) {
        List<CompareQueue> compareQueueList = null;
        int pageNo;
        int pageSize;
        try {
            pageNo = pageInfo.getPage();
            pageSize = pageInfo.getEvePageRecordCnt();
            query.setOffset((pageNo - 1) * pageSize);
//            query.setRows(query.getOffset() + pageSize);
            query.setRows(pageInfo.getEvePageRecordCnt());
            compareQueueList = compareQueueMapper.selectByQueueVoList(query);
        } catch (Exception e) {
    		logger.error("Error selectByQueueVoList: ", e);
    	}
        return compareQueueList;
    }

    @Override
    public int selectByQueueVoCount(CompareQueueVo query) {
        return compareQueueMapper.selectByQueueVoCount(query);
    }

    @Override
    public List<CompareQueue> selectCompareQueueList(String queueType) {
        return compareQueueMapper.selectCompareQueueList(queueType);
    }

    @Override
    public List<CompareQueue> selectCompareQueueAlloList() {
        return compareQueueMapper.selectCompareQueueAlloList();
    }

    @Override
    public List<String> contributorGene(String id) {
        return compareQueueMapper.contributorGene(id);
    }

    @Override
    public String selectCompareQueueGeneInfo(String id) {
        return compareQueueMapper.selectCompareQueueGeneInfo(id);
    }

    @Override
    public mixContributorBean getMixSampleInfo(String id) {
        return compareQueueMapper.getMixSampleInfo(id);
    }

}
