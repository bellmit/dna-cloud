package com.bazl.dna.lims.stats.controller.ArchivesEvidence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.lims.model.bo.ArchivesEvidenceModel;
import com.bazl.dna.lims.model.po.DnaEvidenceCount;
import com.bazl.dna.lims.service.ArchivesEvidenceService;
import com.bazl.dna.lims.stats.controller.BaseController;

/**
 * @author liuChang
 * @since 2020-07-30
 * @Desc 档案与物证
 * 档案
 * 档案总数：已签发鉴定书的案件总数
 * 现存档案数：档案库中已存储的档案数量
 *
 * 物证
 * 物证总数：已受理案件中的物证总数
 * 现存物证数：物证保管系统存储的物证总数
 */
@RestController
@RequestMapping("/stats/archivesEvidence")
public class ArchivesEvidenceController extends BaseController {

    @Autowired
    ArchivesEvidenceService archivesEvidenceService;

    /*
     * 类说明：查询全部档案总数 现存档案数
     * Date:2020-07-30
     *
     * @return
     */
    @GetMapping(value = "/archivesData")
    public ResponseData archivesData(){
        int archivesCount = 0; //档案数
        int extantArchivesCount = 0;//现存档案数
        int evidenceCount = 0;//物证数
        int extantEvidenceCount =0;//现存物证数
        ArchivesEvidenceModel  archivesEvidenceModel =  new ArchivesEvidenceModel();//档案与物证数据总计Model
        try{
            List<DnaEvidenceCount> dnaEvidenceCountList = archivesEvidenceService.selectAllCount();//查询全部物证和档案信息集合
            if (dnaEvidenceCountList!=null && !dnaEvidenceCountList.isEmpty()) {
                for (DnaEvidenceCount dnaEvidenceCount : dnaEvidenceCountList) {
                    Integer archives = dnaEvidenceCount.getArchiveCount();
                    archivesCount += archives;//档案数目总计
                    Integer extantArchives = dnaEvidenceCount.getExtantArchiveCount();
                    extantArchivesCount += extantArchives;//现存档案数目总计
                    Integer evidence = dnaEvidenceCount.getEvidenceCount();
                    evidenceCount += evidence;//物证数目总计
                    Integer extantEvidence = dnaEvidenceCount.getExtantEvidenceCount();
                    extantEvidenceCount += extantEvidence;//现存物证数目总计
                }
                archivesEvidenceModel.setArchivesCount(archivesCount);//档案总数
                archivesEvidenceModel.setExtantArchivesCount(extantArchivesCount);//现存档案总数
                archivesEvidenceModel.setEvidenceCount(evidenceCount);//物证总数
                archivesEvidenceModel.setExtantEvidenceCount(extantEvidenceCount);//现存物证总数
                return new ResponseData(archivesEvidenceModel);
            }else{
                return new ResponseData(archivesEvidenceModel); //如果集合数据为空的话 直接返回空对象
            }
        }catch (Exception ex){
            logger.error("invoke ArchivesEvidenceController.archivesData error",ex.getMessage());
        }
        return new ResponseData();
    }
}
