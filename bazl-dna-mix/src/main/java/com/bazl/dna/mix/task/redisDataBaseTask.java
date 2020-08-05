package com.bazl.dna.mix.task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.mix.algorithm.comparator.MixStrComparator;
import com.bazl.dna.mix.algorithm.params.CompareParams;
import com.bazl.dna.mix.algorithm.result.MixStrCompareResult;
import com.bazl.dna.mix.client.GeneListServerClient;
import com.bazl.dna.mix.client.ISysUserClient;
import com.bazl.dna.mix.constants.Constants;
import com.bazl.dna.mix.dao.MobileNewsMapper;
import com.bazl.dna.mix.model.po.CompareQueue;
import com.bazl.dna.mix.model.po.MatchResult;
import com.bazl.dna.mix.model.po.MobileNews;
import com.bazl.dna.mix.model.po.SplitedSampleGene;
import com.bazl.dna.mix.model.po.SysDict;
import com.bazl.dna.mix.model.po.mixContributorBean;
import com.bazl.dna.mix.model.po.newSampleInfo;
import com.bazl.dna.mix.model.vo.SampleDnaGeneVo;
import com.bazl.dna.mix.rebbitmq.ISendMixCompareService;
import com.bazl.dna.mix.service.CompareQueueService;
import com.bazl.dna.mix.service.MatchResulService;
import com.bazl.dna.mix.service.SplitedSampleGeneService;
import com.bazl.dna.mix.utils.GeneformatUtils;
import com.bazl.dna.mix.utils.ListUtils;
import com.bazl.dna.mix.utils.RedisUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component
public class redisDataBaseTask {

    private final Logger logger = LoggerFactory.getLogger(getClass());

//    @Value("${singleGeneCountUrl}")
//    private String singleGeneCountUrl;
//    @Value("${locusNameUrl}")
//    private String locusNameUrl;
//    @Value("${singleGeneUrl}")
//    private String singleGeneUrl;
    //基因信息转换条件
    @Value("${Gen}")
    private int gen;

    @Autowired
    GeneListServerClient geneListServerClient;

    @Autowired
    GeneformatUtils geneformatUtils;

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    CompareQueueService compareQueueService;

    @Autowired
    MatchResulService matchResulService;

    @Autowired
    MobileNewsMapper mobileNewsMapper;

    @Autowired
    SplitedSampleGeneService selectByCompareQueueId;
    @Autowired
    @Qualifier("jsonRedisTemplate")
    private RedisTemplate<String, Object> jsonRedisTemplate;
    @Autowired
    private ISysUserClient sysUserClient;

    @Autowired
    ISendMixCompareService sendMixCompareService;

    //@Scheduled(fixedDelay = 86400000)
    public void redisCount(){
        try {
            // 查询混合基因信息(关联案件，地区，样本，字典项) 总数
            ResponseData mixGeneData = geneListServerClient.selectMixGeneByCwsd();
            if (mixGeneData != null && mixGeneData.getCode() == 1){
                JSONObject mixJson = JSONObject.parseObject(JSONObject.toJSONString(mixGeneData.getResult()));
                int msc = mixJson.getIntValue("mixGeneCount");
                jsonRedisTemplate.opsForValue().set("mixGeneCount",msc);
            }
            //获取国家库单一样本总数量
            ResponseData singleGeneData = geneListServerClient.selectSingleGeneByCwsd();
            if (singleGeneData != null && singleGeneData.getCode() == 1){
                JSONObject singleJson = JSONObject.parseObject(JSONObject.toJSONString(singleGeneData.getResult()));
                int ssc = singleJson.getIntValue("singleGeneCount");
                jsonRedisTemplate.opsForValue().set("singleGeneCount",ssc);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("往redis添加数量失败！");
        }
    }


//    @Scheduled(fixedDelay = 86400000)  //间隔一天
    //@Scheduled(cron = "0 0 1 1/3 * ?") //每三天凌晨一点执行一次
//    @Scheduled(fixedDelay = 86000)
    @SuppressWarnings({ "unchecked", "unused" })
	public void redisDataBase(){
        /*String redisInsrtDate = redisUtils.get("RedisInsrtDate").toString();
        String newDate = DateUtils.dateToString(new Date(), "yyyy-MM-dd");*/
        /*logger.info("==================获取国家库样本总数量开始==================");
        //获取国家库单一样本总数量json串
        int singleCount = 0;
        ResponseData responseData = geneListServerClient.selectSingleGeneByCwsd();
        if (1 != responseData.getCode()){
            logger.info("----------获取国家库样本总数量-----------");
        }else{
            Gson gson = new Gson();
            HashMap<String,String> map  = gson.fromJson(responseData.getResult().toString(), new TypeToken<HashMap<String,String>>(){}. getType());
            System.out.println(map.get("singleGeneCount"));
            singleCount = Integer.parseInt(map.get("singleGeneCount")) ;
//            redisUtils.set("SampleSum",singleCount);
            jsonRedisTemplate.opsForValue().set("SampleSum",singleCount,-1);

        }
        logger.info("==================获取国家库样本总数量查询结束==================");*/
        try {
            look:
                    logger.info("===========查询基因信息==========");
                    List<Map<String,String>> listLocusName = new ArrayList<Map<String,String>>();
                    logger.info("------------查询基因座信息开始----------");
                    ResponseData locusInfo = geneListServerClient.selectByLocusName();
                    if (1 != locusInfo.getCode()){
                        logger.info("----------查询基因座信息失败-----------");
                    }else{
                        Gson gson = new Gson();
                        List<Map<String, String>> llocus  = gson.fromJson(locusInfo.getResult().toString(), new TypeToken<List<Map<String, String>>>(){}. getType());
                        for (Object object : llocus ) {
                            listLocusName.add((Map<String, String>) object);
                        }
                        logger.info("-----------------查询基因座信息结束--------------------");
                        int num = 0;
                        int number = 0;
                        boolean flag = true;
                        while (flag) {
                            logger.info("==================查询单一样本基因信息开始==================");
                            ResponseData selectSingleGeneByCwsdPage = geneListServerClient.selectSingleGeneByCwsdPage(num + "",1000);

                            if (1 != selectSingleGeneByCwsdPage.getCode()){
                                logger.info("==================查询单一样本基因信息结束==================");
                                break;//直接跳出while语句
                            }else{
                                List<SampleDnaGeneVo> postList  = gson.fromJson(gson.toJson(selectSingleGeneByCwsdPage.getResult()), new TypeToken<List<SampleDnaGeneVo>>(){}. getType());
                                num++;
                                if (ListUtils.isNullOrEmptyList(postList)) {
                                    break;
                                } else {
                                    number += postList.size();
                                    logger.info("==================查询单一样本基因信息结束==================");
                                    logger.info("==================组装基因信息开始==================");
                                    for (SampleDnaGeneVo geneVo : postList) {
                                        String gene = null;
                                        if (gen == 1){
                                            //logger.info("------基因信息转换开始------");
                                            gene = geneformatUtils.geneFormatListTask(geneVo.getEntity().getGeneInfo(),listLocusName);
                                            geneVo.getEntity().setGeneInfo(gene);
                                            //logger.info("------基因信息转换结束------");
                                        }else{
                                            gene = geneVo.getEntity().getGeneInfo();
                                        }
                                        String key = geneVo.getEntity().getGeneType()+"_"+geneVo.getPersonCode()+"_"+geneVo.getEntity().getSampleId();
//                                        redisUtils.set(key,gene,1000000);//有效时间后期删除
//                                        jsonRedisTemplate.opsForValue().set(key,gene,60, TimeUnit.MINUTES);
                                        jsonRedisTemplate.opsForValue().set(key,gene);
                                        //redisUtils.set(key,gene);
                                    }
                                    //获得完整基因信息SampleDnaGeneList
                                    logger.info("==================组装基因信息结束==================");
                                }
                            }

                        }

                    }
        } catch (Exception e) {
            logger.error("===========查询基因信息失败！==========" + e);
        }

    }

 /*   @Autowired
    @Qualifier("jsonRedisTemplate")
    private RedisTemplate<String, Object> jsonRedisTemplate;
    @Scheduled(fixedDelay = 1000)
    public void firs11() throws Exception {
        for (int i = 0 ; i<= 10 ;i++ ){
            redisUtils.set("SampleSum_"+i,"1231"+i,12312);
            jsonRedisTemplate.opsForValue().set("SampleSum_"+i, "1231"+i, 1, TimeUnit.MINUTES);

        }
//        ArrayList arrayList = redisUtils.lGetLike("*_SampleSum");

        List<String> keys = redisUtils.keys("*SampleSum_*");
        System.out.println(keys);
        //redisUtils.scan("*_SampleSum");
//        System.out.println(arrayList);
    }*/

  /*  @Scheduled(fixedDelay = 1000)
    public void firs11() throws Exception {
        String key = "1_010201_8a40948c43682b0d0143790504b414a6";
        String geneInfo = redisUtils.get(key).toString();
        System.out.println(geneInfo);
    }
*/
    /*
     *    混合比对单一基因
     * */
    //@Async
   // @Scheduled(fixedDelay = 18000)
    public void first2() throws Exception {
        try {
                Gson gson = new Gson();
                /*logger.info("==================获取国家库样本总数量开始==================");
                //获取国家库单一样本总数量json串
                int singleCount = 0;
                String sampleSum = redisUtils.get("SampleSum").toString();
                if (!"".equals(sampleSum) && null != sampleSum ){
                    singleCount = Integer.parseInt(sampleSum);
                }else{
                    ResponseData responseData = geneListServerClient.selectSingleGeneByCwsd();
                    if (1 != responseData.getCode()){
                        logger.info("----------查询基因座数量失败-----------");
                    }else{
                        HashMap<String,String> map  = gson.fromJson(responseData.getResult().toString(), new TypeToken<HashMap<String,String>>(){}. getType());
                        System.out.println(map.get("singleGeneCount"));
                        singleCount = Integer.parseInt(map.get("singleGeneCount")) ;
                    }
                }
                logger.info("==================获取国家库样本总数量结束==================");*/

                logger.info("#####混合拆分比对定时任务开始 : ");
                //查询比对队列表中状态是未比对以及混合基因型的队列任务
                List<CompareQueue> compareQueueList = compareQueueService.selectCompareQueueAlloList();
                if (ListUtils.isNullOrEmptyList(compareQueueList)) {
                    logger.info("----------暂无比对任务-----------");
                } else {
                    for (CompareQueue compareQueue : compareQueueList) { //队列任务
                        logger.info("修改比对状态为比对中----------------------");
                        compareQueue.setStatus(Constants.MATCH_STATUS_04);
                        compareQueueService.updateStatus(compareQueue);
                        logger.info("修改比对状态为比对中结束----------------------");
                        int number = 0;
                        int singleCount = 0;
                        //计算当前比对数量  用于结算比对条数
                        boolean flag = true;
                        while (flag) {
                            //本次队列的贡献者基因信息
                            SplitedSampleGene splitedSampleGene = null;
//                            logger.info("==============查询队列信息================");
                            List<String> contributorGeneList = compareQueueService.contributorGene(compareQueue.getMixedSampleId());
                            //本次队列的混合表数据
//                            logger.info("==============本次队列的混合数据================");
                            mixContributorBean mixContributorBean = compareQueueService.getMixSampleInfo(compareQueue.getMixedSampleId());
                            if ("2".equals(compareQueue.getQueueType())){ //拆分
//                                logger.info("==============本次队列的拆分数据================");
                                splitedSampleGene = selectByCompareQueueId.selectByCompareQueueId(compareQueue.getId());
                            }

                            //查询redis单一样本基因信息
//                            logger.info("==============人员类型转换================");
                            SysDict sysDict = selectPersontype(compareQueue.getPersonType());//转换类型
                            if (sysDict != null && ListUtils.isNotNullAndEmptyList(sysDict.getDictValue2())){
                                //计算比对总数
                                logger.info("==============查询目标比对总数量================");
                                for (String personCodeType :sysDict.getDictValue2()){
                                    String keys = "*_"+personCodeType+"_*";
//                                    List<String> arrayList = redisUtils.keys(keys);
                                    int size = jsonRedisTemplate.keys(keys).size();
                                    singleCount += size;
                                }
                                for (String personCodeType :sysDict.getDictValue2()){
                                    String keys = "*_"+personCodeType+"_*";
                                    logger.info("==============从redis获取key值================");
                                    List<String> arrayList = redisUtils.keys(keys);
                                    if (!ListUtils.isNullOrEmptyList(arrayList)) {
                                        for (String key :arrayList){//遍历模糊符合规则的redisKey
                                            number++;
//                                            String geneInfo = redisUtils.get(key).toString();
                                            logger.info("==============通过key从redis获取基因信息================");
                                            String geneInfo = (String) jsonRedisTemplate.opsForValue().get(key);
                                            MixStrCompareResult mixStrCompareResult = null ;
                                            CompareParams compareParams = new CompareParams();
//                                            logger.info("==============获取匹配下限================");
                                            compareParams.setLowestSameLimit(Integer.parseInt(compareQueue.getMixsameCount()));//匹配下限
//                                            logger.info("==============获取容差================");
                                            if (StringUtils.isNotBlank(compareQueue.getCondition())){
                                                compareParams.setMostDiffLimit(Integer.parseInt(compareQueue.getCondition()));//容差上限
                                            }else {
                                                compareParams.setMostDiffLimit(0);//容差上限
                                            }
                                            //开始比对
                                            logger.info("==============开始比对================");
                                            if (mixContributorBean != null && StringUtils.isNotBlank(geneInfo) ){
                                                if ("2".equals(compareQueue.getQueueType())) { //拆分
                                                    logger.info("==============比对拆分================");
                                                    if (splitedSampleGene != null && null != splitedSampleGene.getGeneInfo() && !"".equals(splitedSampleGene.getGeneInfo())){
                                                        mixStrCompareResult = MixStrComparator.compareToSingleStr(splitedSampleGene.getGeneInfo(), Integer.parseInt(mixContributorBean.getContributorCount()), contributorGeneList,geneInfo, compareParams);
                                                    }else{
                                                        logger.info("----------拆分任务基因信息为空-----------");
                                                        flag = false;
                                                        continue;
                                                    }
                                                }else{
                                                    logger.info("==============比对混合================");
                                                    mixStrCompareResult = MixStrComparator.compareToSingleStr(gson.toJson(mixContributorBean.getGeneInfo()), Integer.parseInt(mixContributorBean.getContributorCount()), contributorGeneList, geneInfo, compareParams);
                                                }
                                            }
                                            //修改比对进度
                                            logger.info("修改比对进度开始----------------------");
                                            Double aDouble = new Double(number);
                                            Double bigDecimal1 = new Double(singleCount);
                                            double v = aDouble / bigDecimal1;
                                            int schedule = (int) Math.round(v * 100);
                                            compareQueue.setTargetCount(Integer.toString(schedule)+"%");
                                            compareQueue.setUpdateDatetime(new Date());
                                            compareQueueService.updateByPrimaryKey(compareQueue);
                                            logger.info("修改比对进度结束----------------------");
                                            if (mixStrCompareResult == null) {
                                                continue;
                                            }
                                            if (mixStrCompareResult.getMixStrCompareResultAlleleList().size() > 0 ) {//有比中
                                                logger.info("==============有比中信息================");
                                                logger.info("====================sampleId" + key.substring(key.lastIndexOf("_")+1) + "========================");
                                                ResponseData caseInfoSampleNo = geneListServerClient.getCaseInfoSampleNo(key.substring(key.lastIndexOf("_")+1),"",personCodeType);
                                                logger.info("==============获取比中信息================");
                                                newSampleInfo map  = gson.fromJson(gson.toJson(caseInfoSampleNo.getResult()), new TypeToken<newSampleInfo>(){}. getType());
                                                if (map != null){
                                                    map.setSampleGene(geneInfo);
                                                    logger.info("==============添加比中结果================");
                                                    insertMatchResultSing(mixStrCompareResult, map, compareQueue);
                                                }else {
                                                    logger.info("获取比中样本信息为空！");
                                                }
                                            }
                                        }
                                    }else{
                                        logger.info("----------未查到该分类redis数据-----------");
                                        flag = false;
                                    }
                                }
                                //修改比对状态
                                if (compareQueue.getStatus().equals(Constants.MATCH_STATUS_04)){
                                    logger.info("修改比对状态开始----------------------");
                                    compareQueue.setStatus(Constants.MATCH_STATUS_01);
                                    compareQueue.setUpdateDatetime(new Date());
                                    compareQueueService.updateStatus(compareQueue);
                                    logger.info("修改比对状态结束----------------------");
                                }
                                flag = false;
                            }
                        }
                    }
                }
            logger.info("#####混合拆分比对定时任务结束 : ");

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("#####混合拆分比对定时任务失败！" + e.getMessage());
        }
    }


    /**
     * 添加拆分比对单一结果信息
     */
    private void insertMatchResultSing(MixStrCompareResult mixStrCompareResult, newSampleInfo geneVo, CompareQueue compareQueue) {
        //查询比对结果是否存在
        MatchResult result = new MatchResult();
        result.setCompareId(compareQueue.getId());
        result.setSingleGeneId(geneVo.getId());
        if (compareQueue.getQueueType().equals("1")){//混合
            result.setSampleGeneResultType(Constants.RESULT_TYPE_01);
        }else if (compareQueue.getQueueType().equals("2")){
            result.setSampleGeneResultType(Constants.RESULT_TYPE_04);
        }
        MatchResult matchResult = null;
        try {
            matchResult = matchResulService.selectByMatch(result);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("通过队列和比中单一样本id查询结果失败！");
        }
        //如果存在就修改结果，不存在添加
        if (matchResult != null){//比中数:混合库样本的比中数量,
            matchResult.setMixedSampleGeneId(compareQueue.getMixedSampleId());
            matchResult.setCompareId(compareQueue.getId());
            matchResult.setSingleGeneId(geneVo.getId());
            matchResult.setProportionSiteNum(mixStrCompareResult.getProportionCount()+"");//比中样本位点个数
            matchResult.setRatio(mixStrCompareResult.getMatchedLocusCount());//比中数
            matchResult.setSplitDigit(mixStrCompareResult.getDiffLocusCount());//差异数
            matchResult.setProportionCaseNo(geneVo.getCaseNo());//案件编号
            matchResult.setProportionCaseName(geneVo.getCaseName());//案件名称
            matchResult.setProportionSampleNo(geneVo.getSampleLabNo());//检材编号
            matchResult.setProportionSampleName(geneVo.getSampleName());//检材名称
            matchResult.setGeneInfo(geneVo.getSampleGene());//基因信息
            matchResult.setGenePicture(null);//基因图片
            matchResult.setProportionPersonName(geneVo.getPersonName());//人员姓名
            matchResult.setProportionPersonnel(geneVo.getPersonTypeName());//人员类型
            matchResult.setProportionPersonCode(geneVo.getPersonTypeCode());//人员类型编号
            matchResult.setIdCardNo(geneVo.getIdCardNo());//身份证号
            matchResult.setProportionDistrict(geneVo.getLabServerName());//地区
            matchResult.setProportionKilName(geneVo.getKilName());//试剂盒
            matchResult.setComparisonTime(new Date());//比中时间
            matchResult.setCreatePerson(compareQueue.getCreatePerson());//创建人
            if (compareQueue.getQueueType().equals("1")){//混合
                matchResult.setSampleGeneResultType(Constants.RESULT_TYPE_01);
            }else if (compareQueue.getQueueType().equals("2")){
                matchResult.setSampleGeneResultType(Constants.RESULT_TYPE_04);
            }
//            matchResult.setEffectFlag("1");
            try {
                matchResulService.updateByPrimaryKey(matchResult);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("修改比中结果失败！");
            }
        }else{
            MatchResult match = new MatchResult();
            match.setId(UUID.randomUUID().toString());
            match.setMixedSampleGeneId(compareQueue.getMixedSampleId());
            match.setCompareId(compareQueue.getId());
            match.setSingleGeneId(geneVo.getId());
            match.setProportionSiteNum(mixStrCompareResult.getProportionCount()+"");//比中样本位点个数
            match.setRatio(mixStrCompareResult.getMatchedLocusCount());//比中数
            match.setSplitDigit(mixStrCompareResult.getDiffLocusCount());//差异数
            match.setProportionCaseNo(geneVo.getCaseNo());//案件编号
            match.setProportionCaseName(geneVo.getCaseName());//案件名称
            match.setProportionSampleNo(geneVo.getSampleLabNo());//检材编号
            match.setProportionSampleName(geneVo.getSampleName());//检材名称
            match.setGeneInfo(geneVo.getSampleGene());//基因信息
            match.setGenePicture(null);//基因图片
            logger.info("=============添加人员姓名====================");
            match.setProportionPersonName(geneVo.getPersonName());//人员姓名
            logger.info("=============添加人员类型====================");
            match.setProportionPersonnel(geneVo.getPersonTypeName());//人员类型
            match.setProportionPersonCode(geneVo.getPersonTypeCode());//人员类型编号
            logger.info("=============添加身份证号====================");
            match.setIdCardNo(geneVo.getIdCardNo());//身份证号
            logger.info("=============添加地区====================");
            match.setProportionDistrict(geneVo.getLabServerName());//地区
            logger.info("=============添加身份证号====================");
            match.setProportionKilName(geneVo.getKilName());//试剂盒
            match.setComparisonTime(new Date());//比中时间
            match.setCreatePerson(compareQueue.getCreatePerson());//创建人
            if (compareQueue.getQueueType().equals("1")){//混合
                match.setSampleGeneResultType(Constants.RESULT_TYPE_01);
            }else if (compareQueue.getQueueType().equals("2")){
                match.setSampleGeneResultType(Constants.RESULT_TYPE_04);
            }
//            matchResult.setEffectFlag("1");
            try {
                matchResulService.insert(match);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("添加比中结果失败！");
            }
            //添加比中消息
            logger.info("========添加拆分比对单一比中消息.============");
            MobileNews mobileNews = new MobileNews();
            mobileNews.setId(UUID.randomUUID().toString());
            mobileNews.setTitle("比对消息提示");//标题
            String username = "";
            try {
                logger.info("========查询登录人信息============");
                ResponseData sysUserData = sysUserClient.selectByPrimaryKey(compareQueue.getCreatePerson());
                if (sysUserData != null){
                		JSONObject json = JSON.parseObject(sysUserData.getResult().toString());
                    username = json.getString("realName");
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("查询登录人信息失败！");
            }
            mobileNews.setContent(username + "提交比对的混合分型比中 ["+geneVo.getPersonTypeName()
                    + geneVo.getPersonName() +"编号" + geneVo.getSampleLabNo() + "]");//内容
            mobileNews.setState(0);//状态  未读
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            mobileNews.setCreateDatetime(sdf.format(new Date()));
            mobileNews.setType(11);
            mobileNews.setUserid(compareQueue.getCreatePerson());
          //  mobileNews.setCaseId(compareQueue.getId());//案件id
//          mobileNews.setUserOrg(caseInfo.getOrgId());
            mobileNews.setMessageType(3);//消息类型
            mobileNews.setCompareId(compareQueue.getId());//队列id
            mobileNews.setMobileFlag(Constants.MOBILE_FLAG_0);//是否忽略
            try {
                mobileNewsMapper.insert(mobileNews);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("添加消息失败！");
            }
            System.out.println("===========添加拆分比对单一比中消息成功!============");
            //添加可能贡献者
            /*if (ListUtils.isNotNullAndEmptyList(mixStrCompareResult.getContributorGenePossibilityList())
                && mixStrCompareResult.getContributorGenePossibilityList().size() > 0){
                    possibilityInfo bean = new possibilityInfo();
                    bean.setId(UUID.randomUUID().toString());
                    bean.setCompareId(compareQueue.getId());
                    bean.setContributorPossibilityGene(gson.toJson(mixStrCompareResult.getDnaGeneInfoDetailsList()));
                    matchResulService.insertPossibilityGene(bean);
            }*/
        }
    }

    public SysDict selectPersonCategory(String personType) {
        SysDict sysDict = new SysDict();
        if (null != personType && !"".equals(personType)){
            if (personType.equals("0") ) {
                sysDict.setDictValue2(Arrays.asList(Constants.PERSON_TYPE_1_DICT.split(",")));
                sysDict.setDictValue1(Constants.PERSON_TYPE_1);
                sysDict.setDictKey(Constants.PERSON_TYPE_1_CODE);
            }else
            if (personType.equals("1") || personType.equals("01") ) {
                sysDict.setDictValue2(Arrays.asList(Constants.PERSON_TYPE_2_DICT.split(",")));
                sysDict.setDictValue1(Constants.PERSON_TYPE_2);
                sysDict.setDictKey(Constants.PERSON_TYPE_2_CODE);
            }else
            if (personType.equals("2") || personType.equals("02") ) {
                sysDict.setDictValue2(Arrays.asList(Constants.PERSON_TYPE_3_DICT.split(",")));
                sysDict.setDictValue1(Constants.PERSON_TYPE_3);
                sysDict.setDictKey(Constants.PERSON_TYPE_3_CODE);
            }else
            if (personType.equals("3") || personType.equals("03") ) {
                sysDict.setDictValue2(Arrays.asList(Constants.PERSON_TYPE_4_DICT.split(",")));
                sysDict.setDictValue1(Constants.PERSON_TYPE_4);
                sysDict.setDictKey(Constants.PERSON_TYPE_4_CODE);
            }else
            if (personType.equals("4") || personType.equals("04") ) {
                sysDict.setDictValue2(Arrays.asList(Constants.PERSON_TYPE_5_DICT.split(",")));
                sysDict.setDictValue1(Constants.PERSON_TYPE_5);
                sysDict.setDictKey(Constants.PERSON_TYPE_5_CODE);
            }else
            if (personType.equals("5") || personType.equals("05") ) {
                sysDict.setDictValue2(Arrays.asList(Constants.PERSON_TYPE_6_DICT.split(",")));
                sysDict.setDictValue1(Constants.PERSON_TYPE_6);
                sysDict.setDictKey(Constants.PERSON_TYPE_6_CODE);
            }else
            if (personType.equals("6") || personType.equals("06") ) {
                sysDict.setDictValue2(Arrays.asList(Constants.PERSON_TYPE_7_DICT.split(",")));
                sysDict.setDictValue1(Constants.PERSON_TYPE_7);
                sysDict.setDictKey(Constants.PERSON_TYPE_7_CODE);
            }
        }
        return sysDict;
    }

    @SuppressWarnings("unchecked")
	public SysDict selectPersontype(String personType) {
        SysDict sysDict = new SysDict();
        List<String> list = JSON.parseObject(personType, List.class);
        sysDict.setDictValue2(list);
        return sysDict;
    }

}


