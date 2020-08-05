package com.bazl.dna.lims.core;

import com.bazl.dna.lims.core.compare.InitDict;
import com.bazl.dna.lims.core.model.po.AlleleFrequency;
import com.bazl.dna.lims.core.model.po.MarkerInfo;
import com.bazl.dna.lims.core.service.AlleleFrequencyService;
import com.bazl.dna.lims.core.service.MarkerInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Sun on 2019/4/1.
 */
@Component
public class LimsApplicationRunner implements ApplicationRunner {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    AlleleFrequencyService alleleFrequencyService;
    @Autowired
    MarkerInfoService markerInfoService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        System.out.println("加载字典项开始...");
        logger.info("加载字典项开始...");
        List<AlleleFrequency> alleleFrequencyList  = alleleFrequencyService.selectAll();
        List<MarkerInfo> markerInfoList = markerInfoService.selectAll();
        InitDict.alleles = alleleFrequencyList;
        InitDict.markers = markerInfoList;
//        System.out.println("加载字典项结束...");
        logger.info("加载字典项结束...");
    }

}
