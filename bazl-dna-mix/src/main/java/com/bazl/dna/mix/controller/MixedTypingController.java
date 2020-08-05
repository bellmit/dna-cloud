package com.bazl.dna.mix.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.common.OpenErrorCodes;
import com.bazl.dna.common.PageInfo;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.common.filter.AuthUser;
import com.bazl.dna.mix.client.GeneListServerClient;
import com.bazl.dna.mix.client.IKitClientService;
import com.bazl.dna.mix.config.DnaGeneInfoDetail;
import com.bazl.dna.mix.config.MixedGeneInfoDetail;
import com.bazl.dna.mix.constants.Constants;
import com.bazl.dna.mix.constants.ErrorCodes;
import com.bazl.dna.mix.constants.ErrorInfo;
import com.bazl.dna.mix.constants.GlobalConstants;
import com.bazl.dna.mix.controller.base.BaseController;
import com.bazl.dna.mix.controller.base.ResultBean;
import com.bazl.dna.mix.controller.base.error.ErrorCode;
import com.bazl.dna.mix.model.CompareQueueModel;
import com.bazl.dna.mix.model.po.CompareQueue;
import com.bazl.dna.mix.model.po.ContributorInfo;
import com.bazl.dna.mix.model.po.MatchResult;
import com.bazl.dna.mix.model.po.MixedSampleGene;
import com.bazl.dna.mix.model.po.SplitedSampleGene;
import com.bazl.dna.mix.model.vo.MatchResultDetailsListVo;
import com.bazl.dna.mix.model.vo.MatchResultVo;
import com.bazl.dna.mix.model.vo.MixedSampleGenePagingVo;
import com.bazl.dna.mix.model.vo.MixedTypingVo;
import com.bazl.dna.mix.service.CompareQueueService;
import com.bazl.dna.mix.service.ContributorInfoService;
import com.bazl.dna.mix.service.MatchResulService;
import com.bazl.dna.mix.service.MixedSampleGeneService;
import com.bazl.dna.mix.service.SplitedSampleGeneService;
import com.bazl.dna.mix.utils.GeneMixCompareUtil;
import com.bazl.dna.mix.utils.GeneSameCompareUtil;
import com.bazl.dna.mix.utils.GeneformatUtils;
import com.bazl.dna.mix.utils.ListUtils;
import com.bazl.dna.util.RequestUtils;


@RestController
@RequestMapping("/mixedTyping")
public class MixedTypingController extends BaseController {


    @Autowired
    CompareQueueService compareQueueService;
    @Autowired
    GeneformatUtils geneformatUtils;
    @Autowired
    MatchResulService matchResulService;
    @Autowired
    GeneMixCompareUtil geneMixCompareUtil;
    @Value("${Gen}")
    private int gen;
    @Autowired
    GeneSameCompareUtil geneSameCompareUtil;
    @Autowired
    GeneListServerClient geneListServerClient;
    @Autowired
    IKitClientService kitClientService;
    @Autowired
    MixedSampleGeneService mixedSampleGeneService;
    @Autowired
    ContributorInfoService contributorInfoService;
    @Autowired
    SplitedSampleGeneService splitedSampleGeneService;
    
    /**
     * 混合数据库管理列表 新
     */
    @PostMapping(value = "/selectListPaging", produces = "application/json; charset=utf-8")
    public ResponseData selectListPaging(HttpServletRequest request,@RequestBody MixedTypingVo mixedTypingVo) throws IOException {
        try {
            // 获得用户对象
            AuthUser user = RequestUtils.getAuthUser();
            if (user != null && user.getId() != null){
                mixedTypingVo.setCreatePerson(user.getId());
            }
            //分页条件查询
            ArrayList<MixedSampleGenePagingVo> mixedSampleGeneArrayList = mixedSampleGeneService.selectListPaging(mixedTypingVo);
            //总数
            int totalCount=mixedSampleGeneService.selectListPagingCount(mixedTypingVo);
            //分页展示
            PageInfo pageInfo = new PageInfo();
            pageInfo.setAllRecordCount(totalCount);
            Map<String, Object> resultObj = new HashMap<>();
//            resultObj.put("offset", mixedTypingVo.getOffset());
//            resultObj.put("rows", mixedTypingVo.getRows());
//            resultObj.put("totalCount", totalCount);
            resultObj.put("pageInfo", pageInfo);
            resultObj.put("mixedSampleGeneArrayList", mixedSampleGeneArrayList);
            return new ResponseData(resultObj);
        }catch (Exception ex){
            logger.error("invoke MixedTypingController.selectList error", ex);
            return new ResponseData(OpenErrorCodes.ERR_INTERNAL_RESPONSE,ex.toString());
        }


//		if (mixedTypingVo == null) {
//
//		    ArrayList<MixedSampleGene> mixedSampleGeneArrayList=mixedSampleGeneService.selectList();
//
//
//           // List<NewSampleDnaGeneVo> resultUserVOS = JSON.parseArray(data, NewSampleDnaGeneVo.class);
//           // return new ResultBean(ResultBean.CODE_SUCCESS, ErrorCode.IBOAN_REQBODY_INVALID_MSGTYPE, resultUserVOS, "查询成功!");
//            ResponseData responseData = geneListServerClient.queryMixedSampleGenes("1", null, null, null, null);
//            String data = JSONObject.toJSONString(responseData.getResult());
//            JSONObject jsondata = JSONObject.parseObject(data);
//            String count = jsondata.getString("count");
//            String sampleDnaGeneVoMix = jsondata.getString("sampleDnaGeneVoMix");
//            List<Map<String, Object>> jsonArray2 = (List) JSONObject.parseArray(sampleDnaGeneVoMix);
//            PageInfo pageInfo = new PageInfo();
//            pageInfo.setAllRecordCount(Integer.valueOf(count));
//            HashMap<String, Object> map = new HashMap<>();
//            map.put("sampleDnaGeneVoMix",jsonArray2);
//            map.put("pageInfo",pageInfo);
//            return new ResultBean(ResultBean.CODE_SUCCESS, 0, map, "查询成功!");
//        }else{
//            ResponseData responseData = geneListServerClient.queryMixedSampleGenes(String.valueOf(mixedTypingVo.getOffset()),
//            		mixedTypingVo.getCaseNo(), mixedTypingVo.getCaseName(), mixedTypingVo.getSampleNo(), mixedTypingVo.getSampleName());
//            String data = JSONObject.toJSONString(responseData.getResult());
//            JSONObject jsondata = JSONObject.parseObject(data);
//            String count = jsondata.getString("count");
//            String sampleDnaGeneVoMix = jsondata.getString("sampleDnaGeneVoMix");
//            List<Map<String, Object>> jsonArray2 = (List) JSONObject.parseArray(sampleDnaGeneVoMix);
//            PageInfo pageInfo = new PageInfo();
//            pageInfo.setAllRecordCount(Integer.valueOf(count));
//
//            HashMap<String, Object> map = new HashMap<>();
//            map.put("sampleDnaGeneVoMix",jsonArray2);
//            map.put("pageInfo",pageInfo);
//            return new ResultBean(ResultBean.CODE_SUCCESS, ErrorCode.IBOAN_REQBODY_INVALID_MSGTYPE, map, "查询成功!");
//        }
    }

    /**
     * 单条删除混合库 新
     * @return
     * @throws IOException
     */
    @GetMapping(value = "/deleteById" , produces = "application/json; charset=utf-8")
    public ResponseData deleteById(HttpServletRequest request,@RequestParam("id") String id) throws IOException {
        try {
            Boolean bool = mixedSampleGeneService.deleteById(id);

            return new ResponseData(bool);
        }catch(Exception ex){
            logger.error("invoke MixedTypingController.deleteById error.", ex);
            return new ResponseData(OpenErrorCodes.ERR_INTERNAL_RESPONSE,ex.toString());
        }
    }

    /**
     * 批量删除混合库 新
     * @return
     * @throws IOException
     */
    @GetMapping(value = "/deleteByIds" , produces = "application/json; charset=utf-8")
    public ResponseData deleteByIds(HttpServletRequest request,@RequestParam("ids") String ids) throws IOException {
        try {
            Boolean bool = mixedSampleGeneService.deleteByIds(ids);

            return new ResponseData(bool);
        }catch(Exception ex){
            logger.error("invoke MixedTypingController.deleteByIds error.", ex);
            return new ResponseData(OpenErrorCodes.ERR_INTERNAL_RESPONSE,ex.toString());
        }



//        if (StringUtils.isNotBlank(ids)){
//            //判断比对类型
//            String[] strings = null;
//            //替换传过来的
//            if (StringUtils.isNotBlank(ids)) {
//                ids = ids.replace("'", "");
//                ids = ids.replace("\"", "");
//                ids = ids.replace("]", "");
//                ids = ids.replace("[", "");
//                //判断比对类型
//                strings = ids.split(",");
//            }
//            if (strings != null) {
//                for (String id : strings) {
//                    geneListServerClient.updateSampleDnaGeneDF(id);
//                }
//                return new ResultBean(ResultBean.CODE_SUCCESS, 1, 1, "删除成功!");
//            }
//            return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQBODY_INVALID_MSGTYPE, 2, "删除错误!");
//        }else{
//            return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQBODY_INVALID_MSGTYPE, 2, "删除错误!");
//        }
    }

    /**
     * 查询混合库基因分型 新
     * @return
     * @throws IOException
     */
    @GetMapping(value = "/queryMixedDetails" , produces = "application/json; charset=utf-8")
    public ResponseData queryMixedDetails(@RequestParam("id") String id) throws IOException {
        try {
            MixedSampleGenePagingVo mixedSampleGenePagingVo=mixedSampleGeneService.selectPagingVoById(id);
            if (mixedSampleGenePagingVo != null){
                List<DnaGeneInfoDetail> dnaGeneInfoDetailList= JSON.parseArray(mixedSampleGenePagingVo.getGeneInfo(),DnaGeneInfoDetail.class);
                List<MixedGeneInfoDetail> mixedGeneInfoDetailList= new ArrayList<MixedGeneInfoDetail>();
                MixedGeneInfoDetail m=null;
                for(DnaGeneInfoDetail d:dnaGeneInfoDetailList){
                    m=new MixedGeneInfoDetail();
                    m.setMarkerName(d.getName());
                    m.setAllele(d.getValue()!=null?d.getValue().replaceAll("/",","):"");
                    mixedGeneInfoDetailList.add(m);
                }
                mixedSampleGenePagingVo.setGeneInfoDetail(mixedGeneInfoDetailList);

                //峰图
                if (StringUtils.isBlank(mixedSampleGenePagingVo.getGeneImagePath())){
                    mixedSampleGenePagingVo.setGeneImagePath(GlobalConstants.SAMPLE_GANE_IMAGEPATH);
                }
            }
            return new ResponseData(mixedSampleGenePagingVo);
        }catch (Exception ex){
            logger.error("invoke MixedTypingController.queryMixedDetails error", ex);
            return new ResponseData(OpenErrorCodes.ERR_INTERNAL_RESPONSE,ex.toString());
        }



////        List< Map<String,String>> listLocusName = new ArrayList<Map<String,String>>();
////        String gene = null;
//        JSONObject jsonObject = new JSONObject();
//
////        if (StringUtils.isNotBlank(geneInfo)){
////                String locusInfo = HttpClientUtil.doGet(locusNameUrl,null);
////                JSONObject resultObject = JSON.parseObject(locusInfo);
////                String dataResultStr = resultObject.getString("data");
////                List<HashMap> llocus = JSON.parseArray(dataResultStr, HashMap.class);
////                for (Object object : llocus ) {
////                    listLocusName.add((Map<String, String>) object);
////                }
////                logger.info("基因信息转换开始----------------------");
////                gene = geneformatUtils.geneFormatConversionNew(geneInfo,listLocusName);
////                logger.info("基因信息转换结束----------------------");
////                jsonObject= JSON.parseObject(gene);
////            }
//
//        if (StringUtils.isNotBlank(geneInfo) && StringUtils.isNotBlank(reagentKit)){
////            String geneInfo1 = geneSameCompareUtil.markerNameGeneInfo(geneInfo);
////            Map<String, List<String>> stringListMap = GeneformatUtils.mixedSampleGeneformat(geneInfo1);
////            if (stringListMap != null) {
////                Map<String, Object> stringObjectMap = geneSameCompareUtil.analysisGene(stringListMap);
////                return new ResultBean(ResultBean.CODE_SUCCESS, 1, stringObjectMap, "查询成功!");
////            }
//            System.out.println("===========查询基因信息==========");
//            List<Map<String,String>> listLocusName = new ArrayList<Map<String,String>>();
//            String geneInfos = null;
//            String gene = null;
//            if(gen == 1){
//                logger.info("查询基因座信息开始----------------------");
//                ResponseData responseData = geneListServerClient.selectByLocusName();
//                String dataResultStr = JSONObject.toJSONString(responseData.getResult());
//                List<HashMap> llocus = JSON.parseArray(dataResultStr, HashMap.class);
//                for (Object object : llocus ) {
//                    listLocusName.add((Map<String, String>) object);
//                }
//                logger.info("查询基因座信息结束----------------------");
//                logger.info("国家库基因信息转化开始----------------------");
//                geneInfos = geneformatUtils.geneFormatConversionNew(geneInfo, listLocusName);
//                gene = geneFormatConversionGj(geneInfos,reagentKit);
//                logger.info("国家库基因信息转化结束----------------------");
//            }else {
//                gene = geneInfo;
//            }
//
//
//
//            Map<String, List<String>> stringListMap = GeneformatUtils.mixedSampleGeneformat(gene);
//            if (stringListMap != null) {
//                Map<String, Object> stringObjectMap = geneSameCompareUtil.analysisGene(stringListMap);
//                return new ResultBean(ResultBean.CODE_SUCCESS, 1, stringObjectMap, "查询成功!");
//            }
//        }
//        return new ResultBean(ResultBean.CODE_SUCCESS, 1, jsonObject, "查询成功!");
    }






    /**
     * 查看比中详情 拆分比中单一和混合比中单一 列表 新
     */
    @RequestMapping(value = "/selectMatchResultList", produces = "application/json;charset=utf-8")
    public ResponseData selectMatchResultList(@RequestBody MixedTypingVo mixedTypingVo) throws IOException {
        if (mixedTypingVo != null){
            try {
                MatchResultDetailsListVo matchResultVos=mixedSampleGeneService.selectMatchResultList(mixedTypingVo);
                return new ResponseData(matchResultVos);
            }catch (Exception ex){
                logger.error("invoke MixedTypingController.selectMatchResultList error", ex);
                return new ResponseData(OpenErrorCodes.ERR_INTERNAL_RESPONSE,ex.toString());
            }
        }else {
            logger.error("传入参数为空！");
            return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
        }
    }





    /**
     * 比中详情 列表 查看比中详情按钮
     */
    @GetMapping(value = "/selectMatchDetails" , produces = "application/json; charset=utf-8")
    public ResponseData selectMatchDetails(HttpServletRequest request, @RequestParam("id") String id, @RequestParam("matchId") String matchId, @RequestParam("type") String type) throws IOException {
        try {
            //type为0表示拆分比中单一 type为1表示混合比中单一
            HashMap<Object, Object> matchResultDetailsVos=matchResulService.selectMatchDetails(id,matchId,type);

            return new ResponseData(matchResultDetailsVos);
        }catch (Exception ex){
            logger.error("invoke MixedTypingController.selectMatchDetails error", ex);
            return new ResponseData(OpenErrorCodes.ERR_INTERNAL_RESPONSE,ex.toString());
        }
    }



    /**
     * 查看比中详情
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/queryMatchResultMixSingle" , produces = "application/json; charset=utf-8")
    public ResultBean queryMatchResultMixSingle(HttpServletRequest request, String id,String resultType) throws IOException {
        if (StringUtils.isNotBlank(id)) {
            HashMap<String, Object> map = new HashMap<>();
            List<MatchResult> matchResults = new ArrayList<>();
            PageInfo pageInfo = new PageInfo();
            int count1 = 0;
            int count2 = 0;
            int count3 = 0;
            CompareQueue compareQueue = new CompareQueue();
            compareQueue.setMixedSampleId(id);
            compareQueue.setQueueType("1");
            List<CompareQueue> compareQueues = compareQueueService.selectByMixedSampleId(compareQueue);
            if (ListUtils.isNotNullAndEmptyList(compareQueues)) {
                MatchResult matchResult = new MatchResult();
                matchResult.setCompareId(compareQueues.get(0).getId());
                matchResult.setSampleGeneResultType(resultType);
                matchResult.setStartRum(0);
                matchResult.setEndRum(15);
//                matchResults = matchResultMixSingleService.selectByMatchResult1(matchResult);
//                int count = matchResultMixSingleService.selectMatchinfoCount(matchResult);
//                pageInfo.setAllRecordCount(count);
                //List<Map> jsonArray = JSONArray.parseArray(compareQueues.get(0).getGeneInfo().toString(), Map.class);
                Map<String, Object> jsonArray = JSONObject.parseObject(compareQueues.get(0).getGeneInfo());
//                System.out.println(jsonArray);
                if (ListUtils.isNotNullAndEmptyList(matchResults)) {
                    for (MatchResult result : matchResults) {
                        result.setSampleNo(compareQueues.get(0).getSampleNo());
                        result.setSiteNum(String.valueOf(jsonArray.size()));
                        if (StringUtils.isNotBlank(result.getGeneInfo())) {
                            Map<String, Object> jsonArray2 = JSONObject.parseObject(result.getGeneInfo());
                            if (jsonArray2.size() != 0) {
                                result.setThanInsiteNum(String.valueOf(jsonArray2.size()));
                            } else {
                                result.setThanInsiteNum("0");
                            }
                        }
                    }
                }
                //查询数量
                //拆分比单一样本
                matchResult.setSampleGeneResultType(Constants.RESULT_TYPE_04);
//                count1 = matchResultMixSingleService.selectMatchinfoCount(matchResult);
                //混合比单一样本
                matchResult.setSampleGeneResultType(Constants.RESULT_TYPE_01);
//                count2 = matchResultMixSingleService.selectMatchinfoCount(matchResult);
                //拆分比混合样本
                matchResult.setSampleGeneResultType(Constants.RESULT_TYPE_02);
//                count3 = matchResultMixSingleService.selectMatchinfoCount(matchResult);
            }
            map.put("matchResults", matchResults);
            map.put("pageInfo", pageInfo);
            map.put("count1", count1);
            map.put("count2", count2);
            map.put("count3", count3);
            return new ResultBean(ResultBean.CODE_SUCCESS, 0, map, "查询成功!");
        } else {
            return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQBODY_INVALID_MSGTYPE, 1, "传入参数为空!");
        }
    }


    /**
     * 查看比重详情各列表点击比中详情
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/queryMatchResultInfo" , produces = "application/json; charset=utf-8")
    public ResultBean queryMatchResultInfo(HttpServletRequest request, String id) throws IOException {
        if (StringUtils.isNotBlank(id)){
            HashMap<String, Object> objectObjectHashMap = new HashMap<>();
           /* MatchResult matchResults = matchResultMixSingleService.queryMatchResultInfo(id);
            if (StringUtils.isNotBlank(matchResults.getGenePicture())){
                objectObjectHashMap.put("ratioSampleGeneImagePath",matchResults.getGenePicture());
            }else{
                objectObjectHashMap.put("ratioSampleGeneImagePath",0);
            }*/
            CompareQueue compareQueue = new CompareQueue();
//            compareQueue.setId(matchResults.getCompareId());
            List<CompareQueue> compareQueues = compareQueueService.selectQueueList(compareQueue);
            if(ListUtils.isNotNullAndEmptyList(compareQueues)){
                if (StringUtils.isNotBlank(compareQueues.get(0).getGenePicture())){
                    objectObjectHashMap.put("splitdSampleGeneImagePath",compareQueues.get(0).getGenePicture());
                }else{
                    objectObjectHashMap.put("splitdSampleGeneImagePath",0);
                }
            }
//            int mixSameCount = 0;
//            Map<String, List<String>> stringListMap = GeneformatUtils.mixedSampleGeneformat(matchResults.getGeneInfo());
//            if (stringListMap != null) {
//                Map<String, Object> stringObjectMap = geneSameCompareUtil.analysisGene(stringListMap);
//                Map<String, List<String>> stringListMap2 = GeneformatUtils.mixedSampleGeneformat(matchResults.getGeneInfo());
//                if (stringListMap2 != null) {
//                    Map<String, Object> stringObjectMap2 = geneSameCompareUtil.analysisGene(stringListMap2);
//                }
//            }

//            Map<String, List<String>> map = GeneformatUtils.mixedSampleGeneformat(matchResults.getGeneInfo());
//            Map<String, List<String>> map1 = GeneformatUtils.mixedSampleGeneformat(compareQueues.get(0).getGeneInfo());
//            Map<String, Object> stringObjectMap = geneMixCompareUtil.compareResult(map, map1, Integer.valueOf(compareQueues.get(0).getMixsameCount()));
//            objectObjectHashMap.put("stringObjectMap",stringObjectMap);
            return new ResultBean(ResultBean.CODE_SUCCESS, 0, objectObjectHashMap, "查询成功!");
        }
        return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQBODY_INVALID_MSGTYPE, 1, "查询失败!");
    }

    /**
     * 查看比中结果
     * @return
     * @throws IOException
     */
//    @RequestMapping(value = "/queryMatchResultList" , produces = "application/json; charset=utf-8")
//    public ResultBean queryMatchResultList(HttpServletRequest request, String id) throws IOException {
//        ArrayList<MatchResult> matchList = new ArrayList<>();
//        if (StringUtils.isNotBlank(id)){
//            CompareQueue compareQueue = new CompareQueue();
//            compareQueue.setPersonType(id);
//            compareQueue.setStatus("1");
//            List<CompareQueue> compareQueues = compareQueueService.selectQueueList(compareQueue);
//            if (compareQueues.size() != 0){
//                for (CompareQueue queue : compareQueues) {
//                    MatchResult matchResult = new MatchResult();
//                    matchResult.setCompareId(queue.getId());
//                    matchResult.setStartRum(0);
//                    matchResult.setEndRum(10);
//                    List<MatchResult> matchResults = matchResultMixSingleService.selectByMatchResult(matchResult);
//                    if (matchResults.size() != 0){
//                        matchResults.get(0).setSampleNo(queue.getSampleNo());
//                        matchList.add(matchResults.get(0));
//                    }
//                }
//
//            }
//
//        }
//        return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQBODY_INVALID_MSGTYPE, matchList, "查询失败!");
//    }


    /*
    *   比对记录---查看比中结果列表
    * */
    @RequestMapping(value = "/queryMatchResultList" , produces = "application/json; charset=utf-8")
    public ResultBean queryMatchResultList(@RequestBody CompareQueueModel compareQueueModel) throws IOException {
        HashMap<String, Object> map = new HashMap<>();
        String compareId = compareQueueModel.getCompareId();
        int page = compareQueueModel.getPage();
        if (StringUtils.isNotBlank(compareId) && page != 0){
            String sampleNo = null;//混合样本编号
            String sampleName = null;//混合样本名称
            String geneInfo = null;//样本源基因信息
            String personName = null;//人员类型
            String district = null;//地区
            String mixSameCount = null;//匹配下限
            String condition = null;//容差
            String mixCount = "0";//几人混合
            List<ContributorInfo> contributorInfoList = new ArrayList<>();
            int contributorCount = 0;//贡献者数量
            MatchResultVo query = new MatchResultVo();
            PageInfo pageInfo = new PageInfo();
            //查询队列信息
            CompareQueue queue = compareQueueService.selectByPrimaryKey(compareId);
            if (queue != null ){
                if (queue.getQueueType().equals(Constants.QUEUE_TYPE_01)){
                    //查询混合样本信息
                    MixedSampleGene mixedSampleGene = mixedSampleGeneService.selectById(queue.getMixedSampleId());
                    if (mixedSampleGene != null){
                        sampleNo = mixedSampleGene.getSampleNo();
                        sampleName = mixedSampleGene.getSampleName();
                        geneInfo = mixedSampleGene.getGeneInfo();
                        mixCount = mixedSampleGene.getContributorCount();
                        //贡献者信息
                        contributorInfoList = contributorInfoService.findListByMixSampleGeneId(mixedSampleGene.getId());
                        contributorCount = contributorInfoList.size();
                    }
                }else {
                    //拆分样本信息
                    SplitedSampleGene splitedSampleGene = splitedSampleGeneService.selectByCompareQueueId(queue.getId());
                    if (splitedSampleGene != null){
                        geneInfo = splitedSampleGene.getGeneInfo();
                        //混合样本信息
                        MixedSampleGene mixedSampleGene = mixedSampleGeneService.selectById(splitedSampleGene.getMixedSampleGeneId());
                        if (mixedSampleGene != null){
                            sampleNo = mixedSampleGene.getSampleNo();
                            sampleName = mixedSampleGene.getSampleName();
                        }
                    }
                }
                personName = queue.getPersonTypeName();
                district = queue.getDistrictName();
                mixSameCount = queue.getMixsameCount();
                condition = queue.getCondition();
            }
            //查询比中信息
            pageInfo.setPage(page);
            query.getEntity().setCompareId(compareId);
            List<MatchResult> matchResults = matchResulService.selectByMatchResultVo(query,pageInfo);
            if(ListUtils.isNotNullAndEmptyList(matchResults)){
                for (MatchResult result : matchResults) {
                    //时间格式转化
                    if (null != result.getComparisonTime()){
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String currentDateStr = sdf.format(result.getComparisonTime());
                        result.setDatetime(currentDateStr);
                    }
                    //比中信息查询
                    /*if (StringUtils.isBlank(result.getProportionPersonName())){
                        String singleGeneId = result.getSingleGeneId();
                        String districtCode = result.getProportionDistrictCode();
                        String personCode = result.getProportionPersonCode();
                        if (StringUtils.isNotBlank(singleGeneId) && StringUtils.isNotBlank(districtCode) && StringUtils.isNotBlank(personCode)){
                            ResponseData caseInfoSampleNo = geneListServerClient.getCaseInfoSampleNo(singleGeneId,districtCode,personCode);
                            Gson gson = new Gson();
                            newSampleInfo geneVo  = gson.fromJson(gson.toJson(caseInfoSampleNo.getResult()), new TypeToken<newSampleInfo>(){}. getType());
                            if (geneVo != null){
                                result.setProportionCaseNo(geneVo.getCaseNo());//案件编号
                                result.setProportionCaseName(geneVo.getCaseName());//案件名称
                                result.setProportionSampleNo(geneVo.getSampleLabNo());//检材编号
                                result.setProportionSampleName(geneVo.getSampleName());//检材名称
                                result.setProportionPersonName(geneVo.getPersonName());//人员姓名
                                result.setProportionPersonnel(geneVo.getPersonTypeName());//人员类型
                                result.setIdCardNo(geneVo.getIdCardNo());//身份证号
                                result.setProportionDistrict(geneVo.getLabServerName());//地区
                                result.setProportionKilName(geneVo.getKilName());//试剂盒
                                try {
                                    matchResulService.updateByPrimaryKey(result);
                                }catch (Exception e){
                                    logger.error("insert error:", e);
                                }
                            }
                        }
                    }*/
                }
            }
            int count = matchResulService.selectByMatchResultCount(query);
            pageInfo.setAllRecordCount(count);
            map.put("pageInfo",pageInfo);
            map.put("matchResults",matchResults);
            map.put("geneInfo",geneInfo);
            map.put("queueSampleNo",sampleNo);
            map.put("queueSampleName",sampleName);
            map.put("mixCount",mixCount);
            map.put("contributorInfoList",contributorInfoList);
            map.put("contributorCount",contributorCount);
            map.put("personName",personName);
            map.put("district",district);
            map.put("mixSameCount",mixSameCount);
            map.put("condition",condition);
            return new ResultBean(ResultBean.CODE_SUCCESS, 0, map, "查询成功!");
        } else {
            return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQBODY_INVALID_MSGTYPE, map, "传入参数为空!");
        }
    }

    /**
     * 查看比中详情
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/queryMatchResult" , produces = "application/json; charset=utf-8")
    public ResultBean queryMatchResult(HttpServletRequest request, String id) throws IOException {
        if (StringUtils.isNotBlank(id)){
            HashMap<Object, Object> hashMap = new HashMap<>();
            MatchResult matchResult = new MatchResult();
            matchResult.setCompareId(id);
//            List<MatchResult> matchResults = matchResultMixSingleService.queryByMatchResult(matchResult);
//            if (ListUtils.isNotNullAndEmptyList(matchResults)){
//                CompareQueue compareQueue = new CompareQueue();
//                compareQueue.setId(matchResults.get(0).getCompareId());
////                compareQueue.setStatus("1");
//                List<CompareQueue> compareQueues = compareQueueService.selectQueueList(compareQueue);
//                if (ListUtils.isNotNullAndEmptyList(compareQueues)){
////                matchResults.get(0).setGenePicture2(compareQueues.get(0).getGenePicture());
//                    matchResults.get(0).setQueueSampleNo(compareQueues.get(0).getSampleNo());
//                    Map<String, List<String>> map = GeneformatUtils.mixedSampleGeneformat(matchResults.get(0).getGeneInfo());
//                    Map<String, List<String>> map1 = GeneformatUtils.mixedSampleGeneformat(compareQueues.get(0).getGeneInfo());
//                    Map<String, Object> stringObjectMap = geneMixCompareUtil.compareResult(map, map1, Integer.valueOf(compareQueues.get(0).getMixsameCount()));
//                    matchResults.get(0).setGeneInfos(stringObjectMap);
//                    if (StringUtils.isNotBlank(matchResults.get(0).getGenePicture())){
//                        hashMap.put("ratioSampleGeneImagePath",matchResults.get(0).getGenePicture());
//                    }else{
//                        hashMap.put("ratioSampleGeneImagePath",0);
//                    }
//                    if (StringUtils.isNotBlank(compareQueues.get(0).getGenePicture())){
//                        hashMap.put("splitdSampleGeneImagePath",compareQueues.get(0).getGenePicture());
//                    }else{
//                        hashMap.put("splitdSampleGeneImagePath",0);
//                    }
//                }
//            }else{
//                hashMap.put("ratioSampleGeneImagePath",0);
//                hashMap.put("splitdSampleGeneImagePath",0);
//            }
//            hashMap.put("matchResults",matchResults);
////            Map<String, List<String>> stringListMap = GeneformatUtils.mixedSampleGeneformat(matchResults.get(0).getGeneInfo());
//
////            if (stringListMap != null) {
////                Map<String, Object> stringObjectMap = geneSameCompareUtil.analysisGene(stringListMap);
////                matchResults.get(0).setGeneInfos(stringObjectMap);
////                return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQBODY_INVALID_MSGTYPE, matchResults, "查询成功!");
////            }
            return new ResultBean(ResultBean.CODE_SUCCESS, 0, hashMap, "查询成功!");

        }
        return new ResultBean(ResultBean.CODE_ERROR, ErrorCode.IBOAN_REQBODY_INVALID_MSGTYPE, 1, "查询失败!");
    }


    @SuppressWarnings("rawtypes")
	public  String geneFormatConversionGj(String gene, String reagentKitId) {
        String geneInfos = null;
        try {
            ResponseData responseData = kitClientService.selectByKitId(reagentKitId);
            String dataResultStr = JSONObject.toJSONString(responseData.getResult());
            List<Map> resultUserVOS = JSON.parseArray(dataResultStr, Map.class);
            Map<Object, Object> GeneInfo = new LinkedHashMap<>();
            for (int i = 0; i < resultUserVOS.size(); i++) {
                String locusName = (String) resultUserVOS.get(i).get("locusName");
                Map<String, Object> jsonToMap = JSONObject.parseObject(gene);
                if ( jsonToMap.containsKey(locusName) == true) {
                    GeneInfo.put(locusName, jsonToMap.get(locusName));
                }

                if(jsonToMap.containsKey(locusName) == false){
                    List<String> stirngList = new ArrayList<>();
                    stirngList.add("");
                    GeneInfo.put(locusName,stirngList);
                }
            }
            geneInfos = JSON.toJSONString(GeneInfo);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("=======国家库基因信息转换失败========");
        }
        return geneInfos;
    }
}
