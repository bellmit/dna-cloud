package com.bazl.dna.dpznsy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


@Component
public class BazlDnaDpznsyApplicationRunner implements ApplicationRunner {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    /*@Autowired
    AlleleFrequencyService alleleFrequencyService;
    @Autowired
    MarkerInfoService markerInfoService;*/

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("加载字典项开始...");
        logger.info("加载字典项开始...");
        /*List<AlleleFrequency> alleleFrequencyList = alleleFrequencyService.selectAll();
        List<MarkerInfo> markerInfoList = markerInfoService.selectAll();
        InitDict.alleles = alleleFrequencyList;
        InitDict.markers = markerInfoList;*/
        System.out.println("加载字典项结束...");
        logger.info("加载字典项结束...");
    }
}
