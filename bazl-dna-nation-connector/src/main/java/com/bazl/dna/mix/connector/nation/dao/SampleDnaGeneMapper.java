package com.bazl.dna.mix.connector.nation.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bazl.dna.mix.connector.nation.model.po.SampleDnaGene;
import com.bazl.dna.mix.connector.nation.model.po.newSampleInfo;
import com.bazl.dna.mix.connector.nation.model.vo.SampleDnaGeneVo;

public interface SampleDnaGeneMapper {
    int deleteByPrimaryKey(String id);

    int insert(SampleDnaGene record);

    SampleDnaGene selectByPrimaryKey(String id);

    List<SampleDnaGene> selectAll();

    int updateByPrimaryKey(SampleDnaGene record);

    //混合库 查询国家库 案件相关单一样本
    List<SampleDnaGene> selectByCaseToSingleGeneList(String caseId);

    //混合库 查询国家库 案件相关混合样本
    List<SampleDnaGene> selectByCaseToMixSampleDnaGeneList(String caseId);

    //混合库 查询国家库 混合样本   分页条查
    List<SampleDnaGeneVo> queryByMixDnaGene(@Param("startRum") int startRum, @Param("endRum") int endRum, @Param("sampleDnaGeneVo") SampleDnaGeneVo sampleDnaGeneVo);


    //国家库删除混合样本 （假删除）
    int updateSampleDnaGeneDF(String sampleGeneId);

    //查询混合基因信息(关联案件，地区，样本，字典项) 总数
    int  selectMixGeneByCwsd();

    //查询单一基因信息(关联案件，地区，样本，字典项) 总数
    int  selectSingleGeneByCwsd();

    //查询规定时间内的数据条数
    int  selectSingleCountByDate(Date date);


    //查询混合基因信息(关联案件，地区，样本，字典项) 分页
    List<SampleDnaGeneVo> selectMixGeneByCwsdPage(SampleDnaGeneVo query);

    //查询单一基因信息(关联案件，地区，样本，字典项)  分页
    List<SampleDnaGeneVo> selectSingleGeneByCwsdPage(SampleDnaGeneVo query);

    //查询规定天数的单一基因信息  分页
    List<SampleDnaGeneVo> selectSingByDatePage(SampleDnaGeneVo query);

    //国家库混合样本总数
    List<SampleDnaGeneVo> countMixDnaGene();


    //混合库 查询国家库 混合样本   分页条查
    List<SampleDnaGeneVo> queryByMixDnaGenes(SampleDnaGeneVo sampleDnaGeneVo);

    //混合库 查询国家库 混合样本总数
    int countMixDnaGenes(SampleDnaGeneVo sampleDnaGeneVo);

    //检材编号模糊查询
    List<SampleDnaGeneVo> selectBySampleNoList(String sampleNo);
    //根据样本编号 查询单一基因信息(关联案件，地区，样本，字典项)
    List<SampleDnaGeneVo> selectSingleGeneBySampleNo(SampleDnaGeneVo query);

    newSampleInfo selectSingleGene(@Param("sampleId")String sampleId, @Param("personCodeType")String personCodeType);
    //根据案件id查询混合样本库信息
    List<SampleDnaGene> selectMixedSampleGeneList(String caseId);
}