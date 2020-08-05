package com.bazl.dna.mix.task;

/**
 * Created by Administrator on 2019/7/26.
 */
//@Component注解用于对那些比较中立的类进行注释；
//@Component
//@EnableScheduling   // 1.开启定时任务
//@EnableAsync        // 2.开启多线程
public class SplitSingleMatchResultTask {
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
//
//    @Autowired
//    CaseInfoService caseInfoService;
//
//    @Autowired
//    MobileNewsMapper mobileNewsMapper;
//
//    @Value("${minSameMixCount}")
//    private int minSameMixCount;
//
//    @Autowired
//    GeneSplitSingleCompareUtil geneSplitSingleCompareUtil;
//
//    /*
//    *       拆分比对单一
//    * */
////    @Async
//    @Scheduled(fixedDelay = 1800000)  //间隔30分钟
//    public void first() throws InterruptedException {
//        int mixSameCount = 0;
//        try {
//            int numQueue = 0;
//            boolean isFinish = true;
//            look:        //使用标记语句look
//            while (isFinish) {
//                //查询比对队列表中状态是未比对以及混合基因型的数据
//                List<CompareQueue> splitCompareQueueList = compareQueueService.selectNotMixedSampleGeneCompareQueue(Constants.MATCH_STATUS_02, Constants.QUEUE_TYPE_02, numQueue, 10000);
//                numQueue++;
//                if (splitCompareQueueList == null || splitCompareQueueList.size() == 0) {
//                    isFinish = false;
//                    break;
//                } else {
//                    logger.info("#####拆分单一比对定时任务开始 : " + LocalDateTime.now().toLocalTime());
//                    for (CompareQueue compareQueue : splitCompareQueueList) {
//                        //设置匹配下线
////                        if (0 == compareQueue.getMixSameCount()){
////                            mixSameCount = minSameMixCount;
////                        }else {
////                            mixSameCount = compareQueue.getMixSameCount();
////                        }
//                        //根据样本id查询混合样本基因表
////                        List<MixedSampleGene> mixedSampleGeneList = mixedSampleGeneService.selectMixedSampleGeneBySampleId(compareQueue.getSampleId());
//                        //根据混合id查询拆分样本基因表
////                        List<SplitedSampleGene> splitedSampleGeneList = splitedSampleGeneService.selectSplitedSampleGeneByMixedSampleGeneId(mixedSampleGeneList.get(0).getId());
//                        //查询单一样本基因信息
//                        int num = 0;
//                        boolean flag = true;
////                        while (flag) {
////                            if (ListUtils.isNullOrEmptyList(splitedSampleGeneList)) {
////                                flag = false;
////                                break  look;//跳出while循环
////                            }
////                            List<SingleSampleGene> singleSampleGeneList = singleSampleGeneService.selectSingleSampleGeneAll(num, 1000);
////                            num++;
////                            if (ListUtils.isNullOrEmptyList(singleSampleGeneList)) {
////                                flag = false;
////                                break  look;//跳出while循环
////                            }
////                            if (null != splitedSampleGeneList && splitedSampleGeneList.size() > 0 && null != singleSampleGeneList && singleSampleGeneList.size() > 0) {
////                                Map<String, Object> resultMap = new HashMap<>();
////                                SplitedSampleGene splitAuditedGene = null;
////                                SingleSampleGene auditedGene1 = null;
////                                for (int m = 0; m < splitedSampleGeneList.size(); m++) {
////                                    splitAuditedGene = splitedSampleGeneList.get(m);
////
////                                    for (int i = 0; i < singleSampleGeneList.size(); i++) {
////                                        auditedGene1 = singleSampleGeneList.get(i);
////                                        //判断混合基因是否包含此基因
////                                        if (mixSameCount == 0) {
////                                            mixSameCount = minSameMixCount;
////                                        }
////                                        //比对
////                                        resultMap = geneSplitSingleCompareUtil.compareGeneInfo(splitAuditedGene, auditedGene1, mixSameCount);
////                                        if (resultMap == null) {
////                                            continue;
////                                        }
////                                        if ((Boolean) resultMap.get("matched")) {
////                                            auditedGene1.setCaseID(mixedSampleGeneList.get(0).getCaseID());
////                                            insertMatchResultMixSingle(resultMap, splitAuditedGene, auditedGene1, compareQueue.getId());
////                                        }
////                                    }
////                                }
////                            }
////                        }
//                    }
//                }
//            }
//            logger.info("#####拆分单一比对定时任务结束 : " + LocalDateTime.now().toLocalTime());
//        } catch (Exception e) {
//            logger.error("#####拆分单一比对定时任务失败！!" + e);
//        }
//    }
//
//    /**
//     * 添加拆分单一比中详情信息
//     *
//     * @param resultMap
//     * @param splitedSampleGene
//     * @param singleSampleGene
//     */
//    private void insertMatchResultMixSingle(Map<String, Object> resultMap, SplitedSampleGene splitedSampleGene, SingleSampleGene singleSampleGene, String id) {
//        try {
//            //查看是否已经匹配过 如果已经匹配过了就修改  没有就添加
////            MatchResultSplitSingle matchResultSplitSingle = new MatchResultSplitSingle();
////            matchResultSplitSingle.setSplieId(splitedSampleGene.getId());
////            matchResultSplitSingle.setSingleId(singleSampleGene.getId());
////            List<MatchResultSplitSingle> matchResultSplitSingleList = matchResultSplitSingleService.selectBySampleGeneId(matchResultSplitSingle);
////            if (null != matchResultSplitSingleList && matchResultSplitSingleList.size() > 0) {
////                MatchResultSplitSingle matchResultSplitSingle1 = matchResultSplitSingleList.get(0);
////                matchResultSplitSingle1.setRatio(Short.valueOf(resultMap.get("sameCount").toString()));
////                matchResultSplitSingle1.setSplitDigit(Short.valueOf(resultMap.get("diffCount").toString()));
////                matchResultSplitSingleService.update(matchResultSplitSingle1);
////                //修改比对状态为已比对
////                CompareQueue compareQueue = new CompareQueue();
////                compareQueue.setId(id);
////                compareQueue.setStatus(Constants.MATCH_STATUS_01);
////                compareQueueService.updateStatus(compareQueue);
////            } else {
////                //添加拆分单一样本比中详情信息
////                MatchResultSplitSingle matchResultSplitSingle1 = new MatchResultSplitSingle();
////                matchResultSplitSingle1.setId(UUID.randomUUID().toString());
////                matchResultSplitSingle1.setSplieId(splitedSampleGene.getId());
////                matchResultSplitSingle1.setSingleId(singleSampleGene.getId());
////                matchResultSplitSingle1.setRatio(Short.valueOf(resultMap.get("sameCount").toString()));
////                matchResultSplitSingle1.setSplitDigit(Short.valueOf(resultMap.get("diffCount").toString()));
////                matchResultSplitSingle1.setComparisonTime(new Date());
////                matchResultSplitSingleService.insert(matchResultSplitSingle1);
////                //修改比对状态为已比对
////                CompareQueue compareQueue = new CompareQueue();
////                compareQueue.setId(id);
////                compareQueue.setStatus(Constants.MATCH_STATUS_01);
////                compareQueueService.updateStatus(compareQueue);
////
////                //添加比中消息信息
////                if (null != singleSampleGene.getCaseID()){
////                    CaseInfo caseInfo = caseInfoService.selectByCaseId(singleSampleGene.getCaseID());
////                    if (caseInfo != null){
////                        MobileNews mobileNews = new MobileNews();
////                        mobileNews.setId(UUID.randomUUID().toString());
////                        mobileNews.setTitle("比对消息提示");//标题
////                        mobileNews.setContent(caseInfo.getCaseName()+"拆分单一比对已比中");//内容
////                        mobileNews.setState(0);//状态  未读
////                        Date date = new Date();
////                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////                        String createdate = sdf.format(date);
////                        mobileNews.setCreateDatetime(createdate);
////                        mobileNews.setType(11);
////                        mobileNews.setUserid(caseInfo.getCreatePerson());
////                        mobileNews.setCaseId(singleSampleGene.getCaseID());//案件id
////                        mobileNews.setUserOrg(caseInfo.getOrgId());
////                        mobileNews.setMessageType(3);//消息类型
////                        mobileNewsMapper.insert(mobileNews);
////                        System.out.println("添加拆分单一比对比中消息");
////                    }
////                }
////            }
//        } catch (Exception e) {
//            logger.error("添加拆分单一比中详情信息失败！" + e);
//        }
//    }
}
