package com.bazl.dna.test.common;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import com.bazl.dna.test.rabbitmq.MySender;

/**
 * Rabbitmq Test
 * @author zhaoyong
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Component
public class RabbitmqTests {

	@Autowired
	private MySender sender;

    @Test
    public void send() {
    	for (int i = 0; i < 10; i++) {
	    	String context = "hello " + new Date();
			System.out.println("Send: " + context);
	    	sender.send(context);
    	}
    }
}
