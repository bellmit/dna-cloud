package com.bazl.dna.mix.task;

/**
 * Created by Administrator on 2019/7/26.
 */
//@Component注解用于对那些比较中立的类进行注释；
//@Component
public class MixedSplitSingleMatchResultTask {
//
//    private final Logger logger = LoggerFactory.getLogger(getClass());
//
//    @Autowired
//    CompareQueueService compareQueueService;
//
//    @Autowired
//    MixedSampleGeneService mixedSampleGeneService;
//
//    @Autowired
//    SingleSampleGeneService singleSampleGeneService;
//
//    @Autowired
//    SplitedSampleGeneService splitedSampleGeneService;
//    @Autowired
//    QualtyPersonnelService qualtyPersonnelService;
//    @Autowired
//    GeneMixCompareUtil geneMixCompareUtil;
//
//    @Value("${minSameMixCount}")
//    private int minSameMixCount;
//
//    @Autowired
//    GeneCompareUtil geneCompareUtil;
//
//    @Autowired
//    MobileNewsMapper mobileNewsMapper;
//    @Autowired
//    CaseInfoService caseInfoService;
//    @Autowired
//    RedisUtils redisUtils;
//
//    /*
//    *    混合比对单一样本
//    * */
////    @Async
//    @Scheduled(fixedDelay = 1800000)  //间隔30分钟
//    public void first1() throws InterruptedException {
//        int mixSameCount = minSameMixCount;
//        //判断混合基因是否包含此基因
//        if (mixSameCount == 0) {
//            mixSameCount = minSameMixCount;
//        }
//        try {
//            int numQueue = 0;
//            boolean isFinish = true;
//            look:        //使用标记语句look
//            while (isFinish) {
//                logger.info("#####混合单一比对定时任务开始 : " + LocalDateTime.now().toLocalTime());
//                //查询比对队列表中状态是未比对以及混合基因型的数据
//                List<CompareQueue> mixedCompareQueueList = compareQueueService.selectNotMixedSampleGeneCompareQueue(Constants.MATCH_STATUS_02, Constants.QUEUE_TYPE_01, numQueue,1000);
//                numQueue++;
//                if (mixedCompareQueueList == null || mixedCompareQueueList.size() == 0) {
//                    isFinish = false;
//                    break;
//                } else {
//                    for (CompareQueue compareQueue : mixedCompareQueueList) {
//                        //根据样本id查询混合样本基因表
////                        List<MixedSampleGene> mixedSampleGeneList = mixedSampleGeneService.selectMixedSampleGeneBySampleId(compareQueue.getSampleId());
////                        if (null != mixedSampleGeneList && mixedSampleGeneList.size() > 0) {
////                            int num = 0;
////                            boolean flag = true;
////                            while (flag) {
////                                //查询单一样本基因信息
////                                List<SingleSampleGene> singleSampleGeneList = singleSampleGeneService.selectSingleSampleGeneAll(num, 1000);
////                                num++;
////                                if (ListUtils.isNullOrEmptyList(singleSampleGeneList)) {
////                                    flag = false;
////                                    break look;//直接跳出while语句
////                                }
////                                if (null != singleSampleGeneList && singleSampleGeneList.size() > 0) {
////                                    //循环比对
////                                    for (SingleSampleGene singleSampleGene : singleSampleGeneList) {
////
////                                        //获取混合基因信息比对单一基因表中的基因信息
////                                        Map<String, Object> resultMap = geneCompareUtil.compareGeneInfo(mixedSampleGeneList.get(0).getGeneInfo(), singleSampleGene.getGeneInfo(), mixSameCount);
////
////                                        if (resultMap == null) {
////                                            continue;
////                                        }
////                                        if ((Boolean) resultMap.get("matched")) {
////                                            insertMatchResultMixSingle(resultMap, mixedSampleGeneList.get(0), singleSampleGene, compareQueue.getId());
////                                        }
////                                    }
////                                }
////                            }
////                        }
//
//                    }
//                }
//            }
//            logger.info("#####混合单一比对定时任务结束 : " + LocalDateTime.now().toLocalTime());
//
//        } catch (Exception e) {
//            logger.error("#####混合单一比对定时任务失败！" + e);
//        }
//    }
//
//    /*
//    *    混合比对拆分基因
//    * */
////    @Async
//    @Scheduled(fixedDelay = 1800000)  //间隔30分钟
//    public void first2() throws InterruptedException {
//        int mixSameCount = minSameMixCount;
//        //判断混合基因是否包含此基因
//        if (mixSameCount == 0) {
//            mixSameCount = minSameMixCount;
//        }
//        try {
//            int numQueue = 0;
//            boolean isFinish = true;
//            look:        //使用标记语句look
//            while (isFinish) {
//                logger.info("#####混合拆分比对定时任务开始 : " + LocalDateTime.now().toLocalTime());
//                //查询比对队列表中状态是未比对以及混合基因型的数据
//                List<CompareQueue> mixedCompareQueueList = compareQueueService.selectNotMixedSampleGeneCompareQueue(Constants.MATCH_STATUS_02, Constants.QUEUE_TYPE_01, numQueue, 5);
//                numQueue++;
//                if (mixedCompareQueueList == null || mixedCompareQueueList.size() == 0) {
//                    isFinish = false;
//                    break;
//                } else {
//                    for (CompareQueue compareQueue : mixedCompareQueueList) {
//                        //根据样本id查询混合样本基因表
////                        List<MixedSampleGene> mixedSampleGeneList = mixedSampleGeneService.selectMixedSampleGeneBySampleId(compareQueue.getSampleId());
////                        if (null != mixedSampleGeneList && mixedSampleGeneList.size() > 0) {
////                            int num = 0;
////                            boolean flag = true;
////                            while (flag) {
////                                //查询拆分基因信息
////                                List<SplitedSampleGene> splitedSampleGeneList = splitedSampleGeneService.selectAll(num, 10000);
////                                num++;
////                                if (ListUtils.isNullOrEmptyList(splitedSampleGeneList)) {
////                                    flag = false;
////                                    break look; //直接跳出while语句
////                                }
////                                if (null != splitedSampleGeneList && splitedSampleGeneList.size() > 0) {
////                                    for (SplitedSampleGene splitedSampleGene : splitedSampleGeneList) {
////                                        //获取混合基因信息比对拆分基因表中的基因信息
////                                        Map<String, Object> resultMap = geneCompareUtil.compareGeneInfo(mixedSampleGeneList.get(0).getGeneInfo(), splitedSampleGene.getGeneInfo(), mixSameCount);
////
////                                        if ((Boolean) resultMap.get("matched")) {
////                                            insertMatchSplitedMixSingle(resultMap, mixedSampleGeneList.get(0), splitedSampleGene, compareQueue.getId());
////
////                                        }
////                                    }
////                                }
////                            }
////                        }
//
//                    }
//                }
//            }
//            logger.info("#####混合拆分比对定时任务结束 : " + LocalDateTime.now().toLocalTime());
//
//        } catch (Exception e) {
//            logger.error("#####混合单拆分比对定时任务失败！" + e);
//        }
//    }
//
//    /*
//    *    混合比对质控人员
//    * */
////    @Async
//    @Scheduled(fixedDelay = 1800000)  //间隔30分钟
//    public void first3() throws InterruptedException {
//        int mixSameCount = minSameMixCount;
//        //判断混合基因是否包含此基因
//        if (mixSameCount == 0) {
//            mixSameCount = minSameMixCount;
//        }
//        try {
//            int numQueue = 0;
//            boolean isFinish = true;
//            look:        //使用标记语句look
//            while (isFinish) {
//                logger.info("#####混合质控人员比对定时任务开始 : " + LocalDateTime.now().toLocalTime());
//                //查询比对队列表中状态是未比对以及混合基因型的数据
//                List<CompareQueue> mixedCompareQueueList = compareQueueService.selectNotMixedSampleGeneCompareQueue(Constants.MATCH_STATUS_02, Constants.QUEUE_TYPE_01, numQueue, 5);
//                if (mixedCompareQueueList == null || mixedCompareQueueList.size() == 0) {
//                    isFinish = false;
//                    break;
//                } else {
//                    for (CompareQueue compareQueue : mixedCompareQueueList) {
//                        //根据样本id查询混合样本基因表
////                        List<MixedSampleGene> mixedSampleGeneList = mixedSampleGeneService.selectMixedSampleGeneBySampleId(compareQueue.getSampleId());
////                        if (null != mixedSampleGeneList && mixedSampleGeneList.size() > 0) {
////                            int num = 0;
////                            boolean flag = true;
////                            while (flag) {
////                                //查询质控人员样本基因信息
////                                List<QualtyPersonnel> qualtyPersonnelList = qualtyPersonnelService.selectAll(num, 10000);
////                                num++;
////                                if (ListUtils.isNullOrEmptyList(qualtyPersonnelList)) {
////                                    flag = false;
////                                    break look; //直接跳出while语句
////                                }
////                                if (null != qualtyPersonnelList && qualtyPersonnelList.size() > 0) {
////                                    for (QualtyPersonnel qualtyPersonnel : qualtyPersonnelList) {
////
////                                        Map<String, Object> resultMap = geneCompareUtil.compareGeneInfo(mixedSampleGeneList.get(0).getGeneInfo(), qualtyPersonnel.getGeneInfo(), mixSameCount);
////                                        if (resultMap == null) {
////                                            continue;
////                                        }
////
////                                        if ((Boolean) resultMap.get("matched")) {
////                                            insertMatchResultMixQualty(resultMap, mixedSampleGeneList.get(0), qualtyPersonnel, compareQueue.getId());
////                                        }
////                                    }
////                                }
////                            }
////                        }
//
//                    }
//                }
//            }
//            logger.info("#####混合质控人员比对定时任务结束 : " + LocalDateTime.now().toLocalTime());
//
//        } catch (Exception e) {
//            logger.error("#####混合质控人员比对定时任务结束失败!!!" + e);
//        }
//    }
//
//    /*
//    *      全库比对  单一样本和质控人员
//    * */
//    @Scheduled(fixedDelay = 1800000)
//    public void first4() throws InterruptedException {
//        try {
//            boolean isFinish = true;
//            look:        //使用标记语句look
//            while (isFinish) {
//                logger.info("#####全库比对定时任务开始 : " + LocalDateTime.now().toLocalTime());
//                //查询比对队列表中状态是未比对以及混合基因型的数据
////                List<CompareContribution> compareContributions = compareContributionService.selectByStatus(Constants.MATCH_STATUS_02);
////                if (ListUtils.isNotNullAndEmptyList(compareContributions)) {
////                    for (CompareContribution contribution : compareContributions) {
////                        if (StringUtils.isNotBlank(contribution.getGeneInfo()) && StringUtils.isNotBlank(contribution.getTargetType())) {
////                            List<SingleSampleGeneVo> singleSampleGeneVoList = null;
////                            List<SingleSampleGeneVo> singleSampleGeneLists = new ArrayList<>();
////                            //判断比对类型
////                            String[] strings = stringsUtils(contribution.getTargetType());
////                            if (strings != null) {
////                                //判断物证、人员、质控
////                                for (String sampleflag : strings) {
////                                    //0 .物证
////                                    if (sampleflag.equals("0")) {
////                                        List<SingleSampleGeneVo> singleSampleGeneList = null;
////                                        //从缓存里查数据
////                                        int sumPage = (int) redisUtils.get(sampleflag + "_sumPage");
////                                        if (sumPage == 0){
////                                            break;
////                                        }
////                                        for (int i=0;i<=sumPage;i++){
////                                            List<SingleSampleGeneVo> caseList = (List<SingleSampleGeneVo>) redisUtils.get("allFastComparison_"+sampleflag+"_"+i);
////                                            if (ListUtils.isNotNullAndEmptyList(caseList)) {
////                                                singleSampleGeneList = caseList;
////                                            } else {
//////                                                singleSampleGeneList = singleSampleGeneService.selectSampleTypeSample(sampleflag);
//////                                                redisUtils.set("allFastComparison_"+sampleflag, singleSampleGeneList);
////                                                continue;
////                                            }
////                                            if (ListUtils.isNotNullAndEmptyList(singleSampleGeneList)) {
////                                                for (SingleSampleGeneVo singleSampleGene : singleSampleGeneList) {
////                                                    SingleSampleGene sampleGene = singleSampleGeneService.selectById(singleSampleGene.getEntity().getId());
////                                                    if (sampleGene != null){
////                                                        //获取贡献者基因信息比对单一基因表中的基因信息
////                                                        Map<String, Object> resultMap = geneMixCompareUtil.fastComparisonGeneAll(contribution.getGeneInfo(), sampleGene.getGeneInfo(), Integer.parseInt(contribution.getCondition()));
////                                                        if ((Boolean) resultMap.get("matched")) {
////                                                            insertAllLibraries1(resultMap, contribution, sampleGene);
////                                                        }
////                                                    }
////                                                }
////                                                //判断strMix解析报告比对
////                                            /*if ((Boolean) resultMap.get("matched")) {
////                                                insertMatchSplitedMixSingle(resultMap, mixedSampleGeneList.get(0), splitedSampleGene, compareQueue.getId());
////                                            }*/
////                                            }
////                                        }
////                                    } else if (sampleflag.equals("3")) {
////                                        List<QualtyPersonnel> qualtyPersonnels = null;
////                                        //从缓存里查询质控人员
////                                        List<QualtyPersonnel> caseList = (List<QualtyPersonnel>) redisUtils.get("allFastComparison_"+sampleflag);
////                                        if (ListUtils.isNotNullAndEmptyList(caseList)) {
////                                            qualtyPersonnels = caseList;
////                                        } else {
////                                            qualtyPersonnels = qualtyPersonnelService.selectAllList();
////                                            redisUtils.set("allFastComparison_"+sampleflag, qualtyPersonnels);
////                                        }
////                                        if (ListUtils.isNotNullAndEmptyList(qualtyPersonnels)) {
////                                            for (QualtyPersonnel personnel : qualtyPersonnels) {
////                                                //获取贡献者基因信息比对质控人员基因表中的基因信息
////                                                Map<String, Object> resultMap = geneMixCompareUtil.fastComparisonGeneAll(contribution.getGeneInfo(), personnel.getGeneInfo(), Integer.parseInt(contribution.getCondition()));
////                                                if ((Boolean) resultMap.get("matched")) {
////                                                    insertAllLibraries2(resultMap, contribution, personnel);
////                                                }
////                                            }
////                                        }
////                                        /*//判断strMix解析报告比对
////                                        if (StringUtils.isNotBlank(geneInfo) && ListUtils.isNotNullAndEmptyList(singleSampleGeneList)) {
////                                            singleSampleGeneVoList = geneMixCompareUtil.fastComparisonGene(geneInfo, singleSampleGeneList, conditionId);
////                                            singleSampleGeneLists.addAll(singleSampleGeneVoList);
////                                        }*/
////                                    } else {
////                                        List<SingleSampleGeneVo> singleSampleGeneList = null;
////                                        //从缓存里查数据
////                                        int sumPage = (int) redisUtils.get(sampleflag + "_sumPage");
////                                        if (sumPage == 0){
////                                            break;
////                                        }
////                                        for (int i=0;i<=sumPage;i++){
////                                            List<SingleSampleGeneVo> caseList = (List<SingleSampleGeneVo>) redisUtils.get("allFastComparison_"+sampleflag+"_"+i);
////                                            if (ListUtils.isNotNullAndEmptyList(caseList)) {
////                                                singleSampleGeneList = caseList;
////                                            } else {
//////                                               singleSampleGeneList = singleSampleGeneService.selectPersonSample(sampleflag);
//////                                                redisUtils.set("allFastComparison_"+sampleflag, singleSampleGeneList);
////                                                continue;
////                                            }
////                                        }
////                                        if (ListUtils.isNullOrEmptyList(singleSampleGeneList)) {
////                                            for (SingleSampleGeneVo singleSampleGene : singleSampleGeneList) {
////                                                SingleSampleGene sampleGene = singleSampleGeneService.selectById(singleSampleGene.getEntity().getId());
////                                                if (sampleGene != null){
////                                                    //获取贡献者基因信息比对单一基因表中的基因信息
////                                                    Map<String, Object> resultMap = geneMixCompareUtil.fastComparisonGeneAll(contribution.getGeneInfo(), sampleGene.getGeneInfo(), Integer.parseInt(contribution.getCondition()));
////                                                    if ((Boolean) resultMap.get("matched")) {
////                                                        insertAllLibraries1(resultMap, contribution, sampleGene);
////                                                    }
////                                                }
////                                            }
////                                        }
////                                        //判断strMix解析报告比对
////                                        /*if (StringUtils.isNotBlank(geneInfo) && ListUtils.isNotNullAndEmptyList(singleSampleGeneList)) {
////                                            singleSampleGeneVoList = geneMixCompareUtil.fastComparisonGene(geneInfo, singleSampleGeneList, conditionId);
////                                            singleSampleGeneLists.addAll(singleSampleGeneVoList);
////
////                                        }*/
////                                    }
////                                }
////                            }
////
////                        }
////                    }
////                } else {
////                    isFinish = false;
////                    break;
////                }
//            }
//            logger.info("#####全库比对定时任务结束 : " + LocalDateTime.now().toLocalTime());
//        } catch (Exception e) {
//            logger.error("#####全库比对定时任务失败！" + e);
//        }
//    }
//
//    /**
//     * 添加混合比对单一结果信息
//     *
//     * @param resultMap
//     * @param mixedSampleGene
//     * @param singleSampleGene
//     * @param id
//     */
//    private void insertMatchResultMixSingle(Map<String, Object> resultMap, MixedSampleGene mixedSampleGene, SingleSampleGene singleSampleGene, String id) {
//        try {
//            //查看是否已经匹配过 如果已经匹配过了就修改  没有就添加
////            MatchResultMixSingle matchResultMixSingle1 = new MatchResultMixSingle();
////            matchResultMixSingle1.setMixedSampleGendId(mixedSampleGene.getId());
////            matchResultMixSingle1.setSinglegeneId(singleSampleGene.getId());
////            List<MatchResultMixSingle> matchResultMixSingleList1 = matchResultMixSingleService.selectByMixedSampleGeneId(matchResultMixSingle1);
////            if (null != matchResultMixSingleList1 && matchResultMixSingleList1.size() > 0) {
////                MatchResultMixSingle matchResultMixSingle = matchResultMixSingleList1.get(0);
////                matchResultMixSingle.setRatio(Short.valueOf(resultMap.get("sameCount").toString()));
////                matchResultMixSingle.setSplitDigit(Short.valueOf(resultMap.get("diffCount").toString()));
////                matchResultMixSingle.setSampleGeneResultType(Constants.SAMPLEGENERESULT_TYPE_1);
////                matchResultMixSingleService.update(matchResultMixSingle);
////                //修改比对状态为已比对
////                CompareQueue compareQueue = new CompareQueue();
////                compareQueue.setId(id);
////                compareQueue.setStatus(Constants.MATCH_STATUS_01);
////                compareQueueService.updateStatus(compareQueue);
////
////            } else {
////                //添加混合单一样本比中详情信息
////                MatchResultMixSingle matchResultMixSingle = new MatchResultMixSingle();
////                matchResultMixSingle.setId(UUID.randomUUID().toString());
////                matchResultMixSingle.setMixedSampleGendId(mixedSampleGene.getId());
////                matchResultMixSingle.setSinglegeneId(singleSampleGene.getId());
////                matchResultMixSingle.setRatio(Short.valueOf(resultMap.get("sameCount").toString()));
////                matchResultMixSingle.setSplitDigit(Short.valueOf(resultMap.get("diffCount").toString()));
////                matchResultMixSingle.setComparisonTime(new Date());
////                matchResultMixSingle.setSampleGeneResultType(Constants.SAMPLEGENERESULT_TYPE_1);
////                matchResultMixSingleService.insert(matchResultMixSingle);
////                //修改比对状态为已比对
////                CompareQueue compareQueue = new CompareQueue();
////                compareQueue.setId(id);
////                compareQueue.setStatus(Constants.MATCH_STATUS_01);
////                compareQueueService.updateStatus(compareQueue);
////
////                //添加比中消息信息
////                if (null != mixedSampleGene.getCaseID()){
////                    CaseInfo caseInfo = caseInfoService.selectByCaseId(mixedSampleGene.getCaseID());
////                    if (caseInfo != null){
////                        MobileNews mobileNews = new MobileNews();
////                        mobileNews.setId(UUID.randomUUID().toString());
////                        mobileNews.setTitle("比对消息提示");//标题
////                        mobileNews.setContent(caseInfo.getCaseName()+"单一比对已比中");//内容
////                        mobileNews.setState(0);//状态  未读
////                        Date date = new Date();
////                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////                        String createdate = sdf.format(date);
////                        mobileNews.setCreateDatetime(createdate);
////                        mobileNews.setType(11);
////                        mobileNews.setUserid(caseInfo.getCreatePerson());
////                        mobileNews.setCaseId(mixedSampleGene.getCaseID());//案件id
////                        mobileNews.setUserOrg(caseInfo.getOrgId());
////                        mobileNews.setMessageType(3);//消息类型
////                        mobileNewsMapper.insert(mobileNews);
////                        System.out.println("添加混合单一比对比中消息");
////                    }
////                }
////            }
//        } catch (Exception e) {
//            logger.error("添加混合单一比中详情表失败！" + e);
//        }
//    }
//
//    /**
//     * 添加混合比对拆分结果信息
//     *
//     * @param resultMap
//     * @param mixedSampleGene
//     * @param splitedSampleGene
//     */
//    private void insertMatchSplitedMixSingle(Map<String, Object> resultMap, MixedSampleGene mixedSampleGene, SplitedSampleGene splitedSampleGene, String id) {
//        try {
//            //查看是否已经匹配过 如果已经匹配过了就修改  没有就添加
////            MatchResultMixSingle matchResultMixSingle1 = new MatchResultMixSingle();
////            matchResultMixSingle1.setMixedSampleGendId(mixedSampleGene.getId());
////            matchResultMixSingle1.setSplitedSampleGeneId(splitedSampleGene.getId());
////            List<MatchResultMixSingle> matchResultMixSingleList1 = matchResultMixSingleService.selectByMixedSampleGeneIdAndSplitSampleGeneId(matchResultMixSingle1);
////            if (null != matchResultMixSingleList1 && matchResultMixSingleList1.size() > 0) {
////                MatchResultMixSingle matchResultMixSingle = matchResultMixSingleList1.get(0);
////                matchResultMixSingle.setRatio(Short.valueOf(resultMap.get("sameCount").toString()));
////                matchResultMixSingle.setSplitDigit(Short.valueOf(resultMap.get("diffCount").toString()));
////                matchResultMixSingle.setSampleGeneResultType(Constants.SAMPLEGENERESULT_TYPE_2);
////                matchResultMixSingleService.update(matchResultMixSingle);
////                //修改比对状态为已比对
////                CompareQueue compareQueue = new CompareQueue();
////                compareQueue.setId(id);
////                compareQueue.setStatus(Constants.MATCH_STATUS_01);
////                compareQueueService.updateStatus(compareQueue);
////            } else {
////                //添加混合单一样本比中详情信息
////                MatchResultMixSingle matchResultMixSingle = new MatchResultMixSingle();
////                matchResultMixSingle.setId(UUID.randomUUID().toString());
////                matchResultMixSingle.setMixedSampleGendId(mixedSampleGene.getId());
////                matchResultMixSingle.setSplitedSampleGeneId(splitedSampleGene.getId());
////                matchResultMixSingle.setRatio(Short.valueOf(resultMap.get("sameCount").toString()));
////                matchResultMixSingle.setSplitDigit(Short.valueOf(resultMap.get("diffCount").toString()));
////                matchResultMixSingle.setComparisonTime(new Date());
////                matchResultMixSingle.setSampleGeneResultType(Constants.SAMPLEGENERESULT_TYPE_2);
////                matchResultMixSingleService.insert(matchResultMixSingle);
////                //修改比对状态为已比对
////                CompareQueue compareQueue = new CompareQueue();
////                compareQueue.setId(id);
////                compareQueue.setStatus(Constants.MATCH_STATUS_01);
////                compareQueueService.updateStatus(compareQueue);
////
////                //添加比中消息信息
////                if (null != mixedSampleGene.getCaseID()){
////                    CaseInfo caseInfo = caseInfoService.selectByCaseId(mixedSampleGene.getCaseID());
////                    if (caseInfo != null){
////                        MobileNews mobileNews = new MobileNews();
////                        mobileNews.setId(UUID.randomUUID().toString());
////                        mobileNews.setTitle("比对消息提示");//标题
////                        mobileNews.setContent(caseInfo.getCaseName()+"混合比对已比中");//内容
////                        mobileNews.setState(0);//状态  未读
////                        Date date = new Date();
////                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////                        String createdate = sdf.format(date);
////                        mobileNews.setCreateDatetime(createdate);
////                        mobileNews.setType(11);
////                        mobileNews.setUserid(caseInfo.getCreatePerson());
////                        mobileNews.setCaseId(mixedSampleGene.getCaseID());//案件id
////                        mobileNews.setUserOrg(caseInfo.getOrgId());
////                        mobileNews.setMessageType(3);//消息类型
////                        mobileNewsMapper.insert(mobileNews);
////                        System.out.println("添加混合比对比中消息");
////                    }
////                }
////            }
//        } catch (Exception e) {
//            logger.error("添加混合拆分比中详情表失败！" + e);
//        }
//    }
//
//    /**
//     * 添加混合比对 质控人员结果信息
//     *
//     * @param resultMap
//     * @param mixedSampleGene
//     * @param qualtyPersonnel
//     * @param id
//     */
//    private void insertMatchResultMixQualty(Map<String, Object> resultMap, MixedSampleGene mixedSampleGene,
//                                            QualtyPersonnel qualtyPersonnel, String id) {
//        try {
//            //查看是否已经匹配过 如果已经匹配过了就修改  没有就添加
////            MatchResultMixQuality matchResultMixQuality = new MatchResultMixQuality();
////            matchResultMixQuality.setMixedId(mixedSampleGene.getId());
////            matchResultMixQuality.setQusltyId(qualtyPersonnel.getId());
////            List<MatchResultMixQuality> matchResultMixQualityList = matchResultMixQualityService.selectByMatchResultMixQualityGeneId(matchResultMixQuality);
////            if (null != matchResultMixQualityList && matchResultMixQualityList.size() > 0) {
////                MatchResultMixQuality matchResultMixQuality1 = matchResultMixQualityList.get(0);
////                matchResultMixQuality1.setRatio(Short.valueOf(resultMap.get("sameCount").toString()));
////                matchResultMixQuality1.setSplitDigit(Short.valueOf(resultMap.get("diffCount").toString()));
////                matchResultMixQualityService.updateMatchResultMixQuality(matchResultMixQuality1);
////                //修改比对状态为已比对
////                CompareQueue compareQueue = new CompareQueue();
////                compareQueue.setId(id);
////                compareQueue.setStatus(Constants.MATCH_STATUS_01);
////                compareQueueService.updateStatus(compareQueue);
////            } else {
////                //添加混合单一样本比中详情信息
////                MatchResultMixQuality matchResultMixQuality1 = new MatchResultMixQuality();
////                matchResultMixQuality1.setId(UUID.randomUUID().toString());
////                matchResultMixQuality1.setMixedId(mixedSampleGene.getId());
////                matchResultMixQuality1.setQusltyId(qualtyPersonnel.getId());
////                matchResultMixQuality1.setRatio(Short.valueOf(resultMap.get("sameCount").toString()));
////                matchResultMixQuality1.setSplitDigit(Short.valueOf(resultMap.get("diffCount").toString()));
////                matchResultMixQuality1.setComparisonTime(new Date());
////                matchResultMixQualityService.insertMatchResultMixQuality(matchResultMixQuality1);
////                //修改比对状态为已比对
////                CompareQueue compareQueue = new CompareQueue();
////                compareQueue.setId(id);
////                compareQueue.setStatus(Constants.MATCH_STATUS_01);
////                compareQueueService.updateStatus(compareQueue);
////
////                //添加比中消息信息
////                if (null != mixedSampleGene.getCaseID()){
////                    CaseInfo caseInfo = caseInfoService.selectByCaseId(mixedSampleGene.getCaseID());
////                    if (caseInfo != null){
////                        MobileNews mobileNews = new MobileNews();
////                        mobileNews.setId(UUID.randomUUID().toString());
////                        mobileNews.setTitle("比对消息提示");//标题
////                        mobileNews.setContent(caseInfo.getCaseName()+"质控人员比对已比中");//内容
////                        mobileNews.setState(0);//状态  未读
////                        Date date = new Date();
////                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////                        String createdate = sdf.format(date);
////                        mobileNews.setCreateDatetime(createdate);
////                        mobileNews.setType(11);
////                        mobileNews.setUserid(caseInfo.getCreatePerson());
////                        mobileNews.setCaseId(mixedSampleGene.getCaseID());//案件id
////                        mobileNews.setUserOrg(caseInfo.getOrgId());
////                        mobileNews.setMessageType(3);//消息类型
////                        mobileNewsMapper.insert(mobileNews);
////                        System.out.println("添加质控人员比对比中消息");
////                    }
////                }
////            }
//        } catch (Exception e) {
//            logger.error("添加混合比对质控人员比中表失败！" + e);
//        }
//    }
//
//    /**
//     * 添加全库比对单一结果信息
//     *
//     * @param resultMap
//     * @param contribution
//     * @param singleSampleGene
//     */
////    private void insertAllLibraries1(Map<String, Object> resultMap, CompareContribution contribution, SingleSampleGene singleSampleGene) {
////        try {
////            //查看是否已经匹配过 如果已经匹配过了就修改  没有就添加
////            MatchResultSinglsQualty matchResultSinglsQualty = new MatchResultSinglsQualty();
////            matchResultSinglsQualty.setContributionGeneId(contribution.getId());
////            matchResultSinglsQualty.setSingleGeneId(singleSampleGene.getId());
////            List<MatchResultSinglsQualty> matchResultSinglsQualties = matchResultSinglsQualtyService.selectList(matchResultSinglsQualty);
////            if (ListUtils.isNotNullAndEmptyList(matchResultSinglsQualties)) {
////                MatchResultSinglsQualty matchResultSingl = matchResultSinglsQualties.get(0);
////                matchResultSingl.setRatio(Short.valueOf(resultMap.get("sameCount").toString()));
////                matchResultSingl.setSplitDigit(Short.valueOf(resultMap.get("diffCount").toString()));
////                matchResultSingl.setSampleGeneResultType(Constants.SAMPLEGENERESULT_TYPE_3);
////                matchResultSinglsQualtyService.updateByPrimaryKey(matchResultSingl);
////                //修改比对状态为已比对
////                contribution.setStatus(Constants.MATCH_STATUS_01);
//////                compareContributionService.updateByPrimaryKey(contribution);
////            } else {
////                //添加混合单一样本比中详情信息
////                MatchResultSinglsQualty matchResult = new MatchResultSinglsQualty();
////                matchResult.setId(UUID.randomUUID().toString());
////                matchResult.setContributionGeneId(contribution.getId());
////                matchResult.setSingleGeneId(singleSampleGene.getId());
////                matchResult.setRatio(Short.valueOf(resultMap.get("sameCount").toString()));
////                matchResult.setSplitDigit(Short.valueOf(resultMap.get("diffCount").toString()));
////                matchResult.setComparisonTime(new Date());
////                matchResult.setSampleGeneResultType(Constants.SAMPLEGENERESULT_TYPE_3);
////                matchResultSinglsQualtyService.insert(matchResult);
////                //修改比对状态为已比对
////                contribution.setStatus(Constants.MATCH_STATUS_01);
//////                compareContributionService.updateByPrimaryKey(contribution);
////            }
////        } catch (Exception e) {
////            logger.error("添加全库单一比中详情表失败！" + e);
////        }
////    }
//
//    /**
//     * 添加全库比对质控结果信息
//     *
//     * @param resultMap
//     * @param contribution
//     * @param qualtyPersonnel
//     */
////    private void insertAllLibraries2(Map<String, Object> resultMap, CompareContribution contribution, QualtyPersonnel qualtyPersonnel) {
////        try {
////            //查看是否已经匹配过 如果已经匹配过了就修改  没有就添加
////            MatchResultSinglsQualty matchResultSinglsQualty = new MatchResultSinglsQualty();
////            matchResultSinglsQualty.setContributionGeneId(contribution.getId());
////            matchResultSinglsQualty.setQusltyId(qualtyPersonnel.getId());
////            List<MatchResultSinglsQualty> matchResultSinglsQualties = matchResultSinglsQualtyService.selectList(matchResultSinglsQualty);
////            if (ListUtils.isNotNullAndEmptyList(matchResultSinglsQualties)) {
////                MatchResultSinglsQualty matchResultSingls = matchResultSinglsQualties.get(0);
////                matchResultSingls.setRatio(Short.valueOf(resultMap.get("sameCount").toString()));
////                matchResultSingls.setSplitDigit(Short.valueOf(resultMap.get("diffCount").toString()));
////                matchResultSingls.setSampleGeneResultType(Constants.SAMPLEGENERESULT_TYPE_4);
////                matchResultSinglsQualtyService.updateByPrimaryKey(matchResultSingls);
////                //修改比对状态为已比对
////                contribution.setStatus(Constants.MATCH_STATUS_01);
//////                compareContributionService.updateByPrimaryKey(contribution);
////            } else {
////                //添加混合单一样本比中详情信息
////                MatchResultSinglsQualty matchResult = new MatchResultSinglsQualty();
////                matchResult.setId(UUID.randomUUID().toString());
////                matchResult.setContributionGeneId(contribution.getId());
////                matchResult.setSingleGeneId(qualtyPersonnel.getId());
////                matchResult.setRatio(Short.valueOf(resultMap.get("sameCount").toString()));
////                matchResult.setSplitDigit(Short.valueOf(resultMap.get("diffCount").toString()));
////                matchResult.setComparisonTime(new Date());
////                matchResult.setSampleGeneResultType(Constants.SAMPLEGENERESULT_TYPE_4);
////                matchResultSinglsQualtyService.insert(matchResult);
////                //修改比对状态为已比对
////                contribution.setStatus(Constants.MATCH_STATUS_01);
//////                compareContributionService.updateByPrimaryKey(contribution);
////            }
////        } catch (Exception e) {
////            logger.error("添加全库质控比中详情表失败！" + e);
////        }
////    }
//
//    /**
//     * 快速比对 比对条件 公共方法
//     *
//     * @param targetType
//     * @return
//     */
//    private String[] stringsUtils(String targetType) {
//        String[] strings = null;
//        //替换传过来的
//        if (StringUtils.isNotBlank(targetType)) {
//            targetType = targetType.replace("'", "");
//            targetType = targetType.replace("\"", "");
//            targetType = targetType.replace("]", "");
//            targetType = targetType.replace("[", "");
//            //判断比对类型
//            strings = targetType.split(",");
//        }
//        return strings;
//    }
}
