package com.bazl.dna.mix.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.bazl.dna.mix.model.po.*;
import com.bazl.dna.mix.rebbitmq.ISendMixCompareService;
import com.bazl.dna.mix.service.*;
import com.bazl.dna.mix.model.vo.ContributorAddVo;
import com.bazl.dna.mix.model.vo.SampleDnaGeneVo;
import com.bazl.dna.mix.utils.*;
import com.bazl.dna.util.GeneTransFormUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.common.OpenErrorCodes;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.common.filter.AuthUser;
import com.bazl.dna.mix.client.GeneListServerClient;
import com.bazl.dna.mix.client.IKitClientService;
import com.bazl.dna.mix.client.SampleDataServicr;
import com.bazl.dna.mix.constants.Constants;
import com.bazl.dna.mix.constants.ErrorCodes;
import com.bazl.dna.mix.constants.ErrorInfo;
import com.bazl.dna.mix.controller.base.BaseController;
import com.bazl.dna.mix.controller.base.ResultBean;
import com.bazl.dna.mix.controller.base.error.ErrorCode;
import com.bazl.dna.mix.model.CompareQueueModel;
import com.bazl.dna.mix.model.MixedSplitedSampleModel;
import com.bazl.dna.mix.model.vo.ResultUserVO;
import com.bazl.dna.util.RequestUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


@RestController
@RequestMapping("/kitLocus")
public class KitLocusController extends BaseController {

    @Autowired
    GeneSameCompareUtil geneSameCompareUtil;
    @Autowired
    CompareQueueService compareQueueService;
    @Autowired
    SampleNumUtil sampleNumUtil;
    @Autowired
    MatchResulService matchResulService;
    @Autowired
    GeneformatUtils geneformatUtils;
    @Autowired
    GeneMixCompareUtil geneMixCompareUtil;
    @Autowired
    RepetitionUtils repetitionUtils;
    @Autowired
    IKitClientService iKitClientService;
    @Autowired
    SampleDataServicr sampleDataServicr;
    @Autowired
    MixedSampleGeneService mixedSampleGeneService;
    @Autowired
    SplitedSampleGeneService splitedSampleGeneService;
    @Autowired
    GeneListServerClient geneListServerClient;
    @Autowired
    ContributorInfoService contributorInfoService;
    @Autowired
    ISendMixCompareService sendMixCompareService;

    /**
     * 查询试剂盒接口
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/querykitLocusListInfo" , produces = "application/json; charset=utf-8")
    public ResponseData querykitLocusListInfo() throws IOException {
        return iKitClientService.selectKitNameList();
    }

    /**
     * 通过试剂盒id查询基因座
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/queryLocusListInfoForkitId" , produces = "application/json; charset=utf-8")
    public ResponseData queryLocusListInfoForkitId(String reagentKitId) throws IOException {
        if (StringUtils.isNotBlank(reagentKitId)){
            return iKitClientService.selectByKitId(reagentKitId);
        }else{
            return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
        }
    }

    /*
    *   基因座名称模糊查询
    * */
    @RequestMapping(value = "/queryVagueLocusName", produces = "application/json; charset=utf-8")
    public ResponseData queryVagueLocusName(String locusName){
        try {
            return iKitClientService.queryLocusNameVague(locusName);
        }catch (Exception e) {
            e.printStackTrace();
            logger.error("基因座名称查询失败！"+e.getMessage());
            return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
        }
    }

    /**
     * 生成样本编号
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/querySimleNofoForkitId" , produces = "application/json; charset=utf-8")
    public ResultBean querySimpleNofoForkitId(HttpServletRequest request) throws Exception {
    		AuthUser user = RequestUtils.getAuthUser();
        if (StringUtils.isNotBlank(user.getId())){
            String dictTypeCode = "SAMPLE_NO";
            String nextSampleNoVal = sampleNumUtil.getNextSampleNoVal(user.getId(), dictTypeCode);
            String mixedSampleId = UUID.randomUUID().toString();
            HashMap<String, String> map = new HashMap<>();
            map.put("nextSampleNoVal",nextSampleNoVal);
            map.put("mixedSampleId",mixedSampleId);
            return new ResultBean(ResultBean.CODE_SUCCESS, 1, map, "查询成功!");
        }else{
            return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQBODY_INVALID_MSGTYPE, 1, "查询错误!");
        }
    }

    /*
    *      DNA分型快速比对(案件智能拆分通用)---加入比对列表
    *         判断混合还是拆分添加基因信息  并判断基因信息是否重复
    * */
    @RequestMapping(value = "/addLocusListInfoForkitId", produces = "application/json;charset=UTF-8")
    public ResponseData addLocusListInfoForkitId(@RequestBody MixedSplitedSampleModel sampleModel) throws Exception {
        Map<String, Object> result = new HashMap<>();
        AuthUser user = RequestUtils.getAuthUser();
        if (user != null && sampleModel != null && null != sampleModel.getMixedSampleGeneId()){
            String geneInfo = GeneSameCompareUtil.markerNameGene(sampleModel.getGeneInfo());
            //判断是混合还是拆分基因
            if (sampleModel.getQueueType().equals(Constants.QUEUE_TYPE_01)){
                //如果是混合分型
                MixedSampleGene mixedSampleGene = mixedSampleGeneService.selectById(sampleModel.getMixedSampleGeneId());
                if (mixedSampleGene == null){
                    //如果不存在直接添加
                    MixedSampleGene mixedSample = new MixedSampleGene();
                    mixedSample.setId(sampleModel.getMixedSampleGeneId());
                    mixedSample.setSampleId(sampleModel.getSampleId());
                    mixedSample.setSampleNo(sampleModel.getSampleNo());
                    mixedSample.setSampleName(sampleModel.getSampleName());
                    mixedSample.setReagentName(sampleModel.getReagentName());
                    mixedSample.setBoardBarcode(sampleModel.getBoardBarcode());
                    mixedSample.setGeneInfo(geneInfo);
                    mixedSample.setGenePicture(sampleModel.getGenePicture());
                    mixedSample.setCreatePerson(user.getId());
                    mixedSample.setCreateDatetime(new Date());
                    mixedSample.setContributorCount(sampleModel.getContributorCount());
                    mixedSample.setIsDeleted(Constants.IS_DELETED_0);
                    try {
                        mixedSampleGeneService.insert(mixedSample);
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("添加混合基因报错！"+e.getMessage());
                        return new ResponseData(0, "添加混合基因报错!");
                    }
                }else if (mixedSampleGene.getIsDeleted().equals("1")){
                    //数据库管理页面有物理删除  所以改状态
                    mixedSampleGene.setIsDeleted(Constants.IS_DELETED_0);
                    mixedSampleGeneService.updateMixedSampleGene(mixedSampleGene);
                }
                //判断比对列表是否存在
                CompareQueue queue = new CompareQueue();
                queue.setMixedSampleId(sampleModel.getMixedSampleGeneId());
//                queue.setQueueFlag(Constants.QUEUE_FLAG_1);//已提交
                queue.setQueueType(Constants.QUEUE_TYPE_01);//混合
                List<CompareQueue> compareQueues = compareQueueService.selectQueueList(queue);
                if (ListUtils.isNullOrEmptyList(compareQueues)){
                    //不存在直接添加
                    queue.setCreatePerson(user.getId());
                    saveCompareQueue(queue,sampleModel);
                    result.put("code", 0);
                }else{
                    //否则重复添加
                    result.put("code", 1);
                }
            }else if(sampleModel.getQueueType().equals(Constants.QUEUE_TYPE_02)){
                //如果是拆分分型
                //先添加混合信息  没有就添加
                MixedSampleGene mixedSampleGene = mixedSampleGeneService.selectById(sampleModel.getMixedSampleGeneId());
                if (mixedSampleGene == null){
                    MixedSampleGene mixedSample = sampleModel.getMixedSampleGene();
                    mixedSample.setId(sampleModel.getMixedSampleGeneId());
                    mixedSample.setIsDeleted(Constants.IS_DELETED_0);
                    mixedSample.setCreatePerson(user.getId());
                    String mixGeneInfo = GeneSameCompareUtil.markerNameGene(mixedSample.getGeneInfo());
                    mixedSample.setGeneInfo(mixGeneInfo);
                    try {
                        mixedSampleGeneService.insert(mixedSample);
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("添加混合基因报错！"+e.getMessage());
                        return new ResponseData(0, "添加混合基因报错!");
                    }
                }else if (mixedSampleGene.getIsDeleted().equals("1")){
                    //数据库管理页面有物理删除  所以改状态
                    mixedSampleGene.setIsDeleted(Constants.IS_DELETED_0);
                    mixedSampleGeneService.updateMixedSampleGene(mixedSampleGene);
                }
                //查询拆分信息
                List<SplitedSampleGene> splitedSampleGenes = splitedSampleGeneService.selectSplitedSampleGeneByMixedSampleGeneId(sampleModel.getMixedSampleGeneId());
                if (ListUtils.isNullOrEmptyList(splitedSampleGenes)){
                    //不存在直接添加基因信息
                    //添加队列信息
                    CompareQueue queue = new CompareQueue();
                    queue.setMixedSampleId(sampleModel.getMixedSampleGeneId());
                    queue.setCreatePerson(user.getId());
                    queue.setQueueType(Constants.QUEUE_TYPE_02);//拆分
                    saveCompareQueue(queue,sampleModel);
                    //添加拆分信息
                    SplitedSampleGene splitedGene = new SplitedSampleGene();
                    splitedGene.setId(UUID.randomUUID().toString());
                    splitedGene.setMixedSampleGeneId(sampleModel.getMixedSampleGeneId());
                    splitedGene.setGeneInfo(geneInfo);
                    splitedGene.setCreatePerson(user.getId());
                    splitedGene.setCreateDatetime(new Date());
                    splitedGene.setCompareQueueId(queue.getId());
                    try {
                        splitedSampleGeneService.insert(splitedGene);
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("添加拆分基因报错！"+e.getMessage());
                        return new ResponseData(0, "添加拆分基因报错!");
                    }
                    result.put("code", 0);
                }else {
                    //判断比对列表是否存在
                    CompareQueue queue = new CompareQueue();
                    queue.setMixedSampleId(sampleModel.getMixedSampleGeneId());
                    queue.setQueueType(Constants.QUEUE_TYPE_02);//拆分
                    List<CompareQueue> compareQueueList = compareQueueService.selectQueueList(queue);
                    //如果没有拆分，可以直接加入
                    if (ListUtils.isNullOrEmptyList(compareQueueList)){
                        queue.setCreatePerson(user.getId());
                        saveCompareQueue(queue,sampleModel);
                        result.put("code", 0);
                    }else {
                        //如果有拆分信息，先判断基因信息是否重复
                        int determine = repetitionUtils.DetermineNew(splitedSampleGenes, geneInfo);
                        //条数相同表示没有拆分一样的基因信息
                        if (determine == compareQueueList.size() ){
                            //添加队列信息
                            queue.setCreatePerson(user.getId());
                            saveCompareQueue(queue,sampleModel);
                            //添加拆分信息
                            SplitedSampleGene splitedGene = new SplitedSampleGene();
                            splitedGene.setId(UUID.randomUUID().toString());
                            splitedGene.setMixedSampleGeneId(sampleModel.getMixedSampleGeneId());
                            splitedGene.setGeneInfo(geneInfo);
                            splitedGene.setCreatePerson(user.getId());
                            splitedGene.setCreateDatetime(new Date());
                            splitedGene.setCompareQueueId(queue.getId());
                            try {
                                splitedSampleGeneService.insert(splitedGene);
                            } catch (Exception e) {
                                e.printStackTrace();
                                logger.error("添加拆分基因报错！"+e.getMessage());
                                return new ResponseData(0, "添加拆分基因报错!");
                            }
                            result.put("code", 0);
                        }else {
                            //条数不同表示有拆分一样的基因信息
                            int cc =0;
                            for (CompareQueue cq:compareQueueList) {
                                if(StringUtils.equals(cq.getSex(),sampleModel.getSex())
                                        && StringUtils.equals(cq.getPersonType(),sampleModel.getPersonType())
                                        && StringUtils.equals(cq.getDistrict(),sampleModel.getDistrict())
                                        && StringUtils.equals(String.valueOf(cq.getMixsameCount()),String.valueOf(sampleModel.getMixsameCount()))
                                        ){
//                                    result.put("code", 1);
                                    continue;
                                }else{
                                    cc++;
                                }
                            }
                            if(cc == compareQueueList.size()){
                                //添加队列信息
                                queue.setCreatePerson(user.getId());
                                saveCompareQueue(queue,sampleModel);
                                //添加拆分信息
                                SplitedSampleGene splitedGene = new SplitedSampleGene();
                                splitedGene.setId(UUID.randomUUID().toString());
                                splitedGene.setMixedSampleGeneId(sampleModel.getMixedSampleGeneId());
                                splitedGene.setGeneInfo(geneInfo);
                                splitedGene.setCreatePerson(user.getId());
                                splitedGene.setCreateDatetime(new Date());
                                splitedGene.setCompareQueueId(queue.getId());
                                try {
                                    splitedSampleGeneService.insert(splitedGene);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    logger.error("添加拆分基因报错！"+e.getMessage());
                                    return new ResponseData(0, "添加拆分基因报错!");
                                }
                                result.put("code", 0);
                            }else{
                                result.put("code", 1);
                            }
                        }
                    }
                }
            }

            //页面回显列表  已提交和未提交的都回显
            CompareQueue query = new CompareQueue();
            query.setMixedSampleId(sampleModel.getMixedSampleGeneId());
//            query.setQueueFlag(Constants.QUEUE_FLAG_0);
            List<CompareQueue> compareQueues = compareQueueService.selectQueueList(query);
            //已提交
            // query.setMixedSampleId(compareQueue.getMixedSampleId());
//            query.setQueueFlag(Constants.QUEUE_FLAG_1);
//            List<CompareQueue> compareQueueList = compareQueueService.selectQueueList(query);
//            compareQueues.addAll(compareQueueList);

            if ( ListUtils.isNotNullAndEmptyList(compareQueues)){
                for (CompareQueue queue : compareQueues) {
                    //查询对应的数据获取基因信息
                    if (queue.getQueueType().equals(Constants.QUEUE_TYPE_01)){
                        //如果是混合类型
                        MixedSampleGene sampleGene = mixedSampleGeneService.selectById(queue.getMixedSampleId());
                        if (sampleGene != null){
                            //基因格式转换
                            Map<String, Object> stringListMap = GeneformatUtils.sampleformatList(sampleGene.getGeneInfo());
                            queue.setGeneInfos(stringListMap);
                        }
                    }else if (queue.getQueueType().equals(Constants.QUEUE_TYPE_02)){
                        //如果是拆分类型
                        SplitedSampleGene splitedSampleGene = splitedSampleGeneService.selectByCompareQueueId(queue.getId());
                        if (splitedSampleGene != null){
                            Map<String, Object> stringListMap = GeneformatUtils.sampleformatList(splitedSampleGene.getGeneInfo());
                            queue.setGeneInfos(stringListMap);
                        }
                    }
                    queue.setGeneImagePath(String.valueOf(0));
                    //时间格式转化
                    if (null != queue.getCreateDatetime()){
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String currentDateStr = sdf.format(queue.getCreateDatetime());
                        queue.setDatetime(currentDateStr);
                    }

                    if(null !=queue.getQueueFlag() ){
                        if(queue.getQueueFlag().equals("0")){
                            result.put("QueueFlag",0);
                        }
                    }
                }
            }

            result.put("compareQueueList",compareQueues);
            // compareQueue1.setQueueType(compareQueue.getQueueType());
            //List<CompareQueue> compareQueues = compareQueueService.selectByMixedSampleId(compareQueue1);

            return new ResponseData(result);
        }else {
            logger.error("传入参数为空");
            return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
        }
    }

    /**
     * 省市二级联动
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/selectProvinceAndCity" , produces = "application/json; charset=utf-8")
    public ResultBean selectProvinceAndCity(HttpServletRequest request,String code) throws IOException {
        
        ResponseData sendRequest = iKitClientService.selectProvinceAndCity(StringUtils.trimToEmpty(code));
        Gson gson = new Gson();
        List<Map<String,Object>> result  = gson.fromJson(gson.toJson(sendRequest.getResult()), new TypeToken<List<Map<String,Object>>>(){}. getType());
        if (result != null){
            for (Map<String,Object> map : result){
                List<ResultUserVO> resultUserVOS = new ArrayList<>();
                String personlism = JSONObject.toJSONString(map.get("personlism"));
//                List<ResultUserVO> personlismList = JSON.parseArray(personlism, ResultUserVO.class);
                ResultUserVO register = gson.fromJson(personlism,ResultUserVO.class);
                resultUserVOS.add(register);
                String data = JSONObject.toJSONString(map.get("city"));
                List<ResultUserVO> list = JSON.parseArray(data, ResultUserVO.class);
                if (ListUtils.isNotNullAndEmptyList(list)){
                    for (ResultUserVO resultUserVO : list) {
                        resultUserVO.setbOn(false);
//                        if (StringUtils.isNotBlank(code)){
                            if (resultUserVO.getRegionalismName().indexOf("省") != -1){
                                String[] split = resultUserVO.getRegionalismName().split("省");
                                for (String s : split) {
                                    resultUserVO.setRegionalismName(s);
                                }
                            }else if (resultUserVO.getRegionalismName().indexOf("区") != -1){
                                String[] split = resultUserVO.getRegionalismName().split("区");
                                for (String s : split) {
                                    resultUserVO.setRegionalismName(s);
                                }
                            }
//                        }
                    }
                }
                resultUserVOS.addAll(list);
                map.put("city",resultUserVOS);
            }

            /*List<ResultUserVO> resultUser = result.get("personlism");
            if (ListUtils.isNotNullAndEmptyList(resultUser)){
                resultUserVOS.addAll(resultUser);
            }
            resultUserVOS.addAll(result);*/
        }

        return new ResultBean(ResultBean.CODE_SUCCESS, 1, result, "查询成功!");
    }

    /**
     * 人员类型信息
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/selectPersonCategory" , produces = "application/json; charset=utf-8")
    public ResponseData selectPersonCategory(HttpServletRequest request) throws IOException {
        ResponseData responseData = sampleDataServicr.selectPersonCategory();
        Gson gson = new Gson();
        List<Map<String,Object>> resultUserVOS  = gson.fromJson(gson.toJson(responseData.getResult()), new TypeToken<List<Map<String,Object>>>(){}. getType());
        //前端做判断用
       /* for (ResultUserVO resultUserVO : resultUserVOS) {
                resultUserVO.setFlag(false);
        }*/
        return new ResponseData(resultUserVOS);
    }

    /**
     * 通过编号查询
     * @param request
     * @param sampleNo
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/selectBySampleNo", produces = "application/json;charset=UTF-8")
    public ResultBean selectBySampleNo(HttpServletRequest request, String sampleNo) throws IOException {
        if (StringUtils.isNotBlank(sampleNo)){
            List<CompareQueue> compareQueueList =compareQueueService.selectBySampleNo(sampleNo);
            System.out.println(compareQueueList);
        }
        return new ResultBean(ResultBean.CODE_SUCCESS, 1, 1, "查询成功!");
    }

    /*
    *   删除队列任务
    * */
    @RequestMapping(value = "/deleteLocusListInfoForId", produces = "application/json;charset=UTF-8")
    public ResponseData deleteLocusListInfoForId(HttpServletRequest request, String id) throws IOException {
        if (StringUtils.isNotBlank(id)){
            CompareQueue queue = compareQueueService.selectByPrimaryKey(id);
            //删除基因信息
            if (queue != null){
                if (queue.getQueueType().equals(Constants.QUEUE_TYPE_01)){
                    //删除混合基因
                    MixedSampleGene mixedSampleGene = mixedSampleGeneService.selectById(queue.getMixedSampleId());
                    if (mixedSampleGene != null){
                        //物理删除
                        mixedSampleGeneService.deleteById(mixedSampleGene.getId());
                    }
                }else if (queue.getQueueType().equals(Constants.QUEUE_TYPE_02)){
                    //删除拆分样本
                    SplitedSampleGene splitedSampleGene = splitedSampleGeneService.selectByCompareQueueId(queue.getId());
                    if (splitedSampleGene != null){
                        splitedSampleGeneService.deleteByPrimaryKey(splitedSampleGene.getId());
                    }
                }
            }
            //删除队列信息
            compareQueueService.deleteByPrimaryKey(id);
            return new ResponseData(1,"删除成功!");
        }else {
            logger.error("传入参数为空");
            return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
        }
    }

    /**
     *      案件智能拆分---提交比对
     */
    @RequestMapping(value = "/updateLocusList", produces = "application/json;charset=UTF-8")
    public ResponseData updateLocusList(@RequestBody CompareQueueModel compareQueueModel) throws IOException {
        if (ListUtils.isNotNullAndEmptyList(compareQueueModel.getCompareQueueList())){
            for (CompareQueue queue : compareQueueModel.getCompareQueueList()){
                if (null != queue.getMixedSampleId()){
                    //修改比对任务
//                    CompareQueue primary= compareQueueService.selectByPrimaryKey(queue.getId());
//                    queue.setGeneInfo(primary.getGeneInfo());
                    queue.setPersonTypeName(queue.getRenyuan());
                    queue.setDistrictName(queue.getDiqu());
                    queue.setQueueFlag(Constants.QUEUE_FLAG_1);//是否比对
                    queue.setTargetCount("0%");//比对进度
                    if (queue.getQueueType().equals("拆分")){
                        queue.setQueueType(Constants.QUEUE_TYPE_02);
                    }else if (queue.getQueueType().equals("混合")){
                        queue.setQueueType(Constants.QUEUE_TYPE_01);
                    }
                    try {
                        compareQueueService.updateByPrimaryKey(queue);
                        queue.setDataType(queue.getServeNo());
                        //基因转换
                        String gene = GeneTransFormUtils.geneFormatString(queue.getGeneInfo());
                        queue.setGeneInfo(gene);
                        sendMixCompareService.sendCompare(JSON.toJSONString(queue));
                    } catch (Exception e) {
                        e.printStackTrace();
                        return new ResponseData(0,"失败！");
                    }
                    //查看比中结果
                    /*MatchResult match = new MatchResult();
                    match.setCompareId(queue.getId());
                    List<MatchResult> matchResults = matchResulService.selectMatchList(match);
                    if (ListUtils.isNotNullAndEmptyList(matchResults)){
                        for (MatchResult res : matchResults){
                            Map<String, List<String>> map = GeneformatUtils.mixedSampleGeneformat(queue.getGeneInfo());
                            Map<String, List<String>> map1 = GeneformatUtils.mixedSampleGeneformat(res.getGeneInfo());
                            Map<String, Object> stringObjectMap = geneMixCompareUtil.compareResult(map, map1, Integer.valueOf(queue.getMixsameCount()));
                            res.setGeneInfos(stringObjectMap);
                        }
                        resultList.addAll(matchResults);
                    }*/
                }else {
                    logger.error("传入参数为空!");
                }
            }
//            result.put("resultList",resultList);
//            result.put("plan","0%");
            return new ResponseData(1);
        }else {
            logger.error("传入参数为空!");
            return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
        }
    }

    /**
     *     DNA分型快速比对---提交比对
     */
    @RequestMapping(value = "/updateLocusListInfoForId", produces = "application/json;charset=UTF-8")
    public ResponseData updateLocusListInfoForId(@RequestBody CompareQueueModel compareQueueModel) throws Exception {
        Map<String, Object> result = new HashMap<>();
        //获取当前用户
        AuthUser user = RequestUtils.getAuthUser();
        List<String> ids = compareQueueModel.getIds();
        List<ContributorInfo> contributorInfoList = compareQueueModel.getContributorInfoList();
        if (ListUtils.isNotNullAndEmptyList(ids)){
            //添加已知贡献者
            if (ListUtils.isNotNullAndEmptyList(contributorInfoList)){
                for (ContributorInfo info : contributorInfoList){
                    //防止重复添加
                    boolean flag = true;
                    List<ContributorInfo> listByMixSampleGeneId = contributorInfoService.findListByMixSampleGeneId(info.getMixedSampleGeneId());
                    if (ListUtils.isNotNullAndEmptyList(listByMixSampleGeneId)){
                        for (ContributorInfo contributorInfo : listByMixSampleGeneId){
                            if (contributorInfo.getSampleNo().equals(info.getSampleNo())){
                                flag = false;
                            }
                        }
                    }
                    if (flag){
                        info.setId(UUID.randomUUID().toString());
                        //基因信息转换
                        String geneInfo = GeneSameCompareUtil.markerNameGene(info.getGeneInfo());
                        info.setGeneInfo(geneInfo);
                        if (user != null){
                            info.setCreatePerson(user.getId());
                        }
                        info.setCreateDatetime(new Date());
                        try {
                            contributorInfoService.insert(info);
                        } catch (Exception e) {
                            e.printStackTrace();
                            logger.error("添加贡献者报错！"+e.getMessage());
                            return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
                        }
                    }
                }
            }
            //提交加入比对
            List<MatchResult> resultList = new ArrayList<>();
            for (String id : ids) {
                CompareQueue queue = compareQueueService.selectByPrimaryKey(id);
                if (queue != null){
                    //修改状态  改为提交比对状态
                    if (user != null){
                        queue.setUpdatePereson(user.getId());
                    }
                    queue.setQueueFlag(Constants.QUEUE_FLAG_1);
                    queue.setUpdateDatetime(new Date());
                    try {
                        compareQueueService.updateByPrimaryKey(queue);
                        queue.setDataType(queue.getServeNo());
                        //基因转换
                        String gene = GeneTransFormUtils.geneFormatString(queue.getGeneInfo());
                        queue.setGeneInfo(gene);
                        sendMixCompareService.sendCompare(JSON.toJSONString(queue));
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.error("修改队列提交比对失败！"+e.getMessage());
                        return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
                    }

                    /*//查询比对结果（以前的）
                    MatchResult matchResult = new MatchResult();
                    matchResult.setCompareId(id);
                    List<MatchResult> matchResultList = matchResulService.selectMatchList(matchResult);
                    if(ListUtils.isNotNullAndEmptyList(matchResultList)){
                        for (MatchResult matchResult1:matchResultList) {
                            Map<String, List<String>> map = GeneformatUtils.mixedSampleGeneformat(queue.getGeneInfo());
                            Map<String, List<String>> map1 = GeneformatUtils.mixedSampleGeneformat(matchResult1.getGeneInfo());
                            Map<String, Object> stringObjectMap = geneMixCompareUtil.compareResult(map, map1, Integer.valueOf(queue.getMixsameCount()));
                            matchResult1.setGeneInfos(stringObjectMap);
                        }
                        resultList.addAll(matchResultList);
                    }*/
                }
            }
            result.put("resultList",resultList);
            result.put("plan","0%");
            result.put("QueueFlag",1);
            return new ResponseData(result);
        }
        return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
    }

    /**
     *   比对记录---重新比对
     * @param id
     */
    @RequestMapping(value = "/queryLocusListInfoForId", produces = "application/json;charset=UTF-8")
    public ResponseData queryLocusListInfoForId(String id) throws IOException {
        if (StringUtils.isNotBlank(id)){
            HashMap<String, Object> hashMap = new HashMap<>();
            //获取基因信息
            MixedSampleGene mixedSampleGene = mixedSampleGeneService.selectById(id);
            if (mixedSampleGene != null){
                //基因格式转换
                Map<String, Object> stringListMap = GeneSameCompareUtil.alleleNameGene(mixedSampleGene.getGeneInfo());
                mixedSampleGene.setGeneInfos(stringListMap);
            }
            hashMap.put("blendList",mixedSampleGene);

            //查询队列数据
            CompareQueue compareQueue = new CompareQueue();
            compareQueue.setMixedSampleId(id);
//            compareQueue.setQueueFlag(Constants.QUEUE_FLAG_0);
            List<CompareQueue>  compareQueuesList= compareQueueService.selectQueueList(compareQueue);
//            compareQueue.setQueueFlag(Constants.QUEUE_FLAG_1);
//            List<CompareQueue>  compareQueuesList= compareQueueService.selectQueueList(compareQueue);
            //合并
//            compareQueues1.addAll(compareQueuesList);
            //查询对应的数据获取基因信息
            if (ListUtils.isNotNullAndEmptyList(compareQueuesList)){
                for (CompareQueue queue : compareQueuesList) {
                    /* Map<String, List<String>> stringListMap = GeneformatUtils.mixedSampleGeneformat(queue.getGeneInfo());
                    if (stringListMap != null) {
                        Map<String, Object> stringObjectMap = geneSameCompareUtil.analysisGene(stringListMap);
                        queue.setGeneInfos(stringObjectMap);
                    }*/
                    if (queue.getQueueType().equals(Constants.QUEUE_TYPE_01)){
                        //如果是混合类型
                        MixedSampleGene sampleGene = mixedSampleGeneService.selectById(queue.getMixedSampleId());
                        if (sampleGene != null){
                            //基因格式转换
                            Map<String, Object> stringListMap = GeneformatUtils.sampleformatList(sampleGene.getGeneInfo());
                            queue.setGeneInfos(stringListMap);
                        }
                    }else if (queue.getQueueType().equals(Constants.QUEUE_TYPE_02)){
                        //如果是拆分类型
                        SplitedSampleGene splitedSampleGene = splitedSampleGeneService.selectByCompareQueueId(queue.getId());
                        if (splitedSampleGene != null){
                            Map<String, Object> stringListMap = GeneformatUtils.sampleformatList(splitedSampleGene.getGeneInfo());
                            queue.setGeneInfos(stringListMap);
                        }
                    }
                    //时间格式转化
                    if (null != queue.getCreateDatetime()){
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        String currentDateStr = sdf.format(queue.getCreateDatetime());
                        queue.setDatetime(currentDateStr);
                    }
                    //性别展示
                    /*if (null != queue.getSex()){
                        if (queue.getSex().contains("1")){
                            if (queue.getSex().contains("2")){
                                queue.setSex("男,女");
                            }else{
                                queue.setSex("男");
                            }
                        }else{
                            queue.setSex("女");
                        }
                    }*/
                }
                /*if (compareQueues.get(0).getSource().equals(compareQueues1.get(0).getSource())){
                    hashMap.put("source",compareQueues.get(0).getSource());
                }*/
            }
            hashMap.put("splitList",compareQueuesList);
            //查询贡献者
            List<ContributorInfo> listByMixSampleGeneId = contributorInfoService.findListByMixSampleGeneId(id);
            for (ContributorInfo info : listByMixSampleGeneId){
                String markerNameGene = GeneSameCompareUtil.alleleMarkerNameGene(info.getGeneInfo());
                info.setGeneInfo(markerNameGene);
            }
            hashMap.put("contriList",listByMixSampleGeneId);
            return new ResponseData(hashMap);
        }
        return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
    }



    public void saveCompareQueue(CompareQueue queue,MixedSplitedSampleModel sampleModel) {
        try {
            //添加队列信息
            queue.setId(UUID.randomUUID().toString());
            queue.setSampleNo(sampleModel.getSampleNo());
            queue.setStatus(Constants.MATCH_STATUS_02);
            //数据来源
            queue.setSource(Constants.SOURCE_TYPE_1);
            queue.setCreateDatetime(new Date());
            queue.setMixsameCount(sampleModel.getMixsameCount()+"");
            queue.setCondition(sampleModel.getCondition());
            queue.setSex(sampleModel.getSex());
            queue.setPersonType(sampleModel.getPersonType());
            queue.setDistrict(sampleModel.getDistrict());
            queue.setQueueFlag(Constants.QUEUE_FLAG_0);
            queue.setTargetCount("0%");
            queue.setDistrictName(sampleModel.getDistrictName());
            queue.setPersonTypeName(sampleModel.getPersonTypeName());
            if (sampleModel.getServeNo() != null){
                queue.setServeNo(sampleModel.getServeNo());
            }else {
                queue.setServeNo("");
            }
            try {
                compareQueueService.insert(queue);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("添加队列报错！"+e.getMessage());
            }
            logger.info("====添加队列信息===="+ LocalDateTime.now().toLocalTime());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("添加比对队列失败" + e.getMessage());
        }
    }

    /**
     * 根据样本编号查询单一样本基因信息
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping(value = "/selectSingleGeneBySampleNo")
    public ResponseData selectSingleGeneBySampleNo(@RequestParam("sampleNo") String sampleNo) throws IOException {
        if (StringUtils.isBlank(sampleNo)){
            return new ResponseData(OpenErrorCodes.ERR_INVALID_PARAMETER,"invoke KitLocusController.selectSingleGeneBySampleNo Request parameter is null");
        }
        try {
            ResponseData responseData = geneListServerClient.selectSingleGeneBySampleNo(sampleNo);


            //获取基因座列表
            List<Map<String,String>> listLocusName = new ArrayList<Map<String,String>>();
            logger.info("查询基因座信息开始----------------------");
            ResponseData responseData1 = geneListServerClient.selectByLocusName();
            String locusInfo = JSONObject.toJSONString(responseData1.getResult());
            List<HashMap> llocus = JSON.parseArray(locusInfo, HashMap.class);
            for (Object object : llocus ) {
                listLocusName.add((Map<String, String>) object);
            }
            logger.info("查询基因座信息结束----------------------");


            //页面回显 数据处理
            if(responseData.getResult()== null){
                return new ResponseData(OpenErrorCodes.ERR_INTERNAL_RESPONSE);
            }
            List<ContributorAddVo> contributorAddVoList=new ArrayList<ContributorAddVo>();
            ContributorAddVo contributorAddVo = null;
//            List<SampleDnaGeneVo> sampleDnaGeneVoList = (List<SampleDnaGeneVo>) responseData.getResult();
            String data = JSONObject.toJSONString(responseData.getResult());
            List<SampleDnaGeneVo> sampleDnaGeneVoList = JSON.parseArray(data, SampleDnaGeneVo.class);
            for(SampleDnaGeneVo s:sampleDnaGeneVoList) {
//                String geneInfoSample = geneformatUtils.geneFormatConversionNew(s.getEntity().getGeneInfo(), listLocusName);
//                String reagentKitId = sampleDnaGeneVoList.get(0).getEntity().getReagentName();
//                String gene = geneFormatConversionGj(geneInfoSample, reagentKitId);
//                Map<String, List<String>> singleSampleGeneInfo = GeneformatUtils.mixedSampleGeneformat(gene);
//                geneSameCompareUtil.analysisGene(singleSampleGeneInfo);

                String jsonGene=geneformatUtils.geneFormatListTask(s.getEntity().getGeneInfo(),listLocusName);
                String[] locusNames=s.getEntity().getGeneInfo().split(";");
                List noRepeatList = removeNullStringArray(locusNames);

                contributorAddVo=new ContributorAddVo();
                contributorAddVo.setGeneInfo(jsonGene);
                contributorAddVo.setSampleNo(s.getSampleNo()!= null ? s.getSampleNo() : null);
                contributorAddVo.setSampleName(s.getSampleName()!= null ?s.getSampleName(): null);
                contributorAddVo.setSampleType(s.getSampleType()!= null ?s.getSampleType(): null);
                contributorAddVo.setSampleTypeName(s.getSampleTypeName()!= null ?s.getSampleTypeName(): null);
                contributorAddVo.setCaseNo(s.getCaseNo()!= null ?s.getCaseNo(): null);
                contributorAddVo.setCaseName(s.getCaseName()!= null ?s.getCaseName(): null);
                contributorAddVo.setPanelId(s.getKitId()!= null ?s.getKitId(): null);
                contributorAddVo.setPanelName(s.getKitName()!= null ?s.getKitName(): null);
                contributorAddVo.setGeneCount(noRepeatList.size());
                contributorAddVoList.add(contributorAddVo);
            }

            return new ResponseData(contributorAddVoList);
        }catch (Exception ex){
            return new ResponseData(OpenErrorCodes.ERR_INTERNAL_RESPONSE,ex.toString());
        }
    }

    @SuppressWarnings("rawtypes")
	public String geneFormatConversionGj(String gene, String reagentKitName) {
        String geneInfos = null;
        try {
//                Map reagentNameMap = new LinkedHashMap();
//                reagentNameMap.put("reagentKitName", reagentKitName);
//                String sendRequest = HttpClientUtil.doGet(KitNameToLocusName, reagentNameMap);

            ResponseData responseData=iKitClientService.selectByKitNameToLocusName(reagentKitName);

//                JSONObject jsonObject = JSONObject.parseObject(sendRequest);
//                String data = jsonObject.getString("data");
            List<Map> resultUserVOS = JSON.parseArray(JSONObject.toJSONString(responseData.getResult()), Map.class);
            Map<Object, Object> GeneInfo = new LinkedHashMap<>();
            for (int i = 0; i < resultUserVOS.size(); i++) {
                String locusName = (String) resultUserVOS.get(i).get("locusName");
                Map<String, Object> jsonToMap = JSONObject.parseObject(gene);
                if ( jsonToMap.containsKey(locusName) == true) {
                    GeneInfo.put(locusName, jsonToMap.get(locusName));
                }
            }
            geneInfos = JSON.toJSONString(GeneInfo);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("=======国家库基因信息转换失败========");
        }
        return geneInfos;
    }


    /**
     * 去掉数组为null的
     * @param arrayString
     * @return
     */
    public List<String> removeNullStringArray(String[] arrayString) {
        List<String> list1 = new ArrayList<String>();
        for (int i = 0; i < arrayString.length; i++) {
            if (arrayString[i] != null && arrayString[i].length() != 0) { //过滤掉数组arrayString里面的空字符串
                list1.add(arrayString[i]);
            }
        }
        return list1;
    }

}
