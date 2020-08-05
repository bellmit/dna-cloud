package com.bazl.dna.database.nation.converter.task;

import java.util.Map;

import com.bazl.dna.database.nation.converter.service.LocusInfoService;
import com.bazl.dna.database.nation.converter.service.SysDictService;
import com.bazl.dna.database.nation.converter.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bazl.dna.database.nation.converter.service.impl.BaseService;

@Component
public class DataTask extends BaseService {

    @Autowired
    ConverterConfigs converterConfigs;

    @Autowired
    DataInfo dataInfo;

    @Autowired
    SysDictService sysDictService;

    @Autowired
    LocusInfoService locusInfoService;


    private synchronized void initSysData(){
        if(StaticPages.allDictList == null){
            StaticPages.allDictList = sysDictService.selectAll();
        }

        if(StaticPages.strLocusNameList == null){
            StaticPages.strLocusNameList = locusInfoService.selectNameByLocusType(Constants.GENE_TYPE_STR);
        }

        if(StaticPages.ystrLocusNameList == null){
            StaticPages.ystrLocusNameList = locusInfoService.selectNameByLocusType(Constants.GENE_TYPE_YSTR);
        }

    }

    /**
     *
     * 每个线程开启一个地市级的数据转换
     *
     * 案件委托数据转换
     *      Consignment.category = 1
     * initialDelay=15000: 启动后15秒开启任务
     * fixedRate = 60000： 每60秒执行一次
     */
    @Async
    @Scheduled(initialDelay=15000,fixedRate = 60000)
    public void caseInfoConverter(){
        if(!converterConfigs.isCaseConvertOpen()){
            logger.info("############  案件数据转换任务未开启！！！！");
            return;
        }

        initSysData();

        Map<String, String> labServerMap = converterConfigs.getLabServerMap();

        String processingLabServerNoPrefix = null;
        String processingLabServerName = null;

        for(Map.Entry<String, String> city : labServerMap.entrySet()){
            if(StaticPages.processingCaseList.contains(city.getKey())){
                continue;
            }

            processingLabServerNoPrefix = city.getKey();
            processingLabServerName = city.getValue();
            StaticPages.processingCaseList.add(processingLabServerNoPrefix);
            break;
        }

        if(processingLabServerNoPrefix != null){
            dataInfo.doConvertCaseInfo(processingLabServerNoPrefix, processingLabServerName);
        }
    }

    /**
     * 身份不明人员委托数据转换
     *      Consignment.category = 2
     * initialDelay=45000: 启动后45秒开启任务
     * fixedRate = 60000： 每60秒执行一次
     */
    @SuppressWarnings("unused")
	@Async
    @Scheduled(initialDelay=45000,fixedRate = 60000)
    public void unknownPersonConverter(){
        if(!converterConfigs.isUnknownPersonConvertOpen()){
            logger.info("############  身份不明人员数据转换任务未开启！！！！");
            return;
        }

        initSysData();

        Map<String, String> labServerMap = converterConfigs.getLabServerMap();

        String processingLabServerNoPrefix = null;
        String processingLabServerName = null;

        for(Map.Entry<String, String> city : labServerMap.entrySet()){
            if(StaticPages.processingUnknownPersonList.contains(city.getKey())){
                continue;
            }

            processingLabServerNoPrefix = city.getKey();
            processingLabServerName = city.getValue();
            StaticPages.processingUnknownPersonList.add(processingLabServerNoPrefix);
            break;
        }

        if(processingLabServerNoPrefix != null){
            //TODO
        }
    }

    /**
     * 失踪人口委托数据转换
     *      Consignment.category = 3
     * initialDelay=90000: 启动后90秒开启任务
     * fixedRate = 90000： 每90秒执行一次
     */
    @SuppressWarnings("unused")
	@Async
    @Scheduled(initialDelay=90000,fixedRate = 90000)
    public void missingPersonConverter(){
        if(!converterConfigs.isMissingPersonConvertOpen()){
            logger.info("############  失踪人口数据转换任务未开启！！！！");
            return;
        }

        initSysData();

        Map<String, String> labServerMap = converterConfigs.getLabServerMap();

        String processingLabServerNoPrefix = null;
        String processingLabServerName = null;

        for(Map.Entry<String, String> city : labServerMap.entrySet()){
            if(StaticPages.processingMissingPersonList.contains(city.getKey())){
                continue;
            }

            processingLabServerNoPrefix = city.getKey();
            processingLabServerName = city.getValue();
            StaticPages.processingMissingPersonList.add(processingLabServerNoPrefix);
            break;
        }

        if(processingLabServerNoPrefix != null){
            //TODO
        }
    }

    /**
     * 建库委托数据转换
     *      Consignment.category = 4
     * initialDelay=90000: 启动后90秒开启任务
     * fixedRate = 90000： 每90秒执行一次
     */
    @Async
    @Scheduled(initialDelay=90000,fixedRate = 90000)
    public void criminalPersonConverter(){
        if(!converterConfigs.isCriminalPersonConvertOpen()){
            logger.info("############  建库人员数据转换任务未开启！！！！");
            return;
        }

        initSysData();

        Map<String, String> labServerMap = converterConfigs.getLabServerMap();

        String processingLabServerNoPrefix = null;
        String processingLabServerName = null;

        for(Map.Entry<String, String> city : labServerMap.entrySet()){
            if(StaticPages.processingCriminalPersonList.contains(city.getKey())){
                continue;
            }

            processingLabServerNoPrefix = city.getKey();
            processingLabServerName = city.getValue();
            StaticPages.processingCriminalPersonList.add(processingLabServerNoPrefix);
            break;
        }

        if(processingLabServerNoPrefix != null){
            dataInfo.doConvertCriminalPersonInfo(processingLabServerNoPrefix, processingLabServerName);
        }
    }

    /**
     * 灾难委托数据转换
     *      Consignment.category = 5
     * initialDelay=180000: 启动后180秒开启任务
     * fixedRate = 90000： 每90秒执行一次
     */
    @SuppressWarnings("unused")
	@Async
    @Scheduled(initialDelay=180000,fixedRate = 90000)
    public void disasterPersonConverter(){
        if(!converterConfigs.isDisasterPersonConvertOpen()){
            logger.info("############  灾难人员数据转换任务未开启！！！！");
            return;
        }

        initSysData();

        Map<String, String> labServerMap = converterConfigs.getLabServerMap();

        String processingLabServerNoPrefix = null;
        String processingLabServerName = null;

        for(Map.Entry<String, String> city : labServerMap.entrySet()){
            if(StaticPages.processingDisasterPersonList.contains(city.getKey())){
                continue;
            }

            processingLabServerNoPrefix = city.getKey();
            processingLabServerName = city.getValue();
            StaticPages.processingDisasterPersonList.add(processingLabServerNoPrefix);
            break;
        }

        if(processingLabServerNoPrefix != null){
            //TODO
        }
    }

    /**
     * 质控委托数据转换
     *      Consignment.category = 6
     * initialDelay=300000: 启动后300秒开启任务
     * fixedRate = 90000： 每90秒执行一次
     */
    @Async
    @Scheduled(initialDelay=300000,fixedRate = 90000)
    public void qualityPersonConverter(){
        if(!converterConfigs.isQualityPersonConvertOpen()){
            logger.info("############  质控人员数据转换任务未开启！！！！");
            return;
        }

        initSysData();

        Map<String, String> labServerMap = converterConfigs.getLabServerMap();

        String processingLabServerNoPrefix = null;
        String processingLabServerName = null;

        for(Map.Entry<String, String> city : labServerMap.entrySet()){
            if(StaticPages.processingQualityPersonList.contains(city.getKey())){
                continue;
            }

            processingLabServerNoPrefix = city.getKey();
            processingLabServerName = city.getValue();
            StaticPages.processingQualityPersonList.add(processingLabServerNoPrefix);
            break;
        }

        if(processingLabServerNoPrefix != null){
            //TODO
            dataInfo.doConvertQualityPersonInfo(processingLabServerNoPrefix, processingLabServerName);
        }
    }

    /**
     * 基础对象委托数据转换
     *      Consignment.category = 8
     * initialDelay=300000: 启动后300秒开启任务
     * fixedRate = 90000： 每90秒执行一次
     */
    @SuppressWarnings("unused")
	@Async
    @Scheduled(initialDelay=300000,fixedRate = 90000)
    public void basicPersonConverter(){
        if(!converterConfigs.isBasicPersonConvertOpen()){
            logger.info("############  基础人员数据转换任务未开启！！！！");
            return;
        }

        initSysData();

        Map<String, String> labServerMap = converterConfigs.getLabServerMap();

        String processingLabServerNoPrefix = null;
        String processingLabServerName = null;

        for(Map.Entry<String, String> city : labServerMap.entrySet()){
            if(StaticPages.processingBasicPersonList.contains(city.getKey())){
                continue;
            }

            processingLabServerNoPrefix = city.getKey();
            processingLabServerName = city.getValue();
            StaticPages.processingBasicPersonList.add(processingLabServerNoPrefix);
            break;
        }

        if(processingLabServerNoPrefix != null){
            //TODO
        }
    }

    /**
     * 打拐儿童委托数据转换
     *      Consignment.category = 10
     * initialDelay=300000: 启动后300秒开启任务
     * fixedRate = 90000： 每90秒执行一次
     */
    @SuppressWarnings("unused")
	@Async
    @Scheduled(initialDelay=300000,fixedRate = 90000)
    public void abductionChildConverter(){
        if(!converterConfigs.isAbductionChildConvertOpen()){
            logger.info("############  打拐儿童数据转换任务未开启！！！！");
            return;
        }

        initSysData();

        Map<String, String> labServerMap = converterConfigs.getLabServerMap();

        String processingLabServerNoPrefix = null;
        String processingLabServerName = null;

        for(Map.Entry<String, String> city : labServerMap.entrySet()){
            if(StaticPages.processingAbductionChildList.contains(city.getKey())){
                continue;
            }

            processingLabServerNoPrefix = city.getKey();
            processingLabServerName = city.getValue();
            StaticPages.processingAbductionChildList.add(processingLabServerNoPrefix);
            break;
        }

        if(processingLabServerNoPrefix != null){
            //TODO
        }
    }

    /**
     * 打拐父母委托数据转换
     *      Consignment.category = 11
     * initialDelay=300000: 启动后300秒开启任务
     * fixedRate = 90000： 每90秒执行一次
     */
    @SuppressWarnings("unused")
	@Async
    @Scheduled(initialDelay=300000,fixedRate = 90000)
    public void abductionParentConverter(){
        if(!converterConfigs.isAbductionParentConvertOpen()){
            logger.info("############  打拐父母数据转换任务未开启！！！！");
            return;
        }

        initSysData();

        Map<String, String> labServerMap = converterConfigs.getLabServerMap();

        String processingLabServerNoPrefix = null;
        String processingLabServerName = null;

        for(Map.Entry<String, String> city : labServerMap.entrySet()){
            if(StaticPages.processingAbductionParentList.contains(city.getKey())){
                continue;
            }

            processingLabServerNoPrefix = city.getKey();
            processingLabServerName = city.getValue();
            StaticPages.processingAbductionParentList.add(processingLabServerNoPrefix);
            break;
        }

        if(processingLabServerNoPrefix != null){
            //TODO
        }
    }


/*

    //国家库数据同步mysql DnaData同步数据以案件为基础往外延伸,同步内容case_info,consignment_info,case_person_info,dna_sample_info,dna_str_gene_info,dna_ystr_gene_info
    @Async
    @Scheduled(initialDelay=5000,fixedRate = 60000) //每60s增加一次线程任务\
    public void caseInfoDataBase(){
        if(CaseIsOpen == 1){
            Integer caseInfoCount = converterConfigs.getCaseInfoCount();
            Integer pageSize = converterConfigs.getPageSize();
            //计算出总页数
            int totalPageCount = caseInfoCount%pageSize == 0 ? caseInfoCount/pageSize : (caseInfoCount/pageSize+1);

            //将initServerNo和分页索引拼起来判重
            String initServerNoLikeStr = null;
            int currentPageIdx = 0;
            String initServerNoAndIdx = null;

            *//**
             * 已经启动的线程，将该线程处理的页面放入全局List中，下次任务调度进入时，跳过已处理的页码
             *//*
            outer: for(String initServerNoLike : initServerNoLikeList) {
                initServerNoLikeStr = initServerNoLike;

                for (int idx = 1; idx <= totalPageCount; idx++) {
                    currentPageIdx = idx;
                    initServerNoAndIdx = initServerNoLike + "_" + idx;

                    if (StaticPages.processingCaseList.contains(initServerNoAndIdx)) {
                        continue;
                    } else {
                        StaticPages.processingCaseList.add(initServerNoAndIdx);
                        break;
                    }
                }
            }

            if(initServerNoAndIdx != null) {
                logger.info("******************开始处理第 " + initServerNoAndIdx + "页的数据。**************");
                dataInfo.taskCaseinfo(currentPageIdx, pageSize, initServerNoLikeStr);
            }
//            else{
//                logger.info("******************案件数据同步已结束*******************");
//            }
        }
    }*/

    /*//国家库数据同步mysql 同步内容组织机构信息
    @Scheduled(fixedDelay = 6000)
    public void OrgInfoDataBase(){
        //国家库里面直到分局级别，没有到技术队、派出所的层面故取消此定时
         caseInfoService.selectOrgInfoDataBase(1,initServerNoLike);
    }*/
//
//    //试剂盒,基因座同步
//    //@Scheduled(fixedDelay = 6000)
//    public void reagentDataBase(){
//        caseInfoService.reagentDataBase();
//    }


}
