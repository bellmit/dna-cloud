package com.bazl.dna.database.nation.converter.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bazl.dna.common.PageInfo;
import com.bazl.dna.database.nation.converter.constants.ConverterConstants;
import com.bazl.dna.database.nation.converter.dao.SampleDnaGeneMapper;
import com.bazl.dna.database.nation.converter.model.po.SampleDnaGene;
import com.bazl.dna.database.nation.converter.model.po.newSampleInfo;
import com.bazl.dna.database.nation.converter.model.vo.SampleDnaGeneVo;
import com.bazl.dna.database.nation.converter.service.SampleDnaGeneService;

@Service
public class SampleDnaGeneServiceImpl extends BaseService implements SampleDnaGeneService {

    @Autowired
    private SampleDnaGeneMapper sampleDnaGeneMapper;

    @Override
    public List<SampleDnaGene> selectByCaseToSingleGeneList(String caseId) {
        try {
            return sampleDnaGeneMapper.selectByCaseToSingleGeneList(caseId);
        } catch (Exception e) {
            e.getStackTrace();
            logger.error("查询国家库单一基因信息失败！" + e);
            return null;
        }
    }

    @Override
    public List<SampleDnaGene> selectByCaseToMixSampleDnaGeneList(String caseId) {
        try {
            return sampleDnaGeneMapper.selectByCaseToMixSampleDnaGeneList(caseId);
        } catch (Exception e) {
            e.getStackTrace();
            logger.error("查询国家库混合基因信息失败！" + e);
            return null;
        }
    }

    @Override
    public List<SampleDnaGeneVo> queryByMixDnaGene(int page, int size, SampleDnaGeneVo sampleDnaGeneVo) {
        List<SampleDnaGeneVo> sampleDnaGeneVoList = new ArrayList<>();
        try{
            int startRum = (page-1)*size;
            int endRum = page*size;
            sampleDnaGeneVoList= sampleDnaGeneMapper.queryByMixDnaGene(startRum,endRum,sampleDnaGeneVo);
        }catch(Exception ex) {
            logger.info("查询国家库数据报错："+ex);
            System.out.println("查询国家库数据报错");
        }
        return sampleDnaGeneVoList;
    }



    @Override
    public int updateSampleDnaGeneDF(String sampleGeneId) {
        int i = 0;
        try{
            i = sampleDnaGeneMapper.updateSampleDnaGeneDF(sampleGeneId);
        }catch(Exception ex) {
            logger.info("修改国家库数据报错："+ex);
            System.out.println("修改国家库数据报错");
        }
        return i;
    }

    @Override
    public int selectMixGeneByCwsd() {
        int i = 0;
        try{
            i = sampleDnaGeneMapper.selectMixGeneByCwsd();
        }catch(Exception ex) {
            logger.info("查询国家库混合基因数据报错："+ex);
            System.out.println("查询国家库混合基因数据报错");
        }
        return i;
    }

    @Override
    public int selectSingleGeneByCwsd() {
       int i = 0;
        try{
           i = sampleDnaGeneMapper.selectSingleGeneByCwsd();
        }catch(Exception ex) {
            logger.info("查询国家库单一基因数据报错："+ex);
            ex.printStackTrace();
        }
        return i;
    }



    @Override
    public List<SampleDnaGeneVo> selectMixGeneByCwsdPage(SampleDnaGeneVo query, int page) {
        List<SampleDnaGeneVo> mixGeneList = null;
        int pageNo;
        int pageSize;
        try{
            pageNo = page;
            pageSize = 1000;
            query.setOffset(pageNo * pageSize);
            query.setRows(query.getOffset() + pageSize);
            mixGeneList = sampleDnaGeneMapper.selectMixGeneByCwsdPage(query);
        }catch(Exception ex) {
            logger.info("分页查询国家库混合基因数据报错："+ex);
            System.out.println("分页查询国家库混合基因数据报错");
        }
        return mixGeneList;
    }




    @Override
    public List<SampleDnaGeneVo> selectSingleGeneByCwsdPage(SampleDnaGeneVo query, int page) {
        List<SampleDnaGeneVo> singleGeneList = null;
        int pageNo;
        int pageSize;
        try{
            pageNo = page;
            pageSize = 1000;
            query.setOffset(pageNo * pageSize);
            query.setRows(query.getOffset() + pageSize);
            singleGeneList = sampleDnaGeneMapper.selectSingleGeneByCwsdPage(query);
        }catch(Exception ex) {
            logger.info("分页查询国家库单一基因数据报错："+ex);
            System.out.println("分页查询国家库单一基因数据报错");
        }
        return singleGeneList;
    }

    @Override
    public newSampleInfo selectSingleGene(String sampleId) {
        newSampleInfo singleGeneList = sampleDnaGeneMapper.selectSingleGene(sampleId);
        return singleGeneList;
    }

    @Override
    public  List<SampleDnaGeneVo> countMixDnaGene() {
        List<SampleDnaGeneVo> countMixDnaGene = new ArrayList<>();
        try{
             countMixDnaGene = sampleDnaGeneMapper.countMixDnaGene();
        }catch(Exception ex) {
            logger.info("获取国家库混合样本总数："+ex);
            System.out.println("获取国家库混合样本总数报错");
        }
        return countMixDnaGene;
    }


    @Override
    public List<SampleDnaGeneVo> queryByMixDnaGenes(PageInfo pageInfo, SampleDnaGeneVo sampleDnaGeneVo) {
        List<SampleDnaGeneVo> sampleDnaGeneVoList = new ArrayList<>();
        int pageNo;
        int pageSize;
        try {
            pageNo = pageInfo.getPage();
            pageSize = pageInfo.getEvePageRecordCnt();
            sampleDnaGeneVo.setOffset((pageNo - 1) * pageSize);
            sampleDnaGeneVo.setRows(sampleDnaGeneVo.getOffset() + pageSize);
            sampleDnaGeneVoList = sampleDnaGeneMapper.queryByMixDnaGenes(sampleDnaGeneVo);
        } catch(Exception ex) {
            logger.info("获取国家库混合样本："+ex);
            System.out.println("获取国家库混合样本报错");
        }
        return sampleDnaGeneVoList;
    }


    @Override
    public int  countMixDnaGenes(SampleDnaGeneVo sampleDnaGeneVo) {
        return sampleDnaGeneMapper.countMixDnaGenes(sampleDnaGeneVo) ;
    }

    @Override
    public List<SampleDnaGeneVo> selectBySampleNoList(String sampleNo) {
        return sampleDnaGeneMapper.selectBySampleNoList(sampleNo);
    }

    @Override
    public List<SampleDnaGeneVo> selectSingleGeneBySampleNo(SampleDnaGeneVo query) {
        List<SampleDnaGeneVo> singleGeneList = null;
        try{
            singleGeneList = sampleDnaGeneMapper.selectSingleGeneBySampleNo(query);
        }catch(Exception ex) {
            logger.error("根据样本编号查询国家库单一基因数据报错："+ex);
            return null;
        }
        return singleGeneList;
    }

    @Override
    public List<SampleDnaGene> selectMixedSampleGeneList(String caseId) {
        List<SampleDnaGene> sampleDnaGeneList = null;
        try{
            sampleDnaGeneList = sampleDnaGeneMapper.selectMixedSampleGeneList(caseId);
        }catch(Exception ex) {
            logger.error("根据案件id获取混合样本信息报错："+ex);
            return null;
        }
        return sampleDnaGeneList;
    }

    /**
     * 根据委托id查询基因信息列表
     * @param consignmentId
     * @return
     */
    @Override
    public List<SampleDnaGeneVo> selectSelfVoListByConsignmentId(String consignmentId) {
        try{
            return sampleDnaGeneMapper.selectSelfVoListByConsignmentId(consignmentId, ConverterConstants.SAMPLE_RELATION_SELF, ConverterConstants.SAMPLE_RELATION_SELF_PROBABLY);
        }catch(Exception ex) {
            logger.error("根据委托id查询基因信息列表报错："+ex);
            return null;
        }
    }

    /**
     * 根据委托id查询所有的基因信息列表
     * @param consignmentId
     * @return
     */
    @Override
    public List<SampleDnaGeneVo> selectAllVoListByConsignmentId(String consignmentId) {
        try{
            return sampleDnaGeneMapper.selectAllVoListByConsignmentId(consignmentId);
        }catch(Exception ex) {
            logger.error("根据委托id查询基因信息列表报错："+ex);
            return null;
        }
    }
}

