package com.bazl.dna.mix.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bazl.dna.common.ResponseData;
import com.bazl.dna.common.filter.AuthUser;
import com.bazl.dna.mix.client.GeneListServerClient;
import com.bazl.dna.mix.client.SampleDataServicr;
import com.bazl.dna.mix.constants.Constants;
import com.bazl.dna.mix.constants.ErrorCodes;
import com.bazl.dna.mix.constants.ErrorInfo;
import com.bazl.dna.mix.controller.base.BaseController;
import com.bazl.dna.mix.controller.base.error.ErrorCode;
import com.bazl.dna.mix.controller.base.error.ErrorMsgManager;
import com.bazl.dna.mix.dao.DictCountDAO;
import com.bazl.dna.mix.dao.MatchResultMapper;
import com.bazl.dna.mix.dao.MixedSampleGeneDAO;
import com.bazl.dna.mix.dao.SingleSampleGeneDAO;
import com.bazl.dna.mix.model.po.CompareQueue;
import com.bazl.dna.mix.model.po.DictCount;
import com.bazl.dna.mix.model.po.MatchResult;
import com.bazl.dna.mix.model.po.MixedSampleGene;
import com.bazl.dna.mix.model.po.SplitedSampleGene;
import com.bazl.dna.mix.service.CompareQueueService;
import com.bazl.dna.mix.service.MatchResulService;
import com.bazl.dna.mix.service.MixedSampleGeneService;
import com.bazl.dna.mix.service.SplitedSampleGeneService;
import com.bazl.dna.mix.utils.GeneMixCompareUtil;
import com.bazl.dna.mix.utils.GeneSameCompareUtil;
import com.bazl.dna.mix.utils.GeneformatUtils;
import com.bazl.dna.mix.utils.ListUtils;
import com.bazl.dna.util.DateTools;
import com.bazl.dna.util.RequestUtils;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Created by Administrator on 2019/12/17.
 */
@RestController
@RequestMapping("/home")
public class HomePageController extends BaseController {

    @Autowired
    CompareQueueService compareQueueService;
    @Autowired
    MixedSampleGeneDAO mixedSampleGeneDAO;
    @Autowired
    SingleSampleGeneDAO singleSampleGeneDAO;
    @Autowired
    MatchResultMapper matchResultMapper;
    @Autowired
    DictCountDAO dictCountDAO;
    @Autowired
    MatchResulService matchResulService;
    @Autowired
    MixedSampleGeneService mixedSampleGeneService;
    @Autowired
    SplitedSampleGeneService splitedSampleGeneService;
    @Autowired
    GeneMixCompareUtil geneMixCompareUtil;
    @Autowired
    SampleDataServicr sampleDataService;
    @Autowired
    GeneListServerClient geneListServerClient;
    @Autowired
    @Qualifier("jsonRedisTemplate")
    private RedisTemplate<String, Object> jsonRedisTemplate;
    
    /**
     *  首页   混合拆分分型数量
     */
    @RequestMapping(value = "/compareCount")
    public ResponseData compareCount() {
        try {
            Map<String, Object> result = new HashMap<>();
            AuthUser user = RequestUtils.getAuthUser();
            CompareQueue compareQueue = new CompareQueue();
            //查询混合分型的数量
            compareQueue.setQueueType(Constants.QUEUE_TYPE_01);
            compareQueue.setQueueFlag(Constants.QUEUE_FLAG_1);
            compareQueue.setCreatePerson(user.getUsername());
            List<CompareQueue> mixcount = compareQueueService.selectQueueList(compareQueue);
            result.put("mixcount", mixcount.size());
            //查询拆分分型的数量
            compareQueue.setQueueType(Constants.QUEUE_TYPE_02);
            compareQueue.setQueueFlag(Constants.QUEUE_FLAG_1);
            compareQueue.setCreatePerson(user.getUsername());
            List<CompareQueue> singcount = compareQueueService.selectQueueList(compareQueue);
            result.put("splitcount", singcount.size());
            return new ResponseData(result);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ResponseData(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION,
                    ErrorMsgManager.GetErrorMsg(ErrorCode.IBOAN_REQJSON_PARSEMSG_EXCEPTION));
        }
    }

    /**
     *  首页   混合样本，单一样本
     */
    @RequestMapping(value = "/sampleCount")
    public ResponseData sampleCount() {
        Map<String, List<Integer>> result = new HashMap<>();
		//查询当月的混合库数据
		DictCount dictCount = new DictCount();

		// 查询混合基因信息(关联案件，地区，样本，字典项) 总数
		/*ResponseData mixGeneData = geneListServerClient.selectMixGeneByCwsd();
		JSONObject mixJson = JSONObject.parseObject(JSONObject.toJSONString(mixGeneData.getResult()));
		int msc = mixJson.getIntValue("mixGeneCount");*/
        Object o = jsonRedisTemplate.opsForValue().get("mixGeneCount");
        if (o != null){
            int msc = (int) o;
            dictCount.setDictTypeCode(Constants.DICT_TYPE_MIXCOUNT_SAMPLE);
            dictCount.setDictCountNumber(msc+"");
            saveDictCount(dictCount);
        }

		//获取国家库单一样本总数量json串
		/*ResponseData singleGeneData = geneListServerClient.selectSingleGeneByCwsd();
		JSONObject singleJson = JSONObject.parseObject(JSONObject.toJSONString(singleGeneData.getResult()));
		int ssc = singleJson.getIntValue("singleGeneCount");*/
        Object o1 = jsonRedisTemplate.opsForValue().get("singleGeneCount");
        if (o1 != null){
            int ssc = (int) o1;
            dictCount.setDictTypeCode(Constants.DICT_TYPE_SINGCOUNT_SAMPLE);
            dictCount.setDictCountNumber(ssc+"");
            saveDictCount(dictCount);
        }

		List<Integer> mixSampleCountList = new ArrayList<>();
		List<Integer> singSampleCountList = new ArrayList<>();

		for (int i=1;i<13;i++){
		    //获取当前年份和月份
		    String year = String.valueOf(DateTools.getToYear());
//              String month = DateUtils.getCurrentMonthStr();


		    //查询当年的混合数量
		    DictCount dict = new DictCount();
		    dict.setDictTypeCode(Constants.DICT_TYPE_MIXCOUNT_SAMPLE);
		    dict.setYears(year);
		    dict.setMonths(Integer.toString(i));
		    DictCount count1 = dictCountDAO.selectByYears(dict);
		    if (count1 == null){
		        mixSampleCountList.add(0);
		    }else{
		        mixSampleCountList.add(Integer.parseInt(count1.getDictCountNumber()));
		    }
		    result.put("mixcount", mixSampleCountList);


		    //查询当年的单一数量
		    dict.setDictTypeCode(Constants.DICT_TYPE_SINGCOUNT_SAMPLE);
		    dict.setYears(year);
		    dict.setMonths(Integer.toString(i));
		    DictCount count2 = dictCountDAO.selectByYears(dict);
		    if (count2 == null){
		        singSampleCountList.add(0);
		    }else{
		        singSampleCountList.add(Integer.parseInt(count2.getDictCountNumber()));
		    }
		    result.put("singcount", singSampleCountList);


		    //查询当年的拆分分型数量
//                dict.setDictTypeCode(Constants.DICT_TYPE_SPLITCOUNT_TYPING);
//                dict.setYears(year);
//                dict.setMonths(Integer.toString(i));
//                DictCount count3 = dictCountDAO.selectByYears(dict);
//                if (count3 == null){
//                    splitTypingCountList.add(0);
//                }else{
//                    splitTypingCountList.add(Integer.parseInt(count3.getDictCountNumber()));
//                }
//                result.put("splitTypingCountList", splitTypingCountList);


		    //查询当年的混合分型数量
//                dict.setDictTypeCode(Constants.DICT_TYPE_MIXCOUNT_TYPING);
//                dict.setYears(year);
//                dict.setMonths(Integer.toString(i));
//                DictCount count4 = dictCountDAO.selectByYears(dict);
//                if (count4 == null){
//                    mixTypingCountList.add(0);
//                }else{
//                    mixTypingCountList.add(Integer.parseInt(count4.getDictCountNumber()));
//                }
//                result.put("mixTypingCountList", mixTypingCountList);

		}
		return new ResponseData(result);
    }

    public void saveDictCount(DictCount dictCount){
        try {
            //插入当前月数据
            String year = String.valueOf(DateTools.getToYear());
            String month = String.valueOf(DateTools.getToMonth());
            String substring = null;
            if (month.contains("0")){
                substring = month.substring(1, 2);
            }
            dictCount.setYears(year);
            dictCount.setMonths(substring);
            DictCount count = dictCountDAO.selectByYears(dictCount);
            if (count != null){
                dictCount.setId(count.getId());
                dictCountDAO.updateByPrimaryKey(dictCount);
            }else {
                dictCount.setId(UUID.randomUUID().toString());
                dictCountDAO.insert(dictCount);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("插入首页数量错误！！！" + e.getMessage());
        }
    }



    /**
     *  首页   违法犯罪人员，嫌疑人员，失踪人员，质控人员，现场物证，受害人，其他的数量
     */
    @RequestMapping(value = "/matchResultSortCount")
    public ResponseData matchResultSortCount() throws Exception {
        // 获得用户对象
        AuthUser user = RequestUtils.getAuthUser();
        if (user != null && user.getId() != null){
            List<Map<String, Object>> list = Lists.newArrayList();
            ResponseData resultBean = sampleDataService.selectPersonCategory();
            Gson gson = new Gson();
            List<Map<String,Object>> resultUserVOS  = gson.fromJson(gson.toJson(resultBean.getResult()), new TypeToken<List<Map<String,Object>>>(){}. getType());
            if(ListUtils.isNotNullAndEmptyList(resultUserVOS)){
                for (Map<String,Object> map : resultUserVOS){
                    int count = 0;
                    Map<String, Object> result = new HashMap<>();
                    @SuppressWarnings("unchecked")
					List<Map<String,String>> mapList = (List<Map<String,String>>)map.get("val");
                    String name = (String)map.get("name");
                    result.put("name",name);
                    if (ListUtils.isNotNullAndEmptyList(mapList)){
                        for (Map<String,String> dict : mapList){
                            String dictKey = dict.get("dictKey");
                            List<MatchResult> matchResults = matchResultMapper.querymatchResultSort(dictKey,user.getId());
                            count += matchResults.size();
                        }
                    }
                    result.put("count",count);
                    list.add(result);
                }
            }
            return new ResponseData(list);
        }else {
            logger.error("用户信息错误！");
            return new ResponseData(0, "用户信息错误!");
        }
    }

    /*
     *   首页---最新比中
     * */
    @RequestMapping(value = "/findNewestList", produces = "application/json;charset=UTF-8")
    public ResponseData findNewestList() throws Exception {
        // 获得用户对象
        AuthUser user = RequestUtils.getAuthUser();
        if (user != null && user.getId() != null){
            try {
                List<MatchResult> matchResults = matchResulService.selectByThirtyMatchList(user.getId());
                if (ListUtils.isNotNullAndEmptyList(matchResults)){
                    for (MatchResult result : matchResults){
                        CompareQueue queue = compareQueueService.selectByPrimaryKey(result.getCompareId());
                        if (queue != null){
                            MixedSampleGene sampleGene = mixedSampleGeneService.selectById(queue.getMixedSampleId());
                            if(sampleGene != null){
                                result.setSampleName(sampleGene.getSampleName());
                                result.setSampleNo(sampleGene.getSampleNo());
                            }
                        }
                        if(result.getSampleGeneResultType().equals(Constants.RESULT_TYPE_01)){
                            result.setSampleFlag("混合比中单一");
                        }else if(result.getSampleGeneResultType().equals(Constants.RESULT_TYPE_02)){
                            result.setSampleFlag("拆分比中混合");
                        }else if(result.getSampleGeneResultType().equals(Constants.RESULT_TYPE_03)){
                            result.setSampleFlag("混合比中质控");
                        }else if(result.getSampleGeneResultType().equals(Constants.RESULT_TYPE_04)){
                            result.setSampleFlag("拆分比中单一");
                        }
                    }
                }
                return new ResponseData(matchResults);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseData(0, "请求失败！");
            }
        }else {
            logger.error("用户信息错误！");
            return new ResponseData(0, "用户信息错误！");
        }
    }

    /*
    *     查看比中详情基因信息
    * */
    @RequestMapping(value = "/findNewestGene", produces = "application/json;charset=UTF-8")
    public ResponseData findNewestGene(String id,String compareId) throws Exception {
        if (StringUtils.isNotBlank(id) && StringUtils.isNotBlank(compareId)){
            Map<String,Object> resultMap = new HashMap<>();
            MatchResult matchResult = matchResulService.selectByPrimaryKey(id);
            if (matchResult != null){
                CompareQueue queue = compareQueueService.selectByPrimaryKey(compareId);
                if (queue != null){
                    //查询对应的数据获取基因信息
                    if (queue.getQueueType().equals(Constants.QUEUE_TYPE_01)){
                        //如果是混合类型
                        MixedSampleGene sampleGene = mixedSampleGeneService.selectById(queue.getMixedSampleId());
                        if (sampleGene != null){
                            //基因格式转换
                            String geneInfoString = GeneSameCompareUtil.getGeneInfoString(sampleGene.getGeneInfo());
                            Map<String, List<String>> map = GeneformatUtils.mixedSampleGeneformat(geneInfoString);
                            String geneInfoString1 = GeneSameCompareUtil.getGeneInfoString(matchResult.getGeneInfo());
                            Map<String, List<String>> map1 = GeneformatUtils.mixedSampleGeneformat(geneInfoString1);
                            Map<String, Object> stringObjectMap = geneMixCompareUtil.compareResultFlag(map, map1, Integer.valueOf(queue.getMixsameCount()));
                            resultMap.put("stringObjectMap",stringObjectMap);
                            if (StringUtils.isNotBlank(sampleGene.getGenePicture())){
                                resultMap.put("splitdSampleGeneImagePath",sampleGene.getGenePicture());
                            }else {
                                resultMap.put("splitdSampleGeneImagePath",0);
                            }
                        }
                    }else if (queue.getQueueType().equals(Constants.QUEUE_TYPE_02)){
                        //如果是拆分类型
                        SplitedSampleGene splitedSampleGene = splitedSampleGeneService.selectByCompareQueueId(queue.getId());
                        if (splitedSampleGene != null){
                            //基因格式转换
                            String geneInfoString = GeneSameCompareUtil.getGeneInfoString(splitedSampleGene.getGeneInfo());
                            Map<String, List<String>> map = GeneformatUtils.mixedSampleGeneformat(geneInfoString);
                            String geneInfoString1 = GeneSameCompareUtil.getGeneInfoString(matchResult.getGeneInfo());
                            Map<String, List<String>> map1 = GeneformatUtils.mixedSampleGeneformat(geneInfoString1);
                            Map<String, Object> stringObjectMap = geneMixCompareUtil.compareResultFlag(map, map1, Integer.valueOf(queue.getMixsameCount()));
                            resultMap.put("stringObjectMap",stringObjectMap);
                            resultMap.put("splitdSampleGeneImagePath",0);
                        }
                    }
                    if (StringUtils.isNotBlank(matchResult.getGenePicture())){
                        resultMap.put("ratioSampleGeneImagePath",matchResult.getGenePicture());
                    }else {
                        resultMap.put("ratioSampleGeneImagePath",0);
                    }
                }
            }
            return new ResponseData(resultMap);
        }else {
            logger.error("传入参数为空！");
            return new ResponseData(ErrorCodes.ERR_PARAM, ErrorInfo.ERR_PARAM);
        }
    }
    
    /**
     * 混合比对统计数据
     * @return
     */
    @RequestMapping("getMixAndMatchResultCount")
    public ResponseData getMixAndMatchResultCount() throws Exception {
        // 获得用户对象
        AuthUser user = RequestUtils.getAuthUser();
        if (user != null && user.getId() != null){
            Map<String,Object> resultMap = new HashMap<>();
            int mixCount = mixedSampleGeneService.selectMixCount(user.getId());
            resultMap.put("mixCount", mixCount);
            resultMap.put("totalMixCount", mixCount*3);
            int effectCount = matchResulService.selectMatchResultByEffect("1",user.getId());
            int totalEffectCount = matchResulService.selectMatchResultByEffect(null,user.getId());
            resultMap.put("effectCount", effectCount);
            resultMap.put("totalEffectCount", totalEffectCount);
            return new ResponseData(resultMap);
        }else {
            logger.error("用户信息错误！");
            return new ResponseData(0,"用户信息错误！");
        }
    }
}
