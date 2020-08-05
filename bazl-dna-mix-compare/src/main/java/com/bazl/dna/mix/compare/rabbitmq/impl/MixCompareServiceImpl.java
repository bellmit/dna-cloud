package com.bazl.dna.mix.compare.rabbitmq.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.bazl.dna.common.PublicConstants;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.common.filter.RedisConfig;
import com.bazl.dna.mix.compare.algorithm.comparator.MixStrComparator;
import com.bazl.dna.mix.compare.algorithm.params.CompareParams;
import com.bazl.dna.mix.compare.algorithm.result.MixStrCompareResult;
import com.bazl.dna.mix.compare.client.CompareQueueClient;
import com.bazl.dna.mix.compare.client.GeneListServerClient;
import com.bazl.dna.mix.compare.client.ISysUserClient;
import com.bazl.dna.mix.compare.constants.Constants;
import com.bazl.dna.mix.compare.rabbitmq.IMixCompareService;
import com.bazl.dna.mix.compare.service.entity.CompareQueue;
import com.bazl.dna.mix.compare.service.entity.MatchResult;
import com.bazl.dna.mix.compare.service.entity.MobileNews;
import com.bazl.dna.mix.compare.service.entity.SplitedSampleGene;
import com.bazl.dna.mix.compare.service.entity.SysDict;
import com.bazl.dna.mix.compare.service.entity.mixContributorBean;
import com.bazl.dna.mix.compare.service.entity.newSampleInfo;
import com.bazl.dna.mix.compare.utils.ListUtils;
import com.bazl.dna.util.GeneTransFormUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import redis.clients.jedis.Jedis;

@Service
public class MixCompareServiceImpl implements IMixCompareService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private GeneListServerClient geneListServerClient;

    @Autowired
    private CompareQueueClient compareQueueClient;

    @Autowired
    private ISysUserClient sysUserClient;

    Gson gson = new Gson();

    @Override
    public void compare(String json){
        try {
            logger.info("MQ请求的Json:"+json);
            CompareQueue compareQueue = JSON.parseObject(json, CompareQueue.class);
            SysDict sysDict = selectPersontype(compareQueue.getPersonType());//转换类型
            int dictValueList = 0;//目标总数
            int singleCount = 0;
            String[] split = compareQueue.getDataType().split(",");
            for (String datatype :split){
                logger.info("换库查询数量：" + datatype + "库");
    //            redisConfig.dynamicRedisConfig(datatype);//切redis库
                LettuceConnectionFactory connectionFactory = RedisConfig.dataSourceMap.get(datatype);
                Jedis jedis = new Jedis(connectionFactory.getHostName(), connectionFactory.getPort(),100000000);
                String auth = jedis.auth(connectionFactory.getPassword());
                if (auth.equals("OK") && jedis.isConnected()) {
                    jedis.select(connectionFactory.getDatabase());

                    //计算比对总数
                    for (String personCodeType :sysDict.getDictValue2()){
                        String keys = "*-"+personCodeType+"_*";
    //                    int size = redisUtils.keys(keys).size();
                        int size = jedis.keys(keys).size();
                        dictValueList += size;
                    }
                }
                jedis.close();

            }

            logger.debug("修改比对状态为比对中----------------------");
            compareQueue.setStatus(Constants.MATCH_STATUS_04);
            compareQueueClient.updateStatus(compareQueue.getId(),compareQueue.getStatus(),compareQueue.getTargetCount());
            logger.debug("修改比对状态为比对中结束----------------------");
            //计算当前比对数量  用于结算比对条数
            boolean flag = true;
            while (flag) {
                //本次队列的贡献者基因信息
                SplitedSampleGene splitedSampleGene = null;
                // logger.debug("==============查询队列信息================");
                ResponseData responseData = compareQueueClient.contributorGene(compareQueue.getMixedSampleId());
                List<String> contributorGeneList  = gson.fromJson(gson.toJson(responseData.getResult()), new TypeToken<List<String>>(){}. getType());
                //本次队列的混合表数据
                // logger.debug("==============本次队列的混合数据================");
                ResponseData mixSampleInfo = compareQueueClient.getMixSampleInfo(compareQueue.getMixedSampleId());
                mixContributorBean mixContributorBean  = gson.fromJson(gson.toJson(mixSampleInfo.getResult()), new TypeToken<mixContributorBean>(){}. getType());
                if ("2".equals(compareQueue.getQueueType())){ //拆分
                    // logger.debug("==============本次队列的拆分数据================");
                    ResponseData responseData1 = compareQueueClient.selectByCompareQueueId(compareQueue.getId());
                    splitedSampleGene  = gson.fromJson(gson.toJson(responseData1.getResult()), new TypeToken<SplitedSampleGene>(){}. getType());
                }
                //查询redis单一样本基因信息
                if (sysDict != null && ListUtils.isNotNullAndEmptyList(sysDict.getDictValue2())){
    //                    logger.info("库名"+datatype+"比对总数量:"+singleCount);
                    CompareParams compareParams = new CompareParams() ;
                    compareParams.setLowestSameLimit(Integer.parseInt(compareQueue.getMixsameCount()));//匹配下限
                    if (StringUtils.isNotBlank(compareQueue.getCondition())){
                        compareParams.setMostDiffLimit(Integer.parseInt(compareQueue.getCondition()));//容差上限
                    }else {
                        compareParams.setMostDiffLimit(0);//容差上限
                    }

                    int schedule = (int) Math.round(dictValueList / 100);
                    int percent = 0;
                    for (String personCodeType :sysDict.getDictValue2()){
                        for (String datatype :split){
                            LettuceConnectionFactory connectionFactory = RedisConfig.dataSourceMap.get(datatype);
                            Jedis jedis = new Jedis(connectionFactory.getHostName(), connectionFactory.getPort(),100000000);
                            String auth = jedis.auth(connectionFactory.getPassword());
                            String keys = "*-"+personCodeType+"_*";
                            if (auth.equals("OK") && jedis.isConnected()) {
                                jedis.select(connectionFactory.getDatabase());
                                Set<String> arrayList = jedis.keys(keys);

                                if (arrayList != null && !arrayList.isEmpty()) {
                                    for (String key :arrayList){//遍历模糊符合规则的redisKey
                                        //修改比对进度
                                        singleCount++;
                                        if (schedule !=0 && singleCount % schedule == 0 && percent <= 100) {
                                            logger.info("keys:{} size:{}, schedule:{}, {}%", keys, arrayList.size(), singleCount, percent);
                                            compareQueue.setTargetCount(percent+"%");
                                            compareQueue.setUpdateDatetime(new Date());
                                            compareQueueClient.updateByPrimaryKey(compareQueue.getId(), percent + "%");
                                            percent++;
                                        }else {
                                            Double aDouble = new Double(singleCount);
                                            Double bigDecimal1 = new Double(dictValueList);
                                            double v = aDouble / bigDecimal1;
                                            int tar = (int) Math.round(v * 100);
                                            compareQueue.setTargetCount(tar+"%");
                                            compareQueue.setUpdateDatetime(new Date());
                                            compareQueueClient.updateByPrimaryKey(compareQueue.getId(), tar + "%");
                                        }

                                        String geneInfo = jedis.get(key);

                                        MixStrCompareResult mixStrCompareResult = null ;
                                        //开始比对
                                        if (mixContributorBean != null && StringUtils.isNotBlank(geneInfo) ){
                                            if ("2".equals(compareQueue.getQueueType())) { //拆分
                                                if (splitedSampleGene != null && null != splitedSampleGene.getGeneInfo() && !"".equals(splitedSampleGene.getGeneInfo())){
                                                    mixStrCompareResult = MixStrComparator.compareToSingleStr(splitedSampleGene.getGeneInfo(), Integer.parseInt(mixContributorBean.getContributorCount()),
                                                            contributorGeneList,geneInfo, compareParams);
                                                }else{
                                                    flag = false;
                                                    continue;
                                                }
                                            }else{
                                                mixStrCompareResult = MixStrComparator.compareToSingleStr(gson.toJson(mixContributorBean.getGeneInfo()), Integer.parseInt(mixContributorBean.getContributorCount()), contributorGeneList, geneInfo, compareParams);
                                            }
                                        }
                                        if (mixStrCompareResult == null) {
                                            continue;
                                        }
                                        if (mixStrCompareResult.getMixStrCompareResultAlleleList().size() > 0 ) {//有比中
                                            logger.info("有比中信息====================sampleId" + key.substring(key.lastIndexOf("-")+1) + "========================");
                                            //切换数据源
    //                                    ContextHolder.setDataSource(datatype);
                                            String substring = key.substring(key.lastIndexOf("-") + 1);
                                            ResponseData caseInfoSampleNo = geneListServerClient.getCaseInfoSampleNo(substring,datatype,personCodeType);
                                            newSampleInfo map  = gson.fromJson(gson.toJson(caseInfoSampleNo.getResult()), new TypeToken<newSampleInfo>(){}. getType());
                                            if (map != null){
                                                map.setSampleGene(GeneTransFormUtils.geneFormatString2(geneInfo));
                                                insertMatchResultSing(mixStrCompareResult, map, compareQueue, datatype);
                                            }else {
                                                MatchResult match = new MatchResult();
                                                match.setId(UUID.randomUUID().toString());
                                                match.setMixedSampleGeneId(compareQueue.getMixedSampleId());
                                                match.setSingleGeneId(substring);
                                                match.setCompareId(compareQueue.getId());
                                                match.setProportionSiteNum(mixStrCompareResult.getProportionCount()+"");//比中样本位点个数
                                                match.setRatio(mixStrCompareResult.getMatchedLocusCount());//比中数
                                                match.setSplitDigit(mixStrCompareResult.getDiffLocusCount());//差异数
                                                match.setGeneInfo(GeneTransFormUtils.geneFormatString2(geneInfo));//基因信息
                                                match.setComparisonTime(new Date());//比中时间
                                                match.setCreatePerson(compareQueue.getCreatePerson());//创建人
                                                match.setProportionDistrictCode(datatype);//匹配服务地区
                                                match.setProportionPersonCode(personCodeType);//比中人员类型
                                                if (compareQueue.getQueueType().equals("1")){//混合
                                                    match.setSampleGeneResultType(Constants.RESULT_TYPE_01);
                                                }else if (compareQueue.getQueueType().equals("2")){
                                                    match.setSampleGeneResultType(Constants.RESULT_TYPE_04);
                                                }
                                                try {
                                                    compareQueueClient.insert(JSON.toJSONString(match));
                                                }catch (Exception e){
                                                    logger.error(e.getMessage());
                                                }

                                                logger.debug("获取比中样本信息为空！");
                                            }
                                        }


                                    }

                                }else{
                                    logger.debug("----------未查到该分类redis数据-----------");
                                    flag = false;
                                }
                                jedis.close();
                            }
                        }
                    }
                    //修改比对状态
                    if (compareQueue.getStatus().equals(Constants.MATCH_STATUS_04)){
                        logger.debug("修改比对状态开始----------------------");
                        compareQueue.setStatus(Constants.MATCH_STATUS_01);
                        compareQueue.setUpdateDatetime(new Date());
                        compareQueue.setTargetCount("100%");
                        compareQueueClient.updateStatus(compareQueue.getId(),compareQueue.getStatus(),compareQueue.getTargetCount());
                        logger.debug("修改比对状态结束----------------------");
                    }
                    flag = false;
                }
            }
            logger.info("------------比对结束！-------------");
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            logger.error("————————————————————————————————比对失败！" + e.getMessage());
        }
    }



    /**
     * 添加拆分比对单一结果信息
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private void insertMatchResultSing(MixStrCompareResult mixStrCompareResult, newSampleInfo geneVo,
                                       CompareQueue compareQueue, String datatype) {
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
        ResponseData responseData = compareQueueClient.selectByMatch(result.getCompareId(),result.getSingleGeneId(),result.getSampleGeneResultType());
        if(responseData.getCode() == 1){
           matchResult  = gson.fromJson(gson.toJson(responseData.getResult()), new TypeToken<MatchResult>(){}. getType());
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
            matchResult.setProportionDistrictCode(datatype);//匹配服务地区
            matchResult.setProportionKilName(geneVo.getKilName());//试剂盒
            matchResult.setComparisonTime(new Date());//比中时间
            matchResult.setCreatePerson(compareQueue.getCreatePerson());//创建人
            if (compareQueue.getQueueType().equals("1")){//混合
                matchResult.setSampleGeneResultType(Constants.RESULT_TYPE_01);
            }else if (compareQueue.getQueueType().equals("2")){
                matchResult.setSampleGeneResultType(Constants.RESULT_TYPE_04);
            }
            compareQueueClient.updateMatchResult(JSON.toJSONString(matchResult));

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
            match.setProportionPersonName(geneVo.getPersonName());//人员姓名
            match.setProportionPersonnel(geneVo.getPersonTypeName());//人员类型
            match.setProportionPersonCode(geneVo.getPersonTypeCode());//人员类型编号
            match.setIdCardNo(geneVo.getIdCardNo());//身份证号
            match.setProportionDistrict(geneVo.getLabServerName());//地区
            match.setProportionDistrictCode(datatype);//匹配服务地区
            match.setProportionKilName(geneVo.getKilName());//试剂盒
            match.setComparisonTime(new Date());//比中时间
            match.setCreatePerson(compareQueue.getCreatePerson());//创建人
            if (compareQueue.getQueueType().equals("1")){//混合
                match.setSampleGeneResultType(Constants.RESULT_TYPE_01);
            }else if (compareQueue.getQueueType().equals("2")){
                match.setSampleGeneResultType(Constants.RESULT_TYPE_04);
            }
            try {
                compareQueueClient.insert(JSON.toJSONString(match));
            }catch (Exception e){
                logger.error("insert error:", e);
            }
            //添加比中消息
            MobileNews mobileNews = new MobileNews();
            mobileNews.setId(UUID.randomUUID().toString());
            mobileNews.setTitle("比对消息提示");//标题
            String username = "";
            ResponseData sysUserData = sysUserClient.selectByPrimaryKey(compareQueue.getCreatePerson());
			if (sysUserData.getCode() == PublicConstants.SUCCESS_CODE) {
				logger.info("sysUserData: {}", sysUserData.getResult());
				LinkedHashMap<String, Object> map = (LinkedHashMap)sysUserData.getResult();
                username = map.get("realName").toString();
            }
            mobileNews.setContent(username + "提交比对的混合分型比中 ["+geneVo.getPersonTypeName() + geneVo.getPersonName() +"编号" + geneVo.getSampleLabNo() + "]");//内容
            mobileNews.setState(0);//状态  未读
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            mobileNews.setCreateDatetime(sdf.format(new Date()));
            mobileNews.setType(11);
            mobileNews.setUserid(compareQueue.getCreatePerson());
            mobileNews.setMessageType(3);//消息类型
            mobileNews.setCompareId(compareQueue.getId());//队列id
            mobileNews.setMobileFlag(Constants.MOBILE_FLAG_0);//是否忽略
            try {
                compareQueueClient.insertMobile(JSON.toJSONString(mobileNews));
            }catch (Exception e){
                logger.error(e.getMessage());
            }
            System.out.println("===========添加拆分比对单一比中消息成功!============");
        }
    }


    @SuppressWarnings("unchecked")
	public SysDict selectPersontype(String personType) {
        SysDict sysDict = new SysDict();
        List<String> typeList = new ArrayList<>();
        List<String> list = JSON.parseObject(personType, List.class);
        sysDict.setDictValue2(typeList);
        sysDict.setDictValue2(list);
        return sysDict;
    }


}
