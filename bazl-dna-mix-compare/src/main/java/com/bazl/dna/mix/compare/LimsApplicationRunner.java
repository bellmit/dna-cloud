package com.bazl.dna.mix.compare;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Created by Sun on 2019/4/1.
 */
@Component
public class LimsApplicationRunner implements ApplicationRunner {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("加载字典项开始...");
        logger.info("加载字典项开始...");
        System.out.println("加载字典项结束...");
        logger.info("加载字典项结束...");
    }

}
