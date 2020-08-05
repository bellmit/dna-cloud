package com.bazl.dna.database.core.controller.web.caseReport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bazl.dna.common.PageInfo;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.database.constants.Constants;
import com.bazl.dna.database.core.controller.BaseController;
import com.bazl.dna.database.service.mapper.DictItemMapper;
import com.bazl.dna.database.service.model.po.DictItem;
import com.bazl.dna.database.service.model.qo.CriminalReportQuery;
import com.bazl.dna.database.service.service.CriminalPersonInfoService;
import com.bazl.dna.database.service.service.CriminalSampleInfoService;
import com.bazl.dna.database.service.service.TransferCriminalQueueService;

/*
 * Created by Liuchang on 2020/6/9.
 * 建库人员上报监控
 */
@RestController
@RequestMapping("/criminalReport")
public class CriminalReportController extends BaseController {

    @Autowired
    CriminalPersonInfoService  criminalPersonInfoService;

    @Autowired
    CriminalSampleInfoService  criminalSampleInfoService;

    @Autowired
    TransferCriminalQueueService transferCriminalQueueService;

    @Autowired
    DictItemMapper dictItemMapper;

    /*
     * 查询建库人员上报信息接口--liuchang
     * @param query
     * @return
     */
     @PostMapping(value = "/queryCriminalReport")
     public ResponseData queryBuildReport(@RequestBody CriminalReportQuery query){
         //查询非空判断信息
         if (query!=null) {
             query = trimCriminalReportQueryPrams(query);
         }else {
             query = new CriminalReportQuery();
         }
         List<DictItem> sampleTypeDictItem = dictItemMapper.selectListByDictType(Constants.DICT_TPYE_CRIMINAL_PERSON_TYPE);//查询建库人员类型字典项
         try{
             List<CriminalReportQuery>  failedReportCriminalList = new ArrayList<>();    //建库失败上报集合
             List<CriminalReportQuery>  successReportCriminalList = new ArrayList<>();   //建库成功上报集合
             List<CriminalReportQuery>  waitReportCriminalList = new ArrayList<>();      //建库待上报集合
            //查询建库人员上报监控信息（分页查询）
             List<CriminalReportQuery> criminalReportQueryList = criminalPersonInfoService.selectCriminalReportList(query);
             if (criminalReportQueryList.size()>0 &&criminalReportQueryList!=null){
                 for (CriminalReportQuery criminalReport:criminalReportQueryList){
                    if (StringUtils.isNotBlank(criminalReport.getTransferStatus())){ //性别处理
                        if (criminalReport.getPersonSex().equals(Constants.GENDER_MALE_CODE)){
                            criminalReport.setPersonGenderName("男");
                        }else if(criminalReport.getPersonSex().equals(Constants.GENDER_FEMALE_CODE)){
                            criminalReport.setPersonGenderName("女");
                        }else {
                            criminalReport.setPersonGenderName("未知");
                        }
                        if (StringUtils.isNotBlank(criminalReport.getCriminalPersonType())){ //违法犯罪人员类型CODE
                            for (DictItem dictItem : sampleTypeDictItem) {
                                if (criminalReport.getCriminalPersonType()!=null){
                                    if (dictItem.getDictCode().equals(criminalReport.getCriminalPersonType())) {
                                        criminalReport.setCriminalPersonName(dictItem.getDictName());} //违法犯罪人员类型名称
                                }else{
                                    criminalReport.setCriminalPersonName("其他");
                                }
                            }
                        }
                        if (criminalReport.getTransferStatus().equals(Constants.TRANSFER_STATUS_FAILED)){
                            failedReportCriminalList.add(criminalReport);//建库失败上报集合
                        }else if (criminalReport.getTransferStatus().equals(Constants.TRANSFER_STATUS_SUCCESS)){
                            successReportCriminalList.add(criminalReport);//建库成功上报集合
                        }else if (criminalReport.getTransferStatus().equals(Constants.TRANSFER_STATUS_WAITED)){
                            waitReportCriminalList.add(criminalReport);//建库待上报集合
                        }
                    }
                 }
              }
              /*
              * 上报失败分页展示信息
              */
             PageInfo pageInfoFaiLedReport = new PageInfo(); //上报失败分页信息
             pageInfoFaiLedReport.setEvePageRecordCnt(query.getRows());//每页显示条数
             pageInfoFaiLedReport.setAllRecordCount(failedReportCriminalList.size());//总计条数
             if(failedReportCriminalList.size() >0 && failedReportCriminalList.size() % pageInfoFaiLedReport.getEvePageRecordCnt() == 0){ //总计页码
                 pageInfoFaiLedReport.setPageCount((int)failedReportCriminalList.size()/pageInfoFaiLedReport.getEvePageRecordCnt());
             }else{
                 pageInfoFaiLedReport.setPageCount((int)failedReportCriminalList.size()/pageInfoFaiLedReport.getEvePageRecordCnt()+1);
             }
             List<CriminalReportQuery>  failedReport = failedReportCriminalList;
             for (CriminalReportQuery caseReportQuery:failedReport){
                 caseReportQuery.setPageInfo(pageInfoFaiLedReport);//分页展示信息
             }
             /*
              * 上报成功分页展示信息
              */
             PageInfo pageInfoSuccessReport = new PageInfo(); //上报成功分页信息
             pageInfoSuccessReport.setEvePageRecordCnt(query.getRows());//每页显示条数
             pageInfoSuccessReport.setAllRecordCount(successReportCriminalList.size());//总计条数
             if(successReportCriminalList.size() >0 && successReportCriminalList.size() % pageInfoSuccessReport.getEvePageRecordCnt() == 0){ //总计页码
                 pageInfoSuccessReport.setPageCount((int)successReportCriminalList.size()/pageInfoSuccessReport.getEvePageRecordCnt());
             }else{
                 pageInfoSuccessReport.setPageCount((int)successReportCriminalList.size()/pageInfoSuccessReport.getEvePageRecordCnt()+1);
             }
             List<CriminalReportQuery>  successReport = successReportCriminalList;
             for (CriminalReportQuery caseReportQuery:successReport){
                 caseReportQuery.setPageInfo(pageInfoSuccessReport);//分页展示信息
             }
             /*
              * 待上报分页展示信息
              */
             PageInfo pageInfoWaitReport = new PageInfo(); //上报成功分页信息
             pageInfoWaitReport.setEvePageRecordCnt(query.getRows());//每页显示条数
             pageInfoWaitReport.setAllRecordCount(waitReportCriminalList.size());//总计条数
             if(waitReportCriminalList.size() >0 && waitReportCriminalList.size() % pageInfoWaitReport.getEvePageRecordCnt() == 0){ //总计页码
                 pageInfoWaitReport.setPageCount((int)waitReportCriminalList.size()/pageInfoWaitReport.getEvePageRecordCnt());
             }else{
                 pageInfoWaitReport.setPageCount((int)waitReportCriminalList.size()/pageInfoWaitReport.getEvePageRecordCnt()+1);
             }
             List<CriminalReportQuery>  waitReport = waitReportCriminalList;
             for (CriminalReportQuery caseReportQuery:waitReport){
                 caseReportQuery.setPageInfo(pageInfoWaitReport);//分页展示信息
             }

             Map<String, Object> resultObj = new HashMap<>();//返回数据
             resultObj.put("waitReportCriminalList",waitReportCriminalList);//待上报
             resultObj.put("successReportCriminalList",successReportCriminalList);//上报成功
             resultObj.put("failedReportCriminalList",failedReportCriminalList);//上报失败
             return new ResponseData(resultObj);
         }catch (Exception ex){
             logger.error("invoke CriminalReportController.queryCriminalReport error");
             return new ResponseData("查询建库人员上报信息接口出现异常!"+ex.getMessage());
         }
     }

     public CriminalReportQuery  trimCriminalReportQueryPrams(CriminalReportQuery query){
         //查询非空判断
        if (StringUtils.isNotBlank(query.getCriminalPersonType())){
            query.setCriminalPersonType(query.getCriminalPersonType().trim());//违法犯罪人员类型
        }
        if (StringUtils.isNotBlank(query.getSampleLabNo())){
            query.setSampleLabNo(query.getSampleLabNo().trim());//获取样本编号信息
        }
        if (StringUtils.isNotBlank(query.getPersonName())){
            query.setPersonName(query.getPersonName().trim());//人员姓名
        }
        if (StringUtils.isNotBlank(query.getPersonSex())){
            query.setPersonSex(query.getPersonSex().trim());//人员性别
        }
        if (StringUtils.isNotBlank(query.getPersonIdcardNo())){
            query.setPersonIdcardNo(query.getPersonIdcardNo().trim());//人员身份证号码
        }
        if (StringUtils.isNotBlank(query.getCollectOrgTopCode())){
            query.setCollectOrgTopCode(query.getCollectOrgTopCode().trim());//一级采集单位
        }
        if (StringUtils.isNotBlank(query.getCollectOrgSecondCode())){
            query.setCollectOrgSecondCode(query.getCollectOrgSecondCode().trim());//二级采集单位
        }
         return query;
    }
}
