package com.bazl.dna.mix.task;

/**
 * @Author: lzn
 * @Date: 2019/8/26 15:09
 * @Version: 1.0
 *
 * 定时任务：获取首页统计数量
 */
//@Component
public class HomeCountTask {
//
//    private final Logger logger = LoggerFactory.getLogger(getClass());
//
//    @Autowired
//    MixedSampleGeneDAO mixedSampleGeneDAO;
//
//    @Autowired
//    SingleSampleGeneDAO singleSampleGeneDAO;
//
//    @Autowired
//    DictCountDAO dictCountDAO;
//
//    @Async
//    @Scheduled(fixedDelay = 7200000)  //间隔10分钟
//    public void firstHomeCount() throws InterruptedException {
//
//      int suspectsNumber = 0;
//        try {
//            suspectsNumber = mixedSampleGeneDAO.selectMatchedSuspectCount();
//            DictCount  dictCount=new DictCount();
//            dictCount.setDictTypeCode(Constants.DICT_TYPE_CODE_SUSPECTS_NUMBER);
//            dictCount.setDictCountNumber(String.valueOf(suspectsNumber));
//            dictCountDAO.updateDictCount(dictCount);
//        } catch (Exception ex) {
//            logger.error("首页查询混合样本比中嫌疑人的总数失败！！！", ex.getMessage());
//        }
//
//        int serialCaseMixedGeneCount = 0;
//        try {
//            serialCaseMixedGeneCount = mixedSampleGeneDAO.selectSerialCaseMixedSampleCount();
//            DictCount  dictCount=new DictCount();
//            dictCount.setDictTypeCode(Constants.DICT_TYPE_CODE_SERIAL_COUNT);
//            dictCount.setDictCountNumber(String.valueOf(serialCaseMixedGeneCount));
//            dictCountDAO.updateDictCount(dictCount);
//        } catch (Exception ex) {
//
//            logger.error("首页查询混合基因串并案比中嫌疑人总数失败！！！", ex.getMessage());
//        }
//
//        int mixedQualityCount = 0;
//        try {
//            mixedQualityCount = mixedSampleGeneDAO.selectMoreQualityGeneCount();
//            DictCount  dictCount=new DictCount();
//            dictCount.setDictTypeCode(Constants.DICT_TYPE_CODE_MIXEDQUALITY_COUNT);
//            dictCount.setDictCountNumber(String.valueOf(mixedQualityCount));
//            dictCountDAO.updateDictCount(dictCount);
//        } catch (Exception ex) {
//            logger.error("首页查询混合样本比中质控人员的总数失败！！！", ex.getMessage());
//        }
//
//        int mixedSampleGeneCount = 0;
//        try {
//            mixedSampleGeneCount = mixedSampleGeneDAO.selectMixedSampleGeneCount();
//            DictCount  dictCount=new DictCount();
//            dictCount.setDictTypeCode(Constants.DICT_TYPE_CODE_MIXED_GENE_COUNT);
//            dictCount.setDictCountNumber(String.valueOf(mixedSampleGeneCount));
//            dictCountDAO.updateDictCount(dictCount);
//        } catch (Exception ex) {
//            logger.error("查询混合样本总数失败！！！", ex.getMessage());
//        }
//
//        int singleSampleGeneCount = 0;
//        try {
//            singleSampleGeneCount = singleSampleGeneDAO.selectSingleSampleGeneCount();
//            DictCount  dictCount=new DictCount();
//            dictCount.setDictTypeCode(Constants.DICT_TYPE_CODE_SINGLE_GENE_COUNT);
//            dictCount.setDictCountNumber(String.valueOf(singleSampleGeneCount));
//            dictCountDAO.updateDictCount(dictCount);
//        } catch (Exception ex) {
//            logger.error("查询单一样本总数失败！！！", ex.getMessage());
//        }
//    }
}
