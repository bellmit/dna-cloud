package com.bazl.dna.mix.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.annotation.CurrentUser;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.common.filter.AuthUser;
import com.bazl.dna.mix.constants.Constants;
import com.bazl.dna.mix.constants.ErrorCodes;
import com.bazl.dna.mix.constants.ErrorInfo;
import com.bazl.dna.mix.controller.base.BaseController;
import com.bazl.dna.mix.controller.base.ResultBean;
import com.bazl.dna.mix.controller.base.error.ErrorCode;
import com.bazl.dna.mix.model.po.CompareQueue;
import com.bazl.dna.mix.model.po.MixedSampleGene;
import com.bazl.dna.mix.model.po.SampleInfo;
import com.bazl.dna.mix.model.po.SplitedSampleGene;
import com.bazl.dna.mix.model.vo.SplitedSampleGeneVo;
import com.bazl.dna.mix.service.CompareQueueService;
import com.bazl.dna.mix.service.MixedSampleGeneService;
import com.bazl.dna.mix.service.SampleInfoService;
import com.bazl.dna.mix.service.SplitedSampleGeneService;
import com.bazl.dna.mix.utils.GeneSameCompareUtil;
import com.bazl.dna.mix.utils.GeneformatUtils;
import com.bazl.dna.mix.utils.ListUtils;
import com.bazl.dna.mix.utils.RepetitionUtils;
import com.bazl.dna.mix.utils.SampleNumUtil;
import com.bazl.dna.util.RequestUtils;

/**
 * Created by Administrator on 2019/11/19.
 *
 *      拆分比对队列
 */
@RestController
@RequestMapping("/split")
public class SplitController extends BaseController {

    @Autowired
    CompareQueueService compareQueueService;
    @Autowired
    MixedSampleGeneService mixedSampleGeneService;
    @Autowired
    SplitedSampleGeneService splitedSampleGeneService;
    @Autowired
    SampleInfoService sampleInfoService;
    @Autowired
    GeneSameCompareUtil geneSameCompareUtil;
    @Autowired
    RepetitionUtils repetitionUtils;
    @Autowired
    SampleNumUtil sampleNumUtil;

    /**
     *  快速拆分比   拆分入库
     * @throws Exception 
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Transactional
    @RequestMapping(value = "/addSplit", produces = "application/json;charset=UTF-8")
    public ResultBean addSplit(@CurrentUser AuthUser authUser, 
    		String geneInfo, String condition, String fileName, String contributorName) throws Exception {
        if (geneInfo != null) {
            Map<String, Object> result = new HashMap<>();
            //获取登录信息
            //查询库里是否有文件名称和贡献者相同数据
//            CompareContribution contribution = new CompareContribution();
//            contribution.setFileName(fileName);
//            contribution.setQueueType("2");
//            contribution.setContributorName(contributorName);
//            contribution.setCreatePerson(user.getId());
//            List<CompareContribution> contributionList = compareContributionService.selectByList(contribution);
            //基因型转化
            List<Map<String, Object>> jsonArray = null;
            jsonArray = (List) JSONObject.parseArray(geneInfo);
            //用来存储到数据库的格式
            Map<String, Object> geneMap = new HashMap<>();
            //对前台给的json字符串进行格式转换
            for (int i = 0; i < jsonArray.size(); i++) {
                String markerName = jsonArray.get(i).get("geneName").toString();
                String allele = jsonArray.get(i).get("allele").toString();
                String[] allele1 = allele.split(",");
                geneMap.put(markerName, allele1);
            }
//            String geneInfos = JSON.toJSONString(geneMap);
//            CompareContribution compareContribution = new CompareContribution();
//            compareContribution.setGeneInfo(geneInfos);//基因信息
//            compareContribution.setCondition(String.valueOf(condition));//容差
//            compareContribution.setFileName(fileName);//文件名称
//            compareContribution.setContributorName(contributorName);//贡献者
//            compareContribution.setCreatePerson(user.getId());
            //有判断基因是否相同,没有直接添加
            /*if (ListUtils.isNotNullAndEmptyList(contributionList)){
                int count = repetitionUtils(contributionList,geneMap);
                if (count == contributionList.size()) {
                    //将拆分基因型插入到比对队列表中
                    addSplitCompare(compareContribution);
                    result.put("code", 0);
                } else {
                    //有重复的基因信息
                    result.put("code", 1);
                }
            }else {
                addSplitCompare(compareContribution);
                result.put("code", 0);
            }*/
            return new ResultBean(ResultBean.CODE_SUCCESS, 0, result, ResultBean.NAME_SUCCESS);
        }else {
            logger.error("传入参数为空!");
            return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1, "传入参数为空!");
        }
    }

    /*
   *      案件智能模块  拆分样本加入比对（新版）
   * */
    @RequestMapping(value = "/addSplitCaseTask", produces = "application/json;charset=UTF-8")
    public ResultBean addSplitCaseTask(CompareQueue compareQueue) throws Exception {
        Map<String, Object> result = new HashMap<>();

        if(compareQueue != null){
            //获取登录信息
            AuthUser user = RequestUtils.getAuthUser();
            compareQueue.setCreatePerson(user.getId());
            //前端传来的基因转换
//        String geneInfo = geneSameCompareUtil.getGeneInfo(compareQueue.getGeneInfo());
            String geneInfo = geneSameCompareUtil.markerNameGeneInfo(compareQueue.getGeneInfo());
            compareQueue.setGeneInfo(geneInfo);
            //来源
            compareQueue.setSource(Constants.SOURCE_TYPE_1);

            //判断是混合还是拆分基因
            if (compareQueue.getQueueType().equals(Constants.QUEUE_TYPE_01)){
                CompareQueue queue = new CompareQueue();
                queue.setMixedSampleId(compareQueue.getMixedSampleId());
                queue.setQueueType(Constants.QUEUE_TYPE_01);//混合
                //查看要加入的混合样本是否已存在队列
                List<CompareQueue> compareQueueList = compareQueueService.selectQueueList(queue);
                //如果没有当前混合样本，便可以直接加入
                if (ListUtils.isNullOrEmptyList(compareQueueList)){
                    saveSplitCase(compareQueue);
                    result.put("code", 0);
                }else {
                    //判断之前拆分是否添加了混合样本
                    CompareQueue compare = compareQueueList.get(0);
                    if (StringUtils.isBlank(compare.getQueueFlag())){
                        //删除之前的混合样本
                        compareQueueService.deleteByPrimaryKey(compare.getId());
                        saveSplitCase(compareQueue);
                        result.put("code", 0);
                    }else{
                        //如果有，那么基因信息已存在
                        result.put("code", 1);
                    }
                }
            }else if (compareQueue.getQueueType().equals(Constants.QUEUE_TYPE_02)){
                CompareQueue queue = new CompareQueue();
                queue.setMixedSampleId(compareQueue.getMixedSampleId());
                queue.setQueueType(Constants.QUEUE_TYPE_02);//拆分
                //通过混合id查询当前混合样本拆分的基因信息条数
                List<CompareQueue> compareQueueList = compareQueueService.selectQueueList(queue);
                //如果没有拆分，可以直接加入
                if (ListUtils.isNullOrEmptyList(compareQueueList)){
                    saveSplitCase(compareQueue);
                    result.put("code", 0);
                }else {
                    //如果有拆分信息，先判断基因信息是否重复
                    int determine = repetitionUtils.Determine(compareQueueList, compareQueue.getGeneInfo());
                    //条数相同表示没有拆分一样的基因信息
                    if (determine == compareQueueList.size()){
                        saveSplitCase(compareQueue);
                        result.put("code", 0);
                    }else {
                        //条数不同表示有拆分一样的基因信息
                        result.put("code", 1);
                    }
                }
            }
            return new ResultBean(ResultBean.CODE_SUCCESS, 0, result, ResultBean.NAME_SUCCESS);
        }else {
            logger.error("传入参数为空");
            return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1, "传入参数为空!");
        }
    }

    public void saveSplitCase(CompareQueue compareQueue) {
        try {
            //添加队列信息
            compareQueue.setId(UUID.randomUUID().toString());
            compareQueue.setStatus(Constants.MATCH_STATUS_02);//未必对
            compareQueue.setQueueFlag(Constants.QUEUE_FLAG_0);//不比对
            compareQueue.setCreateDatetime(new Date());//入库时间
            compareQueue.setServeNo("");
            compareQueueService.insert(compareQueue);
            logger.info("====添加队列信息===="+ LocalDateTime.now().toLocalTime());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("添加比对队列失败" + e.getMessage());
        }
    }

    /*
  *      案件智能模块---查看比对任务（新版）
  * */
    @RequestMapping(value = "/findCompareTaskList", produces = "application/json;charset=UTF-8")
    public ResponseData findCompareTaskList(String mixedSampleId) throws IOException {
        if (mixedSampleId != null){
            CompareQueue compareQueue = new CompareQueue();
            compareQueue.setMixedSampleId(mixedSampleId);
            compareQueue.setQueueFlag(Constants.QUEUE_FLAG_0);
            List<CompareQueue> compareQueueList = compareQueueService.selectQueueList(compareQueue);
            /*if (ListUtils.isNotNullAndEmptyList(compareQueueList)){
                for (CompareQueue queue : compareQueueList){
                    queue.setGeneImagePath(String.valueOf(0));
                    if (queue.getQueueType().equals(Constants.QUEUE_TYPE_01)){
                        queue.setQueueType("混合");
                    }else {
                        queue.setQueueType("拆分");
                    }
                }
            }*/
            return new ResponseData(compareQueueList);
        }else {
            logger.error("传入参数为空");
            return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
        }
    }

    /*
  *      案件智能模块---查看比对任务条数（新版）
  * */
    @RequestMapping(value = "/selectCount", produces = "application/json;charset=UTF-8")
    public ResponseData selectCount(String mixedSampleId) throws IOException {
        Map<String, Object> result = new HashMap<>();
        if (mixedSampleId != null){
            CompareQueue compareQueue = new CompareQueue();
            compareQueue.setMixedSampleId(mixedSampleId);
            compareQueue.setQueueFlag(Constants.QUEUE_FLAG_0);
            List<CompareQueue> compareQueueList = compareQueueService.selectQueueList(compareQueue);
            int size = compareQueueList.size();
            result.put("count",size);
            return new ResponseData(result);
        }else {
            logger.error("传入参数为空");
            return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
        }
    }

    /*
   *      案件智能模块---查看基因信息（新）
   * */
    @RequestMapping(value = "/findGeneTask", produces = "application/json;charset=UTF-8")
    public ResponseData findGeneTask(String id) throws IOException {
        if (id != null){
            Map<String, Object> result = new HashMap<>();
            CompareQueue compareQueue = compareQueueService.selectByPrimaryKey(id);
            if (compareQueue != null ){
                /*//返回给前端的基因信息
                Map<String, List<String>> stringListMap = GeneformatUtils.mixedSampleGeneformat(compareQueue.getGeneInfo());
                //判空
                if (stringListMap != null) {
//                    Map<String, Object> stringObjectMap = geneSameCompareUtil.analysisGene(stringListMap);
                    Map<String, Object> stringObjectMap = geneSameCompareUtil.analysisMarkerNameGene();
                    result.put("GeneMap",stringObjectMap);
                }
                //基因图谱
                if (null != compareQueue.getGenePicture()){
                    result.put("geneImagePath",compareQueue.getGenePicture());
                }else{
                    result.put("geneImagePath",0);
                }*/
                //查询对应的数据获取基因信息
                if (compareQueue.getQueueType().equals(Constants.QUEUE_TYPE_01)){
                    //如果是混合类型
                    MixedSampleGene sampleGene = mixedSampleGeneService.selectById(compareQueue.getMixedSampleId());
                    if (sampleGene != null){
                        //基因格式转换
                        Map<String, Object> stringListMap = GeneformatUtils.sampleformatList(sampleGene.getGeneInfo());
                        result.put("GeneMap",stringListMap);
                        result.put("geneImagePath",sampleGene.getGenePicture());
                    }
                }else if (compareQueue.getQueueType().equals(Constants.QUEUE_TYPE_02)){
                    //如果是拆分类型
                    SplitedSampleGene splitedSampleGene = splitedSampleGeneService.selectByCompareQueueId(compareQueue.getId());
                    if (splitedSampleGene != null){
                        Map<String, Object> stringListMap = GeneformatUtils.sampleformatList(splitedSampleGene.getGeneInfo());
                        result.put("GeneMap",stringListMap);
                        result.put("geneImagePath",0);
                    }
                }
                //样本编号
                result.put("sampleNo",compareQueue.getSampleNo());
            }
            return new ResponseData(result);
        }else {
            logger.error("传入参数为空");
            return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
        }
    }

    /*
    *      案件智能模块   删除队列任务（新）
    * */
    @RequestMapping(value = "/deleteCompareQueue", produces = "application/json;charset=UTF-8")
    public ResultBean deleteCompareQueue(String id) throws IOException {
        if (id != null){
            int i = compareQueueService.deleteByPrimaryKey(id);
            return new ResultBean(ResultBean.CODE_SUCCESS, 0, i, ResultBean.NAME_SUCCESS);
        }else {
            logger.error("传入参数为空");
            return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1, "传入参数为空!");
        }
    }

    /*
      *   拆分报告---加入比对
      * */
    @RequestMapping(value = "/addSTRSplitCaseTask", produces = "application/json;charset=UTF-8")
    public ResponseData addSTRSplitCaseTask(@RequestBody CompareQueue compareQueue) throws Exception {
        try {
            if (compareQueue != null && null != compareQueue.getMixedSampleId()){
                Map<String, Object> result = new HashMap<>();
                //获取登录信息
                AuthUser user = RequestUtils.getAuthUser();
                if (user != null){
                    compareQueue.setCreatePerson(user.getId());
                }
                //先添加混合信息  没有就添加
                MixedSampleGene mixedSampleGene = mixedSampleGeneService.selectById(compareQueue.getMixedSampleId());
                if (mixedSampleGene == null){
                    MixedSampleGene mixedSample = compareQueue.getMixedSampleGene();
                    mixedSample.setId(compareQueue.getMixedSampleId());
                    mixedSample.setIsDeleted(Constants.IS_DELETED_0);
                    mixedSample.setCreateDatetime(new Date());
                    if (user != null){
                        mixedSample.setCreatePerson(user.getId());
                    }
                    String mixGeneInfo = GeneSameCompareUtil.markerNameGene(mixedSample.getGeneInfo());
                    mixedSample.setGeneInfo(mixGeneInfo);
                    try {
                        mixedSampleGeneService.insert(mixedSample);
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("添加混合基因报错！"+e.getMessage());
                        return new ResponseData(0, "添加混合基因报错!");
                    }
                }
                //前端传来的基因转换
    //            String geneInfo = geneSameCompareUtil.getGeneInfo(compareQueue.getGeneInfo());
                String geneInfo = geneSameCompareUtil.getGeneNameInfo(compareQueue.getGeneInfo());
                compareQueue.setSource(Constants.SOURCE_TYPE_2);//来源
                //查询拆分信息
                List<SplitedSampleGene> splitedSampleGenes = splitedSampleGeneService.selectSplitedSampleGeneByMixedSampleGeneId(compareQueue.getMixedSampleId());
                if (ListUtils.isNullOrEmptyList(splitedSampleGenes)){
                    //添加队列信息
                    saveSplitCase(compareQueue);
                    //没有拆分信息直接添加
                    SplitedSampleGene splitedGene = new SplitedSampleGene();
                    splitedGene.setId(UUID.randomUUID().toString());
                    splitedGene.setMixedSampleGeneId(compareQueue.getMixedSampleId());
                    splitedGene.setGeneInfo(geneInfo);
                    if (user != null){
                        splitedGene.setCreatePerson(user.getId());
                    }
                    splitedGene.setCreateDatetime(new Date());
                    splitedGene.setCompareQueueId(compareQueue.getId());
                    try {
                        splitedSampleGeneService.insert(splitedGene);
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("添加拆分基因报错！"+e.getMessage());
                        return new ResponseData(0, "添加拆分基因报错!");
                    }
                    result.put("code", 0);
                }else {
                    //如果有拆分信息，先判断基因信息是否重复
                    int determine = repetitionUtils.DetermineNew(splitedSampleGenes, geneInfo);
                    if (determine == splitedSampleGenes.size()) {
                        //添加队列
                        saveSplitCase(compareQueue);
                        //添加拆分基因
                        SplitedSampleGene splitedGene = new SplitedSampleGene();
                        splitedGene.setId(UUID.randomUUID().toString());
                        splitedGene.setMixedSampleGeneId(compareQueue.getMixedSampleId());
                        splitedGene.setGeneInfo(geneInfo);
                        if (user != null){
                            splitedGene.setCreatePerson(user.getId());
                        }
                        splitedGene.setCreateDatetime(new Date());
                        splitedGene.setCompareQueueId(compareQueue.getId());
                        try {
                            splitedSampleGeneService.insert(splitedGene);
                        } catch (Exception e) {
                            e.printStackTrace();
                            logger.error("添加拆分基因报错！" + e.getMessage());
                            return new ResponseData(0, "添加拆分基因报错!");
                        }
                        result.put("code", 0);
                    } else {
                        //条数不同表示有拆分一样的基因信息
                        result.put("code", 1);
                    }
                }
                /*//查询拆分的队列信息
                CompareQueue queue = new CompareQueue();
                queue.setMixedSampleId(compareQueue.getMixedSampleId());
                queue.setQueueType(Constants.QUEUE_TYPE_02);//拆分
                //通过混合查询当前混合样本拆分的基因信息条数
                List<CompareQueue> compareQueueList = compareQueueService.selectQueueList(queue);
                //如果没有拆分，可以直接加入
                if (ListUtils.isNullOrEmptyList(compareQueueList)){
                    //如果为混合分型说明是手动处的STR拆分，那么需要样本编号
                    *//*if (compareQueue.getQueueType().equals(Constants.QUEUE_TYPE_01)){
                        compareQueue.setQueueType(Constants.QUEUE_TYPE_02);
                        String sample_no = sampleNumUtil.getNextSampleNoVal(userInfo.getUserId(), "SAMPLE_NO");
                        compareQueue.setSampleNo(sample_no);
                    }*//*
                    saveSplitCase(compareQueue);
                    result.put("code", 0);
                }else {
                    //如果有拆分信息，先判断基因信息是否重复
                    int determine = repetitionUtils.DetermineNew(splitedSampleGeneList, geneInfo);
                    int determine = repetitionUtils.Determine(compareQueueList, compareQueue.getGeneInfo());
                    //条数相同表示没有拆分一样的基因信息
                    if (determine == compareQueueList.size()){
                        //如果为混合分型说明是手动处的STR拆分，那么需要样本编号
                        *//*if (compareQueue.getQueueType().equals(Constants.QUEUE_TYPE_01)){
                            compareQueue.setQueueType(Constants.QUEUE_TYPE_02);
                            String sample_no = sampleNumUtil.getNextSampleNoVal(userInfo.getUserId(), "SAMPLE_NO");
                            compareQueue.setSampleNo(sample_no);
                        }*//*
                        saveSplitCase(compareQueue);
                        result.put("code", 0);
                    }else {
                        //条数不同表示有拆分一样的基因信息
                        result.put("code", 1);
                    }
                }*/
                return new ResponseData(result);
            }else {
                logger.error("传入参数为空");
                return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
            }
        } catch (Exception e) {
            logger.error("添加错误！" + e.getMessage());
            return new ResponseData(0, "添加错误!");
        }
    }

    /*
    *      案件智能模块   查看拆分任务
    * */
    @RequestMapping(value = "/findSplitCaseTaskList", produces = "application/json;charset=UTF-8")
    public ResultBean findSplitCaseTaskList(String id) throws IOException {
        if (id != null){
            MixedSampleGene gene = mixedSampleGeneService.selectById(id);
            SampleInfo sampleInfo = sampleInfoService.selectBySampleId(gene.getSampleId());
            List<SplitedSampleGeneVo> splitedSampleGeneVoList = new ArrayList<>();
            if (gene != null){
                List<SplitedSampleGene> splitedSampleGenes = splitedSampleGeneService.selectSplitedSampleGeneByMixedSampleGeneId(gene.getId());
                if (ListUtils.isNotNullAndEmptyList(splitedSampleGenes)){
                    for (SplitedSampleGene sampleGene : splitedSampleGenes){
                        SplitedSampleGeneVo sampleGeneVo = new SplitedSampleGeneVo();
                        sampleGeneVo.setSampleName(sampleInfo.getSampleName());
                        Map<String, List<String>> stringListMap = GeneformatUtils.mixedSampleGeneformat(sampleGene.getGeneInfo());
                        //判空
                        if (stringListMap != null) {
                            Map<String, Object> stringObjectMap = geneSameCompareUtil.analysisGene(stringListMap);
                            sampleGeneVo.setGeneMap(stringObjectMap);
                        }
                        sampleGeneVo.getEntity().setId(sampleGene.getId());
                        splitedSampleGeneVoList.add(sampleGeneVo);
                    }
                }
            }
            return new ResultBean(ResultBean.CODE_SUCCESS, 0, splitedSampleGeneVoList, ResultBean.NAME_SUCCESS);
        }else {
            logger.error("传入参数为空");
            return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1, "传入参数为空!");
        }
    }

    /*
   *      案件智能模块   删除拆分任务
   * */
    @RequestMapping(value = "/deleteSplitCaseTask", produces = "application/json;charset=UTF-8")
    public ResultBean deleteSplitCaseTask(String id) throws IOException {
        if (id != null){
            int i = splitedSampleGeneService.deleteByPrimaryKey(id);
            return new ResultBean(ResultBean.CODE_SUCCESS, 0, i, ResultBean.NAME_SUCCESS);
        }else {
            logger.error("传入参数为空");
            return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1, "传入参数为空!");
        }
    }

    /*
  *      快速拆分   查看拆分任务
  * */
    @RequestMapping(value = "/findTaskList", produces = "application/json;charset=UTF-8")
    public ResultBean findTaskList(String fileName) throws IOException {
        if (fileName != null){
//            CompareContribution contribution = new CompareContribution();
//            contribution.setFileName(fileName);
//            contribution.setQueueType("2");
//            List<CompareContribution> contributionList = compareContributionService.selectByList(contribution);
            /*if (ListUtils.isNotNullAndEmptyList(contributionList)){
                for (CompareContribution compareContribution : contributionList){
                    Map<String, List<String>> stringListMap = GeneformatUtils.mixedSampleGeneformat(compareContribution.getGeneInfo());
                    //判空
                    if (stringListMap != null) {
                        Map<String, Object> stringObjectMap = geneSameCompareUtil.analysisGene(stringListMap);
                        compareContribution.setGeneMap(stringObjectMap);
                    }
                }
            }*/
            return new ResultBean(ResultBean.CODE_SUCCESS, 0, 0, ResultBean.NAME_SUCCESS);
        }else {
            logger.error("传入参数为空");
            return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1, "传入参数为空!");
        }
    }

    /*
   *     快速拆分   删除拆分任务
   * */
    @RequestMapping(value = "/deleteSplitTask", produces = "application/json;charset=UTF-8")
    public ResultBean deleteSplitTask(String id) throws IOException {
        if (id != null){
//            int i = compareContributionService.deleteByPrimaryKey(id);
            return new ResultBean(ResultBean.CODE_SUCCESS, 0, 0, ResultBean.NAME_SUCCESS);
        }else {
            logger.error("传入参数为空");
            return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION, 1, "传入参数为空!");
        }
    }

    /**
     * 判断拆分入库是否是重复数据
     *
     * @param contributionList
     * @param geneMap1
     * @return
     */
    /*private int repetitionUtils(List<CompareContribution> contributionList, Map<String, Object> geneMap1) {

        int count = 0;
        //遍历数据库的基因信息
        for (CompareContribution contribution : contributionList) {
            //对数据库基因信息转为Map对象
            Map<String, Object> geneDBMap = JSON.parseObject(contribution.getGeneInfo());
            Map<String, Object> geneMap = JSON.parseObject(JSON.toJSONString(geneMap1));
            //首先判断两个map的key的数量是否相同，不相同的话，统计循环次数，跳出循环
            if (geneDBMap.keySet().size() != geneMap.keySet().size()) {
                count++;
                continue;
            } else {
                //如果相同那么循环比较各个value值
                for (String key : geneMap.keySet()) {
                    //如果前台的key在数据库的基因信息不存在则跳出循环
                    if (geneDBMap.get(key) == null) {
                        count++;
                        break;
                    } else {
                        //判断基因value值
                        if (match(geneDBMap.get(key).toString(), geneMap.get(key).toString())) {
                            continue;
                        } else {
                            count++;
                            break;
                        }
                    }
                }

            }
        }
        return count;
    }*/

    /**
     * 不考虑字符串顺序，判断字符串是否相同  公共方法
     *
     * @param o
     * @param o1
     * @return
     */
    public static boolean match(String o, String o1) {
        boolean flag = false;
        String[] split = o.split(",");
        String[] split1 = o1.split(",");
        Arrays.sort(split);
        Arrays.sort(split1);
        if (split.length == split1.length) {
            if (Arrays.equals(split, split1)) {
                flag = true;
            }
        }
        return flag;
    }

    //添加拆分信息
    /*private int addSplitCompare(CompareContribution compareContribution) {
        int insert = 0;
        try {
            //添加比对队列
            compareContribution.setId(UUID.randomUUID().toString());
            compareContribution.setQueueType("2");//队列类型
            compareContribution.setStatus(Constants.MATCH_STATUS_02);
            compareContribution.setCreateDatetime(new Date());
//            insert = compareContributionService.insert(compareContribution);
            logger.info("添加拆分比对信息 : " + LocalDateTime.now().toLocalTime());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("添加拆分队列信息错误"+e.getMessage());
        }
        return insert;
    }*/
}
