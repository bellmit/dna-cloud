package com.bazl.dna.mix.connector.nation.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.bazl.dna.common.PageInfo;
import com.bazl.dna.mix.connector.nation.model.po.SampleDnaGene;
import com.bazl.dna.mix.connector.nation.model.po.newSampleInfo;
import com.bazl.dna.mix.connector.nation.model.vo.SampleDnaGeneVo;

@Repository
public interface SampleDnaGeneService {

    SampleDnaGene selectByPrimaryKey(String id);

    //查询国家库 单一样本
    List<SampleDnaGene> selectByCaseToSingleGeneList(String caseId);

    //查询国家库 混合样本
    List<SampleDnaGene> selectByCaseToMixSampleDnaGeneList(String caseId);

    //查询国家库混合样本数据（分页条查）
    List<SampleDnaGeneVo> queryByMixDnaGene(int page, int size, SampleDnaGeneVo sampleDnaGeneVo);

    //国家库删除混合样本 （假删除）
    int  updateSampleDnaGeneDF(String sampleGeneId);

    //查询国家库混合基因信息(关联案件，地区，样本，字典项) 总数
    int selectMixGeneByCwsd();

    //查询国家库单一基因信息(关联案件，地区，样本，字典项) 总数
    int selectSingleGeneByCwsd();

    //查询国家库混合基因信息(关联案件，地区，样本，字典项)   分页
    List<SampleDnaGeneVo> selectMixGeneByCwsdPage(SampleDnaGeneVo query, int page);

    //查询国家库单一基因信息(关联案件，地区，样本，字典项)   分页
    List<SampleDnaGeneVo> selectSingleGeneByCwsdPage(SampleDnaGeneVo query, PageInfo pageInfo);

    //查询规定天数的单一基因信息
    List<SampleDnaGeneVo> selectByDateSing(SampleDnaGeneVo query, PageInfo pageInfo);

    //查询规定时间内的数据条数
    int  selectSingleCountByDate(Date date);


    newSampleInfo selectSingleGene(String sampleId,String personCodeType);

    //查询总条数
    List<SampleDnaGeneVo>  countMixDnaGene();

    //查询国家库混合样本数据  分页2
    List<SampleDnaGeneVo> queryByMixDnaGenes(PageInfo pageInfo, SampleDnaGeneVo sampleDnaGeneVo);

    //查询国家库混合样本总数2
    int countMixDnaGenes(SampleDnaGeneVo sampleDnaGeneVo);

    //检材编号模糊查询
    List<SampleDnaGeneVo> selectBySampleNoList(String sampleNo);
    //根据样本编号 查询单一基因信息(关联案件，地区，样本，字典项)
    List<SampleDnaGeneVo> selectSingleGeneBySampleNo(SampleDnaGeneVo query);
    //根据案件id查询混合样本库信息
    List<SampleDnaGene> selectMixedSampleGeneList(String caseId);
}
