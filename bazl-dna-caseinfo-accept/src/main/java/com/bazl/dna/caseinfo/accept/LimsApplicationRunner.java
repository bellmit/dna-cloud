package com.bazl.dna.caseinfo.accept;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.bazl.dna.caseinfo.accept.compare.InitDict;
import com.bazl.dna.lims.model.po.AlleleFrequency;
import com.bazl.dna.lims.model.po.MarkerInfo;
import com.bazl.dna.lims.service.AlleleFrequencyService;
import com.bazl.dna.lims.service.MarkerInfoService;

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
