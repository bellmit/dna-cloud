package com.bazl.dna.mix.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.bazl.dna.common.PageInfo;
import com.bazl.dna.exception.DnaRuntimeException;
import com.bazl.dna.mix.config.DnaGeneInfoDetail;
import com.bazl.dna.mix.dao.MatchResultMapper;
import com.bazl.dna.mix.dao.MixedSampleGeneDAO;
import com.bazl.dna.mix.dao.SplitedSampleGeneDAO;
import com.bazl.dna.mix.model.po.MatchResult;
import com.bazl.dna.mix.model.po.MixedSampleGene;
import com.bazl.dna.mix.model.po.SplitedSampleGene;
import com.bazl.dna.mix.model.po.possibilityInfo;
import com.bazl.dna.mix.model.vo.MatchResultDetailsVo;
import com.bazl.dna.mix.model.vo.MatchResultVo;
import com.bazl.dna.mix.service.MatchResulService;

/**
 * Created by Administrator on 2019/12/19.
 */
@Service
public class MatchResulServiceImpl extends MixBaseService implements MatchResulService {

    @Autowired
    MatchResultMapper matchResultMapper;
    @Autowired
    SplitedSampleGeneDAO splitedSampleGeneDAO;
    @Autowired
    MixedSampleGeneDAO mixedSampleGeneDAO;

    @Override
    @Transactional
    public int deleteByPrimaryKey(String id) {
    	try {
    		return matchResultMapper.deleteByPrimaryKey(id);
    	} catch (Exception e) {
    		logger.error("Error deleteByPrimaryKey: ", e);
    		throw new DnaRuntimeException();
    	}
    }

    @Override
    @Transactional
    public int insert(MatchResult record) {
    	try {
    		return matchResultMapper.insert(record);
    	} catch (Exception e) {
    		logger.error("Error insert: ", e);
    		throw new DnaRuntimeException();
    	}
    }

    @Override
    public MatchResult selectByPrimaryKey(String id) {
        return matchResultMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<MatchResult> selectAll() {
        return matchResultMapper.selectAll();
    }

    @Override
    @Transactional
    public int updateByPrimaryKey(MatchResult matchResult) {
    	try {
    		return matchResultMapper.updateByPrimaryKey(matchResult);
    	} catch (Exception e) {
    		logger.error("Error updateByPrimaryKey: ", e);
    		throw new DnaRuntimeException();
    	}
    }

    @Override
    public List<MatchResult> selectByMatchResult(MatchResult matchResult) {
        return matchResultMapper.selectByMatchResult(matchResult);
    }
    
    @Override
    public int selectMatchResultByEffect(String effectFlag,String createPerson) {
    		return matchResultMapper.selectMatchResultByEffect(effectFlag,createPerson);
    }

    @Override
    public MatchResult queryMatchResultInfo(String id) {
        return matchResultMapper.queryMatchResultInfo(id);
    }

    @Override
    public int selectByMatchCount(String compareId) {
        return matchResultMapper.selectByMatchCount(compareId);
    }

    @Override
    public MatchResult selectByMatch(MatchResult matchResult) {
        try {
            return matchResultMapper.selectByMatch(matchResult);
        } catch (Exception e) {
        	logger.error("Error selectByMatch: ", e);
        }
        return null;
    }

    @Override
    public List<MatchResult> selectMatchList(MatchResult matchResult) {
        return matchResultMapper.selectMatchList(matchResult);
    }

    @Override
    public List<MatchResult> selectByMatchResultVo(MatchResultVo query,PageInfo pageInfo) {
        List<MatchResult> selectByMatchResult = null;
        int pageNo;
        int pageSize;
        try {
            pageNo = pageInfo.getPage();
            pageSize = pageInfo.getEvePageRecordCnt();
            query.setOffset((pageNo - 1) * pageSize);
//            query.setRows(query.getOffset() + pageSize);
            query.setRows(pageInfo.getEvePageRecordCnt());
            selectByMatchResult = matchResultMapper.selectByMatchResultVo(query);
        } catch(Exception ex) {
            logger.info("查询报错："+ex);
        }
        return selectByMatchResult;
    }

    @Override
    public int selectByMatchResultCount(MatchResultVo query) {
        return matchResultMapper.selectByMatchResultCount(query);
    }

    @Override
    @Transactional
    public int insertPossibilityGene(possibilityInfo bean) {
    	try {
    		return matchResultMapper.insertPossibilityGene(bean);
    	} catch (Exception e) {
    		logger.error("Error insertPossibilityGene: ", e);
    		throw new DnaRuntimeException();
    	}
    }

    @Override
    public List<MatchResult> selectByThirtyMatchList(String createPerson) {
        return matchResultMapper.selectByThirtyMatchList(createPerson);
    }

    @Override
    public HashMap<Object, Object> selectMatchDetails(String id, String matchId, String type) {
        try {
            HashMap<Object, Object> hashMap = new HashMap<>();
            ArrayList<MatchResultDetailsVo> matchResultDetailsVoList=new ArrayList<MatchResultDetailsVo>();

            //type为0查询 已拆分分型比中单一分型比中详情，type为1查询 混合分型比中单一分型比中详情
            if("0".equals(type)){
                //获取已拆分基因信息
                SplitedSampleGene splitedSampleGene=splitedSampleGeneDAO.selectById(id);
                //获取比中的基因信息
                MatchResult matchResult=matchResultMapper.selectById(matchId);

                if (StringUtils.isNotBlank(matchResult.getGenePicture())){
                    hashMap.put("ratioSampleGeneImagePath",matchResult.getGenePicture());
                }else{
                    hashMap.put("ratioSampleGeneImagePath",0);
                }
                hashMap.put("splitdSampleGeneImagePath",0);

                List<DnaGeneInfoDetail> splitedGeneInfo= JSON.parseArray(splitedSampleGene.getGeneInfo(),DnaGeneInfoDetail.class);
                List<DnaGeneInfoDetail> matchGeneInfo= JSON.parseArray(matchResult.getGeneInfo(),DnaGeneInfoDetail.class);

                MatchResultDetailsVo matchResultDetailsVo=null;
                for(DnaGeneInfoDetail s:splitedGeneInfo){
                    for(DnaGeneInfoDetail m:matchGeneInfo){
                        if(s.getName().equals(m.getName())){
                            matchResultDetailsVo=new MatchResultDetailsVo();
                            matchResultDetailsVo.setMarkerName(s.getName());
                            matchResultDetailsVo.setGeneStr1(s.getValue());
                            matchResultDetailsVo.setGeneStr2(m.getValue());
                            matchResultDetailsVoList.add(matchResultDetailsVo);
                        }
                    }
                }

            }else if("1".equals(type)){
                //获取混合基因信息
                MixedSampleGene mixedGene=mixedSampleGeneDAO.selectById(id);
                //获取比中的基因信息
                MatchResult matchResult=matchResultMapper.selectById(matchId);

                if (StringUtils.isNotBlank(matchResult.getGenePicture())){
                    hashMap.put("ratioSampleGeneImagePath",matchResult.getGenePicture());
                }else{
                    hashMap.put("ratioSampleGeneImagePath",0);
                }
                if (StringUtils.isNotBlank(mixedGene.getGenePicture())){
                    hashMap.put("splitdSampleGeneImagePath",mixedGene.getGenePicture());
                }else{
                    hashMap.put("splitdSampleGeneImagePath",0);
                }

                List<DnaGeneInfoDetail> mixedGeneInfo= JSON.parseArray(mixedGene.getGeneInfo(),DnaGeneInfoDetail.class);
                List<DnaGeneInfoDetail> matchGeneInfo= JSON.parseArray(matchResult.getGeneInfo(),DnaGeneInfoDetail.class);

                MatchResultDetailsVo matchResultDetailsVo=null;
                for(DnaGeneInfoDetail s:mixedGeneInfo){
                    for(DnaGeneInfoDetail m:matchGeneInfo){
                        if(s.getName().equals(m.getName())){
                            matchResultDetailsVo=new MatchResultDetailsVo();
                            matchResultDetailsVo.setMarkerName(s.getName());
                            matchResultDetailsVo.setGeneStr1(s.getValue());
                            matchResultDetailsVo.setGeneStr2(m.getValue());
                            matchResultDetailsVoList.add(matchResultDetailsVo);
                        }
                    }
                }

            }

            hashMap.put("matchResultDetailsVoList",matchResultDetailsVoList);

            return hashMap;
        }catch(Exception ex) {
            logger.error("invoke MatchResulServiceImpl.selectMatchDetails error.", ex);
        }
        return null;
    }
}
