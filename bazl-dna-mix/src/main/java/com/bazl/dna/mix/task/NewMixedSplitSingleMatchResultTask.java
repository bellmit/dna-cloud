package com.bazl.dna.mix.task;

import com.bazl.dna.mix.controller.base.BaseController;

/**
 * Created by Administrator on 2019/7/26.
 */
//@Component注解用于对那些比较中立的类进行注释；
//@Component
public class NewMixedSplitSingleMatchResultTask extends BaseController {
//
//    private final Logger logger = LoggerFactory.getLogger(getClass());
//
//    @Value("${minSameMixCount}")
//    private int minSameMixCount;
//
//    @Autowired
//    CompareQueueService compareQueueService;
//    @Autowired
//    MatchResulService matchResulService;
//    @Autowired
//    GeneCompareUtil geneCompareUtil;
//    @Value("${singleGeneUrl}")
//    private String singleGeneUrl;
//    @Value("${mixGeneUrl}")
//    private String mixGeneUrl;
//    //基因信息转换条件
//    @Value("${Gen}")
//    private int gen;
//    @Autowired
//    MobileNewsMapper mobileNewsMapper;
//    @Autowired
//    GeneformatUtils geneformatUtils;
//    @Value("${locusNameUrl}")
//    private String locusNameUrl;
//    @Value("${singleGeneCountUrl}")
//    private String singleGeneCountUrl;
//    @Value("${mixGeneCountUrl}")
//    private String mixGeneCountUrl;
//
//    /*
//     *    单一比对拆分基因
//     * */
//    //    @Async
//    @Scheduled(fixedDelay = 86400000)  //间隔一天
//    public void first1() throws InterruptedException {
//       /*int mixSameCount = minSameMixCount;
//        //判断单一基因是否包含此基因
//        if (mixSameCount == 0) {
//            mixSameCount = minSameMixCount;
//        }*/
//        //获取国家库单一样本总数量json串
//        String singleGeneCoun = HttpClientUtil.doGet(singleGeneCountUrl, null);
//        //转化为对象
//        JSONObject json = JSON.parseObject(singleGeneCoun);
//        //获取有数据的data
//        String count = json.getString("data");
//        JSONObject strCount = JSON.parseObject(count);
//        //判断是否包含
//        int singleCount = 0;
//        if (strCount.containsKey("singleGeneCount")) {
//            singleCount = (int) strCount.get("singleGeneCount");
//        }
//        try {
//            boolean isFinish = true;
//            look:
//            //使用标记语句look
//            while (isFinish) {
//                logger.info("#####单一拆分比对定时任务开始 : " + LocalDateTime.now().toLocalTime());
//                //查询比对队列表中状态是未比对以及混合基因型的队列任务
//                List<CompareQueue> compareQueueList = compareQueueService.selectCompareQueueList(Constants.QUEUE_TYPE_02);
//                if (ListUtils.isNullOrEmptyList(compareQueueList)) {
//                    isFinish = false;
//                    break;
//                } else {
//                    System.out.println("===========查询基因信息==========");
//                    List< Map<String,String>> listLocusName = new ArrayList<Map<String,String>>();
//                    if (gen == 1){
//                        logger.info("查询基因座信息开始----------------------");
//                        String locusInfo = HttpClientUtil.doGet(locusNameUrl,null);
//                        JSONObject resultlocusInfoObject = JSON.parseObject(locusInfo);
//                        String dataResultlocusInfoStr = resultlocusInfoObject.getString("data");
//                        List<HashMap> llocus = JSON.parseArray(dataResultlocusInfoStr, HashMap.class);
//                        for (Object object : llocus ) {
//                            listLocusName.add((Map<String, String>) object);
//                        }
//                        logger.info("查询基因座信息结束----------------------");
//                    }
//                    for (CompareQueue compareQueue : compareQueueList) {
//                        int num = 0;
//                        int number = 0;
//                        boolean flag = true;
//                        while (flag) {
//                            //查询单一样本基因信息
//                            Map<String, String> params = new HashMap<>();
//                            params.put("page", num + "");
//                            String sendRequest = HttpClientUtil.doGet(singleGeneUrl,params);
//                            num++;
//                            JSONObject jsonObject = JSONObject.parseObject(sendRequest);
//                            String code = jsonObject.getString("code");
//                            if (code.equals("0")){
//                                flag = false;
//                                break;//直接跳出while语句
//                            }
//                            String data = jsonObject.getString("data");
//                            JSONObject str = JSON.parseObject(data);
//                            List<SampleDnaGeneVo> SampleDnaGeneList = new ArrayList<>();
//                            if (str.containsKey("singleGeneVosList")) {
//                                try {
//                                    JSONObject str2 = JSONObject.parseObject(str.toString());
//                                    List<SampleDnaGeneVo> list2 = JSON.parseArray(JSON.parseObject(String.valueOf(str2)).getString("singleGeneVosList"), SampleDnaGeneVo.class);
//                                    if (list2.size() > 0) {
//                                        for (int j = 0; j < list2.size(); j++) {
//                                            SampleDnaGeneVo sampleDnaGeneVo = list2.get(j);
//                                            SampleDnaGeneList.add(sampleDnaGeneVo);
//                                        }
//                                    }
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                    logger.error("单一基因信息格式转化失败！！！");
//                                } finally {
//                                }
//                            }
//                            if (ListUtils.isNullOrEmptyList(SampleDnaGeneList)) {
//                                flag = false;
//                                break look;//直接跳出while语句
//                            } else {
//                                number += SampleDnaGeneList.size();
//                                //循环比对
//                                for (SampleDnaGeneVo geneVo : SampleDnaGeneList) {
//                                    if (StringUtils.isNotBlank(geneVo.getPersonSex()) && StringUtils.isNotBlank(geneVo.getPersonType()) && StringUtils.isNotBlank(geneVo.getRegionalismCode()) && StringUtils.isNotBlank(geneVo.getParentCode())){
//                                        if (compareQueue.getSex().contains(geneVo.getPersonSex()) && compareQueue.getPersonType().contains(geneVo.getPersonType())
//                                                && compareQueue.getDistrict().contains(geneVo.getRegionalismCode()) || compareQueue.getDistrict().contains(geneVo.getParentCode())) {
//                                            String gene = null;
//                                            if (gen == 1){
//                                                logger.info("基因信息转换开始----------------------");
//                                                gene = geneformatUtils.geneFormatConversionNew(geneVo.getEntity().getGeneInfo(),listLocusName);
//                                                geneVo.getEntity().setGeneInfo(gene);
//                                                logger.info("基因信息转换结束----------------------");
//                                            }else {
//                                                gene = geneVo.getEntity().getGeneInfo();
//                                            }
//                                            //获取混合基因信息比对单一基因表中的基因信息
//                                            Map<String, Object> resultMap = geneCompareUtil.compareGeneInfo(gene,compareQueue.getGeneInfo(),Integer.parseInt(compareQueue.getMixsameCount()));
//                                            if (resultMap == null) {
//                                                continue;
//                                            }
//                                            if ((Boolean) resultMap.get("matched")) {
//                                                insertMatchResultSing(resultMap, geneVo, compareQueue);
//                                            }
//                                        }
//                                    }
//
//                                }
//                                logger.info("修改比对进度开始----------------------");
//                                // TODO 比对进度计算
//                                Double aDouble = new Double(number);
//                                Double bigDecimal1 = new Double(singleCount);
//                                double v = aDouble / bigDecimal1;
//                                int schedule = (int) Math.round(v * 100);
//                                compareQueue.setTargetCount(Integer.toString(schedule)+"%");
//                                compareQueueService.updateByPrimaryKey(compareQueue);
//                                logger.info("修改比对进度结束----------------------");
//                            }
//                        }
//                        //修改比对状态
//                        if (compareQueue.getStatus().equals(Constants.MATCH_STATUS_02)){
//                            logger.info("修改比对状态开始----------------------");
//                            compareQueue.setStatus(Constants.MATCH_STATUS_01);
//                            compareQueueService.updateStatus(compareQueue);
//                            logger.info("修改比对状态结束----------------------");
//                        }
//                    }
//                }
//            }
//            logger.info("#####单一拆分比对定时任务结束 : " + LocalDateTime.now().toLocalTime());
//
//        } catch (Exception e) {
//            logger.error("#####单一拆分比对定时任务失败！" + e);
//        }
//    }
//
//    /*
//     *    混合比对拆分基因
//     * */
//    //    @Async
//    @Scheduled(fixedDelay = 8640000)  //间隔一谈
//    public void first2() throws InterruptedException {
//        //获取国家库单一样本总数量json串
//        String singleGeneCoun = HttpClientUtil.doGet(mixGeneCountUrl, null);
//        //转化为对象
//        JSONObject json = JSON.parseObject(singleGeneCoun);
//        //获取有数据的data
//        String count = json.getString("data");
//        JSONObject strCount = JSON.parseObject(count);
//        //判断是否包含
//        int singleCount = 0;
//        if (strCount.containsKey("mixGeneCount")) {
//            singleCount = (int) strCount.get("mixGeneCount");
//        }
//        try {
//            boolean isFinish = true;
//            look:
//            //使用标记语句look
//            while (isFinish) {
//                logger.info("#####混合拆分比对定时任务开始 : " + LocalDateTime.now().toLocalTime());
//                //查询比对队列表中状态是未比对以及混合基因型的队列任务
//                List<CompareQueue> compareQueueList = compareQueueService.selectCompareQueueList(Constants.QUEUE_TYPE_02);
//                if (ListUtils.isNullOrEmptyList(compareQueueList)) {
//                    isFinish = false;
//                    break;
//                } else {
//                    System.out.println("===========查询基因信息==========");
//                    List< Map<String,String>> listLocusName = new ArrayList<Map<String,String>>();
//                    if (gen == 1){
//                        logger.info("查询基因座信息开始----------------------");
//                        String locusInfo = HttpClientUtil.doGet(locusNameUrl,null);
//                        JSONObject resultlocusInfoObject = JSON.parseObject(locusInfo);
//                        String dataResultlocusInfoStr = resultlocusInfoObject.getString("data");
//                        List<HashMap> llocus = JSON.parseArray(dataResultlocusInfoStr, HashMap.class);
//                        for (Object object : llocus ) {
//                            listLocusName.add((Map<String, String>) object);
//                        }
//                        logger.info("查询基因座信息结束----------------------");
//                    }
//                    for (CompareQueue compareQueue : compareQueueList) {
//                        int num = 0;
//                        //计算当前比对数量  用于结算比对条数
//                        int number = 0;
//                        boolean flag = true;
//                        while (flag) {
//                            //查询单一样本基因信息
//                            Map<String, String> params = new HashMap<>();
//                            params.put("page", num + "");
//                            String sendRequest = HttpClientUtil.doGet(mixGeneUrl,params);
//                            num++;
//                            JSONObject jsonObject = JSONObject.parseObject(sendRequest);
//                            String code = jsonObject.getString("code");
//                            if (code.equals("0")){
//                                flag = false;
//                                break;//直接跳出while语句
//                            }
//                            String data = jsonObject.getString("data");
//                            JSONObject str = JSON.parseObject(data);
//                            List<SampleDnaGeneVo> SampleDnaGeneList = new ArrayList<>();
//                            if (str.containsKey("mixGeneVosList")) {
//                                try {
//                                    JSONObject str2 = JSONObject.parseObject(str.toString());
//                                    List<SampleDnaGeneVo> list2 = JSON.parseArray(JSON.parseObject(String.valueOf(str2)).getString("mixGeneVosList"), SampleDnaGeneVo.class);
//                                    if (list2.size() > 0) {
//                                        for (int j = 0; j < list2.size(); j++) {
//                                            SampleDnaGeneVo sampleDnaGeneVo = list2.get(j);
//                                            SampleDnaGeneList.add(sampleDnaGeneVo);
//                                        }
//                                    }
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                    logger.error("混合基因信息格式转化失败！！！");
//                                } finally {
//                                }
//                            }
//                            if (ListUtils.isNullOrEmptyList(SampleDnaGeneList)) {
//                                flag = false;
//                                break;//直接跳出while语句
//                            } else {
//                                number += SampleDnaGeneList.size();
//                                //循环比对
//                                for (SampleDnaGeneVo geneVo : SampleDnaGeneList) {
//                                    if (null != geneVo.getPersonSex() && null != geneVo.getPersonCode()
//                                            && null != geneVo.getRegionalismCode() && null != geneVo.getParentCode()){
//                                        if (compareQueue.getSex().contains(geneVo.getPersonSex()) && compareQueue.getPersonType().contains(geneVo.getPersonType())
//                                                && compareQueue.getDistrict().contains(geneVo.getRegionalismCode()) || compareQueue.getDistrict().contains(geneVo.getParentCode())) {
//                                            String gene = null;
//                                            if (gen == 1){
//                                                logger.info("基因信息转换开始----------------------");
//                                                gene = geneformatUtils.geneFormatConversionNew(geneVo.getEntity().getGeneInfo(),listLocusName);
//                                                geneVo.getEntity().setGeneInfo(gene);
//                                                logger.info("基因信息转换结束----------------------");
//                                            }else {
//                                                gene = geneVo.getEntity().getGeneInfo();
//                                            }
//                                            //获取混合基因信息比对单一基因表中的基因信息
//                                            Map<String, Object> resultMap = geneCompareUtil.compareGeneInfo(gene, compareQueue.getGeneInfo(), Integer.parseInt(compareQueue.getMixsameCount()));
//                                            if (resultMap == null) {
//                                                continue;
//                                            }
//                                            if ((Boolean) resultMap.get("matched")) {
//                                                insertMatchResultMix(resultMap, geneVo, compareQueue);
//                                            }
//                                        }
//                                    }
//                                }
//                                //修改比对进度
//                                logger.info("修改比对进度开始----------------------");
//                                Double aDouble = new Double(number);
//                                Double bigDecimal1 = new Double(singleCount);
//                                double v = aDouble / bigDecimal1;
//                                int schedule = (int) Math.round(v * 100);
//                                compareQueue.setTargetCount(Integer.toString(schedule)+"%");
//                                compareQueueService.updateByPrimaryKey(compareQueue);
//                                logger.info("修改比对进度结束----------------------");
//                            }
//                        }
//                        //修改比对状态
//                        if (compareQueue.getStatus().equals(Constants.MATCH_STATUS_02)){
//                            logger.info("修改比对状态开始----------------------");
//                            compareQueue.setStatus(Constants.MATCH_STATUS_01);
//                            compareQueueService.updateStatus(compareQueue);
//                            logger.info("修改比对状态结束----------------------");
//                        }
//                    }
//                }
//            }
//            logger.info("#####混合拆分比对定时任务结束 : " + LocalDateTime.now().toLocalTime());
//
//        } catch (Exception e) {
//            logger.error("#####混合拆分比对定时任务失败！" + e);
//        }
//    }
//
//    /**
//     * 添加拆分比对单一结果信息
//     */
//    private void insertMatchResultSing(Map<String, Object> resultMap, SampleDnaGeneVo geneVo, CompareQueue compareQueue) {
//        //查询比对结果是否存在
//        MatchResult result = new MatchResult();
//        result.setCompareId(compareQueue.getId());
//        result.setSingleGeneId(geneVo.getEntity().getId());
//        result.setSampleGeneResultType(Constants.RESULT_TYPE_04);
//        MatchResult matchResult = matchResulService.selectByMatch(result);
//        //如果存在就修改结果，不存在添加
//        if (matchResult != null){
//            matchResult.setProportionSiteNum(resultMap.get("sumCount").toString());//比中样本位点个数
//            matchResult.setRatio(Integer.parseInt(resultMap.get("sameCount").toString()));//比中数
//            matchResult.setSplitDigit(Integer.parseInt(resultMap.get("diffCount").toString()));//差异数
//            matchResult.setProportionCaseNo(geneVo.getCaseNo());//案件编号
//            matchResult.setProportionCaseName(geneVo.getCaseName());//案件名称
//            matchResult.setProportionSampleNo(geneVo.getSampleNo());//检材编号
//            matchResult.setProportionSampleName(geneVo.getSampleName());//检材名称
//            matchResult.setGeneInfo(geneVo.getEntity().getGeneInfo());//基因信息
//            matchResult.setGenePicture(geneVo.getEntity().getGenePicture());//基因图片
//            matchResult.setProportionPersonnel(geneVo.getPersonType());//人员类型
//            matchResult.setProportionDistrict(geneVo.getRegionalismName());//地区
//            matchResult.setComparisonTime(new Date());//比中时间
//            matchResult.setSampleGeneResultType(Constants.RESULT_TYPE_04);
//            matchResulService.updateByPrimaryKey(matchResult);
//        }else{
//            MatchResult match = new MatchResult();
//            match.setId(UUID.randomUUID().toString());
//            match.setCompareId(compareQueue.getId());
//            match.setSingleGeneId(geneVo.getEntity().getId());
//            match.setProportionSiteNum(resultMap.get("sumCount").toString());//比中样本位点个数
//            match.setRatio(Integer.parseInt(resultMap.get("sameCount").toString()));//比中数
//            match.setSplitDigit(Integer.parseInt(resultMap.get("diffCount").toString()));//差异数
//            match.setProportionCaseNo(geneVo.getCaseNo());//案件编号
//            match.setProportionCaseName(geneVo.getCaseName());//案件名称
//            match.setProportionSampleNo(geneVo.getSampleNo());//检材编号
//            match.setProportionSampleName(geneVo.getSampleName());//检材名称
//            match.setGeneInfo(geneVo.getEntity().getGeneInfo());//基因信息
//            match.setGenePicture(geneVo.getEntity().getGenePicture());//基因图片
//            match.setProportionPersonnel(geneVo.getPersonType());//人员类型
//            match.setProportionDistrict(geneVo.getRegionalismName());//地区
//            match.setComparisonTime(new Date());//比中时间
//            match.setSampleGeneResultType(Constants.RESULT_TYPE_04);
//            matchResulService.insert(match);
//            //添加比中消息
//            System.out.println("========添加拆分比对单一比中消息.============");
//            MobileNews mobileNews = new MobileNews();
//            mobileNews.setId(UUID.randomUUID().toString());
//            mobileNews.setTitle("比对消息提示");//标题
//            mobileNews.setContent("案件"+geneVo.getCaseNo()+"的检材"+geneVo.getSampleNo()+"被比中");//内容
//            mobileNews.setState(0);//状态  未读
//            Date date = new Date();
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String createdate = sdf.format(date);
//            mobileNews.setCreateDatetime(createdate);
//            mobileNews.setType(11);
//            mobileNews.setUserid(compareQueue.getCreatePerson());
//            mobileNews.setCaseId(compareQueue.getId());//案件id
////            mobileNews.setUserOrg(caseInfo.getOrgId());
//            mobileNews.setMessageType(3);//消息类型
//            mobileNewsMapper.insert(mobileNews);
//            System.out.println("===========添加拆分比对单一比中消息成功!============");
//        }
//
//
//    }
//
//    /**
//     * 添加拆分比混合信息
//     */
//    private void insertMatchResultMix(Map<String, Object> resultMap, SampleDnaGeneVo geneVo, CompareQueue compareQueue) {
//        //查询比对结果是否存在
//        MatchResult result = new MatchResult();
//        result.setCompareId(compareQueue.getId());
//        result.setMixedSampleGeneId(geneVo.getEntity().getId());
//        result.setSampleGeneResultType(Constants.RESULT_TYPE_02);
//        MatchResult matchResult = matchResulService.selectByMatch(result);
//        if (matchResult != null){
//            // 如果存在，修改
//            matchResult.setProportionSiteNum(resultMap.get("sumCount").toString());//比中样本位点个数
//            matchResult.setRatio(Integer.parseInt(resultMap.get("sameCount").toString()));//比中数
//            matchResult.setSplitDigit(Integer.parseInt(resultMap.get("diffCount").toString()));//差异数
//            matchResult.setProportionCaseNo(geneVo.getCaseNo());//案件编号
//            matchResult.setProportionCaseName(geneVo.getCaseName());//案件名称
//            matchResult.setProportionSampleNo(geneVo.getSampleNo());//检材编号
//            matchResult.setProportionSampleName(geneVo.getSampleName());//检材名称
//            matchResult.setGeneInfo(geneVo.getEntity().getGeneInfo());//基因信息
//            matchResult.setGenePicture(geneVo.getEntity().getGenePicture());//基因图片
//            matchResult.setProportionPersonnel(geneVo.getPersonType());//人员类型
//            matchResult.setProportionDistrict(geneVo.getRegionalismName());//地区
//            matchResult.setComparisonTime(new Date());//比中时间
//            matchResult.setSampleGeneResultType(Constants.RESULT_TYPE_02);
////            matchResultMixSingleService.updateMatchInfo(matchResult);
//        }else{
//            //新增
//            MatchResult match = new MatchResult();
//            match.setId(UUID.randomUUID().toString());
//            match.setCompareId(compareQueue.getId());
//            match.setMixedSampleGeneId(geneVo.getEntity().getId());
//            match.setSampleGeneResultType(Constants.RESULT_TYPE_02);
//            match.setRatio(Integer.parseInt(resultMap.get("sameCount").toString()));//比中数
//            match.setSplitDigit(Integer.parseInt(resultMap.get("diffCount").toString()));//差异数
//            match.setProportionCaseNo(geneVo.getCaseNo());//案件编号
//            match.setProportionCaseName(geneVo.getCaseName());//案件名称
//            match.setProportionSampleNo(geneVo.getSampleNo());//检材编号
//            match.setProportionSampleName(geneVo.getSampleName());//检材名称
//            match.setGeneInfo(geneVo.getEntity().getGeneInfo());//基因信息
//            match.setGenePicture(geneVo.getEntity().getGenePicture());//基因图片
//            match.setProportionPersonnel(geneVo.getPersonType());//人员类型
//            match.setProportionDistrict(geneVo.getRegionalismName());//地区
//            match.setComparisonTime(new Date());//比中时间
////            matchResultMixSingleService.insetMatchInfo(match);
//
//            //添加比中消息
//            System.out.println("========添加拆分比对混合样本比中消息.============");
//            MobileNews mobileNews = new MobileNews();
//            mobileNews.setId(UUID.randomUUID().toString());
//            mobileNews.setTitle("比对消息提示");//标题
//            mobileNews.setContent("案件"+geneVo.getCaseNo()+"的检材"+geneVo.getSampleNo()+"被比中");//内容
//            mobileNews.setState(0);//状态  未读
//            Date date = new Date();
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            String createdate = sdf.format(date);
//            mobileNews.setCreateDatetime(createdate);
//            mobileNews.setType(11);
//            mobileNews.setUserid(compareQueue.getCreatePerson());
//            mobileNews.setCaseId(compareQueue.getId());//案件id
////            mobileNews.setUserOrg(caseInfo.getOrgId());
//            mobileNews.setMessageType(3);//消息类型
//            mobileNewsMapper.insert(mobileNews);
//            System.out.println("===========添加拆分比对混合样本比中消息成功!============");
//        }
//    }
}
