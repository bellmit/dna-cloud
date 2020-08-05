package com.bazl.dna.sys;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaAutoServiceRegistration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.bazl.dna.sys.entity.SysDictItemType;

/**
 * 接口测试
 * @author zhaoyong
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceApplicationTests {
	
	@MockBean
	private EurekaAutoServiceRegistration eurekaAutoServiceRegistration;
	
	@Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;
    
    private MockHttpServletRequestBuilder builder;
    
    private String token;

    @Before
    public void setUp() throws Exception {
    		mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    		
    		JSONObject paramJson = new JSONObject();
    		paramJson.put("userName", "admin");
    		paramJson.put("password", "E10ADC3949BA59ABBE56E057F20F883E");
    		
    		// login
    		builder = MockMvcRequestBuilders.post("/auth/login")
    				.contentType(MediaType.APPLICATION_JSON)
    				.header("Authorization", MediaType.APPLICATION_JSON)
    				.content(paramJson.toJSONString());
    		MvcResult mvcResult = mvc.perform(builder).andReturn();
    		// result auth token
    		String result = mvcResult.getResponse().getContentAsString();
    		JSONObject json = JSONObject.parseObject(result);
    		token = json.getJSONObject("result").getString("accessToken");
    }
	
	@Test
	public void testSysItemGetTypeById() throws Exception {
		Assert.assertNotNull(token);
		
		// getTypeById
		builder = MockMvcRequestBuilders.get("/sys/item/getTypeById/{id}", 1)
				.contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token);
		mvc.perform(builder).andDo(print());
	}
	
	@Test
	public void testSysItemAddDictItemType() throws Exception {
		Assert.assertNotNull(token);
		
		SysDictItemType type = new SysDictItemType();
		type.setTypeCode("aaa");
		type.setTypeName("111");
		type.setDelStatus("0");
		
		// addDictItemType
		builder = MockMvcRequestBuilders.post("/sys/item/addDictItemType")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSONObject.toJSONString(type))
				.header("Authorization", token);
		mvc.perform(builder).andDo(print());
	}

}
