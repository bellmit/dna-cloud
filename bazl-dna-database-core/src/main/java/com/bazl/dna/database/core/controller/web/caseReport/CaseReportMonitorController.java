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
import com.bazl.dna.database.service.model.po.TransferCaseSample;
import com.bazl.dna.database.service.model.qo.CaseReportQuery;
import com.bazl.dna.database.service.service.CaseInfoService;
import com.bazl.dna.database.service.service.CasePersonInfoService;
import com.bazl.dna.database.service.service.DnaSampleInfoService;
import com.bazl.dna.database.service.service.TransferCaseSampleService;

/**
 * Created by Liuchang on 2020/6/7--案件上报监控接口
 */
@RestController
@RequestMapping("/caseReport")
public class CaseReportMonitorController extends BaseController{

    @Autowired
    CaseInfoService  caseInfoService;
    @Autowired
    CasePersonInfoService casePersonInfoService;
    @Autowired
    DnaSampleInfoService  dnaSampleInfoService;
    @Autowired
    DictItemMapper  dictItemMapper;
    @Autowired
    TransferCaseSampleService transferCaseSampleService;
    /**
     * 查询案件上报信息接口--liuchang
     * @return
     */
    @PostMapping(value = "/queryCaseReport")
    @SuppressWarnings("all")
    public ResponseData queryCaseReport(@RequestBody CaseReportQuery query){
        List<DictItem> sampleTypeDictItem = dictItemMapper.selectListByDictType(Constants.DICT_TPYE_CASE_PROPERTY);//查询案件性质字典项

        if (query != null) {
            query = trimCaseReportQueryPrams(query); //查询全部检索条件
        }else{
            query = new CaseReportQuery();
        }
         try{
            //查询案件上报信息接口
             List<CaseReportQuery>  failedReportCaseList = new ArrayList<>();    //失败上报案件集合
             List<CaseReportQuery>  successReportCaseList = new ArrayList<>();   //成功上报案件集合
             List<CaseReportQuery>  waitReportCaseList = new ArrayList<>();      //待上报案件集合
             List<TransferCaseSample> successTransferSampleCount = new ArrayList<>(); //上报成功检材数
             List<TransferCaseSample> failTransferSampleCount = new ArrayList<>();    //上报失败检材数
             List<CaseReportQuery> caseReportQueryList = caseInfoService.selectCaseReportList(query);
             for (CaseReportQuery caseInfo: caseReportQueryList){
                 //查询案件性质名称根据案件性质字典项
                 for (DictItem dictItem : sampleTypeDictItem) {
                     if (caseInfo.getCaseProperty()!=null){
                         if(dictItem.getDictCode().equals(caseInfo.getCaseProperty())) {
                             caseInfo.setCasePropertyName(dictItem.getDictName());} //案件性质名称
                     }else{
                         caseInfo.setCasePropertyName("其他");
                     }
                 }
                 if (StringUtils.isNotBlank(caseInfo.getTransferStatus())){
                 /*
                 * 上报失败信息
                 */
                 if (caseInfo.getTransferStatus().equals(Constants.TRANSFER_STATUS_FAILED)){
                         successTransferSampleCount.clear();//清空集合数据-上报成功样本数
                         failTransferSampleCount.clear();//清空集合数据--上报失败样本数
                         //根据案件ID查询提交入库检材数
                         List<TransferCaseSample> transferCaseSample = transferCaseSampleService.selectByCaseQueueId(caseInfo.getTransferCaseQueueId());
                         if (transferCaseSample.size()>0 && transferCaseSample!=null){
                             caseInfo.setSumbitSampleCount(transferCaseSample.size()); //入库检材数
                             for (TransferCaseSample caseSample:transferCaseSample){
                                 if (caseSample.getTransferStats().equals(1)){
                                     successTransferSampleCount.add(caseSample);      //上报成功样本数
                                 }else if (caseSample.getTransferStats().equals(2)){
                                     failTransferSampleCount.add(caseSample);         //上报失败样本数
                                 }
                             }
                         }
                         if (successTransferSampleCount.size()>0) {
                             caseInfo.setSuccessTransferSampleCount(successTransferSampleCount.size());//入库成功检材数
                         }else{
                             caseInfo.setSuccessTransferSampleCount(0);}
                         if (failTransferSampleCount.size()>0) {
                             caseInfo.setFailTransferSampleCount(failTransferSampleCount.size());     //入库失败检材数
                         }else{
                             caseInfo.setFailTransferSampleCount(0);}
                         failedReportCaseList.add(caseInfo);  //上报失败案件集合
                 /*
                 * 上报成功信息
                 */
               }else if (caseInfo.getTransferStatus().equals(Constants.TRANSFER_STATUS_SUCCESS)){
                          successTransferSampleCount.clear();//清空集合数据-上报成功样本数
                          failTransferSampleCount.clear();//清空集合数据--上报失败样本数
                         //根据案件ID查询提交入库检材数
                         List<TransferCaseSample> transferCaseSample = transferCaseSampleService.selectByCaseQueueId(caseInfo.getTransferCaseQueueId());
                         if (transferCaseSample.size()>0 && transferCaseSample!=null){
                             caseInfo.setSumbitSampleCount(transferCaseSample.size());//入库检材数
                             for (TransferCaseSample caseSample:transferCaseSample){
                                 if (caseSample.getTransferStats().equals(1)){
                                     successTransferSampleCount.add(caseSample);//上报成功样本数
                                 }else if (caseSample.getTransferStats().equals(2)){
                                     failTransferSampleCount.add(caseSample);//上报失败样本数
                                 }
                             }
                         }
                         if(successTransferSampleCount.size()>0) {
                             caseInfo.setSuccessTransferSampleCount(successTransferSampleCount.size()); //入库成功检材数
                         }else{
                             caseInfo.setSuccessTransferSampleCount(0);}
                         if (failTransferSampleCount.size()>0) {
                             caseInfo.setFailTransferSampleCount(failTransferSampleCount.size()); //入库失败检材数
                         }else{
                             caseInfo.setFailTransferSampleCount(0);}
                             successReportCaseList.add(caseInfo);        //上报成功案件集合
                /*
                 * 待上报信息
                 */
               }else if (caseInfo.getTransferStatus().equals(Constants.TRANSFER_STATUS_WAITED)){
                         //根据案件ID查询提交入库检材数
                         List<TransferCaseSample> transferCaseSample = transferCaseSampleService.selectByCaseQueueId(caseInfo.getTransferCaseQueueId());
                         if (transferCaseSample.size()>0 && transferCaseSample!=null){
                             caseInfo.setSumbitSampleCount(transferCaseSample.size());}//入库检材数
                             waitReportCaseList.add(caseInfo);//待上报案件集合
                    }
                 }
             }
             /*
              * 上报失败分页展示信息
              */
             PageInfo pageInfoFaiLedReport = new PageInfo(); //上报失败分页信息
             pageInfoFaiLedReport.setEvePageRecordCnt(query.getRows());//每页显示条数
             pageInfoFaiLedReport.setAllRecordCount(failedReportCaseList.size());//总计条数
             if(failedReportCaseList.size() >0 && failedReportCaseList.size() % pageInfoFaiLedReport.getEvePageRecordCnt() == 0){ //总计页码
                 pageInfoFaiLedReport.setPageCount((int)failedReportCaseList.size()/pageInfoFaiLedReport.getEvePageRecordCnt());
             }else{
                 pageInfoFaiLedReport.setPageCount((int)failedReportCaseList.size()/pageInfoFaiLedReport.getEvePageRecordCnt()+1);
             }
             List<CaseReportQuery>  failedReport = failedReportCaseList;
             for (CaseReportQuery caseReportQuery:failedReport){
                 caseReportQuery.setPageInfo(pageInfoFaiLedReport);//分页展示信息
             }
             /*
              * 上报成功分页展示信息
              */
             PageInfo pageInfoSuccessReport = new PageInfo(); //上报成功分页信息
             pageInfoSuccessReport.setEvePageRecordCnt(query.getRows());//每页显示条数
             pageInfoSuccessReport.setAllRecordCount(successReportCaseList.size());//总计条数
             if(successReportCaseList.size() >0 && successReportCaseList.size() % pageInfoSuccessReport.getEvePageRecordCnt() == 0){ //总计页码
                 pageInfoSuccessReport.setPageCount((int)successReportCaseList.size()/pageInfoSuccessReport.getEvePageRecordCnt());
             }else{
                 pageInfoSuccessReport.setPageCount((int)successReportCaseList.size()/pageInfoSuccessReport.getEvePageRecordCnt()+1);
             }
             List<CaseReportQuery>  successReport = successReportCaseList;
             for (CaseReportQuery caseReportQuery:successReport){
                 caseReportQuery.setPageInfo(pageInfoSuccessReport);//分页展示信息
             }
             /*
              * 待上报分页展示信息
              */
             PageInfo pageInfoWaitReport = new PageInfo(); //上报成功分页信息
             pageInfoWaitReport.setEvePageRecordCnt(query.getRows());//每页显示条数
             pageInfoWaitReport.setAllRecordCount(waitReportCaseList.size());//总计条数
             if(waitReportCaseList.size() >0 && waitReportCaseList.size() % pageInfoWaitReport.getEvePageRecordCnt() == 0){ //总计页码
                 pageInfoWaitReport.setPageCount((int)waitReportCaseList.size()/pageInfoWaitReport.getEvePageRecordCnt());
             }else{
                 pageInfoWaitReport.setPageCount((int)waitReportCaseList.size()/pageInfoWaitReport.getEvePageRecordCnt()+1);
             }
             List<CaseReportQuery>  waitReport = waitReportCaseList;
             for (CaseReportQuery caseReportQuery:waitReport){
                 caseReportQuery.setPageInfo(pageInfoWaitReport);//分页展示信息
             }

             Map<String, Object> resultObj = new HashMap<>();//返回数据
             resultObj.put("waitReportCaseList",waitReportCaseList);//待上报
             resultObj.put("successReportCaseList",successReportCaseList);//上报成功
             resultObj.put("failedReportCaseList",failedReportCaseList);//上报失败
             return new ResponseData(resultObj);
        }catch (Exception ex){
            logger.error("invoke CaseReportMonitorController.queryCaseReport error!");
            return new ResponseData("查询案件上报信息接口失败！"+ex.getMessage());
        }
     }

        //案件上报信息查询检索条件--liuchang
        public CaseReportQuery  trimCaseReportQueryPrams(CaseReportQuery query){
            if (StringUtils.isNotBlank(query.getCaseProperty())){
                 query.setCaseProperty(query.getCaseProperty());//案件性质
            }
            if (StringUtils.isNotBlank(query.getCaseName())){
                query.setCaseName(query.getCaseName().trim());//案件名称
            }
            if (StringUtils.isNotBlank(query.getCaseAcceptNo())){
                query.setCaseAcceptNo(query.getCaseAcceptNo().trim());//案件受理编号
            }
            if (StringUtils.isNotBlank(query.getSampleNo())){
                query.setSampleNo(query.getSampleNo().trim());//检材编号
            }
            if (StringUtils.isNotBlank(query.getSampleName())){
                query.setSampleName(query.getSampleName().trim());//检材名称
            }
            if (StringUtils.isNotBlank(query.getAcceptPersonName())){
                query.setAcceptPersonName(query.getAcceptPersonName().trim());//受理人姓名
            }
            return query;
       }
}
