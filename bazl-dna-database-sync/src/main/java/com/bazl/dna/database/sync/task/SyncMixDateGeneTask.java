package com.bazl.dna.database.sync.task;

import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.common.PublicConstants;
import com.bazl.dna.common.ResponseData;
import com.bazl.dna.database.sync.client.GeneListServerClient;
import com.bazl.dna.database.sync.task.thread.SyncMixDateGeneThread;
import com.bazl.dna.util.DateTools;
import com.bazl.dna.util.DateUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class SyncMixDateGeneTask {

    private static final Logger LOG = LoggerFactory.getLogger(SyncMixDateGeneTask.class);

    @Autowired
    private SyncMixDateGeneThread thread;

    @Autowired
    private GeneListServerClient geneListServerClient;

    @Value("${compare.mixDate.enabled}")
    private boolean isEnabled;

    @Value("${thread.number}")
    private int threadNumber;

    @Value("${thread.size}")
    private int pageSize;

    @Value("${thread.day}")
    private int days;

    private int totalPage = 0;
    private int pageIndex = 0;

    /**
     * 执行任务
     */
    @Scheduled(fixedDelay = 18000)
//    @Scheduled(cron = "0 0 1 0 * ?") //每天凌晨执行一次
    public void execute() throws Exception {
        try {
            if (isEnabled) {
                List<Map<String, String>> listLocusName = new ArrayList<Map<String, String>>();
                ResponseData locusInfo = geneListServerClient.selectByLocusName();
                if (PublicConstants.SUCCESS_CODE == locusInfo.getCode()) {
                    List<Map<String, String>> llocus = new Gson().fromJson(locusInfo.getResult().toString(),
                            new TypeToken<List<Map<String, String>>>() {}.getType());
                    for (Map<String, String> object : llocus) {
                        listLocusName.add(object);
                    }
                }
                //获取进几天时间
                LOG.info("——————————————获取近"+ days +"天时间——————————————");
                String dateToString = DateUtil.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss");
                Date dateStartTime = DateUtil.getDateStartTime(dateToString);
                Date date = DateTools.addDateByDay(dateStartTime, -days);
                ResponseData singleGeneData = geneListServerClient.selectCountByDate(date);
                if (singleGeneData != null && singleGeneData.getCode() == PublicConstants.SUCCESS_CODE) {
                    JSONObject singleJson = JSONObject.parseObject(JSONObject.toJSONString(singleGeneData.getResult()));
                    int total = singleJson.getIntValue("singleGeneCount");
                    totalPage = total / pageSize + 1;
                    LOG.info("total: {}, totalPage:{}", total, totalPage);
                    for (int i = 0; i < threadNumber; i++) {
                        if (totalPage == pageIndex) {
                            break;
                        }
                        thread.execute(listLocusName, pageIndex, pageSize, date);
                        pageIndex++;
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("Error task: ", e);
        }
        LOG.info("执行定时任务结束：{}", new Date());

    }

}
