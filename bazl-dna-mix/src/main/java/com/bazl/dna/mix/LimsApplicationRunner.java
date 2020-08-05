package com.bazl.dna.mix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.bazl.dna.mix.service.AlleleFrequencyService;

/**
 * Created by Sun on 2019/4/1.
 */
@Component
public class LimsApplicationRunner implements ApplicationRunner {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    AlleleFrequencyService alleleFrequencyService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("加载字典项开始...");
        logger.info("加载字典项开始...");
//        List<AlleleFrequency> alleleFrequencyList  = alleleFrequencyService.selectAll();
//        InitDict.alleles = alleleFrequencyList;
        System.out.println("加载字典项结束...");
        logger.info("加载字典项结束...");
    }

}
